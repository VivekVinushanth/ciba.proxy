package handlers;

import ciba.proxy.server.servicelayer.ServerUserRegistrationHandler;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWTClaimsSet;
import dao.DaoFactory;
import errorfiles.BadRequest;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.User;
import util.CodeGenerator;

import java.util.logging.Logger;

public class UserRegisterHandler implements Handlers {

    private static final Logger LOGGER = Logger.getLogger(UserRegisterHandler.class.getName());
    DaoFactory daoFactory = DaoFactory.getInstance();


    private UserRegisterHandler() {

    }

    private static UserRegisterHandler userRegisterHandlerInstance = new UserRegisterHandler();

    public static UserRegisterHandler getInstance() {
        if (userRegisterHandlerInstance == null) {

            synchronized (UserRegisterHandler.class) {

                if (userRegisterHandlerInstance == null) {

                    /* instance will be created at request time */
                    userRegisterHandlerInstance = new UserRegisterHandler();
                }
            }
        }
        return userRegisterHandlerInstance;


    }

    public void store(String id, User user){
        daoFactory.getStorageConnector().addUser(id,user);

        System.out.println(  "User " +daoFactory.getStorageConnector().getUser(id).getUserName());

    }


    public String receive(JSONObject userjson, HttpHeaders httpHeaders) {
        User user = new User();
        if (String.valueOf(userjson.get("appid")) == "null") {

        }
        else {
            user.setAppid(userjson.get("appid").toString());
        }

        if (String.valueOf(userjson.get("ClientID"))!="null"){
            user.setClientappid(userjson.get("ClientID").toString());
        }

        user.setUserName(userjson.get("userName").toString());
        user.addClaim("emails",userjson.getAsString("emails"));

        try {
            if (validate(userjson)) {

               createUserregistrationResponse(userjson,httpHeaders);
            } else {
                throw new BadRequest("Parameters missing");

            }
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        }
        return "";
    }

    private String createUserregistrationResponse(JSONObject user,HttpHeaders httpHeaders) {
        return registerInServer(user,httpHeaders);
    }

    public Boolean validate(JSONObject user){

        return true;
        // TODO: 8/13/19 implemented
    }


    public String registerInServer(JSONObject user,HttpHeaders httpHeaders){
        return (ServerUserRegistrationHandler.getInstance().save(user,httpHeaders));
    }
}
