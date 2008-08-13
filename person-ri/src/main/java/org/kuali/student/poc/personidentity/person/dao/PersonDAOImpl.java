package org.kuali.student.poc.personidentity.person.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplayDTO;


public class PersonDAOImpl implements PersonDAO {

	private EntityManager entityManager;

	@PersistenceContext(unitName="Person")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Person createPerson(Person person) {
		entityManager.persist(person);
		return person;
	}

	public PersonType createPersonType(PersonType personType) {
		entityManager.persist(personType);
		return personType;
	}

	public PersonType fetchPersonType(String id) {
		return entityManager.find(PersonType.class, id);
	}

    public boolean deletePersonType(PersonType personType) {
        entityManager.remove(personType);
        return true;
    }

	public PersonType updatePersonType(PersonType personType) {
		return entityManager.merge(personType);
	}

	public PersonAttributeSetType createPersonAttributeSetType(
			PersonAttributeSetType personAttributeSetType) {

		entityManager.persist(personAttributeSetType);
		return personAttributeSetType;
	}

	public PersonAttribute createPersonAttribute(PersonAttribute personAttribute) {

		entityManager.persist(personAttribute);
		return personAttribute;
	}

	public Person updatePerson(Person person) {
		return entityManager.merge(person);
	}

	public Person lookupPerson(String id) {
		return entityManager.find(Person.class, id);
	}


	@SuppressWarnings("unchecked")
	public List<PersonAttributeSetType> findPersonAttributeSetTypes(String nameMatch){
	    Query query = entityManager.createNamedQuery("PersonAttributeSetType.findByName");
	    query.setParameter("nameMatch", nameMatch);
	    List<PersonAttributeSetType> personAttributeSetTypes = query.getResultList();
	    return personAttributeSetTypes;
	}

	@SuppressWarnings("unchecked")
	public List<PersonType> findPersonTypes(String nameMatch) {
		Query query = entityManager.createNamedQuery("PersonType.findByName");
		query.setParameter("nameMatch", nameMatch);
		List<PersonType> personTypes = query.getResultList();
		return personTypes;
	}

	public List<Person> findPeople(PersonCriteria criteria) {
	    Query query = entityManager.createNamedQuery("Person.findByName");
	    query.setParameter("firstName", criteria.getFirstName());
	    query.setParameter("lastName", criteria.getLastName());
	    @SuppressWarnings("unchecked")
	    List<Person> people = query.getResultList();
        return people;
    }

	public List<Person> findPeople(List<String> personIdList){

	    StringBuffer personIdSb = new StringBuffer();
	    for (String str:personIdList){
	        personIdSb.append((personIdSb.length()>0 ? ",":"("));
	        personIdSb.append("'" + str + "'");
	    }
	    personIdSb.append(")");

	    System.out.println("QUERY STRING: " + personIdSb);

        Query query = entityManager.createQuery(
        "SELECT p FROM Person p where p.id IN " + personIdSb.toString());

	    @SuppressWarnings("unchecked")
	    List<Person> people = query.getResultList();

	    return people;
	}

    public List<Person> findPeopleWithAttributeSetType(String personAttributeSetTypeId, PersonCriteria criteria) {
        Query query = entityManager.createNamedQuery("Person.findByAttributeSetTypeAndCriteria");
        query.setParameter("firstName", criteria.getFirstName());
        query.setParameter("lastName", criteria.getLastName());
        query.setParameter("personAttributeSetTypeId", personAttributeSetTypeId);
        @SuppressWarnings("unchecked")
        List<Person> people = query.getResultList();
        return people;
    }

    public List<Person> findPeopleWithPersonType(String personTypeId, PersonCriteria criteria) {
        Query query = entityManager.createNamedQuery("Person.findByPersonTypeAndCriteria");
        query.setParameter("firstName", criteria.getFirstName());
        query.setParameter("lastName", criteria.getLastName());
        query.setParameter("personTypeId", personTypeId);
        @SuppressWarnings("unchecked")
        List<Person> people = query.getResultList();
        return people;
    }

    public List<PersonDisplayDTO> findPersonDisplayDTO(PersonCriteria criteria) {
    	final Query query = entityManager.createNamedQuery("PersonName.findPersonDisplayDTOByCriteria");
        query.setParameter("firstName", criteria.getFirstName());
        query.setParameter("lastName", criteria.getLastName());
        @SuppressWarnings("unchecked")
        List<PersonDisplayDTO> persons = query.getResultList();
        return persons;
    }

	public boolean deletePerson(Person person) {
		entityManager.remove(person);
		return true; // until I know better what needs to happen
	}

	public PersonAttributeType createPersonAttributeType(
			PersonAttributeType personAttributeType) {
		entityManager.persist(personAttributeType);
		return personAttributeType;
	}

	public PersonAttributeSetType fetchPersonAttributeSetType(String id) {
		return entityManager.find(PersonAttributeSetType.class, id);
	}

	public PersonAttributeType fetchPersonAttributeType(String id) {
		return entityManager.find(PersonAttributeType.class, id);
	}

	public boolean deletePersonAttribute(PersonAttribute personAttribute) {
		entityManager.remove(personAttribute);
		return true; // error if it fails, right?
	}

	public PersonName createPersonName(PersonName personName) {
		entityManager.persist(personName);
		return personName;
	}

	public boolean deletePersonName(PersonName personName) {
		entityManager.remove(personName);
		return true;
	}

	public List<PersonAttributeType> findPersonAttributeTypesFromPersonTypeIds(
			List<String> personTypeIds) {
		Set<PersonAttributeType> personAttributeTypes = new HashSet<PersonAttributeType>();
		for (String personTypeId : personTypeIds) {
			PersonType personType = fetchPersonType(personTypeId);
			for (PersonAttributeSetType personAttributeSetType : personType
					.getPersonAttributeSetTypes()) {
				personAttributeTypes.addAll(personAttributeSetType
						.getPersonAttributeTypes());
			}
		}
		return new ArrayList<PersonAttributeType>(personAttributeTypes);
	}

	@SuppressWarnings("unchecked")
	public Set<PersonAttribute> fetchAttributesByPersonAttributeSetType(
			String personId, List<String> personAttributeSetTypeKeyList) {
		Set<PersonAttribute> attributeSet = new HashSet<PersonAttribute>();
		for(String personAttributeSetTypeKey:personAttributeSetTypeKeyList){
			Query q = entityManager.createQuery("SELECT attributes FROM PersonAttribute attributes " +
					//"JOIN attributes.person per " +
					",  " +
					"IN(attributes.personAttributeType.personAttributeSetTypes) past " +
					"WHERE attributes.person.id=:personId and past.id=:personAttributeSetTypeKey ");
					q.setParameter("personId", personId);
					q.setParameter("personAttributeSetTypeKey", personAttributeSetTypeKey);
			attributeSet.addAll(q.getResultList());
		}
		return attributeSet;
	}

	@SuppressWarnings("unchecked")
	public Set<PersonAttribute> fetchAttributesByPersonType(String personId,
			String personTypeKey) {
		Query q = entityManager.createQuery("SELECT attributes FROM PersonAttribute attributes " +
				", " +
				"IN(attributes.personAttributeType.personAttributeSetTypes) past, IN(past.personTypes) pt " +
				"WHERE attributes.person.id=:personId and pt.id=:personTypeKey ");
				q.setParameter("personId", personId);
				q.setParameter("personTypeKey", personTypeKey);
		return new HashSet<PersonAttribute>(q.getResultList());
	}

}
