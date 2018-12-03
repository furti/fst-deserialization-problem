package io.github.furti.fstserializationproblem;

import static java.util.Objects.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.codec.binary.Base64;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.simpleapi.DefaultCoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import pnet.portal.security.PnetAuthenticationToken;
import pnet.portal.security.PnetPortalAuthenticationDetails;
import pnet.portal.security.PnetPortalUserEmployment;

public class DeserializeSession
{
    private static final ThreadLocal<DefaultCoder> CODERS = ThreadLocal.withInitial(() -> new DefaultCoder());
    private static final FSTConfiguration CONF = FSTConfiguration.createDefaultConfiguration();
    private static final List<String> FIELD_NAMES_TO_IGNORE = Arrays.asList("serialVersionUID");

    public static void main(String[] args) throws SQLException, IOException
    {
        System.out.print("folder: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            String directory = reader.readLine();
            List<Pair<String, String>> base64Datas = readData(directory);

            // When the list is not reversed it works. So it does not depend on the data in the list but on the order the data is processed
            Collections.reverse(base64Datas);

            List<Pair<String, byte[]>> byteList = new ArrayList<>();

            for (Pair<String, String> base64Data : base64Datas)
            {
                byteList.add(Pair.of(base64Data.getLeft(), Base64.decodeBase64(base64Data.getRight())));
            }

            int count = 0;

            for (Pair<String, byte[]> bytes : byteList)
            {
                count++;

                System.out.println(bytes.getLeft());

                try
                {
                    deserialize(bytes.getRight());
                }
                catch (Exception e)
                {
                    System.err.println(String.format("%s / %s", count, bytes.getLeft()));
                    e.printStackTrace(System.err);
                }
            }
        }
        finally
        {
            reader.close();
        }
    }

    private static void deserialize(byte[] bytes) throws Exception
    {
        // Use one of the 3 methods when deserializing. 1 and 3 fail. Two works. 

        // 1. This fails even when called with a single thread. So no problem with concurrent usage
        //        SecurityContext object = (SecurityContext) CODERS.get().toObject(bytes);

        // 2. Using this line instead of the previous one works. So it has to do with reusing coders
        //        SecurityContext object = (SecurityContext) new DefaultCoder().toObject(bytes);

        // 3. This fails also. So it has nothing to do with the default coder. It is a problem of the config or the object input
        FSTObjectInput objectInput = CONF.getObjectInput(bytes);
        SecurityContext object = (SecurityContext) objectInput.readObject();

        Authentication authentication = object.getAuthentication();

        if (authentication instanceof PnetAuthenticationToken)
        {
            System.out.println("-------");
            printData(authentication, "");
            System.out.println("-------");
            validatePortalToken((PnetAuthenticationToken) authentication);
        }
        else
        {
            throw new IllegalArgumentException(
                String.format("Unexpected authentication type %s", authentication.getClass()));
        }
    }

    private static void printData(Object object, String prefix) throws Exception
    {
        Class<? extends Object> clazz = object.getClass();

        List<Field> fields = getAllFields(clazz);

        for (Field field : fields)
        {
            if (FIELD_NAMES_TO_IGNORE.contains(field.getName()))
            {
                continue;
            }

            field.setAccessible(true);
            Object value = field.get(object);

            if (value instanceof Collection)
            {
                System.out.println(String.format("%s%s: [", prefix, field.getName(), value));

                Collection<?> collection = (Collection<?>) value;

                for (Object entry : collection)
                {
                    String subPrefix = prefix + "    ";
                    System.out.println(String.format("%s%s", subPrefix, entry));

                    if (!ignoreSubFields(entry))
                    {
                        printData(entry, subPrefix + "    ");
                    }
                }

                System.out.println(String.format("%s]", prefix, field.getName(), value));
            }
            else
            {
                System.out.println(String.format("%s%s: %s", prefix, field.getName(), value));

                if (!ignoreSubFields(value))
                {
                    printData(value, prefix + "    ");
                }
            }
        }
    }

    private static List<Field> getAllFields(Class<?> clazz)
    {
        List<Field> fields = new ArrayList<>();

        Class<?> superClass = clazz;

        while (!Object.class.equals(superClass))
        {
            Field[] declaredFields = superClass.getDeclaredFields();

            for (Field f : declaredFields)
            {
                fields.add(f);
            }

            superClass = superClass.getSuperclass();
        }

        return fields;
    }

    private static boolean ignoreSubFields(Object value)
    {
        if (value == null)
        {
            return true;
        }

        Class<? extends Object> clazz = value.getClass();

        if (Number.class.isAssignableFrom(clazz)
            || Boolean.class.equals(clazz)
            || Enum.class.isAssignableFrom(clazz)
            || LocalDateTime.class.isAssignableFrom(clazz)
            || String.class.isAssignableFrom(clazz)
            || Locale.class.isAssignableFrom(clazz))
        {
            return true;
        }

        return false;
    }

    private static List<Pair<String, String>> readData(String directory) throws IOException
    {
        System.out.println("Processing Order");
        System.out.println("----");

        Path dir = Paths.get(directory);
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir);

        Iterator<Path> files = directoryStream.iterator();

        List<Pair<String, String>> datas = new ArrayList<>();

        while (files.hasNext())
        {
            Path file = files.next();
            BufferedReader reader = Files.newBufferedReader(file);

            try
            {
                String data = reader.readLine();

                datas.add(Pair.of(file.getFileName().toString(), data));

                System.out.println(file.getFileName());
            }
            finally
            {
                reader.close();
            }
        }

        System.out.println("----");

        return datas;
    }

    private static void validatePortalToken(PnetAuthenticationToken authentication)
    {
        PnetPortalAuthenticationDetails details = (PnetPortalAuthenticationDetails) authentication.getDetails();

        validatePortalEmployments(details.getEmployments());
    }

    private static void validatePortalEmployments(ArrayList<PnetPortalUserEmployment> employments)
    {
        Iterator<PnetPortalUserEmployment> iterator = employments.iterator();

        while (iterator.hasNext())
        {
            PnetPortalUserEmployment employment = (PnetPortalUserEmployment) iterator.next();

            // Add this because there where errors where a string.value was null after deserialization
            employment.hasRight("SOMETHING");
        }
    }

    @SuppressWarnings("unused")
    private static void validateStringCollection(Collection<String> strings)
    {
        requireNonNull(strings);

        Iterator<String> iterator = strings.iterator();

        while (iterator.hasNext())
        {
            String s = (String) iterator.next();
        }
    }
}
