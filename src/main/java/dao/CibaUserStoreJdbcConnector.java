/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package dao;

import transactionartifacts.User;

/**
 * User store connector for JDBC.
 */
public class CibaUserStoreJdbcConnector implements UserStoreConnector {

    private static CibaUserStoreJdbcConnector cibaUserStoreJdbcConnectorInstance = new CibaUserStoreJdbcConnector();

    public static CibaUserStoreJdbcConnector getInstance() {

        if (cibaUserStoreJdbcConnectorInstance == null) {

            synchronized (CibaUserStoreJdbcConnector.class) {

                if (cibaUserStoreJdbcConnectorInstance == null) {

                    /* instance will be created at request time */
                    cibaUserStoreJdbcConnectorInstance = new CibaUserStoreJdbcConnector();
                }
            }
        }
        return cibaUserStoreJdbcConnectorInstance;

    }

    @Override
    public void addUser(String userid, Object user) {

    }

    @Override
    public void removeUser(String userid) {

    }

    @Override
    public User getUser(String userid) {

        return null;
    }
}
