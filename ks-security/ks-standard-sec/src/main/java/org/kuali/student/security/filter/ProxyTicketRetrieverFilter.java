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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jasig.cas.client.validation.Assertion;
import org.kuali.student.security.saml.service.SamlIssuerService;
import org.kuali.student.security.util.SamlUtils;
import org.opensaml.SAMLAssertion;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.cas.CasAuthenticationToken;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.w3c.dom.Document;

public class ProxyTicketRetrieverFilter extends SpringSecurityFilter {
    
    private String proxyTargetService = null;
    private SamlIssuerService samlIssuerService;
    private boolean useCasProxyMechanism = false;
    
    @Override
    public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        CasAuthenticationToken cat = (CasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        
        if(cat != null && !isSAMLInSecurityContext()){
            // This is not a SAML Assertion. It is CAS specific way to hold information about the authenticated user.
            // The information is returned from the CAS server as a response to a validation request.
            Assertion casAssertion = null;
            String proxyTicket = null;
            String principal = null;
            
            System.out.println("ProxyTicketRetrieverFilter : inside if");
            casAssertion = cat.getAssertion();
            if(casAssertion != null){
                System.out.println("ProxyTicketRetrieverFilter : casAssertion is not null");
                if(useCasProxyMechanism){
                    proxyTicket = casAssertion.getPrincipal().getProxyTicketFor(proxyTargetService);
                } else {
                    principal = casAssertion.getPrincipal().getName();
                }
            }
            
            Document signedSAMLDoc = null;
            SAMLAssertion samlAssertion = null;
            String signedSAMLRet = null;
            
            try{
                System.out.println("ProxyTicketRetrieverFilter : Proxy Ticket Returned from CAS " + proxyTicket);
                if(useCasProxyMechanism){
                    signedSAMLRet = samlIssuerService.validateCasProxyTicket(proxyTicket, proxyTargetService);
                } else {
                    signedSAMLRet = samlIssuerService.getSamlPrincipal(principal);
                }
                
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
                
                DocumentBuilder db = dbf.newDocumentBuilder();
                ByteArrayInputStream bais = new ByteArrayInputStream(signedSAMLRet.getBytes());
             
                signedSAMLDoc = db.parse(bais);
                samlAssertion = SamlUtils.unsignAssertion(signedSAMLDoc);
                 
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

    @Override
    public int getOrder() {
        return FilterChainOrder.CAS_PROCESSING_FILTER + 2;
    }

    public String getProxyTargetService() {
        return proxyTargetService;
    }

    public void setProxyTargetService(String proxyTargetService) {
        this.proxyTargetService = proxyTargetService;
    }

    public SamlIssuerService getSamlIssuerService() {
        return samlIssuerService;
    }

    public void setSamlIssuerService(SamlIssuerService samlIssuerService) {
        this.samlIssuerService = samlIssuerService;
    }

    public boolean getUseCasProxyMechanism() {
        return useCasProxyMechanism;
    }

    public void setUseCasProxyMechanism(boolean useCasProxyMechanism) {
        this.useCasProxyMechanism = useCasProxyMechanism;
    }

}
