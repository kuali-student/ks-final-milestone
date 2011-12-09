/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.core.classI.atp.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;

import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;

import org.kuali.student.test.utilities.MockHelper;

import javax.jws.WebParam;

/**
 * This is a mock memory based implementation for ATP service
 * 
 * @author Kuali Student Team (Kamal)
 */
public class AtpServiceMockImpl implements AtpService {

    private Map<String, AtpInfo> atpCache = new HashMap<String, AtpInfo>();
    private Map<String, MilestoneInfo> milestoneCache = new HashMap<String, MilestoneInfo>();
    private Map<String, AtpAtpRelationInfo> atpAtpRltnCache = new HashMap<String, AtpAtpRelationInfo>();

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        return new ArrayList<String>();
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        return null;
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
        return new ArrayList<TypeInfo>();
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return new ArrayList<TypeInfo>();
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return new ArrayList<TypeTypeRelationInfo>();
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<String> getProcessByObjectType(String objectTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<String>();
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<StateInfo>();
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<StateInfo>();
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return null;
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
    public List<AtpInfo> getAtpsByDateAndType(Date searchDate, String searchTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<AtpInfo> atpList = new ArrayList<AtpInfo>();
        for (AtpInfo atp : this.getAtpsByDate(searchDate, context)) {
            if (searchTypeKey.equals(atp.getTypeKey())) {
                atpList.add(atp);
            }
        }
        return atpList;
    }

    @Override
    public List<AtpInfo> getAtpsByDatesAndType(Date searchDate, Date endDate, String searchTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<AtpInfo> atpList = new ArrayList<AtpInfo>();
        for (AtpInfo atp : this.getAtpsByDates(searchDate, endDate, context)) {
            if (searchTypeKey.equals(atp.getTypeKey())) {
                atpList.add(atp);
            }
        }
        return atpList;
    }

    @Override
    public List<AtpInfo> getAtpsByStartDateRange(Date searchDateRangeStart, Date searchDateRangeEnd, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<AtpInfo> atpList = new ArrayList<AtpInfo>();
        for (AtpInfo atp : atpCache.values()) {
            if (searchDateRangeStart.before(atp.getStartDate()) && searchDateRangeEnd.after(atp.getStartDate())) {
                atpList.add(atp);
            }
        }
        return atpList;
    }

    @Override
    public List<AtpInfo> getAtpsByStartDateRangeAndType(Date searchDateRangeStart, Date searchDateRangeEnd, String searchTypeKey, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpInfo> atpList = new ArrayList<AtpInfo>();
        for (AtpInfo atp : this.getAtpsByStartDateRange(searchDateRangeStart, searchDateRangeEnd, context)) {
            if (searchTypeKey.equals(atp.getTypeKey())) {
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
    public List<AtpInfo> getAtpsByKeys(List<String> atpKeyList, ContextInfo context) throws InvalidParameterException, DoesNotExistException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
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
    public List<MilestoneInfo> getMilestonesForAtp(String atpKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<MilestoneInfo> milestoneList = new ArrayList<MilestoneInfo>();
        return milestoneList;
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDatesForAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null; // To change body of implemented methods use File |
                     // Settings | File Templates.
    }

    @Override
    public List<MilestoneInfo> getMilestonesByTypeForAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "milestoneTypeKey") String milestoneTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null; // To change body of implemented methods use File |
                     // Settings | File Templates.
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
    public List<MilestoneInfo> getMilestonesByIds(List<String> milestoneKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestoneList = new ArrayList<MilestoneInfo>();

        for (String key : milestoneKeyList) {
            milestoneList.add(this.getMilestone(key, context));
        }

        return milestoneList;
    }

    @Override
    public List<String> getMilestoneIdsByType(String milestoneTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
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
    public List<String> searchForAtpKeys(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<String>();
    }

    @Override
    public List<AtpInfo> searchForAtps(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<AtpInfo>();
    }

    public List<ValidationResultInfo> validateAtp(String validationType, String atpTypeKey, AtpInfo atpInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public AtpInfo createAtp(String atpKey, AtpInfo atpInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
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
    public AtpInfo updateAtp(String atpKey, AtpInfo atpInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        AtpInfo existing = this.atpCache.get(atpKey);
        if (existing == null) {
            throw new DoesNotExistException(atpKey);
        }
        if (!atpInfo.getMeta().getVersionInd().equals(existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().getUpdateId() + " on " + existing.getMeta().getUpdateId() + " with version of " + existing.getMeta().getVersionInd());
        }
        AtpInfo atp = new AtpInfo(atpInfo);
        atp.setMeta(new MockHelper().updateMeta(existing.getMeta(), context));
        // update attributes in order to be different than that in
        // luiPersonRelationInfo
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (AttributeInfo att : atpInfo.getAttributes()) {
            atts.add(new AttributeInfo(att));
        }
        atp.setAttributes(atts);
        this.atpCache.put(atpKey, atp);

        return atp;
    }

    @Override
    public StatusInfo deleteAtp(String atpKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.atpCache.remove(atpKey) == null) {
            throw new DoesNotExistException(atpKey);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public List<String> searchForMilestoneIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<String>();
    }

    @Override
    public List<MilestoneInfo> searchForMilestones(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<MilestoneInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateMilestone(String validationType, MilestoneInfo milestoneInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public MilestoneInfo createMilestone(MilestoneInfo milestoneInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        MilestoneInfo existing = this.milestoneCache.get(milestoneInfo.getKey());
        if (existing != null) {
            throw new DataValidationErrorException(milestoneInfo.getKey());
        }

        MockHelper helper = new MockHelper();
        MilestoneInfo mInfo = new MilestoneInfo(milestoneInfo);
        mInfo.setMeta(helper.createMeta(context));
        this.milestoneCache.put(milestoneInfo.getKey(), mInfo);
        return mInfo;
    }

    @Override
    public MilestoneInfo updateMilestone(String milestoneKey, MilestoneInfo milestoneInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
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
    public StatusInfo deleteMilestone(String milestoneKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // Check to see if Milestone exists in a relationship
        // TODO: Put the check once methods to access milestons/atp using
        // relations are added to the service
        if (this.milestoneCache.remove(milestoneKey) == null) {
            throw new DoesNotExistException(milestoneKey);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo addMilestoneToAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null; // To change body of implemented methods use File |
                     // Settings | File Templates.
    }

    @Override
    public StatusInfo removeMilestoneFromAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null; // To change body of implemented methods use File |
                     // Settings | File Templates.
    }

    @Override
    public AtpAtpRelationInfo getAtpAtpRelation(String atpAtpRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        AtpAtpRelationInfo atpRltn = atpAtpRltnCache.get(atpAtpRelationId);
        if (null == atpRltn) {
            throw new DoesNotExistException("No atp atp relationship found for: " + atpAtpRelationId);
        }

        return atpRltn;
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByIds(List<String> atpAtpRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<AtpAtpRelationInfo> atpRltnList = new ArrayList<AtpAtpRelationInfo>();

        for (String id : atpAtpRelationIds) {
            atpRltnList.add(this.getAtpAtpRelation(id, contextInfo));
        }

        return atpRltnList;
    }

    @Override
    public List<String> getAtpAtpRelationIdsByType(String atpAtpRelationTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
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
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(String atpKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
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
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtps(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList();
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByTypeAndAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "atpRelationTypeKey") String atpRelationTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList();
    }

    @Override
    public List<String> searchForAtpAtpRelationIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<String>();
    }

    @Override
    public List<AtpAtpRelationInfo> searchForAtpAtpRelations(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<AtpAtpRelationInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateAtpAtpRelation(String validationTypeKey, String atpKey, String atpPeerKey, String atpAtpRelationTypeKey, AtpAtpRelationInfo atpAtpRelationInfo,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public AtpAtpRelationInfo createAtpAtpRelation(String atpKey, String atpPeerKey, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo contextInfo) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        MockHelper helper = new MockHelper();
        AtpAtpRelationInfo aarInfo = new AtpAtpRelationInfo(atpAtpRelationInfo);
        aarInfo.setMeta(helper.createMeta(contextInfo));
        aarInfo.setId(UUIDHelper.genStringUUID());
        this.atpAtpRltnCache.put(aarInfo.getId(), aarInfo);
        return aarInfo;
    }

    @Override
    public AtpAtpRelationInfo updateAtpAtpRelation(String atpAtpRelationId, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        AtpAtpRelationInfo existing = this.atpAtpRltnCache.get(atpAtpRelationId);
        if (existing == null) {
            throw new DoesNotExistException(atpAtpRelationId);
        }
        if (!atpAtpRelationInfo.getMeta().getVersionInd().equals(existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().getUpdateId() + " on " + existing.getMeta().getUpdateId() + " with version of " + existing.getMeta().getVersionInd());
        }
        AtpAtpRelationInfo aari = new AtpAtpRelationInfo(atpAtpRelationInfo);
        aari.setMeta(new MockHelper().updateMeta(existing.getMeta(), context));
        // update attributes in order to be different than that in
        // luiPersonRelationInfo
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (AttributeInfo att : atpAtpRelationInfo.getAttributes()) {
            atts.add(new AttributeInfo(att));
        }
        aari.setAttributes(atts);
        this.atpAtpRltnCache.put(atpAtpRelationId, aari);

        return aari;
    }

    @Override
    public StatusInfo deleteAtpAtpRelation(String atpAtpRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.atpAtpRltnCache.remove(atpAtpRelationId) == null) {
            throw new DoesNotExistException(atpAtpRelationId);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

}
