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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.ws.rs.PUT;

@Path("auth")
public class UsuarioREST {

    @Inject
    AuthToken at;

    @Inject
    UsuarioDAO dao;

    @POST
    @Path("login")
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

    /*
    curl -X POST 'http://localhost:8080/MavenREST/rest/auth' -H 'Content-Type: application/x-www-form-urlencoded' -d 'nome=Henrique&email=henrique@gmail.com&senha=fdsa&permissao=admin'
     */
    @POST
    public String createUser(
            @FormParam("nome") String nome,
            @FormParam("email") String email,
            @FormParam("senha") String senha,
            @FormParam("permissao") String permissao) throws NoSuchAlgorithmException, InvalidKeySpecException {
        dao.createUser(nome, email, senha, permissao);
        return "criado com sucesso!";
    }

    /*
    curl -X PUT 'http://localhost:8080/MavenREST/rest/auth' -H 'Content-Type: application/x-www-form-urlencoded' -d 'id=51&nome=Pedro&email=henrique@gmail.com&senha=fdsa&permissao=admin'
     */
    @PUT
    public String updateUser(
            @FormParam("id") Long id,
            @FormParam("nome") String nome,
            @FormParam("email") String email,
            @FormParam("senha") String senha,
            @FormParam("permissao") String permissao) throws NoSuchAlgorithmException, InvalidKeySpecException {
        dao.updateUser(id, nome, email, senha, permissao);
        return "atualizado com sucesso!";
    }

}
