package wzjtech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wzjtech.document.User;

@Service
public class TestService {

  @Autowired
  ReactiveMongoTemplate template;

//  ReactiveMongoDatabaseUtils utils;

  public Mono<User> get() {
    var user = new User();
    user.setAge(20);
    user.setName("wzj");
    return template.insert(user);
  }

}
