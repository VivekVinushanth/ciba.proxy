package ciba.proxy.server.servicelayer;

import cibaparameters.CIBAParameters;
import configuration.ConfigurationFile;
import dao.DaoFactory;
import errorfiles.BadRequest;
import errorfiles.InternalServerError;
import handlers.Handlers;
import handlers.NotificationHandler;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import transactionartifacts.TokenResponse;
import util.RestTemplateFactory;
import validator.TokenResponseValidator;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ServerResponseHandler implements Handlers {



    private static final Logger LOGGER = Logger.getLogger(ServerResponseHandler.class.getName());

    private ServerResponseHandler() {

    }

    private static ServerResponseHandler serverResponseHandlerInstance = new ServerResponseHandler();

    public static ServerResponseHandler getInstance() {
        if (serverResponseHandlerInstance == null) {

            synchronized (ServerResponseHandler.class) {

                if (serverResponseHandlerInstance == null) {

                    /* instance will be created at request time */
                    serverResponseHandlerInstance = new ServerResponseHandler();
                }
            }
        }
        return serverResponseHandlerInstance;
    }


    

   /* public  void InitiateFakeResponse(String identifier) {
        //for now create a fake token and send!


            //wait(3);
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccessToken("G5kXH2wHvUra0sHlDy1iTkDJgsgUO1bN");
            tokenResponse.setIdToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IjE2NzcyNiJ9.eyJpc3MiOiJo"
                    + "dHRwczovL3NlcnZlci5leGFtcGxlLmNvbSIsInN1YiI6IjI0ODI4OTc2MTAwMSIs"
                    + "ImF1ZCI6InM2QmhkUmtxdDMiLCJlbWFpbCI6ImphbmVkb2VAZXhhbXBsZS5jb20i"
                    + "LCJleHAiOjE1Mzc4MTk4MDMsImlhdCI6MTUzNzgxOTUwM30.aVq83mdy72ddIFVJ"
                    + "LjlNBX-5JHbjmwK-Sn9Mir-blesfYMceIOw6u4GOrO_ZroDnnbJXNKWAg_dxVynv"
                    +"MHnk3uJc46feaRIL4zfHf6Anbf5_TbgMaVO8iczD16A5gNjSD7yenT5fslrrW-NU"
                    +"vtmi0s1puoM4EmSaPXCR19vRJyWuStJiRHK5yc3BtBlQ2xwxH1iNP49rGAQe_LH"
                    +"fW1G74NY5DaPv-V23JXDNEIUTY-jT-NbbtNHAxnhNPyn8kcO2WOoeIwANO9BfLF1"
                    +"EFWtjGPPMj6kDVrikec47yK86HArGvsIIwk1uExynJIv_tgZGE0eZI7MtVb2UlCw"
                    +"DQrVlg");

            tokenResponse.setRefreshToken("4bwc0ESC_IAhflf-ACC_vjD_ltc11ne-8gFPfA2Kx16");
            tokenResponse.setTokenExpirein(3600);
            tokenResponse.setTokenType("Bearer");

            addtoStore(tokenResponse,identifier);

        }*/

    public void getToken(String code, String idenitifier) {
        try {
            RestTemplate restTemplate = RestTemplateFactory.getInstance().getRestTemplate();

            //headers set
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(ConfigurationFile.getInstance().getCLIENT_ID(),ConfigurationFile.getInstance().getCLIENT_SECRET());
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

               /* JWTClaimsSet claims = new JWTClaimsSet.Builder()
                        .claim("grant_type", "authorization_code")
                        .claim("code", code)
                        .claim("redirect_uri", CIBAParameters.getInstance().getCallBackURL())
                        .build();

                String responseString = claims.toJSONObject().toString();

*/

            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("grant_type", "authorization_code");
            map.add("code", code);
            map.add("redirect_uri", CIBAParameters.getInstance().getCallBackURL());
            map.add("state", idenitifier);


            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            String token = restTemplate.postForObject("https://localhost:9443/oauth2/token", request, String.class);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(token);
            receivetoken(json, idenitifier);


        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


/*
    public void register(){
        DaoFactory.getInstance().getArtifactStoreConnector("InMemoryCache").registerToAuthRequestObservers(this);
    }*/

    public TokenResponse validate(JSONObject token) {
        return TokenResponseValidator.getInstance().validateTokens(token);
    }

    public void addtoStore(TokenResponse tokenResponse, String identifier) {

        DaoFactory.getInstance().getArtifactStoreConnector(ConfigurationFile.getInstance().getSTORE_CONNECTOR_TYPE()).
                addTokenResponse(ServerRequestHandler.getInstance().getAuthReqId(identifier), tokenResponse);

        LOGGER.info("Token Response Received and added to Store.");
        notify(ServerRequestHandler.getInstance().getAuthReqId(identifier));

    }


    public void receivetoken(JSONObject token, String identifier) {
        if (validate(token) != null) {

            addtoStore(validate(token), identifier);
            LOGGER.info("Token Response added to store.");
        }
    }


    public void receivecode(JSONObject codeobject, String identifier) {
        String code = codeobject.get("code").toString();

        this.getToken(code, identifier);
    }



    private void notify(String auth_req_id) {
      NotificationHandler.getInstance().sendNotificationtoClient(auth_req_id);
    }
}
