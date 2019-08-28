package jdbc;

import errorfiles.InternalServerError;
import handlers.Handlers;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.TokenRequest;
import transactionartifacts.TokenResponse;

import java.util.ArrayList;
import java.util.logging.Logger;

public class TokenRequestDB implements ProxyJdbc {
    private static final Logger LOGGER = Logger.getLogger(TokenRequestDB.class.getName());

    private TokenRequestDB() {

    }

    private static TokenRequestDB tokenRequestDBInstance = new TokenRequestDB();

    public static TokenRequestDB getInstance() {

        // TODO: 8/27/19 need to change this to database
        if (tokenRequestDBInstance == null) {

            synchronized (TokenRequestDB.class) {

                if (tokenRequestDBInstance == null) {

                    /* instance will be created at request time */
                    tokenRequestDBInstance = new TokenRequestDB();
                }
            }
        }
        return tokenRequestDBInstance;


    }
    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();
    @Override
    public void add(String auth_req_id, Object tokenRequest) {
        if(tokenRequest instanceof TokenRequest) {

            if (DbQuery.getInstance().addTokenRequest(auth_req_id,tokenRequest)){
                LOGGER.info("Token Request added to store.");
            }else{
                try {
                    throw new InternalServerError("Error Adding Token Request");
                } catch (InternalServerError internalServerError) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
                }
            }

        }
    }

    @Override
    public void remove(String auth_req_id) {
        if (DbQuery.getInstance().deleteTokenRequest(auth_req_id)){

        }else{
            try {
                throw new InternalServerError("Error deleting Token Request");
            } catch (InternalServerError internalServerError) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
            }
        }

    }



    @Override
    public Object get(String auth_req_id) {
        return DbQuery.getInstance().getTokenRequest(auth_req_id);

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
