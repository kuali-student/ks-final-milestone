package org.kuali.student.core.organization.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	public void TestFinds() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
	    //Test Finds
		List<OrgHierarchyInfo> orgHierarchyInfos = client.getOrgHierarchies();
		assertEquals(2,orgHierarchyInfos.size());

		List<OrgOrgRelationInfo> orgOrgRelationInfos = client.getOrgOrgRelationsByOrg("4");
		assertEquals(8,orgOrgRelationInfos.size());

		List<OrgPersonRelationInfo> orgPersonRelationInfos = client.getAllOrgPersonRelationsByOrg("68");

		assertEquals(2, orgPersonRelationInfos.size());

		List<OrgPositionRestrictionInfo>  orgPositionRestrictionInfos = client.getPositionRestrictionsByOrg("19");
		assertEquals(6, orgPositionRestrictionInfos.size());

		List<OrgTypeInfo> orgTypeinfos = client.getOrgTypes();
		assertEquals(17, orgTypeinfos.size());

		OrgTypeInfo orgTypeInfo = client.getOrgType("kuali.org.Division");
		assertEquals(orgTypeInfo.getKey(), "kuali.org.Division");

		List<String> descendants = client.getAllDescendants("4", "kuali.org.hierarchy.Main");
		assertEquals(8,descendants.size());
	}

	@Test
	public void getOrgOrgRelationTypes() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationTypeInfo> orgOrgRelationTypeInfos = client.getOrgOrgRelationTypes();
		assertEquals(13, orgOrgRelationTypeInfos.size());
	}

	@Test
	public void getOrgOrgRelationType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgOrgRelationTypeInfo orgOrgRelationTypeInfo = client.getOrgOrgRelationType("kuali.org.Report");
		assertEquals(orgOrgRelationTypeInfo.getKey(), "kuali.org.Report");
	}

	@Test
	public void getOrgOrgRelationTypesForOrgHierarchy() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationTypeInfo> orgOrgRelationTypeInfos = client.getOrgOrgRelationTypesForOrgHierarchy("kuali.org.hierarchy.Main");
		assertEquals(12, orgOrgRelationTypeInfos.size());
	}

	@Test
	public void getAllOrgPersonRelationsByPerson() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgPersonRelationInfo> orgPersonRelationsByPerson = client.getAllOrgPersonRelationsByPerson("KIM-1");
		assertEquals(2, orgPersonRelationsByPerson.size());
	}

	@Test
	public void getOrgHierarchy() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgHierarchyInfo orgHierarchyInfo = client.getOrgHierarchy("kuali.org.hierarchy.Curriculum");
		assertEquals(orgHierarchyInfo.getKey(), "kuali.org.hierarchy.Curriculum");
	}


	@Test
	public void getOrgPersonRelationTypesForOrgType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgPersonRelationTypeInfo> orgPersonRelationsByOrgType = client.getOrgPersonRelationTypesForOrgType("kuali.org.School");
		assertEquals(2, orgPersonRelationsByOrgType.size());
	}

}
