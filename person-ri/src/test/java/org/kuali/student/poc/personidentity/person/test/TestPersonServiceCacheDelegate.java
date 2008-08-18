package org.kuali.student.poc.personidentity.person.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
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
import org.kuali.student.poc.personidentity.person.PersonService;
import org.kuali.student.poc.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonAttributeTypeInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonCitizenshipInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonCreateInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonNameInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonReferenceIdInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonUpdateInfo;
import org.kuali.student.poc.personidentity.person.service.PersonServiceCacheDelegate;

@Daos( { @Dao("org.kuali.student.poc.personidentity.person.dao.PersonDAOImpl") })
@PersistenceFileLocation("classpath:META-INF/person-persistence.xml")
public class TestPersonServiceCacheDelegate extends AbstractServiceTest {

	@Client(value = "org.kuali.student.poc.personidentity.person.service.PersonServiceCacheDelegate", port = "9191")
	public PersonService client;

	public PersonService clientCacheDelegate;

	@Client(value = "org.kuali.student.poc.personidentity.person.service.PersonServiceSecure", port = "9191", secure = true)
	public PersonService clientSecure;

	@Before
	public void setup() {
		clientCacheDelegate = new PersonServiceCacheDelegate(client, "ehcache2.xml");
	}

	@Test
	public void testPerson() throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DoesNotExistException, DisabledIdentifierException,
			ReadOnlyException {
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

		assertEquals(result.getName().get(0).getGivenName(), person.getName()
				.get(0).getGivenName());
		assertEquals(result.getAttribute("Attr3"), person.getAttribute("Attr3"));

		// Sneaking in a test for findPersonIdsForPersonType.
		List<String> personIds = client.findPersonIdsForPersonType(
				personTypeId, null);
		assertEquals(1, personIds.size());
		assertEquals(resultId, personIds.get(0));
		// should only be in server cache here
		clientCacheDelegate.findPeopleByPersonIds(personIds);
		
		
		////////////////////////////////////////////////////////////////////
		// invalidate the cache here
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
        
		// should not be in cache now
		clientCacheDelegate.findPeopleByPersonIds(personIds);
		// TODO test invalid (or unrelated) personTypes and attributeTypes
	}

}
