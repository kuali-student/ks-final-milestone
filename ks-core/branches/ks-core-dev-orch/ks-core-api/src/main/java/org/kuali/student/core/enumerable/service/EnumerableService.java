/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.enumerable.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.kuali.student.core.enumerable.dto.EnumeratedValue;
@WebService(name = "EnumerableService", targetNamespace = "http://student.kuali.org/wsdl/enumerable")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface EnumerableService {
    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.enumerable.service.jaxws.GetEnumeration", targetNamespace="http://student.kuali.org/wsdl/enumerable")
    @ResponseWrapper(className="org.kuali.student.core.enumerable.service.jaxws.GetEnumerationResponse", targetNamespace="http://student.kuali.org/wsdl/enumerable")    
    public List<EnumeratedValue> getEnumeration(@WebParam(name = "enumerationKey") String enumerationKey, 
            @WebParam(name = "enumContextKey") String enumContextKey, 
            @WebParam(name = "contextValue") String contextValue,
            @WebParam(name = "contextDate") Date contextDate);
}
