package org.kuali.student.enumeration.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.enumeration.dao.EnumerationManagementDAO;
import org.kuali.student.enumeration.entity.EnumeratedValueEntity;
import org.kuali.student.enumeration.entity.EnumerationMetaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class EnumerationManagementDAOImpl implements EnumerationManagementDAO {
    private EntityManager entityManager;

    @PersistenceContext(name = "EnumerationManagement")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<EnumerationMetaEntity> findEnumerationMetas() {
        Query query = entityManager.createQuery("SELECT e FROM EnumerationMeta e");
        return (List<EnumerationMetaEntity>) query.getResultList();
    }

    public EnumerationMetaEntity fetchEnumerationMeta(String enumerationKey) {
        Query query = entityManager.createQuery("SELECT e FROM EnumerationMeta e where e.enumerationKey = :key");
        query.setParameter("key", enumerationKey);

        Object obj = query.getResultList().get(0);

        return (EnumerationMetaEntity) obj;
    }

    public List<EnumeratedValueEntity> fetchEnumeration(String enumerationKey, String enumContextKey, String contextValue, Date contextDate) {
        Query query = entityManager.createQuery(
                "select e from EnumeratedValue e, Context c " +
                "where e.id = c.enumerationId and " +
                "e.effectiveDate < :contextDate and " +
                "e.expirationDate > :contextDate and " +
                "c.value = :contextValue and " +
                "c.key = :enumContextKey and " +
                "e.enumerationKey = :enumerationKey ");

        query.setParameter("enumerationKey", enumerationKey);
        query.setParameter("enumContextKey", enumContextKey);        
        query.setParameter("contextValue", contextValue);
        query.setParameter("contextDate", contextDate);
                
        return (List<EnumeratedValueEntity>)query.getResultList();

    }

    public EnumeratedValueEntity addEnumeratedValue(String enumerationKey, EnumeratedValueEntity value) {
        entityManager.persist(value);
        value.setEnumerationKey(enumerationKey);

        return value;
    }

    public EnumeratedValueEntity updateEnumeratedValue(String enumerationKey, String code, EnumeratedValueEntity value) {
        Query query = entityManager.createQuery("update EnumeratedValue e set e.value = :value where e.enumerationKey = :key and e.code = :code");
        query.setParameter("key", enumerationKey);
        query.setParameter("code", code);
        query.setParameter("value", value);
        query.executeUpdate();

        query = entityManager.createQuery("SELECT e FROM EnumeratedValue e where e.enumerationKey = :key and e.code = :code");
        query.setParameter("key", enumerationKey);
        query.setParameter("code", code);
        Object obj = query.getResultList().get(0);

        return (EnumeratedValueEntity) obj;
    }

    public void removeEnumeratedValue(String enumerationKey, String code) {
        Query query = entityManager.createQuery("delete from EnumeratedValue e where e.enumerationKey = :key and e.code = :code");
        query.setParameter("key", enumerationKey);
        query.setParameter("code", code);

        query.executeUpdate();
    }
}
