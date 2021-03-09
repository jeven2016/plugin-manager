package wzjtech.repo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import wzjtech.document.PluginGroupDocument;

public interface PluginGroupRepo extends ReactiveMongoRepository<PluginGroupDocument, String> {

  /**
   * Solution1:
   * The fields property in @Query will cause only the fields set to 1 being returned. the
   * format: @Query(value="{ 'field1' : ?0 }",fields="{ 'field2' : 1, 'field2' : 1}")
   *
   *
   * Solution2:
   *  using aggregate with $project
   * @return Flux<PluginGroupDocument>
   */
  @Query(value = "{}", fields = "{plugins: 0}")
  Flux<PluginGroupDocument> findPureGroups();
}
