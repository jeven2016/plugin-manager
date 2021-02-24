package wzjtech.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import wzjtech.document.PluginGroupDocument;

public interface PluginGroupRepo extends ReactiveMongoRepository<PluginGroupDocument, String> {

}
