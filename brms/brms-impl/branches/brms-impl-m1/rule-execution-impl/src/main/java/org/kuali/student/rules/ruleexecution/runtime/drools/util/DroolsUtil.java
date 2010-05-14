/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.rules.ruleexecution.runtime.drools.util;

import org.kuali.student.rules.ruleexecution.runtime.drools.logging.DroolsExecutionStatistics;

public class DroolsUtil {

	public DroolsUtil() {}
	
	/**
	 * Gets Drools statistics summary.
	 * 
	 * @param executionStats Drools execution statistics
	 * @return Execution summary
	 */
    public String getStatisticsSummary(DroolsExecutionStatistics executionStats) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n**********  Rule Statistics  **********");
		sb.append("\n* Total activations fired count: " + executionStats.getStatistics().getTotalActivationsFiredCount());
		sb.append("\n* Total log event count:         " + executionStats.getStatistics().getTotalLogEventCount());
		sb.append("\n* Total object insert count:     " + executionStats.getStatistics().getTotalObjectInsertCount());
		sb.append("\n* Total object retract count:    " + executionStats.getStatistics().getTotalObjectRetractCount());
		sb.append("\n* Total object update count:     " + executionStats.getStatistics().getTotalObjectUpdateCount());
		sb.append("\n* Total package add count:       " + executionStats.getStatistics().getTotalPackageAddCount());
		sb.append("\n* Total package remove count:    " + executionStats.getStatistics().getTotalPackageRemoveCount());
		sb.append("\n* Rule Activation Counts: ");
		for(DroolsExecutionStatistics.EventLogger event : executionStats.getRuleActivationStatistics()) {
			sb.append("\n*\tRule Base Type:          " + event.getRuleBaseType());
			sb.append("\n*\tPackage name:            " + event.getPackageName());
			sb.append("\n*\tRule name:               " + event.getRuleName());
			sb.append("\n*\tRule activations:        " + event.getActivationCount());
			double timeInSeconds = event.getAverageExecutionTime() / 1000000000;
			sb.append("\n*\tRule avg execution time: " + timeInSeconds + " secs");
		}
		sb.append("\n***************************************");
		return sb.toString();
    }
    
}
