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
package org.kuali.student.r2.lum.util.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.lum.clu.dto.CluInfo;

/**
 * Constants used by CluService
 * 
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class CluServiceConstants {
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "clu";
    public static final String REF_OBJECT_URI_CLU = NAMESPACE + "/" + CluInfo.class.getSimpleName();

    public static final String SERVICE_NAME_LOCAL_PART = "CluService";
    public static final String CLU_NAMESPACE = "http://student.kuali.org/wsdl/clu";
    public static final String CLU_NAMESPACE_URI = "{" + CLU_NAMESPACE + "}cluInfo";
    
    public static final String CREDIT_COURSE_LU_TYPE_KEY = "kuali.lu.type.CreditCourse";
    public static final String COURSE_FORMAT_TYPE_KEY = "kuali.lu.type.CreditCourseFormatShell";
    public static final String COURSE_ACTIVITY_LAB_TYPE_KEY = "kuali.lu.type.activity.Lab";
    public static final String COURSE_ACTIVITY_DISCUSSION_TYPE_KEY = "kuali.lu.type.activity.Discussion";
    public static final String COURSE_ACTIVITY_TUTORIAL_TYPE_KEY = "kuali.lu.type.activity.Tutorial";
    public static final String COURSE_ACTIVITY_LECTURE_TYPE_KEY = "kuali.lu.type.activity.Lecture";
    public static final String COURSE_ACTIVITY_WEBLECTURE_TYPE_KEY = "kuali.lu.type.activity.WebLecture";
    public static final String COURSE_ACTIVITY_WEBDISCUSS_TYPE_KEY = "kuali.lu.type.activity.WebDiscussion";
    public static final String COURSE_ACTIVITY_DIRECTED_TYPE_KEY = "kuali.lu.type.activity.Directed";
    
    
    public static final String COURSE_ACTIVITY_EXP_LEARNING_OTHER_TYPE_KEY = "kuali.lu.type.activity.ExperientialLearningOROther";
    public static final String COURSE_ACTIVITY_HOMEWORK_TYPE_KEY = "kuali.lu.type.activity.Homework";
    public static final String COURSE_ACTIVITY_SEMINAR_TYPE_KEY =  "kuali.lu.type.activity.Seminar";
    
    public static final String CLUSET_TYPE_CREDIT_COURSE = "kuali.cluSet.type.CreditCourse";
    public static final String CLUSET_TYPE_PROGRAM = "kuali.cluSet.type.Program";
    public static final String CLUSET_TYPE_TEST = "kuali.cluSet.type.Test";
    
    /**
     * OpenCollab/rSmart KRAD CM Conversion Alliance!
     */
    public static final String CAMPUS_LOCATION_ENUM_KEY = "kuali.lu.campusLocation";
    public static final String FINAL_EXAM_STATUS_ENUM_KEY = "kuali.lu.finalExam.status";
}
