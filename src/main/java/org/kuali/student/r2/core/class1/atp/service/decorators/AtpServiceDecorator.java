/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.class1.atp.service.decorators;

import java.util.Date;
import java.util.List;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
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

public class AtpServiceDecorator 
    implements AtpService {

    private AtpService nextDecorator;

    public AtpService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(AtpService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public AtpInfo getAtp(String atpId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtp(atpId, contextInfo);
    }

    @Override
    public List<AtpInfo> getAtpsByIds(
            List<String> atpIds, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpsByIds(atpIds, contextInfo);
    }

    @Override
    public List<String> getAtpIdsByType(String atpTypeKey, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpIdsByType(atpTypeKey, contextInfo);
    }

    @Override
    public List<AtpInfo> getAtpsByCode(String code, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpsByCode(code, contextInfo);
    }

    @Override
    public List<AtpInfo> getAtpsByDate(Date date, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpsByDate(date, contextInfo);
    }

    @Override
    public List<AtpInfo> getAtpsByDateAndType(Date date, String atpTypeKey, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpsByDateAndType(date, atpTypeKey, contextInfo);
    }

    @Override
    public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpsByDates(startDate, endDate, contextInfo);
    }

    @Override
    public List<AtpInfo> getAtpsByDatesAndType(Date startDate, Date endDate, String atpTypeKey, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpsByDatesAndType(startDate, endDate, atpTypeKey, contextInfo);
    }

    @Override
    public List<AtpInfo> getAtpsByStartDateRange(Date dateRangeStart, Date dateRangeEnd, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpsByStartDateRange(dateRangeStart, dateRangeEnd, contextInfo);
    }

    @Override
    public List<AtpInfo> getAtpsByStartDateRangeAndType(Date dateRangeStart, Date dateRangeEnd, String atpTypeKey,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpsByStartDateRangeAndType(dateRangeStart, dateRangeEnd, atpTypeKey, contextInfo);
    }

    @Override
    public List<AtpInfo> getAtpsForMilestone(String milestoneId, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpsForMilestone(milestoneId, contextInfo);
    }

    @Override
    public List<String> searchForAtpIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.searchForAtpIds(criteria, contextInfo);
    }

    @Override
    public List<AtpInfo> searchForAtps(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.searchForAtps(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateAtp(String validationTypeKey, String atpTypeKey, AtpInfo atpInfo,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.validateAtp(validationTypeKey, atpTypeKey, atpInfo, contextInfo);
    }

    @Override
    public AtpInfo createAtp(String atpTypeKey, AtpInfo atpInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return nextDecorator.createAtp(atpTypeKey, atpInfo, contextInfo);
    }

    @Override
    public AtpInfo updateAtp(String atpId, AtpInfo atpInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return nextDecorator.updateAtp(atpId, atpInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteAtp(String atpId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.deleteAtp(atpId, contextInfo);
    }

    @Override
    public AtpAtpRelationInfo getAtpAtpRelation(String atpAtpRelationId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpAtpRelation(atpAtpRelationId, contextInfo);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByIds(
            List<String> atpAtpRelationIds, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpAtpRelationsByIds(atpAtpRelationIds, contextInfo);
    }

    @Override
    public List<String> getAtpAtpRelationIdsByType(String atpAtpRelationTypeKey, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpAtpRelationIdsByType(atpAtpRelationTypeKey, contextInfo);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(String atpId, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpAtpRelationsByAtp(atpId, contextInfo);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtps(String atpId, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpAtpRelationsByAtps(atpId, contextInfo);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByTypeAndAtp(String atpId, String atpAtpRelationTypeKey,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAtpAtpRelationsByTypeAndAtp(atpId, atpAtpRelationTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForAtpAtpRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.searchForAtpAtpRelationIds(criteria, contextInfo);
    }

    @Override
    public List<AtpAtpRelationInfo> searchForAtpAtpRelations(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.searchForAtpAtpRelations(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateAtpAtpRelation(String validationTypeKey, String atpId, String atpPeerId,
            String atpAtpRelationTypeKey, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.validateAtpAtpRelation(validationTypeKey, atpId, atpPeerId, atpAtpRelationTypeKey, atpAtpRelationInfo,
                contextInfo);
    }

    @Override
    public AtpAtpRelationInfo createAtpAtpRelation(String atpId, String relatedAtpId, String atpAtpRelationTypeKey,
            AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo contextInfo) throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return nextDecorator.createAtpAtpRelation(atpId, relatedAtpId, atpAtpRelationTypeKey, atpAtpRelationInfo, contextInfo);
    }

    @Override
    public AtpAtpRelationInfo updateAtpAtpRelation(String atpAtpRelationId, AtpAtpRelationInfo atpAtpRelationInfo,
            ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return nextDecorator.updateAtpAtpRelation(atpAtpRelationId, atpAtpRelationInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteAtpAtpRelation(String atpAtpRelationId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.deleteAtpAtpRelation(atpAtpRelationId, contextInfo);
    }

    @Override
    public MilestoneInfo getMilestone(String milestoneId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getMilestone(milestoneId, contextInfo);
    }

    @Override
    public List<MilestoneInfo> getMilestonesByIds(
            List<String> milestoneIds, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getMilestonesByIds(milestoneIds, contextInfo);
    }

    @Override
    public List<String> getMilestoneIdsByType(String milestoneTypeKey, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getMilestoneIdsByType(milestoneTypeKey, contextInfo);
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDates(Date startDate, Date endDate, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getMilestonesByDates(startDate, endDate, contextInfo);
    }

    @Override
    public List<MilestoneInfo> getMilestonesForAtp(String atpId, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getMilestonesForAtp(atpId, contextInfo);
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDatesForAtp(String atpId, Date startDate, Date endDate, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getMilestonesByDatesForAtp(atpId, startDate, endDate, contextInfo);
    }

    @Override
    public List<MilestoneInfo> getMilestonesByTypeForAtp(String atpId, String milestoneTypeKey, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getMilestonesByTypeForAtp(atpId, milestoneTypeKey, contextInfo);
    }

    @Override
    public List<MilestoneInfo> getImpactedMilestones(String milestoneId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getImpactedMilestones(milestoneId, contextInfo);
    }

    @Override
    public List<String> searchForMilestoneIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.searchForMilestoneIds(criteria, contextInfo);
    }

    @Override
    public List<MilestoneInfo> searchForMilestones(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.searchForMilestones(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateMilestone(String validationTypeKey, MilestoneInfo milestoneInfo,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.validateMilestone(validationTypeKey, milestoneInfo, contextInfo);
    }

    @Override
    public MilestoneInfo createMilestone(String milestoneTypeKey, MilestoneInfo milestoneInfo, ContextInfo contextInfo) throws
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return nextDecorator.createMilestone(milestoneTypeKey, milestoneInfo, contextInfo);
    }

    @Override
    public MilestoneInfo updateMilestone(String milestoneId, MilestoneInfo milestoneInfo, ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return nextDecorator.updateMilestone(milestoneId, milestoneInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteMilestone(String milestoneId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.deleteMilestone(milestoneId, contextInfo);
    }

    @Override
    public MilestoneInfo calculateMilestone(String milestoneId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.calculateMilestone(milestoneId, contextInfo);
    }

    @Override
    public StatusInfo addMilestoneToAtp(String milestoneId, String atpId, ContextInfo contextInfo) throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.addMilestoneToAtp(milestoneId, atpId, contextInfo);
    }

    @Override
    public StatusInfo removeMilestoneFromAtp(String milestoneId, String atpId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.removeMilestoneFromAtp(milestoneId, atpId, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return nextDecorator.getSearchTypes(contextInfo);
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return nextDecorator.getSearchType(searchTypeKey, contextInfo);
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException,
            InvalidParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.search(searchRequestInfo, contextInfo);
    }

    
    
}
