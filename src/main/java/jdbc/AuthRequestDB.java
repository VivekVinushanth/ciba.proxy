package jdbc;

import ciba.proxy.server.servicelayer.ServerRequestHandler;
import errorfiles.InternalServerError;
import handlers.Handlers;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.CIBAauthRequest;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AuthRequestDB implements ProxyJdbc {
    private static final Logger LOGGER = Logger.getLogger(AuthRequestDB.class.getName());

    private AuthRequestDB() {

    }

    private static AuthRequestDB authRequestDBInstance = new AuthRequestDB();

    public static AuthRequestDB getInstance() {

        // TODO: 8/27/19 need to change this to database
        if (authRequestDBInstance == null) {

            synchronized (AuthRequestDB.class) {

                if (authRequestDBInstance == null) {

                    /* instance will be created at request time */
                    authRequestDBInstance = new AuthRequestDB();
                }
            }
        }
        return authRequestDBInstance;


    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();

    @Override
    public void add(String auth_req_id, Object authrequest ) {
        if (authrequest instanceof CIBAauthRequest) {

            try {
               if( DbQuery.getInstance().addAuthRequest(auth_req_id,authrequest)) {
                   LOGGER.info("CIBA Authentication added to store");
               }else {
                   throw new InternalServerError("Error Adding Authentication Request to the store");
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
        if(DbQuery.getInstance().deleteAuthRequest(auth_req_id)){
            LOGGER.info("Requested Authentication request is been deleted.");
        }
        else{
            try {
                throw new InternalServerError("Error Deleting Authentication Request");
            } catch (InternalServerError internalServerError) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
            }
        }

    }

    @Override
    public Object get(String auth_req_id) {
        return DbQuery.getInstance().getAuthRequest(auth_req_id);
    }

    @Override
    public void clear() {

    }

    @Override
    public long size() {
        return 0;
        // TODO: 8/27/19 implement getting size of the store-may be later.
    }

    @Override
    public void register(Object object) {
        interestedparty.add((Handlers) object);
    }
}
