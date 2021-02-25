package wzjtech.service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
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
    var criteria = where("id").is(groupId);
    return template.updateFirst(query(criteria), update, PluginGroupDocument.class);
  }

  /**
   * Update a plugin itself
   *
   * @param groupId plugin group id
   * @param pluginDocument plugin document
   * @return result
   */
  public Mono<UpdateResult> update(String groupId, String pluginName,
      PluginDocument pluginDocument) {
    var update = new Update()
        .set("plugins.$.name", pluginDocument.getName())
        .set("plugins.$.description", pluginDocument.getDescription())
        .set("plugins.$.provider", pluginDocument.getProvider())
        .set("plugins.$.projectUrl", pluginDocument.getProjectUrl());

    //find the group by groupId and update the element in plugins list by plugin name
    var query = query(where("id").is(groupId))
        .addCriteria(where("plugins").elemMatch(where("name").is(pluginName)));

    return template.updateFirst(query, update, PluginGroupDocument.class);
  }

}
