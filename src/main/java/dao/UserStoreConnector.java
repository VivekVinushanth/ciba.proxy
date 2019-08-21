package dao;

import transactionartifacts.User;

public interface UserStoreConnector {
    void addUser(String userid,Object user);
    void removeUser(String userid);
    User getUser(String userid);
}
