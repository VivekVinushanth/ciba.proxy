package cache;

import transactionartifacts.Client;

import java.util.HashMap;
import java.util.logging.Logger;

public class ClientStore implements ProxyStore {

    private static final Logger LOGGER = Logger.getLogger(IntervalCache.class.getName());

    private ClientStore() {

    }

    private static ClientStore clientStoreInstance = new ClientStore();

    public static ClientStore getInstance() {
        if (clientStoreInstance == null) {

            synchronized (ClientStore.class) {

                if (clientStoreInstance == null) {

                    /* instance will be created at request time */
                    clientStoreInstance = new ClientStore();
                }
            }
        }
        return clientStoreInstance;
    }

    private HashMap<String, Object> clientstore = new HashMap<String , Object>();
    @Override
    public void add(String clientid, Object client) {
        if (client instanceof Client) {
            clientstore.put(clientid, client);
            LOGGER.info("User added to the store");
        }
    }
    @Override
    public void remove(String clientid) {
        clientstore.remove(clientid);
    }

    @Override
    public Object get(String clientid) {
        return  clientstore.get(clientid);
    }

    @Override
    public void clear() {

    }

    @Override
    public long size() {
        return clientstore.size();
    }
}
