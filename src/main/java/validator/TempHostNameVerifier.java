package validator;

import com.sun.net.ssl.X509TrustManager;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class TempHostNameVerifier implements X509TrustManager, HostnameVerifier {


    private TempHostNameVerifier() {

    }

    private static TempHostNameVerifier tempHostNameVerifierInstance = new TempHostNameVerifier();

    public static TempHostNameVerifier getInstance() {
        if (tempHostNameVerifierInstance == null) {

            synchronized (TempHostNameVerifier.class) {

                if (tempHostNameVerifierInstance == null) {

                    /* instance will be created at request time */
                    tempHostNameVerifierInstance = new TempHostNameVerifier();
                }
            }
        }
        return tempHostNameVerifierInstance;


    }

        public void checkClientTrusted( X509Certificate[] x509 , String authType ) throws CertificateException { /* nothing */ }
        public void checkServerTrusted( X509Certificate[] x509 , String authType ) throws CertificateException { /* nothing */ }

    @Override
    public boolean isClientTrusted(X509Certificate[] x509Certificates) {
        return true;
    }

    @Override
    public boolean isServerTrusted(X509Certificate[] x509Certificates) {
        return true;
    }

    public X509Certificate[] getAcceptedIssuers() { return null; }

    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }
}
