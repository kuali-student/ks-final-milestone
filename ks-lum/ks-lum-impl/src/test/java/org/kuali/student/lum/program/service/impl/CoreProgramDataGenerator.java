package org.kuali.student.lum.program.service.impl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.program.service.assembler.ProgramAssemblerConstants;

public class CoreProgramDataGenerator {
	public CoreProgramInfo getCoreProgramTestData() throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException{
		CoreProgramInfo testData = ProgramDataGeneratorUtils.generateTestData(CoreProgramInfo.class, ProgramAssemblerConstants.CORE_PROGRAM, 0, 0,null, false);
		return testData;
	}

	public static void main(String[] args) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException{
		CoreProgramDataGenerator generator = new CoreProgramDataGenerator();
		generator.getCoreProgramTestData();
	}
}
