package org.kuali.student.enrollment.class2.courseoffering.dao;

import org.kuali.student.enrollment.class2.courseoffering.model.CodeGeneratorLocksEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
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
        //em.flush();
    }

}
