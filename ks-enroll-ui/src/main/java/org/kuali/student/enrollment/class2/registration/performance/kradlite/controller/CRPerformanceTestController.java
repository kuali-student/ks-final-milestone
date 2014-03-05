package org.kuali.student.enrollment.class2.registration.performance.kradlite.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesServiceConstants;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/action")
public class CRPerformanceTestController {

    private static final Logger LOG = Logger.getLogger(CRPerformanceTestController.class);

    public static final String KUALI_ATP_2012_FALL = "kuali.atp.2012Fall";

    ScheduleOfClassesService scheduleOfClassesService;
    CourseRegistrationService courseRegistrationService;

    private JmsTemplate jmsTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public String printHello(@RequestParam Map<String, String> allRequestParams, ModelMap model) {
        String courseCode = allRequestParams.get("inputOne") == null ? "" : allRequestParams.get("inputOne");
        String termId = KUALI_ATP_2012_FALL;

        List<CourseSearchResult> collectionList = Collections.emptyList();
        if (!StringUtils.isEmpty(courseCode)) {
            try {
                collectionList = getScheduleOfClassesService().searchForCourseOfferingsByTermIdAndCourse(termId, courseCode);
            } catch (Exception e) {
                throw new RuntimeException("Error", e);
            }
        }
        model.addAttribute("courseResults", collectionList); // add collection to model.
        model.addAttribute("inputOne", courseCode); // add collection to form.
        String appUrl = ConfigContext.getCurrentContextConfig().getProperty("application.url");
        model.addAttribute("appUrl", appUrl);
        return "cr-performance-test";
    }

    @RequestMapping(value = "/messageTest", method = RequestMethod.GET)
    public String messageTest(@RequestParam Map<String, String> allRequestParams, ModelMap model) {
        //Initiate a call to the message queue ("start processing")
        String regReqId = String.valueOf(System.currentTimeMillis());
        try{
            getCourseRegistrationService().submitRegistrationRequest(regReqId, getCurrentContextInfo());
        }catch (Exception ex){
            ex.printStackTrace();
            LOG.error(ex.getMessage());
        }

        return "message-test";
    }

    private ContextInfo getCurrentContextInfo(){
        ContextInfo ci = ContextUtils.createDefaultContextInfo();
        ci.setPrincipalId(SecurityUtils.getCurrentUserId());
        ci.setAuthenticatedPrincipalId(SecurityUtils.getCurrentUserId());

        return ci;
    }

    @RequestMapping(value = "/checkStatus", method = RequestMethod.GET)
    @ResponseBody
    public String checkStatus(@RequestParam Map<String, String> allRequestParams, ModelMap model) {
        final String destination = CourseRegistrationConstants.USER_MESSAGE_QUEUE_PREFIX + SecurityUtils.getCurrentUserId();
        String message = (String)jmsTemplate.receiveAndConvert(destination);

        if(message == null || "".equals(message)){
            message = "[" + System.currentTimeMillis() + "] There are no messages in user queue.";
        }
        return message;
    }

    private ScheduleOfClassesService getScheduleOfClassesService() {
        if (scheduleOfClassesService == null) {
            scheduleOfClassesService = (ScheduleOfClassesService) GlobalResourceLoader.getService(new QName(ScheduleOfClassesServiceConstants.NAMESPACE, ScheduleOfClassesServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return scheduleOfClassesService;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setScheduleOfClassesService(ScheduleOfClassesService scheduleOfClassesService) {
        this.scheduleOfClassesService = scheduleOfClassesService;
    }


    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            courseRegistrationService = (CourseRegistrationService) GlobalResourceLoader.getService(new QName(CourseRegistrationServiceConstants.NAMESPACE, CourseRegistrationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }
}
