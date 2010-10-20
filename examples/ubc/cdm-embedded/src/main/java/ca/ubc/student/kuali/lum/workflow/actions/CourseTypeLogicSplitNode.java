package ca.ubc.student.kuali.lum.workflow.actions;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;
import org.kuali.rice.kew.engine.node.SplitNode;
import org.kuali.rice.kew.engine.node.SplitResult;
import org.kuali.student.lum.lu.dto.CluInfo;
import java.util.ArrayList;
import java.util.List;


public class CourseTypeLogicSplitNode extends LogicSplitNode implements SplitNode {
	protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(CourseTypeLogicSplitNode.class);
	@Override
	public SplitResult process(RouteContext context, RouteHelper helper)
			throws Exception {
	    List<String> branchNames = new ArrayList<String>();
	    try {
	    	
	    	CluInfo clu = getProposal(context);

	    	// get course number	    	
	    	String courseNumber = clu.getOfficialIdentifier().getSuffixCode();
	    	
	    	// get change category	    	
	    	boolean isCat2 = "2".equals(clu.getAttributes().get("changeCategory"));

	    	// determine if course is undergrad or graduate or Cat2 and select appropriate branch
	    	if (isCat2) {
    			branchNames.add("Cat2");	    		
	    	}else{
		    	if (courseNumber != null && courseNumber.matches("^[0-9]*$")){
		    		if (Integer.parseInt(courseNumber) >= 500){
		    			branchNames.add("Graduate");
		    		}else{
		    			branchNames.add("UnderGraduate");
		    		}
		    	}	    		
	    	}	    	
	    } catch (Exception e) {
	      LOG.info("There was a problem checking the course type (course number)");
	    }

	    // default to undergraduate course
	    if (branchNames.size() == 0){
	    	branchNames.add("UnderGraduate");
	    }
	    return new SplitResult(branchNames);
	}
}
