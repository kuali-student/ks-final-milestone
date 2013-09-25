package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

import java.util.ArrayList;
import java.util.List;

public class FormatOfferingTypeForCreateCOTypeKeyValues extends AbstractFormatOfferingTypeKeyValues {

    @Override
    protected List<FormatInfo> getFormats(ViewModel model) {
        MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
        CourseOfferingCreateWrapper coWrapper = (CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        if(coWrapper.getCourse() != null){
            return coWrapper.getCourse().getFormats();
        }   else {
            return null;
        }

    }

    @Override
    protected List<String> getExistingFormatIdsFromFormatOfferings(ViewModel model) throws Exception {

        MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
        CourseOfferingCreateWrapper coWrapper = (CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        List<String> availableFormatTypes = new ArrayList<String>();
        List <FormatOfferingWrapper> formatOfferingList= coWrapper.getFormatOfferingWrappers();

        for (FormatOfferingWrapper foWrapper : formatOfferingList){
            availableFormatTypes.add(foWrapper.getFormatOfferingInfo().getFormatId());
        }

        return availableFormatTypes;
    }
}
