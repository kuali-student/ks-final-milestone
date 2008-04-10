package org.kuali.student.common_test_tester;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.kuali.student.common_test_tester.support.MyDao;
import org.kuali.student.common_test_tester.support.Value;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;

@PersistenceFileLocation("classpath:META-INF/test-persistence.xml")
public class DaoCommonTest extends AbstractTransactionalDaoTest {

	@Dao("org.kuali.student.common_test_tester.support.MyDaoImpl")
	public MyDao myDao;

	@Test
	public void test1() {
		Value value = new Value("Cheerios");

		Long id = myDao.createValue(value);
		String result = myDao.findValue(id);
		assertEquals("Cheerios", result);

		result = myDao.findValue(new Long(123));
	}
}
