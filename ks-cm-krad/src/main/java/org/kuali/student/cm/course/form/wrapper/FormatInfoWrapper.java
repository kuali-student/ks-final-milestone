package org.kuali.student.cm.course.form.wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper around Format for review Proposal
 */
public class FormatInfoWrapper implements CourseCompareCollectionElement{

    private List<ActivityInfoWrapper> activities;
    private boolean fakeObjectForCompare;

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

    /**
     * @see org.kuali.student.cm.course.form.wrapper.CourseCompareCollectionElement#isHightlightRow()
     * @return
     */
    @Override
    public boolean isHightlightRow() {
        throw new UnsupportedOperationException("hightlightRow doesnot support for " + this.getClass().getSimpleName());
    }

    /**
     * @see CourseCompareCollectionElement#setHightlightRow(boolean)
     * @param hightlightRow
     */
    @Override
    public void setHightlightRow(boolean hightlightRow) {
        throw new UnsupportedOperationException("hightlightRow doesnot support for " + this.getClass().getSimpleName());
    }

    /**
     * @see org.kuali.student.cm.course.form.wrapper.CourseCompareCollectionElement#isFakeObjectForCompare()
     * @return
     */
    @Override
    public boolean isFakeObjectForCompare() {
        return fakeObjectForCompare;
    }

    /**
     * @see CourseCompareCollectionElement#setFakeObjectForCompare(boolean)
     * @param fakeObjectForCompare
     */
    @Override
    public void setFakeObjectForCompare(boolean fakeObjectForCompare) {
        this.fakeObjectForCompare = fakeObjectForCompare;
    }
}