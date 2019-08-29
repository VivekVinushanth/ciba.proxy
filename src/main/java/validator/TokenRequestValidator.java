package validator;


import cibaparameters.CIBAParameters;
import configuration.ConfigurationFile;
import dao.DaoFactory;
import dao.ArtifactStoreConnectors;
import errorfiles.BadRequest;
import errorfiles.UnAuthorizedRequest;
import handlers.TokenResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.PollingAtrribute;
import transactionartifacts.TokenRequest;

import java.time.ZonedDateTime;

/**
 * This class do the validation of token request.
 * */
public class TokenRequestValidator {
private DaoFactory daoFactory = DaoFactory.getInstance();

    private TokenRequestValidator() {

    }

    private static TokenRequestValidator tokenRequestValidatorInstance = new TokenRequestValidator();

    public static TokenRequestValidator getInstance() {
        if (tokenRequestValidatorInstance == null) {

            synchronized (TokenRequestValidator.class) {

                if (tokenRequestValidatorInstance == null) {

                    /* instance will be created at request time */
                    tokenRequestValidatorInstance = new TokenRequestValidator();
                }
            }
        }
        return tokenRequestValidatorInstance;


    }


    public TokenRequest validateTokenRequest(String auth_req_id, String grant_type) {
        TokenRequest tokenRequest = new TokenRequest();
            ArtifactStoreConnectors artifactStoreConnectors = daoFactory.getArtifactStoreConnector(ConfigurationFile.getInstance().getSTORE_CONNECTOR_TYPE());

        CIBAParameters cibaparameters = CIBAParameters.getInstance();

        try {
            if (auth_req_id.isEmpty() || auth_req_id.equals("") || auth_req_id == null) {
                tokenRequest = null;
                throw new UnAuthorizedRequest("Invalid auth_req_id");

            } else if (grant_type.isEmpty()) {
                tokenRequest = null;
                throw new BadRequest("Improper grant_type");

            } else {
               // System.out.println(artifactStoreConnectors.getAuthResponse(auth_req_id).getAuth_req_id());
                //check whether provided auth_req_id is valid and provided by the system and has relevant auth response
               try {
                   if (!(artifactStoreConnectors.getAuthResponse(auth_req_id) == null) &&
                        (grant_type.equals(cibaparameters.getGrant_type()))) {
                     //System.out.println(artifactStoreConnectors.getAuthResponse(auth_req_id).getAuth_req_id());
                  /*     long expiryduration = artifactStoreConnectors.getExpiresTime(auth_req_id);
                       long issuedtime = artifactStoreConnectors.getIssuedTime(auth_req_id);
                       long currenttime = ZonedDateTime.now().toInstant().toEpochMilli();
                       long interval = artifactStoreConnectors.getInterval(auth_req_id);
                       long lastpolltime = artifactStoreConnectors.getLastPollTime(auth_req_id);*/

                       long expiryduration = artifactStoreConnectors.getPollingAttribute(auth_req_id).getExpiresIn();
                       long issuedtime = artifactStoreConnectors.getPollingAttribute(auth_req_id).getIssuedTime();
                       long currenttime = ZonedDateTime.now().toInstant().toEpochMilli();
                       long interval = artifactStoreConnectors.getPollingAttribute(auth_req_id).getPollingInterval();
                       long lastpolltime = artifactStoreConnectors.getPollingAttribute(auth_req_id).getLastPolledTime();

                       try {
                           //checking for token expiry
                           if (currenttime > issuedtime + expiryduration + 5) {
                               tokenRequest = null;
                               throw new BadRequest("Expired Token");

                               //checking for frequency of poll
                           } else if (currenttime - lastpolltime < interval) {


                               artifactStoreConnectors.removePollingAttribute(auth_req_id);

                               PollingAtrribute pollingAtrribute2 =new PollingAtrribute();
                               pollingAtrribute2.setIssuedTime(issuedtime);
                               pollingAtrribute2.setLastPolledTime(currenttime);
                               pollingAtrribute2.setPollingInterval(5000);
                               pollingAtrribute2.setAuth_req_id(auth_req_id);
                               pollingAtrribute2.setExpiresIn(expiryduration);

                               artifactStoreConnectors.addPollingAttribute(auth_req_id,pollingAtrribute2);
                               //updating the polling frequency -deleting and adding new object with updated values
                               tokenRequest = null;
                               throw new BadRequest("Slow Down");

                           } else {
                               if (TokenResponseHandler.getInstance().checkTokenReceived(auth_req_id)) {
                                   tokenRequest.setGrant_type(grant_type);
                                   tokenRequest.setAuth_req_id(auth_req_id);

                                   //storing token request
                                  artifactStoreConnectors.addTokenRequest(auth_req_id, tokenRequest);

                                   artifactStoreConnectors.removePollingAttribute(auth_req_id);

                                   PollingAtrribute pollingAtrribute3 =new PollingAtrribute();
                                   pollingAtrribute3.setIssuedTime(issuedtime);
                                   pollingAtrribute3.setLastPolledTime(currenttime);
                                   pollingAtrribute3.setPollingInterval(interval);
                                   pollingAtrribute3.setAuth_req_id(auth_req_id);
                                   pollingAtrribute3.setExpiresIn(expiryduration);

                                   artifactStoreConnectors.addPollingAttribute(auth_req_id,pollingAtrribute3);
                                   //updating last polled time

                               } else {
                                   tokenRequest = null;
                                   throw new BadRequest("authorization pending");
                               }
                           }
                           return tokenRequest;

                       } catch (BadRequest badRequest) {
                           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badRequest.getMessage());
                       }


                   }
                   throw new BadRequest("invalid_grant");

                 } catch (BadRequest badrequest) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badrequest.getMessage());
                }

            }


        } catch (UnAuthorizedRequest unAuthorizedRequest) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, unAuthorizedRequest.getMessage());



        } catch  (BadRequest badRequest) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, badRequest.getMessage());
        }



    }



}
