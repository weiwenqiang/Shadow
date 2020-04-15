package com.zzz.myemergencyclientnew.encrypt.rsa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加解密公用类
 */
public class KeyUtils {

    /***************************************** 公用方法 **********************/
    /**
     * 对数据体进行MD5加密
     * @param data 数据结构字符串
     * @return
     */
    public static String encryptDataMD5(String data){
        String result = null;
        try {
            result = MD5.MD5Encode(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /***************************************** 客户端用方法 **********************/
    /**
     * 对AES的key进行加密
     * @param publicKey 客户端保存的RSA公钥
     * @param aesKey 客户端随机生成的AES key
     * @return
     */
    public static String encryptAESKey(String publicKey, String aesKey){
        String result = null;
        try {
            result = RSA.encrypt(aesKey,publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 对数据体进行AES加密
     * @param aesKey 随机生成的AES KEY
     * @param data 数据结构字符串
     * @return
     */
    public static String encryptDataAES(String aesKey, String data){
        String result = null;
        try {
            result = AES.encryptToBase64(data,aesKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***************************************** 服务端用方法 **********************/
    /**
     * 对AES key 进行RSA解密
     * @param aesKey RSA加密后的AES的key
     * @param privateKey RSA私钥
     * @return
     */
    public static String decryptKeyRSA(String aesKey, String privateKey){
        String result = null;
        try {
            result = RSA.decrypt(aesKey,privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 对数据体进行AES解密
     * @param aesKey RSA解密后的AES的key
     * @param data 数据结构字符串
     * @return
     */
    public static String decryptDataAES(String aesKey, String data){
        String result = null;
        try {
            result = AES.decryptFromBase64(data,aesKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * sha1加密
     */
    public static String sha1(String data) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(data.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(String.format("%02X", 0xFF & messageDigest[i]));

            return hexString.toString().toLowerCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "-1";
    }
}
