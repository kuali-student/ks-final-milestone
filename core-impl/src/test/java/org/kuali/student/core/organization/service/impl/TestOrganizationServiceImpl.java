package org.kuali.student.core.organization.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.atp.dto.TimeAmountInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
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


@Daos( { @Dao(value = "org.kuali.student.core.organization.dao.impl.OrganizationDaoImpl",testSqlFile="classpath:ks-org.sql"/*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/organization-persistence.xml")
public class TestOrganizationServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.core.organization.service.impl.OrganizationServiceImpl", port = "8181")
	public OrganizationService client;


	@Test
	public void testCreateUpdateOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		OrgInfo orgInfo = new OrgInfo();
		orgInfo.setDesc("Description for new OrgInfo");
		orgInfo.setLongName("TestOrgLongName");
		orgInfo.setShortName("TestOrgShortName");
		orgInfo.setState("Active");
		orgInfo.setType("");//set as a param
		orgInfo.setEffectiveDate(df.parse("20090101"));
		orgInfo.setExpirationDate(df.parse("21001231"));
		orgInfo.getAttributes().put("Alias", "OrgAlias");

		OrgInfo createOrg = client.createOrganization("kuali.org.Program", orgInfo);

		//Validate all fields
		assertEquals("Description for new OrgInfo",createOrg.getDesc());
		assertEquals("TestOrgLongName",createOrg.getLongName());
		assertEquals("TestOrgShortName",createOrg.getShortName());
		assertEquals("Active",createOrg.getState());
		assertEquals("kuali.org.Program",createOrg.getType());
		assertEquals(df.parse("20090101"),createOrg.getEffectiveDate());
		assertEquals(df.parse("21001231"),createOrg.getExpirationDate());
		assertEquals("OrgAlias",createOrg.getAttributes().get("Alias"));
		assertNotNull(createOrg.getId());

		OrgInfo updateInfo = client.getOrganization(createOrg.getId());
		updateInfo.setDesc("Updated Description for new OrgInfo");
		updateInfo.setLongName("Updated TestOrgLongName");
		updateInfo.setShortName("Updated TestOrgShortName");
		updateInfo.setState("Updated Active");
		updateInfo.setType("kuali.org.College");
		updateInfo.setEffectiveDate(df.parse("20090111"));
		updateInfo.setExpirationDate(df.parse("21001211"));
		updateInfo.getAttributes().put("Alias", "Updated OrgAlias");
		
		OrgInfo updated=null;
		try {
			updated = client.updateOrganization(updateInfo.getId(), updateInfo);
		} catch (VersionMismatchException e) {
			fail("Should not throw VersionMismatchException");
		}
		
		//Validate
		assertEquals("Updated Description for new OrgInfo",updated.getDesc());
		assertEquals("Updated TestOrgLongName",updated.getLongName());
		assertEquals("Updated TestOrgShortName",updated.getShortName());
		assertEquals("Updated Active",updated.getState());
		assertEquals("kuali.org.College",updated.getType());
		assertEquals(df.parse("20090111"),updated.getEffectiveDate());
		assertEquals(df.parse("21001211"),updated.getExpirationDate());
		assertEquals("Updated OrgAlias",updated.getAttributes().get("Alias"));
		
		//Check version mismatch
		try {
			client.updateOrganization(updateInfo.getId(), updateInfo);
			fail("Should throw VersionMismatchException");
		} catch (VersionMismatchException e) {
		}
		
	}

	@Test
	public void testCreateOrgPersonRelation() throws ParseException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		OrgPersonRelationInfo orgPersonRelationInfo = new OrgPersonRelationInfo();
		orgPersonRelationInfo.setState("Active");
		orgPersonRelationInfo.setEffectiveDate(df.parse("20090101"));
		orgPersonRelationInfo.setExpirationDate(df.parse("21001231"));
		orgPersonRelationInfo.setOrgId("");
		orgPersonRelationInfo.setPersonId("");
		orgPersonRelationInfo.setType("");

		OrgPersonRelationInfo createdOPRInfo = client.createOrgPersonRelation("28", "KIM-12345", "kuali.org.PersonRelation.Dean", orgPersonRelationInfo);

		//Validate all fields
		assertEquals("Active",createdOPRInfo.getState());
		assertEquals(df.parse("20090101"),createdOPRInfo.getEffectiveDate());
		assertEquals(df.parse("21001231"),createdOPRInfo.getExpirationDate());
		assertEquals("28",createdOPRInfo.getOrgId());
		assertEquals("KIM-12345",createdOPRInfo.getPersonId());
		assertEquals("kuali.org.PersonRelation.Dean",createdOPRInfo.getType());
		assertNotNull(createdOPRInfo.getId());
		
//		OrgPersonRelationInfo updateInfo = client.getOrgPersonRelation(createdOPRInfo.getId());
//		updateInfo.setState("Updated Active");
//		updateInfo.setEffectiveDate(df.parse("20090111"));
//		updateInfo.setExpirationDate(df.parse("21001211"));
//		updateInfo.setOrgId("4");
//		updateInfo.setPersonId("Updated KIM-12345");
//		updateInfo.setType("kuali.org.PersonRelation.Dean");
		
		
	}

	@Test
	public void testCreateDeleteOrgOrgRelation() throws ParseException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		OrgOrgRelationInfo orgOrgRelationInfo = new OrgOrgRelationInfo();
		orgOrgRelationInfo.setState("Active");
		orgOrgRelationInfo.setEffectiveDate(df.parse("20090101"));
		orgOrgRelationInfo.setExpirationDate(df.parse("21001231"));
		orgOrgRelationInfo.setOrgId("");
		orgOrgRelationInfo.setRelatedOrgId("");
		orgOrgRelationInfo.setType("");

		OrgOrgRelationInfo createdOORInfo = client.createOrgOrgRelation("16", "17", "kuali.org.Part", orgOrgRelationInfo);

		//Validate all fields
		assertEquals("Active",createdOORInfo.getState());
		assertEquals(df.parse("20090101"),createdOORInfo.getEffectiveDate());
		assertEquals(df.parse("21001231"),createdOORInfo.getExpirationDate());
		assertEquals("16",createdOORInfo.getOrgId());
		assertEquals("17",createdOORInfo.getRelatedOrgId());
		assertEquals("kuali.org.Part",createdOORInfo.getType());
		assertNotNull(createdOORInfo.getId());
	}
	
	@Test
	public void testAddPositionRestriction() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		
		TimeAmountInfo stdDuration = new TimeAmountInfo();
		stdDuration.setAtpDurationTypeKey("ks.foreign.atp.key");
		stdDuration.setTimeQuantity(new Integer(123456));
		
		OrgPositionRestrictionInfo orgPositionRestrictionInfo = new OrgPositionRestrictionInfo();
		orgPositionRestrictionInfo.setDesc("Description For Position Restriction");
		orgPositionRestrictionInfo.setMaxNumRelations("2345");
		orgPositionRestrictionInfo.setMinNumRelations(2);
		orgPositionRestrictionInfo.setStdDuration(stdDuration);
		orgPositionRestrictionInfo.setTitle("Title for PositionRestriction");
		orgPositionRestrictionInfo.setOrgId("");
		orgPositionRestrictionInfo.setOrgPersonRelationTypeKey("");
		
		OrgPositionRestrictionInfo created = client.addPositionRestrictionToOrg("1", "kuali.org.PersonRelation.Treasurer", orgPositionRestrictionInfo);
		
		//validate fields
		assertEquals("Description For Position Restriction",created.getDesc());
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
		List<OrgHierarchyInfo> orgHierarchyInfos = client.getOrgHierarchies();
		assertEquals(2,orgHierarchyInfos.size());
	}

	@Test
	public void getOrgOrgRelationsByOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationInfo> orgOrgRelationInfos = client.getOrgOrgRelationsByOrg("4");
		assertEquals(8,orgOrgRelationInfos.size());

		orgOrgRelationInfos = client.getOrgOrgRelationsByOrg("-1");
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}

	@Test
	public void getAllOrgPersonRelationsByOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgPersonRelationInfo> orgPersonRelationInfos = client.getAllOrgPersonRelationsByOrg("68");
		assertEquals(3, orgPersonRelationInfos.size());

		orgPersonRelationInfos = client.getAllOrgPersonRelationsByOrg("-1");
		assertTrue(orgPersonRelationInfos == null || orgPersonRelationInfos.size() == 0);
	}

	@Test
	public void getPositionRestrictionsByOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgPositionRestrictionInfo>  orgPositionRestrictionInfos = client.getPositionRestrictionsByOrg("19");
		assertEquals(6, orgPositionRestrictionInfos.size());

		 orgPositionRestrictionInfos = client.getPositionRestrictionsByOrg("-1");
		 assertTrue(orgPositionRestrictionInfos == null || orgPositionRestrictionInfos.size() == 0);
	}

	@Test
	public void getOrgTypes() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgTypeInfo> orgTypeinfos = client.getOrgTypes();
		assertEquals(17, orgTypeinfos.size());
	}

	@Test
	public void getOrgType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgTypeInfo orgTypeInfo = client.getOrgType("kuali.org.Division");
		assertEquals(orgTypeInfo.getKey(), "kuali.org.Division");

		try {
			orgTypeInfo = client.getOrgType("Dr.Who");
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}
	}

	@Test
	public void getAllDescendants() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<String> descendants = client.getAllDescendants("4", "kuali.org.hierarchy.Main");
		assertEquals(8,descendants.size());

		descendants = client.getAllDescendants("4", "Star.Trek");
		assertTrue(descendants == null || descendants.size() == 0);

		descendants = client.getAllDescendants("-1", "kuali.org.hierarchy.Main");
		assertTrue(descendants == null || descendants.size() == 0);

		descendants = client.getAllDescendants("-1", "-1");
		assertTrue(descendants == null || descendants.size() == 0);
	}


	@Test
	public void getOrgOrgRelationTypes() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationTypeInfo> orgOrgRelationTypeInfos = client.getOrgOrgRelationTypes();
		assertNotNull(orgOrgRelationTypeInfos);
		assertEquals(13, orgOrgRelationTypeInfos.size());
	}

	@Test
	public void getOrgOrgRelationType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgOrgRelationTypeInfo orgOrgRelationTypeInfo = client.getOrgOrgRelationType("kuali.org.Report");
		assertEquals(orgOrgRelationTypeInfo.getKey(), "kuali.org.Report");

		try {
			orgOrgRelationTypeInfo = client.getOrgOrgRelationType("Babylon.5");
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}
	}

	@Test
	public void getOrgOrgRelationTypesForOrgHierarchy() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationTypeInfo> orgOrgRelationTypeInfos = client.getOrgOrgRelationTypesForOrgHierarchy("kuali.org.hierarchy.Main");
		assertEquals(12, orgOrgRelationTypeInfos.size());

		orgOrgRelationTypeInfos = client.getOrgOrgRelationTypesForOrgHierarchy("Red.Dwarf");
		assertTrue(orgOrgRelationTypeInfos == null || orgOrgRelationTypeInfos.size() == 0);
	}

	@Test
	public void getAllOrgPersonRelationsByPerson() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgPersonRelationInfo> orgPersonRelationsByPerson = client.getAllOrgPersonRelationsByPerson("KIM-1");
		assertEquals(3, orgPersonRelationsByPerson.size());

		orgPersonRelationsByPerson = client.getAllOrgPersonRelationsByPerson("Homer");
		assertTrue(orgPersonRelationsByPerson == null || orgPersonRelationsByPerson.size() == 0);
	}

	@Test
	public void getOrgHierarchy() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgHierarchyInfo orgHierarchyInfo = client.getOrgHierarchy("kuali.org.hierarchy.Curriculum");
		assertEquals(orgHierarchyInfo.getKey(), "kuali.org.hierarchy.Curriculum");

		try {
			orgHierarchyInfo = client.getOrgHierarchy("Spectre");
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}
	}

	@Test
	public void getOrgPersonRelationTypesForOrgType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgPersonRelationTypeInfo> orgPersonRelationsByOrgType = client.getOrgPersonRelationTypesForOrgType("kuali.org.School");
		assertEquals(2, orgPersonRelationsByOrgType.size());

		orgPersonRelationsByOrgType = client.getOrgPersonRelationTypesForOrgType("K12");
		assertTrue(orgPersonRelationsByOrgType == null || orgPersonRelationsByOrgType.size() == 0);
	}

	@Test
	public void getOrganization() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgInfo org = client.getOrganization("42");
		assertEquals(org.getId(), "42");

		try {
			org = client.getOrganization("Kaos");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void getOrgOrgRelation() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgOrgRelationInfo orgOrgRelationInfo = client.getOrgOrgRelation("16");
		assertNotNull(orgOrgRelationInfo);

		try {
			orgOrgRelationInfo = client.getOrgOrgRelation("-1");
			assertTrue(false);
		} catch (DoesNotExistException e1) {
			assertTrue(true);
		}

		try {
			orgOrgRelationInfo = client.getOrgOrgRelation(null);
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
		List<OrgOrgRelationInfo> orgOrgRelationInfos = client.getOrgOrgRelationsByIdList(idList);
		assertEquals(2, orgOrgRelationInfos.size());

		idList.add("-1");
		orgOrgRelationInfos = client.getOrgOrgRelationsByIdList(idList);
		assertEquals(2, orgOrgRelationInfos.size());

		idList = new ArrayList<String>(2);
		idList.add("-1");
		idList.add("-2");
		orgOrgRelationInfos = client.getOrgOrgRelationsByIdList(idList);
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}

	@Test
	public void getOrgOrgRelationsByRelatedOrg() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationInfo> orgOrgRelationInfos = client.getOrgOrgRelationsByRelatedOrg("16");
		assertEquals(1, orgOrgRelationInfos.size());

		orgOrgRelationInfos = client.getOrgOrgRelationsByRelatedOrg("-1");
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}

	@Test
	public void getOrgPersonRelationsByIdList() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<String> idList = new ArrayList<String>(2);
		idList.add("2");
		idList.add("3");
		List<OrgPersonRelationInfo> orgOrgRelationInfos = client.getOrgPersonRelationsByIdList(idList);
		assertEquals(2, orgOrgRelationInfos.size());

		idList.add("-1");
		orgOrgRelationInfos = client.getOrgPersonRelationsByIdList(idList);
		assertEquals(2, orgOrgRelationInfos.size());

		idList = new ArrayList<String>(2);
		idList.add("-1");
		idList.add("-2");
		orgOrgRelationInfos = client.getOrgPersonRelationsByIdList(idList);
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}

	@Test
	public void getOrgPersonRelationsByPerson() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgPersonRelationInfo> orgOrgRelationInfos = client.getOrgPersonRelationsByPerson("KIM-1", "68");
		assertEquals(2, orgOrgRelationInfos.size());

		orgOrgRelationInfos = client.getOrgPersonRelationsByPerson("KIM-1", "-1");
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);

		orgOrgRelationInfos = client.getOrgPersonRelationsByPerson("-1", "68");
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);

		orgOrgRelationInfos = client.getOrgPersonRelationsByPerson("-1", "-1");
		assertTrue(orgOrgRelationInfos == null || orgOrgRelationInfos.size() == 0);
	}

	@Test
	public void getOrgOrgRelationTypesForOrgType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationTypeInfo> orgOrgRelationTypeInfos = client.getOrgOrgRelationTypesForOrgType("kuali.org.Division");
		assertEquals(4, orgOrgRelationTypeInfos.size());

		orgOrgRelationTypeInfos = client.getOrgOrgRelationTypesForOrgType("org.klingon");
		assertTrue(orgOrgRelationTypeInfos == null || orgOrgRelationTypeInfos.size() == 0);
	}

	@Test
	public void hasOrgPersonRelation() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		Boolean hasRelation = client.hasOrgPersonRelation("68", "KIM-1", "kuali.org.PersonRelation.Head");
		assertNotNull(hasRelation);
		assertTrue(hasRelation);

		hasRelation = client.hasOrgPersonRelation("68x", "KIM-1", "kuali.org.PersonRelation.Head");
		assertNotNull(hasRelation);
		assertFalse(hasRelation);

		hasRelation = client.hasOrgPersonRelation("68", "KIM--1", "kuali.org.PersonRelation.Head");
		assertNotNull(hasRelation);
		assertFalse(hasRelation);
		
		hasRelation = client.hasOrgPersonRelation("68", "KIM-1", "kuali.org.PersonRelation.HeadTTT");
		assertNotNull(hasRelation);
		assertFalse(hasRelation);
		
		
		try {
			hasRelation = client.hasOrgPersonRelation(null, "KIM-1", "kuali.org.PersonRelation.Head");
			assertFalse(true);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}

		try {
			hasRelation = client.hasOrgPersonRelation("68", null, "kuali.org.PersonRelation.Head");
			assertFalse(true);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}

		try {
			hasRelation = client.hasOrgPersonRelation("68", "KIM-1", null);
			assertFalse(true);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}

		
	}

	
	@Test 
	public void testGetOrgTreeInfo() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		List<OrgTreeInfo> results = client.getOrgTree("4", "kuali.org.hierarchy.Main", 1);
		assertEquals(9,results.size());

	}
	
	@Test
	public void testHasOrgOrgRelation() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		assertTrue(client.hasOrgOrgRelation("15", "28", "kuali.org.Part"));
		assertFalse(client.hasOrgOrgRelation("1", "15", "kuali.org.Part"));
	}
	
	/*
	 * Test delete operations
	 */
	@Test
	public void removeOrgOrgRelation() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		StatusInfo si;
		try {
			si = client.removeOrgOrgRelation("16");
			assertTrue(si.getSuccess());
		} catch (DoesNotExistException e) {
			assertTrue(false);
		}

		try {
			si = client.removeOrgOrgRelation("16");
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}

		try {
			si = client.removeOrgOrgRelation(null);
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}
	}

	@Test
	public void removeOrgPersonRelation() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		StatusInfo si;
		try {
			si = client.removeOrgPersonRelation("5");
			assertTrue(si.getSuccess());
		} catch (DoesNotExistException e) {
			assertTrue(false);
		}

		try {
			si = client.removeOrgPersonRelation("5");
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}

		try {
			si = client.removeOrgPersonRelation(null);
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}
	}


	@Test
	public void getOrgPersonIdsByRelationType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		List<String> result = client.getPersonIdsForOrgByRelationType("68", "kuali.org.PersonRelation.Professor");
		assertEquals(2, result.size());
		result = client.getPersonIdsForOrgByRelationType("147", "kuali.org.PersonRelation.Coordinator");
		assertEquals(1, result.size());
		assertEquals("KIM-3", result.get(0));
	}
	
}
