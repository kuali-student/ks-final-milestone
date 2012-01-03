/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.exemption.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.ExemptionServiceConstants;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.dto.ExemptionRequestInfo;

/**
 *
 * @author nwright
 */
public class ExemptionServiceMockImpl implements ExemptionService {

    private Map<String, ExemptionRequestInfo> exemptionRequests = new HashMap<String, ExemptionRequestInfo>();
    private Map<String, ExemptionInfo> exemptions = new HashMap<String, ExemptionInfo>();

    @Override
    public StatusInfo addUseToExemption(String exemptionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
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

    @Override
    public ExemptionInfo createExemption(String exemptionRequestId, ExemptionInfo exemptionInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ExemptionInfo copy = new ExemptionInfo(exemptionInfo);
        copy.setId(exemptions.size() + "");
        copy.setMeta(newMeta(context));
        exemptions.put(copy.getId(), copy);
        return new ExemptionInfo(copy);
    }

    @Override
    public ExemptionRequestInfo createExemptionRequest(ExemptionRequestInfo exemptionRequestInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ExemptionRequestInfo copy = new ExemptionRequestInfo(exemptionRequestInfo);
        copy.setId(exemptionRequests.size() + "");
        copy.setMeta(newMeta(context));
        this.exemptionRequests.put(copy.getId(), copy);
        return new ExemptionRequestInfo(copy);
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo deleteExemption(String exemptionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.exemptions.remove(exemptionId) == null) {
            throw new DoesNotExistException(exemptionId);
        }
        return newStatus();
    }

    @Override
    public StatusInfo deleteExemptionRequest(String exemptionRequestId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.exemptionRequests.remove(exemptionRequestId) == null) {
            throw new DoesNotExistException(exemptionRequestId);
        }
        return newStatus();
    }

    @Override
    public List<ExemptionInfo> getActiveExemptionsByTypeForPerson(String typeKey, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionInfo> list = new ArrayList<ExemptionInfo>();
        for (ExemptionInfo info : this.getExemptionsByTypeForPerson(typeKey, personId, context)) {
            if (info.getStateKey().equals(ExemptionServiceConstants.EXEMPTION_ACTIVE_STATE_KEY)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<ExemptionInfo> getActiveExemptionsForPerson(String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionInfo> list = new ArrayList<ExemptionInfo>();
        for (ExemptionInfo info : this.getExemptionsForPerson(personId, context)) {
            if (info.getStateKey().equals(ExemptionServiceConstants.EXEMPTION_ACTIVE_STATE_KEY)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public ExemptionInfo getExemption(String exemptionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ExemptionInfo info = this.exemptions.get(exemptionId);
        if (info == null) {
            throw new DoesNotExistException(exemptionId);
        }
        return new ExemptionInfo(info);
    }

    @Override
    public List<String> getExemptionIdsByType(String exemptionTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (ExemptionInfo info : this.exemptions.values()) {
            if (info.getTypeKey().equals(exemptionTypeKey)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public ExemptionRequestInfo getExemptionRequest(String exemptionRequestId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ExemptionRequestInfo info = this.exemptionRequests.get(exemptionRequestId);
        if (info == null) {
            throw new DoesNotExistException(exemptionRequestId);
        }
        return new ExemptionRequestInfo(info);
    }

    @Override
    public List<String> getExemptionRequestIdsByCheck(String checkKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (ExemptionRequestInfo info : this.exemptionRequests.values()) {
            if (info.getCheckKey().equals(checkKey)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getExemptionRequestIdsByType(String exemptionRequestTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (ExemptionRequestInfo info : this.exemptionRequests.values()) {
            if (info.getTypeKey().equals(exemptionRequestTypeKey)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<ExemptionRequestInfo> getExemptionRequestsByIdList(List<String> exemptionRequestIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionRequestInfo> list = new ArrayList<ExemptionRequestInfo>(exemptionRequestIdList.size());
        for (String id : exemptionRequestIdList) {
            list.add(this.getExemptionRequest(id, context));
        }
        return list;
    }

    @Override
    public List<ExemptionInfo> getExemptionsByIdList(List<String> exemptionIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionInfo> list = new ArrayList<ExemptionInfo>(exemptionIdList.size());
        for (String id : exemptionIdList) {
            list.add(this.getExemption(id, context));
        }
        return list;
    }

    @Override
    public List<ExemptionInfo> getExemptionsByTypeForPerson(String typeKey, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionInfo> list = new ArrayList<ExemptionInfo>();
        for (ExemptionInfo info : this.exemptions.values()) {
            if (info.getTypeKey().equals(typeKey)) {
                if (info.getExemptedPersonId().equals(personId)) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    @Override
    public List<ExemptionInfo> getExemptionsForPerson(String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionInfo> list = new ArrayList<ExemptionInfo>();
        for (ExemptionInfo info : this.exemptions.values()) {
            if (info.getExemptedPersonId().equals(personId)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<ExemptionRequestInfo> getRequestsByTypeForPerson(String typeKey, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionRequestInfo> list = new ArrayList<ExemptionRequestInfo>();
        for (ExemptionRequestInfo info : this.getRequestsForPerson(personId, context)) {
            if (info.getTypeKey().equals(typeKey)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<ExemptionRequestInfo> getRequestsForPerson(String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionRequestInfo> list = new ArrayList<ExemptionRequestInfo>();
        for (ExemptionRequestInfo info : this.exemptionRequests.values()) {
            if (info.getPersonId().equals(personId)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public ExemptionInfo retrieveDateExemption(String checkKey, String personId, String milestoneId, String qualifierTypeKey, String qualifierId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExemptionInfo retrieveMilestoneExemption(String checkKey, String personId, String milestoneId, String qualifierTypeKey, String qualifierId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

    @Override
    public ExemptionInfo updateExemption(String exemptionId, ExemptionInfo exemptionInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        ExemptionInfo copy = new ExemptionInfo(exemptionInfo);
        ExemptionInfo old = this.getExemption(exemptionId, context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException (old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));        
        this.exemptions.put(exemptionInfo.getId(), copy);
        return new ExemptionInfo(copy);
    }

    @Override
    public ExemptionRequestInfo updateExemptionRequest(String exemptionRequestId, ExemptionRequestInfo exemptionRequestInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        ExemptionRequestInfo copy = new ExemptionRequestInfo(exemptionRequestInfo);
        ExemptionRequestInfo old = this.getExemptionRequest(exemptionRequestId, context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException (old.getMeta().getVersionInd());
        }        
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.exemptionRequests.put(exemptionRequestInfo.getId(), copy);
        return new ExemptionRequestInfo(copy);
    }

    @Override
    public List<ValidationResultInfo> validateExemption(String validationTypeKey, ExemptionInfo exemptionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateExemptionRequest(String validationTypeKey, ExemptionRequestInfo exemptionRequestInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getProcessByObjectType(String refObjectUri, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
