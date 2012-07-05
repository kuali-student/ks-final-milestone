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
package org.kuali.student.enrollment.class2.courseoffering.service.strategy;

import java.util.List;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.infc.FormatOffering;

/**
 * 
 * Defines a way for the particulars of generating a registration group code to be externalized.
 * 
 * @author ocleirig
 *
 */
public interface RegistrationGroupCodeGenerator {
	
	/**
	 * 
	 * @param fo the format Offering
	 * @param activities The list of Activities in the registration group
	 * @return the unique registration code that will shown to users during the registration process.
	 * 
	 */
	public String generateRegistrationGroupCode(FormatOffering fo, List<ActivityOfferingInfo>activities);

}
