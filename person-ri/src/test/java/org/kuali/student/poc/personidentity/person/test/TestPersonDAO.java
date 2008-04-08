package org.kuali.student.poc.personidentity.person.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.personidentity.person.dao.Person;
import org.kuali.student.poc.personidentity.person.dao.PersonAttribute;
import org.kuali.student.poc.personidentity.person.dao.PersonAttributeSetType;
import org.kuali.student.poc.personidentity.person.dao.PersonAttributeType;
import org.kuali.student.poc.personidentity.person.dao.PersonDAO;
import org.kuali.student.poc.personidentity.person.dao.PersonName;
import org.kuali.student.poc.personidentity.person.dao.PersonType;
import org.kuali.student.poc.personidentity.person.dao.PersonalInformation;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:META-INF/default-dao-context-test.xml"})
@Transactional
@TransactionConfiguration(transactionManager="JtaTxManager")
public class TestPersonDAO{

	@Autowired
    private PersonDAO personDAO;
	
	@PersistenceContext
	private EntityManager em;
	
	private long studentTypeId;

	private long personId;

	private Long humanTypeId;
	
	@Before
    public void onSetUpInTransaction() throws Exception {
	    PersonType studentType = new PersonType("student");
		PersonAttributeSetType directorySetDef;
		directorySetDef = new PersonAttributeSetType("directory", Boolean.FALSE);
		PersonAttributeType personAttributeType;
		personAttributeType = new PersonAttributeType("year", "String");
		directorySetDef.getPersonAttributeTypes().add(personAttributeType);
		personAttributeType = new PersonAttributeType("email", "email");
		directorySetDef.getPersonAttributeTypes().add(personAttributeType);
		personAttributeType = new PersonAttributeType("major", "String");
		directorySetDef.getPersonAttributeTypes().add(personAttributeType);
		studentType.getPersonAttributeSetTypes().add(directorySetDef);
		em.persist(studentType);
		studentTypeId = studentType.getId();
		
		PersonType humanType = new PersonType("human");
		PersonAttributeSetType bioSetDef;
		bioSetDef = new PersonAttributeSetType("bio", Boolean.FALSE);
		personAttributeType = new PersonAttributeType("height", "INT");
		bioSetDef.getPersonAttributeTypes().add(personAttributeType);
		personAttributeType = new PersonAttributeType("weight", "INT");
		bioSetDef.getPersonAttributeTypes().add(personAttributeType);
		personAttributeType = new PersonAttributeType("hair", "STRING");
		bioSetDef.getPersonAttributeTypes().add(personAttributeType);
		humanType.getPersonAttributeSetTypes().add(bioSetDef);
		em.persist(humanType);
		humanTypeId = humanType.getId();
		
		
		PersonType personType = personDAO.fetchPersonType(studentTypeId);
		Person person = new Person();
        person.getPersonTypes().add(personType);
		
		PersonName personName = new PersonName("Joe", "Student");
		personName.setNameType("Official");
		personName.setPerson(person);
		person.getPersonNames().add(personName);
		
		//quick hack
		int emailCount = 0;
		
		PersonAttribute personAttribute;
		for(PersonAttributeSetType setDef : personType.getPersonAttributeSetTypes()) {
			for(PersonAttributeType def : setDef.getPersonAttributeTypes()) {
				
				personAttribute = new PersonAttribute();
				personAttribute.setValue(setDef.getName() + " : " + def.getName() + " : value");
				personAttribute.setPerson(person);
				personAttribute.setPersonAttributeType(def);
				
				if(def.getName().equals("email")) {
					if(emailCount == 0) {
						emailCount++;
						person.getAttributes().add(personAttribute);
					}
				}
				else {
					person.getAttributes().add(personAttribute);
				}
			}
		}
		
		em.persist(person);
		personId=person.getId();
	}
	
	@Test
	public void testCreatePersonType()	{
		PersonType studentType = new PersonType("new_student");
		
		PersonAttributeSetType directorySetDef;
		directorySetDef = new PersonAttributeSetType("directory", Boolean.FALSE);
		
		PersonAttributeType personAttributeType;
		
		personAttributeType = new PersonAttributeType("year", "String");
		directorySetDef.getPersonAttributeTypes().add(personAttributeType);
		
		personAttributeType = new PersonAttributeType("email", "email");
		directorySetDef.getPersonAttributeTypes().add(personAttributeType);
		
		personAttributeType = new PersonAttributeType("major", "String");
		directorySetDef.getPersonAttributeTypes().add(personAttributeType);
		
		studentType.getPersonAttributeSetTypes().add(directorySetDef);
		studentType = personDAO.createPersonType(studentType);
		
		assertTrue(studentType.getId() != null);
	}

	@Test
	public void testUpdatePersonType() {
		PersonAttributeSetType contactSetDef;
		contactSetDef = new PersonAttributeSetType("contact", Boolean.FALSE);
		
		PersonAttributeType phoneDef = new PersonAttributeType("Phone Number", "String");
		contactSetDef.getPersonAttributeTypes().add(phoneDef);
		
		PersonType personType = personDAO.fetchPersonType(studentTypeId);
		for(PersonAttributeSetType pasd : personType.getPersonAttributeSetTypes()) {
			for(PersonAttributeType pa : pasd.getPersonAttributeTypes()) {
				if(pa.getName().equals("email")) {
					contactSetDef.getPersonAttributeTypes().add(pa);
				}
			}
		}
		personType.getPersonAttributeSetTypes().add(contactSetDef);
		
		personType = personDAO.updatePersonType(personType);
		assertTrue(personType.getPersonAttributeSetTypes().size() == 2);
	}
	
	@Test
	public void testCreatePerson() {
		PersonType personType = personDAO.fetchPersonType(studentTypeId);
		Person person = new Person();
		person.getPersonTypes().add(personType);
		
        PersonName personName = new PersonName("Joe", "Student");
        personName.setPerson(person);
        personName.setNameType("Official");
        person.getPersonNames().add(personName);

        personName = new PersonName("Joseph", "Student");
        personName.setPerson(person);
        personName.setNameType("Diploma");
        person.getPersonNames().add(personName);
		
        //quick hack
		int emailCount = 0;
		
		PersonAttribute personAttribute;
		for(PersonAttributeSetType setDef : personType.getPersonAttributeSetTypes()) {
			for(PersonAttributeType def : setDef.getPersonAttributeTypes()) {
				
				personAttribute = new PersonAttribute();
				personAttribute.setValue(setDef.getName() + " : " + def.getName() + " : value");
				personAttribute.setPerson(person);
				personAttribute.setPersonAttributeType(def);
				
				if(def.getName().equals("email")) {
					if(emailCount == 0) {
						emailCount++;
						person.getAttributes().add(personAttribute);
					}
				}
				else {
					person.getAttributes().add(personAttribute);
				}
			}
		}
		
		person = personDAO.createPerson(person);
		assertTrue(person.getId() != null);
				        
        person = personDAO.lookupPerson(person.getId());
        assertEquals(2,person.getPersonNames().size());        
		       
		// test for the join table link - we know there is only one person
		// this doesn't work, will succeed even if join table not populated
		personType = personDAO.fetchPersonType(studentTypeId);
		for(Person p : personType.getPeople()) {
			assertTrue(p.getId().equals(person.getId()));
		}
		
		//testing findPeople
		PersonCriteria criteria = new PersonCriteria();
		criteria.setFirstName("Joseph");
		criteria.setLastName("Student");
		List<Person> people = personDAO.findPeople(criteria);
		assertEquals(1,people.size());
		criteria.setFirstName("%");
		people = personDAO.findPeople(criteria);
		assertEquals(2,people.size()); //the one created in @Before and the one created here, but not counting the two nicknames as separate people 
	}
	
	@Test
	public void testUpdatePerson() {
		Person person = personDAO.lookupPerson(personId);
		
		for(PersonAttribute attr : person.getAttributes()) {
			if(attr.getPersonAttributeType().getName().equals("email")) {
				attr.setValue("joe@student.com");
			}
		}
		
		personDAO.updatePerson(person);
	}
	
	@Test
	public void testFindPersonTypes() {
		PersonType studentsType = new PersonType("students");
		personDAO.createPersonType(studentsType);
		
		List<PersonType> personTypeList = personDAO.findPersonTypes("stu%");
		assertTrue(personTypeList.size() == 2);
		for(PersonType personType : personTypeList) {
			assertTrue(personType.getName().startsWith("stu"));
		}
		
		personTypeList = personDAO.findPersonTypes("student");
		for(PersonType personType : personTypeList) {
			assertTrue(personType.getName().equals("student"));
		}
		
		personTypeList = personDAO.findPersonTypes("%");
		assertTrue(personTypeList.size() > 0);
	}
	
	@Test
	public void testDeletePerson() {
	    Person person = personDAO.lookupPerson(personId);
	    assertTrue(personDAO.deletePerson(person));
	    assertNull(personDAO.lookupPerson(personId));
	}
	
	@Test
	public void testFetchAttributesByPersonType(){
		//Create a person with two types, human and student
		Person person = new Person();
		
		PersonalInformation personalInfo = new PersonalInformation();
		
		personalInfo.setGender('M');
		personalInfo.setDateOfBirth(new Date());
		personalInfo.setPerson(person);
		person.setPersonalInformation(personalInfo);
		
		PersonName personName = new PersonName("Joe", "Student");
	    personName.setPerson(person);
	    personName.setNameType("Official");
        person.getPersonNames().add(personName);
		
		person.getPersonTypes().add(personDAO.fetchPersonType(humanTypeId));
		person.getPersonTypes().add(personDAO.fetchPersonType(studentTypeId));
		for(PersonType personType : person.getPersonTypes()) {
			for(PersonAttributeSetType setDef : personType.getPersonAttributeSetTypes()) {
				for(PersonAttributeType def : setDef.getPersonAttributeTypes()) {
					PersonAttribute personAttribute = new PersonAttribute();
					personAttribute.setPerson(person);
					personAttribute.setPersonAttributeType(def);
					if(def.getName().equals("year")){
						personAttribute.setValue("1994");
					}else if(def.getName().equals("hair")){
						personAttribute.setValue("Brown");
					}else{
						personAttribute.setValue(setDef.getName() + " : " + def.getName() + " : value");
					}
					person.getAttributes().add(personAttribute);
				}
				
			}
		}
		//save the person
		long createdId = personDAO.createPerson(person).getId();
		
		//fetch just one of those types
		Set<PersonAttribute> attributes = personDAO.fetchAttributesByPersonType(createdId, humanTypeId);
		
		//ThHis is not filtered yet
		assertEquals(6,person.getAttributes().size());
		assertEquals(3,attributes.size());
	}
	
    @Test
    public void testFindPeopleWithAttributeSetType(){
        //Create a person with two types, human and student
        Person person = new Person();
        
        PersonalInformation personalInfo = new PersonalInformation();
        
        personalInfo.setGender('M');
        personalInfo.setDateOfBirth(new Date());
        personalInfo.setPerson(person);
        person.setPersonalInformation(personalInfo);
        
        PersonName personName = new PersonName("Lloyd", "Christmas");
        personName.setPerson(person);
        personName.setNameType("Official");
        person.getPersonNames().add(personName);
        
        person.getPersonTypes().add(personDAO.fetchPersonType(humanTypeId));
        person.getPersonTypes().add(personDAO.fetchPersonType(studentTypeId));
        for(PersonType personType : person.getPersonTypes()) {
            for(PersonAttributeSetType setDef : personType.getPersonAttributeSetTypes()) {
                for(PersonAttributeType def : setDef.getPersonAttributeTypes()) {
                    PersonAttribute personAttribute = new PersonAttribute();
                    personAttribute.setPerson(person);
                    personAttribute.setPersonAttributeType(def);
                    if(def.getName().equals("year")){
                        personAttribute.setValue("1994");
                    }else if(def.getName().equals("hair")){
                        personAttribute.setValue("Brown");
                    }else{
                        personAttribute.setValue(setDef.getName() + " : " + def.getName() + " : value");
                    }
                    person.getAttributes().add(personAttribute);
                }
                
            }
        }
        //save the person
        long createdId = personDAO.createPerson(person).getId();
        
        List<PersonAttributeSetType> pasts = personDAO.findPersonAttributeSetTypes("directory");
        assertEquals(1,pasts.size());
        PersonCriteria criteria = new PersonCriteria();
        criteria.setFirstName("%");
        criteria.setLastName("%");
        List<Person> people = personDAO.findPeopleWithAttributeSetType(pasts.get(0).getId(), criteria);
        assertEquals(2,people.size());
        criteria.setLastName("Christmas");
        people = personDAO.findPeopleWithAttributeSetType(pasts.get(0).getId(), criteria);
        assertEquals(1,people.size());
        
        //and now with personType
        List<PersonType> personTypes = personDAO.findPersonTypes("student");
        assertEquals(1,personTypes.size());
        criteria.setLastName("%");
        people = personDAO.findPeopleWithPersonType(personTypes.get(0).getId(), criteria);
        assertEquals(2,people.size());
        criteria.setLastName("Christmas");
        people = personDAO.findPeopleWithPersonType(personTypes.get(0).getId(), criteria);
        assertEquals(1,people.size());
    }
}
