package pnet.portal.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PnetPortalUserEmployment implements Serializable
{

    private static final long serialVersionUID = -4849035758189891100L;

    private Integer companyId;
    private Integer pfId;

    private ArrayList<PnetPortalUserEmploymentRight> rights;
    private ArrayList<PnetPortalUserEmploymentDependency> functions;
    private ArrayList<PnetPortalUserEmploymentDependency> activities;

    private ArrayList<String> brandIds;
    private boolean brandFree;

    public List<PnetPortalUserEmploymentRight> getRights()
    {
        return rights;
    }

    public void setRights(ArrayList<PnetPortalUserEmploymentRight> rights)
    {
        this.rights = rights;
    }

    public boolean hasRight(String rightMC)
    {
        if ((rightMC == null) || (rights == null))
        {
            return false;
        }

        for (PnetPortalUserEmploymentDependency right : rights)
        {
            if (right.getMc() != null && right.getMc().equals(rightMC))
            {
                return true;
            }
        }

        return false;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(Integer companyId)
    {
        this.companyId = companyId;
    }

    public List<String> getBrandIds()
    {
        return brandIds;
    }

    public boolean isBrandFree()
    {
        return brandFree;
    }

    public void setBrandIds(ArrayList<String> brandIds)
    {
        this.brandIds = brandIds;
    }

    public void setBrandFree(boolean brandFree)
    {
        this.brandFree = brandFree;
    }

    public Integer getPfId()
    {
        return pfId;
    }

    public void setPfId(Integer pfId)
    {
        this.pfId = pfId;
    }

    public List<PnetPortalUserEmploymentDependency> getFunctions()
    {
        return functions;
    }

    public List<PnetPortalUserEmploymentDependency> getActivities()
    {
        return activities;
    }

    public void setFunctions(ArrayList<PnetPortalUserEmploymentDependency> functions)
    {
        this.functions = functions;
    }

    public void setActivities(ArrayList<PnetPortalUserEmploymentDependency> activities)
    {
        this.activities = activities;
    }

    public String toString()
    {
        return String
            .format(
                "PnetPortalUserEmployment [companyId=%s, pfId=%s, rights=%s, functions=%s, activities=%s, brandIds=%s, brandFree=%s]",
                companyId, pfId, rights, functions, activities, brandIds, brandFree);
    }

}
