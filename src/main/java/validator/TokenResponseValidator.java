package validator;

import exceptions.InternalServerErrorException;
import net.minidev.json.JSONObject;
import transactionartifacts.TokenResponse;

import java.util.logging.Logger;

public class TokenResponseValidator  {


    private static final Logger LOGGER = Logger.getLogger(TokenResponseValidator.class.getName());
    private TokenResponseValidator() {
        // this.run();
    }

    private static TokenResponseValidator tokenResponseValidatorInstance = new TokenResponseValidator();

    public static TokenResponseValidator getInstance() {
        if (tokenResponseValidatorInstance == null) {

            synchronized (TokenResponseValidator.class) {

                if (tokenResponseValidatorInstance == null) {

                    /* instance will be created at request time */
                    tokenResponseValidatorInstance = new TokenResponseValidator();
                }
            }
        }
        return tokenResponseValidatorInstance;
    }


    public TokenResponse validateTokens(JSONObject token) {
        TokenResponse tokenResponse = new TokenResponse();


        /*Validation for refresh token
         * Not mandatory
         */
        if ((String.valueOf(token.get("refresh_token")).isEmpty())) {
            //do nothing
            tokenResponse = null;

        } else if (token.get("refresh_token") == null) {
            //do nothing
            tokenResponse = null;

        } else {
            tokenResponse.setRefreshToken(String.valueOf(token.get("refresh_token")));
        }



        /*Validation for id-token
         * Mandatory parameter
         */
        try {
            if (String.valueOf(token.get("id_token")).isEmpty()) {

                tokenResponse = null;
                LOGGER.warning("Invalid Token Parameters.Id_token not found.");
                throw new InternalServerErrorException("Invalid Token Parameters.Id_token not found.");

            }  else if (token.get("id_token")==null) {
                tokenResponse = null;
                LOGGER.warning("Invalid Token Parameters.Id_token not found.");
                throw new InternalServerErrorException("Invalid Token Parameters.Id_token not found.");
            }else {
                tokenResponse.setIdToken(String.valueOf(token.get("id_token")));
            }
        } catch (InternalServerErrorException internalServerErrorException) {
            internalServerErrorException.getMessage();
        }



        /*Validation for access-token
         * Mandatory parameter
         */
        try {
            if (String.valueOf(token.get("access_token")).isEmpty()) {
                tokenResponse = null;
                LOGGER.warning("Invalid Token Parameters.Access_token not found.");
                throw new InternalServerErrorException("Invalid Token Parameters.");

            } else if (token.get("access_token")==null) {
                tokenResponse = null;
                LOGGER.warning("Invalid Token Parameters.Access_token not found.");
                throw new InternalServerErrorException("Invalid Token Parameters.Access_token not found.");

            }else {
                tokenResponse.setAccessToken(String.valueOf(token.get("access_token")));
            }


        } catch (InternalServerErrorException internalServerErrorException) {
            internalServerErrorException.getMessage();
        }

        /*Validation for expires_in
         * Mandatory parameter
         */
        try {
            if (String.valueOf(token.get("expires_in")).isEmpty()) {
                tokenResponse = null;
                LOGGER.warning("Invalid Token Parameters.'expires_in' not found.");
                throw new InternalServerErrorException("Invalid Token Parameters.'expires_in' not found.");
            } else if (token.get("expires_in")==null) {
                tokenResponse = null;
                LOGGER.warning("Invalid Token Parameters.'expires_in' not found.");
                throw new InternalServerErrorException("Invalid Token Parameters.'expires_in' not found.");

            }else {
                tokenResponse.setTokenExpirein(Long.parseLong(String.valueOf(token.get("expires_in"))));
            }


        } catch (InternalServerErrorException internalServerErrorException) {
            internalServerErrorException.getMessage();
        }

        /*Validation for token_type
         * Mandatory parameter
         */
        try {
            if (String.valueOf(token.get("token_type")).isEmpty()) {
                tokenResponse = null;
                LOGGER.warning("Invalid Token Parameters.'token_type' not found.");
                throw new InternalServerErrorException("Invalid Token Parameters.'token_type' not found.");
            } else if (token.get("token_type")==null) {
                tokenResponse = null;
                LOGGER.warning("Invalid Token Parameters.'token_type' not found.");
                throw new InternalServerErrorException("Invalid Token Parameters.'token_type' not found.");

            }else {
                tokenResponse.setTokenType(String.valueOf(token.get("token_type")));
            }


        } catch (InternalServerErrorException internalServerErrorException) {
            internalServerErrorException.getMessage();
        }




     return tokenResponse;

    }
}
