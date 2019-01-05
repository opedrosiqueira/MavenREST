package mavenrest.autenticacao.exemplo;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import mavenrest.autenticacao.Autenticado;

@Path("usuario")
public class UsuarioAuthREST {

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
