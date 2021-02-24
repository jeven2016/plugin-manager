package wzjtech.controller;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public Mono<UpdateResult> create(String groupid, @RequestBody PluginDocument plugin) {
    return pluginService.save(groupid, plugin);
  }

}
