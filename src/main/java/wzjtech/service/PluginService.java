package wzjtech.service;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wzjtech.document.PluginDocument;
import wzjtech.document.PluginGroupDocument;

@Service
public class PluginService {

  private final ReactiveMongoTemplate template;

  @Autowired
  public PluginService(ReactiveMongoTemplate template) {
    this.template = template;
  }

  /**
   * Save or update a plugin
   *
   * @param groupId plugin group id
   * @param pluginDocument plugin doc
   * @return Mono<UpdateResult>
   */
  public Mono<UpdateResult> save(String groupId, PluginDocument pluginDocument) {
    var update = new Update().addToSet("plugins", pluginDocument);
    var criteria = Criteria.where("id").is(groupId);
    return template.updateFirst(Query.query(criteria), update, PluginGroupDocument.class);
  }

}
