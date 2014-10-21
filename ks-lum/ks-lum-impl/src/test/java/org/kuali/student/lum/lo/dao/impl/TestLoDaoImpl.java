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

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.lum.lo.dao.LoDao;
import org.kuali.student.r2.lum.lo.entity.Lo;
import org.kuali.student.r2.lum.lo.entity.LoRepository;

@PersistenceFileLocation("classpath:META-INF/lo-persistence.xml")
public class TestLoDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.r2.lum.lo.dao.impl.LoDaoImpl", testSqlFile = "classpath:ks-lo.sql")
	public LoDao dao;

	@Before
	public void addLosToRepository() throws DoesNotExistException {
		// set the hierarchy's root Lo; constraint violation if we do it in ks-lo.sql
		LoRepository repository = dao.fetch(LoRepository.class, "kuali.loRepository.key.singleUse");
		Lo lo = dao.fetch(Lo.class, "81abea67-3bcc-4088-8348-e265f3670145");
		repository.setRootLo(lo);
	}

	@Test
	public void testGetLo() throws DoesNotExistException {
		Lo lo;
        lo = dao.fetch(Lo.class, "81abea67-3bcc-4088-8348-e265f3670145");
		assertNotNull(lo);
		assertEquals("Edit Wiki Message Structure", lo.getName());
		assertEquals("singleUse", lo.getLoRepository().getName());
        lo = dao.fetch(Lo.class, "91a91860-d796-4a17-976b-a6165b1a0b05");
		assertNotNull(lo);
		assertEquals("Destroy Wiki", lo.getDescr().getPlain());
	}
	
	@Test
	public void testGetRelatedLosByLoId() throws DoesNotExistException {
		String loId = "81abea67-3bcc-4088-8348-e265f3670145";
		List<Lo> relatedLos;
        relatedLos = dao.getRelatedLosByLoId(loId, "kuali.lo.relation.type.includes");
		assertNotNull(relatedLos);
		assertEquals(2, relatedLos.size());
		assertTrue(relatedLos.get(0).getId().equals("e0b456b2-62cb-4bd3-8867-a0d59fd8f2cf") || relatedLos.get(1).getId().equals("e0b456b2-62cb-4bd3-8867-a0d59fd8f2cf"));
	}
	
	@Test
	public void testGetLosByRelatedLoId() throws DoesNotExistException {
		String relatedLoId = "abd8ae21-34e9-4858-a714-b04134f55d68";
		List<Lo> relatedLos;
        relatedLos = dao.getLosByRelatedLoId(relatedLoId, "kuali.lo.relation.type.includes");
		assertNotNull(relatedLos);
		assertEquals(2, relatedLos.size());
		assertTrue(relatedLos.get(0).getId().equals("e0b456b2-62cb-4bd3-8867-a0d59fd8f2cf") || relatedLos.get(1).getId().equals("e0b456b2-62cb-4bd3-8867-a0d59fd8f2cf"));
	}
	
	@Test
	public void getLoByIdList() {
		List<Lo> los = dao.getLoByIdList(Arrays.asList("81abea67-3bcc-4088-8348-e265f3670145", "e0b456b2-62cb-4bd3-8867-a0d59fd8f2cf", "dd0658d2-fdc9-48fa-9578-67a2ce53bf8a"));
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
		assertTrue(los.isEmpty());
	}

	@Test
    public void testAddLoCategoryToLo() {
		String loId = "7BCD7C0E-3E6B-4527-AC55-254C58CECC22";
		String loCatId = "054CAA88-C21D-4496-8287-36A311A11D68";
		LoCategory loCategory = null;
        loCategory = dao.fetch(LoCategory.class, loCatId);
		assertFalse(dao.getLoCategoriesForLo(loId).contains(loCategory));
		assertEquals("loHierarchy.fsu", loCategory.getLoHierarchy().getId());
		assertTrue(dao.addLoCategoryToLo(loCatId, loId));
		// confirm Category's hierarchy was switched.
        loCategory = dao.fetch(LoCategory.class, loCatId);
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
        loCategory = dao.fetch(LoCategory.class, loCatId);
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
		} catch (DependentObjectsExistException doee) {
		    assertNotNull(eoee.getmessage());
		}
		// now delete one that has no associated Lo's
		assertTrue(dao.deleteLoCategory(emptyCategoryId));
	}
	
	@Test
    public void testDeleteLo() throws DoesNotExistException, DependentObjectsExistException {
		assertEquals(1, dao.getRelatedLosByLoId("91A91860-D796-4A17-976B-A6165B1A0B05", "kuali.lo.relation.type.includes").size());
		try {
			dao.deleteLo("91A91860-D796-4A17-976B-A6165B1A0B05");
			fail("LoDao.deleteLo() should have thrown DependentObjectsExistException");
		} catch (DependentObjectsExistException doee) {
		    assertNotNull(eoee.getmessage());
		}
		dao.deleteLo("FDE6421E-64B4-41AF-BAC5-269005101C2A");
        assertTrue(dao.deleteLo("91A91860-D796-4A17-976B-A6165B1A0B05"));
	}
	*/
}