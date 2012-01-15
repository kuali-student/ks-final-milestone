package org.kuali.student.lum.program.service.impl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerConstants;

public class MajorDisciplineDataGenerator {
  
	public MajorDisciplineInfo getMajorDisciplineInfoTestData() throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException{
		MajorDisciplineInfo testData = ProgramDataGeneratorUtils.generateTestData(MajorDisciplineInfo.class, ProgramAssemblerConstants.MAJOR_DISCIPLINE, 0, 0,null, false);
		return testData;
	}

	public static void main(String[] args) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException{
		MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
		generator.getMajorDisciplineInfoTestData();
	}
}
