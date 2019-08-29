package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class YamlToJSON {


    public static JSONObject convertYamlToJson() throws IOException, ParseException {

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            String config = new String(Files.readAllBytes(Paths.get("src/main/resources/config.yaml")));
            System.out.println(config);
            String json = convert(config);

            System.out.println(json);
        JSONParser parser = new JSONParser();
        JSONObject  configfile = (JSONObject) parser.parse(json);

        return configfile;
        }


        private static String convert(String yaml) throws IOException {
                ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
                Object obj = yamlReader.readValue(yaml, Object.class);
                ObjectMapper jsonWriter = new ObjectMapper();
                return jsonWriter.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            }




}
