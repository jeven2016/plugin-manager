package wzjtech.document;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "plugin_group")
@Getter
@Setter
public class PluginGroupDocument extends PluginGroupInfo {

  private List<PluginDocument> plugins = new ArrayList<>();

  public static PluginGroupDocument from(PluginGroupInfo groupInfo) {
    var group = new PluginGroupDocument();
    group.setId(groupInfo.getId());
    group.setName(groupInfo.getName());
    group.setDescription(groupInfo.getDescription());
    group.setVersion(groupInfo.getVersion());
    return group;
  }

  public PluginGroupInfo toGroupInfo() {
    var groupInfo = new PluginGroupInfo();
    groupInfo.setId(getId());
    groupInfo.setName(getName());
    groupInfo.setDescription(getDescription());
    groupInfo.setCreatedDate(getCreatedDate());
    groupInfo.setVersion(getVersion());
    return groupInfo;
  }
}
