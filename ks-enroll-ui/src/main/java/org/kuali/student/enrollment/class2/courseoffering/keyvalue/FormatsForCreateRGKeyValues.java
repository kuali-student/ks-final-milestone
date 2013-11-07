package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormatsForCreateRGKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        CourseOfferingManagementForm rgForm = (CourseOfferingManagementForm) model;

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        if (rgForm.getFoId2aoTypeMap() != null) {
            for (FormatOfferingInfo formatOfferingInfo : rgForm.getFoId2aoTypeMap().values()) {
                //In keyValues, key is the FormatOfferingInfo.id and value is the FormatOfferingInfo.name
                keyValues.add(new ConcreteKeyValue(formatOfferingInfo.getId(), formatOfferingInfo.getName()));
            }
        }

        return keyValues;
    }
}

