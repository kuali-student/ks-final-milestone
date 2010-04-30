/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.security.util;

import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.apache.xpath.XPathAPI;
import org.opensaml.SAMLAssertion;
import org.opensaml.SAMLAttribute;
import org.opensaml.SAMLAttributeStatement;
import org.opensaml.SAMLAuthenticationStatement;
import org.opensaml.SAMLException;
import org.opensaml.SAMLNameIdentifier;
import org.opensaml.SAMLSubject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This is a description of what this class does - Rich don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class SamlUtils {

    static {
        org.apache.xml.security.Init.init();
     }
    
    //All the parameters for the keystore
    private static String keystoreType;
    private static String keystoreFile;
    private static String keystorePass;
    private static String privateKeyAlias;
    private static String privateKeyPass;
    private static String certificateAlias;
    
    private static ThreadLocal<Map<String,String>> samlPropertiesHolder = new ThreadLocal<Map<String,String>>();
    
    public static SAMLAssertion createAssertion() throws SAMLException, CloneNotSupportedException{
        
        String user = getSamlProperties().get("user");
        String pgt = getSamlProperties().get("proxyGrantingTicket");
        String proxies = getSamlProperties().get("proxies");
        String issuer = getSamlProperties().get("samlIssuerForUser");
        String nameQualifier = getSamlProperties().get("samlIssuerForUser");
        
        SAMLAssertion assertion = new SAMLAssertion();
        assertion.setIssuer(issuer);
        
        // prepare subject
        SAMLNameIdentifier nameId = new SAMLNameIdentifier(user, nameQualifier, "");
        String[] confirmationMethods = {SAMLSubject.CONF_SENDER_VOUCHES};
        SAMLSubject subject = new SAMLSubject();
        subject.setNameIdentifier(nameId);
        subject.setConfirmationMethods(Arrays.asList(confirmationMethods));
    
        // prepare auth statement
        SAMLAuthenticationStatement authStmt = new SAMLAuthenticationStatement();
        authStmt.setAuthInstant(new Date());
        authStmt.setAuthMethod(SAMLAuthenticationStatement.AuthenticationMethod_Password);
        authStmt.setSubject(subject);
    
        // prepare attributes
        SAMLAttributeStatement attrStatement = new SAMLAttributeStatement();
        SAMLAttribute attr1 = new SAMLAttribute();
        attr1.setName("proxyGrantingTicket");
        attr1.setNamespace("http://student.kuali.org/wsdl/security/saml");
    
        SAMLAttribute attr2 = new SAMLAttribute();
        attr2.setName("proxies");
        attr2.setNamespace("http://student.kuali.org/wsdl/security/saml");
    
        attr1.addValue(pgt);
        attr2.addValue(proxies);
        
        attrStatement.addAttribute(attr1);
        attrStatement.addAttribute(attr2);
        
        SAMLSubject subjectInAttr = (SAMLSubject)subject.clone();
        attrStatement.setSubject(subjectInAttr);
        
        // prepare Assertion
        assertion.addStatement(authStmt);
        assertion.addStatement(attrStatement);
        
        return assertion;    
    }
    
    public static void setSamlProperties(Map<String, String> samlProperties){
        //this.samlProperties = samlProperties;
        SamlUtils.samlPropertiesHolder.set(samlProperties);
    }

    public static Map<String, String> getSamlProperties() {
        //return samlProperties;
        return SamlUtils.samlPropertiesHolder.get();
    }
    
    public static Document signAssertion(SAMLAssertion assertion) throws XMLSecurityException, KeyStoreException, 
                            NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException, 
                            ParserConfigurationException, DOMException, SAMLException {
        Constants.setSignatureSpecNSprefix("ds");
        
        ClassLoader classLoader = SamlUtils.class.getClassLoader();
        URL url = classLoader.getResource(keystoreFile);

        //load the keystore
        KeyStore ks = KeyStore.getInstance(keystoreType);
        ks.load(url.openStream(), keystorePass.toCharArray());

        //get the private key for signing.
        PrivateKey privateKey = (PrivateKey) ks.getKey(privateKeyAlias, privateKeyPass.toCharArray());
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        //XML Signature needs to be namespace aware
        dbf.setNamespaceAware(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        Element root = doc.createElementNS("http://student.kuali.org", "ks:RootElement");
        root.setAttributeNS(Constants.NamespaceSpecNS, "xmlns:ks", "http://student.kuali.org");
        
        doc.appendChild(root);
        // append the SAML assertion to the root.
        root.appendChild(assertion.toDOM(doc));
        
        XMLSignature sig = new XMLSignature(doc, null, XMLSignature.ALGO_ID_SIGNATURE_RSA);
                                                        //XMLSignature.ALGO_ID_SIGNATURE_DSA);

        //Append the signature element to the root element before signing because
        //this is going to be an enveloped signature.
        //This means the signature is going to be enveloped by the document.
        //Two other possible forms are enveloping where the document is inside the
        //signature and detached where they are seperate.
        //Note that they can be mixed in 1 signature with seperate references as
        //shown below.
        root.appendChild(sig.getElement());
        
        //create the transforms object for the Document/Reference
        Transforms transforms = new Transforms(doc);

        //First we have to strip away the signature element (it's not part of the
        //signature calculations). The enveloped transform can be used for this.
        transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
        //Part of the signature element needs to be canonicalized. It is a kind
        //of normalizing algorithm for XML. For more information please take a
        //look at the W3C XML Digital Signature webpage.
        transforms.addTransform(Transforms.TRANSFORM_C14N_WITH_COMMENTS);
        //Add the above Document/Reference
        sig.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);
        

        //Add in the KeyInfo for the certificate that we used the private key of
        X509Certificate cert = (X509Certificate) ks.getCertificate(certificateAlias);

        sig.addKeyInfo(cert);
        sig.addKeyInfo(cert.getPublicKey());
        // Sign the document
        sig.sign(privateKey);
        
        return doc;
    }

    public static SAMLAssertion unsignAssertion(Document doc) throws TransformerException, XMLSecurityException, SAMLException {
        boolean validSig = false;
        Element nscontext = doc.createElementNS(null, "namespaceContext");
        nscontext.setAttributeNS(Constants.NamespaceSpecNS, "xmlns:ds", Constants.SignatureSpecNS);
        
        Element sigElement = (Element) XPathAPI.selectSingleNode(doc, "//ds:Signature[1]", nscontext);
        
        XMLSignature signature = new XMLSignature(sigElement, null);
        
        KeyInfo ki = signature.getKeyInfo();
        
        if (ki != null) {
           X509Certificate cert = ki.getX509Certificate();

           if (cert != null) {
               validSig = signature.checkSignatureValue(cert);
              
           } else {
              PublicKey pk = ki.getPublicKey();

              if (pk != null) {
                 validSig = signature.checkSignatureValue(pk);
                 
              } else {
                 throw new XMLSignatureException("Could not find a certificate or a public key in the message, can't check the signature");
              }
           }
        } else {
           throw new XMLSignatureException("Could not find a KeyInfo element in message");
        }
        
        // if the signature is valid get the assertion and return it.
        if(validSig){
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            Node childNode = null;
            
            for(int i=0; i < nodeList.getLength(); i++){
                childNode = nodeList.item(i);
                if((childNode.getNodeName().equals("Assertion")) && (childNode.getNodeType() == Node.ELEMENT_NODE)){
                    SAMLAssertion assertion = new SAMLAssertion((Element)childNode);
                    return assertion;
                }
            }
        }
        
        throw new XMLSignatureException("The message signature was invalid");
    }
    
    /**
     * @return the keystoreType
     */
    public String getKeystoreType() {
        return keystoreType;
    }

    /**
     * @param keystoreType the keystoreType to set
     */
    public void setKeystoreType(String keystoreType) {
        SamlUtils.keystoreType = keystoreType;
    }

    /**
     * @return the keystoreFile
     */
    public String getKeystoreFile() {
        return keystoreFile;
    }

    /**
     * @param keystoreFile the keystoreFile to set
     */
    public void setKeystoreFile(String keystoreFile) {
        SamlUtils.keystoreFile = keystoreFile;
    }

    /**
     * @return the keystorePass
     */
    public String getKeystorePass() {
        return keystorePass;
    }

    /**
     * @param keystorePass the keystorePass to set
     */
    public void setKeystorePass(String keystorePass) {
        SamlUtils.keystorePass = keystorePass;
    }

    /**
     * @return the privateKeyAlias
     */
    public String getPrivateKeyAlias() {
        return privateKeyAlias;
    }

    /**
     * @param privateKeyAlias the privateKeyAlias to set
     */
    public void setPrivateKeyAlias(String privateKeyAlias) {
        SamlUtils.privateKeyAlias = privateKeyAlias;
    }

    /**
     * @return the privateKeyPass
     */
    public String getPrivateKeyPass() {
        return privateKeyPass;
    }

    /**
     * @param privateKeyPass the privateKeyPass to set
     */
    public void setPrivateKeyPass(String privateKeyPass) {
        SamlUtils.privateKeyPass = privateKeyPass;
    }

    /**
     * @return the certificateAlias
     */
    public String getCertificateAlias() {
        return certificateAlias;
    }

    /**
     * @param certificateAlias the certificateAlias to set
     */
    public void setCertificateAlias(String certificateAlias) {
        SamlUtils.certificateAlias = certificateAlias;
    }
}
