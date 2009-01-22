package org.kuali.student.core.dictionary.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.kuali.student.core.dictionary.dto.EnumeratedValue;
import org.kuali.student.core.dictionary.dto.ObjectStructure;

@WebService(name = "DictionaryService", targetNamespace = "http://org.kuali.student/core/dictonary")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface DictionaryService {

    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.dictionary.service.jaxws.GetObjectTypes", targetNamespace="http://org.kuali.student/core/dictonary")    
    @ResponseWrapper(className="org.kuali.student.core.dictionary.service.jaxws.GetObjectTypesResponse", targetNamespace="http://org.kuali.student/core/dictonary")
    public List<String> getObjectTypes();

    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.dictionary.service.jaxws.GetObjectStructure", targetNamespace="http://org.kuali.student/core/dictonary")    
    @ResponseWrapper(className="org.kuali.student.core.dictionary.service.jaxws.GetObjectStructureResponse", targetNamespace="http://org.kuali.student/core/dictonary")
    public ObjectStructure getObjectStructure(@WebParam(name = "objectTypeKey") String objectTypeKey);

    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.dictionary.service.jaxws.GetEnumeration", targetNamespace="http://org.kuali.student/core/dictonary")
    @ResponseWrapper(className="org.kuali.student.core.dictionary.service.jaxws.GetEnumerationResponse", targetNamespace="http://org.kuali.student/core/dictonary")    
    public List<EnumeratedValue> getEnumeration(@WebParam(name = "enumerationKey") String enumerationKey, 
            @WebParam(name = "enumContextKey") String enumContextKey, 
            @WebParam(name = "contextValue") String contextValue,
            @WebParam(name = "contextDate") Date contextDate);

    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.dictionary.service.jaxws.ValidateObject", targetNamespace="http://org.kuali.student/core/dictonary")    
    @ResponseWrapper(className="org.kuali.student.core.dictionary.service.jaxws.ValidateObjectResponse", targetNamespace="http://org.kuali.student/core/dictonary")
    public boolean validateObject(@WebParam(name = "objectTypeKey")String objectTypeKey, 
            @WebParam(name = "stateKey")String stateKey, 
            @WebParam(name = "info")String info);

    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.dictionary.service.jaxws.ValidateStructureData", targetNamespace="http://org.kuali.student/core/dictonary")    
    @ResponseWrapper(className="org.kuali.student.core.dictionary.service.jaxws.ValidateStructureDataResponse", targetNamespace="http://org.kuali.student/core/dictonary")
    public boolean validateStructureData(@WebParam(name = "objectTypeKey")String objectTypeKey, 
            @WebParam(name = "stateKey")String stateKey, 
            @WebParam(name = "info")String info);
}
