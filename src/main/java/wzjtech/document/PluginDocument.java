package wzjtech.document;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
public class PluginDocument {

  @Indexed
  public String name;

  public String description;

  public String provider;

  public String projectUrl;

  public List<PluginVersionDocument> pluginVersions = new ArrayList<>();
}
