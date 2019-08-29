package cache;

import handlers.Handlers;
import transactionartifacts.PollingAtrribute;

import java.util.ArrayList;

public class CibaProxyCache {

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();


    private AuthRequestCache authRequestCache;
    private AuthResponseCache authResponseCache;
    private TokenRequestCache tokenRequestCache;
    private TokenResponseCache tokenResponseCache;
    private PollingAttributeCache pollingAtrributeCache;

    private CibaProxyCache() {
        authRequestCache = AuthRequestCache.getInstance();
        authResponseCache = AuthResponseCache.getInstance();
        tokenRequestCache =  TokenRequestCache.getInstance();
        tokenResponseCache =  TokenResponseCache.getInstance();
        pollingAtrributeCache = PollingAttributeCache.getInstance();

    }

    private static CibaProxyCache cibaProxyCacheInstance = new CibaProxyCache();

    public static CibaProxyCache getInstance() {
        if (cibaProxyCacheInstance == null) {

            synchronized (CibaProxyCache.class) {

                if (cibaProxyCacheInstance == null) {

                    /* instance will be created at request time */
                    cibaProxyCacheInstance = new CibaProxyCache();
                }
            }
        }
        return cibaProxyCacheInstance;


    }

    public  AuthRequestCache getAuthRequestCache() {
        return authRequestCache;
    }

    public AuthResponseCache getAuthResponseCache() {
        return authResponseCache;
    }

    public TokenRequestCache getTokenRequestCache() {
        return tokenRequestCache;
    }

    public TokenResponseCache getTokenResponseCache() {
        return tokenResponseCache;
    }
    public  PollingAttributeCache getPollingAtrributeCache(){
        return pollingAtrributeCache;
    }



    public static CibaProxyCache getCibaProxyCacheInstance() {
        return cibaProxyCacheInstance;
    }


}
