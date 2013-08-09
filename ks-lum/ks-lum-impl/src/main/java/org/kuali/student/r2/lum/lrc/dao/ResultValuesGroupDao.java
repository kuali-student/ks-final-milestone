package org.kuali.student.r2.lum.lrc.dao;

import java.util.List;
import javax.persistence.Query;
import org.kuali.student.r2.lum.lrc.model.ResultValuesGroupEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

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
