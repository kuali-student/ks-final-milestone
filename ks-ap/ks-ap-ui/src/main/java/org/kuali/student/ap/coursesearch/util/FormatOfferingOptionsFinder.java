package org.kuali.student.ap.coursesearch.util;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.ap.coursesearch.dataobject.CourseOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.CourseTermDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.FormatOfferingInfoWrapper;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsForm;

import java.util.ArrayList;
import java.util.List;

/**
 * OptionsFinder used to populate the available format offerings for a given term and course offering.
 * Data attributes on the field are used to pass through the term and course offering IDs.
 */
public class FormatOfferingOptionsFinder extends UifKeyValuesFinderBase {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<KeyValue> getKeyValues(ViewModel model, InputField field) {
        List<KeyValue> formatOfferingList = new ArrayList<KeyValue>();
        String termId = field.getDataAttributes().get("termId");
        String courseOfferingId = field.getDataAttributes().get("courseOfferingId");
        List<FormatOfferingInfoWrapper> formatOfferings = getFormatOfferingsByTermAndCourseOffering((CourseSectionDetailsForm)model, termId, courseOfferingId);

        for (FormatOfferingInfoWrapper formatOfferingInfoWrapper : formatOfferings) {
            formatOfferingList.add(new ConcreteKeyValue(formatOfferingInfoWrapper.getFormatOfferingId(), formatOfferingInfoWrapper.getFormatOfferingName()));
        }
        return formatOfferingList;
    }

    private List<FormatOfferingInfoWrapper> getFormatOfferingsByTermAndCourseOffering(CourseSectionDetailsForm form, String termId, String courseOfferingId) {
        List<CourseTermDetailsWrapper> terms = form.getCourseTermDetailsWrappers();
        for (CourseTermDetailsWrapper term : terms) {
            if (!term.getTermId().equals(termId))
                continue;
            for (CourseOfferingDetailsWrapper courseOffering : term.getCourseOfferingDetailsWrappers()) {
                if (courseOffering.getCourseOfferingId().equals(courseOfferingId)) {
                    return courseOffering.getFormatOfferingInfoWrappers();
                }
            }
        }
        return new ArrayList<FormatOfferingInfoWrapper>();
    }
}
