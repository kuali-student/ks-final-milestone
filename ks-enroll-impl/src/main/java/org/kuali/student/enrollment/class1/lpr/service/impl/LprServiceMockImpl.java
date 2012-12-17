/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebParam;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
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
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.class1.util.ValidationUtils;

public class LprServiceMockImpl implements LprService, MockService {

    private LuiService luiService;

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    
    @Override
	public void clear() {
		this.lprMap.clear();
		this.lprTransactionMap.clear();
	
	}

	@Override
    public LprInfo getLpr(String lprId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.lprMap.containsKey(lprId)) {
            throw new DoesNotExistException(lprId);
        }
        return this.lprMap.get(lprId);
    }

	
    @Override
	public List<LprInfo> getLprsByLuis(
			List<String> luiIds,
			 ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<LprInfo> lprs = new ArrayList<LprInfo>();

		Set<LprInfo> allLprs = new HashSet<LprInfo>(this.lprMap.values());

		for (String luiId : luiIds) {

			Iterator<LprInfo> it = allLprs.iterator();
			while (it.hasNext()) {
				LprInfo lprInfo = it.next();

				if (lprInfo.getLuiId().equals(luiId)) {
					lprs.add(lprInfo);
					it.remove();
				}
			}

		}
		
		return lprs;
	}

	@Override
    public List<LprInfo> getLprsByIds(List<String> lprIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprInfo> list = new ArrayList<LprInfo>();
        for (String id : lprIds) {
            list.add(this.getLpr(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getLuiIdsByPersonAndTypeAndState(String personId, String lprTypeKey, String relationState, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (LprInfo info : lprMap.values()) {
            if (personId.equals(info.getPersonId())) {
                if (lprTypeKey.equals(info.getTypeKey())) {
                    if (relationState.equals(info.getStateKey())) {
                        list.add(info.getLuiId());
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getPersonIdsByLuiAndTypeAndState(String luiId, String lprTypeKey, String relationState, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (LprInfo info : lprMap.values()) {
            if (luiId.equals(info.getLuiId())) {
                if (lprTypeKey.equals(info.getTypeKey())) {
                    if (relationState.equals(info.getStateKey())) {
                        list.add(info.getId());
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<LprInfo> getLprsByPersonAndLui(String personId, String luiId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprInfo> list = new ArrayList<LprInfo>();
        for (LprInfo info : lprMap.values()) {
            if (personId.equals(info.getPersonId())) {
                if (luiId.equals(info.getLuiId())) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    @Override
    public List<LprInfo> getLprsByPerson(String personId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprInfo> list = new ArrayList<LprInfo>();
        for (LprInfo info : lprMap.values()) {
            if (personId.equals(info.getPersonId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<LprInfo> getLprsByLui(String luiId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprInfo> list = new ArrayList<LprInfo>();
        for (LprInfo info : lprMap.values()) {
            if (luiId.equals(info.getLuiId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<LprInfo> getLprsByLuiAndType(String luiId, String lprTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprInfo> list = new ArrayList<LprInfo>();
        for (LprInfo info : lprMap.values()) {
            if (luiId.equals(info.getTypeKey())) {
                if (lprTypeKey.equals(info.getTypeKey())) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    @Override
    public List<LprInfo> getLprsByPersonForAtp(String personId, String atpId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprInfo> list = new ArrayList<LprInfo>();
        for (LprInfo info : lprMap.values()) {
            if (personId.equals(info.getPersonId())) {
                LuiInfo lui = luiService.getLui(info.getLuiId(), contextInfo);
                if (atpId.equals(lui.getAtpId())) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    @Override
    public List<LprInfo> getLprsByPersonAndTypeForAtp(String personId, String atpId, String lprTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprInfo> list = new ArrayList<LprInfo>();
        for (LprInfo info : lprMap.values()) {
            if (personId.equals(info.getPersonId())) {
                if (lprTypeKey.equals(info.getTypeKey())) {
                    LuiInfo lui = luiService.getLui(info.getLuiId(), contextInfo);
                    if (atpId.equals(lui.getAtpId())) {
                        list.add(info);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<LprInfo> getLprsByPersonAndLuiType(String personId, String luiTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprInfo> list = new ArrayList<LprInfo>();
        for (LprInfo info : lprMap.values()) {
            if (personId.equals(info.getTypeKey())) {
                if (luiTypeKey.equals(info.getTypeKey())) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    @Override
    public List<LprInfo> getLprsByPersonForAtpAndLuiType(String personId, String atpId, String luiTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprInfo> list = new ArrayList<LprInfo>();
        for (LprInfo info : lprMap.values()) {
            if (personId.equals(info.getTypeKey())) {
                if (atpId.equals(info.getTypeKey())) {
                    if (luiTypeKey.equals(info.getTypeKey())) {
                        list.add(info);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<ValidationResultInfo> validateLpr(String validationType, String luiId, String personId, String lprTypeKey, LprInfo lprInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<String> searchForLprIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForLprIds has not been implemented");
    }

    @Override
    public List<LprInfo> searchForLprs(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForLprs has not been implemented");
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, LprInfo> lprMap = new LinkedHashMap<String, LprInfo>();

    @Override
    public LprInfo createLpr(String personId, String luiId, String lprTypeKey, LprInfo lprInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        if (!lprTypeKey.equals(lprInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        LprInfo copy = new LprInfo(lprInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        lprMap.put(copy.getId(), copy);
        return new LprInfo(copy);
    }

    @Override
    public List<BulkStatusInfo> createLprsForPerson(String personId, String lprTypeKey, List<LprInfo> lprInfos, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        List<BulkStatusInfo> list = new ArrayList<BulkStatusInfo>(lprInfos.size());
        for (LprInfo info : lprInfos) {
            BulkStatusInfo bsi = new BulkStatusInfo();
            try {
                LprInfo created = this.createLpr(personId, info.getLuiId(), lprTypeKey, info, contextInfo);
                bsi.setSuccess(Boolean.TRUE);
                bsi.setId(created.getId());
            } catch (DataValidationErrorException de) {
                bsi.setSuccess(Boolean.FALSE);
                bsi.setMessage(asString(de.getValidationResults()));
            }
            list.add(bsi);
        }
        return list;
    }

    private String asString(List<ValidationResultInfo> vris) {
        StringBuilder sb = new StringBuilder();
        String newLine = ";";
        for (ValidationResultInfo vri : vris) {
            sb.append(newLine);
            newLine = "/n";
            sb.append(vri.getMessage());
        }
        return sb.toString();
    }

    @Override
    public List<BulkStatusInfo> createLprsForLui(String luiId, String lprTypeKey, List<LprInfo> lprInfos, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        List<BulkStatusInfo> list = new ArrayList<BulkStatusInfo>(lprInfos.size());
        for (LprInfo info : lprInfos) {
            BulkStatusInfo bsi = new BulkStatusInfo();
            try {
                LprInfo created = this.createLpr(info.getPersonId(), luiId, lprTypeKey, info, contextInfo);
                bsi.setSuccess(Boolean.TRUE);
                bsi.setId(created.getId());
            } catch (DataValidationErrorException de) {
                bsi.setSuccess(Boolean.FALSE);
                bsi.setMessage(ValidationUtils.asString(de.getValidationResults()));
            }
            list.add(bsi);
        }
        return list;
    }

    @Override
    public LprInfo updateLpr(String lprId, LprInfo lprInfo, ContextInfo contextInfo)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        if (!lprId.equals(lprInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        LprInfo copy = new LprInfo(lprInfo);
        LprInfo old = this.getLpr(lprInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.lprMap.put(lprInfo.getId(), copy);
        return new LprInfo(copy);
    }

    @Override
    public StatusInfo deleteLpr(String lprId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.lprMap.remove(lprId) == null) {
            throw new DoesNotExistException(lprId);
        }
        return newStatus();
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, LprTransactionInfo> lprTransactionMap = new LinkedHashMap<String, LprTransactionInfo>();

    @Override
    public LprTransactionInfo createLprTransaction(String lprTransactionType, LprTransactionInfo lprTransactionInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // create 
        if (!lprTransactionType.equals(lprTransactionInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        LprTransactionInfo copy = new LprTransactionInfo(lprTransactionInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        
        lprTransactionMap.put(copy.getId(), copy);
        return new LprTransactionInfo(copy);
    }

    @Override
    public LprTransactionInfo createLprTransactionFromExisting(String lprTransactionId, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("createLprTransactionFromExisting has not been implemented");
    }

    @Override
    public LprTransactionInfo updateLprTransaction(String lprTransactionId, LprTransactionInfo lprTransactionInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // update
        if (!lprTransactionId.equals(lprTransactionInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        LprTransactionInfo copy = new LprTransactionInfo(lprTransactionInfo);
        LprTransactionInfo old = this.getLprTransaction(lprTransactionInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.lprTransactionMap.put(lprTransactionInfo.getId(), copy);
        return new LprTransactionInfo(copy);
    }

    @Override
    public LprTransactionInfo getLprTransaction(String lprTransactionId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.lprTransactionMap.containsKey(lprTransactionId)) {
            throw new DoesNotExistException(lprTransactionId);
        }
        return this.lprTransactionMap.get(lprTransactionId);
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItemsByPersonAndLui(String personId, String luiId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprTransactionItemInfo> list = new ArrayList<LprTransactionItemInfo>();
        for (LprTransactionInfo trans : lprTransactionMap.values()) {
            for (LprTransactionItemInfo info : trans.getLprTransactionItems()) {
                if (personId.equals(info.getPersonId())) {
                    if (luiId.equals(info.getNewLuiId())) {
                        list.add(info);
                    } else if (luiId.equals(info.getExistingLuiId())) {
                        list.add(info);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsByIds(List<String> lprTransactionIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LprTransactionInfo> list = new ArrayList<LprTransactionInfo>();
        for (String id : lprTransactionIds) {
            list.add(this.getLprTransaction(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItemsByResultingLpr(String lprId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LprTransactionItemInfo> list = new ArrayList<LprTransactionItemInfo>();
        for (LprTransactionInfo trans : this.lprTransactionMap.values()) {
            for (LprTransactionItemInfo info : trans.getLprTransactionItems()) {
                if (info.getLprTransactionItemResult() != null) {
                    if (lprId.equals(info.getLprTransactionItemResult().getResultingLprId())) {
                        list.add(info);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItemsByLui(String luiId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LprTransactionItemInfo> list = new ArrayList<LprTransactionItemInfo>();

        for (LprTransactionInfo trans : lprTransactionMap.values()) {
            for (LprTransactionItemInfo info : trans.getLprTransactionItems()) {
                if (luiId.equals(info.getNewLuiId())) {
                    list.add(info);
                } else if (luiId.equals(info.getExistingLuiId())) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    @Override
    public List<LprTransactionInfo> getUnsubmittedLprTransactionsByRequestingPersonAndAtp(String requestingPersonId, String atpId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LprTransactionInfo> list = new ArrayList<LprTransactionInfo>();
        for (LprTransactionInfo info : lprTransactionMap.values()) {
            if (requestingPersonId.equals(info.getRequestingPersonId())) {
                if (atpId.equals(info.getAtpId())) {
                    if (info.getStateKey().equals(LprServiceConstants.LPRTRANS_NEW_STATE_KEY)) {
                        list.add(info);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public StatusInfo deleteLprTransaction(String lprTransactionId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.lprTransactionMap.remove(lprTransactionId) == null) {
            throw new DoesNotExistException(lprTransactionId);
        }
        return newStatus();
    }

    @Override
    public LprTransactionInfo processLprTransaction(String lprTransactionId, ContextInfo contextInfo)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("processLprTransaction has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> verifyLprTransaction(String lprTransactionId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("verifyLprTransaction has not been implemented");
    }

    @Override
    public List<LprTransactionInfo> searchForLprTransactions(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForLprTransactions has not been implemented");
    }

    @Override
    public List<String> searchForLprTransactionIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForLprTransactionIds has not been implemented");
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }
}
