/**
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.enrollment.class1.lui.service.impl;

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class1.lui.model.LuCodeEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiAttributeEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiIdentifierEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiUnitsDeploymentEntity;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiSetInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;


public class LuiServiceImpl 
    implements LuiService {

    private CriteriaLookupService criteriaLookupService;

    private LuiDao luiDao;
    private LuiLuiRelationDao luiLuiRelationDao;

    public LuiDao getLuiDao() {
        return luiDao;
    }

    public void setLuiDao(LuiDao luiDao) {
        this.luiDao = luiDao;
    }

    public CriteriaLookupService getCriteriaLookupService() {
        return criteriaLookupService;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }

    public LuiLuiRelationDao getLuiLuiRelationDao() {
        return luiLuiRelationDao;
    }

    public void setLuiLuiRelationDao(LuiLuiRelationDao luiLuiRelationDao) {
        this.luiLuiRelationDao = luiLuiRelationDao;
    }

    @Override
    @Transactional(readOnly = true)
    public LuiInfo getLui(String luiId, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        LuiEntity lui = luiDao.find(luiId);
        if (null == lui) {
            throw new DoesNotExistException(luiId);
        }

        return lui.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LuiInfo> getLuisByIds(List<String> luiIds, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        List<LuiEntity> entityList = luiDao.findByIds(luiIds);
        List<LuiInfo> infoList = new ArrayList<LuiInfo>();


        for (LuiEntity luiEntity : entityList) {

            infoList.add(luiEntity.toDto());
        }

        return infoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getLuiIdsByType(String luiTypeKey, ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        // make sure the given type key is a valid lui type

        List<LuiEntity> luis = luiDao.getLuisByType(luiTypeKey);
        List<String> luiIds = new ArrayList<String>();

        for (LuiEntity lui : luis) {
            luiIds.add(lui.getId());
        }

        return luiIds;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getLuiIdsByClu(String cluId, ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        List<LuiEntity> luis = luiDao.getLuisByClu(cluId);
        List<String> luiIds = new ArrayList<String>();

        for (LuiEntity lui : luis) {
            luiIds.add(lui.getId());
        }

        return luiIds;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getLuiIdsByAtpAndType(String atpId, String typeKey, 
                                              ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {
        return luiDao.getLuisIdsByAtpAndType(atpId,typeKey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getLuiIdsByAtpAndClu(String cluId, String atpId,ContextInfo context)
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        List<LuiEntity> luis = luiDao.getLuisByAtpAndClu(atpId, cluId);
        List<String> luiIds = new ArrayList<String>();

        for (LuiEntity lui : luis) {
            luiIds.add(lui.getId());
        }

        return luiIds;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LuiInfo> getLuisByAtpAndClu(String cluId, String atpId, 
                                            ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        List<LuiEntity> luiEntities = luiDao.getLuisByAtpAndClu(atpId, cluId);
        List<LuiInfo> luiInfos = new ArrayList<LuiInfo>();
        for(LuiEntity luiEntity: luiEntities){
            luiInfos.add(luiEntity.toDto());
        }
      return luiInfos;

    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForLuiIds(QueryByCriteria criteria, 
                                        ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        GenericQueryResults<String> results = criteriaLookupService.lookupIds(LuiEntity.class, criteria);
        return results.getResults();

    }

    @Override
    @Transactional(readOnly = true)
    public List<LuiInfo> searchForLuis(QueryByCriteria criteria, 
                                       ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return new ArrayList<LuiInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateLui(String validationTypeKey, 
                                                  String luiTypeKey, 
                                                  String cluId, 
                                                  String atpId, 
                                                  LuiInfo luiInfo, 
                                                  ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public LuiInfo createLui(String cluId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo context) 
        throws DataValidationErrorException, DoesNotExistException, 
               InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException, 
               ReadOnlyException {

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
        
        entity.setEntityCreated(context);
        
        if(entity.getIdentifiers() != null){
            for(LuiIdentifierEntity ident:entity.getIdentifiers()){
                ident.setCreateId(context.getPrincipalId());
                ident.setCreateTime(context.getCurrentDate());
                ident.setUpdateId(context.getPrincipalId());
                ident.setUpdateTime(context.getCurrentDate());
            }
        }
        if(entity.getLuiCodes() != null){
            for(LuCodeEntity code : entity.getLuiCodes()){
                code.setCreateId(context.getPrincipalId());
                code.setCreateTime(context.getCurrentDate());
                code.setUpdateId(context.getPrincipalId());
                code.setUpdateTime(context.getCurrentDate());
            }
        }


        luiDao.persist(entity);

        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context) 
        throws DataValidationErrorException, DoesNotExistException, 
               InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException, 
               ReadOnlyException, VersionMismatchException {

        LuiEntity entity = luiDao.find(luiId);

        if (!luiId.equals(luiInfo.getId())) {
            throw new InvalidParameterException(luiId + " does not match the id on the object " + luiInfo.getId());
        }
        if (null == entity) {
            throw new DoesNotExistException(luiId);
        }

        //Transform the DTO to the entity
        List<Object> orphans = entity.fromDto(luiInfo);

        //Delete any orphaned children
        for(Object orphan : orphans){
            luiDao.getEm().remove(orphan);
        }

        //Update any Meta information
       
        entity.setEntityUpdated(context);
        

        if(entity.getIdentifiers() != null){
            for(LuiIdentifierEntity ident:entity.getIdentifiers()){
                if(ident.getCreateId() == null){
                    ident.setCreateId(context.getPrincipalId());
                }
                if(ident.getCreateTime() == null){
                    ident.setCreateTime(context.getCurrentDate());
                }
                ident.setUpdateId(context.getPrincipalId());
                ident.setUpdateTime(context.getCurrentDate());
            }
        }
        if(entity.getLuiCodes() != null){
            for(LuCodeEntity code : entity.getLuiCodes()){
                if(code.getCreateId() == null){
                    code.setCreateId(context.getPrincipalId());
                }
                if(code.getCreateTime() == null){
                    code.setCreateTime(context.getCurrentDate());
                }
                code.setUpdateId(context.getPrincipalId());
                code.setUpdateTime(context.getCurrentDate());
            }
        }

        //Perform the merge
        entity = luiDao.merge(entity);

        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteLui(String luiId, ContextInfo context) 
        throws DependentObjectsExistException, DoesNotExistException, 
               InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        LuiEntity entity = luiDao.find(luiId);
        if (null == entity) {
            throw new DoesNotExistException(luiId);
        }
        //Delete relationships
        for (LuiLuiRelationEntity rel : luiLuiRelationDao.getLuiLuiRelationsByLui(luiId)) {
            luiLuiRelationDao.remove(rel);
        }
        //Delete attributes
        if(entity.getAttributes()!=null){
            for(LuiAttributeEntity attr:entity.getAttributes()){
                luiDao.getEm().remove(attr); //TODO why is the dao strongly typed to a single entity?
            }
        }
        //Delete identifiers
        if(entity.getIdentifiers()!=null){
            for(LuiIdentifierEntity ident:entity.getIdentifiers()){
                luiDao.getEm().remove(ident);
            }
        }
        //Delete units
        if(entity.getLuiUnitsDeployment()!=null){
            for(LuiUnitsDeploymentEntity units:entity.getLuiUnitsDeployment()){
                luiDao.getEm().remove(units);
            }
        }
        //Delete Codes
        if(entity.getLuiCodes()!=null){
            for(LuCodeEntity luiCode:entity.getLuiCodes()){
                luiDao.getEm().remove(luiCode);
            }
        }

        //Delete the actual Lui entity
        luiDao.remove(entity);

        return new StatusInfo();
    }

    @Override
    @Transactional(readOnly = true)
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, 
                                                ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return luiLuiRelationDao.find(luiLuiRelationId).toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIds(List<String> luiLuiRelationIds, 
                                                            ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getLuiLuiRelationIdsByType(String luiLuiRelationTypeKey, 
                                                   ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, 
                                                            ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException { 



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
    @Transactional(readOnly = true)
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLuiAndRelatedLui(String luiId,
                                                                         String relatedLuiId,
                                                                         ContextInfo context)
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException { 

        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<LuiInfo> getLuiLuiRelationsByLuiAndRelatedLuiType(String luiId,
                                                                  String relatedLuiTypeKey,
                                                                  ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<LuiInfo> getLuisByRelatedLuiAndRelationType(String relatedLuiId,
                                                            String luiLuiRelationTypeKey,
                                                            ContextInfo context)
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {
        
        List<LuiEntity> entityList = luiLuiRelationDao.getLuisByRelation(relatedLuiId, luiLuiRelationTypeKey);
        List<LuiInfo> infoList = new ArrayList<LuiInfo>();
        if (entityList != null && !entityList.isEmpty()) {
            for (LuiEntity entity : entityList) {
                infoList.add(entity.toDto());
            }

        }

        return infoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getLuiIdsByRelatedLuiAndRelationType(String relatedLuiId,
                                                             String luiLuiRelationTypeKey,
                                                             ContextInfo context)
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        List<String> returnVals = new ArrayList<String>();
        returnVals.addAll(luiLuiRelationDao.getLuiIdsByRelation(relatedLuiId, luiLuiRelationTypeKey));
        return returnVals;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getLuiIdsByLuiAndRelationType(String luiId,
                                                      String luiLuiRelationTypeKey,
                                                      ContextInfo context)
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        List<String> returnVals = new ArrayList<String>();
        returnVals.addAll(luiLuiRelationDao.getRelatedLuisByLuiId(luiId, luiLuiRelationTypeKey));
        return returnVals;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LuiInfo> getRelatedLuisByLuiAndRelationType(String luiId,
                                                            String luiLuiRelationTypeKey,
                                                            ContextInfo context)
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {
        if (luiId == null) {
            throw new MissingParameterException("luiId is null");
        }
        List<LuiInfo> relatedLuis =  new ArrayList<LuiInfo>();
        List<LuiEntity> relatedLuiEntities =  luiLuiRelationDao.getRelatedLuisByLuiIdAndRelationType(luiId, luiLuiRelationTypeKey);
        for(LuiEntity relatedLuiEntity : relatedLuiEntities) {
            relatedLuis.add(relatedLuiEntity.toDto());
        }

        return  relatedLuis;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForLuiLuiRelationIds(QueryByCriteria criteria, 
                                                   ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        // TODO
        return new ArrayList<String>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LuiLuiRelationInfo> searchForLuiLuiRelations(QueryByCriteria criteria, 
                                                             ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        // TODO
        return new ArrayList<LuiLuiRelationInfo>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValidationResultInfo> validateLuiLuiRelation(String validationTypeKey, 
                                                             String luiId,
                                                             String relatedLuiId,
                                                             String luiLuiRelationTypeKey,
                                                             LuiLuiRelationInfo luiLuiRelationInfo, 
                                                             ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

       List<ValidationResultInfo> validationResultInfos = new ArrayList<ValidationResultInfo>() ;
        ValidationResultInfo invalidIdsValidationInfo = new ValidationResultInfo();
        if(luiLuiRelationInfo.getLuiId() ==null || luiLuiRelationInfo.getRelatedLuiId() == null)        {
            invalidIdsValidationInfo.setError("Relation Info is missing relation id data");
            invalidIdsValidationInfo.setLevel(ValidationResult.ErrorLevel.ERROR);
            validationResultInfos.add(invalidIdsValidationInfo);
        }
        ValidationResultInfo typeStateValidation = new ValidationResultInfo();
        if(luiLuiRelationInfo.getTypeKey() ==null || luiLuiRelationInfo.getStateKey()==null ){
            typeStateValidation.setError("Missing type or state data");
            typeStateValidation.setLevel(ValidationResult.ErrorLevel.ERROR);
            validationResultInfos.add(typeStateValidation);

        }
            return validationResultInfos;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, 
                                                   String relatedLuiId, 
                                                   String luiLuiRelationTypeKey, 
                                                   LuiLuiRelationInfo luiLuiRelationInfo, 
                                                   ContextInfo context)
        throws DataValidationErrorException,
               DoesNotExistException, InvalidParameterException,
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException, ReadOnlyException  {

        LuiLuiRelationEntity entity = new LuiLuiRelationEntity(luiLuiRelationInfo);
        entity.setLuiLuiRelationType(luiLuiRelationTypeKey);
        entity.setLui(luiDao.find(luiId));
        if (entity.getLui() == null) {
            throw new DoesNotExistException(luiId);
        }

        entity.setRelatedLui(luiDao.find(relatedLuiId));
        if (entity.getRelatedLui() == null) {
            throw new DoesNotExistException(relatedLuiId);
        }

        entity.setLuiLuiRelationType(luiLuiRelationTypeKey);
        
        entity.setEntityCreated(context);
        
        luiLuiRelationDao.persist(entity);

        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, 
                                                   LuiLuiRelationInfo luiLuiRelationInfo, 
                                                   ContextInfo context) 
        throws DataValidationErrorException, DoesNotExistException, 
               InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException, 
               ReadOnlyException, VersionMismatchException {


        if (!luiLuiRelationId.equals(luiLuiRelationInfo.getId())) {
            throw new InvalidParameterException(luiLuiRelationId + " does not match the id on the object " + luiLuiRelationInfo.getId());
        }

        LuiLuiRelationEntity entity = luiLuiRelationDao.find(luiLuiRelationId);
        if (entity == null) {
            throw new DoesNotExistException(luiLuiRelationId);
        }


        //Transform the DTO to the entity
        List<Object> orphans = entity.fromDto(luiLuiRelationInfo);
        
        entity.setEntityUpdated(context);
        

        luiLuiRelationDao.merge(entity);

        //Delete any orphaned children
        for(Object orphan : orphans){
            luiLuiRelationDao.getEm().remove(orphan);
        }

        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, 
                                           ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        LuiLuiRelationEntity entity = luiLuiRelationDao.find(luiLuiRelationId);
        if (entity == null) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        luiLuiRelationDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public LuiCapacityInfo getLuiCapacity(String luiCapacityId, 
                                          ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<LuiCapacityInfo> getLuiCapacitiesByIds(List<String> luiCapacityIds, 
                                                       ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<LuiCapacityInfo> getLuiCapacitiesByLui(String luiId, 
                                                       ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getLuiCapacityIdsByType(String luiCapacityTypeKey, 
                                                ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForLuiCapacityIds(QueryByCriteria criteria, 
                                                ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<LuiCapacityInfo> searchForLuiCapacities(QueryByCriteria criteria, 
                                                        ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateLuiCapacity(String validationTypeKey, 
                                                          String luiCapacityTypeKey, 
                                                          LuiCapacityInfo luiCapacityInfo, 
                                                          ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public LuiCapacityInfo createLuiCapacity(String luiCapacityTypeKey, 
                                             LuiCapacityInfo luiCapacityInfo, 
                                             ContextInfo context) 
        throws DataValidationErrorException, DoesNotExistException, 
               InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException, 
               ReadOnlyException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public LuiCapacityInfo updateLuiCapacity(String luiCapacityId, 
                                             LuiCapacityInfo luiCapacityInfo, 
                                             ContextInfo context) 
        throws DataValidationErrorException, DoesNotExistException, 
               InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException, 
               ReadOnlyException, VersionMismatchException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteLuiCapacity(String luiCapacityId, 
                                        ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LuiSetInfo getLuiSet(String luiSetId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public List<LuiSetInfo> getLuiSetsByIds(List<String> luiSetIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public List<String> getLuiIdsFromLuiSet( String luiSetId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public List<ValidationResultInfo> validateLuiSet( String validationTypeKey,  String luiSetTypeKey, LuiSetInfo LuiSetInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public LuiSetInfo createLuiSet( String luiSetTypeKey, LuiSetInfo luiSetInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public LuiSetInfo updateLuiSet( String luiSetId, LuiSetInfo luiSetInfo,  ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException, VersionMismatchException {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public StatusInfo deleteLuiSet( String luiSetId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public List<LuiSetInfo> getLuiSetsByLui(String luiId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public List<String> getLuiSetIdsByType(String luiSetTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
    }
}