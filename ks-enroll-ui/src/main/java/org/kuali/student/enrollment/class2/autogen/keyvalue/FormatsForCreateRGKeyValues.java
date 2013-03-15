package org.kuali.student.enrollment.class2.autogen.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormatsForCreateRGKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private transient CourseOfferingService courseOfferingService;
    private static final long serialVersionUID = 1L;
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        ARGCourseOfferingManagementForm rgForm = (ARGCourseOfferingManagementForm) model;

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        CourseOfferingInfo selectedCourseOffering = rgForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            String courseOfferingId = selectedCourseOffering.getId();
            List<FormatOfferingInfo> formatOfferingInfos =
                    getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, contextInfo);
            for (FormatOfferingInfo formatOfferingInfo : formatOfferingInfos) {
                //In keyValues, key is the FormatOfferingInfo.id and value is the FormatOfferingInfo.name
                keyValues.add(new ConcreteKeyValue(formatOfferingInfo.getId(), formatOfferingInfo.getName()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting Formats for course offering", e);
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

