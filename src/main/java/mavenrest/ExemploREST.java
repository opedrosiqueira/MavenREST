package mavenrest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("exemplo")
public class ExemploREST {

    @POST
    @Path("post")
    public String post(String parametro1) {
        return "{\"retorno\":\"" + parametro1 + "\"}";
    }

    /*
      POST /MavenREST/rest/exemplo/postHeaderParam HTTP/1.1
      Host: localhost:8080
      param1: Um Texto
      param2: 123
     */
    @POST
    @Path("postHeaderParam")
    public String postHeaderParam(
            @HeaderParam("param1") String parametro1,
            @HeaderParam("param2") Integer parametro2) {
        return parametro1 + "/" + parametro2;
    }

    /*
      POST /MavenREST/rest/exemplo/postFormParam HTTP/1.1
      Host: localhost:8080
      Content-Type: application/x-www-form-urlencoded
      nome=Henrique&email=henrique@gmail.com&senha=fdsa
     */
    @POST
    @Path("postFormParam")
    public User postFormParam(
            @FormParam("nome") String nome,
            @FormParam("email") String email,
            @FormParam("senha") String senha) {
        User u = new User(nome, email, senha);
        return u;
    }

    /*
      POST /MavenREST/rest/exemplo/postQueryParam?nome=hello&email=world&senha=fdsa HTTP/1.1
      Host: localhost:8080
     */
    @POST
    @Path("postQueryParam")
    public User postQueryParam(
            @QueryParam("nome") String nome,
            @QueryParam("email") String email,
            @QueryParam("senha") String senha) {
        User u = new User(nome, email, senha);
        return u;
    }

    /*
      POST /MavenREST/rest/exemplo/postPathParam/pedro/p@gmail.com/topsecret HTTP/1.1
      Host: localhost:8080
     */
    @POST
    @Path("/postPathParam/{nome}/{email}/{senha}")
    public User postPathParam(
            @PathParam("nome") String nome,
            @PathParam("email") String email,
            @PathParam("senha") String senha) {
        User u = new User(nome, email, senha);
        return u;
    }

    /*
      POST /MavenREST/rest/exemplo/postJSONParam HTTP/1.1
      Host: localhost:8080
      Content-Type: application/json
      {"nome":"pedro","email":"pedro@gmail", "senha":"topsecret"}
     */
    @POST
    @Path("/postJSONParam")
    public String postJSONParam(User u) throws JsonProcessingException {
        u.setNome(u.getNome() + "*alterado*");
        u.setSenha(u.getSenha() + "*modificado*");
        u.setEmail(u.getEmail() + "*mudado*");
        //converte POJO para JSON
        return new ObjectMapper().writeValueAsString(u);
    }

    /*
      POST /MavenREST/rest/exemplo/postJSONParam2 HTTP/1.1
      Host: localhost:8080
      {"nome":"pedro","email":"pedro@gmail", "senha":"topsecret"}
     */
    @POST
    @Path("/postJSONParam2")
    public User postJSONParam2(String jsonString) throws IOException {
        //converte JSON para POJO
        User u = new ObjectMapper().readValue(jsonString, User.class);
        u.setNome(u.getNome() + "*alterado*");
        u.setSenha(u.getSenha() + "*modificado*");
        u.setEmail(u.getEmail() + "*mudado*");
        return u;
    }

    /*
      POST /MavenREST/rest/exemplo/postListJSON HTTP/1.1
      Host: localhost:8080
      Content-Type: application/json
      [{"nome":"Pedro","email":"pedro@gmail",
      "senha":"topsecret"},{"nome":"Henrique","email":"henrique@hotmail",
      "senha":"confidencial"}]
     */
    @POST
    @Path("/postListJSON")
    public String postListJSON(List<User> us) throws JsonProcessingException {
        if (us == null) {
            us = new ArrayList<>();
        }
        us.add(new User("Fulano", "f.beltrano@gmail.com", "topsecret"));
        return new ObjectMapper().writeValueAsString(us);
    }

    /*
      POST /MavenREST/rest/exemplo/postListJSON2 HTTP/1.1
      Host: localhost:8080
      [{"nome":"Pedro","email":"pedro@gmail",
      "senha":"topsecret"},{"nome":"Henrique","email":"henrique@hotmail",
      "senha":"confidencial"}]
     */
    @POST
    @Path("/postListJSON2")
    public List<User> postListJSON2(String jsonString) throws IOException {
        List<User> us = new ObjectMapper().readValue(jsonString, new TypeReference<List<User>>() {
        });
        //List<User> us = new ObjectMapper().readValue(jsonString, List.class); //outra alternativa
        //User[] us2 = new ObjectMapper().readValue(jsonString, User[].class); //outra alternativa
        if (us == null) {
            us = new ArrayList<>();
        }
        us.add(new User("Fulano", "f.beltrano@gmail.com", "topsecret"));
        return us;
    }
}
