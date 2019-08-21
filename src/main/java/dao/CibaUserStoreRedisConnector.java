package dao;

import transactionartifacts.User;

public class CibaUserStoreRedisConnector implements UserStoreConnector{


    private static CibaUserStoreRedisConnector cibaUserStoreRedisConnectorInstance = new CibaUserStoreRedisConnector();

    public static CibaUserStoreRedisConnector getInstance() {
        if (cibaUserStoreRedisConnectorInstance == null) {

            synchronized (CibaUserStoreRedisConnector.class) {

                if (cibaUserStoreRedisConnectorInstance == null) {

                    /* instance will be created at request time */
                    cibaUserStoreRedisConnectorInstance = new CibaUserStoreRedisConnector();
                }
            }
        }
        return cibaUserStoreRedisConnectorInstance;


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
