package org.kuali.student.security.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.jasig.cas.client.validation.Assertion;
import org.kuali.student.security.saml.service.SamlIssuerService;
import org.opensaml.SAMLAssertion;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.cas.CasAuthenticationToken;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ProxyTicketRetrieverFilter extends SpringSecurityFilter {
    
    //private String proxyTargetService = "http://localhost:8181/ks-core-web/Service/ProxyTicketValidationService";
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
            
            // if statement above checks for SAML in security context, if its there don't make this service call.
            // The first time we make this call the CxfJaxWsProxyClientFactory client with SamlTokenInHandler interceptor 
            // wiil place the SAML from the header in the security context, therefore skipping this call in the next request.
            String signedSAMLRet = samlIssuerService.validateCasProxyTicket(proxyTicket, proxyTargetService);
            
            javax.xml.parsers.DocumentBuilderFactory dbf =
                javax.xml.parsers.DocumentBuilderFactory.newInstance();

             //XML Signature needs to be namespace aware
             dbf.setNamespaceAware(true);
             Document signedSAMLDoc = null;
             try{
                 javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
                 ByteArrayInputStream bais = new ByteArrayInputStream(signedSAMLRet.getBytes());
             
                 signedSAMLDoc = db.parse(bais);
                 
             } catch(Exception e){
                 throw new ServletException(e.getMessage());
             }

            //samlAssertion = signedSAML.unsign();
            cat.setDetails(signedSAMLDoc);
        }
        filterChain.doFilter(request, response);
    }
    
    private boolean isSAMLInSecurityContext(){
        CasAuthenticationToken cat = (CasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //if(cat.getDetails() instanceof SAMLAssertion){
        if(cat.getDetails() instanceof Document){
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
