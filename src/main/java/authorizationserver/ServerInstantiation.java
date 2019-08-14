package authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class ServerInstantiation extends SpringBootServletInitializer {


    public static void main(String[] args) {

        SpringApplication.run(ServerInstantiation.class, args);


    }

}
