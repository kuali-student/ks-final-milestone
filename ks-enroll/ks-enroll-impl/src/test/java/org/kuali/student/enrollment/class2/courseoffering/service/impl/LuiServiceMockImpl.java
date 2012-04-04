/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author nwright
 */
public class LuiServiceMockImpl implements LuiService {

    private Map<String, LuiInfo> luis = new LinkedHashMap<String, LuiInfo>();
    private Map<String, LuiLuiRelationInfo> luiLuiRelations = new LinkedHashMap<String, LuiLuiRelationInfo>();

    @Override
    public LuiInfo createLui(String cluId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiInfo copy = new LuiInfo(luiInfo);
        if (copy.getId() == null) {
            copy.setId(luis.size() + "");
        }
        luis.put(copy.getId(), copy);
        return new LuiInfo(copy);
    }

    @Override
    public LuiCapacityInfo createLuiCapacity(LuiCapacityInfo luiCapacityInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luLuRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) throws AlreadyExistsException, CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiLuiRelationInfo copy = new LuiLuiRelationInfo(luiLuiRelationInfo);
        if (copy.getId() == null) {
            copy.setId(luiLuiRelations.size() + "");
        }
        luiLuiRelations.put(copy.getId(), copy);
        return new LuiLuiRelationInfo(copy);
    }

    @Override
    public StatusInfo deleteLui(String luiId, ContextInfo context) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        this.getLui(luiId, context);
        luis.remove(luiId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo deleteLuiCapacity(String luiCapacityId, ContextInfo context) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        this.getLuiLuiRelation(luiLuiRelationId, context);
        luiLuiRelations.remove(luiLuiRelationId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public LuiInfo getLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        if (!luis.containsKey(luiId)) {
            throw new DoesNotExistException(luiId);
        }
        return new LuiInfo (luis.get(luiId));
    }

    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByIds(List<String> luiCapacityIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LuiCapacityInfo getLuiCapacity(String luiCapacityId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getLuiCapacityIdsByType(String luiCapacityTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getLuiIdsByAtpAndType(String atpId, String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
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
    public List<String> getLuiIdsByCluId(String cluId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> list = new ArrayList<String>();
        for (LuiInfo info : this.luis.values()) {
            if (info.getAtpId().equals(cluId)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getLuiIdsByRelation(String relatedLuiId, String luLuRelationTypeKey, ContextInfo context) 
            throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> list = new ArrayList<String>();
        for (LuiLuiRelationInfo info : this.luiLuiRelations.values()) {
            if (info.getRelatedLuiId().equals(relatedLuiId)) {
                if (info.getTypeKey().equals(luLuRelationTypeKey)) {
                    list.add(info.getLuiId());
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getLuiIdsByType(String luiTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> list = new ArrayList<String>();
        for (LuiInfo info : this.luis.values()) {
            if (info.getTypeKey().equals(luiTypeKey)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getLuiIdsInAtpByCluId(String cluId, String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
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
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        if (!this.luiLuiRelations.containsKey(luiLuiRelationId)) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        return new LuiLuiRelationInfo(this.luiLuiRelations.get(luiLuiRelationId));
    }

    @Override
    public List<String> getLuiLuiRelationIdsByType(String luiLuiRelationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<String> list = new ArrayList<String>();
        for (LuiLuiRelationInfo info : this.luiLuiRelations.values()) {
            if (info.getTypeKey().equals(luiLuiRelationTypeKey)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIds(List<String> luiLuiRelationIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<LuiLuiRelationInfo> list = new ArrayList<LuiLuiRelationInfo>();
        for (LuiLuiRelationInfo info : this.luiLuiRelations.values()) {
            if (luiLuiRelationIds.contains(info.getId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<LuiLuiRelationInfo> list = new ArrayList<LuiLuiRelationInfo>();
        for (LuiLuiRelationInfo info : this.luiLuiRelations.values()) {
            if (info.getLuiId().equals(luiId)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<LuiInfo> getLuisByIds(List<String> luiIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<LuiInfo> list = new ArrayList<LuiInfo>();
        for (String id : luiIds) {
            list.add (this.getLui(id, context));
        }
        return list;
    }

    @Override
    public List<LuiInfo> getLuisByRelation(String relatedLuiId, String luLuRelationTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
            List<String> ids = this.getLuiIdsByRelation(relatedLuiId, luLuRelationTypeKey, context);
            return this.getLuisByIds(ids, context);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    @Override
    public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
            return this.getLuisByIds(this.getLuiIdsInAtpByCluId(cluId, atpId, context), context);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    @Override
    public List<String> getRelatedLuiIdsByLuiId(String luiId, String luLuRelationTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        Set<String> set = new HashSet<String>();
        for (LuiLuiRelationInfo info : this.luiLuiRelations.values()) {
            if (info.getLuiId().equals(luiId)) {
                if (info.getTypeKey().equals(luLuRelationTypeKey)) {
                    set.add(info.getRelatedLuiId());
                }
            }
        }
        return new ArrayList(set);
    }

    @Override
    public List<LuiInfo> getRelatedLuisByLuiId(String luiId, String luLuRelationTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
            return this.getLuisByIds(this.getRelatedLuiIdsByLuiId(luiId, luLuRelationTypeKey, context), context);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    @Override
    public List<LuiCapacityInfo> searchForLuiCapacities(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForLuiCapacityIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForLuiIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForLuiLuiRelationIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LuiLuiRelationInfo> searchForLuiLuiRelations(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LuiInfo> searchForLuis(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        if (!luis.containsKey(luiId)) {
            throw new DoesNotExistException(luiId);
        }
        LuiInfo copy = new LuiInfo(luiInfo);
        luis.put(luiId, copy);
        return new LuiInfo(copy);
    }

    @Override
    public LuiCapacityInfo updateLuiCapacity(String luiCapacityId, LuiCapacityInfo luiCapacityInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        if (!luiLuiRelations.containsKey(luiLuiRelationId)) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        LuiLuiRelationInfo copy = new LuiLuiRelationInfo(luiLuiRelationInfo);
        luiLuiRelations.put(luiLuiRelationId, copy);
        return new LuiLuiRelationInfo(copy);
    }

    @Override
    public LuiInfo updateLuiState(String luiId, String luState, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiInfo info = this.getLui(luiId, context);
        info.setStateKey(luState);
        try {
            return this.updateLui(luiId, info, context);
        } catch (VersionMismatchException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    @Override
    public List<ValidationResultInfo> validateLui(String validationType, LuiInfo luiInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateLuiCapacity(String validationType, LuiCapacityInfo luiCapacityInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateLuiLuiRelation(String validationType, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }
}
