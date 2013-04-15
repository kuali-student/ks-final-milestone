/*
 * Copyright 2012 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.util.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.fee.dto.EnrollmentFeeInfo;

/**
 * This class holds the constants used by the Fee service.
 *
 * @author tom
 */

public class FeeServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String SERVICE_NAME_LOCAL_PART = "FeeService";
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "fee";
    public static final String REF_OBJECT_URI_FEE = NAMESPACE + "/" + EnrollmentFeeInfo.class.getSimpleName();

    /**
     * Fee types. (these type keys used in CM)
     */
    public static final String FEE_KEY_PREFIX = "kuali.enum.type.feeTypes";
    public static final String FEE_ENROLLMENT_TYPE_KEY = "kuali.enum.type.feeTypes.enrollmentFee";
    public static final String FEE_LAB_TYPE_KEY = "kuali.enum.type.feeTypes.labFee";
    public static final String FEE_MATERIAL_TYPE_KEY = "kuali.enum.type.feeTypes.materialFee";
    public static final String FEE_STUDIO_TYPE_KEY = "kuali.enum.type.feeTypes.studioFee";
    public static final String FEE_FIELD_TRIP_TYPE_KEY = "kuali.enum.type.feeTypes.fieldTripFee";
    public static final String FEE_FIELD_STUDY_TYPE_KEY = "kuali.enum.type.feeTypes.fieldStudyFee";
    public static final String FEE_ADMIN_TYPE_KEY = "kuali.enum.type.feeTypes.administrativeFee";
    public static final String FEE_COOP_TYPE_KEY = "kuali.enum.type.feeTypes.coopFee";
    public static final String FEE_GREENS_TYPE_KEY = "kuali.enum.type.feeTypes.greensFee";

    /**
     * States for Fees.
     */
    public static final String FEE_PROCESS_KEY = "kuali.fee.process.fee";
    public static final String FEE_ACTIVE_STATE_KEY = "kuali.fee.state.active";
    public static final String FEE_INACTIVE_STATE_KEY = "kuali.fee.state.inactive";
    public static final String[] FEE_PROCESS_KEYS = {FEE_ACTIVE_STATE_KEY,
                                                     FEE_INACTIVE_STATE_KEY};
}
