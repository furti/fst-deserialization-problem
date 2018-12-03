# fst-deserialization-problem

This repository contains a minimal test case that reproduces https://github.com/RuedigerMoeller/fast-serialization/issues/270 in the fast-serialization library.

## License

There is no license on purpose, as this repository contains code written from me and others for a company. I'm allowed to publish the relevant parts for fixing the problem with fst. So **don't** use this code for anything else than fixing the linked issue :)

## Usage

This repository contains a maven project with three dependencies:
 - fst:2.57: Is needed because we wan't to show a fst problem
 - commons-coded: For Base64 stuff
 - spring-security-core: Because the serialized data depends on classes from spring-security

1. Setup a maven repository in your favourite IDE
2. Run or Debug ```io.github.furti.fstserializationproblem.DeserializeSession.main(String[])``` as a Java Application.
3. The Program will ask you for a folder, containing the 3 sessions. (It will print "folder:" in the console)
    - The folder containing the 3 sessions is called ```testdata``` and is also inside this repository.
    - Simply paste ```/path/to/repository/testdata``` in the console and hit Enter
4. Now the testcase should produce some output in the console.
    - The output is described later on

**Important**
The program prints the processing order to the console. Make sure that the sessions are read in the following order:

```
Processing Order
----
001d2226-bb05-4de6-8c05-bb74ae4399d7.txt
003887a7-acbb-482b-8f96-b2bce27f3ee2.txt
0046470e-8396-441e-b694-606b59da67d3.txt
----
```

## Understanding the Test Case Code

The test case reads the folder, containing the session files, from the command line and reads all files inside the given folder. Each file contains a single line containing Base64 encoded binary data.

Next the session list is reversed. This is a important step. Because the problem only occurs when the data is deserialized in a specific order.
**Hint:** You can remove ```Collections.reverse(base64Datas);``` here, and the deserialization of the data will succeed.

Next we decode the Base64 data.

And now the deserialize method is called for each entry in the list. Basically this method deserializes the binary data in the file, outputs the content to the console, and performs a simply test to ensure that the data is correct.

### The deserialize method in depth

There are three options available to setup the FSTObjectInput in the method. 

```java
// Use one of the 3 methods when deserializing. 1 and 3 fail. Two works. 

// 1. This fails even when called with a single thread. So no problem with concurrent usage
//        SecurityContext object = (SecurityContext) CODERS.get().toObject(bytes);

// 2. Using this line instead of the previous one works. So it has to do with reusing coders
//        SecurityContext object = (SecurityContext) new DefaultCoder().toObject(bytes);

// 3. This fails also. So it has nothing to do with the default coder. It is a problem of the config or the object input
FSTObjectInput objectInput = CONF.getObjectInput(bytes);
SecurityContext object = (SecurityContext) objectInput.readObject();
```

So we can proof the following:
 - The Problem must be somewhere in FSTObjectInput, because it fails with FSTObjectInputs obtained directly by the FSTConfiguration, and with DefaultCoders.
 - The problem has nothing to do with the data itself. Because option 2 (Creating a new DefaultCoder for each entry) does not fail. So the data is perfectly valid
 - It has nothing to do with non Threadsafe usage of the library. We run a simple java application without any threads here.

 The validation logic is a simple call to ```pnet.portal.security.PnetPortalUserEmployment.hasRight(String)``` for each employment object in the deserialized data. Because the error occured exactly at this position in the running application.
 **Hint:** The data was anonymized. So a lot of IDs and string values are the same for each entry.

## Understanding the console output

First the application prints the order the sessions where read (This is the order before reversing the sessions!).

```
Processing Order
----
001d2226-bb05-4de6-8c05-bb74ae4399d7.txt
003887a7-acbb-482b-8f96-b2bce27f3ee2.txt
0046470e-8396-441e-b694-606b59da67d3.txt
----
```

Next it prints the data of each deserialized session. It does so by using reflection to get the data for each field in the whole object tree.

```
0046470e-8396-441e-b694-606b59da67d3.txt
-------
principal: pn....
-------
```

You should pay attention to the list of ```pnet.portal.security.PnetPortalUserEmploymentRight``` for the last session.
Normally such an object looks like this:

```
pnet.portal.security.PnetPortalUserEmploymentRight@cc285f4
    appId: 5
    id: 249
    mc: 433
    brands: [
        V
        L
        A
    ]
}
```

The matchcode, even looking like a Integer in this case, is a string. But there are two entries in the list that look like this:

```
pnet.portal.security.PnetPortalUserEmploymentRight@55f3ddb1
    appId: 5
    id: 250
    mc: [
        com.poi.egh.pnet.user.dto.PnetPortalUserMenuItemCompany@2d209079
            brandId: null
            companyId: 11111
        com.poi.egh.pnet.user.dto.PnetPortalUserMenuItemCompany@6bdf28bb
            brandId: null
            companyId: 11111
    ]
    brands: [
        V
        L
        A
    ]
```

Suddenly the matchcode is a collection of PnetPortalUserMenuItemCompany objects.

The program also prints each exception it encounters while deserializing to the console. So we should see something like this somewhere in the output:

```
3 / 001d2226-bb05-4de6-8c05-bb74ae4399d7.txt
java.lang.NullPointerException
	at java.lang.String.equals(String.java:982)
	at pnet.portal.security.PnetPortalUserEmployment.hasRight(PnetPortalUserEmployment.java:41)
	at io.github.furti.fstserializationproblem.DeserializeSession.validatePortalEmployments(DeserializeSession.java:257)
	at io.github.furti.fstserializationproblem.DeserializeSession.validatePortalToken(DeserializeSession.java:245)
	at io.github.furti.fstserializationproblem.DeserializeSession.deserialize(DeserializeSession.java:106)
	at io.github.furti.fstserializationproblem.DeserializeSession.main(DeserializeSession.java:70)
```

We can see here, that it fails at line 982 in the java.lang.String.equals method. Under normal circumstances it should be nearly impossible to fail at this location.

## Additional findings (03.12.2018 09:10)

After I added the description I looked over the console output again. And I found something interesting there.

When we look at the wrong ```mc``` value of the ```PnetPortalUserEmploymentRight``` for the last session, we see that it contains a list of PnetPortalUserMenuItemCompany objects.
But the same list of this two objects is already output for the previous session.

It looks to me like fst is reusing already seen objects and this cache is not cleared correctly before each invocation.