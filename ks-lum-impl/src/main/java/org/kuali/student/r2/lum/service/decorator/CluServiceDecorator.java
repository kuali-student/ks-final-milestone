package org.kuali.student.r2.lum.service.decorator;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluPublicationInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetTreeViewInfo;
import org.kuali.student.r2.lum.clu.service.CluService;

import javax.jws.WebParam;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 11/1/13
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class CluServiceDecorator implements CluService {

    private CluService nextDecorator;

    public CluService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }

        return nextDecorator;
    }

    public void setNextDecorator(CluService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public StatusInfo addCluResourceRequirement(@WebParam(name = "resourceTypeKey") String resourceTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().addCluResourceRequirement(resourceTypeKey, cluId, contextInfo);
    }

    @Override
    public StatusInfo addCluSetsToCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "addedCluSetIds") List<String> addedCluSetIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        return getNextDecorator().addCluSetsToCluSet(cluSetId, addedCluSetIds, contextInfo);
    }

    @Override
    public StatusInfo addCluSetToCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "addedCluSetId") String addedCluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        return getNextDecorator().addCluSetToCluSet(cluSetId, addedCluSetId, contextInfo);
    }

    @Override
    public StatusInfo addClusToCluSet(@WebParam(name = "cluSetIds") List<String> cluSetIds, @WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        return getNextDecorator().addClusToCluSet(cluSetIds, cluSetId, contextInfo);
    }

    @Override
    public StatusInfo addCluToCluSet(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        return getNextDecorator().addCluToCluSet(cluId, cluSetId, contextInfo);
    }

    @Override
    public CluInfo createClu(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "cluInfo") CluInfo cluInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createClu(luTypeKey, cluInfo, contextInfo);
    }

    @Override
    public CluCluRelationInfo createCluCluRelation(@WebParam(name = "cluId") String cluId, @WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "cluCluRelationInfo") CluCluRelationInfo cluCluRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createCluCluRelation(cluId, relatedCluId, cluCluRelationTypeKey, cluCluRelationInfo, contextInfo);
    }

    @Override
    public CluLoRelationInfo createCluLoRelation(@WebParam(name = "cluId") String cluId, @WebParam(name = "loId") String loId, @WebParam(name = "cluLoRelationTypeKey") String cluLoRelationTypeKey, @WebParam(name = "cluLoRelationInfo") CluLoRelationInfo cluLoRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createCluLoRelation(cluId, loId, cluLoRelationTypeKey, cluLoRelationInfo, contextInfo);
    }

    @Override
    public CluPublicationInfo createCluPublication(@WebParam(name = "cluId") String cluId, @WebParam(name = "luPublicationTypeKey") String luPublicationTypeKey, @WebParam(name = "cluPublicationInfo") CluPublicationInfo cluPublicationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createCluPublication(cluId, luPublicationTypeKey, cluPublicationInfo, contextInfo);
    }

    @Override
    public CluResultInfo createCluResult(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluResultTypeKey") String cluResultTypeKey, @WebParam(name = "cluResultInfo") CluResultInfo cluResultInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createCluResult(cluId, cluResultTypeKey, cluResultInfo, contextInfo);
    }

    @Override
    public CluSetInfo createCluSet(@WebParam(name = "cluSetTypeKey") String cluSetTypeKey, @WebParam(name = "cluSetInfo") CluSetInfo cluSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException {
        return getNextDecorator().createCluSet(cluSetTypeKey, cluSetInfo, contextInfo);
    }

    @Override
    public CluInfo createNewCluVersion(@WebParam(name = "cluId") String cluId, @WebParam(name = "versionComment") String versionComment, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createNewCluVersion(cluId, versionComment, contextInfo);
    }

    @Override
    public StatusInfo deleteClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteClu(cluId, contextInfo);
    }

    @Override
    public StatusInfo deleteCluCluRelation(@WebParam(name = "cluCluRelationId") String cluCluRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCluCluRelation(cluCluRelationId, contextInfo);
    }

    @Override
    public StatusInfo deleteCluLoRelation(@WebParam(name = "cluLoRelationId") String cluLoRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCluLoRelation(cluLoRelationId, contextInfo);
    }

    @Override
    public StatusInfo deleteCluPublication(@WebParam(name = "cluPublicationId") String cluPublicationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCluPublication(cluPublicationId, contextInfo);
    }

    @Override
    public StatusInfo deleteCluResult(@WebParam(name = "cluResultId") String cluResultId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCluResult(cluResultId, contextInfo);
    }

    @Override
    public StatusInfo deleteCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCluSet(cluSetId, contextInfo);
    }

    @Override
    public List<String> getAllCluIdsInCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAllCluIdsInCluSet(cluSetId, contextInfo);
    }

    @Override
    public List<CluInfo> getAllClusInCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAllClusInCluSet(cluSetId, contextInfo);
    }

    @Override
    public List<String> getAllowedCluCluRelationTypesByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAllowedCluCluRelationTypesByClu(cluId, relatedCluId, contextInfo);
    }

    @Override
    public List<String> getAllowedCluLoRelationTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAllowedCluLoRelationTypesForLuType(luTypeKey, contextInfo);
    }

    @Override
    public List<String> getAllowedLuLuRelationTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "relatedLuTypeKey") String relatedLuTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAllowedLuLuRelationTypesForLuType(luTypeKey, relatedLuTypeKey, contextInfo);
    }

    @Override
    public List<String> getAllowedResultComponentTypesForResultUsageType(@WebParam(name = "resultUsageTypeKey") String resultUsageTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAllowedResultComponentTypesForResultUsageType(resultUsageTypeKey, contextInfo);
    }

    @Override
    public List<String> getAllowedResultUsageTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAllowedResultUsageTypesForLuType(luTypeKey, contextInfo);
    }

    @Override
    public CluInfo getClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getClu(cluId, contextInfo);
    }

    @Override
    public CluCluRelationInfo getCluCluRelation(@WebParam(name = "cluCluRelationId") String cluCluRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluCluRelation(cluCluRelationId, contextInfo);
    }

    @Override
    public List<CluCluRelationInfo> getCluCluRelationsByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluCluRelationsByClu(cluId, contextInfo);
    }

    @Override
    public List<TypeInfo> getCluCluRelationTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluCluRelationTypes(contextInfo);
    }

    @Override
    public List<String> getCluIdsByLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "luState") String luState, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluIdsByLuType(luTypeKey, luState, contextInfo);
    }

    @Override
    public List<String> getCluIdsByRelatedCluAndRelationType(@WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluIdsByRelatedCluAndRelationType(relatedCluId, cluCluRelationTypeKey, contextInfo);
    }

    @Override
    public List<String> getCluIdsByResultComponent(@WebParam(name = "resultComponentId") String resultComponentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getCluIdsByResultComponent(resultComponentId, contextInfo);
    }

    @Override
    public List<String> getCluIdsByResultUsageType(@WebParam(name = "resultUsageTypeKey") String resultUsageTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getCluIdsByResultUsageType(resultUsageTypeKey, contextInfo);
    }

    @Override
    public List<String> getCluIdsFromCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluIdsFromCluSet(cluSetId, contextInfo);
    }

    @Override
    public CluLoRelationInfo getCluLoRelation(@WebParam(name = "cluLoRelationId") String cluLoRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluLoRelation(cluLoRelationId, contextInfo);
    }

    @Override
    public List<CluLoRelationInfo> getCluLoRelationsByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluLoRelationsByClu(cluId, contextInfo);
    }

    @Override
    public List<CluLoRelationInfo> getCluLoRelationsByLo(@WebParam(name = "loId") String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluLoRelationsByLo(loId, contextInfo);
    }

    @Override
    public TypeInfo getCluLoRelationType(@WebParam(name = "cluLoRelationTypeKey") String cluLoRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluLoRelationType(cluLoRelationTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getCluLoRelationTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluLoRelationTypes(contextInfo);
    }

    @Override
    public CluPublicationInfo getCluPublication(@WebParam(name = "cluPublicationId") String cluPublicationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluPublication(cluPublicationId, contextInfo);
    }

    @Override
    public List<CluPublicationInfo> getCluPublicationsByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluPublicationsByClu(cluId, contextInfo);
    }

    @Override
    public List<CluPublicationInfo> getCluPublicationsByType(@WebParam(name = "luPublicationTypeKey") String luPublicationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluPublicationsByType(luPublicationTypeKey, contextInfo);
    }

    @Override
    public CluResultInfo getCluResult(@WebParam(name = "cluResultId") String cluResultId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluResult(cluResultId, contextInfo);
    }

    @Override
    public List<CluResultInfo> getCluResultByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getCluResultByClu(cluId, contextInfo);
    }

    @Override
    public List<CluResultInfo> getCluResultsByClus(@WebParam(name = "cluIds") List<String> cluIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getCluResultsByClus(cluIds, contextInfo);
    }

    @Override
    public TypeInfo getCluResultType(@WebParam(name = "cluResultTypeKey") String cluResultTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluResultType(cluResultTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getCluResultTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluResultTypes(contextInfo);
    }

    @Override
    public List<TypeInfo> getCluResultTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluResultTypesForLuType(luTypeKey, contextInfo);
    }

    @Override
    public List<CluInfo> getClusByIds(@WebParam(name = "cluIds") List<String> cluIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getClusByIds(cluIds, contextInfo);
    }

    @Override
    public List<CluInfo> getClusByLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "luState") String luState, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getClusByLuType(luTypeKey, luState, contextInfo);
    }

    @Override
    public List<CluInfo> getClusByRelatedCluAndRelationType(@WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCLuRelationTypeKey") String cluCLuRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getClusByRelatedCluAndRelationType(relatedCluId, cluCLuRelationTypeKey, contextInfo);
    }

    @Override
    public CluSetInfo getCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluSet(cluSetId, contextInfo);
    }

    @Override
    public List<String> getCluSetIdsFromCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluSetIdsFromCluSet(cluSetId, contextInfo);
    }

    @Override
    public List<CluSetInfo> getCluSetsByIds(@WebParam(name = "cluSetIds") List<String> cluSetIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluSetsByIds(cluSetIds, contextInfo);
    }

    @Override
    public CluSetTreeViewInfo getCluSetTreeView(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluSetTreeView(cluSetId, contextInfo);
    }

    @Override
    public TypeInfo getCluSetType(@WebParam(name = "cluSetTypeKey") String cluSetTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluSetType(cluSetTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getCluSetTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCluSetTypes(contextInfo);
    }

    @Override
    public List<CluInfo> getClusFromCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getClusFromCluSet(cluSetId, contextInfo);
    }

    @Override
    public TypeInfo getDeliveryMethodType(@WebParam(name = "deliveryMethodTypeKey") String deliveryMethodTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getDeliveryMethodType(deliveryMethodTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getDeliveryMethodTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getDeliveryMethodTypes(contextInfo);
    }

    @Override
    public TypeInfo getInstructionalFormatType(@WebParam(name = "instructionalFormatTypeKey") String instructionalFormatTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructionalFormatType(instructionalFormatTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getInstructionalFormatTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructionalFormatTypes(contextInfo);
    }

    @Override
    public TypeInfo getLuCodeType(@WebParam(name = "luCodeTypeKey") String luCodeTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuCodeType(luCodeTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getLuCodeTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuCodeTypes(contextInfo);
    }

    @Override
    public TypeInfo getLuLuRelationType(@WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuLuRelationType(cluCluRelationTypeKey, contextInfo);
    }

    @Override
    public TypeInfo getLuPublicationType(@WebParam(name = "luPublicationTypeKey") String luPublicationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuPublicationType(luPublicationTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getLuPublicationTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuPublicationTypes(contextInfo);
    }

    @Override
    public List<String> getLuPublicationTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuPublicationTypesForLuType(luTypeKey, contextInfo);
    }

    @Override
    public TypeInfo getLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuType(luTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getLuTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuTypes(contextInfo);
    }

    @Override
    public List<String> getRelatedCluIdsByCluAndRelationType(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRelatedCluIdsByCluAndRelationType(cluId, cluCluRelationTypeKey, contextInfo);
    }

    @Override
    public List<CluInfo> getRelatedClusByCluAndRelationType(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRelatedClusByCluAndRelationType(cluId, cluCluRelationTypeKey, contextInfo);
    }

    @Override
    public List<String> getResourceRequirementsForClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResourceRequirementsForClu(cluId, contextInfo);
    }

    @Override
    public TypeInfo getResultUsageType(@WebParam(name = "resultUsageTypeKey") String resultUsageTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultUsageType(resultUsageTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getResultUsageTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultUsageTypes(contextInfo);
    }

    @Override
    public Boolean isCluInCluSet(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().isCluInCluSet(cluId, cluSetId, contextInfo);
    }

    @Override
    public Boolean isCluSetDynamic(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().isCluSetDynamic(cluSetId, contextInfo);
    }

    @Override
    public StatusInfo removeCluFromCluSet(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        return getNextDecorator().removeCluFromCluSet(cluId, cluSetId, contextInfo);
    }

    @Override
    public StatusInfo removeCluResourceRequirement(@WebParam(name = "resourceTypeKey") String resourceTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removeCluResourceRequirement(resourceTypeKey, cluId, contextInfo);
    }

    @Override
    public StatusInfo removeCluSetFromCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "removedCluSetId") String removedCluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        return getNextDecorator().removeCluSetFromCluSet(cluSetId, removedCluSetId, contextInfo);
    }

    @Override
    public List<String> searchForCluCluRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCluCluRelationIds(criteria, contextInfo);
    }

    @Override
    public List<CluCluRelationInfo> searchForCluCluRelations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCluCluRelations(criteria, contextInfo);
    }

    @Override
    public List<String> searchForCluIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCluIds(criteria, contextInfo);
    }

    @Override
    public List<String> searchForCluLoRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCluLoRelationIds(criteria, contextInfo);
    }

    @Override
    public List<CluLoRelationInfo> searchForCluLoRelations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCluLoRelations(criteria, contextInfo);
    }

    @Override
    public List<String> searchForCluPublicationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCluPublicationIds(criteria, contextInfo);
    }

    @Override
    public List<CluPublicationInfo> searchForCluPublications(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCluPublications(criteria, contextInfo);
    }

    @Override
    public List<String> searchForCluResultIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCluResultIds(criteria, contextInfo);
    }

    @Override
    public List<CluResultInfo> searchForCluResults(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCluResults(criteria, contextInfo);
    }

    @Override
    public List<CluInfo> searchForClus(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForClus(criteria, contextInfo);
    }

    @Override
    public StatusInfo setCurrentCluVersion(@WebParam(name = "cluVersionId") String cluVersionId, @WebParam(name = "currentVersionStart") Date currentVersionStart, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().setCurrentCluVersion(cluVersionId, currentVersionStart, contextInfo);
    }

    @Override
    public CluInfo updateClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluInfo") CluInfo cluInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateClu(cluId, cluInfo, contextInfo);
    }

    @Override
    public CluCluRelationInfo updateCluCluRelation(@WebParam(name = "cluCluRelationId") String cluCluRelationId, @WebParam(name = "cluCluRelationInfo") CluCluRelationInfo cluCluRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateCluCluRelation(cluCluRelationId, cluCluRelationInfo, contextInfo);
    }

    @Override
    public CluLoRelationInfo updateCluLoRelation(@WebParam(name = "cluLoRelationId") String cluLoRelationId, @WebParam(name = "cluLoRelationInfo") CluLoRelationInfo cluLoRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateCluLoRelation(cluLoRelationId, cluLoRelationInfo, contextInfo);
    }

    @Override
    public CluPublicationInfo updateCluPublication(@WebParam(name = "cluPublicationId") String cluPublicationId, @WebParam(name = "cluPublicationInfo") CluPublicationInfo cluPublicationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateCluPublication(cluPublicationId, cluPublicationInfo, contextInfo);
    }

    @Override
    public CluResultInfo updateCluResult(@WebParam(name = "cluResultId") String cluResultId, @WebParam(name = "cluResultInfo") CluResultInfo cluResultInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateCluResult(cluResultId, cluResultInfo, contextInfo);
    }

    @Override
    public CluSetInfo updateCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "cluSetInfo") CluSetInfo cluSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException, VersionMismatchException {
        return getNextDecorator().updateCluSet(cluSetId, cluSetInfo, contextInfo);
    }

    @Override
    public CluInfo updateCluState(@WebParam(name = "cluId") String cluId, @WebParam(name = "luState") String luState, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateCluState(cluId, luState, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateClu(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluInfo") CluInfo cluInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateClu(validationTypeKey, cluInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCluCluRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "cluCluRelationInfo") CluCluRelationInfo cluCluRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCluCluRelation(validationTypeKey, cluId, relatedCluId, cluCluRelationTypeKey, cluCluRelationInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCluLoRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "loId") String loId, @WebParam(name = "cluLoRelationTypeKey") String cluLoRelationTypeKey, @WebParam(name = "cluLoRelationInfo") CluLoRelationInfo cluLoRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCluLoRelation(validationTypeKey, cluId, loId, cluLoRelationTypeKey, cluLoRelationInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCluPublication(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "luPublicationTypeKey") String luPublicationTypeKey, @WebParam(name = "cluPublicationInfo") CluPublicationInfo cluPublicationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCluPublication(validationTypeKey, cluId, luPublicationTypeKey, cluPublicationInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCluResult(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "cluResultTypeKey") String cluResultTypeKey, @WebParam(name = "cluResultInfo") CluResultInfo cluResultInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCluResult(validationTypeKey, cluId, cluResultTypeKey, cluResultInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCluSet(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluSetTypeKey") String cluSetTypeKey, @WebParam(name = "cluSetInfo") CluSetInfo cluSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCluSet(validationTypeKey, cluSetTypeKey, cluSetInfo, contextInfo);
    }

    @Override
    public VersionDisplayInfo getCurrentVersion(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCurrentVersion(refObjectUri, refObjectId, contextInfo);
    }

    @Override
    public VersionDisplayInfo getCurrentVersionOnDate(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "date") Date date, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCurrentVersionOnDate(refObjectUri, refObjectId, date, contextInfo);
    }

    @Override
    public VersionDisplayInfo getFirstVersion(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getFirstVersion(refObjectUri, refObjectId, contextInfo);
    }

    @Override
    public VersionDisplayInfo getLatestVersion(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLatestVersion(refObjectUri, refObjectId, contextInfo);
    }

    @Override
    public VersionDisplayInfo getVersionBySequenceNumber(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "sequence") Long sequence, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getVersionBySequenceNumber(refObjectUri, refObjectId, sequence, contextInfo);
    }

    @Override
    public List<VersionDisplayInfo> getVersions(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getVersions(refObjectUri, refObjectId, contextInfo);
    }

    @Override
    public List<VersionDisplayInfo> getVersionsInDateRange(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "from") Date from, @WebParam(name = "to") Date to, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getVersionsInDateRange(refObjectUri, refObjectId, from, to, contextInfo);
    }

    @Override
    public TypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getSearchType(searchTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getSearchTypes(contextInfo);
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().search(searchRequestInfo, contextInfo);
    }

	@Override
	public List<CluCluRelationInfo> getCluCluRelationsByIds(
			List<String> cluCluRelationIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().getCluCluRelationsByIds(cluCluRelationIds, contextInfo);
	}

	@Override
	public List<CluPublicationInfo> getCluPublicationsByIds(
			List<String> cluPublicationIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().getCluPublicationsByIds(cluPublicationIds, contextInfo);
	}

	@Override
	public List<CluResultInfo> getCluResultsByIds(List<String> cluResultIds,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return getNextDecorator().getCluResultsByIds(cluResultIds, contextInfo);
	}

	@Override
	public List<CluLoRelationInfo> getCluLoRelationsByIds(
			List<String> cluLoRelationIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().getCluLoRelationsByIds(cluLoRelationIds, contextInfo);
	}
    
    
}
