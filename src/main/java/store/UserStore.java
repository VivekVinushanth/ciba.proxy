package store;

import cache.IntervalCache;
import transactionartifacts.User;

import java.util.HashMap;
import java.util.logging.Logger;

public class UserStore implements ProxyStore {
    private static final Logger LOGGER = Logger.getLogger(IntervalCache.class.getName());

    private UserStore() {

    }

    private static UserStore userStoreInstance = new UserStore();

    public static UserStore getInstance() {
        if (userStoreInstance == null) {

            synchronized (UserStore.class) {

                if (userStoreInstance == null) {

                    /* instance will be created at request time */
                    userStoreInstance = new UserStore();
                }
            }
        }
        return userStoreInstance;
    }

    private HashMap<String, Object> userstore = new HashMap<String , Object>();
    @Override
    public void add(String userid, Object user) {
        if (user instanceof User) {
            userstore.put(userid, user);
            LOGGER.info("User added to the store");
        }
    }
    @Override
    public void remove(String userid) {
        userstore.remove(userid);
    }

    @Override
    public Object get(String userid) {
        return  userstore.get(userid);
    }

    @Override
    public void clear() {

    }

    @Override
    public long size() {
        return userstore.size();
    }
}
