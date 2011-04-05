package org.kuali.student.enrollment.lpr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StateInfo;




public class LuiPersonRelationServiceImplPostProcessing  {
	
	
	public List<StateInfo> findLuiPersonRelationStates(List<StateInfo> lprInfo, ContextInfo context) {
		StateInfo lprInfoLocal = lprInfo.get(0);
		
		List <StateInfo> returnList =  new ArrayList<StateInfo>();
		
		return returnList;
	}	
}
