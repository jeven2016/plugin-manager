package wzjtech.controller;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import wzjtech.document.PluginDocument;
import wzjtech.service.PluginService;

@RestController
public class PluginController {

  private final PluginService pluginService;

  @Autowired
  public PluginController(PluginService pluginService) {
    this.pluginService = pluginService;
  }

  @PostMapping("/plugin-groups/{groupId}/plugins")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<UpdateResult> create(@RequestBody PluginDocument plugin,
      @PathVariable String groupId) {
    return pluginService.save(groupId, plugin);
  }

  @PutMapping("/plugin-groups/{groupId}/plugins/{pluginName}")
  public Mono<UpdateResult> update(@RequestBody PluginDocument plugin, @PathVariable String groupId,
      @PathVariable String pluginName) {
    return pluginService.update(groupId, pluginName, plugin);
  }

  @GetMapping("/plugin-groups/test")
  public Mono<String> test() {
    return Mono.just("test");
  }
}
