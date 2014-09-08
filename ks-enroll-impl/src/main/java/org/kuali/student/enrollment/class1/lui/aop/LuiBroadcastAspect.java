package org.kuali.student.enrollment.class1.lui.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.kuali.student.common.eventing.EventMessage;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * This AOP aspect broadcasts lui method calls to a JMS broker.
 */
public class LuiBroadcastAspect {
    public static final Logger LOGGER = LoggerFactory.getLogger(LuiBroadcastAspect.class);

    private String jmsDestination;  // where messages should be sent
    private JmsTemplate jmsTemplate;  // you pass in the jms template. This is where you determine queue or topic via config

    /**
     * This method is only valid for the LuiService.updateLui method. It's hard coded to that method structure.
     * If the method structure for updateLui changes, this will break.
     *
     * This method is used for AOP "Around"
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public Object updateLuiAdvise(ProceedingJoinPoint joinPoint) throws Throwable {
        LuiInfo newInfoObject;
        Object[] args = joinPoint.getArgs();
        ContextInfo contextInfo = (ContextInfo)args[2];

        String jmsDestination = getJmsDestination();

        if(jmsDestination == null || jmsDestination.isEmpty()){
            throw new IllegalArgumentException("jmsDestination cannot be null or empty.");
        }

        try{
            newInfoObject = (LuiInfo)joinPoint.proceed();    // perform updateLui
        } catch (Exception ex){
            throw new OperationFailedException("updateLui failed. EventMessage will not be broadcast.", ex);
        }

        createAndSendMessage(newInfoObject, contextInfo);

        return newInfoObject;  // you must send the return object back.

    }

    /**
     * Creates an EventMessage based on luiInfo and contextInfo parameters. Then sends it to the jms broker
     * @param luiInfo
     * @param contextInfo
     */
    protected  void createAndSendMessage(LuiInfo luiInfo, ContextInfo contextInfo){
        // build an event message to broadcast
        EventMessage eventMessage = new EventMessage();
        eventMessage.setAction(EventMessage.Action.UPDATE.toString());
        eventMessage.setId(luiInfo.getId());
        eventMessage.setType(luiInfo.getTypeKey());
        eventMessage.setContextInfo(contextInfo);

        // broadcast event message
        getJmsTemplate().convertAndSend(getJmsDestination(), eventMessage);
    }

    public String getJmsDestination() {
        return jmsDestination;
    }

    public void setJmsDestination(String jmsDestination) {
        this.jmsDestination = jmsDestination;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
