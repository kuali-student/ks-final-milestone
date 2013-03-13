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

package org.kuali.student.r2.core.krms.naturallanguage;

/**
 * <p>Requirement component types are used to map a dot notation field key
 * (e.g. kuali.term.parameter.type.cluSet) to a key without dot notation
 * (cluSet) since most template engines don't allow dot notation for template 
 * variables because dot notations are used to get class properties or methods 
 * (e.g. clu.getOfficialIdentifier().getShortName()).</p>
 * <p>
 * Some Common Template keys
 * <ul>
 * <li><code>kuali.term.parameter.type.clu</code> maps to <code>clu</code></li>
 * <li><code>kuali.term.parameter.type.cluSet</code> maps to <code>cluSet</code></li>
 * <li><code>kuali.term.parameter.type.value.positive.integer</code> maps to <code>value</code></li>
 * <li><code>kuali.term.parameter.type.operator</code> maps to <code>relationalOperator</code></li>
 * </ul>
 * Template: <code>Student must have completed all of $cluSet.getCluSetAsShortName()</code>
 * </p>
 */
public enum TermParameterTypes {
    GPA_KEY("kuali.term.parameter.type.gpa"),
    TOTAL_CREDIT_KEY("kuali.term.parameter.type.totalCredits"),
    INCLUSION_FILTER_TYPE_KEY("kuali.term.parameter.type.inclusionFilter.type"),
    INCLUSION_FILTER_VALUE_KEY("kuali.term.parameter.type.inclusionFilter.value"),
    GRADE_TYPE_KEY("kuali.term.parameter.type.gradeType.id"),
    GRADE_KEY("kuali.term.parameter.type.grade.id"),
    ORGANIZATION_KEY("kuali.term.parameter.type.org.id"),
    PERSON_KEY("kuali.term.parameter.type.person.id"),
    DURATION_KEY("kuali.term.parameter.type.duration"),
    DURATION_TYPE_KEY("kuali.term.parameter.type.durationType.id"),
    CLU_KEY("kuali.term.parameter.type.clu.id"),
    CLUSET_KEY("kuali.term.parameter.type.cluSet.id"),
    COURSE_CLU_KEY("kuali.term.parameter.type.course.clu.id"),
    COURSE_CLUSET_KEY("kuali.term.parameter.type.course.cluSet.id"),
    PROGRAM_CLU_KEY("kuali.term.parameter.type.program.clu.id"),
    PROGRAM_CLUSET_KEY("kuali.term.parameter.type.program.cluSet.id"),
    TEST_CLU_KEY("kuali.term.parameter.type.test.clu.id"),//FIXME Why do we have test data in our code?
    TEST_CLUSET_KEY("kuali.term.parameter.type.test.cluSet.id"),
    FREE_TEXT_KEY("kuali.term.parameter.type.free.text");

	private String id;

    TermParameterTypes(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
}
