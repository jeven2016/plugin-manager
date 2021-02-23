package wzjtech.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import wzjtech.entity.PluginGroup;

public interface PluginGroupRepo extends ReactiveMongoRepository<PluginGroup, String> {

}
