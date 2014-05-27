package com.zenika.xml.signature;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * A builder for generating the SignedInfo node af an xml signature document.
 * You should not instantiate this class yourself, use XmlSignatureBuilder instead and attached it.
 */
public class SignedInfoBuilder {

    private final XmlSignatureBuilder xmlSignatureBuilder;

    private final XMLSignatureFactory xmlSignatureFactory;

    private final List<Reference> references = new ArrayList<Reference>();

    private final List<Transform> transforms = new ArrayList<Transform>();

    private SignatureMethod signatureMethod;

    private CanonicalizationMethod canonicalizationMethod;

    public SignedInfoBuilder(XmlSignatureBuilder xmlSignatureBuilder) {
        this.xmlSignatureBuilder = xmlSignatureBuilder;
        this.xmlSignatureFactory = xmlSignatureBuilder.getXmlSignatureFactory();
    }

    public XMLSignatureFactory getXmlSignatureFactory() {
        return xmlSignatureFactory;
    }

    public SignedInfoBuilder withTransform(String transform) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        transforms.add(xmlSignatureFactory.newTransform(transform, (TransformParameterSpec) null));
        return this;
    }

    /**
     * Set the signature method with the rsa-sha256 method.
     * @return
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.security.NoSuchAlgorithmException
     */
    public SignedInfoBuilder withRsaSha256Signature() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withSignature("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
    }

    /**
     * Set the signature method with the given url.
     * @param url
     * @return
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.security.NoSuchAlgorithmException
     */
    public SignedInfoBuilder withSignature(String url) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        signatureMethod = xmlSignatureFactory.newSignatureMethod(url, null);
        return this;
    }

    /**
     * Set the canonicalization as inclusive
     * @return
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.security.NoSuchAlgorithmException
     */
    public SignedInfoBuilder withInclusiveCanonicalization() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withCanonicalization(CanonicalizationMethod.INCLUSIVE);
    }

    /**
     * Set the canonicalization as exclusive
     * @return
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.security.NoSuchAlgorithmException
     */
    public SignedInfoBuilder withExclusiveCanonicalization() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withCanonicalization(CanonicalizationMethod.EXCLUSIVE);
    }

    /**
     * Set the canonicalization according to the given method.
     * @param method
     * @return
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.security.NoSuchAlgorithmException
     */
    public SignedInfoBuilder withCanonicalization(String method) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        canonicalizationMethod = xmlSignatureFactory.newCanonicalizationMethod(method, (C14NMethodParameterSpec) null);
        return this;
    }

    /**
     * Instantiate and return a ReferenceBuilder used to set the Reference node of the xml signature document.
     * @return
     */
    public ReferenceBuilder withReference() {
        return new ReferenceBuilder(this);
    }

    /**
     * Set the Reference that will be used to generate the Reference node of the xml signature document.
     * You should not call this method directy, instead use withURI to attached the Reference.
     * @return
     */
    protected SignedInfoBuilder withReference(Reference reference) {
        references.add(reference);
        return this;
    }

    /**
     * Instantiate and return the SignedInfo used to generate the SignedInfo node of the xml signature document.
     * @return
     */
    protected SignedInfo build() {
        return xmlSignatureFactory.newSignedInfo(canonicalizationMethod, signatureMethod, references);
    }

    /**
     * Instantiate and attached to the contained XMLSignature the SignedInfo used to generate the SignedInfo node of the xml signature document.
     * @return
     */
    public XmlSignatureBuilder buildAndAttach() {
        return xmlSignatureBuilder.withSignedInfo(build());
    }
}
