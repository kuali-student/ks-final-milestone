package org.kuali.student.core.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;

import org.kuali.student.core.dictionary.dto.EnumeratedValue;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;

public interface DictionaryService {
    /** 
     * Retrieves the list of object type identifiers known by this service. Example: cluInfo.
     * @param None No parameters
     * @return list of object type identifiers
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> findObjectTypes() throws OperationFailedException;

    /** 
     * Retrieves the basic dictionary information about a particular object structure. Including all variations based on a certain type and state. Example: Given that a CLU is of type "Course" and in the state of "Proposed," tell which fields are read only, mandatory, not applicable, have enumerations available, etc.
     * @param objectTypeKey identifier of the object type
     * @return describes the fields for the input object type
     * @throws DoesNotExistException specified objectTypeKey not found
     * @throws InvalidParameterException invalid objectTypeKey
     * @throws MissingParameterException missing objectTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public ObjectStructure fetchObjectStructure(@WebParam(name="objectTypeKey")String objectTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of enumeration values for a particular enumeration with a certain context for a particular date. The values returned should be those where the supplied date is between the effective and expiration dates. Certain enumerations may not support this functionality.
     * @param enumerationKey identifier of the enumeration
     * @param contextType identifier of the enumeration context type
     * @param contextValue value of the enumeration context
     * @param contextDate date and time to get the enumeration for
     * @return list of enumerated codes and values
     * @throws DoesNotExistException enumerationKey not found
     * @throws InvalidParameterException invalid enumerationKey, contextType, contextValue, contextDate
     * @throws MissingParameterException missing enumerationKey, contextType, contextValue, contextDate
     * @throws OperationFailedException unable to complete request
	 */
    public List<EnumeratedValue> fetchEnumeration(@WebParam(name="enumerationKey")String enumerationKey, @WebParam(name="contextType")String contextType, @WebParam(name="contextValue")String contextValue, @WebParam(name="contextDate")Date contextDate) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

}
