package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgresham
 * Date: 7/30/13
 * Time: 4:05 PM
 */
public class CourseOfferingCodesKeyValues extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues( ViewModel model ) {
        MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
        CourseOfferingEditWrapper wrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        return wrapper.getRenderHelper().getRelatedCOs();
    }
}
