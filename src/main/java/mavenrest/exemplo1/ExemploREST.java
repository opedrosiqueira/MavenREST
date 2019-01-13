package mavenrest.exemplo1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Providers;

@Path("exemplo")
public class ExemploREST {

    /*
    curl -X POST 'http://localhost:8080/MavenREST/rest/exemplo/post' -d 'uma string'
     */
    @POST
    @Path("post")
    @FiltroSelecionadoAnotacao
    public String post(String parametro1) {
        return "{\"retorno\":\"" + parametro1 + "\"}";
    }

    /*
      POST /MavenREST/rest/exemplo/postHeaderParam HTTP/1.1
      Host: localhost:8080
      param1: Um Texto
      param2: 123
    
    curl -X POST 'http://localhost:8080/MavenREST/rest/exemplo/postHeaderParam' -H 'param1: uma string' -H 'param2: 123'
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
    
    curl -X POST 'http://localhost:8080/MavenREST/rest/exemplo/postFormParam' -H 'Content-Type: application/x-www-form-urlencoded' -d 'nome=Henrique&email=henrique@gmail.com&senha=fdsa'
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
    
    curl -X POST 'http://localhost:8080/MavenREST/rest/exemplo/postQueryParam?nome=hello&email=world&senha=fdsa'
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
    
    curl -X POST 'http://localhost:8080/MavenREST/rest/exemplo/postPathParam/Pedro/pedro@gmail.com/asdf'
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
    
    curl -X POST 'http://localhost:8080/MavenREST/rest/exemplo/postJSONParam' -H 'Content-Type: application/json' -d '{"nome":"pedro","email":"pedro@gmail", "senha":"topsecret"}'
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
    
    curl -X POST 'http://localhost:8080/MavenREST/rest/exemplo/postJSONParam2' -d '{"nome":"pedro","email":"pedro@gmail", "senha":"topsecret"}'
     */
    @POST
    @Path("/postJSONParam2")
    public User postJSONParam2(String jsonString) throws IOException {
        //converte JSON para POJO
        User u = new ObjectMapper().readValue(jsonString, User.class);
        u.setNome(u.getNome() + "*mudado*");
        u.setSenha(u.getSenha() + "*alterado*");
        u.setEmail(u.getEmail() + "*modificado*");
        return u;
    }

    /*
      POST /MavenREST/rest/exemplo/postListJSON HTTP/1.1
      Host: localhost:8080
      Content-Type: application/json
      [{"nome":"Pedro","email":"pedro@gmail", "senha":"topsecret"},{"nome":"Henrique","email":"henrique@hotmail", "senha":"confidencial"}]
    
    curl -X POST 'http://localhost:8080/MavenREST/rest/exemplo/postListJSON' -H 'Content-Type: application/json' -d '[{"nome":"Pedro","email":"pedro@gmail", "senha":"topsecret"},{"nome":"Henrique","email":"henrique@hotmail", "senha":"confidencial"}]'
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
    
    curl -X POST 'http://localhost:8080/MavenREST/rest/exemplo/postListJSON2' -d '[{"nome":"Pedro","email":"pedro@gmail", "senha":"topsecret"},{"nome":"Henrique","email":"henrique@hotmail", "senha":"confidencial"}]'
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

    /*
    curl -X GET 'http://localhost:8080/MavenREST/rest/exemplo/get'
     */
    @GET
    @Path("get")
    @FiltroSelecionadoAnotacao
    public Response get(
            /*
             * podemos injetar informacoes de requisicao e resposta com a anotacao @Context.
             * podemos injetar em parametros, atributos e ate em metodos
             */
            @Context Application app,
            @Context Configuration config,
            @Context HttpHeaders headers,
            @Context Providers providers,
            @Context Request request,
            @Context ResourceContext resourceContext,
            @Context ResourceInfo resourceInfo,
            @Context SecurityContext securityContext,
            @Context UriInfo uriInfo) {
        HashMap<String, String> m = new HashMap<>();
        m.put("app.properties", app.getProperties().toString());
        m.put("config.properties", config.getProperties().toString());
        m.put("headers.host", headers.getHeaderString("Host"));
        m.put("providers.context", providers.getContextResolver(User.class, MediaType.WILDCARD_TYPE).getContext(User.class).toString());
        m.put("request.method", request.getMethod());
        m.put("resourceContext.resource", resourceContext.getResource(ExemploREST.class).post("ola como vai"));
        m.put("resourceInfo.resourceMethod", resourceInfo.getResourceMethod().getName());
        m.put("securityContext.authenticationScheme", securityContext.getAuthenticationScheme());
        m.put("uriInfo.absolutePath", uriInfo.getAbsolutePath().toString());

        /* o metodo ok gera uma resposta com status 200 e recebe como parametro um objeto que sera anexado ao corpo da resposta */
        return Response.ok(m).build();
    }

}
