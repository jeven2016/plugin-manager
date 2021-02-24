package wzjtech.service;

import org.pf4j.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wzjtech.common.config.PluginManagerException;
import wzjtech.common.config.PluginManagerException.DocumentNotFoundException;
import wzjtech.document.PluginGroupDocument;
import wzjtech.document.PluginGroupInfo;
import wzjtech.repo.PluginGroupRepo;

@Service
public class PluginGroupService {

  private final PluginGroupRepo repo;

  @Autowired
  public PluginGroupService(PluginGroupRepo repo) {
    this.repo = repo;
  }

  public Mono<PluginGroupDocument> findById(String id) {
    return repo.findById(id).switchIfEmpty(Mono.error(new DocumentNotFoundException("No document exists with id " + id)));
  }

  public Flux<PluginGroupInfo> findAll() {
    return repo.findAll().map(PluginGroupDocument::toGroupInfo);
  }

  public Mono<PluginGroupDocument> save(PluginGroupInfo groupInfo) {
    return repo.save(PluginGroupDocument.from(groupInfo));
  }

  public Mono<PluginGroupDocument> update(PluginGroupInfo groupInfo) {
    if (StringUtils.isNullOrEmpty(groupInfo.getId())) {
      return Mono.error(new PluginManagerException("The id is required to update"));
    }
    return repo.findById(groupInfo.getId())
        .switchIfEmpty(Mono.error(
            new DocumentNotFoundException("No document exists with id" + groupInfo.getId())))
        .doOnNext(groupDocument -> {
          groupDocument.setId(groupInfo.getId());
          groupDocument.setName(groupInfo.getName());
          groupDocument.setDescription(groupInfo.getDescription());
        })
        .flatMap(repo::save);
  }

  public Mono<Void> delete(String groupId) {
    return repo.deleteById(groupId);
  }

  public Mono<Void> deleteAll() {
    return repo.deleteAll();
  }
}
