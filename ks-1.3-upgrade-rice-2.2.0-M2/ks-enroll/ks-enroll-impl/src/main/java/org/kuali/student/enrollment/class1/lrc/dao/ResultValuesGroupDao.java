package org.kuali.student.enrollment.class1.lrc.dao;

import java.util.List;
import javax.persistence.Query;
import org.kuali.student.enrollment.class1.lrc.model.ResultValuesGroupEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class ResultValuesGroupDao extends GenericEntityDao<ResultValuesGroupEntity> {

    public List<String> getIdsByType(String type) {
        Query query = em.createNamedQuery("ResultValuesGroupEntity.getIdsByType");
        query.setParameter("type", type);
        return query.getResultList();
    }

    public List<ResultValuesGroupEntity> getByResultValue(String resultValueKey) {
        Query query = em.createNamedQuery("ResultValuesGroupEntity.getByResultValue");
        query.setParameter("resultValueKey", resultValueKey);
        return query.getResultList();
    }

    public List<ResultValuesGroupEntity> getByResultScale(String resultScaleKey) {
        Query query = em.createNamedQuery("ResultValuesGroupEntity.getByResultScale");
        query.setParameter("resultScaleKey", resultScaleKey);
        return query.getResultList();
    }
}
