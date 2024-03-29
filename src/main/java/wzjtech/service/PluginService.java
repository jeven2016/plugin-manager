package wzjtech.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wzjtech.common.config.PluginManagerException;
import wzjtech.document.PluginDocument;
import wzjtech.document.CatalogDocument;
import wzjtech.document.PluginVersionDocument;
import wzjtech.dto.PluginDto;

import java.util.HashMap;

@Service
public class PluginService {

  private final ReactiveMongoTemplate template;

  @Autowired
  public PluginService(ReactiveMongoTemplate template) {
    this.template = template;
  }


  /**
   * db.catalog.aggregate([
   * {$match:{ _id: ObjectId("6061aed87210fe3eee0cd66c")}},
   * {$project:  {plugins: 1}},
   * {$unwind: "$plugins"},
   * {$replaceRoot: {newRoot: "$plugins"}},
   * {$addFields: {versionCount: {$size: "$pluginVersions"}}},
   * {$project: {pluginVersions: 0, provider: 0, description: 0}}
   * ])
   *
   * @return Flux
   */
  public Flux<HashMap> findPlugins(String id) {
    Aggregation aggregation = newAggregation(
        match(Criteria.where("id").is(id)),
        project("plugins"),
        unwind("$plugins"),
        replaceRoot("$plugins"),
        addFields().addField("versionCount")
            .withValueOf(new BasicDBObject("$size", "$pluginVersions"))
            .build(),
        project("name", "versionCount", "enabled")
    );
    return template.aggregate(aggregation, CatalogDocument.class, HashMap.class);
  }

  /**
   * Save or update a plugin
   *
   * @param groupId        plugin group id
   * @param pluginDocument plugin doc
   * @return Mono<UpdateResult>
   */
  public Mono<UpdateResult> save(String groupId, PluginDocument pluginDocument) {
    var update = new Update().addToSet("plugins", pluginDocument);
    var criteria = where("id").is(groupId);
    return template.updateFirst(query(criteria), update, CatalogDocument.class);
  }

  /**
   * Update a plugin itself
   *
   * @param groupId        plugin group id
   * @param pluginDocument plugin document
   * @return result
   */
  public Mono<UpdateResult> update(String groupId, String pluginName,
                                   PluginDocument pluginDocument) {
    var update = new Update()
        .set("plugins.$.name", pluginDocument.getName())
        .set("plugins.$.description", pluginDocument.getDescription())
        .set("plugins.$.enabled", pluginDocument.isEnabled())
        .set("plugins.$.provider", pluginDocument.getProvider())
        .set("plugins.$.projectUrl", pluginDocument.getProjectUrl());

    //find the group by groupId and update the element in plugins list by plugin name
    var query = createQuery(groupId, pluginName);

    return template.exists(query, CatalogDocument.class).flatMap(exists -> {
      if (!exists) {
        return Mono.error(pluginNotFound(pluginName));
      } else {
        return template.updateFirst(query, update, CatalogDocument.class);
      }
    });
  }

  /**
   * Delete a plugin and releases
   *
   * @param groupId    group id
   * @param pluginName plugin name
   * @return Mono<DeleteResult>
   */
  public Mono<UpdateResult> delete(String groupId, String pluginName) {
    var query = createQuery(groupId, pluginName);
    var update = new Update().pull("plugins", new BasicDBObject("name", pluginName));

    return template.exists(query, CatalogDocument.class).flatMap(exists -> {
      if (!exists) {
        return Mono.error(pluginNotFound(pluginName));
      } else {
        return template.updateFirst(query, update, CatalogDocument.class);
      }
    });
  }

  public Mono<UpdateResult> saveVersion(String groupId, String pluginName,
                                        PluginVersionDocument version) {
    var query = createQuery(groupId, pluginName);
    var update = new Update().addToSet("plugins.$.pluginVersions", version);

    return template.updateFirst(query, update, CatalogDocument.class);
  }

  private Query createQuery(String groupId, String pluginName) {
    //find the group by groupId and update the element in plugins list by plugin name
    return query(where("id").is(groupId))
        .addCriteria(where("plugins").elemMatch(where("name").is(pluginName)));
  }

  private PluginManagerException pluginNotFound(String pluginName) {
    return new PluginManagerException.DocumentNotFoundException(
        "Plugin " + pluginName + " not found");
  }


  /**
   * {plugins: [ {pluginVersions: [version: '0.1.']}]}
   */
  public Mono<UpdateResult> deleteVersion(String groupId, String pluginName, String version) {
    var query = createQuery(groupId, pluginName);

    var update = new Update()
        .pull("plugins.pluginVersions", new BasicDBObject("version", version));
    return template.updateFirst(query, update, CatalogDocument.class);
  }

  /**
   * {plugins: [ {pluginVersions: [version: '0.1.']}]}
   */
  public Mono<UpdateResult> deleteVersion3(String groupId, String pluginName, String version) {
    var qry = query(where("id").is(groupId))
        .addCriteria(where("plugins").elemMatch(where("name").is(pluginName)
            .and("pluginVersions").elemMatch(where("version").is(version))));

    var update = new Update()
        .pull("plugins.$.pluginVersions", qry);
    return template.updateFirst(qry, update, CatalogDocument.class);
  }

  /**
   * {plugins: [ {pluginVersions: [version: '0.1.']}]}
   */
  public Mono<UpdateResult> deleteVersion2(String groupId, String pluginName, String version) {
    var qry = createQuery(groupId, pluginName);//find group

    var update = new Update()
        .pull("plugins.$.pluginVersions", where("version").is("0.1.0"));
    return template.updateFirst(qry, update, CatalogDocument.class);
  }

  public Mono<PluginDto.PluginPureDto> findByName(String groupId, String pluginName) {
    //1. 根据catalog id 和plugin name到document
    //2. 不需要返回pluginVersions
    //3. unwind将数组扁平化出来
    //4. 找到匹配的plugin
    //5. 把plugin结构移到根节点
    //6. 返回需要的plugin字段
    var aggr = newAggregation(
        match(where("id").is(groupId).and("plugins.name").is(pluginName)),
        project().andExclude("plugins.pluginVersions"),
        unwind("$plugins"),
        match(where("plugins.name").is(pluginName)),
        replaceRoot("$plugins")
    );
    return template.aggregate(aggr, CatalogDocument.class, PluginDto.PluginPureDto.class).singleOrEmpty();
  }
}
