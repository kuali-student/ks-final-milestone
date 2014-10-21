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

package org.kuali.student.common.ui.client.logging;


@Deprecated
public enum LogLevel {
		DEBUG(0),
		INFO(1),
		WARN(2),
		ERROR(3),
		FATAL(4);
		
		private int level;
		private LogLevel(int level) {
			this.level = level;
		}
		public int getLevel() {
			return level;
		}
}
