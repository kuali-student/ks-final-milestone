package org.kuali.student.enrollment.class2.courseoffering.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;


import java.util.ArrayList;
import java.util.List;

public class CourseOfferingManagementForm extends UifFormBase {
    private String termCode;
    private TermInfo termInfo;
    private String courseOfferingCode;
    private String subjectCode;
    private String radioSelection;
    private String inputCode;
    private boolean haveValidTerm;
    private List<CourseOfferingInfo> courseOfferingList;
    private CourseOfferingInfo theCourseOffering;    
    private List<ActivityOfferingInfo> activityOfferingList;
    
    public CourseOfferingManagementForm (){
        courseOfferingList = new ArrayList<CourseOfferingInfo>();
        activityOfferingList = new ArrayList<ActivityOfferingInfo>();
        haveValidTerm = false;
    }

    public String getTermCode(){
        return termCode;
    }

    public void setTermCode(String termCode){
        this.termCode = termCode;
    }

    public TermInfo getTermInfo(){
        return termInfo;    
    }
    
    public void setTermInfo(TermInfo termInfo){
        this.termInfo = termInfo;
    }
    
    public String getCourseOfferingCode(){
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode){
        this.courseOfferingCode = courseOfferingCode;
    }
    
    public String getSubjectCode(){
        return subjectCode;
    }
    
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    
    public String getRadioSelection(){
        return radioSelection;
    } 
    
    public void setRadioSelection(String radioSelection){
        this.radioSelection = radioSelection;
    }
    
    public String getInputCode(){
        return inputCode;
    }
    
    public void setInputCode(String inputCode){
        this.inputCode = inputCode;
    }
    
    public boolean isHaveValidTerm(){
        return haveValidTerm;
    }

    public void setHaveValidTerm(boolean haveValidTerm){
        this.haveValidTerm = haveValidTerm;
    }
    
    public List<CourseOfferingInfo> getCourseOfferingList(){
        return courseOfferingList;
    }
    
    public void setCourseOfferingList(List<CourseOfferingInfo> courseOfferingList) {
        this.courseOfferingList = courseOfferingList;
    }
    
    public CourseOfferingInfo getTheCourseOffering(){
        return theCourseOffering;
    }

    public void setTheCourseOffering(CourseOfferingInfo theCourseOffering)  {
        this.theCourseOffering = theCourseOffering;
    }
    
    public List<ActivityOfferingInfo> getActivityOfferingList(){
        return activityOfferingList;
    }

    public void setActivityOfferingList (List<ActivityOfferingInfo> activityOfferingList) {
        this.activityOfferingList = activityOfferingList;
    }
}
