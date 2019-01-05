/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mavenrest.autenticacao;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import mavenrest.AppPropriedades;
import mavenrest.user.User;
import mavenrest.user.UserDAO;

/**
 * REST Web Service
 *
 * @author pedro
 */
@Path("auth")
public class AutenticacaoREST {

    @Inject
    UserDAO userDAO;

    @Inject
    AppPropriedades ap;

    @POST
    public Response loginUser(
            @FormParam("email") String email,
            @FormParam("senha") String senha) {
        User u = userDAO.getUserByEmail(email);
        if (u == null || !AutenticacaoHash.igual(senha, u.getSalt(), u.getSenha())) {
            return Response.status(Response.Status.FORBIDDEN).entity("usuario ou senha invalida").build();
        }

        Date dateIssued = new Date();
        Date dateExpires = new Date(dateIssued.getTime() + ap.getExpiresAfter());
        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(ap.getSecret()));
        String token = Jwts.builder()
                .setIssuer(ap.getIssuer())
                .setSubject(u.getEmail())
                .setIssuedAt(dateIssued)
                .setExpiration(dateExpires)
                .signWith(key)
                .compact();

        return Response.ok(token).build();
    }

}
