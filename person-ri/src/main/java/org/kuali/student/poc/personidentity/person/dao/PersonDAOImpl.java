package org.kuali.student.poc.personidentity.person.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria;

//@Transactional
public class PersonDAOImpl implements PersonDAO {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Person createPerson(Person person) {
		entityManager.persist(person);
		return person;
	}

	@Override
	public PersonType createPersonType(PersonType personType) {
		entityManager.persist(personType);
		return personType;
	}

	@Override
	public PersonType fetchPersonType(Long id) {
		return entityManager.find(PersonType.class, id);
	}
	
    @Override
    public boolean deletePersonType(PersonType personType) {
        entityManager.remove(personType);
        return true;
    }

	@Override
	public PersonType updatePersonType(PersonType personType) {
		return entityManager.merge(personType);
	}

	@Override
	public PersonAttributeSetType createPersonAttributeSetType(
			PersonAttributeSetType personAttributeSetType) {

		entityManager.persist(personAttributeSetType);
		return personAttributeSetType;
	}

	@Override
	public PersonAttribute createPersonAttribute(PersonAttribute personAttribute) {

		entityManager.persist(personAttribute);
		return personAttribute;
	}

	@Override
	public Person updatePerson(Person person) {
		return entityManager.merge(person);
	}

	@Override
	public Person lookupPerson(long id) {
		return entityManager.find(Person.class, id);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonAttributeSetType> findPersonAttributeSetTypes(String nameMatch){
	    Query query = entityManager.createNamedQuery("PersonAttributeSetType.findByName");
	    query.setParameter("nameMatch", nameMatch);
	    List<PersonAttributeSetType> personAttributeSetTypes = query.getResultList();
	    return personAttributeSetTypes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonType> findPersonTypes(String nameMatch) {
		Query query = entityManager.createNamedQuery("PersonType.findByName");
		query.setParameter("nameMatch", nameMatch);
		List<PersonType> personTypes = query.getResultList();
		return personTypes;
	}

	@Override
    public List<Person> findPeople(PersonCriteria criteria) {
	    Query query = entityManager.createNamedQuery("Person.findByName");
	    query.setParameter("firstName", criteria.getFirstName());
	    query.setParameter("lastName", criteria.getLastName());
	    @SuppressWarnings("unchecked")
	    List<Person> people = query.getResultList();
        return people;
    }

    @Override
    public List<Person> findPeopleWithAttributeSetType(Long personAttributeSetTypeId, PersonCriteria criteria) {
        Query query = entityManager.createNamedQuery("Person.findByAttributeSetAndCriteria");
        query.setParameter("firstName", criteria.getFirstName());
        query.setParameter("lastName", criteria.getLastName());
        query.setParameter("personAttributeSetTypeId", personAttributeSetTypeId);
        @SuppressWarnings("unchecked")
        List<Person> people = query.getResultList();
        return people;
    }

    @Override
	public boolean deletePerson(Person person) {
		entityManager.remove(person);
		return true; // until I know better what needs to happen
	}

	@Override
	public PersonAttributeType createPersonAttributeType(
			PersonAttributeType personAttributeType) {
		entityManager.persist(personAttributeType);
		return personAttributeType;
	}

	@Override
	public PersonAttributeSetType fetchPersonAttributeSetType(Long id) {
		return entityManager.find(PersonAttributeSetType.class, id);
	}

	@Override
	public PersonAttributeType fetchPersonAttributeType(Long id) {
		return entityManager.find(PersonAttributeType.class, id);
	}

	@Override
	public boolean deletePersonAttribute(PersonAttribute personAttribute) {
		entityManager.remove(personAttribute);
		return true; // error if it fails, right?
	}

	@Override
	public PersonName createPersonName(PersonName personName) {
		entityManager.persist(personName);
		return personName;
	}

	@Override
	public boolean deletePersonName(PersonName personName) {
		entityManager.remove(personName);
		return true;
	}

	@Override
	public List<PersonAttributeType> findPersonAttributeTypesFromPersonTypeIds(
			List<Long> personTypeIds) {
		Set<PersonAttributeType> personAttributeTypes = new HashSet<PersonAttributeType>();
		for (Long personTypeId : personTypeIds) {
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
	@Override
	public Set<PersonAttribute> fetchAttributesByPersonAttributeSetType(
			Long personId, List<Long> personAttributeSetTypeKeyList) {
		Set<PersonAttribute> attributeSet = new HashSet<PersonAttribute>(); 
		for(Long personAttributeSetTypeKey:personAttributeSetTypeKeyList){
			Query q = entityManager.createQuery("SELECT attributes FROM PersonAttribute attributes " +
					"JOIN attributes.person per " +
					"JOIN attributes.personAttributeType pat, " +
					"IN(pat.personAttributeSetTypes) past " +
					"WHERE per.id=:personId and past.id=:personAttributeSetTypeKey ");
					q.setParameter("personId", personId);
					q.setParameter("personAttributeSetTypeKey", personAttributeSetTypeKey);
			attributeSet.addAll(q.getResultList());
		}
		return attributeSet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<PersonAttribute> fetchAttributesByPersonType(Long personId,
			Long personTypeKey) {
		Query q = entityManager.createQuery("SELECT attributes FROM PersonAttribute attributes " +
				"JOIN attributes.person per " +
				"JOIN attributes.personAttributeType pat, " +
				"IN(pat.personAttributeSetTypes) past, IN(past.personTypes) pt " +
				"WHERE per.id=:personId and pt.id=:personTypeKey ");
				q.setParameter("personId", personId);
				q.setParameter("personTypeKey", personTypeKey);
		return new HashSet<PersonAttribute>(q.getResultList());
	}

}
