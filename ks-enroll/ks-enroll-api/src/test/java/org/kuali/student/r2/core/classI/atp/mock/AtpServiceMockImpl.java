/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.r2.core.classI.atp.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.test.utilities.MockHelper;

/**
 * This is a mock memory based implementation for ATP service
 * 
 * @author Kuali Student Team (Kamal)
 */
public class AtpServiceMockImpl implements AtpService {

    private Map<String, AtpInfo> atpCache = new HashMap<String, AtpInfo>();
    private Map<String, MilestoneInfo> milestoneCache = new HashMap<String, MilestoneInfo>();
    private Map<String, AtpMilestoneRelationInfo> atpMilestoneRltnCache = new HashMap<String, AtpMilestoneRelationInfo>();
    private Map<String, AtpAtpRelationInfo> atpAtpRltnCache = new HashMap<String, AtpAtpRelationInfo>();

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        throw new UnsupportedOperationException();
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setKey(typeKey);
        typeInfo.setName(typeKey);
        return typeInfo;
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException();
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.r2.common.service.TypeService#getAllowedTypesForType(java.lang.String, java.lang.String,
     *      org.kuali.student.common.dto.ContextInfo)
     */
    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public List<String> getProcessByObjectType(String objectTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public AtpInfo getAtp(String atpKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        AtpInfo atp = atpCache.get(atpKey);
        if (null == atp) {
            throw new DoesNotExistException("No atp found for: " + atpKey);

        }
        return atp;
    }

    @Override
    public List<AtpInfo> getAtpsByDate(Date searchDate, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        List<AtpInfo> atpList = new ArrayList<AtpInfo>();

        Set<String> keys = atpCache.keySet();

        for (String key : keys) {
            AtpInfo atp = atpCache.get(key);
            if ((atp.getStartDate().before(searchDate) || atp.getStartDate().equals(searchDate)) && (atp.getEndDate().after(searchDate) || atp.getEndDate().equals(searchDate))) {
                atpList.add(atp);
            }
        }

        return atpList;

    }

    @Override
    public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<AtpInfo> atpList = new ArrayList<AtpInfo>();

        Set<String> keys = atpCache.keySet();

        for (String key : keys) {
            AtpInfo atp = atpCache.get(key);
            if (startDate.before(atp.getStartDate()) && endDate.after(atp.getEndDate())) {
                atpList.add(atp);
            }
        }

        return atpList;

    }

    @Override
    public List<String> getAtpKeysByType(String atpTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> atpKeyList = new ArrayList<String>();

        Set<String> keys = atpCache.keySet();

        for (String key : keys) {
            AtpInfo atp = atpCache.get(key);
            if (atp.getTypeKey().equalsIgnoreCase(atpTypeKey)) {
                atpKeyList.add(atp.getKey());
            }
        }

        return atpKeyList;
    }

    @Override
    public List<AtpInfo> getAtpsByKeyList(List<String> atpKeyList, ContextInfo context) throws InvalidParameterException, DoesNotExistException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpInfo> atpList = new ArrayList<AtpInfo>();

        for (String key : atpKeyList) {
            atpList.add(this.getAtp(key, context));
        }

        return atpList;
    }

    @Override
    public MilestoneInfo getMilestone(String milestoneKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        MilestoneInfo milestone = milestoneCache.get(milestoneKey);
        if (null == milestone) {
            throw new DoesNotExistException("No milestone found for: " + milestoneKey);
        }

        return milestone;
    }

    @Override
    public List<MilestoneInfo> getMilestonesByAtp(String atpKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<MilestoneInfo> milestoneList = new ArrayList<MilestoneInfo>();

        Set<String> keys = atpMilestoneRltnCache.keySet();

        Set<String> milestoneKeys = new HashSet<String>();

        for (String key : keys) {
            AtpMilestoneRelationInfo rltn = atpMilestoneRltnCache.get(key);
            if (atpKey.equalsIgnoreCase(rltn.getAtpKey()) && !milestoneKeys.contains(rltn.getMilestoneKey())) {
                milestoneKeys.add(rltn.getMilestoneKey());
                milestoneList.add(milestoneCache.get(rltn.getMilestoneKey()));
            }
        }

        return milestoneList;
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDates(Date startDate, Date endDate, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<MilestoneInfo> milestoneList = new ArrayList<MilestoneInfo>();

        Set<String> keys = milestoneCache.keySet();

        for (String key : keys) {
            MilestoneInfo milestone = milestoneCache.get(key);

            if (milestone.getIsDateRange()) {
                if ((startDate.before(milestone.getStartDate()) || startDate.equals(milestone.getStartDate())) && (endDate.after(milestone.getEndDate()) || endDate.equals(milestone.getEndDate()))) {
                    milestoneList.add(milestone);
                }
            } else {
                if ((startDate.before(milestone.getStartDate()) || startDate.equals(milestone.getStartDate()))) {
                    milestoneList.add(milestone);
                }
            }
        }

        return milestoneList;
    }

    @Override
    public List<MilestoneInfo> getMilestonesByKeyList(List<String> milestoneKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestoneList = new ArrayList<MilestoneInfo>();

        for (String key : milestoneKeyList) {
            milestoneList.add(this.getMilestone(key, context));
        }

        return milestoneList;
    }

    @Override
    public List<String> getMilestoneKeysByType(String milestoneTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> milestoneKeyList = new ArrayList<String>();

        Set<String> keys = milestoneCache.keySet();

        for (String key : keys) {
            MilestoneInfo milestone = milestoneCache.get(key);
            if (milestone.getTypeKey().equalsIgnoreCase(milestoneTypeKey)) {
                milestoneKeyList.add(milestone.getKey());
            }
        }

        return milestoneKeyList;
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDatesAndType(String milestoneTypeKey, Date startDate, Date endDate, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<MilestoneInfo> milestoneList = new ArrayList<MilestoneInfo>();

        List<MilestoneInfo> milestoneByDates = this.getMilestonesByDates(startDate, endDate, context);

        for (MilestoneInfo milestone : milestoneByDates) {
            if (milestone.getTypeKey().equalsIgnoreCase(milestoneTypeKey)) {
                milestoneList.add(milestone);
            }
        }

        return milestoneList;
    }

    @Override
    public List<ValidationResultInfo> validateAtp(String validationType, AtpInfo atpInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public AtpInfo createAtp(String atpKey, AtpInfo atpInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo existing = this.atpCache.get(atpKey);
        if (existing != null) {
            throw new AlreadyExistsException(atpKey);
        }

        MockHelper helper = new MockHelper();
        AtpInfo atp = new AtpInfo(atpInfo);
        atp.setMeta(helper.createMeta(context));
        this.atpCache.put(atpKey, atp);
        return atp;
    }

    @Override
    public AtpInfo updateAtp(String atpKey, AtpInfo atpInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        AtpInfo existing = this.atpCache.get(atpKey);
        if (existing == null) {
            throw new DoesNotExistException(atpKey);
        }
        if (!atpInfo.getMeta().getVersionInd().equals(existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().getUpdateId() + " on " + existing.getMeta().getUpdateId() + " with version of " + existing.getMeta().getVersionInd());
        }
        AtpInfo atp = new AtpInfo(atpInfo);
        atp.setMeta(new MockHelper().updateMeta(existing.getMeta(), context));
        // update attributes in order to be different than that in luiPersonRelationInfo
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (AttributeInfo att : atpInfo.getAttributes()) {
            atts.add(new AttributeInfo(att));
        }
        atp.setAttributes(atts);
        this.atpCache.put(atpKey, atp);

        return atp;
    }

    @Override
    public StatusInfo deleteAtp(String atpKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.atpCache.remove(atpKey) == null) {
            throw new DoesNotExistException(atpKey);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public List<ValidationResultInfo> validateMilestone(String validationType, MilestoneInfo milestoneInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public MilestoneInfo createMilestone(String milestoneKey, MilestoneInfo milestoneInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MilestoneInfo existing = this.milestoneCache.get(milestoneKey);
        if (existing != null) {
            throw new AlreadyExistsException(milestoneKey);
        }

        MockHelper helper = new MockHelper();
        MilestoneInfo mInfo = new MilestoneInfo(milestoneInfo);
        mInfo.setMeta(helper.createMeta(context));
        this.milestoneCache.put(milestoneKey, mInfo);
        return mInfo;
    }

    @Override
    public MilestoneInfo updateMilestone(String milestoneKey, MilestoneInfo milestoneInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        MilestoneInfo existing = this.milestoneCache.get(milestoneKey);
        if (existing == null) {
            throw new DoesNotExistException(milestoneKey);
        }
        if (!milestoneInfo.getMeta().getVersionInd().equals(existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().getUpdateId() + " on " + existing.getMeta().getUpdateId() + " with version of " + existing.getMeta().getVersionInd());
        }
        MilestoneInfo mInfo = new MilestoneInfo(milestoneInfo);
        mInfo.setMeta(new MockHelper().updateMeta(existing.getMeta(), context));
        this.milestoneCache.put(milestoneKey, mInfo);

        return mInfo;
    }

    @Override
    public StatusInfo deleteMilestone(String milestoneKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // Check to see if Milestone exists in a relationship
        // TODO: Put the check once methods to access milestons/atp using relations are added to the service
        if (this.milestoneCache.remove(milestoneKey) == null) {
            throw new DoesNotExistException(milestoneKey);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public AtpAtpRelationInfo getAtpAtpRelation(String atpAtpRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpAtpRelationInfo atpRltn = atpAtpRltnCache.get(atpAtpRelationId);
        if (null == atpRltn) {
            throw new DoesNotExistException("No atp atp relationship found for: " + atpAtpRelationId);
        }

        return atpRltn;
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByIdList(List<String> atpAtpRelationIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpAtpRelationInfo> atpRltnList = new ArrayList<AtpAtpRelationInfo>();

        for (String id : atpAtpRelationIdList) {
            atpRltnList.add(this.getAtpAtpRelation(id, context));
        }

        return atpRltnList;
    }

    @Override
    public List<String> getAtpAtpRelationIdsByType(String atpAtpRelationTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> atpRltnList = new ArrayList<String>();

        Set<String> atpRltnIds = atpAtpRltnCache.keySet();

        for (String id : atpRltnIds) {
            AtpAtpRelationInfo rltn = atpAtpRltnCache.get(id);
            if (rltn.getTypeKey().equalsIgnoreCase(atpAtpRelationTypeKey)) {
                atpRltnList.add(id);
            }
        }

        return atpRltnList;
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(String atpKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpAtpRelationInfo> atpRltnList = new ArrayList<AtpAtpRelationInfo>();

        Set<String> atpRltnIds = atpAtpRltnCache.keySet();

        for (String id : atpRltnIds) {
            AtpAtpRelationInfo rltn = atpAtpRltnCache.get(id);
            if (rltn.getAtpKey().equalsIgnoreCase(atpKey) || rltn.getRelatedAtpKey().equalsIgnoreCase(atpKey)) {
                atpRltnList.add(rltn);
            }
        }

        return atpRltnList;
    }

    @Override
    public List<ValidationResultInfo> validateAtpAtpRelation(String validationType, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public AtpAtpRelationInfo createAtpAtpRelation(AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MockHelper helper = new MockHelper();
        AtpAtpRelationInfo aarInfo = new AtpAtpRelationInfo(atpAtpRelationInfo);
        aarInfo.setMeta(helper.createMeta(context));
        aarInfo.setId(UUIDHelper.genStringUUID());
        this.atpAtpRltnCache.put(aarInfo.getId(), aarInfo);
        return aarInfo;
    }

    @Override
    public AtpAtpRelationInfo updateAtpAtpRelation(String atpAtpRelationId, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        AtpAtpRelationInfo existing = this.atpAtpRltnCache.get(atpAtpRelationId);
        if (existing == null) {
            throw new DoesNotExistException(atpAtpRelationId);
        }
        if (!atpAtpRelationInfo.getMeta().getVersionInd().equals(existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().getUpdateId() + " on " + existing.getMeta().getUpdateId() + " with version of " + existing.getMeta().getVersionInd());
        }
        AtpAtpRelationInfo aari = new AtpAtpRelationInfo(atpAtpRelationInfo);
        aari.setMeta(new MockHelper().updateMeta(existing.getMeta(), context));
        // update attributes in order to be different than that in luiPersonRelationInfo
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (AttributeInfo att : atpAtpRelationInfo.getAttributes()) {
            atts.add(new AttributeInfo(att));
        }
        aari.setAttributes(atts);
        this.atpAtpRltnCache.put(atpAtpRelationId, aari);

        return aari;
    }

    @Override
    public StatusInfo deleteAtpAtpRelation(String atpAtpRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.atpAtpRltnCache.remove(atpAtpRelationId) == null) {
            throw new DoesNotExistException(atpAtpRelationId);
        }
        StatusInfo status =new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public AtpMilestoneRelationInfo getAtpMilestoneRelation(String atpMilestoneRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpMilestoneRelationInfo atpM = atpMilestoneRltnCache.get(atpMilestoneRelationId);
        if (null == atpM) {
            throw new DoesNotExistException("No atp milestone rltn found for: " + atpMilestoneRelationId);
        }

        return atpM;
    }

    @Override
    public List<AtpMilestoneRelationInfo> getAtpMilestoneRelationsByAtp(String atpKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpMilestoneRelationInfo> atpMilestoneList = new ArrayList<AtpMilestoneRelationInfo>();

        Set<String> keys = atpMilestoneRltnCache.keySet();

        for (String key : keys) {
            AtpMilestoneRelationInfo atpM = atpMilestoneRltnCache.get(key);
            if (atpM.getAtpKey().equalsIgnoreCase(atpKey)) {
                atpMilestoneList.add(atpM);
            }
        }

        return atpMilestoneList;
    }

    @Override
    public List<AtpMilestoneRelationInfo> getAtpMilestoneRelationsByMilestone(String milestoneKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpMilestoneRelationInfo> atpMilestoneList = new ArrayList<AtpMilestoneRelationInfo>();

        Set<String> keys = atpMilestoneRltnCache.keySet();

        for (String key : keys) {
            AtpMilestoneRelationInfo atpM = atpMilestoneRltnCache.get(key);
            if (atpM.getMilestoneKey().equalsIgnoreCase(milestoneKey)) {
                atpMilestoneList.add(atpM);
            }
        }

        return atpMilestoneList;
    }

    @Override
    public List<ValidationResultInfo> validateAtpMilestoneRelation(String validationType, AtpMilestoneRelationInfo atpMilestoneRelationInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public AtpMilestoneRelationInfo createAtpMilestoneRelation(AtpMilestoneRelationInfo atpMilestoneRelationInfo, ContextInfo context) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpMilestoneRelationInfo existing = this.atpMilestoneRltnCache.get(atpMilestoneRelationInfo.getId());
        if (existing != null) {
            throw new AlreadyExistsException(atpMilestoneRelationInfo.getId());
        }

        MockHelper helper = new MockHelper();
        AtpMilestoneRelationInfo amrInfo = new AtpMilestoneRelationInfo(atpMilestoneRelationInfo);
        amrInfo.setMeta(helper.createMeta(context));
        this.atpMilestoneRltnCache.put(amrInfo.getId(), amrInfo);
        return amrInfo;
    }

    @Override
    public AtpMilestoneRelationInfo updateAtpMilestoneRelation(String atpMilestoneRelationId, AtpMilestoneRelationInfo atpMilestoneRelationInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        AtpMilestoneRelationInfo existing = this.atpMilestoneRltnCache.get(atpMilestoneRelationId);
        if (existing == null) {
            throw new DoesNotExistException(atpMilestoneRelationId);
        }
        if (!atpMilestoneRelationInfo.getMeta().getVersionInd().equals(existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().getUpdateId() + " on " + existing.getMeta().getUpdateId() + " with version of " + existing.getMeta().getVersionInd());
        }
        AtpMilestoneRelationInfo amri = new AtpMilestoneRelationInfo(atpMilestoneRelationInfo);
        amri.setMeta(new MockHelper().updateMeta(existing.getMeta(), context));
        // update attributes in order to be different than that in luiPersonRelationInfo
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (AttributeInfo att : atpMilestoneRelationInfo.getAttributes()) {
            atts.add(new AttributeInfo(att));
        }
        amri.setAttributes(atts);
        this.atpMilestoneRltnCache.put(atpMilestoneRelationId, amri);

        return amri;
    }

    @Override
    public StatusInfo deleteAtpMilestoneRelation(String atpMilestoneRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // Check to see if Milestone exists in a relationship
        // TODO: Put the check once methods to access milestons/atp using relations are added to the service
        if (this.atpMilestoneRltnCache.remove(atpMilestoneRelationId) == null) {
            throw new DoesNotExistException(atpMilestoneRelationId);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public List<AtpMilestoneRelationInfo> getAtpMilestoneRelationsByIdList(List<String> atpMilestoneRelationIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpMilestoneRelationInfo> atpRltnList = new ArrayList<AtpMilestoneRelationInfo>();

        for (String id : atpMilestoneRelationIdList) {
            atpRltnList.add(this.getAtpMilestoneRelation(id, context));
        }

        return atpRltnList;
    }

    @Override
    public List<String> getAtpMilestoneRelationIdsByType(String atpMilestoneRelationTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> atpRltnList = new ArrayList<String>();

        Set<String> atpRltnIds = atpMilestoneRltnCache.keySet();

        for (String id : atpRltnIds) {
            AtpMilestoneRelationInfo rltn = atpMilestoneRltnCache.get(id);
            if (rltn.getTypeKey().equalsIgnoreCase(atpMilestoneRelationTypeKey)) {
                atpRltnList.add(id);
            }
        }

        return atpRltnList;
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtpAndRelationType(String atpKey, String relationType,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
}
