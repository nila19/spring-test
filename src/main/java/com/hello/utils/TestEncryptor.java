package com.hello.utils;

import org.jasypt.util.text.AES256TextEncryptor;

public class TestEncryptor {
  public static void main(String[] args) {
    System.out.println("TestEncryptor => start");
    String salt = "C0mpl3xS@1t";
    String pwd = "";

    AES256TextEncryptor encryptor = new AES256TextEncryptor();
    encryptor.setPassword(salt);
    String encrypted = encryptor.encrypt(pwd);
    String decrypted = encryptor.decrypt(encrypted);
    System.out.println("Original:" + pwd + " Encrypted:" + encrypted + " Decrypted:" + decrypted);
  }
}
