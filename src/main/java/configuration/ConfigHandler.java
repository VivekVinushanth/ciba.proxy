package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public class ConfigHandler {
    private static final Logger LOGGER = Logger.getLogger(ConfigHandler.class.getName());
    private ConfigHandler() {

    }

    private static ConfigHandler configHandlerInstance = new ConfigHandler();

    public static ConfigHandler getInstance() {
        if (configHandlerInstance == null) {

            synchronized (ConfigHandler.class) {

                if (configHandlerInstance == null) {

                    /* instance will be created at request time */
                    configHandlerInstance = new ConfigHandler();
                }
            }
        }
        return configHandlerInstance;
    }


    public  void configure() throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            TempConfig tempConfig1  = mapper.readValue(new File("src/main/resources/config.yaml"), TempConfig.class);


            if (tempConfig1 instanceof TempConfig) {
                TempConfig tempConfig = (TempConfig) tempConfig1;
                try{
                    if(tempConfig.getAppName().isEmpty() || tempConfig.getAppName().equals("")
                            || tempConfig.getAppName().equals("null")){
                        throw new IllegalArgumentException();
                    } else{
                        ConfigurationFile.getInstance().setAPP_NAME(tempConfig.getAppName());
                    }
                }catch (IllegalArgumentException e){
                    LOGGER.severe("App Name can not be null");
                }


                try{
                    if(tempConfig.getClientId().isEmpty() ||tempConfig.getClientId().equals("") || tempConfig.getClientId().equals("null")){
                        throw new IllegalArgumentException();
                    } else{
                        ConfigurationFile.getInstance().setCLIENT_ID(tempConfig.getClientId());
                    }
                }catch (IllegalArgumentException e){
                    LOGGER.severe("Client ID can not be null");
                }


                try{
                    if(tempConfig.getClientSecret().isEmpty() ||tempConfig.getClientSecret().equals("")  || tempConfig.getClientSecret().equals("null")){
                        throw new IllegalArgumentException();
                    } else{
                        ConfigurationFile.getInstance().setCLIENT_SECRET(tempConfig.getClientSecret());
                    }
                }catch (IllegalArgumentException e){
                    LOGGER.severe("Client Secret can not be null");
                }


              /*  try{
                    if(tempConfig.getAuthorizationUser().isEmpty() ||tempConfig.getAuthorizationUser().equals("")
                            || tempConfig.getAuthorizationUser().equals("null")){
                        throw new IllegalArgumentException();
                    } else{
                        ConfigurationFile.getInstance().setAUTHORIZATION_USER(tempConfig.getAuthorizationUser());
                    }
                }catch (IllegalArgumentException e){
                    LOGGER.severe("Authorization User can not be null");
                }


                try{
                    if(tempConfig.getAuthorizationPassword().isEmpty() ||tempConfig.getAuthorizationPassword().equals("")
                          || tempConfig.getAuthorizationPassword().equals("null")){
                        throw new IllegalArgumentException();
                    } else{
                        ConfigurationFile.getInstance().setAUTHORIZATION_PASSWORD(tempConfig.getAuthorizationPassword());
                    }
                }catch (IllegalArgumentException e){
                    LOGGER.severe("Authorization Password can not be null");
                }*/

                try{
                    if(tempConfig.getStoreConnectorType().isEmpty() ||tempConfig.getStoreConnectorType().equals("") ||
                            tempConfig.getStoreConnectorType().equals("null")){
                        throw new IllegalArgumentException();
                    } else{
                        ConfigurationFile.getInstance().setSTORE_CONNECTOR_TYPE(tempConfig.getStoreConnectorType());
                    }
                }catch (IllegalArgumentException e){
                    LOGGER.severe("Store Connector Type can not be null");
                }

                try{
                    if(tempConfig.getDbUserName().isEmpty() ||tempConfig.getDbUserName().equals("") ||
                             tempConfig.getDbUserName().equals("null")){
                        throw new IllegalArgumentException();
                    } else{
                        ConfigurationFile.getInstance().setDB_USER_NAME(tempConfig.getDbUserName());
                    }
                }catch (IllegalArgumentException e){
                    LOGGER.severe("Database User Name can not be null");
                }

                try{
                    if(tempConfig.getDbUserName().isEmpty() ||tempConfig.getDbUserName().equals("") ||
                             tempConfig.getDbUserName().equals("null")){
                        //do nothing as of now
                        ConfigurationFile.getInstance().setDB_PASSWORD(tempConfig.getDbUserPassword());
                        LOGGER.warning("Database Password is null.Advisory to configure one.");

                    } else{
                        ConfigurationFile.getInstance().setDB_PASSWORD(tempConfig.getDbUserPassword());
                    }
                }catch (IllegalArgumentException e){
                    LOGGER.warning("Database Password is null.Advisory to configure one.");
                }

                try{
                    if(tempConfig.getDatabase().isEmpty() ||tempConfig.getDatabase().equals("") ||
                            tempConfig.getDatabase().equals("null")){
                        throw new IllegalArgumentException();
                    } else{
                        ConfigurationFile.getInstance().setDATABASE(tempConfig.getDatabase());
                    }
                }catch (IllegalArgumentException e){
                    LOGGER.severe("Database can not be null");
                }


                try{
                    if(tempConfig.getflowMode().isEmpty() ||tempConfig.getflowMode().equals("") ||
                          tempConfig.getflowMode().equals("null")){
                        throw new IllegalArgumentException();
                    } else{
                        ConfigurationFile.getInstance().setFLOW_MODE(tempConfig.getflowMode());
                    }
                }catch (IllegalArgumentException e){
                    LOGGER.severe("Flow_mode can not be null");
                }

                try{
                    if(tempConfig.getClientNotificationEndpoint().isEmpty() ||tempConfig.getClientNotificationEndpoint().equals("") ||
                            tempConfig.getClientNotificationEndpoint().equals("null")){
                        throw new IllegalArgumentException();
                    } else{
                        ConfigurationFile.getInstance().setCLIENT_NOTIFICATION_ENDPOINT(tempConfig.getClientNotificationEndpoint());
                    }
                }catch (IllegalArgumentException e){
                    LOGGER.severe("Client Notification EndPoint can not be null");
                }


               // this.setConfiguration();

                try {
                    if (tempConfig.getflowMode().equalsIgnoreCase("push")) {
                        throw new IllegalArgumentException();
                    }
                }catch (IllegalArgumentException e){
                    LOGGER.warning("Push mode is not Supported by the Proxy.Configure with 'Poll'or 'Ping'.");
                }
            }
        } catch (IOException e ){
            LOGGER.severe("Config.yaml Not Found. Check File or Source Path.");
            e.printStackTrace();
        } finally {
            LOGGER.info("Proxy Server Configured Properly.");
        }


    }



//    private  void setConfiguration(){
//
//        try {
//            ConfigurationFile.getInstance().setSEC_TOKEN(ConfigurationFile.getInstance().getAUTHORIZATION_USER(),
//                    ConfigurationFile.getInstance().getAUTHORIZATION_PASSWORD());
//        } catch (UnsupportedEncodingException e) {
//            LOGGER.severe("can not Create Sec-Token.Check whether Authorization UserName and Password is configured properly.");
//        }
//
//    }





}
