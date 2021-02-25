package wzjtech.controller;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import wzjtech.document.PluginDocument;
import wzjtech.document.PluginGroupDocument;
import wzjtech.service.PluginService;

@RestController
@RequestMapping("/plugin-groups/{groupId}/plugins")
public class PluginController {

  private final PluginService pluginService;

  @Autowired
  public PluginController(PluginService pluginService) {
    this.pluginService = pluginService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<UpdateResult> create(@RequestBody PluginDocument plugin,
                                   @PathVariable String groupId) {
    return pluginService.save(groupId, plugin);
  }

  @PutMapping("{pluginName}")
  public Mono<UpdateResult> update(@RequestBody PluginDocument plugin, @PathVariable String groupId,
                                   @PathVariable String pluginName) {
    return pluginService.update(groupId, pluginName, plugin);
  }


  @DeleteMapping("{pluginName}")
  public Mono<PluginGroupDocument> delete(@PathVariable String groupId,
                                          @PathVariable String pluginName) {
    return pluginService.delete(groupId, pluginName);
  }
}
