package org.kuali.student.enumeration.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.Enumerations;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;

@WebService(name = "EnumerationService", targetNamespace = "http://student.kuali.org/wsdl/EnumerationService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface EnumerationService {

    @WebMethod
    public List<Enumerations> getEnumerations();

    @WebMethod
    public List<EnumeratedValue> getEnumeration(@WebParam(name = "enumerationKey") String key) throws OperationFailedException;

    @WebMethod
    public EnumeratedValue addEnumeratedValue(@WebParam(name = "enumeratedValue")EnumeratedValue value);

    @WebMethod
    public EnumeratedValue updateEnumeratedValue(@WebParam(name = "enumeratedValueOld")EnumeratedValue oldValue, @WebParam(name = "enumeratedValueNew")EnumeratedValue newValue);

 //   public Status removeEnumeratedValue(String key);

}
