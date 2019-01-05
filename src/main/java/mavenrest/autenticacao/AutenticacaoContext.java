package mavenrest.autenticacao;

import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author pedro
 */
public class AutenticacaoContext implements SecurityContext {

    private final Principal p;
    private final boolean isSecure;
    private final String authenticationScheme;

    public AutenticacaoContext(Principal u, boolean isSecure, String authenticationScheme) {
        this.p = u;
        this.isSecure = isSecure;
        this.authenticationScheme = authenticationScheme;
    }

    @Override
    public Principal getUserPrincipal() {
        return p;
    }

    @Override
    public boolean isUserInRole(String role) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isSecure() {
        return isSecure;
    }

    @Override
    public String getAuthenticationScheme() {
        return authenticationScheme;
    }

}
