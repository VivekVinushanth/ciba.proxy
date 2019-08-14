package cache;

import ciba.proxy.server.servicelayer.ServerRequestHandler;
import handlers.Handlers;
import transactionartifacts.Artifacts;
import transactionartifacts.CIBAauthRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class CibaProxyCache {

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();


    private AuthRequestCache authRequestCache;
    private AuthResponseCache authResponseCache;
    private TokenRequestCache tokenRequestCache;
    private TokenResponseCache tokenResponseCache;
    private ExpiresInCache expiresInCache;
    private IntervalCache intervalCache;
    private IssuedTimeCache issuedTimeCache;
    private LastPollCache lastPollCache;

    private CibaProxyCache() {
        authRequestCache = AuthRequestCache.getInstance();
        authResponseCache = AuthResponseCache.getInstance();
        tokenRequestCache =  TokenRequestCache.getInstance();
        tokenResponseCache =  TokenResponseCache.getInstance();
        expiresInCache =  ExpiresInCache.getInstance();
        intervalCache =  IntervalCache.getInstance();
        issuedTimeCache =  IssuedTimeCache.getInstance();
        lastPollCache =  LastPollCache.getInstance();

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

    public ExpiresInCache getExpiresInCache() {
        return expiresInCache;
    }

    public IntervalCache getIntervalCache() {
        return intervalCache;
    }

    public IssuedTimeCache getIssuedTimeCache() {
        return issuedTimeCache;
    }

    public LastPollCache getLastPollCache() {
        return lastPollCache;
    }

    public static CibaProxyCache getCibaProxyCacheInstance() {
        return cibaProxyCacheInstance;
    }


}
