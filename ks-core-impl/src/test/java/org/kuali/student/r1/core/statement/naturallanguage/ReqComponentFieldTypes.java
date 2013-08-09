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

package org.kuali.student.r1.core.statement.naturallanguage;

/**
 * <p>Requirement component types are used to map a dot notation field key
 * (e.g. kuali.reqComponent.field.type.cluSet) to a key without dot notation (cluSet) since most 
 * template engines don't allow dot notation for template variables because
 * dot notations are used to get class properties or methods 
 * (e.g. clu.getOfficialIdentifier().getShortName()).</p>
 * <p>
 * Some Common Template keys
 * <ul>
 * <li><code>kuali.reqComponent.field.type.clu</code> maps to <code>clu</code></li>
 * <li><code>kuali.reqComponent.field.type.cluSet</code> maps to <code>cluSet</code></li>
 * <li><code>kuali.reqComponent.field.type.requiredCount</code> maps to <code>expectedValue</code></li>
 * <li><code>kuali.reqComponent.field.type.operator</code> maps to <code>relationalOperator</code></li>
 * </ul>
 * Template: <code>Student must have completed all of $cluSet.getCluSetAsShortName()</code>
 * </p>
 */
public enum ReqComponentFieldTypes {
	CLU_KEY("kuali.reqComponent.field.type.clu.id"),
	CLUSET_KEY("kuali.reqComponent.field.type.cluSet.id"),
	REQUIRED_COUNT_KEY("kuali.reqComponent.field.type.requiredCount"),
    GPA_KEY("kuali.reqComponent.field.type.gpa"),
    TOTAL_CREDIT_KEY("kuali.reqComponent.field.type.totalCredits"),
	OPERATOR_KEY("kuali.reqComponent.field.type.operator"),
	COUNT_TYPE_KEY("kuali.reqComponent.field.type.countType"),
	INCLUSION_FILTER_TYPE_KEY("kuali.reqComponent.field.type.inclusionFilter.type"),
	INCLUSION_FILTER_VALUE_KEY("kuali.reqComponent.field.type.inclusionFilter.value");
    
	private String type;
	
	ReqComponentFieldTypes(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
}
