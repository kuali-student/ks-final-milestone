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
package org.kuali.student.enrollment.classI.lui.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.service.LuiService;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;

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

import org.kuali.student.test.utilities.MockHelper;

/**
 * @author nwright
 */
public class LuiServiceMockImpl implements LuiService {
    private final Map<String, LuiInfo> luiCache = new HashMap<String, LuiInfo>();
    private final Map<String, LuiLuiRelationInfo> llrCache = new HashMap<String, LuiLuiRelationInfo>();
    private final Map<String, LuiCapacityInfo> lcapCache = new HashMap<String, LuiCapacityInfo>();

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context)
	throws OperationFailedException, MissingParameterException,
	       PermissionDeniedException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) 
	throws OperationFailedException, MissingParameterException, 
	       PermissionDeniedException, DoesNotExistException {
	// TODO Auto-generated method stub
	return null;
    }
    
    @Override
    public TypeInfo getType(String typeKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	       MissingParameterException, OperationFailedException {
	// TODO Auto-generated method stub
	return null;
    }
    
    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {
	// TODO Auto-generated method stub
	return null;
    }
    
    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	       MissingParameterException, OperationFailedException {
	// TODO Auto-generated method stub
	return null;
    }
    
    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	       MissingParameterException, OperationFailedException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public LuiInfo getLui(String luiId, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {

	if (luiId == null) {
	    throw new MissingParameterException("luiId is null");
	}

        LuiInfo info = this.luiCache.get(luiId);
        if (info == null) {
            throw new DoesNotExistException(luiId);
        }

        return info;
    }

    @Override
    public List<LuiInfo> getLuisByIdList(List<String> luiIdList, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {

	if (luiIdList == null) {
	    throw new MissingParameterException("luiIdList is null");
	}

        List<LuiInfo> infos = new ArrayList<LuiInfo>();
	for (String id : luiIdList) {
	    infos.add(getLui(id, context));
	}

        return infos;
    }

    @Override
    public List<String> getLuiIdsByType(String luiTypeKey, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {

	if (luiTypeKey == null) {
	    throw new MissingParameterException("luiTypeKey is null");
	}
	
        List<String> luiIds = new ArrayList<String>();
        for (LuiInfo info : this.luiCache.values()) {
	    if (luiTypeKey.equals(info.getTypeKey())) {
		luiIds.add(info.getId());
	    }
	}

	return luiIds;
    }

    @Override
    public List<String> getLuiIdsByCluId(String cluId, ContextInfo context)
	throws InvalidParameterException, MissingParameterException, 
	       OperationFailedException {

	if (cluId == null) {
	    throw new MissingParameterException("cluId is null");
	}

        List<String> luiIds = new ArrayList<String>();
        for (LuiInfo info : this.luiCache.values()) {
            if (cluId.equals(info.getCluId())) {
                luiIds.add(info.getId());
            }
        }

        return luiIds;
    }

    @Override
    public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, 
		   MissingParameterException, OperationFailedException {

        List<String> luiIds = new ArrayList<String>();
        for (LuiInfo info : getLuisInAtpByCluId(cluId, atpKey, context)) {
	    luiIds.add(info.getId());
        }

        return luiIds;
    }

    @Override
    public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {

	if (cluId == null) {
	    throw new MissingParameterException("cluId is null");
	}

	if (atpKey == null) {
	    throw new MissingParameterException("atpKey is null");
	}

        List<LuiInfo> infos = new ArrayList<LuiInfo>();
        for (LuiInfo info : this.luiCache.values()) {
            if (cluId.equals(info.getCluId()) && atpKey.equals(info.getAtpKey())) {
                infos.add(info);
            }
        }

        return infos;
    }

    @Override
    public List<String> getLuiIdsByRelation(String relatedLuiId, String luLuRelationTypeKey, ContextInfo context)
	throws InvalidParameterException, MissingParameterException, OperationFailedException {

	List<LuiInfo> infos = getLuisByRelation(relatedLuiId, luLuRelationTypeKey, context);
        List<String> luiIds = new ArrayList<String>();
        for (LuiInfo info : infos) {
	    luiIds.add(info.getId());
        }

        return luiIds;
    }

    @Override
    public List<LuiInfo> getLuisByRelation(String relatedLuiId, String luLuRelationType, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {

	if (relatedLuiId == null) {
	    throw new MissingParameterException("relatedLuiId is null");
	}

	if (luLuRelationType == null) {
	    throw new MissingParameterException("luLuRelationType is null");
	}

        List<LuiInfo> infos = new ArrayList<LuiInfo>();
        for (LuiLuiRelationInfo info : this.llrCache.values()) {
            if (relatedLuiId.equals(info.getRelatedLuiId()) || relatedLuiId.equals(info.getLuiId())) {
                if (luLuRelationType.equals(info.getTypeKey())) {
                    try {
                        infos.add(this.getLui(info.getLuiId(), context));
                    } catch (DoesNotExistException ex) {
                        throw new OperationFailedException
                          ("Referenetial integrity bad for luiId on llr"
                          + info.getLuiId() + " llr.id=" + info.getId());
                    }
                }
            }
        }

        return infos;
    }

    @Override
    public List<String> getRelatedLuiIdsByLuiId(String luiId, String luLuRelationType, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {

	List<String> luiIds = new ArrayList<String>();	
	for (LuiInfo lui : getRelatedLuisByLuiId(luiId, luLuRelationType, context)) {
	    luiIds.add(lui.getId());
        }

        return luiIds;
    }

    @Override
    public List<LuiInfo> getRelatedLuisByLuiId(String luiId, String luLuRelationType, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {

	if (luiId == null) {
	    throw new MissingParameterException("luiId is null");
	}

	if (luLuRelationType == null) {
	    throw new MissingParameterException("luLuRelationType is null");
	}

        List<LuiInfo> infos = new ArrayList<LuiInfo>();
        for (LuiLuiRelationInfo info : this.llrCache.values()) {
            if (luiId.equals(info.getLuiId()) || luiId.equals(info.getRelatedLuiId())) {
                if (luLuRelationType.equals(info.getTypeKey())) {
                    try {
                        infos.add(this.getLui(info.getRelatedLuiId(), context));
                    } catch (DoesNotExistException ex) {
                        throw new OperationFailedException
			    ("Referenetial integrity bad for luiId on llr"
			     + info.getLuiId() + " llr.id=" + info.getId());
                    }
                }
            }
        }

        return infos;
    }

    public List<ValidationResultInfo> validateLui(String validationType, LuiInfo luiInfo, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {
	
	if (validationType == null) {
	    throw new MissingParameterException("validationType is null");
	}

	if (luiInfo == null) {
	    throw new MissingParameterException("luiInfo is null");
	}

	return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public LuiInfo createLui(String cluId, String atpKey, LuiInfo luiInfo, ContextInfo context)
	throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException,
	       InvalidParameterException, MissingParameterException, OperationFailedException,
	       PermissionDeniedException {

	if (cluId == null) {
	    throw new MissingParameterException("cluId is null");
	}

	if (luiInfo == null) {
	    throw new MissingParameterException("luiInfo is null");
	}
	
        MockHelper helper = new MockHelper();
        LuiInfo lInfo = new LuiInfo(luiInfo);

        lInfo.setId(UUID.randomUUID().toString());
        lInfo.setCluId(cluId);
        lInfo.setAtpKey(atpKey);
        lInfo.setMeta(helper.createMeta(context));
        this.luiCache.put(lInfo.getId(), lInfo);

        return lInfo;
    }

    @Override
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context) 
	throws DataValidationErrorException, DoesNotExistException, 
	       InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException, 
	       VersionMismatchException {

	if (luiId == null) {
	    throw new MissingParameterException("luiId is null");
	}

	if (luiInfo == null) {
	    throw new MissingParameterException("luiInfo is null");
	}

        LuiInfo existing = this.luiCache.get(luiId);
        if (existing == null) {
            throw new DoesNotExistException(luiId);
        }

        if (!luiInfo.getMeta().getVersionInd().equals(
                existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException(
                    "Updated by " + existing.getMeta().getUpdateId() + " on "
                    + existing.getMeta().getUpdateId() + " with version of "
                    + existing.getMeta().getVersionInd());
        }

        MockHelper helper = new MockHelper();
        LuiInfo lInfo = new LuiInfo(luiInfo);
        lInfo.setMeta(helper.updateMeta(existing.getMeta(), context));
        this.luiCache.put(luiId, lInfo);

        return lInfo;
    }

    @Override
    public StatusInfo deleteLui(String luiId, ContextInfo context) 
	throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException, PermissionDeniedException {

	if (luiId == null) {
	    throw new MissingParameterException("luiId is null");
	}

        if (this.luiCache.remove(luiId) == null) {
            throw new DoesNotExistException(luiId);
        }

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public LuiInfo updateLuiState(String luiId, String luState, ContextInfo context)
	throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        LuiInfo existing = this.getLui(luiId, context);
        LuiInfo luiInfo = new LuiInfo(existing);
        luiInfo.setStateKey(luState);

        try {
            return this.updateLui(luiId, luiInfo, context);
        } catch (VersionMismatchException ex) {
            throw new OperationFailedException("someone changed version since get ", ex);
        }
    }

    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {

	if (luiLuiRelationId == null) {
	    throw new MissingParameterException("luiLuiRelationId is null");
	}

        LuiLuiRelationInfo info = this.llrCache.get(luiLuiRelationId);
        if (info == null) {
            throw new DoesNotExistException(luiLuiRelationId + " does not exist");
        }

        return info;
    }

    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIdList(List<String> luiLuiRelationIdList, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {

	if (luiLuiRelationIdList == null) {
	    throw new MissingParameterException("luiLuiRelationIdList is null");
	}

	List<LuiLuiRelationInfo> infos = new ArrayList<LuiLuiRelationInfo>();
	for (String id : luiLuiRelationIdList) {
	    infos.add(getLuiLuiRelation(id, context));
	}

	return infos;
    }


    public List<String> getLuiLuiRelationIdsByType(String luiLuiRelationTypeKey, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {

	if (luiLuiRelationTypeKey == null) {
	    throw new MissingParameterException("luiLuiRelationTypeKey is null");
	}

        List<String> llrIds = new ArrayList<String>();
        for (LuiLuiRelationInfo info : this.llrCache.values()) {
	    if (luiLuiRelationTypeKey.equals(info.getTypeKey())) {
		llrIds.add(info.getId());
	    }
	}

	return llrIds;
    }


    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {

	if (luiId == null) {
	    throw new MissingParameterException("luiId is null");
	}

        List<LuiLuiRelationInfo> infos = new ArrayList<LuiLuiRelationInfo>();
        for (LuiLuiRelationInfo info : this.llrCache.values()) {
            if (luiId.equals(info.getLuiId()) || luiId.equals(info.getRelatedLuiId())) {
                infos.add(info);
            }
        }

        return infos;
    }

    public List<ValidationResultInfo> validateLuiLuiRelation(String validationType, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {

	if (validationType == null) {
	    throw new MissingParameterException("validationType is null");
	}

	if (luiLuiRelationInfo == null) {
	    throw new MissingParameterException("luiLuiRelationInfo is null");
	}

	return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luLuRelationType, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
	throws AlreadyExistsException, CircularRelationshipException, DataValidationErrorException,
	       DoesNotExistException, InvalidParameterException, MissingParameterException,
	       OperationFailedException, PermissionDeniedException {

	if (luiId == null) {
	    throw new MissingParameterException("luiId is null");
	}

	if (relatedLuiId == null) {
	    throw new MissingParameterException("relatedLuiId is null");
	}

	if (luiLuiRelationInfo == null) {
	    throw new MissingParameterException("luiLuiRelationInfo is null");
	}

        MockHelper helper = new MockHelper();
        LuiLuiRelationInfo llrInfo = new LuiLuiRelationInfo(luiLuiRelationInfo);
        llrInfo.setId(UUID.randomUUID().toString());
        llrInfo.setLuiId(luiId);
        llrInfo.setRelatedLuiId(relatedLuiId);
        llrInfo.setTypeKey(luLuRelationType);
        llrInfo.setMeta(helper.createMeta(context));
        this.llrCache.put(llrInfo.getId(), llrInfo);

        return llrInfo;
    }

    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) 
	throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException, PermissionDeniedException, 
	       VersionMismatchException {

	if (luiLuiRelationId == null) {
	    throw new MissingParameterException("luiLuiRelationId is null");
	}

	if (luiLuiRelationInfo == null) {
	    throw new MissingParameterException("luiLuiRelationInfo is null");
	}

        LuiLuiRelationInfo existing = this.llrCache.get(luiLuiRelationId);
        if (existing == null) {
            throw new DoesNotExistException(luiLuiRelationId);
        }

        if (!luiLuiRelationInfo.getMeta().getVersionInd().equals(
                existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException(
                    "Updated by " + existing.getMeta().getUpdateId() + " on "
                    + existing.getMeta().getUpdateId() + " with version of "
                    + existing.getMeta().getVersionInd());
        }

        MockHelper helper = new MockHelper();
        LuiLuiRelationInfo llrInfo = new LuiLuiRelationInfo(luiLuiRelationInfo);
        llrInfo.setMeta(helper.updateMeta(existing.getMeta(), context));
        this.llrCache.put(luiLuiRelationId, llrInfo);

        return llrInfo;
    }

    @Override
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException {

	if (luiLuiRelationId == null) {
	    throw new MissingParameterException("luiLuiRelationId is null");
	}

        if (this.luiCache.remove(luiLuiRelationId) == null) {
            throw new DoesNotExistException(luiLuiRelationId);
        }

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public LuiCapacityInfo getLuiCapacity(String luiCapacityId, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {

	if (luiCapacityId == null) {
	    throw new MissingParameterException("luiCapacityId is null");
	}

        LuiCapacityInfo info = this.lcapCache.get(luiCapacityId);
        if (info == null) {
            throw new DoesNotExistException(luiCapacityId);
        }

        return info;
    }

    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByIdList(List<String> luiCapacityIdList, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {

	if (luiCapacityIdList == null) {
	    throw new MissingParameterException("luiCapacityIdList is null");
	}

        List<LuiCapacityInfo> infos = new ArrayList<LuiCapacityInfo>();
	for (String id : luiCapacityIdList) {
	    infos.add(getLuiCapacity(id, context));
	}

        return infos;
    }

    @Override
    public List<String> getLuiCapacityIdsByType(String luiCapacityTypeKey, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {

	if (luiCapacityTypeKey == null) {
	    throw new MissingParameterException("luiCapacityTypeKey is null");
	}
	
        List<String> luiCapacityIds = new ArrayList<String>();
        for (LuiCapacityInfo info : this.lcapCache.values()) {
	    if (luiCapacityTypeKey.equals(info.getTypeKey())) {
		luiCapacityIds.add(info.getId());
	    }
	}

	return luiCapacityIds;
    }

    @Override
    public List<ValidationResultInfo> validateLuiCapacity(String validationType, LuiCapacityInfo luiCapacityInfo, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {

	if (validationType == null) {
	    throw new MissingParameterException("validationType is null");
	}

	if (luiCapacityInfo == null) {
	    throw new MissingParameterException("luiCapacityInfo is null");
	}

	return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public LuiCapacityInfo createLuiCapacity(LuiCapacityInfo luiCapacityInfo, ContextInfo context) 
	throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, 
	       InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException {

	if (luiCapacityInfo == null) {
	    throw new MissingParameterException("luiCapacityInfo is null");
	}
	
        MockHelper helper = new MockHelper();
        LuiCapacityInfo lInfo = new LuiCapacityInfo(luiCapacityInfo);

        lInfo.setId(UUID.randomUUID().toString());
        lInfo.setMeta(helper.createMeta(context));
        this.lcapCache.put(lInfo.getId(), lInfo);

        return lInfo;
    }

    @Override
    public LuiCapacityInfo updateLuiCapacity(String luiCapacityId, LuiCapacityInfo luiCapacityInfo, ContextInfo context) 
	throws DataValidationErrorException, DoesNotExistException, 
	       InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException, 
	       VersionMismatchException {

	if (luiCapacityId == null) {
	    throw new MissingParameterException("luiCapacityId is null");
	}

	if (luiCapacityInfo == null) {
	    throw new MissingParameterException("luiCapacityInfo is null");
	}

        LuiCapacityInfo existing = this.lcapCache.get(luiCapacityId);
        if (existing == null) {
            throw new DoesNotExistException(luiCapacityId);
        }

        if (!luiCapacityInfo.getMeta().getVersionInd().equals(
                existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException(
                    "Updated by " + existing.getMeta().getUpdateId() + " on "
                    + existing.getMeta().getUpdateId() + " with version of "
                    + existing.getMeta().getVersionInd());
        }

        MockHelper helper = new MockHelper();
        LuiCapacityInfo lInfo = new LuiCapacityInfo(luiCapacityInfo);
        lInfo.setMeta(helper.updateMeta(existing.getMeta(), context));
        this.lcapCache.put(luiCapacityId, lInfo);

        return lInfo;
    }

    @Override
    public StatusInfo deleteLuiCapacity(String luiCapacityId, ContextInfo context) 
	throws DependentObjectsExistException, DoesNotExistException, 
	       InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException {

	if (luiCapacityId == null) {
	    throw new MissingParameterException("luiCapacityId is null");
	}

        if (this.lcapCache.remove(luiCapacityId) == null) {
            throw new DoesNotExistException(luiCapacityId);
        }

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }
}

