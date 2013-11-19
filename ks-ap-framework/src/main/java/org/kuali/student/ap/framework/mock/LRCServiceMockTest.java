package org.kuali.student.ap.framework.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.jws.WebParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class LRCServiceMockTest implements LRCService {
    /**
     * Retrieves existing result values group by an identifier.
     *
     * @param resultValuesGroupKey identifiers for resultValuesGroup to be retrieved
     * @param contextInfo          Context information containing the principalId
     *                             and locale information about the caller of service
     *                             operation
     * @return details of the results for these Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValuesGroupKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValuesGroupKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid resultValuesGroupKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ResultValuesGroupInfo getResultValuesGroup(@WebParam(name = "resultValuesGroupKey") String resultValuesGroupKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves result values groups by a list of identifiers.
     *
     * @param resultValuesGroupKeys identifiers for result values group
     * @param contextInfo           Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return result values group list
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValuesGroup not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValuesGroupKeys
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid resultValuesGroupKeys
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByKeys(@WebParam(name = "resultValuesGroupKeys") List<String> resultValuesGroupKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of existing result values groups that a result value is tied to.
     *
     * @param resultValueKey identifier for result value
     * @param contextInfo    Context information containing the principalId
     *                       and locale information about the caller of service
     *                       operation
     * @return details of the results for these keys
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValue not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValueKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid resultValueKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(@WebParam(name = "resultValueKey") String resultValueKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of existing result values groups that a result scale is tied to.
     *
     * @param resultScaleKey identifier for result scale
     * @param contextInfo    Context information containing the principalId
     *                       and locale information about the caller of service
     *                       operation
     * @return details of the results for these keys
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValue not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValueKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid resultValueKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of result group identifiers for a specified
     * result values group type.
     *
     * @param resultValuesGroupTypeKey identifier for the result group type
     * @param contextInfo              Context information containing the principalId
     *                                 and locale information about the caller of service
     *                                 operation
     * @return list of result group identifiers
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValuesGroupTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValuesGroupTypeKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValuesGroupTypeKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getResultValuesGroupKeysByType(@WebParam(name = "resultValuesGroupTypeKey") String resultValuesGroupTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new result Values Group.
     *
     * @param resultValuesGroupInfo information about the result values group
     *                              being created
     * @param contextInfo           Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return create result values group information
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          result values group already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          one or more values invalid for
     *          this operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValuesGroupInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValuesGroupInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ResultValuesGroupInfo createResultValuesGroup(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "resultValuesGroupTypeKey") String resultValuesGroupTypeKey, @WebParam(name = "resultGroupInfo") ResultValuesGroupInfo resultValuesGroupInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing result values group.
     *
     * @param resultValuesGroupKey identifier of the result values group to update
     * @param resultGroupInfo      updated information about the result values group
     * @param contextInfo          Context information containing the principalId
     *                             and locale information about the caller of service
     *                             operation
     * @return updated result values group information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          one or more values invalid for
     *          this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValuesGroupKey not found
     * @throws InvalidParameterExceptioninvalid
     *          resultValuesGroupKey or
     *          resultValuesGroupInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValuesGroupKey or
     *          resultValuesGroupInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          action was attempted on an out of
     *          date version
     */
    @Override
    public ResultValuesGroupInfo updateResultValuesGroup(@WebParam(name = "resultValuesGroupKey") String resultValuesGroupKey, @WebParam(name = "resultValuesGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing result values group.
     *
     * @param resultValuesGroupKey identifier of the result values group to update
     * @param contextInfo          Context information containing the principalId
     *                             and locale information about the caller of service
     *                             operation
     * @return status of the operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValuesGroupKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValuesGroupKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValuesGroupKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteResultValuesGroup(@WebParam(name = "resultValuesGroupKey") String resultValuesGroupKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a result values group. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects
     * or expanded to perform all tests related to this object.
     *
     * @param validationType       Identifier of the extent of validation
     * @param gradeValuesGroupInfo Result values group to be validated
     * @param contextInfo          Context information containing the principalId
     *                             and locale information about the caller of service
     *                             operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValuesGroupInfo does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          validationType or
     *          resultValuesGroupInfo does not exist
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationType, resultValuesGroupInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateResultValuesGroup(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get or create a new result values group holding the specified number of credits
     * <p/>
     * The resulting RVG should have a type of FIXED
     * <p/>
     * May also create the corresponding credit value.
     *
     * @param creditValue the credit value to be found/created
     * @param scaleKey    the scale associated with this type of credit (regular or remedial or continuing ed, etc)
     * @param contextInfo Context information containing the principalId
     *                    and locale information about the caller of service
     *                    operation
     * @return create result values group information
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          result values group already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          one or more values invalid for
     *          this operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValuesGroupInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValuesGroupInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @impl the parameters hold string representations of floating points to avoid rounding issues
     */
    @Override
    public ResultValuesGroupInfo getCreateFixedCreditResultValuesGroup(@WebParam(name = "creditValue") String creditValue, @WebParam(name = "scaleKey") String scaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get or create a new result values group holding the specified number of credits
     * <p/>
     * The resulting RVG will have a type of RANGE
     *
     * @param creditValueMin       the minimum credit value of the range to be found/created
     * @param creditValueMax       the maximum credit value to be found/created
     * @param creditValueIncrement the credit value increment of the range to be found/created
     * @param scaleKey             the scale associated with this type of credit (regular or remedial or continuing ed, etc)
     * @param contextInfo          Context information containing the principalId
     *                             and locale information about the caller of service
     *                             operation
     * @return create result values group information
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          result values group already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          one or more values invalid for
     *          this operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValuesGroupInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValuesGroupInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @impl the parameters hold string representations of floating points to avoid rounding issues
     */
    @Override
    public ResultValuesGroupInfo getCreateRangeCreditResultValuesGroup(@WebParam(name = "creditValueMin") String creditValueMin, @WebParam(name = "creditValueMax") String creditValueMax, @WebParam(name = "creditValueIncrement") String creditValueIncrement, @WebParam(name = "scaleKey") String scaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get or create a new result values group holding the specified number of credits
     * <p/>
     * The resulting RVG should have the type of MULTIPLE
     *
     * @param creditValues the minimum credit value of the range to be found/created
     * @param scaleKey     the scale associated with this type of credit (regular or remedial or continuing ed, etc)
     * @param contextInfo  Context information containing the principalId
     *                     and locale information about the caller of service
     *                     operation
     * @return create result values group information
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          result values group already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          one or more values invalid for
     *          this operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValuesGroupInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValuesGroupInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @impl the parameters hold string representations of floating points to avoid rounding issues
     */
    @Override
    public ResultValuesGroupInfo getCreateMultipleCreditResultValuesGroup(@WebParam(name = "creditValues") List<String> creditValues, @WebParam(name = "scaleKey") String scaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves result value by its id.
     *
     * @param resultValueKey identifier for the result
     * @param contextInfo    Context information containing the principalId
     *                       and locale information about the caller of service
     *                       operation
     * @return details about a result value
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the resultValueKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValueKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ResultValueInfo getResultValue(@WebParam(name = "resultValueKey") String resultValueKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of result value objects for a list of identifiers.
     *
     * @param resultValueKeys identifier for the result
     * @param contextInfo     Context information containing the principalId
     *                        and locale information about the caller of service
     *                        operation
     * @return list of result group identifiers
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          a resultValueKey from the list is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValueKeys
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValueKeys
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ResultValueInfo> getResultValuesByKeys(@WebParam(name = "resultValueKeys") List<String> resultValueKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of result values by type.
     *
     * @param resultValueTypeKey identifier for the result group type
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of service
     *                           operation
     * @return list of result group identifiers
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValueTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValueTypeKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValueTypeKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getResultValueKeysByType(@WebParam(name = "resultValueTypeKey") String resultValueTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of result value objects for a specified result
     * values group. It is sorted by the scale inside the values group.
     *
     * @param resultValuesGroupKey identifier for the result values group
     * @param contextInfo          Context information containing the principalId
     *                             and locale information about the caller of service
     *                             operation
     * @return list of result group identifiers
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValueKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValuesGroupKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValuesGroupKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(@WebParam(name = "resultValuesGroupKey") String resultValuesGroupKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Create a new result value
     *
     * @param resultScaleKey     scale to which this value is attached
     * @param resultValueTypeKey type of the result value to be created
     * @param resultValueInfo    info about the result value
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of service
     *                           operation
     * @return newly created resultValue
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          resultValue already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          one or more values invalid for
     *          this operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValueInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValueInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ResultValueInfo createResultValue(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "resultValueTypeKey") String resultValueTypeKey, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Update a result value
     *
     * @param resultValueKey  resultValueKey to be updated
     * @param resultValueInfo update information for the result value
     * @param contextInfo     Context information containing the principalId
     *                        and locale information about the caller of service
     *                        operation
     * @return updated information about the result value
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          one or more values invalid for
     *          this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValueKey does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValueKey, resultValueInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValueKey, resultValueInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          action was attempted on an out of
     *          date version
     */
    @Override
    public ResultValueInfo updateResultValue(@WebParam(name = "resultValueKey") String resultValueKey, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Delete a result value. This should not be allowed if any result values group is still referencing the result value.
     *
     * @param resultValueKey result value to be deleted
     * @param contextInfo    Context information containing the principalId
     *                       and locale information about the caller of service
     *                       operation
     * @return status of the delete operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValueKey does not exist
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *          if a group is tied to this value
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValueKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValueKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteResultValue(@WebParam(name = "resultValueKey") String resultValueKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DependentObjectsExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a Result Value. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained subobjects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the academic calendar and a record
     * is found for that identifier, the validation checks if the
     * academic calendar can be shifted to the new values. If a
     * record cannot be found for the identifier, it is assumed that
     * the record does not exist and as such, the checks performed
     * will be much shallower, typically mimicking those performed by
     * setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the
     * caller provides the identifier in the create statement instead
     * of the server assigning an identifier.
     *
     * @param validationType  Identifier of the extent of validation
     * @param resultValueInfo Result value to be validated
     * @param contextInfo     Context information containing the principalId
     *                        and locale information about the caller of service
     *                        operation
     * @return a ValidationResultInfo
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValueInfo does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          validationType or resultValueInfo
     *          does not exist
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationType or resultValueInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateResultValue(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get or create a new result value holding the specified numeric value within the range
     * <p/>
     * The resulting result value should be attached to the specified group and must be within
     * the range of the group
     *
     * @param resultValue the result value within the specified group range to be found/created
     * @param scaleKey    the with associated with this value
     * @param contextInfo Context information containing the principalId
     *                    and locale information about the caller of service
     *                    operation
     * @return the result value group information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid scaleKey or scaleKey is not a range
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultValuesGroupInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @impl the parameters hold string representations of floating points to avoid rounding issues
     */
    @Override
    public ResultValueInfo getCreateResultValueForScale(@WebParam(name = "resultValue") String resultValue, @WebParam(name = "scaleKey") String scaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves result scale by an identifier.
     *
     * @param resultScaleKey identifiers for result scale to be retrieved
     * @param contextInfo    Context information containing the principalId
     *                       and locale information about the caller of service
     *                       operation
     * @return details of the result scale for the id
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValuesGroupKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValuesGroupKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid resultValuesGroupKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ResultScaleInfo getResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves result scales by a list of identifiers.
     *
     * @param resultScaleKeys identifiers for result scale
     * @param contextInfo     Context information containing the principalId
     *                        and locale information about the caller of service
     *                        operation
     * @return result scale list
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultScale not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultScaleKeys
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid resultScaleKeys
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ResultScaleInfo> getResultScalesByKeys(@WebParam(name = "resultScaleKeys") List<String> resultScaleKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of result group identifiers for a specified
     * result scale type.
     *
     * @param resultScaleTypeKey identifier for the result group type
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of service
     *                           operation
     * @return list of result group identifiers
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultScaleTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultScaleTypeKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultScaleTypeKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getResultScaleKeysByType(@WebParam(name = "resultScaleTypeKey") String resultScaleTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new result scale.
     *
     * @param resultScaleTypeKey type key of the result scale
     * @param resultScaleInfo    information about the result scale
     *                           being created
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of service
     *                           operation
     * @return create result scale information
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          result scale already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          one or more values invalid for
     *          this operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultScaleInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultScaleInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ResultScaleInfo createResultScale(@WebParam(name = "resultScaleTypeKey") String resultScaleTypeKey, @WebParam(name = "resultGroupInfo") ResultScaleInfo resultScaleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing result scale.
     *
     * @param resultScaleKey  identifier of the result scale to update
     * @param resultGroupInfo updated information about the result scale
     * @param contextInfo     Context information containing the principalId
     *                        and locale information about the caller of service
     *                        operation
     * @return updated result scale information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          one or more values invalid for
     *          this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultScaleKey not found
     * @throws InvalidParameterExceptioninvalid
     *          resultScaleKey or
     *          resultScaleInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultScaleKey or
     *          resultScaleInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          action was attempted on an out of
     *          date version
     */
    @Override
    public ResultScaleInfo updateResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "resultScaleInfo") ResultScaleInfo resultScaleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing result scale.
     *
     * @param resultScaleKey identifier of the result scale to update
     * @param contextInfo    Context information containing the principalId
     *                       and locale information about the caller of service
     *                       operation
     * @return status of the operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultScaleKey not found
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *          if a group or value exists for this scale
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultScaleKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultScaleKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DependentObjectsExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a result scale. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects
     * or expanded to perform all tests related to this object.
     *
     * @param validationType Identifier of the extent of validation
     * @param gradeScaleInfo Result scale to be validated
     * @param contextInfo    Context information containing the principalId
     *                       and locale information about the caller of service
     *                       operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultScaleInfo does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          validationType or
     *          resultScaleInfo does not exist
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationType, resultScaleInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateResultScale(@WebParam(name = "validationType") String validationType, @WebParam(name = "gradeScaleInfo") ResultScaleInfo gradeScaleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves result values by result scale key.
     *
     * @param resultScaleKey key to the scale
     * @param contextInfo    Context information containing the principalId
     *                       and locale information about the caller of service
     *                       operation
     * @return a list of result values for the scale
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultScaleKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultScaleKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          null resultScaleKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ResultValueInfo> getResultValuesForScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a result value by result scale key and value
     *
     * @param resultScaleKey key to the scale
     * @param value          the specific value
     * @param contextInfo    Context information containing the principalId
     *                       and locale information about the caller of service
     *                       operation
     * @return the matching Result Value
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultScaleKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultScaleKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          null resultScaleKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ResultValueInfo getResultValueForScaleAndValue(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "value") String value, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves result values for the list of Result values groups.
     * <p/>
     * No values are selected for groups that are RANGES.
     *
     * @param resultValuesGroupKeys list of result value groups for which to return values
     * @param contextInfo           Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return a list of unique result values for that set of groups
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultScaleKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultScaleKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          null resultScaleKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroups(@WebParam(name = "resultValuesGroupKeys") List<String> resultValuesGroupKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of existing result values groups that have a scale
     * of the specified type.
     *
     * @param resultScaleTypeKey identifier for result scale type key
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of service
     *                           operation
     * @return details of the results for these keys
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultValue not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultValueKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid resultValueKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScaleType(@WebParam(name = "resultScaleTypeKey") String resultScaleTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for result scale ids using a free form search criteria.
     *
     * @param criteria
     * @param context
     * @return
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<String> searchForResultScaleIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for result scales using a free form search criteria
     *
     * @param criteria
     * @param context
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<ResultScaleInfo> searchForResultScales(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for result value ids using a free form search criteria.
     *
     * @param criteria
     * @param context
     * @return
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<String> searchForResultValueIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for result values using a free form search criteria
     *
     * @param criteria
     * @param context
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<ResultValueInfo> searchForResultValues(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for result value group ids using a free form search criteria.
     *
     * @param criteria
     * @param context
     * @return
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<String> searchForResultValuesGroupIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for result value groups using a free form search criteria
     *
     * @param criteria
     * @param context
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<ResultValuesGroupInfo> searchForResultValuesGroups(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
