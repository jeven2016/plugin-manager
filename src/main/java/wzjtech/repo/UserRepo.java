package wzjtech.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import wzjtech.entity.User;

//ReactiveMongoRepository
//MongoRepository
//Repository
public interface UserRepo extends ReactiveMongoRepository<User, String> {

}