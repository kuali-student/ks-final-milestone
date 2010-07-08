package org.kuali.student.common.validator;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.validator.poc.ServerDateParser;

public class ValidatorMockObjectGenerator {
    public static ConstraintMockPerson buildTestPerson1() {
    	ConstraintMockPerson person = new ConstraintMockPerson();
    	
    	person.setFirstName("first");
    	person.setLastName("last");
    	person.setEmail("first@test.com");
    	person.setType("STUDENT");
    	person.setState("CREATE");
    	person.setId("P1");
    	    	
    	return person;
    }

    
    public static ConstraintMockPerson buildTestPerson2() {
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

    
    public static ConstraintMockPerson buildTestPerson3() {
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
}
