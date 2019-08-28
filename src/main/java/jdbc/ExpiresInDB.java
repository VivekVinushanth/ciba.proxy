package jdbc;

import errorfiles.InternalServerError;
import handlers.Handlers;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ExpiresInDB implements ProxyJdbc {
    private static final Logger LOGGER = Logger.getLogger(ExpiresInDB.class.getName());

    private ExpiresInDB() {

    }

    private static ExpiresInDB expiresInDBInstance = new ExpiresInDB();

    public static ExpiresInDB getInstance() {

        // TODO: 8/27/19 need to change this to database
        if (expiresInDBInstance == null) {

            synchronized (ExpiresInDB.class) {

                if (expiresInDBInstance == null) {

                    /* instance will be created at request time */
                    expiresInDBInstance = new ExpiresInDB();
                }
            }
        }
        return expiresInDBInstance;


    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();

    @Override
    public void add(String auth_req_id, Object expires_in) {

        try {
            if (DbQuery.getInstance().addExpiresIn(auth_req_id,expires_in)){
                LOGGER.info("Expiry Time  added to store.");
            }else{
                try {
                    throw new InternalServerError("Error Adding Expiry Time of  Response");
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
        if (DbQuery.getInstance().deleteExpiresIn(auth_req_id)){

        }else{
            try {
                throw new InternalServerError("Error deleting Authentication Response");
            } catch (InternalServerError internalServerError) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
            }
        }

    }



    @Override
    public Object get(String auth_req_id) {
        return DbQuery.getInstance().getExpiresIn(auth_req_id);

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

    }
}
