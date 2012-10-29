package org.kuali.student.enrollment.class2.courseoffering.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

/**
 * Created with IntelliJ IDEA.
 * User: aliabad4
 * Date: 10/29/12
 * Time: 12:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingBaseForm extends UifFormBase {

    private boolean withinPortal = true;
    private CourseOfferingInfo theCourseOffering;

    public CourseOfferingInfo getTheCourseOffering(){
        return theCourseOffering;
    }

    public void setTheCourseOffering(CourseOfferingInfo theCourseOffering)  {
        this.theCourseOffering = theCourseOffering;
    }

    public boolean isWithinPortal() {
        return withinPortal;
    }

    public void setWithinPortal(boolean withinPortal) {
        this.withinPortal = withinPortal;
    }
}
