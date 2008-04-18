package org.kuali.student.poc.personidentity.person.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.common.util.EhCacheHelper;
import org.kuali.student.poc.wsdl.personidentity.exceptions.AlreadyExistsException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DoesNotExistException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.InvalidParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.MissingParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.OperationFailedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.PermissionDeniedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.ReadOnlyException;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCitizenshipInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCreateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonNameInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonUpdateInfo;
import org.springframework.test.context.transaction.AfterTransaction;

@Daos({@Dao("org.kuali.student.poc.personidentity.person.dao.PersonDAOImpl")})
@PersistenceFileLocation("classpath:META-INF/person-persistence.xml")
public class TestPersonServiceCache extends AbstractServiceTest {
	
    @Client(value="org.kuali.student.poc.personidentity.person.service.PersonServiceImpl", port="9191")
    public PersonService client;
    
	private int numInstances = 5;
	private char he = 'M';
	private char she = 'F';
	
	@Test
	public void cachPersonTypeInfo() throws AlreadyExistsException, DisabledIdentifierException, DoesNotExistException, 
				InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, 
																										ReadOnlyException {
		assertNotNull("Client is null ", client);
		List<PersonAttributeTypeInfo> personAttTypeInfos = createPersonAttTypeInfos() ;
		assertEquals(personAttTypeInfos.size(), numInstances);
		//List<Long> personAttributeTypeInfoIds = persistPersonAttributeTypeInfos(personAttTypeInfos);
		//assertEquals(personAttributeTypeInfoIds.size(), numInstances);
		
		List<PersonAttributeSetTypeInfo>personAttributeSetTypeInfos = createPersonAttSetTypeInfos(personAttTypeInfos);
		assertEquals(personAttributeSetTypeInfos.size(), numInstances);
		List<Long>personAttSetTypeInfoIds = persistPersonAttSetTypeInfos(personAttributeSetTypeInfos);
		assertEquals(personAttSetTypeInfoIds.size(), numInstances);
		personAttributeSetTypeInfos = findAllPersonAttributeSetTypeInfos(personAttSetTypeInfoIds); 
		assertEquals(personAttributeSetTypeInfos.size(), numInstances);
		
		List<PersonTypeInfo> personTypeInfos = createPersonTypeInfos(personAttributeSetTypeInfos);
		assertEquals(personTypeInfos.size(), numInstances);
		List<Long> personTypeInfoIds = persistPersonTypeInfos(personTypeInfos);
		assertEquals(personTypeInfoIds.size(), numInstances);
		personTypeInfos = findAllPersonTypeInfos(personTypeInfoIds);
		assertEquals(personTypeInfos.size(), numInstances);
		
		List<PersonNameInfo>personNameInfos = createPersonNameInfos();
		List<PersonCitizenshipInfo>personCitizenshipInfos = createPersonCitizenshipInfos();
		List<PersonCreateInfo>personCreateInfos = createPersonInfos(personNameInfos,personCitizenshipInfos);
		List<Long>personCreateInfoIds = persistPersonCreateInfos(personCreateInfos,personTypeInfoIds);
		assertEquals(personCreateInfoIds.size(), numInstances);
		List<PersonInfo>personInfos = findAllPersonInfos(personCreateInfoIds);
		assertEquals(personInfos.size(), numInstances);
		
		updatePersonInfos(personCreateInfoIds, personInfos);
		personInfos = checkUpdateOfPersonInfos(personCreateInfoIds);
		assertEquals(personInfos.size(), numInstances);
		
		deleteAllPersonInfos(personCreateInfoIds); 
		//personInfos = findAllPersonInfos(personCreateInfoIds);
		//assertEquals(personInfos.size(), 0);
	}

	private List<PersonTypeInfo> createPersonTypeInfos(List<PersonAttributeSetTypeInfo>personAttributeSetTypeInfos) {
		PersonTypeInfo personTypeInfo = null;
		List<PersonTypeInfo> personTypeInfos = new ArrayList<PersonTypeInfo>();
    	for(int i = 0; i < numInstances; i++) {
    		personTypeInfo = new PersonTypeInfo();
    		personTypeInfo.setName("Name" + i);
    		personTypeInfo.getAttributeSets().add(personAttributeSetTypeInfos.get(i));
    		personTypeInfos.add(personTypeInfo);
    	}
		return personTypeInfos;
	}
	
	private List<Long> persistPersonTypeInfos(List<PersonTypeInfo> personTypeInfos) throws AlreadyExistsException, InvalidParameterException, 
									MissingParameterException, OperationFailedException,PermissionDeniedException {
		
		
		List<Long>personTypeInfoIds = new ArrayList<Long>();
		for(PersonTypeInfo personTypeInfo : personTypeInfos) {
			personTypeInfoIds.add(client.createPersonTypeInfo(personTypeInfo));
		}
		return personTypeInfoIds;
	}
		
	private List<PersonTypeInfo>findAllPersonTypeInfos(List<Long>personTypeInfoIds) throws DoesNotExistException,
										InvalidParameterException, MissingParameterException, OperationFailedException {
		List<PersonTypeInfo> personTypeInfos = new ArrayList();
		for(Long personTypeInfoId : personTypeInfoIds) {
			PersonTypeInfo personTypeInfo = client.fetchPersonType(personTypeInfoId);
			assertNotNull("PersonTypeInfo is null ", personTypeInfo);
			personTypeInfos.add(personTypeInfo);
		}
		return personTypeInfos;
	}
	
	private List<PersonAttributeTypeInfo> createPersonAttTypeInfos() {
    	PersonAttributeTypeInfo attributeTypeInfo = null;
    	List<PersonAttributeTypeInfo> personAttTypeInfos = new ArrayList<PersonAttributeTypeInfo>();
    	for(int i = 0; i < numInstances; i++) {
        	attributeTypeInfo = new PersonAttributeTypeInfo();
        	attributeTypeInfo.setLabel("Label" + i);
        	attributeTypeInfo.setName("Name" + i);
        	attributeTypeInfo.setType("Type" + i);
        	personAttTypeInfos.add(attributeTypeInfo);
    	}
    	return personAttTypeInfos;
	}

	private List<Long> persistPersonAttributeTypeInfos(List<PersonAttributeTypeInfo> personAttTypeInfos) throws AlreadyExistsException, InvalidParameterException, 
	MissingParameterException, OperationFailedException,PermissionDeniedException {

		List<Long>personAttributeTypeInfoIds = new ArrayList<Long>();
		for(PersonAttributeTypeInfo personAttTypeInfo : personAttTypeInfos) {
			personAttributeTypeInfoIds.add(client.createAttributeDefinition(personAttTypeInfo));
		}
		return personAttributeTypeInfoIds;
	}



	private List<PersonAttributeSetTypeInfo> createPersonAttSetTypeInfos(List<PersonAttributeTypeInfo> personAttTypeInfos) {
		PersonAttributeSetTypeInfo attributeSetTypeInfo = null;
    	List<PersonAttributeSetTypeInfo> personSetTypeInfos = new ArrayList<PersonAttributeSetTypeInfo>();
    	for(int i = 0; i < numInstances; i++) {
        	attributeSetTypeInfo = new PersonAttributeSetTypeInfo();
        	attributeSetTypeInfo.setName("Name" + i);
        	attributeSetTypeInfo.getAttributeTypes().add(personAttTypeInfos.get(i));
        	personSetTypeInfos.add(attributeSetTypeInfo);
    	}
    	return personSetTypeInfos;
	}
	
	private List<Long> persistPersonAttSetTypeInfos(List<PersonAttributeSetTypeInfo>personAttributeSetTypeInfos ) throws AlreadyExistsException, InvalidParameterException, 
									MissingParameterException, OperationFailedException,PermissionDeniedException {
		
		List<Long>personAttributeSetTypeInfoIds = new ArrayList<Long>();
		for(PersonAttributeSetTypeInfo personAttributeSetTypeInfo : personAttributeSetTypeInfos) {
			personAttributeSetTypeInfoIds.add(client.createPersonAttributeSetType(personAttributeSetTypeInfo));
		}
		return personAttributeSetTypeInfoIds;
	}

	
	private List<PersonAttributeSetTypeInfo>findAllPersonAttributeSetTypeInfos(List<Long>personAttributeSetTypeInfoIds) throws DoesNotExistException,
										InvalidParameterException, MissingParameterException, OperationFailedException {
		List<PersonAttributeSetTypeInfo> personAttributeSetTypeInfos = new ArrayList<PersonAttributeSetTypeInfo>();
		for(Long personAttributeSetTypeInfoId : personAttributeSetTypeInfoIds) {
			PersonAttributeSetTypeInfo personAttributeSetTypeInfo = client.fetchPersonAttributeSetType(personAttributeSetTypeInfoId);
			assertNotNull("PersonAttributeSetTypeInfo is null ", personAttributeSetTypeInfo);
			personAttributeSetTypeInfos.add(personAttributeSetTypeInfo);
		}
		return personAttributeSetTypeInfos;
	}
	
	private List<PersonNameInfo> createPersonNameInfos() {
		PersonNameInfo personNameInfo = null;
		List<PersonNameInfo>personNameInfos = new ArrayList<PersonNameInfo>();
    	for(int i = 0; i < numInstances; i++) {
    		personNameInfo = new PersonNameInfo();
    		personNameInfo.setGivenName("givenName" + i);
    		personNameInfo.setMiddleName("middleName" + i);
    		personNameInfo.setPersonTitle("personTitle" + i);
    		personNameInfo.setSurname("surname" + i);
    		personNameInfo.setNameType("nameType" + i);
    		personNameInfos.add(personNameInfo);
    	}
    	return personNameInfos;
	}
	
	private List<PersonCitizenshipInfo> createPersonCitizenshipInfos() {
		PersonCitizenshipInfo personCitizenshipInfo = null;
		List<PersonCitizenshipInfo>personCitizenshipInfos = new ArrayList<PersonCitizenshipInfo>();
    	for(int i = 0; i < numInstances; i++) {
    		personCitizenshipInfo = new PersonCitizenshipInfo();
    		personCitizenshipInfo.setCountryOfCitizenshipCode("US");
    		personCitizenshipInfos.add(personCitizenshipInfo);
    	}
    	return personCitizenshipInfos;
	}

	private List<PersonCreateInfo> createPersonInfos(List<PersonNameInfo>personNameInfos,List<PersonCitizenshipInfo>personCitizenshipInfos) {
		PersonCreateInfo personCreateInfo = null;
		List<PersonCreateInfo>personCreateInfos = new ArrayList<PersonCreateInfo>();
		Date dateTime = new Date();
    	for(int i = 0; i < numInstances; i++) {
    		personCreateInfo = new PersonCreateInfo();
    		personCreateInfo.setBirthDate(new Date(dateTime.getTime() - (i * 1000000)));
    		personCreateInfo.setGender('M');
    		personCreateInfo.getAttributes().clear();
    		personCreateInfo.setAttribute("key" + i, "value" + i); // persist NPE if no attribute
    		personCreateInfo.getName().add(personNameInfos.get(i));    		
    		personCreateInfo.setCitizenship(personCitizenshipInfos.get(i));

    		personCreateInfos.add(personCreateInfo);
    	}
    	return personCreateInfos;
	}
	
	private List<Long> persistPersonCreateInfos(List<PersonCreateInfo> personCreateInfos,List<Long> personTypeInfoIds) throws AlreadyExistsException, InvalidParameterException, 
	MissingParameterException, OperationFailedException,PermissionDeniedException {


		List<Long>personCreateInfoIds = new ArrayList<Long>();
		for(PersonCreateInfo personCreateInfo : personCreateInfos) {
			personCreateInfoIds.add(client.createPerson(personCreateInfo, personTypeInfoIds));
		}
		return personCreateInfoIds;
	}

	
	private List<PersonInfo>findAllPersonInfos(List<Long>personCreateInfoIds) throws DisabledIdentifierException, DoesNotExistException,
						InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		List<PersonInfo> personInfos = new ArrayList<PersonInfo>();
		for(Long personCreateInfoId : personCreateInfoIds) {
			PersonInfo personInfo = client.fetchFullPersonInfo(personCreateInfoId);
			assertNotNull("PersonInfo is null ", personInfo);
			personInfos.add(personInfo);
		}
		return personInfos;
	}
	
	private void updatePersonInfos(List<Long>personCreateInfoIds, List<PersonInfo> personInfos) throws DisabledIdentifierException,
	DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
	ReadOnlyException{
		boolean updated = false;
		PersonUpdateInfo personUpdateInfo = null;
		for(PersonInfo personInfo :personInfos) {
			personUpdateInfo = new PersonUpdateInfo(personInfo);
			assertEquals(he, personUpdateInfo.getGender());
			personUpdateInfo.setGender(she);
			for(Long personCreateInfoId : personCreateInfoIds) {
				updated = client.updatePerson(personCreateInfoId,  personUpdateInfo);
				assertEquals(true, updated);
			}			
		}
	}
	
	
	private List<PersonInfo>checkUpdateOfPersonInfos(List<Long>personCreateInfoIds) throws DisabledIdentifierException, DoesNotExistException,
						InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		List<PersonInfo> personInfos = new ArrayList<PersonInfo>();
		for(Long personCreateInfoId : personCreateInfoIds) {
			PersonInfo personInfo = client.fetchFullPersonInfo(personCreateInfoId);
			assertNotNull("PersonInfo is null ", personInfo);
			assertEquals(she, personInfo.getGender());
			personInfos.add(personInfo);
		}
		return personInfos;
	}
	
	private void deleteAllPersonInfos(List<Long>personCreateInfoIds) throws DisabledIdentifierException, DoesNotExistException,
						InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		boolean deleted = false;
		List<PersonInfo> personInfos = new ArrayList<PersonInfo>();
		for(Long personCreateInfoId : personCreateInfoIds) {
			deleted = client.deletePerson(personCreateInfoId);
			assertEquals(true, deleted);
		}
	}

	/*
	PersonUpdateInfo personUpdateInfo = new PersonUpdateInfo(personInfos.get(0));
	char gender = personUpdateInfo.getGender();
	assertEquals('M', gender);
	personUpdateInfo.setGender('F');
	boolean updated = client.updatePerson(personCreateInfoIds.get(0),  personUpdateInfo);
	assertEquals(true, updated);
	*/
	/*
	 * Methods with Spring's @BeforeTransaction run before entering a transaction
	 * can be used to check database is ready for the test
	 * Called before every @Test method
	@BeforeTransaction
	public verifyInitialDatabaseState() {
		
	}
	*/
	
	/*
	 * Methods with JUnit 4 @Before run when transaction has begun
	 * * Called before every @Test method
	@Before
	public void setUpDataWithinTransaction() throws Exception {
		
	}
	*/
	

/*
	
	@After
	public void tearDownWithinTransaction() {

	}
	*
	 * Methods with Spring's @AfterTransaction run after a transaction ends
	 * can be used to ensure test data was deleted from database
	 * Called after every @Test method
	 *
	@AfterTransaction 
	public void verifyFinalDatabaseState() {

	}	
*/
}
