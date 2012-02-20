/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.core.statement.util;

/**
 * Collection of constant values related to the Statement Service
 *
 * @author alubbers
 */
public class StatementServiceConstants {

    // Statement Types
    public static final String PREREQUISITE_STATEMENT_TYPE = "kuali.statement.type.course.academicReadiness.studentEligibilityPrereq";
    public static final String ANTIREQUISITE_STATEMENT_TYPE = "kuali.statement.type.course.academicReadiness.antireq";
    public static final String COREQUISITE_STATEMENT_TYPE = "kuali.statement.type.course.academicReadiness.coreq";

    // Requirement Component Types
    public static final String COMPLETED_COURSE_REQ_COM_TYPE = "kuali.reqComponent.type.course.completed";
    public static final String ALL_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE = "kuali.reqComponent.type.course.courseset.completed.all";
    public static final String N_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE = "kuali.reqComponent.type.course.courseset.completed.nof";
    public static final String MAX_N_OF_COURSES_COMPLETED_REQ_COM_TYPE = "kuali.reqComponent.type.course.courseset.completed.max";
    public static final String NONE_OF_COURSES_COMPLETED_REQ_COM_TYPE = "kuali.reqComponent.type.course.courseset.completed.none";
    public static final String NOT_COMPLETED_COURSE_REQ_COM_TYPE = "kuali.reqComponent.type.course.notcompleted";

    public static final String ENROLLED_COURSE_REQ_COM_TYPE = "kuali.reqComponent.type.course.enrolled";
    public static final String ALL_OF_REQUIRED_COURSES_ENROLLED_REQ_COM_TYPE = "kuali.reqComponent.type.course.courseset.enrolled.all";
    public static final String N_OF_REQUIRED_COURSES_ENROLLED_REQ_COM_TYPE = "kuali.reqComponent.type.course.courseset.enrolled.nof";

    // Requirement Component Field Types
    public static final String COURSE_ID_REQ_COM_FIELD_TYPE = "kuali.reqComponent.field.type.course.clu.id";
    public static final String COURSE_SET_ID_REQ_COM_FIELD_TYPE = "kuali.reqComponent.field.type.course.cluSet.id";
    public static final String INTEGER_REQ_COM_FIELD_TYPE = "kuali.reqComponent.field.type.value.positive.integer";
}
