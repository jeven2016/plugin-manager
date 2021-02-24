package wzjtech.document;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Version;

@Getter
@Setter
public class PluginVersionDocument {

  @Version
  private String version;

  private Date date;

  private String requires;

  private String url;

  private String sha512sum;
}
