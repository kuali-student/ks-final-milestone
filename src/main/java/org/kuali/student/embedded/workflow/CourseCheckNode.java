package org.kuali.student.embedded.workflow;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;
import org.kuali.rice.kew.engine.node.SimpleNode;
import org.kuali.rice.kew.engine.node.SimpleResult;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;

public class CourseCheckNode implements SimpleNode {

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(CourseCheckNode.class);
	
	public SimpleResult process(RouteContext arg0, RouteHelper arg1)
			throws Exception {
		LOG.info("Entering CourseCheckNode process method.");

		DocumentRouteHeaderValue dhv = arg0.getDocument();
		
		LOG.info("Finished CourseCheckNode process method.");
		
		return new SimpleResult(true);
	}

}
