package org.kuali.student.r2.core.organization.service.impl.dao;



import org.kuali.student.r2.core.organization.service.impl.lib.GenericEntityDao;
import org.kuali.student.r2.core.organization.service.impl.model.OrgOrgRelationEntity;

import javax.persistence.Query;
import java.util.List;

public class OrgOrgRelationDao extends GenericEntityDao<OrgOrgRelationEntity> {

    public List<String> getIdsByType(String type) {
        Query query = em.createNamedQuery("OrgOrgRelationEntity.getIdsByType");
        query.setParameter("type", type);
        return query.getResultList();
    }
}
