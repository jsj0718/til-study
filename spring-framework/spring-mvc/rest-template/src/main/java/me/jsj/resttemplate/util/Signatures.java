package me.jsj.resttemplate.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class Signatures {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String PROVIDER = "BC";
//    private static final String HMAC_SHA256 = "HMac-SHA256";
    private static final String HMAC_SHA256 = "HmacSHA256";

    public static String of(String timestamp, String method, String resource, String key) throws SignatureException {
        return of(timestamp + "." + method + "." + resource, key);
    }

    public static String of(String data, String key) throws SignatureException {
        try {
//            Mac mac = Mac.getInstance(HMAC_SHA256, PROVIDER);
//            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_SHA256));
//            return DatatypeConverter.printBase64Binary(mac.doFinal(data.getBytes()));
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(new SecretKeySpec(key.getBytes(), HMAC_SHA256));
            return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
        } catch (GeneralSecurityException e) {
            throw new SignatureException("Failed to generate a signature.", e);
        }
    }
}
