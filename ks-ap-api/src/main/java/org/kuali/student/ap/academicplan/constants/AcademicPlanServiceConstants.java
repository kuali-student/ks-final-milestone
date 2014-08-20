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

import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * Constant for the AcademicPlanService api
 *
 * @Author Kuali Student Team  (ks-collab@kuali.org)
 * @version 1.0 (Dev)
 */
public class AcademicPlanServiceConstants {
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "acadplan";
    public static final String SERVICE_NAME = AcademicPlanService.class.getSimpleName();
    public static final String REF_OBJECT_URI_ACADEMIC_PLAN_INFO = NAMESPACE + "/"
            + LearningPlanInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ACADEMIC_PLAN_ITEM_INFO = NAMESPACE + "/"
            + PlanItemInfo.class.getSimpleName();
    /**
     * Namespaces
     */
    public static final String KS_AP_NAMESPACE = "KS-AP";


    /**
     * Attribute definition
     */
    public static final String SUBJECT_AREA_ATTR_DEFINITION = "subjectArea";
    public static final String DEPARTMENT_ATTR_DEFINITION = "department";


    public static final String EXEC_SERVICE_METHOD = "Exec ";


    public static final String LEARNING_PLAN_TYPE_PLAN =            "kuali.academicplan.type.plan";
    public static final String LEARNING_PLAN_TYPE_PLAN_TEMPLATE =   "kuali.academicplan.type.plan.template";
    public static final String LEARNING_PLAN_TYPE_PLAN_REVIEW =     "kuali.academicplan.type.plan.review";
    public static final String LEARNING_PLAN_ACTIVE_STATE_KEY  =    "kuali.academicplan.plan.state.active";
    public static final String LEARNING_PLAN_ITEM_TYPE =            "kuali.academicplan.type.planitem";
    public static final String LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY ="kuali.academicplan.planitem.state.active";

    public static final String ADVISOR_AFFILIATION_TYPE_KEY = "ADVSR";

    public static enum ItemCategory {
        PLANNED, BACKUP, WISHLIST, WHATIF, CART, COMPLETE;

        public static ItemCategory fromString(String enumString) {
            return ItemCategory.valueOf(enumString.toUpperCase());
        }
       }

    public static final String LEARNING_PLAN_ITEM_SHARED_TRUE_KEY = "true";
    public static final String LEARNING_PLAN_ITEM_SHARED_FALSE_KEY = "false";

    /**
     * PlanItem relation type key that maps a course to a registration group
     */
    public static final String PLAN_ITEM_RELATION_TYPE_COURSE2RG = "kuali.academicplan.item.item.relation.course2rg";

    /**
     * PlanItem relation type key that maps a registration group to a course
     */
    public static final String PLAN_ITEM_RELATION_TYPE_RG2COURSE = "kuali.academicplan.item.item.relation.rg2course";

}
