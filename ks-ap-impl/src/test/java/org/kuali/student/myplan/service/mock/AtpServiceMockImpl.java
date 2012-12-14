package org.kuali.student.myplan.service.mock;

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

/**
 * Mock AtpService to be use with AcademicPlanServiceImpl.
 */
public class AtpServiceMockImpl implements AtpService {
	
	@Override
	public AtpInfo getAtp(String atpId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        AtpInfo atp = new AtpInfo();
        atp.setId(atpId);
        return atp;
	}

	@Override
	public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public SearchResultInfo search(SearchRequestInfo searchRequestInfo,
			ContextInfo contextInfo) throws MissingParameterException,
			InvalidParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpInfo> getAtpsByIds(List<String> atpIds,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<String> getAtpIdsByType(String atpTypeKey,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpInfo> getAtpsByCode(String code, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpInfo> getAtpsByDate(Date date, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpInfo> getAtpsByDateAndType(Date date, String atpTypeKey,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpInfo> getAtpsByDatesAndType(Date startDate, Date endDate,
			String atpTypeKey, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpInfo> getAtpsByStartDateRange(Date dateRangeStart,
			Date dateRangeEnd, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpInfo> getAtpsByStartDateRangeAndType(Date dateRangeStart,
			Date dateRangeEnd, String atpTypeKey, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<String> searchForAtpIds(QueryByCriteria criteria,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpInfo> searchForAtps(QueryByCriteria criteria,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<ValidationResultInfo> validateAtp(String validationTypeKey,
			String atpTypeKey, AtpInfo atpInfo, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public AtpInfo createAtp(String atpTypeKey, AtpInfo atpInfo,
			ContextInfo contextInfo) throws DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public AtpInfo updateAtp(String atpId, AtpInfo atpInfo,
			ContextInfo contextInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, ReadOnlyException,
			VersionMismatchException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public StatusInfo deleteAtp(String atpId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public AtpAtpRelationInfo getAtpAtpRelation(String atpAtpRelationId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpAtpRelationInfo> getAtpAtpRelationsByIds(
			List<String> atpAtpRelationIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<String> getAtpAtpRelationIdsByType(
			String atpAtpRelationTypeKey, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(String atpId,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtps(String atpId,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpAtpRelationInfo> getAtpAtpRelationsByTypeAndAtp(
			String atpId, String atpAtpRelationTypeKey, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<String> searchForAtpAtpRelationIds(QueryByCriteria criteria,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<AtpAtpRelationInfo> searchForAtpAtpRelations(
			QueryByCriteria criteria, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<ValidationResultInfo> validateAtpAtpRelation(
			String validationTypeKey, String atpId, String atpPeerId,
			String atpAtpRelationTypeKey,
			AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public AtpAtpRelationInfo createAtpAtpRelation(String atpId,
			String relatedAtpId, String atpAtpRelationTypeKey,
			AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo contextInfo)
			throws DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public AtpAtpRelationInfo updateAtpAtpRelation(String atpAtpRelationId,
			AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo contextInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException, VersionMismatchException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public StatusInfo deleteAtpAtpRelation(String atpAtpRelationId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public MilestoneInfo getMilestone(String milestoneId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<MilestoneInfo> getMilestonesByIds(List<String> milestoneIds,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<String> getMilestoneIdsByType(String milestoneTypeKey,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<MilestoneInfo> getMilestonesByDates(Date startDate,
			Date endDate, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<MilestoneInfo> getMilestonesForAtp(String atpId,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<MilestoneInfo> getMilestonesByDatesForAtp(String atpId,
			Date startDate, Date endDate, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<MilestoneInfo> getMilestonesByTypeForAtp(String atpId,
			String milestoneTypeKey, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<MilestoneInfo> getImpactedMilestones(String milestoneId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<String> searchForMilestoneIds(QueryByCriteria criteria,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<MilestoneInfo> searchForMilestones(QueryByCriteria criteria,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public List<ValidationResultInfo> validateMilestone(
			String validationTypeKey, MilestoneInfo milestoneInfo,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public MilestoneInfo createMilestone(String milestoneTypeKey,
			MilestoneInfo milestoneInfo, ContextInfo contextInfo)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, ReadOnlyException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public MilestoneInfo updateMilestone(String milestoneId,
			MilestoneInfo milestoneInfo, ContextInfo contextInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException, VersionMismatchException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public StatusInfo deleteMilestone(String milestoneId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public MilestoneInfo calculateMilestone(String milestoneId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public StatusInfo addMilestoneToAtp(String milestoneId, String atpId,
			ContextInfo contextInfo) throws AlreadyExistsException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}

	@Override
	public StatusInfo removeMilestoneFromAtp(String milestoneId, String atpId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented");
	}
}
