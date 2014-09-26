package org.kuali.student.enrollment.class1.lui.aop;

import org.aspectj.lang.JoinPoint;
import org.kuali.student.common.eventing.BaseEventingAspect;
import org.kuali.student.common.eventing.EventMessage;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This AOP aspect broadcasts lui method calls to a JMS broker.
 */
public class LuiBroadcastAspect extends BaseEventingAspect {
    public static final Logger LOGGER = LoggerFactory.getLogger(LuiBroadcastAspect.class);

    /**
     * This method is only valid for the LuiService.updateLui method. It's hard coded to that method structure.
     * If the method structure for updateLui changes, this will break.
     *
     * This method is used for AOP "Around"
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public void updateLuiAdvise(JoinPoint joinPoint, LuiInfo retVal) throws Throwable {
        Object[] args = joinPoint.getArgs();
        ContextInfo contextInfo = (ContextInfo)args[2];

        createAndSendMessage(retVal,EventMessage.Action.UPDATE.toString(), contextInfo);

    }

    /**
     * createLuit(String cluId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo context)
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public void createLuiAdvise(JoinPoint joinPoint, LuiInfo retVal) throws Throwable {
        Object[] args = joinPoint.getArgs();
        ContextInfo contextInfo = (ContextInfo)args[4];

        createAndSendMessage(retVal,EventMessage.Action.CREATE.toString(), contextInfo);

    }

    /**
     * Creates an EventMessage based on luiInfo and contextInfo parameters. Then sends it to the jms broker
     * @param luiInfo
     * @param contextInfo
     */
    protected  void createAndSendMessage(LuiInfo luiInfo, String action,  ContextInfo contextInfo){

        // build an event message to broadcast
        EventMessage eventMessage = new EventMessage();
        eventMessage.setAction(action);
        eventMessage.setId(luiInfo.getId());
        eventMessage.setType(luiInfo.getTypeKey());
        eventMessage.setContextInfo(contextInfo);

        super.sendEventMessage(eventMessage);
    }


}
