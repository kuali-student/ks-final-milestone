package org.kuali.student.enrollment.registration.engine.service.impl;

import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseItemInfo;
import org.kuali.student.enrollment.registration.engine.service.RegistrationProcessService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WORK IN PROGRESS - NOT WORKING OR WIRED IN YET
 *
 * This class is an optional part of the registration flow.
 *
 * It's designed to create Reg group queue's for the users.
 *
 * ENGL101-0106:[user1, user2, user3]
 * MATH240-0201:[user4, user5]
 *
 * So each one above is a queue, per reg group. Just used so if user1 submits before user2, they get into ENGL101-0106
 * regardless of processing time.
 *
 */
public class CourseRegistrationOrderingServiceImpl implements RegistrationProcessService{

    private static List<OrderQueueElement> queue = new ArrayList<OrderQueueElement>();
    private static Map<String, List<OrderQueueElement>> queueMap = new HashMap<String, List<OrderQueueElement>>();

    public static int REG_STATUS_PROCESSING = 1;
    public static int REG_STATUS_VALID = 2;


    @Override
    public RegistrationResponseInfo process(String courseRegistrationRequestId) {
        RegistrationResponseInfo regRespInfo = getRegistrationResponseInfo(courseRegistrationRequestId);

        for(RegistrationResponseItemInfo regItem : regRespInfo.getRegistrationResponseItems() ){
            if(queueMap.containsKey(getKey(regItem)) ){

            } else{

            }
        }

        return null;
    }

    protected String getKey(RegistrationResponseItemInfo regItem){
        return regItem.getCourseRegistrationId();
    }

    /**
     * Contact system to get response info object.
     *
     * @param courseRegistrationRequestId
     * @return
     */
    protected RegistrationResponseInfo getRegistrationResponseInfo(String courseRegistrationRequestId){
        RegistrationResponseInfo responseInfo;

        // I'm just going to mock this out for now.
        responseInfo = new RegistrationResponseInfo();
        responseInfo.setRegistrationRequestId(courseRegistrationRequestId);
        responseInfo.setHasFailed(false);

        RegistrationResponseItemInfo regItem = new RegistrationResponseItemInfo();

        regItem.setCourseRegistrationId("ENGL101-0102");
        regItem.setRegistrationRequestItemId(System.currentTimeMillis() + "");
        List<RegistrationResponseItemInfo>  regItemList = new ArrayList<RegistrationResponseItemInfo>();
        regItemList.add(regItem);

        responseInfo.setRegistrationResponseItems(regItemList);

        return responseInfo;
    }

    private class OrderQueueElement {
        String regReqId;
        long currSysTime;
        int regReqStatus;

        private OrderQueueElement(String regReqId) {
            this.regReqId = regReqId;
            currSysTime = System.currentTimeMillis();
            regReqStatus = REG_STATUS_PROCESSING;
        }
    }
}
