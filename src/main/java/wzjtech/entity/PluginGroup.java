package wzjtech.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "plugin_group")
@Getter
@Setter
public class PluginGroup extends PluginGroupInfo {

  private List<Plugin> plugins = new ArrayList<>();

  public static PluginGroup from(PluginGroupInfo groupInfo) {
    var group = new PluginGroup();
    group.setName(groupInfo.getName());
    group.setDescription(groupInfo.getDescription());

    return group;
  }

  public PluginGroupInfo toGroupInfo() {
    var groupInfo = new PluginGroupInfo();
    groupInfo.setId(getId());
    groupInfo.setName(getName());
    groupInfo.setDescription(getDescription());
    groupInfo.setCreatedDate(getCreatedDate());
    return groupInfo;
  }
}
