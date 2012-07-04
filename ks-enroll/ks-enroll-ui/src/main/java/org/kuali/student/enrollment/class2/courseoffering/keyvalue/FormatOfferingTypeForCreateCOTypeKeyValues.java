package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.lum.course.dto.FormatInfo;

import java.util.ArrayList;
import java.util.List;

public class FormatOfferingTypeForCreateCOTypeKeyValues extends AbstractFormatOfferingTypeKeyValues {

    @Override
    protected List<FormatInfo> getFormats(ViewModel model) {
        MaintenanceForm form = (MaintenanceForm)model;
        CourseOfferingCreateWrapper coWrapper = (CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        if(coWrapper.getCourse() != null){
            return coWrapper.getCourse().getFormats();
        }   else {
            return null;
        }

    }

    @Override
    protected List<String> getExistingFormatIdsFromFormatOfferings(ViewModel model) throws Exception {

        MaintenanceForm form = (MaintenanceForm)model;
        CourseOfferingCreateWrapper coWrapper = (CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        List<String> availableFormatTypes = new ArrayList<String>();
        List <FormatOfferingWrapper> formatOfferingWrapperList= coWrapper.getFormatOfferingList();

        for (FormatOfferingWrapper formatOfferingInfo : formatOfferingWrapperList){
            availableFormatTypes.add(formatOfferingInfo.getFormatOfferingInfo().getFormatId());
        }

        return availableFormatTypes;
    }
}
