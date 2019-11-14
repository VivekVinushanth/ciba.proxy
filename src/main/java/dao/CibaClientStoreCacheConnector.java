package dao;

import exceptions.BadRequestException;
import store.ClientStore;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.Client;

public class CibaClientStoreCacheConnector implements ClientStoreConnector {

    ClientStore clientStore = ClientStore.getInstance();

    private static CibaClientStoreCacheConnector cibaClientStoreCacheConnectorInstance =
            new CibaClientStoreCacheConnector();

    public static CibaClientStoreCacheConnector getInstance() {

        if (cibaClientStoreCacheConnectorInstance == null) {

            synchronized (CibaClientStoreCacheConnector.class) {

                if (cibaClientStoreCacheConnectorInstance == null) {

                    /* instance will be created at request time */
                    cibaClientStoreCacheConnectorInstance = new CibaClientStoreCacheConnector();
                }
            }
        }
        return cibaClientStoreCacheConnectorInstance;

    }

    @Override
    public void addClient(String clientid, Object client) {

        if (client instanceof Client) {
            clientStore.add(clientid, client);
        }
    }

    @Override
    public void removeClient(String clientid) {

        if (clientStore.get(clientid) != null) {
            clientStore.remove(clientid);
        }

    }

    @Override
    public Client getClient(String clientid) {

        try {
            if (ClientStore.getInstance().get(clientid) == null) {
                throw new BadRequestException("Unexpected client.");

            } else {
                return (Client) ClientStore.getInstance().get(clientid);
            }
        } catch (BadRequestException badrequest) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badrequest.getMessage());
        }
    }
}
