package wzjtech.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import wzjtech.common.ExceptionHandler;
import wzjtech.document.converter.EnablePluginReadConverter;
import wzjtech.document.converter.EnablePluginWriteConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class GlobalConfig {


  @Bean
  @Order(-2)
  public ExceptionHandler registerExceptionHandler(ObjectMapper objectMapper) {
    return new ExceptionHandler(objectMapper);
  }

  @Bean
  public MongoCustomConversions customConversions() {
    List<Converter<?, ?>> converterList = new ArrayList<>();
    converterList.add(new EnablePluginWriteConverter());
    converterList.add(new EnablePluginReadConverter());
    return new MongoCustomConversions(converterList);
  }
}
