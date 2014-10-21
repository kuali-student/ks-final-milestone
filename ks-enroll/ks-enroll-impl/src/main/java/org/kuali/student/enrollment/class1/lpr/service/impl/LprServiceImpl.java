/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.class1.lpr.dao.LprDao;
import org.kuali.student.enrollment.class1.lpr.dao.LprTransactionDao;
import org.kuali.student.enrollment.class1.lpr.dao.LprTransactionItemDao;
import org.kuali.student.enrollment.class1.lpr.model.LprEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprTransactionEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprTransactionItemEntity;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
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
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sambit
 */
//@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class LprServiceImpl implements LprService {

    public static final Logger LOGGER = LoggerFactory.getLogger(LprServiceImpl.class);

    private LprDao lprDao;
    private LprTransactionDao lprTransactionDao;
    private LprTransactionItemDao lprTransactionItemDao;

    private List<LprInfo> getLprsByLuiPersonAndState(String personId, String luiId, String stateKey, ContextInfo context) throws
            DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<LprInfo> lprs = this.getLprsByPersonAndLui(personId, luiId, context);
        List<LprInfo> list = new ArrayList<LprInfo>(lprs.size());
        for (LprInfo lpr : lprs) {
            if (lpr.getStateKey().equals(stateKey)) {
                list.add(lpr);
            }
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LprInfo> getLprsByLui(String luiId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LprEntity> luiPersonRelations = lprDao.getByLuiId(luiId);
        List<LprInfo> dtos = new ArrayList<LprInfo>();
        for (LprEntity entity : luiPersonRelations) {
            dtos.add(entity.toDto());
        }
        return dtos;
    }


    @Override
    public List<LprInfo> getLprsByLuis(
            List<String> luiIds,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<LprEntity> entityList = lprDao.getLprsByLuis(luiIds);
        List<LprInfo> infoList = new ArrayList<LprInfo>();

        for (LprEntity lprEntity : entityList) {
            infoList.add(lprEntity.toDto());
        }

        return infoList;
    }

    @Override
    @Transactional
    public List<BulkStatusInfo> createLprsForPerson(String personId,
                                                    String lprTypeKey,
                                                    List<LprInfo> lprInfos,
                                                    ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        List<BulkStatusInfo> bulkStatusInfoList = new ArrayList<BulkStatusInfo>();

        /*
        * This is an intentionally simple implementation.
        *
        * Later we can manage the transactions ourselves and use a paging approach.
        */

        for (LprInfo lprInfo : lprInfos) {

            BulkStatusInfo bsi = new BulkStatusInfo();

            try {
                LprInfo created = this.createLpr(personId, lprInfo.getLuiId(), lprTypeKey, lprInfo, contextInfo);
                bsi.setSuccess(Boolean.TRUE);
                bsi.setId(created.getId());
            } catch (DataValidationErrorException de) {
                bsi.setSuccess(Boolean.FALSE);
                bsi.setMessage(ValidationUtils.asString(de.getValidationResults()));
            }
        }


        return bulkStatusInfoList;
    }

    @Override
    @Transactional(readOnly = true)
    public LprInfo getLpr(String lprId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        LprEntity lpr = lprDao.find(lprId);

        if (lpr == null)
            throw new DoesNotExistException("No LprEntity for id = " + lprId);

        // else
        return lpr.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LprInfo> getLprsByIds(List<String> luiPersonRelationIds, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LprInfo> lprInfos = new ArrayList<LprInfo>();
        List<LprEntity> lprEntities = lprDao.findByIds(luiPersonRelationIds);
        for (LprEntity lprEntity : lprEntities) {
            LprInfo lprInfo = lprEntity.toDto();
            lprInfos.add(lprInfo);
        }
        return lprInfos;
    }

    @Override
    public List<String> getLuiIdsByPersonAndTypeAndState(String personId, String luiPersonRelationType, String relationState, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return this.lprDao.getLuiIdsByPersonAndTypeAndState(personId, luiPersonRelationType, relationState);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getPersonIdsByLuiAndTypeAndState(String luiId, String luiPersonRelationType, String relationState, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> returnVals = new ArrayList<String>();

        returnVals.addAll(lprDao.getPersonIdsByLui(luiId, luiPersonRelationType, relationState));
        return returnVals;

    }

    @Override
    @Transactional(readOnly = true)
    public List<LprInfo> getLprsByPersonAndLui(String personId, String luiId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LprEntity> entityList = lprDao.getLprByLuiAndPerson(personId, luiId);

        List<LprInfo> infoList = new ArrayList<LprInfo>();

        if (entityList != null && !entityList.isEmpty()) {
            for (LprEntity entity : entityList) {
                infoList.add(entity.toDto());
            }

        }
        return infoList;

    }

    @Override
    public List<LprInfo> getLprsByMasterLprId(String masterLprId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        List<LprEntity> entityList = lprDao.getLprsByMasterLprId(masterLprId);

        List<LprInfo> infoList = new ArrayList<LprInfo>();

        if (entityList != null && !entityList.isEmpty()) {
            for (LprEntity entity : entityList) {
                infoList.add(entity.toDto());
            }

        }
        return infoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LprInfo> getLprsByPerson(String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LprEntity> entityList = lprDao.getLprsByPerson(personId);

        List<LprInfo> infoList = new ArrayList<LprInfo>();
        if (entityList != null && !entityList.isEmpty()) {
            for (LprEntity entity : entityList) {
                infoList.add(entity.toDto());
            }

        }
        return infoList;
    }

    @Override
    public List<String> searchForLprIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        GenericQueryResults<String> results = criteriaLookupService.lookupIds(LprEntity.class, criteria);
        return results.getResults();
    }

    @Override
    @Transactional
    public LprInfo createLpr(String personId, String luiId, String lprTypeKey, LprInfo lprInfo, ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {

        if (!personId.equals(lprInfo.getPersonId())) {
            throw new InvalidParameterException(personId + " does not match the personId in the info object " + lprInfo.getPersonId());
        }
        if (!luiId.equals(lprInfo.getLuiId())) {
            throw new InvalidParameterException(luiId + " does not match the luiId in the info object " + lprInfo.getLuiId());
        }
        if (!lprTypeKey.equals(lprInfo.getTypeKey())) {
            throw new InvalidParameterException(lprTypeKey + " does not match the lprType in the info object " + lprInfo.getTypeKey());
        }

        // make sure params are consistent with lprInfo:
        lprInfo.setPersonId(personId);
        lprInfo.setLuiId(luiId);
        lprInfo.setTypeKey(lprTypeKey);

        LprEntity lpr = new LprEntity(lprInfo);

        lpr.setEntityCreated(context);

        lprDao.persist(lpr);

        lprDao.getEm().flush();

        return lpr.toDto();
    }

    @Override
    @Transactional
    public LprInfo updateLpr(String lprId, LprInfo lprInfo, ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException,
            OperationFailedException, PermissionDeniedException {

        if (!lprId.equals(lprInfo.getId())) {
            throw new InvalidParameterException(lprId + " does not match the id in the info object " + lprInfo.getId());
        }


        LprEntity lprEntity = lprDao.find(lprId);

        if (lprEntity != null) {


            lprEntity.fromDto(lprInfo);

            if (lprInfo.getStateKey() != null) {
                lprEntity.setPersonRelationStateId(lprInfo.getStateKey());
            }

            if (lprInfo.getTypeKey() != null) {
                lprEntity.setPersonRelationTypeId(lprInfo.getTypeKey());
            }

            if (lprInfo.getPersonId() != null) {
                lprEntity.setPersonId(lprInfo.getPersonId());
            }

            lprEntity.setEntityUpdated(contextInfo);

            try {
                lprEntity = lprDao.merge(lprEntity);
            } catch (VersionMismatchException e) {
                // TODO KSENROLL-9296 remove this exception translation once 
                // version mismatch exception is added to the updateLpr method 
                throw new OperationFailedException("Version Mismatch", e);
            }

            lprDao.getEm().flush();

            return lprEntity.toDto();
        } else {
            throw new DoesNotExistException(lprId);
        }
    }

    @Override
    @Transactional
    public StatusInfo deleteLpr(String lprId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LprEntity lprEntity = lprDao.find(lprId);

        if (lprEntity == null) {
            throw new DoesNotExistException("No LprEntity for id = " + lprId);
        }

        lprDao.remove(lprEntity);

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public List<LprInfo> searchForLprs(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LprInfo> resultList = new ArrayList<LprInfo>();
        GenericQueryResults<LprEntity> results = criteriaLookupService.lookup(LprEntity.class, criteria);

        if(results != null){
            for(LprEntity lprEntity : results.getResults()){
                resultList.add(lprEntity.toDto());
            }
        }

        return resultList;
    }


    @Override
    @Transactional(readOnly = true)
    public LprTransactionInfo getLprTransaction(String lprTransactionId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        LprTransactionEntity transactionEntity = lprTransactionDao.find(lprTransactionId);

        if (transactionEntity == null)
            throw new DoesNotExistException("No LprTransactionEntity for id = " + lprTransactionId);

        return transactionEntity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public LprTransactionItemInfo getLprTransactionItem(String lprTransactionItemId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        LprTransactionItemEntity transactionItemEntity = lprTransactionItemDao.find(lprTransactionItemId);

        if (transactionItemEntity == null)
            throw new DoesNotExistException("No LprTransactionItemEntity for id = " + transactionItemEntity);

        return transactionItemEntity.toDto();
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItemsByIds(@WebParam(name = "lprTransactionItemIds") List<String> lprTransactionItemIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<String> getLprTransactionItemsByType(@WebParam(name = "lprTransactionItemTypeKey") String lprTransactionItemTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<String> searchForLprTransactionItemIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<LprTransactionItemInfo> searchForLprTransactionItems(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ValidationResultInfo> validateLprTransactionItem(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "lprTransactionItemTypeKey") String lprTransactionItemTypeKey, @WebParam(name = "lprTransactionItem") LprTransactionItemInfo lprTransactionItem, @WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("unimplemented");
    }

    @Override
    @Transactional
    public LprTransactionItemInfo changeLprTransactionItem(@WebParam(name = "lprTransactionItemId") String lprTransactionItemId, @WebParam(name = "lprTransactionItemInfo") LprTransactionItemInfo lprTransactionItemInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        LprTransactionItemEntity lprTransItem = lprTransactionItemDao.find(lprTransactionItemId);

        if (null != lprTransItem) {

            lprTransItem.fromDto(lprTransactionItemInfo);

            if (null != lprTransactionItemInfo.getStateKey()) {
                lprTransItem.setLprTransactionItemState(lprTransactionItemInfo.getStateKey());
            }

            lprTransItem.setEntityUpdated(contextInfo);

            try {
                lprTransItem = lprTransactionItemDao.merge(lprTransItem);
            } catch (VersionMismatchException e) {
                // TODO KSENROLL-9296 remove this exception translation once
                // version mismatch exception is added to the updateLprTransaction method
                throw new OperationFailedException("Version Mismatch", e);
            }

            lprTransactionItemDao.getEm().flush();

            return lprTransItem.toDto();

        } else {
            throw new DoesNotExistException(lprTransactionItemId);
        }
    }

    @Override
    @Transactional
    public StatusInfo deleteLprTransaction(String lprTransactionId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StatusInfo status = new StatusInfo();

        LprTransactionEntity lprTrans = lprTransactionDao.find(lprTransactionId);
        if (null != lprTrans) {

            lprTransactionDao.remove(lprTrans);

            status.setSuccess(Boolean.TRUE);

        } else {
            status.setSuccess(Boolean.FALSE);
        }

        return status;
    }


    @Override
    @Transactional
    public LprTransactionInfo createLprTransaction(String lprTransactionType, LprTransactionInfo lprTransactionInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        if (!lprTransactionType.equals(lprTransactionInfo.getTypeKey())) {
            throw new InvalidParameterException(lprTransactionType + " does not match the typeKey in the info object " + lprTransactionInfo.getTypeKey());
        }

        LprTransactionEntity lprTransactionEntity = new LprTransactionEntity(lprTransactionInfo);


        if (lprTransactionEntity.getId() == null) {
            lprTransactionEntity.setId(UUIDHelper.genStringUUID());
        }
        if (null != lprTransactionInfo.getStateKey()) {
            lprTransactionEntity.setLprTransState(lprTransactionInfo.getStateKey());
        }

        if (null != lprTransactionInfo.getTypeKey()) {
            lprTransactionEntity.setLprTransType(lprTransactionInfo.getTypeKey());
        }
        if (null != lprTransactionInfo.getDescr()) {
            RichTextInfo descr = lprTransactionInfo.getDescr();

            lprTransactionEntity.setDescrFormatted(descr.getFormatted());
            lprTransactionEntity.setDescrPlain(descr.getPlain());
        }

        Set<LprTransactionItemEntity> lprTransItemEntities = new HashSet<LprTransactionItemEntity>();


        for (LprTransactionItemInfo lprTransItemInfo : lprTransactionInfo.getLprTransactionItems()) {

            LprTransactionItemEntity lprTransItemEntity = createLprTransactionItem(lprTransItemInfo, lprTransactionEntity, context);

            lprTransItemEntities.add(lprTransItemEntity);
        }

        lprTransactionEntity.setLprTransactionItems(lprTransItemEntities);

        lprTransactionEntity.setEntityCreated(context);

        lprTransactionDao.persist(lprTransactionEntity);

        lprTransactionDao.getEm().flush();

        return lprTransactionEntity.toDto();

    }

    @Override
    @Transactional
    public LprTransactionInfo createLprTransactionFromExisting(String lprTransactionId, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        LprTransactionEntity existingLprTransactionEntity = lprTransactionDao.find(lprTransactionId);
        LprTransactionEntity newLprTransactionEntity = new LprTransactionEntity();
        if (existingLprTransactionEntity != null) {
            newLprTransactionEntity.setId(UUIDHelper.genStringUUID());
            newLprTransactionEntity.setAttributes(existingLprTransactionEntity.getAttributes());
            newLprTransactionEntity.setDescrFormatted(existingLprTransactionEntity.getDescrFormatted());
            newLprTransactionEntity.setDescrPlain(existingLprTransactionEntity.getDescrPlain());
            Set<LprTransactionItemEntity> newItems = new HashSet<LprTransactionItemEntity>(existingLprTransactionEntity.getLprTransactionItems().size());
            for (LprTransactionItemEntity existingItem : existingLprTransactionEntity.getLprTransactionItems()) {
                LprTransactionItemEntity newItem = new LprTransactionItemEntity();
                newItem.setId(UUIDHelper.genStringUUID());
                newItem.setExistingLuiId(lprTransactionId);
                newItem.setLprTransactionItemState(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
                newItem.setLprTransactionItemType(existingItem.getLprTransactionItemType());
                newItem.setNewLuiId(existingItem.getNewLuiId());
                newItem.setPersonId(existingItem.getPersonId());
                newItem.setDescrFormatted(existingItem.getDescrFormatted());
                newItem.setDescrPlain(existingItem.getDescrPlain());

            }
            newLprTransactionEntity.setLprTransactionItems(newItems);
            newLprTransactionEntity.setLprTransState(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
            newLprTransactionEntity.setLprTransType(existingLprTransactionEntity.getLprTransType());
            newLprTransactionEntity.setRequestingPersonId(existingLprTransactionEntity.getRequestingPersonId());
            lprTransactionDao.persist(newLprTransactionEntity);

            lprTransactionDao.getEm().flush();

            return newLprTransactionEntity.toDto();

        } else {
            throw new DoesNotExistException("Could not find any LPR Transaction for id : " + lprTransactionId);
        }
    }

    @Override
    @Transactional
    public LprTransactionInfo processLprTransaction(String lprTransactionId, ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        LprTransactionInfo lprTransaction = getLprTransaction(lprTransactionId, context);

        for (LprTransactionItemInfo lprTransactionItemInfo : lprTransaction.getLprTransactionItems()) {
            if (lprTransactionItemInfo.getTypeKey().equals(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY) ||
                lprTransactionItemInfo.getTypeKey().equals(LprServiceConstants.REQ_ITEM_ADD_TO_WAITLIST_TYPE_KEY)) {
                /*
                // TODO Mezba - the method createLprFromLprTransactionItem is no longer there, decide what to do
                String lprCreated = createLprFromLprTransactionItem(lprTransactionItemInfo, context);
                
                lprTransResultInfo.setResultingLprId(lprCreated);
                 */
                throw new UnsupportedOperationException("Empty If Statement: No action defined for this method. Get Mezba to fix");
            } else if (lprTransactionItemInfo.getTypeKey().equals(LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY)) {
                /**
                 * TODO this needs to be implemented for drop to work, read below Be careful with the implementation here,
                 * because we DO NOT want to delete all lprs that are matched by lui, person, and state, but instead only the
                 * ones that are related to the registration group we are dropping. It is VERY possible that a student could
                 * have the same course offering on their schedule twice with different reg groups, therefore deleting every
                 * LPR that matches that course offering id would be WRONG. In addition, it is possible that 2 reg groups
                 * that point to the same activity offering could be on the same schedule (this is more unlikely however and
                 * may be prevented by the system), and deleting both of those LPRs would be incorrect. So what we actually
                 * want to do is delete only lprs that have a direct relation to the reg group being dropped. However, there
                 * is no easy way currently to link these things together, one possible route is to get the original
                 * transactions and use their group id somehow, but this route may be flawed if the there is more than one
                 * succeeded transaction for the same reg group (VERY possible). There is no way currently (that I know of)
                 * to link the lprs for courseOffering, reg group, activities, and roster in a way that would be simple to
                 * determine by retrieving them from the db. This may be a possible hole in the service/db design.
                 */
                List<LprInfo> toBeDroppedLPRs;
                try {
                    toBeDroppedLPRs = getLprsByLuiPersonAndState(lprTransactionItemInfo.getPersonId(),
                            lprTransactionItemInfo.getExistingLprId(),
                            LprServiceConstants.ACTIVE_STATE_KEY, context);
                } catch (DisabledIdentifierException ex) {
                    throw new OperationFailedException("unexpected", ex);
                }

                if (toBeDroppedLPRs.size() > 1) {
                    throw new OperationFailedException("Multiple LuiPersonRelations between person:" +
                            lprTransactionItemInfo.getPersonId() + " and lpr:" + lprTransactionItemInfo.getExistingLprId() +
                            "; unimplemented functionality required to deal with this scenario is currentluy unimplemented");
                }
                for (LprInfo lprInfo : toBeDroppedLPRs) {
                    // TODO - change state to LprServiceConstants.DROPPED_STATE_KEY, rather than deleting
                    /*
                     * do this instead of delete lprInfo.setStateKey(LprServiceConstants.DROPPED_STATE_KEY);
                     * try { updateLpr(lprInfo.getId(), lprInfo, context); } catch (ReadOnlyException e) { throw new
                     * OperationFailedException("updateLpr() failure in processLprTransaction()", e); }
                     */
                    deleteLpr(lprInfo.getId(), context);
                    lprTransactionItemInfo.setResultingLprId(lprInfo.getId());
                }

            } else if (lprTransactionItemInfo.getTypeKey().equals(LprServiceConstants.REQ_ITEM_SWAP_TYPE_KEY)) {

                List<LprInfo> toBeDroppedLPRs;
                try {
                    toBeDroppedLPRs = getLprsByLuiPersonAndState(lprTransactionItemInfo.getPersonId(),
                            lprTransactionItemInfo.getExistingLprId(), LprServiceConstants.ACTIVE_STATE_KEY, context);
                } catch (DisabledIdentifierException ex) {
                    throw new OperationFailedException("unexpected", ex);
                }
                if (toBeDroppedLPRs.size() > 1) {
                    throw new OperationFailedException("Multiple LuiPersonRelations between person:" +
                            lprTransactionItemInfo.getPersonId() + " and lpr:" + lprTransactionItemInfo.getExistingLprId() +
                            "; unimplemented functionality required to deal with this scenario is currentluy unimplemented");
                }
                for (LprInfo lprInfo : toBeDroppedLPRs) {
                    // TODO - change state to LprServiceConstants.DROPPED_STATE_KEY, rather than deleting
                    /*
                     * do this instead of delete lprInfo.setStateKey(LprServiceConstants.DROPPED_STATE_KEY);
                     * try { updateLpr(lprInfo.getId(), lprInfo, context); } catch (ReadOnlyException e) { throw new
                     * OperationFailedException("updateLpr() failure in processLprTransaction()", e); }
                     */
                    deleteLpr(lprInfo.getId(), context);
                    /*
                    // TODO Mezba - the method createLprFromLprTransactionItem is no longer there, decide what to do
                    
                    String lprCreated = createLprFromLprTransactionItem(lprTransactionItemInfo, context);
                    
                    lprTransResultInfo.setResultingLprId(lprCreated);
                     */
                }
            } else {

                throw new OperationFailedException("The LPR Transaction Item did not have one of the supported type ");
            }
            lprTransactionItemInfo.setStateKey(LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY);

        }
        try {
            lprTransaction.setStateKey(LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY);
            updateLprTransaction(lprTransactionId, lprTransaction, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException(ex.getMessage());
        }
        return lprTransaction;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsByIds(List<String> lprIds, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Operation not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<LprTransactionItemInfo> getLprTransactionItemsByResultingLpr(String lprId, ContextInfo context) throws
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LprTransactionItemEntity> lprTransItems = lprTransactionItemDao.getLprTransactionItemsByLpr(lprId);
        List<LprTransactionEntity> lprTrans = new ArrayList<LprTransactionEntity>();
        for (LprTransactionItemEntity lprTransItem : lprTransItems) {

            lprTrans.add(lprTransactionDao.getByLprTransactionItemId(lprTransItem.getId()));
        }
        List<LprTransactionInfo> lprTransInfos = new ArrayList<LprTransactionInfo>();

        for (LprTransactionEntity lprTransEntity : lprTrans) {
            lprTransInfos.add(lprTransEntity.toDto());
        }
        // return lprTransInfos;
        throw new UnsupportedOperationException("Operation not implemented");
    }


    @Override
    @Transactional
    public LprTransactionInfo updateLprTransaction(String lprTransactionId, LprTransactionInfo lprTransactionInfo, ContextInfo context) throws
            DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LprTransactionEntity lprTrans = lprTransactionDao.find(lprTransactionId);

        if (null != lprTrans) {

            lprTrans.fromDto(lprTransactionInfo);

            if (lprTransactionInfo.getStateKey() != null) {
                lprTrans.setLprTransState(lprTransactionInfo.getStateKey());
            }
            if (lprTransactionInfo.getTypeKey() != null) {
                lprTrans.setLprTransType(lprTransactionInfo.getTypeKey());
            }

            lprTrans.setEntityUpdated(context);

            try {
                lprTrans = lprTransactionDao.merge(lprTrans);
            } catch (VersionMismatchException e) {
                // TODO KSENROLL-9296 remove this exception translation once 
                // version mismatch exception is added to the updateLprTransaction method 
                throw new OperationFailedException("Version Mismatch", e);
            }

            lprTransactionDao.getEm().flush();

            return lprTrans.toDto();

        } else {
            throw new DoesNotExistException(lprTransactionId);
        }
    }

//    @Transactional
//    @Override
//    public LprTransactionItemInfo updateLprTransactionItem(String lprTransactionItemId, LprTransactionItemInfo lprTransactionItemInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
//        LprTransactionItemEntity lprTransItem = lprTransactionItemDao.find(lprTransactionItemId);
//
//        if (null != lprTransItem) {
//
//            lprTransItem.fromDto(lprTransactionItemInfo);
//
//            if (null != lprTransactionItemInfo.getStateKey()) {
//                lprTransItem.setLprTransactionItemState(lprTransactionItemInfo.getStateKey());
//            }
//
//            lprTransItem.setEntityUpdated(contextInfo);
//
//            try {
//                lprTransItem = lprTransactionItemDao.merge(lprTransItem);
//            } catch (VersionMismatchException e) {
//                // TODO KSENROLL-9296 remove this exception translation once
//                // version mismatch exception is added to the updateLprTransaction method
//                throw new OperationFailedException("Version Mismatch", e);
//            }
//
//            lprTransactionItemDao.getEm().flush();
//
//            return lprTransItem.toDto();
//
//        } else {
//            throw new DoesNotExistException(lprTransactionItemId);
//        }
//    }


    @Override
    @Transactional
    public StatusInfo changeLprTransactionItemState(@WebParam(name = "lprTransactionItemId") String lprTransactionItemId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LprTransactionItemEntity lprTransItem = lprTransactionItemDao.find(lprTransactionItemId);

        if (nextStateKey != null) {
            lprTransItem.setLprTransactionItemState(nextStateKey);
        }
        lprTransItem.setEntityUpdated(contextInfo);

        try {
            lprTransactionItemDao.merge(lprTransItem);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("changeLprTransactionItemState failed", e);
        }

        lprTransactionItemDao.getEm().flush();

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(Boolean.TRUE);
        return statusInfo;
    }

    private LprTransactionItemEntity createLprTransactionItem(LprTransactionItemInfo lprTransactionItemInfo, LprTransactionEntity owner, ContextInfo context) {

        LprTransactionItemEntity lprTransItemEntity = new LprTransactionItemEntity(lprTransactionItemInfo);

        if (lprTransItemEntity.getId() == null) {
            lprTransItemEntity.setId(UUIDHelper.genStringUUID());
        }
        if (null != lprTransactionItemInfo.getStateKey()) {
            lprTransItemEntity.setLprTransactionItemState(lprTransactionItemInfo.getStateKey());
        }

        if (null != lprTransactionItemInfo.getTypeKey()) {
            lprTransItemEntity.setLprTransactionItemType(lprTransactionItemInfo.getTypeKey());
        }
        if (null != lprTransactionItemInfo.getDescr()) {
            RichTextInfo descr = lprTransactionItemInfo.getDescr();
            lprTransItemEntity.setDescrFormatted(descr.getFormatted());
            lprTransItemEntity.setDescrPlain(descr.getPlain());
        }

        lprTransItemEntity.setEntityCreated(context);

        lprTransItemEntity.setOwner(owner.getId());

        return lprTransItemEntity;
    }

    @Override
    public List<LprTransactionInfo> searchForLprTransactions(QueryByCriteria criteria, ContextInfo context) throws
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LprTransactionInfo> ids = new ArrayList<>();
        GenericQueryResults<LprTransactionEntity> results = getCriteriaLookupService().lookup(LprTransactionEntity.class, criteria);
        for (LprTransactionEntity entity : results.getResults()) {
            ids.add(entity.toDto());
        }
        return ids;
    }

    @Override
    public List<String> searchForLprTransactionIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> ids = new ArrayList<String>();
        GenericQueryResults<LprTransactionEntity> results = getCriteriaLookupService().lookup(LprTransactionEntity.class, criteria);
        for (LprTransactionEntity entity : results.getResults()) {
            ids.add(entity.getId());
        }
        return ids;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LprInfo> getLprsByPersonForAtpAndLuiType(String personId, String atpId, String luiTypeKey, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // TODO: get a reference to the luiService here to resolve the atp and lui type key data
        throw new UnsupportedOperationException("Operation not implemented");
//        List<LprEntity> entityList = lprDao.getLprsByPerson(personId);
//
//        List<LprInfo> infoList = new ArrayList<LprInfo>();
//        for (LprEntity entity : entityList) {
////            LuiEntity lui = luiDao.find(entity.getLuiId());
////            if ((lui.getAtpId().equals(atpId)) && (lui.getLuiType().equals(luiTypeKey))) {
//            infoList.add(entity.toDto());
////            }
//        }
//
//        return infoList;
    }

    @Override
    public List<LprInfo> getLprsByLuiAndType(String luiId, String lprTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LprInfo> infoList = new ArrayList<LprInfo>();

        for (LprEntity lprEntity : lprDao.getLprsByLuiAndType(luiId, lprTypeKey)) {

            infoList.add(lprEntity.toDto());
        }
        return infoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LprInfo> getLprsByPersonAndLuiType(String personId, String luiTypeKey, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprEntity> entityList = lprDao.getLprsByPerson(personId);

        List<LprInfo> infoList = new ArrayList<LprInfo>();
        for (LprEntity entity : entityList) {
//            LuiEntity lui = luiDao.find(entity.getLuiId());
//            if ((lui.getLuiType().equals(luiTypeKey))) {
            infoList.add(entity.toDto());
//            }
        }

        return infoList;
    }

    @Override
    public List<ValidationResultInfo> verifyLprTransaction(String lprTransactionId,
                                                           ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Operation not implemented");
    }

    @Override
    public List<ValidationResultInfo> validateLpr(String validationType,
                                                  String luiId,
                                                  String personId,
                                                  String lprTypeKey,
                                                  LprInfo lprInfo,
                                                  ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItemsByLui(String luiId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Operation not implemented");
    }

    @Override
    public List<LprTransactionInfo> getUnsubmittedLprTransactionsByRequestingPersonAndAtp(
            String requestingPersonId,
            String atpId,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Operation not implemented");
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItemsByPersonAndLui(
            String personId, String luiId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Operation not implemented");

    }

    @Override
    @Transactional
    public List<BulkStatusInfo> createLprsForLui(String luiId,
                                                 String lprTypeKey,
                                                 List<LprInfo> lprInfos,
                                                 ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {

        List<BulkStatusInfo> bulkStatusInfoList = new ArrayList<BulkStatusInfo>();

        /*
        * This is an intentionally simple implementation.
        *
        * Later we can manage the transactions ourselves and use a paging approach.
        */

        for (LprInfo lprInfo : lprInfos) {

            BulkStatusInfo bsi = new BulkStatusInfo();

            try {
                LprInfo created = this.createLpr(lprInfo.getPersonId(), luiId, lprTypeKey, lprInfo, contextInfo);
                bsi.setSuccess(Boolean.TRUE);
                bsi.setId(created.getId());
            } catch (DataValidationErrorException de) {
                bsi.setSuccess(Boolean.FALSE);
                bsi.setMessage(ValidationUtils.asString(de.getValidationResults()));
            }

            bulkStatusInfoList.add(bsi);
        }


        return bulkStatusInfoList;
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.lpr.service.LprService#validateLprTransaction(java.lang.String, java.lang.String, org.kuali.student.enrollment.lpr.dto.LprTransactionInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<ValidationResultInfo> validateLprTransaction(
            String validationType,
            String lprTransactionType,
            LprTransactionInfo lprTransactionInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public StatusInfo changeLprState(String lprId, String nextStateKey,
                                     ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO KSENROLL-8714
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    @Transactional
    public StatusInfo changeLprTransactionState(String lprTransactionId,
                                                String nextStateKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // KSENROLL-8714
        LprTransactionInfo lprTransaction = getLprTransaction(lprTransactionId, contextInfo);
        lprTransaction.setStateKey(nextStateKey);

        //Do some manual state propagation
        if(LprServiceConstants.LPRTRANS_PROCESSING_STATE_KEY.equals(nextStateKey)){
            for(LprTransactionItemInfo lprTransactionItemInfo:lprTransaction.getLprTransactionItems()){
                //If the Transaction is now processing, set the child items that were new to processing
                if(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY.equals(lprTransactionItemInfo.getStateKey())){
                    lprTransactionItemInfo.setStateKey(LprServiceConstants.LPRTRANS_ITEM_PROCESSING_STATE_KEY);
                }
            }
        }

        try {
            updateLprTransaction(lprTransactionId, lprTransaction, contextInfo);
        } catch (DataValidationErrorException e) {
            throw new OperationFailedException("changeLprTransactionState failed", e);
        }
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(Boolean.TRUE);
        return statusInfo;
    }

    @Override
    public List<String> getLprIdsByType(String lprTypeKey,
                                        ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public List<LprInfo> getLprsByPersonAndAtp(String personId, String atpId,
                                               ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprEntity> lprEntities = lprDao.getLprsByPersonAndAtp(personId, atpId);
        List<LprInfo> lprInfos = new ArrayList<LprInfo>(lprEntities.size());
        for (LprEntity entity : lprEntities) {
            lprInfos.add(entity.toDto());
        }
        return lprInfos;
    }

    @Override
    public List<LprInfo> getLprsByTypeAndPersonAndAtp(String lprTypeKey,
                                                      String personId, String atpId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public List<String> getLprTransactionIdsByType(
            String lprTransactionTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("not implemented");
    }

    public CriteriaLookupService getCriteriaLookupService() {
        return criteriaLookupService;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }

    private CriteriaLookupService criteriaLookupService;

    public LprTransactionDao getLprTransactionDao() {
        return lprTransactionDao;
    }

    public void setLprTransactionDao(LprTransactionDao lprTransactionDao) {
        this.lprTransactionDao = lprTransactionDao;
    }

    public LprTransactionItemDao getLprTransactionItemDao() {
        return lprTransactionItemDao;
    }

    public void setLprTransactionItemDao(LprTransactionItemDao lprTransactionItemDao) {
        this.lprTransactionItemDao = lprTransactionItemDao;
    }

    public LprDao getLprDao() {
        return lprDao;
    }

    public void setLprDao(LprDao lprDao) {
        this.lprDao = lprDao;
    }


}
