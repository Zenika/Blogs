package com.zenika.xml.signature.validation;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import java.security.PublicKey;

/**
 * An XmlSignatureValidation implementation for validating XML signatures with a public key
 */
public class PublicKeyXmlSignatureValidation extends XmlSignatureValidation {

    private PublicKey publicKey;

    protected PublicKeyXmlSignatureValidation(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * Validate the XMLSignature contained in the given Document using the public key
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
        DOMValidateContext validateContext = new DOMValidateContext(publicKey, signatureNode);
        return super.validate(validateContext);
    }
}
