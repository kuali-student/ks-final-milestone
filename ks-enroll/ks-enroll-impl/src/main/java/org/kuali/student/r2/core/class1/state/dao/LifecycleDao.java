package org.kuali.student.r2.core.class1.state.dao;

import java.util.List;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.state.model.LifecycleEntity;

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
