package dao;

import jdbc.CibaProxyJdbcStore;
import transactionartifacts.*;

/**
 * This class supports JDBC storage.
 * */
public class JdbcArtifactStoreConnector implements ArtifactStoreConnectors {

    private CibaProxyJdbcStore cibaProxyJdbcStore = CibaProxyJdbcStore.getcibaProxyJdbcStoreInstance();

    private static JdbcArtifactStoreConnector jdbcArtifactStoreConnectorInstance = new JdbcArtifactStoreConnector();

    public static JdbcArtifactStoreConnector getInstance() {
        if (jdbcArtifactStoreConnectorInstance == null) {

            synchronized (JdbcArtifactStoreConnector.class) {

                if (jdbcArtifactStoreConnectorInstance == null) {

                    /* instance will be created at request time */
                    jdbcArtifactStoreConnectorInstance = new JdbcArtifactStoreConnector();
                }
            }
        }
        return jdbcArtifactStoreConnectorInstance;
    }


    /**
     * Add Authentication request object to authrequest DB.
     */
    public void addAuthRequest(String auth_req_id, Object authrequest) {
        if (authrequest instanceof CIBAauthRequest) {
            cibaProxyJdbcStore.getAuthRequestDB().add(auth_req_id, authrequest);
        } else {
            // TODO: 8/6/19 do logging here for all below
        }
    }


    /**
     * Add Authentication response object to authresponse DB.
     */
    public void addAuthResponse(String auth_req_id, Object authresponse) {
        if (authresponse instanceof CIBAauthResponse) {
            cibaProxyJdbcStore.getAuthResponseDB().add(auth_req_id, authresponse);
        }
    }


    /**
     * Add Token request object to tokenrequest DB.
     */
    public void addTokenRequest(String auth_req_id, Object tokenrequest) {
        if (tokenrequest instanceof TokenRequest) {
            cibaProxyJdbcStore.getTokenRequestDB().add(auth_req_id, tokenrequest);
        }
    }


    /**
     * Add Authentication response object to token DB.
     */
    public void addTokenResponse(String auth_req_id, Object tokenresponse) {
        if (tokenresponse instanceof TokenResponse) {
            cibaProxyJdbcStore.getTokenResponseDB().add(auth_req_id, tokenresponse);
        }
    }

    /**
     * Add Polling Attribute object to Polling Attribute DB.
     */
    public void addPollingAttribute(String auth_req_id, Object pollingattribute) {
        if(pollingattribute instanceof PollingAtrribute){
            cibaProxyJdbcStore.getPollingAttributeDB().add(auth_req_id,pollingattribute);
        }
    }



    /**
     * Remove Authentication request object from authrequest DB.
     */
    public void removeAuthRequest(String auth_req_id) {
        cibaProxyJdbcStore.getAuthRequestDB().remove(auth_req_id);
    }

    /**
     * Remove Authentication response object from authresponse DB.
     */
    public void removeAuthResponse(String auth_req_id) {
        cibaProxyJdbcStore.getAuthResponseDB().remove(auth_req_id);
    }

    /**
     * Remove token request object from tokenrequest DB.
     */
    public void removeTokenRequest(String auth_req_id) {
        cibaProxyJdbcStore.getTokenRequestDB().remove(auth_req_id);
    }

    /**
     * Remove Authentication response object from authresponse DB.
     */
    public void removeTokenResponse(String auth_req_id) {
        cibaProxyJdbcStore.getTokenResponseDB().remove(auth_req_id);
    }


    /**
     * Remove Polling Attribute object from Polling Attribute DB.
     */
    public void removePollingAttribute(String auth_req_id) {
        cibaProxyJdbcStore.getPollingAttributeDB().remove(auth_req_id);
    }


    /**
     * Get CIBA auth request  from Authrequest DB.
     */
    public CIBAauthRequest getAuthRequest(String auth_req_id) {
        return (CIBAauthRequest) cibaProxyJdbcStore.getAuthRequestDB().get(auth_req_id);
    }


    /**
     * Get CIBA auth response  from Authresponse DB.
     */
    public CIBAauthResponse getAuthResponse(String auth_req_id) {
        return CIBAauthResponse.class.cast(cibaProxyJdbcStore.getAuthResponseDB().get(auth_req_id));
    }


    /**
     * Get Token request  from token request DB.
     */
    public TokenRequest getTokenRequest(String auth_req_id) {
        return TokenRequest.class.cast(cibaProxyJdbcStore.getTokenRequestDB().get(auth_req_id));
    }


    /**
     * Get token response  from token response DB.
     */
    public TokenResponse getTokenResponse(String auth_req_id) {
        return TokenResponse.class.cast(cibaProxyJdbcStore.getTokenResponseDB().get(auth_req_id));
    }


    public PollingAtrribute getPollingAttribute(String auth_req_id) {
        return PollingAtrribute.class.cast(cibaProxyJdbcStore.getPollingAttributeDB().get(auth_req_id));
    }





    public void registerToAuthRequestObservers(Object authRequestHandler) {
        cibaProxyJdbcStore.getAuthRequestDB().register(authRequestHandler);

    }


    public void registerToAuthResponseObservers(Object authResponseHandler) {
        cibaProxyJdbcStore.getAuthResponseDB().register(authResponseHandler);
    }


    public void registerToTokenRequestObservers(Object tokenRequestHandler) {
        cibaProxyJdbcStore.getTokenRequestDB().register(tokenRequestHandler);
    }


    public void registerToTokenResponseObservers(Object tokenResponseHandler) {
        cibaProxyJdbcStore.getTokenResponseDB().register(tokenResponseHandler);
    }

    public void registerToPollingAttribute(Object pollingatrribute) {
       //implement if needed
    }

}



