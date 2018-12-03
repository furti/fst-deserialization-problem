package pnet.security.identity;

public enum PnetAuthenticationType
{
    NONE(-1, "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified"),

    USERPASS(2, "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport"),

    CERTIFICATE(1, "urn:oasis:names:tc:SAML:2.0:ac:classes:TLSClient"),

    SAML(0, "urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession"),

    SAML2(0, "urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession"),

    OPENID_CONNECT(0, "urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession"),

    REQUESTHEADER(0, "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified"),

    REQUESTHEADER_OUTDATED(0, "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified"),

    SALESNUMBER(0, "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified"),

    /**
     * Token must be level 2. Otherwise the settings page will not work. Have to change this when we know what happens
     * with the portal.
     */
    TOKEN(2, "urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession"),

    PKI_CARD(3, "urn:oasis:names:tc:SAML:2.0:ac:classes:SmartcardPKI"),

    SERVICE(0, "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified"),

    REMEMBERME(0, "urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession"),

    SERVICE_OVERRIDE(0, "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified"),

    JWT(0, "urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession");

    /**
     * Defines the http://openid.net/specs/openid-provider-authentication-policy-extension-1_0.html#NIST_Examples
     * authentication level for this authentication type.
     */
    private final int nistAuthenticationLevel;

    /**
     * Defines a SAML http://docs.oasis-open.org/security/saml/v2.0/saml-authn-context-2.0-os.pdf
     * AuthenticationContextClassReference.
     */
    private final String samlAuthenticationContextClassReference;

    PnetAuthenticationType(int nistAuthenticationLevel, String samlAuthenticationContextClassReference)
    {
        this.nistAuthenticationLevel = nistAuthenticationLevel;
        this.samlAuthenticationContextClassReference = samlAuthenticationContextClassReference;
    }

    /**
     * @return if it is strong
     * @deprecated use the nist authentication level instead
     */
    @Deprecated
    public boolean isStrongAuthentication()
    {
        return nistAuthenticationLevel >= 3;
    }

    public int getNistAuthenticationLevel()
    {
        return nistAuthenticationLevel;
    }

    public String getSamlAuthenticationContextClassReference()
    {
        return samlAuthenticationContextClassReference;
    }
}