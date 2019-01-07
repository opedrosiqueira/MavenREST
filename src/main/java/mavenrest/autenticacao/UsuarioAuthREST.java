package mavenrest.autenticacao;

import authrest.AutenticacaoToken;
import authrest.Autenticado;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("auth")
public class UsuarioAuthREST {

    @Inject
    AutenticacaoToken at;

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
