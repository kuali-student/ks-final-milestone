package org.kuali.student.ap.test.mock;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

import javax.jws.WebParam;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnumerationManagementServiceMockTest implements EnumerationManagementService {
    /**
     * Retrieves the list of meta information for the enumerations supported by
     * this service.
     *
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return List of enumeration meta information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<EnumerationInfo> getEnumerations(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves meta information for a particular Enumeration. The meta
     * information should describe constraints on the various fields comprising
     * the enumeration as well as the allowed contexts.
     *
     * @param enumerationKey identifier for the Enumeration
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return Meta information about an enumeration
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          enumerationKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing enumerationKey or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public EnumerationInfo getEnumeration(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of values for a particular enumeration with a certain
     * context for a particular date. The values returned should be those where
     * the supplied date is between the effective and expiration dates. Certain
     * enumerations may not support this functionality.
     *
     * @param enumerationKey identifier for the Enumeration
     * @param contextTypeKey identifier for the enumeration context type
     * @param contextValue   value for the enumeration context
     * @param contextDate    date and time to get the enumeration for
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return List of Codes and Values
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          enumerationKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextValue, contextDate or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing enumerationKey, contextTypeKey,
     *          contextValue, contextDate or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<EnumeratedValueInfo> getEnumeratedValues(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "contextTypeKey") String contextTypeKey, @WebParam(name = "contextValue") String contextValue, @WebParam(name = "contextDate") Date contextDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates an EnumerationValue. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this object. If an identifier is present for the Process and a
     * record is found for that identifier, the validation checks if the Process
     * can be shifted to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as such, the
     * checks performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the server
     * assigning an identifier.
     *
     * @param validationTypeKey   the identifier of the extent of validation
     * @param enumerationKey      identifier for the Enumeration
     * @param code                code identifying the value to be validated
     * @param enumeratedValueInfo the Room information to be tested
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return Results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey, enumerationKey or
     *          code not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid enumeratedValueInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, enumerationKey,
     *          code, enumeratedValueInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateEnumeratedValue(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "code") String code, @WebParam(name = "enumeratedValueInfo") EnumeratedValueInfo enumeratedValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates a value in a particular Enumeration. The pattern in this
     * signature is different from most updates in that it is unlikely for
     * multiple individuals or processes to be altering the same construct at
     * the same time.
     *
     * @param enumerationKey      identifier for the Enumeration
     * @param code                code identifying the value to be updated
     * @param enumeratedValueInfo updated information on the value
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return updated enumerated value
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          enumerationKey, code not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid enumeratedValueInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing enumerationKey, code,
     *          enumeratedValueInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at changing information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          an optimistic locking failure or the
     *          action was attempted on an out of
     *          date version
     */
    @Override
    public EnumeratedValueInfo updateEnumeratedValue(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "code") String code, @WebParam(name = "enumeratedValueInfo") EnumeratedValueInfo enumeratedValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Removes a value from a particular Enumeration. This particular operation
     * should be used sparingly, as removal of a value may lead to dangling
     * references. It is suggested that standard procedure should be to update
     * the expiration date for the value so that it is seen as expired.
     *
     * @param enumerationKey Identifier for the Enumeration
     * @param code           code identifying the value to be removed
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return Status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          enumerationKey, code not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing enumerationKey, code or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteEnumeratedValue(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "code") String code, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Adds a value to a particular Enumeration.
     *
     * @param enumerationKey      Identifier for the Enumeration
     * @param code                code identifying the value to be added
     * @param enumeratedValueInfo Value to be added
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return Newly created enumerated value
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          combination of enumerationKey, code
     *          already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          enumerationKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid enumeratedValueInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing enumerationKey, enumeratedValueInfo
     *          or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at changing information
     *          designated as read only
     */
    @Override
    public EnumeratedValueInfo addEnumeratedValue(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "code") String code, @WebParam(name = "enumeratedValueInfo") EnumeratedValueInfo enumeratedValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of search types known by this service.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of search type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<TypeInfo> getSearchTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a particular search type.
     *
     * @param searchTypeKey identifier of the search type
     * @param contextInfo   information containing the principalId and locale
     *                      information about the caller of service operation
     * @return information on the search type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified searchTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          searchTypeKey or contextInfo is missing
     *          or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public TypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Performs a search.
     *
     * @param searchRequestInfo the search request
     * @param contextInfo       information containing the principalId and locale
     *                          information about the caller of service operation
     * @return the results of the search
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          searchRequestInfo or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
