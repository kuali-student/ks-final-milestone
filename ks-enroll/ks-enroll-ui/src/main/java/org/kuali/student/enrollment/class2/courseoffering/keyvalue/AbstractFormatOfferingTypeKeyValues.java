package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.lum.course.dto.FormatInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 6/22/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFormatOfferingTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<FormatInfo> formats = getFormats(model);

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        for (FormatInfo format : formats) {
            ConcreteKeyValue keyValue = new ConcreteKeyValue();
            keyValue.setKey(format.getId());
            keyValue.setValue(format.getType());
            keyValues.add(keyValue);
        }

        return keyValues;
    }

    abstract List<FormatInfo> getFormats(ViewModel model);
}
