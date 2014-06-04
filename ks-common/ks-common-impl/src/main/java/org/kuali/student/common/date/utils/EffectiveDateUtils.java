/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.date.utils;

import java.util.Date;

/**
 * @author Kuali Student Team
 * 
 * A helper for effective date comparisons.
 *
 */
public final class EffectiveDateUtils {

	/**
	 * 
	 */
	private EffectiveDateUtils() {
	}


	/**
	 * The targetDate is effective if:
	 * 1. startDate <= targetDate
	 * 2. targetDate <= endDate or endDate is null.
	 * 
	 * @param startDate the beginning of the date range.
	 * @param endDate the end of the date range or null if no end has been set.
	 * @param targetDate the time of interest that we want to determine to be inside or outside of the date range.
	 * 
	 * @return true if the targetDate is effective, false otherwise.
	 */
	public static boolean isTargetDateEffective(Date startDate, Date endDate, Date targetDate) {
		
		// TODO: this seems like a specialized version of DateRange 
		// that might be something to investigate.
		if (startDate.equals(targetDate) || startDate.before(targetDate)) {
			
			if (endDate == null || endDate.equals(targetDate) || endDate.after(targetDate))
				return true;
			else
				return false;
		}
		else
			return false;
	}
}
