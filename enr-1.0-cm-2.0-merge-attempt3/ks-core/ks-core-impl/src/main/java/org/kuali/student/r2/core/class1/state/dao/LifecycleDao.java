package org.kuali.student.r2.core.class1.state.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.state.model.LifecycleEntity;

import java.util.List;

public class LifecycleDao extends GenericEntityDao<LifecycleEntity> {
    public LifecycleEntity getProcessByKey(String processKey) {
        return find(processKey);
    }

    @SuppressWarnings("unchecked")
    public List<LifecycleEntity> getLifecyclesByRefObjectUri(String refObjectUri) {
        return (List<LifecycleEntity>) em.createQuery("from LifecycleEntity se where se.refObjectUri=:refObjectUri").
                setParameter("refObjectUri", refObjectUri).getResultList();
    }
}
