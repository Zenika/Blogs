package com.zenika.xml.signature.builder;

import com.zenika.xml.signature.XmlSignatureBuilder;
import com.zenika.xml.signature.validation.AdvancedKeySelector;
import com.zenika.xml.signature.validation.HashKeySelector;
import com.zenika.xml.signature.validation.XmlSignatureValidationFactory;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * XmlSignatureBuilder & XmlSignatureValidation testing for 3 signature modes (enveloped, enveloping and detached)
 */
public class XmlSignatureBuilderTest {

    private static final Logger LOGGER = Logger.getLogger(XmlSignatureBuilderTest.class.getSimpleName());

    private Certificate certificate;

    private KeyStore keyStore;

    private PrivateKey privateKey;

    private PublicKey publicKey;

    private Transformer transformer;

    private DocumentBuilder documentBuilder;

    private Document document1;

    private Document document2;

    private AdvancedKeySelector keySelector;

    public XmlSignatureBuilderTest() throws TransformerConfigurationException, KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        transformer = TransformerFactory.newInstance().newTransformer();

        // load private key
        InputStream keyStoreStream = this.getClass().getResourceAsStream("/keystore.jks");
        keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(keyStoreStream, "zenika".toCharArray());
        privateKey = (PrivateKey) keyStore.getKey("zenika", "zenika".toCharArray());
        certificate = keyStore.getCertificate("zenika");
        publicKey = certificate.getPublicKey();

        keySelector = new HashKeySelector(keyStore);
    }

    @Before
    public void before() throws ParserConfigurationException, IOException, SAXException {
        // load doc1
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/doc1.xml");
        document1 = documentBuilder.parse(resourceAsStream);

        //load doc2
        resourceAsStream = this.getClass().getResourceAsStream("/doc2.xml");
        document2 = documentBuilder.parse(resourceAsStream);
    }

    @Test
    public void testEnvelopedSignature() throws ParserConfigurationException, IOException, SAXException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, XMLSignatureException, MarshalException, CertificateException, TransformerException {
        LOGGER.info("ENVELOPED SIGNATURE");

        Document result = new XmlSignatureBuilder().
                withKeyInfo().
                    withKeyName(certificate, keySelector.getKeyNameFunction()).
                    buildAndAttach().
                withSignedInfo().
                    withExclusiveCanonicalization().
                    withRsaSha256Signature().
                    withReference().
                        withEnvelopedTransformation().
                        withSHA256Digest().
                        buildAndAttach().
                    buildAndAttach().
                buildAndSign(privateKey, document1);

        printDocument(result);
        validateWithKeySelector(result);
    }

    @Test
    public void testEnvelopingSignature() throws ParserConfigurationException, IOException, SAXException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, XMLSignatureException, MarshalException, CertificateException, TransformerException, KeyException {
        LOGGER.info("ENVELOPING SIGNATURE");
        Document result = new XmlSignatureBuilder().
                withKeyInfo().
                    withKeyName(certificate, keySelector.getKeyNameFunction()).
                    buildAndAttach().
                withSignedInfo().
                    withExclusiveCanonicalization().
                    withRsaSha256Signature().
                    withReference().
                        withURI("#doc1").
                        withSHA256Digest().
                        withExclusiveCanonicalization().
                        buildAndAttach().
                    withReference().
                        withURI("#doc2").
                        withSHA1Digest().
                        withExclusiveCanonicalization().
                        buildAndAttach().
                    buildAndAttach().
                withObject(document1.getDocumentElement(), "doc1").
                withObject(document2.getDocumentElement(), "doc2").
                buildAndSign(privateKey);

        printDocument(result);
        validateWithKeySelector(result);
    }

    @Test
    public void testDetachedSignature() throws ParserConfigurationException, IOException, SAXException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, XMLSignatureException, MarshalException, CertificateException, TransformerException, KeyException {
        LOGGER.info("DETACHED SIGNATURE");

        Document result = new XmlSignatureBuilder().
                withKeyInfo().
                    withPublicKey(publicKey).
                    buildAndAttach().
                withSignedInfo().
                    withExclusiveCanonicalization().
                    withRsaSha256Signature().
                    withReference().
                        withURI("http://www.w3schools.com/xml/note.xml").
                        withSHA256Digest().
                        buildAndAttach().
                    buildAndAttach().
                buildAndSign(privateKey);

        printDocument(result);
        validateWithPublicKey(result);
    }

    private void validateWithKeySelector(Document document) throws MarshalException, XMLSignatureException {
        assertTrue(XmlSignatureValidationFactory.
                fromKeySelector(keySelector).
                validate(document));
    }

    private void validateWithPublicKey(Document document) throws MarshalException, XMLSignatureException {
        assertTrue(XmlSignatureValidationFactory.
                fromPublicKey(publicKey).
                validate(document));
    }

    private void printDocument(Document document) throws TransformerException {
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        LOGGER.info(writer.toString());
    }
}
