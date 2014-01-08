package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.class2.courseregistration.service.transformer.RegistrationRequestTransformer;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.xml.namespace.QName;

public class CourseRegistrationServiceImpl 
    extends AbstractCourseRegistrationService
    implements CourseRegistrationService {

    private LprService lprService;
    private CourseOfferingService courseOfferingService;

    private JmsTemplate jmsTemplate;  // needed to call ActiveMQ based Registration Engine

    /**
     * For this implementation we are using ActiveMQ as our request engine.
     * So, all this method does is send a message to the first node in the
     * registration engine, kicking off the process.
     *
     *
     * @param registrationRequestId an identifier for a RegistrationRequest
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return
     * @throws AlreadyExistsException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegistrationResponseInfo submitRegistrationRequest(String registrationRequestId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {


        try{
            MapMessage mapMessage = new ActiveMQMapMessage();
            mapMessage.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_USER_ID, contextInfo.getPrincipalId());
            mapMessage.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID, registrationRequestId);
            jmsTemplate.convertAndSend(CourseRegistrationConstants.REGISTRATION_INITILIZATION_QUEUE, mapMessage);
        }catch (JMSException jmsEx){
            System.out.println(jmsEx.getMessage());
        }

        RegistrationResponseInfo regResp = new RegistrationResponseInfo();
        regResp.setRegistrationRequestId(registrationRequestId);
        regResp.getMessages().add("Request Submitted");

        return regResp;
    }

    @Override
    public RegistrationRequestInfo createRegistrationRequest(String registrationRequestTypeKey, RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // There is no Reg Request table. the reg request is converted to an LPR and stored.

        LprTransactionInfo lprTransactionInfo = RegistrationRequestTransformer.regRequest2LprTransaction(registrationRequestInfo);

        LprTransactionInfo newLprTransaction = getLprService().createLprTransaction(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY, lprTransactionInfo, contextInfo);

        return RegistrationRequestTransformer.lprTransaction2RegRequest(newLprTransaction);

    }

    @Override
    public RegistrationRequestInfo getRegistrationRequest(String registrationRequestId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LprTransactionInfo lprTransaction = getLprService().getLprTransaction(registrationRequestId, contextInfo);
        RegistrationRequestInfo result = RegistrationRequestTransformer.lprTransaction2RegRequest(lprTransaction);
        return result;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public LprService getLprService() {
        if (lprService == null){
            lprService = (LprService) GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null){
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }
}
