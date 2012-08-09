package org.kuali.student.enrollment.class1.lrc.dao;

import java.util.List;
import javax.persistence.Query;
import org.kuali.student.enrollment.class1.lrc.model.ResultScaleEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class ResultScaleDao extends GenericEntityDao<ResultScaleEntity> {

    public List<String> getIdsByType (String type) {
        Query query = em.createNamedQuery("ResultScaleEntity.getIdsByType");
        query.setParameter("type", type);
        return query.getResultList();
    }
}
