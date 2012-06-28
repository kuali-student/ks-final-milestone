package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormatOfferingTypeForCreateCOTypeKeyValues  extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        MaintenanceForm form = (MaintenanceForm)model;

        CourseOfferingCreateWrapper coWrapper = (CourseOfferingCreateWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        if (coWrapper.getCourse() != null){
            for (FormatInfo format : coWrapper.getCourse().getFormats()) {
                ConcreteKeyValue keyValue = new ConcreteKeyValue();
                keyValue.setKey(format.getId());
                keyValue.setValue(format.getTypeKey());
                keyValues.add(keyValue);
            }
        }

        return keyValues;
    }
}
