package wzjtech.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.index.TextIndexed;

@Getter
@Setter
public class PluginDocument {

  @Indexed(unique = true)
  public String name;

  public String description;

  public String provider;

  public String projectUrl;

  public List<PluginVersionDocument> pluginVersions = new ArrayList<>();
}
