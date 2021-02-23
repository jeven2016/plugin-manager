package wzjtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wzjtech.entity.User;
import wzjtech.service.TestService;

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


  @Autowired
  TestService testService;

  @GetMapping("test")
  public Mono<User> test() {
    return testService.get();
  }

}
