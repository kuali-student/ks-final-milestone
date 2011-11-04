package org.kuali.student.r2.common.dao;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.common.model.StateProcessEntity;

public class StateProcessDao extends GenericEntityDao<StateProcessEntity>{
	public StateProcessEntity getProcessByKey(String processKey){
		return find(processKey);
	}
}
