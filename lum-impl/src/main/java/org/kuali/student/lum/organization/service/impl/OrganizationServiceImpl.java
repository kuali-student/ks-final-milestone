package org.kuali.student.lum.organization.service.impl;

import java.util.List;

import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.service.impl.DictionarySearchServiceImpl;
import org.kuali.student.core.validation.dto.ValidationResult;
import org.kuali.student.lum.organization.dao.OrganizationDao;
import org.kuali.student.lum.organization.dto.OrgCriteria;
import org.kuali.student.lum.organization.dto.OrgHierarchyInfo;
import org.kuali.student.lum.organization.dto.OrgInfo;
import org.kuali.student.lum.organization.dto.OrgOrgRelationCriteria;
import org.kuali.student.lum.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.lum.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.lum.organization.dto.OrgPersonRelationCriteria;
import org.kuali.student.lum.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.lum.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.lum.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.lum.organization.dto.OrgTypeInfo;
import org.kuali.student.lum.organization.service.OrganizationService;

public class OrganizationServiceImpl extends DictionarySearchServiceImpl implements OrganizationService{

	private OrganizationDao organizationDao;
	
	
	@Override
	public OrgPositionRestrictionInfo addPositionRestrictionToOrg(String orgId,
			String orgPersonRelationTypeKey,
			OrgPositionRestrictionInfo orgPositionRestrictionInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgOrgRelationInfo createOrgOrgRelation(String orgId,
			String relatedOrgId, String orgOrgRelationTypeKey,
			OrgOrgRelationInfo orgOrgRelationInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgPersonRelationInfo createOrgPersonRelation(String orgId,
			String personId, String orgPersonRelationTypeKey,
			OrgPersonRelationInfo orgPersonRelationInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgInfo createOrganization(String orgTypeKey, OrgInfo orgInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteOrganization(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgHierarchyInfo fetchOrgHierarchy(String orgHierarchyKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgOrgRelationInfo fetchOrgOrgRelation(String orgOrgRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgOrgRelationTypeInfo fetchOrgOrgRelationType(
			String orgOrgRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgPersonRelationInfo fetchOrgPersonRelation(
			String orgPersonRelationId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgPersonRelationTypeInfo fetchOrgPersonRelationType(
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgTypeInfo fetchOrgType(String orgTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgInfo fetchOrganization(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAllDescendants(String orgId, String orgHierarchy)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgPersonRelationInfo> findAllOrgPersonRelationsByOrg(
			String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgPersonRelationInfo> findAllOrgPersonRelationsByPerson(
			String personId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAncestors(String orgId, String orgHierarchy)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgHierarchyInfo> findOrgHierarchies()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgOrgRelationTypeInfo> findOrgOrgRelationTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgOrgRelationTypeInfo> findOrgOrgRelationTypesForOrgHierarchy(
			String orgHierarchyKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgOrgRelationTypeInfo> findOrgOrgRelationTypesForOrgType(
			String orgTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgOrgRelationInfo> findOrgOrgRelationsByIdList(
			List<String> orgOrgRelationIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgOrgRelationInfo> findOrgOrgRelationsByOrg(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgOrgRelationInfo> findOrgOrgRelationsByRelatedOrg(
			String relatedOrgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgPersonRelationTypeInfo> findOrgPersonRelationTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgPersonRelationTypeInfo> findOrgPersonRelationTypesForOrgType(
			String orgTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgPersonRelationInfo> findOrgPersonRelationsByIdList(
			List<String> orgPersonRelationIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgPersonRelationInfo> findOrgPersonRelationsByOrg(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgPersonRelationInfo> findOrgPersonRelationsByPerson(
			String personId, String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgTypeInfo> findOrgTypes() throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgInfo> findOrganizationsByIdList(List<String> orgIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findPersonIdsForOrgByRelationType(String orgId,
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgPositionRestrictionInfo> findPositionRestrictionsByOrg(
			String orgId) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasOrgOrgRelation(String orgId, String comparisonOrgId,
			String orgOrgRelationTypeKey) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasOrgPersonRelation(String orgId, String personId,
			String orgPersonRelationTypeKey) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isDescendant(String orgId, String descendantOrgId,
			String orgHierarchy) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeOrgOrgRelation(String orgOrgRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeOrgPersonRelation(String orgPersonRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removePositionRestrictionFromOrg(String orgId,
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForOrgOrgRelations(
			OrgOrgRelationCriteria orgOrgRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForOrgPersonRelations(
			OrgPersonRelationCriteria orgPersonRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForOrganizations(OrgCriteria orgCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgOrgRelationInfo updateOrgOrgRelation(String orgOrgRelationId,
			OrgOrgRelationInfo orgOrgRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgPersonRelationInfo updateOrgPersonRelation(
			String orgPersonRelationId,
			OrgPersonRelationInfo orgPersonRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgInfo updateOrganization(String orgId, OrgInfo orgInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrgPositionRestrictionInfo updatePositionRestrictionForOrg(
			String orgId, String orgPersonRelationTypeKey,
			OrgPositionRestrictionInfo orgPositionRestrictionInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResult> validateOrg(String validationType,
			OrgInfo orgInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResult> validateOrgOrgRelation(String validationType,
			OrgOrgRelationInfo orgOrgRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResult> validateOrgPersonRelation(
			String validationType, OrgPersonRelationInfo orgPersonRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResult> validateOrgPositionRestriction(
			String validationType,
			OrgPositionRestrictionInfo orgPositionRestrictionInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

}
