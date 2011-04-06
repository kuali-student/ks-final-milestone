package org.kuali.rice.student.maintenance;

import java.sql.Date;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.bo.DocumentHeader;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

import org.kuali.rice.student.bo.CourseUpdate;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;

public class CourseUpdateMaintainableImpl extends KualiMaintainableImpl {
    private CourseService courseService;
    @Override
    public void doRouteStatusChange(DocumentHeader documentHeader) {
        // when document status is processed, call KS service to update course
        if (documentHeader.getWorkflowDocument().stateIsProcessed()){
            CourseUpdate courseUpdate = (CourseUpdate) getBusinessObject();
            String courseId = courseUpdate.getCourseId();
            String courseTitle = courseUpdate.getCourseTitle();
            String courseCode = courseUpdate.getCourseCode();
            String subjectArea = courseUpdate.getSubjectArea();
            courseService = getCourseService();
            if (courseId != null ){
                try{
                    CourseInfo courseInfo = courseService.getCourse(courseId);
                    if (courseTitle != null){
                        courseInfo.setCourseTitle(courseTitle);
                    }
                    if (courseCode != null){
                        courseInfo.setCode(courseCode);
                    }                   
                    if (subjectArea != null){
                        courseInfo.setSubjectArea(subjectArea);
                    }
                    courseService.updateCourse(courseInfo);
                }catch (Exception e){
                    System.out.println(">>> get exception in CourseUpdateMaintainableImpl: "+e.toString());
                }
            }
                
        }
    }
    
    protected CourseService getCourseService() {
        if (this.courseService == null) {
            this.courseService = (CourseService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/course","CourseService")); 
        }
        return this.courseService;
    }
}
