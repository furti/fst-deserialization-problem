package com.poi.egh.pnet.user.dto;

import java.io.Serializable;

public class PnetPortalUserMenuItemCompany implements Serializable
{
    private static final long serialVersionUID = 3475754975030511190L;

    private String brandId;
    private Integer companyId;

    public PnetPortalUserMenuItemCompany()
    {
    }

    public PnetPortalUserMenuItemCompany(String brandId, Integer companyId)
    {
        super();
        this.brandId = brandId;
        this.companyId = companyId;
    }

    public String getBrandId()
    {
        return brandId;
    }

    public void setBrandId(String brandId)
    {
        this.brandId = brandId;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(Integer companyId)
    {
        this.companyId = companyId;
    }

    public boolean isBrandFree()
    {
        return brandId == null;
    }
}
