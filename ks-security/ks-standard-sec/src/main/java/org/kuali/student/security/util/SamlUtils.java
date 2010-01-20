/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.cxf.interceptor.Fault;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.kuali.student.security.cxf.interceptors.SamlTokenCxfOutInterceptor;
import org.opensaml.SAMLAssertion;
import org.opensaml.SAMLAttribute;
import org.opensaml.SAMLAttributeStatement;
import org.opensaml.SAMLAuthenticationStatement;
import org.opensaml.SAMLException;
import org.opensaml.SAMLNameIdentifier;
import org.opensaml.SAMLSubject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
    
    private ThreadLocal<Map<String,String>> samlPropertiesHolder = new ThreadLocal<Map<String,String>>();
    
    public SAMLAssertion createAssertion(){
        
        String user = getSamlProperties().get("user");
        String pgt = getSamlProperties().get("proxyGrantingTicket");
        String proxies = getSamlProperties().get("proxies");
        String issuer = getSamlProperties().get("samlIssuerForUser");
        String nameQualifier = getSamlProperties().get("samlIssuerForUser");
        
        SAMLAssertion assertion = new SAMLAssertion();
        assertion.setIssuer(issuer);
        
        try{   
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
            attr1.setNamespace("Namesapce_of_Attribute1");
        
            SAMLAttribute attr2 = new SAMLAttribute();
            attr2.setName("proxies");
            attr2.setNamespace("Namesapce_of_Attribute2");
        
            
            attr1.addValue(pgt);
            attr1.addValue("additional value for proxy granting ticket");
            attr2.addValue(proxies);
            attr2.addValue("additional value for proxies");
            
            attrStatement.addAttribute(attr1);
            attrStatement.addAttribute(attr2);
            
            SAMLSubject subjectInAttr = (SAMLSubject)subject.clone();
            attrStatement.setSubject(subjectInAttr);
            
            // prepare Assertion
            assertion.addStatement(authStmt);
            assertion.addStatement(attrStatement);
            
            return assertion;
            
        } catch(SAMLException se){
            Logger log = Logger.getLogger(SamlTokenCxfOutInterceptor.class.getName());
            throw new Fault("Error when adding SAML Attributes or Attribute Statement : ", log, se);
        } catch(CloneNotSupportedException cnse){
            Logger log = Logger.getLogger(SamlTokenCxfOutInterceptor.class.getName());
            throw new Fault("Error when cloning subject : ", log, cnse);
        }      
    }
    
    public void setSamlProperties(Map<String, String> samlProperties){
        //this.samlProperties = samlProperties;
        this.samlPropertiesHolder.set(samlProperties);
    }

    public Map<String, String> getSamlProperties() {
        //return samlProperties;
        return this.samlPropertiesHolder.get();
    }
    
    public Document signAssertion(SAMLAssertion assertion) throws Exception{
        Constants.setSignatureSpecNSprefix("ds");

        //J-
        //All the parameters for the keystore
        String keystoreType = "JKS";
        String keystoreFile = "x509.jks";
        String keystorePass = "changeit";
        String privateKeyAlias = "tomcat";
        String privateKeyPass = "changeit";
        String certificateAlias = "tomcat";
        File signatureFile = new File("signature.xml");
        //J+
        KeyStore ks = KeyStore.getInstance(keystoreType);
        
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL url = classLoader.getResource(keystoreFile);
        
        //FileInputStream fis = new FileInputStream(keystoreFile);

        //load the keystore
        ks.load(url.openStream(), keystorePass.toCharArray());

        //get the private key for signing.
        PrivateKey privateKey = (PrivateKey) ks.getKey(privateKeyAlias,
                                               privateKeyPass.toCharArray());
        javax.xml.parsers.DocumentBuilderFactory dbf =
           javax.xml.parsers.DocumentBuilderFactory.newInstance();

        //XML Signature needs to be namespace aware
        dbf.setNamespaceAware(true);

        javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        //Build a sample document. It will look something like:
        //<!-- Comment before -->
        //<apache:RootElement xmlns:apache="http://www.apache.org/ns/#app1">Some simple text
        //</apache:RootElement>
        //<!-- Comment after -->
        doc.appendChild(doc.createComment(" Comment before "));

        Element root = doc.createElementNS("http://www.apache.org/ns/#app1",
                                           "apache:RootElement");

        root.setAttributeNS(null, "attr1", "test1");
        root.setAttributeNS(null, "attr2", "test2");
        root.setAttributeNS(Constants.NamespaceSpecNS, "xmlns:foo", "http://example.org/#foo");
        root.setAttributeNS("http://example.org/#foo", "foo:attr1", "foo's test");


        root.setAttributeNS(Constants.NamespaceSpecNS, "xmlns:apache", "http://www.apache.org/ns/#app1");
        doc.appendChild(root);
        root.appendChild(doc.createTextNode("Some simple text\n"));
        
        root.appendChild(assertion.toDOM(doc));
        
        //The BaseURI is the URI that's used to prepend to relative URIs
        String BaseURI = signatureFile.toURL().toString();
        System.out.println("BaseURI : " + BaseURI);
        //Create an XML Signature object from the document, BaseURI and
        //signature algorithm (in this case DSA)
        XMLSignature sig = new XMLSignature(doc, BaseURI, XMLSignature.ALGO_ID_SIGNATURE_RSA);
                                                        //XMLSignature.ALGO_ID_SIGNATURE_DSA);


        //Append the signature element to the root element before signing because
        //this is going to be an enveloped signature.
        //This means the signature is going to be enveloped by the document.
        //Two other possible forms are enveloping where the document is inside the
        //signature and detached where they are seperate.
        //Note that they can be mixed in 1 signature with seperate references as
        //shown below.
        root.appendChild(sig.getElement());
        doc.appendChild(doc.createComment(" Comment after "));

        
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
       System.out.println("Start signing");
       sig.sign(privateKey);
       System.out.println("Finished signing");
        

        //FileOutputStream f = new FileOutputStream(signatureFile);

        //XMLUtils.outputDOMc14nWithComments(doc, f);

        //f.close();
        System.out.println("Wrote signature to " + BaseURI);
        
        return doc;
    }
}
