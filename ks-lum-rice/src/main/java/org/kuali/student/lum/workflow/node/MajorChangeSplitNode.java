/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
