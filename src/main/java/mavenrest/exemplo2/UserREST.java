package mavenrest.exemplo2;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("users")
public class UserREST {

    @Inject
    UserDAO userDAO;

    @GET
    public List<User> getUsers() {
        return userDAO.getAllUsers();
    }

    @GET
    @Path("{id}")
    public User getUser(@PathParam("id") long id) {
        return userDAO.getUser(id);
    }

    @GET
    @Path("search")
    public List<User> search(
            @QueryParam("termo") String termo,
            @QueryParam("valor") String valor) {
        return userDAO.search(termo, valor);
    }

    @POST
    public String createUser(
            @FormParam("nome") String nome,
            @FormParam("email") String email,
            @FormParam("senha") String senha) throws NoSuchAlgorithmException, InvalidKeySpecException {
        userDAO.createUser(nome, email, senha);
        return "criado com sucesso!";
    }

    @DELETE
    @Path("{id}")
    public String deleteByID(@PathParam("id") Long id) {
        userDAO.delete(id);
        return "removido com sucesso!";
    }

    @PUT
    @Path("{id}")
    public String updateUser(
            @PathParam("id") long id,
            @FormParam("nome") String nome,
            @FormParam("email") String email,
            @FormParam("senha") String senha) throws NoSuchAlgorithmException, InvalidKeySpecException {
        userDAO.updateUser(id, nome, email, senha);
        return "atualizado com sucesso!";
    }
}
