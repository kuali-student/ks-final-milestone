package org.kuali.student.lum.program.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.service.ProgramService;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerConstants;

public class TestProgramServiceImpl extends AbstractServiceTest {

    @Client(value = "org.kuali.student.lum.program.service.impl.ProgramServiceImpl", additionalContextFile="classpath:program-additional-context.xml")
    public ProgramService programService;

    @Test
    public void testProgramServiceSetup() {
    	assertNotNull(programService);
    }

	@Test
    @Ignore public void testCreateMajorDiscipline() {
		MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
        MajorDisciplineInfo majorDisciplineInfo = null;
        try {
            assertNotNull(majorDisciplineInfo = generator.getMajorDisciplineInfoTestData());
            MajorDisciplineInfo createdMD = programService.createMajorDiscipline(majorDisciplineInfo);
            assertNotNull(createdMD);
            assertEquals(ProgramAssemblerConstants.DRAFT, createdMD.getState());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, createdMD.getType());
        } catch (Exception e) {
            fail(e.getMessage());
        }
	}
}
