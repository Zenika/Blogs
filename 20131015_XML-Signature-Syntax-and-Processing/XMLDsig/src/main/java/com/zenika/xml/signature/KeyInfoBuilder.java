package com.zenika.xml.signature;

import com.google.common.base.Function;
import com.zenika.xml.signature.validation.AdvancedKeySelector;
import com.zenika.xml.signature.validation.HashKeySelector;

import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import java.security.KeyException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

/**
 * A builder for generating the KeyInfo node af an xml signature document.
 */
public class KeyInfoBuilder {

    private final XmlSignatureBuilder xmlSignatureBuilder;

    private final KeyInfoFactory keyInfoFactory;

    private List<XMLStructure> keyInfos = new ArrayList<XMLStructure>();

    public KeyInfoBuilder(XmlSignatureBuilder xmlSignatureBuilder) {
        this.xmlSignatureBuilder = xmlSignatureBuilder;
        this.keyInfoFactory = xmlSignatureBuilder.getXmlSignatureFactory().getKeyInfoFactory();
    }

    /**
     * Set the key name with the given value
     * @param keyName
     * @return
     */
    public KeyInfoBuilder withKeyName(String keyName) {
        keyInfos.add(keyInfoFactory.newKeyName(keyName));
        return this;
    }

    /**
     * Set the key name by converting this certificate into a String using the keyNameFunction
     * @param certificate
     * @return
     */
    public KeyInfoBuilder withKeyName(Certificate certificate, Function<Certificate, String> keyNameFunction) throws CertificateException {
        keyInfos.add(keyInfoFactory.newKeyName(keyNameFunction.apply(certificate)));
        return this;
    }

    /**
     * Set the KeyValue using this public key
     * @param publicKey
     * @return
     * @throws java.security.KeyException
     */
    public KeyInfoBuilder withPublicKey(PublicKey publicKey) throws KeyException {
        keyInfos.add(keyInfoFactory.newKeyValue(publicKey));
        return this;
    }


    /**
     * Instantiate and attached to the contained XMLSignature the KeyInfo used to generate the KeyInfo node of the xml signature document.
     * @return
     */
    public XmlSignatureBuilder buildAndAttach() {
        return xmlSignatureBuilder.withKeyInfo(build());
    }

    /**
     * Instantiate the KeyInfo used to generate the KeyInfo node.
     * @return
     */
    protected KeyInfo build() {
        return keyInfoFactory.newKeyInfo(keyInfos);
    }
}
