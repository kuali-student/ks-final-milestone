package org.kuali.student.r2.core.organization.service.impl.dao;


import org.kuali.student.r2.core.organization.service.impl.lib.GenericEntityDao;
import org.kuali.student.r2.core.organization.service.impl.model.OrgHierarchyEntity;

import javax.persistence.Query;
import java.util.List;

public class OrgHierarchyDao extends GenericEntityDao<OrgHierarchyEntity> {

    public List<String> getIdsByType(String type) {
        Query query = em.createNamedQuery("OrgHierarchyEntity.getIdsByType");
        query.setParameter("type", type);
        return query.getResultList();
    }
}
