package wzjtech.controller;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import wzjtech.document.PluginDocument;
import wzjtech.document.PluginVersionDocument;
import wzjtech.service.PluginService;

@RestController
@RequestMapping("/catalogs/{groupId}/plugins")
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
  public Mono<UpdateResult> delete(@PathVariable String groupId,
      @PathVariable String pluginName) {
    return pluginService.delete(groupId, pluginName);
  }

  @PostMapping("{pluginName}/versions")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<UpdateResult> createVersion(@PathVariable String groupId,
      @PathVariable String pluginName, @RequestBody PluginVersionDocument version) {
    return pluginService.saveVersion(groupId, pluginName, version);
  }

  @DeleteMapping("{pluginName}/versions/{version}")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<UpdateResult> deleteVersion(@PathVariable String groupId,
      @PathVariable String pluginName, @PathVariable String version) {
    return pluginService.deleteVersion(groupId, pluginName, version);
  }
}
