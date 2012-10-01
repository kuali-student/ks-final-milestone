package org.kuali.student.r2.core.class1.atp.dao;

import java.util.List;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.atp.model.AtpAtpRelationEntity;

public class AtpAtpRelationDao extends GenericEntityDao<AtpAtpRelationEntity> {

    @SuppressWarnings({"unchecked"})
    public List<AtpAtpRelationEntity> getAtpAtpRelationsByAtp(String atpId) {
        return (List<AtpAtpRelationEntity>) em
                .createQuery("from AtpAtpRelationEntity rel where rel.atp.id=:atpId OR rel.relatedAtp.id=:atpId")
                .setParameter("atpId", atpId).getResultList();
    }

    @SuppressWarnings({"unchecked"})
    public List<AtpAtpRelationEntity> getAtpAtpRelationsByAtpAndRelationType(String atpId, String relationType) {
        return (List<AtpAtpRelationEntity>) em
                .createQuery("from AtpAtpRelationEntity rel where rel.atp.id=:atpId AND rel.atpType=:relationType")
                .setParameter("atpId", atpId).setParameter("relationType", relationType).getResultList();
    }

    @SuppressWarnings({"unchecked"})
    public List<AtpAtpRelationEntity> getAtpAtpRelationsByAtpsAndRelationType(String atpId, String atpPeerId,
            String relationType) {
        return (List<AtpAtpRelationEntity>) em
                .createQuery(
                        "from AtpAtpRelationEntity rel where rel.atp.id=:atpId AND rel.relatedAtp.id=:atpPeerId AND rel.atpType=:relationType")
                .setParameter("atpId", atpId).setParameter("atpPeerId", atpPeerId)
                .setParameter("relationType", relationType).getResultList();
    }
}
