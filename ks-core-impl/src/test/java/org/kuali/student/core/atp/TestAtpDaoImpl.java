package org.kuali.student.core.atp;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.atp.dao.AtpDao;

@PersistenceFileLocation("classpath:META-INF/atp-persistence.xml")
public class TestAtpDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.core.atp.dao.impl.AtpDaoImpl")
	public AtpDao dao;

	@Test
	public void testCreateType() {

	}
}
