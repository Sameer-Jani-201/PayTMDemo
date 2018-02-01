package com.paytmdemo.crypto;


import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Sameer Jani on 29/1/18.
 */
public class AesEncryption implements Encryption {
    private final byte[] ivParamBytes = new byte[]{64, 64, 64, 64, 38, 38, 38, 38, 35, 35, 35, 35, 36, 36, 36, 36};
    private Base64 base64Encoder;
    private Base64 base64Decoder;

    public AesEncryption() {
    }

    public String encrypt(String toEncrypt, String key) throws Exception {
        String encryptedValue = "";
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(1, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(this.ivParamBytes));
        encryptedValue = this.base64Encoder.encodeToString(cipher.doFinal(toEncrypt.getBytes()), Base64.DEFAULT);
        return encryptedValue;
    }

    public String decrypt(String toDecrypt, String key) throws Exception {
        String decryptedValue = "";
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(2, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(this.ivParamBytes));
        decryptedValue = new String(cipher.doFinal(this.base64Decoder.decode(toDecrypt.getBytes(), Base64.DEFAULT)));
        return decryptedValue;
    }
}
