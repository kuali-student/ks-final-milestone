/**
 * Copyright 2010 The Kuali Foundation
 *
 *  Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

/**
 * @author nwright
 */

public class LuiServiceMockImpl 
    implements LuiService {

    private Map<String, LuiInfo> luis = new LinkedHashMap<String, LuiInfo>();
    private Map<String, LuiLuiRelationInfo> luiLuiRelations = new LinkedHashMap<String, LuiLuiRelationInfo>();
    
    @Override
    public LuiInfo getLui(String luiId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!luis.containsKey(luiId)) {
            throw new DoesNotExistException(luiId);
        }
        return new LuiInfo (luis.get(luiId));
    }

    @Override
    public List<LuiInfo> getLuisByIds(List<String> luiIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiInfo> list = new ArrayList<LuiInfo>();
        for (String id : luiIds) {
            list.add (this.getLui(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getLuiIdsByType(String luiTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (LuiInfo info : this.luis.values()) {
            if (info.getTypeKey().equals(luiTypeKey)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getLuiIdsByClu(String cluId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (LuiInfo info : this.luis.values()) {
            if (info.getAtpId().equals(cluId)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getLuiIdsByAtpAndType(String atpId, String typeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (LuiInfo info : this.luis.values()) {
            if (info.getAtpId().equals(atpId)) {
                if (info.getTypeKey().equals(typeKey)) {
                    list.add(info.getId());
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getLuiIdsByAtpAndClu(String cluId, String atpId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (LuiInfo info : this.luis.values()) {
            if (info.getAtpId().equals(atpId)) {
                if (info.getCluId().equals(cluId)) {
                    list.add(info.getId());
                }
            }
        }
        return list;
    }

    @Override
    public List<LuiInfo> getLuisByAtpAndClu(String cluId, String atpId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            return this.getLuisByIds(this.getLuiIdsByAtpAndClu(cluId, atpId, contextInfo), contextInfo);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    @Override
    public List<String> searchForLuiIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LuiInfo> searchForLuis(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateLui(String validationTypeKey, String cluId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public LuiInfo createLui(String cluId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        LuiInfo copy = new LuiInfo(luiInfo);
        if (copy.getId() == null) {
            copy.setId(luis.size() + "");
        }
        luis.put(copy.getId(), copy);
        return new LuiInfo(copy);
    }

    @Override
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        if (!luis.containsKey(luiId)) {
            throw new DoesNotExistException(luiId);
        }
        LuiInfo copy = new LuiInfo(luiInfo);
        luis.put(luiId, copy);
        return new LuiInfo(copy);
    }

    @Override
    public StatusInfo deleteLui(String luiId, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        this.getLui(luiId, contextInfo);
        luis.remove(luiId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!this.luiLuiRelations.containsKey(luiLuiRelationId)) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        return new LuiLuiRelationInfo(this.luiLuiRelations.get(luiLuiRelationId));
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIds(List<String> luiLuiRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiLuiRelationInfo> list = new ArrayList<LuiLuiRelationInfo>();
        for (LuiLuiRelationInfo info : this.luiLuiRelations.values()) {
            if (luiLuiRelationIds.contains(info.getId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<String> getLuiLuiRelationIdsByType(String luiLuiRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (LuiLuiRelationInfo info : this.luiLuiRelations.values()) {
            if (info.getTypeKey().equals(luiLuiRelationTypeKey)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiLuiRelationInfo> list = new ArrayList<LuiLuiRelationInfo>();
        for (LuiLuiRelationInfo info : this.luiLuiRelations.values()) {
            if (info.getLuiId().equals(luiId)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLuiAndRelatedLui(String luiId, String relatedLuiId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiLuiRelationInfo> list = new ArrayList<LuiLuiRelationInfo>();
        for (LuiLuiRelationInfo info : this.luiLuiRelations.values()) {
            if (info.getLuiId().equals(luiId) && 
                info.getRelatedLuiId().equals(relatedLuiId)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<LuiInfo> getLuiLuiRelationsByLuiAndRelatedLuiType(String luiId, String relatedLuiTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO
        return null;
    }


    @Override
    public List<String> getLuiIdsByRelatedLuiAndRelationType(String luiId, String luiLuiRelationTypeKey, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (LuiLuiRelationInfo info : this.luiLuiRelations.values()) {
            if (info.getLuiId().equals(luiId)) {
                if (info.getTypeKey().equals(luiLuiRelationTypeKey)) {
                    list.add(info.getRelatedLuiId());
                }
            }
        }
        return list;
    }

    @Override
    public List<LuiInfo> getLuisByRelatedLuiAndRelationType(String relatedLuiId, String luiLuiRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            List<String> ids = this.getLuiIdsByLuiAndRelationType(relatedLuiId, luiLuiRelationTypeKey, contextInfo);
            return this.getLuisByIds(ids, contextInfo);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }


    @Override
    public List<String> getLuiIdsByLuiAndRelationType(String relatedLuiId, String luiLuiRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Set<String> set = new HashSet<String>();
        for (LuiLuiRelationInfo info : this.luiLuiRelations.values()) {
            if (info.getRelatedLuiId().equals(relatedLuiId)) {
                if (info.getTypeKey().equals(luiLuiRelationTypeKey)) {
                    set.add(info.getLuiId());
                }
            }
        }
        return new ArrayList(set);
    }

    @Override
    public List<LuiInfo> getRelatedLuisByLuiAndRelationType(String luiId, String luiLuiRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            return this.getLuisByIds(this.getLuiIdsByRelatedLuiAndRelationType(luiId, luiLuiRelationTypeKey, contextInfo), contextInfo);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    @Override
    public List<String> searchForLuiLuiRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LuiLuiRelationInfo> searchForLuiLuiRelations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateLuiLuiRelation(String validationTypeKey, String luiId, String relatedLuiId, String luiLuiRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luiLuiRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        LuiLuiRelationInfo copy = new LuiLuiRelationInfo(luiLuiRelationInfo);
        if (copy.getId() == null) {
            copy.setId(luiLuiRelations.size() + "");
        }
        luiLuiRelations.put(copy.getId(), copy);
        return new LuiLuiRelationInfo(copy);
    }

    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        if (!luiLuiRelations.containsKey(luiLuiRelationId)) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        LuiLuiRelationInfo copy = new LuiLuiRelationInfo(luiLuiRelationInfo);
        luiLuiRelations.put(luiLuiRelationId, copy);
        return new LuiLuiRelationInfo(copy);
    }

    @Override
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        this.getLuiLuiRelation(luiLuiRelationId, contextInfo);
        luiLuiRelations.remove(luiLuiRelationId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public LuiCapacityInfo getLuiCapacity(String luiCapacityId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getLuiCapacityIdsByType(String luiCapacityTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByIds(List<String> luiCapacityIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByLui(String luiId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LuiCapacityInfo> searchForLuiCapacities(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForLuiCapacityIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateLuiCapacity(String validationTypeKey, String luiCapacityTypeKey, LuiCapacityInfo luiCapacityInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public LuiCapacityInfo createLuiCapacity(String luiCapacityTypeKey, LuiCapacityInfo luiCapacityInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LuiCapacityInfo updateLuiCapacity(String luiCapacityId, LuiCapacityInfo luiCapacityInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo deleteLuiCapacity(String luiCapacityId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
