package org.kuali.student.core.class1.type.dao;

import java.util.List;

import javax.persistence.Query;

import org.kuali.student.core.class1.type.model.TypeEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class TypeDao extends GenericEntityDao<TypeEntity> {

    public List<TypeEntity> getTypesByRefObjectUri(String refObjectUri) {
        Query query = em.createNamedQuery("Type.GetByRefObjectUri");
        query.setParameter("refObjectUri", refObjectUri);
        List<TypeEntity> typesByRefObjectUri = query.getResultList();
        return typesByRefObjectUri;

    }
}
