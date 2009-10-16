/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lo.dao.LoDao;
import org.kuali.student.lum.lo.entity.Lo;
import org.kuali.student.lum.lo.entity.LoCategory;
import org.kuali.student.lum.lo.entity.LoHierarchy;

@PersistenceFileLocation("classpath:META-INF/lo-persistence.xml")
public class TestLoDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lo.dao.impl.LoDaoImpl", testSqlFile = "classpath:ks-lo.sql")
	public LoDao dao;

	@Before
	public void addLosToHierarchies() throws DoesNotExistException {
		// set the hierarchy's root Lo; constraint violation if we do it in ks-lo.sql
		LoHierarchy hierarchy = dao.fetch(LoHierarchy.class, "loHierarchy.fsu");
		Lo lo = dao.fetch(Lo.class, "81ABEA67-3BCC-4088-8348-E265F3670145");
		hierarchy.setRootLo(lo);
		hierarchy = dao.fetch(LoHierarchy.class, "loHierarchy.kualiproject.common");
		lo = dao.fetch(Lo.class, "7BCD7C0E-3E6B-4527-AC55-254C58CECC22");
		hierarchy.setRootLo(lo);
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
		assertEquals("FSU", lo.getLoHierarchy().getName());
	}
	
	@Test
	public void getLoByIdList() {
		List<Lo> los = dao.getLoByIdList(Arrays.asList("81ABEA67-3BCC-4088-8348-E265F3670145", "E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF", "DD0658D2-FDC9-48FA-9578-67A2CE53BF8A"));
		assertNotNull(los);
		assertEquals(3, los.size());
	}
	
	@Test
	public void testGetLoCategoriesForLo() {
		List<LoCategory> categories = dao.getLoCategoriesForLo("81ABEA67-3BCC-4088-8348-E265F3670145");
		assertNotNull(categories);
		assertEquals(2, categories.size());
		assertTrue(categories.get(0).getName().equals("Test Category 3") ^ categories.get(1).getName().equals("Test Category 3"));
	}
	
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
	public void testGetLoCategories() {
		List<LoCategory> categories = dao.getLoCategories("loHierarchy.fsu");
		assertNotNull(categories);
		assertEquals(2, categories.size());
		categories = dao.getLoCategories("loHierarchy.bogus");
		assertNotNull(categories);
		assertEquals(0, categories.size());
	}
	
	@Test
	public void testGetLoChildren() {
		List<Lo> los = dao.getLoChildren("81ABEA67-3BCC-4088-8348-E265F3670145");
		assertNotNull(los);
		assertEquals(2, los.size());
		assertTrue(los.get(0).getId().equals("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A") ^ los.get(1).getId().equals("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A"));
	}
	
	@Test
	public void testGetAllDescendantLoIds() {
		List<String> descendantIds = dao.getAllDescendantLoIds("81ABEA67-3BCC-4088-8348-E265F3670145");
		assertNotNull(descendantIds);
		assertEquals(3, descendantIds.size());
		for (String id : descendantIds) {
			if (id.equals("ABD8AE21-34E9-4858-A714-B04134F55D68")) {
				return;
			}
		}
		fail("Descendant ID's didn't include: " + " ABD8AE21-34E9-4858-A714-B04134F55D68");
	}
	
	@Test
	public void testGetLoParents() {
		List<Lo> parents = dao.getLoParents("ABD8AE21-34E9-4858-A714-B04134F55D68");
		assertNotNull(parents);
		assertEquals(2, parents.size());
	}
	
	@Test
	public void testIsDescendant() {
		assertTrue(dao.isDescendant("81ABEA67-3BCC-4088-8348-E265F3670145", "DD0658D2-FDC9-48FA-9578-67A2CE53BF8A"));
		assertFalse(dao.isDescendant("81ABEA67-3BCC-4088-8348-E265F3670145", "BogusLoId"));
	}
	
	@Test
	public void testGetAncestors() {
		List<String> ancestorIds = dao.getAncestors("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
		assertNotNull(ancestorIds);
		assertEquals(1, ancestorIds.size());
	}
	
	@Test
	public void testGetEquivalentLos() {
		List<Lo> equivLos = dao.getEquivalentLos("ABD8AE21-34E9-4858-A714-B04134F55D68");
		assertNotNull(equivLos);
		assertEquals(1, equivLos.size());
		assertEquals("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A", equivLos.get(0).getId());
	}
	
	@Test
	public void testGetLoEquivalents() {
		List<Lo> equivLos = dao.getLoEquivalents("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A");
		assertNotNull(equivLos);
		assertEquals(1, equivLos.size());
		assertEquals("ABD8AE21-34E9-4858-A714-B04134F55D68", equivLos.get(0).getId());
	}
	
	@Test
    public void testIsEquivalent() {
		assertTrue(dao.isEquivalent("DD0658D2-FDC9-48FA-9578-67A2CE53BF8A", "ABD8AE21-34E9-4858-A714-B04134F55D68"));
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
    public void testAddChildLoToLo() {
		String parentLoId = "81ABEA67-3BCC-4088-8348-E265F3670145";
		String childLoId = "7BCD7C0E-3E6B-4527-AC55-254C58CECC22";
		
		Lo parentLo = null;
		Lo childLo = null;
		try {
			parentLo = dao.fetch(Lo.class, parentLoId); 
			childLo = dao.fetch(Lo.class, childLoId); 
		} catch (DoesNotExistException e) {
			fail("DoesNotExistException when retrieving Lo w/ ID == " + parentLoId);
		}
		assertEquals(2, parentLo.getChildLos().size());
		assertEquals("loHierarchy.kualiproject.common", childLo.getLoHierarchy().getId());
		try {
			dao.addChildLoToLo("7BCD7C0E-3E6B-4527-AC55-254C58CECC22", parentLoId);
		} catch (AlreadyExistsException aee) {
			fail("AlreadyExistsException when adding Lo(id = 7BCD7C0E-3E6B-4527-AC55-254C58CECC22) as child to Lo(id = " +
				 parentLoId + ")");
		} catch (DoesNotExistException e) {
			fail("DoesNotExistException when adding Lo(id = 7BCD7C0E-3E6B-4527-AC55-254C58CECC22) as child to Lo(id = " +
				 parentLoId + ")");
		}
		try {
			parentLo = dao.fetch(Lo.class, parentLoId); 
			childLo = dao.fetch(Lo.class, childLoId); 
		} catch (DoesNotExistException e) {
			fail("DoesNotExistException when retrieving Lo w/ ID == " + parentLoId);
		}
		assertEquals(3, parentLo.getChildLos().size());
		assertEquals("loHierarchy.fsu", childLo.getLoHierarchy().getId());
	}
	
	@Test
    public void testRemoveChildLoFromLo() throws DoesNotExistException, DependentObjectsExistException {
		try {
			dao.removeChildLoFromLo("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF", "81ABEA67-3BCC-4088-8348-E265F3670145");
			fail("LoDao.removeChildLoFromLo() should have thrown DependentObjectsExistException");
		} catch (DependentObjectsExistException doee) {}
		assertEquals(1, dao.getLoChildren("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF").size());
		assertTrue(dao.removeChildLoFromLo("ABD8AE21-34E9-4858-A714-B04134F55D68", "E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF"));
		assertEquals(0, dao.getLoChildren("E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF").size());
	}
	
	@Test
    public void testDeleteLo() throws DoesNotExistException, DependentObjectsExistException {
		assertEquals(1, dao.getLoChildren("91A91860-D796-4A17-976B-A6165B1A0B05").size());
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
	
	@Test
    public void testAddEquivalentLoToLo() {
		String loId = "E0619A90-66D6-4AF4-B357-E73AE44F7E88";
		String equivLoId = "7BCD7C0E-3E6B-4527-AC55-254C58CECC22";
		assertFalse(dao.isEquivalent(loId, equivLoId));
		try {
			dao.addEquivalentLoToLo(loId, equivLoId);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertTrue(dao.isEquivalent(equivLoId, loId)); 
	}
	
	@Test
    public void testRemoveEquivalentLoFromLo() {
		assertEquals(1, dao.getEquivalentLos("E0619A90-66D6-4AF4-B357-E73AE44F7E88").size());
		assertTrue(dao.removeEquivalentLoFromLo("E0619A90-66D6-4AF4-B357-E73AE44F7E88", "81ABEA67-3BCC-4088-8348-E265F3670145"));
		assertEquals(0, dao.getLoChildren("E0619A90-66D6-4AF4-B357-E73AE44F7E88").size());
	}
}
