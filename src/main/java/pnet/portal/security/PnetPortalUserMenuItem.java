/**
 *
 */
package pnet.portal.security;

import java.io.Serializable;
import java.util.ArrayList;

import com.poi.egh.pnet.user.dto.PnetPortalUserMenuItemCompany;

public class PnetPortalUserMenuItem implements Serializable
{
    private static final long serialVersionUID = -672528397679119112L;

    private Integer mnId;
    private Integer applicationId;
    private Integer moduleId;
    private boolean favorite;
    private boolean lockedInRighttransfer;
    private ArrayList<PnetPortalUserMenuItemCompany> companyBrands;
    private ArrayList<PnetPortalUserMenuItem> children;

    public Integer getMnId()
    {
        return mnId;
    }

    public void setMnId(Integer mnId)
    {
        this.mnId = mnId;
    }

    public ArrayList<PnetPortalUserMenuItem> getChildren()
    {
        return children;
    }

    public void setChildren(ArrayList<PnetPortalUserMenuItem> children)
    {
        this.children = children;
    }

    public ArrayList<PnetPortalUserMenuItemCompany> getCompanyBrands()
    {
        return companyBrands;
    }

    public void setCompanyBrands(ArrayList<PnetPortalUserMenuItemCompany> companyBrands)
    {
        this.companyBrands = companyBrands;
    }

    public Integer getApplicationId()
    {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId)
    {
        this.applicationId = applicationId;
    }

    public Integer getModuleId()
    {
        return moduleId;
    }

    public void setModuleId(Integer moduleId)
    {
        this.moduleId = moduleId;
    }

    public boolean isFavorite()
    {
        return favorite;
    }

    public void setFavorite(boolean favorite)
    {
        this.favorite = favorite;
    }

    public boolean isLockedInRighttransfer()
    {
        return lockedInRighttransfer;
    }

    public void setLockedInRighttransfer(boolean lockedInRighttransfer)
    {
        this.lockedInRighttransfer = lockedInRighttransfer;
    }
}
