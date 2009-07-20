package org.kuali.student.security.saml.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "ProxyTicketValidationService", targetNamespace = "http://org.kuali.student/security/saml")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ProxyTicketValidationService {

    public String validateProxyTicket(@WebParam(name="proxyTicketId")String proxyTicketId, 
                                      @WebParam(name="proxyTargetService")String proxyTargetService);
}
