package mavenrest.user;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
            @FormParam("senha") String senha) {
        try {
            userDAO.createUser(nome, email, senha);
        } catch (Exception e) {
            return e.getMessage();
        }
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
            @FormParam("senha") String senha) {
        try {
            userDAO.updateUser(id, nome, email, senha);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "atualizado com sucesso!";
    }
}
