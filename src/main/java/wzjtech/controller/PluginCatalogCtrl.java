package wzjtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wzjtech.document.CatalogDocument;
import wzjtech.document.CatalogInfo;
import wzjtech.service.PluginGroupService;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;

@RestController
@RequestMapping("catalogs")
public class PluginCatalogCtrl {

  private final PluginGroupService groupService;

  @Autowired
  public PluginCatalogCtrl(PluginGroupService groupService) {
    this.groupService = groupService;
  }


  @GetMapping
  public Flux<CatalogInfo> getAll(String id) {
    return groupService.findAll();
  }


  @GetMapping("{id}")
  public Mono<CatalogDocument> get(@PathVariable String id) {
    return groupService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<CatalogInfo> create(@RequestBody CatalogInfo groupInfo) {
    return groupService.save(groupInfo).map(CatalogDocument::toGroupInfo);
  }

  @PutMapping("{id}")
  public Mono<CatalogInfo> update(@RequestBody CatalogInfo groupInfo, String id) {
    return groupService.update(groupInfo).map(CatalogDocument::toGroupInfo);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> delete(@PathVariable String id) {
    return groupService.delete(id);
  }


  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteAll(String id) {
    return groupService.deleteAll();
  }
}
