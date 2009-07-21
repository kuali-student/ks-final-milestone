package org.kuali.student.security.saml.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.jws.WebService;

import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;

@WebService(endpointInterface = "org.kuali.student.security.saml.service.ProxyTicketValidationService", serviceName = "ProxyTicketValidationService", portName = "ProxyTicketValidationService", targetNamespace = "http://org.kuali.student/security/saml")
public class ProxyTicketValidationServiceImpl implements ProxyTicketValidationService {
    
    private String casServerUrl;
    
    public String validateProxyTicket(String proxyTicketId, String proxyTargetService){
        
        String url = constructUrl(proxyTicketId, proxyTargetService);
        HttpURLConnection conn = null;
        
        try {
            URL constructedUrl = new URL(url);
            conn = (HttpURLConnection) constructedUrl.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            StringBuffer stringBuffer = new StringBuffer(255);
            String response;

            // don't think i this sync is needed
            synchronized (stringBuffer) {
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line);
                }
                response = stringBuffer.toString();
            }

            String error = XmlUtils.getTextForElement(response, "authenticationFailure");

            if (CommonUtils.isNotEmpty(error)) {
                return error;
            }

            String user = XmlUtils.getTextForElement(response, "user");
            String pgt  = XmlUtils.getTextForElement(response, "proxyGrantingTicket");
            String proxies = XmlUtils.getTextForElement(response, "proxies");
            
            // we have to take the user and added to the attributes in the SAML Assertion before inserting in WS-Security header.
            return "Signed SAML Assertion is in soap header for user : " + user + "\n" + 
            "proxyGrantingTicket" + pgt + "\n" + "proxies : " + proxies;
            
        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        
        /*DefaultBootstrap.bootstrap(); 
        XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
        
        SAMLObjectBuilder<Issuer> issuerBuilder = (SAMLObjectBuilder<Issuer>) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
        Issuer issuer = issuerBuilder.buildObject();
        
        SAMLObjectBuilder<Subject> subjectBuilder = (SAMLObjectBuilder<Subject>) builderFactory.getBuilder(Subject.DEFAULT_ELEMENT_NAME);
        Subject subject = subjectBuilder.buildObject();
        
        SAMLObjectBuilder<AuthnStatement> authnStatementBuilder = (SAMLObjectBuilder<AuthnStatement>) builderFactory.getBuilder(AuthnStatement.DEFAULT_ELEMENT_NAME);
        AuthnStatement authnStatement = authnStatementBuilder.buildObject();
        
        SAMLObjectBuilder<Assertion> assertionBuilder = (SAMLObjectBuilder<Assertion>) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
        Assertion assertion = assertionBuilder.buildObject();
        assertion.setVersion(SAMLVersion.VERSION_20);
        assertion.setID("xxxxx-ID-xxxxx");
        assertion.setIssueInstant(new DateTime());
        assertion.setIssuer(issuer);
        assertion.setSubject(subject);
        List<AuthnStatement> authnStatements = assertion.getAuthnStatements();
        authnStatements.add(authnStatement);*/
        
        
        /*NameIDType nameIDType = new NameIDType();
        nameIDType.setValue("https://identityProvider.umd.edu/SAML2");
        
        SubjectType subjectType = new SubjectType();
        //subjectType.getContent().add(e);
        
        AuthnStatementType authStmtType = new AuthnStatementType();
        authStmtType.setSessionIndex("b07b804c-7c29-ea16-7300-4f3d6f7928ac");
        
        AssertionType assertionType = new AssertionType();
        assertionType.setVersion(SAMLVersion.VERSION_20.toString());
        assertionType.setID("xxxxx-ID-xxxxx");
        assertionType.setIssuer(nameIDType);
        assertionType.getStatementOrAuthnStatementOrAuthzDecisionStatement().add(authStmtType);*/
    }
    
    private String constructUrl(String proxyTicketId, String proxyTargetService) {
        try {
            return this.casServerUrl + (this.casServerUrl.endsWith("/") ? "" : "/") + "proxyValidate" + "?ticket=" 
            + proxyTicketId + "&service=" + URLEncoder.encode(proxyTargetService, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCasServerUrl() {
        return casServerUrl;
    }

    public void setCasServerUrl(String casServerUrl) {
        this.casServerUrl = casServerUrl;
    }
}
