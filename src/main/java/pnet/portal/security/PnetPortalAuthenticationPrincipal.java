package pnet.portal.security;

import java.io.Serializable;
import java.time.LocalDateTime;

import pnet.security.identity.PnetAuthenticationMode;
import pnet.security.identity.PnetAuthenticationType;

public class PnetPortalAuthenticationPrincipal implements Serializable
{
    private static final long serialVersionUID = 599247596567556272L;

    private final Integer gpId;
    private final PnetAuthenticationType authenticationType;
    private final PnetAuthenticationMode authenticationMode;
    private final int nistAuthenticationLevel;

    private final LocalDateTime lastLogin;

    private final LocalDateTime loginTime;

    private final LocalDateTime passwordLastChanged;

    private final Integer swappedUserId;
    private final String swappedName;
    private final boolean passwordChangeRequired;

    private boolean certificateAvailable = false;
    private boolean loginCheckFailed;
    private String username;

    public PnetPortalAuthenticationPrincipal(Integer gpId, PnetAuthenticationType authenticationType,
        PnetAuthenticationMode authenticationMode, int nistAuthenticationLevel, LocalDateTime lastLogin,
        LocalDateTime loginTime, LocalDateTime passwordLastChanged, boolean passwordChangeRequired)
    {
        this(gpId, authenticationType, authenticationMode, nistAuthenticationLevel, lastLogin, loginTime,
            passwordLastChanged, passwordChangeRequired, null, null);
    }

    public PnetPortalAuthenticationPrincipal(Integer gpId, PnetAuthenticationType authenticationType,
        PnetAuthenticationMode authenticationMode, int nistAuthenticationLevel, LocalDateTime lastLogin,
        LocalDateTime loginTime, LocalDateTime passwordLastChanged, boolean passwordChangeRequired,
        Integer swappedUserId, String swappedName)
    {
        super();

        this.gpId = gpId;
        this.authenticationType = authenticationType;
        this.authenticationMode = authenticationMode;
        this.nistAuthenticationLevel = nistAuthenticationLevel;
        this.lastLogin = lastLogin;
        this.loginTime = loginTime;
        this.passwordLastChanged = passwordLastChanged;
        this.swappedUserId = swappedUserId;
        this.swappedName = swappedName;
        this.passwordChangeRequired = passwordChangeRequired;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Integer getGpId()
    {
        return gpId;
    }

    public PnetAuthenticationType getAuthenticationType()
    {
        return authenticationType;
    }

    public PnetAuthenticationMode getAuthenticationMode()
    {
        return authenticationMode;
    }

    public int getNistAuthenticationLevel()
    {
        return nistAuthenticationLevel;
    }

    public boolean isCertificateAvailable()
    {
        return certificateAvailable;
    }

    public void setCertificateAvailable(boolean certificateAvailable)
    {
        this.certificateAvailable = certificateAvailable;
    }

    public LocalDateTime getLastLogin()
    {
        return lastLogin;
    }

    public LocalDateTime getLoginTime()
    {
        return loginTime;
    }

    public LocalDateTime getPasswordLastChanged()
    {
        return passwordLastChanged;
    }

    public boolean isPasswordChangeRequired()
    {
        return passwordChangeRequired;
    }

    public Integer getSwappedUserId()
    {
        return swappedUserId;
    }

    public String getSwappedName()
    {
        return swappedName;
    }

    public boolean isLoginCheckFailed()
    {
        return loginCheckFailed;
    }

    public void setLoginCheckFailed(boolean loginCheckFailed)
    {
        this.loginCheckFailed = loginCheckFailed;
    }
}
