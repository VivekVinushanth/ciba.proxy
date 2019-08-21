package dao;

import transactionartifacts.Client;

public class CibaClientStoreRedisConnector implements ClientStoreConnector {

    private static CibaClientStoreRedisConnector cibaClientStoreRedisConnectorInstance = new CibaClientStoreRedisConnector();

    public static CibaClientStoreRedisConnector getInstance() {
        if (cibaClientStoreRedisConnectorInstance == null) {

            synchronized (CibaClientStoreRedisConnector.class) {

                if (cibaClientStoreRedisConnectorInstance == null) {

                    /* instance will be created at request time */
                    cibaClientStoreRedisConnectorInstance = new CibaClientStoreRedisConnector();
                }
            }
        }
        return cibaClientStoreRedisConnectorInstance;


    }
    @Override
    public void addClient(String clientid, Object client) {

    }

    @Override
    public void removeClient(String clientid) {

    }

    @Override
    public Client getClient(String clientid) {
        return null;
    }
}
