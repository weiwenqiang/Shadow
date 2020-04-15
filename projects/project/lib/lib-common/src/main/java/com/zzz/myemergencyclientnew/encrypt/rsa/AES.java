package com.zzz.myemergencyclientnew.encrypt.rsa;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;


public class AES {
	/**
	 * 加密
	 * @param data 需要加密的内容
	 * @param key 加密密码
	 * @return
	 */
	public static byte[] encrypt(byte[] data, byte[] key) {
		CheckUtils.notEmpty(data, "data");
		CheckUtils.notEmpty(key, "key");
		if(key.length!=16){
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat,"AES");
			Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, seckey);// 初始化
			byte[] result = cipher.doFinal(data);
			return result; // 加密
		} catch (Exception e){
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param data 待解密内容
	 * @param key 解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] data, byte[] key) {
		CheckUtils.notEmpty(data, "data");
		CheckUtils.notEmpty(key, "key");
		if(key.length!=16){
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, seckey);// 初始化
			byte[] result = cipher.doFinal(data);
			return result; // 加密
		} catch (Exception e){
			throw new RuntimeException("decrypt fail!", e);
		}
	}
	
	public static String encryptToBase64(String data, String key){
		try {
			byte[] valueByte = encrypt(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
			return new String(Base64.encode(valueByte));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encrypt fail!", e);
		}
		
	}
	
	public static String decryptFromBase64(String data, String key){
		try {
			byte[] originalData = Base64.decode(data.getBytes());
			byte[] valueByte = decrypt(originalData, key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
			return new String(valueByte, ConfigureEncryptAndDecrypt.CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("decrypt fail!", e);
		}
	}
	
	public static String encryptWithKeyBase64(String data, String key){
		try {
			byte[] valueByte = encrypt(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), Base64.decode(key.getBytes()));
			return new String(Base64.encode(valueByte));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}
	
	public static String decryptWithKeyBase64(String data, String key){
		try {
			byte[] originalData = Base64.decode(data.getBytes());
			byte[] valueByte = decrypt(originalData, Base64.decode(key.getBytes()));
			return new String(valueByte, ConfigureEncryptAndDecrypt.CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("decrypt fail!", e);
		}
	}
	
	public static byte[] genarateRandomKey(){
		KeyGenerator keygen = null;
		try {
			keygen = KeyGenerator.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(" genarateRandomKey fail!", e);
		}
		SecureRandom random = new SecureRandom();
		keygen.init(random);
		Key key = keygen.generateKey();
		return key.getEncoded();
	}
	
	public static String genarateRandomKeyWithBase64(){
		return new String(Base64.encode(genarateRandomKey()));
	}

	/**
	 * 生成指定长度的随机密码
	 * @param len 密码长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum(int len) {
		int i;  //生成的随机数
		int count = 0; //生成的密码的长度
		// 密码字典
		char[] str = {
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'~', '!', '@', '#', '$', '%', '^', '-', '+'
		};
		StringBuffer stringBuffer = new StringBuffer("");
		Random r = new Random();
		while(count < len){
			//生成 0 ~ 密码字典-1之间的随机数
			i = r.nextInt(str.length);
			stringBuffer.append(str[i]);
			count ++;
		}
		return stringBuffer.toString();
	}

	/**
	 * 生成16位长度的随机字符串
	 * @return
	 */
	public static String getAESRandomKey() {
		return genRandomNum(16);
	}
	
}
