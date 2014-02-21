package org.kuali.student.ap.coursesearch.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jasonosgood
 * Date: 12/5/12
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingTerm implements Serializable {

	private static final long serialVersionUID = 7057613732672051148L;

	//    private YearTerm yearTerm;
	private String atpId;
    private String term;
    private String courseComments;
    private String curriculumComments;

    /*NOTE: Added the institute code in this course offering term
    because in UI accessing the parent element is difficult.*/
    private String instituteCode;
    /*Used to differentiate the course sections color code for this term if they are planned
    * If planned type : Planned , if backup type : Backup If none : null is returned*/
    private String coursePlanType;
    private List<ActivityOfferingItem> activityOfferingItemList;


//    public YearTerm getYearTerm() {
//        return yearTerm;
//    }
//
//    public void setYearTerm(YearTerm yearTerm) {
//        this.yearTerm = yearTerm;
//    }
//

	public String getAtpId() {
		return atpId;
	}

	public String getAtpIdXmlSafe() {
		return atpId == null ? null : atpId.replace('.', '-');
	}

	public void setAtpId(String atpId) {
		this.atpId = atpId;
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

	public String getInstituteCodeXmlSafe() {
		return instituteCode == null ? null : instituteCode.replace('.', '-');
	}

    public void setInstituteCode(String instituteCode) {
        this.instituteCode = instituteCode;
    }

    public String getCurriculumComments() {
        return curriculumComments;
    }

    public void setCurriculumComments(String curriculumComments) {
        this.curriculumComments = curriculumComments;
    }

    public String getCoursePlanType() {
        return coursePlanType;
    }

    public void setCoursePlanType(String coursePlanType) {
        this.coursePlanType = coursePlanType;
    }
}
