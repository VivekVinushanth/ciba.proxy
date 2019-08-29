package cache;

import handlers.Handlers;
import transactionartifacts.PollingAtrribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class PollingAttributeCache implements ProxyCache {


    private static final Logger LOGGER = Logger.getLogger(PollingAttributeCache.class.getName());

    private PollingAttributeCache() {

    }

    private static PollingAttributeCache PollingAttributeCacheInstance = new PollingAttributeCache();

    public static PollingAttributeCache getInstance() {
        if (PollingAttributeCacheInstance == null) {

            synchronized (PollingAttributeCache.class) {

                if (PollingAttributeCacheInstance == null) {

                    /* instance will be created at request time */
                    PollingAttributeCacheInstance = new PollingAttributeCache();
                }
            }
        }
        return PollingAttributeCacheInstance;


    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();

    private HashMap<String, Object> PollingAttributeCache = new HashMap<String , Object>();

    @Override
    public void add(String auth_req_id, Object pollingattribute) {
        if (pollingattribute instanceof PollingAtrribute) {

            LOGGER.info("PollingAttribute added to store");
            PollingAttributeCache.put(auth_req_id, pollingattribute);
        }
    }

    @Override
    public void remove(String auth_req_id) {
        PollingAttributeCache.remove(auth_req_id);

    }

    @Override
    public Object get(String auth_req_id) {
        return PollingAttributeCache.get(auth_req_id);
    }

    @Override
    public void clear() {
        //To be implemented if needed
    }

    @Override
    public long size() {
       return PollingAttributeCache.size();
    }

    @Override
    public void register(Object object) {
        interestedparty.add((Handlers) object);
    }
}
