package handlers;

import ciba.proxy.server.servicelayer.ServerResponseHandler;
import configuration.ConfigurationFile;
import dao.ArtifactStoreConnectors;
import dao.DaoFactory;
import transactionartifacts.Artifacts;
import transactionartifacts.PollingAtrribute;

public class NotificationHandler implements Handlers {

 ArtifactStoreConnectors artifactStoreConnectors= DaoFactory.getInstance().getArtifactStoreConnector(ConfigurationFile.
         getInstance().getSTORE_CONNECTOR_TYPE());
    private NotificationHandler() {

    }

    private static NotificationHandler NotificationHandlerInstance = new NotificationHandler();

    public static NotificationHandler getInstance() {
        if (NotificationHandlerInstance == null) {

            synchronized (NotificationHandler.class) {

                if (NotificationHandlerInstance == null) {

                    /* instance will be created at request time */
                    NotificationHandlerInstance = new NotificationHandler();
                }
            }
        }
        return NotificationHandlerInstance;


    }





    public void sendNotificationtoClient(String auth_req_id) {

        if (setNotificationFlag(auth_req_id)){
            // TODO: 8/31/19 Actually need to send a notification to client end.but setting id fine.
        }
    }


    private Boolean setNotificationFlag(String auth_req_id){
       PollingAtrribute pollingAtrribute1 = artifactStoreConnectors.getPollingAttribute(auth_req_id);
       artifactStoreConnectors.removePollingAttribute(auth_req_id);
       pollingAtrribute1.setNotificationIssued(true);

       artifactStoreConnectors.addPollingAttribute(auth_req_id,pollingAtrribute1);
       return true;
    }




}
