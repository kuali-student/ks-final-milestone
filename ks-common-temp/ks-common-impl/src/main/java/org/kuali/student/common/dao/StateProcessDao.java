package org.kuali.student.common.dao;

import org.kuali.student.common.model.StateProcessEntity;

public class StateProcessDao extends GenericEntityDao<StateProcessEntity>{
	public StateProcessEntity getProcessByKey(String processKey){
		return find(processKey);
	}
}
