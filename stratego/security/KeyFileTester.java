package stratego.security;

import java.security.*;
import java.security.spec.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class KeyFileTester{

  private static final String publicKeyPath = "onlinekey";
  private static final String privateKeyPath = "serverkey";
  private static final String algorithm = "RSA";
  private static final String shortTestPhrase = "testing the RSA";
  private static final String longTestPhrase = "testing loading the keys adding more stuff asdflkashdf aosdfhqow fiodjf asdfoihsdfojhasdofisdoif  " +
    "asdfasdlfkjas flhasd kjhasd jklhasdf ljashdflas dflkjsdh laksdjhfskldjfh ldkjfhasldkfjh sldkjfhsldk jhasdlkfjh dlfkjh asdfkjh knbkbcasb  " +
    "askdfhj gasdfhjldkd vjbnaksjdhf asdfjkl hasdf kljasdhf asdfh aslkfh asdjkfh askldjfh asdf hasdf asdjkfh asdlfjhaskdfh asdkfjhasdlkfh asdf" +
    "asldjkfh asjkldfh asdklfh hjklbvaw bhvipusdhf sadjfh sdkjlfba lsjbvak lsjdblaks dasjkdf asdkjfh askldjfh as;dflkasdjkfhasdlkfhj  djklfhsdf";

  private static PublicKey publicKey = null;
  private static PrivateKey privateKey = null;
  private static SecretKey aesKey;


  private static SecretKey getAESKey(){
    try{
      javax.crypto.KeyGenerator gen = javax.crypto.KeyGenerator.getInstance("AES");
      return gen.generateKey();
    } catch(Exception e){ e.printStackTrace();}
    return null;
  }

  private static PublicKey getPublicKey(){
    FileInputStream stream = null;
    PublicKey key = null;

    try{

      stream = new FileInputStream(publicKeyPath);
      byte[] data = new byte[2048];
      stream.read(data);
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(data);
      KeyFactory factory = KeyFactory.getInstance(algorithm);
      key = factory.generatePublic(keySpec);

    } catch(Exception e){
      e.printStackTrace();
    } finally{
      try{stream.close();} catch(Exception e){}
    }

    return key;
  }

  private static PrivateKey getPrivateKey(){
    FileInputStream stream = null;
    PrivateKey key = null;

    try{

      stream = new FileInputStream(privateKeyPath);
      byte[] data = new byte[128];
      stream.read(data);
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(data);
      KeyFactory factory = KeyFactory.getInstance(algorithm);
      key = factory.generatePrivate(keySpec);

    } catch(Exception e){
      e.printStackTrace();
    } finally{
      try{stream.close();} catch(Exception e){}
    }

    return key;
  }

  private static void testRSAKeys(){
    Cipher cipher = null;
    byte[] data = shortTestPhrase.getBytes(StandardCharsets.UTF_8);

    System.out.println("------RSA-------");
    System.out.println(shortTestPhrase);

    try{

      cipher = Cipher.getInstance(algorithm);
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      data = cipher.doFinal(data);

      System.out.println(new String(data, StandardCharsets.UTF_8) + "\n" + data.length);

      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      data = cipher.doFinal(data);

      System.out.println(new String(data, StandardCharsets.UTF_8));

    } catch(Exception e){
      e.printStackTrace();
    }

  }

  private static void testAESKey(){
    Cipher cipher = null;
    byte[] data = longTestPhrase.getBytes(StandardCharsets.UTF_8);

    System.out.println("-------AES------");
    System.out.println(longTestPhrase);

    try{

      cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, aesKey);
      data = cipher.doFinal(data);

      System.out.println(new String(data, StandardCharsets.UTF_8) + "\n" + data.length);

      cipher.init(Cipher.DECRYPT_MODE, aesKey);
      data = cipher.doFinal(data);

      System.out.println(new String(data, StandardCharsets.UTF_8));

    } catch(Exception e){
      e.printStackTrace();
    }

  }

  public static void main(String[] args){
    KeyFileTester.publicKey = getPublicKey();
    KeyFileTester.privateKey = getPrivateKey();
    KeyFileTester.aesKey = getAESKey();
    testRSAKeys();
    testAESKey();
  }

}
