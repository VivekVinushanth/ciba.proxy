package validator;

import ciba.proxy.server.servicelayer.ServerResponseHandler;
import com.nimbusds.jose.Payload;

public class RegistrationValidator {

    private RegistrationValidator() {

    }

    private static RegistrationValidator registrationValidatorInstance = new RegistrationValidator();

    public static RegistrationValidator getInstance() {
        if (registrationValidatorInstance == null) {

            synchronized (RegistrationValidator.class) {

                if (registrationValidatorInstance == null) {

                    /* instance will be created at request time */
                    registrationValidatorInstance = new RegistrationValidator();
                }
            }
        }
        return registrationValidatorInstance;


    }

    public Boolean validate(String appname, String password, String mode) {

        return true;
        // TODO: 8/12/19 have to validate the client request for duplicates. 

    }
}
