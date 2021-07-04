package dev.stanuch;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class KeycloakCustomApiProvider implements RealmResourceProvider {

    private KeycloakSession session;

    public KeycloakCustomApiProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return this;
    }

    @Override
    public void close() {
        // Nothing to close
    }

    @GET
    @Path("hello")
    @NoCache
    @Produces({ MediaType.APPLICATION_JSON })
    @Encoded
    public String Hello() {
        return "hello";
    }
}
