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
 * Created by Mezba Mahtab (mezba.mahtab@utoronto.ca) on 7/15/13
 */
package org.kuali.student.r2.common.util.constants;

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

    public static final String EXAM_OFFERING_TYPE_KEY = "kuali.lui.type.exam.offering";
    public static final String EXAM_OFFERING_FINAL_TYPE_KEY = "kuali.lui.type.exam.offering.final";

    /////////////////
    // STATES
    /////////////////

    public static final String EXAM_OFFERING_SCHEDULING_EXEMPT_STATE_KEY = "kuali.lui.exam.offering.scheduling.state.exempt";
    public static final String EXAM_OFFERING_SCHEDULING_UNSCHEDULED_STATE_KEY = "kuali.lui.exam.offering.scheduling.state.unscheduled";
    public static final String EXAM_OFFERING_SCHEDULING_MATRIX_ERROR_STATE_KEY = "kuali.lui.exam.offering.scheduling.state.matrix.error";

}
