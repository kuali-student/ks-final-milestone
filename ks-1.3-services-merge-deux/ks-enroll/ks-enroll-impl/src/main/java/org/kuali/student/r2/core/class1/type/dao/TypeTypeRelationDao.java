package org.kuali.student.r2.core.class1.type.dao;

import java.util.List;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.type.model.TypeTypeRelationEntity;

public class TypeTypeRelationDao extends GenericEntityDao<TypeTypeRelationEntity> {

    @SuppressWarnings("unchecked")
    public List<TypeTypeRelationEntity> getTypeTypeRelationsByOwnerAndRelationType(String ownerTypeId, String typeId) {
        return (List<TypeTypeRelationEntity>) em.createQuery("from TypeTypeRelationEntity rel where rel.type=:typeId AND rel.ownerTypeId=:ownerTypeId").setParameter("typeId", typeId).setParameter("ownerTypeId", ownerTypeId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<TypeTypeRelationEntity> getTypeTypeRelationsByRelatedTypeAndRelationType(String relatedTypeId, String typeId) {
        return (List<TypeTypeRelationEntity>) em.createQuery("from TypeTypeRelationEntity rel where rel.type=:typeId AND rel.relatedTypeId=:relatedTypeId").setParameter("typeId", typeId).setParameter("relatedTypeId", relatedTypeId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<TypeTypeRelationEntity> getTypeTypeRelationsByOwnerTypeForRefObjectUri(String ownerTypeId, String refObjectUri) {
        return (List<TypeTypeRelationEntity>) em.createQuery("from TypeTypeRelationEntity rel where rel.ownerTypeId=:ownerTypeId and rel.refObjectUri=:refObjectUri").setParameter("ownerTypeId", ownerTypeId).setParameter("refObjectUri", refObjectUri).getResultList();
    }

    public List<TypeTypeRelationEntity> getTypeTypeRelationsByOwnerTypeAndRelationType(String ownerTypeId, String relationType) {
        return (List<TypeTypeRelationEntity>) em.createQuery("from TypeTypeRelationEntity rel where rel.ownerTypeId=:ownerTypeId and rel.type=:relationType").setParameter("ownerTypeId", ownerTypeId).setParameter("relationType", relationType).getResultList();
    }
}
