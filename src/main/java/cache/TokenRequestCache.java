package cache;

import handlers.Handlers;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import transactionartifacts.TokenRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class TokenRequestCache implements ProxyCache {
    private static final Logger LOGGER = Logger.getLogger(TokenRequestCache.class.getName());
    private TokenRequestCache() {

    }

    private static TokenRequestCache tokenRequestCacheInstance = new TokenRequestCache();

    public static TokenRequestCache getInstance() {
        if (tokenRequestCacheInstance == null) {

            synchronized (TokenRequestCache.class) {

                if (tokenRequestCacheInstance == null) {

                    /* instance will be created at request time */
                    tokenRequestCacheInstance = new TokenRequestCache();
                }
            }
        }
        return tokenRequestCacheInstance;
    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();
    HashMap<String, Object> tokenRequestCache = new HashMap<String , Object>();

    @Override
    public void add(String auth_req_id, Object tokenrequest) {
       if (tokenrequest instanceof TokenRequest) {
           tokenRequestCache.put(auth_req_id, tokenrequest);
            LOGGER.info(auth_req_id+" : Token Request added.");
       }
    }

    @Override
    public void remove(String auth_req_idey) {
        tokenRequestCache.remove(auth_req_idey);
    }

    @Override
    public Object get(String auth_req_id) {
        return tokenRequestCache.get(auth_req_id);
    }

    @Override
    public void clear() {

    }

    @Override
    public long size() {
        return tokenRequestCache.size();
    }

    @Override
    public void register(Object object) {
        interestedparty.add((Handlers) object);
    }

}
