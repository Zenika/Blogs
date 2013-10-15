package com.zenika.xml.signature.validation;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.crypto.KeySelector;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import java.security.PublicKey;

/**
 * A factory for XmlSignatureValidation
 */
public class XmlSignatureValidationFactory {

    /**
     * No instanciation is expected
     */
    private XmlSignatureValidationFactory() {
    }

    /**
     * Returns a XmlSignatureValidation implementation ready to validate an XML signature with the given public key
     * @param publicKey
     * @return
     */
    public static XmlSignatureValidation fromPublicKey(PublicKey publicKey) {
        return new PublicKeyXmlSignatureValidation(publicKey);
    }

    /**
     * Returns an XmlSignatureValidation implementation ready to validate an XML signature using the given KeySelected instance
     * @param keySelector
     * @return
     */
    public static XmlSignatureValidation fromKeySelector(KeySelector keySelector) {
        return new KeySelectorXmlSignatureValidation(keySelector);
    }
}
