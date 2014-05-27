package com.zenika.xml.signature;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.spec.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * A builder for generating Reference node af an xml signature.
 */
public class ReferenceBuilder {

    private final SignedInfoBuilder signedInfoBuilder;

    private final XMLSignatureFactory xmlSignatureFactory;

    private List<Transform> transforms = new ArrayList<Transform>();

    private DigestMethod digestMethod;

    private String uri = "";

    public ReferenceBuilder(SignedInfoBuilder signedInfoBuilder) {
        this.signedInfoBuilder = signedInfoBuilder;
        this.xmlSignatureFactory = signedInfoBuilder.getXmlSignatureFactory();
    }

    /**
     * Add a SHA256 digest transformation
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     */
    public ReferenceBuilder withSHA256Digest() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withDigest(DigestMethod.SHA256);
    }

    /**
     * Add a SHA1 digest transformation
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     */
    public ReferenceBuilder withSHA1Digest() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withDigest(DigestMethod.SHA1);
    }

    /**
     * Add a SHA512 digest transformation
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     */
    public ReferenceBuilder withSHA512Digest() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withDigest(DigestMethod.SHA512);
    }

    /**
     * Add a digest transformation using the given algorithm (W3C URI identification)
     * @param algorithm
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     */
    public ReferenceBuilder withDigest(String algorithm) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        digestMethod = xmlSignatureFactory.newDigestMethod(algorithm, null);
        return this;
    }

    /**
     * Add a base 64 transformation
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     */
    public ReferenceBuilder withBase64() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withTransformation(Transform.BASE64);
    }

    /**
     * Discard the signature containing this element from the digest calculation of this same element
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     */
    public ReferenceBuilder withEnvelopedTransformation() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withTransformation(Transform.ENVELOPED);
    }

    public ReferenceBuilder withXPath2(XPathType... xPaths) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withTransformation(Transform.XPATH2, new XPathFilter2ParameterSpec(Lists.newArrayList(xPaths)));
    }

    public ReferenceBuilder withXPath2(String... expressions) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        List xPaths = Lists.transform(Lists.newArrayList(expressions),
                new Function<String, Object>() {
                    @Override
                    public XPathType apply(String expression) {
                        return new XPathType(expression, XPathType.Filter.INTERSECT);
                    }
                });
        return withTransformation(Transform.XPATH2, new XPathFilter2ParameterSpec(xPaths));
    }

    public ReferenceBuilder withXPath(String expression) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withTransformation(Transform.XPATH, new XPathFilterParameterSpec(expression));
    }

    /**
     * Add a transformation using the given algorithm (W3C URI identification)
     * @param algorithm
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     */
    public ReferenceBuilder withTransformation(String algorithm) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withTransformation(algorithm, null);
    }

    /**
     * Add a transformation using the given algorithm (W3C URI identification)
     * @param algorithm
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     */
    public ReferenceBuilder withTransformation(String algorithm, TransformParameterSpec parameterSpec) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        transforms.add(xmlSignatureFactory.newTransform(algorithm, parameterSpec));
        return this;
    }

    /**
     * Add an inclusive canonicalization
     * @return
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.security.NoSuchAlgorithmException
     */
    public ReferenceBuilder withInclusiveCanonicalization() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withCanonicalization(CanonicalizationMethod.INCLUSIVE);
    }

    /**
     * Add an exclusive canonicalization
     * @return
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.security.NoSuchAlgorithmException
     */
    public ReferenceBuilder withExclusiveCanonicalization() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        return withCanonicalization(CanonicalizationMethod.EXCLUSIVE);
    }

    /**
     * Add a canonicalization according to the given algorithm.
     * @param algorithm
     * @return
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.security.NoSuchAlgorithmException
     */
    public ReferenceBuilder withCanonicalization(String algorithm) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        CanonicalizationMethod canonicalizationMethod = xmlSignatureFactory.newCanonicalizationMethod(algorithm, (C14NMethodParameterSpec) null);
        transforms.add(canonicalizationMethod);
        return this;
    }

    /**
     * Set the uri attribute of this reference
     * @param uri
     * @return
     */
    public ReferenceBuilder withURI(String uri) {
        this.uri = uri;
        return this;
    }

    /**
     * Build the corresponding reference
     * @return
     */
    protected Reference build() {
        return xmlSignatureFactory.newReference(uri, digestMethod, transforms, null, null);
    }

    /**
     * Build the corresponding reference and attached it to the XmlSignatureBuilder
     * @return
     */
    public SignedInfoBuilder buildAndAttach() {
        return signedInfoBuilder.withReference(build());
    }
}
