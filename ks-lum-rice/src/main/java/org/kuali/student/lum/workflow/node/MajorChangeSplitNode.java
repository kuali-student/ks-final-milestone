/**
 * 
 */
package org.kuali.student.lum.workflow.node;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;
import org.kuali.rice.kew.engine.node.SplitNode;
import org.kuali.rice.kew.engine.node.SplitResult;

/**
 * @author delyea
 *
 */
public class MajorChangeSplitNode implements SplitNode {

	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.engine.node.SplitNode#process(org.kuali.rice.kew.engine.RouteContext, org.kuali.rice.kew.engine.RouteHelper)
	 */
	@Override
	public SplitResult process(RouteContext context, RouteHelper helper) throws Exception {
		List<String> branchNames = new ArrayList<String>();
		branchNames.add("True");
		return new SplitResult(branchNames);
	}

}
