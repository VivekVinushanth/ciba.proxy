package cache;

import ciba.proxy.server.servicelayer.ServerRequestHandler;
import errorfiles.InternalServerError;
import handlers.Handlers;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.CIBAauthRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class AuthRequestCache implements ProxyCache {
    private static final Logger LOGGER = Logger.getLogger(AuthRequestCache.class.getName());

    private AuthRequestCache() {

    }

    private static AuthRequestCache authRequestCacheInstance = new AuthRequestCache();

    public static AuthRequestCache getInstance() {
        if (authRequestCacheInstance == null) {

            synchronized (AuthRequestCache.class) {

                if (authRequestCacheInstance == null) {

                    /* instance will be created at request time */
                    authRequestCacheInstance = new AuthRequestCache();
                }
            }
        }
        return authRequestCacheInstance;


    }
    private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();

    private HashMap<String, Object> authRequestCache = new HashMap<String , Object>();

    @Override
    public void add(String auth_req_id, Object authrequest) {
        if (authrequest instanceof CIBAauthRequest) {
         LOGGER.info("CIBA Authentication added to store");
            authRequestCache.put(auth_req_id, authrequest);


            try {
                //wait(3000);
                if (!interestedparty.isEmpty()) {
                    for (Handlers handler : interestedparty) {
                        if (handler instanceof ServerRequestHandler) {
                            ServerRequestHandler serverRequestHandler = (ServerRequestHandler) handler;

                            serverRequestHandler.receive((CIBAauthRequest) authrequest, auth_req_id);

                        }
                    }
                }else {
                        throw new InternalServerError("No handlers attached");
                    }

            } catch (InternalServerError internalServerError) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
        }}
    }

    @Override
    public void remove(String auth_req_idey) {
        authRequestCache.remove(auth_req_idey);
    }

    @Override
    public Object get(String auth_req_id) {
        return authRequestCache.get(auth_req_id);
    }

    @Override
    public void clear() {
        //To be implemented if needed

    }

    @Override
    public long size() {
       return authRequestCache.size();
    }

    @Override
    public void register(Object object) {
        interestedparty.add((Handlers) object);

    }



}
