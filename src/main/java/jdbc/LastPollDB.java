package jdbc;

import errorfiles.InternalServerError;
import handlers.Handlers;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.logging.Logger;

public class LastPollDB implements ProxyJdbc {
    private static final Logger LOGGER = Logger.getLogger(LastPollDB.class.getName());

    private LastPollDB() {

    }

    private static LastPollDB lastPollDBInstance = new LastPollDB();

    public static LastPollDB getInstance() {

        // TODO: 8/27/19 need to change this to database
        if (lastPollDBInstance == null) {

            synchronized (LastPollDB.class) {

                if (lastPollDBInstance == null) {

                    /* instance will be created at request time */
                    lastPollDBInstance = new LastPollDB();
                }
            }
        }
        return lastPollDBInstance;


    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();

    @Override
    public void add(String auth_req_id, Object lastPoll) {

        try {
            if (DbQuery.getInstance().addLastPoll(auth_req_id,lastPoll)){
                LOGGER.info("Last Poll of the Token Request added to store.");
            }else{
                try {
                    throw new InternalServerError("Error Adding the Last Poll of the Token Request");
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
        if (DbQuery.getInstance().deleteLastPoll(auth_req_id)){

        }else{
            try {
                throw new InternalServerError("Error deleting Last Poll of the Token Request");
            } catch (InternalServerError internalServerError) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
            }
        }

    }



    @Override
    public Object get(String auth_req_id) {
        return DbQuery.getInstance().getLastPoll(auth_req_id);
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
