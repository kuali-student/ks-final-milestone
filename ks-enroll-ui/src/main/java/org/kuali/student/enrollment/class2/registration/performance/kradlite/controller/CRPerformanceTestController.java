package org.kuali.student.enrollment.class2.registration.performance.kradlite.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.ui.registration.ScheduleOfClassesService;
import org.kuali.student.enrollment.ui.registration.dto.CourseSearchResult;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.search.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/action")
public class CRPerformanceTestController {

    public static final String KUALI_ATP_2012_FALL = "kuali.atp.2012Fall";
    ScheduleOfClassesService scheduleOfClassesService;

    @RequestMapping(method = RequestMethod.GET)
    public String printHello(@RequestParam Map<String,String> allRequestParams, ModelMap model) {
        String courseCode = allRequestParams.get("inputOne")==null?"":allRequestParams.get("inputOne");
        String termId = KUALI_ATP_2012_FALL;

        List<CourseSearchResult> collectionList = Collections.emptyList();
        if (!StringUtils.isEmpty(courseCode)) {
            try {
                collectionList = getScheduleOfClassesService().loadCourseOfferingsByTermAndCourseCode(termId, courseCode);
            } catch (Exception e) {
                throw new RuntimeException("Error", e);
            }
        }
        model.addAttribute("courseResults", collectionList); // add collection to model.
        model.addAttribute("inputOne", courseCode); // add collection to form.

        return "cr-performance-test";
    }

    private ScheduleOfClassesService getScheduleOfClassesService() {
        if (scheduleOfClassesService == null) {
            scheduleOfClassesService = (ScheduleOfClassesService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "scheduleOfClasses", ScheduleOfClassesService.class.getSimpleName()));
        }
        return scheduleOfClassesService;
    }
}
