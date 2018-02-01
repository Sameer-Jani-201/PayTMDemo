package com.paytmdemo.crypto;

/**
 * Created by Sameer Jani on 29/1/18.
 */
public interface EncryptConstants {
    String ALGTHM_TYPE_3DES = "DESede";
    String ALGTHM_TYPE_HASH_SHA_256 = "SHA-256";
    String ALGTHM_TYPE_AES = "AES";
    String ALGTHM_CBC_PAD_AES = "AES/CBC/PKCS5PADDING";
    String ALGTHM_PROVIDER_BC = "SunJCE";
    String STR_COMMA = ",";
    String KEYSTORE_TYPE = "JCEKS";
    String ALIAS_TYPE_AES = "ALIAS_AES";
    String ALIAS_TYPE_3DES = "ALIAS_3DES";
    String KEYSTORE_NOT_FOUND_MSG = "Not able to load or generate the KeyStore";
    String ALGTHM_PROVIDER_JCE = "IBMJCE";
}
