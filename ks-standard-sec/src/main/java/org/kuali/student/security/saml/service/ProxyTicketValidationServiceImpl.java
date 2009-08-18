package org.kuali.student.security.saml.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;
import org.kuali.student.security.cxf.interceptors.SamlTokenCxfOutInterceptor;

@WebService(endpointInterface = "org.kuali.student.security.saml.service.ProxyTicketValidationService", serviceName = "ProxyTicketValidationService", portName = "ProxyTicketValidationService", targetNamespace = "http://org.kuali.student/security/saml")
public class ProxyTicketValidationServiceImpl implements ProxyTicketValidationService {
    
    private String casServerUrl;
    private SamlTokenCxfOutInterceptor samlTokenCxfOutInterceptor = null;
    
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

            // don't think this sync is needed
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
            
            Map<String,String> samlProperties = new HashMap<String,String>();
            samlProperties.put("user", user);
            samlProperties.put("proxyGrantingTicket", pgt);
            samlProperties.put("proxies", proxies);
            
            if(samlTokenCxfOutInterceptor != null){
                samlTokenCxfOutInterceptor.setSamlProperties(samlProperties);
            }
            
            return "Signed SAML Assertion is in soap header for authenticated user";
            
        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
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

    public SamlTokenCxfOutInterceptor getSamlTokenCxfOutInterceptor() {
        return samlTokenCxfOutInterceptor;
    }

    public void setSamlTokenCxfOutInterceptor(SamlTokenCxfOutInterceptor samlTokenCxfOutInterceptor) {
        this.samlTokenCxfOutInterceptor = samlTokenCxfOutInterceptor;
    }
}
