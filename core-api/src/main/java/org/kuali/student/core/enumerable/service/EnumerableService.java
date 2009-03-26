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
@WebService(name = "EnumerableService", targetNamespace = "http://student.kuali.org/core/enumerable")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface EnumerableService {
    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.enumerable.service.jaxws.GetEnumeration", targetNamespace="http://student.kuali.org/core/enumerable")
    @ResponseWrapper(className="org.kuali.student.core.enumerable.service.jaxws.GetEnumerationResponse", targetNamespace="http://student.kuali.org/core/enumerable")    
    public List<EnumeratedValue> getEnumeration(@WebParam(name = "enumerationKey") String enumerationKey, 
            @WebParam(name = "enumContextKey") String enumContextKey, 
            @WebParam(name = "contextValue") String contextValue,
            @WebParam(name = "contextDate") Date contextDate);
}
