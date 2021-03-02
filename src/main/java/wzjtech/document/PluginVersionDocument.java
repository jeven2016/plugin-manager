package wzjtech.document;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PluginVersionDocument {

  private String version;

  //  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
  private Instant date;

  private String requires;

  private String url;

  private String sha512sum;
}
