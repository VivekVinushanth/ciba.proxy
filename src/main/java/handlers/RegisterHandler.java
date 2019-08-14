package handlers;

import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWTClaimsSet;
import dao.DaoFactory;
import errorfiles.BadRequest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sun.awt.SunHints;
import transactionartifacts.Client;
import util.CodeGenerator;
import util.SecretKeyPairGenerator;
import validator.RegistrationValidator;

import java.security.*;
import java.util.logging.Logger;

/**
 * This class handles the registration of client and user to the store.
 * */
@ComponentScan("handlers")
@Configuration
public class RegisterHandler implements Handlers {
    private static final Logger LOGGER = Logger.getLogger(RegisterHandler.class.getName());
    DaoFactory daoFactory = DaoFactory.getInstance();


    private RegisterHandler() {

    }

    private static RegisterHandler registerHandlerInstance = new RegisterHandler();

    public static RegisterHandler getInstance() {
        if (registerHandlerInstance == null) {

            synchronized (RegisterHandler.class) {

                if (registerHandlerInstance == null) {

                    /* instance will be created at request time */
                    registerHandlerInstance = new RegisterHandler();
                }
            }
        }
        return registerHandlerInstance;


    }



    public Payload receive(String appname , String password, String mode) {
        LOGGER.info("Client Application registration request received.");
        return extractParameters(appname, password, mode);
    }

    private Payload extractParameters(String appname, String password, String mode) {
        if (!RegistrationValidator.getInstance().validate(appname,password,mode)){
            try {
                throw new BadRequest("Invalid Parameters");
            } catch (BadRequest badRequest) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badRequest.getMessage());
            }
        }
        return (this.createRegistrationResponse(appname,password,mode));

    }

    private Payload createRegistrationResponse(String appname,String password,String mode) {
        try {
        String client_id = CodeGenerator.getInstance().getRandomID();
            // TODO: 8/12/19 we dont need this.Is will taje care of this.
        String client_secret = new String (SecretKeyPairGenerator.getInstance().generatesecretkey().getPrivate().getEncoded());
        String public_key = new String (SecretKeyPairGenerator.getInstance().generatesecretkey().getPublic().getEncoded());

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .claim("client_id",client_id)
                .claim("client_secret", client_secret)
                .claim("public_key",public_key)
                .build();

        Payload payload = new Payload(claims.toJSONObject());
        //JWSObject response = new JWSObject(header, payload);

            Client client = new Client();
            client.setClientName(appname);
            client.setPublickey(public_key);
            client.setClientSecret(client_secret);
            client.setClientMode(mode);


            store(client_id,client);

        LOGGER.info("Registration response returned with client id and client secret.");
        return payload;

            // TODO: 8/12/19 Actually need to send the request to IS and get the response from it.

    } catch (NoSuchAlgorithmException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client key generation aborted.");
    }

    }

    private void store(String client_id, Client client) {

        DaoFactory.getInstance().getStorageConnector().addClient(client_id,client);
        System.out.println("Name of Client " +DaoFactory.getInstance().getStorageConnector().getClient(client_id).getClientName());
        LOGGER.info("Client store into the client store.");
    }


}
