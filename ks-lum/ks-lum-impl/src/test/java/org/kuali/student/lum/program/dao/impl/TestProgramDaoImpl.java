/**
 *
 */
package org.kuali.student.lum.program.dao.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.lum.program.dao.ProgramDao;

/**
 * @author glindholm
 *
 */
@PersistenceFileLocation("classpath:META-INF/program-persistence.xml")
public class TestProgramDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.program.dao.impl.ProgramDaoImpl", testSqlFile = "classpath:ks-program.sql")

	private ProgramDao programDao;

	public ProgramDao getProgramDao() {
		return programDao;
	}

	public void setProgramDao(ProgramDao programDao) {
		this.programDao = programDao;
	}

	@Test
	public void testDao() {
		assertNotNull(programDao);
	}
}
