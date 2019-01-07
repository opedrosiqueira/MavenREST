package mavenrest.autenticacao;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import mavenrest.auth.AutenticacaoHash;
import mavenrest.auth.AutenticacaoPropriedades;
import mavenrest.auth.Autenticado;
import mavenrest.user.User;
import mavenrest.user.UserDAO;

@Path("auth")
public class UsuarioAuthREST {

    @Inject
    UserDAO userDAO;

    @Inject
    AutenticacaoPropriedades ap;

    @POST
    public Response loginUser(
            @FormParam("email") String email,
            @FormParam("senha") String senha) {
        User u = userDAO.getUserByEmail(email);
        if (u == null || !AutenticacaoHash.igual(senha, u.getSalt(), u.getPassword())) {
            return Response.status(Response.Status.FORBIDDEN).entity("usuario ou senha invalida").build();
        }
        //parei aqui: criar uma funcao que devolve o token dentro da api Auth
        Date dateIssued = new Date();
        Date dateExpires = new Date(dateIssued.getTime() + ap.getExpiresAfter());
        String token = Jwts.builder()
                .setIssuer(ap.getIssuer())
                .setSubject(u.getEmail())
                .setIssuedAt(dateIssued)
                .setExpiration(dateExpires)
                .signWith(ap.getKey())
                .compact();

        return Response.ok(token).build();
    }

    @GET
    @Path("todos")
    public String get(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() != null) {
            System.out.println(sc.getUserPrincipal());
        }
        return "todos tem acesso a esse servico";
    }

    @Autenticado
    @GET
    @Path("autenticado")
    public String getAutenticado(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() != null) {
            System.out.println(sc.getUserPrincipal());
        }
        return "voce esta autenticado, por isso tem acesso a esse servico";
    }

    @Autenticado("admin")
    @GET
    @Path("admin")
    public String getAdmin(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() != null) {
            System.out.println(sc.getUserPrincipal());
        }
        return "voce eh admin, por isso tem acesso a esse servico";
    }

    @Autenticado({"admin", "gerente"})
    @GET
    @Path("gerente")
    public String getGerente(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() != null) {
            System.out.println(sc.getUserPrincipal());
        }
        return "voce eh gerente, por isso tem acesso a esse servico";
    }

}
