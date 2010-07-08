package org.kuali.student.lum.program.service.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.lum.program.service.ProgramService;

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
