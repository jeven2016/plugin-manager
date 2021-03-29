package wzjtech;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Slf4j
@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableReactiveMongoAuditing
public class PluginManagerApplication {

  public static void main(String[] args) {
    try {
      SpringApplication.run(PluginManagerApplication.class);
    } catch (Exception e) {
      log.error("The application is unable to start up", e);
    }
  }

}
