package dao;

import cache.CibaProxyCache;
import transactionartifacts.*;

/**
 * This class supports Cache -in memory storage.
 * */
public class CacheArtifactStoreConnector implements ArtifactStoreConnectors {

    CibaProxyCache cibaProxyCache;

    private CacheArtifactStoreConnector() {
        cibaProxyCache = CibaProxyCache.getCibaProxyCacheInstance();
    }

    private static CacheArtifactStoreConnector cacheArtifactStoreConnectorInstance = new CacheArtifactStoreConnector();

    public static CacheArtifactStoreConnector getInstance() {
        if (cacheArtifactStoreConnectorInstance == null) {

            synchronized (CacheArtifactStoreConnector.class) {

                if (cacheArtifactStoreConnectorInstance == null) {

                    /* instance will be created at request time */
                    cacheArtifactStoreConnectorInstance = new CacheArtifactStoreConnector();
                }
            }
        }
        return cacheArtifactStoreConnectorInstance;
    }



    /**
     * Add Authentication request object to authrequest cache.
     * */
    public void addAuthRequest(String auth_req_id, Object authrequest) {
        if (authrequest instanceof CIBAauthRequest) {
            cibaProxyCache.getAuthRequestCache().add(auth_req_id, authrequest);
        } else {
            // TODO: 8/6/19 do logging here for all below
        }
    }


    /**
     * Add Authentication response object to authresponse cache.
     * */
    public void addAuthResponse(String auth_req_id, Object authresponse) {
        if (authresponse instanceof CIBAauthResponse) {
            cibaProxyCache.getAuthResponseCache().add(auth_req_id, authresponse);
        }
    }


    /**
     * Add Token request object to tokenrequest cache.
     * */
    public void addTokenRequest(String auth_req_id, Object tokenrequest) {
        if (tokenrequest instanceof TokenRequest) {
            cibaProxyCache.getTokenRequestCache().add(auth_req_id, tokenrequest);
        }
    }


    /**
     * Add Authentication response object to token cache.
     * */
    public void addTokenResponse(String auth_req_id, Object tokenresponse) {
        if (tokenresponse instanceof TokenResponse) {
            cibaProxyCache.getTokenResponseCache().add(auth_req_id, tokenresponse);
        }
    }

    @Override
    public void addPollingAttribute(String auth_req_id, Object pollingattribute) {
        if(pollingattribute instanceof PollingAtrribute){
            cibaProxyCache.getPollingAtrributeCache().add(auth_req_id,pollingattribute);
        }
    }



    /**
     * Remove Authentication request object from authrequest cache.
     * */
    public void removeAuthRequest(String auth_req_id) {
        cibaProxyCache.getAuthRequestCache().remove(auth_req_id);
    }

    /**
     * Remove Authentication response object from authresponse cache.
     * */
    public void removeAuthResponse(String auth_req_id) {
        cibaProxyCache.getAuthResponseCache().remove(auth_req_id);
    }

    /**
     * Remove token request object from tokenrequest cache.
     * */
    public void removeTokenRequest(String auth_req_id) {
        cibaProxyCache.getTokenRequestCache().remove(auth_req_id);
    }

    /**
     * Remove Authentication response object from authresponse cache.
     * */
    public void removeTokenResponse(String auth_req_id) {
        cibaProxyCache.getTokenResponseCache().remove(auth_req_id);
    }

    @Override
    public void removePollingAttribute(String auth_req_id) {
        cibaProxyCache.getPollingAtrributeCache().remove(auth_req_id);
    }



    /**
     * Get CIBA auth request  from Authrequest cache.
     * */
    public CIBAauthRequest getAuthRequest(String auth_req_id) {
       return (CIBAauthRequest) cibaProxyCache.getAuthRequestCache().get(auth_req_id);
    }


    /**
     * Get CIBA auth response  from Authresponse cache.
     * */
    public CIBAauthResponse getAuthResponse(String auth_req_id) {
        return CIBAauthResponse.class.cast(cibaProxyCache.getAuthResponseCache().get(auth_req_id));
    }


    /**
     * Get Token request  from token request cache.
     * */
    public TokenRequest getTokenRequest(String auth_req_id) {
        return TokenRequest.class.cast(cibaProxyCache.getTokenRequestCache().get(auth_req_id));
    }


    /**
     * Get token response  from token response cache.
     * */
    public TokenResponse getTokenResponse(String auth_req_id) {
        return TokenResponse.class.cast(cibaProxyCache.getTokenResponseCache().get(auth_req_id));
    }

    @Override
    public PollingAtrribute getPollingAttribute(String auth_req_id) {
        return PollingAtrribute.class.cast(cibaProxyCache.getPollingAtrributeCache().get(auth_req_id));
    }



    @Override
    public void registerToAuthRequestObservers(Object authRequestHandler) {
        cibaProxyCache.getAuthRequestCache().register(authRequestHandler);

    }

    @Override
    public void registerToAuthResponseObservers(Object authResponseHandler) {
        cibaProxyCache.getAuthResponseCache().register(authResponseHandler);

    }

    @Override
    public void registerToTokenRequestObservers(Object tokenRequestHandler) {
        cibaProxyCache.getTokenRequestCache().register(tokenRequestHandler);
    }


    @Override
    public void registerToTokenResponseObservers(Object tokenResponseHandler) {
        cibaProxyCache.getTokenResponseCache().register(tokenResponseHandler);
    }

    @Override
    public void registerToPollingAttribute(Object pollingatrribute) {
        //No need of validation or implementation
    }

}
