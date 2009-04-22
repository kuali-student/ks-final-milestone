package org.kuali.student.lum.lrc.dao.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.lum.lrc.dao.LrcDao;

@PersistenceFileLocation("classpath:META-INF/lrc-persistence.xml")
public class TestLrcDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lrc.dao.impl.LrcDaoImpl", testSqlFile = "classpath:ks-lrc.sql")
	public LrcDao dao;

	@Test
	public void testLrc() {
		assertTrue(true);
	}
}
