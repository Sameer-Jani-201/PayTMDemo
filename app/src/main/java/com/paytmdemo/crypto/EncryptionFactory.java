package com.paytmdemo.crypto;

/**
 * Created by Sameer Jani on 29/1/18.
 */
public class EncryptionFactory {
    private EncryptionFactory() {
    }

    public static Encryption getEncryptionInstance(String algorithmType) {
        return new AesEncryption();
    }

    public static EncryptionGAE getEncryptionInstanceGAE(String algorithmType) {
        return new AesEncryptionGAE();
    }

    public static EncryptionIBMJCE getEncryptionInstanceIBMJCE(String algorithmType) {
        return new AesEncryptionIBMJCE();
    }
}
