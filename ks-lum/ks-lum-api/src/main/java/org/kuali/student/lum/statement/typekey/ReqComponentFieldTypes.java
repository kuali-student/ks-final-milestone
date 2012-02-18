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

package org.kuali.student.lum.statement.typekey;

/**
 * <p>Requirement component types are used to map a dot notation field key
 * (e.g. kuali.reqComponent.field.type.cluSet) to a key without dot notation 
 * (cluSet) since most template engines don't allow dot notation for template 
 * variables because dot notations are used to get class properties or methods 
 * (e.g. clu.getOfficialIdentifier().getShortName()).</p>
 * <p>
 * Some Common Template keys
 * <ul>
 * <li><code>kuali.reqComponent.field.type.clu</code> maps to <code>clu</code></li>
 * <li><code>kuali.reqComponent.field.type.cluSet</code> maps to <code>cluSet</code></li>
 * <li><code>kuali.reqComponent.field.type.value.positive.integer</code> maps to <code>value</code></li>
 * <li><code>kuali.reqComponent.field.type.operator</code> maps to <code>relationalOperator</code></li>
 * </ul>
 * Template: <code>Student must have completed all of $cluSet.getCluSetAsShortName()</code>
 * </p>
 */
public enum ReqComponentFieldTypes {
	INTEGER_VALUE1_KEY("kuali.reqComponent.field.type.value.positive.integer"),
	OPERATOR_KEY("kuali.reqComponent.field.type.operator"),
    GPA_KEY("kuali.reqComponent.field.type.gpa"),
    TOTAL_CREDIT_KEY("kuali.reqComponent.field.type.totalCredits"),
	INCLUSION_FILTER_TYPE_KEY("kuali.reqComponent.field.type.inclusionFilter.type"),
	INCLUSION_FILTER_VALUE_KEY("kuali.reqComponent.field.type.inclusionFilter.value"),
    GRADE_TYPE_KEY("kuali.reqComponent.field.type.gradeType.id"),
    GRADE_KEY("kuali.reqComponent.field.type.grade.id"),
    ORGANIZATION_KEY("kuali.reqComponent.field.type.org.id"),
    PERSON_KEY("kuali.reqComponent.field.type.person.id"),
    DURATION_KEY("kuali.reqComponent.field.type.duration"),
    DURATION_TYPE_KEY("kuali.reqComponent.field.type.durationType.id"),
	CLU_KEY("kuali.reqComponent.field.type.clu.id"),
	CLUSET_KEY("kuali.reqComponent.field.type.cluSet.id"),
	COURSE_CLU_KEY("kuali.reqComponent.field.type.course.clu.id"),
	COURSE_CLUSET_KEY("kuali.reqComponent.field.type.course.cluSet.id"),
	PROGRAM_CLU_KEY("kuali.reqComponent.field.type.program.clu.id"),
	PROGRAM_CLUSET_KEY("kuali.reqComponent.field.type.program.cluSet.id"),
	TEST_CLU_KEY("kuali.reqComponent.field.type.test.clu.id"),//FIXME Why do we have test data in our code?
	TEST_CLUSET_KEY("kuali.reqComponent.field.type.test.cluSet.id");

	private String id;
	
	ReqComponentFieldTypes(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
//
//	public enum ValueDataType {
//		STRING,
//		INTEGER,
//		DOUBLE,
//	}
}
