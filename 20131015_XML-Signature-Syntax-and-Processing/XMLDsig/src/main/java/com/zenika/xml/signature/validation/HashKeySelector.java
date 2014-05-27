package com.zenika.xml.signature.validation;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.hash.Hashing;

import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import javax.xml.crypto.*;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyName;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.util.Enumeration;

/**
 * A key selector expecting hashcode (upper-case SHA1) certificate for a key name.
 */
public class HashKeySelector extends AdvancedKeySelector {

    private static final Function<Certificate, String> keyNameFunction = new Function<Certificate, String>() {
        @Override
        public String apply(Certificate certificate) {
            try {
                return Hashing.sha1().hashBytes(certificate.getEncoded()).toString().toUpperCase();
            } catch (CertificateEncodingException e) {
                return null;
            }
        }
    };

    public HashKeySelector(KeyStore keyStore) {
        super(keyStore, keyNameFunction);
    }
}
