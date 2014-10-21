/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupCodeGenerator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.infc.FormatOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;

/**
 * A really simple registration code that is just a UUID.
 * 
 * This is a place holder.  Using a UUID is not what is wanted so during M4 this implementation should be thrown away
 * in favour of something that crafts what the Analysis team determines is the desired format.
 * 
 * @author ocleirig
 *
 */
public class UUIDRegistrationGroupCodeGenerator implements
		RegistrationGroupCodeGenerator {

	/**
	 * 
	 */
	public UUIDRegistrationGroupCodeGenerator() {
		// TODO Auto-generated constructor stub
	}


    @Override
    public void initializeGenerator(CourseOfferingService coService, FormatOffering fo, ContextInfo context, Map<String, Object> keyValues) {
        // no initialization
    }

    /* (non-Javadoc)
      * @see org.kuali.student.enrollment.class2.courseoffering.service.strategy.RegistrationGroupCodeGenerationStrategy#generateRegistrationGroupCode(org.kuali.student.enrollment.courseoffering.infc.FormatOffering, java.util.List)
      */
	@Override
	public String generateRegistrationGroupCode(FormatOffering fo,
			List<ActivityOfferingInfo> activities,
            Map<String, Object> keyValues) {
		
		// something easy
		return UUIDHelper.genStringUUID();
	}

}
