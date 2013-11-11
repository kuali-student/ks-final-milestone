package org.kuali.student.myplan.plan.dataobject;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingItem;

/**
 * Created by IntelliJ IDEA.
 * User: hemanthg
 * Date: 4/27/12
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class AcademicRecordDataObject {
    //  A unique identifier in case a particular record needs to appear more than once in the DOM.
    private String uuid;
    private transient String personId;
    private transient String courseId;
    private transient String courseTitle;
    private transient String courseCode;
    private transient String activityCode;
    private transient String atpId;
    private transient String grade;
    private transient String credit;
    private transient boolean isRepeated;
    private transient ActivityOfferingItem activityOfferingItem;

    public AcademicRecordDataObject() {
        uuid = UUIDHelper.genStringUUID();
    }

    public String getUuid() {
        return uuid;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public boolean isRepeated() {
        return isRepeated;
    }

    public void setRepeated(boolean repeated) {
        isRepeated = repeated;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public ActivityOfferingItem getActivityOfferingItem() {
        return activityOfferingItem;
    }

    public void setActivityOfferingItem(ActivityOfferingItem activityOfferingItem) {
        this.activityOfferingItem = activityOfferingItem;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getUuidXmlSafe(){
        return this.uuid.replace(".","_");
    }
}
