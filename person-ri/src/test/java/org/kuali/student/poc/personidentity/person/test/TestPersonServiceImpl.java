package org.kuali.student.poc.personidentity.person.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.poc.common.ws.exceptions.ReadOnlyException;
import org.kuali.student.poc.personidentity.person.dto.PersonAttributeSetTypeDisplay;
import org.kuali.student.poc.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonAttributeTypeInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonCitizenshipInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonCreateInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonCriteria;
import org.kuali.student.poc.personidentity.person.dto.PersonDisplay;
import org.kuali.student.poc.personidentity.person.dto.PersonDisplayDTO;
import org.kuali.student.poc.personidentity.person.dto.PersonInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonNameInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonReferenceIdInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonUpdateInfo;
import org.kuali.student.poc.personidentity.person.service.PersonService;

@Daos({@Dao("org.kuali.student.poc.personidentity.person.dao.impl.PersonDAOImpl")})
@PersistenceFileLocation("classpath:META-INF/person-persistence.xml")
public class TestPersonServiceImpl extends AbstractServiceTest{

    @Client(value="org.kuali.student.poc.personidentity.person.service.impl.PersonServiceImpl", port="9191")
    public PersonService client;

    @Client(value = "org.kuali.student.poc.personidentity.person.service.impl.PersonServiceSecure", port = "9191", secure=true)
    public PersonService clientSecure;

    @Test
    public void testCreatePersonInfoType() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DisabledIdentifierException {

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

        String personTypeId = client.createPersonTypeInfo(personType1);

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

        String personTypeId = client.createPersonTypeInfo(personType2);

        assertNotNull(personTypeId);

        PersonCreateInfo person = new PersonCreateInfo();
        person.setBirthDate(new Date());
        person.setGender('M');
        person.setAttribute("Attr3", "123-23-3456");

        PersonNameInfo name = new PersonNameInfo();
        name.setGivenName("Harold Horkins");
        name.setSurname("Sea Bass");
        name.setNameType("Official");
        person.getName().add(name);

        List<String> personTypeInfoList = new ArrayList<String>();
        personTypeInfoList.add(personTypeId);

        String resultId = client.createPerson(person, personTypeInfoList);

        PersonInfo result = client.fetchFullPersonInfo(resultId);

        assertEquals(result.getName().get(0).getGivenName(), person.getName().get(0).getGivenName());
        assertEquals(result.getAttribute("Attr3"), person.getAttribute("Attr3"));

        //Sneaking in a test for findPersonIdsForPersonType.
        List<String> personIds = client.findPersonIdsForPersonType(personTypeId, null);
        assertEquals(1, personIds.size());
        assertEquals(resultId, personIds.get(0));

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

        String personType3Id = client.createPersonTypeInfo(personType3);

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

        String personType4Id = client.createPersonTypeInfo(personType5);

        PersonCreateInfo person = new PersonCreateInfo();
        person.setBirthDate(new Date());
        person.setGender('M');
        person.setAttribute("Attr4", "Cobra Commander");

        PersonNameInfo name = new PersonNameInfo();
        name.setGivenName("Foggy Bottom");
        name.setSurname("Serpentor");
        name.setNameType("Official");
        person.getName().add(name);

        List<String> personTypeInfoList = new ArrayList<String>();
        personTypeInfoList.add(personType3Id);

        String personId = client.createPerson(person, personTypeInfoList);

        assertTrue(client.assignPersonType(personId, personType4Id));

        List<String> personTypes = client.findPersonTypesForPerson(personId);

        assertEquals(2, personTypes.size());
        assertTrue((personTypes.get(0).equals(personType3Id) && personTypes.get(1).equals(personType4Id)) || (personTypes.get(0).equals(personType4Id) && personTypes.get(1).equals(personType3Id)));

        //Testing searchForPeopleByPersonAttributeSetType
        person = new PersonCreateInfo();
        person.setBirthDate(new Date());
        person.setGender('F');
        person.setAttribute("Attr4", "Check the briefcase");

        name = new PersonNameInfo();
        name.setGivenName("Mary");
        name.setSurname("Swanson");
        name.setNameType("Official");
        person.getName().add(name);

        name = new PersonNameInfo();
        name.setGivenName("Mary");
        name.setSurname("Samsonite");
        name.setNameType("Briefcase");
        person.getName().add(name);

        String personId2 = client.createPerson(person, personTypeInfoList);

        PersonCriteria criteria = new PersonCriteria();
        criteria.setFirstName("%");
        criteria.setLastName("%");
        List<PersonAttributeSetTypeDisplay> attributeSetTypes = client.findPersonAttributeSetTypesForPersonType(personType3Id);
        assertEquals(1,attributeSetTypes.size());
        List<PersonInfo> people = client.searchForPeopleByPersonAttributeSetType(attributeSetTypes.get(0).getId(), criteria);
        assertEquals(2,people.size());
        criteria.setFirstName("Mary");
        people = client.searchForPeopleByPersonAttributeSetType(attributeSetTypes.get(0).getId(), criteria);
        assertEquals(1,people.size());

        //Testing find people by person ids

        List<String> personIdList = new ArrayList<String>();
        personIdList.add(personId);
        personIdList.add(personId2);

        List<PersonDisplay> personDisplay = client.findPeopleDisplayByPersonIds(personIdList);
        assertEquals("Wrong number of PersonDisplay", 3, personDisplay.size());
        List<String> personNames = new ArrayList<String>();
        for (PersonDisplay display : personDisplay) {
        	personNames.add(display.getName().getGivenName());
        }
        assertTrue(personNames.contains("Foggy Bottom"));
        assertTrue(personNames.contains("Mary"));

        //Testing removePersonType
        assertTrue(client.removePersonType(personId, personType3Id));

        personTypes = client.findPersonTypesForPerson(personId);

        assertEquals(1, personTypes.size());
        assertTrue(personTypes.get(0).equals(personType4Id));
	}

	/*
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

        String personTypeId = client.createPersonTypeInfo(personType4);

        assertNotNull(personTypeId);

        PersonCreateInfo person = new PersonCreateInfo();
        person.setBirthDate(new Date());
        person.setAttribute("Attr6", "none");

        PersonNameInfo name = new PersonNameInfo();
        name.setGivenName("John Smith");
        name.setNameType("Official");
        person.getName().add(name);

        List<String> personTypeInfoList = new ArrayList<String>();
        personTypeInfoList.add(personTypeId);

        String resultId = client.createPerson(person, personTypeInfoList);

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

        //testing searchForPeople
        PersonCriteria personCriteria = new PersonCriteria();
        personCriteria.setFirstName("John");
        personCriteria.setLastName("Smith");
        List<PersonInfo> people = client.searchForPeople(personCriteria);
        assertEquals(1,people.size());
        assertEquals(resultId,people.get(0).getPersonId());
        List<String> personIds = client.searchForPersonIds(personCriteria);
        assertEquals(1,personIds.size());
        assertEquals(resultId,personIds.get(0));
    }


    @Test
    public void testSecureCreatePersonInfoType() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DisabledIdentifierException, Exception {

        PersonAttributeSetTypeInfo attributeSet1 = new PersonAttributeSetTypeInfo();
        attributeSet1.setName("AttrSetSecure");

        PersonTypeInfo personType1 = new PersonTypeInfo();
        personType1.setName("PersonTypeSecure");
        personType1.getAttributeSets().add(attributeSet1);

        // Make a call with valid username/pwd
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("kuali-admin", "admin"));

        String personTypeId = clientSecure.createPersonTypeInfo(personType1);

        PersonCreateInfo person = new PersonCreateInfo();
        person.setBirthDate(new Date());
        person.setGender('M');
        person.setAttribute("Attr3", "123-23-3456");

        PersonNameInfo name = new PersonNameInfo();
        name.setGivenName("Billy Bob");
        name.setSurname("Bob");
        name.setNameType("Official");
        person.getName().add(name);

        List<String> personTypeInfoList = new ArrayList<String>();
        personTypeInfoList.add(personTypeId);

        String personId = clientSecure.createPerson(person, personTypeInfoList);

        PersonInfo personInfo = client.fetchFullPersonInfo(personId);

        //FIXME: Metro fails to inject web service, so the update user id is never set
        //assertEquals("1234-344", personInfo.getCreateUserId());

        // Now find all the types
        PersonTypeInfo personTypeInfo = clientSecure.fetchPersonType(personTypeId);

        // Validate results
        assertEquals("PersonTypeSecure", personTypeInfo.getName());
        assertEquals(personTypeId, personTypeInfo.getId());

        // Make a call with valid username/pwd
        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("kuali-admin", "badpwd"));

        try {
            clientSecure.createPersonTypeInfo(personType1);
            throw new Exception("Security not processed");
        } catch (SOAPFaultException sfe) {
            //This is the soap fault message thrown by CXF
            assertEquals("The security token could not be authenticated or authorized", sfe.getMessage());
        }
        catch (WebServiceException wse){
            //FIXME: This is a wrong, because Metro should be throwing SoapFaultException, but is
            //throwing "javax.xml.ws.WebServiceException: No Content-type in the header!"
            assertEquals("No Content-type in the header!", wse.getMessage());
        }
    }

    @Test
    public void testPersonDisplayDTO() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DisabledIdentifierException, Exception {
    	final PersonCriteria criteria = new PersonCriteria();
    	List<PersonDisplayDTO> persons = client.searchForPersonDisplayDTOs(criteria);
    	assertNotNull("Should not have null pointer as result", persons);
    	assertEquals("Wrong number of person names", 7, persons.size());
    	PersonDisplayDTO dto = persons.get(0);
    	assertEquals("Wrong Person name", "Harold Horkins Sea Bass", dto.getName());
    	assertNotNull("No person id", dto.getPersonId());

    	criteria.setFirstName("Darth");
    	List<PersonDisplayDTO> persons2 = client.searchForPersonDisplayDTOs(criteria);
       	assertTrue("Should have null pointer/size 0 as result", (persons2 == null || persons2.size() == 0));
    }
}
