package org.kuali.student.r2.core.class1.type.dao;

import java.util.List;

import javax.persistence.Query;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.kuali.student.r2.core.class1.type.entity.TypeEntity;

public class TypeDao extends GenericEntityDao<TypeEntity> {

    public List<TypeEntity> getTypesByRefObjectUri(String refObjectUri) {
        Query query = em.createNamedQuery("Type.GetByRefObjectUri");
        query.setParameter("refObjectUri", refObjectUri);
        List<TypeEntity> typesByRefObjectUri = query.getResultList();
        return typesByRefObjectUri;

    }
}
