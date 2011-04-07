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
package org.kuali.student.enrollment.lpr.service.impl.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.CircularRelationshipException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DependentObjectsExistException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.infc.HoldsLuService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.mock.LuiServiceAdapter;
import org.kuali.student.lum.lu.service.LuService;

/**
 * @author nwright
 */
public class LuiServiceMockPersistenceImpl extends LuiServiceAdapter
        implements HoldsLuService {

    private LuService luService;

    @Override
    public LuService getLuService() {
        return luService;
    }

    @Override
    public void setLuService(LuService luService) {
        this.luService = luService;
    }
    private Map<String, LuiInfo> luiCache = new HashMap<String, LuiInfo>();
    private Map<String, LuiLuiRelationInfo> llrCache = new HashMap<String, LuiLuiRelationInfo>();

    @Override
    public LuiInfo createLui(String cluId,
            String atpKey,
            LuiInfo luiInfo,
            ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        LuiInfo.Builder builder = new LuiInfo.Builder(luiInfo);
        MockHelper helper = new MockHelper();
        builder.setId(UUID.randomUUID().toString()).setCluId(cluId);
        builder.setAtpKey(atpKey).metaInfo(helper.createMeta(context));
        LuiInfo copy = builder.build();
        this.luiCache.put(copy.getId(), copy);
        return copy;
    }

    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId,
            String relatedLuiId,
            String luLuRelationType,
            LuiLuiRelationInfo luiLuiRelationInfo,
            ContextInfo context)
            throws AlreadyExistsException,
            CircularRelationshipException,
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        MockHelper helper = new MockHelper();
        LuiLuiRelationInfo.Builder builder = new LuiLuiRelationInfo.Builder(luiLuiRelationInfo).id(UUID.randomUUID().toString()).luiId(luiId);
        LuiLuiRelationInfo copy = builder.relatedLuiId(relatedLuiId).type(luLuRelationType).metaInfo(helper.createMeta(context)).build();
        this.llrCache.put(copy.getId(), copy);
        return copy;
    }

    @Override
    public StatusInfo deleteLui(String luiId, ContextInfo context) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.luiCache.remove(luiId) == null) {
            throw new DoesNotExistException(luiId);
        }
        return new StatusInfo.Builder().success(Boolean.TRUE).build();
    }

    @Override
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.luiCache.remove(luiLuiRelationId) == null) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        return new StatusInfo.Builder().success(Boolean.TRUE).build();
    }

    @Override
    public LuiInfo getLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        LuiInfo bean = this.luiCache.get(luiId);
        if (bean == null) {
            throw new DoesNotExistException(luiId);
        }
        return bean;
    }

    @Override
    public List<String> getLuiIdsByCluId(String cluId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> luiIds = new ArrayList<String>();
        for (LuiInfo info : this.luiCache.values()) {
            if (info.getCluId().equals(cluId)) {
                luiIds.add(info.getId());
            }
        }
        return luiIds;
    }

    @Override
    public List<String> getLuiIdsByRelation(String relatedLuiId, String luLuRelationType, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> luiIds = new ArrayList<String>();
        for (LuiLuiRelationInfo info : this.llrCache.values()) {
            if (info.getRelatedLuiId().equals(relatedLuiId)) {
                if (info.getType().equals(luLuRelationType)) {
                    luiIds.add(info.getLuiId());
                }
            }
        }
        return luiIds;
    }

    @Override
    public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> luiIds = new ArrayList<String>();
        for (LuiInfo info : this.luiCache.values()) {
            if (info.getCluId().equals(cluId)) {
                luiIds.add(info.getId());
            }
        }
        return luiIds;
    }

    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        LuiLuiRelationInfo bean = this.llrCache.get(luiLuiRelationId);
        if (bean == null) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        return bean;
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<LuiLuiRelationInfo> infos = new ArrayList<LuiLuiRelationInfo>();
        for (LuiLuiRelationInfo info : this.llrCache.values()) {
            if (info.getLuiId().equals(luiId)) {
                infos.add(info);
            }
        }
        return infos;
    }

    @Override
    public List<LuiInfo> getLuisByIdList(List<String> luiIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<LuiInfo> infos = new ArrayList<LuiInfo>();
        for (LuiInfo info : this.luiCache.values()) {
            // TODO: consider speading up the list search by converting to a hashmap
            if (luiIdList.contains(info.getId())) {
                infos.add(info);
            }
        }
        return infos;
    }

    @Override
    public List<LuiInfo> getLuisByRelation(String relatedLuiId, String luLuRelationType, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<LuiInfo> infos = new ArrayList<LuiInfo>();
        for (LuiLuiRelationInfo info : this.llrCache.values()) {
            // TODO: consider speading up the list search by converting to a hashmap
            if (info.getRelatedLuiId().equals(relatedLuiId)) {
                if (info.getType().equals(luLuRelationType)) {
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
    public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<LuiInfo> infos = new ArrayList<LuiInfo>();
        for (LuiInfo info : this.luiCache.values()) {
            // TODO: consider speading up the list search by converting to a hashmap
            if (info.getCluId().equals(cluId)) {
                if (info.getAtpKey().equals(atpKey)) {
                    infos.add(info);
                }
            }
        }
        return infos;
    }

    @Override
    public List<String> getRelatedLuiIdsByLuiId(String luiId, String luLuRelationType, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> luiIds = new ArrayList<String>();
        for (LuiLuiRelationInfo info : this.llrCache.values()) {
            if (info.getLuiId().equals(luiId)) {
                if (info.getType().equals(luLuRelationType)) {
                    luiIds.add(info.getRelatedLuiId());
                }
            }
        }
        return luiIds;
    }

    @Override
    public List<LuiInfo> getRelatedLuisByLuiId(String luiId, String luLuRelationType, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<LuiInfo> luiIds = new ArrayList<LuiInfo>();
        for (LuiLuiRelationInfo info : this.llrCache.values()) {
            if (info.getLuiId().equals(luiId)) {
                if (info.getType().equals(luLuRelationType)) {
                    try {
                        luiIds.add(this.getLui(info.getRelatedLuiId(), context));
                    } catch (DoesNotExistException ex) {
                        throw new OperationFailedException
                          ("Referenetial integrity bad for luiId on llr"
                          + info.getLuiId() + " llr.id=" + info.getId());

                    }
                }
            }
        }
        return luiIds;
    }

    @Override
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        LuiInfo existing = this.luiCache.get(luiId);
        if (existing == null) {
            throw new DoesNotExistException(luiId);
        }
        if (!luiInfo.getMetaInfo().getVersionInd().equals(
                existing.getMetaInfo().getVersionInd())) {
            throw new VersionMismatchException(
                    "Updated by " + existing.getMetaInfo().getUpdateId() + " on "
                    + existing.getMetaInfo().getUpdateId() + " with version of "
                    + existing.getMetaInfo().getVersionInd());
        }
        MockHelper helper = new MockHelper();
        LuiInfo copy = new LuiInfo.Builder(luiInfo).metaInfo(helper.updateMeta(existing.getMetaInfo(), context)).build();
        this.luiCache.put(luiId, copy);
        // mirroring what was done before immutable DTO's; why returning copy of copy?
        return new LuiInfo.Builder(copy).build();
    }

    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        LuiLuiRelationInfo existing = this.llrCache.get(luiLuiRelationId);
        if (existing == null) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        if (!luiLuiRelationInfo.getMetaInfo().getVersionInd().equals(
                existing.getMetaInfo().getVersionInd())) {
            throw new VersionMismatchException(
                    "Updated by " + existing.getMetaInfo().getUpdateId() + " on "
                    + existing.getMetaInfo().getUpdateId() + " with version of "
                    + existing.getMetaInfo().getVersionInd());
        }
        MockHelper helper = new MockHelper();
        LuiLuiRelationInfo copy = new LuiLuiRelationInfo.Builder(luiLuiRelationInfo).metaInfo(helper.updateMeta(existing.getMetaInfo(), context)).build();
        this.llrCache.put(luiLuiRelationId, copy);
        return copy;

    }

    @Override
    public LuiInfo updateLuiState(String luiId, String luState, ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        LuiInfo existing = this.getLui(luiId, context);
        LuiInfo updated = new LuiInfo.Builder(existing).setState(luState).build();
        try {
            return this.updateLui(luiId, updated, context);
        } catch (VersionMismatchException ex) {
            throw new OperationFailedException("someone changed version since get ", ex);
        }
    }
}

