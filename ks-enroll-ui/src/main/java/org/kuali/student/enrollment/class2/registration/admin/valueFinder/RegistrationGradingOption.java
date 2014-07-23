package org.kuali.student.enrollment.class2.registration.admin.valueFinder;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 21 July 2014
 * <p/>
 * Implementation of the RegistrationGradingOption used to retrieve the Student Registration Grading Options that support the AdminRegistrationView.xml.
 */
public class RegistrationGradingOption extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        AdminRegistrationForm adminRegForm = ((AdminRegistrationForm) model);

        if (adminRegForm.getPerson().getId() != null && adminRegForm.getTerm() != null) {
            List<RegistrationCourse> registrationCourses = new ArrayList<RegistrationCourse>();
            registrationCourses = adminRegForm.getPendingCourses();
            /*TODO To be Implemented with the table Edit inline action is clicked.
            if(adminRegForm.getPendingCourses() != null) {
               registrationCourses = adminRegForm.getPendingCourses();
            }else if (adminRegForm.getCoursesInProcess() != null) {
                registrationCourses = adminRegForm.getCoursesInProcess();
            }
            */
            for (RegistrationCourse registrationCourse : registrationCourses) {
                try {
                    CourseOfferingInfo coInfo = AdminRegResourceLoader.getCourseOfferingService().getCourseOffering(registrationCourse.getRegGroup().getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
                    for (String studRegGradOpt : coInfo.getStudentRegistrationGradingOptions()) {
                        keyValues.add(new ConcreteKeyValue(studRegGradOpt, CourseRegistrationAndScheduleOfClassesUtil.translateGradingOptionKeyToName(studRegGradOpt)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return keyValues;
        }
        return new ArrayList<KeyValue>();
    }
}
