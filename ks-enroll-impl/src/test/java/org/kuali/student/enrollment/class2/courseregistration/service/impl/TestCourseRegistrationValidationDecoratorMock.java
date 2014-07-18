package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.kuali.student.enrollment.class2.courseregistration.service.decorators.CourseRegistrationServiceDecorator;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.registration.engine.TestCourseRegistrationEngine;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Checks the context attributes and may fail validation and/or throw an exception based on the result.
 *
 * Everything else always passes validation.
 */
public class TestCourseRegistrationValidationDecoratorMock extends CourseRegistrationServiceDecorator {
    private JmsTemplate jmsTemplate;

    @Override
    public List<ValidationResultInfo> verifyRegistrationRequestForSubmission(String registrationRequestId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AttributeInfo> attributes=contextInfo.getAttributes();
        for (AttributeInfo attribute:attributes) {
            if (attribute.getKey().equals(TestCourseRegistrationEngine.VALIDATION_EXCEPTION)
                    && attribute.getValue().equals(TestCourseRegistrationEngine.TRUE)) {
                throw new OperationFailedException("Validation Exception attribute found in context info");
            }
        }

        return new ArrayList<>();
    }

    @Override
    public RegistrationRequestInfo submitRegistrationRequest(String registrationRequestId, ContextInfo contextInfo)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        RegistrationRequestInfo regRequestInfo =
                getRegistrationRequest(registrationRequestId, contextInfo);

        if (LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY.equals(regRequestInfo.getTypeKey())) {
            regRequestInfo = convertRegCartToRegRequest(regRequestInfo, contextInfo);
        }

        try {
            MapMessage mapMessage = new ActiveMQMapMessage();
            mapMessage.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_USER_ID, contextInfo.getPrincipalId());
            mapMessage.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID, regRequestInfo.getId());
            for (AttributeInfo attribute:contextInfo.getAttributes()) {
                for (String exception : TestCourseRegistrationEngine.EXCEPTIONS) {
                    if (attribute.getKey().equals(exception) && attribute.getValue().equals(TestCourseRegistrationEngine.TRUE)) {
                        mapMessage.setBoolean(exception, true);
                    }
                }
            }
            jmsTemplate.convertAndSend(CourseRegistrationConstants.REGISTRATION_INITIALIZATION_QUEUE, mapMessage);
        } catch (JMSException jmsEx) {
            throw new RuntimeException("Error submitting registration request.", jmsEx);
        }

        return regRequestInfo;
    }

    private RegistrationRequestInfo convertRegCartToRegRequest(RegistrationRequestInfo cartInfo, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, AlreadyExistsException {

        try {
            // Create a copy of the registration request
            RegistrationRequestInfo copy = new RegistrationRequestInfo(cartInfo);
            // Change the type to standard
            copy.setTypeKey(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY);
            // Remove IDs and meta
            copy.setId(null);
            copy.setMeta(null);

            // Empty out original cart so it can be reused
            cartInfo.setRegistrationRequestItems(new ArrayList<RegistrationRequestItemInfo>());

            // return the copy
            return createRegistrationRequest(copy.getTypeKey(), copy, contextInfo);
        } catch (ReadOnlyException ex) {
            throw new OperationFailedException("Exception: " + ex.getMessage(), ex);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("Exception: " + ex.getMessage(), ex);
        }
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}
