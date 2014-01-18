package org.kuali.student.enrollment.registration.client.service.impl;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationClientService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesServiceConstants;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.statistics.RegEngineMqStatisticsGenerator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

import javax.jms.Connection;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by swedev on 1/3/14.
 */
public class CourseRegistrationClientServiceImpl implements CourseRegistrationClientService {

    private ScheduleOfClassesService scheduleOfClassesService;
    private CourseRegistrationService courseRegistrationService;

    @Override
    public RegistrationResponseInfo registerForRegistrationGroupByTermCodeAndCourseCodeAndRegGroupName(String termCode,String courseCode, String regGroupName) throws Exception {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        if(contextInfo.getPrincipalId() == null || "".equals(contextInfo.getPrincipalId())){
            throw new LoginException("User must be logged in to access this service");
        }

        // get the registration group
        RegGroupSearchResult regGroupSearchResult = getScheduleOfClassesService().searchForRegistrationGroupByTermAndCourseAndRegGroup(termCode, courseCode, regGroupName);

        //Create the request object
        RegistrationRequestInfo regReqInfo = createAddRegistrationRequest(contextInfo.getPrincipalId(),
                getScheduleOfClassesService().getTermIdByTermCode(termCode),regGroupSearchResult.getRegGroupId()  );

        // persist the request object in the service
        RegistrationRequestInfo newRegReq = getCourseRegistrationService().createRegistrationRequest(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY, regReqInfo, contextInfo);

        // submit the request to the registration engine.
        RegistrationResponseInfo registrationResponseInfo = getCourseRegistrationService().submitRegistrationRequest(newRegReq.getId(), contextInfo);

        return registrationResponseInfo;
    }


    @Override
    public Response getRegEngineStats() throws Exception {
        List<RegEngineMqStatisticsGenerator.RegistrationEngineStatsType> statTypesToRequest = new LinkedList<RegEngineMqStatisticsGenerator.RegistrationEngineStatsType>();
        statTypesToRequest.add( RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.BROKER );
        statTypesToRequest.add( RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.INITIALIZATION_QUEUE );
        statTypesToRequest.add( RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.VERIFICATION_QUEUE );
        statTypesToRequest.add( RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.SEAT_CHECK_QUEUE );


        RegEngineMqStatisticsGenerator generator = new RegEngineMqStatisticsGenerator();
        generator.initiateRequestForStats( statTypesToRequest );

        return Response.ok( generator.getStats() ).build();
    }


    /**
     * This method creates a registration request for the add operation of a single registration group.
     * @param principalId
     * @param termId
     * @param regGroupid
     * @return
     */
    private RegistrationRequestInfo createAddRegistrationRequest(String principalId, String termId, String regGroupid){
        RegistrationRequestInfo regReqInfo = new RegistrationRequestInfo();
        regReqInfo.setRequestorId(principalId);
        regReqInfo.setTermId(termId); // bad bc we have it from the load call above
        regReqInfo.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY); // new reg request
        regReqInfo.setTypeKey(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);

        RegistrationRequestItemInfo registrationRequestItem = new RegistrationRequestItemInfo();
        registrationRequestItem.setTypeKey(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY);
        registrationRequestItem.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        registrationRequestItem.setRegistrationGroupId(regGroupid);
        registrationRequestItem.setPersonId(principalId);

        regReqInfo.getRegistrationRequestItems().add(registrationRequestItem);

        return regReqInfo;
    }

    public ScheduleOfClassesService getScheduleOfClassesService() {

        if (scheduleOfClassesService == null){
            scheduleOfClassesService = (ScheduleOfClassesService) GlobalResourceLoader.getService(new QName(ScheduleOfClassesServiceConstants.NAMESPACE, ScheduleOfClassesServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return scheduleOfClassesService;
    }

    public void setScheduleOfClassesService(ScheduleOfClassesService scheduleOfClassesService) {
        this.scheduleOfClassesService = scheduleOfClassesService;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null){
            courseRegistrationService = (CourseRegistrationService) GlobalResourceLoader.getService(new QName(CourseRegistrationServiceConstants.NAMESPACE, CourseRegistrationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

}


