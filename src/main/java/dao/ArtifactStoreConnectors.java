/*
 * Copyright (c) 2013, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import transactionartifacts.CIBAauthRequest;
import transactionartifacts.CIBAauthResponse;
import transactionartifacts.PollingAtrribute;
import transactionartifacts.TokenRequest;
import transactionartifacts.TokenResponse;

/**
 * Abstracts artifact store connector.
 */
public interface ArtifactStoreConnectors {
      void addAuthResponse(String authReqID, Object authresponse);
      void addTokenRequest(String authReqID, Object tokenrequest);
      void addTokenResponse(String authReqID, Object tokenresponse);
      void addPollingAttribute(String authReqID , Object pollingattribute);


      void removeAuthRequest(String authReqID);
      void removeAuthResponse(String authReqID);
      void removeTokenRequest(String authReqID);
      void removeTokenResponse(String authReqID);
      void removePollingAttribute(String authReqID);


      CIBAauthRequest getAuthRequest(String authReqID);
      CIBAauthResponse getAuthResponse(String authReqID);
      TokenRequest getTokenRequest(String authReqID);
      TokenResponse getTokenResponse(String authReqID);
      PollingAtrribute getPollingAttribute(String authReqID);


      void registerToAuthRequestObservers(Object authRequestHandler);
      void registerToAuthResponseObservers(Object authResponseHandler);
      void registerToTokenRequestObservers(Object tokenRequestHandler);
      void registerToTokenResponseObservers(Object tokenResponseHandler);
      void registerToPollingAttribute(Object pollingatrribute);


 }

