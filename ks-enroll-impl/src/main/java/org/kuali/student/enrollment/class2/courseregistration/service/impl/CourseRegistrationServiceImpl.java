package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.class2.courseregistration.service.transformer.RegistrationRequestTransformer;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.xml.namespace.QName;
import java.util.ArrayList;

public class CourseRegistrationServiceImpl extends AbstractCourseRegistrationService implements CourseRegistrationService {

    private LprService lprService;
    private CourseOfferingService courseOfferingService;

    private JmsTemplate jmsTemplate;  // needed to call ActiveMQ based Registration Engine

    /**
     * For this implementation we are using ActiveMQ as our request engine.
     * So, all this method does is send a message to the first node in the
     * registration engine, kicking off the process.
     *
     * @param registrationRequestId an identifier for a RegistrationRequest
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of the service
     *                              operation
     * @return Registration Response
     * @throws AlreadyExistsException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegistrationResponseInfo submitRegistrationRequest(String registrationRequestId, ContextInfo contextInfo)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // If registration request ID refers to a reg cart, then
        RegistrationResponseInfo result = submitRegistrationCart(registrationRequestId, contextInfo);
        if (result != null) { // If it's null, then it's a normal submit request
            return result;
        }

        // Back to the main code if reg request is not a cart
        try {
            MapMessage mapMessage = new ActiveMQMapMessage();
            mapMessage.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_USER_ID, contextInfo.getPrincipalId());
            mapMessage.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID, registrationRequestId);
            jmsTemplate.convertAndSend(CourseRegistrationConstants.REGISTRATION_INITILIZATION_QUEUE, mapMessage);
        } catch (JMSException jmsEx) {
            throw new RuntimeException("Error submitting registration request.", jmsEx);
        }

        RegistrationResponseInfo regResp = new RegistrationResponseInfo();
        regResp.setRegistrationRequestId(registrationRequestId);
        regResp.getMessages().add("Request Submitted");

        return regResp;
    }

    /**
     *
     * @param registrationCartId Same as a reg request ID.  Refers to a RegistrationRequestInfo/LPRTransactionInfo
     *                           object (with type
     * @param contextInfo The context info
     */
    private RegistrationResponseInfo submitRegistrationCart(String registrationCartId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, AlreadyExistsException {
        Exception ex = null;
        try {
            RegistrationRequestInfo cartInfo =
                    getRegistrationRequest(registrationCartId, contextInfo);
            if (!LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY.equals(cartInfo.getTypeKey())) {
                return null; // This bails out back to the original method if the type is something else
            }
            // Create a copy of the registration request
            RegistrationRequestInfo copy = new RegistrationRequestInfo(cartInfo);
            // Change the type to standard
            copy.setTypeKey(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);
            // Remove IDs and meta
            copy.setId(null);
            copy.setMeta(null);
            for (RegistrationRequestItemInfo item: copy.getRegistrationRequestItems()) {
                item.setId(null);
                item.setMeta(null);
            }
            // Persist this
            RegistrationRequestInfo updated =
                    createRegistrationRequest(copy.getTypeKey(), copy, contextInfo);
            // Empty out original cart so it can be reused
            cartInfo.setRegistrationRequestItems(new ArrayList<RegistrationRequestItemInfo>());
            cartInfo = updateRegistrationRequest(cartInfo.getId(), cartInfo, contextInfo);
            // Submit the copy
            submitRegistrationRequest(updated.getId(), contextInfo);
        } catch (ReadOnlyException e) {
            ex = e;
        } catch (DataValidationErrorException e) {
            ex = e;
        } catch (VersionMismatchException e) {
            ex = e;
        }
        if (ex != null) {
            // Rethrow
            throw new OperationFailedException("Exception: " + ex.getMessage(), ex);
        }

        RegistrationResponseInfo regResp = new RegistrationResponseInfo();
        regResp.setRegistrationRequestId(registrationCartId);
        regResp.getMessages().add("Reg Cart Submitted");

        return regResp;
    }
    /**
     * If the registration request type key is LPRTRANS_REG_CART_TYPE_KEY, then it will check the types
     * of the operations of the transaction items to make sure they are "add" operations.
     * @param registrationRequestTypeKey Type of the registration request
     * @param regRequest The reg request to be created
     *
     */
    private void verifyRegRequestCartTypeKey(String registrationRequestTypeKey, RegistrationRequestInfo regRequest)
            throws InvalidParameterException {
        if (LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY.equals(registrationRequestTypeKey)) {
            for (RegistrationRequestItemInfo item: regRequest.getRegistrationRequestItems()) {
                // Throw exception if any item is not an "add"
                if (!LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY.equals(item.getTypeKey())) {
                    throw new InvalidParameterException("Only add item keys permitted for reg cart type");
                }
            }
        }
    }

    @Override
    @Transactional
    public RegistrationRequestInfo createRegistrationRequest(String registrationRequestTypeKey,
                                                             RegistrationRequestInfo registrationRequestInfo,
                                                             ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // Check reg request of type "cart" only has add items
        verifyRegRequestCartTypeKey(registrationRequestTypeKey, registrationRequestInfo);
        // There is no Reg Request table. the reg request is converted to an LPR and stored.
        LprTransactionInfo lprTransactionInfo = RegistrationRequestTransformer.regRequest2LprTransaction(registrationRequestInfo, contextInfo);

        LprTransactionInfo newLprTransaction = getLprService().createLprTransaction(lprTransactionInfo.getTypeKey(), lprTransactionInfo, contextInfo);

        return RegistrationRequestTransformer.lprTransaction2RegRequest(newLprTransaction, contextInfo);

    }

    @Override
    @Transactional
    public RegistrationRequestInfo updateRegistrationRequest(String registrationRequestId,
                                                             RegistrationRequestInfo registrationRequestInfo,
                                                             ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // Check reg request of type "cart" only has add items
        verifyRegRequestCartTypeKey(registrationRequestInfo.getTypeKey(), registrationRequestInfo);
        // There is no Reg Request table. the reg request is converted to an LPR and stored.
        LprTransactionInfo lprTransactionInfo
                = RegistrationRequestTransformer.regRequest2LprTransaction(registrationRequestInfo, contextInfo);
        LprTransactionInfo updated
            = getLprService().updateLprTransaction(lprTransactionInfo.getId(), lprTransactionInfo, contextInfo);
        return RegistrationRequestTransformer.lprTransaction2RegRequest(updated, contextInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public RegistrationRequestInfo getRegistrationRequest(String registrationRequestId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LprTransactionInfo lprTransaction = getLprService().getLprTransaction(registrationRequestId, contextInfo);
        RegistrationRequestInfo result = RegistrationRequestTransformer.lprTransaction2RegRequest(lprTransaction, contextInfo);
        return result;
    }

    @Override
    @Transactional
    public StatusInfo changeRegistrationRequestState(String registrationRequestId, String nextStateKey,
                                                     ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // Need a validation decorator to check for valid nextStateKey
        getLprService().changeLprTransactionState(registrationRequestId, nextStateKey, contextInfo);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public LprService getLprService() {
        if (lprService == null) {
            lprService = (LprService) GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }
}
