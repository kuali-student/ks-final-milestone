package org.kuali.student.ap.academicplan.service;

import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * kmuthu Don't forget to add comment
 *
 * @Author kmuthu
 * Date: 1/6/12
 */
public class AcademicPlanServiceConstants {
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "acadplan";
    public static final String SERVICE_NAME = "AcademicPlanService";

    public static final String LEARNING_PLAN_TYPE_PLAN = "kuali.academicplan.type.plan";
    public static final String LEARNING_PLAN_TYPE_PLAN_TEMPLATE = "kuali.academicplan.type.plan.template";
    public static final String LEARNING_PLAN_TYPE_PLAN_REVIEW = "kuali.academicplan.type.plan.review";

    public static final String LEARNING_PLAN_ITEM_TYPE_PLANNED = "kuali.academicplan.item.type.planned";
    public static final String LEARNING_PLAN_ITEM_TYPE_BACKUP = "kuali.academicplan.item.type.backup";
    public static final String LEARNING_PLAN_ITEM_TYPE_WISHLIST = "kuali.academicplan.item.type.wishlist";
    public static final String LEARNING_PLAN_ITEM_TYPE_WHATIF = "'kuali.academicplan.item.type.whatif";
    public static final String LEARNING_PLAN_ITEM_TYPE_CART = "kuali.academicplan.item.type.cart";

    public static final String LEARNING_PLAN_ACTIVE_STATE_KEY  = "kuali.academicplan.plan.state.active";
    public static final String LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY = "kuali.academicplan.planitem.state.active";
    public static final String LEARNING_PLAN_ITEM_SHARED_TRUE_KEY = "true";
    public static final String LEARNING_PLAN_ITEM_SHARED_FALSE_KEY = "false";

}
