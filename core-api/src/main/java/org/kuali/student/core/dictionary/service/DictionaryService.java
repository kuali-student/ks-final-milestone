package org.kuali.student.core.dictionary.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
@WebService(name = "DictionaryService", targetNamespace = "http://org.kuali.student/core/dictionary")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface DictionaryService {
    
	/** 
     * Retrieves the list of object type identifiers known by this service. Example: cluInfo.
     * @param None No parameters
     * @return list of object type identifiers
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getObjectTypes() throws OperationFailedException;

    /** 
     * Retrieves the basic dictionary information about a particular object structure. Including all variations based on a certain type and state. Example: Given that a CLU is of type "Course" and in the state of "Proposed," tell which fields are read only, mandatory, not applicable, have enumerations available, etc.
     * @param objectTypeKey identifier of the object type
     * @return describes the fields for the input object type
     * @throws DoesNotExistException specified objectTypeKey not found
     * @throws InvalidParameterException invalid objectTypeKey
     * @throws MissingParameterException missing objectTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public ObjectStructure getObjectStructure(@WebParam(name="objectTypeKey")String objectTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

}
