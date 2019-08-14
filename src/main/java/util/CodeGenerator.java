package util;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;


import java.util.UUID;

/**
 * This class create random codes for various purposes.
 */

@Scope("singleton")
public class CodeGenerator {

    private CodeGenerator() {

    }

    private static CodeGenerator codeGeneratorInstance = new CodeGenerator();

    public static CodeGenerator getInstance() {
        if (codeGeneratorInstance == null) {

            synchronized (CodeGenerator.class) {

                if (codeGeneratorInstance == null) {

                    /* instance will be created at request time */
                    codeGeneratorInstance = new CodeGenerator();
                }
            }
        }
        return codeGeneratorInstance;


    }

        /**
         * Generate a random string.
         */
         public String getAuthReqId() {
             UUID authReqId = UUID.randomUUID();
             return authReqId.toString();

         }


         public String getRandomID(){
             UUID ID = UUID.randomUUID();
             return ID.toString();

         }

         public String getUserid(){
             UUID userId = UUID.randomUUID();
             return userId.toString();
         }

}
