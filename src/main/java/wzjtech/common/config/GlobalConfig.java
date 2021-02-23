package wzjtech.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wzjtech.common.ExceptionHandler;

@Configuration
public class GlobalConfig {


  @Bean
  public ExceptionHandler registerExceptionHandler(ObjectMapper objectMapper) {
    return new ExceptionHandler(objectMapper);
  }

}
