package org.kuali.student.enrollment.class2.examoffering.service.facade;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FinalExam;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.lum.course.infc.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SW Genis on 2014/04/25.
 */
public class ExamOfferingContext {

    private CourseOfferingInfo courseOffering;
    private String termId;
    private String examPeriodId;

    /**
     * For the courseOfferingId, this is a map from FO Ids (of the CO) to the AO Infos belonging to the FO.
     * Saves a service call by passing this info in.
     */
    private Map<String,List<ActivityOfferingInfo>> foIdToListOfAOs = null;

    private Driver driver;
    public enum Driver {PER_CO, PER_FO, PER_AO, NONE}

    public ExamOfferingContext(CourseOfferingInfo courseOffering){
        super();
        this.courseOffering = courseOffering;
        this.termId = courseOffering.getTermId();
        this.driver = this.calculateEODriver();
    }

    public ExamOfferingContext(CourseOfferingInfo courseOffering, ActivityOfferingInfo activityOfferingInfo){
        super();
        this.courseOffering = courseOffering;
        this.termId = activityOfferingInfo.getTermId();
        this.driver = this.calculateEODriver();
        this.setActivityOffering(activityOfferingInfo);
    }

    public CourseOfferingInfo getCourseOffering() {
        return courseOffering;
    }

    public void setCourseOffering(CourseOfferingInfo courseOffering) {
        this.courseOffering = courseOffering;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getExamPeriodId() {
        return examPeriodId;
    }

    public void setExamPeriodId(String examPeriodId) {
        this.examPeriodId = examPeriodId;
    }

    public Map<String, List<ActivityOfferingInfo>> getFoIdToListOfAOs() {
        return foIdToListOfAOs;
    }

    public void setFoIdToListOfAOs(Map<String, List<ActivityOfferingInfo>> foIdToListOfAOs) {
        this.foIdToListOfAOs = foIdToListOfAOs;
    }

    public void setActivityOffering(ActivityOfferingInfo activityOffering){
        //Build up the map of AO for the methods so it does not have to make a DB call.
        Map<String,List<ActivityOfferingInfo>> foIdToListOfAOs = new HashMap<String, List<ActivityOfferingInfo>>();
        List<ActivityOfferingInfo> aoInfos = new ArrayList<ActivityOfferingInfo>();
        aoInfos.add(activityOffering);
        foIdToListOfAOs.put(activityOffering.getFormatOfferingId(),aoInfos);
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    private Driver calculateEODriver() {
        if (FinalExam.STANDARD.toString().equals(this.getCourseOffering().getFinalExamType())) {

            //Check driver.
            String examDriver = this.getCourseOffering().getAttributeValue(CourseOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR);
            if (LuServiceConstants.LU_EXAM_DRIVER_AO_KEY.equals(examDriver)) {
                return Driver.PER_AO;
            } else if (LuServiceConstants.LU_EXAM_DRIVER_CO_KEY.equals(examDriver)) {
                return Driver.PER_CO;
            }
            return Driver.NONE;
        } else {

            //If it is not a Standard exam, the course offering should not have any exams.
            return Driver.NONE;
        }
    }

    /**
     * The Final_Exam_Use_Matrix attribute is set to TRUE when the user selected the toggle on the edit Course Offering screen.
     * If toggle is deselected, the system should not use the matrix to do any slotting on this course offering.
     */
    public boolean useFinalExamMatrix() {
        return Boolean.parseBoolean(this.getCourseOffering().getAttributeValue(CourseOfferingServiceConstants.FINAL_EXAM_USE_MATRIX));
    }

}
