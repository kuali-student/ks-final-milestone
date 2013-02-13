/*
 * Copyright 2012 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.krms;


/**
 * 
 * @author Kuali Student Team
 * 
 */
public final class KRMSConstants {

    // Terms
    public static final String COURSE_ID = "kuali.krms.term.type.course.specific.id";
    public static final String COURSE = "kuali.krms.term.type.course.specific";
    public static final String COURSE_ID_SET = "kuali.krms.term.type.course.set.id";
    public static final String COURSE_SET = "kuali.krms.term.type.course.set";
    public static final String COURSE_OFFERING_ID = "kuali.krms.term.type.course.offering.specific.id";
    public static final String COURSE_OFFERING = "kuali.krms.term.type.course.offering.specific";
    public static final String COURSE_OFFERING_ID_SET = "kuali.krms.term.type.course.offering.set.id";
    public static final String COURSE_OFFERING_SET = "kuali.krms.term.type.course.offering.set";
    public static final String STUDENT_ID = "kuali.krms.term.type.person.student.specific.id";
    public static final String STUDENT = "kuali.krms.term.type.person.student.specific";
    public static final String START_TERM_ID = "kuali.krms.term.type.acal.term.start.id";
    public static final String START_TERM = "kuali.krms.term.type.acal.term.start";
    public static final String END_TERM_ID = "kuali.krms.term.type.acal.term.end.id";
    public static final String END_TERM = "kuali.krms.term.type.acal.term.end";
    public static final String START_DATE = "kuali.krms.term.type.date.start";
    public static final String END_DATE = "kuali.krms.term.type.date.end";
    
    public static final String ATP_ID = "kuali.krms.term.type.atp.id";
    
    public static final String CURRENT_TERM_ID = "kuali.krms.term.type.atp.current.id";
    
    public static final String FIELD_TYPE_PARAMETER = "kuali.krms.term.type.field";

    public static final String STUDENT_VIEW = "StudentView";
    public static final String STUDENT_LOOKUP_VIEW = "StudentLookupView";
    public static final String STUDENT_INQUIRY_VIEW = "StudentInquiryView";

    public static final String PROP_COMP_DEFAULT_DESCR = "Must meet all of the following";

    public final static class WebPaths {
        public static final String AGENDA_STUDENT_INQUIRY_PATH = "krmsAgendaStudentInquiry";
        public static final String AGENDA_STUDENT_EDITOR_PATH = "krmsAgendaStudentEditor";
        public static final String RULE_STUDENT_INQUIRY_PATH = "krmsRuleStudentInquiry";
        public static final String RULE_STUDENT_EDITOR_PATH = "krmsRuleStudentEditor";
    }
    
    private KRMSConstants() {

    }

}
