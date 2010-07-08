package org.kuali.student.lum.program.service.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.lum.program.service.ProgramService;

@Daos({@Dao(value = "org.kuali.student.lum.program.dao.impl.ProgramDaoImpl")})
@PersistenceFileLocation("classpath:META-INF/program-persistence.xml")
public class TestProgramServiceImpl extends AbstractServiceTest {

    @Client(value = "org.kuali.student.lum.program.service.impl.ProgramServiceImpl", additionalContextFile="classpath:program-additional-context.xml")
    public ProgramService programService;

    @Test
    public void testProgramServiceSetup() {
    	assertNotNull(programService);
    }

	@Test
    public void testCreateMajorDiscipline() {

	}
}
