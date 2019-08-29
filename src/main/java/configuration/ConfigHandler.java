package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ConfigHandler {
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
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
           TempConfig tempConfig1  = mapper.readValue(new File("src/main/resources/config.yaml"), TempConfig.class);

        if (tempConfig1 instanceof TempConfig) {
            TempConfig tempConfig = (TempConfig) tempConfig1;
            ConfigurationFile.getInstance().setAPP_NAME(tempConfig.getAppName());
            ConfigurationFile.getInstance().setCLIENT_ID(tempConfig.getClientId());
            ConfigurationFile.getInstance().setCLIENT_SECRET(tempConfig.getClientSecret());
            ConfigurationFile.getInstance().setAUTHORIZATION_USER(tempConfig.getAuthorizationServer());
            ConfigurationFile.getInstance().setAUTHORIZATION_PASSWORD(tempConfig.getAuthorizationPassword());
            ConfigurationFile.getInstance().setSTORE_CONNECTOR_TYPE(tempConfig.getStoreConnectorType());
            ConfigurationFile.getInstance().setDB_USER_NAME(tempConfig.getDbUserName());
            ConfigurationFile.getInstance().setDB_PASSWORD(tempConfig.getDbUserPassword());
            ConfigurationFile.getInstance().setDATABASE(tempConfig.getDatabase());
            this.setConfiguration();
        }
}



    private  void setConfiguration(){

        try {
            ConfigurationFile.getInstance().setSEC_TOKEN(ConfigurationFile.getInstance().getAUTHORIZATION_USER(),
                    ConfigurationFile.getInstance().getAUTHORIZATION_PASSWORD());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }





}
