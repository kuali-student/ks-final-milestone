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
package org.kuali.student.core.atp;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.atp.dao.AtpDao;

@PersistenceFileLocation("classpath:META-INF/atp-persistence.xml")
public class TestAtpDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.core.atp.dao.impl.AtpDaoImpl", testSqlFile = "classpath:ks-atp.sql")
	public AtpDao dao;

	@Test
	public void testCreateType() {

	}
}
