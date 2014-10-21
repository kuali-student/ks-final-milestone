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

import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

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
    GPA_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GPA_KEY),
    TOTAL_CREDIT_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TOTAL_CREDIT_KEY),
    INCLUSION_FILTER_TYPE_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_INCLUSION_FILTER_TYPE_KEY),
    INCLUSION_FILTER_VALUE_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_INCLUSION_FILTER_VALUE_KEY),
    GRADE_TYPE_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_TYPE_KEY),
    GRADE_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY),
    ORGANIZATION_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_ORGANIZATION_KEY),
    PERSON_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_PERSON_KEY),
    DURATION_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_KEY),
    DURATION_TYPE_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_TYPE_KEY),
    CLU_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLU_KEY),
    CLUSET_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLUSET_KEY),
    COURSE_CLU_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY),
    COURSE_CLUSET_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY),
    PROGRAM_CLU_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_PROGRAM_CLU_KEY),
    PROGRAM_CLUSET_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_PROGRAM_CLUSET_KEY),
    TEST_CLU_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TEST_CLU_KEY),      //note, this is a test as in assessment, not environment
    TEST_CLUSET_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TEST_CLUSET_KEY),  //note, this is a test as in assessment, not environment
    FREE_TEXT_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_FREE_TEXT_KEY),
    TERM_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY),
    TERM2_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM2_KEY),
    POPULATION_KEY(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_POPULATION_KEY),
    TIMESLOT_WEEKDAY_STRING(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_WEEKDAY_STRING),
    TIMESLOT_START(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_START),
    TIMESLOT_END(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_END);

	private String id;

    TermParameterTypes(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
}
