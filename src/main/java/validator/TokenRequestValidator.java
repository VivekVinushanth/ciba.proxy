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
import java.util.logging.Logger;

/**
 * This class do the validation of token request.
 * */
public class TokenRequestValidator {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger LOGGER = Logger.getLogger(TokenRequestValidator.class.getName());
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
            if (auth_req_id.isEmpty() || auth_req_id.equals("") || auth_req_id == null ) {
                tokenRequest = null;
                LOGGER.info("Invalid auth_req_id");

                throw new UnAuthorizedRequest("Invalid auth_req_id");

            } else if(artifactStoreConnectors.getAuthResponse(auth_req_id)==null) {
                LOGGER.info("Invalid auth_req_id");

                throw new UnAuthorizedRequest("Invalid auth_req_id");

            }else if (grant_type.isEmpty()) {
                tokenRequest = null;
                LOGGER.info("Improper grant_type");
                throw new BadRequest("Improper grant_type");

            } else {
                //check whether provided auth_req_id is valid and provided by the system and has relevant auth response

                    if (!(artifactStoreConnectors.getAuthResponse(auth_req_id) == null) &&
                            (grant_type.equals(cibaparameters.getGrant_type()))) {

                        long expiryduration = artifactStoreConnectors.getPollingAttribute(auth_req_id).getExpiresIn();
                        long issuedtime = artifactStoreConnectors.getPollingAttribute(auth_req_id).getIssuedTime();
                        long currenttime = ZonedDateTime.now().toInstant().toEpochMilli();
                        long interval = artifactStoreConnectors.getPollingAttribute(auth_req_id).getPollingInterval();
                        long lastpolltime = artifactStoreConnectors.getPollingAttribute(auth_req_id).getLastPolledTime();
                        Boolean notificationIssued = artifactStoreConnectors.getPollingAttribute(auth_req_id).getNotificationIssued();
                        System.out.println("notification status : " +notificationIssued);
                        try {
                            if (!notificationIssued) {
                                    LOGGER.info("Improper Flow. Subscribed to Ping but yet Polling");
                                    throw new BadRequest("Improper Flow. Subscribed to Ping but yet Polling");

                            }   else if (currenttime > issuedtime + expiryduration + 5) {
                                tokenRequest = null;
                                LOGGER.info("Expired Token");
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
                                System.out.println("notification status 2: " +notificationIssued);
                                pollingAtrribute2.setNotificationIssued(notificationIssued);
                                // TODO: 8/31/19   pollingAtrribute2.setNotificationIssued();

                                artifactStoreConnectors.addPollingAttribute(auth_req_id,pollingAtrribute2);
                                //updating the polling frequency -deleting and adding new object with updated values
                                tokenRequest = null;
                                throw new BadRequest("Slow Down");

                            } else {
                                if (TokenResponseHandler.getInstance().checkTokenReceived(auth_req_id)) {
                                    //check for the reception of token is handled here
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
                                    pollingAtrribute3.setNotificationIssued(notificationIssued);

                                    artifactStoreConnectors.addPollingAttribute(auth_req_id,pollingAtrribute3);
                                    //updating last polled time

                                } else {
                                    tokenRequest = null;
                                    throw new BadRequest("authorization pending");
                                }
                            }
                        } catch (BadRequest badrequest) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badrequest.getMessage());
                        }
                        return tokenRequest;

                    }



            }

        } catch (UnAuthorizedRequest authorizedRequest) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, authorizedRequest.getMessage());
        } catch (BadRequest badrequest) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badrequest.getMessage());
        }

        return tokenRequest;
    }

}



