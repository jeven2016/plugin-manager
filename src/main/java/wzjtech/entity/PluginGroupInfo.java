package wzjtech.entity;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
public class PluginGroupInfo {

  @MongoId
  private String id;

  @Indexed(unique = true)
  private String name;

  private String description;

  @Version
  private Long version;

  @CreatedDate
  private Instant createdDate;
}
