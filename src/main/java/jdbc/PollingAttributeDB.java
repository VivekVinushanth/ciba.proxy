package jdbc;

import ciba.proxy.server.servicelayer.ServerRequestHandler;
import errorfiles.InternalServerError;
import handlers.Handlers;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.CIBAauthRequest;
import transactionartifacts.PollingAtrribute;

import java.util.ArrayList;
import java.util.logging.Logger;

public class PollingAttributeDB implements ProxyJdbc {

    private static final Logger LOGGER = Logger.getLogger(PollingAttributeDB.class.getName());

    private PollingAttributeDB() {

    }

    private static PollingAttributeDB PollingAttributeDBInstance = new PollingAttributeDB();

    public static PollingAttributeDB getInstance() {

        // TODO: 8/27/19 need to change this to database
        if (PollingAttributeDBInstance == null) {

            synchronized (PollingAttributeDB.class) {

                if (PollingAttributeDBInstance == null) {

                    /* instance will be created at request time */
                    PollingAttributeDBInstance = new PollingAttributeDB();
                }
            }
        }
        return PollingAttributeDBInstance;


    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();

    @Override
    public void add(String auth_req_id, Object pollingattribute) {
        if (pollingattribute instanceof PollingAtrribute) {

            try {
                if( DbQuery.getInstance().addPollingAttribute(auth_req_id,pollingattribute)) {
                    LOGGER.info("Polling Attribute added to store");


                }else {
                    throw new InternalServerError("Error Adding Polling Attribute to the store");
                }
            } catch (InternalServerError internalServerError) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void remove(String auth_req_id) {
        if( DbQuery.getInstance().deletePollingAttribute(auth_req_id)){
            LOGGER.info(" Polling Attribute is been deleted.");

        }
        else{
            try {
                throw new InternalServerError("Error Deleting Polling Attribute");
            } catch (InternalServerError internalServerError) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
            }
        }

    }


    @Override
    public Object get(String auth_req_id) {
        return DbQuery.getInstance().getPollingAttribute(auth_req_id);
    }



    @Override
    public void clear() {

    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public void register(Object object) {
        interestedparty.add((Handlers) object);
    }
}
