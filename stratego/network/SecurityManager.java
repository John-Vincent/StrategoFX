package stratego.network;

import java.io.InputStream;

import java.security.MessageDigest;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;
import java.security.KeyFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;


public class SecurityManager{

  private static final int RSAKeySize = 32;
  private static final int AESKeySize = 16;
  private static PrivateKey rsa = null;

  private static MessageDigest hash;

  static{
    try{
      hash = MessageDigest.getInstance("SHA-256");
    } catch(Exception e){
      e.printStackTrace();
    }
  }

  public static byte[] hashBytes(byte[] data){
    return hash.digest(data);
  }

  protected static byte[] securePacket(){
    PublicKey oldkey = getPublicKey();
    SecretKey aes = getAESKey();
    KeyPair pair = null;
    byte[] data = null;

    try{
      KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
      gen.initialize(RSAKeySize*8);
      pair = gen.genKeyPair();


      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, aes);
      rsa = pair.getPrivate();

      data = new byte[RSAKeySize + cipher.getOutputSize(RSAKeySize) + 1];
      data[0] = Networker.SECURE;

      cipher.doFinal(pair.getPublic().getEncoded(), 0, RSAKeySize, data, RSAKeySize + 1);

      cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.ENCRYPT_MODE, oldkey);
      cipher.doFinal(aes.getEncoded(), 0, AESKeySize, data, 1);

    } catch(Exception e){
      e.printStackTrace();
    }
    return data;
  }

  protected static byte[] verifySecurity(byte[] data){
    return null;
  }

  private static PublicKey getPublicKey(){
    InputStream stream = null;
    PublicKey key = null;

    try{

      stream = SecurityManager.class.getClass().getResourceAsStream("/stratego/network/onlinekey");
      byte[] data = new byte[RSAKeySize];
      stream.read(data);
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(data);
      KeyFactory factory = KeyFactory.getInstance("RSA");
      key = factory.generatePublic(keySpec);

    } catch(Exception e){
      e.printStackTrace();
    } finally{
      try{stream.close();} catch(Exception e){}
    }

    return key;
  }

  private static SecretKey getAESKey(){
    try{
      KeyGenerator gen = KeyGenerator.getInstance("AES");
      return gen.generateKey();
    } catch(Exception e){ e.printStackTrace();}
    return null;
  }

}
