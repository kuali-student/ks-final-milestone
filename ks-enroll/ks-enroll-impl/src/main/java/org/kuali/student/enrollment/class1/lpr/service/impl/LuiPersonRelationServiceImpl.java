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

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class1.lpr.dao.LprDao;
import org.kuali.student.enrollment.class1.lpr.dao.LprStateDao;
import org.kuali.student.enrollment.class1.lpr.dao.LprTransactionDao;
import org.kuali.student.enrollment.class1.lpr.dao.LprTypeDao;
import org.kuali.student.enrollment.class1.lpr.model.LprRichTextEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprTransactionEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprTransactionItemEntity;
import org.kuali.student.enrollment.class1.lpr.model.LuiPersonRelationEntity;
import org.kuali.student.enrollment.lpr.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemResultInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
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
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author sambit
 */
@WebService(name = "LuiPersonRelationService", serviceName = "LuiPersonRelationService", portName = "LuiPersonRelationService", targetNamespace = "http://student.kuali.org/wsdl/lpr")
@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class LuiPersonRelationServiceImpl implements LuiPersonRelationService {

    private LprDao lprDao;
    private LprTransactionDao lprTransDao;
    private LprStateDao lprStateDao;
    private LprTypeDao lprTypeDao;
    private LuiService luiService;

    private LuiPersonRelationInfo getLprsByLuiPersonAndState(String personId, String luiId, String stateKey,
            ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    private LuiPersonRelationEntity toCluForCreate(LuiPersonRelationInfo luiPersonRelationInfo) {
        LuiPersonRelationEntity lpr = new LuiPersonRelationEntity(luiPersonRelationInfo);
        if (null != luiPersonRelationInfo.getStateKey()) {
            lpr.setPersonRelationState(lprStateDao.find(luiPersonRelationInfo.getStateKey()));
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
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
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
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByLuiAndPerson(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
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
    @Transactional(readOnly = false)
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
    public LuiPersonRelationInfo updateLpr(String luiPersonRelationId, LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            ReadOnlyException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional
    public StatusInfo deleteLpr(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        _checkForMissingParameter(luiPersonRelationId, "luiPersonRelationId");

        LuiPersonRelationEntity lprEntity = lprDao.find(luiPersonRelationId);
        if (null != lprEntity) {
            lprDao.remove(lprEntity);
        } else {
            throw new DoesNotExistException("LPR entity does not exist for id '" + luiPersonRelationId
                    + "'; cannot delete");
        }

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

    public void setLprDao(LprDao lprDao) {
        this.lprDao = lprDao;
    }

    public void setLprStateDao(LprStateDao lprStateDao) {
        this.lprStateDao = lprStateDao;
    }

    public void setLprTypeDao(LprTypeDao lprTypeDao) {
        this.lprTypeDao = lprTypeDao;
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
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
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
    public String createLprRoster(LprRosterInfo lprRosterInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteLprRoster(String lprRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprRosterEntryInfo> getEntriesForLprRoster(String lprRosterId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLuiAndRosterType(String luiId, String lprRosterTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLui(String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiInfo> getLprRoster(String lprRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String createLprRosterEntry(LprRosterEntryInfo lprRosterEntryInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteLprRosterEntry(String lprRosterEntryId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
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
    @Transactional
    public LprTransactionInfo createLprTransaction(LprTransactionInfo lprTransactionInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        LprTransactionEntity lprTransactionEntity = new LprTransactionEntity(lprTransactionInfo);
        if (null != lprTransactionInfo.getStateKey()) {
            lprTransactionEntity.setLprTransState(lprStateDao.find(lprTransactionInfo.getStateKey()));
        }

        if (null != lprTransactionInfo.getTypeKey()) {
            lprTransactionEntity.setLprTransType(lprTypeDao.find(lprTransactionInfo.getTypeKey()));
        }
        if (null != lprTransactionInfo.getDescr()) {
            lprTransactionEntity.setDescr(new LprRichTextEntity(lprTransactionInfo.getDescr()));
        }

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
    public LprTransactionInfo createLprTransactionFromExisting(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        LprTransactionEntity existingLprTransactionEntity = lprTransDao.find(lprTransactionId);
        LprTransactionEntity newLprTransactionEntity = new LprTransactionEntity();
        if (existingLprTransactionEntity != null) {
            newLprTransactionEntity.setId(String.valueOf(Math.random()));
            newLprTransactionEntity.setAttributes(existingLprTransactionEntity.getAttributes());
            newLprTransactionEntity.setDescr(existingLprTransactionEntity.getDescr());
            newLprTransactionEntity.setLprTransactionItems(existingLprTransactionEntity.getLprTransactionItems());
            newLprTransactionEntity.setLprTransState(lprStateDao
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
                    .equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY)) {
                String lprCreated = createLprFromLprTransactionItem(lprTransactionItemInfo, context);

                lprTransResultInfo.setResultingLprId(lprCreated);
                lprTransResultInfo.setStatus("SUCCESS");

            } else if (lprTransactionItemInfo.getTypeKey().equals(
                    LuiPersonRelationServiceConstants.LPRTRANS_ITEM_DROP_TYPE_KEY)) {
                LuiPersonRelationInfo toBeDroppedLPR = getLprsByLuiPersonAndState(lprTransactionItemInfo.getPersonId(),
                        lprTransactionItemInfo.getExistingLuiId(),
                        LuiPersonRelationServiceConstants.REGISTERED_STATE_KEY, context);

                deleteLprTransaction(toBeDroppedLPR.getId(), context);

            } else if (lprTransactionItemInfo.getTypeKey().equals(
                    LuiPersonRelationServiceConstants.LPRTRANS_ITEM_SWAP_TYPE_KEY)) {

                LuiPersonRelationInfo toBeDroppedLPR = getLprsByLuiPersonAndState(lprTransactionItemInfo.getPersonId(),
                        lprTransactionItemInfo.getExistingLuiId(),
                        LuiPersonRelationServiceConstants.REGISTERED_STATE_KEY, context);
                deleteLprTransaction(toBeDroppedLPR.getId(), context);
                String lprCreated = createLprFromLprTransactionItem(lprTransactionItemInfo, context);
                lprTransResultInfo.setResultingLprId(lprCreated);
                lprTransResultInfo.setStatus("SUCCESS");

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

        List<LprTransactionItemEntity> lprTransItems = lprTransDao.getLprTransactionItemByPerson(personId);

        List<LprTransactionInfo> lprTrans = new ArrayList<LprTransactionInfo>();

        for (LprTransactionItemEntity lprTransItem : lprTransItems) {
            LuiInfo lui = luiService.getLui(lprTransItem.getNewLuiId(), context);
            if (lui.getAtpKey().equals(atpKey)) {
                lprTrans.add(lprTransItem.getLprTransactionEntity().toDto());
            }
        }

        return lprTrans;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForLpr(String lprId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<LprTransactionItemEntity> lprTransItems = lprTransDao.getLprTransactionItemByLpr(lprId);
        List<LprTransactionEntity> lprTrans = new ArrayList<LprTransactionEntity>();
        for (LprTransactionItemEntity lprTransItem : lprTransItems) {
            lprTrans.addAll(lprTransDao.getByLprTransactionItemId(lprTransItem.getId()));
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

    @Override
    public LprTransactionInfo updateLprTransaction(String lprTransactionId, LprTransactionInfo lprTransactionInfo,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        LprTransactionEntity lprTrans = lprTransDao.find(lprTransactionId);

        if (null != lprTrans) {
            LprTransactionEntity modifiedLprTrans = new LprTransactionEntity(lprTransactionInfo);
            if (lprTransactionInfo.getStateKey() != null)
                modifiedLprTrans.setLprTransState(lprStateDao.find(lprTransactionInfo.getStateKey()));
            if (lprTransactionInfo.getTypeKey() != null)
                modifiedLprTrans.setLprTransType(lprTypeDao.find(lprTransactionInfo.getTypeKey()));
            lprTransDao.merge(modifiedLprTrans);

            return lprTransDao.find(modifiedLprTrans.getId()).toDto();

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
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndLuiType(String personId, String atpKey,
            String luiTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndPersonType(String personId, String atpKey,
            String personTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}