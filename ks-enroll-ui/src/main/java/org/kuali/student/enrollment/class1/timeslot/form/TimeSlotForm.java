package org.kuali.student.enrollment.class1.timeslot.form;

import edu.emory.mathcs.backport.java.util.Collections;
import org.kuali.student.common.uif.form.KSUifForm;
import org.kuali.student.enrollment.class1.timeslot.dto.TimeSlotWrapper;

import java.util.List;

/**
 * Form for Manage Time Slots.
 */
public class TimeSlotForm extends KSUifForm {

    //  Term type multi-select drop-down selections.
    private List<String> termTypeSelections = Collections.emptyList();

    //  Storage for Time Slot search results.
    private List<TimeSlotWrapper> timeSlotResults =  Collections.emptyList();

    public List<TimeSlotWrapper> getTimeSlotResults() {
        return timeSlotResults;
    }

    public void setTimeSlotResults(List<TimeSlotWrapper> timsSlotResults) {
        this.timeSlotResults = timeSlotResults;
    }

    public List<String> getTermTypeSelections() {
        return termTypeSelections;
    }

    public void setTermTypeSelections(List<String> termTypeSelections) {
        this.termTypeSelections = termTypeSelections;
    }
}
