package org.kuali.student.lum.program.service.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerConstants;

public class MajorDisciplineDataGenerator {
	private static final String[] campusLocations = {CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_NORTH,CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_SOUTH};
	Random generator = new Random();
    
	public MajorDisciplineInfo getMajorDisciplineInfoTestData() throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException{
		MajorDisciplineInfo testData = ProgramDataGeneratorUtils.generateTestData(MajorDisciplineInfo.class, ProgramAssemblerConstants.MAJOR_DISCIPLINE, 0, 0,null, false);
		return testData;
	}

	public static void main(String[] args) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException{
		MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
		generator.getMajorDisciplineInfoTestData();
	}
}
