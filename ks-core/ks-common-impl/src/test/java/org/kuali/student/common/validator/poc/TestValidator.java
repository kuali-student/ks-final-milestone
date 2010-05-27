package org.kuali.student.common.validator.poc;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.dictionary.poc.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.service.impl.poc.DictionaryServiceImpl;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class TestValidator {
	Validator val = null;
	DictionaryServiceImpl dictionaryDelegate = new DictionaryServiceImpl("classpath:poc/test-validator-context.xml");
	
	@Before
	public void init() {
		val = new Validator();
		val.setDateParser(new ServerDateParser());
		val.setMessageService(null);
	}
		
	@Test     
    public void testRequired() {
    	    	
    	List<ValidationResultInfo> results = val.validateObject( buildTestPerson1(), buildObjectStructure1());    
    	assertEquals(results.size(), 1);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.required");
    }
    

    @Test     
    public void testLengthRange() {
    	
    	ConstraintMockPerson p = buildTestPerson1();
    	p.setFirstName("thisisaveryveryverylo");
    	
    	List<ValidationResultInfo> results = val.validateObject( p, buildObjectStructure1());    
    	assertEquals(results.size(), 2);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.lengthOutOfRange");
    }
    
    @Test     
    public void testMinLength() {
    	
    	ConstraintMockPerson p = buildTestPerson1();
    	p.setFirstName("t");

    	ObjectStructureDefinition o1 = buildObjectStructure1();
    	o1.getAttributes().get(0).setMaxLength(null);
    	
    	List<ValidationResultInfo> results = val.validateObject( p, o1);    
    	assertEquals(results.size(), 2);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.minLengthFailed");
    }
    

    @Test
    public void testMinDateValue() {
    	ConstraintMockPerson p = buildTestPerson1();
    	ServerDateParser sp = new ServerDateParser();
    	p.setDob(sp.parseDate("1960-01-01"));
    	ObjectStructureDefinition o1 = buildObjectStructure1();
    	
    	List<ValidationResultInfo> results = val.validateObject( p, o1);    
    	assertEquals(results.size(), 1);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.minValueFailed");    	
    }
    
    @Test     
    public void testMaxLength() {
    	
    	ConstraintMockPerson p = buildTestPerson1();
    	p.setFirstName("thisisaveryveryverylo");

    	ObjectStructureDefinition o1 = buildObjectStructure1();
    	o1.getAttributes().get(0).setMinLength(0);
    	
    	List<ValidationResultInfo> results = val.validateObject( p, o1);    
    	assertEquals(results.size(), 2);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.maxLengthFailed");
    }
    
    @Test     
    public void testValidChars() {
    	    	
    	ConstraintMockPerson p = buildTestPerson1();
    	p.setFirstName("in$#valid");

    	ObjectStructureDefinition o1 = buildObjectStructure1();
    	
    	List<ValidationResultInfo> results = val.validateObject( p, o1);    
    	assertEquals(results.size(), 2);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.validCharsFailed");
    }


    @Test     
    public void testDoubleValueRange() {
    	
    	ConstraintMockPerson p = buildTestPerson2();
    	p.setGpa(5.0);

    	ObjectStructureDefinition o1 = buildObjectStructure1();
    	
    	List<ValidationResultInfo> results = val.validateObject( p, o1);    
    	assertEquals(results.size(), 1);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.outOfRange");
    }
    
    @Test
    public void testNestedStructures() {    	
    	ConstraintMockPerson p = buildTestPerson3();

    	ObjectStructureDefinition o = buildObjectStructure2();
    	
    	List<ValidationResultInfo> results = val.validateObject( p, o);    
    	assertEquals(results.size(), 3);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.required");

    	assertEquals(results.get(1).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(1).getMessage(), "validation.validCharsFailed");    	
    	assertEquals(results.get(2).getMessage(), "validation.requiresField");
    }
    
    
    public ConstraintMockPerson buildTestPerson1() {
    	return ValidatorMockObjectGenerator.buildTestPerson1();
    }

    
    public ConstraintMockPerson buildTestPerson2() {
    	return ValidatorMockObjectGenerator.buildTestPerson2();
    }

    
    public ConstraintMockPerson buildTestPerson3() {
    	return ValidatorMockObjectGenerator.buildTestPerson3();
    }
    
    public ObjectStructureDefinition buildObjectStructure1() { 
    	return dictionaryDelegate.getObjectStructure("objectStructure1");
    }

    public ObjectStructureDefinition buildObjectStructure2() {
    	return dictionaryDelegate.getObjectStructure("objectStructure2");
    }
}
