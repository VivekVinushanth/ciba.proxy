package util;
import java.security.*;


public class SecretKeyPairGenerator {

    private SecretKeyPairGenerator() {

    }

    private static SecretKeyPairGenerator secretKeyPairGeneratorInstance = new SecretKeyPairGenerator();

    public static SecretKeyPairGenerator getInstance() {
        if (secretKeyPairGeneratorInstance == null) {

            synchronized (SecretKeyPairGenerator.class) {

                if (secretKeyPairGeneratorInstance == null) {

                    /* instance will be created at request time */
                    secretKeyPairGeneratorInstance = new SecretKeyPairGenerator();
                }
            }
        }
        return secretKeyPairGeneratorInstance;


    }


    public KeyPair generatesecretkey() throws NoSuchAlgorithmException {

        try {

            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");


            //Initializing the KeyPairGenerator
            keyPairGen.initialize(2048);

            //Generating the pair of keys
            KeyPair pair = keyPairGen.generateKeyPair();

       /* //Getting the private key from the key pair
        PrivateKey privKey = pair.getPrivate();

        //Getting the public key from the key pair
        PublicKey publicKey = pair.getPublic();*/
            return pair;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

// TODO: 8/12/19 check this error handling 
            return KeyPairGenerator.getInstance("DSA").generateKeyPair();
        }


    }
}
