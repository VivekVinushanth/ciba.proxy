package dao;

import transactionartifacts.Client;

public class CibaClientStoreJdbcConnector implements ClientStoreConnector {

    private static CibaClientStoreJdbcConnector CibaClientStoreJdbcConnectorInstance =
            new CibaClientStoreJdbcConnector();

    public static CibaClientStoreJdbcConnector getInstance() {

        if (CibaClientStoreJdbcConnectorInstance == null) {

            synchronized (CibaClientStoreJdbcConnector.class) {

                if (CibaClientStoreJdbcConnectorInstance == null) {

                    /* instance will be created at request time */
                    CibaClientStoreJdbcConnectorInstance = new CibaClientStoreJdbcConnector();
                }
            }
        }
        return CibaClientStoreJdbcConnectorInstance;
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
