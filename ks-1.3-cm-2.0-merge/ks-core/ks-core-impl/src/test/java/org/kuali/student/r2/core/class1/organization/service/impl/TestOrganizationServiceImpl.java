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
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
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
import org.kuali.student.r2.core.organization.infc.OrgPositionRestriction;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.common.dto.TypeInfo;
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
	public OrganizationService orgService;

	public static String principalId = "123";

    public ContextInfo callContext = null;
    
    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }
    
	@Test
	public void testSearchForOrgs() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
	    QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "1"));
        QueryByCriteria qbc = qbcBuilder.build();
        try {
            List<OrgInfo> orgInfos = orgService.searchForOrgs(qbc, callContext);
            assertNotNull(orgInfos);
            assertEquals(1, orgInfos.size());
            OrgInfo orgInfo = orgInfos.get(0);
            assertEquals("1", orgInfo.getId());
            assertEquals("KUSystem", orgInfo.getShortName());
            assertEquals("Kuali University System", orgInfo.getLongName());
            assertEquals("", orgInfo.getShortDescr().getPlain());
            assertEquals("", orgInfo.getLongDescr().getPlain());
            assertEquals("kuali.org.CorporateEntity", orgInfo.getTypeKey());

            List<String> orgIds = orgService.searchForOrgIds(qbc, callContext);
            assertNotNull(orgIds);
            assertEquals(1, orgIds.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }

	}
	
	@Test
    public void testSearchForOrgOrgRelations() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "1"));
        QueryByCriteria qbc = qbcBuilder.build();
        try {
            List<OrgOrgRelationInfo> orgOrgRelationInfos = orgService.searchForOrgOrgRelations(qbc, callContext);
            assertNotNull(orgOrgRelationInfos);
            assertEquals(1, orgOrgRelationInfos.size());
            OrgOrgRelationInfo orgOrgRelationInfo = orgOrgRelationInfos.get(0);
            assertEquals("1", orgOrgRelationInfo.getId());
            assertEquals("1", orgOrgRelationInfo.getOrgId());
            assertEquals("2", orgOrgRelationInfo.getRelatedOrgId());
            assertEquals("kuali.org.Board", orgOrgRelationInfo.getTypeKey());
            
            List<String> orgOrgRelationIds = orgService.searchForOrgOrgRelationIds(qbc, callContext);
            assertNotNull(orgOrgRelationIds);
            assertEquals(1, orgOrgRelationIds.size());

        } catch (Exception e) {
            fail(e.getMessage());
        }

    }
	
	@Test
    public void testSearchForOrgPositionRestrictions() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "1"));
        QueryByCriteria qbc = qbcBuilder.build();
        try {
            List<OrgPositionRestrictionInfo> orgPositionRestrictionInfos = orgService.searchForOrgPositionRestrictions(qbc, callContext);
            assertNotNull(orgPositionRestrictionInfos);
            assertEquals(1, orgPositionRestrictionInfos.size());
            OrgPositionRestriction orgPositionRestriction = orgPositionRestrictionInfos.get(0);
            assertEquals("1", orgPositionRestriction.getId());
            assertEquals("2", orgPositionRestriction.getOrgId());
            assertEquals("100", orgPositionRestriction.getMaxNumRelations());
            assertEquals("kuali.org.PersonRelation.Member", orgPositionRestriction.getOrgPersonRelationTypeKey());

            List<String> orgPositionRestrictionIds = orgService.searchForOrgPositionRestrictionIds(qbc, callContext);
            assertNotNull(orgPositionRestrictionIds);
            assertEquals(1, orgPositionRestrictionIds.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }
	
	@Test
    public void testSearchForOrgPersonRelations() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "1"));
        QueryByCriteria qbc = qbcBuilder.build();
        try {
            List<OrgPersonRelationInfo> orgPersonRelationInfos = orgService.searchForOrgPersonRelations(qbc, callContext);
            assertNotNull(orgPersonRelationInfos);
            assertEquals(1, orgPersonRelationInfos.size());
            OrgPersonRelationInfo orgPersonRelationInfo = orgPersonRelationInfos.get(0);
            assertEquals("1", orgPersonRelationInfo.getId());
            assertEquals("kuali.org.PersonRelation.Head", orgPersonRelationInfo.getTypeKey());
            assertEquals("68", orgPersonRelationInfo.getOrgId());
            assertEquals("KIM-1", orgPersonRelationInfo.getPersonId());

            List<String> orgPersonRelationIds = orgService.searchForOrgPersonRelationIds(qbc, callContext);
            assertNotNull(orgPersonRelationIds);
            assertEquals(1, orgPersonRelationIds.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }

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
		
		OrgInfo createOrg1 = orgService.createOrg("kuali.org.Program", orgInfo, callContext);
		OrgInfo createOrg = orgService.getOrg(createOrg1.getId(), callContext);
		
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

		OrgInfo updateInfo = orgService.getOrg(createOrg.getId(), callContext);
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
			updated = orgService.updateOrg(updateInfo.getId(), updateInfo, callContext);
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
			orgService.updateOrg(updateInfo.getId(), updateInfo, callContext);
			fail("Should throw VersionMismatchException");
		} catch (VersionMismatchException e) {
		}
		
		// now test delete (and clean up changes made)
		StatusInfo si;
		String orgId = createOrg.getId();
		try {
			si = orgService.deleteOrg(orgId, callContext);
			assertTrue(si.getIsSuccess());
		} catch (DoesNotExistException e) {
			fail("OrganizationService.deleteOrganization() failed deleting just-created Organization");
		}

		try {
			orgService.deleteOrgPersonRelation(orgId, callContext);
			fail("OrganizationService.deleteOrganization() of a deleted Organization did not throw DoesNotExistException as expected");
		} catch (DoesNotExistException e) {
		}

		try {
			orgService.deleteOrgPersonRelation(null, callContext);
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

		OrgPersonRelationInfo createdOPRInfo = orgService.createOrgPersonRelation("28", "KIM-12345", "kuali.org.PersonRelation.Dean", orgPersonRelationInfo, callContext);

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
			si = orgService.deleteOrgPersonRelation(oprId, callContext);
			assertTrue(si.getIsSuccess());
		} catch (DoesNotExistException e) {
			fail("OrganizationService.removeOrgPersonRelation() failed removing just-created OrgPersonRelation");
		}

		try {
			orgService.deleteOrgPersonRelation(oprId, callContext);
			fail("OrganizationService.removeOrgPersonRelation() of a deleted OrgPersonRelation did not throw DoesNotExistException as expected");
		} catch (DoesNotExistException e) {
		}

		try {
			orgService.deleteOrgPersonRelation(null, callContext);
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

		OrgOrgRelationInfo createdOORInfo = orgService.createOrgOrgRelation("16", "17", "kuali.org.Part", orgOrgRelationInfo, callContext);

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
			si = orgService.deleteOrgOrgRelation(oorId, callContext);
			assertTrue(si.getIsSuccess());
		} catch (DoesNotExistException e) {
			fail("OrganizationService.removeOrgOrgRelation() failed removing just-created OrgOrgRelation");
		}

		try {
			orgService.deleteOrgOrgRelation(oorId, callContext);
			fail("OrganizationService.removeOrgOrgRelation() of a deleted OrgOrgRelation did not throw DoesNotExistException as expected");
		} catch (DoesNotExistException e) {
		}

		try {
			orgService.deleteOrgOrgRelation(null, callContext);
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

		OrgPositionRestrictionInfo created = orgService.createOrgPositionRestriction("1", "kuali.org.PersonRelation.Treasurer", orgPositionRestrictionInfo, callContext);
		
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
		List<OrgHierarchyInfo> orgHierarchyInfos = orgService.getOrgHierarchies(callContext);
		assertEquals(2,orgHierarchyInfos.size());
	}

	@Test
	public void getOrgOrgRelationsByOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationInfo> orgOrgRelationInfos = orgService.getOrgOrgRelationsByOrg("4", callContext);
		assertEquals(8,orgOrgRelationInfos.size());

		orgOrgRelationInfos = orgService.getOrgOrgRelationsByOrg("-1", callContext);
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}

	@Test
	public void getAllOrgPersonRelationsByOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgPersonRelationInfo> orgPersonRelationInfos = orgService.getOrgPersonRelationsByOrg("68", callContext);
		assertEquals(3, orgPersonRelationInfos.size());

		orgPersonRelationInfos = orgService.getOrgPersonRelationsByOrg("-1", callContext);
		assertTrue(orgPersonRelationInfos == null || orgPersonRelationInfos.size() == 0);
	}

	@Test
	public void getPositionRestrictionsByOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<String>  orgPositionRestrictionInfos = orgService.getOrgPositionRestrictionIdsByOrg("19", callContext);
		assertEquals(2, orgPositionRestrictionInfos.size());

		 orgPositionRestrictionInfos = orgService.getOrgPositionRestrictionIdsByOrg("-1", callContext);
		 assertTrue(orgPositionRestrictionInfos == null || orgPositionRestrictionInfos.size() == 0);
	}

	@Test
	public void getOrgTypes() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<TypeInfo> orgTypeInfos = orgService.getOrgTypes(callContext);
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
		List<String> descendants = orgService.getAllDescendants("6", "kuali.org.hierarchy.Main", callContext);
		assertEquals(22, descendants.size());
		assertTrue(descendants.containsAll(Arrays.asList("7", "121", "141")));

		descendants = orgService.getAllDescendants("4", "Star.Trek", callContext);
		assertTrue(descendants == null || descendants.size() == 0);

		descendants = orgService.getAllDescendants("-1", "kuali.org.hierarchy.Main", callContext);
		assertTrue(descendants == null || descendants.size() == 0);

		descendants = orgService.getAllDescendants("-1", "-1", callContext);
		assertTrue(descendants == null || descendants.size() == 0);
	}


	@Test
	public void getAncestors() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<String> ancestors = orgService.getAllAncestors("26", "kuali.org.hierarchy.Main", callContext);
		assertEquals(4,ancestors.size());
		assertTrue(ancestors.containsAll(Arrays.asList("1", "4", "15", "19")));

		ancestors = orgService.getAllAncestors("2", "Star.Trek", callContext);
		assertTrue(ancestors == null || ancestors.size() == 0);

		ancestors = orgService.getAllAncestors("-1", "kuali.org.hierarchy.Main", callContext);
		assertTrue(ancestors == null || ancestors.size() == 0);

		ancestors = orgService.getAllAncestors("-1", "-1", callContext);
		assertTrue(ancestors == null || ancestors.size() == 0);
	}

	
	@Test
	public void isDescendant() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		assertTrue(orgService.isDescendant("1", "26", "kuali.org.hierarchy.Main", callContext));
		assertTrue(orgService.isDescendant("19", "26", "kuali.org.hierarchy.Main", callContext));
		assertFalse(orgService.isDescendant("5", "26", "kuali.org.hierarchy.Main", callContext));
	}

	@Test
	public void getOrgOrgRelationType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<TypeInfo> orgOrgRelationTypeInfos = orgService.getOrgOrgRelationTypes(callContext);
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
		List<TypeInfo> orgOrgRelationTypeInfos = orgService.getOrgOrgRelationTypesForOrgHierarchy("kuali.org.hierarchy.Main", callContext);
		assertEquals(12, orgOrgRelationTypeInfos.size());

		orgOrgRelationTypeInfos = orgService.getOrgOrgRelationTypesForOrgHierarchy("Red.Dwarf", callContext);
		assertTrue(orgOrgRelationTypeInfos == null || orgOrgRelationTypeInfos.size() == 0);
	}

	@Test
	public void getOrgHierarchy() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgHierarchyInfo orgHierarchyInfo = orgService.getOrgHierarchy("kuali.org.hierarchy.Curriculum", callContext);
		assertEquals("kuali.org.hierarchy.Curriculum", orgHierarchyInfo.getId());

		try {
			orgHierarchyInfo = orgService.getOrgHierarchy("Spectre", callContext);
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}
	}

	@Test
	public void getOrgPersonRelationTypesForOrgType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<TypeInfo> orgPersonRelationsByOrgType = orgService.getOrgPersonRelationTypesForOrgType("kuali.org.School", callContext);
		assertEquals(2, orgPersonRelationsByOrgType.size());

		orgPersonRelationsByOrgType = orgService.getOrgPersonRelationTypesForOrgType("K12", callContext);
		assertTrue(orgPersonRelationsByOrgType == null || orgPersonRelationsByOrgType.size() == 0);
	}

	@Test
	public void getOrganization() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgInfo org = orgService.getOrg("42", callContext);
		assertEquals(org.getId(), "42");

		try {
			org = orgService.getOrg("Kaos", callContext);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void getOrgOrgRelation() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgOrgRelationInfo orgOrgRelationInfo = orgService.getOrgOrgRelation("16", callContext);
		assertNotNull(orgOrgRelationInfo);

		try {
			orgOrgRelationInfo = orgService.getOrgOrgRelation("-1", callContext);
			assertTrue(false);
		} catch (DoesNotExistException e1) {
			assertTrue(true);
		}

		try {
			orgOrgRelationInfo = orgService.getOrgOrgRelation(null, callContext);
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}
	}


	@Test
	public void getOrgOrgRelationsByIds() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<String> idList = new ArrayList<String>(2);
		idList.add("3");
		idList.add("15");
		List<OrgOrgRelationInfo> orgOrgRelationInfos = orgService.getOrgOrgRelationsByIds(idList, callContext);
		assertEquals(2, orgOrgRelationInfos.size());

		idList.add("-1");
		orgOrgRelationInfos = orgService.getOrgOrgRelationsByIds(idList, callContext);
		assertEquals(2, orgOrgRelationInfos.size());

		idList = new ArrayList<String>(2);
		idList.add("-1");
		idList.add("-2");
		orgOrgRelationInfos = orgService.getOrgOrgRelationsByIds(idList, callContext);
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}

	@Test
	public void getOrgOrgRelationsByRelatedOrg() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationInfo> orgOrgRelationInfos = orgService.getOrgOrgRelationsByOrg("16", callContext);
		assertEquals(10, orgOrgRelationInfos.size());

		orgOrgRelationInfos = orgService.getOrgOrgRelationsByOrg("-1", callContext);
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}

	@Test
	public void getOrgPersonRelationsByIds() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<String> idList = new ArrayList<String>(2);
		idList.add("2");
		idList.add("3");
		List<OrgPersonRelationInfo> orgOrgRelationInfos = orgService.getOrgPersonRelationsByIds(idList, callContext);
		assertEquals(2, orgOrgRelationInfos.size());

		idList.add("-1");
		orgOrgRelationInfos = orgService.getOrgPersonRelationsByIds(idList, callContext);
		assertEquals(2, orgOrgRelationInfos.size());

		idList = new ArrayList<String>(2);
		idList.add("-1");
		idList.add("-2");
		orgOrgRelationInfos = orgService.getOrgPersonRelationsByIds(idList, callContext);
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}
	
	@Test
	public void getOrgPersonRelationsByOrg() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	    List<OrgPersonRelationInfo> orgPersonRelations = orgService.getOrgPersonRelationsByTypeAndOrgAndPerson("kuali.org.PersonRelation.Professor", "68", "KIM-1", callContext);
	    assertNotNull(orgPersonRelations);
        assertEquals(1, orgPersonRelations.size());
    }

	@Test
    public void getOrgPersonRelationsByTypeAndOrg() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<OrgPersonRelationInfo> orgPersonRelations = orgService.getOrgPersonRelationsByTypeAndOrg("kuali.org.PersonRelation.Professor", "68", callContext);
        assertNotNull(orgPersonRelations);
        assertEquals(2, orgPersonRelations.size());
    }

    @Test
    public void getOrgPersonRelationsByPerson() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<OrgPersonRelationInfo> orgPersonRelations = orgService.getOrgPersonRelationsByPerson("KIM-1", callContext);
        assertNotNull(orgPersonRelations);
        assertEquals(3, orgPersonRelations.size());
    }

    @Test
    public void getOrgPersonRelationsByTypeAndPerson() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<OrgPersonRelationInfo> orgPersonRelations = orgService.getOrgPersonRelationsByTypeAndPerson("kuali.org.PersonRelation.Head", "KIM-1", callContext);
        assertNotNull(orgPersonRelations);
        assertEquals(2, orgPersonRelations.size());
    }

    @Test
    public void getOrgPersonRelationsByOrgAndPerson() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<OrgPersonRelationInfo> orgPersonRelations = orgService.getOrgPersonRelationsByOrgAndPerson("68", "KIM-1", callContext);
        assertNotNull(orgPersonRelations);
        assertEquals(2, orgPersonRelations.size());
    }
    
	@Test
	public void getOrgOrgRelationTypesForOrgType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<TypeInfo> orgOrgRelationTypeInfos = orgService.getOrgOrgRelationTypesForOrgType("kuali.org.Division", callContext);
		assertEquals(4, orgOrgRelationTypeInfos.size());

		orgOrgRelationTypeInfos = orgService.getOrgOrgRelationTypesForOrgType("org.klingon", callContext);
		assertTrue(orgOrgRelationTypeInfos == null || orgOrgRelationTypeInfos.size() == 0);
	}

	@Test
	public void hasOrgPersonRelation() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		Boolean hasRelation = orgService.hasOrgPersonRelation("68", "KIM-1", "kuali.org.PersonRelation.Head", callContext);
		assertNotNull(hasRelation);
		assertTrue(hasRelation);

		hasRelation = orgService.hasOrgPersonRelation("68x", "KIM-1", "kuali.org.PersonRelation.Head", callContext);
		assertNotNull(hasRelation);
		assertFalse(hasRelation);

		hasRelation = orgService.hasOrgPersonRelation("68", "KIM--1", "kuali.org.PersonRelation.Head", callContext);
		assertNotNull(hasRelation);
		assertFalse(hasRelation);

		hasRelation = orgService.hasOrgPersonRelation("68", "KIM-1", "kuali.org.PersonRelation.HeadTTT", callContext);
		assertNotNull(hasRelation);
		assertFalse(hasRelation);


		try {
			hasRelation = orgService.hasOrgPersonRelation(null, "KIM-1", "kuali.org.PersonRelation.Head", callContext);
			assertFalse(true);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}

		try {
			hasRelation = orgService.hasOrgPersonRelation("68", null, "kuali.org.PersonRelation.Head", callContext);
			assertFalse(true);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}

		try {
			hasRelation = orgService.hasOrgPersonRelation("68", "KIM-1", null, callContext);
			assertFalse(true);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}


	}


	@Test
	public void testGetOrgTreeInfo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		// test getting one level
		List<OrgTreeInfo> results = orgService.getOrgTree("4", "kuali.org.hierarchy.Main", 1, callContext);
		assertEquals(9,results.size());

		// test getting the whole tree
		results = orgService.getOrgTree("4", "kuali.org.hierarchy.Main", 0, callContext);
		assertEquals(142, results.size());
	}

	@Test
	public void testHasOrgOrgRelation() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		assertTrue(orgService.hasOrgOrgRelation("15", "28", "kuali.org.Part", callContext));
		assertFalse(orgService.hasOrgOrgRelation("1", "15", "kuali.org.Part", callContext));
	}

	@Test
	public void removePositionRestrictionFromOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		StatusInfo si;
		try {
			si = orgService.deleteOrgPositionRestriction("68", callContext);
			assertTrue(si.getIsSuccess());
		} catch (DoesNotExistException e) {
			assertTrue(false);
		}

		try {
			si = orgService.deleteOrgPositionRestriction("68", callContext);
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}

		try {
			si = orgService.deleteOrgPositionRestriction(null, callContext);
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}
	}	
	
	@Test
	public void getOrgsByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	    String orgTypeKey = "kuali.org.Department";
	    List<String> orgIds = orgService.getOrgIdsByType(orgTypeKey, callContext);
	    assertNotNull(orgIds);
	    assertEquals(28, orgIds.size());
	    assertTrue(orgIds.contains("12"));
	    assertTrue(orgIds.contains("64"));
    }

	@Test
    public void getOrgOrgRelationsByTypeAndOrg() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        String orgId = "4"; 
        String orgOrgRelationTypeKey = "kuali.org.Part";
        List<OrgOrgRelationInfo> orgOrgRelations = orgService.getOrgOrgRelationsByTypeAndOrg(orgId, orgOrgRelationTypeKey, callContext);
        assertNotNull(orgOrgRelations);
        assertEquals(5, orgOrgRelations.size());
        
        boolean found = false;
        for (OrgOrgRelationInfo relation : orgOrgRelations){
            if (relation.getId().equals("13")){
                assertEquals("4", relation.getOrgId());
                assertEquals("15", relation.getRelatedOrgId());
                assertEquals("kuali.org.Part", relation.getTypeKey());
                found = true;
            }
        }
        assertTrue(found);
    }
    
	@Test
    public void getOrgOrgRelationsByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String orgOrgRelationTypeKey = "kuali.org.Part";
        List<String> orgOrgRelationIds = orgService.getOrgOrgRelationIdsByType(orgOrgRelationTypeKey, callContext);
        assertNotNull(orgOrgRelationIds);
        assertEquals(45, orgOrgRelationIds.size());
        assertTrue(orgOrgRelationIds.contains("13"));
        assertTrue(orgOrgRelationIds.contains("31"));
    }
    
	@Test
    public void getOrgPositionRestrictionsByIds() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgPositionRestrictionIds = new ArrayList<String>();
        orgPositionRestrictionIds.add("40");
        orgPositionRestrictionIds.add("41");
        orgPositionRestrictionIds.add("42");
        List<OrgPositionRestrictionInfo> orgPositionRestrictions = orgService.getOrgPositionRestrictionsByIds(orgPositionRestrictionIds, callContext);
        assertNotNull(orgPositionRestrictions);
        assertEquals(3, orgPositionRestrictions.size());
        
        boolean found = false;
        for (OrgPositionRestrictionInfo restriction : orgPositionRestrictions){
            if (restriction.getId().equals("40")){
                assertEquals("67", restriction.getOrgId());
                assertEquals("kuali.org.PersonRelation.Chair", restriction.getOrgPersonRelationTypeKey());
                found = true;
            }
        }
        assertTrue(found);
    }

	@Test
    public void getOrgPositionRestrictionsByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String orgPersonRelationTypeKey = "kuali.org.PersonRelation.President";
        List<String> orgPositionRestrictionIds = orgService.getOrgPositionRestrictionIdsByType(orgPersonRelationTypeKey, callContext);
        assertNotNull(orgPositionRestrictionIds);
        assertEquals(2, orgPositionRestrictionIds.size());
        assertTrue(orgPositionRestrictionIds.contains("5"));
        assertTrue(orgPositionRestrictionIds.contains("70"));
    }
    
	@Test
    public void getOrgHierarchiesByIds() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        List<String> orgHierarchyIds = new ArrayList<String>();
        orgHierarchyIds.add("kuali.org.hierarchy.Curriculum");
        orgHierarchyIds.add("kuali.org.hierarchy.Main");
        List<OrgHierarchyInfo> orgHierarchies = orgService.getOrgHierarchiesByIds(orgHierarchyIds, callContext);
        assertNotNull(orgHierarchies);
        assertEquals(2, orgHierarchies.size());
        
        boolean found = false;
        for (OrgHierarchyInfo hierarchy : orgHierarchies){
            if (hierarchy.getId().equals("kuali.org.hierarchy.Main")){
                assertEquals("4", hierarchy.getRootOrgId());
                assertEquals("Main", hierarchy.getName());
                found = true;
            }
        }
        assertTrue(found);
    }

	@Test
    public void getOrgHierarchiesByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String orgHierarchyTypeKey = "kuali.org.hierarchy.Main";
        List<String> orgHierarchyIds = orgService.getOrgHierarchyIdsByType(orgHierarchyTypeKey, callContext);
        assertNotNull(orgHierarchyIds);
        assertEquals(1, orgHierarchyIds.size());
        assertTrue(orgHierarchyIds.contains("kuali.org.hierarchy.Main"));
    }
}
