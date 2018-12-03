/**
 *
 */
package pnet.portal.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PnetPortalUserSection implements Serializable
{
    private static final long serialVersionUID = -5159494435582937686L;

    private String matchcode;
    private ArrayList<PnetPortalUserMenuItem> menuItems;

    public List<PnetPortalUserMenuItem> getMenuItems()
    {
        return menuItems;
    }

    public void setMenuItems(ArrayList<PnetPortalUserMenuItem> menuItems)
    {
        this.menuItems = menuItems;
    }

    public String getMatchcode()
    {
        return matchcode;
    }

    public void setMatchcode(String matchcode)
    {
        this.matchcode = matchcode;
    }
}
