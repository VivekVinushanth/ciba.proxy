package ciba.proxy.server.servicelayer;

import com.sun.net.ssl.HostnameVerifier;
import com.sun.net.ssl.HttpsURLConnection;
import handlers.Handlers;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import util.RestTemplateFactory;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class ServerUserRegistrationHandler implements Handlers  {

    private static final Logger LOGGER = Logger.getLogger(ServerResponseHandler.class.getName());

    private ServerUserRegistrationHandler() {
        // this.run();
    }

    private static ServerUserRegistrationHandler serverUserRegistrationHandlerInstance = new ServerUserRegistrationHandler();

    public static ServerUserRegistrationHandler getInstance() {
        if (serverUserRegistrationHandlerInstance == null) {

            synchronized (ServerUserRegistrationHandler.class) {

                if (serverUserRegistrationHandlerInstance == null) {

                    /* instance will be created at request time */
                    serverUserRegistrationHandlerInstance = new ServerUserRegistrationHandler();
                }
            }
        }
        return serverUserRegistrationHandlerInstance;
    }


    public void receive() {
        //receive token from Identity server
    }

    public String save(JSONObject user, HttpHeaders headers) {

        /*try {
            URL url = new URL("https://localhost:9443/scim2/Users");

            HttpsURLConnectionImpl  con = (HttpsURLConnectionImpl) url.openConnection();
            con.setHostnameVerifier(TempHostNameVerifier.getInstance());
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = user.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                //System.out.println(response.toString());
                return response.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            RestTemplate restTemplate = RestTemplateFactory.getInstance().getRestTemplate();

            System.out.println(user);
            HttpEntity<String> request = new HttpEntity<String>(user.toString(), headers);
            String uri = "https://localhost:9443/scim2/Users";
            return (restTemplate.postForObject(uri, request, String.class));


        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return "Unstored";

    }

}