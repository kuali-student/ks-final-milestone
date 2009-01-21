package org.kuali.student.core.dictionary.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.dictionary.dto.EnumeratedValue;
import org.kuali.student.core.dictionary.dto.ObjectStructure;

@WebService(name = "DictionaryService", targetNamespace = "http://org.kuali.student/core/dictonary")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface DictionaryService {

    @WebMethod
    public List<String> getObjectTypes();

    @WebMethod
    public ObjectStructure getObjectStructure(@WebParam(name = "objectTypeKey") String objectTypeKey);

    @WebMethod
    public List<EnumeratedValue> getEnumeration(@WebParam(name = "enumerationKey") String enumerationKey, 
            @WebParam(name = "enumContextKey") String enumContextKey, 
            @WebParam(name = "contextValue") String contextValue,
            @WebParam(name = "contextDate") Date contextDate);

    @WebMethod
    public boolean validateObject(@WebParam(name = "objectTypeKey")String objectTypeKey, 
            @WebParam(name = "stateKey")String stateKey, 
            @WebParam(name = "info")String info);

    @WebMethod
    public boolean validateStructureData(@WebParam(name = "objectTypeKey")String objectTypeKey, 
            @WebParam(name = "stateKey")String stateKey, 
            @WebParam(name = "info")String info);
}
