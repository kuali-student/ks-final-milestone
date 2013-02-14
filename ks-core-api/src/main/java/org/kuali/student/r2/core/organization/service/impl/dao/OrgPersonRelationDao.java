package org.kuali.student.r2.core.organization.service.impl.dao;



import org.kuali.student.r2.core.organization.service.impl.lib.GenericEntityDao;
import org.kuali.student.r2.core.organization.service.impl.model.OrgPersonRelationEntity;

import javax.persistence.Query;
import java.util.List;

public class OrgPersonRelationDao extends GenericEntityDao<OrgPersonRelationEntity> {

    public List<String> getIdsByType(String type) {
        Query query = em.createNamedQuery("OrgPersonRelationEntity.getIdsByType");
        query.setParameter("type", type);
        return query.getResultList();
    }
}
