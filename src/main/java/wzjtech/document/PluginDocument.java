package wzjtech.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PluginDocument {

  //当plugin不存在时，会报duplicate key的错, 添加sparse
  @Indexed(unique = true, sparse = true)
  private String name;

  private String description;

  private String provider;

  private String projectUrl;

  private boolean enabled;

  private List<PluginVersionDocument> pluginVersions = new ArrayList<>();
}
