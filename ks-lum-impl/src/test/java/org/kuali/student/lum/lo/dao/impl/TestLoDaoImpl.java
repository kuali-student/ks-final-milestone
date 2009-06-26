package org.kuali.student.lum.lo.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
		assertEquals(3, los.size());
		assertTrue(loNames.contains(los.get(0).getName()));
		assertTrue(loNames.contains(los.get(1).getName()));
		assertTrue(loNames.contains(los.get(2).getName()));
		
		// LoCategory associated w/ a LoHierarchy that has no LO's in it
		los= dao.getLosByLoCategory("7114D2A4-F66D-4D3A-9D41-A7AA4299C797");
		assertNotNull(los);
		assertEquals(0, los.size());
		
		// Bogus LoCategory
		los= dao.getLosByLoCategory("Not a valid UUID");
		assertNotNull(los);
		assertEquals(0, los.size());
	}
}