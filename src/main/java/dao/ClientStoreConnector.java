package dao;

import transactionartifacts.Client;

public interface ClientStoreConnector {
    void addClient(String clientid, Object client);
    void removeClient(String clientid);
    Client getClient(String clientid);
}
