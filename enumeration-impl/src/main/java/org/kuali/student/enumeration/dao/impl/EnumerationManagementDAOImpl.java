package org.kuali.student.enumeration.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.kuali.student.enumeration.dao.EnumerationManagementDAO;
import org.kuali.student.enumeration.entity.EnumeratedValue;

import org.springframework.stereotype.Repository;

@Repository
public class EnumerationManagementDAOImpl implements EnumerationManagementDAO {
    private EntityManager entityManager;

    @PersistenceContext(name = "EnumerationManagement")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
/*
    public EnumeratedValue createEnumeratedValue(EnumeratedValue value) {
        this.entityManager.persist(value);
        return value;
    }

    public EnumeratedValueContext createEnumeratedValueContext(EnumeratedValueContext context) {
        this.entityManager.persist(context);
        return context;
    }

    public Enumerations createEnumerations(Enumerations entry) {
        this.entityManager.persist(entry);
        return entry;
    }

    public boolean deleteEnumeratedValue(EnumeratedValue value) {
        entityManager.remove(value);
        return true;
    }

    public boolean deleteEnumeratedValueContext(EnumeratedValueContext context) {
        entityManager.remove(context);
        return true;
    }

    public boolean deleteEnumerations(Enumerations entry) {
        entityManager.remove(entry);
        return true;
    }

    public EnumeratedValue lookupEnumeratedValue(String id) {
        return entityManager.find(EnumeratedValue.class, id);
    }

    public EnumeratedValueContext lookupEnumeratedValueContext(String id) {
        return entityManager.find(EnumeratedValueContext.class, id);
    }

    public Enumerations lookupEnumerations(String id) {
        return entityManager.find(Enumerations.class, id);
    }

    public EnumeratedValue updateEnumeratedValue(EnumeratedValue value) {
        this.entityManager.merge(value);
        return value;
    }

    public EnumeratedValueContext updateEnumeratedValueContext(EnumeratedValueContext context) {
        this.entityManager.merge(context);
        return context;
    }

    public Enumerations updateEnumerations(Enumerations entry) {
        this.entityManager.merge(entry);
        return entry;
    }

    public List<EnumeratedValueContext> getEnumeratedValueContexts(String enumeratedValueId) {
        try {
            entityManager.createNamedQuery("select e from EnumeratedValueContext " + "e where e.enumeratedValueId = :enumeratedValueId ").setParameter("enumeratedValueId", enumeratedValueId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return null;
    }

    public List<EnumeratedValue> getEnumeratedValues(String enumerationId) {
        try {
            entityManager.createNamedQuery("select e from EnumeratedValue " + "e where e.enumerationId = :enumerationId ").setParameter("enumerationId", enumerationId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return null;
    }
*/
}
