package org.kuali.student.r2.core.class1.state.dao;

import org.kuali.student.r2.common.class1.state.model.LifecycleEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;



public class LifecycleDao extends GenericEntityDao<LifecycleEntity>{
	public LifecycleEntity getProcessByKey(String processKey){
		return find(processKey);
	}
}
