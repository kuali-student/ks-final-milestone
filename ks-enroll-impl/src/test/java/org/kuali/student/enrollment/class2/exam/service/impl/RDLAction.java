/**
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.exam.service.impl;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.Action;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.HashSet;
import java.util.Set;

/**
 * Used to help test agendas
 * @author gilesp
 *
 */
public class RDLAction implements Action {

    private SchedulingService schedulingService;

	@Override
	public void execute(ExecutionEnvironment environment) {
        ContextInfo context = (ContextInfo) environment.getFacts().get(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        //schedulingService.createScheduleRequest("scheduleRequestTypeKey", scheduleRequest, context);
	}
	
	/**
	 * @see org.kuali.rice.krms.framework.engine.Action#executeSimulation(org.kuali.rice.krms.api.engine.ExecutionEnvironment)
	 */
	@Override
	public void executeSimulation(ExecutionEnvironment environment) {
		throw new UnsupportedOperationException();
	}
	
}
