package stratego.security;

import java.io.*;
import java.security.*;
import java.security.spec.*;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class KeyFileGenerator{
  private final KeyPair keys;
  private static String publicKeyPath = "onlinekey";
  private static String privateKeyPath = "serverkey";
  private static String algorithm = "RSA";
  private static String test = "testing the keys";

  public KeyFileGenerator(){
    this.keys = setKeys();
  }

  private KeyPair setKeys(){
    KeyPair pair = null;
    try{
      KeyPairGenerator gen = KeyPairGenerator.getInstance(algorithm);
      gen.initialize(2048);
      pair = gen.genKeyPair();
    } catch(Exception e){
      e.printStackTrace();
    }
    return pair;
  }

  public void testKeys(){
    byte[] testing;
    try{
      System.out.println(test);
      Cipher cipher = Cipher.getInstance(algorithm);
      cipher.init(Cipher.ENCRYPT_MODE, keys.getPrivate());
      testing = cipher.doFinal(test.getBytes(StandardCharsets.UTF_8));
      cipher.init(Cipher.DECRYPT_MODE, keys.getPublic());
      testing = cipher.doFinal(testing);
      System.out.println(new String(testing, StandardCharsets.UTF_8));
    } catch(Exception e){
      e.printStackTrace();
    }
  }

  public void writeKeys(){
    File publicKeyFile = null;
    File privateKeyFile = null;
    FileOutputStream out = null;

    try{
      publicKeyFile = new File(publicKeyPath);
      privateKeyFile = new File(privateKeyPath);
      if(!publicKeyFile.exists()){
        publicKeyFile.createNewFile();
      }
      if(!privateKeyFile.exists()){
        privateKeyFile.createNewFile();
      }
      out = new FileOutputStream(publicKeyFile);
      out.write(keys.getPublic().getEncoded());
      out.close();

      out = new FileOutputStream(privateKeyFile);
      out.write(keys.getPrivate().getEncoded());
      out.close();
    } catch(Exception e){
      e.printStackTrace();
    } finally {
      try{out.close();} catch(Exception e){ e.printStackTrace();}
    }
  }

  public static void main(String[] args){
    KeyFileGenerator gen = new KeyFileGenerator();
    gen.testKeys();
    gen.writeKeys();
  }


}
