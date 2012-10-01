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

package org.kuali.student.common_test_tester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common_test_tester.support.MyDao;
import org.kuali.student.common_test_tester.support.Value;

@PersistenceFileLocation("classpath:META-INF/test-persistence.xml")
public class DaoCommonTest extends AbstractTransactionalDaoTest {

	@Dao(value = "org.kuali.student.common_test_tester.support.MyDaoImpl", testDataFile = "classpath:META-INF/load-my-beans.xml", testSqlFile="classpath:test.sql")
	private MyDao myDao;

	public Value value1;
	
	@Test
	public void test1() {
		Value value = new Value("Cheerios");

		String id = myDao.createValue(value);
		//em.flush();//This is needed for eclipselink for some reason
		String result = myDao.findValue(id);
		assertEquals("Cheerios", result);
		
		assertNotNull(myDao.findValueFromValue("Value Number One"));
		assertNotNull(myDao.findValue("11223344-1122-1122-1111-000000000000"));
	}
	
	@Test
	public void test2() {
		Value value = new Value("Cheerios");

		String id = myDao.createValue(value);
		//em.flush();//This is needed for eclipselink for some reason
		String result = myDao.findValue(id);
		assertEquals("Cheerios", result);
		
		assertNotNull(myDao.findValueFromValue("Value Number One"));
		assertNotNull(myDao.findValue("11223344-1122-1122-1111-000000000000"));
	}
}
