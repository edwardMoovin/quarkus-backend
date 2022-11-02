package service.encrypt;

public interface EncryptService {
  String desencrypt(String word);

  String encrypt(String word);

  String encryptSimetric(String word);
}
