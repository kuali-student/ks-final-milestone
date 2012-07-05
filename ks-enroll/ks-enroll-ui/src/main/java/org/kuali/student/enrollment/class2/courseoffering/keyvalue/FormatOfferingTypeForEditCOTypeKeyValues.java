package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingEditMaintainableImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormatOfferingTypeForEditCOTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private transient CourseService courseService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        MaintenanceForm form = (MaintenanceForm)model;
        CourseOfferingEditWrapper coWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("", "Select Format Type"));
        CourseOfferingInfo selectedCourseOffering = coWrapper.getCoInfo();

        List<FormatInfo> formatInfos;
        try {

            CourseInfo courseInfo = this.getCourseService().getCourse(selectedCourseOffering.getCourseId());
            formatInfos = courseInfo.getFormats();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            for (FormatInfo formatInfo : formatInfos) {
                keyValues.add(new ConcreteKeyValue(formatInfo.getId(), formatInfo.getName()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return keyValues;
    }

    public CourseService getCourseService() {
        if(courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return this.courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}

