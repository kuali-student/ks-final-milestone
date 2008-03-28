package org.kuali.student.poc.personidentity.person.test;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.wsdl.personidentity.exceptions.AlreadyExistsException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DoesNotExistException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.InvalidParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.MissingParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.OperationFailedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.PermissionDeniedException;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:META-INF/jetty-context.xml"})
public class TestPersonServiceImpl {
	@Autowired
	PersonService client;

	@Test
	public void testCreatePersonInfoType() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DisabledIdentifierException{
		
		PersonAttributeTypeInfo attributeType1 = new PersonAttributeTypeInfo();
		attributeType1.setLabel("Attribute 1 Label");
		attributeType1.setName("Attr1");
		attributeType1.setType("STRING");
		
		PersonAttributeTypeInfo attributeType2 = new PersonAttributeTypeInfo();
		attributeType2.setLabel("Attribute 2 Label");
		attributeType2.setName("Attr2");
		attributeType2.setType("DATE");
		
		PersonAttributeSetTypeInfo attributeSet1 = new PersonAttributeSetTypeInfo();
		attributeSet1.setName("AttrSet1");
		attributeSet1.getAttributeTypes().add(attributeType1);
		attributeSet1.getAttributeTypes().add(attributeType2);
		
		PersonTypeInfo personType1 = new PersonTypeInfo();
		personType1.setName("PersonType1");
		personType1.getAttributeSets().add(attributeSet1);
		
		Long personTypeId = client.createPersonTypeInfo(personType1);
		
		//Now find all the types
		List<PersonTypeDisplay> personTypes = client.findPersonTypes();

		//Validate results
		assertEquals(1,personTypes.size());
		assertEquals("PersonType1",personTypes.get(0).getName());
		assertEquals(personTypeId,personTypes.get(0).getId());
		
		
	}
	
	/*@Test
	public void testCreatePerson() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DisabledIdentifierException{
		
		List<PersonTypeDTO> personTypes = client.findCreatablePersonTypes();
		PersonTypeDTO personType = null;
		for(PersonTypeDTO type : personTypes) {
			if(type.getName().equals("PersonType1")) {
				personType = type;
			}
		}
		assertNotNull(personType);
		
		PersonDTO person = new PersonDTO();
		person.setConfidential(false);
		person.setDob(new Date());
		person.setFirstName("Eric");
		person.setLastName("Clapton");
		person.setGender('M');
		person.setAttribute("Attr1", "123-23-3456");
		
		List<PersonTypeInfoDTO> personTypeInfoList = new ArrayList<PersonTypeInfoDTO>();
		personTypeInfoList.add(personType);
		
		long resultId = client.createPerson(person, personTypeInfoList);
		
		PersonDTO result = client.fetchFullPersonInfo(resultId);
		
		assertEquals(result.getFirstName(), person.getFirstName());
		assertEquals(result.getAttribute("Attr1"), person.getAttribute("Attr1"));
		
		// TODO test invalid (or unrelated) personTypes and attributeTypes
	}*/
	
	/*
	@Test(expected=AlreadyExistsException.class)
	public void testCreatePersonTypeInfo() throws OperationFailedException, InvalidParameterException, AlreadyExistsException, MissingParameterException, PermissionDeniedException {
		PersonTypeInfoDTO personTypeInfo = new PersonTypeInfoDTO(0, "testType");
		long newId = client.createPersonTypeInfo(personTypeInfo);
		assertTrue(newId > 0);
		
		List<PersonTypeDTO> personTypeDTOList = client.findCreatablePersonTypes();
		
		boolean found = false;
		for(PersonTypeDTO personTypeDTO : personTypeDTOList) {
			if(personTypeDTO.getId() == newId && personTypeDTO.getName().equals("testType")) {
				found = true;
			}
		}
		
		assertTrue(found);
		
		// Test the AlreadyExistsException
		client.createPersonTypeInfo(personTypeInfo);
	}
	
	@Test
	public void testUpdatePerson() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException {
        PersonDTO person = new PersonDTO();
        person.setConfidential(false);
        person.setDob(new Date());
        person.setFirstName("Deric");
        person.setLastName("D'Clapton");
        person.setGender('M');
        person.setAttribute("ssn", "879-65-3154");
        person.setAttribute("Attr1", "123-23-3456");
        person.setAttribute("Attr2", "20080202");
        List<PersonTypeInfoDTO> persontypes = new ArrayList<PersonTypeInfoDTO>();
        for(PersonTypeDTO ptype: client.findCreatablePersonTypes()) {
            persontypes.add(ptype);
        }
        long id = client.createPerson(person, persontypes);

        person.getAttributes().clear();
        person.setId(id);
        person.setFirstName("Derek");
        client.updatePerson(person);
        
        person.setAttribute("Attr1", "879-65-3154");
        client.updatePerson(person);
        
        PersonDTO personResult = client.fetchFullPersonInfo(id);
        assertEquals("879-65-3154", personResult.getAttribute("Attr1"));
        assertEquals("20080202", personResult.getAttribute("Attr2"));
        
        person.setAttribute("Attr2", null);
        client.updatePerson(person);
        
        personResult = client.fetchFullPersonInfo(id);
        assertNull(personResult.getAttribute("Attr2"));
	}
	
	@Test
	public void testFetchPersonByPeronType() throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, PermissionDeniedException{
        PersonDTO person = new PersonDTO();
        person.setConfidential(false);
        person.setDob(new Date());
        person.setFirstName("Frank");
        person.setLastName("Zappa");
        person.setGender('M');
        person.setAttribute("ssn", "879-65-3154");
        person.setAttribute("Attr1", "123-23-3456");
        person.setAttribute("Attr2", "20080202");
        List<PersonTypeInfoDTO> persontypes = new ArrayList<PersonTypeInfoDTO>();
        for(PersonTypeDTO ptype: client.findCreatablePersonTypes()) {
            persontypes.add(ptype);
        }
        long id = client.createPerson(person, persontypes);
		PersonDTO foundPerson = client.fetchPersonInfoByPersonType(id, persontypes.get(0));
		assertEquals(1,foundPerson.getPersonTypes().size());
	}*/
}
