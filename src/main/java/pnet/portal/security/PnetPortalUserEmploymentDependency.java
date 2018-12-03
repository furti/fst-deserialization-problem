/**
 *
 */
package pnet.portal.security;

import java.io.Serializable;
import java.util.List;

public class PnetPortalUserEmploymentDependency implements Serializable
{

    private static final long serialVersionUID = -6555681422994416458L;

    private Integer id;
    private String mc;
    private List<String> brands;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getMc()
    {
        return mc;
    }

    public void setMc(String mc)
    {
        this.mc = mc;
    }

    public List<String> getBrands()
    {
        return brands;
    }

    public void setBrands(List<String> brands)
    {
        this.brands = brands;
    }

}
