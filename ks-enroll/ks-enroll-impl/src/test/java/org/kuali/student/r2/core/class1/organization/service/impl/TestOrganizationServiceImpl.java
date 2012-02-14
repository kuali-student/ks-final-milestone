/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.class1.organization.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.organization.dto.OrgCodeInfo;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.r2.core.organization.dto.OrgTreeInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:org-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestOrganizationServiceImpl {
    
    @Resource(name="orgServiceImpl")
	public OrganizationService client;

	public static String principalId = "123";

    public ContextInfo callContext = null;
    
    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }
    
	@Test
	public void testSearch() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		/*List<SearchParam> searchParams = new ArrayList<SearchParam>();
		SearchParam searchParam = new SearchParam();
		searchParam.setKey("org.queryParam.orgGenericType");
		searchParam.setValue("kuali.org.College");
		searchParams.add(searchParam);
		
		searchParam = new SearchParam();
		searchParam.setKey("org.queryParam.orgGenericShortName");
		searchParam.setValue("CollegeE%");
		searchParams.add(searchParam);
		
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setSearchKey("org.search.test.orgs");
		searchRequest.setParams(searchParams);
		SearchResult result = client.search(searchRequest);
		assertEquals(2,result.getRows().size());
		
		searchParams = new ArrayList<SearchParam>();
		searchParam = new SearchParam();
		searchParam.setKey("org.queryParam.orgType");
		searchParam.setValue("kuali.org.College");
		searchParams.add(searchParam);
		
		searchRequest = new SearchRequest();
		searchRequest.setSearchKey("org.search.orgQuickViewByOrgType");
		searchRequest.setParams(searchParams);
		
		result = client.search(searchRequest);
		assertEquals(6,result.getRows().size());
		assertEquals(2,result.getRows().get(0).getCells().size());
		
		//Test org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgType
		searchParams = new ArrayList<SearchParam>();
		searchParam = new SearchParam();
		searchParam.setKey("org.queryParam.relationType");
		searchParam.setValue("kuali.org.Chartered");
		searchParams.add(searchParam);
		
		searchParam = new SearchParam();
		searchParam.setKey("org.queryParam.orgType");
		searchParam.setValue("kuali.org.College");
		searchParams.add(searchParam);
		
		searchParam = new SearchParam();
		searchParam.setKey("org.queryParam.relatedOrgType");
		searchParam.setValue("kuali.org.COC");
		searchParams.add(searchParam);
		
		
		searchRequest = new SearchRequest();
		searchRequest.setSearchKey("org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgType");
		searchRequest.setParams(searchParams);
		
		result = client.search(searchRequest);
		assertEquals(5,result.getRows().size());*/

	}
	
	@Test
	public void testSearchHierarchyShortName() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		/*List<SearchParam> searchParams = new ArrayList<SearchParam>();
		SearchParam searchParam = new SearchParam();
		searchParam.setKey("org.queryParam.orgHierarchyId");
		searchParam.setValue("kuali.org.hierarchy.Main");
		searchParams.add(searchParam);
		searchParam = new SearchParam();
		searchParam.setKey("org.queryParam.orgShortName");
		searchParam.setValue("Bio%");
		searchParams.add(searchParam);
		
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setSearchKey("org.search.orgQuickViewByHierarchyShortName");
		searchRequest.setParams(searchParams);
		
		SearchResult result = client.search(searchRequest);
		assertEquals(4,result.getRows().size());
		assertEquals(2,result.getRows().get(0).getCells().size());*/
	}
	
	@Test
	public void testCreateUpdateOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, ParseException, ReadOnlyException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		OrgInfo orgInfo = new OrgInfo();
		RichTextInfo shortDescr = new RichTextInfo();
        shortDescr.setPlain("Description for new OrgInfo");
        shortDescr.setFormatted("Description for new OrgInfo");
		orgInfo.setShortDescr(shortDescr);
        RichTextInfo longDescr = new RichTextInfo();
        longDescr.setPlain("Loooooooooong description for new OrgInfo");
        longDescr.setFormatted("Loooooooooong description for new OrgInfo");
		orgInfo.setLongDescr(longDescr);
		orgInfo.setLongName("TestOrgLongName");
		orgInfo.setShortName("TestOrgShortName");
		orgInfo.setStateKey("Active");
		orgInfo.setTypeKey("");//set as a param
		orgInfo.setEffectiveDate(df.parse("20090101"));
		orgInfo.setExpirationDate(df.parse("21001231"));
		AttributeInfo attribute = new AttributeInfo();
        attribute.setKey("Alias");
        attribute.setValue("OrgAlias");
        orgInfo.setAttributes(new ArrayList<AttributeInfo>());
		orgInfo.getAttributes().add(attribute);
		
		OrgCodeInfo orgCode1 = new OrgCodeInfo();
		RichTextInfo descr = new RichTextInfo();
		descr.setPlain("Org Code 1 Desc");
		descr.setFormatted("Org Code 1 Desc");
		orgCode1.setDescr(descr);
		orgCode1.setValue("OrgCodeValue 1");
		orgInfo.getOrgCodes().add(orgCode1);
		
		OrgCodeInfo orgCode2 = new OrgCodeInfo();
		RichTextInfo codeDescr = new RichTextInfo();
		codeDescr.setPlain("Org Code 2 Desc");
		codeDescr.setFormatted("Org Code 2 Desc");
		orgCode2.setDescr(codeDescr);
		orgCode2.setValue("OrgCodeValue 2");
		orgInfo.getOrgCodes().add(orgCode2);
		
		OrgInfo createOrg1 = client.createOrg("kuali.org.Program", orgInfo, callContext);
		OrgInfo createOrg = client.getOrg(createOrg1.getId(), callContext);
		
		//Validate all fields
		assertEquals("Description for new OrgInfo",createOrg.getShortDescr().getPlain());
		assertEquals("Loooooooooong description for new OrgInfo", createOrg.getLongDescr().getPlain());
		assertEquals("TestOrgLongName",createOrg.getLongName());
		assertEquals("TestOrgShortName",createOrg.getShortName());
		assertEquals("Active",createOrg.getStateKey());
		assertEquals("kuali.org.Program",createOrg.getTypeKey());
		assertEquals(df.parse("20090101"),createOrg.getEffectiveDate());
		assertEquals(df.parse("21001231"),createOrg.getExpirationDate());
		boolean found = false;
		for (AttributeInfo attrInfo : createOrg.getAttributes()){
		    if (attrInfo.getKey().equals("Alias")){
		        assertEquals("OrgAlias",attrInfo.getValue());
		        found = true;
		    }
		}
		assertTrue(found);
		assertNotNull(createOrg.getId());

		OrgInfo updateInfo = client.getOrg(createOrg.getId(), callContext);
		shortDescr = new RichTextInfo();
		shortDescr.setPlain("Updated Description for new OrgInfo");
		shortDescr.setFormatted("Updated Description for new OrgInfo");
		updateInfo.setShortDescr(shortDescr);
		updateInfo.setLongName("Updated TestOrgLongName");
		updateInfo.setShortName("Updated TestOrgShortName");
		updateInfo.setStateKey("Updated Active");
		updateInfo.setTypeKey("kuali.org.College");
		updateInfo.setEffectiveDate(df.parse("20090111"));
		updateInfo.setExpirationDate(df.parse("21001211"));
		for (AttributeInfo attrInfo : updateInfo.getAttributes()){
		    if (attrInfo.getKey().equals("Alias")){
		        attrInfo.setValue("Updated OrgAlias");
            }
		}
		attribute = new AttributeInfo();
        attribute.setKey("Alias2");
        attribute.setValue("New OrgAlias2");
		updateInfo.getAttributes().add(attribute);
		
		OrgInfo updated=null;
		try {
			updated = client.updateOrg(updateInfo.getId(), updateInfo, callContext);
		} catch (VersionMismatchException e) {
			fail("Should not throw VersionMismatchException");
		}

		//Validate
		assertEquals("Updated Description for new OrgInfo",updated.getShortDescr().getPlain());
		assertEquals("Updated TestOrgLongName",updated.getLongName());
		assertEquals("Updated TestOrgShortName",updated.getShortName());
		assertEquals("Updated Active",updated.getStateKey());
		assertEquals("kuali.org.College",updated.getTypeKey());
		assertEquals(df.parse("20090111"),updated.getEffectiveDate());
		assertEquals(df.parse("21001211"),updated.getExpirationDate());
		boolean found1 = false;
		boolean found2 = false;
        for (AttributeInfo attrInfo : updated.getAttributes()){
            if (attrInfo.getKey().equals("Alias")){
                assertEquals("Updated OrgAlias",attrInfo.getValue());
                found1 = true;
            } else if (attrInfo.getKey().equals("Alias2")){
                assertEquals("New OrgAlias2",attrInfo.getValue());
                found2 = true;
            }
        }
        assertTrue(found1);
        assertTrue(found2);
		
		//Check version mismatch
		try {
			client.updateOrg(updateInfo.getId(), updateInfo, callContext);
			fail("Should throw VersionMismatchException");
		} catch (VersionMismatchException e) {
		}
		
		// now test delete (and clean up changes made)
		StatusInfo si;
		String orgId = createOrg.getId();
		try {
			si = client.deleteOrg(orgId, callContext);
			assertTrue(si.getIsSuccess());
		} catch (DoesNotExistException e) {
			fail("OrganizationService.deleteOrganization() failed deleting just-created Organization");
		}

		try {
			client.deleteOrgPersonRelation(orgId, callContext);
			fail("OrganizationService.deleteOrganization() of a deleted Organization did not throw DoesNotExistException as expected");
		} catch (DoesNotExistException e) {
		}

		try {
			client.deleteOrgPersonRelation(null, callContext);
			fail("OrganizationService.deleteOrganization(null) did not throw DoesNotExistException as expected");
		} catch (MissingParameterException e) {
		}
	}

	@Test
	public void testCreateDeleteOrgPersonRelation() throws ParseException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, ReadOnlyException{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		OrgPersonRelationInfo orgPersonRelationInfo = new OrgPersonRelationInfo();
		orgPersonRelationInfo.setStateKey("Active");
		orgPersonRelationInfo.setEffectiveDate(df.parse("20090101"));
		orgPersonRelationInfo.setExpirationDate(df.parse("21001231"));
		orgPersonRelationInfo.setOrgId("");
		orgPersonRelationInfo.setPersonId("");
		orgPersonRelationInfo.setTypeKey("");

		OrgPersonRelationInfo createdOPRInfo = client.createOrgPersonRelation("28", "KIM-12345", "kuali.org.PersonRelation.Dean", orgPersonRelationInfo, callContext);

		//Validate all fields
		assertEquals("Active",createdOPRInfo.getStateKey());
		assertEquals(df.parse("20090101"),createdOPRInfo.getEffectiveDate());
		assertEquals(df.parse("21001231"),createdOPRInfo.getExpirationDate());
		assertEquals("28",createdOPRInfo.getOrgId());
		assertEquals("KIM-12345",createdOPRInfo.getPersonId());
		assertEquals("kuali.org.PersonRelation.Dean",createdOPRInfo.getTypeKey());
		assertNotNull(createdOPRInfo.getId());

		// now test remove (and clean up changes made)
		StatusInfo si;
		String oprId = createdOPRInfo.getId();
		try {
			si = client.deleteOrgPersonRelation(oprId, callContext);
			assertTrue(si.getIsSuccess());
		} catch (DoesNotExistException e) {
			fail("OrganizationService.removeOrgPersonRelation() failed removing just-created OrgPersonRelation");
		}

		try {
			client.deleteOrgPersonRelation(oprId, callContext);
			fail("OrganizationService.removeOrgPersonRelation() of a deleted OrgPersonRelation did not throw DoesNotExistException as expected");
		} catch (DoesNotExistException e) {
		}

		try {
			client.deleteOrgPersonRelation(null, callContext);
			fail("OrganizationService.removeOrgPersonRelation(null) did not throw DoesNotExistException as expected");
		} catch (MissingParameterException e) {
		}
	}

	@Test
	public void testCreateDeleteOrgOrgRelation() throws ParseException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, ReadOnlyException{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		OrgOrgRelationInfo orgOrgRelationInfo = new OrgOrgRelationInfo();
		orgOrgRelationInfo.setStateKey("Active");
		orgOrgRelationInfo.setEffectiveDate(df.parse("20090101"));
		orgOrgRelationInfo.setExpirationDate(df.parse("21001231"));
		orgOrgRelationInfo.setOrgId("");
		orgOrgRelationInfo.setRelatedOrgId("");
		orgOrgRelationInfo.setTypeKey("");

		OrgOrgRelationInfo createdOORInfo = client.createOrgOrgRelation("16", "17", "kuali.org.Part", orgOrgRelationInfo, callContext);

		//Validate all fields
		assertEquals("Active",createdOORInfo.getStateKey());
		assertEquals(df.parse("20090101"),createdOORInfo.getEffectiveDate());
		assertEquals(df.parse("21001231"),createdOORInfo.getExpirationDate());
		assertEquals("16",createdOORInfo.getOrgId());
		assertEquals("17",createdOORInfo.getRelatedOrgId());
		assertEquals("kuali.org.Part",createdOORInfo.getTypeKey());
		assertNotNull(createdOORInfo.getId());
		
		// now test remove (and clean up changes made)
		StatusInfo si;
		String oorId = createdOORInfo.getId();
		try {
			si = client.deleteOrgOrgRelation(oorId, callContext);
			assertTrue(si.getIsSuccess());
		} catch (DoesNotExistException e) {
			fail("OrganizationService.removeOrgOrgRelation() failed removing just-created OrgOrgRelation");
		}

		try {
			client.deleteOrgOrgRelation(oorId, callContext);
			fail("OrganizationService.removeOrgOrgRelation() of a deleted OrgOrgRelation did not throw DoesNotExistException as expected");
		} catch (DoesNotExistException e) {
		}

		try {
			client.deleteOrgOrgRelation(null, callContext);
			fail("OrganizationService.removeOrgOrgRelation(null) did not throw MissingParameterException as expected");
		} catch (MissingParameterException e) {
		}
	}

	@Test
	public void testAddPositionRestriction() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException{

		TimeAmountInfo stdDuration = new TimeAmountInfo();
		stdDuration.setAtpDurationTypeKey("ks.foreign.atp.key");
		stdDuration.setTimeQuantity(new Integer(123456));

		OrgPositionRestrictionInfo orgPositionRestrictionInfo = new OrgPositionRestrictionInfo();
		RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Description For Position Restriction");
        descr.setFormatted("Description For Position Restriction");
		orgPositionRestrictionInfo.setDescr(descr);
		orgPositionRestrictionInfo.setMaxNumRelations("2345");
		orgPositionRestrictionInfo.setMinNumRelations(2);
		orgPositionRestrictionInfo.setStdDuration(stdDuration);
		orgPositionRestrictionInfo.setTitle("Title for PositionRestriction");
		orgPositionRestrictionInfo.setOrgId("");
		orgPositionRestrictionInfo.setOrgPersonRelationTypeKey("");

		OrgPositionRestrictionInfo created = client.createOrgPositionRestriction("1", "kuali.org.PersonRelation.Treasurer", orgPositionRestrictionInfo, callContext);
		OrgPositionRestrictionInfo created2 = client.createOrgPositionRestriction("1", "kuali.org.PersonRelation.Treasurer", orgPositionRestrictionInfo, callContext);

		//validate fields
		assertEquals("Description For Position Restriction",created.getDescr().getPlain());
		assertEquals("2345",created.getMaxNumRelations());
		assertEquals(new Integer(2),created.getMinNumRelations());
		assertEquals("ks.foreign.atp.key",created.getStdDuration().getAtpDurationTypeKey());
		assertEquals(new Integer(123456),created.getStdDuration().getTimeQuantity());
		assertEquals("Title for PositionRestriction",created.getTitle());
		assertEquals("1",created.getOrgId());
		assertEquals("kuali.org.PersonRelation.Treasurer",created.getOrgPersonRelationTypeKey());
	}

	@Test
	public void getOrgHierarchies() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgHierarchyInfo> orgHierarchyInfos = client.getOrgHierarchies(callContext);
		assertEquals(2,orgHierarchyInfos.size());
	}

	@Test
	public void getOrgOrgRelationsByOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationInfo> orgOrgRelationInfos = client.getOrgOrgRelationsByOrg("4", callContext);
		assertEquals(8,orgOrgRelationInfos.size());

		orgOrgRelationInfos = client.getOrgOrgRelationsByOrg("-1", callContext);
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}

	@Test
	public void getAllOrgPersonRelationsByOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgPersonRelationInfo> orgPersonRelationInfos = client.getOrgPersonRelationsByOrg("68", callContext);
		assertEquals(3, orgPersonRelationInfos.size());

		orgPersonRelationInfos = client.getOrgPersonRelationsByOrg("-1", callContext);
		assertTrue(orgPersonRelationInfos == null || orgPersonRelationInfos.size() == 0);
	}

	@Test
	public void getPositionRestrictionsByOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<String>  orgPositionRestrictionInfos = client.getOrgPositionRestrictionIdsByOrg("19", callContext);
		assertEquals(2, orgPositionRestrictionInfos.size());

		 orgPositionRestrictionInfos = client.getOrgPositionRestrictionIdsByOrg("-1", callContext);
		 assertTrue(orgPositionRestrictionInfos == null || orgPositionRestrictionInfos.size() == 0);
	}

	@Test
	public void getOrgTypes() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<TypeInfo> orgTypeInfos = client.getOrgTypes(callContext);
		assertEquals(17, orgTypeInfos.size());
		
		boolean found = false;
        for (TypeInfo type : orgTypeInfos){
            if (type.getKey().equals("kuali.org.Division")){
                assertEquals("Division", type.getName());
                found = true;
            }
        }
        assertTrue(found);
	}

	@Test
	public void getAllDescendants() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<String> descendants = client.getAllDescendants("6", "kuali.org.hierarchy.Main", callContext);
		assertEquals(22, descendants.size());
		assertTrue(descendants.containsAll(Arrays.asList("7", "121", "141")));

		descendants = client.getAllDescendants("4", "Star.Trek", callContext);
		assertTrue(descendants == null || descendants.size() == 0);

		descendants = client.getAllDescendants("-1", "kuali.org.hierarchy.Main", callContext);
		assertTrue(descendants == null || descendants.size() == 0);

		descendants = client.getAllDescendants("-1", "-1", callContext);
		assertTrue(descendants == null || descendants.size() == 0);
	}


	@Test
	public void getAncestors() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<String> ancestors = client.getAllAncestors("26", "kuali.org.hierarchy.Main", callContext);
		assertEquals(4,ancestors.size());
		assertTrue(ancestors.containsAll(Arrays.asList("1", "4", "15", "19")));

		ancestors = client.getAllAncestors("2", "Star.Trek", callContext);
		assertTrue(ancestors == null || ancestors.size() == 0);

		ancestors = client.getAllAncestors("-1", "kuali.org.hierarchy.Main", callContext);
		assertTrue(ancestors == null || ancestors.size() == 0);

		ancestors = client.getAllAncestors("-1", "-1", callContext);
		assertTrue(ancestors == null || ancestors.size() == 0);
	}

	
	@Test
	public void isDescendant() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		assertTrue(client.isDescendant("1", "26", "kuali.org.hierarchy.Main", callContext));
		assertTrue(client.isDescendant("19", "26", "kuali.org.hierarchy.Main", callContext));
		assertFalse(client.isDescendant("5", "26", "kuali.org.hierarchy.Main", callContext));
	}

	@Test
	public void getOrgOrgRelationType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<TypeInfo> orgOrgRelationTypeInfos = client.getOrgOrgRelationTypes(callContext);
		assertNotNull(orgOrgRelationTypeInfos);
        assertEquals(13, orgOrgRelationTypeInfos.size());
		
		boolean found = false;
		for(TypeInfo type : orgOrgRelationTypeInfos){
		    if (type.getKey().equals("kuali.org.Report")){
		        assertEquals("Report", type.getName());
		        found = true;
		    }
		}
		assertTrue(found);
		
	}

	@Test
	public void getOrgOrgRelationTypesForOrgHierarchy() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<TypeInfo> orgOrgRelationTypeInfos = client.getOrgOrgRelationTypesForOrgHierarchy("kuali.org.hierarchy.Main", callContext);
		assertEquals(12, orgOrgRelationTypeInfos.size());

		orgOrgRelationTypeInfos = client.getOrgOrgRelationTypesForOrgHierarchy("Red.Dwarf", callContext);
		assertTrue(orgOrgRelationTypeInfos == null || orgOrgRelationTypeInfos.size() == 0);
	}

	@Test
	public void getAllOrgPersonRelationsByPerson() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgPersonRelationInfo> orgPersonRelationsByPerson = client.getOrgPersonRelationsByPerson("KIM-1", callContext);
		//assertEquals(3, orgPersonRelationsByPerson.size());

		//orgPersonRelationsByPerson = client.getOrgPersonRelationsByPerson("Homer", callContext);
		//assertTrue(orgPersonRelationsByPerson == null || orgPersonRelationsByPerson.size() == 0);
	}

	@Test
	public void getOrgHierarchy() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgHierarchyInfo orgHierarchyInfo = client.getOrgHierarchy("kuali.org.hierarchy.Curriculum", callContext);
		assertEquals("kuali.org.hierarchy.Curriculum", orgHierarchyInfo.getId());

		try {
			orgHierarchyInfo = client.getOrgHierarchy("Spectre", callContext);
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}
	}

	@Test
	public void getOrgPersonRelationTypesForOrgType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<TypeInfo> orgPersonRelationsByOrgType = client.getOrgPersonRelationTypesForOrgType("kuali.org.School", callContext);
		assertEquals(2, orgPersonRelationsByOrgType.size());

		orgPersonRelationsByOrgType = client.getOrgPersonRelationTypesForOrgType("K12", callContext);
		assertTrue(orgPersonRelationsByOrgType == null || orgPersonRelationsByOrgType.size() == 0);
	}

	@Test
	public void getOrganization() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgInfo org = client.getOrg("42", callContext);
		assertEquals(org.getId(), "42");

		try {
			org = client.getOrg("Kaos", callContext);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void getOrgOrgRelation() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgOrgRelationInfo orgOrgRelationInfo = client.getOrgOrgRelation("16", callContext);
		assertNotNull(orgOrgRelationInfo);

		try {
			orgOrgRelationInfo = client.getOrgOrgRelation("-1", callContext);
			assertTrue(false);
		} catch (DoesNotExistException e1) {
			assertTrue(true);
		}

		try {
			orgOrgRelationInfo = client.getOrgOrgRelation(null, callContext);
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}
	}


	@Test
	public void getOrgOrgRelationsByIdList() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<String> idList = new ArrayList<String>(2);
		idList.add("3");
		idList.add("15");
		List<OrgOrgRelationInfo> orgOrgRelationInfos = client.getOrgOrgRelationsByIds(idList, callContext);
		assertEquals(2, orgOrgRelationInfos.size());

		idList.add("-1");
		orgOrgRelationInfos = client.getOrgOrgRelationsByIds(idList, callContext);
		assertEquals(2, orgOrgRelationInfos.size());

		idList = new ArrayList<String>(2);
		idList.add("-1");
		idList.add("-2");
		orgOrgRelationInfos = client.getOrgOrgRelationsByIds(idList, callContext);
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}

	@Test
	public void getOrgOrgRelationsByRelatedOrg() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationInfo> orgOrgRelationInfos = client.getOrgOrgRelationsByOrg("16", callContext);
		assertEquals(10, orgOrgRelationInfos.size());

		orgOrgRelationInfos = client.getOrgOrgRelationsByOrg("-1", callContext);
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}

	@Test
	public void getOrgPersonRelationsByIdList() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<String> idList = new ArrayList<String>(2);
		idList.add("2");
		idList.add("3");
		List<OrgPersonRelationInfo> orgOrgRelationInfos = client.getOrgPersonRelationsByIds(idList, callContext);
		assertEquals(2, orgOrgRelationInfos.size());

		idList.add("-1");
		orgOrgRelationInfos = client.getOrgPersonRelationsByIds(idList, callContext);
		assertEquals(2, orgOrgRelationInfos.size());

		idList = new ArrayList<String>(2);
		idList.add("-1");
		idList.add("-2");
		orgOrgRelationInfos = client.getOrgPersonRelationsByIds(idList, callContext);
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}

	@Test
	public void getOrgPersonRelationsByPerson() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgPersonRelationInfo> orgOrgRelationInfos = client.getOrgPersonRelationsByPerson("KIM-1", callContext);
		//assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
		//assertEquals(2, orgOrgRelationInfos.size());

		//orgOrgRelationInfos = client.getOrgPersonRelationsByPerson("-1", callContext);
		//assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);

	}

	@Test
	public void getOrgOrgRelationTypesForOrgType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<TypeInfo> orgOrgRelationTypeInfos = client.getOrgOrgRelationTypesForOrgType("kuali.org.Division", callContext);
		assertEquals(4, orgOrgRelationTypeInfos.size());

		orgOrgRelationTypeInfos = client.getOrgOrgRelationTypesForOrgType("org.klingon", callContext);
		assertTrue(orgOrgRelationTypeInfos == null || orgOrgRelationTypeInfos.size() == 0);
	}

	@Test
	public void hasOrgPersonRelation() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		Boolean hasRelation = client.hasOrgPersonRelation("68", "KIM-1", "kuali.org.PersonRelation.Head", callContext);
		assertNotNull(hasRelation);
		assertTrue(hasRelation);

		hasRelation = client.hasOrgPersonRelation("68x", "KIM-1", "kuali.org.PersonRelation.Head", callContext);
		assertNotNull(hasRelation);
		assertFalse(hasRelation);

		hasRelation = client.hasOrgPersonRelation("68", "KIM--1", "kuali.org.PersonRelation.Head", callContext);
		assertNotNull(hasRelation);
		assertFalse(hasRelation);

		hasRelation = client.hasOrgPersonRelation("68", "KIM-1", "kuali.org.PersonRelation.HeadTTT", callContext);
		assertNotNull(hasRelation);
		assertFalse(hasRelation);


		try {
			hasRelation = client.hasOrgPersonRelation(null, "KIM-1", "kuali.org.PersonRelation.Head", callContext);
			assertFalse(true);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}

		try {
			hasRelation = client.hasOrgPersonRelation("68", null, "kuali.org.PersonRelation.Head", callContext);
			assertFalse(true);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}

		try {
			hasRelation = client.hasOrgPersonRelation("68", "KIM-1", null, callContext);
			assertFalse(true);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}


	}


	@Test
	public void testGetOrgTreeInfo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		// test getting one level
		List<OrgTreeInfo> results = client.getOrgTree("4", "kuali.org.hierarchy.Main", 1, callContext);
		//assertEquals(9,results.size());

		// test getting the whole tree
		results = client.getOrgTree("4", "kuali.org.hierarchy.Main", 0, callContext);
		//assertEquals(142, results.size());
	}

	@Test
	public void testHasOrgOrgRelation() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		assertTrue(client.hasOrgOrgRelation("15", "28", "kuali.org.Part", callContext));
		assertFalse(client.hasOrgOrgRelation("1", "15", "kuali.org.Part", callContext));
	}

	@Test
	public void getOrgPersonIdsByRelationType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		List<OrgPersonRelationInfo> result = client.getOrgPersonRelationsByTypeAndOrg("kuali.org.PersonRelation.Professor", "68", callContext);
		//assertEquals(2, result.size());
		//result = client.getOrgPersonRelationsByTypeAndOrg("kuali.org.PersonRelation.Coordinator", "147", callContext);
		//assertEquals(1, result.size());
		//assertEquals("KIM-3", result.get(0));
	}

	@Test
	public void removePositionRestrictionFromOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		StatusInfo si;
		try {
			si = client.deleteOrgPositionRestriction("68", callContext);
			assertTrue(si.getIsSuccess());
		} catch (DoesNotExistException e) {
			assertTrue(false);
		}

		try {
			si = client.deleteOrgPositionRestriction("68", callContext);
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}

		try {
			si = client.deleteOrgPositionRestriction(null, callContext);
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testHierarchiesOrgIsIn() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		/*List<SearchParam> searchParams = new ArrayList<SearchParam>();
		SearchParam searchParam = new SearchParam();
		searchParam.setKey("org.queryParam.orgId");
		searchParam.setValue("31");
		searchParams.add(searchParam);
		
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setSearchKey("org.search.hierarchiesOrgIsIn");
		searchRequest.setParams(searchParams);
		
		SearchResult result = client.search(searchRequest);
		assertEquals(1,result.getRows().size());
		List<SearchResultCell> cells = result.getRows().get(0).getCells();
		assertEquals(1,cells.size());
		SearchResultCell cell = cells.get(0);
		assertEquals("org.resultColumn.orgHierarchyId", cell.getKey());
		assertEquals("kuali.org.hierarchy.Main", cell.getValue());*/
	}	
}
