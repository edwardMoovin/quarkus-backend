package service.encrypt;

import common.CommonFuction;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Service
public class EncryptServiceImpl implements EncryptService {
  @Override
  public String desencrypt(String word) {
    CommonFuction commonFuction = new CommonFuction();
    PrivateKey privateKey = commonFuction.readPrivateKey();
    try {
      Cipher decrypt = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
      decrypt.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] word1 = Base64.getDecoder().decode(word.getBytes());
      String decryptedMessage = new String(decrypt.doFinal(word1));
      return decryptedMessage;
    } catch (Exception ex) {
      return null;
    }
  }

  @Override
  public String encrypt(String word) {
    CommonFuction commonFuction = new CommonFuction();
    PublicKey publicKey = commonFuction.readPublicKey();
    try {
      Cipher encrypt = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
      // init with the *public key*!
      encrypt.init(Cipher.ENCRYPT_MODE, publicKey);
      // encrypt with known character encoding, you should probably use hybrid cryptography instead
      byte[] encryptedMessage = encrypt.doFinal(word.getBytes(StandardCharsets.UTF_8));
      String text = new String(Base64.getEncoder().encode(encryptedMessage));
      return text;
    } catch (Exception ex) {
      return null;
    }
  }

  public String encryptSimetric(String word) {
    CommonFuction commonFuction = new CommonFuction();
    PrivateKey privateKey = commonFuction.readPrivateKey();
    try {
      Cipher encrypt = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      // init with the *public key*!
      encrypt.init(Cipher.ENCRYPT_MODE, privateKey);
      // encrypt with known character encoding, you should probably use hybrid cryptography instead
      byte[] encryptedMessage = encrypt.doFinal(word.getBytes(StandardCharsets.UTF_8));
      String text = new String(Base64.getEncoder().encode(encryptedMessage));
      return text;
    } catch (Exception ex) {
      return null;
    }
  }
}
