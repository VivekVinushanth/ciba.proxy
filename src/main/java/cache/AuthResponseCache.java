package cache;

import handlers.Handlers;
import jdk.nashorn.api.scripting.JSObject;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import transactionartifacts.CIBAauthResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;


public class AuthResponseCache  implements ProxyCache {

    private static final Logger LOGGER = Logger.getLogger(AuthResponseCache.class.getName());

    private AuthResponseCache() {

    }

    private static AuthResponseCache authResponseCacheInstance = new AuthResponseCache();

    public static AuthResponseCache getInstance() {
        if (authResponseCacheInstance == null) {

            synchronized (AuthResponseCache.class) {

                if (authResponseCacheInstance == null) {

                    /* instance will be created at request time */
                    authResponseCacheInstance = new AuthResponseCache();
                }
            }
        }
        return authResponseCacheInstance;


    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();
    private HashMap<String, Object> authResponseCache = new HashMap<String , Object>();

    @Override
    public void add(String auth_req_id, Object authresponse) {
      if(authresponse instanceof CIBAauthResponse) {

          authResponseCache.put(auth_req_id, authresponse);
          LOGGER.info("CIBA Auth response added to store.");
      }

    }

    @Override
    public void remove(String auth_req_idey) {
        authResponseCache.remove(auth_req_idey);
    }

    @Override
    public Object get(String auth_req_id) {
        return authResponseCache.get(auth_req_id);
    }

    @Override
    public void clear() {

    }

    @Override
    public long size() {
        return authResponseCache.size();
    }

    @Override
    public void register(Object object) {
        interestedparty.add((Handlers) object);
    }
}
