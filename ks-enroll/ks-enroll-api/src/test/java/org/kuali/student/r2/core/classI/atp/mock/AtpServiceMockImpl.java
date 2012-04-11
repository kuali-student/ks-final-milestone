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

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.mock.utilities.MockHelper;

import javax.jws.WebParam;
import java.util.*;

/**
 * This is a mock memory based implementation for ATP service
 * 
 * @author Kuali Student Team (Kamal)
 */
public class AtpServiceMockImpl implements AtpService {

    private Map<String, AtpInfo> atpCache = new HashMap<String, AtpInfo>();
    private Map<String, MilestoneInfo> milestoneCache = new HashMap<String, MilestoneInfo>();
    private Map<String, AtpAtpRelationInfo> atpAtpRltnCache = new HashMap<String, AtpAtpRelationInfo>();
    private Map<String, Set<String>> milestonesForAtp = new HashMap<String, Set<String>>();

    @Override
    public AtpInfo getAtp(String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        AtpInfo atp = atpCache.get(atpId);
        if (null == atp) {
            throw new DoesNotExistException("No atp found for: " + atpId);

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
    public List<String> getAtpIdsByType(String atpTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> atpIds = new ArrayList<String>();

        Set<String> keys = atpCache.keySet();

        for (String key : keys) {
            AtpInfo atp = atpCache.get(key);
            if (atp.getTypeKey().equalsIgnoreCase(atpTypeKey)) {
                atpIds.add(atp.getId());
            }
        }

        return atpIds;
    }

    @Override
    public List<AtpInfo> getAtpsByCode(String code, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO
        return new ArrayList<AtpInfo>();
    }

    @Override
    public List<AtpInfo> getAtpsByIds(List<String> atpIds, ContextInfo context) throws InvalidParameterException, DoesNotExistException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<AtpInfo> atpList = new ArrayList<AtpInfo>();

        for (String key : atpIds) {
            atpList.add(this.getAtp(key, context));
        }

        return atpList;
    }

    @Override
    public MilestoneInfo getMilestone(String milestoneId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        MilestoneInfo milestone = milestoneCache.get(milestoneId);
        if (null == milestone) {
            throw new DoesNotExistException("No milestone found for: " + milestoneId);
        }

        return milestone;
    }

    @Override
    public List<MilestoneInfo> getMilestonesForAtp(String atpId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<MilestoneInfo> list = new ArrayList<MilestoneInfo>();
        if (!this.milestonesForAtp.containsKey(atpId)) {
            this.milestonesForAtp.put(atpId, new HashSet<String>());
        }
        Set<String> milestoneIds = this.milestonesForAtp.get(atpId);
        for (String id : milestoneIds) {
            MilestoneInfo info;
            try {
                info = this.getMilestone(id, context);
            } catch (DoesNotExistException ex) {
                throw new OperationFailedException("id in set not in milestone" + id, ex);
            }
            list.add(info);
        }
        return list;
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDatesForAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null; // To change body of implemented methods use File |
        // Settings | File Templates.
    }

    @Override
    public List<MilestoneInfo> getMilestonesByTypeForAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "milestoneTypeKey") String milestoneTypeKey,
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
    public List<MilestoneInfo> getMilestonesByIds(List<String> milestoneIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestoneList = new ArrayList<MilestoneInfo>();

        for (String key : milestoneIds) {
            milestoneList.add(this.getMilestone(key, context));
        }

        return milestoneList;
    }

    @Override
    public List<String> getMilestoneIdsByType(String milestoneTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> milestoneIds = new ArrayList<String>();

        Set<String> keys = milestoneCache.keySet();

        for (String key : keys) {
            MilestoneInfo milestone = milestoneCache.get(key);
            if (milestone.getTypeKey().equalsIgnoreCase(milestoneTypeKey)) {
                milestoneIds.add(milestone.getId());
            }
        }

        return milestoneIds;
    }

    @Override
    public List<String> searchForAtpIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
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
    public AtpInfo createAtp(String atpTypeKey,
            AtpInfo atpInfo,
            ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        MockHelper helper = new MockHelper();
        AtpInfo atp = new AtpInfo(atpInfo);
        atp.setMeta(helper.createMeta(context));
        atp.setId(UUIDHelper.genStringUUID());
        this.atpCache.put(atp.getId(), atp);
        return new AtpInfo(atp);
    }

    @Override
    public AtpInfo updateAtp(String atpId, AtpInfo atpInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        AtpInfo existing = this.atpCache.get(atpId);
        if (existing == null) {
            throw new DoesNotExistException(atpId);
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
        this.atpCache.put(atpId, atp);

        return new AtpInfo(atp);
    }

    @Override
    public StatusInfo deleteAtp(String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.atpCache.remove(atpId) == null) {
            throw new DoesNotExistException(atpId);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public List<MilestoneInfo> getImpactedMilestones(String milestoneId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<MilestoneInfo>();
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
    public MilestoneInfo createMilestone(String milestoneTypeKey,
            MilestoneInfo milestoneInfo, ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException,
            MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        MilestoneInfo existing = this.milestoneCache.get(milestoneInfo.getId());
        if (existing != null) {
            throw new DataValidationErrorException(milestoneInfo.getId());
        }

        MockHelper helper = new MockHelper();
        MilestoneInfo mInfo = new MilestoneInfo(milestoneInfo);
        mInfo.setMeta(helper.createMeta(context));
        this.milestoneCache.put(milestoneInfo.getId(), mInfo);
        return new MilestoneInfo(mInfo);
    }

    @Override
    public MilestoneInfo updateMilestone(String milestoneId, MilestoneInfo milestoneInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        MilestoneInfo existing = this.milestoneCache.get(milestoneId);
        if (existing == null) {
            throw new DoesNotExistException(milestoneId);
        }
        if (!milestoneInfo.getMeta().getVersionInd().equals(existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().getUpdateId() + " on " + existing.getMeta().getUpdateId() + " with version of " + existing.getMeta().getVersionInd());
        }
        MilestoneInfo mInfo = new MilestoneInfo(milestoneInfo);
        mInfo.setMeta(new MockHelper().updateMeta(existing.getMeta(), context));
        this.milestoneCache.put(milestoneId, mInfo);

        return new MilestoneInfo(mInfo);
    }

    @Override
    public StatusInfo deleteMilestone(String milestoneId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // Check to see if Milestone exists in a relationship
        // TODO: Put the check once methods to access milestons/atp using
        // relations are added to the service
        if (this.milestoneCache.remove(milestoneId) == null) {
            throw new DoesNotExistException(milestoneId);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public MilestoneInfo calculateMilestone(String milestoneId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getMilestone(milestoneId, contextInfo);
    }

    @Override
    public StatusInfo addMilestoneToAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (!this.milestonesForAtp.containsKey(atpId)) {
            this.milestonesForAtp.put(atpId, new HashSet<String>());
        }
        Set<String> milestones = this.milestonesForAtp.get(atpId);
        if (milestones.contains(milestoneId)) {
            throw new AlreadyExistsException();
        }
        milestones.add(milestoneId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo removeMilestoneFromAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!this.milestonesForAtp.containsKey(atpId)) {
            this.milestonesForAtp.put(atpId, new HashSet<String>());
        }
        Set<String> milestones = this.milestonesForAtp.get(atpId);
        if (!milestones.contains(milestoneId)) {
            throw new DoesNotExistException();
        }
        milestones.remove(milestoneId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public AtpAtpRelationInfo getAtpAtpRelation(String atpAtpRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        AtpAtpRelationInfo atpRltn = atpAtpRltnCache.get(atpAtpRelationId);
        if (null == atpRltn) {
            throw new DoesNotExistException("No atp atp relationship found for: " + atpAtpRelationId);
        }

        return new AtpAtpRelationInfo(atpRltn);
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
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(String atpId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<AtpAtpRelationInfo> atpRltnList = new ArrayList<AtpAtpRelationInfo>();

        Set<String> atpRltnIds = atpAtpRltnCache.keySet();

        for (String id : atpRltnIds) {
            AtpAtpRelationInfo rltn = atpAtpRltnCache.get(id);
            if (rltn.getAtpId().equalsIgnoreCase(atpId) || rltn.getRelatedAtpId().equalsIgnoreCase(atpId)) {
                atpRltnList.add(new AtpAtpRelationInfo(rltn));
            }
        }

        return atpRltnList;
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtps(@WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList();
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByTypeAndAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "atpRelationTypeKey") String atpRelationTypeKey,
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
    public List<ValidationResultInfo> validateAtpAtpRelation(String validationTypeKey, String atpId, String atpPeerId, String atpAtpRelationTypeKey, AtpAtpRelationInfo atpAtpRelationInfo,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public AtpAtpRelationInfo createAtpAtpRelation(String atpId,
            String relatedAtpId,
            String atpAtpRelationTypeKey,
            AtpAtpRelationInfo atpAtpRelationInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
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
