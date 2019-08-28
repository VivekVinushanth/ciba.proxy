package jdbc;

import errorfiles.InternalServerError;
import handlers.Handlers;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.CIBAauthResponse;

import java.util.ArrayList;
import java.util.logging.Logger;

public class AuthResponseDB  implements ProxyJdbc{
    private static final Logger LOGGER = Logger.getLogger(AuthResponseDB.class.getName());

    private AuthResponseDB() {

    }

    private static AuthResponseDB authResponseDBInstance = new AuthResponseDB();

    public static AuthResponseDB getInstance() {

        // TODO: 8/27/19 need to change this to database
        if (authResponseDBInstance == null) {

            synchronized (AuthResponseDB.class) {

                if (authResponseDBInstance == null) {

                    /* instance will be created at request time */
                    authResponseDBInstance = new AuthResponseDB();
                }
            }
        }
        return authResponseDBInstance;


    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();
    @Override
    public void add(String auth_req_id, Object authresponse) {
        if(authresponse instanceof CIBAauthResponse) {

            if (DbQuery.getInstance().addAuthResponse(auth_req_id,authresponse)){
                LOGGER.info("CIBA Auth response added to store.");
            }else{
                    try {
                        throw new InternalServerError("Error Adding Authentication Response");
                    } catch (InternalServerError internalServerError) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
                    }
                }

        }
    }

    @Override
    public void remove(String auth_req_id) {
        if (DbQuery.getInstance().deleteAuthResponse(auth_req_id)){

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
        return DbQuery.getInstance().getAuthResponse(auth_req_id);

    }

    @Override
    public void clear() {
        //To be implemented if needed
    }

    @Override
    public long size() {
        return 0;
        //To be implemented if needed
    }

    @Override
    public void register(Object object) {
        interestedparty.add((Handlers) object);
    }
}
