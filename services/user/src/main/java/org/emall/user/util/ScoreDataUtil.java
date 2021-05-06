package org.emall.user.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * 解密aes数据
 * @author gaopeng 2021/4/29
 */
@Slf4j
public class ScoreDataUtil {

    private static final String password = "D23ABC@#56";
    private static final String salt = "secret";
    private static final String uri = "apidata/api/gk/score/special";
    private static final String algorithm = "AES/CBC/PKCS5Padding";

    public static String decrypt(String data) {
        try {
            byte[] key = getKeyFromPassword(password, salt, 256);
            byte[] iv = getKeyFromPassword(uri, salt, 128);

            return decrypt(algorithm, data, key, new IvParameterSpec(iv));
        } catch (Exception e) {
            log.warn("解析数据失败", e);
        }

        return null;
    }

    private static byte[] getKeyFromPassword(String password, String salt, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1000, keyLength);
        return factory.generateSecret(spec).getEncoded();
    }

    private static String decrypt(String algorithm, String cipherText, byte[] key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), iv);
        byte[] plainText = cipher.doFinal(hexStringToByte(cipherText));
        return new String(plainText);
    }

    private static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        return result;
    }

    private static byte toByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
    }
}
