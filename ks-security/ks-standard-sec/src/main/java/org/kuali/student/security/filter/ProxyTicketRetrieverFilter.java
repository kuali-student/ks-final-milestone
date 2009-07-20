package org.kuali.student.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.validation.Assertion;
import org.kuali.student.security.saml.service.ProxyTicketValidationService;
import org.opensaml.SAMLAssertion;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.cas.CasAuthenticationToken;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.security.ui.WebAuthenticationDetails;

public class ProxyTicketRetrieverFilter extends SpringSecurityFilter {
    
    private String proxyTargetService = "http://localhost:8181/ks-core-web/Service/ProxyTicketValidationService";
    private ProxyTicketValidationService samlIssuerService;
    
    @Override
    public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        
        CasAuthenticationToken cat = (CasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        
        if(cat != null && !isSAMLInSecurityContext()){
            System.out.println("Request URI : " + request.getRequestURI() );
            System.out.println("CasAuthenticationToken is not null");
            System.out.println("The Details : " + ((WebAuthenticationDetails)cat.getDetails()).getRemoteAddress() );
            Assertion assertion = null;
            String proxyTicket = "result";
            
            assertion = cat.getAssertion();
            
            if(assertion != null){
                System.out.println("assertion is not null");
                proxyTicket = assertion.getPrincipal().getProxyTicketFor(proxyTargetService);
            }
            
            // IF() statement above checks for SAML in security context, if its there don't make this service call
            // the first time we make this call the CxfJaxWsProxyClientFactory client with SamlTokenInHandler interceptor 
            // wiil place the SAML from the header in the security context, then the next time we dont make the call.
            System.out.println("The proxy ticket sent : " + proxyTicket);
            String serviceResponse = samlIssuerService.validateProxyTicket(proxyTicket, proxyTargetService);
            System.out.println("samlIssuerService response : " + serviceResponse);
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

    public ProxyTicketValidationService getSamlIssuerService() {
        return samlIssuerService;
    }

    public void setSamlIssuerService(ProxyTicketValidationService samlIssuerService) {
        this.samlIssuerService = samlIssuerService;
    }

}
