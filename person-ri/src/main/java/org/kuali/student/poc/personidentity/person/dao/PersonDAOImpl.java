package org.kuali.student.poc.personidentity.person.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
	public PersonAttribute createPersonAttribute(
			PersonAttribute personAttribute) {
		
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
	public List<PersonType> findPersonTypes(String nameMatch) {
		Query query = entityManager.createNamedQuery("PersonType.findByName");
		query.setParameter("nameMatch", nameMatch);
		List<PersonType> personTypes = query.getResultList();
		return personTypes;
	}

    @Override
    public boolean deletePerson(Person person) {
        entityManager.remove(person);
        return true; //until I know better what needs to happen
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
        return true; //error if it fails, right?
    }

	@Override
	public Person fetchPersonByType(Long personId, Long personTypeId) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery("SELECT person FROM Person person, IN(person.personTypes) personType WHERE person.id = :personId AND personType.id = :personTypeId");
		query.setParameter("personId", personId);
		query.setParameter("personTypeId", personTypeId);
		return (Person) query.getSingleResult();
	}

}
