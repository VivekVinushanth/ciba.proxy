package cache;

import handlers.Handlers;
import transactionartifacts.TokenResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class TokenResponseCache  implements  ProxyCache {
    private static final Logger LOGGER = Logger.getLogger(TokenResponseCache.class.getName());
    private TokenResponseCache() {

    }

    private static TokenResponseCache tokenResponseCacheInstance = new TokenResponseCache();

    public static TokenResponseCache getInstance() {
        if (tokenResponseCacheInstance == null) {

            synchronized (TokenResponseCache.class) {

                if (tokenResponseCacheInstance == null) {

                    /* instance will be created at request time */
                    tokenResponseCacheInstance = new TokenResponseCache();
                }
            }
        }
        return tokenResponseCacheInstance;
    }
    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();
    private HashMap<String, Object> tokenResponseCache = new HashMap<String , Object>();

    @Override
    public void add(String auth_req_id, Object tokenresponse) {
        if (tokenresponse instanceof TokenResponse) {
            tokenResponseCache.put(auth_req_id, tokenresponse);
            LOGGER.info(auth_req_id+" : Token Response added by the server.");

        }


    }

    @Override
    public void remove(String auth_req_id) {
        tokenResponseCache.remove(auth_req_id);
    }

    @Override
    public Object get(String auth_req_id) {
        LOGGER.info(auth_req_id+" : Polling checked for Token Response availability.");
        return tokenResponseCache.get(auth_req_id);
    }

    @Override
    public void clear() {

    }

    @Override
    public long size() {
        return tokenResponseCache.size();
    }

    @Override
    public void register(Object object) {
        interestedparty.add((Handlers) object);
    }
}
