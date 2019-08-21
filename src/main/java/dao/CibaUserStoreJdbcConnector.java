package dao;

import transactionartifacts.User;

public class CibaUserStoreJdbcConnector implements UserStoreConnector {


    private static CibaUserStoreJdbcConnector cibaUserStoreJdbcConnectorInstance = new CibaUserStoreJdbcConnector();

    public static CibaUserStoreJdbcConnector getInstance() {
        if (cibaUserStoreJdbcConnectorInstance == null) {

            synchronized (CibaUserStoreJdbcConnector.class) {

                if (cibaUserStoreJdbcConnectorInstance == null) {

                    /* instance will be created at request time */
                    cibaUserStoreJdbcConnectorInstance = new CibaUserStoreJdbcConnector();
                }
            }
        }
        return cibaUserStoreJdbcConnectorInstance;


    }

    @Override
    public void addUser(String userid, Object user) {

    }

    @Override
    public void removeUser(String userid) {

    }

    @Override
    public User getUser(String userid) {
        return null;
    }
}
