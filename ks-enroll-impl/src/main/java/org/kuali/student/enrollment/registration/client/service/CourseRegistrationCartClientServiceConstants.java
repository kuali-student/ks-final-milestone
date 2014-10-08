package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.r2.common.constants.CommonServiceConstants;

import javax.xml.namespace.QName;

/**
 * Created by swedev on 2/11/14.
 */
public class CourseRegistrationCartClientServiceConstants {
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseRegistrationCartClient";
    public static final String SERVICE_NAME_LOCAL_PART = CourseRegistrationCartClientService.class.getSimpleName();
    public static final QName QNAME = new QName(NAMESPACE, SERVICE_NAME_LOCAL_PART);

    public enum ACTION_LINKS {
        REMOVE_ITEM_FROM_CART("removeItemFromCart"),
        UNDO_DELETE_COURSE("undoDeleteCourse");

        private String action;

        ACTION_LINKS (String action) {
            this.action = action;
        }

        public String getAction() {
            return action;
        }

        public String toString () {
            return action;
        }
    }

    public interface AddToCartStates {
        final String ERROR = "kuali.cr.cart.add.state.error";
        final String SUCCESS = "kuali.cr.cart.add.state.success";
    }

    public interface CartMessages {
        // Course Code / Section (RegGroupCode) Missing
        final String COURSE_CODE_REQUIRED = "kuali.cr.cart.message.course.code.required";
        final String REG_GROUP_CODE_REQUIRED = "kuali.cr.cart.message.section.required";
        final String COURSE_CODE_AND_SECTION_REQUIRED = "kuali.cr.cart.message.course.code.and.section.required";

        // Couldn't find Cart / Course
        final String CART_DOES_NOT_EXIST = "kuali.cr.cart.message.cart.does.not.exist";
        final String COURSE_DOES_NOT_EXIST = "kuali.cr.cart.message.course.does.not.exist";

        // Required Reg options missing / invalid
        final String CREDIT_OR_GRADING_OPTIONS_MISSING = "kuali.cr.cart.message.options.missing";
        final String CREDIT_OPTION_INVALID = "kuali.cr.cart.message.credits.invalid";
        final String GRADING_OPTION_INVALID = "kuali.cr.cart.message.grading.invalid";

        // Reg Group State != Offered
        final String REG_GROUP_NOT_OFFERED = "kuali.cr.cart.message.reg.group.not.offered";

        final String ADD_TO_CART_UNKNOWN_EXCEPTION = "kuali.cr.cart.message.add.failed.unknown";
    }
}
