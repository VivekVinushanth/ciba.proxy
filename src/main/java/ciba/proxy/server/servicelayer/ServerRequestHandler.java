package ciba.proxy.server.servicelayer;

import authorizationserver.CIBAProxyServer;
import cache.TokenRequestCache;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWTClaimsSet;
import dao.DaoFactory;
import handlers.Handlers;
import transactionartifacts.CIBAauthRequest;
import util.CodeGenerator;
import java.util.HashMap;
import java.util.logging.Logger;

public class ServerRequestHandler implements Handlers {
    private HashMap<String, String> identifierstore = new HashMap<String, String>();
    private static final Logger LOGGER = Logger.getLogger(ServerRequestHandler.class.getName());

    private ServerRequestHandler() {
       // DaoFactory.getInstance().getConnector("InMemoryCache").registerToAuthRequestCache(this);
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
        DaoFactory.getInstance().getConnector("InMemoryCache").registerToTokenRequestCache(this);

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


        //create identifier for auth_req_id
        private String storeInDB (String authreqid){
            String identifier = CodeGenerator.getInstance().getRandomID();
            identifierstore.put(identifier, authreqid);
            LOGGER.info("Identifier for auth_req_id generated and stored.");

            return identifier;
        }

        //Initiate request to server
        private void initiateRequest(String  cibarequest,String identifier){
            //Start sending request to IS server and listen upon

            // TODO: 8/8/19 but now will manualy do something for now
            ServerResponseHandler.getInstance().InitiateFakeResponse(identifier);
        }

        public String getAuthReqId(String identifier){
        return identifierstore.get(identifier);

        }

        public void register(){

            DaoFactory.getInstance().getConnector("InMemoryCache").registerToAuthRequestCache(this);

        }




}


