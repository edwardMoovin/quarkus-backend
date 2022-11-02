package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class UserWord {
  @JsonIgnore
  private Long id;
  private String word;
  private UserClient userClient;

  public UserWord() {
  }

  public UserWord(String word, UserClient userClient) {
    this.word = word;
    this.userClient = userClient;
  }

  @Id
  @GeneratedValue
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @ManyToOne
  @JoinColumn(name = "id_user")
  @JsonIgnore
  public UserClient getUserClient() {
    return userClient;
  }

  public void setUserClient(UserClient userClient) {
    this.userClient = userClient;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }
}
