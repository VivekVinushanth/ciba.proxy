package authorizationserver;
import ciba.proxy.server.servicelayer.ServerRequestHandler;
import ciba.proxy.server.servicelayer.ServerResponseHandler;
import ciba.proxy.server.servicelayer.ServerUserRegistrationHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWTClaimsSet;
import configuration.ConfigHandler;
import dao.DaoFactory;
import errorfiles.InternalServerError;
import handlers.*;
import com.nimbusds.jose.Payload;
import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * This class is the actual implementation of CIBA proxy server.
 */
@RestController
public class CIBAProxyServer implements AuthorizationServer {

    /** List of Observers.
     * Interested in the incoming requests
     */


    private ArrayList<Handlers> handlers = new ArrayList<Handlers> ();
    private static final Logger LOGGER = Logger.getLogger(CIBAProxyServer.class.getName());
    
    
    private final Object mutex = new Object(); //to serve as a mutex lock in synchronization


    public CIBAProxyServer() {

       /**
        * Registering CIBA auth request handle, token request handler to handlers arraylist of ciba proxy.
        * Adding serverrequest handler to observers of cache
       */
        CIBAAuthRequestHandler cibaauthrequesthandler = CIBAAuthRequestHandler.getInstance();
        this.register(cibaauthrequesthandler); //registering to Proxy server and to observe on auth requests coming

        TokenRequestHandler tokenrequesthandler = TokenRequestHandler.getInstance();
        this.register(tokenrequesthandler); //registering to Proxy server and to observe on token requests coming

        RegisterHandler registerHandler = RegisterHandler.getInstance();
        this.register(registerHandler); //registering to Proxy server and to observe on client app registration requests coming

        UserRegisterHandler userRegisterHandler = UserRegisterHandler.getInstance();
        this.register(userRegisterHandler); //registering to Proxy server and to observe on user registration requests coming

        ServerResponseHandler serverResponseHandler = ServerResponseHandler.getInstance();
        this.register(serverResponseHandler); //registering to Proxy server and to observe on grant codes coming

        ServerRequestHandler serverRequestHandler= ServerRequestHandler.getInstance();
                serverRequestHandler.registerto(); //registering to Authentication request store



        LOGGER.config("Authentication Request Handler & Token Request Handler Added");


        ConfigHandler.getInstance().setConfiguration("PayHere");
        LOGGER.config("Configuring Proxy for the Client of PayHere Application ");

    }


    /**
     * Endpoint where authentication request hits and then proceeded.
    */
    @RequestMapping(value = "/CIBAEndPoint",method = RequestMethod.POST)
    public String acceptAuthRequest(@RequestParam(defaultValue = "" , value = "request") String request) {

       /**
        * Considering that the request is always  signed.
        * */
       LOGGER.info("CIBA Authentication request hits the CIBA Auth Request Endpoint.");

        try {
        if (!handlers.isEmpty()) {
            for (Handlers handler : handlers) {
                if (handler instanceof CIBAAuthRequestHandler && !request.equals("")) {
                    return notifyHandler(handler, request);
                }
            }
            }

            LOGGER.warning("No handlers to listen the request.");
            throw new InternalServerError("No EndPoints to listen.");

        } catch (InternalServerError internalServerError) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
        }

    }


    /**
     *Endpoint where token request hits and then proceeded.
    */
    @RequestMapping("/TokenEndPoint")
    public String acceptTokenRequest(@RequestParam(defaultValue = "" , value = "auth_req_id") String auth_req_id,
                                      @RequestParam(defaultValue = "" , value = "grant_type") String grantType) {

        LOGGER.info("CIBA Token request hits the CIBA Token Request Endpoint.");

        try {
        if (!handlers.isEmpty()) {
            for (Handlers handler : handlers) {
                if (handler instanceof TokenRequestHandler) {

                    String result = this.notifyHandler(handler, auth_req_id , grantType).toString();
                    return result;

                }
            }
        }

            LOGGER.warning("No Token request handlers added to the system.");
            throw  new InternalServerError("No handlers registered");
        } catch (InternalServerError internalServerError) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
        }

    }

    /**
     *Endpoint where token request hits and then proceeded.
     */
    @RequestMapping("/RegistrationEndPoint")
    public String acceptRegistrationRequest(@RequestParam(defaultValue = "" , value = "name") String name,
                                     @RequestParam(defaultValue = "" , value = "password") String password,
                                     @RequestParam(defaultValue = "" , value = "mode") String mode) {

        LOGGER.info("CIBA Client App registration request hits the CIBA Registration Endpoint.");

        try {
            if (!handlers.isEmpty()) {
                for (Handlers handler : handlers) {
                    if (handler instanceof RegisterHandler) {

                        String result = this.notifyHandler(handler, name, password, mode).toString();
                        return result;

                    }
                }
            }

            LOGGER.warning("No Token request handlers added to the system.");
            throw new InternalServerError("No handlers registered");
        } catch (InternalServerError internalServerError) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
        }

    }

    /**
     *Endpoint where token request hits and then proceeded.
     */
    @RequestMapping("/UserRegistrationEndPoint")
    public String acceptUserRegistration(@RequestBody JSONObject user, @RequestHeader HttpHeaders headersRequest) {


       // TODO: 8/13/19 consider the json tree-if possible- that would be greater

        LOGGER.info("CIBA User registration request hits the CIBA User Registration Endpoint.");

        try {
            if (!handlers.isEmpty()) {
                for (Handlers handler : handlers) {
                    if (handler instanceof UserRegisterHandler) {

                        String result = this.notifyHandler(handler, user, headersRequest);
                        return result;

                    }
                }
            }

            LOGGER.warning("No User Registration handlers added to the system.");
            throw  new InternalServerError("No handlers registered");
        } catch (InternalServerError internalServerError) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
        }

    }

    /**
     *Endpoint which serves as Callbackurl.
     */
    @RequestMapping("/CallBackEndpoint")
    public void acceptUserRegistration(@RequestParam(defaultValue = "" , value = "code") String code,
                                       @RequestParam(defaultValue = "" , value = "session_state") String session_state,
                                       @RequestParam(defaultValue = "" , value = "state") String state)
    {

        // TODO: 8/15/19 since both code and tokens are received here right the logic for it.

        LOGGER.info("Grant code is being received at this Callback Endpoint.");

        try {
            if (!handlers.isEmpty()) {

                for (Handlers handler : handlers) {
                    if (handler instanceof ServerResponseHandler) {
                       // if (response.get("code").toString() != null && response.get("session_state") != null) {
                        if(! session_state.isEmpty() && !code.isEmpty() ){
                            System.out.println("Hnadlers not empty");
                            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                                    .claim("code", code)
                                    .claim("session_state", session_state)
                                    .claim("state",state)
                                    .build();


                            JSONObject response = claims.toJSONObject();
                            //System.out.println("here - session"+String.valueOf(response.get("session_state")));
                            notifyCodeHandler(handler, response, state);


                        }/* else if (response.get("access_token").toString() != null && response.get("id_token") != null) {
                            notifyTokenHandler(handler, response, state);

                        }*/ else{
                            throw new InternalServerError("Parameters missing");
                        }
                    }
                }

                }
            LOGGER.warning("No Token request handlers added to the system.");
                throw new InternalServerError("No handlers registered");

            } catch(InternalServerError internalServerError){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());


        }
    }




    private void notifyCodeHandler(Handlers handler, JSONObject response, String identifier) {

        try {
            if (handler instanceof ServerResponseHandler) {

                ServerResponseHandler serverResponseHandler = (ServerResponseHandler) handler;


                    LOGGER.info("Server Request Handler is notified about reception of grant code");
                    serverResponseHandler.receivecode(response,identifier);


            } else {
                throw new InternalServerError("No CallBack handlers found");
            }
        } catch (InternalServerError internalServerError) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
        }
    }

    private void notifyTokenHandler(Handlers handler, JSONObject response, String identifier) {

        try {
            if (handler instanceof ServerResponseHandler) {

                ServerResponseHandler serverResponseHandler = (ServerResponseHandler) handler;


                LOGGER.info("Server Response Handler is notified about reception of token");
                serverResponseHandler.receivetoken(response,identifier);


            } else {
                throw new InternalServerError("No CallBack handlers found");
            }
        } catch (InternalServerError internalServerError) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
        }
    }





    /**
     *  Add interested observers.
    */

    public void register(Handlers handler) {
        if (handler == null) {
            throw new NullPointerException("Null Handlers");
        }
        synchronized (mutex) {
            if (!handlers.contains(handler)) {
                handlers.add(handler);
            }
        }
    }



    /**
     *Remove uninterested observers.

     */

    public void deRegister(Handlers handler) {

            synchronized (mutex) {
                handlers.remove(handler);
            }
        }




    /**
     * Different notifying methods to notify specific handler.
    */
    private String notifyHandler(Handlers handler, String params) {
        try {
        if (handler instanceof CIBAAuthRequestHandler) {

            CIBAAuthRequestHandler cibaauthrequesthandler = (CIBAAuthRequestHandler) handler;
            LOGGER.info("Authentication request handler notified.");

            return cibaauthrequesthandler.receive(params);

        }
            throw new InternalServerError("No Authentication request handlers found");

        } catch (InternalServerError internalServerError) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
        }

    }

    private Payload notifyHandler(Handlers handler, String authReqid, String grantType) {


        try {
            if (handler instanceof TokenRequestHandler) {

            TokenRequestHandler tokenrequesthandler = (TokenRequestHandler) handler;
                LOGGER.info("Token request handler notified.");
            return tokenrequesthandler.receive(authReqid, grantType);

        } else {
                throw new InternalServerError("No Authentication request handlers found");
            }
        } catch (InternalServerError internalServerError) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
        }
    }

    private Payload notifyHandler(Handlers handler, String name, String password, String mode) {
        try {
            if (handler instanceof RegisterHandler) {

                RegisterHandler registerHandler = (RegisterHandler) handler;
                LOGGER.info("Client Registration handler notified.");

                return registerHandler.receive(name,password,mode);

            }
            throw new InternalServerError("No Authentication request handlers found");

        } catch (InternalServerError internalServerError) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
        }

    }

    private String notifyHandler(Handlers handler, JSONObject user, HttpHeaders httpHeaders) {


        try {
            if (handler instanceof UserRegisterHandler) {

                UserRegisterHandler userRegisterHandler = (UserRegisterHandler) handler;
                LOGGER.info("User registration handler notified.");

                return userRegisterHandler.receive(user,httpHeaders);

            }
            throw new InternalServerError("No Authentication request handlers found");

        } catch (InternalServerError internalServerError) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
        }

    }



    // TODO: 8/5/19 public void communicateToAuthDevice(){}




}

