package org.kuali.student.lum.statement.config.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.common.search.dto.SearchTypeInfo;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;

public class OrganizationContextImplTest {

	private OrganizationService organizationService = new OrganizationServiceMock();
	private OrganizationContextImpl organizationContext = new OrganizationContextImpl();

	private ReqComponentInfo reqComponent1;
	private ReqComponentInfo reqComponent2;
	
	private void setupReqComponent1() {
		reqComponent1 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.ORGANIZATION_KEY.getId());
        reqCompField1.setValue("59");
        reqCompFieldList.add(reqCompField1);
		reqComponent1.setReqCompFields(reqCompFieldList);
	}

	private void setupReqComponent2() {
		reqComponent2 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.ORGANIZATION_KEY.getId());
        reqCompField1.setValue(null);
        reqCompFieldList.add(reqCompField1);
		reqComponent2.setReqCompFields(reqCompFieldList);
	}

	@Before
	public void beforeMethod() {
		organizationContext.setOrganizationService(organizationService);
		setupReqComponent1();
		setupReqComponent2();
	}

	@Test
    public void testCreateContextMap() throws OperationFailedException {
		Map<String, Object> contextMap = organizationContext.createContextMap(reqComponent1);
		OrgInfo org = (OrgInfo) contextMap.get(OrganizationContextImpl.ORG_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("kuali.org.Department", org.getType());
		Assert.assertEquals("Sociology", org.getShortName());
		Assert.assertEquals("Sociology Dept", org.getLongName());
	}

	@Test
    public void testCreateContextMap_NullTokenValues() throws OperationFailedException {
		Map<String, Object> contextMap = organizationContext.createContextMap(reqComponent2);
		OrgInfo org = (OrgInfo) contextMap.get(OrganizationContextImpl.ORG_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals(null, org);
	}

	private static class OrganizationServiceMock implements OrganizationService {

		private Map<String, OrgInfo> orgMap = new HashMap<String, OrgInfo>();
		
		public OrganizationServiceMock() {
			OrgInfo org1 = new OrgInfo();
			org1.setId("59");
			org1.setLongName("Sociology Dept");
			org1.setShortName("Sociology");
			org1.setType("kuali.org.Department");
			orgMap.put("59", org1);

			OrgInfo org2 = new OrgInfo();
			org2.setId("60");
			org2.setLongName("Interdisciplinary Studies in Social Science Program");
			org2.setShortName("InterdiscBSOS");
			org2.setType("kuali.org.Office");
			orgMap.put("60", org2);
		}
		
		@Override
		public OrgPositionRestrictionInfo addPositionRestrictionToOrg(
				String orgId, String orgPersonRelationTypeKey,
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
		public List<String> getAllAncestors(String orgId, String orgHierarchy)
				throws InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getAllDescendants(String orgId, String orgHierarchy)
				throws InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgPersonRelationInfo> getAllOrgPersonRelationsByOrg(
				String orgId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgPersonRelationInfo> getAllOrgPersonRelationsByPerson(
				String personId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgHierarchyInfo> getOrgHierarchies()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OrgHierarchyInfo getOrgHierarchy(String orgHierarchyKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OrgOrgRelationInfo getOrgOrgRelation(String orgOrgRelationId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OrgOrgRelationTypeInfo getOrgOrgRelationType(
				String orgOrgRelationTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypesForOrgHierarchy(
				String orgHierarchyKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypesForOrgType(
				String orgTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgOrgRelationInfo> getOrgOrgRelationsByIdList(
				List<String> orgOrgRelationIdList)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgOrgRelationInfo> getOrgOrgRelationsByRelatedOrg(
				String relatedOrgId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OrgPersonRelationInfo getOrgPersonRelation(
				String orgPersonRelationId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OrgPersonRelationTypeInfo getOrgPersonRelationType(
				String orgPersonRelationTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypesForOrgType(
				String orgTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgPersonRelationInfo> getOrgPersonRelationsByIdList(
				List<String> orgPersonRelationIdList)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(
				String orgId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgPersonRelationInfo> getOrgPersonRelationsByPerson(
				String personId, String orgId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgTreeInfo> getOrgTree(String rootOrgId,
				String orgHierarchyId, int maxLevels)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OrgTypeInfo getOrgType(String orgTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<OrgTypeInfo> getOrgTypes() throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OrgInfo getOrganization(String orgId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			return orgMap.get(orgId);
		}

		@Override
		public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
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
				String orgPersonRelationTypeKey)
				throws InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
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
		public List<ValidationResultInfo> validateOrg(String validationType,
				OrgInfo orgInfo) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateOrgOrgRelation(
				String validationType, OrgOrgRelationInfo orgOrgRelationInfo)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateOrgPersonRelation(
				String validationType,
				OrgPersonRelationInfo orgPersonRelationInfo)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateOrgPositionRestriction(
				String validationType,
				OrgPositionRestrictionInfo orgPositionRestrictionInfo)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
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
		public SearchResultTypeInfo getSearchResultType(
				String searchResultTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
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

		@Override
		public SearchResult search(SearchRequest searchRequest)
				throws MissingParameterException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
