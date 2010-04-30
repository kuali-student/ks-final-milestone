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

package org.kuali.student.brms.internal.common.statement.regex;

public interface RegularExpression {
	/**
	 * Performs a regular expression match.
	 * 
	 * @param regex Regular expression
	 * @param s String to match the regular expression on
	 * @return True if string matches regular expression; otherwise false
	 */
	public Boolean matches(String regex, String s);
}
