package org.kuali.student.lum.program.service.assembler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;

public class TestMajorDisciplineAssembler {

    private final MajorDisciplineAssembler assembler = new MajorDisciplineAssembler();

	@Test
    public void testDisassemble() {
		MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
        MajorDisciplineInfo majorDisciplineInfo = null;
        try {
            assertNotNull(majorDisciplineInfo = generator.getMajorDisciplineInfoTestData());
            // assertNotNull(assembler.disassemble(majorDisciplineInfo, NodeOperation.CREATE));
        } catch (Exception e) {
            fail(e.getMessage());
        }
	}
}
