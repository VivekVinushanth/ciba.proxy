package handlers;

import cibaparameters.CIBAParameters;
import configuration.ConfigurationFile;
import dao.DaoFactory;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWTClaimsSet;
import transactionartifacts.CIBAauthResponse;
import transactionartifacts.PollingAtrribute;

import java.time.ZonedDateTime;
import java.util.logging.Logger;

/**
 * This class handles the CIBA authentication responses.
 * */
public class CIBAAuthResponseHandler implements Handlers {

    private static final Logger LOGGER = Logger.getLogger(CIBAAuthResponseHandler.class.getName());
    DaoFactory daoFactory = DaoFactory.getInstance();


    private CIBAAuthResponseHandler() {

    }

    private static CIBAAuthResponseHandler cibaAuthResponseHandlerInstance = new CIBAAuthResponseHandler();

    public static CIBAAuthResponseHandler getInstance() {
        if (cibaAuthResponseHandlerInstance == null) {

            synchronized (CIBAAuthResponseHandler.class) {

                if (cibaAuthResponseHandlerInstance == null) {

                    /* instance will be created at request time */
                    cibaAuthResponseHandlerInstance = new CIBAAuthResponseHandler();
                }
            }
        }
        return cibaAuthResponseHandlerInstance;


    }


    /**
     * Creating authentication response.
     */
    public Payload createAuthResponse(String auth_req_id) {
        CIBAauthResponse cibAauthResponse = new CIBAauthResponse();
        CIBAParameters cibaparameters = CIBAParameters.getInstance();


        /*creating authentication response for the request*/
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .claim("auth_req_id", auth_req_id)
                .claim("expires_in", cibaparameters.getExpires_in())
                .claim("interval", cibaparameters.getInterval())
                .build();

        String responseString = claims.toJSONObject().toString();

        Payload responsepayload = new Payload(claims.toJSONObject());


        /**
         * Creating authentication response object and store to memory.
         * */
        cibAauthResponse.setAuth_req_id(auth_req_id);
        cibAauthResponse.setExpires_in(cibaparameters.getExpires_in());
        cibAauthResponse.setInterval(cibaparameters.getInterval());
        this.storeAuthResponse(auth_req_id, cibAauthResponse);

         long currenttime = ZonedDateTime.now().toInstant().toEpochMilli();
       /* daoFactory.getArtifactStoreConnector("InMemoryCache").addExpiresTime(auth_req_id, cibaparameters.getExpires_in() * 1000);
        daoFactory.getArtifactStoreConnector("InMemoryCache").addInterval(auth_req_id, cibaparameters.getInterval() * 1000);
        daoFactory.getArtifactStoreConnector("InMemoryCache").addIssuedTime(auth_req_id, currenttime);
        daoFactory.getArtifactStoreConnector("InMemoryCache").addLastPollTime(auth_req_id, currenttime);

*/
        PollingAtrribute pollingAtrribute = new PollingAtrribute();
        pollingAtrribute.setAuth_req_id(auth_req_id);
        pollingAtrribute.setExpiresIn(cibaparameters.getExpires_in() * 1000);
        pollingAtrribute.setIssuedTime(currenttime);
        pollingAtrribute.setLastPolledTime(currenttime);
        pollingAtrribute.setPollingInterval(cibaparameters.getInterval() * 1000);

       /* daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().getSTORE_CONNECTOR_TYPE()).addExpiresTime(auth_req_id, cibaparameters.getExpires_in() * 1000);
        daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().getSTORE_CONNECTOR_TYPE()).addInterval(auth_req_id, cibaparameters.getInterval() * 1000);
        daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().getSTORE_CONNECTOR_TYPE()).addIssuedTime(auth_req_id, currenttime);
        daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().getSTORE_CONNECTOR_TYPE()).addLastPollTime(auth_req_id, currenttime);
*/
daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().getSTORE_CONNECTOR_TYPE()).addPollingAttribute(auth_req_id,pollingAtrribute);
        LOGGER.info("CIBA Authentication Response payload created and forwarded");

        LOGGER.info("Expiry_time , Last_Poll_Time , Interval , Issued_Time  stored ");

        return responsepayload;

    }


    /**
     * Creating error response.
     */
   /* public Payload createAuthErrorResponse() {
        // ErrorCodeHandlers errorCodeHandlers = ErrorCodeHandlers.getInstance();

        try {
            throw new BadRequest("Bad Request");
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        }

        return new Payload("Here");
    }*/


  /**
   * Storing auth response to cache memory.
   * */
   public void storeAuthResponse(String auth_req_id, CIBAauthResponse cibAauthResponse) {

       daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().getSTORE_CONNECTOR_TYPE()).addAuthResponse(auth_req_id,cibAauthResponse);
       //daoFactory.getArtifactStoreConnector("InMemoryCache").addAuthResponse(auth_req_id, cibAauthResponse);
      LOGGER.info("CIBA Authentication Response stored in Auth Response Store.");
      System.out.println("Working in perfection"+daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().getSTORE_CONNECTOR_TYPE()).getAuthResponse(auth_req_id).getExpires_in());
       
   }
}
