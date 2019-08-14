package handlers;

import authorizationserver.CIBAProxyServer;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import validator.TokenRequestValidator;

import java.util.logging.Logger;



/**
 * This class accepts token requets and initaite validation.
 * Then it proceeds to token response.
 * */
@ComponentScan("handlers")
@Configuration
public class TokenRequestHandler implements Handlers {

    private static final Logger LOGGER = Logger.getLogger(CIBAProxyServer.class.getName());
    private TokenRequestHandler() {

    }

    private static TokenRequestHandler tokenRequestHandlerInstance = new TokenRequestHandler();

    public static TokenRequestHandler getInstance() {
        if (tokenRequestHandlerInstance == null) {

            synchronized (TokenRequestHandler.class) {

                if (tokenRequestHandlerInstance == null) {

                    /* instance will be created at request time */
                    tokenRequestHandlerInstance = new TokenRequestHandler();
                }
            }
        }
        return tokenRequestHandlerInstance;


    }



    public Payload extractParameters(String authreqid , String granttype) {

        TokenRequestValidator tokenRequestValidator = TokenRequestValidator.getInstance();

        /**
         * Validator class taking care of validation of the token request.
         * */
        if (tokenRequestValidator.validateTokenRequest(authreqid, granttype) != null) {


            /**
             * TokenRequestHandler getting the service from Token_Response_Handler to create response.
             * */
            TokenResponseHandler tokenresponsehandler = TokenResponseHandler.getInstance();
            LOGGER.info("Token Response created.");
            return (tokenresponsehandler.createTokenResponse(authreqid));


        } else {
            TokenResponseHandler tokenresponsehandler = TokenResponseHandler.getInstance();
            return (tokenresponsehandler.createTokenErrorResponse());
        }

    }





    public Payload receive(String auth_req_id , String grant_type) {

        return extractParameters(auth_req_id, grant_type);
    }


}
