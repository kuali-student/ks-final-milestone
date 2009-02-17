package org.kuali.student.lum.lu.dao.impl;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.lum.lu.dao.LuDao;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl"/*, testSqlFile = "classpath:ks-org.sql"*/)
	public LuDao dao;

	@Test
	public void testFoo(){
	}
}