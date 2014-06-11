package org.kuali.student.enrollment.class2.courseoffering.dao;

import org.kuali.student.enrollment.class2.courseoffering.model.CodeGeneratorLocksEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 12/7/12
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class CodeGeneratorLocksDao extends GenericEntityDao<CodeGeneratorLocksEntity> implements CodeGeneratorLocksDaoApi {

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void createLock (String code, String key, String namespace){
        CodeGeneratorLocksEntity entity = new CodeGeneratorLocksEntity();
        entity.setCode(code);
        entity.setUniqueKey(key);
        entity.setNamespace(namespace);
        em.persist(entity);
    }
    @Transactional
    public void removeLock (String code, String key, String namespace){
        em.createQuery("delete from CodeGeneratorLocksEntity a where a.code=:code and a.uniqueKey=:uniqueKey and a.namespace=:namespace")
                .setParameter("code", code).setParameter("uniqueKey", key).setParameter("namespace", namespace).executeUpdate();
    }
    @Transactional
    public void cleanLocks (String key, String pendingKey, String namespace){

        em.createQuery("delete from CodeGeneratorLocksEntity a where a.uniqueKey=:key and a.namespace=:namespace and 0 = (Select count(*) from CodeGeneratorLocksEntity b where b.uniqueKey=:pendingKey and b.namespace=:namespace) ")
                .setParameter("key", key).setParameter("namespace", namespace).setParameter("pendingKey", pendingKey).executeUpdate();
    }
}
