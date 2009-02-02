package org.kuali.student.core.organization.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

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
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.dto.StatusInfo;


@Daos( { @Dao(value = "org.kuali.student.core.organization.dao.impl.OrganizationDaoImpl",testSqlFile="classpath:ks-org.sql"/*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/organization-persistence.xml")
public class TestOrganizationServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.core.organization.service.impl.OrganizationServiceImpl", port = "8181")
	public OrganizationService client;


	@Test
	public void testCreateDeleteOrg() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, ParseException {
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

		orgTypeInfo = client.getOrgType("Dr.Who");
		assertNull(orgTypeInfo);
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

		orgOrgRelationTypeInfo = client.getOrgOrgRelationType("Babylon.5");
		assertNull(orgOrgRelationTypeInfo);
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

		orgHierarchyInfo = client.getOrgHierarchy("Spectre");
		assertNull(orgHierarchyInfo);
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

		org = client.getOrganization("Kaos");
		assertNull(org);
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
			si = client.removeOrgPersonRelation("4");
			assertTrue(si.getSuccess());
		} catch (DoesNotExistException e) {
			assertTrue(false);
		}

		try {
			si = client.removeOrgPersonRelation("4");
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
}
