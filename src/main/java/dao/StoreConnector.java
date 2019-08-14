package dao;

import transactionartifacts.Client;
import transactionartifacts.User;

public interface StoreConnector {
    void addClient(String clientid, Object client);
    void addUser(String userid,Object user);

    void removeClient(String clientid);
    void removeUser(String userid);

    Client getClient(String clientid);
    User getUser(String userid);
}
