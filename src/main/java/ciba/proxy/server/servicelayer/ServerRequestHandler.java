package ciba.proxy.server.servicelayer;

import cibaparameters.CIBAParameters;
import com.nimbusds.jwt.SignedJWT;
import configuration.ConfigurationFile;
import errorfiles.BadRequest;
import handlers.Handlers;
import net.minidev.json.JSONObject;
import org.apache.http.client.HttpResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import tempErrorCache.TempErrorCache;
import transactionartifacts.CIBAauthRequest;
import util.CodeGenerator;
import util.RestTemplateFactory;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Logger;

public class ServerRequestHandler implements Handlers {
    private HashMap<String, String> identifierstore = new HashMap<String, String>();
    private static final Logger LOGGER = Logger.getLogger(ServerRequestHandler.class.getName());

    private ServerRequestHandler() {

    }

    private static ServerRequestHandler serverRequestHandlerInstance = new ServerRequestHandler();

    public static ServerRequestHandler getInstance() {
        if (serverRequestHandlerInstance == null) {

            synchronized (ServerRequestHandler.class) {

                if (serverRequestHandlerInstance == null) {

                    /* instance will be created at request time */
                    serverRequestHandlerInstance = new ServerRequestHandler();
                }
            }
        }
        return serverRequestHandlerInstance;
    }


    //should be notified
    public void initiateServerCommunication(CIBAauthRequest cibAauthRequest, String auth_req_id) {

        refactor(cibAauthRequest, storeInDB(auth_req_id));



    }
    

    private void refactor(CIBAauthRequest cibAauthRequest, String identifier) {
        try {
            // TODO: 9/2/19 write the logic for empty usercode and binding message
            /*String bindingmessage = cibAauthRequest.getBinding_message();
            String usercode = cibAauthRequest.getUser_code();*/
/*
            if (cibAauthRequest.getUser_code().isEmpty() && !cibAauthRequest.getBinding_message().isEmpty()){
                    *//*creating authentication response for the request*//*
                    JWTClaimsSet claims = new JWTClaimsSet.Builder()
                            .claim("identifier", identifier)
                            .claim("bindingmessage", cibAauthRequest.getBinding_message())
                            .build();

                    String cibarequest = claims.toJSONObject().toString();

                    Payload cibarequestpaylaod = new Payload(claims.toJSONObject());
                 
                 
                 

                    *//**
                    Send the Authentication Request to the Identity Server.
                     *//*
                    this.initiateRequest(cibarequest,identifier); //sending the request

                }
            if (!cibAauthRequest.getUser_code().isEmpty() && cibAauthRequest.getBinding_message().isEmpty()){

                *//*creating authentication response for the request*//*
                JWTClaimsSet claims = new JWTClaimsSet.Builder()
                        .claim("identifier", identifier)
                        .claim("usercode", cibAauthRequest.getUser_code())
                        .build();

                String cibarequest = claims.toJSONObject().toString();

                Payload cibarequestpaylaod = new Payload(claims.toJSONObject());

                *//**
                 Send the Authentication Request to the Identity Server.
                 *//*
                this.initiateRequest(cibarequest,identifier); //sending the request


            }*/
           // if (cibAauthRequest.getUser_code().isEmpty() && cibAauthRequest.getBinding_message().isEmpty()){
                /*creating authentication response for the request*/
               /* JWTClaimsSet claims = new JWTClaimsSet.Builder()
                        .claim("identifier", identifier)
                        .claim("bindingmessage",cibAauthRequest.getBinding_message())
                        .claim("usercode",cibAauthRequest.getUser_code())
                        .claim("username","cvivekvinushanth@gmail.com")
                        .build();

                String cibarequest = claims.toJSONObject().toString();

                Payload cibarequestpaylaod = new Payload(claims.toJSONObject());
*/
                /**
                 Send the Authentication Request to the Identity Server.
                 */
                initiateRequest(cibAauthRequest,identifier); //sending the request as binded



        }

        catch(Exception e){
                e.printStackTrace();
            }
        }


        //create identifier for  and store
        private String storeInDB (String authreqid){
            String identifier = CodeGenerator.getInstance().getRandomID();
            identifierstore.put(identifier, authreqid);
            LOGGER.info("Identifier for auth_req_id generated and stored.");

            return identifier;
        }

        //Initiate token request to server

        private void initiateRequest(CIBAauthRequest  cibAauthRequest,String identifier) {
        System.out.println("Initiating server auth2 code grant");
            //Start sending request to IS server and listen upon

            // TODO: 8/8/19 but now will manualy do something for now
           // ServerResponseHandler.getInstance().InitiateFakeResponse(identifier);

            try {
               String user= getUser(cibAauthRequest);
               if(!user.equals(null)) {
                   String bindingmessage = cibAauthRequest.getBinding_message(); // TODO: 9/16/19 its fine if they are null. we can send null.
                   String usercode = cibAauthRequest.getUser_code();

                   // TODO: 9/12/19 just added
                   TempErrorCache.getInstance().addAuthenticationStatus(ServerRequestHandler.getInstance().getAuthReqId(identifier), "RequestSent");

                   RestTemplate restTemplate = RestTemplateFactory.getInstance().getRestTemplate();
                   String result = restTemplate.getForObject(CIBAParameters.getInstance().getAUTHORIZE_ENDPOINT() + "?scope=openid&" +
                           "response_type=code&state=" + identifier + "&redirect_uri=" + CIBAParameters.getInstance().
                           getCallBackURL() + "&client_id=" + ConfigurationFile.getInstance().getCLIENT_ID() + "&user=" + user, String.class);


                   if (result != null) {
                       LOGGER.info("Code received at the Endpoint. Need processing the code flow");
                       // System.out.println("uri"+CIBAParameters.getInstance().getCallBackURL());
                       //System.out.println(result);

                   }
               } else {
                   throw new BadRequest("Identifier for request not found.");
               }

            } catch (KeyStoreException e) {
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Improper Keys.");
            } catch (HttpClientErrorException e){
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Denied the consent.");
            } catch (NoSuchAlgorithmException e) {
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such Algorithm.");
            } catch (KeyManagementException e) {
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "KeyManagement Exception.");

            } catch (BadRequest badRequest) {
                LOGGER.info("Identifier for request not found.");
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,badRequest.getMessage());
            }
        }


    private String getUser(CIBAauthRequest cibAauthRequest) {
        try {
            if (!cibAauthRequest.getLogin_hint().equals("null")) {
                return cibAauthRequest.getLogin_hint(); //sending the request as binded

                /**
                 *  We don't support this for now.
                 *  But still extensible.
                 *  */
            } else if (!cibAauthRequest.getLogin_hint_token().equals("null")) { // TODO: 9/16/19 implement for loginhinttoken

                return null; // TODO: 9/16/19 We need to support authentication profile 1.0 spec for this.

            } else if (!cibAauthRequest.getId_token_hint().equals("null")) {
                //String user = cibAauthRequest.getId_token_hint();   TODO: 9/16/19 implement for getting user claims from idtokenhint

                SignedJWT signedJWT = SignedJWT.parse(cibAauthRequest.getId_token_hint());
                String payload = signedJWT.getPayload().toString();
                System.out.println(payload);

                JSONObject jo = signedJWT.getJWTClaimsSet().toJSONObject();


                if (String.valueOf(jo.get("email")) != null) {
                    return String.valueOf(jo.get("email")); //obtaining email of user and setting it as user

                } else if (String.valueOf(jo.get("given_name")) != null) {
                    return String.valueOf(jo.get("given_name")); //obtaining name of user and setting it as user

                } else if (String.valueOf(jo.get("family_name")) != null) {
                    return String.valueOf(jo.get("family_name")); //obtaining name of user and setting it as user

                } else if (String.valueOf(jo.get("name")) != null) {
                    return String.valueOf(jo.get("name")); //obtaining email of name and setting it as user

                } else if (String.valueOf(jo.get("username")) != null) {
                    return String.valueOf(jo.get("username")); //obtaining username of user and setting it as user

                }else if (String.valueOf(jo.get("userid")) != null) {
                    return String.valueOf(jo.get("userid")); //obtaining userid of user and setting it as user

                }else if (String.valueOf(jo.get("mobile")) != null) {
                    return String.valueOf(jo.get("mobile")); //obtaining mobile of user and setting it as user
// TODO: 9/16/19 Good if we can get the userclaims using a rest call.Else we need to follow this weird long method.
//  Or user need to configure here in config as well.
                }else if (String.valueOf(jo.get("phonenumber.work")) != null) {
                    return String.valueOf(jo.get("phonenumber.work")); //obtaining phonenumber of user and setting it as user

                }else if (String.valueOf(jo.get("phonenumber.home")) != null) {
                return String.valueOf(jo.get("phonenumber.home")); //obtaining phonenumber of user and setting it as user

            }else {
                    return null;
                }

            } else {
                return null;
            }


        } catch (ParseException e) {
            LOGGER.info("Unable to parse given ID TokenHint.");
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to parse given ID TokenHint.");
        }

    }



        public String getAuthReqId(String identifier){
        return identifierstore.get(identifier);

        }





}


