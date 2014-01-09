package org.kuali.student.ap.bookmark.dto;

import org.kuali.student.ap.common.infc.HasUniqueId;
import org.kuali.student.ap.coursesearch.dataobject.CourseSummaryDetails;
import org.kuali.student.ap.coursesearch.util.CollectionListPropertyEditor;
import org.kuali.student.ap.coursesearch.util.ScheduledTermsPropertyEditor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 12/20/13
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookmarkDetailWrapper implements HasUniqueId, Comparable<BookmarkDetailWrapper> {
    private Date dateAdded;
    private String learningPlanId;
    private String planItemId;
    private String uniqueId;

    private CourseSummaryDetails courseSummaryDetails;

    public String getLearningPlanId() {
        return learningPlanId;
    }

    public void setLearningPlanId(String learningPlanId) {
        this.learningPlanId = learningPlanId;
    }

    public String getPlanItemId() {
        return planItemId;
    }


    public void setPlanItemId(String planItemId) {
        this.planItemId = planItemId;
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public CourseSummaryDetails getCourseSummaryDetails() {
        return courseSummaryDetails;
    }

    public void setCourseSummaryDetails(CourseSummaryDetails courseSummaryDetails) {
        this.courseSummaryDetails = courseSummaryDetails;
    }

    public String getScheduledForUI(){
        ScheduledTermsPropertyEditor editor = new ScheduledTermsPropertyEditor();
        editor.setValue(getCourseSummaryDetails());
        return editor.getAsText();
    }

    public String getProjectedForUI(){
        CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
        editor.setValue(getCourseSummaryDetails().getTermsOffered());
        editor.setEmptyListMessage("Check with the department or your adviser for more information about this course.");
        editor.setApplyClassOnItem(true);
        if (editor.getEmptyListStyleClasses()==null) {
            editor.setEmptyListStyleClasses(new ArrayList<String>());
        }
        editor.getEmptyListStyleClasses().add("empty");

        return editor.getAsText();
    }
    public String getRequisitesForUI(){
        CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
        editor.setValue(getCourseSummaryDetails().getRequisites());
        editor.setEmptyListMessage("None");
        return editor.getAsText();
    }
    public String getAbbrGenEdRequirementsForUI(){
        CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
        editor.setValue(getCourseSummaryDetails().getAbbrGenEdRequirements());
        editor.setEmptyListMessage("&nbsp;");
        return editor.getAsText();
    }


    @Override
    public int compareTo(BookmarkDetailWrapper o) {
        return this.getDateAdded().compareTo(o.getDateAdded());
    }
}
