package org.kuali.student.r2.common.class1.type.dao;

import java.util.List;

import javax.persistence.Query;


import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.common.class1.type.model.TypeEntity;

public class TypeDao extends GenericEntityDao<TypeEntity> {

    public List<TypeEntity> getTypesByRefObjectUri(String refObjectUri) {
        Query query = em.createNamedQuery("Type.GetByRefObjectUri");
        query.setParameter("refObjectURI", refObjectUri);
        List<TypeEntity> typesByRefObjectUri = query.getResultList();
        return typesByRefObjectUri;

    }
    
    public List<String> getAllRefObjectUris() {
        Query query = em.createNamedQuery("Type.GetAllRefObjectUris");
        List<String> allRefObjectUris = query.getResultList();
        return allRefObjectUris;
    }
    
}
