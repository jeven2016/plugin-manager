package wzjtech.document;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

//the @Indexed works for top level field, but not for an embedded document.
//that means you need to create a compound index and set sparse to ture.
//Sparse indexes only contain entries for documents that have the indexed field,
// even if the index field contains a null value.
//Here What you we is a sparse index which only indexes actual values
// (and ignores documents with null for that field).
//
// solve this problem: no more than groups can be inserted with blank plugins, a exception occurs
//to complain the null value of plugins.pluginVersions.version is duplicated
@CompoundIndex(name = "group_plugins_name", def = "{'plugins.pluginVersions.version' : 1}", unique = true, sparse = true)
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
