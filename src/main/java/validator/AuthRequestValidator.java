package validator;

import authorizationserver.CIBAProxyServer;
import configuration.ConfigurationFile;
import errorfiles.BadRequest;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.CIBAauthRequest;

import java.util.logging.Logger;

/**
 * This class do the validation of authentication request.
 */
public class AuthRequestValidator {

    private static final Logger LOGGER = Logger.getLogger(CIBAProxyServer.class.getName());

    private AuthRequestValidator() {

    }

    private static AuthRequestValidator authRequestValidatorInstance = new AuthRequestValidator();

    public static AuthRequestValidator getInstance() {

        if (authRequestValidatorInstance == null) {

            synchronized (AuthRequestValidator.class) {

                if (authRequestValidatorInstance == null) {

                    /* instance will be created at request time */
                    authRequestValidatorInstance = new AuthRequestValidator();
                }
            }
        }
        return authRequestValidatorInstance;

    }



    public CIBAauthRequest validateAuthRequest(JSONObject jo) {

        CIBAauthRequest cibaAuthRequest = new CIBAauthRequest();

        // Validation for aud-audience.
        if (String.valueOf(jo.get("aud")) == null) {
            try {

                LOGGER.warning("Invalid request : Missing mandatory parameter 'aud'");
                throw new BadRequest("Invalid request : Missing mandatory parameter 'aud'");

            } catch (BadRequest badRequest) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }

        } else {
            cibaAuthRequest.setAud(String.valueOf(jo.get("aud")));

        }

        // Validation for iss-issuer.
        if (String.valueOf(jo.get("iss")) == null) {
            try {

                LOGGER.warning("Invalid request : Missing mandatory parameter 'iss'");
                throw new BadRequest("Invalid request : Missing mandatory parameter 'iss'");
            } catch (BadRequest badRequest) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }

        } else {
            cibaAuthRequest.setIss(String.valueOf(jo.get("iss")));

        }

        // Validation for jti.
        if (String.valueOf(jo.get("jti")) == null) {
            try {

                LOGGER.warning("Invalid request : Missing mandatory parameter 'jti'");
                throw new BadRequest("Invalid request : Missing mandatory parameter 'jti'");
            } catch (BadRequest badRequest) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }

        } else {
            //cibaparameters.setJti(String.valueOf(jo.get("jti")));
            cibaAuthRequest.setJti(String.valueOf(jo.get("jti")));
        }

        // Validation for exp.
        if ((String.valueOf(jo.get("exp")).isEmpty())) {
            try {

                LOGGER.warning("Invalid request : Missing mandatory parameter 'exp'");
                throw new BadRequest("Invalid request : Missing  mandatory parameter 'exp'");
            } catch (BadRequest badRequest) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }

        } else if ((jo.get("exp")) == null) {
            try {

                LOGGER.warning("Invalid request : Missing mandatory parameter 'exp'");
                throw new BadRequest("Invalid request : Missing  mandatory parameter 'exp'");
            } catch (BadRequest badRequest) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }

        } else {

            cibaAuthRequest.setExp(Long.parseLong(String.valueOf(jo.get("exp"))));
        }

        //  Validation for iat-issued at.
        if ((String.valueOf(jo.get("iat")).isEmpty())) {
            try {

                LOGGER.warning("Invalid request : Missing mandatory parameter 'iat'");
                throw new BadRequest("Invalid request : Missing  mandatory parameter 'iat'");
            } catch (BadRequest badRequest) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }

        } else if ((jo.get("iat")) == null) {
            try {

                LOGGER.warning("Invalid request : Missing mandatory parameter 'iat'");
                throw new BadRequest("Invalid request : Missing  mandatory parameter 'iat'");
            } catch (BadRequest badRequest) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }

        } else {

            cibaAuthRequest.setIat(Long.parseLong(String.valueOf(jo.get("iat"))));
        }

        // Validation for nbf-time before signed request is acceptable.
        if ((String.valueOf(jo.get("nbf")).isEmpty())) {
            try {

                LOGGER.warning("Invalid request : Missing mandatory parameter 'nbf'");
                throw new BadRequest("Invalid request : Missing  mandatory parameter 'nbf'");
            } catch (BadRequest badRequest) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }

        } else if ((jo.get("nbf")) == null) {
            try {

                LOGGER.warning("Invalid request : Missing mandatory parameter 'nbf'");
                throw new BadRequest("Invalid request : Missing  mandatory parameter 'nbf'");
            } catch (BadRequest badRequest) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }

        } else {
            cibaAuthRequest.setNbf(Long.parseLong(String.valueOf(jo.get("nbf"))));
        }

        // Validation for scope.
        if (String.valueOf(jo.get("scope")) == null) {
            try {

                LOGGER.warning("Invalid request : Missing mandatory parameter 'scope'");
                throw new BadRequest("Invalid request : Missing  mandatory parameter 'scope'");
            } catch (BadRequest badRequest) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }

        } else {

            cibaAuthRequest.setScope(String.valueOf(jo.get("scope")));

        }

        // Validation for client_notification_token.
        if (ConfigurationFile.getInstance().getFLOW_MODE().equalsIgnoreCase("ping")) {
            try {
                if ((String.valueOf(jo.get("client_notification_token")).isEmpty())) {
                    throw new BadRequest("Client Notification Token is mandotory for ping");

                } else if ((jo.get("client_notification_token")) == null) {
                    throw new BadRequest("Client Notification Token is mandotory for ping");

                } else {
                    //cibaparameters.setClient_notification_token(String.valueOf(jo.get("client_notification_token")));
                    cibaAuthRequest.setClient_notification_token(String.valueOf(jo.get("client_notification_token")));
                }
            } catch (BadRequest badRequest) {
                LOGGER.warning("Invalid request : Missing Client Notification token'");
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }

        } else {
            if ((String.valueOf(jo.get("client_notification_token")).isEmpty())) {
                //do nothing

            } else if ((jo.get("client_notification_token")) == null) {
                //do nothing

            } else {
                cibaAuthRequest.setClient_notification_token(String.valueOf(jo.get("client_notification_token")));
            }

        }



        /*Validation for login_hint_token,token_hint
         * Anyone and exactly one is mandatory */
        if ((String.valueOf(jo.get("login_hint_token")) != "null")
                && (String.valueOf(jo.get("login_hint")) == "null")
                && (String.valueOf(jo.get("id_token_hint")) == "null")) {

            cibaAuthRequest.setLogin_hint_token(String.valueOf(jo.get("login_hint_token")));

        } else if ((String.valueOf(jo.get("login_hint_token")) == "null")
                && (String.valueOf(jo.get("login_hint")) != "null")
                && (String.valueOf(jo.get("id_token_hint")) == "null")) {

            cibaAuthRequest.setLogin_hint(String.valueOf(jo.get("login_hint")));

        } else if ((String.valueOf(jo.get("login_hint_token")) == "null")
                && (String.valueOf(jo.get("login_hint")) != "null")
                && (String.valueOf(jo.get("id_token_hint")) != "null")) {

            cibaAuthRequest.setId_token_hint(String.valueOf(jo.get("id_token_hint")));
            // id_token_hint cannot be validated.

        } else {
            try {

                LOGGER.warning("Invalid request : Missing user identity - hints/ login hint / id hint token'");
                throw new BadRequest("Invalid request : Messed up with hints.Anyone and only one is a must");
            } catch (BadRequest badRequest) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }

        }

        // Validation for acr-values.        
        if ((String.valueOf(jo.get("acr")).isEmpty())) {
            //do nothing

        } else if ((jo.get("acr")) == null) {
            //do nothing

        } else {

            cibaAuthRequest.setAcr_values((String.valueOf(jo.get("acr"))));

        }

        // Validation for user-code.
        if ((String.valueOf(jo.get("user_code")).isEmpty())) {
            //do nothing

        } else if ((jo.get("user_code")) == null) {
            //do nothing

        } else {

            //cibaparameters.setExp(Long.parseLong(String.valueOf(jo.get("user_code"))));
            cibaAuthRequest.setExp(Long.parseLong(String.valueOf(jo.get("user_code"))));
        }

        // Validation for binding_message.
        if ((String.valueOf(jo.get("binding_message")).isEmpty())) {
            //do nothing

        } else if ((jo.get("binding_message")) == null) {
            //do nothing

        } else {

            //cibaparameters.setBinding_message(String.valueOf(jo.get("binding_message")));
            cibaAuthRequest.setBinding_message(String.valueOf(jo.get("binding_message")));
        }

        // Validation for iat-issued at.
        if ((String.valueOf(jo.get("requested_expiry")).isEmpty())) {
            //do nothing

        } else if ((jo.get("requested_expiry")) == null) {
            //do nothing

        } else {

            //cibaparameters.setRequested_expiry(Long.parseLong(String.valueOf(jo.get("requested_expiry"))));
            cibaAuthRequest.setRequested_expiry(Long.parseLong(String.valueOf(jo.get("requested_expiry"))));
        }

        LOGGER.info("Ciba Authentication request validated.");

        return cibaAuthRequest;
    }

}
