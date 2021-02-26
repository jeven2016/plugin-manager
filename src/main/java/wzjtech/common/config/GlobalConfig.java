package wzjtech.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import wzjtech.common.ExceptionHandler;

@Configuration
public class GlobalConfig {


  @Bean
  @Order(-2)
  public ExceptionHandler registerExceptionHandler(ObjectMapper objectMapper) {
    return new ExceptionHandler(objectMapper);
  }
}
