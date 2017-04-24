package stratego.network;

import java.io.InputStream;

import java.util.Arrays;

import java.security.MessageDigest;
import java.security.spec.X509EncodedKeySpec;
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

  public static final int X509SIZE = 162;
  private static final int RSAKeySize = 128;
  private static final int AESKeySize = 16;
  private static PrivateKey rsaDecrypt = null;
  private static PublicKey rsaEncrypt = null;
  private static PublicKey pubKey = null;
  private static PublicKey hostKey = null;

  private static MessageDigest hash;

  static{
    try{
      hash = MessageDigest.getInstance("SHA-256");
      rsaEncrypt = getPublicKey();
    } catch(Exception e){
      e.printStackTrace();
    }
  }

  public static byte[] hashBytes(byte[] data){
    return hash.digest(data);
  }

  protected static byte[] securePacket(){
    SecretKey aes = getAESKey();
    KeyPair pair = null;
    byte[] data = null;
    byte[] temp = null;

    try{
      KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
      gen.initialize(RSAKeySize*8);
      pair = gen.genKeyPair();
      rsaDecrypt = pair.getPrivate();

      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, aes);
      pubKey = pair.getPublic();

      byte[] rsaKey = pubKey.getEncoded();

      temp = new byte[rsaKey.length + 5];
      temp[4] = Networker.SECURE;
      for(int i = 0; i < rsaKey.length; i++){
        temp[i+5] = rsaKey[i];
      }

      temp = cipher.doFinal(temp, 0, temp.length);

      data = new byte[temp.length + RSAKeySize];

      cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.ENCRYPT_MODE, rsaEncrypt);
      cipher.doFinal(aes.getEncoded(), 0, aes.getEncoded().length, data, 0);

      for(int i = 0; i < temp.length; i++){
        data[RSAKeySize + i] = temp[i];
      }

    } catch(Exception e){
      e.printStackTrace();
    }

    return data;
  }

  protected static boolean makeSecure(Packet p){
    if(p.getType() != Networker.SECURE){
      return false;
    }
    byte[] data = p.getData();
    int id = data[0] << 24 | data[1] << 16 | data[2] << 8 | data[3];
    Networker.setID(id);
    return true;
  }

  private static PublicKey getPublicKey(){
    InputStream stream = null;
    PublicKey key = null;

    try{

      stream = SecurityManager.class.getClass().getResourceAsStream("/stratego/network/onlinekey");
      byte[] data = new byte[X509SIZE];
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

  public static byte[] encrypt(byte[] data, SocketAddress address){
    SecretKey aesKey = getAESKey();
    byte[] ans = null;
    try{
      byte[] aesCrypt;
      byte[] temp;

      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, aesKey);
      temp = cipher.doFinal(data, 0, data.length);

      cipher = Cipher.getInstance("RSA");
      if(address.equals(Networker.host)){
        cipher.init(Cipher.ENCRYPT_MODE, hostkey);
      }else{
        cipher.init(Cipher.ENCRYPT_MODE, rsaEncrypt);
      }
      aesCrypt = aesKey.getEncoded();
      aesCrypt = cipher.doFinal(aesCrypt, 0, aesCrypt.length);

      ans = new byte[aesCrypt.length + temp.length];
      for(int i = 0; i < aesCrypt.length; i++){
        ans[i] = aesCrypt[i];
      }
      for(int i = 0; i < temp.length; i++){
        ans[i+aesCrypt.length] = temp[i];
      }

    } catch(Exception e){
      System.out.println(e.getMessage());
    }

    return ans;
  }

  public static byte[] decrypt(byte[] data, SocketAddress address){
    byte[] ans = null;
    byte[] AES = null;

    try{
      Cipher cipher = Cipher.getInstance("RSA");
      if(address.equals(Networker.host)){
        cipher.init(Cipher.DECRYPT_MODE, hostkey);
      }else{
        cipher.init(Cipher.DECRYPT_MODE, rsaDecrypt);
      }
      AES = cipher.doFinal(data, 0, RSAKeySize);

      SecretKey AESKey = new SecretKeySpec(AES, 0, AES.length, "AES");

      cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, AESKey);
      ans = cipher.doFinal(data, RSAKeySize, data.length - RSAKeySize);
    } catch(Exception e){
      e.printStackTrace();
      System.out.println(e.getMessage());
    }
    return ans;

  }

  public static boolean addHostKey(byte[] key){
    try{
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
      KeyFactory factory = KeyFactory.getInstance("RSA");
      hostKey = factory.generatePublic(keySpec);
      return true;
    } catch(Exception e){
      e.printStackTrace();
    }
    return false;
  }

}
