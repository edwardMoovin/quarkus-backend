package dto.Translate;

import dto.general.BaseResponse;
import entity.UserWord;

import java.util.List;

public class ResponseWordEncrypt extends BaseResponse {
  private List<UserWord> listWord;

  public List<UserWord> getListWord() {
    return listWord;
  }

  public void setListWord(List<UserWord> listWord) {
    this.listWord = listWord;
  }
}
