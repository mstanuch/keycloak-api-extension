package dev.stanuch;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

public class KeycloakCustomApiProviderFactory implements RealmResourceProviderFactory {
    public static final String ID = "custom-api";

    @Override
    public RealmResourceProvider create(KeycloakSession keycloakSession) {
        return new KeycloakCustomApiProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {
        // Nothing to initialize
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // Nothing to do
    }

    @Override
    public void close() {
        // Nothing to do
    }

    @Override
    public String getId() {
        return ID;
    }
}