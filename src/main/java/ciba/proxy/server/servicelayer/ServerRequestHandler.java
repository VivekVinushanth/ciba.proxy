package ciba.proxy.server.servicelayer;

import cibaparameters.CIBAParameters;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWTClaimsSet;
import configuration.ConfigurationFile;
import dao.DaoFactory;
import handlers.Handlers;
import org.springframework.web.client.RestTemplate;
import transactionartifacts.CIBAauthRequest;
import util.CodeGenerator;
import util.RestTemplateFactory;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.logging.Logger;

public class ServerRequestHandler implements Handlers {
    private HashMap<String, String> identifierstore = new HashMap<String, String>();
    private static final Logger LOGGER = Logger.getLogger(ServerRequestHandler.class.getName());

    private ServerRequestHandler() {
       // DaoFactory.getInstance().getArtifactStoreConnector("InMemoryCache").registerToAuthRequestCache(this);
        //this.run();

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
    public void receive(CIBAauthRequest cibAauthRequest, String auth_req_id) {

        refactor(cibAauthRequest, storeInDB(auth_req_id));



    }

   /* public void register(){
        DaoFactory.getInstance().getArtifactStoreConnector("InMemoryCache").registerToTokenRequestCache(this);

        }
*/

    private void refactor(CIBAauthRequest cibAauthRequest, String identifier) {
        try {
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
                JWTClaimsSet claims = new JWTClaimsSet.Builder()
                        .claim("identifier", identifier)
                        .claim("bindingmessage",cibAauthRequest.getBinding_message())
                        .claim("usercode",cibAauthRequest.getUser_code())
                        .build();

                String cibarequest = claims.toJSONObject().toString();

                Payload cibarequestpaylaod = new Payload(claims.toJSONObject());

                /**
                 Send the Authentication Request to the Identity Server.
                 */
                initiateRequest(cibarequest,identifier); //sending the request



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
        private void initiateRequest(String  cibarequest,String identifier){
        System.out.println("Initiating server auth2 code grant");
            //Start sending request to IS server and listen upon

            // TODO: 8/8/19 but now will manualy do something for now
           // ServerResponseHandler.getInstance().InitiateFakeResponse(identifier);

            try {
                RestTemplate restTemplate = RestTemplateFactory.getInstance().getRestTemplate();
                String result = restTemplate.getForObject(CIBAParameters.getInstance().getAUTHORIZE_ENDPOINT()+"?scope=openid&" +
                        "response_type=code&state="+identifier+"&redirect_uri="+CIBAParameters.getInstance().
                        getCallBackURL()+"&client_id="+ ConfigurationFile.getInstance().getCLIENT_ID()+
                        "&sectoken="+ ConfigurationFile.getInstance().getSEC_TOKEN()+"=&prompt=none",String.class);

                // typecasting obj to JSONObject


                if (result != null){
                    LOGGER.info("Code received at the Endpoint. Need processing the code flow");
                   // System.out.println("uri"+CIBAParameters.getInstance().getCallBackURL());
                    //System.out.println(result);

                }

            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }

        public String getAuthReqId(String identifier){
        return identifierstore.get(identifier);

        }

        public void registerto(){

            DaoFactory.getInstance().getArtifactStoreConnector("InMemoryCache").registerToAuthRequestCache(this);

        }





}


