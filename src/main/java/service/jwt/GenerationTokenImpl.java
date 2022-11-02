package service.jwt;

import common.CommonFuction;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonObject;
import java.security.PrivateKey;

@Service
public class GenerationTokenImpl implements GenerationToken {
  @ConfigProperty(name = "com.ard333.quarkusjwt.jwt.duration")
  public Long duration;
  @ConfigProperty(name = "mp.jwt.verify.issuer")
  public String issuer;

  @Override
  public String generationToken(String username, Long id) throws Exception {
    CommonFuction commonFuction = new CommonFuction();
    JsonObject userName = Json.createObjectBuilder().add("username", username).add("idClient", id.toString()).build();
    JsonObject json = Json.createObjectBuilder(userName).build();
    PrivateKey privateKey = commonFuction.readPrivateKey();
    JwtClaimsBuilder claimsBuilder = Jwt.claims(json);
    long currentTimeInSecs = commonFuction.currentTimeInSecs();
    claimsBuilder.issuer(issuer);
    claimsBuilder.subject(username);
    claimsBuilder.issuedAt(currentTimeInSecs);
    claimsBuilder.expiresAt(currentTimeInSecs + duration);
    return claimsBuilder.jws().sign(privateKey);
  }
}
