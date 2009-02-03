package org.kuali.student.core.organization.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.core.dictionary.dto.EnumeratedValue;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.organization.dao.OrganizationDao;
import org.kuali.student.core.organization.dto.OrgCriteria;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationCriteria;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationCriteria;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgHierarchy;
import org.kuali.student.core.organization.entity.OrgOrgRelation;
import org.kuali.student.core.organization.entity.OrgOrgRelationType;
import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelationType;
import org.kuali.student.core.organization.entity.OrgPositionRestriction;
import org.kuali.student.core.organization.entity.OrgType;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResult;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.core.organization.service.OrganizationService", serviceName = "OrganizationService", portName = "OrganizationService", targetNamespace = "http://org.kuali.student/core/organization")
@Transactional
public class OrganizationServiceImpl implements OrganizationService {


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

		//Check Missing params
		if (orgId == null) {
			throw new MissingParameterException("orgId can not be null");
		} else if (relatedOrgId == null) {
			throw new MissingParameterException("relatedOrgId can not be null");
		} else if (orgOrgRelationTypeKey == null) {
			throw new MissingParameterException("orgOrgRelationTypeKey can not be null");
		} else if (orgOrgRelationInfo == null) {
			throw new MissingParameterException("orgOrgRelationInfo can not be null");
		}

		//Set all the values on OrgOrgRelationInfo
		orgOrgRelationInfo.setOrgId(orgId);
		orgOrgRelationInfo.setRelatedOrgId(relatedOrgId);
		orgOrgRelationInfo.setType(orgOrgRelationTypeKey);

		OrgOrgRelation orgOrgRelation = null;

		//Create a new persistence entity from the orgInfo
		try {
			orgOrgRelation = OrganizationAssembler.toOrgOrgRelation(false, orgOrgRelationInfo, organizationDao);
		} catch (DoesNotExistException e) {
		} catch (VersionMismatchException e) {
		}

		//Persist the orgOrgRelation
		organizationDao.create(orgOrgRelation);

		//Copy back to an OrgOrgRelationInfo and return
		OrgOrgRelationInfo createdOrgOrgRelationInfo = OrganizationAssembler.toOrgOrgRelationInfo(orgOrgRelation);
		return createdOrgOrgRelationInfo;
	}

	@Override
	public OrgPersonRelationInfo createOrgPersonRelation(String orgId,
			String personId, String orgPersonRelationTypeKey,
			OrgPersonRelationInfo orgPersonRelationInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException {

		//Check Missing params
		if (orgId == null) {
			throw new MissingParameterException("orgId can not be null");
		} else if (personId == null) {
			throw new MissingParameterException("personId can not be null");
		} else if (orgPersonRelationTypeKey == null) {
			throw new MissingParameterException("orgPersonRelationTypeKey can not be null");
		} else if (orgPersonRelationInfo == null) {
			throw new MissingParameterException("orgPersonRelationInfo can not be null");
		}

		//Make sure that only valid org person relations are done
		if(!organizationDao.validatePositionRestriction(orgId,orgPersonRelationTypeKey)){
			throw new InvalidParameterException("There is no Position for this relationship");
		}

		//Set all the values on OrgOrgRelationInfo
		orgPersonRelationInfo.setOrgId(orgId);
		orgPersonRelationInfo.setPersonId(personId);
		orgPersonRelationInfo.setType(orgPersonRelationTypeKey);

		OrgPersonRelation orgPersonRelation = null;

		//Create a new persistence entity from the orgInfo
		try {
			orgPersonRelation = OrganizationAssembler.toOrgPersonRelation(false, orgPersonRelationInfo, organizationDao);
		} catch (DoesNotExistException e) {
		} catch (VersionMismatchException e) {
		}

		//Persist the orgPersonRelation
		organizationDao.create(orgPersonRelation);

		//Copy back to an orgPersonRelationInfo and return
		OrgPersonRelationInfo createdOrgPersonRelationInfo = OrganizationAssembler.toOrgPersonRelationInfo(orgPersonRelation);
		return createdOrgPersonRelationInfo;
	}

	@Override
	public OrgInfo createOrganization(String orgTypeKey, OrgInfo orgInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		//Check Missing params
		if (orgTypeKey == null) {
			throw new MissingParameterException("orgTypeKey can not be null");
		} else if (orgInfo == null) {
			throw new MissingParameterException("orgInfo can not be null");
		}

		//Set all the values on orgInfo
		orgInfo.setType(orgTypeKey);

		Org org = null;

		//Create a new persistence entity from the orgInfo
		try {
			org = OrganizationAssembler.toOrg(false, orgInfo, organizationDao);
		} catch (DoesNotExistException e) {
		} catch (VersionMismatchException e) {
		}

		//Persist the org
		organizationDao.create(org);

		//Copy back to an orgInfo and return
		OrgInfo createdOrgInfo = OrganizationAssembler.toOrgInfo(org);
		return createdOrgInfo;

	}

	@Override
	public StatusInfo deleteOrganization(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		if(orgId==null){
			throw new MissingParameterException("orgId can not be null");
		}

		Org org = organizationDao.fetch(Org.class, orgId);

		if(org==null){
			throw new DoesNotExistException("Org does not exist for id: "+orgId);
		}

		organizationDao.delete(org);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);
		return statusInfo;
	}

	@Override
	public List<String> getAllDescendants(String orgId, String orgHierarchy)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Exception Handling
		List<String> descendants = this.organizationDao.getAllDescendants(orgId, orgHierarchy);
		return descendants;
	}

	@Override
	public List<OrgPersonRelationInfo> getAllOrgPersonRelationsByOrg(
			String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return OrganizationAssembler.toOrgPersonRelationInfos(organizationDao.getAllOrgPersonRelationsByOrg(orgId));
	}

	@Override
	public List<OrgPersonRelationInfo> getAllOrgPersonRelationsByPerson(
			String personId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<OrgPersonRelation> orgPersonRelationInfo = organizationDao.getAllOrgPersonRelationsByPerson(personId);
		return OrganizationAssembler.toOrgPersonRelationInfos(orgPersonRelationInfo);
	}

	@Override
	public List<String> getAncestors(String orgId, String orgHierarchy)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgHierarchyInfo> getOrgHierarchies()
			throws OperationFailedException {
		//TODO flush out exceptions
		return OrganizationAssembler.toOrgHierarchyInfos(organizationDao.find(OrgHierarchy.class));

	}

	@Override
	public OrgHierarchyInfo getOrgHierarchy(String orgHierarchyKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return OrganizationAssembler.toOrgHierarchyInfo((OrgHierarchy) organizationDao.fetch(OrgHierarchy.class, orgHierarchyKey));
	}

	@Override
	public OrgOrgRelationInfo getOrgOrgRelation(String orgOrgRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		if (orgOrgRelationId == null) {
			throw new MissingParameterException("orgOrgRelationId can not be null");
		}

		return OrganizationAssembler.toOrgOrgRelationInfo((OrgOrgRelation) organizationDao.fetch(OrgOrgRelation.class, orgOrgRelationId));

	}

	@Override
	public OrgOrgRelationTypeInfo getOrgOrgRelationType(
			String orgOrgRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return OrganizationAssembler.toOrgOrgRelationTypeInfo((OrgOrgRelationType) organizationDao.fetch(OrgOrgRelationType.class, orgOrgRelationTypeKey));
	}

	@Override
	public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypes()
			throws OperationFailedException {
		List<OrgOrgRelationType> orgOrgRelationTypes = organizationDao.find(OrgOrgRelationType.class);
		return OrganizationAssembler.toOrgOrgRelationTypeInfos(orgOrgRelationTypes);
	}

	@Override
	public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypesForOrgHierarchy(
			String orgHierarchyKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<OrgOrgRelationType> orgOrgRelationTypes = organizationDao.getOrgOrgRelationTypesForOrgHierarchy(orgHierarchyKey);
		return OrganizationAssembler.toOrgOrgRelationTypeInfos(orgOrgRelationTypes);
	}

	@Override
	public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypesForOrgType(
			String orgTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<OrgOrgRelationType> orgOrgRelationTypes = organizationDao.getOrgOrgRelationTypesForOrgType(orgTypeKey);
		return OrganizationAssembler.toOrgOrgRelationTypeInfos(orgOrgRelationTypes);
	}

	@Override
	public List<OrgOrgRelationInfo> getOrgOrgRelationsByIdList(
			List<String> orgOrgRelationIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<OrgOrgRelation> orgOrgRelations = organizationDao.getOrgOrgRelationsByIdList(orgOrgRelationIdList);
		return OrganizationAssembler.toOrgOrgRelationInfos(orgOrgRelations);
	}

	@Override
	public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Flush out exceptions
		List<OrgOrgRelation> orgOrgRelations = organizationDao.getOrgOrgRelationsByOrg(orgId);
		return OrganizationAssembler.toOrgOrgRelationInfos(orgOrgRelations);
	}

	@Override
	public List<OrgOrgRelationInfo> getOrgOrgRelationsByRelatedOrg(
			String relatedOrgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<OrgOrgRelation> orgOrgRelations = organizationDao.getOrgOrgRelationsByRelatedOrg(relatedOrgId);
		return OrganizationAssembler.toOrgOrgRelationInfos(orgOrgRelations);
	}

	@Override
	public OrgPersonRelationInfo getOrgPersonRelation(String orgPersonRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		OrgPersonRelation opr = organizationDao.fetch(OrgPersonRelation.class, orgPersonRelationId);
		return OrganizationAssembler.toOrgPersonRelationInfo(opr);
	}

	@Override
	public OrgPersonRelationTypeInfo getOrgPersonRelationType(
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		OrgPersonRelationType oprt = organizationDao.fetch(OrgPersonRelationType.class, orgPersonRelationTypeKey);
		return OrganizationAssembler.toOrgPersonRelationTypeInfo(oprt);
	}

	@Override
	public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypes()
			throws OperationFailedException {
		List<OrgPersonRelationType> oprts = organizationDao.find(OrgPersonRelationType.class);
		return OrganizationAssembler.toOrgPersonRelationTypeInfos(oprts);
	}

	@Override
	public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypesForOrgType(
			String orgTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<OrgPersonRelationType> oprts = organizationDao.getOrgPersonRelationTypesForOrgType(orgTypeKey);
		return OrganizationAssembler.toOrgPersonRelationTypeInfos(oprts);
	}

	@Override
	public List<OrgPersonRelationInfo> getOrgPersonRelationsByIdList(
			List<String> orgPersonRelationIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<OrgPersonRelation> oprts = organizationDao.getOrgPersonRelationsByIdList(orgPersonRelationIdList);
		return OrganizationAssembler.toOrgPersonRelationInfos(oprts);
	}

	@Override
	public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<OrgPersonRelation> relations = organizationDao.getAllOrgPersonRelationsByOrg(orgId);
		return OrganizationAssembler.toOrgPersonRelationInfos(relations);
	}

	@Override
	public List<OrgPersonRelationInfo> getOrgPersonRelationsByPerson(
			String personId, String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<OrgPersonRelation> oprts = organizationDao.getOrgPersonRelationsByPerson(personId, orgId);
		return OrganizationAssembler.toOrgPersonRelationInfos(oprts);
	}

	@Override
	public OrgTypeInfo getOrgType(String orgTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return OrganizationAssembler.toOrgTypeInfo((OrgType) organizationDao.fetch(OrgType.class, orgTypeKey));
	}

	@Override
	public List<OrgTypeInfo> getOrgTypes() throws OperationFailedException {
		return OrganizationAssembler.toOrgTypeInfos(organizationDao.find(OrgType.class));
	}

	@Override
	public OrgInfo getOrganization(String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return OrganizationAssembler.toOrgInfo(organizationDao.fetch(Org.class, orgId));
	}

	@Override
	public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<Org> orgs = this.organizationDao.getOrganizationsByIdList(orgIdList);
		return OrganizationAssembler.toOrgInfos(orgs);
	}

	@Override
	public List<String> getPersonIdsForOrgByRelationType(String orgId,
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgPositionRestrictionInfo> getPositionRestrictionsByOrg(
			String orgId) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException {
		List<OrgPositionRestriction> restrictions = organizationDao.getPositionRestrictionsByOrg(orgId);
		return OrganizationAssembler.toOrgPositionRestrictionInfos(restrictions);
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
		if (orgOrgRelationId == null) {
			throw new MissingParameterException("orgOrgRelationId can not be null");
		}
		organizationDao.delete(OrgOrgRelation.class, orgOrgRelationId);
		return new StatusInfo();
	}

	@Override
	public StatusInfo removeOrgPersonRelation(String orgPersonRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		if (orgPersonRelationId == null) {
			throw new MissingParameterException("orgPersonRelationId can not be null");
		}
		organizationDao.delete(OrgPersonRelation.class, orgPersonRelationId);
		return new StatusInfo();
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
	public List<Result> searchForResults(String searchTypeKey,
			List<QueryParamValue> queryParamValues)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
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

	@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchTypeInfo> getSearchTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	@Override
	public List<EnumeratedValue> getEnumeration(String enumerationKey,
			String enumContextKey, String contextValue, Date contextDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectStructure getObjectStructure(String objectTypeKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getObjectTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateObject(String objectTypeKey, String stateKey,
			String info) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateStructureData(String objectTypeKey, String stateKey,
			String info) {
		// TODO Auto-generated method stub
		return false;
	}

}
