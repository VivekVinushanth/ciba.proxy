package jdbc;

import errorfiles.InternalServerError;
import handlers.Handlers;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.logging.Logger;

public class IssuedTimeDB implements ProxyJdbc{
    private static final Logger LOGGER = Logger.getLogger(IssuedTimeDB.class.getName());

    private IssuedTimeDB() {

    }

    private static IssuedTimeDB issuedTimeDBInstance = new IssuedTimeDB();

    public static IssuedTimeDB getInstance() {

        // TODO: 8/27/19 need to change this to database
        if (issuedTimeDBInstance == null) {

            synchronized (IssuedTimeDB.class) {

                if (issuedTimeDBInstance == null) {

                    /* instance will be created at request time */
                    issuedTimeDBInstance = new IssuedTimeDB();
                }
            }
        }
        return issuedTimeDBInstance;


    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();

    @Override
    public void add(String auth_req_id, Object issuedTime) {

        try {
            if (DbQuery.getInstance().addIssuedTime(auth_req_id,issuedTime)){
                LOGGER.info("Issued Time of the response added to store.");
            }else{
                try {
                    throw new InternalServerError("Error Adding the IssuedTime of Response");
                } catch (InternalServerError internalServerError) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void remove(String auth_req_id) {
        if (DbQuery.getInstance().deleteIssuedTime(auth_req_id)){

        }else{
            try {
                throw new InternalServerError("Error deleting Issued Time of Response");
            } catch (InternalServerError internalServerError) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
            }
        }

    }



    @Override
    public Object get(String auth_req_id) {
        return DbQuery.getInstance().getIssuedTime(auth_req_id);
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
