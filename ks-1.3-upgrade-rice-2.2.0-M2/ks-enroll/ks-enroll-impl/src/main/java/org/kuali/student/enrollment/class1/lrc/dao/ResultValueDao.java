package org.kuali.student.enrollment.class1.lrc.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.kuali.student.enrollment.class1.lrc.model.ResultValueEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class ResultValueDao extends GenericEntityDao<ResultValueEntity> {

    public List<String> getIdsByType(String type) {
        Query query = em.createNamedQuery("ResultValueEntity.getIdsByType");
        query.setParameter("type", type);
        return query.getResultList();
    }

    public List<ResultValueEntity> getByScale(String resultScaleKey) {
        Query query = em.createNamedQuery("ResultValueEntity.getByScale");
        query.setParameter("resultScaleKey", resultScaleKey);
        return query.getResultList();
    }

    public ResultValueEntity getByScaleAndValue(String resultScaleKey,
            String value) {
        Query query = em.createNamedQuery("ResultValueEntity.getByScaleAndValue");
        query.setParameter("resultScaleKey", resultScaleKey);
        query.setParameter("value", value);
        try {
            return (ResultValueEntity) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
