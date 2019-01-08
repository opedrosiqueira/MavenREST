package mavenrest.exemplo3;

import authrest.AuthToken;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import authrest.Auth;

@Path("auth")
public class UsuarioREST {

    @Inject
    AuthToken at;

    @POST
    public Response loginUser(
            @FormParam("email") String email,
            @FormParam("senha") String senha) {
        String token = at.getToken(email, senha);
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

    @Auth
    @GET
    @Path("autenticado")
    public String getAutenticado(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() != null) {
            System.out.println(sc.getUserPrincipal());
        }
        return "voce esta autenticado, por isso tem acesso a esse servico";
    }

    @Auth("admin")
    @GET
    @Path("admin")
    public String getAdmin(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() != null) {
            System.out.println(sc.getUserPrincipal());
        }
        return "voce eh admin, por isso tem acesso a esse servico";
    }

    @Auth({"admin", "gerente"})
    @GET
    @Path("gerente")
    public String getGerente(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() != null) {
            System.out.println(sc.getUserPrincipal());
        }
        return "voce eh gerente, por isso tem acesso a esse servico";
    }

}
