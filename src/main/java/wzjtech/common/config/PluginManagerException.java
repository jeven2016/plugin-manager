package wzjtech.common.config;

public class PluginManagerException extends RuntimeException {

  public PluginManagerException(String msg) {
    super(msg);
  }

  public static class DocumentNotFoundException extends PluginManagerException {

    public DocumentNotFoundException(String msg) {
      super(msg);
    }
  }

}
