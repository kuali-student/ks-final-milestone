package org.kuali.student.ap.test.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiSetInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.jws.WebParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class LuiServiceMockTest implements LuiService {
    /**
     * Retrieves a single Lui by a Lui Id.
     *
     * @param luiId       the identifier for the Lui to be retrieved
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the Lui requested
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luiId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public LuiInfo getLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of Luis from a list of Lui Ids. The
     * returned list may be in any order and if duplicates Ids are
     * supplied, a unique set may or may not be returned.
     *
     * @param luiIds      a list of Lui identifiers
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of Luis
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          a luiId in the list was not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiIds, an Id in luiIds, or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiInfo> getLuisByIds(@WebParam(name = "luiIds") List<String> luiIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of Lui Ids by Lui Type.
     *
     * @param luiTypeKey  an identifier for a Lui Type
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of Lui identifiers matching luiTypeKey or an
     *         empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiTypeKey or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getLuiIdsByType(@WebParam(name = "luiTypeKey") String luiTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of Lui Ids by Clu.
     * (the pattern says this should return the objects).
     *
     * @param cluId       an identifier for the Clu
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of Lui Ids for the Clu
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          cluId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getLuiIdsByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of Lui Ids for the specified atp and Lui
     * Type.
     *
     * @param atpId       an identifier for the Atp
     * @param luiTypeKey  an identifier for the Lui Type
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of identifiers of Luis offered in the given Atp
     *         of the specified luiType
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          atpId, luiTypeKey, or contextInfo
     *          is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getLuiIdsByAtpAndType(@WebParam(name = "atpId") String atpId, @WebParam(name = "luiTypeKey") String luiTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of Lui Ids for the specified Clu and Time
     * period.
     *
     * @param cluId       an identifier for the Clu
     * @param atpId       an identifier for the Atp
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of identifiers of Luis offered in atpId and
     *         related to cluId
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          cluId, atpId, or contextInfo
     *          is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getLuiIdsByAtpAndClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of Luis for the specified Clu and Atp.
     *
     * @param cluId       an identifier for the Clu
     * @param atpId       an identifier for the Atp
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of Luis offered in atpId and related to cluId
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          cluId, atpId, or contextInfo
     *          is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiInfo> getLuisByAtpAndClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for Luis that meet the given search criteria and
     * returns a list of Lui identifiers that meet the criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of Lui Ids matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> searchForLuiIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for Luis that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of Luis matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiInfo> searchForLuis(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a Lui. Depending on the value of validationType, this
     * validation could be limited to tests on just the current Lui
     * and its directly contained sub-objects or expanded to perform
     * all tests related to this Lui. If an identifier is present for
     * the Lui (and/or one of its contained sub-objects) and a record
     * is found for that identifier, the validation checks if the Lui
     * can be updated to the new values. If an identifier is not
     * present or a record does not exist, the validation checks if
     * the object with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param cluId             the identifier for the Clu to which the Lui is
     *                          attached
     * @param atpId             the identifier for the Atp to which the Lui if
     *                          offered
     * @param luiTypeKey        the identifier for the Lui Type
     * @param luiInfo           the object to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey, cluId, atpId,
     *          or luiTypeKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          luiInfo or contextInfo is not
     *          valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          validationTypeKey, cluId,
     *          atpId, luiTypeKey, luiInfo, or contextInfo is missing
     *          or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<ValidationResultInfo> validateLui(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "atpId") String atpId, @WebParam(name = "luiTypeKey") String luiTypeKey, @WebParam(name = "luiInfo") LuiInfo luiInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new LUI. The Lui Id, Type, Clu Id, Atp Id, and Meta
     * information may not be set in the supplied data.
     *
     * @param cluId       the identifier for the Clu
     * @param atpId       the identifier for the Atp
     * @param luiTypeKey  an identifier for the Type of the new Lui
     * @param luiInfo     the data with which to create the Lui
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the new Lui
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId, atpId, or luiTypeKey is
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          luiInfo or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          cluId, atpId, luiTypeKey,
     *          luiInfo, or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     */
    @Override
    public LuiInfo createLui(@WebParam(name = "cluId") String cluId, @WebParam(name = "atpId") String atpId, @WebParam(name = "luiTypeKey") String luiTypeKey, @WebParam(name = "luiInfo") LuiInfo luiInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing Lui. The Lui Id, Type, Clu Id, Atp Id, and
     * Meta information may not be changed.
     *
     * @param luiId       the identifier for the LUI to be updated
     * @param luiInfo     the new data for the Lui
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the updated Lui
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luiId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          luiInfo or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiId, luiInfo, or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at changing information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          optimistic locking failure or
     *          the action was attempted on an out of date version
     */
    @Override
    public LuiInfo updateLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "luiInfo") LuiInfo luiInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing Lui.
     *
     * @param luiId       the identifier for the LUI to be deleted
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the status of the delete operation. This must always be true.
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *          the delete operation
     *          would leave orphaned objects or violate integrity
     *          constraints
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luiId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public StatusInfo deleteLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a single LuiLuiRelation by a LuiLuiRelation Id.
     *
     * @param luiLuiRelationId a unique identifier for the
     *                         LuiLuiRelation to be retrieved
     * @param contextInfo      information containing the principalId and
     *                         locale information about the caller of service operation
     * @return the LuiLuiRelation requested
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luiLuiRelationId is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiLuiRetaionId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(@WebParam(name = "luiLuiRelationId") String luiLuiRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of LuiLuiRelations from a list of
     * LuiLuiRelation Ids. The returned list may be in any order and
     * if duplicates Ids are supplied, a unique set may or may not be
     * returned.
     *
     * @param luiLuiRelationIds a list of LuiLuiRelation identifiers
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return information about a list of LuiLuiRelations
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          a luiLuiRelationId in the list
     *          was not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiLuiRelationIds, a
     *          luiLuiRelationId in luiLuiRelationIds, or contextInfo
     *          is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIds(@WebParam(name = "luiLuiRelationIds") List<String> luiLuiRelationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of LuiLuiRelation Ids by a LuiLuiRelation
     * Type.
     *
     * @param luiLuiRelationTypeKey an identifier for a LuiLuiRelation
     *                              Type
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of service operation
     * @return a list of LuiLuiRelation identifiers
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiLuiRelationTypeKey or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getLuiLuiRelationIdsByType(@WebParam(name = "luiLuiRelationTypeKey") String luiLuiRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all LuiLuiRelations to the given Lui.
     *
     * @param luiId       a unique identifier of the LUI
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the LuiLuiRelations to the Lui
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all LuiLuiRelations between the given Luis.
     *
     * @param luiId        a unique identifier of the LUI
     * @param relatedLuiId a unique identifier of another LUI
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of service operation
     * @return the LuiLuiRelations between the given Luis
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiId, relatedLuiId, or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLuiAndRelatedLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "relatedLuiId") String relatedLuiId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of LUI Ids for the specified related LUI Id
     * and LU to LU relation type (getRelatedLuiIdsByLuiId from the
     * other direction).
     *
     * @param relatedLuiId          identifier of the LUI
     * @param luiLuiRelationTypeKey the LU to LU relation type
     * @param contextInfo           Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return list of LUI identifiers, empty list of none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getLuiIdsByRelatedLuiAndRelationType(@WebParam(name = "relatedLuiId") String relatedLuiId, @WebParam(name = "luiLuiRelationTypeKey") String luiLuiRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of LUI information for the LUIs related to
     * the specified LUI Id with a certain LU to LU relation type.
     * (getRelatedLuisByLuiId from the other direction)
     *
     * @param relatedLuiId          identifier of the LUI
     * @param luiLuiRelationTypeKey the LU to LU relation type
     * @param contextInfo           Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return list of LUI information, empty list if none
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing paremeter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiInfo> getLuisByRelatedLuiAndRelationType(@WebParam(name = "relatedLuiId") String relatedLuiId, @WebParam(name = "luiLuiRelationTypeKey") String luiLuiRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of related LUI Ids for the specified LUI Id
     * and LU to LU relation type. (getLuiIdsByRelatedLuiAndRelationType from the other
     * direction).
     *
     * @param luiId                 identifier of the LUI
     * @param luiLuiRelationTypeKey the LU to LU relation type
     * @param contextInfo           Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return list of LUI identifier, empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getLuiIdsByLuiAndRelationType(@WebParam(name = "luiId") String luiId, @WebParam(name = "luiLuiRelationTypeKey") String luiLuiRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of related LUI information for the specified
     * LUI Id and LU to LU relation type (getLuisByRelatedLuiAndRelationType from the
     * other direction).
     *
     * @param luiId                 identifier of the LUI
     * @param luiLuiRelationTypeKey the LU to LU relation type
     * @param contextInfo           Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return list of LUI information, empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid luiId, luiLuiRelationTypeKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luiId, luiLuiRelationTypeKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiInfo> getRelatedLuisByLuiAndRelationType(@WebParam(name = "luiId") String luiId, @WebParam(name = "luiLuiRelationTypeKey") String luiLuiRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all LuiLuiRelations between a Lui and Luis of the
     * given Lui Type.
     *
     * @param luiId             a unique identifier for the Lui
     * @param relatedLuiTypeKey a unique identifier for a Lui Type
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of LuiLuiRelations between luiId and Luis of
     *         relatedLuiTypeKey
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiId, relatedLuiTypeKey, or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiInfo> getLuiLuiRelationsByLuiAndRelatedLuiType(@WebParam(name = "luiId") String luiId, @WebParam(name = "relatedLuiTypeKey") String relatedLuiTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for LuiLuiRelations that meet the search criteria and
     * returns a list of LuiLuiRelation identifiers that meet the
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of LuiLuiRelationIds
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is
     *          missing os null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> searchForLuiLuiRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for LuiLuiRelations that meet the search criteria and
     * returns a list of LuiLuiRelations that meet the criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of LuiLuiRelations
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is
     *          missing os null
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiLuiRelationInfo> searchForLuiLuiRelations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a LuiLuiRelations. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current LuiLuiRelation and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * LuiLuiRelation. If an identifier is present for the
     * LuiLuiRelation (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the LuiLuiRelation can be updated to the new values. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the object with the given data can be
     * created.
     *
     * @param validationTypeKey     the identifier for the validation Type
     * @param luiId                 the identifier for the Lui
     * @param relatedLuiId          the identifier for the related Lui
     * @param luiLuiRelationTypeKey the identifier for LuiLuiRelation Type
     * @param luiLuiRelationInfo    the LuiLuiRelation to ve validated
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey, luiId,
     *          relatedLuiId, or luiLuiRelationTypeKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          luiLuiRelationInfo or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          validationTypeKey, luiId,
     *          relatedLuiId, luiLuiRelationTypeKey,
     *          luiLuiRelationInfo, or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<ValidationResultInfo> validateLuiLuiRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "luiId") String luiId, @WebParam(name = "relatedLuiId") String relatedLuiId, @WebParam(name = "luiLuiRelationTypeKey") String luiLuiRelationTypeKey, @WebParam(name = "luiLuiRelationInfo") LuiLuiRelationInfo luiLuiRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Create new LuiLuiRelation. The LuiLuiRelation Id, Type, luiId,
     * relatedLuiId, and Meta information may not be set in the
     * supplied data.
     *
     * @param luiId                 identifier of the first LUI in the relationship
     * @param relatedLuiId          identifier of the second LUI in the
     *                              relationship to be related
     * @param luiLuiRelationTypeKey a unique key fo rthe Type or new
     *                              LuiLuiRelation
     * @param luiLuiRelationInfo    the data with which to create the
     *                              LuiLuiRelation
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of service operation
     * @return the new LuiLuiRelation
     * @throws org.kuali.student.r2.common.exceptions.CircularRelationshipException
     *          luiId equals relatedLuiId
     *          (why is this a contract failure?)
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luiId, relatedLuiId, or
     *          luiLuiRelationTypeKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          luiLuiRelationInfo ro
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiId, relatedLuiId,
     *          luiLuiRelationTypeKey, luiLuiRelationInfo, or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     */
    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(@WebParam(name = "luiId") String luiId, @WebParam(name = "relatedLuiId") String relatedLuiId, @WebParam(name = "luiLuiRelationTypeKey") String luiLuiRelationTypeKey, @WebParam(name = "luiLuiRelationInfo") LuiLuiRelationInfo luiLuiRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing LuiLuiRelation. The LuiLuiRelation Id,
     * luiId, relatedLuiId, and Meta information may not be changed.
     *
     * @param luiLuiRelationId   the identifier for the LuiLuiRelation
     *                           to be updated
     * @param luiLuiRelationInfo the new data for the LuiLuiRelation
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service operation
     * @return the updated LuiLuiRelation
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luiLuiRelationId is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          luiLuiRelationInfo or contextInfo
     *          is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiLuiRelationId,
     *          luiLuiRelationInfo, or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at changing information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          optimistic locking failure or
     *          the action was attempted on an out of date version
     */
    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(@WebParam(name = "luiLuiRelationId") String luiLuiRelationId, @WebParam(name = "luiLuiRelationInfo") LuiLuiRelationInfo luiLuiRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing LuiLuiRelation.
     *
     * @param luiLuiRelationId identifier or the LuiLuiRelation to be
     *                         deleted
     * @param contextInfo      information containing the principalId and
     *                         locale information about the caller of service operation
     * @return the status of the delete operation. This must always be true.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luiLuiRelationId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiLuiRelationId
     *          or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public StatusInfo deleteLuiLuiRelation(@WebParam(name = "luiLuiRelationId") String luiLuiRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a single LuiCapacity by a LuiCapacity Id.
     *
     * @param luiCapacityId the identifier for the LuiCapacity to be
     *                      retrieved
     * @param contextInfo   information containing the principalId and
     *                      locale information about the caller of service operation
     * @return the LuiCapacity requested
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luiCapacityId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiCapacityId or contextInfo
     *          is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public LuiCapacityInfo getLuiCapacity(@WebParam(name = "luiCapacityId") String luiCapacityId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of LuiCapacity from a list of LuiCapacity
     * Ids. The returned list may be in any order and if duplicate Ids
     * are supplied, a unique set may or may not be returned.
     *
     * @param luiCapacityIds a list of LuiCapacity identifiers
     * @param contextInfo    information containing the principalId and
     *                       locale information about the caller of service operation
     * @return a list of LuiCapacities
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          a luiCapacityId in the list was
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiCapacityIds, an Id in
     *          luiCapacityIds, or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByIds(@WebParam(name = "luiCapacityIds") List<String> luiCapacityIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of LuiCapacity Ids by LuiCapacity Type.
     *
     * @param luiCapacityTypeKey a unique identifier for a LuiCapacity Type
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service operation
     * @return information about a list of Lui Capacities
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiCapacityTypeKey or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getLuiCapacityIdsByType(@WebParam(name = "luiCapacityTypeKey") String luiCapacityTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves LuiCapacities associated with a Lui.
     *
     * @param luiId       a unique identifier for a Lui
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of LuiCapacities associated with the given Lui
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for LuiCapacities that meet the search criteria and
     * returns a list of LuiCapacity identifiers that meet the
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of LuiCapacity Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> searchForLuiCapacityIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for LuiCapacities that meet the search criteria and
     * returns a list of LuiCapacities that meet the criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of LuiCapacitiess
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LuiCapacityInfo> searchForLuiCapacities(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a LuiCapacity. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current LuiCapacity and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * LuiCapacity. If an identifier is present for the LuiCapacity
     * (and/or one of its contained sub-objects) and a record is
     * found for that identifier, the validation checks if the
     * LuiCpacity can be updated to the new values. If an identifier
     * is not present or a record does not exist, the validation
     * checks if the object with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param luiCapacityInfo   the LuiCapacity to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey or
     *          luiCapacityTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          luiInfo or contextInfo is not
     *          valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          validationTypeKey, luiInfo,
     *          or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<ValidationResultInfo> validateLuiCapacity(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "luiCapacityTypeKey") String luiCapacityTypeKey, @WebParam(name = "luiCapacityInfo") LuiCapacityInfo luiCapacityInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new LuiCapacity. The LuiCapacity Id, Type, and Meta
     * information may not be set in the supplied data.
     *
     * @param luiCapacityTypeKey an identifier for the Type of the new
     *                           LuiCapacity
     * @param luiCapacityInfo    the data with which to create the
     *                           LuiCapacity
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service operation
     * @return the new LuiCapacity
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luiCapacityTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          luiCapacityInfo or
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiCapacityTypeKey,
     *          luiCapacityInfo, or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     */
    @Override
    public LuiCapacityInfo createLuiCapacity(@WebParam(name = "luiCapacityTypeKey") String luiCapacityTypeKey, @WebParam(name = "luiCapacityInfo") LuiCapacityInfo luiCapacityInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing LuiCapacity. The LuiCapacity Id, Type, and
     * Meta information may not be changed.
     *
     * @param luiCapacityId   the identifier for the LuiCapacity to be
     *                        updated
     * @param luiCapacityInfo the new data for the LuiCapacity
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return the updated LuiCapacity
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luiCapacityId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          luiCapacityInfo or contextInfo
     *          is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiCapacityId,
     *          luiCapacityInfo, or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at changing information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          optimistic locking failure or
     *          the action was attempted on an out of date version
     */
    @Override
    public LuiCapacityInfo updateLuiCapacity(@WebParam(name = "luiCapacityId") String luiCapacityId, @WebParam(name = "luiCapacityInfo") LuiCapacityInfo luiCapacityInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing LuiCapacity.
     *
     * @param luiCapacityId the identifier for the LuiCapacity to be
     *                      deleted
     * @param contextInfo   information containing the principalId and
     *                      locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luiCapacityId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiCapacityId or contextInfo
     *          is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public StatusInfo deleteLuiCapacity(@WebParam(name = "luiCapacityId") String luiCapacityId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information on a Lui set.
     *
     * @param luiSetId    Identifier of the Lui set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved Lui set information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          LuiSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing LuiSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public LuiSetInfo getLuiSet(@WebParam(name = "luiSetId") String luiSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve information on Lui sets from a list of LuiSet Ids.
     *
     * @param luiSetIds   List of identifiers of Lui sets
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of Lui set information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          One or more LuiSets not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing LuiSetIds or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<LuiSetInfo> getLuiSetsByIds(@WebParam(name = "luiSetIds") List<String> luiSetIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Given a Lui, retrieves the LuiSets that it belongs to.
     *
     * @param luiId       the identifier for the Lui
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of LuiSets that the Lui with specified luiId belongs to (or an empty list if the Lui isn't found)
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luiId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<LuiSetInfo> getLuiSetsByLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of Lui Identifiers within a Lui Set.
     *
     * @param luiSetId    Identifier of the Lui set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of Lui Ids within the specified Lui set
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          LuiSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing LuiSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getLuiIdsFromLuiSet(@WebParam(name = "luiSetId") String luiSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of LuiSet Ids by Lui Type.
     *
     * @param luiSetTypeKey type of the Lui set to be created
     * @param contextInfo   information containing the principalId and
     *                      locale information about the caller of service operation
     * @return a list of Lui identifiers matching luiTypeKey or an
     *         empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          luiTypeKey or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getLuiSetIdsByType(@WebParam(name = "luiSetTypeKey") String luiSetTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates information about a Lui set. Depending on the value of
     * validationTypeKey, this validation could be limited to tests on just the
     * current object and its directly contained sub-objects or expanded to
     * perform all tests related to this object. If an identifier is present for
     * the Lui set (and/or one of its contained sub-objects) and a record is
     * found for that identifier, the validation checks if the Lui set can be
     * shifted to the new values. If an identifier is not present or a record
     * cannot be found for the identifier, it is assumed that the record does
     * not exist and as such, the checks performed will be much shallower,
     * typically mimicking those performed by setting the validationType to the
     * current object.
     *
     * @param validationTypeKey identifier of the extent of validation
     * @param luiSetTypeKey     type of the Lui set to be created
     * @param LuiSetInfo        Lui set information to be tested.
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey or LuiSetTypeKey not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid LuiSetInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, LuiSetTypeKey,
     *          LuiSetInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateLuiSet(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "luiSetTypeKey") String luiSetTypeKey, @WebParam(name = "LuiSetInfo") LuiSetInfo LuiSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a Lui set.
     *
     * @param luiSetTypeKey type of the Lui set to be created
     * @param luiSetInfo    information required to create a Lui set
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return the created Lui set information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          data validation error
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          LuiSetTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid LuiSetInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing LuiSetTypeKey, LuiSetInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.UnsupportedActionException
     *          Lui set need to be static or dynamic
     *          but not both
     */
    @Override
    public LuiSetInfo createLuiSet(@WebParam(name = "luiSetTypeKey") String luiSetTypeKey, @WebParam(name = "luiSetInfo") LuiSetInfo luiSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Update the information for a Lui set
     *
     * @param luiSetId    identifier of the Lui set to be updated
     * @param luiSetInfo  updated information about the Lui set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the updated Lui set information
     * @throws org.kuali.student.r2.common.exceptions.CircularRelationshipException
     *          added LuiSetId cannot be added to
     *          the LuiSetInfo
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          data validation error
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          LuiSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid LuiSetInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing LuiSetId, LuiSetInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.UnsupportedActionException
     *          Lui set need to be static or
     *          dynamic but not both
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          an optimistic locking failure or
     *          the action was attempted on an out
     *          of date version
     */
    @Override
    public LuiSetInfo updateLuiSet(@WebParam(name = "luiSetId") String luiSetId, @WebParam(name = "luiSetInfo") LuiSetInfo luiSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Delete a Lui set
     *
     * @param luiSetId    identifier of the Lui set to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          LuiSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing LuiSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteLuiSet(@WebParam(name = "luiSetId") String luiSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
