/*
 * Copyright (c) 2013, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package dao;

import cache.CibaProxyCache;
import transactionartifacts.CIBAauthRequest;
import transactionartifacts.CIBAauthResponse;
import transactionartifacts.PollingAtrribute;
import transactionartifacts.TokenRequest;
import transactionartifacts.TokenResponse;

/**
 * Supports Cache -in memory storage.
 */
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
     *
     * @param authReqId   Ciba Authentication request identifier.
     * @param authrequest Ciba Authentication request.
     */
    public void addAuthRequest(String authReqId, Object authrequest) {

        if (authrequest instanceof CIBAauthRequest) {
            cibaProxyCache.getAuthRequestCache().add(authReqId, authrequest);
        } else {
            // do nothing.
        }
    }

    /**
     * Add Authentication response object to auth response cache.
     *
     * @param authReqId    Ciba Authentication request identifier.
     * @param authresponse Ciba Authentication response.
     */
    public void addAuthResponse(String authReqId, Object authresponse) {

        if (authresponse instanceof CIBAauthResponse) {
            cibaProxyCache.getAuthResponseCache().add(authReqId, authresponse);
        }
    }

    /**
     * Add Token request object to token request cache.
     *
     * @param authReqID    Ciba Authentication request identifier.
     * @param tokenrequest TokenRequest Object.
     */
    public void addTokenRequest(String authReqID, Object tokenrequest) {

        if (tokenrequest instanceof TokenRequest) {
            cibaProxyCache.getTokenRequestCache().add(authReqID, tokenrequest);
        }
    }

    /**
     * Add Authentication response object to token cache.
     *
     * @param authReqId     Ciba Authentication request identifier.
     * @param tokenresponse Token Response.
     */
    public void addTokenResponse(String authReqId, Object tokenresponse) {

        if (tokenresponse instanceof TokenResponse) {
            cibaProxyCache.getTokenResponseCache().add(authReqId, tokenresponse);
        }
    }

    /**
     * Add pollingAttributeDO polling attribute cache.
     *
     * @param authReqID        Ciba Authentication request identifier.
     * @param pollingattribute Polling attribute DO.
     */
    @Override
    public void addPollingAttribute(String authReqID, Object pollingattribute) {

        if (pollingattribute instanceof PollingAtrribute) {
            cibaProxyCache.getPollingAtrributeCache().add(authReqID, pollingattribute);
        }
    }

    /**
     * Remove Authentication request object from auth request cache.
     *
     * @param authReqID Ciba Authentication request identifier.
     */
    public void removeAuthRequest(String authReqID) {

        cibaProxyCache.getAuthRequestCache().remove(authReqID);
    }

    /**
     * Remove Authentication response object from auth response cache.
     *
     * @param authReqID Ciba Authentication request identifier.
     */
    public void removeAuthResponse(String authReqID) {

        cibaProxyCache.getAuthResponseCache().remove(authReqID);
    }

    /**
     * Remove token request object from token request cache.
     *
     * @param authReqID Ciba Authentication request identifier.
     */
    public void removeTokenRequest(String authReqID) {

        cibaProxyCache.getTokenRequestCache().remove(authReqID);
    }

    /**
     * Remove Authentication response object from auth response cache.
     *
     * @param authReqID Ciba Authentication request identifier.
     */
    public void removeTokenResponse(String authReqID) {

        cibaProxyCache.getTokenResponseCache().remove(authReqID);
    }

    /**
     * Remove Polling attribute DO from cache.
     *
     * @param authReqID Ciba Authentication request identifier.
     */
    @Override
    public void removePollingAttribute(String authReqID) {

        cibaProxyCache.getPollingAtrributeCache().remove(authReqID);
    }

    /**
     * Get CIBA auth request  from Auth request cache.
     *
     * @param authReqID Ciba Authentication request identifier.
     * @return Ciba Authentication request DO.
     */
    public CIBAauthRequest getAuthRequest(String authReqID) {

        return (CIBAauthRequest) cibaProxyCache.getAuthRequestCache().get(authReqID);
    }

    /**
     * Get CIBA auth response  from Authresponse cache.
     *
     * @param authReqID Ciba Authentication request identifier.
     * @return Ciba Authentication response.
     */
    public CIBAauthResponse getAuthResponse(String authReqID) {

        return (CIBAauthResponse) cibaProxyCache.getAuthResponseCache().get(authReqID);
    }

    /**
     * Get Token request  from token request cache.
     *
     * @param authReqID Ciba Authentication request identifier.
     * @return Token Request.
     */
    public TokenRequest getTokenRequest(String authReqID) {

        return (TokenRequest) cibaProxyCache.getTokenRequestCache().get(authReqID);
    }

    /**
     * Get token response  from token response cache.
     *
     * @param authReqID Ciba Authentication request identifier.
     * @return Token Response.
     */
    public TokenResponse getTokenResponse(String authReqID) {

        return (TokenResponse) cibaProxyCache.getTokenResponseCache().get(authReqID);
    }

    /**
     * Get token response  from token response cache.
     *
     * @param authReqID Ciba Authentication request identifier.
     * @return Polling attribute DO.
     */
    @Override
    public PollingAtrribute getPollingAttribute(String authReqID) {

        return (PollingAtrribute) cibaProxyCache.getPollingAtrributeCache().get(authReqID);
    }

    /**
     * Register to authentication request observer list.
     *
     * @param authRequestHandler Authentication request handler.
     */
    @Override
    public void registerToAuthRequestObservers(Object authRequestHandler) {

        cibaProxyCache.getAuthRequestCache().register(authRequestHandler);

    }

    /**
     * Register to authentication response observer list.
     *
     * @param authResponseHandler Authentication response handler.
     */
    @Override
    public void registerToAuthResponseObservers(Object authResponseHandler) {

        cibaProxyCache.getAuthResponseCache().register(authResponseHandler);

    }

    /**
     * Register to token request observer list.
     *
     * @param tokenRequestHandler Token request handler.
     */
    @Override
    public void registerToTokenRequestObservers(Object tokenRequestHandler) {

        cibaProxyCache.getTokenRequestCache().register(tokenRequestHandler);
    }

    /**
     * Register to token response observer list.
     *
     * @param tokenResponseHandler Token response handler.
     */
    @Override
    public void registerToTokenResponseObservers(Object tokenResponseHandler) {

        cibaProxyCache.getTokenResponseCache().register(tokenResponseHandler);
    }

    /**
     * Register to authentication response observer list.
     *
     * @param pollingHandler Polling handler.
     */
    @Override
    public void registerToPollingAttribute(Object pollingHandler) {
        //No need of validation or implementation
    }

}
