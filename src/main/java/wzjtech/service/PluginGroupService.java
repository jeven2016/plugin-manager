package wzjtech.service;

import org.pf4j.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wzjtech.common.config.PluginManagerException;
import wzjtech.common.config.PluginManagerException.DocumentNotFoundException;
import wzjtech.document.CatalogDocument;
import wzjtech.document.CatalogInfo;
import wzjtech.repo.CatalogRepo;

import java.util.HashMap;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class PluginGroupService {

  private final CatalogRepo repo;
  private final ReactiveMongoTemplate template;

  @Autowired
  public PluginGroupService(CatalogRepo repo, ReactiveMongoTemplate template) {
    this.repo = repo;
    this.template = template;
  }

  public Mono<CatalogDocument> findById(String id) {
    return repo.findById(id)
        .switchIfEmpty(
            Mono.error(new DocumentNotFoundException("No document exists with id " + id)));
  }

  public Flux<CatalogInfo> findAll() {
    return repo.findPureGroups().map(CatalogDocument::toGroupInfo);
  }

  public Mono<CatalogDocument> save(CatalogInfo groupInfo) {
    return repo.save(CatalogDocument.from(groupInfo));
  }


  /**
   * Only update the group itself
   *
   * @param groupInfo PluginGroupInfo
   * @return Mono<PluginGroupDocument>
   */
  public Mono<CatalogDocument> update(CatalogInfo groupInfo) {
    if (StringUtils.isNullOrEmpty(groupInfo.getId())) {
      return Mono.error(new PluginManagerException("The id is required to update"));
    }
    return findById(groupInfo.getId())
        .doOnNext(
            groupDocument -> {
              groupDocument.setId(groupInfo.getId());
              groupDocument.setName(groupInfo.getName());
              groupDocument.setEnablePlugin(groupInfo.getEnablePlugin());
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
