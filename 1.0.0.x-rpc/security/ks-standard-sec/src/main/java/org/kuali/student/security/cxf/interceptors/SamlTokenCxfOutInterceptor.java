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

package org.kuali.student.security.cxf.interceptors;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptor;
import org.apache.cxf.ws.security.wss4j.AbstractWSS4JInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSEncryptionPart;
import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.handler.RequestData;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.util.WSSecurityUtil;
import org.opensaml.SAMLAssertion;
import org.opensaml.SAMLAttribute;
import org.opensaml.SAMLAttributeStatement;
import org.opensaml.SAMLAuthenticationStatement;
import org.opensaml.SAMLException;
import org.opensaml.SAMLNameIdentifier;
import org.opensaml.SAMLSubject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/*  Most of this code was taken from org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor
    and modified for our purpose, additions should have comments starting with 'KS: '
    
    This WSS4JOutInterceptor along with the wss4j library, handled the sender-vouches with a signed 
    SAML Assertion correctly. The information to create that sender-vouches Assertion comes from the 
    saml.properties file.
    We needed to create another signed SAML Assertion that would hold the authenticated user and any 
    SAML attributes along with the original voucher SAML Assertion. In the end the message would have two
    signed SAML assertions. This was not possible with WSS4JOutInterceptor. see 'KS :' comments
    
*/
public class SamlTokenCxfOutInterceptor extends AbstractWSS4JInterceptor {
    private static final Logger LOG = LogUtils
            .getL7dLogger(SamlTokenCxfOutInterceptor.class);

    private static final Logger TIME_LOG = LogUtils
            .getL7dLogger(SamlTokenCxfOutInterceptor.class,
                          null,
                          SamlTokenCxfOutInterceptor.class.getName() + "-Time");
    private SamlTokenCxfOutInterceptorInternal ending;
    private SAAJOutInterceptor saajOut = new SAAJOutInterceptor();
    private boolean mtomEnabled;
    
    // KS : added to hold information used to create authenticated user, SAML Assertion
    // KS : made this a ThreadLocal since we don't know if interceptors are thread-safe.
    //private Map<String,String> samlProperties = new HashMap<String,String>();
    private ThreadLocal<Map<String,String>> samlPropertiesHolder = new ThreadLocal<Map<String,String>>();
    private ThreadLocal<SAMLAssertion> samlAssertionHolder = new ThreadLocal<SAMLAssertion>();;
    
    public SamlTokenCxfOutInterceptor() {
        super();
        setPhase(Phase.PRE_PROTOCOL);
        getAfter().add(SAAJOutInterceptor.class.getName());
        
        ending = createEndingInterceptor();
    }
    
    public SamlTokenCxfOutInterceptor(Map<String, Object> props) {
        this();
        setProperties(props);
    }
    
    public boolean isAllowMTOM() {
        return mtomEnabled;
    }
    /**
     * Enable or disable mtom with WS-Security.   By default MTOM is disabled as
     * attachments would not get encrypted or be part of the signature.
     * @param mtomEnabled
     */
    public void setAllowMTOM(boolean allowMTOM) {
        this.mtomEnabled = allowMTOM;
    }

    public void handleMessage(SoapMessage mc) throws Fault {
        //must turn off mtom when using WS-Sec so binary is inlined so it can
        //be properly signed/encrypted/etc...
        if (!mtomEnabled) {
            mc.put(org.apache.cxf.message.Message.MTOM_ENABLED, false);
        }
        
        if (mc.getContent(SOAPMessage.class) == null) {
            saajOut.handleMessage(mc);
        }
        
        mc.getInterceptorChain().add(ending);
    }    
    public void handleFault(SoapMessage message) {
        saajOut.handleFault(message);
    } 
    
    public final SamlTokenCxfOutInterceptorInternal createEndingInterceptor() {
        return new SamlTokenCxfOutInterceptorInternal();
    }
    
    final class SamlTokenCxfOutInterceptorInternal 
        implements PhaseInterceptor<SoapMessage> {
        public SamlTokenCxfOutInterceptorInternal() {
            super();
        }
        
        public void handleMessage(SoapMessage mc) throws Fault {
            // KS : added so we can create the authenticated user SAML Assertion before the message is handled.
            String wsuId = handleMessageUserSAML(mc);
            
            boolean doDebug = LOG.isLoggable(Level.FINE);
            boolean doTimeDebug = TIME_LOG.isLoggable(Level.FINE);
            SoapVersion version = mc.getVersion();
    
            long t0 = 0;
            long t1 = 0;
            long t2 = 0;
    
            if (doTimeDebug) {
                t0 = System.currentTimeMillis();
            }
    
            if (doDebug) {
                LOG.fine("SamlTokenCxfOutInterceptor: enter handleMessage()");
            }
            
            // KS : this has been the problem and why we needed to code our own WSS4JOutInterceptor.
            // WSS4JOutInterceptor needed to expose RequestData out of here so you can add the signature parts
            // see code below, before doSenderAction();  Essentially anything signature parts, gets singed by wss4j.
            RequestData reqData = new RequestData();
            
            reqData.setMsgContext(mc);
            
            /*
             * The overall try, just to have a finally at the end to perform some
             * housekeeping.
             */
            try {
                /*
                 * Get the action first.
                 */
                Vector actions = new Vector();
                String action = getString(WSHandlerConstants.ACTION, mc);
                if (action == null) {
                    throw new SoapFault(new Message("NO_ACTION", LOG), version
                            .getReceiver());
                }
    
                int doAction = WSSecurityUtil.decodeAction(action, actions);
                if (doAction == WSConstants.NO_SECURITY) {
                    return;
                }
    
                /*
                 * For every action we need a username, so get this now. The
                 * username defined in the deployment descriptor takes precedence.
                 */
                reqData.setUsername((String) getOption(WSHandlerConstants.USER));
                if (reqData.getUsername() == null
                        || reqData.getUsername().equals("")) {
                    String username = (String) getProperty(reqData.getMsgContext(),
                            WSHandlerConstants.USER);
                    if (username != null) {
                        reqData.setUsername(username);
                    }
                }
    
                /*
                 * Now we perform some set-up for UsernameToken and Signature
                 * functions. No need to do it for encryption only. Check if
                 * username is available and then get a passowrd.
                 */
                if ((doAction & (WSConstants.SIGN | WSConstants.UT | WSConstants.UT_SIGN)) != 0
                        && (reqData.getUsername() == null
                        || reqData.getUsername().equals(""))) {
                    /*
                     * We need a username - if none throw an SoapFault. For
                     * encryption there is a specific parameter to get a username.
                     */
                    throw new SoapFault(new Message("NO_USERNAME", LOG), version
                            .getReceiver());
                }
                if (doDebug) {
                    LOG.fine("Action: " + doAction);
                    LOG.fine("Actor: " + reqData.getActor());
                }
                /*
                 * Now get the SOAP part from the request message and convert it
                 * into a Document. This forces CXF to serialize the SOAP request
                 * into FORM_STRING. This string is converted into a document.
                 * During the FORM_STRING serialization CXF performs multi-ref of
                 * complex data types (if requested), generates and inserts
                 * references for attachements and so on. The resulting Document
                 * MUST be the complete and final SOAP request as CXF would send it
                 * over the wire. Therefore this must shall be the last (or only)
                 * handler in a chain. Now we can perform our security operations on
                 * this request.
                 */
                SOAPMessage saaj = mc.getContent(SOAPMessage.class);
    
                if (saaj == null) {
                    LOG.warning("SAAJOutHandler must be enabled for WS-Security!");
                    throw new SoapFault(new Message("NO_SAAJ_DOC", LOG), version
                            .getReceiver());
                }
    
                Document doc = saaj.getSOAPPart();
                /**
                 * There is nothing to send...Usually happens when the provider
                 * needs to send a HTTP 202 message (with no content)
                 */
                if (mc == null) {
                    return;
                }
    
                if (doTimeDebug) {
                    t1 = System.currentTimeMillis();
                }
                
                // KS : any element represented by an encryptionPart and added to signatureParts will be signed.
                // The wsuId is the SAML Assertion we created above in handleMessageUserSAML(mc).
                WSEncryptionPart encP = new WSEncryptionPart(wsuId);
                reqData.getSignatureParts().add(encP);
                // KS : add the body, so it is also singed
                WSEncryptionPart encPBody = new WSEncryptionPart("Body", 
                        doc.getDocumentElement().getNamespaceURI(), "Content");
                reqData.getSignatureParts().add(encPBody);
                
                
                doSenderAction(doAction, doc, reqData, actions, Boolean.TRUE
                        .equals(getProperty(mc, org.apache.cxf.message.Message.REQUESTOR_ROLE)));
                
                if (doTimeDebug) {
                    t2 = System.currentTimeMillis();
                    TIME_LOG.fine("Send request: total= " + (t2 - t0)
                            + " request preparation= " + (t1 - t0)
                            + " request processing= " + (t2 - t1)
                            + "\n");
                }
    
                if (doDebug) {
                    LOG.fine("SamlTokenCxfOutInterceptor: exit handleMessage()");
                }
            } catch (WSSecurityException e) {
                throw new SoapFault(new Message("SECURITY_FAILED", LOG), e, version
                        .getSender());
            } finally {
                reqData.clear();
                reqData = null;
            }
        }
        
        public Set<String> getAfter() {
            return Collections.emptySet();
        }

        public Set<String> getBefore() {
            return Collections.emptySet();
        }

        public String getId() {
            return SamlTokenCxfOutInterceptorInternal.class.getName();
        }

        public String getPhase() {
            return Phase.POST_PROTOCOL;
        }

        public void handleFault(SoapMessage message) {
            //nothing
        }
        
        // KS : All three methods below added to create the authenticated user SAML Assertion
        public String handleMessageUserSAML(SoapMessage msg) throws Fault {
            String wsuId = null;
            Node assertionNode = null;
            
/*            String user = getSamlProperties().get("user");
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
                
                assertionNode = assertion.toDOM();
                
            } catch(SAMLException se){
                Logger log = Logger.getLogger(SamlTokenCxfOutInterceptor.class.getName());
                throw new Fault("Error when adding SAML Attributes or Attribute Statement : ", log, se);
            } catch(CloneNotSupportedException cnse){
                Logger log = Logger.getLogger(SamlTokenCxfOutInterceptor.class.getName());
                throw new Fault("Error when cloning subject : ", log, cnse);
            } */
            
            try{
                assertionNode = getSamlAssertion().toDOM();
            } catch(SAMLException se){
                Logger log = Logger.getLogger(SamlTokenCxfOutInterceptor.class.getName());
                throw new Fault("Error when adding SAML Attributes or Attribute Statement : ", log, se);
            }
            
            try{
                // get the envelope and create a header
                SOAPMessage soapMsg = msg.getContent(SOAPMessage.class);
                SOAPPart doc = soapMsg.getSOAPPart();
                SOAPEnvelope envelope = doc.getEnvelope();
                envelope.addHeader();
                SOAPHeader soapHeader = soapMsg.getSOAPHeader();
                
                
                SOAPFactory soapFactory = SOAPFactory.newInstance();
                
                // create wsse:Security element
                SOAPElement wsseSecurity = soapFactory.createElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
                
                // insert assertion in wsse:Security element
                if(assertionNode.getNodeType() == Node.ELEMENT_NODE ){
                    Element assertionElement = (Element)assertionNode;
                    
                    // add a wsu:Id
                    wsuId = setWsuId(assertionElement);
                    
                    SOAPElement assertionSOAP = soapFactory.createElement(assertionElement);
                    wsseSecurity.addChildElement(assertionSOAP);
                }
                // insert wsse:Security in header element
                soapHeader.addChildElement(wsseSecurity);
            } catch(SOAPException se){
                throw new Fault(se);
            }
            return wsuId;
        }
        
        protected String setWsuId(Element bodyElement) {
            String id = bodyElement.getAttributeNS(WSConstants.WSU_NS, "Id");

            if ((id == null) || (id.length() == 0)) {
                id = WSSConfig.getDefaultWSConfig().getIdAllocator().createId("id-", bodyElement);
                String prefix = 
                    WSSecurityUtil.setNamespace(bodyElement, WSConstants.WSU_NS, WSConstants.WSU_PREFIX);
                bodyElement.setAttributeNS(WSConstants.WSU_NS, prefix + ":Id", id);
            }
            return id;
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

    public SAMLAssertion getSamlAssertion() {
        return this.samlAssertionHolder.get();
    }

    public void setSamlAssertion(SAMLAssertion samlAssertion) {
        this.samlAssertionHolder.set(samlAssertion);
    }
}
