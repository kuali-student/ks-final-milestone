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
    
    @Override
    public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("\n\n In the  ProxyTicketRetrieverFilter  ...... ");
        CasAuthenticationToken cat = (CasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        
        if(cat != null && !isSAMLInSecurityContext()){
            // This is not a SAML Assertion. It is CAS specific way to hold information about the authenticated user.
            // The information is returned from the CAS server as a response to a validation request.
            Assertion casAssertion = null;
            String proxyTicket = null;
            
            casAssertion = cat.getAssertion();
            if(casAssertion != null){
                proxyTicket = casAssertion.getPrincipal().getProxyTicketFor(proxyTargetService);
            }
            
            // I think this is the proxyGrantingTicket PGT, not the proxyTicket
            System.out.println("\n\n In the  ProxyTicketRetrieverFilter proxyTicket = " + proxyTicket);
            
            Document signedSAMLDoc = null;
            SAMLAssertion samlAssertion = null;
            
            try{
                String signedSAMLRet = samlIssuerService.validateCasProxyTicket(proxyTicket, proxyTargetService);
                
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
            
            System.out.println("\n\n In the  ProxyTicketRetrieverFilter finish");
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

}
