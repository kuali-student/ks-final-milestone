package org.kuali.student.enrollment.class2.registration.admin.valueFinder;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 21 July 2014
 * <p/>
 * Implementation of the RegistrationGradingOptionsFinder used to retrieve the Student Registration Grading Options that support the AdminRegistrationView.xml.
 */
public class RegistrationGradingOptionsFinder extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model, InputField field) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        // Get the term id required to retrieve the grading options.
        AdminRegistrationForm form = (AdminRegistrationForm) model;
        if (form.getTerm() == null || form.getTerm().getId() == null) {
            return keyValues;
        }

        // Get the course code required to retrieve the grading options.
        RegistrationCourse course = (RegistrationCourse) field.getContext().get(UifConstants.ContextVariableNames.LINE);
        if (course.getCode() == null || course.getCode().isEmpty()) {
            return keyValues;
        }

        try {
            if((course.getGradingOptions()==null)){
                course.setGradingOptions(new ArrayList<String>());
            }
            // Create keyvalues from grading options for registration course.
            if ((course.getGradingOption()!=null)&&(!course.getGradingOptions().contains(course.getGradingOption()))) {
                keyValues.add(new ConcreteKeyValue(course.getGradingOption(), CourseRegistrationAndScheduleOfClassesUtil.translateGradingOptionKeyToName(course.getGradingOption())));
            }
            for (String studRegGradOpt : course.getGradingOptions()) {
                keyValues.add(new ConcreteKeyValue(studRegGradOpt, CourseRegistrationAndScheduleOfClassesUtil.translateGradingOptionKeyToName(studRegGradOpt)));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return keyValues;

    }
}
