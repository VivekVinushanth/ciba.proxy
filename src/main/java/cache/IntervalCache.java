package cache;

import handlers.Handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * This class is responsible to store polling frequency.
 * */
public class IntervalCache implements ProxyCache {
    private static final Logger LOGGER = Logger.getLogger(IntervalCache.class.getName());
    private IntervalCache() {

    }

    private static IntervalCache intervalCacheInstance = new IntervalCache();

    public static IntervalCache getInstance() {
        if (intervalCacheInstance == null) {

            synchronized (IntervalCache.class) {

                if (intervalCacheInstance == null) {

                    /* instance will be created at request time */
                    intervalCacheInstance = new IntervalCache();
                }
            }
        }
        return intervalCacheInstance;
    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();
    private HashMap<String, Object> intervalCache = new HashMap<String , Object>();


    public void add(String auth_req_id, Object interval) {
        intervalCache.put(auth_req_id, interval);
        LOGGER.info(auth_req_id +": Interval added.");
    }



    @Override
    public void remove(String auth_req_id) {
        intervalCache.remove(auth_req_id);
    }

    @Override
    public Object get(String auth_req_id) {
        return intervalCache.get(auth_req_id);
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
