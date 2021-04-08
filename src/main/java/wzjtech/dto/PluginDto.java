package wzjtech.dto;

import lombok.Getter;
import lombok.Setter;

public class PluginDto {

  @Getter
  @Setter
  public static final class PluginPureDto {
    private String name;

    private String description;

    private String provider;

    private String projectUrl;

    private boolean enabled;
  }
}
