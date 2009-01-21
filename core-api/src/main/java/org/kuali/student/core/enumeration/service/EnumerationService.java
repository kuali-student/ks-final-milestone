package org.kuali.student.core.enumeration.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;

import org.kuali.student.core.enumeration.dto.EnumeratedValue;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;

public interface EnumerationService {
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
    public List<EnumeratedValue> getEnumeration(@WebParam(name="enumerationKey")String enumerationKey, @WebParam(name="contextType")String contextType, @WebParam(name="contextValue")String contextValue, @WebParam(name="contextDate")Date contextDate) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

}
