package taxcalcmultithread.config;

import static taxcalcmultithread.constants.Path.APPLICATION_YML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class DbConfig {
  public static final String username;
  public static final String password;
  public static final String url;

  static {
    try {
      Map<String,Map<String,String>> config= new Yaml()
          .load(Files.newInputStream(Paths.get(APPLICATION_YML)));

      Map<String,String> datasource= config.get("datasource");
      username = datasource.get("username");
      password = datasource.get("password");
      url = datasource.get("url");
    } catch (IOException e) {
      throw new RuntimeException("Unable to read YML file",e);
    }
  }
}
