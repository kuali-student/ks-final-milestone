package org.kuali.student.core.organization.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgAttribute;
import org.kuali.student.core.organization.entity.OrgAttributeDef;
import org.kuali.student.core.organization.entity.OrgHierarchy;
import org.kuali.student.core.organization.entity.OrgOrgRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.core.organization.entity.OrgType;

@PersistenceFileLocation("classpath:META-INF/organization-persistence.xml")
public class TestOrganizationDao extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.core.organization.dao.impl.OrganizationDaoImpl",testSqlFile="classpath:ks-org.sql")
	public OrganizationDao dao;

	@Test
	public void testCreateOrganization() throws DoesNotExistException{
		
		OrgType orgType= new OrgType();
		orgType.setKey("kuali.org.CorporateEntity");
		orgType.setName("Corporate Entity");
		orgType.setDesc("A legal corporate entity");
		
		
		Org org = new Org();
		org.setType(orgType);
		org.setShortName("KU");
		org.setDesc("Kuali University");
		
		OrgHierarchy orgHierarchy = new OrgHierarchy();
		orgHierarchy.setKey("kuali.org.Main");
		orgHierarchy.setName("Main");
		orgHierarchy.setDesc("Main Organizational Hierarchy");
		orgHierarchy.setRootOrg(org);
		
		dao.create(orgType);
		dao.create(org);
		dao.create(orgHierarchy);
		
		assertNotNull(dao.fetch(OrgHierarchy.class, "kuali.org.hierarchy.Curriculum"));
		
		//Check the alias attribute
		Org borgOrg = dao.fetch(Org.class, "2");
		assertEquals(1,borgOrg.getAttributes().size());
		assertEquals("Governors",borgOrg.getAttributes().get(0).getValue());
		
		OrgAttributeDef alias = new OrgAttributeDef();
		alias.setId("ks.org.attr.Alias");
		alias.setName("Alias");
		em.persist(alias);
		
		OrgAttribute borgAlias = new OrgAttribute();
		borgAlias.setAttrDef(alias);
		borgAlias.setValue("Governors");
		borgAlias.setOwner(borgOrg);
		
		borgOrg.getAttributes().add(borgAlias);
		
		em.persist(borgOrg);
	}
	
	@Test
	public void testDeleteOrganizationByReference() {
		
		OrgType orgType= new OrgType();
		orgType.setKey("kauli.org.TestOrgTypeKey1");
		orgType.setName("Test OrgType 1");
		orgType.setDesc("A test OrgType");
		
		Org org = new Org();
		org.setType(orgType);
		org.setShortName("TestOrg1");
		org.setDesc("Test Org 1");
		
		OrgAttribute orgAttr1 = new OrgAttribute();
		orgAttr1.setValue("orgAttr1Value");
		OrgAttribute orgAttr2 = new OrgAttribute();
		orgAttr1.setValue("orgAttr2Value");
		
		org.setAttributes(Arrays.asList(new OrgAttribute[] { orgAttr1, orgAttr2 } ));
		
		OrgHierarchy orgHierarchy = new OrgHierarchy();
		orgHierarchy.setKey("kuali.org.TestOrgHierarchy1");
		orgHierarchy.setName("TestOrgHeir1");
		orgHierarchy.setDesc("Test Organizational Hierarchy 1");
		orgHierarchy.setRootOrg(org);
		
		dao.create(orgType);
		dao.create(org);
		dao.create(orgHierarchy);
		
		String orgID = org.getId();
		
		try {
			assertNotNull(org = dao.fetch(Org.class, orgID));
		} catch (DoesNotExistException dnee) {
			fail("TestOrganizationDao#fetch(Org.class, <id>) failed: " + dnee.getMessage());
		}
		assertEquals(2, org.getAttributes().size());
		List<String> attrIDs = new ArrayList<String>();
		for (int i = 0; i < 2; i++) {
			attrIDs.add(org.getAttributes().get(i).getId());
		}
		dao.delete(org);
		
		try {
			assertNull(dao.fetch(Org.class, orgID));
			fail("OrganizationDAO#fetch(Org.class, <id>) of a deleted Org did not throw org.kuali.student.core.exceptions.DoesNotExistException");
		} catch (DoesNotExistException dnee) {}
		
		// make sure Attrs were deleted
		try {
			for (String id : attrIDs) {
				assertNull(dao.fetch(OrgAttribute.class, id));
			}
			fail("OrganizationDAO#fetch(OrgAttribute.class, <id> of a deleted OrgAttribute did not throw org.kuali.student.core.exceptions.DoesNotExistException");
		} catch (DoesNotExistException dnee) {}
	}
	
	@Test
	public void testDeleteOrganizationByKey() {
		
		OrgType orgType= new OrgType();
		orgType.setKey("kauli.org.TestOrgTypeKey1");
		orgType.setName("Test OrgType 1");
		orgType.setDesc("A test OrgType");
		
		Org org = new Org();
		org.setType(orgType);
		org.setShortName("TestOrg1");
		org.setDesc("Test Org 1");
		
		OrgAttribute orgAttr1 = new OrgAttribute();
		orgAttr1.setValue("orgAttr1Value");
		OrgAttribute orgAttr2 = new OrgAttribute();
		orgAttr1.setValue("orgAttr2Value");
		
		org.setAttributes(Arrays.asList(new OrgAttribute[] { orgAttr1, orgAttr2 } ));
		
		OrgHierarchy orgHierarchy = new OrgHierarchy();
		orgHierarchy.setKey("kuali.org.TestOrgHierarchy1");
		orgHierarchy.setName("TestOrgHeir1");
		orgHierarchy.setDesc("Test Organizational Hierarchy 1");
		orgHierarchy.setRootOrg(org);
		
		dao.create(orgType);
		dao.create(org);
		dao.create(orgHierarchy);
		
		String orgID = org.getId();
		
		//String orgTypeID = orgType.getKey();
		
		try {
			assertNotNull(org = dao.fetch(Org.class, orgID));
		} catch (DoesNotExistException e) {
			fail("TestOrganizationDao#fetch(Org.class, <id>) failed: " + e.getMessage());
		}
		assertEquals(2, org.getAttributes().size());
		List<String> attrIDs = new ArrayList<String>();
		for (int i = 0; i < 2; i++) {
			attrIDs.add(org.getAttributes().get(i).getId());
		}
		try {
			dao.delete(Org.class, orgID);
		} catch (DoesNotExistException e) {
			fail("TestOrganizationDao#deleteOrganizationByKey failed: " + e.getMessage());
		}
		
		try {
			// assertNull(dao.fetch(Org.class, orgID));
			dao.fetch(Org.class, orgID);
			fail("OrganizationDAO#fetch(Org.class, <id>) of a deleted Org did not throw org.kuali.student.core.exceptions.DoesNotExistException");
		} catch (DoesNotExistException dnee) { }
		// make sure Attrs were deleted
		try {
			for (String id : attrIDs) {
				assertNull(dao.fetch(OrgAttribute.class, id));
			}
			fail("OrganizationDAO#fetch(OrgAttribute.class, <id> of a deleted OrgAttribute did not throw org.kuali.student.core.exceptions.DoesNotExistException");
		} catch (DoesNotExistException dnee) {}
	}
	
	@Test
	public void getOrganizationsByIdList(){
		List<String> orgIdList = new ArrayList<String>();
		orgIdList.add("2");
		orgIdList.add("3");
		orgIdList.add("4");
		List<Org> orgs = dao.getOrganizationsByIdList(orgIdList);
		assertEquals(3,orgs.size());
	}
	
	@Test
	public void getOrgOrgRelationsByOrg(){
		List<OrgOrgRelation> orgOrgRelations = dao.getOrgOrgRelationsByOrg("60");
		assertEquals(1,orgOrgRelations.size());
	}
	
	@Test
	public void getAllOrgPersonRelationsByPerson(){
		List<OrgPersonRelation> orgPersonRelations = dao.getAllOrgPersonRelationsByPerson("KIM-1");
		assertEquals(3, orgPersonRelations.size());
	}
	
	@Test
	public void getAllOrgPersonRelationsByOrg(){
		List<OrgPersonRelation> orgPersonRelations = dao.getAllOrgPersonRelationsByOrg("68");
		assertEquals(3, orgPersonRelations.size());
	}
	
	@Test
	public void getPersonIdsForOrgByRelationType(){
		List<String> orgPersonRelations = dao.getPersonIdsForOrgByRelationType("147", "kuali.org.PersonRelation.Coordinator");
		assertEquals(1, orgPersonRelations.size());
		assertEquals("KIM-3", orgPersonRelations.get(0));
	}
}
