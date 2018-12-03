package pnet.portal.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PnetPortalAuthenticationDetails implements Serializable
{
    private static final long serialVersionUID = -2165426503808436695L;

    private final String pnId;
    private final String displayName;
    private final Integer contactCompanyId;
    private final Locale labelLocale;
    private final Locale dateLocale;

    private final ArrayList<PnetPortalUserSection> menu;
    private final ArrayList<String> portals;
    private final ArrayList<PnetPortalUserEmployment> employments;
    private final HashMap<String, String> moduleRedirects;

    public PnetPortalAuthenticationDetails(String pnId, String displayName, Integer contactCompanyId,
        Locale labelLocale, Locale dateLocale, ArrayList<PnetPortalUserSection> menu, ArrayList<String> portals,
        ArrayList<PnetPortalUserEmployment> employments, HashMap<String, String> moduleRedirects)
    {
        super();
        this.pnId = pnId;
        this.displayName = displayName;
        this.contactCompanyId = contactCompanyId;
        this.labelLocale = labelLocale;
        this.dateLocale = dateLocale;
        this.menu = menu;
        this.portals = portals;
        this.employments = employments;
        this.moduleRedirects = moduleRedirects;
    }

    public String getPnId()
    {
        return pnId;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public Integer getContactCompanyId()
    {
        return contactCompanyId;
    }

    public Locale getLabelLocale()
    {
        return labelLocale;
    }

    public Locale getDateLocale()
    {
        return dateLocale;
    }

    public List<PnetPortalUserSection> getMenu()
    {
        return menu;
    }

    public ArrayList<String> getPortals()
    {
        return portals;
    }

    public ArrayList<PnetPortalUserEmployment> getEmployments()
    {
        return employments;
    }

    public Map<String, String> getModuleRedirects()
    {
        return moduleRedirects;
    }

    public boolean hasRight(String mc)
    {
        if (mc == null || employments == null)
        {
            return false;
        }

        for (PnetPortalUserEmployment employment : employments)
        {
            if (employment.hasRight(mc))
            {
                return true;
            }
        }

        return false;
    }
}
