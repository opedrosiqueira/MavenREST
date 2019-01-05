package mavenrest.autenticacao;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mavenrest.AppPropriedades;
import mavenrest.user.User;
import mavenrest.user.UserDAO;

@Autenticado
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AutenticacaoFiltro implements ContainerRequestFilter {

    @Inject
    UserDAO userDAO;

    @Inject
    AppPropriedades ap;

    @Context
    private ResourceInfo ri;

    @Override
    public void filter(ContainerRequestContext req) {
        String token = req.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith("Bearer")) {
            req.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Esquema de autenticacao invalido ou inexistente").build());
            return;
        }
        token = token.substring(7);
        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(ap.getSecret()));
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            User u = userDAO.getUserByEmail(claims.getBody().getSubject());
            boolean isSecure = "https".equalsIgnoreCase(req.getUriInfo().getRequestUri().getScheme());
            req.setSecurityContext(new AutenticacaoContext(u, isSecure, "Bearer"));
            //parei aqui: nao permitir se nao for autorizado
            System.out.println(Arrays.toString(ri.getResourceMethod().getAnnotation(Autenticado.class).value()));

        } catch (JwtException e) {
            req.abortWith(Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build());
        }
    }

}
