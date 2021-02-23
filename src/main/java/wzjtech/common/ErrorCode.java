package wzjtech.common;

public enum ErrorCode {
  DUPLICATED_ENTITY("Duplicated entity");

  private String value;

  ErrorCode(String value) {
    this.value = value;
  }

  public String getValue(){
    return value;
  }
}
