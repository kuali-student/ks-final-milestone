package org.kuali.student.enrollment.class1.lui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class1.lui.model.LuiCluCluRelationEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiResultValuesGroupEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiUnitsContentOwnerEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiUnitsDeploymentEntity;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.state.service.StateService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;

@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class LuiServiceImpl implements LuiService {
    private LuiDao luiDao;
    private LuiLuiRelationDao luiLuiRelationDao;
    private StateService stateService;
    private AtpService atpService;
    private CluService luService;

    public LuiDao getLuiDao() {
        return luiDao;
    }

    public void setLuiDao(LuiDao luiDao) {
        this.luiDao = luiDao;
    }

    public LuiLuiRelationDao getLuiLuiRelationDao() {
        return luiLuiRelationDao;
    }

    public void setLuiLuiRelationDao(LuiLuiRelationDao luiLuiRelationDao) {
        this.luiLuiRelationDao = luiLuiRelationDao;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public CluService getLuService() {
        return luService;
    }

    public void setLuService(CluService luService) {
        this.luService = luService;
    }

    @Override
    public LuiInfo getLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        LuiEntity lui = luiDao.find(luiId);
        if (null == lui) {
            throw new DoesNotExistException(luiId);
        }

        return lui.toDto();
    }

    @Override
    public List<LuiInfo> getLuisByIds(List<String> luiIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        List<LuiEntity> entityList = luiDao.findByIds(luiIds);
        List<LuiInfo> infoList = new ArrayList<LuiInfo>();

        for (LuiEntity luiEntity : entityList) {
            infoList.add(luiEntity.toDto());
        }

        return infoList;
    }

    @Override
    public List<String> getLuiIdsByType(String luiTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        // make sure the given type key is a valid lui type

        List<LuiEntity> luis = luiDao.getLuisByType(luiTypeKey);

        List<String> luiIds = new ArrayList<String>();

        for (LuiEntity lui : luis) {
            luiIds.add(lui.getId());
        }

        return luiIds;
    }

    @Override
    public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<LuiInfo>();
    }

    @Override
    public List<String> getLuiIdsByCluId(String cluId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<String>();
    }

    @Override
    public List<String> getLuiIdsByAtpAndType( String atpId,String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
        {
            return  new ArrayList<String>();
        }

    @Override
    public List<String> getLuiIdsInAtpByCluId(String cluId, String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<String>();
    }

    @Override
    public List<LuiInfo> getLuisByRelation(String relatedLuiId, String luLuRelationTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        List<LuiEntity> entityList = luiLuiRelationDao.getLuisByRelation(relatedLuiId, luLuRelationTypeKey);

        List<LuiInfo> infoList = new ArrayList<LuiInfo>();
        if (entityList != null && !entityList.isEmpty()) {
            for (LuiEntity entity : entityList) {
                infoList.add(entity.toDto());
            }

        }
        return infoList;
    }

    @Override
    public List<String> getLuiIdsByRelation(String relatedLuiId, String luLuRelationTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> returnVals = new ArrayList<String>();

        returnVals.addAll(luiLuiRelationDao.getLuiIdsByRelation(relatedLuiId, luLuRelationTypeKey));
        return returnVals;
    }

    @Override
    public List<LuiInfo> getRelatedLuisByLuiId(String luiId, String luLuRelationTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<LuiInfo>();
    }

    @Override
    public List<String> getRelatedLuiIdsByLuiId(String luiId, String luLuRelationTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> returnVals = new ArrayList<String>();
        returnVals.addAll(luiLuiRelationDao.getRelatedLuisByLuiId(luiId, luLuRelationTypeKey));
        return returnVals;
    }

    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiLuiRelationDao.find(luiLuiRelationId).toDto();
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIds(List<String> luiLuiRelationIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<LuiLuiRelationInfo>();
    }

    @Override
    public List<String> getLuiLuiRelationIdsByType(String luiLuiRelationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<String>();
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        // Ensure the lui id is valid
        getLui(luiId, context);

        List<LuiLuiRelationEntity> relEntities = luiLuiRelationDao.getLuiLuiRelationsByLui(luiId);
        List<LuiLuiRelationInfo> relInfos = new ArrayList<LuiLuiRelationInfo>();
        if (relEntities != null && !relEntities.isEmpty()) {
            for (LuiLuiRelationEntity relEntity : relEntities) {
                LuiLuiRelationInfo relInfo = relEntity.toDto();
                relInfos.add(relInfo);
            }
        }

        return relInfos;
    }

    @Override
    public List<String> searchForLuiIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<String>();
    }

    @Override
    public List<LuiInfo> searchForLuis(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<LuiInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateLui(String validationType, LuiInfo luiInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    // private StateEntity findState(String stateKey, ContextInfo context) throws InvalidParameterException,
    // MissingParameterException, OperationFailedException, PermissionDeniedException {
    // StateEntity state = null;
    // try {
    // StateInfo stInfo = stateService.getState(stateKey, context);
    // if (stInfo != null) {
    // state = new StateEntity(stInfo);
    // return state;
    // } else
    // throw new OperationFailedException("The state does not exist. stateKey: " + stateKey);
    // } catch (DoesNotExistException e) {
    // throw new OperationFailedException("The state does not exist. stateKey: " + stateKey);
    // }
    // }

    // TODO:call LuService
    private boolean checkExistenceForClu(String cluId, ContextInfo context) {
        // clu = luService.getClu(cluId, context);
        return true;
    }

    private boolean checkExistenceForAtp(String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        /*
         * boolean existing = false; try { AtpInfo atp = atpService.getAtp(atpId, context); if(atp != null) existing = true;
         * else throw new DoesNotExistException("The ATP does not exist. atp " + atpId); } catch (DoesNotExistException e) {
         * throw new DoesNotExistException("The ATP does not exist. atp " + atpId); } catch (InvalidParameterException e) { }
         * catch (MissingParameterException e) { } catch (OperationFailedException e) { } catch (PermissionDeniedException e)
         * { } return existing;
         */

        return true;
    }

    @Override
    @Transactional
    public LuiInfo createLui(String cluId, String atpId, LuiInfo luiInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiEntity entity = new LuiEntity(luiInfo);
        entity.setId(UUIDHelper.genStringUUID());

        LuiEntity existing = luiDao.find(entity.getId());
        if (existing != null) {
            throw new AlreadyExistsException();
        }

        this.setLuiCluCluRelations(luiInfo.getCluCluRelationIds(), entity.getCluCluReltns(), entity);
        this.setUnitsContentOwner(luiInfo.getUnitsContentOwner(), entity.getUnitsContentOwners(), entity);
        this.addUnitsDeployment(luiInfo.getUnitsDeployment(), entity.getUnitsDeployments(), entity);
        this.addResultValuesGroups(luiInfo.getResultValuesGroupKeys(), entity.getResultValuesGroupRelationEntities(), entity);

        luiDao.persist(entity);

        return luiDao.find(entity.getId()).toDto();
    }

    @Override
    @Transactional
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        LuiEntity entity = luiDao.find(luiId);

        if (null != entity) {
            LuiEntity modifiedEntity = new LuiEntity(luiInfo);
            String cluId = luiInfo.getCluId();
            if (null != cluId && checkExistenceForClu(cluId, context))
                modifiedEntity.setCluId(cluId);

            String atpId = luiInfo.getAtpId();
            if (null != atpId && checkExistenceForAtp(atpId, context))
                modifiedEntity.setAtpId(atpId);

            this.setLuiCluCluRelations(luiInfo.getCluCluRelationIds(), entity.getCluCluReltns(), modifiedEntity);
            this.setUnitsContentOwner(luiInfo.getUnitsContentOwner(), entity.getUnitsContentOwners(), modifiedEntity);
            this.addUnitsDeployment(luiInfo.getUnitsDeployment(), entity.getUnitsDeployments(), modifiedEntity);
            this.addResultValuesGroups(luiInfo.getResultValuesGroupKeys(), entity.getResultValuesGroupRelationEntities(), modifiedEntity);

            luiDao.merge(modifiedEntity);
            return luiDao.find(modifiedEntity.getId()).toDto();
        } else
            throw new DoesNotExistException(luiId);
    }

    @Override
    @Transactional
    public StatusInfo deleteLui(String luiId, ContextInfo context) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        LuiEntity entity = luiDao.find(luiId);
        if (null != entity) {
            List<LuiLuiRelationEntity> rels = luiLuiRelationDao.getLuiLuiRelationsByLui(luiId);
            if (null != rels && !rels.isEmpty()) {
                for (LuiLuiRelationEntity rel : rels)
                    luiLuiRelationDao.remove(rel);
            }

            luiDao.remove(entity);
        } else
            throw new DoesNotExistException(luiId);

        return status;
    }

    /**
     * 
     * Sets the LuiResultValuesGroup entities on the LuiEntity.
     * 
     * The method loops thru the existing entities to find already added relations and only add new entities if required. New entities 
     * are created based on the given resultValuesGroupKeys.  
     * 
     * @param resultValuesGroupKeys
     * @param existingResultValuesGrps
     * @param entity
     */
    private void addResultValuesGroups(final List<String> resultValuesGroupKeys, final List<LuiResultValuesGroupEntity> existingResultValuesGrps, LuiEntity entity) {

        if (resultValuesGroupKeys == null)
            return;

        keys: for (String resultValueGroupKey : resultValuesGroupKeys) {
            for (LuiResultValuesGroupEntity existingResultValueGroup : existingResultValuesGrps) {
                if (existingResultValueGroup.getResultValuesGroupKey().equals(resultValueGroupKey)) {
                    entity.getResultValuesGroupRelationEntities().add(existingResultValueGroup);
                    continue keys;
                }
            }

            entity.getResultValuesGroupRelationEntities().add(new LuiResultValuesGroupEntity(entity, resultValueGroupKey));
        }

    }

    /**
     * 
     * Sets the LuiResultValuesGroup entities on the LuiEntity.
     * 
     * The method loops thru the existing entities to find already added relations and only add new entities if required. New entities 
     * are created based on the given orgIds. 
     * 
     * @param orgIds
     * @param existingUnitsDeployments
     * @param entity
     */
    private void addUnitsDeployment(final List<String> orgIds, final List<LuiUnitsDeploymentEntity> existingUnitsDeployments, LuiEntity entity) {

        if (orgIds == null)
            return;

        orgs: for (String orgId : orgIds) {
            for (LuiUnitsDeploymentEntity existingUnitsDeployment : existingUnitsDeployments) {
                if (existingUnitsDeployment.getOrgId().equals(orgId)) {
                    entity.getUnitsDeployments().add(existingUnitsDeployment);
                    continue orgs;
                }
            }

            entity.getUnitsDeployments().add(new LuiUnitsDeploymentEntity(entity, orgId));
        }

    }

    /**
     * 
     * Sets the LuiUnitsContentOwner entities on the LuiEntity.
     * 
     * The method loops thru the existing entities to find already added relations and only add new entities if required. New entities 
     * are created based on the given orgIds.
     * 
     * @param orgIds
     * @param existingUnitsContentOwners
     * @param entity
     */
    private void setUnitsContentOwner(final List<String> orgIds, final List<LuiUnitsContentOwnerEntity> existingUnitsContentOwners, LuiEntity entity) {

        if (orgIds == null)
            return;

        orgs: for (String orgId : orgIds) {
            for (LuiUnitsContentOwnerEntity existingUnitsContentOwner : existingUnitsContentOwners) {
                if (existingUnitsContentOwner.getOrgId().equals(orgId)) {
                    entity.getUnitsContentOwners().add(existingUnitsContentOwner);
                    continue orgs;
                }
            }

            entity.getUnitsContentOwners().add(new LuiUnitsContentOwnerEntity(entity, orgId));
        }

    }

    /**
     * 
     * Sets the LuiCluCluRelation entities on the LuiEntity.
     * 
     * The method loops thru the existing entities to find already added relations and only add new entities if required. New entities 
     * are created based on the given cluCluRelationdIds.
     * 
     * @param cluCluRelationdIds
     * @param existingCluCluReltns
     * @param entity
     */
    private void setLuiCluCluRelations(final List<String> cluCluRelationdIds, List<LuiCluCluRelationEntity> existingCluCluReltns, LuiEntity entity) {

        if (cluCluRelationdIds == null)
            return;

        reltns: for (String cluCluRelationId : cluCluRelationdIds) {
            for (LuiCluCluRelationEntity existingCluCluReltn : existingCluCluReltns) {
                if (existingCluCluReltn.getClucluRelationId().equals(cluCluRelationId)) {
                    entity.getCluCluReltns().add(existingCluCluReltn);
                    continue reltns;
                }
            }

            entity.getCluCluReltns().add(new LuiCluCluRelationEntity(entity, cluCluRelationId));
        }

    }

    @Override
    public LuiInfo updateLuiState(String luiId, String luState, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<String> searchForLuiLuiRelationIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<String>();
    }

    @Override
    public List<LuiLuiRelationInfo> searchForLuiLuiRelations(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<LuiLuiRelationInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateLuiLuiRelation(String validationType, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    private boolean checkExistenceForRelation(LuiLuiRelationInfo relationInfo) {
        boolean existing = false;

        List<LuiLuiRelationEntity> rels = luiLuiRelationDao.getLuiLuiRelationsByLui(relationInfo.getLuiId());

        if (rels != null && !rels.isEmpty()) {
            for (LuiLuiRelationEntity rel : rels) {
                if (rel.getLui().getId().equals(relationInfo.getLuiId()) && rel.getRelatedLui().getId().equals(relationInfo.getRelatedLuiId())) {
                    existing = true;
                    break;

                }
            }
        }

        return existing;
    }

    @Override
    @Transactional
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luLuRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) throws AlreadyExistsException, CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (!checkExistenceForRelation(luiLuiRelationInfo)) {
            LuiLuiRelationEntity entity = new LuiLuiRelationEntity(luiLuiRelationInfo);
            entity.setId(UUIDHelper.genStringUUID());
            entity.setLui(luiDao.find(luiId));
            entity.setRelatedLui(luiDao.find(relatedLuiId));
            luiLuiRelationDao.persist(entity);
            return entity.toDto();
        } else
            throw new AlreadyExistsException("The Lui-Lui relation already exists. lui=" + luiLuiRelationInfo.getLuiId() + ", relatedLui=" + luiLuiRelationInfo.getRelatedLuiId());

    }

    @Override
    @Transactional
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        luiLuiRelationInfo.setId(luiLuiRelationId);
        LuiLuiRelationEntity luiLuiRelationEntity = luiLuiRelationDao.find(luiLuiRelationId);
        luiLuiRelationEntity = new LuiLuiRelationEntity(luiLuiRelationInfo);
        return luiLuiRelationEntity.toDto();
    }

    @Override
    @Transactional
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.FALSE);
        LuiLuiRelationEntity deleteLuiLuiRelationEntity = luiLuiRelationDao.find(luiLuiRelationId);
        luiLuiRelationDao.remove(deleteLuiLuiRelationEntity);
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public LuiCapacityInfo getLuiCapacity(String luiCapacityId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByIds(List<String> luiCapacityIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<LuiCapacityInfo>();
    }

    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getLuiCapacityIdsByType(String luiCapacityTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<String>();
    }

    @Override
    public List<String> searchForLuiCapacityIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<String>();
    }

    @Override
    public List<LuiCapacityInfo> searchForLuiCapacities(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<LuiCapacityInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateLuiCapacity(String validationType, LuiCapacityInfo luiCapacityInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public LuiCapacityInfo createLuiCapacity(LuiCapacityInfo luiCapacityInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public LuiCapacityInfo updateLuiCapacity(String luiCapacityId, LuiCapacityInfo luiCapacityInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;
    }

    @Override
    public StatusInfo deleteLuiCapacity(String luiCapacityId, ContextInfo context) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }
}
