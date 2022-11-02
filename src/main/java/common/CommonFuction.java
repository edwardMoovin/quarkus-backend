package common;

import org.eclipse.microprofile.config.ConfigProvider;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

public class CommonFuction {
  private static byte[] toByteArray(File file) throws IOException {
    // java 7's try-with-resources statement
    try (FileInputStream in = new FileInputStream(file);
         FileChannel channel = in.getChannel()) {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      channel.transferTo(0, channel.size(), Channels.newChannel(out));
      return out.toByteArray();
    }
  }

  private static byte[] toDecodedBase64ByteArray(byte[] base64EncodedByteArray) {
    String text = new String(base64EncodedByteArray, StandardCharsets.UTF_8);
    byte[] prueba = DatatypeConverter.parseBase64Binary(text);
    String text2 = new String(prueba, StandardCharsets.UTF_8);
    return prueba;
  }

  public int currentTimeInSecs() {
    long currentTimeMS = System.currentTimeMillis();
    return (int) (currentTimeMS / 1000);
  }

  private InputStream getFileFromResourceAsStream(String fileName) {
    // The class loader that loaded the class
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);
    // the stream holding the file content
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      return inputStream;
    }
  }

  public PrivateKey readPrivateKey() {
    try {
      String privateKeyLocation = ConfigProvider.getConfig().getValue("privatekey.location", String.class);
      String fileName = privateKeyLocation;
      System.out.println("getResourceAsStream : " + fileName);
      InputStream is = getFileFromResourceAsStream(fileName);
      byte[] privateKeyBytes = toDecodedBase64ByteArray(is.readAllBytes());
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      KeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
      PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
      return privateKey;
    } catch (Exception ex) {
      return null;
    }
  }

  public PublicKey readPublicKey() {
    try {
      String publicKeyLocation = ConfigProvider.getConfig().getValue("publickey.location", String.class);
      String fileName = publicKeyLocation;
      System.out.println("getResourceAsStream : " + fileName);
      InputStream is = getFileFromResourceAsStream(fileName);
      byte[] publicKeyBytes = toDecodedBase64ByteArray(is.readAllBytes());
      //KeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
      CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
      Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(publicKeyBytes));
      PublicKey publicKey = certificate.getPublicKey();
      return publicKey;
    } catch (Exception ex) {
      return null;
    }
  }
}
