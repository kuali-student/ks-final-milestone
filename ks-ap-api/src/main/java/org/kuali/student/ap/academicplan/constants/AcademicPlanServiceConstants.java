/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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
package org.kuali.student.ap.academicplan.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * Constant for the AcademicPlanService api
 *
 * @Author Kuali Student Team
 * @version 1.0 (Dev)
 */
public class AcademicPlanServiceConstants {
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "acadplan";
    public static final String SERVICE_NAME = "AcademicPlanService";

    public static final String LEARNING_PLAN_TYPE_PLAN = "kuali.academicplan.type.plan";
    public static final String LEARNING_PLAN_TYPE_PLAN_TEMPLATE = "kuali.academicplan.type.plan.template";
    public static final String LEARNING_PLAN_TYPE_PLAN_REVIEW = "kuali.academicplan.type.plan.review";
    public static final String LEARNING_PLAN_ACTIVE_STATE_KEY  = "kuali.academicplan.plan.state.active";

    public static enum ItemCategory {
        PLANNED, BACKUP, WISHLIST, WHATIF, CART, COMPLETE;

        public static ItemCategory fromString(String enumString) {
            return ItemCategory.valueOf(enumString.toUpperCase());
        }
       }

    public static final String LEARNING_PLAN_ITEM_TYPE = "kuali.academicplan.planitem";
    public static final String LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY = "kuali.academicplan.planitem.state.active";
    public static final String LEARNING_PLAN_ITEM_SHARED_TRUE_KEY = "true";
    public static final String LEARNING_PLAN_ITEM_SHARED_FALSE_KEY = "false";

}
