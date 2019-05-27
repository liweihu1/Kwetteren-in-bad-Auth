package com.kwetter.filters;

import com.kwetter.filters.interfaces.IKeyGenerator;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@WebFilter(filterName = "TokenFilter")
public class TokenFilter implements Filter {
    @EJB(beanName = "KeyGenerator")
    private IKeyGenerator authenticator;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Extract access token from the request
        String token = request.getParameter("token");
        if (token == null || token.trim().isEmpty()) {
            returnForbiddenError(response, "An access token is required to connect");
            return;
        }

        // Validate the token and get the user who the token has been issued for
        UUID id = authenticator.getIdFromToken(token);
        if (id != null) {
            filterChain.doFilter(new AuthenticatedRequest(request, id), servletResponse);
        } else {
            returnForbiddenError(response, "Invalid auth token");
        }
    }

    private void returnForbiddenError(HttpServletResponse response, String message)
            throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, message);
    }

    @Override
    public void destroy() {

    }

    /**
     * Wrapper for a {@link HttpServletRequest} which decorates a
     * {@link HttpServletRequest} by adding a {@link Principal} to it.
     */
    private static class AuthenticatedRequest extends HttpServletRequestWrapper {

        private UUID id;

        public AuthenticatedRequest(HttpServletRequest request, UUID id) {
            super(request);
            this.id = id;
        }

        @Override
        public Principal getUserPrincipal() {
            return () -> String.valueOf(id);
        }
    }
}
