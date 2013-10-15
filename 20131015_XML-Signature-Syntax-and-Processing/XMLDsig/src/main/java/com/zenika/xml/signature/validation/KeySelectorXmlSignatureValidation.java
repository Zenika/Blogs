package com.zenika.xml.signature.validation;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.crypto.KeySelector;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.dom.DOMValidateContext;

/**
 * An XmlSignatureValidation implementation for validating XML signatures with a key selector
 */
public class KeySelectorXmlSignatureValidation extends XmlSignatureValidation {

    private KeySelector keySelector;

    public KeySelectorXmlSignatureValidation(KeySelector keySelector) {
        this.keySelector = keySelector;
    }

    /**
     * Validate the XMLSignature contained in the given Document using the KeySelector
     * @param document
     * @return true if the signature pass validation, false otherwise
     * @throws MarshalException
     * @throws XMLSignatureException
     */
    public boolean validate(Document document) throws MarshalException, XMLSignatureException {
        NodeList nodes = document.getElementsByTagName("Signature");
        if(nodes.getLength() == 0) {
            throw  new XMLSignatureException("The document does not seem to contain an XmlSignature node.");
        }

        Node signatureNode = nodes.item(0);

        DOMValidateContext validateContext = new DOMValidateContext(keySelector, signatureNode);
        return super.validate(validateContext);
    }
}
