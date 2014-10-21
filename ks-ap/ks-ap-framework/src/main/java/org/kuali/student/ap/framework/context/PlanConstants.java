package org.kuali.student.ap.framework.context;

import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

public class PlanConstants extends AcademicPlanServiceConstants {

	public static final String PARAM_COURSE_ID = "courseId";
	public static final String PARAM_TERM_ID = "termId";
    public static final String PARAM_OFFERINGS_FLAG = "loadActivityOffering";
    public static final String PARAM_VIEW_ID = "viewId";

	public static final int PLANNED_PLAN_ITEM_CAPACITY = 8;
	public static final int BACKUP_PLAN_ITEM_CAPACITY = 8;
    public static final int CART_PLAN_ITEM_CAPACITY = 8;
	public static final int MAX_FUTURE_YEARS = 4;

    //config parm: number [future] yrs open for planning
    public static final String PLAN_FUTURE_YEARS = "ks.ap.planner.future.years";

	public static final String PLAN_ITEM_RESPONSE_PAGE_ID = "plan_item_action_response_page";
	public static final String PLAN_PAGE_ID = "planned_courses_detail_page";

	public static final String COURSE_TYPE = CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY;
    public static final String REG_GROUP_TYPE = LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY;
	public static final String SECTION_TYPE = "kuali.lu.type.CourseSection";
    public static final String TERM_NOTE_COMMENT_TYPE ="kuali.academicplan.type.note.term";
    public static final String TERM_NOTE_COMMENT_ATTRIBUTE_ATPID = "kauli.ap.type.note.term.attr.atpid";

	// CRUD operations positive feedback.
	public static final String SUCCESS_KEY = "ksap.text.success";
	public static final String SUCCESS_KEY_PLANNED_ITEM_ADDED = "ksap.text.success.plannedCourseList.itemAdded";
	public static final String SUCCESS_KEY_PLANNED_ITEM_MOVED = "ksap.text.success.plannedCourseList.itemMoved";
	public static final String SUCCESS_KEY_PLANNED_ITEM_COPIED = "ksap.text.success.plannedCourseList.itemCopied";
	public static final String SUCCESS_KEY_PLANNED_ITEM_MARKED_BACKUP = "ksap.text.success.plannedCourseList.itemMarkedAsBackup";
	public static final String SUCCESS_KEY_PLANNED_ITEM_MARKED_PLANNED = "ksap.text.success.plannedCourseList.itemMarkedAsPlanned";
    public static final String SUCCESS_KEY_PLANNED_ITEM_MARKED_CART = "ksap.text.success.plannedCourseList.itemMarkedAsCart";
	public static final String SUCCESS_KEY_ITEM_DELETED = "ksap.text.success.itemDeleted";
    public static final String SUCCESS_KEY_ITEM_EDITED = "ksap.text.success.itemEdited";

	public static final String SUCCESS_KEY_SAVED_ITEM_ADDED = "ksap.text.success.savedCourseList.itemAdded";

	// CRUD error feedback
	public static final String ERROR_KEY_PLANNED_ITEM_CAPACITY_EXCEEDED = "ksap.text.error.plannedCourseList.plannedCapacityExceeded";
	public static final String ERROR_KEY_BACKUP_ITEM_CAPACITY_EXCEEDED = "ksap.text.error.plannedCourseList.backupCapacityExceeded";
    public static final String ERROR_KEY_CART_ITEM_CAPACITY_EXCEEDED = "ksap.text.error.plannedCourseList.backupCapacityExceeded";

	public static final String ERROR_KEY_HISTORICAL_ATP = "ksap.text.error.plannedCourseList.historicalAtp";

	public static final String ERROR_KEY_PAGE_RESET_REQUIRED = "ksap.text.error.pageResetRequired";

	public static final String ERROR_KEY_ADVISER_ACCESS = "ksap.text.error.adviserAccess";
	public static final String ERROR_KEY_ILLEGAL_ADVISER_ACCESS = "ksap.text.error.illegalAdviserAccess";
	public static final String ERROR_KEY_OPERATION_FAILED = "ksap.text.error.operationFailed";
	public static final String ERROR_KEY_UNKNOWN_COURSE = "ksap.text.error.unknownCourse";
	public static final String ERROR_KEY_UNKNOWN_PLAN_ITEM = "ksap.text.error.savedCoursesList.unknownPlanItem";
	public static final String ERROR_KEY_DATA_VALIDATION_ERROR = "ksap.text.error.dataValidationError";
	public static final String ERROR_KEY_DUPLICATE_PLAN = "ksap.text.error.savedCoursesList.duplicatePlan";
	public static final String ERROR_KEY_DUPLICATE_PLAN_ITEM = "ksap.text.error.savedCoursesList.duplicatePlanItem";
	public static final String ERROR_KEY_INVALID_PARAM = "ksap.text.error.invalidParameter";
	public static final String ERROR_KEY_MISSING_PARAM = "ksap.text.error.missingParameter";
	public static final String ERROR_KEY_PERMISSION_DENIED = "ksap.text.error.permissionDenied";
	public static final String ERROR_TECHNICAL_PROBLEMS = "ksap.text.error.technicalProblems";

	public static final String ERROR_KEY_NO_STUDENT_PROXY_ID = "ksap.text.error.adviser.noStudentId";
	public static final String WARNING_STUDENT_CONTEXT_SWITCH = "ksap.text.warning.adviser.studentSwitch";

	/* Keys for storing info in the session. */
	public static final String SESSION_KEY_IS_ADVISER = "kuali.ap.authz.adviser";
	public static final String SESSION_KEY_STUDENT_ID = "kuali.ap.authn.studentId";
	public static final String SESSION_KEY_STUDENT_NAME = "kuali.ap.authn.studentName";

	public static final String FOCUS_ATP_ID_KEY = "focusAtpId";

	public static final String PLANNED_TYPE = "planned";
	public static final String BACKUP_TYPE = "backup";

	/* Query keys for getting the termInfos from the academic calender */
	public static final String PLANNING = "%kuali.atp.state.Planning%";
	public static final String INPROGRESS = "%kuali.atp.state.Inprogress%";
	public static final String PUBLISHED = AtpServiceConstants.ATP_OFFICIAL_STATE_KEY;

	public static final String WITHDRAWN_GRADE = "W";

    public static final String ADD_DIALOG_PAGE = "plan_item_add_page";
	public static final String COURSE_SUMMARY_DIALOG_PAGE = "course_summary_dialog_page";
	public static final String COPY_DIALOG_PAGE = "copy_dialog_page";
    public static final String COURSE_NOT_FOUND = "planner.quickAdd.text.error.course.not.found";
    public static final String COURSE_ALREADY_PLANNED = "planner.quickAdd.text.error.course.already.planned";

	/* Course Credit Types */
	public static final String RANGE = "-";
	public static final String MULTIPLE = ",";

    /**
	 * Names of javascript events that can be scheduled in response to the
	 * outcome of a plan item request.
	 */
	public static enum JS_EVENT_NAME {
		PLAN_ITEM_ADDED,
		SECTION_ITEM_ADDED,
		PLAN_ITEM_DELETED,
		SECTION_ITEM_DELETED,
		UPDATE_NEW_TERM_TOTAL_CREDITS,
		UPDATE_OLD_TERM_TOTAL_CREDITS,
        PLAN_NOTE_UPDATED,
        TERM_NOTE_UPDATED,
        COURSE_ADDED,
        BOOKMARK_ADDED,
        UPDATE_BOOKMARK_TOTAL,
        UPDATE_PLAN_ITEM_STATUS
	}

    // Validation Messages
    public static final String PLANNER_VALIDATION_MESSAGE_SUSPENDED_OR_CANCELED = "planner.item.validation.suspended.or.canceled";
    public static final String PLANNER_VALIDATION_MESSAGE_NOT_SCHEDULED = "planner.item.validation.scheduled.not";
}
