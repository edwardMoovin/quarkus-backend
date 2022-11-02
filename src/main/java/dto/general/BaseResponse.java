package dto.general;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse {
  @JsonProperty("result")
  private Boolean result;
  private String status;
  private ErrorInfo errorInfo;

  public BaseResponse() {
    result = false;
    errorInfo = new ErrorInfo();
  }

  public ErrorInfo getErrorInfo() {
    return errorInfo;
  }

  public void setErrorInfo(ErrorInfo errorInfo) {
    this.errorInfo = errorInfo;
  }

  public Boolean getResult() {
    return result;
  }

  public void setResult(Boolean result) {
    this.result = result;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "BaseResponse{" + "result=" + result + ", status='" + status + '\'' + ", errorInfo=" + errorInfo + '}';
  }
}
