package wzjtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wzjtech.document.PluginGroupDocument;
import wzjtech.document.PluginGroupInfo;
import wzjtech.service.PluginGroupService;

@RestController
@RequestMapping("plugin-groups")
public class PluginGroupCtrl {

  private final PluginGroupService groupService;

  @Autowired
  public PluginGroupCtrl(PluginGroupService groupService) {
    this.groupService = groupService;
  }


  @GetMapping
  public Flux<PluginGroupInfo> getAll(String id) {
    return groupService.findAll();
  }


  @GetMapping("{id}")
  public Mono<PluginGroupDocument> get(String id) {
    return groupService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<PluginGroupInfo> create(@RequestBody PluginGroupInfo groupInfo) {
    return groupService.save(groupInfo).map(PluginGroupDocument::toGroupInfo);
  }

  @PutMapping("{id}")
  public Mono<PluginGroupInfo> update(@RequestBody PluginGroupInfo groupInfo, String id) {
    return groupService.save(groupInfo).map(PluginGroupDocument::toGroupInfo);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> delete(String id) {
    return groupService.delete(id);
  }


  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteAll(String id) {
    return groupService.deleteAll();
  }

}
