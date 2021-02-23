package wzjtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import wzjtech.entity.PluginGroup;
import wzjtech.entity.PluginGroupInfo;
import wzjtech.service.PluginGroupService;

@RestController
@RequestMapping("plugin-groups")
public class PluginGroupCtrl {

  private final PluginGroupService groupService;

  @Autowired
  public PluginGroupCtrl(PluginGroupService groupService) {
    this.groupService = groupService;
  }

  @PostMapping
  public Mono<PluginGroupInfo> create(@RequestBody PluginGroupInfo groupInfo) {
    return groupService.create(groupInfo).map(PluginGroup::toGroupInfo);
  }

  @DeleteMapping
  public void deleteAll(){

  }

}
