package net.applee.minecraft.protocol.encryption;

import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Objects;

public interface SignatureVerifier {
    SignatureVerifier NOOP = (updatable, signatureData) -> {
        return true;
    };

    private static boolean verify(SignatureUpdatable updatable, byte[] signatureData, Signature signature) throws SignatureException {
        Objects.requireNonNull(signature);
        updatable.update(signature::update);
        return signature.verify(signatureData);
    }

    static SignatureVerifier create(PublicKey publicKey, String algorithm) {
        return (updatable, signatureData) -> {
            try {
                Signature signature = Signature.getInstance(algorithm);
                signature.initVerify(publicKey);
                return verify(updatable, signatureData, signature);
            } catch (Exception var5) {
                return false;
            }
        };
    }

    boolean validate(SignatureUpdatable updatable, byte[] signatureData);

    default boolean validate(byte[] signedData, byte[] signatureData) {
        return this.validate((updater) -> {
            updater.update(signedData);
        }, signatureData);
    }

}