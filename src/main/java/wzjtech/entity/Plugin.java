package wzjtech.entity;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plugin {

  public String id;
  public String name;
  public String description;
  public String provider;
  public String projectUrl;
  public List<PluginVersion> versions;
}
