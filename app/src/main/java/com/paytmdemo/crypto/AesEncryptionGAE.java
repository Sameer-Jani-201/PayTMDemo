package com.paytmdemo.crypto;

import android.util.Base64;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Sameer Jani on 29/1/18.
 */
public class AesEncryptionGAE implements EncryptionGAE {
    private final byte[] ivParamBytes = new byte[]{64, 64, 64, 64, 38, 38, 38, 38, 35, 35, 35, 35, 36, 36, 36, 36};

    public AesEncryptionGAE() {
    }

    public String encryptGAE(String toEncrypt, String password) throws Exception {
        byte[] key = password.getBytes();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING", "SunJCE");
        cipher.init(1, new SecretKeySpec(key, "AES"), new IvParameterSpec(this.ivParamBytes));
        return Base64.encodeToString(cipher.doFinal(toEncrypt.getBytes()),Base64.DEFAULT);
    }

    public String decryptGAE(String toDecrypt, String key) throws Exception {
        String decryptedValue = "";
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING", "SunJCE");
        cipher.init(2, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(this.ivParamBytes));
        decryptedValue = new String(cipher.doFinal(Base64.decode(toDecrypt,Base64.DEFAULT)));
        return decryptedValue;
    }
}
