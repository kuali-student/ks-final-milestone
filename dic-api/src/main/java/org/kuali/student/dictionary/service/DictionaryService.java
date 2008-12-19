package org.kuali.student.dictionary.service;

import java.util.List;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.dictionary.dto.EnumeratedValue;
import org.kuali.student.dictionary.dto.ObjectStructure;

@WebService(name = "DictionaryService", targetNamespace = "http://org.kuali.student/dictonary")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface DictionaryService {

    @WebMethod
    public List<String> findObjectTypes();

    @WebMethod
    public ObjectStructure fetchObjectStructure(@WebParam(name = "objectTypeKey") String objectTypeKey);

    @WebMethod
    public List<EnumeratedValue> fetchEnumeration(@WebParam(name = "enumerationKey") String enumerationKey, 
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
