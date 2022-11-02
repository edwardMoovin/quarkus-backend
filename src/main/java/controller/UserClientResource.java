package controller;

import dto.Translate.ResponseWordEncrypt;
import dto.Translate.Word;
import dto.general.ErrorInfo;
import dto.login.RequestLogin;
import dto.login.ResponseLogin;
import dto.register.ResponseRegister;
import entity.UserClient;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.springframework.beans.factory.annotation.Autowired;
import service.encrypt.EncryptService;
import service.jwt.GenerationToken;
import service.user.UserClientService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/users")
public class UserClientResource {
  private final UserClientService userClientService;
  private final EncryptService encryptService;
  private final GenerationToken generationToken;
  @Inject
  JsonWebToken jwt;

  @Autowired
  public UserClientResource(UserClientService userClientService, EncryptService encryptService, GenerationToken generationToken) {
    this.userClientService = userClientService;
    this.encryptService = encryptService;
    this.generationToken = generationToken;
  }

  @PermitAll
  @POST
  @Path("/encrypt")
  public ResponseWordEncrypt encryptWord(Word word) {
    //Find user
    ResponseWordEncrypt responseTranslate = new ResponseWordEncrypt();
    UserClient userClient = userClientService.getClient(jwt);
    if (userClient != null) {
      responseTranslate = userClientService.encryptWord(word, userClient);
    } else {
      responseTranslate.setResult(Boolean.FALSE);
      responseTranslate.setStatus("NOEXISTSUSER");
      responseTranslate.setErrorInfo(new ErrorInfo("400", "Error when system register word"));
    }
    return responseTranslate;
  }

  @GET
  public List<UserClient> findAll(@Context SecurityContext ctx) {
    return userClientService.findAll();
  }

  @PermitAll
  @GET
  @Path("/words")
  public ResponseWordEncrypt listWord(Word word) {
    //Find user
    ResponseWordEncrypt responseTranslate = new ResponseWordEncrypt();
    UserClient userClient = userClientService.getClient(jwt);
    if (userClient != null) {
      responseTranslate = userClientService.words(userClient);
    } else {
      responseTranslate.setResult(Boolean.FALSE);
      responseTranslate.setStatus("NOEXISTSUSER");
      responseTranslate.setErrorInfo(new ErrorInfo("400", "Error when system register word"));
    }
    return responseTranslate;
  }

  @PermitAll
  @POST
  @Path("/login")
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseLogin login(RequestLogin login) throws Exception {
    ResponseLogin responseLogin = userClientService.login(login);
    return responseLogin;
  }

  @PermitAll
  @POST
  @Path("/save")
  public ResponseRegister save(RequestLogin login) {
    return userClientService.save(login);
  }
}
