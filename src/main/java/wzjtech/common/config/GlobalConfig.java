package wzjtech.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wzjtech.common.ExceptionHandler;

@Configuration
public class GlobalConfig {


  @Bean
  public ExceptionHandler registerExceptionHandler() {
    return new ExceptionHandler();
  }

}
