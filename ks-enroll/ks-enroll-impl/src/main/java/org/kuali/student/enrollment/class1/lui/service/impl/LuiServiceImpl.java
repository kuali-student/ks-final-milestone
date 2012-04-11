package org.kuali.student.enrollment.class1.lui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
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
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class LuiServiceImpl implements LuiService {

    private LuiDao luiDao;
    private LuiLuiRelationDao luiLuiRelationDao;

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
    public List<String> getLuiIdsByAtpAndType(String atpId, String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<String>();
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
    public LuiInfo createLui(String cluId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (!cluId.equals(luiInfo.getCluId())) {
            throw new InvalidParameterException(cluId + " does not match the cluId in the info object " + luiInfo.getCluId());
        }
        if (!atpId.equals(luiInfo.getAtpId())) {
            throw new InvalidParameterException(atpId + " does not match the atp in the info object " + luiInfo.getAtpId());
        }
        if (!luiTypeKey.equals(luiInfo.getTypeKey())) {
            throw new InvalidParameterException(luiTypeKey + " does not match the type in the info object " + luiInfo.getTypeKey());
        }
        LuiEntity entity = new LuiEntity(luiInfo);
        entity.setAtpId(atpId);
        entity.setCluId(cluId);
        entity.setLuiType(luiTypeKey);
        entity.setCreateId(context.getPrincipalId());
        entity.setCreateTime(context.getCurrentDate());
        entity.setUpdateId(context.getPrincipalId());
        entity.setUpdateTime(context.getCurrentDate());
        luiDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        LuiEntity entity = luiDao.find(luiId);

        if (!luiId.equals(luiInfo.getId())) {
            throw new InvalidParameterException(luiId + " does not match the id on the object " + luiInfo.getId());
        }
        if (null == entity) {
            throw new DoesNotExistException(luiId);
        }
        entity.fromDto(luiInfo);
        entity.setUpdateId(context.getPrincipalId());
        entity.setUpdateTime(context.getCurrentDate());
        luiDao.merge(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public StatusInfo deleteLui(String luiId, ContextInfo context)
            throws DependentObjectsExistException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LuiEntity entity = luiDao.find(luiId);
        if (null == entity) {
            throw new DoesNotExistException(luiId);
        }
        List<LuiLuiRelationEntity> rels = luiLuiRelationDao.getLuiLuiRelationsByLui(luiId);
        for (LuiLuiRelationEntity rel : rels) {
            luiLuiRelationDao.remove(rel);
        }
        luiDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public LuiInfo updateLuiState(String luiId, String luState, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiEntity entity = luiDao.find(luiId);
        if (null == entity) {
            throw new DoesNotExistException(luiId);
        }
        entity.setLuiState(luState);
        entity.setUpdateId(context.getPrincipalId());
        entity.setUpdateTime(context.getCurrentDate());
        luiDao.merge(entity);
        return entity.toDto();
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

    @Override
    @Transactional
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId,
            String luLuRelationTypeKey,
            LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
            throws AlreadyExistsException, CircularRelationshipException,
            DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        LuiLuiRelationEntity entity = new LuiLuiRelationEntity(luiLuiRelationInfo);
        entity.setLuiLuiRelationType(luLuRelationTypeKey);
        entity.setLui(luiDao.find(luiId));
        if (entity.getLui() == null) {
            throw new DoesNotExistException(luiId);
        }
        entity.setRelatedLui(luiDao.find(relatedLuiId));
        if (entity.getRelatedLui() == null) {
            throw new DoesNotExistException(luiId);
        }
        entity.setLuiLuiRelationType(luLuRelationTypeKey);
        entity.setCreateId(context.getPrincipalId());
        entity.setCreateTime(context.getCurrentDate());
        entity.setUpdateId(context.getPrincipalId());
        entity.setUpdateTime(context.getCurrentDate());
        luiLuiRelationDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        if (!luiLuiRelationId.equals(luiLuiRelationInfo.getId())) {
            throw new InvalidParameterException(luiLuiRelationId + " doe not match the id on the object " + luiLuiRelationInfo.getId());
        }
        LuiLuiRelationEntity entity = luiLuiRelationDao.find(luiLuiRelationId);
        if (entity == null) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        entity.fromDto(luiLuiRelationInfo);
        entity.setUpdateId(context.getPrincipalId());
        entity.setUpdateTime(context.getCurrentDate());
        luiLuiRelationDao.merge(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        LuiLuiRelationEntity entity = luiLuiRelationDao.find(luiLuiRelationId);
        if (entity == null) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        luiLuiRelationDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.FALSE);
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
