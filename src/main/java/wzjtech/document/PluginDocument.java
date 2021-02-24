package wzjtech.document;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
public class PluginDocument {

  @MongoId
  public String id;
  public String name;
  public String description;
  public String provider;
  public String projectUrl;
  
  @Version
  private Long version;

  public List<PluginVersionDocument> pluginVersions;
}
