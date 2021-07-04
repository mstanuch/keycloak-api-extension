package dev.stanuch;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.authorization.policy.evaluation.Realm;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.*;
import org.keycloak.services.ErrorResponse;
import org.keycloak.services.messages.Messages;
import org.keycloak.services.resource.RealmResourceProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @POST
    @Path("update-password")
    @NoCache
    @Produces({MediaType.APPLICATION_JSON})
    @Encoded
    public Response UpdatePassword(PasswordUpdate passwordUpdate) {
        var userId = "34322aa0-9fc7-44af-bff4-1a33fcb373f7";
        RealmModel realm = session.getContext().getRealm();
        UserModel user = session.users().getUserById(
                userId,
                realm
        );

        UserCredentialModel userCredentialModel = UserCredentialModel.password(passwordUpdate.getCurrentPassword());
        boolean isCurrentPasswordValid = session.userCredentialManager().isValid(realm, user, userCredentialModel);

        if (!isCurrentPasswordValid) {
            return ErrorResponse.error(Messages.DELEGATION_FAILED, Response.Status.BAD_REQUEST);
        }

        if (passwordUpdate.getNewPassword() == null) {
            return ErrorResponse.error(Messages.INVALID_PASSWORD, Response.Status.BAD_REQUEST);
        }

        if (!passwordUpdate.getNewPassword().equals(passwordUpdate.getConfirmation())) {
            return ErrorResponse.error(Messages.NOTMATCH_PASSWORD, Response.Status.BAD_REQUEST);
        }

        try {
            session.userCredentialManager().updateCredential(realm, user, UserCredentialModel.password(passwordUpdate.getNewPassword(), false));
        } catch (ModelException e) {
            return ErrorResponse.error(e.getMessage(), e.getParameters(), Response.Status.BAD_REQUEST);
        }

        return Response.noContent().build();
    }

    public static class PasswordUpdate {

        private String currentPassword;
        private String newPassword;
        private String confirmation;

        public String getCurrentPassword() {
            return currentPassword;
        }

        public void setCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getConfirmation() {
            return confirmation;
        }

        public void setConfirmation(String confirmation) {
            this.confirmation = confirmation;
        }

    }
}
