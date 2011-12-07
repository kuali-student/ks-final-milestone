/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.enrollment.class1.lrr.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.engine.TermSpecification;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lrr.service.LearningResultRecordService;
import org.kuali.student.enrollment.lui.service.LuiService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Term resolver class to find all completed courses for a student
 * 
 * @author alubbers
 */
public class CompletedCoursesResolver implements
		TermResolver<Collection<String>> {

	private LearningResultRecordService lrrService;

	private LuiPersonRelationService lprService;

	private LuiService luiService;

	private final static Set<String> prerequisites = new HashSet<String>(2);

	@Override
	public Set<TermSpecification> getPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermSpecification getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getParameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<String> resolve(
			Map<TermSpecification, Object> resolvedPrereqs,
			Map<String, String> parameters) throws TermResolutionException {
		// TODO Auto-generated method stub
		return null;
	}

}
