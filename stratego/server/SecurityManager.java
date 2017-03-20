package stratego.server;

import java.io.InputStream;

import java.util.Arrays;

import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;
import java.security.KeyFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.KeyGenerator;


public class SecurityManager{

  private static final int RSAKeySize = 128;
  private static final int AESKeySize = 16;
  private static PrivateKey rsaDecrypt = null;

  static{
    rsaDecrypt = getPrivateKey();
  }


  private static PrivateKey getPrivateKey(){
    InputStream stream = null;
    PrivateKey key = null;

    try{

      stream = SecurityManager.class.getClass().getResourceAsStream("stratego/server/serverkey");
      byte[] data = new byte[RSAKeySize];
      stream.read(data);
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(data);
      KeyFactory factory = KeyFactory.getInstance("RSA");
      key = factory.generatePrivate(keySpec);

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

  public static byte[] encrypt(byte[] data, byte[] key){
    byte[] ans = null;
    byte[] cryptAES;
    byte[] temp;
    PublicKey RSAKey;
    SecretKey aesKey = getAESKey();

    try{
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
      KeyFactory factory = KeyFactory.getInstance("RSA");
      RSAKey = factory.generatePublic(keySpec);

      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, aesKey);
      temp = cipher.doFinal(data, 0, data.length);

      cryptAES = aesKey.getEncoded();

      cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.ENCRYPT_MODE, RSAKey);
      cryptAES = cipher.doFinal(cryptAES, 0, cryptAES.length);

      ans = new byte[temp.length + cryptAES.length];

      for(int i = 0; i < cryptAES.length; i++){
        ans[i] = cryptAES[i];
      }

      for(int i = 0; i < temp.length; i++){
        ans[i + cryptAES.length] = temp[i];
      }

    } catch(Exception e){
      System.out.println(e.getMessage());
    }

    return ans;

  }

  public static byte[] decrypt(byte[] data){
    byte[] ans = null;
    byte[] AES = Arrays.copyOfRange(data, 0, RSAKeySize);

    try{
      Cipher cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.DECRYPT_MODE, rsaDecrypt);
      AES = cipher.doFinal(AES, 0, RSAKeySize);

      SecretKey AESKey = new SecretKeySpec(AES, 0, AES.length, "AES");

      cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, AESKey);
      ans = cipher.doFinal(data, RSAKeySize, data.length);
    } catch(Exception e){
      System.out.println(e.getMessage());
    }
    return ans;
  }

}
