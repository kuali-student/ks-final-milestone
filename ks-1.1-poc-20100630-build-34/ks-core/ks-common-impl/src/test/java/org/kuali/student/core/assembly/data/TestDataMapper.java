package org.kuali.student.core.assembly.data;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.assembly.transform.DataBeanMapper;
import org.kuali.student.core.assembly.transform.DefaultDataBeanMapper;


public class TestDataMapper {

	MockPerson person;

	@Before
	public void setup() throws Exception{
		person = new MockPerson();
		person.setFirstName("first");
		person.setLastName("last");
		person.setEmail("first@test.com");
		person.setType("STUDENT");
		person.setState("CREATE");
		person.setId("P1");
		person.setGpa(3.5);
		DateFormat df = new SimpleDateFormat("yy-MM-DD");
		person.setDob(df.parse("1978-01-01"));
		
		
		MockAddress address = new MockAddress();
        address.setState("ACTIVE");
        address.setType("MAILING");
        address.setId("A1");
        address.setCity("TLH");
        address.setStateCode("FL");
        address.setCountry("US");
        address.setLine1("");
        address.setLine2("line2value");
        address.setPostalCode("32306");

        //Add a dynamic attribute
        Map<String,String> attributes = new HashMap<String,String>();
        attributes.put("POBox", "1145");
        attributes.put("building", "Residential");
        address.setAttributes(attributes);
        
        List<MockAddress> addressL = new ArrayList<MockAddress>();
        addressL.add(address);

        person.setAddress(addressL);

		
	}

	@Test
	public void testConverDTOtoData() throws Exception {
		DataBeanMapper dataMapper = new DefaultDataBeanMapper();
		
		Data data = dataMapper.convertFromBean(person);
		
		MockPerson convertedPerson = (MockPerson)dataMapper.convertFromData(data, MockPerson.class);

		assertEquals(person.getDob(), convertedPerson.getDob());
		assertEquals(person.getEmail(), convertedPerson.getEmail());
		assertEquals(person.getFirstName(), convertedPerson.getFirstName());
		assertEquals(person.getGpa(), convertedPerson.getGpa());
		assertEquals(person.getId(), convertedPerson.getId());
		assertEquals(person.getLastName(), convertedPerson.getLastName());
		assertEquals(person.getState(), convertedPerson.getState());
		assertEquals(person.getType(), convertedPerson.getType());
			
		assertEquals(convertedPerson.getAddress().size(),1);
		
		MockAddress address = person.getAddress().get(0);
		MockAddress convertedAddress = convertedPerson.getAddress().get(0);
		
		assertEquals(address.getCity(), convertedAddress.getCity());
		assertEquals(address.getCountry(), convertedAddress.getCountry());
		assertEquals(address.getId(), convertedAddress.getId());
		assertEquals(address.getLine1(), convertedAddress.getLine1());
		assertEquals(address.getLine2(), convertedAddress.getLine2());
		assertEquals(address.getPostalCode(), convertedAddress.getPostalCode());
		assertEquals(address.getState(), convertedAddress.getState());
		assertEquals(address.getStateCode(), convertedAddress.getStateCode());
		assertEquals(address.getType(), convertedAddress.getType());
		
		Map<String,String> attributes = convertedAddress.getAttributes();
		assertEquals(attributes.size(),2);
		assertEquals(attributes.get("POBox"),"1145");
		assertEquals(attributes.get("building"),"Residential");
	}
	
	

}
