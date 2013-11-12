package org.kuali.student.ap.planner.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingItem;
import org.kuali.student.ap.coursesearch.service.impl.CourseDetailsInquiryHelperImpl;
import org.kuali.student.myplan.plan.form.PlanForm;

public class ActivityOfferingBuilder extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = 8313646732965462320L;
	
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
            display=display+activity.getMeetingDetailsList();

            String key = activity.getPrimaryActivityOfferingId();

            KeyValue newValue = new ConcreteKeyValue(key, display);
            values.add(newValue);
        }

        return values;
    }
}
