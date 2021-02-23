package wzjtech.common;

import com.mongodb.DuplicateKeyException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ExceptionHandler implements ErrorWebExceptionHandler {

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    if(ex instanceof DuplicateKeyException){
      exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);

    }
    return null;
  }
}
