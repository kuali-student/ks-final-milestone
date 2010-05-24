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

package org.kuali.student.core.statement.naturallanguage.util;

/**
 * <p>Requirement component types are used to map a dot notation field key
 * (e.g. reqCompFieldType.cluSet) to a key without dot notation (cluSet) since most 
 * template engines don't allow dot notation for template variables because
 * dot notations are used to get class properties or methods 
 * (e.g. clu.getOfficialIdentifier().getShortName()).</p>
 * <p>
 * Some Common Template keys
 * <ul>
 * <li><code>reqCompFieldType.clu</code> maps to <code>clu</code></li>
 * <li><code>reqCompFieldType.cluSet</code> maps to <code>cluSet</code></li>
 * <li><code>reqCompFieldType.requiredCount</code> maps to <code>expectedValue</code></li>
 * <li><code>reqCompFieldType.operator</code> maps to <code>relationalOperator</code></li>
 * </ul>
 * Template: <code>Student must have completed all of $cluSet.getCluSetAsShortName()</code>
 * </p>
 */
public enum ReqComponentFieldTypes {
	CLU_KEY("reqCompFieldType.clu"),
	CLUSET_KEY("reqCompFieldType.cluSet"),
	REQUIRED_COUNT_KEY("reqCompFieldType.requiredCount"),
    GPA_KEY("reqCompFieldType.gpa"),
    TOTAL_CREDIT_KEY("reqCompFieldType.totalCredits"),
	OPERATOR_KEY("reqCompFieldType.operator"),
	COUNT_TYPE_KEY("reqCompFieldType.countType"),
	INCLUSION_FILTER_TYPE_KEY("reqCompFieldType.inclusionFilter.type"),
	INCLUSION_FILTER_VALUE_KEY("reqCompFieldType.inclusionFilter.value");

	private String key;
	
	ReqComponentFieldTypes(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
}
