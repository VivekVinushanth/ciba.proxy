package cache;


import handlers.Handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * This class is responsible to store latest time of polling.
 * */
public class LastPollCache implements ProxyCache {
    private static final Logger LOGGER = Logger.getLogger(LastPollCache.class.getName());
    private LastPollCache() {

    }

    private static LastPollCache lastPollCacheInstance = new LastPollCache();

    public static LastPollCache getInstance() {
        if (lastPollCacheInstance == null) {

            synchronized (LastPollCache.class) {

                if (lastPollCacheInstance == null) {

                    /* instance will be created at request time */
                    lastPollCacheInstance = new LastPollCache();
                }
            }
        }
        return lastPollCacheInstance;
    }

    HashMap<String, Object > lastPollCache = new HashMap<String , Object>();
    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();


    @Override
    public void add(String auth_req_id, Object timestamp) {
            lastPollCache.put(auth_req_id, timestamp);
            LOGGER.info(auth_req_id+ ": Last Polltime added");
        }


    @Override
    public void remove(String auth_req_id) {
        lastPollCache.remove(auth_req_id);
    }

    @Override
    public Object get(String auth_req_id) {
        return lastPollCache.get(auth_req_id);
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

