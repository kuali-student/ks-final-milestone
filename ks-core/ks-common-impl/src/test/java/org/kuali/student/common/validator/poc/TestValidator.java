package org.kuali.student.common.validator.poc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.validator.poc.ConstraintMockAddress;
import org.kuali.student.common.validator.poc.ConstraintMockPerson;
import org.kuali.student.common.validator.poc.ServerDateParser;
import org.kuali.student.common.validator.poc.Validator;
import org.kuali.student.core.dictionary.dto.ConstraintSelector;
import org.kuali.student.core.dictionary.poc.dto.DataType;
import org.kuali.student.core.dictionary.poc.dto.FieldDefinition;
import org.kuali.student.core.dictionary.poc.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.poc.dto.RequiredConstraint;
import org.kuali.student.core.dictionary.poc.dto.ValidCharsConstraint;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class TestValidator {
	Validator val = null;
	
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
    	ConstraintMockPerson person = new ConstraintMockPerson();
    	
    	person.setFirstName("first");
    	person.setLastName("last");
    	person.setEmail("first@test.com");
    	person.setType("STUDENT");
    	person.setState("CREATE");
    	person.setId("P1");
    	    	
    	return person;
    }

    
    public ConstraintMockPerson buildTestPerson2() {
    	ConstraintMockPerson person = new ConstraintMockPerson();
    	
    	person.setFirstName("first");
    	person.setLastName("last");
    	person.setEmail("first@test.com");
    	person.setType("STUDENT");
    	person.setState("CREATE");
    	person.setId("P1");
    	person.setGpa(3.5);
    	ServerDateParser dp = new ServerDateParser();    	
    	person.setDob(dp.parseDate("1978-01-01"));
    	
    	
    	return person;
    }

    
    public ConstraintMockPerson buildTestPerson3() {
    	ConstraintMockPerson person = new ConstraintMockPerson();
    	
    	person.setFirstName("first");
    	person.setLastName("last");
    	person.setEmail("first@test.com");
    	person.setType("STUDENT");
    	person.setState("CREATE");
    	person.setId("P1");
    	ServerDateParser dp = new ServerDateParser();    	
    	person.setDob(dp.parseDate("1978-01-01"));
    	
    	ConstraintMockAddress address = new ConstraintMockAddress();
    	address.setState("ACTIVE");
    	address.setType("MAILING");
    	address.setId("A1");
    	address.setCity("TLH");
    	address.setStateCode("FL");
    	address.setCountry("US");
    	address.setLine1("");
    	address.setLine2("line2value");
    	address.setPostalCode("32306");
    	    	
    	List<ConstraintMockAddress> addressL = new ArrayList<ConstraintMockAddress>();
    	addressL.add(address);
    	
    	person.setAddress(addressL);

    	return person;
    }
    
    public ObjectStructureDefinition buildObjectStructure2() {
    	
    	ObjectStructureDefinition addrStruct = new ObjectStructureDefinition();
    	addrStruct.setName("addressInfo");
    	
    	// Line 1 Field
    	FieldDefinition f1 = new FieldDefinition();
    	f1.setName("line1");
    	f1.setDataType(DataType.STRING);
    	f1.setMaxLength("20");
    	f1.setMinLength(2);
    	f1.setMinOccurs(1);
    	ValidCharsConstraint vcs1 = new ValidCharsConstraint();
    	vcs1.setValue("regex:[a-z]*");
    	f1.setValidChars(vcs1);
    	
    	addrStruct.getAttributes().add(f1);

    	// Line 2 Field
    	FieldDefinition f2 = new FieldDefinition();
    	f2.setName("line2");
    	f2.setDataType(DataType.STRING);
    	f2.setMaxLength("20");
    	f2.setMinLength(2);
    	f2.setMinOccurs(0);
    	
    	RequiredConstraint rc2 = new RequiredConstraint();
    	rc2.setFieldPath("line1");
    	List<RequiredConstraint> rcList2 = new ArrayList<RequiredConstraint>();
    	rcList2.add(rc2);
    	f2.setRequireConstraint(rcList2);
    	
    	ValidCharsConstraint vcs2 = new ValidCharsConstraint();
    	vcs2.setValue("regex:[a-z]*");
    	f2.setValidChars(vcs1);
    	
    	addrStruct.getAttributes().add(f2);
    	
    	ObjectStructureDefinition objStruct = buildObjectStructure1();
    	
    	// Complex nested obj struct
    	FieldDefinition f3 = new FieldDefinition();
    	f3.setName("address");
       	f3.setDataType(DataType.COMPLEX);
    	f3.setDataObjectStructure(addrStruct);
    	
    	objStruct.getAttributes().add(f3);
    	
    	return objStruct;
    }

    public ObjectStructureDefinition buildObjectStructure1() {    
    	ObjectStructureDefinition objStruct = new ObjectStructureDefinition();
    	objStruct.setName("personInfo");
    	    	
    	// Line 1 Field
    	FieldDefinition f1 = new FieldDefinition();
    	f1.setName("firstName");
    	f1.setDataType(DataType.STRING);
    	f1.setMaxLength("20");
    	f1.setMinLength(2);
    	f1.setMinOccurs(1);
    	ValidCharsConstraint vcs1 = new ValidCharsConstraint();
    	vcs1.setValue("regex:[a-z]*");
    	f1.setValidChars(vcs1);
    	objStruct.getAttributes().add(f1);
    	    	    	
    	// DOB Field
    	FieldDefinition f2 = new FieldDefinition();
    	f2.setName("dob");
    	f2.setDataType(DataType.DATE);
    	f2.setExclusiveMin("1970-01-01");
    	f2.setMinOccurs(1);
    	objStruct.getAttributes().add(f2);
    	    	

    	// GAP
    	FieldDefinition f3 = new FieldDefinition();
    	f3.setName("gpa");
    	f3.setDataType(DataType.DOUBLE);
    	f3.setExclusiveMin("1.0");
    	f3.setInclusiveMax("4.0");
    	objStruct.getAttributes().add(f3);

    	return objStruct;    	
    }
}
