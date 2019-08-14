package cache;

import handlers.Handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * This class is responsible to store issued time of
 * authentication response of authentication response.
 * */
public class IssuedTimeCache implements ProxyCache {
    private static final Logger LOGGER = Logger.getLogger(IssuedTimeCache.class.getName());
    private IssuedTimeCache() {

    }

    private static IssuedTimeCache issuedTimeCacheInstance = new IssuedTimeCache();

    public static IssuedTimeCache getInstance() {
        if (issuedTimeCacheInstance == null) {

            synchronized (IssuedTimeCache.class) {

                if (issuedTimeCacheInstance == null) {

                    /* instance will be created at request time */
                    issuedTimeCacheInstance = new IssuedTimeCache();
                }
            }
        }
        return issuedTimeCacheInstance;
    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();
    private HashMap<String, Object> issuedTimeCache = new HashMap<String , Object>();

    @Override
    public void add(String auth_req_id, Object issuedtime) {
        issuedTimeCache.put(auth_req_id, issuedtime);
        LOGGER.info(auth_req_id +": Issued Time added.");
    }

    @Override
    public void remove(String auth_req_id) {
        issuedTimeCache.remove(auth_req_id);
    }

    @Override
    public Object get(String auth_req_id) {
        return issuedTimeCache.get(auth_req_id);
    }

    @Override
    public void clear() {

    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public void register(Object object) {
        interestedparty.add((Handlers) object);

    }


}
