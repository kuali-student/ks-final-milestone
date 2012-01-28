package org.kuali.student.r2.core.class1.type.dao;

import java.util.List;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.type.model.TypeTypeRelationEntity;

public class TypeTypeRelationDao extends GenericEntityDao<TypeTypeRelationEntity> {

    @SuppressWarnings("unchecked")
    public List<TypeTypeRelationEntity> getTypeTypeRelationsByOwnerAndRelationTypesForRefObjectUri(String ownerTypeId, String relationTypeId, String refObjectUri) {
        return (List<TypeTypeRelationEntity>) em.createQuery("from TypeTypeRelationEntity rel where rel.type=:relationTypeId AND rel.ownerTypeId=:ownerTypeId and rel.refObjectUri=:refObjectUri")
                .setParameter("relationTypeId", relationTypeId).setParameter("ownerTypeId", ownerTypeId).setParameter("refObjectUri", refObjectUri).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<TypeTypeRelationEntity> getTypeTypeRelationsByOwnerAndRelationTypes(String ownerTypeId, String relationTypeId) {
        return (List<TypeTypeRelationEntity>) em.createQuery("from TypeTypeRelationEntity rel where rel.type=:relationTypeId AND rel.ownerTypeId=:ownerTypeId")
                .setParameter("relationTypeId", relationTypeId).setParameter("ownerTypeId", ownerTypeId).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<TypeTypeRelationEntity> getTypeTypeRelationsByOwnerType(String ownerTypeId, String refObjectUri) {
        return (List<TypeTypeRelationEntity>) em.createQuery("from TypeTypeRelationEntity rel where rel.ownerTypeId=:ownerTypeId and rel.refObjectUri=:refObjectUri")
                .setParameter("ownerTypeId", ownerTypeId).setParameter("refObjectUri", refObjectUri).getResultList();
    }
 
}
