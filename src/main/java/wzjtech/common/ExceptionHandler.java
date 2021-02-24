package wzjtech.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Slf4j
public class ExceptionHandler implements ErrorWebExceptionHandler {
  private final ObjectMapper objectMapper;

  public ExceptionHandler(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    var response = exchange.getResponse();
    var code = response.getStatusCode();

    var map = new HashMap<String, String>();
    if (ex instanceof DuplicateKeyException) {
      response.setStatusCode(HttpStatus.BAD_REQUEST);
      map.put("code", "DUPLICATED_ENTITY");
      map.put("description", "Duplicated entity");
    } else {
      response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
      map.put("code", "INTERNAL_ERROR");
      map.put("description", "Unknown error");

      log.warn("Unkown exception occurs", ex);
    }
    var mono = Mono.just(map).map(m -> {
      try {
        return objectMapper.writeValueAsBytes(map);
      } catch (JsonProcessingException e) {
        log.warn("JSON format", e);
      }
      return new byte[0];
    }).map(bytes -> response.bufferFactory().wrap(bytes));

    return response.writeWith(mono);
  }
}
