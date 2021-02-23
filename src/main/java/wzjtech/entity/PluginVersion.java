package wzjtech.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PluginVersion {

  private String version;
  private Date date;
  private String requires;
  private String url;
  private String sha512sum;
}
