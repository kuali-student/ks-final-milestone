package org.kuali.student.enrollment.class2.courseoffering.keyvalue;


import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

import java.util.ArrayList;
import java.util.List;


public class FormatOfferingTypeForEditCOTypeKeyValues extends AbstractFormatOfferingTypeKeyValues {
    private static final long serialVersionUID = 1L;

    @Override
    protected List<FormatInfo> getFormats(ViewModel model) {
        MaintenanceDocumentForm form = (MaintenanceDocumentForm) model;
        CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper) form.getDocument().getNewMaintainableObject().getDataObject();

        if (coEditWrapper.getCourse() != null) {
            return coEditWrapper.getCourse().getFormats();
        } else {
            return null;
        }

    }

    @Override
    protected List<String> getExistingFormatIdsFromFormatOfferings(ViewModel model) throws Exception {
        MaintenanceDocumentForm form = (MaintenanceDocumentForm) model;
        CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        List<String> availableFormatTypes = new ArrayList<String>();
        List<FormatOfferingWrapper> formatOfferingList = coEditWrapper.getFormatOfferingList();
        if (formatOfferingList != null) {
            for (FormatOfferingWrapper foWrapper : formatOfferingList) {
                if(!foWrapper.getRenderHelper().isNewRow()){
                availableFormatTypes.add(foWrapper.getFormatOfferingInfo().getFormatId());
                }
            }
        }
        return availableFormatTypes;
    }


}

