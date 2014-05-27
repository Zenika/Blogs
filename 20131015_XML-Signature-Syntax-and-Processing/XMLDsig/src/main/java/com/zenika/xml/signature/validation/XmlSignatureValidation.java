package com.zenika.xml.signature.validation;

import org.w3c.dom.Document;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;

/**
 * An abstract class for XML signature validation. See PublicKeyXmlSignatureValidation and KeySelectorXmlSignatureValidation
 */
public abstract class XmlSignatureValidation {

    /**
     * Validate the XMLSignature contained in the given Document
     * @param document
     * @return
     * @throws MarshalException
     * @throws XMLSignatureException
     */
    public abstract boolean validate(Document document) throws MarshalException, XMLSignatureException;

    /**
     * Validate the XMLSignature contained in the given validateContext
     * @param validateContext
     * @return true if the signature pass validation, false otherwise
     * @throws MarshalException
     * @throws XMLSignatureException
     */
    protected boolean validate(DOMValidateContext validateContext) throws MarshalException, XMLSignatureException {
        XMLSignature xmlSignature = XMLSignatureFactory.getInstance().unmarshalXMLSignature(validateContext);
        return xmlSignature.validate(validateContext);
    }
}
