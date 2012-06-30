package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.lum.course.dto.FormatInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormatOfferingTypeForEditCOTypeKeyValues extends AbstractFormatOfferingTypeKeyValues implements Serializable {

    @Override
    protected List<FormatInfo> getFormats(ViewModel model) {
        MaintenanceForm form = (MaintenanceForm)model;
        CourseOfferingEditWrapper coWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        if(coWrapper.getCourse() != null){
            return coWrapper.getCourse().getFormats();
        }   else {
            return null;
        }

    }

    @Override
    protected List<String> getAvailableFormatTypes(ViewModel model) throws Exception {

        MaintenanceForm form = (MaintenanceForm)model;
        CourseOfferingEditWrapper coWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        List<String> availableFormatTypes = new ArrayList<String>();
        List <FormatOfferingWrapper> formatOfferingWrapperList= coWrapper.getFormatOfferingWrapperList();

        for (FormatOfferingWrapper wrapper : formatOfferingWrapperList){
            availableFormatTypes.add(wrapper.getFormatOfferingInfo().getFormatId());
        }

        return availableFormatTypes;
    }
}

