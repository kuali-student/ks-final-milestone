package org.kuali.student.myplan.plan.util;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.util.UifKeyValue;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.myplan.course.dataobject.ActivityOfferingItem;
import org.kuali.student.myplan.course.service.CourseDetailsInquiryHelperImpl;
import org.kuali.student.myplan.plan.form.PlanForm;

import java.util.ArrayList;
import java.util.List;

public class ActivityOfferingBuilder extends UifKeyValuesFinderBase {
    /**
     * Builds a list of key values representations for valid value selections using the given view model
     * to retrieve values from other fields and conditionally building the options
     *
     * @return List of KeyValue objects
     */
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue>  values = new ArrayList<KeyValue>();
        if(!(model instanceof PlanForm))return values;

        PlanForm form = (PlanForm) model;
        CourseDetailsInquiryHelperImpl courseDetailsInquiryHelper = new CourseDetailsInquiryHelperImpl();

        List<ActivityOfferingItem> activities = courseDetailsInquiryHelper.getActivityOfferingItemsById(
                form.getCourseId(),form.getAtpId());

        for(int i = 0; i < activities.size(); i++){
            ActivityOfferingItem activity = activities.get(i);
            String display = "";
            display=display+activity.getCode()+" ";
            display=display+activity.getInstructor()+" ";
            display=display+activity.getMeetingDetailsListOutput();

            String key = activity.getActivityIdentifier();

            KeyValue newValue = new ConcreteKeyValue(key, display);
            values.add(newValue);
        }

        return values;
    }
}
