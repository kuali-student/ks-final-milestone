package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.enrollment.class2.courseregistration.service.transformer.RegistrationRequestTransformer;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
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
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseRegistrationServiceImpl extends AbstractCourseRegistrationService implements CourseRegistrationService {
    public static final Logger LOG = LoggerFactory.getLogger(CourseRegistrationServiceImpl.class);
    private LprService lprService;
    private CourseOfferingService courseOfferingService;
    private RegistrationRequestTransformer registrationRequestTransformer;


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
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RegistrationRequestInfo submitRegistrationRequest(String registrationRequestId, ContextInfo contextInfo)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        LOG.info("Submitting Registration Request for id:"+registrationRequestId);

        // had to create transactional helper methods
        RegistrationRequestInfo regRequestInfo =  getRegistrationRequestToSubmit(registrationRequestId,contextInfo);

        //Check that this is a new item
        if(!LprServiceConstants.LPRTRANS_NEW_STATE_KEY.equals(regRequestInfo.getStateKey())){
            throw new RuntimeException("Cannot submit request that is already initialized requestId:"+regRequestInfo.getId());
        }
        for(RegistrationRequestItemInfo requestItemInfo:regRequestInfo.getRegistrationRequestItems()){
            if(!LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY.equals(requestItemInfo.getStateKey())){
                throw new RuntimeException("Cannot submit request item that is already initialized requestId:"+regRequestInfo.getId() +" itemId:"+requestItemInfo.getId());
            }
        }

        try {
            MapMessage mapMessage = new ActiveMQMapMessage();
            mapMessage.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_USER_ID, regRequestInfo.getRequestorId());
            mapMessage.setString(CourseRegistrationConstants.REGISTRATION_QUEUE_MESSAGE_REG_REQ_ID, regRequestInfo.getId());
            jmsTemplate.convertAndSend(CourseRegistrationConstants.REGISTRATION_INITIALIZATION_QUEUE, mapMessage);
        } catch (JMSException jmsEx) {
            throw new RuntimeException("Error submitting registration request.", jmsEx);
        }

        return regRequestInfo;
    }

    protected RegistrationRequestInfo getRegistrationRequestToSubmit(String registrationRequestId, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, AlreadyExistsException {
        RegistrationRequestInfo regRequestInfo =
                getRegistrationRequest(registrationRequestId, contextInfo);

        if (LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY.equals(regRequestInfo.getTypeKey())) {
            regRequestInfo = convertRegCartToRegRequest(regRequestInfo, contextInfo);
        }
        return regRequestInfo;

    }

    /**
     *
     * @param cartInfo Same as a reg request ID.  Refers to a RegistrationRequestInfo/LPRTransactionInfo
     *                           object (with type
     * @param contextInfo The context info
     */
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
            updateRegistrationRequest(cartInfo.getId(), cartInfo, contextInfo);

            // persist this and return the copy
            return createRegistrationRequest(copy.getTypeKey(), copy, contextInfo);
        } catch (ReadOnlyException|DataValidationErrorException|VersionMismatchException ex) {
            throw new OperationFailedException("Exception: " + ex.getMessage(), ex);
        }
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
        LprTransactionInfo lprTransactionInfo = getRegistrationRequestTransformer().regRequest2LprTransaction(registrationRequestInfo, contextInfo);

        LprTransactionInfo newLprTransaction = getLprService().createLprTransaction(lprTransactionInfo.getTypeKey(), lprTransactionInfo, contextInfo);

        return getRegistrationRequestTransformer().lprTransaction2RegRequest(newLprTransaction, contextInfo);

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
                = getRegistrationRequestTransformer().regRequest2LprTransaction(registrationRequestInfo, contextInfo);
        LprTransactionInfo updated
            = getLprService().updateLprTransaction(lprTransactionInfo.getId(), lprTransactionInfo, contextInfo);
        return getRegistrationRequestTransformer().lprTransaction2RegRequest(updated, contextInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public RegistrationRequestInfo getRegistrationRequest(String registrationRequestId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LprTransactionInfo lprTransaction = getLprService().getLprTransaction(registrationRequestId, contextInfo);
        return getRegistrationRequestTransformer().lprTransaction2RegRequest(lprTransaction, contextInfo);
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

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndTerm(String studentId, String termId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        //Do a query for all lprs that have matching person and atp ids. Limit to RG and CO Lprs that are in state
        // registrant(might want to change to active in future)
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("personId", studentId),
                PredicateFactory.equal("atpId", termId),
                PredicateFactory.in("personRelationTypeId", LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY, LprServiceConstants.REGISTRANT_RG_LPR_TYPE_KEY),
                PredicateFactory.equal("personRelationStateId", LprServiceConstants.ACTIVE_STATE_KEY));
        QueryByCriteria lprCriteria = qbcBuilder.build();
        List<LprInfo> lprs = getLprService().searchForLprs(lprCriteria, contextInfo);

        //Get a mapping from the master lprid to the COid since CourseRegistrationInfo has both CO and RG ids
        Map<String,String> masterLprIdToCoIdMap = new HashMap<>();
        for(LprInfo lpr:lprs){
            if(LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY.equals(lpr.getTypeKey())){
                masterLprIdToCoIdMap.put(lpr.getMasterLprId(), lpr.getLuiId());
            }
        }

        //FOr each RG lpr, greate a new courseRegistrationInfo and lookup the corresponding CO id
        List<CourseRegistrationInfo> courseRegistrations = new ArrayList<>(lprs.size());
        for(LprInfo lpr:lprs){
            if(LprServiceConstants.REGISTRANT_RG_LPR_TYPE_KEY.equals(lpr.getTypeKey())){
                courseRegistrations.add(toCourseRegistration(lpr, masterLprIdToCoIdMap.get(lpr.getMasterLprId())));
            }
        }
        return courseRegistrations;
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsForCourseRegistration(String courseRegistrationId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //Do a search for AO lprs with the masterLPR matching the course registration
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("masterLprId", courseRegistrationId),
                PredicateFactory.in("personRelationTypeId", LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY),
                PredicateFactory.equal("personRelationStateId", LprServiceConstants.ACTIVE_STATE_KEY));
        QueryByCriteria lprCriteria = qbcBuilder.build();
        List<LprInfo> lprs = getLprService().searchForLprs(lprCriteria, contextInfo);

        //Convert these lprs to ActivityRegistrationInfos
        List<ActivityRegistrationInfo> activityRegistrations = new ArrayList<>(lprs.size());
        for(LprInfo lpr:lprs){
            ActivityRegistrationInfo activityRegistration = new ActivityRegistrationInfo();
            activityRegistration.setTypeKey(lpr.getTypeKey());
            activityRegistration.setStateKey(lpr.getStateKey());
            activityRegistration.setId(lpr.getId());
            activityRegistration.setEffectiveDate(lpr.getEffectiveDate());
            activityRegistration.setExpirationDate(lpr.getExpirationDate());
            activityRegistration.setTermId(lpr.getAtpId());
            activityRegistration.setAttributes(lpr.getAttributes());
            activityRegistration.setActivityOfferingId(lpr.getLuiId());
            activityRegistration.setPersonId(lpr.getPersonId());
            activityRegistration.setCourseRegistrationId(lpr.getMasterLprId());
            activityRegistration.setMeta(lpr.getMeta());
            activityRegistrations.add(activityRegistration);
        }
        return activityRegistrations;
    }

    private CourseRegistrationInfo toCourseRegistration(LprInfo rgLpr, String courseOfferingId) {
        CourseRegistrationInfo courseRegistration = new CourseRegistrationInfo();
        for(String rvgKey:rgLpr.getResultValuesGroupKeys()){
            if (rvgKey.startsWith(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_BASE)) {
                courseRegistration.setGradingOptionId(rvgKey);
            } else if (rvgKey.startsWith(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE)) {
                //This will be replaced with just the key in the future
                courseRegistration.setCredits(new KualiDecimal(rvgKey.substring(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE.length() + 1)));
            }
        }
        courseRegistration.setId(rgLpr.getId());
        courseRegistration.setStateKey(rgLpr.getStateKey());
        courseRegistration.setTypeKey(rgLpr.getTypeKey());
        courseRegistration.setRegistrationGroupId(rgLpr.getLuiId());
        courseRegistration.setCourseOfferingId(courseOfferingId);
        courseRegistration.setPersonId(rgLpr.getPersonId());
        courseRegistration.setTermId(rgLpr.getAtpId());
        courseRegistration.setAttributes(rgLpr.getAttributes());
        courseRegistration.setMeta(rgLpr.getMeta());
        courseRegistration.setEffectiveDate(rgLpr.getEffectiveDate());
        courseRegistration.setExpirationDate(rgLpr.getExpirationDate());
        return courseRegistration;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public LprService getLprService() {
        if (lprService == null) {
            lprService = GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public RegistrationRequestTransformer getRegistrationRequestTransformer() {
        return registrationRequestTransformer;
    }

    public void setRegistrationRequestTransformer(RegistrationRequestTransformer registrationRequestTransformer) {
        this.registrationRequestTransformer = registrationRequestTransformer;
    }
}
