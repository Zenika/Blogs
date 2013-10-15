package com.zenika.xml.signature.validation;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.hash.Hashing;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import javax.xml.crypto.*;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyName;

/**
 *
 */
public class AdvancedKeySelector extends KeySelector {

    private final KeyStore keyStore;

    private final Function<Certificate, String> keyNameFunction;

    /**
     * Instanciate HashKeySelector with the key store that will be used to find a matching key and the function that will convert a certificate to a name
     * @param keyStore
     * @param keyNameFunction
     */
    public AdvancedKeySelector(KeyStore keyStore, Function<Certificate, String> keyNameFunction) {
        this.keyStore = keyStore;
        this.keyNameFunction = keyNameFunction;
    }

    /**
     * Retrieve from a keystore the key corresponding to the KeyName value contained in the given KeyInfo
     * @param keyInfo
     * @param purpose
     * @param method
     * @param context
     * @return
     * @throws javax.xml.crypto.KeySelectorException
     */
    @Override
    public KeySelectorResult select(KeyInfo keyInfo, Purpose purpose, AlgorithmMethod method, XMLCryptoContext context) throws KeySelectorException {
        Optional<String> keyValue = FluentIterable.from(keyInfo.getContent()).
                firstMatch(new Predicate() {
                    @Override
                    public boolean apply(Object element) {
                        return ((XMLStructure) element) instanceof KeyName;
                    }
                }).
                transform(new Function() {
                    @Override
                    public String apply(Object element) {
                        return ((KeyName) element).getName();
                    }
                });

        if(!keyValue.isPresent()) {
            throw new KeySelectorException("There is no KeyName node inside the given KeyInfo.");
        }

        try {
            Enumeration<String> aliases = keyStore.aliases();
            while(aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                final Certificate certificate = keyStore.getCertificate(alias);
                if(certificate == null) {
                    continue;
                }

                String hash = keyNameFunction.apply(certificate);
                if(keyValue.get().equals(hash)) {
                    return new KeySelectorResult() {
                        @Override
                        public Key getKey() {
                            return certificate.getPublicKey();
                        }
                    };
                }
            }

        } catch (KeyStoreException e) {
            throw new KeySelectorException(e);
        }

        throw new KeySelectorException("The key store does not contain à key matching '" + keyValue.get() + " '.");
    }

    public Function<Certificate, String> getKeyNameFunction() {
        return keyNameFunction;
    }
}
