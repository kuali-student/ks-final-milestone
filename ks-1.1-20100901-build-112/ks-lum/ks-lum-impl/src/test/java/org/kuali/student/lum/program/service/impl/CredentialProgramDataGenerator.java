package org.kuali.student.lum.program.service.impl;


import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerConstants;

public class CredentialProgramDataGenerator {
    private String CPType;
    
    
	public String getCPType() {
		return CPType;
	}
	public void setCPType(String type) {
		CPType = type;
	}
	
	public CredentialProgramDataGenerator(String CPType){
	this.CPType = CPType;	
	}
	public CredentialProgramInfo getCPTestData() throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException{
		CredentialProgramInfo testData = ProgramDataGeneratorUtils.generateTestData(CredentialProgramInfo.class, getCPType(), 0, 0,null, false);
		return testData;
	}

	public static void main(String[] args) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException{
		CredentialProgramDataGenerator generator = new CredentialProgramDataGenerator(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM);
		generator.getCPTestData();
	}
}
