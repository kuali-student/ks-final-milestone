/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by Mezba Mahtab (mezba.mahtab@utoronto.ca) on 1/17/13
 */
package org.kuali.student.enrollment.exam.service;

import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * This class represents constants for the Exam Service.
 *
 * @author Mezba Mahtab (mezba.mahtab@utoronto.ca)
 */
public class ExamServiceConstants {
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "exam";
    public static final String SERVICE_NAME_LOCAL_PART = "ExamService";

    /////////////////
    // TYPES
    /////////////////

    public static final String TYPE_GENERIC_EXAM = "kuali.lu.type.Exam";
    public static final String TYPE_FINAL_EXAM = "kuali.lu.type.Exam.Final";

}
