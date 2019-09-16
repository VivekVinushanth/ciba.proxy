package handlers;

import ciba.proxy.server.servicelayer.ServerRequestHandler;
import cibaparameters.CIBAParameters;
import configuration.ConfigurationFile;
import dao.DaoFactory;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWTClaimsSet;
import transactionartifacts.CIBAauthRequest;
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
    CIBAParameters cibaparameters = CIBAParameters.getInstance();

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

        LOGGER.info("CIBA Authentication Response payload created and forwarded");
        return responsepayload;

    }



  /**
   * Storing auth response to cache memory.
   * */
   public void storeAuthResponse(String auth_req_id, CIBAauthResponse cibAauthResponse) {

       daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().getSTORE_CONNECTOR_TYPE()).
               addAuthResponse(auth_req_id,cibAauthResponse);

      LOGGER.info("CIBA Authentication Response stored in Auth Response Store.");
      System.out.println("Working in perfection"+daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().
              getSTORE_CONNECTOR_TYPE()).getAuthResponse(auth_req_id).getExpires_in());

      storePollingAttribute(auth_req_id); // store polling attributes first
      triggerServerRequestHandler(auth_req_id); //then trigggering the server to initiate the flow

   }

   private void triggerServerRequestHandler(String auth_req_id){
       CIBAauthRequest cibAauthRequest = daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().
               getSTORE_CONNECTOR_TYPE()).getAuthRequest(auth_req_id);
       ServerRequestHandler.getInstance().initiateServerCommunication(cibAauthRequest,auth_req_id);
   }

   private void storePollingAttribute(String auth_req_id){
       long currenttime = ZonedDateTime.now().toInstant().toEpochMilli();

       PollingAtrribute pollingAtrribute = new PollingAtrribute();
       pollingAtrribute.setAuth_req_id(auth_req_id);
       pollingAtrribute.setExpiresIn(cibaparameters.getExpires_in() * 1000);
       pollingAtrribute.setIssuedTime(currenttime);
       pollingAtrribute.setLastPolledTime(currenttime);
       pollingAtrribute.setPollingInterval(cibaparameters.getInterval() * 1000);

       if(ConfigurationFile.getInstance().getFLOW_MODE().equalsIgnoreCase("ping")){
           pollingAtrribute.setNotificationIssued(false);
       }else if(ConfigurationFile.getInstance().getFLOW_MODE().equalsIgnoreCase("poll")){
           pollingAtrribute.setNotificationIssued(true);
       }else{
           //do nothing
       }


       daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().getSTORE_CONNECTOR_TYPE()).
               addPollingAttribute(auth_req_id,pollingAtrribute);
   }
}
