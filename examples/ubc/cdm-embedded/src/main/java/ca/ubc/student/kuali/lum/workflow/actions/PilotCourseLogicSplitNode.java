package ca.ubc.student.kuali.lum.workflow.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;
import org.kuali.rice.kew.engine.node.SplitNode;
import org.kuali.rice.kew.engine.node.SplitResult;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;


public class PilotCourseLogicSplitNode extends LogicSplitNode implements SplitNode {
	protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(PilotCourseLogicSplitNode.class);
	
	@Override
	public SplitResult process(RouteContext context, RouteHelper helper)
			throws Exception {
	    List<String> branchNames = new ArrayList<String>();

	    try {
	    	// get proposal
	    	CluInfo clu = getProposal(context);
	    	// get pilotCourse
	    	
	    	ListIterator<LuCodeInfo> listItr = clu.getLuCodes().listIterator();
	    	boolean isPilotCourse = false;
	    	
	    	while(listItr.hasNext()){
	    		LuCodeInfo luCodeInfo = (LuCodeInfo) listItr.next();
	    		if ("kuali.lu.code.pilotCourse".equals(luCodeInfo.getType())){
	    			isPilotCourse = Boolean.parseBoolean(luCodeInfo.getValue());
	    			break;
	    		}
	    		
	    	}
	    	
	    	// determine if course is pilot
	    	if (isPilotCourse){
	    	  branchNames.add("PilotCourse");
	    	}else{
    		  branchNames.add("RegularCourse");
	    	}
	    	
	    } catch (Exception e) {
	      LOG.info("There was a problem checking if this is a pilot course (course number)");
	    }

	    // default to undergraduate course
	    if (branchNames.size() == 0){
	    	branchNames.add("RegularCourse");
	    }
	    return new SplitResult(branchNames);
	}
}
