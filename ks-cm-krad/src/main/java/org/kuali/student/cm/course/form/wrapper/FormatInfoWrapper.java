package org.kuali.student.cm.course.form.wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper around Format for review Proposal
 */
public class FormatInfoWrapper {
    private List<ActivityInfoWrapper> activities;

    public FormatInfoWrapper(){
    }

    public FormatInfoWrapper(List<ActivityInfoWrapper> activities) {

        this.activities = activities;

    }

    public List<ActivityInfoWrapper> getActivities() {
        if (activities == null) {
            activities = new ArrayList<ActivityInfoWrapper>();
        }
        return activities;
    }

    public void setActivities(List<ActivityInfoWrapper> activities) {
        this.activities = activities;
    }
}