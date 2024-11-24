package com.example.loms;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * Encrypts the given data using the provided secret key.
     *
     * @param data The plain text to encrypt.
     * @param secretKey The secret key for encryption.
     * @return The encrypted text as a Base64 encoded string.
     * @throws Exception If encryption fails.
     */
    public static String encrypt(String data, String secretKey) throws Exception {
        SecretKey key = generateKey(secretKey);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    /**
     * Decrypts the given data using the provided secret key.
     *
     * @param encryptedData The encrypted text (Base64 encoded).
     * @param secretKey The secret key for decryption.
     * @return The decrypted plain text.
     * @throws Exception If decryption fails.
     */
    public static String decrypt(String encryptedData, String secretKey) throws Exception {
        SecretKey key = generateKey(secretKey);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.decode(encryptedData, Base64.DEFAULT);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, "UTF-8");
    }

    /**
     * Generates a SecretKey object from the given secret key string.
     *
     * @param secretKey The secret key string.
     * @return The SecretKey object.
     * @throws Exception If key generation fails.
     */
    private static SecretKey generateKey(String secretKey) throws Exception {
        byte[] keyBytes = secretKey.getBytes("UTF-8");
        return new SecretKeySpec(keyBytes, 0, 16, ALGORITHM); // Ensure key length is 16 bytes
    }
}
