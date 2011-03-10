package org.kuali.student.enrollment.lpr.dao;

import org.kuali.student.enrollment.lpr.model.LuiPersonRelation;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Igor
 */
public class LuiPersonRelationDao extends JpaDaoSupport {
    public List<LuiPersonRelation> getByLuiId(String luiId) {
        EntityManager entityManager = getJpaTemplate().getEntityManagerFactory().createEntityManager();
        return (List<LuiPersonRelation>) entityManager.createQuery("from LuiPersonRelation pr where pr.luiId=:luiId").setParameter("luiId", luiId).getResultList();
    }
}
