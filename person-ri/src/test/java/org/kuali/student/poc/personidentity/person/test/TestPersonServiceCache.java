package org.kuali.student.poc.personidentity.person.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerListener;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CachePeer;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.common.util.EhCacheHelper;
import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.poc.common.ws.exceptions.ReadOnlyException;
import org.kuali.student.poc.personidentity.person.service.PersonServiceCacheDelegate;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCitizenshipInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCreateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonNameInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonUpdateInfo;

@Daos({@Dao("org.kuali.student.poc.personidentity.person.dao.PersonDAOImpl")})
@PersistenceFileLocation("classpath:META-INF/person-persistence.xml")
public class TestPersonServiceCache extends AbstractServiceTest {
	
    @Client(value="org.kuali.student.poc.personidentity.person.service.PersonServiceCacheDelegate", port="9191")
    public PersonService client;
	public PersonService clientCacheDelegate;
	private int numInstances = 5;
	private char he = 'M';
	private char she = 'F';
	private final String personInfoCacheName = "PersonInfo";
	@Before
	public void setup() {
		clientCacheDelegate = new PersonServiceCacheDelegate(client, "ehcache2.xml");
	}
	@Test
	public void checkPersonInfoIsDistributedCache() throws RemoteException{
		EhCacheHelper ehCacheHelper;
		ehCacheHelper = new EhCacheHelper();
		String[] cacheNames = ehCacheHelper.getCacheNames();
		assertNotNull("CACHNAMES are null ", cacheNames);
		CacheManager cacheManager = new CacheManager();
		CacheManagerPeerProvider cacheManagerPeerProvider = cacheManager.getCachePeerProvider();
		assertNotNull("CacheManagerPeerProvider is null check ehcache.xml is in the classpath and cacheManagerPeerProviderFactory is set",
				cacheManagerPeerProvider);
		CacheManagerPeerListener cachePeerListener = cacheManager.getCachePeerListener();
		assertNotNull("No CacheManagerPeerListener is null check ehcache.xml sets cacheManagerPeerListenerFactory",
				cachePeerListener);
		List<CachePeer> cachePeers = cachePeerListener.getBoundCachePeers();
		assertNotNull("No distributed cachePeers check cache configuration in ehcache.xml ", cachePeers);
		for(CachePeer cachePeer : cachePeers) {
			if(cachePeer.getName().equals(personInfoCacheName)){
				return;
			}
		}
		/* Debug
		CacheConfiguration cacheConfiguration = null;
		for(int i = 0; i < cacheNames.length; i++) {
			System.out.println("CACHE NAME :" + cacheNames[i]);
			Cache cache = cacheManager.getCache(cacheNames[i]);
			if(cache != null) {
				cacheConfiguration = cache.getCacheConfiguration();
				if(cacheConfiguration != null) {
					System.out.println("cacheConfiguration " + cacheConfiguration.toString());
				} else {
					System.out.println("cacheConfiguration is NULL for" + cacheNames[i]);
				}
			}else {
				System.out.println("Cache is NULL! " + cacheNames[i]);
			}

		}
		*/
	}
	
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
		List<String>personAttSetTypeInfoIds = persistPersonAttSetTypeInfos(personAttributeSetTypeInfos);
		assertEquals(personAttSetTypeInfoIds.size(), numInstances);
		personAttributeSetTypeInfos = findAllPersonAttributeSetTypeInfos(personAttSetTypeInfoIds); 
		assertEquals(personAttributeSetTypeInfos.size(), numInstances);
		
		List<PersonTypeInfo> personTypeInfos = createPersonTypeInfos(personAttributeSetTypeInfos);
		assertEquals(personTypeInfos.size(), numInstances);
		List<String> personTypeInfoIds = persistPersonTypeInfos(personTypeInfos);
		assertEquals(personTypeInfoIds.size(), numInstances);
		personTypeInfos = findAllPersonTypeInfos(personTypeInfoIds);
		assertEquals(personTypeInfos.size(), numInstances);
		
		List<PersonNameInfo>personNameInfos = createPersonNameInfos();
		List<PersonCitizenshipInfo>personCitizenshipInfos = createPersonCitizenshipInfos();
		List<PersonCreateInfo>personCreateInfos = createPersonInfos(personNameInfos,personCitizenshipInfos);
		List<String>personCreateInfoIds = persistPersonCreateInfos(personCreateInfos,personTypeInfoIds);
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
	
	private List<String> persistPersonTypeInfos(List<PersonTypeInfo> personTypeInfos) throws AlreadyExistsException, InvalidParameterException, 
									MissingParameterException, OperationFailedException,PermissionDeniedException {
		
		
		List<String>personTypeInfoIds = new ArrayList<String>();
		for(PersonTypeInfo personTypeInfo : personTypeInfos) {
			personTypeInfoIds.add(client.createPersonTypeInfo(personTypeInfo));
		}
		return personTypeInfoIds;
	}
		
	private List<PersonTypeInfo>findAllPersonTypeInfos(List<String>personTypeInfoIds) throws DoesNotExistException,
										InvalidParameterException, MissingParameterException, OperationFailedException {
		List<PersonTypeInfo> personTypeInfos = new ArrayList<PersonTypeInfo>();
		for(String personTypeInfoId : personTypeInfoIds) {
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

	private List<String> persistPersonAttributeTypeInfos(List<PersonAttributeTypeInfo> personAttTypeInfos) throws AlreadyExistsException, InvalidParameterException, 
	MissingParameterException, OperationFailedException,PermissionDeniedException {

		List<String>personAttributeTypeInfoIds = new ArrayList<String>();
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
	
	private List<String> persistPersonAttSetTypeInfos(List<PersonAttributeSetTypeInfo>personAttributeSetTypeInfos ) throws AlreadyExistsException, InvalidParameterException, 
									MissingParameterException, OperationFailedException,PermissionDeniedException {
		
		List<String>personAttributeSetTypeInfoIds = new ArrayList<String>();
		for(PersonAttributeSetTypeInfo personAttributeSetTypeInfo : personAttributeSetTypeInfos) {
			personAttributeSetTypeInfoIds.add(client.createPersonAttributeSetType(personAttributeSetTypeInfo));
		}
		return personAttributeSetTypeInfoIds;
	}

	
	private List<PersonAttributeSetTypeInfo>findAllPersonAttributeSetTypeInfos(List<String>personAttributeSetTypeInfoIds) throws DoesNotExistException,
										InvalidParameterException, MissingParameterException, OperationFailedException {
		List<PersonAttributeSetTypeInfo> personAttributeSetTypeInfos = new ArrayList<PersonAttributeSetTypeInfo>();
		for(String personAttributeSetTypeInfoId : personAttributeSetTypeInfoIds) {
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
	
	private List<String> persistPersonCreateInfos(List<PersonCreateInfo> personCreateInfos,List<String> personTypeInfoIds) throws AlreadyExistsException, InvalidParameterException, 
	MissingParameterException, OperationFailedException,PermissionDeniedException {


		List<String>personCreateInfoIds = new ArrayList<String>();
		for(PersonCreateInfo personCreateInfo : personCreateInfos) {
			personCreateInfoIds.add(client.createPerson(personCreateInfo, personTypeInfoIds));
		}
		return personCreateInfoIds;
	}

	
	private List<PersonInfo>findAllPersonInfos(List<String>personCreateInfoIds) throws DisabledIdentifierException, DoesNotExistException,
						InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		List<PersonInfo> personInfos = new ArrayList<PersonInfo>();
		for(String personCreateInfoId : personCreateInfoIds) {
			PersonInfo personInfo = client.fetchFullPersonInfo(personCreateInfoId);
			assertNotNull("PersonInfo is null ", personInfo);
			personInfos.add(personInfo);
		}
		return personInfos;
	}
	
	private void updatePersonInfos(List<String>personCreateInfoIds, List<PersonInfo> personInfos) throws DisabledIdentifierException,
	DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
	ReadOnlyException{
		boolean updated = false;
		PersonUpdateInfo personUpdateInfo = null;
		for(PersonInfo personInfo :personInfos) {
			personUpdateInfo = new PersonUpdateInfo(personInfo);
			assertEquals(he, personUpdateInfo.getGender());
			personUpdateInfo.setGender(she);
			for(String personCreateInfoId : personCreateInfoIds) {
				updated = client.updatePerson(personCreateInfoId,  personUpdateInfo);
				assertEquals(true, updated);
			}			
		}
	}
	
	
	private List<PersonInfo>checkUpdateOfPersonInfos(List<String>personCreateInfoIds) throws DisabledIdentifierException, DoesNotExistException,
						InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		List<PersonInfo> personInfos = new ArrayList<PersonInfo>();
		for(String personCreateInfoId : personCreateInfoIds) {
			PersonInfo personInfo = client.fetchFullPersonInfo(personCreateInfoId);
			assertNotNull("PersonInfo is null ", personInfo);
			assertEquals(she, personInfo.getGender());
			personInfos.add(personInfo);
		}
		return personInfos;
	}
	
	private void deleteAllPersonInfos(List<String>personCreateInfoIds) throws DisabledIdentifierException, DoesNotExistException,
						InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		boolean deleted = false;
		List<PersonInfo> personInfos = new ArrayList<PersonInfo>();
		for(String personCreateInfoId : personCreateInfoIds) {
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
