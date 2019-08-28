package dao;

import jdbc.CibaProxyJdbcStore;
import transactionartifacts.CIBAauthRequest;
import transactionartifacts.CIBAauthResponse;
import transactionartifacts.TokenRequest;
import transactionartifacts.TokenResponse;

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
     * Add expiry time of each token object to expiry DB.
     */
    public void addExpiresTime(String auth_req_id, long timestamp) {
        cibaProxyJdbcStore.getExpiresInDB().add(auth_req_id, timestamp);
    }


    /**
     * Add latest polling time to last-poll DB.
     */
    public void addLastPollTime(String auth_req_id, long lastpolltime) {
        cibaProxyJdbcStore.getLastPollDB().add(auth_req_id, lastpolltime);
    }


    /**
     * Add issuedtime  to issuedtime DB.
     */
    public void addIssuedTime(String auth_req_id, long issuedtime) {
        cibaProxyJdbcStore.getIssuedTimeDB().add(auth_req_id, issuedtime);
    }


    /**
     * Add interval to interval DB.
     */
    public void addInterval(String auth_req_id, long interval) {
        cibaProxyJdbcStore.getIntervalDB().add(auth_req_id, interval);
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
     * Remove expiry time from expiry DB.
     */
    public void removeExpiresTime(String auth_req_id) {
        cibaProxyJdbcStore.getExpiresInDB().remove(auth_req_id);
    }

    /**
     * Remove last polling time from last-poll DB.
     */
    public void removeLastPollTime(String auth_req_id) {
        cibaProxyJdbcStore.getLastPollDB().remove(auth_req_id);
    }

    /**
     * Remove last issued time from issuedtime DB.
     */
    public void removeIssuedTime(String auth_req_id) {
        cibaProxyJdbcStore.getIssuedTimeDB().remove(auth_req_id);
    }

    /**
     * Remove interval from interval DB.
     */
    public void removeInterval(String auth_req_id) {
        cibaProxyJdbcStore.getIntervalDB().remove(auth_req_id);
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


    /**
     * Get expiry from expirytime DB.
     */
    public long getExpiresTime(String auth_req_id) {
        return Long.parseLong(String.valueOf(cibaProxyJdbcStore.getExpiresInDB().get(auth_req_id)));
    }

    /**
     * Get lastpolltime from last-poll DB.
     */
    public long getLastPollTime(String auth_req_id) {
        return Long.parseLong(String.valueOf(cibaProxyJdbcStore.getLastPollDB().get(auth_req_id)));
    }

    /**
     * Get issuedtime from issuedtime DB.
     */
    public long getIssuedTime(String auth_req_id) {
        return Long.parseLong(String.valueOf((cibaProxyJdbcStore.getIssuedTimeDB().get(auth_req_id))));
    }

    /**
     * Get interval from interval DB.
     */
    public long getInterval(String auth_req_id) {
        return Long.parseLong(String.valueOf((cibaProxyJdbcStore.getIntervalDB().get(auth_req_id))));

    }

    @Override
    public void registerToAuthRequestObservers(Object authRequestHandler) {
        cibaProxyJdbcStore.getAuthRequestDB().register(authRequestHandler);

    }

    @Override
    public void registerToAuthResponseObservers(Object authResponseHandler) {
        cibaProxyJdbcStore.getAuthResponseDB().register(authResponseHandler);
    }

    @Override
    public void registerToTokenRequestObservers(Object tokenRequestHandler) {
        cibaProxyJdbcStore.getTokenRequestDB().register(tokenRequestHandler);
    }

    @Override
    public void registerToTokenResponseObservers(Object tokenResponseHandler) {
        cibaProxyJdbcStore.getTokenResponseDB().register(tokenResponseHandler);
    }

    @Override
    public void registerToExpiryTimeObservers(Object expiryHandler) {
        cibaProxyJdbcStore.getExpiresInDB().register(expiryHandler);
    }

    @Override
    public void registerToLastPollObservers(Object pollHandler) {
        cibaProxyJdbcStore.getLastPollDB().register(pollHandler);
    }

    @Override
    public void registerToIssuedTimeObservers(Object issuedTimeHandler) {
        cibaProxyJdbcStore.getIssuedTimeDB().register(issuedTimeHandler);
    }

    @Override
    public void registerToIntervalObservers(Object intervalHandler) {
        cibaProxyJdbcStore.getIntervalDB().register(intervalHandler);
    }
}



