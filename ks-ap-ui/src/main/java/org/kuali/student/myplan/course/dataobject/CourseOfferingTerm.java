package org.kuali.student.myplan.course.dataobject;

import org.kuali.student.ap.framework.context.YearTerm;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: jasonosgood
 * Date: 12/5/12
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingTerm {
    private YearTerm yearTerm;
    private String term;
    private String courseComments;

    /*NOTE: Added the institute code in this course offering term
    because in UI accessing the parent element is difficult.*/
    private String instituteCode;
    private List<ActivityOfferingItem> activityOfferingItemList;


    public YearTerm getYearTerm() {
        return yearTerm;
    }

    public void setYearTerm(YearTerm yearTerm) {
        this.yearTerm = yearTerm;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCourseComments() {
        return courseComments;
    }

    public void setCourseComments(String courseComments) {
        this.courseComments = courseComments;
    }

    public List<ActivityOfferingItem> getActivityOfferingItemList() {
        if (activityOfferingItemList == null) {
            activityOfferingItemList = new ArrayList<ActivityOfferingItem>();
        }
        return activityOfferingItemList;
    }

    public void setActivityOfferingItemList(List<ActivityOfferingItem> activityOfferingItemList) {
        this.activityOfferingItemList = activityOfferingItemList;
    }

    public String getInstituteCode() {
        return instituteCode;
    }

    public void setInstituteCode(String instituteCode) {
        this.instituteCode = instituteCode;
    }

    public String getComponentId(){
        String componentId=instituteCode;
        componentId=componentId+"_"+yearTerm.getYear();
        componentId=componentId+"_"+yearTerm.getTermName().replace(" ","");

        return componentId;
    }
}
