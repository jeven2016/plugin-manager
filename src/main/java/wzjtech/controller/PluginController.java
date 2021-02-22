package wzjtech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("plugins")
public class PluginController {

  @GetMapping("list")
  public Flux<String> listPlugins() {
    return Flux.just("listPlugins");
  }

  @GetMapping("{id}")
  public Mono<String> getPlugin() {
    return Mono.just("getPlugin");
  }


}
