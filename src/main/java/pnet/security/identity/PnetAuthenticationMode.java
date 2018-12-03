package pnet.security.identity;

public enum PnetAuthenticationMode
{
    USER_NORMAL(true),
    USER_RIGHTTRANSFER,
    USER_MENUPREVIEW,
    TOKEN_DIRECTLINK(true),
    PASSWORD_RESET,
    SUPPORT_DATA;

    private final boolean fullAuthenticationMode;

    PnetAuthenticationMode()
    {
        this(false);
    }

    PnetAuthenticationMode(boolean fullAuthenticationMode)
    {
        this.fullAuthenticationMode = fullAuthenticationMode;
    }

    public boolean isFullAuthenticationMode()
    {
        return fullAuthenticationMode;
    }

}