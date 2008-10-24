package org.kuali.student.enumeration.service.impl;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.service.EnumerationService;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;

@WebService(name = "EnumerationService", targetNamespace = "http://student.kuali.org/wsdl/EnumerationService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class EnumerationServiceImpl implements EnumerationService{

    @WebMethod
    public List<String> getEnumerationKeys(){
        return null;
    }

    @WebMethod
    public List<EnumeratedValue> getEnumeration(@WebParam(name = "enumerationKey") String key) throws OperationFailedException{
        return null;
    }

    @WebMethod
    public EnumeratedValue addEnumeratedValue(@WebParam(name = "enumeratedValue")EnumeratedValue value){
        return null;
    }

    @WebMethod
    public EnumeratedValue updateEnumeratedValue(@WebParam(name = "enumeratedValueOld")EnumeratedValue oldValue, @WebParam(name = "enumeratedValueNew")EnumeratedValue newValue){
        return null;
    }

 //   public Status removeEnumeratedValue(String key);

}
