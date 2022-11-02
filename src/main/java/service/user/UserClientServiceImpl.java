package service.user;

import dto.Translate.ResponseWordEncrypt;
import dto.Translate.Word;
import dto.general.ErrorInfo;
import dto.login.RequestLogin;
import dto.login.ResponseLogin;
import dto.register.ResponseRegister;
import entity.UserClient;
import entity.UserWord;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserClientRepository;
import repository.UserWordRepository;
import service.encrypt.EncryptService;
import service.jwt.GenerationToken;

import java.util.List;

@Service
public class UserClientServiceImpl implements UserClientService {
  private final UserClientRepository userClientRepository;
  private final EncryptService encryptService;
  private final GenerationToken generationToken;
  private final UserWordRepository userWordRepository;

  @Autowired
  public UserClientServiceImpl(UserClientRepository userClientRepository, EncryptService encryptService, GenerationToken generationToken, UserWordRepository userWordRepository) {
    this.userClientRepository = userClientRepository;
    this.encryptService = encryptService;
    this.generationToken = generationToken;
    this.userWordRepository = userWordRepository;
  }

  @Override
  public ResponseWordEncrypt encryptWord(Word word, UserClient userClient) {
    ResponseWordEncrypt responseTranslate = new ResponseWordEncrypt();
    UserWord userWord = new UserWord(word.getWord(), userClient);
    userWord = userWordRepository.save(userWord);
    if (userWord != null) {
      responseTranslate.setResult(Boolean.TRUE);
      responseTranslate.setStatus("SUCCESS");
    } else {
      responseTranslate.setResult(Boolean.FALSE);
      responseTranslate.setStatus("ERRORSAVE");
      responseTranslate.setErrorInfo(new ErrorInfo("400", "Error when system register word"));
    }
    return responseTranslate;
  }

  @Override
  public List<UserClient> findAll() {
    return userClientRepository.findAll();
  }

  @Override
  public UserClient findByName(String name) {
    return userClientRepository.findByUsername(name);
  }

  @Override
  public UserClient getClient(JsonWebToken jwt) {
    String idClient = jwt.getClaim("idClient");
    UserClient userClient = userClientRepository.findById(Long.parseLong(idClient));
    return userClient;
  }

  @Override
  public ResponseLogin login(RequestLogin login) throws Exception {
    //Verify exist user
    ResponseLogin responseLogin = new ResponseLogin();
    UserClient userClientFind = userClientRepository.findByUsername(login.getUsername());
    if (userClientFind != null) {
      //Desencrpt password
      //String clave = encryptService.desencrypt(userClient.getPassword());
      //Encrypt simetric
      String clave = encryptService.encryptSimetric(login.getPassword());
      if (userClientFind.getPassword().equals(clave)) {
        //Generation token
        String token = generationToken.generationToken(userClientFind.getUsername(), userClientFind.getId());
        responseLogin.setResult(Boolean.TRUE);
        responseLogin.setStatus("SUCCES");
        responseLogin.setToken(token);
        responseLogin.setResult(true);
      } else {
        responseLogin.setResult(Boolean.FALSE);
        responseLogin.setStatus("INCORRECTPASSWORD");
        responseLogin.setErrorInfo(new ErrorInfo("400", "Incorrect password"));
      }
    } else {
      responseLogin.setResult(Boolean.FALSE);
      responseLogin.setStatus("NOTEXISTSUSER");
      responseLogin.setErrorInfo(new ErrorInfo("400", "The user does not exist"));
    }
    return responseLogin;
  }

  @Override
  public ResponseRegister save(RequestLogin login) {
    ResponseRegister responseRegister = new ResponseRegister();
    //Verify exist user
    UserClient userExists = userClientRepository.findByUsername(login.getUsername());
    if (userExists == null) {
      //Encript Password
      String password = encryptService.encryptSimetric(login.getPassword());
      UserClient userSave = new UserClient(login.getUsername(), password);
      //Save user
      userClientRepository.save(userSave);
      responseRegister.setResult(Boolean.TRUE);
      responseRegister.setStatus("SUCCESS");
    } else {
      //Exists user
      responseRegister.setResult(Boolean.FALSE);
      responseRegister.setStatus("USEREXISTS");
      responseRegister.setErrorInfo(new ErrorInfo("400", "The user is previously registered"));
    }
    return responseRegister;
  }

  @Override
  public ResponseWordEncrypt words(UserClient userClient) {
    ResponseWordEncrypt responseWordEncrypt = new ResponseWordEncrypt();
    //List user
    List<UserWord> words = userWordRepository.findAllByUserClient(userClient);
    responseWordEncrypt.setListWord(words);
    responseWordEncrypt.setResult(true);
    responseWordEncrypt.setStatus("SUCCESS");
    return responseWordEncrypt;
  }
}
