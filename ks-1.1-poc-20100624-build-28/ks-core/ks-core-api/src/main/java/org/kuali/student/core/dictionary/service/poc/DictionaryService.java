package org.kuali.student.core.dictionary.service.poc;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.kuali.student.core.dictionary.poc.dto.ObjectStructureDefinition;

@WebService(name = "DictionaryService", targetNamespace = "http://student.kuali.org/wsdl/dictionary")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface DictionaryService {
    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.dictionary.service.poc.jaxws.GetObjectTypes", targetNamespace="http://student.kuali.org/wsdl/dictionary")    
    @ResponseWrapper(className="org.kuali.student.core.dictionary.service.poc.jaxws.GetObjectTypesResponse", targetNamespace="http://student.kuali.org/wsdl/dictionary")
    public List<String> getObjectTypes();

    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.dictionary.service.poc.jaxws.GetObjectStructure", targetNamespace="http://student.kuali.org/wsdl/dictionary")    
    @ResponseWrapper(className="org.kuali.student.core.dictionary.service.poc.jaxws.GetObjectStructureResponse", targetNamespace="http://student.kuali.org/wsdl/dictionary")    
    public ObjectStructureDefinition getObjectStructure(@WebParam(name = "objectTypeKey") String objectTypeKey);
}
