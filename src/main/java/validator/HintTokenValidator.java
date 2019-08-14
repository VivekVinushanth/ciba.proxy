package validator;


import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class HintTokenValidator {
    private  HintTokenValidator() {

    }

    private static HintTokenValidator hintTokenValidatorInstance = new HintTokenValidator();

    public static HintTokenValidator getInstance() {
        if (hintTokenValidatorInstance == null) {

            synchronized (HintTokenValidator.class) {

                if (hintTokenValidatorInstance == null) {

                    /* instance will be created at request time */
                    hintTokenValidatorInstance = new HintTokenValidator();
                }
            }
        }
        return hintTokenValidatorInstance;


    }

    public boolean validateLoginHintToken(JSONObject loginHintToken) {
        // typecasting obj to JSONObject
        JSONObject login_hint_token = (JSONObject) loginHintToken;
        // TODO: 8/4/19 Implement this validation.
        return true;
    }


    public boolean validateIDTokenHint(JSONObject idTokenHint) {
        // TODO: 8/4/19 Implement this validation.
        return true;
    }
}
