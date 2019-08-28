package jdbc;

import handlers.Handlers;
import java.util.ArrayList;
public class CibaProxyJdbcStore {

        private ArrayList<Handlers> interestedparty = new ArrayList<Handlers> ();
        private AuthRequestDB authRequestDB;
        private AuthResponseDB authResponseDB;
        private TokenRequestDB tokenRequestDB;
        private TokenResponseDB tokenResponseDB;
        private ExpiresInDB expiresInDB;
        private IntervalDB intervalDB;
        private IssuedTimeDB issuedTimeDB;
        private LastPollDB lastPollDB;

        private CibaProxyJdbcStore() {
            authRequestDB = AuthRequestDB.getInstance();
            authResponseDB = AuthResponseDB.getInstance();
            tokenRequestDB =  TokenRequestDB.getInstance();
            tokenResponseDB =  TokenResponseDB.getInstance();
            expiresInDB =  ExpiresInDB.getInstance();
            intervalDB =  IntervalDB.getInstance();
            issuedTimeDB =  IssuedTimeDB.getInstance();
            lastPollDB =  LastPollDB.getInstance();

        }

        private static CibaProxyJdbcStore cibaProxyJdbcStoreInstance = new CibaProxyJdbcStore();

        public static CibaProxyJdbcStore getInstance() {
            if (cibaProxyJdbcStoreInstance == null) {

                synchronized (CibaProxyJdbcStore.class) {

                    if (cibaProxyJdbcStoreInstance == null) {

                        /* instance will be created at request time */
                        cibaProxyJdbcStoreInstance = new CibaProxyJdbcStore();
                    }
                }
            }
            return cibaProxyJdbcStoreInstance;


        }

        public  AuthRequestDB getAuthRequestDB() {
            return authRequestDB;
        }

        public AuthResponseDB getAuthResponseDB() {
            return authResponseDB;
        }

        public TokenRequestDB getTokenRequestDB() {
            return tokenRequestDB;
        }

        public TokenResponseDB getTokenResponseDB() {
            return tokenResponseDB;
        }

        public ExpiresInDB getExpiresInDB() {
            return expiresInDB;
        }

        public IntervalDB getIntervalDB() {
            return intervalDB;
        }

        public IssuedTimeDB getIssuedTimeDB() {
            return issuedTimeDB;
        }

        public LastPollDB getLastPollDB() {
            return lastPollDB;
        }

        public static CibaProxyJdbcStore getcibaProxyJdbcStoreInstance() {
            return cibaProxyJdbcStoreInstance;
        }


    }


