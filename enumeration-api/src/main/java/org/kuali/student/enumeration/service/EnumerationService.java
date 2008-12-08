package org.kuali.student.enumeration.service;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.dto.EnumerationMeta;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.springframework.beans.factory.annotation.Required;

@WebService(name = "EnumerationService", targetNamespace = "http://student.kuali.org/wsdl/EnumerationService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface EnumerationService {

    @WebMethod
    public EnumerationMetaList findEnumerationMetas();

    @WebMethod
    public EnumerationMeta fetchEnumerationMeta(@WebParam(name = "enumerationKey") String enumerationKey);

    @WebMethod
    public EnumeratedValueList fetchEnumeration(
            @WebParam(name = "enumerationKey") String enumerationKey, 
            @WebParam(name = "enumContextKey") String enumContextKey, 
            @WebParam(name = "contextValue")   String contextValue, 
            @WebParam(name = "contextDate") Date   contextDate );
    
    @WebMethod
    public EnumeratedValue addEnumeratedValue(
            @WebParam(name = "enumerationKey")String enumerationKey, 
            @WebParam(name = "value")EnumeratedValue value);
    @WebMethod
    public EnumeratedValue updateEnumeratedValue(
            @WebParam(name = "enumerationKey")String enumerationKey, 
            @WebParam(name = "code")String code, 
            @WebParam(name = "value")EnumeratedValue value);
    @WebMethod
    public boolean removeEnumeratedValue(
            @WebParam(name = "enumerationKey")String enumerationKey, 
            @WebParam(name = "code")String code);
    
    @WebMethod
    public EnumerationMeta addEnumerationMeta(@WebParam(name = "enumerationMeta")EnumerationMeta meta);
    
    @WebMethod
    public boolean removeEnumerationMeta(@WebParam(name = "enumerationKey")String enumerationKey);

}
