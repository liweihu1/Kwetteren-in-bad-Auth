package com.kwetter.api;

import com.kwetter.callback.LoginCallbackHandler;
import com.kwetter.dto.JWTTokenDTO;
import com.kwetter.dto.LoginDTO;
import com.kwetter.dto.LoginResult;
import com.kwetter.filters.KeyGenerator;
import com.kwetter.service.AuthService;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Path("auth")
public class AuthAPI {
    @Context
    private UriInfo uriInfo;

    @Inject
    private AuthService authService;

    private KeyGenerator keyGenerator;

    public AuthAPI(){
        keyGenerator = new KeyGenerator();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response login(LoginDTO loginDTO) throws UnirestException {
        LoginResult res = authService.login(loginDTO.getUsername(), loginDTO.getPassword());
        JWTTokenDTO jwtToken = new JWTTokenDTO(generateToken(res.getUserId(), res.getRoles()), res.getUserId());
        return Response.ok(jwtToken, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response logout(LoginDTO loginDTO){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return Response.status(200).build();
    }

    private String generateToken(UUID id, List<String> roles) {
        Key key = keyGenerator.generateKey();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date expirationDate = cal.getTime();
        Claims claims = Jwts.claims();
        claims.put("roles", roles);
        claims.put("id", id);
        return Jwts.builder()
                .setSubject(id.toString())
                .setClaims(claims)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
