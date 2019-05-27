package com.kwetter.filters;

import com.kwetter.filters.interfaces.JWTTokenNeeded;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.util.List;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {
    private KeyGenerator keyGenerator;

    public JWTTokenNeededFilter() {
        keyGenerator = new KeyGenerator();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader.isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }
        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("bearer".length()).trim();
        try {
            final Claims claims;
            if ( (claims = validateToken(token)) != null) {
                final SecurityContext securityContext = requestContext.getSecurityContext();
                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return claims::getSubject;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        List<String> roles = (List<String>) claims.get("roles");
                        return roles.contains(role);
                    }

                    @Override
                    public boolean isSecure() {
                        return securityContext.isSecure();
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return "Bearer";
                    }
                });
            }

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private Claims validateToken(String token) {
        // Validate the token
        Key key = keyGenerator.generateKey();
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
