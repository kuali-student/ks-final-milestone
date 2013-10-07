package org.kuali.student.enrollment.class1.timeslot.util;

/**
 * Constants related to Manage Time Slots.
 */
public class TimeSlotConstants {
    // Page IDs
    public static final String TIME_SLOT_PAGE = "timeSlotPage";

    /**
     * These are the confirm dialog bean ids used in Manage SOC view.
     */
    public static class ConfirmDialogs{
        public static final String DELETE_TIMESLOTS = "deleteTimeSlotsConfirmationDialog";
    }


    public static class ApplicationResouceKeys{
        //toolbar matrix -- result/warning messages
        public static final String TIMESLOT_TOOLBAR_DELETE="The selected time slots have been successfully deleted.";
        public static final String TIMESLOT_ADD_SUCCESS = "info.timeslot.add.success";
        public static final String TIMESLOT_EDIT_SUCCESS = "info.timeslot.edit.success";
        public static final String TIMESLOT_DUPLICATE_ERROR = "error.timeslot.duplicate";
    }
}
