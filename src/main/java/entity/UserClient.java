package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserClient {
  private Long id;
  private String username;
  private String password;

  public UserClient() {
  }

  public UserClient(String name, String password) {
    this.username = name;
    this.password = password;
  }

  @GeneratedValue
  @Id
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(columnDefinition = "TEXT")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(unique = true)
  public String getUsername() {
    return username;
  }

  public void setUsername(String name) {
    this.username = name;
  }
}
