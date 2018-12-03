package pnet.portal.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class PnetAuthenticationToken extends AbstractAuthenticationToken
{
    private static final long serialVersionUID = -859890841423024932L;
    private final PnetPortalAuthenticationPrincipal principal;

    public PnetAuthenticationToken(PnetPortalAuthenticationPrincipal principal)
    {
        super(Collections.emptyList());
        this.principal = principal;
        setAuthenticated(true);
    }

    public PnetAuthenticationToken(PnetPortalAuthenticationPrincipal principal, PnetPortalAuthenticationDetails details,
        Collection<? extends GrantedAuthority> roles)
    {
        super(roles);

        this.principal = principal;

        super.setDetails(details);
        super.setAuthenticated(true);
    }

    @Override
    public PnetPortalAuthenticationPrincipal getPrincipal()
    {
        return principal;
    }

    @Override
    public Object getDetails()
    {
        return super.getDetails();
    }

    @Override
    public Object getCredentials()
    {
        return null;
    }
}
