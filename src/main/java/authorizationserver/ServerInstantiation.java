package authorizationserver;

import configuration.ConfigHandler;
import net.minidev.json.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;


@SpringBootApplication
public class ServerInstantiation extends SpringBootServletInitializer {


    public static void main(String[] args) throws IOException {

        SpringApplication.run(ServerInstantiation.class, args);
        ConfigHandler.getInstance().configure();




    }

}
