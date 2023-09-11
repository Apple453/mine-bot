package net.applee.minecraft.protocol.encryption;

import java.security.SignatureException;

public interface SignatureUpdatable {
    void update(SignatureUpdater updater) throws SignatureException;

    interface SignatureUpdater {
        void update(byte[] data) throws SignatureException;
    }
}
