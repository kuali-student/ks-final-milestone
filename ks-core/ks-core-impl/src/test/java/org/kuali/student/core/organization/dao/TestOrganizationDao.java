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

package org.kuali.student.core.organization.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.search.dto.SearchParam;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SortDirection;
import org.kuali.student.common.search.service.SearchManager;
import org.kuali.student.common.search.service.impl.SearchManagerImpl;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgAttribute;
import org.kuali.student.core.organization.entity.OrgHierarchy;
import org.kuali.student.core.organization.entity.OrgOrgRelation;
import org.kuali.student.core.organization.entity.OrgOrgRelationType;
import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelationType;
import org.kuali.student.core.organization.entity.OrgPositionRestriction;
import org.kuali.student.core.organization.entity.OrgType;

import edu.emory.mathcs.backport.java.util.Collections;

@PersistenceFileLocation("classpath:META-INF/organization-persistence.xml")
public class TestOrganizationDao extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.core.organization.dao.impl.OrganizationDaoImpl", testSqlFile = "classpath:ks-org.sql")
	public OrganizationDao dao;
	
	@Test
	public void testNewSearch() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		SearchManager sm = new SearchManagerImpl("classpath:organization-search-config.xml");

		
 		List<SearchParam> searchParams = new ArrayList<SearchParam>();
		SearchParam qpv1 = new SearchParam();
		qpv1.setKey("org.queryParam.orgType");
		qpv1.setValue("kuali.org.College");
		searchParams.add(qpv1);
		
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setNeededTotalResults(Boolean.TRUE);
		searchRequest.setParams(searchParams);
		searchRequest.setSearchKey("org.search.orgQuickViewByOrgType");
		
		SearchResult result = sm.search(searchRequest, dao);
		assertEquals(6,result.getRows().size());
		assertEquals(2,result.getRows().get(0).getCells().size());
		
		searchRequest.setMaxResults(4);
		searchRequest.setSortDirection(SortDirection.DESC);
		searchRequest.setSortColumn("org.resultColumn.orgShortName");
		searchRequest.setStartAt(2);
		result = sm.search(searchRequest, dao);
		assertEquals(4,result.getRows().size());
		assertEquals(2,result.getRows().get(0).getCells().size());
		
		assertEquals("CollegeEng",result.getRows().get(0).getCells().get(1).getValue());
		assertEquals("CollegeArtsHum",result.getRows().get(3).getCells().get(1).getValue());

		searchRequest.setSortDirection(SortDirection.ASC);
		result = sm.search(searchRequest, dao);
		assertEquals("DistanceEducation",result.getRows().get(3).getCells().get(1).getValue());
		assertEquals("CollegeEducation",result.getRows().get(0).getCells().get(1).getValue());
	}
	
	@Test
	public void testSearch() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		SearchManager sm = new SearchManagerImpl("classpath:organization-search-config.xml");

		List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
		SearchParam qpv1 = new SearchParam();
		qpv1.setKey("org.queryParam.orgType");
		qpv1.setValue("kuali.org.College");
		queryParamValues.add(qpv1);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setSearchKey("org.search.orgQuickViewByOrgType");
		searchRequest.setParams(queryParamValues);
		SearchResult result = sm.search(searchRequest, dao);
		assertEquals(6,result.getRows().size());
		assertEquals(2,result.getRows().get(0).getCells().size());

		qpv1.setKey("org.queryParam.orgId");
		qpv1.setValue("31");
		searchRequest.setSearchKey("org.search.hierarchiesOrgIsIn");
		result = sm.search(searchRequest, dao);
		
		assertEquals(1, result.getRows().size());
		assertEquals("kuali.org.hierarchy.Main", result.getRows().get(0).getCells().get(0).getValue());
	}

	@Test
	public void testGetOrgTreeInfo(){
		List<OrgTreeInfo> orgTreeInfos = dao.getOrgTreeInfo("4", "kuali.org.hierarchy.Main");
		assertEquals(8,orgTreeInfos.size());
	}

	@Test
	public void testCreateOrganization() throws DoesNotExistException {

		OrgType orgType = new OrgType();

		orgType.setId("kuali.org.CorporateEntity");
		orgType.setName("Corporate Entity");
		orgType.setDescr("A legal corporate entity");

		Org org = new Org();
		org.setType(orgType);
		org.setShortName("KU");
		org.setShortDesc("Kuali University");
		org.setLongDesc("Kuali University, a Carnegie-class institution of higher learning");

		OrgHierarchy orgHierarchy = new OrgHierarchy();
		orgHierarchy.setId("kuali.org.Main");
		orgHierarchy.setName("Main");
		orgHierarchy.setDescr("Main Organizational Hierarchy");
		orgHierarchy.setRootOrg(org);

		dao.create(orgType);
		dao.create(org);
		dao.create(orgHierarchy);

		assertNotNull(dao.fetch(OrgHierarchy.class,
				"kuali.org.hierarchy.Curriculum"));

		// Check the alias attribute
		Org borgOrg = dao.fetch(Org.class, "2");
		assertEquals(1, borgOrg.getAttributes().size());
		assertEquals("Governors", borgOrg.getAttributes().get(0).getValue());

		OrgAttribute borgAlias = new OrgAttribute();
		borgAlias.setName("Alias2");
		borgAlias.setValue("Governors2");
//		borgAlias.setOwner(borgOrg);

		borgOrg.getAttributes().add(borgAlias);

		em.persist(borgOrg);
	}

	@Test
	public void testDeleteOrganizationByReference() {

		OrgType orgType = new OrgType();
		orgType.setId("kauli.org.TestOrgTypeKey1");
		orgType.setName("Test OrgType 1");
		orgType.setDescr("A test OrgType");

		Org org = new Org();
		org.setType(orgType);
		org.setShortName("TestOrg1");
		org.setShortDesc("Test Org 1");
		org.setLongDesc("Test Org 1 Long Description");

		OrgAttribute orgAttr1 = new OrgAttribute();
		orgAttr1.setValue("orgAttr1Value");
		OrgAttribute orgAttr2 = new OrgAttribute();
		orgAttr1.setValue("orgAttr2Value");

		org.setAttributes(Arrays
				.asList(new OrgAttribute[] { orgAttr1, orgAttr2 }));

		OrgHierarchy orgHierarchy = new OrgHierarchy();
		orgHierarchy.setId("kuali.org.TestOrgHierarchy1");
		orgHierarchy.setName("TestOrgHeir1");
		orgHierarchy.setDescr("Test Organizational Hierarchy 1");
		orgHierarchy.setRootOrg(org);

		dao.create(orgType);
		dao.create(org);
		dao.create(orgHierarchy);

		String orgID = org.getId();

		try {
			assertNotNull(org = dao.fetch(Org.class, orgID));
		} catch (DoesNotExistException dnee) {
			fail("TestOrganizationDao#fetch(Org.class, <id>) failed: "
					+ dnee.getMessage());
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
		} catch (DoesNotExistException dnee) {
		}

		// make sure Attrs were deleted
		try {
			for (String id : attrIDs) {
				assertNull(dao.fetch(OrgAttribute.class, id));
			}
			fail("OrganizationDAO#fetch(OrgAttribute.class, <id> of a deleted OrgAttribute did not throw org.kuali.student.core.exceptions.DoesNotExistException");
		} catch (DoesNotExistException dnee) {
		}
	}

	@Test
	public void testDeleteOrganizationByKey() {

		OrgType orgType = new OrgType();
		orgType.setId("kauli.org.TestOrgTypeKey1");
		orgType.setName("Test OrgType 1");
		orgType.setDescr("A test OrgType");

		Org org = new Org();
		org.setType(orgType);
		org.setShortName("TestOrg1");
		org.setShortDesc("Test Org 1");
		org.setLongDesc("Test Org 1 Long Description");

		OrgAttribute orgAttr1 = new OrgAttribute();
		orgAttr1.setValue("orgAttr1Value");
		OrgAttribute orgAttr2 = new OrgAttribute();
		orgAttr1.setValue("orgAttr2Value");

		org.setAttributes(Arrays
				.asList(new OrgAttribute[] { orgAttr1, orgAttr2 }));

		OrgHierarchy orgHierarchy = new OrgHierarchy();
		orgHierarchy.setId("kuali.org.TestOrgHierarchy1");
		orgHierarchy.setName("TestOrgHeir1");
		orgHierarchy.setDescr("Test Organizational Hierarchy 1");
		orgHierarchy.setRootOrg(org);

		dao.create(orgType);
		dao.create(org);
		dao.create(orgHierarchy);

		String orgID = org.getId();

		try {
			assertNotNull(org = dao.fetch(Org.class, orgID));
		} catch (DoesNotExistException e) {
			fail("TestOrganizationDao#fetch(Org.class, <id>) failed: "
					+ e.getMessage());
		}
		assertEquals(2, org.getAttributes().size());
		List<String> attrIDs = new ArrayList<String>();
		for (int i = 0; i < 2; i++) {
			attrIDs.add(org.getAttributes().get(i).getId());
		}
		try {
			dao.delete(Org.class, orgID);
		} catch (DoesNotExistException e) {
			fail("TestOrganizationDao#deleteOrganizationByKey failed: "
					+ e.getMessage());
		}

		try {
			// assertNull(dao.fetch(Org.class, orgID));
			dao.fetch(Org.class, orgID);
			fail("OrganizationDAO#fetch(Org.class, <id>) of a deleted Org did not throw org.kuali.student.core.exceptions.DoesNotExistException");
		} catch (DoesNotExistException dnee) {
		}
		// make sure Attrs were deleted
		try {
			for (String id : attrIDs) {
				assertNull(dao.fetch(OrgAttribute.class, id));
			}
			fail("OrganizationDAO#fetch(OrgAttribute.class, <id> of a deleted OrgAttribute did not throw org.kuali.student.core.exceptions.DoesNotExistException");
		} catch (DoesNotExistException dnee) {
		}
	}

	@Test
	public void getOrganizationsByIdList() {
		List<String> orgIdList = new ArrayList<String>();
		orgIdList.add("2");
		orgIdList.add("3");
		orgIdList.add("4");
		List<Org> orgs = dao.getOrganizationsByIdList(orgIdList);
		assertEquals(3, orgs.size());
		Collections.sort(orgs, new Comparator<Org>() {
			@Override
			public int compare(Org o1, Org o2) {
				return o1.getId().compareTo(o2.getId());
			}});
		assertEquals("BORG", orgs.get(0).getShortName());
		assertEquals("ChancellorsOffice", orgs.get(1).getShortName());
		assertEquals("KU", orgs.get(2).getShortName());
	}

	@Test
	public void getOrgOrgRelationsByOrg() {
		List<OrgOrgRelation> orgOrgRelations = dao
				.getOrgOrgRelationsByOrg("60");
		assertEquals(1, orgOrgRelations.size());
	}

	@Test
	public void getAllOrgPersonRelationsByPerson() {
		List<OrgPersonRelation> orgPersonRelations = dao
				.getAllOrgPersonRelationsByPerson("KIM-1");
		assertEquals(3, orgPersonRelations.size());
	}

	@Test
	public void getAllOrgPersonRelationsByOrg() {
		List<OrgPersonRelation> orgPersonRelations = dao
				.getAllOrgPersonRelationsByOrg("68");
		assertEquals(3, orgPersonRelations.size());
	}

	@Test
	public void getPersonIdsForOrgByRelationType() {
		List<String> orgPersonRelations = dao.getPersonIdsForOrgByRelationType(
				"147", "kuali.org.PersonRelation.Coordinator");
		assertEquals(1, orgPersonRelations.size());
		assertEquals("KIM-3", orgPersonRelations.get(0));
		orgPersonRelations = dao.getPersonIdsForOrgByRelationType(
				"68", "kuali.org.PersonRelation.Professor");
		assertEquals(2, orgPersonRelations.size());
	}

	@Test
	public void getPositionRestrictionsByOrg() {
		List<OrgPositionRestriction> orgPositionRestrictions = dao
				.getPositionRestrictionsByOrg("2");
		assertEquals(2, orgPositionRestrictions.size());
		assertEquals("Member of the Board of Regents", orgPositionRestrictions
				.get(0).getTitle());
		assertEquals("Treasurer of the Board of Regents",
				orgPositionRestrictions.get(1).getTitle());
	}

	@Test
	public void getAncestors() {
		List<String> ancestors = dao.getAllAncestors("139", "kuali.org.hierarchy.Main");
		assertEquals(5, ancestors.size());
		Set<String> ancestorSet = new TreeSet<String>(ancestors);
		List<String> testAncestors = Arrays.asList("1","6","138"); // top, middle, bottom
		assertTrue(ancestorSet.containsAll(testAncestors));
	}

	@Test
	public void getAllDescendants() {
		List<String> descendants = dao.getAllDescendants("6", "kuali.org.hierarchy.Main");
		assertEquals(22, descendants.size());
		Set<String> descendantSet = new TreeSet<String>(descendants);
		List<String> testDescendants = Arrays.asList("7","115","139"); // top, middle, bottom
		assertTrue(descendantSet.containsAll(testDescendants));
	}

	@Test
	public void getOrgOrgRelationTypesForOrgHierarchy() {
		List<OrgOrgRelationType> relationTypes = dao
				.getOrgOrgRelationTypesForOrgHierarchy("kuali.org.hierarchy.Main");
		assertEquals(12, relationTypes.size());
		assertEquals("kuali.org.Section", relationTypes.get(1).getId());
		assertEquals("kuali.org.Subcommittee", relationTypes.get(11).getId());
		relationTypes = dao.getOrgOrgRelationTypesForOrgHierarchy("kuali.org.hierarchy.Curriculum");
		assertEquals(1, relationTypes.size());
		assertEquals("kuali.org.CurriculumChild", relationTypes.get(0).getId());

	}

	@Test
	public void validatePositionRestriction() {
		assertTrue(dao.validatePositionRestriction("3", "kuali.org.PersonRelation.ViceChancellor"));
		assertFalse(dao.validatePositionRestriction("3", "kuali.org.PersonRelation.VicePresident"));
		assertTrue(dao.validatePositionRestriction("3", "kuali.org.PersonRelation.Chancellor"));
		assertTrue(dao.validatePositionRestriction("2", "kuali.org.PersonRelation.Treasurer"));
		assertFalse(dao.validatePositionRestriction("49", "kuali.org.PersonRelation.Chair"));
		assertTrue(dao.validatePositionRestriction("50", "kuali.org.PersonRelation.Chair"));
	}

	@Test
	public void getOrgPersonRelationTypesForOrgType() {
		List<OrgPersonRelationType> personRelationTypes = dao.getOrgPersonRelationTypesForOrgType("kuali.org.School");
		assertEquals(2, personRelationTypes.size());
		assertEquals("Head", personRelationTypes.get(0).getName());
		assertEquals("Professor", personRelationTypes.get(1).getName());
	}

	@Test
	public void getOrgOrgRelationsByIdList() {
		List<OrgOrgRelation> orgOrgRelations = dao.getOrgOrgRelationsByIdList(Arrays.asList("1", "3", "6"));
		assertEquals(3, orgOrgRelations.size());
		Collections.sort(orgOrgRelations, new Comparator<OrgOrgRelation>() {

			@Override
			public int compare(OrgOrgRelation o1, OrgOrgRelation o2) {
				return o1.getId().compareTo(o2.getId());
			}});
		assertEquals("Board", orgOrgRelations.get(0).getType().getName());
		assertEquals("Board", orgOrgRelations.get(1).getType().getName());
		assertEquals("Advisory", orgOrgRelations.get(2).getType().getName());
	}

	@Test
	public void getOrgPersonRelationsByIdList() {
		List<OrgPersonRelation> orgPersonRelations = dao.getOrgPersonRelationsByIdList(Arrays.asList("1", "3", "6"));
		assertEquals(3, orgPersonRelations.size());
		Collections.sort(orgPersonRelations, new Comparator<OrgPersonRelation>() {

			@Override
			public int compare(OrgPersonRelation o1, OrgPersonRelation o2) {
				return o1.getId().compareTo(o2.getId());
			}});
		assertEquals("68", orgPersonRelations.get(0).getOrg().getId());
		assertEquals("Head", orgPersonRelations.get(0).getType().getDescr());
		assertEquals("KIM-1", orgPersonRelations.get(0).getPersonId());
		assertEquals("147", orgPersonRelations.get(1).getOrg().getId());
		assertEquals("KIM-3", orgPersonRelations.get(1).getPersonId());
		assertEquals("Professor", orgPersonRelations.get(2).getType().getDescr());
		assertEquals("KIM-1", orgPersonRelations.get(2).getPersonId());
	}

	@Test
	public void getOrgPersonRelationsByPerson() {
		List<OrgPersonRelation> orgPersonRelations = dao.getOrgPersonRelationsByPerson("KIM-1", "68");
		assertEquals(2, orgPersonRelations.size());
		assertEquals("Head", orgPersonRelations.get(0).getType().getDescr());
		assertEquals("Professor", orgPersonRelations.get(1).getType().getDescr());
	}

	@Test
	public void getOrgOrgRelationTypesForOrgType() {
		List<OrgOrgRelationType> orgOrgRelationTypes = dao.getOrgOrgRelationTypesForOrgType("kuali.org.Program");
		assertEquals(5, orgOrgRelationTypes.size());
		assertEquals("Chartered", orgOrgRelationTypes.get(1).getName());
		assertEquals("Part", orgOrgRelationTypes.get(4).getName());
	}

	@Test
	public void getOrgOrgRelationsByRelatedOrg() {
		List<OrgOrgRelation> orgOrgRelations = dao.getOrgOrgRelationsByRelatedOrg("25");
		assertEquals(2, orgOrgRelations.size());
		assertEquals("VPStudentsOffice", orgOrgRelations.get(0).getOrg().getShortName());
		assertEquals("UndergraduateProgram", orgOrgRelations.get(1).getOrg().getShortName());
	}
}
