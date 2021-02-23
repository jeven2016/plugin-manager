package wzjtech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wzjtech.entity.PluginGroup;
import wzjtech.entity.PluginGroupInfo;
import wzjtech.repo.PluginGroupRepo;

@Service
public class PluginGroupService {

  private final PluginGroupRepo repo;

  @Autowired
  public PluginGroupService(PluginGroupRepo repo) {
    this.repo = repo;
  }

  public Mono<PluginGroup> create(PluginGroupInfo groupInfo) {
    return repo.save(PluginGroup.from(groupInfo));
  }

}
