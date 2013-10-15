package com.zenika.xml.signature;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A builder for generating an xml signature document. Easier to use than the java xml signature API...
 */
public class XmlSignatureBuilder {

    private final XMLSignatureFactory xmlSignatureFactory;

    private SignedInfo signedInfo;

    private KeyInfo keyInfo;

    private List<XMLObject> objects = new ArrayList<XMLObject>();

    /**
     * Instantiate an XmlSignatureBuilder
     */
    public XmlSignatureBuilder() {
        xmlSignatureFactory = XMLSignatureFactory.getInstance();
    }

    /**
     * Return the XMLSignatureFactory used by the builder
     * @return
     */
    public XMLSignatureFactory getXmlSignatureFactory() {
        return xmlSignatureFactory;
    }

    /**
     * Generate the XMLSignature with the parameters set with this builder.
     * @return
     */
    public XMLSignature build() {
        return xmlSignatureFactory.newXMLSignature(signedInfo, keyInfo, objects, null, null);
    }

    /**
     * Generate the XMLSignature with the parameters set with this builder, sign the given document and insert the signature inside it.
     * @param privateKey
     * @param document
     * @return the document containing the XML signature
     * @throws MarshalException
     * @throws XMLSignatureException
     */
    public Document buildAndSign(Key privateKey, Document document) throws MarshalException, XMLSignatureException {
        build().sign(new DOMSignContext(privateKey, document.getDocumentElement()));
        return document;
    }

    /**
     * Generate the XMLSignature with the parameters set with this builder and insert the result inside a new document.
     * @param privateKey
     * @return the document containing the XML signature
     * @throws MarshalException
     * @throws XMLSignatureException
     * @throws ParserConfigurationException
     */
    public Document buildAndSign(Key privateKey) throws MarshalException, XMLSignatureException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document document = documentBuilderFactory.newDocumentBuilder().newDocument();
        build().sign(new DOMSignContext(privateKey, document));
        return document;
    }

    /**
     * Instantiate and return a KeyInfoBuilder used to set the KeyInfo node of the xml signature document.
     * @return
     */
    public KeyInfoBuilder withKeyInfo() {
        return new KeyInfoBuilder(this);
    }

    /**
     * Instantiate and return a SignedInfoBuilder used to set the SignedInfo node of the xml signature document.
     * @return
     */
    public SignedInfoBuilder withSignedInfo() {
        return new SignedInfoBuilder(this);
    }

    /**
     * Set the KeyInfo that will be used to generate the KeyInfo node of the xml signature document.
     * @param keyInfo
     * @return
     */
    protected XmlSignatureBuilder withKeyInfo(KeyInfo keyInfo) {
        this.keyInfo = keyInfo;
        return this;
    }

    /**
     * Set the SignedInfo that will be used to generate the SignedInfo node of the xml signature document.
     * @param signedInfo
     * @return
     */
    protected XmlSignatureBuilder withSignedInfo(SignedInfo signedInfo) {
        this.signedInfo = signedInfo;
        return this;
    }

    /**
     * Add to the XMLSignature to build an Object node with the given id
     * @param node
     * @param id
     * @return
     */
    public XmlSignatureBuilder withObject(Node node, String id) {
        return withObject(node, id, null, null);
    }

    /**
     * Add to the XMLSignature to build an Object node with the given id, mime type and encoding
     * @param node
     * @param id
     * @param mimeType
     * @param encoding
     * @return
     */
    public XmlSignatureBuilder withObject(Node node, String id, String mimeType, String encoding) {
        objects.add(xmlSignatureFactory.newXMLObject(Collections.singletonList(new DOMStructure(node)), id, mimeType, encoding));
        return this;
    }
}
