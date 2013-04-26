package org.kuali.student.ap.course.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.ap.course.model.ClassFinderCriteriaEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-test-context.xml" })
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class ClassFinderDaoTest {

	private transient ClassFinderDao classFinderDao;

	@Before
	public void setUp() {
		classFinderDao = GlobalResourceLoader.getService("classFinderDao");
	}

	@Test
	public void testGetClassFinderCriteria() {
		ClassFinderCriteriaEntity cfc = classFinderDao.find("foo");
		assertEquals("foo", cfc.getKey());
		assertEquals(2, cfc.getFacet().size());
		assertEquals("bar", cfc.getFacet().get(0));
		assertEquals("baz", cfc.getFacet().get(1));
	}

}
