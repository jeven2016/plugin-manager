package wzjtech.controller;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wzjtech.document.PluginDocument;
import wzjtech.document.PluginVersionDocument;
import wzjtech.dto.PluginDto;
import wzjtech.service.PluginService;

import java.util.HashMap;

@RestController
@RequestMapping("/catalogs/{groupId}/plugins")
public class PluginController {

  private final PluginService pluginService;

  @Autowired
  public PluginController(PluginService pluginService) {
    this.pluginService = pluginService;
  }


  @GetMapping
  public Flux<HashMap> listPlugins(@PathVariable String groupId) {
    return pluginService.findPlugins(groupId);
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


  @GetMapping("{pluginName}")
  public Mono<PluginDto.PluginPureDto> findByName(@PathVariable String groupId, @PathVariable String pluginName) {
    return pluginService.findByName(groupId, pluginName);
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
