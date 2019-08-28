package handlers;


import authorizationserver.CIBAProxyServer;
import configuration.ConfigurationFile;
import dao.DaoFactory;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.CIBAauthRequest;
import util.CodeGenerator;
import validator.AuthRequestValidator;

import java.util.logging.Logger;

//import org.apache.commons.codec.binary.Base64;

/**
 * This class accepts and handle the CIBA authentication requests.
 * */
@ComponentScan("handlers")
@Configuration
public class CIBAAuthRequestHandler implements Handlers {
    private static final Logger LOGGER = Logger.getLogger(CIBAProxyServer.class.getName());
    DaoFactory daoFactory = DaoFactory.getInstance();


    private CIBAAuthRequestHandler() {

    }

    private static CIBAAuthRequestHandler cibaAuthRequestHandlerInstance = new CIBAAuthRequestHandler();

    public static CIBAAuthRequestHandler getInstance() {
        if (cibaAuthRequestHandlerInstance == null) {

            synchronized (CIBAAuthRequestHandler.class) {

                if (cibaAuthRequestHandlerInstance == null) {

                    /* instance will be created at request time */
                    cibaAuthRequestHandlerInstance = new CIBAAuthRequestHandler();
                }
            }
        }
        return cibaAuthRequestHandlerInstance;


    }



    public String extractParameters(String request) {

        String[] paramsarray = new String[3];

        CIBAAuthResponseHandler cibaAuthResponseHandler = CIBAAuthResponseHandler.getInstance();
       try {

           /*Decoding the web token.*/
           paramsarray = request.split("\\.");
           String header = new String(Base64.decodeBase64(paramsarray[0]));
           String payload = new String(Base64.decodeBase64(paramsarray[1]));
           String signature = new String(Base64.decodeBase64(paramsarray[2]));


           // parsing file "JSONExample.json"
           Object obj = new JSONParser().parse(payload);

               // typecasting obj to JSONObject
               JSONObject jo = (JSONObject) obj;

               LOGGER.info("Auth request parameters extracted.");

// TODO: 8/3/19 Need to verify sign here
           /**
            * Once properly validated creating the authentication response.
            Else the error payload will be send from the place of validation.
            */
               if (this.refactorAuthRequest(jo) != null) {

                   /**
                    * creation of auth_req_id happens here.
                    * */
                   CodeGenerator codeGenerator = CodeGenerator.getInstance();
                   String authReqId =  codeGenerator.getAuthReqId();

                    /**
                     * Store CIBA authentication request to the memory.
                     * */
                   storeAuthRequest(authReqId, this.refactorAuthRequest(jo));


                   return cibaAuthResponseHandler.createAuthResponse(authReqId).toString(); //returning authentication response
               }

       } catch (ArrayIndexOutOfBoundsException e) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Improper 'request' parameter.");
    } catch (ParseException e) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to parse JWS.");
       }

        return "";

    }

    public CIBAauthRequest refactorAuthRequest(JSONObject jo) {
        //CIBAParameters ciba_parameters = CIBAParameters.getInstance();


        /**
         * Initiating the validation process.
         * */
        AuthRequestValidator authRequestValidator = AuthRequestValidator.getInstance();

        if (authRequestValidator.validateAuthRequest(jo) != null) {
            return authRequestValidator.validateAuthRequest(jo);
        }

            return null;
    }


    public String receive(String params) {
        LOGGER.info("Auth request handler received the auth request");
        return extractParameters(params);
    }


    /**
     * Storing authentication request to cachememory.
     * */
    public void storeAuthRequest(String auth_req_id, CIBAauthRequest cibAauthRequest) {

      // daoFactory.getArtifactStoreConnector("InMemoryCache").addAuthRequest(auth_req_id, cibAauthRequest);
        daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().getSTORE_CONNECTOR_TYPE()).addAuthRequest(auth_req_id,cibAauthRequest);
      LOGGER.info("Authentication request stored in  Authentication Request Database");
    }
}
