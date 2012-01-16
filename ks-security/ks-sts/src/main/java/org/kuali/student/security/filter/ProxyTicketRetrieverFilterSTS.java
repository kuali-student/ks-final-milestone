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

package org.kuali.student.security.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xpath.XPathAPI;
import org.jasig.cas.client.validation.Assertion;
import org.kuali.student.security.exceptions.KSSecurityException;
import org.kuali.student.security.trust.dto.RequestSecurityTokenResponseType;
import org.kuali.student.security.trust.dto.RequestSecurityTokenType;
import org.kuali.student.security.trust.dto.RequestedSecurityTokenType;
import org.kuali.student.security.trust.service.SecurityTokenService;
import org.kuali.student.security.trust.service.SecurityTokenServiceImpl;
import org.kuali.student.security.util.SamlUtils;
import org.opensaml.SAMLAssertion;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ProxyTicketRetrieverFilterSTS extends GenericFilterBean {
    
    private String proxyTargetService = null;
    private SecurityTokenService stsClient;
    private boolean useCasProxyMechanism = false;

    public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			doFilterHttp((HttpServletRequest) request,
					(HttpServletResponse) response, chain);
		} else {
			// TODO: handle this
		}
	}

    public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        CasAuthenticationToken cat = (CasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        
        if(cat != null && !isSAMLInSecurityContext()){
            // This is not a SAML Assertion. It is CAS specific way to hold information about the authenticated user.
            // The information is returned from the CAS server as a response to a validation request.
            Assertion casAssertion = null;
            String proxyTicket = null;
            String principal = null;
            
            System.out.println("ProxyTicketRetrieverFilter STS : inside if");
            casAssertion = cat.getAssertion();
            if(casAssertion != null){
                System.out.println("ProxyTicketRetrieverFilter STS : casAssertion is not null");
                if(useCasProxyMechanism){
                    proxyTicket = casAssertion.getPrincipal().getProxyTicketFor(proxyTargetService);
                } else {
                    principal = casAssertion.getPrincipal().getName();
                }
            }
            
            Document signedSAMLDoc = null;
            SAMLAssertion samlAssertion = null;
            RequestSecurityTokenResponseType rstr = null;
            Element signedSAMLRet = null;
            
            try{
                System.out.println("ProxyTicketRetrieverFilter STS : Proxy Ticket Returned from CAS " + proxyTicket);
                if(useCasProxyMechanism){
                    RequestSecurityTokenType rst = prepareSecurityToken(proxyTicket, proxyTargetService);
                    rstr = stsClient.requestSecurityToken(rst);
                } else {
                    //signedSAMLRet = samlIssuerService.getSamlPrincipal(principal);
                }
                
                String tokenTypeUri = null;
                List<Object> objects = rstr.getAny();
                
                for(Object o : objects){
                    // if its being accessed as a SOAP service with JAXB.
                    if(o instanceof JAXBElement){
                        JAXBElement<?> e = (JAXBElement<?>)o;
                        if( e.getName().getLocalPart().equalsIgnoreCase("TokenType")){
                            tokenTypeUri = (String)e.getValue();
                        }
                        else if(e.getName().getLocalPart().equalsIgnoreCase("RequestedSecurityToken")) {
                            RequestedSecurityTokenType requestedToken = (RequestedSecurityTokenType)e.getValue();
                            signedSAMLRet = (Element)requestedToken.getAny();
                        }
                    
                    // if its being accessed with a client impl, no SOAP.
                    } else if(o instanceof Element){
                        Element e = (Element)o;
                        if( e.getLocalName().equalsIgnoreCase("TokenType")){
                            tokenTypeUri = e.getTextContent();
                        }
                        else if(e.getLocalName().equalsIgnoreCase("RequestedSecurityToken")) {
                            signedSAMLRet = (Element)e.getFirstChild();
                        }
                    }
                }
                
                
                // transform the saml DOM into a writer
                if(tokenTypeUri.equals(SecurityTokenServiceImpl.SAML_11_NS)){
                    DOMSource domSource = new DOMSource(signedSAMLRet);
                    StringWriter writer = new StringWriter();
                    StreamResult result = new StreamResult(writer);
                    
                    TransformerFactory tf = TransformerFactory.newInstance();
                    Transformer transformer;
                    
                    transformer = tf.newTransformer();
                    transformer.transform(domSource, result);
                    
                    writer.flush();
                    //System.out.println(writer.toString());
                    
                    // now build a new document before unsigning.
                    // I know this is nasty but is the only way it works. 
                    // Could not get it to work by just passing the signedSAMLRet
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    dbf.setNamespaceAware(true);
                    
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    ByteArrayInputStream bais = new ByteArrayInputStream(writer.toString().getBytes());
                 
                    signedSAMLDoc = db.parse(bais);
                    samlAssertion = SamlUtils.unsignAssertion(signedSAMLDoc);
                } else {
                    throw new ServletException("Security token not supported : " + tokenTypeUri);
                }
                
                 
             } catch(Exception e){
                 throw new ServletException(e);
             }
             
             // place saml in security context
             cat.setDetails(samlAssertion);
        }
        filterChain.doFilter(request, response);
    }
    
    private boolean isSAMLInSecurityContext(){
        CasAuthenticationToken cat = (CasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if(cat.getDetails() instanceof SAMLAssertion){
            return true;
        }
        return false;
    }

    private RequestSecurityTokenType prepareSecurityToken(String proxyTicket, String proxyTargetService) throws KSSecurityException {
        RequestSecurityTokenType rst = new RequestSecurityTokenType();
        rst.setContext("Optional URI specifies this request identifier");
        
        // "TokenType"
        Element tokenType = createElement("TokenType");
        tokenType.setTextContent("urn:oasis:names:tc:SAML:1.0:assertion");
        rst.getAny().add(tokenType);
        
        // "RequestType"
        Element requestType = createElement("RequestType");
        requestType.setTextContent("/Issue");
        rst.getAny().add(requestType);
        
        // "CasProxyTicket"
        Element casProxyTicket = createElement("CasProxyTicket");
        casProxyTicket.setTextContent(proxyTicket);
        rst.getAny().add(casProxyTicket);
        
        // "CasProxyTargetService"
        Element casProxyTargetService = createElement("CasProxyTargetService");
        casProxyTargetService.setTextContent(proxyTargetService);
        rst.getAny().add(casProxyTargetService);
        
        return rst;
    }
    
    private Element createElement(String tagName) throws KSSecurityException{
        
        Element element = null;
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
    
            element = doc.createElementNS("http://schemas.xmlsoap.org/ws/2005/02/trust", tagName);
            
        } catch(Exception e){
            throw new KSSecurityException(e);
        }
        return element;
    }
    
//    @Override
//    public int getOrder() {
//        return FilterChainOrder.CAS_PROCESSING_FILTER + 2;
//    }

    public String getProxyTargetService() {
        return proxyTargetService;
    }

    public void setProxyTargetService(String proxyTargetService) {
        this.proxyTargetService = proxyTargetService;
    }

    public SecurityTokenService getSecurityTokenService() {
        return stsClient;
    }

    public void setSecurityTokenService(SecurityTokenService stsClient) {
        this.stsClient = stsClient;
    }

    public boolean getUseCasProxyMechanism() {
        return useCasProxyMechanism;
    }

    public void setUseCasProxyMechanism(boolean useCasProxyMechanism) {
        this.useCasProxyMechanism = useCasProxyMechanism;
    }

}
