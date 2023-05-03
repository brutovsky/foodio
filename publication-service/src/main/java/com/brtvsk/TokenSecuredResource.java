package com.brtvsk;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/secured")
public class TokenSecuredResource {

    private static final Logger LOGGER = Logger.getLogger(TokenSecuredResource.class.getName());

    @Inject
    JsonWebToken jwt;

    @Inject
    SecurityIdentity securityIdentity;
    @Inject
    @Claim(standard = Claims.groups)
    String groups;

    @Inject
    @Claim(standard = Claims.email)
    String email;

    @Inject
    @Claim(standard = Claims.given_name)
    String given_name;

    @GET()
    @Path("permit-all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context SecurityContext ctx) {
        return getResponseString(ctx);
    }

    @GET
    @Path("roles-allowed")
    @RolesAllowed({"User", "user", "Admin", "admin"})
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed(@Context SecurityContext ctx) {
        LOGGER.info(jwt.getClaim("fav"));
        return getResponseString(ctx);
    }

    @GET
    @Path("roles")
    @Produces(MediaType.TEXT_PLAIN)
    public String securedEndpoint() {
        String username = securityIdentity.getPrincipal().getName();
        String[] roles = securityIdentity.getRoles().toArray(new String[0]);
        return "Hello, " + username + "! Your fav is: " + securityIdentity.getAttribute("fav") + "! Your roles are: " +
                String.join(", ", roles);
    }

    private String getResponseString(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        return String.format("hello + %s,"
                        + " isHttps: %s,"
                        + " authScheme: %s,"
                        + " hasJWT: %s,"
                        + " groups: %s,"
                        + " email: %s,"
                        + " given_name: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt(), groups, email, given_name);
    }

    private boolean hasJwt() {
        return jwt.getClaimNames() != null;
    }
}
