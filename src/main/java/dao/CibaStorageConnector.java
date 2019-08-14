package dao;

import cache.ClientStore;
import cache.UserStore;
import errorfiles.BadRequest;
import errorfiles.InternalServerError;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.Client;
import transactionartifacts.User;

import java.net.HttpRetryException;

public class CibaStorageConnector implements StoreConnector {

    private static CibaStorageConnector cibaStorageConnectorInstance = new CibaStorageConnector();

    public static CibaStorageConnector getInstance() {
        if (cibaStorageConnectorInstance == null) {

            synchronized (CibaStorageConnector.class) {

                if (cibaStorageConnectorInstance == null) {

                    /* instance will be created at request time */
                    cibaStorageConnectorInstance = new CibaStorageConnector();
                }
            }
        }
        return cibaStorageConnectorInstance;


    }


    @Override
    public void addClient(String clientid, Object client) {
        if (client instanceof Client){
            ClientStore.getInstance().add(clientid,client);
        }
    }

    @Override
    public void addUser(String userid, Object user) {
        if (user instanceof User){
            UserStore.getInstance().add(userid,user);
        }

    }

    @Override
    public void removeClient(String clientid) {
        if(ClientStore.getInstance().get(clientid) != null){
            ClientStore.getInstance().remove(clientid);
        }

    }

    @Override
    public void removeUser(String userid) {
        if(UserStore.getInstance().get(userid) != null){
            UserStore.getInstance().remove(userid);
        }

    }

    @Override
    public Client getClient(String clientid) {
        try {
            if (ClientStore.getInstance().get(clientid) == null) {
                throw new BadRequest("Unexpected client.");

            } else {
                return (Client) ClientStore.getInstance().get(clientid);
            }
        } catch (BadRequest badrequest) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badrequest.getMessage());
        }
    }
        @Override
        public User getUser(String userid) {
            try {
                if (ClientStore.getInstance().get(userid) == null) {
                    throw new BadRequest("Unexpected user.");

                }
                else{
                    return (User) UserStore.getInstance().get(userid);
                }
            }
                catch (BadRequest badrequest) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badrequest.getMessage());
                }

            }
}
