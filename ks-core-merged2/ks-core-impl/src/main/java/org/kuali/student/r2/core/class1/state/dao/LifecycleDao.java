package org.kuali.student.r2.core.class1.state.dao;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.state.model.LifecycleEntity;

public class LifecycleDao extends GenericEntityDao<LifecycleEntity>{
	public LifecycleEntity getProcessByKey(String processKey){
		return find(processKey);
	}
}
