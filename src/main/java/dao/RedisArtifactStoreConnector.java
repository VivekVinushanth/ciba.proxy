package dao;

import transactionartifacts.CIBAauthRequest;
import transactionartifacts.CIBAauthResponse;
import transactionartifacts.TokenRequest;
import transactionartifacts.TokenResponse;

public class RedisArtifactStoreConnector implements ArtifactStoreConnectors {

    private static RedisArtifactStoreConnector redisArtifactStoreConnectorInstance = new RedisArtifactStoreConnector();

    public static RedisArtifactStoreConnector getInstance() {
        if (redisArtifactStoreConnectorInstance == null) {

            synchronized (RedisArtifactStoreConnector.class) {

                if (redisArtifactStoreConnectorInstance == null) {

                    /* instance will be created at request time */
                    redisArtifactStoreConnectorInstance = new RedisArtifactStoreConnector();
                }
            }
        }
        return redisArtifactStoreConnectorInstance;
    }

    @Override
    public void addAuthRequest(String auth_req_id, Object authrequest) {

    }

    @Override
    public void addAuthResponse(String auth_req_id, Object authresponse) {

    }

    @Override
    public void addTokenRequest(String auth_req_id, Object tokenrequest) {

    }

    @Override
    public void addTokenResponse(String auth_req_id, Object authresponse) {

    }

    @Override
    public void addExpiresTime(String auth_req_id, long timestamp) {

    }

    @Override
    public void addLastPollTime(String auth_req_id, long lastpolltime) {

    }

    @Override
    public void addIssuedTime(String auth_req_id, long issuedtime) {

    }

    @Override
    public void addInterval(String auth_req_id, long interval) {

    }

    @Override
    public void removeAuthRequest(String auth_req_id) {

    }

    @Override
    public void removeAuthResponse(String auth_req_id) {

    }

    @Override
    public void removeTokenRequest(String auth_req_id) {

    }

    @Override
    public void removeTokenResponse(String auth_req_id) {

    }

    @Override
    public void removeExpiresTime(String auth_req_id) {

    }

    @Override
    public void removeLastPollTime(String auth_req_id) {

    }

    @Override
    public void removeIssuedTime(String auth_req_id) {

    }

    @Override
    public void removeInterval(String auth_req_id) {

    }

    @Override
    public CIBAauthRequest getAuthRequest(String auth_req_id) {
        return null;
    }

    @Override
    public CIBAauthResponse getAuthResponse(String auth_req_id) {
        return null;
    }

    @Override
    public TokenRequest getTokenRequest(String auth_req_id) {
        return null;
    }

    @Override
    public TokenResponse getTokenResponse(String auth_req_id) {
        return null;
    }

    @Override
    public long getExpiresTime(String auth_req_id) {
        return 0;
    }

    @Override
    public long getLastPollTime(String auth_req_id) {
        return 0;
    }

    @Override
    public long getIssuedTime(String auth_req_id) {
        return 0;
    }

    @Override
    public long getInterval(String auth_req_id) {
        return 0;
    }

    @Override
    public void registerToAuthRequestCache(Object authRequestHandler) {

    }

    @Override
    public void registerToAuthResponseCache(Object authResponseHandler) {

    }

    @Override
    public void registerToTokenResponseCache(Object tokenRequestHandler) {

    }

    @Override
    public void registerToTokenRequestCache(Object tokenResponseHandler) {

    }

    @Override
    public void registerToExpiryTimeCache(Object expiryHandler) {

    }

    @Override
    public void registerToLastPollCache(Object pollHandler) {

    }

    @Override
    public void registerToIssuedTimeCache(Object issuedTimeHandler) {

    }

    @Override
    public void registerToIntervalCache(Object intervalHandler) {

    }
}
