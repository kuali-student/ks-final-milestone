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

package org.kuali.student.security.saml.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;
import org.kuali.student.security.exceptions.KSSecurityException;
import org.kuali.student.security.util.SamlUtils;
import org.opensaml.SAMLAssertion;
import org.w3c.dom.Document;

@WebService(endpointInterface = "org.kuali.student.security.saml.service.SamlIssuerService", serviceName = "SamlIssuerService", portName = "SamlIssuerService", targetNamespace = "http://student.kuali.org/wsdl/security/saml")
public class SamlIssuerServiceImpl implements SamlIssuerService {
    
    private String casServerUrl;
    private String samlIssuerForUser;
    private String proxyCallBackUrl;
    
    public String validateCasProxyTicket(String proxyTicketId, String proxyTargetService) throws KSSecurityException{
        
        String url = constructUrl(proxyTicketId, proxyTargetService);
        HttpURLConnection conn = null;
        
        try {
            URL constructedUrl = new URL(url);
            conn = (HttpURLConnection) constructedUrl.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            StringBuffer stringBuffer = new StringBuffer(255);
            String response;

            while ((line = in.readLine()) != null) {
                stringBuffer.append(line);
            }
            
            response = stringBuffer.toString();
            String error = XmlUtils.getTextForElement(response, "authenticationFailure");

            if (CommonUtils.isNotEmpty(error)) {
                return error;
            }

            String user = XmlUtils.getTextForElement(response, "user");
            String pgt  = XmlUtils.getTextForElement(response, "proxyGrantingTicket");
            String proxies = XmlUtils.getTextForElement(response, "proxies");
            
            Map<String,String> samlProperties = new HashMap<String,String>();
            samlProperties.put("user", user.trim());
            samlProperties.put("proxyGrantingTicket", pgt.trim());
            samlProperties.put("proxies", proxies.trim());
            samlProperties.put("samlIssuerForUser", samlIssuerForUser.trim());
            
            SamlUtils.setSamlProperties(samlProperties);
            SAMLAssertion samlAssertion = SamlUtils.createAssertion();
            
            Document signedSAML = SamlUtils.signAssertion(samlAssertion);
            
            // transform the saml DOM into a writer, and return as a string response
            DOMSource domSource = new DOMSource(signedSAML);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer;
            
            transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            
            writer.flush();
            
            return writer.toString();
            
        } catch (final Exception e) {
            throw new KSSecurityException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    
    private String constructUrl(String proxyTicketId, String proxyTargetService) throws KSSecurityException{
        try {
            return this.casServerUrl + (this.casServerUrl.endsWith("/") ? "" : "/") + "proxyValidate" + "?ticket=" 
            + proxyTicketId + "&service=" + URLEncoder.encode(proxyTargetService, "UTF-8") 
            + "&pgtUrl=" + URLEncoder.encode(proxyCallBackUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new KSSecurityException(e);
        }
    }

    /* Use this method when not using a CAS proxy. 
     * Example if the ProxyTicketRetrieverFilter useCasProxyMechanism = false
     */
    public String getSamlPrincipal(String principal) throws KSSecurityException{
        try {      
            Map<String,String> samlProperties = new HashMap<String,String>();
            samlProperties.put("user", principal);
            samlProperties.put("proxyGrantingTicket", "");
            samlProperties.put("proxies", "");
            samlProperties.put("samlIssuerForUser", samlIssuerForUser.trim());
            
            SamlUtils.setSamlProperties(samlProperties);
            SAMLAssertion samlAssertion = SamlUtils.createAssertion();
            
            Document signedSAML = SamlUtils.signAssertion(samlAssertion);
            
            // transform the saml DOM into a writer, and return as a string response
            DOMSource domSource = new DOMSource(signedSAML);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer;
            
            transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            
            writer.flush();
            
            return writer.toString();
            
        } catch (final Exception e) {
            throw new KSSecurityException(e);
        } 

    }
    
    public String getCasServerUrl() {
        return casServerUrl;
    }

    public void setCasServerUrl(String casServerUrl) {
        this.casServerUrl = casServerUrl;
    }

    public String getSamlIssuerForUser() {
        return samlIssuerForUser;
    }

    public void setSamlIssuerForUser(String samlIssuerForUser) {
        this.samlIssuerForUser = samlIssuerForUser;
    }

    public String getProxyCallBackUrl() {
        return proxyCallBackUrl;
    }

    public void setProxyCallBackUrl(String proxyCallBackUrl) {
        this.proxyCallBackUrl = proxyCallBackUrl;
    }
}
