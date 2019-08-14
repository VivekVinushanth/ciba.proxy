package cache;

import handlers.Handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * This class is responsible to store expires-in time of authentication response.
 * */
public class ExpiresInCache implements ProxyCache {
    private static final Logger LOGGER = Logger.getLogger(ExpiresInCache.class.getName());

    private ExpiresInCache() {

    }

    private static ExpiresInCache expiresInCacheInstance = new ExpiresInCache();

    public static ExpiresInCache getInstance() {
        if (expiresInCacheInstance == null) {

            synchronized (ExpiresInCache.class) {

                if (expiresInCacheInstance == null) {

                    /* instance will be created at request time */
                    expiresInCacheInstance = new ExpiresInCache();
                }
            }
        }
        return expiresInCacheInstance;


    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();
    private HashMap<String, Object> expiresInCache = new HashMap<String , Object >();

    @Override
    public void add(String auth_req_id, Object timestamp) {
        expiresInCache.put(auth_req_id, timestamp);
        LOGGER.info("Expiry Time added");
    }

    @Override
    public void remove(String auth_req_id) {
        expiresInCache.remove(auth_req_id);
    }

    @Override
    public Object get(String auth_req_id) {
        return expiresInCache.get(auth_req_id);
    }

    @Override
    public void clear() {

    }

    @Override
    public long size() {
        return expiresInCache.size();
    }

    @Override
    public void register(Object object) {
        interestedparty.add((Handlers) object);

    }
}
