package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormatsForCreateRGKeyValues extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        CourseOfferingManagementForm coForm = (CourseOfferingManagementForm) model;
        CourseOfferingManagementViewHelperServiceImpl helperService = ((CourseOfferingManagementViewHelperServiceImpl)coForm.getView().getViewHelperService());

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        CourseOfferingInfo selectedCourseOffering = coForm.getTheCourseOffering();

        try {
            String courseOfferingId = selectedCourseOffering.getId();
            ContextInfo contextInfo = helperService.getContextInfo();
            CourseOfferingService courseOfferingService = helperService.getCourseOfferingService();
            List<FormatOfferingInfo> formatOfferingInfos =
                    courseOfferingService.getFormatOfferingsByCourseOffering(courseOfferingId, contextInfo);
            for (FormatOfferingInfo formatOfferingInfo : formatOfferingInfos) {
                //In keyValues, key is the FormatOfferingInfo.id and value is the FormatOfferingInfo.name
                keyValues.add(new ConcreteKeyValue(formatOfferingInfo.getId(), formatOfferingInfo.getName()));
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return keyValues;
    }
}

