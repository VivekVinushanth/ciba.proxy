package dao;

import exceptions.BadRequestException;
import store.ClientStore;
import store.UserStore;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.User;

public class CibaUserStoreCacheConnector implements UserStoreConnector {

    private static CibaUserStoreCacheConnector cibaStorageConnectorInstance = new CibaUserStoreCacheConnector();

    public static CibaUserStoreCacheConnector getInstance() {
        if (cibaStorageConnectorInstance == null) {

            synchronized (CibaUserStoreCacheConnector.class) {

                if (cibaStorageConnectorInstance == null) {

                    /* instance will be created at request time */
                    cibaStorageConnectorInstance = new CibaUserStoreCacheConnector();
                }
            }
        }
        return cibaStorageConnectorInstance;


    }



    @Override
    public void addUser(String userid, Object user) {
        if (user instanceof User){
            UserStore.getInstance().add(userid,user);
        }

    }



    @Override
    public void removeUser(String userid) {
        if(UserStore.getInstance().get(userid) != null){
            UserStore.getInstance().remove(userid);
        }

    }


        @Override
        public User getUser(String userid) {
            try {
                if (ClientStore.getInstance().get(userid) == null) {
                    throw new BadRequestException("Unexpected user.");

                }
                else{
                    return (User) UserStore.getInstance().get(userid);
                }
            }
                catch (BadRequestException badrequest) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badrequest.getMessage());
                }

            }
}
