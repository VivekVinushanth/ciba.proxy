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

import transactionartifacts.Client;

/**
 * Connector for client store in Redis Database.
 */
public class CibaClientStoreRedisConnector implements ClientStoreConnector {

    private static CibaClientStoreRedisConnector cibaClientStoreRedisConnectorInstance =
            new CibaClientStoreRedisConnector();

    public static CibaClientStoreRedisConnector getInstance() {

        if (cibaClientStoreRedisConnectorInstance == null) {

            synchronized (CibaClientStoreRedisConnector.class) {

                if (cibaClientStoreRedisConnectorInstance == null) {

                    /* instance will be created at request time */
                    cibaClientStoreRedisConnectorInstance = new CibaClientStoreRedisConnector();
                }
            }
        }
        return cibaClientStoreRedisConnectorInstance;

    }

    @Override
    public void addClient(String clientid, Object client) {

    }

    @Override
    public void removeClient(String clientid) {

    }

    @Override
    public Client getClient(String clientid) {

        return null;
    }
}
