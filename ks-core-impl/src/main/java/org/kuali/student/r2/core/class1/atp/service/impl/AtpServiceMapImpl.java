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
package org.kuali.student.r2.core.class1.atp.service.impl;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchManager;
import org.kuali.student.r2.core.search.service.SearchService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a mock memory based implementation for ATP service
 *
 * @author Kuali Student Team (Kamal)
 */
public class AtpServiceMapImpl implements AtpService, MockService {

    private Map<String, AtpInfo> atps = new HashMap<String, AtpInfo>();
    private Map<String, MilestoneInfo> milestones = new HashMap<String, MilestoneInfo>();
    private Map<String, AtpAtpRelationInfo> atpAtpRelations = new HashMap<String, AtpAtpRelationInfo>();
    private Map<String, Set<String>> milestonesForAtp = new HashMap<String, Set<String>>();
    private SearchManager searchManager;
    private SearchService searchDispatcher;

    @Override
    public void clear() {

        this.atpAtpRelations.clear();
        this.atps.clear();
        this.milestones.clear();
        this.milestonesForAtp.clear();
    }

    @Override
    public AtpInfo getAtp(String atpId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        AtpInfo atp = atps.get(atpId);
        if (null == atp) {
            throw new DoesNotExistException("No atp found for: " + atpId);

        }
        return new AtpInfo(atp);
    }

    @Override
    public List<AtpInfo> getAtpsByDate(Date searchDate, ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        List<AtpInfo> list = new ArrayList<AtpInfo>();
        for (AtpInfo atp : atps.values()) {
            if ((atp.getStartDate().before(searchDate) || atp.getStartDate().equals(searchDate)) && (atp.getEndDate().after(
                    searchDate) || atp.getEndDate().equals(searchDate))) {
                list.add(new AtpInfo(atp));
            }
        }
        return list;
    }

    @Override
    public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate, ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        List<AtpInfo> atpList = new ArrayList<AtpInfo>();
        for (AtpInfo atp : atps.values()) {
            Date atpStart = atp.getStartDate();
            Date atpEnd = atp.getEndDate();
//          match exactly on the boundaries.
            if ((startDate.equals(atpStart) || startDate.before(atpStart))
                    && (endDate.equals(atpEnd) || endDate.after(atpEnd))) {
                atpList.add(new AtpInfo(atp));
            }
        }
        return atpList;
    }

    @Override
    public List<AtpInfo> getAtpsByDateAndType(Date searchDate, String searchTypeKey, ContextInfo context) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AtpInfo> list = new ArrayList<AtpInfo>();
        for (AtpInfo atp : this.getAtpsByDate(searchDate, context)) {
            if (searchTypeKey.equals(atp.getTypeKey())) {
                list.add(atp);
            }
        }
        return list;
    }

    @Override
    public List<AtpInfo> getAtpsByDatesAndType(Date searchDate, Date endDate, String searchTypeKey, ContextInfo context) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AtpInfo> atpList = new ArrayList<AtpInfo>();
        for (AtpInfo atp : this.getAtpsByDates(searchDate, endDate, context)) {
            if (searchTypeKey.equals(atp.getTypeKey())) {
                atpList.add(atp);
            }
        }
        return atpList;
    }

    @Override
    public List<AtpInfo> getAtpsByStartDateRange(Date searchDateRangeStart, Date searchDateRangeEnd, ContextInfo context) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AtpInfo> list = new ArrayList<AtpInfo>();
        for (AtpInfo atp : atps.values()) {
            if (searchDateRangeStart.before(atp.getStartDate()) && searchDateRangeEnd.after(atp.getStartDate())) {
                list.add(new AtpInfo(atp));
            }
        }
        return list;
    }

    @Override
    public List<AtpInfo> getAtpsByStartDateRangeAndType(Date searchDateRangeStart, Date searchDateRangeEnd, String searchTypeKey,
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AtpInfo> list = new ArrayList<AtpInfo>();
        for (AtpInfo atp : this.getAtpsByStartDateRange(searchDateRangeStart, searchDateRangeEnd, context)) {
            if (searchTypeKey.equals(atp.getTypeKey())) {
                list.add(atp);
            }
        }
        return list;
    }

    @Override
    public List<String> getAtpIdsByType(String atpTypeKey, ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> atpIds = new ArrayList<String>();
        for (AtpInfo atp : atps.values()) {
            if (atp.getTypeKey().equalsIgnoreCase(atpTypeKey)) {
                atpIds.add(atp.getId());
            }
        }
        return atpIds;
    }

    @Override
    public List<AtpInfo> getAtpsByCode(String code, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AtpInfo> list = new ArrayList<AtpInfo>();
        for (AtpInfo atp : atps.values()) {
            if (code.equals(atp.getCode())) {
                list.add(new AtpInfo(atp));
            }
        }
        return list;
    }

    @Override
    public List<AtpInfo> getAtpsByIds(List<String> atpIds, ContextInfo context) throws InvalidParameterException,
            DoesNotExistException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AtpInfo> list = new ArrayList<AtpInfo>();
        for (String key : atpIds) {
            list.add(this.getAtp(key, context));
        }
        return list;
    }

    @Override
    public MilestoneInfo getMilestone(String milestoneId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        MilestoneInfo milestone = milestones.get(milestoneId);
        if (null == milestone) {
            throw new DoesNotExistException("No milestone found for: " + milestoneId);
        }
        return new MilestoneInfo(milestone);
    }

    @Override
    public List<MilestoneInfo> getMilestonesForAtp(String atpId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
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
    public List<AtpInfo> getAtpsForMilestone(String milestoneId, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AtpInfo> list = new ArrayList<AtpInfo>();
        for (String atpId : this.milestonesForAtp.keySet()) {
            Set<String> milestoneIds = this.milestonesForAtp.get(atpId);
            if (milestoneIds.contains(milestoneId)) {
                try {
                    list.add(this.getAtp(atpId, contextInfo));
                } catch (DoesNotExistException ex) {
                    throw new OperationFailedException("atp id not found but still linked to a milestone " + atpId);
                }
            }
        }
        return list;
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDatesForAtp(String atpId, Date startDate, Date endDate, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<MilestoneInfo> list = new ArrayList<MilestoneInfo>();
        for (MilestoneInfo info : this.getMilestonesForAtp(atpId, contextInfo)) {
            if (this.isMilestoneWithinDates(info, startDate, endDate)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<MilestoneInfo> getMilestonesByTypeForAtp(String atpId, String milestoneTypeKey, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<MilestoneInfo> list = new ArrayList<MilestoneInfo>();
        for (MilestoneInfo info : this.getMilestonesForAtp(atpId, contextInfo)) {
            if (info.getTypeKey().equals(milestoneTypeKey)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDates(Date startDate, Date endDate, ContextInfo context) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        List<MilestoneInfo> list = new ArrayList<MilestoneInfo>();
        for (MilestoneInfo milestone : milestones.values()) {
            if (this.isMilestoneWithinDates(milestone, startDate, endDate)) {
                list.add(milestone);
            }
        }
        return list;
    }

    private boolean isMilestoneWithinDates(MilestoneInfo milestone, Date startDate, Date endDate) {
        if (milestone.getIsDateRange()) {
            if ((startDate.before(milestone.getStartDate()) || startDate.equals(milestone.getStartDate())) && (endDate.after(
                    milestone.getEndDate()) || endDate.equals(milestone.getEndDate()))) {
                return true;
            }
        } else {
            if ((startDate.before(milestone.getStartDate()) || startDate.equals(milestone.getStartDate()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<MilestoneInfo> getMilestonesByIds(List<String> milestoneIds, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<MilestoneInfo> milestoneList = new ArrayList<MilestoneInfo>();
        for (String key : milestoneIds) {
            milestoneList.add(this.getMilestone(key, context));
        }
        return milestoneList;
    }

    @Override
    public List<String> getMilestoneIdsByType(String milestoneTypeKey, ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (MilestoneInfo milestone  : this.milestones.values()) {
            if (milestone.getTypeKey().equals(milestoneTypeKey)) {
                list.add(milestone.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForAtpIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return new ArrayList<String>();
    }

    @Override
    public List<AtpInfo> searchForAtps(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return new ArrayList<AtpInfo>();
    }

    public List<ValidationResultInfo> validateAtp(String validationType, String atpTypeKey, AtpInfo atpInfo, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public AtpInfo createAtp(String atpTypeKey,
            AtpInfo atpInfo,
            ContextInfo context)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        MockHelper helper = new MockHelper();
        AtpInfo info = new AtpInfo(atpInfo);
        info.setMeta(helper.createMeta(context));
        if (info.getId() == null) {
            info.setId(UUIDHelper.genStringUUID());
        }
        helper.setIdOnAttributesThatDoNotHaveOne(info.getAttributes());
        this.atps.put(info.getId(), info);
        return new AtpInfo(info);
    }

    @Override
    public AtpInfo updateAtp(String atpId, AtpInfo atpInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        AtpInfo existing = this.atps.get(atpId);
        if (existing == null) {
            throw new DoesNotExistException(atpId);
        }
        if (!atpInfo.getMeta().getVersionInd().equals(existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().getUpdateId() + " on " + existing.getMeta().
                    getUpdateId() + " with version of " + existing.getMeta().getVersionInd());
        }
        AtpInfo atp = new AtpInfo(atpInfo);
        atp.setMeta(new MockHelper().updateMeta(existing.getMeta(), context));
        new MockHelper().setIdOnAttributesThatDoNotHaveOne(atp.getAttributes());
        this.atps.put(atpId, atp);
        return new AtpInfo(atp);
    }

    @Override
    public StatusInfo deleteAtp(String atpId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (this.atps.remove(atpId) == null) {
            throw new DoesNotExistException(atpId);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public List<MilestoneInfo> getImpactedMilestones(String milestoneId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return new ArrayList<MilestoneInfo>();
    }

    @Override
    public List<String> searchForMilestoneIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<String>();
    }

    @Override
    public List<MilestoneInfo> searchForMilestones(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<MilestoneInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateMilestone(String validationType, MilestoneInfo milestoneInfo, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public MilestoneInfo createMilestone(String milestoneTypeKey,
            MilestoneInfo milestoneInfo, ContextInfo context)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        if (milestoneInfo.getId() != null) {
            if (this.milestones.containsKey(milestoneInfo.getId())) {
                throw new DataValidationErrorException(milestoneInfo.getId());
            }
        }

        MockHelper helper = new MockHelper();
        MilestoneInfo info = new MilestoneInfo(milestoneInfo);
        info.setMeta(helper.createMeta(context));
        if (info.getId() == null) {
            info.setId(UUIDHelper.genStringUUID());
        }
        defaultBooleansToFalse(info);
//      For explanation See https://wiki.kuali.org/display/STUDENT/Storing+and+Querying+Milestone+Dates
        info.setStartDate(DateUtil.startOfDayfIsAllDay(info.getIsAllDay(), info.getStartDate()));
        info.setEndDate(DateUtil.endOfDayIfIsAllDay(info.getIsAllDay(), DateUtil.nullIfNotDateRange(info.getIsDateRange(), info.
                getEndDate())));
        this.milestones.put(info.getId(), info);
        return new MilestoneInfo(info);
    }

    private void defaultBooleansToFalse(MilestoneInfo info) {
        if (info.getIsAllDay() == null) {
            info.setIsAllDay(Boolean.FALSE);
        }
        if (info.getIsDateRange() == null) {
            info.setIsDateRange(Boolean.FALSE);
        }
        if (info.getIsInstructionalDay() == null) {
            info.setIsInstructionalDay(Boolean.FALSE);
        }
        if (info.getIsRelative() == null) {
            info.setIsRelative(Boolean.FALSE);
        }
    }

    @Override
    public MilestoneInfo updateMilestone(String milestoneId, MilestoneInfo milestoneInfo, ContextInfo context) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        MilestoneInfo existing = this.milestones.get(milestoneId);
        if (existing == null) {
            throw new DoesNotExistException(milestoneId);
        }
        if (!milestoneInfo.getMeta().getVersionInd().equals(existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().getUpdateId() + " on " + existing.getMeta().
                    getUpdateId() + " with version of " + existing.getMeta().getVersionInd());
        }
        MilestoneInfo info = new MilestoneInfo(milestoneInfo);
        info.setMeta(new MockHelper().updateMeta(existing.getMeta(), context));
        defaultBooleansToFalse(info);
//      For explanation See https://wiki.kuali.org/display/STUDENT/Storing+and+Querying+Milestone+Dates
        info.setStartDate(DateUtil.startOfDayfIsAllDay(info.getIsAllDay(), info.getStartDate()));
        info.setEndDate(DateUtil.endOfDayIfIsAllDay(info.getIsAllDay(), DateUtil.nullIfNotDateRange(info.getIsDateRange(), info.
                getEndDate())));
        this.milestones.put(milestoneId, info);
        return new MilestoneInfo(info);
    }

    @Override
    public StatusInfo deleteMilestone(String milestoneId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        // Perhaps we should check to see if Milestone exists in a relationship before deleting it
        if (this.milestones.remove(milestoneId) == null) {
            throw new DoesNotExistException(milestoneId);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public MilestoneInfo calculateMilestone(String milestoneId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getMilestone(milestoneId, contextInfo);
    }

    @Override
    public StatusInfo addMilestoneToAtp(String milestoneId, String atpId, ContextInfo contextInfo)
            throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

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
    public StatusInfo removeMilestoneFromAtp(String milestoneId, String atpId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
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
    public AtpAtpRelationInfo getAtpAtpRelation(String atpAtpRelationId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        AtpAtpRelationInfo atpRltn = atpAtpRelations.get(atpAtpRelationId);
        if (null == atpRltn) {
            throw new DoesNotExistException("No atp atp relationship found for: " + atpAtpRelationId);
        }

        return new AtpAtpRelationInfo(atpRltn);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByIds(List<String> atpAtpRelationIds, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        List<AtpAtpRelationInfo> atpRltnList = new ArrayList<AtpAtpRelationInfo>();

        for (String id : atpAtpRelationIds) {
            atpRltnList.add(this.getAtpAtpRelation(id, contextInfo));
        }

        return atpRltnList;
    }

    @Override
    public List<String> getAtpAtpRelationIdsByType(String atpAtpRelationTypeKey, ContextInfo context) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> atpRltnList = new ArrayList<String>();

        Set<String> atpRltnIds = atpAtpRelations.keySet();

        for (String id : atpRltnIds) {
            AtpAtpRelationInfo rltn = atpAtpRelations.get(id);
            if (rltn.getTypeKey().equalsIgnoreCase(atpAtpRelationTypeKey)) {
                atpRltnList.add(id);
            }
        }
        return atpRltnList;
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(String atpId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AtpAtpRelationInfo> atpRltnList = new ArrayList<AtpAtpRelationInfo>();

        Set<String> atpRltnIds = atpAtpRelations.keySet();

        for (String id : atpRltnIds) {
            AtpAtpRelationInfo rltn = atpAtpRelations.get(id);
            if (rltn.getAtpId().equals(atpId) || rltn.getRelatedAtpId().equals(atpId)) {
                atpRltnList.add(new AtpAtpRelationInfo(rltn));
            }
        }

        return atpRltnList;
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtps(String atpId, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return new ArrayList();
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByTypeAndAtp(String atpId, String atpRelationTypeKey,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AtpAtpRelationInfo> list = new ArrayList<AtpAtpRelationInfo>();
        for (AtpAtpRelationInfo info : this.atpAtpRelations.values()) {
            if (info.getAtpId().equals(atpId)) {
                if (info.getTypeKey().equals(atpRelationTypeKey)) {
                    list.add(new AtpAtpRelationInfo(info));
                }
            }
        }
        return list;
    }

    @Override
    public List<String> searchForAtpAtpRelationIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<String>();
    }

    @Override
    public List<AtpAtpRelationInfo> searchForAtpAtpRelations(QueryByCriteria criteria, ContextInfo context) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<AtpAtpRelationInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateAtpAtpRelation(String validationTypeKey, String atpId, String atpPeerId,
            String atpAtpRelationTypeKey, AtpAtpRelationInfo atpAtpRelationInfo,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public AtpAtpRelationInfo createAtpAtpRelation(String atpId,
            String relatedAtpId,
            String atpAtpRelationTypeKey,
            AtpAtpRelationInfo atpAtpRelationInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        MockHelper helper = new MockHelper();
        AtpAtpRelationInfo info = new AtpAtpRelationInfo(atpAtpRelationInfo);
        info.setMeta(helper.createMeta(contextInfo));
        if (info.getId() == null) {
            info.setId(UUIDHelper.genStringUUID());
        }
        this.atpAtpRelations.put(info.getId(), info);
        return info;
    }

    @Override
    public AtpAtpRelationInfo updateAtpAtpRelation(String atpAtpRelationId, AtpAtpRelationInfo atpAtpRelationInfo,
            ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        AtpAtpRelationInfo existing = this.atpAtpRelations.get(atpAtpRelationId);
        if (existing == null) {
            throw new DoesNotExistException(atpAtpRelationId);
        }
        if (!atpAtpRelationInfo.getMeta().getVersionInd().equals(existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().getUpdateId() + " on " + existing.getMeta().
                    getUpdateId() + " with version of " + existing.getMeta().getVersionInd());
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
        this.atpAtpRelations.put(atpAtpRelationId, aari);

        return aari;
    }

    @Override
    public StatusInfo deleteAtpAtpRelation(String atpAtpRelationId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (this.atpAtpRelations.remove(atpAtpRelationId) == null) {
            throw new DoesNotExistException(atpAtpRelationId);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName)
            throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws OperationFailedException,
            InvalidParameterException,
            MissingParameterException {
        return searchManager.getSearchTypes(contextInfo);
    }

    public SearchManager getSearchManager() {
        return searchManager;
    }

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequest, ContextInfo contextInfo) throws MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            InvalidParameterException {
        return this.searchDispatcher.search(searchRequest, contextInfo);
    }
}
