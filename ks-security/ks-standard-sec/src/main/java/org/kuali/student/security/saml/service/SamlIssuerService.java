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

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.security.exceptions.KSSecurityException;

@WebService(name = "SamlIssuerService", targetNamespace = "http://student.kuali.org/wsdl/security/saml")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface SamlIssuerService {

    /* takes a CAS proxy ticket, validates it and returns a SAML token */
    public String validateCasProxyTicket(@WebParam(name="proxyTicketId")String proxyTicketId, 
                                      @WebParam(name="proxyTargetService")String proxyTargetService) throws KSSecurityException;
    
    /* takes a principal and returns a SAML token */
    public String getSamlPrincipal(@WebParam(name="principal")String principal) throws KSSecurityException;
}
