package org.kuali.student.enrollment.class2.autogen.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ARGFormatsForCreateRGKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private transient CourseOfferingService courseOfferingService;
    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        ARGCourseOfferingManagementForm rgForm = (ARGCourseOfferingManagementForm) model;

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        if (rgForm.getFoId2aoTypeMap() != null) {
            for (FormatOfferingInfo formatOfferingInfo : rgForm.getFoId2aoTypeMap().values()) {
                //In keyValues, key is the FormatOfferingInfo.id and value is the FormatOfferingInfo.name
                keyValues.add(new ConcreteKeyValue(formatOfferingInfo.getId(), formatOfferingInfo.getName()));
            }
        }

        return keyValues;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, "CourseOfferingService"));
        }
        return courseOfferingService;
    }
}

