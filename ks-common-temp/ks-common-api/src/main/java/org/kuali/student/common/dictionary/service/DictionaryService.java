package org.kuali.student.common.dictionary.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;

@WebService(name = "DictionaryService", targetNamespace = "http://student.kuali.org/wsdl/dictionary")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface DictionaryService {
    @WebMethod
    @RequestWrapper(className="org.kuali.student.common.dictionary.service.jaxws.GetObjectTypes", targetNamespace="http://student.kuali.org/wsdl/dictionary")    
    @ResponseWrapper(className="org.kuali.student.common.dictionary.service.jaxws.GetObjectTypesResponse", targetNamespace="http://student.kuali.org/wsdl/dictionary")
    public List<String> getObjectTypes();

    @WebMethod
    @RequestWrapper(className="org.kuali.student.common.dictionary.service.jaxws.GetObjectStructure", targetNamespace="http://student.kuali.org/wsdl/dictionary")    
    @ResponseWrapper(className="org.kuali.student.common.dictionary.service.jaxws.GetObjectStructureResponse", targetNamespace="http://student.kuali.org/wsdl/dictionary")    
    public ObjectStructureDefinition getObjectStructure(@WebParam(name = "objectTypeKey") String objectTypeKey);
}
