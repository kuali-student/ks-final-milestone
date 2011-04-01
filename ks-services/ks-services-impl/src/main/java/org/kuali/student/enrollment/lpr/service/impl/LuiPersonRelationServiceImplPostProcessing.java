package org.kuali.student.enrollment.lpr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;




public class LuiPersonRelationServiceImplPostProcessing  {
	
	
	public List<LuiPersonRelationStateInfo> findLuiPersonRelationStates(List<LuiPersonRelationStateInfo> lprInfo, ContextInfo context) {
		LuiPersonRelationStateInfo lprInfoLocal = lprInfo.get(0);
		
		List <LuiPersonRelationStateInfo> returnList =  new ArrayList<LuiPersonRelationStateInfo>();
		
		return returnList;
	}
	
	
}
