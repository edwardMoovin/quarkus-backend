package dto.login;

import dto.general.BaseResponse;

public class ResponseLogin extends BaseResponse {
  String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
