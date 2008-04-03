package org.kuali.student.poc.personidentity.person.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
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
import org.kuali.student.poc.wsdl.personidentity.exceptions.ReadOnlyException;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCitizenshipInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCreateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonNameInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonReferenceIdInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonUpdateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/jetty-context.xml"})
public class TestPersonServiceImpl {
    @Autowired
    PersonService client;

    @Test
    public void testCreatePersonInfoType() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DisabledIdentifierException {
//		BindingProvider bp = (BindingProvider) client;
//
//		Binding binding = bp.getBinding();
//		System.out.println("1: "
//				+ bp.getRequestContext().get(
//						BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
//
//		List<Handler> list = binding.getHandlerChain();
//
//		if (list == null) {
//			list = new ArrayList<Handler>();
//		}
//		list.add(new DebugHandler());
//		binding.setHandlerChain(list);

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

        // Now find all the types
        PersonTypeInfo personTypeInfo = client.fetchPersonType(personTypeId);

        // Validate results
        assertEquals("PersonType1", personTypeInfo.getName());
        assertEquals(personTypeId, personTypeInfo.getId());

    }

    @Test
    public void testCreatePerson() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DisabledIdentifierException {
        PersonAttributeTypeInfo attributeType3 = new PersonAttributeTypeInfo();
        attributeType3.setLabel("Attribute 3 Label");
        attributeType3.setName("Attr3");
        attributeType3.setType("STRING");

        PersonAttributeSetTypeInfo attributeSet2 = new PersonAttributeSetTypeInfo();
        attributeSet2.setName("AttrSet2");
        attributeSet2.getAttributeTypes().add(attributeType3);

        PersonTypeInfo personType2 = new PersonTypeInfo();
        personType2.setName("PersonType2");
        personType2.getAttributeSets().add(attributeSet2);

        Long personTypeId = client.createPersonTypeInfo(personType2);

        assertNotNull(personTypeId);

        PersonCreateInfo person = new PersonCreateInfo();
        person.setBirthDate(new Date());
        person.setGender('M');
        person.setAttribute("Attr3", "123-23-3456");

        PersonNameInfo name = new PersonNameInfo();
        name.setGivenName("Harold Horkins");
        name.setNameType("Official");
        person.getName().add(name);

        List<Long> personTypeInfoList = new ArrayList<Long>();
        personTypeInfoList.add(personTypeId);

        long resultId = client.createPerson(person, personTypeInfoList);

        PersonInfo result = client.fetchFullPersonInfo(resultId);

        assertEquals(result.getName().get(0).getGivenName(), person.getName().get(0).getGivenName());
        assertEquals(result.getAttribute("Attr3"), person.getAttribute("Attr3"));

        //Sneaking in a test for findPersonIdsForPersonType.
        List<Long> personIds = client.findPersonIdsForPersonType(personTypeId, null);
        assertEquals(1, personIds.size());
        assertEquals(resultId, personIds.get(0).longValue());
        
        // TODO test invalid (or unrelated) personTypes and attributeTypes
    }
    
	@Test
	public void testAssignPersonType() throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DisabledIdentifierException, DoesNotExistException {

        PersonAttributeTypeInfo attributeType4 = new PersonAttributeTypeInfo();
        attributeType4.setLabel("Attribute 4 Label");
        attributeType4.setName("Attr4");
        attributeType4.setType("STRING");

        PersonAttributeSetTypeInfo attributeSet3 = new PersonAttributeSetTypeInfo();
        attributeSet3.setName("AttrSet3");
        attributeSet3.getAttributeTypes().add(attributeType4);

        PersonTypeInfo personType3 = new PersonTypeInfo();
        personType3.setName("PersonType3");
        personType3.getAttributeSets().add(attributeSet3);

        Long personType3Id = client.createPersonTypeInfo(personType3);

        PersonAttributeTypeInfo attributeType5 = new PersonAttributeTypeInfo();
        attributeType5.setLabel("Attribute 5 Label");
        attributeType5.setName("Attr5");
        attributeType5.setType("STRING");

        PersonAttributeSetTypeInfo attributeSet4 = new PersonAttributeSetTypeInfo();
        attributeSet4.setName("AttrSet4");
        attributeSet4.getAttributeTypes().add(attributeType5);

        PersonTypeInfo personType5 = new PersonTypeInfo();
        personType5.setName("PersonType5");
        personType5.getAttributeSets().add(attributeSet4);

        Long personType4Id = client.createPersonTypeInfo(personType5);

        PersonCreateInfo person = new PersonCreateInfo();
        person.setBirthDate(new Date());
        person.setGender('M');
        person.setAttribute("Attr4", "Cobra Commander");

        PersonNameInfo name = new PersonNameInfo();
        name.setGivenName("Foggy Bottom");
        name.setNameType("Official");
        person.getName().add(name);

        List<Long> personTypeInfoList = new ArrayList<Long>();
        personTypeInfoList.add(personType3Id);

        Long personId = client.createPerson(person, personTypeInfoList);

        assertTrue(client.assignPersonType(personId, personType4Id));

        List<Long> personTypes = client.findPersonTypesForPerson(personId);

        assertEquals(2, personTypes.size());
        assertTrue((personTypes.get(0).equals(personType3Id) && personTypes.get(1).equals(personType4Id)) || (personTypes.get(0).equals(personType4Id) && personTypes.get(1).equals(personType3Id)));

	}
	/*
	 * @Test(expected=AlreadyExistsException.class) public void
	 * testCreatePersonTypeInfo() throws OperationFailedException,
	 * InvalidParameterException, AlreadyExistsException,
	 * MissingParameterException, PermissionDeniedException { PersonTypeInfoDTO
	 * personTypeInfo = new PersonTypeInfoDTO(0, "testType"); long newId =
	 * client.createPersonTypeInfo(personTypeInfo); assertTrue(newId > 0);
	 * 
	 * List<PersonTypeDTO> personTypeDTOList =
	 * client.findCreatablePersonTypes();
	 * 
	 * boolean found = false; for(PersonTypeDTO personTypeDTO :
	 * personTypeDTOList) { if(personTypeDTO.getId() == newId &&
	 * personTypeDTO.getName().equals("testType")) { found = true; } }
	 * 
	 * assertTrue(found); // Test the AlreadyExistsException
	 * client.createPersonTypeInfo(personTypeInfo); }
	 * 
	 * @Test public void testUpdatePerson() throws AlreadyExistsException,
	 * InvalidParameterException, MissingParameterException,
	 * OperationFailedException, PermissionDeniedException,
	 * DoesNotExistException, DisabledIdentifierException, ReadOnlyException {
	 * PersonDTO person = new PersonDTO(); person.setConfidential(false);
	 * person.setDob(new Date()); person.setFirstName("Deric");
	 * person.setLastName("D'Clapton"); person.setGender('M');
	 * person.setAttribute("ssn", "879-65-3154"); person.setAttribute("Attr1",
	 * "123-23-3456"); person.setAttribute("Attr2", "20080202"); List<PersonTypeInfoDTO>
	 * persontypes = new ArrayList<PersonTypeInfoDTO>(); for(PersonTypeDTO
	 * ptype: client.findCreatablePersonTypes()) { persontypes.add(ptype); }
	 * long id = client.createPerson(person, persontypes);
	 * 
	 * person.getAttributes().clear(); person.setId(id);
	 * person.setFirstName("Derek"); client.updatePerson(person);
	 * 
	 * person.setAttribute("Attr1", "879-65-3154"); client.updatePerson(person);
	 * 
	 * PersonDTO personResult = client.fetchFullPersonInfo(id);
	 * assertEquals("879-65-3154", personResult.getAttribute("Attr1"));
	 * assertEquals("20080202", personResult.getAttribute("Attr2"));
	 * 
	 * person.setAttribute("Attr2", null); client.updatePerson(person);
	 * 
	 * personResult = client.fetchFullPersonInfo(id);
	 * assertNull(personResult.getAttribute("Attr2")); }
	 * 
	 * @Test public void testFetchPersonByPeronType() throws
	 * DoesNotExistException, DisabledIdentifierException,
	 * InvalidParameterException, MissingParameterException,
	 * OperationFailedException, AlreadyExistsException,
	 * PermissionDeniedException{ PersonDTO person = new PersonDTO();
	 * person.setConfidential(false); person.setDob(new Date());
	 * person.setFirstName("Frank"); person.setLastName("Zappa");
	 * person.setGender('M'); person.setAttribute("ssn", "879-65-3154");
	 * person.setAttribute("Attr1", "123-23-3456"); person.setAttribute("Attr2",
	 * "20080202"); List<PersonTypeInfoDTO> persontypes = new ArrayList<PersonTypeInfoDTO>();
	 * for(PersonTypeDTO ptype: client.findCreatablePersonTypes()) {
	 * persontypes.add(ptype); } long id = client.createPerson(person,
	 * persontypes); PersonDTO foundPerson =
	 * client.fetchPersonInfoByPersonType(id, persontypes.get(0));
	 * assertEquals(1,foundPerson.getPersonTypes().size()); }
	 */


    /*
     * @Test(expected=AlreadyExistsException.class) public void testCreatePersonTypeInfo() throws OperationFailedException,
     * InvalidParameterException, AlreadyExistsException, MissingParameterException, PermissionDeniedException {
     * PersonTypeInfoDTO personTypeInfo = new PersonTypeInfoDTO(0, "testType"); long newId =
     * client.createPersonTypeInfo(personTypeInfo); assertTrue(newId > 0); List<PersonTypeDTO> personTypeDTOList =
     * client.findCreatablePersonTypes(); boolean found = false; for(PersonTypeDTO personTypeDTO : personTypeDTOList) {
     * if(personTypeDTO.getId() == newId && personTypeDTO.getName().equals("testType")) { found = true; } }
     * assertTrue(found); // Test the AlreadyExistsException client.createPersonTypeInfo(personTypeInfo); }
     */
    
    @Test
    public void testUpdatePerson() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException {
        PersonAttributeTypeInfo attributeType6 = new PersonAttributeTypeInfo();
        attributeType6.setLabel("Attribute 6 Label");
        attributeType6.setName("Attr6");
        attributeType6.setType("STRING");

        PersonAttributeSetTypeInfo attributeSet5 = new PersonAttributeSetTypeInfo();
        attributeSet5.setName("AttrSet5");
        attributeSet5.getAttributeTypes().add(attributeType6);

        PersonTypeInfo personType4 = new PersonTypeInfo();
        personType4.setName("PersonType4");
        personType4.getAttributeSets().add(attributeSet5);

        Long personTypeId = client.createPersonTypeInfo(personType4);

        assertNotNull(personTypeId);

        PersonCreateInfo person = new PersonCreateInfo();
        person.setBirthDate(new Date());
        person.setAttribute("Attr6", "none");
        
        PersonNameInfo name = new PersonNameInfo();
        name.setGivenName("John Smith");
        name.setNameType("Official");
        person.getName().add(name);

        List<Long> personTypeInfoList = new ArrayList<Long>();
        personTypeInfoList.add(personTypeId);

        long resultId = client.createPerson(person, personTypeInfoList);
               
        PersonInfo personInfo = client.fetchFullPersonInfo(resultId);
        PersonUpdateInfo personUpdateInfo = new PersonUpdateInfo(personInfo);
        
        //Update existing name
        List<PersonNameInfo> personNameList = personUpdateInfo.getName();
        name = personNameList.get(0);               
        name.setGivenName("John");
        name.setSurname("Smith");
        
        //Add new name
        name = new PersonNameInfo();
        name.setGivenName("John");
        name.setSurname("Smith");
        name.setSuffix("III");
        name.setNameType("Diploma");
        personNameList.add(name);
         
        personUpdateInfo.getAttributes().clear();
        personUpdateInfo.setAttribute("Attr6", "Thunder Clap");
        
        personUpdateInfo.setGender('F');
        
        PersonCitizenshipInfo citizenship = new PersonCitizenshipInfo();
        citizenship.setCountryOfCitizenshipCode("US");
        personUpdateInfo.setCitizenship(citizenship);
        
        PersonReferenceIdInfo personReferenceIdInfo = new PersonReferenceIdInfo();
        personReferenceIdInfo.setReferenceId("123-56-7766");
        personReferenceIdInfo.setOrganizationReferenceId("SSA");
        List<PersonReferenceIdInfo> personReferenceIdList = new ArrayList<PersonReferenceIdInfo>();
        personReferenceIdList.add(personReferenceIdInfo);
        personUpdateInfo.setReferenceId(personReferenceIdList);
        
        personUpdateInfo.setUpdateUserComment("Testing update");
        
        client.updatePerson(personInfo.getPersonId(), personUpdateInfo);
        
        PersonInfo personUpdated = client.fetchFullPersonInfo(personInfo.getPersonId());
        
        assertEquals(2, personUpdated.getName().size());
        assertEquals(personUpdateInfo.getGender(),personUpdated.getGender());
        assertEquals(1, personUpdated.getReferenceId().size());
        assertEquals(personUpdateInfo.getCitizenship().getCountryOfCitizenshipCode(),personUpdated.getCitizenship().getCountryOfCitizenshipCode());
        assertEquals(personUpdateInfo.getAttribute("Attr6"), personUpdated.getAttribute("Attr6"));

    }

    /*
     * @Test public void testFetchPersonByPeronType() throws DoesNotExistException, DisabledIdentifierException,
     * InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException,
     * PermissionDeniedException{ PersonDTO person = new PersonDTO(); person.setConfidential(false); person.setDob(new
     * Date()); person.setFirstName("Frank"); person.setLastName("Zappa"); person.setGender('M'); person.setAttribute("ssn",
     * "879-65-3154"); person.setAttribute("Attr1", "123-23-3456"); person.setAttribute("Attr2", "20080202"); List<PersonTypeInfoDTO>
     * persontypes = new ArrayList<PersonTypeInfoDTO>(); for(PersonTypeDTO ptype: client.findCreatablePersonTypes()) {
     * persontypes.add(ptype); } long id = client.createPerson(person, persontypes); PersonDTO foundPerson =
     * client.fetchPersonInfoByPersonType(id, persontypes.get(0)); assertEquals(1,foundPerson.getPersonTypes().size()); }
     */

}
