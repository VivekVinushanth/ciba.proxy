package configuration;

public class TempConfig {
public TempConfig(){}
        private  String appName;
        private  String clientId;
        private  String clientSecret;

        private  String authorizationServer;
        private  String authorizationPassword;
        private  String storeConnectorType; // TODO: 8/28/19 important place for shifting the dao object
        private  String dbUserName;
        private  String dbUserPassword;
        private  String database;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }



        public String getAuthorizationServer() {
            return authorizationServer;
        }

        public void setAuthorizationServer(String authorizationServer) {
            this.authorizationServer = authorizationServer;
        }

        public String getAuthorizationPassword() {
            return authorizationPassword;
        }

        public void setAuthorizationPassword(String authorizationPassword) {
            this.authorizationPassword = authorizationPassword;
        }

        public String getStoreConnectorType() {
            return storeConnectorType;
        }

        public void setStoreConnectorType(String storeConnectorType) {
            this.storeConnectorType = storeConnectorType;
        }

        public String getDbUserName() {
            return dbUserName;
        }

        public void setDbUserName(String dbUserName) {
            this.dbUserName = dbUserName;
        }

        public String getDbUserPassword() {
            return dbUserPassword;
        }

        public void setDbUserPassword(String dbUserPassword) {
            this.dbUserPassword = dbUserPassword;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }


    }
