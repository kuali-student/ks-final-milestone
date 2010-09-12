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

package org.kuali.student.lum.lo.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lo.dao.LoDao;
import org.kuali.student.lum.lo.entity.Lo;
import org.kuali.student.lum.lo.entity.LoCategory;
import org.kuali.student.lum.lo.entity.LoRepository;

@PersistenceFileLocation("classpath:META-INF/lo-persistence.xml")
public class TestLoDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lo.dao.impl.LoDaoImpl", testSqlFile = "classpath:ks-lo.sql")
	public LoDao dao;

	@Before
	public void addLosToRepository() throws DoesNotExistException {
		// set the hierarchy's root Lo; constraint violation if we do it in ks-lo.sql
		LoRepository repository = dao.fetch(LoRepository.class, "kuali.loRepository.key.singleUse");
		Lo lo = dao.fetch(Lo.class, "81ABEA67-3BCC-4088-8348-E265F3670145");
		repository.setRootLo(lo);
	}

	@Test
	public void testGetLo() 
	{
		Lo lo = null;
		try {
			lo = dao.fetch(Lo.class, "81ABEA67-3BCC-4088-8348-E265F3670145");
		} catch (DoesNotExistException dnee) {
			fail("Unable to find existing Learning Objective");
		}
		assertNotNull(lo);
		assertEquals("Edit Wiki Message Structure", lo.getName());
		assertEquals("singleUse", lo.getLoRepository().getName());
		try {
			lo = dao.fetch(Lo.class, "91A91860-D796-4A17-976B-A6165B1A0B05");
		} catch (DoesNotExistException dnee) {
			fail("Unable to find existing Learning Objective");
		}
		assertNotNull(lo);
		assertEquals("Destroy Wiki", lo.getDescr().getPlain());
	}
	
	@Test
	public void testGetRelatedLosByLoId() 
	{
		String loId = "81ABEA67-3BCC-4088-8348-E265F3670145";
		List<Lo> relatedLos = null;
		try {
			relatedLos = dao.getRelatedLosByLoId(loId, "kuali.lo.relation.type.includes");
		} catch (DoesNotExistException dnee) {
			fail("Unable to find existing Lo's related to Lo with ID == " + loId);
		}
		assertNotNull(relatedLos);
		assertEquals(2, relatedLos.size());
		assertTrue(relatedLos.get(0).getId().equals("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF") || relatedLos.get(1).getId().equals("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF"));
	}
	
	@Test
	public void testGetLosByRelatedLoId() 
	{
		String relatedLoId = "ABD8AE21-34E9-4858-A714-B04134F55D68";
		List<Lo> relatedLos = null;
		try {
			relatedLos = dao.getLosByRelatedLoId(relatedLoId, "kuali.lo.relation.type.includes");
		} catch (DoesNotExistException dnee) {
			fail("Unable to find existing Lo's related to Lo with ID == " + relatedLoId);
		}
		assertNotNull(relatedLos);
		assertEquals(2, relatedLos.size());
		assertTrue(relatedLos.get(0).getId().equals("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF") || relatedLos.get(1).getId().equals("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF"));
	}
	
	@Test
	public void getLoByIdList() {
		List<Lo> los = dao.getLoByIdList(Arrays.asList("81ABEA67-3BCC-4088-8348-E265F3670145", "E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF", "DD0658D2-FDC9-48FA-9578-67A2CE53BF8A"));
		assertNotNull(los);
		assertEquals(3, los.size());
	}
	
	/*
	@Test
	public void testGetLosByLoCategory() {
		Set<String> loNames = new TreeSet<String>(Arrays.asList("Edit Wiki Message Structure", "Navigate Wiki", "Install Wiki Engine"));
		List<Lo> los= dao.getLosByLoCategory("054CAA88-C21D-4496-8287-36A311A11D68");
		assertNotNull(los);
		assertEquals(5, los.size());
		assertTrue(loNames.contains(los.get(0).getName()));
		assertTrue(loNames.contains(los.get(1).getName()));
		assertTrue(loNames.contains(los.get(2).getName()));
		
		// LoCategory associated w/ a LoHierarchy that has only one LO in it
		los= dao.getLosByLoCategory("7114D2A4-F66D-4D3A-9D41-A7AA4299C797");
		assertNotNull(los);
		assertEquals(1, los.size());
		
		// Bogus LoCategory
		los= dao.getLosByLoCategory("Not a valid UUID");
		assertNotNull(los);
		assertEquals(0, los.size());
	}

	@Test
    public void testAddLoCategoryToLo() {
		String loId = "7BCD7C0E-3E6B-4527-AC55-254C58CECC22";
		String loCatId = "054CAA88-C21D-4496-8287-36A311A11D68";
		LoCategory loCategory = null;
		try {
			loCategory = dao.fetch(LoCategory.class, loCatId);
		} catch (DoesNotExistException e) {
			fail("DoesNotExistException when retrieving Lo w/ ID == " + loId);
		}
		assertFalse(dao.getLoCategoriesForLo(loId).contains(loCategory));
		assertEquals("loHierarchy.fsu", loCategory.getLoHierarchy().getId());
		assertTrue(dao.addLoCategoryToLo(loCatId, loId));
		// confirm Category's hierarchy was switched.
		try {
			loCategory = dao.fetch(LoCategory.class, loCatId);
		} catch (DoesNotExistException e) {
			fail("DoesNotExistException when retrieving Lo w/ ID == " + loId);
		}
		assertTrue(dao.getLoCategoriesForLo(loId).contains(loCategory));
		assertEquals("loHierarchy.kualiproject.common", loCategory.getLoHierarchy().getId());
	}
	
	@Test
    public void testRemoveLoCategoryFromLo() throws DoesNotExistException {
		String loId = "E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF";
		String loCatId = "054CAA88-C21D-4496-8287-36A311A11D68";
		LoCategory loCategory = null;
		loCategory = dao.fetch(LoCategory.class, loCatId);
		assertTrue(dao.getLoCategoriesForLo(loId).contains(loCategory));
		assertEquals("loHierarchy.fsu", loCategory.getLoHierarchy().getId());
		assertTrue(dao.removeLoCategoryFromLo(loCatId, loId));
		// confirm Category's hierarchy was switched.
		try {
			loCategory = dao.fetch(LoCategory.class, loCatId);
		} catch (DoesNotExistException e) {
			fail("DoesNotExistException when retrieving Lo w/ ID == " + loId);
		}
		assertFalse(dao.getLoCategoriesForLo(loId).contains(loCategory));
		assertNull(loCategory.getLoHierarchy());
	}
	
	@Test
    public void testDeleteLoCategory() throws DoesNotExistException, DependentObjectsExistException {
		String nonEmptyCategoryId = "054CAA88-C21D-4496-8287-36A311A11D68";
		String emptyCategoryId = "F2F02922-4E77-4144-AA07-8C2C956370DC";
		
		assertFalse(dao.getLosByLoCategory(nonEmptyCategoryId).isEmpty());
		try {
			dao.deleteLoCategory(nonEmptyCategoryId);
			fail("Deleting LoCategory with associated Learning Objective(s) should have thrown DependentObjectsExistException");
		} catch (DependentObjectsExistException doee) {}
		// now delete one that has no associated Lo's
		assertTrue(dao.deleteLoCategory(emptyCategoryId));
	}
	
	@Test
    public void testDeleteLo() throws DoesNotExistException, DependentObjectsExistException {
		assertEquals(1, dao.getRelatedLosByLoId("91A91860-D796-4A17-976B-A6165B1A0B05", "kuali.lo.relation.type.includes").size());
		try {
			dao.deleteLo("91A91860-D796-4A17-976B-A6165B1A0B05");
			fail("LoDao.deleteLo() should have thrown DependentObjectsExistException");
		} catch (DependentObjectsExistException doee) {}
		dao.deleteLo("FDE6421E-64B4-41AF-BAC5-269005101C2A");
		try {
			assertTrue(dao.deleteLo("91A91860-D796-4A17-976B-A6165B1A0B05"));
		} catch (DependentObjectsExistException doee) {
			fail("DependentObjectsExistException was thrown when deleting Lo with no children");
		}
	}
	*/
}