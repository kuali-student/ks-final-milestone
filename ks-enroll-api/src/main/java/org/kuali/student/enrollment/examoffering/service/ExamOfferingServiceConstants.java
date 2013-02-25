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
 * Created by Mezba Mahtab (mezba.mahtab@utoronto.ca) on 2/15/13
 */
package org.kuali.student.enrollment.examoffering.service;

import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * This class represents constants for the ExamOffering Service.
 *
 * @author Mezba Mahtab (mezba.mahtab@utoronto.ca)
 */
public class ExamOfferingServiceConstants {
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "examOffering";
    public static final String SERVICE_NAME_LOCAL_PART = "ExamOfferingService";

    /////////////////
    // TYPES
    /////////////////

    public static final String TYPE_EXAM_OFFERING = "kuali.lui.type.ExamOffering";
    public static final String TYPE_EXAM_OFFERING_FINAL = "kuali.lui.type.exam.offering.final";

}
