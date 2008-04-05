package org.kuali.student.poc.dummy.dao;

import org.kuali.student.poc.dummy.Dummy;
import org.kuali.student.poc.dummy.DummyAttribute;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class DummyDAOImpl implements DummyDAO {

	private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
	@Override
	public Dummy createDummy(Dummy dummy) {
		entityManager.persist(dummy);
		return dummy;
	}

	@Override
	public DummyAttribute createDummyAttribute(
			DummyAttribute dummyAttribute) {
		
		entityManager.persist(dummyAttribute);
		return dummyAttribute;
	}

	@Override
	public Dummy updateDummy(Dummy dummy) {
		return entityManager.merge(dummy);
	}

	@Override
	public Dummy lookupDummy(long id) {
		return entityManager.find(Dummy.class, id);
	}

	@Override
    public boolean deleteDummy(Dummy dummy) {
        entityManager.remove(dummy);
        return true; //until I know better what needs to happen
    }

	@Override
    public boolean deleteDummyAttribute(DummyAttribute dummyAttribute) {
        entityManager.remove(dummyAttribute);
        return true; //error if it fails, right?
    }

	
}
