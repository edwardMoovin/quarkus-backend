package dto.general;

public class ErrorInfo {
  private String code;
  private String message;

  public ErrorInfo(String pCode, String pMessage) {
    code = pCode;
    message = pMessage;
  }

  public ErrorInfo() {
    code = null;
    message = null;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "ErrorInfo{" + "code='" + code + '\'' + ", message='" + message + '\'' + '}';
  }
}
