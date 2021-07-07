package dev.stanuch;

import dev.stanuch.resources.Api;
import org.keycloak.models.*;
import org.keycloak.services.resource.RealmResourceProvider;

public class KeycloakCustomApiProvider implements RealmResourceProvider {

    private final KeycloakSession session;

    public KeycloakCustomApiProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new Api(session);
    }

    @Override
    public void close() {
        // Nothing to close
    }
}
