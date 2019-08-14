package cache;

import transactionartifacts.Client;

public class CibaProxyStore {
private ClientStore clientStore;
private UserStore userStore;

    private CibaProxyStore (){
       clientStore =ClientStore.getInstance();
       userStore = UserStore.getInstance();
    }
    private static CibaProxyStore cibaProxyStoreInstance = new CibaProxyStore();

    public static CibaProxyStore getInstance() {
        if (cibaProxyStoreInstance == null) {

            synchronized (CibaProxyStore.class) {

                if (cibaProxyStoreInstance == null) {

                    /* instance will be created at request time */
                    cibaProxyStoreInstance = new CibaProxyStore();
                }
            }
        }
        return cibaProxyStoreInstance;


    }

    public ClientStore clientStore() {
        return clientStore;
    }
    public UserStore userStore() {
        return userStore;
    }
}
