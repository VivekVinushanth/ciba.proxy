package ciba.proxy.server.servicelayer;

import dao.DaoFactory;
import handlers.Handlers;
import transactionartifacts.TokenResponse;

import java.util.logging.Logger;

public class ServerResponseHandler implements Handlers {

    private static final Logger LOGGER = Logger.getLogger(ServerResponseHandler.class.getName());
    private ServerResponseHandler() {
       // this.run();
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




    public void receive(){
        //receive token from Identity server
    }

    public  void InitiateFakeResponse(String identifier) {
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

        }


/*
    public void register(){
        DaoFactory.getInstance().getConnector("InMemoryCache").registerToAuthRequestCache(this);
    }*/

    public Boolean validate(TokenResponse tokenResponse){
        // TODO: 8/8/19 Actually  need to validate
        return true;
    }

    public void addtoStore(TokenResponse tokenResponse,String identifier) {
        if (validate(tokenResponse)) {

            DaoFactory.getInstance().getConnector("InMemoryCache").addTokenResponse(ServerRequestHandler.
                            getInstance().getAuthReqId(identifier), tokenResponse);

        LOGGER.info("Token Response Received");
        }

    }


}
