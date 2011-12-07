package org.kuali.student.enrollment.class1.lui.dao;

import org.kuali.student.enrollment.class1.lui.model.LuiTypeEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

import java.util.List;

public class LuiTypeDao extends GenericEntityDao<LuiTypeEntity>{

    public List<LuiTypeEntity> getLuiTypesByRefObjectUri(String refObjectURI){
		return (List<LuiTypeEntity>) em.createQuery("from LuiTypeEntity type where type.refObjectURI=:refObjectURI")
		.setParameter("refObjectURI", refObjectURI)
		.getResultList();
	}

}
