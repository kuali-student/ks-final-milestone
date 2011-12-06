/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.class1.lpr.dao.LprDao;
import org.kuali.student.enrollment.class1.lpr.dao.LprRosterDao;
import org.kuali.student.enrollment.class1.lpr.dao.LprRosterEntryDao;
import org.kuali.student.enrollment.class1.lpr.dao.LprTransactionDao;
import org.kuali.student.enrollment.class1.lpr.dao.LprTransactionItemDao;
import org.kuali.student.enrollment.class1.lpr.dao.LprTypeDao;
import org.kuali.student.enrollment.class1.lpr.model.LprRichTextEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprRosterAttributeEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprRosterEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprRosterEntryAttributeEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprRosterEntryEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprTransactionEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprTransactionItemEntity;
import org.kuali.student.enrollment.class1.lpr.model.LuiPersonRelationEntity;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.lpr.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemResultInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelation;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dao.StateDao;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author sambit
 */
@WebService(name = "LuiPersonRelationService", serviceName = "LuiPersonRelationService", portName = "LuiPersonRelationService", targetNamespace = "http://student.kuali.org/wsdl/lpr")
@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class LuiPersonRelationServiceImpl implements LuiPersonRelationService {

    private LprDao lprDao;
    private LuiDao luiDao;
    private LprRosterDao lprRosterDao;
    private LprTransactionDao lprTransDao;
    private LprTransactionItemDao lprTransItemDao;
    private StateDao stateDao;
    private LprTypeDao lprTypeDao;
    private StateService stateService;
    private LprRosterEntryDao lprRosterEntryDao;

    public void setLprTransItemDao(LprTransactionItemDao lprTransItemDao) {
        this.lprTransItemDao = lprTransItemDao;
    }

    public void setStateDao(StateDao stateDao) {
        this.stateDao = stateDao;
    }

    public LprTransactionDao getLprTransDao() {
        return lprTransDao;
    }

    public void setLprTransDao(LprTransactionDao lprTransDao) {
        this.lprTransDao = lprTransDao;
    }

    public LprDao getLprDao() {
        return lprDao;
    }

    public StateDao getLprStateDao() {
        return stateDao;
    }

    public LprTypeDao getLprTypeDao() {
        return lprTypeDao;
    }

    public LprRosterEntryDao getLprRosterEntryDao() {
        return lprRosterEntryDao;
    }

    public void setLprRosterEntryDao(LprRosterEntryDao lprRosterEntryDao) {
        this.lprRosterEntryDao = lprRosterEntryDao;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public void setLprDao(LprDao lprDao) {
        this.lprDao = lprDao;
    }

    public void setLprStateDao(StateDao stateDao) {
        this.stateDao = stateDao;
    }

    public void setLprTypeDao(LprTypeDao lprTypeDao) {
        this.lprTypeDao = lprTypeDao;
    }

    public void setLuiDao(LuiDao luiDao) {
        this.luiDao = luiDao;
    }

    public void setLprRosterDao(LprRosterDao lprRosterDao) {
        this.lprRosterDao = lprRosterDao;
    }

    // package-private so it's testable
    List<LuiPersonRelationInfo> getLprsByLuiPersonAndState(String personId, String luiId, String stateKey, ContextInfo context) {
        List<LuiPersonRelationEntity> lprEntities = lprDao.getLprsByLuiPersonAndState(personId, luiId, stateKey);
        List<LuiPersonRelationInfo> lprInfos = new ArrayList<LuiPersonRelationInfo>();
        if (null != lprEntities) {
            for (LuiPersonRelationEntity entity : lprEntities) {
                lprInfos.add(entity.toDto());
            }
        }
        return lprInfos;
    }

    private LuiPersonRelationEntity toCluForCreate(LuiPersonRelationInfo luiPersonRelationInfo) {
        LuiPersonRelationEntity lpr = new LuiPersonRelationEntity(luiPersonRelationInfo);
        if (null != luiPersonRelationInfo.getStateKey()) {
            lpr.setPersonRelationState(stateDao.find(luiPersonRelationInfo.getStateKey()));
        }
        if (null != luiPersonRelationInfo.getTypeKey()) {
            lpr.setPersonRelationType(lprTypeDao.find(luiPersonRelationInfo.getTypeKey()));
        }
        
         // TODO - Attributes?
        return lpr;
    }

    private String createLprFromLprTransactionItem(LprTransactionItemInfo lprTransactionItemInfo, ContextInfo context)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LuiPersonRelationInfo luiPersonRelation = new LuiPersonRelationInfo();
        luiPersonRelation.setCommitmentPercent(100.00F);
        luiPersonRelation.setEffectiveDate(new Date());
        luiPersonRelation.setLuiId(lprTransactionItemInfo.getNewLuiId());
        luiPersonRelation.setPersonId(lprTransactionItemInfo.getPersonId());
        luiPersonRelation.setStateKey(LuiPersonRelationServiceConstants.REGISTERED_STATE_KEY);
        luiPersonRelation.setTypeKey(LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY);
        String createdLpr;
        try {
            createdLpr = createLpr(lprTransactionItemInfo.getPersonId(), lprTransactionItemInfo.getNewLuiId(),
                    LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY, luiPersonRelation, context);
        } catch (DisabledIdentifierException e) {
            throw new OperationFailedException(e.getMessage(), e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException(e.getMessage(), e);
        }

        return createdLpr;
    }

    private void _checkForMissingParameter(Object param, String paramName) throws MissingParameterException {
        if (null == param) {
            throw new MissingParameterException("Parameter '" + paramName + "' cannot be null");
        }
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByLui(String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationEntity> luiPersonRelations = lprDao.getByLuiId(luiId);
        List<LuiPersonRelationInfo> dtos = new ArrayList<LuiPersonRelationInfo>();
        for (LuiPersonRelationEntity entity : luiPersonRelations) {
            dtos.add(entity.toDto());
        }
        return dtos;
    }

    @Override
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState,
            String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;

    }

    @Override
    public LuiPersonRelationInfo getLpr(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiPersonRelationEntity lpr = lprDao.find(luiPersonRelationId);
        return null != lpr ? lpr.toDto() : null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByIdList(List<String> luiPersonRelationIdList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> lprInfos = new ArrayList<LuiPersonRelationInfo>();
        List<LuiPersonRelationEntity> lprEntities = lprDao.findByIds(luiPersonRelationIdList);
        for (LuiPersonRelationEntity lprEntity : lprEntities) {
            LuiPersonRelationInfo lprInfo = lprEntity.toDto();
            lprInfos.add(lprInfo);
        }
        return lprInfos;
    }

    @Override
    public List<String> getLuiIdsByPerson(String personId, String luiPersonRelationType, String relationState,
            ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getPersonIdsByLui(String luiId, String luiPersonRelationType, String relationState,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	List<String> returnVals = new ArrayList<String>();

        returnVals.addAll(lprDao.getPersonIdsByLui(luiId, luiPersonRelationType, relationState));
        return returnVals;

    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByLuiAndPerson(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationEntity> entityList = lprDao.getLprByLuiAndPerson(personId, luiId);
        
        List<LuiPersonRelationInfo> infoList = new ArrayList<LuiPersonRelationInfo>();
        if(entityList != null && !entityList.isEmpty()){
	        for (LuiPersonRelationEntity entity : entityList){
	             infoList.add(entity.toDto());
	        }
       
        }
        return infoList;

    }

    @Override
    public List<String> getLprIdsByLuiAndPerson(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> returnVals = new ArrayList<String>();

        returnVals.addAll(lprDao.getLprIdsByLuiAndPerson(personId, luiId));
        return returnVals;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPerson(String personId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getLprIdsByPerson(String personId, ContextInfo context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getLprIdsByLui(String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateLpr(String validationType, LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForLprIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional
    public String createLpr(String personId, String luiId, String luiPersonRelationType,
            LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiPersonRelationEntity lpr = toCluForCreate(luiPersonRelationInfo);
        lprDao.persist(lpr);
        return lpr.getId();
    }

    @Override
    public List<String> createBulkRelationshipsForLui(String luiId, List<String> personIdList, String relationState,
            String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional
    public LuiPersonRelationInfo updateLpr(String luiPersonRelationId, LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            ReadOnlyException, OperationFailedException, PermissionDeniedException {
        LuiPersonRelationEntity lprEntity = lprDao.find(luiPersonRelationId);

        if (lprEntity != null) {
            LuiPersonRelationEntity modifiedLpr = new LuiPersonRelationEntity(luiPersonRelationInfo);

            if (luiPersonRelationInfo.getStateKey() != null) {
                modifiedLpr.setPersonRelationState(stateDao.find(luiPersonRelationInfo.getStateKey()));
            }

            if (luiPersonRelationInfo.getTypeKey() != null) {
                modifiedLpr.setPersonRelationType(lprTypeDao.find(luiPersonRelationInfo.getTypeKey()));
            }

            lprDao.merge(modifiedLpr);
            return lprDao.find(modifiedLpr.getId()).toDto();
        } else {
            throw new DoesNotExistException(luiPersonRelationId);
        }
    }

    @Override
    @Transactional
    public StatusInfo deleteLpr(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        _checkForMissingParameter(luiPersonRelationId, "luiPersonRelationId");
        LuiPersonRelationEntity lprEntity = lprDao.find(luiPersonRelationId);
        lprEntity.setPersonRelationState(stateDao.find(LuiPersonRelationServiceConstants.DROPPED_STATE_KEY));
        lprDao.merge(lprEntity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException,
            MissingParameterException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getProcessByObjectType(String objectTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return stateService.getInitialValidStates(processKey, context);
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> searchForLprs(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLprTransaction(java.lang.String,
     *      org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LprTransactionInfo getLprTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        LprTransactionEntity transactionEntity = lprTransDao.find(lprTransactionId);

        return transactionEntity.toDto();
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#deleteLprTransaction(java.lang.String,
     *      org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteLprTransaction(String lprTransactionId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StatusInfo status = new StatusInfo();

        LprTransactionEntity lprTrans = lprTransDao.find(lprTransactionId);
        if (null != lprTrans) {

            lprTransDao.remove(lprTrans);

            status.setSuccess(Boolean.TRUE);

        } else
            status.setSuccess(Boolean.FALSE);

        return status;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLprsByPersonForAtp(java.lang.String,
     *      java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtp(String personId, String atpId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LprRosterInfo updateLprRoster(String lprRosterId, LprRosterInfo lprRosterInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional
    public String createLprRoster(LprRosterInfo lprRosterInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        LprRosterEntity rosterEntity = new LprRosterEntity(lprRosterInfo);
        rosterEntity.setId(UUIDHelper.genStringUUID());

        if (lprRosterInfo.getStateKey() != null) {
            rosterEntity.setLprRosterState(stateDao.find(lprRosterInfo.getStateKey()));
        }
        if (lprRosterInfo.getTypeKey() != null) {
            rosterEntity.setLprRosterType(lprTypeDao.find(lprRosterInfo.getTypeKey()));
        }

        if (lprRosterInfo.getAssociatedLuiIds() != null) {
            List<LuiEntity> luiEntities = luiDao.findByIds(lprRosterInfo.getAssociatedLuiIds());
            rosterEntity.setAssociatedLuis(luiEntities);
        }

        if (rosterEntity.getAttributes() != null) {
            for (LprRosterAttributeEntity attribute : rosterEntity.getAttributes()) {
                if (StringUtils.isEmpty(attribute.getId())) {
                    attribute.setId(UUIDHelper.genStringUUID());
                }
            }
        }

        lprRosterDao.persist(rosterEntity);

        rosterEntity = lprRosterDao.find(rosterEntity.getId());

        return rosterEntity.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteLprRoster(String lprRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StatusInfo status = new StatusInfo();

        LprRosterEntity entity = lprRosterDao.find(lprRosterId);

        if (entity != null) {
            status.setSuccess(true);
        } else {
            status.setSuccess(false);
        }

        /**
         * FIXME : Remove entries from KSEN_LPRROSTER_LUI_RELTN, attributes and
         * desc
         */

        lprRosterDao.remove(entity);
        return status;
    }

    @Override
    public List<LprRosterEntryInfo> getEntriesForLprRoster(String lprRosterId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<LprRosterEntryEntity> entities = lprRosterEntryDao.getLprRosterEntriesForLprRoster(lprRosterId);

        List<LprRosterEntryInfo> infos = new ArrayList<LprRosterEntryInfo>();
        if (entities != null) {
            for (LprRosterEntryEntity entry : entities) {
                infos.add(entry.toDto());
            }
        }

        return infos;

    }

    @Override
    public List<LprRosterEntryInfo> getLprRosterEntriesByIdList(List<String> lprRosterEntryIdList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<LprRosterEntryEntity> entities = lprRosterEntryDao.findByIds(lprRosterEntryIdList);

        List<LprRosterEntryInfo> infos = new ArrayList<LprRosterEntryInfo>();
        if (entities != null) {
            for (LprRosterEntryEntity entry : entities) {
                infos.add(entry.toDto());
            }
        }

        return infos;
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLuiAndRosterType(String luiId, String lprRosterTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<LprRosterEntity> entities = lprRosterDao.getLprRostersByLuiAndRosterType(luiId, lprRosterTypeKey);
        List<LprRosterInfo> lprRosterInfoList = new ArrayList();
        if (entities != null) {
            for (LprRosterEntity lprRosterEntity : entities) {
                lprRosterInfoList.add(lprRosterEntity.toDto());
            }
        }

        return lprRosterInfoList;
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLui(String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LprRosterEntity> entities = lprRosterDao.getLprRostersByLui(luiId);
        List<LprRosterInfo> lprRosterInfoList = new ArrayList();
        if (entities != null) {
            for (LprRosterEntity lprRosterEntity : entities) {
                lprRosterInfoList.add(lprRosterEntity.toDto());
            }
        }

        return lprRosterInfoList;
    }

    @Override
    public LprRosterInfo getLprRoster(String lprRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        LprRosterEntity entity = lprRosterDao.find(lprRosterId);
        if (entity == null) {
            return null;
        }

        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false)
    public String createLprRosterEntry(LprRosterEntryInfo lprRosterEntryInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        LprRosterEntryEntity rosterEntity = new LprRosterEntryEntity(lprRosterEntryInfo);
        rosterEntity.setId(UUIDHelper.genStringUUID());
        rosterEntity.setEffectiveDate(lprRosterEntryInfo.getEffectiveDate());
        rosterEntity.setExpirationDate(lprRosterEntryInfo.getExpirationDate());
        rosterEntity.setLprId(lprRosterEntryInfo.getLprId());
        rosterEntity.setLprRosterId(lprRosterEntryInfo.getLprRosterId());

        if (lprRosterEntryInfo.getStateKey() != null) {
            rosterEntity.setLprEntryRelationState(stateDao.find(lprRosterEntryInfo.getStateKey()));
        }
        if (lprRosterEntryInfo.getTypeKey() != null) {
            rosterEntity.setLprEntryRelationType(lprTypeDao.find(lprRosterEntryInfo.getTypeKey()));
        }

        if (rosterEntity.getAttributes() != null) {
            for (LprRosterEntryAttributeEntity attribute : rosterEntity.getAttributes()) {
                if (StringUtils.isEmpty(attribute.getId())) {
                    attribute.setId(UUIDHelper.genStringUUID());
                }
                if (attribute.getOwner() == null) {
                    attribute.setOwner(rosterEntity);
                }
            }
        }

        lprRosterEntryDao.persist(rosterEntity);

        rosterEntity = lprRosterEntryDao.find(rosterEntity.getId());

        return rosterEntity.getId();

    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteLprRosterEntry(String lprRosterEntryId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StatusInfo status = new StatusInfo();

        LprRosterEntryEntity entity = lprRosterEntryDao.find(lprRosterEntryId);

        if (entity != null) {
            status.setSuccess(true);
        } else {
            status.setSuccess(false);
        }

        lprRosterEntryDao.remove(entity);
        return status;
    }

    @Override
    public StatusInfo insertLprRosterEntryInPosition(String lprRosterEntryId, Integer position, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo reorderLprRosterEntries(List<String> lprRosterEntryIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public LprTransactionInfo createLprTransaction(LprTransactionInfo lprTransactionInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        LprTransactionEntity lprTransactionEntity = new LprTransactionEntity(lprTransactionInfo);
        if(lprTransactionEntity.getId()==null){        
            lprTransactionEntity.setId(UUIDHelper.genStringUUID());
        }
        if (null != lprTransactionInfo.getStateKey()) {
            lprTransactionEntity.setLprTransState(stateDao.find(lprTransactionInfo.getStateKey()));
        }

        if (null != lprTransactionInfo.getTypeKey()) {
            lprTransactionEntity.setLprTransType(lprTypeDao.find(lprTransactionInfo.getTypeKey()));
        }
        if (null != lprTransactionInfo.getDescr()) {
            lprTransactionEntity.setDescr(new LprRichTextEntity(lprTransactionInfo.getDescr()));
        }

        List<LprTransactionItemEntity> lprTransItemEntities = new ArrayList<LprTransactionItemEntity>();

        for (LprTransactionItemInfo lprTransItemInfo : lprTransactionInfo.getLprTransactionItems()) {

            LprTransactionItemEntity lprTransItemEntity = new LprTransactionItemEntity(lprTransItemInfo);
            lprTransItemEntity.setId(UUIDHelper.genStringUUID());
            if (null != lprTransItemInfo.getStateKey()) {
                lprTransItemEntity.setLprTransactionItemState(stateDao.find(lprTransItemInfo.getStateKey()));
            }

            if (null != lprTransItemInfo.getTypeKey()) {
                lprTransItemEntity.setLprTransactionItemType(lprTypeDao.find(lprTransItemInfo.getTypeKey()));
            }
            if (null != lprTransItemInfo.getDescr()) {
                lprTransItemEntity.setDescr(new LprRichTextEntity(lprTransItemInfo.getDescr()));
            }
            lprTransItemDao.persist(lprTransItemEntity);
            lprTransItemEntities.add(lprTransItemEntity);

        }
        lprTransactionEntity.setLprTransactionItems(lprTransItemEntities);
        LprTransactionEntity existing = lprTransDao.find(lprTransactionEntity.getId());

        if (existing != null) {
            throw new AlreadyExistsException();
        }

        lprTransDao.persist(lprTransactionEntity);

        LprTransactionEntity retrived = lprTransDao.find(lprTransactionEntity.getId());

        LprTransactionInfo info = null;
        if (retrived != null) {
            info = retrived.toDto();
        }

        return info;

    }

    @Override
    @Transactional(readOnly = false)
    public LprTransactionInfo createLprTransactionFromExisting(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        LprTransactionEntity existingLprTransactionEntity = lprTransDao.find(lprTransactionId);
        LprTransactionEntity newLprTransactionEntity = new LprTransactionEntity();
        if (existingLprTransactionEntity != null) {
            newLprTransactionEntity.setId(UUIDHelper.genStringUUID());
            newLprTransactionEntity.setAttributes(existingLprTransactionEntity.getAttributes());
            newLprTransactionEntity.setDescr(existingLprTransactionEntity.getDescr());
            newLprTransactionEntity.setLprTransactionItems(existingLprTransactionEntity.getLprTransactionItems());
            newLprTransactionEntity.setLprTransState(stateDao
                    .find(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY));
            newLprTransactionEntity.setLprTransType(existingLprTransactionEntity.getLprTransType());
            newLprTransactionEntity.setRequestingPersonId(existingLprTransactionEntity.getRequestingPersonId());
            lprTransDao.persist(newLprTransactionEntity);

        } else {
            throw new DoesNotExistException("Could not find any LPR Transaction for id : " + lprTransactionId);
        }
        LprTransactionEntity retrived = lprTransDao.find(newLprTransactionEntity.getId());
        LprTransactionInfo info = null;
        if (retrived != null) {
            info = retrived.toDto();
        } else {
            throw new OperationFailedException("");
        }
        return info;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForPersonByLui(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateLprTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional
    public LprTransactionInfo processLprTransaction(String lprTransactionId, ContextInfo context)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LprTransactionInfo lprTransaction = getLprTransaction(lprTransactionId, context);

        for (LprTransactionItemInfo lprTransactionItemInfo : lprTransaction.getLprTransactionItems()) {
            LprTransactionItemResultInfo lprTransResultInfo = new LprTransactionItemResultInfo();
            if (lprTransactionItemInfo.getTypeKey()
                    .equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY)
                    || lprTransactionItemInfo.getTypeKey().equals(
                            LuiPersonRelationServiceConstants.LPRTRANS_ITEM_WAITLIST_TYPE_KEY)) {
                String lprCreated = createLprFromLprTransactionItem(lprTransactionItemInfo, context);

                lprTransResultInfo.setResultingLprId(lprCreated);
                lprTransResultInfo.setStatus("SUCCESS");

            } else if (lprTransactionItemInfo.getTypeKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_DROP_TYPE_KEY)) {
                /**TODO this needs to be implemented for drop to work, read below
                Be careful with the implementation here, because we DO NOT want to delete all lprs that are matched
                by lui, person, and state, but instead only the ones that are related to the registration group we
                are dropping.  It is VERY possible that a student could have the same course offering on their schedule
                twice with different reg groups, therefore deleting every LPR that matches that course offering id
                would be WRONG.  In addition, it is possible that 2 reg groups that point to the same activity offering
                could be on the same schedule (this is more unlikely however and may be prevented by the system), and
                deleting both of those LPRs would be incorrect.  So what we actually want to do is delete only lprs
                that have a direct relation to the reg group being dropped.  However, there is no easy way currently
                to link these things together, one possible route is to get the original transactions and use their group
                id somehow, but this route may be flawed if the there is more than one succeeded transaction for the same
                reg group (VERY possible).  There is no way currently (that I know of) to link the lprs for courseOffering,
                reg group, activities, and roster in a way that would be simple to determine by retrieving them from the
                db.  This may be a possible hole in the service/db design.
                */
                List<LuiPersonRelationInfo> toBeDroppedLPRs = getLprsByLuiPersonAndState(lprTransactionItemInfo.getPersonId(), lprTransactionItemInfo.getExistingLuiId(),
                        LuiPersonRelationServiceConstants.REGISTERED_STATE_KEY, context);

                if (toBeDroppedLPRs.size() > 1) {
                    throw new OperationFailedException("Multiple LuiPersonRelations between person:" + lprTransactionItemInfo.getPersonId() + " and lui:" + lprTransactionItemInfo.getExistingLuiId() +
                                    "; unimplemented functionality required to deal with this scenario is currentluy unimplemented");
                }
                for (LuiPersonRelationInfo lprInfo : toBeDroppedLPRs) {
                    // TODO - change state to LuiPersonRelationServiceConstants.DROPPED_STATE_KEY, rather than deleting
                    /* do this instead of delete
                    lprInfo.setStateKey(LuiPersonRelationServiceConstants.DROPPED_STATE_KEY);
                    try {
                        updateLpr(lprInfo.getId(), lprInfo, context);
                    } catch (ReadOnlyException e) {
                        throw new OperationFailedException("updateLpr() failure in processLprTransaction()", e);
                    }
                    */
                    deleteLpr(lprInfo.getId(), context);
                    lprTransResultInfo.setResultingLprId(lprInfo.getId());
                }
                lprTransResultInfo.setStatus("SUCCESS");

            } else if (lprTransactionItemInfo.getTypeKey().equals(
                    LuiPersonRelationServiceConstants.LPRTRANS_ITEM_SWAP_TYPE_KEY)) {

                List<LuiPersonRelationInfo> toBeDroppedLPRs = getLprsByLuiPersonAndState(lprTransactionItemInfo.getPersonId(), lprTransactionItemInfo.getExistingLuiId(),
                        LuiPersonRelationServiceConstants.REGISTERED_STATE_KEY, context);
                if (toBeDroppedLPRs.size() > 1) {
                    throw new OperationFailedException("Multiple LuiPersonRelations between person:" + lprTransactionItemInfo.getPersonId() + " and lui:" + lprTransactionItemInfo.getExistingLuiId() +
                            "; unimplemented functionality required to deal with this scenario is currentluy unimplemented");
                }
                for (LuiPersonRelationInfo lprInfo : toBeDroppedLPRs) {
                    // TODO - change state to LuiPersonRelationServiceConstants.DROPPED_STATE_KEY, rather than deleting
                    /* do this instead of delete
                    lprInfo.setStateKey(LuiPersonRelationServiceConstants.DROPPED_STATE_KEY);
                    try {
                        updateLpr(lprInfo.getId(), lprInfo, context);
                    } catch (ReadOnlyException e) {
                        throw new OperationFailedException("updateLpr() failure in processLprTransaction()", e);
                    }
                    */
                    deleteLpr(lprInfo.getId(), context);
                    String lprCreated = createLprFromLprTransactionItem(lprTransactionItemInfo, context);
                    lprTransResultInfo.setResultingLprId(lprCreated);
                }
            } else {

                throw new OperationFailedException("The LPR Transaction Item did not have one of the supported type ");
            }
            lprTransactionItemInfo.setLprTransactionItemResult(lprTransResultInfo);
        }
        return lprTransaction;
    }

    @Override
    public List<String> getLprTransactionIdsForPerson(String personId, List<String> lprTypes, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsByIdList(List<String> lprIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForPersonByAtp(String atpKey, String personId,
            List<String> lprTypes, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LprTransactionItemEntity> lprTransItems = lprTransItemDao.getLprTransactionItemByPerson(personId);

        List<LprTransactionItemInfo> lprTransItemInfos = new ArrayList<LprTransactionItemInfo>();

        for (LprTransactionItemEntity lprTransItem : lprTransItems) {
            LuiInfo lui = luiDao.find(lprTransItem.getNewLuiId()).toDto();
            if (lui.getAtpKey().equals(atpKey)) {
                lprTransItemInfos.add(lprTransItem.toDto());
            }
        }

        return new ArrayList<LprTransactionInfo>();
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForLpr(String lprId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<LprTransactionItemEntity> lprTransItems = lprTransItemDao.getLprTransactionItemByLpr(lprId);
        List<LprTransactionEntity> lprTrans = new ArrayList<LprTransactionEntity>();
        for (LprTransactionItemEntity lprTransItem : lprTransItems) {
            lprTransItems.add(lprTransItemDao.find(lprTransItem.getId()));
        }
        List<LprTransactionInfo> lprTransInfos = new ArrayList<LprTransactionInfo>();
        for (LprTransactionEntity lprTransEntity : lprTrans) {
            lprTransInfos.add(lprTransEntity.toDto());
        }
        return lprTransInfos;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForLui(String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Transactional(readOnly = false)
    @Override
    public LprTransactionInfo updateLprTransaction(String lprTransactionId, LprTransactionInfo lprTransactionInfo,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        LprTransactionEntity lprTrans = lprTransDao.find(lprTransactionId);
        List<LprTransactionItemEntity> lprTransItemEntityList = new ArrayList<LprTransactionItemEntity>();
        if (null != lprTrans) {
            LprTransactionEntity modifiedLprTrans = new LprTransactionEntity(lprTransactionInfo);
            if (lprTransactionInfo.getStateKey() != null)
                modifiedLprTrans.setLprTransState(stateDao.find(lprTransactionInfo.getStateKey()));
            if (lprTransactionInfo.getTypeKey() != null)
                modifiedLprTrans.setLprTransType(lprTypeDao.find(lprTransactionInfo.getTypeKey()));
            
            if(lprTransactionInfo.getLprTransactionItems()!=null){
                for(LprTransactionItemInfo lprItemInfo: lprTransactionInfo.getLprTransactionItems()){
                    LprTransactionItemEntity lprItemEntity =  new LprTransactionItemEntity(lprItemInfo);
                    
                    if (null != lprItemInfo.getStateKey()) {
                        lprItemEntity.setLprTransactionItemState(stateDao.find(lprItemInfo.getStateKey()));
                    }

                    if (null != lprItemInfo.getTypeKey()) {
                        lprItemEntity.setLprTransactionItemType(lprTypeDao.find(lprItemInfo.getTypeKey()));
                    }
                    if (null != lprItemInfo.getDescr()) {
                        lprItemEntity.setDescr(new LprRichTextEntity(lprItemInfo.getDescr()));
                    }
                    lprTransItemDao.merge(lprItemEntity);
                    lprTransItemEntityList.add(lprTransItemDao.find(lprItemEntity.getId()));
                    
                }
            }
            
            lprTransDao.merge(modifiedLprTrans);
            LprTransactionEntity lprTransToReturn =  lprTransDao.find(modifiedLprTrans.getId());
            lprTransToReturn.setLprTransactionItems(lprTransItemEntityList);
            return lprTransToReturn.toDto();
            

        } else
            throw new DoesNotExistException(lprTransactionId);
    }

    @Override
    public List<LprTransactionInfo> searchForLprTransactions(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForLprTransactionIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprRosterInfo> searchForLprRosters(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForLprRosterIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonAndTypeForAtp(String personId, String atpKey, String typeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<LuiPersonRelationEntity> entityList = lprDao.getLprsByPersonAndType(personId, typeKey);
        List<LuiPersonRelationInfo> infoList = new ArrayList<LuiPersonRelationInfo>();
        for (LuiPersonRelationEntity entity : entityList) {
            LuiEntity lui = luiDao.find(entity.getLuiId());
            if (StringUtils.equals(lui.getAtpKey(), atpKey)) {
                infoList.add(entity.toDto());
            }
        }

        return infoList;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndLuiType(String personId, String atpKey,
            String luiTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationEntity> entityList = lprDao.getLprsByPerson(personId);
        
        List<LuiPersonRelationInfo> infoList = new ArrayList<LuiPersonRelationInfo>();
        for (LuiPersonRelationEntity entity : entityList) {
            LuiEntity lui = luiDao.find(entity.getLuiId());
            if ((lui.getAtpKey().equals( atpKey)) && (lui.getLuiType().equals(luiTypeKey) ) ) {
                infoList.add(entity.toDto());
            }
        }
        
        return infoList;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndPersonType(String personId, String atpKey,
            String personTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByTypeAndLui(String typeKey, String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}