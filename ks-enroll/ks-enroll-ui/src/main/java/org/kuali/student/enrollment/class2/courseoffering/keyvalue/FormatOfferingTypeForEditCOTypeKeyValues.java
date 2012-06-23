package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.lum.course.dto.FormatInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 6/22/12
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormatOfferingTypeForEditCOTypeKeyValues extends AbstractFormatOfferingTypeKeyValues {

    @Override
    List<FormatInfo> getFormats(ViewModel model) {
        MaintenanceForm form = (MaintenanceForm)model;
        CourseOfferingEditWrapper coWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        if(coWrapper.getCourse() != null){
            return coWrapper.getCourse().getFormats();
        }   else {
            return null;
        }

    }
}
