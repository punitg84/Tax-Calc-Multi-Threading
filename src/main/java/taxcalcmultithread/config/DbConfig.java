package taxcalcmultithread.config;

import static taxcalcmultithread.constants.Path.APPLICATION_YML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class DbConfig {
  public static final String USERNAME;
  public static final String PASSWORD;
  public static final String URL;

  static {
    try {
      final Map<String,Map<String,String>> config= new Yaml()
          .load(Files.newInputStream(Paths.get(APPLICATION_YML)));

      final Map<String,String> datasource= config.get("datasource");
      USERNAME = datasource.get("username");
      PASSWORD = datasource.get("password");
      URL = datasource.get("url");
    } catch (IOException e) {
      throw new RuntimeException("Unable to read YML file",e);
    }
  }
}
