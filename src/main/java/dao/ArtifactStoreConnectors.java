package dao;

import ciba.proxy.server.servicelayer.ServerRequestHandler;
import handlers.Handlers;
import transactionartifacts.CIBAauthRequest;
import transactionartifacts.CIBAauthResponse;
import transactionartifacts.TokenRequest;
import transactionartifacts.TokenResponse;

 public interface ArtifactStoreConnectors {
      void addAuthRequest(String auth_req_id, Object authrequest);
      void addAuthResponse(String auth_req_id, Object authresponse);
      void addTokenRequest(String auth_req_id, Object tokenrequest);
      void addTokenResponse(String auth_req_id, Object tokenresponse);
      void addExpiresTime(String auth_req_id, long timestamp);
      void addLastPollTime(String auth_req_id, long lastpolltime);
      void addIssuedTime(String auth_req_id, long issuedtime);
      void addInterval(String auth_req_id, long interval);

      void removeAuthRequest(String auth_req_id);
      void removeAuthResponse(String auth_req_id);
      void removeTokenRequest(String auth_req_id);
      void removeTokenResponse(String auth_req_id);;
      void removeExpiresTime(String auth_req_id);
      void removeLastPollTime(String auth_req_id);
      void removeIssuedTime(String auth_req_id);
      void removeInterval(String auth_req_id);

      CIBAauthRequest getAuthRequest(String auth_req_id);
      CIBAauthResponse getAuthResponse(String auth_req_id);
      TokenRequest getTokenRequest(String auth_req_id);
      TokenResponse getTokenResponse(String auth_req_id);
      long getExpiresTime(String auth_req_id);
      long getLastPollTime(String auth_req_id);
      long  getIssuedTime(String auth_req_id);
      long  getInterval(String auth_req_id);

      void registerToAuthRequestObservers(Object authRequestHandler);
      void registerToAuthResponseObservers(Object authResponseHandler);
      void registerToTokenRequestObservers(Object tokenRequestHandler);
      void registerToTokenResponseObservers(Object tokenResponseHandler);
      void registerToExpiryTimeObservers(Object expiryHandler);
      void registerToLastPollObservers(Object pollHandler);
      void registerToIssuedTimeObservers(Object issuedTimeHandler);
      void registerToIntervalObservers(Object intervalHandler);

 }

