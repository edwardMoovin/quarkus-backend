package service.user;

import dto.Translate.ResponseWordEncrypt;
import dto.Translate.Word;
import dto.login.RequestLogin;
import dto.login.ResponseLogin;
import dto.register.ResponseRegister;
import entity.UserClient;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

public interface UserClientService {
  ResponseWordEncrypt encryptWord(Word word, UserClient userClient);

  List<UserClient> findAll();

  UserClient findByName(String name);

  UserClient getClient(JsonWebToken jwt);

  ResponseLogin login(RequestLogin login) throws Exception;

  ResponseRegister save(RequestLogin login);

  ResponseWordEncrypt words(UserClient userClient);
}
