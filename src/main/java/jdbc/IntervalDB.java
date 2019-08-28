package jdbc;

import errorfiles.InternalServerError;
import handlers.Handlers;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.logging.Logger;

public class IntervalDB implements ProxyJdbc{
    private static final Logger LOGGER = Logger.getLogger(IntervalDB.class.getName());

    private IntervalDB() {

    }

    private static IntervalDB intervalDBInstance = new IntervalDB();

    public static IntervalDB getInstance() {

        // TODO: 8/27/19 need to change this to database
        if (intervalDBInstance == null) {

            synchronized (IntervalDB.class) {

                if (intervalDBInstance == null) {

                    /* instance will be created at request time */
                    intervalDBInstance = new IntervalDB();
                }
            }
        }
        return intervalDBInstance;


    }

    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();

    @Override
    public void add(String auth_req_id, Object interval) {

        try {
            if (DbQuery.getInstance().addInterval(auth_req_id,interval)){
                LOGGER.info("Interval of polling  added to store.");
            }else{
                try {
                    throw new InternalServerError("Error Adding Interval of Polling");
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
        if (DbQuery.getInstance().deleteInterval(auth_req_id)){

        }else{
            try {
                throw new InternalServerError("Error deleting Inteval of Polling");
            } catch (InternalServerError internalServerError) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
            }
        }

    }



    @Override
    public Object get(String auth_req_id) {
        return DbQuery.getInstance().getInterval(auth_req_id);
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

