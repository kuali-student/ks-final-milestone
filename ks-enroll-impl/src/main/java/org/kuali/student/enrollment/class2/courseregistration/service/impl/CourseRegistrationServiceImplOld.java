package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.MapMessage;


/**
 * This is the old Core Slice of registration. It needs to be deleted, but for now it has a lot of good reference code.
 * I just don't want all this commented out code in our current implementation.
 */
public class CourseRegistrationServiceImplOld
    extends AbstractCourseRegistrationService
    implements CourseRegistrationService {

    private LprService lprService;
    private CourseOfferingService courseOfferingService;

    private JmsTemplate jmsTemplate;  // needed to call ActiveMQ based Registration Engine


    public LprService getLprService() {
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

	@Override
	public StatusInfo changeRegistrationRequestState(
			String registrationRequestId, String nextStateKey,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO KSENROLL-8712
		throw new UnsupportedOperationException("not implemented");
	}

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
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
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
    public RegistrationRequestInfo getRegistrationRequest(String registrationRequestId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // THIS IS A MOCK IMPL. NEEDS TO CALL DB

        RegistrationRequestInfo regResp = new RegistrationRequestInfo();
        regResp.setId(registrationRequestId);
        return regResp;

    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

//
//
//    public StatementService getStatementService() {
//        return statementService;
//    }
//
//    public void setStatementService(StatementService statementService) {
//        this.statementService = statementService;
//    }
//
//    public CourseService getCourseService() {
//        return courseService;
//    }
//
//    public void setCourseService(CourseService courseService) {
//        this.courseService = courseService;
//    }
//
//    public PropositionBuilder getPropositionBuilder() {
//        return propositionBuilder;
//    }
//
//    public void setPropositionBuilder(PropositionBuilder propositionBuilder) {
//        this.propositionBuilder = propositionBuilder;
//    }
//
//    public RulesEvaluationUtil getRulesEvaluationUtil() {
//        return rulesEvaluationUtil;
//    }
//
//    public void setRulesEvaluationUtil(RulesEvaluationUtil rulesEvaluationUtil) {
//        this.rulesEvaluationUtil = rulesEvaluationUtil;
//    }


    /*************
     Core-slice code

    public ProcessService getProcessService() {
        return processService;
    }

    public void setProcessService(ProcessService processService) {
        this.processService = processService;
    }

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public RegRequestAssembler getRegRequestAssembler() {
        return regRequestAssembler;
    }

    public void setRegRequestAssembler(RegRequestAssembler regRequestAssembler) {
        this.regRequestAssembler = regRequestAssembler;
    }

    public RegResponseAssembler getRegResponseAssembler() {
        return regResponseAssembler;
    }

    public void setRegResponseAssembler(RegResponseAssembler regResponseAssembler) {
        this.regResponseAssembler = regResponseAssembler;
    }

    public CourseRegistrationAssembler getCourseRegistrationAssembler() {
        return courseRegistrationAssembler;
    }

    public void setCourseRegistrationAssembler(CourseRegistrationAssembler courseRegistrationAssembler) {
        this.courseRegistrationAssembler = courseRegistrationAssembler;
    }

    private List<LprTransactionItemInfo> createModifiedLprTransactionItemsForNew(RegRequestItemInfo regRequestItem, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {

        List<LprTransactionItemInfo> newTransactionItems = new ArrayList<LprTransactionItemInfo>();

        String regGroupId = regRequestItem.getNewRegGroupId();
        RegistrationGroupInfo regGroup = courseOfferingService.getRegistrationGroup(regGroupId, context);
        LprTransactionItemInfo lprTransactionItem = regRequestAssembler.disassembleItem(regRequestItem, null, context);
        if (getAvailableSeatsForStudentInRegGroup(regRequestItem.getStudentId(), regGroupId, context) > 0) {
            List<LprTransactionItemInfo> lprActivityTransactionItems = new ArrayList<LprTransactionItemInfo>();
            for (String activityOfferingId : regGroup.getActivityOfferingIds()) {
                LprTransactionItemInfo activtyItemInfo = regRequestAssembler.disassembleItem(regRequestItem, null, context);
                activtyItemInfo.setId(null);
                activtyItemInfo.setNewLuiId(activityOfferingId);
                newTransactionItems.add(activtyItemInfo);
            }

            String courseOfferingId = regGroup.getCourseOfferingId();
            LprTransactionItemInfo courseOfferingItemInfo = regRequestAssembler.disassembleItem(regRequestItem, null, context);
            courseOfferingItemInfo.setNewLuiId(courseOfferingId);
            courseOfferingItemInfo.setId(null);
            ArrayList<String> rvgs = new ArrayList<String>();
            rvgs.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
            courseOfferingItemInfo.setResultValuesGroupKeys(rvgs);
            newTransactionItems.add(courseOfferingItemInfo);

        } else {
            // TODO: copy the transaction item and change the type
            // of the new one to be ADD TO WAITLIST
            // and then on that item mark the original with a state
            // of failed

            lprTransactionItem.setTypeKey(LprServiceConstants.LPRTRANS_ITEM_ADD_TO_WAITLIST_TYPE_KEY);
            lprTransactionItem.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        }
        newTransactionItems.add(lprTransactionItem);
        return newTransactionItems;

    }

    private List<LprTransactionItemInfo> createModifiedLprTransactionItemsForDrop(RegRequestItemInfo regRequestItem, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        List<LprTransactionItemInfo> newTransactionItems = new ArrayList<LprTransactionItemInfo>();

        String regGroupId = regRequestItem.getExistingRegGroupId();
        RegistrationGroupInfo regGroup = courseOfferingService.getRegistrationGroup(regGroupId, context);
        List<LprTransactionItemInfo> lprActivityTransactionItems = new ArrayList<LprTransactionItemInfo>();
        for (String activityOfferingId : regGroup.getActivityOfferingIds()) {
            LprTransactionItemInfo activtyItemInfo = regRequestAssembler.disassembleItem(regRequestItem, null, context);
            activtyItemInfo.setId(null);
            activtyItemInfo.setExistingLuiId(activityOfferingId);
//            activtyItemInfo.setGroupId(regRequestItem.getId());
            newTransactionItems.add(activtyItemInfo);

        }

        String courseOfferingId = regGroup.getCourseOfferingId();
        LprTransactionItemInfo courseOfferingItemInfo = regRequestAssembler.disassembleItem(regRequestItem, null, context);
        courseOfferingItemInfo.setId(null);
        courseOfferingItemInfo.setExistingLuiId(courseOfferingId);
//        courseOfferingItemInfo.setGroupId(regRequestItem.getId());
        lprActivityTransactionItems.add(courseOfferingItemInfo);
        newTransactionItems.add(courseOfferingItemInfo);
        // regRequestAssembler.disassembleItem(regRequestItem, null, context);
        return newTransactionItems;

    }

    private LprTransactionInfo createModifiedTransactionItems(LprTransactionInfo storedLprTransaction, RegRequestInfo storedRegRequest, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        List<LprTransactionItemInfo> newTransactionItems = new ArrayList<LprTransactionItemInfo>();
        List<RegRequestItemInfo> regRequestItems = storedRegRequest.getRegRequestItems();
        for (RegRequestItemInfo regRequestItem : regRequestItems) {
            if (regRequestItem.getTypeKey().equals(LprServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY)
                    || regRequestItem.getTypeKey().equals(LprServiceConstants.LPRTRANS_ITEM_DROP_TYPE_KEY)
                    || regRequestItem.getTypeKey().equals(LprServiceConstants.LPRTRANS_ITEM_UPDATE_TYPE_KEY)) {
                if (regRequestItem.getTypeKey().equals(LprServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY)) {

                    newTransactionItems.addAll(createModifiedLprTransactionItemsForNew(regRequestItem, context));

                } else if (regRequestItem.getTypeKey().equals(LprServiceConstants.LPRTRANS_ITEM_DROP_TYPE_KEY)) {

                    newTransactionItems.addAll(createModifiedLprTransactionItemsForDrop(regRequestItem, context));
                }

            }

        }
        storedLprTransaction.setLprTransactionItems(newTransactionItems);

        storedLprTransaction = lprService.updateLprTransaction(storedLprTransaction.getId(), storedLprTransaction, context);
        return lprService.getLprTransaction(storedLprTransaction.getId(), context);

    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibility(String studentId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

//        String matchingProcessKey = null;
//        List<ProcessInfo> studentElgibilityProcesses = processService.getProcessesForProcessCategory(ProcessServiceConstants.PROCESS_CATEGORY_STUDENT_ELIGIBILITY, context);
//
//        for (ProcessInfo processInfo : studentElgibilityProcesses) {
//            if (processInfo.getOwnerOrgId().equals(ProcessServiceConstants.PROCESS_OWNING_ORG_GRAD_SCHOOL)) {
//                matchingProcessKey = processInfo.getKey();
//                break;
//            }
//        }

        return null;
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibilityForTerm(String studentId, String termKey, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(String studentId, String courseOfferingId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        CourseOfferingInfo courseOffering = null;
        try {
            courseOffering = courseOfferingService.getCourseOffering(courseOfferingId, context);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("Invalid courseOfferingId, no course offering found with id of: " + courseOfferingId);
        }

        List<StatementTreeViewInfo> statements;

        try {
            // TODO fill in nlUsageType and language parameters once the
            // implementation actually uses them
            statements = courseService.getCourseStatements(courseOffering.getCourseId(), null, null, null);
        } catch (Exception e) {
            throw new OperationFailedException(e.getMessage(), e);
        }

        List<ValidationResultInfo> resultInfos = new ArrayList<ValidationResultInfo>();

        // find and process statements that the PropositionBuilder can handle
        for (StatementTreeViewInfo statementTree : statements) {
            if (PropositionBuilder.TRANSLATABLE_STATEMENT_TYPES.contains(statementTree.getType())) {
                PropositionBuilder.TranslationResults translationResults = null;
                try {
                    translationResults = propositionBuilder.translateStatement(statementTree, null);
                } catch (org.kuali.student.r2.common.exceptions.InvalidParameterException e) {
                    throw new OperationFailedException("Exception thrown attempting statement translation for statement: " + statementTree.getId(), e);
                }

                Map<String, Object> executionFacts = new HashMap<String, Object>();
                executionFacts.put(RulesExecutionConstants.STUDENT_ID_TERM_NAME, studentId);
                executionFacts.put(RulesExecutionConstants.COURSE_ID_TO_ENROLL_TERM_NAME, courseOffering.getCourseId());
                executionFacts.put(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME, context);

                EngineResults engineResults = rulesEvaluationUtil.executeAgenda(translationResults.agenda, executionFacts);

                List<ReqComponentInfo> failedRequirements = rulesEvaluationUtil.getFailedRequirementsFromEngineResults(engineResults, translationResults.reqComponentPropositionMap);

                if (!failedRequirements.isEmpty()) {
                    for (ReqComponentInfo failedRequirement : failedRequirements) {
                        ValidationResultInfo resultInfo = new ValidationResultInfo();
                        resultInfo.setLevel(ValidationResult.ErrorLevel.ERROR);
                        resultInfo.setElement(failedRequirement.getId());
                        try {
                            resultInfo.setMessage(statementService.getNaturalLanguageForReqComponent(failedRequirement.getId(), "KUALI.RULE", "en"));
                        } catch (Exception e) {
                            throw new OperationFailedException(e.getMessage(), e);
                        }

                        resultInfos.add(resultInfo);
                    }
                }
            }
        }

        if (resultInfos.isEmpty()) {
            ValidationResultInfo resultInfo = new ValidationResultInfo();
            resultInfo.setLevel(ValidationResult.ErrorLevel.OK);

            resultInfos.add(resultInfo);
        }

        return resultInfos;
    }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> checkStudentEligibiltyForRegGroup(String studentId, String regGroupId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<RegistrationGroupInfo> getEligibleRegGroupsForStudentInCourseOffering(String studentId, String courseOfferingId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LoadInfo calculateCreditLoadForTerm(String studentId, String termKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LoadInfo calculateCreditLoadForRegRequest(String studentId, RegRequestInfo regRequestInfo, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Integer getAvailableSeatsForCourseOffering(String courseOfferingId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Integer getAvailableSeatsForRegGroup(String regGroupId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Integer getAvailableSeatsForStudentInRegGroup(String studentId, String regGroupId, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO - rules and waitlist implementation needed
        return new Integer(3);

    }

    @Override
    public Integer getAvailableSeatsInSeatPool(String seatpoolId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RegRequestInfo updateRegRequest(String regRequestId, RegRequestInfo regRequestInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        RegRequestInfo rrInfo;
        try {
            LprTransactionInfo lprTransactionInfo = regRequestAssembler.disassemble(regRequestInfo, context);
            rrInfo = regRequestAssembler.assemble(lprService.updateLprTransaction(regRequestId, lprTransactionInfo, context), context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        return rrInfo;
    }

    @Override
    public StatusInfo deleteRegRequest(String regRequestId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return lprService.deleteLprTransaction(regRequestId, context);
    }



    @Override
	public RegRequestInfo createRegRequest(String regRequestTypeKey,
			RegRequestInfo regRequestInfo, ContextInfo context)
			throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException {
        LprTransactionInfo lprTransaction = null;
        try {
            lprTransaction = regRequestAssembler.disassemble(regRequestInfo, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException in disassembling: " + e.getMessage());
        }

        LprTransactionInfo newLprTransaction = lprService.createLprTransaction(lprTransaction.getTypeKey(), lprTransaction, context);
        try {
            newLprTransaction = lprService.getLprTransaction(newLprTransaction.getId(), context);
        } catch (DoesNotExistException e) {
            return null;
        }

        RegRequestInfo createdRegRequestInfo = null;
        try {
            createdRegRequestInfo = regRequestAssembler.assemble(newLprTransaction, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException in assembling: " + e.getMessage());
        }

        return createdRegRequestInfo;
    }

    @Override
    public RegRequestInfo createRegRequestFromExisting(String existingRegRequestId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, DataValidationErrorException, PermissionDeniedException {

        LprTransactionInfo newLprTransaction = lprService.createLprTransactionFromExisting(existingRegRequestId, context);
        RegRequestInfo createdRegRequestInfo = null;
        try {
            createdRegRequestInfo = regRequestAssembler.assemble(newLprTransaction, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException in assembling: " + e.getMessage());
        }
        return createdRegRequestInfo;
    }

    @Override
    public RegResponseInfo submitRegRequest(String regRequestId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, AlreadyExistsException {
        LprTransactionInfo storedLprTransaction = lprService.getLprTransaction(regRequestId, context);

        RegRequestInfo storedRegRequest = null;
        try {
            storedRegRequest = regRequestAssembler.assemble(storedLprTransaction, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException in assembling: " + e.getMessage());
        }

        try {

            List<ValidationResultInfo> verificationResultList = verifyRegRequestForSubmission(regRequestId, context);
            for (ValidationResultInfo verificationResult : verificationResultList) {
                if (!verificationResult.isOk()) {
                    throw new DataValidationErrorException("Error while verifying registration request: " + verificationResult.getMessage());
                }
            }
        } catch (DataValidationErrorException dataValidException) {
            throw new OperationFailedException(dataValidException.getMessage(), dataValidException);
        }

        // check eligibility requirements
        for (RegRequestItemInfo item : storedRegRequest.getRegRequestItems()) {
            if (!StringUtils.equals(LprServiceConstants.LPRTRANS_ITEM_DROP_TYPE_KEY, item.getTypeKey())) {
                RegistrationGroupInfo regGroup = courseOfferingService.getRegistrationGroup(item.getNewRegGroupId(), context);

                List<ValidationResultInfo> validations = checkStudentEligibiltyForCourseOffering(storedLprTransaction.getRequestingPersonId(), regGroup.getCourseOfferingId(), context);
                List<String> errorMessages = new ArrayList<String>();
                for (ValidationResultInfo validation : validations) {
                    if (validation.isError()) {
                        errorMessages.add(validation.getMessage());
                    }
                }

                if (!errorMessages.isEmpty()) {
                    RegResponseInfo errorResponse = new RegResponseInfo();
                    errorResponse.setOperationStatus(new OperationStatusInfo());
                    errorResponse.getOperationStatus().setErrors(errorMessages);
                    errorResponse.getOperationStatus().setStatus("FAILURE");

                    return errorResponse;
                }
            }
        }

        LprTransactionInfo multipleItemsTransaction;
		try {
			multipleItemsTransaction = createModifiedTransactionItems(storedLprTransaction, storedRegRequest, context);
		} catch (DataValidationErrorException dataValidException) {
			 throw new OperationFailedException(dataValidException.getMessage(), dataValidException);
		}catch (VersionMismatchException ex) {
			 throw new OperationFailedException(ex.getMessage(), ex);
		}

        LprTransactionInfo submittedLprTransaction = lprService.processLprTransaction(multipleItemsTransaction.getId(), context);

        RegResponseInfo returnRegResponse = null;
        try {
            returnRegResponse = regResponseAssembler.assemble(submittedLprTransaction, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException in assembling: " + e.getMessage());
        }

        if (checkSuccessfulRegCriteria(returnRegResponse)) {

            try {
                createGradeRosterEntryForRegisteredStudent(submittedLprTransaction, context);
            } catch (DataValidationErrorException dataValidException) {
                throw new OperationFailedException(dataValidException.getMessage(), dataValidException);
            }catch (ReadOnlyException dataValidException) {
                throw new OperationFailedException(dataValidException.getMessage(), dataValidException);
            }

        }

        return returnRegResponse;

    }

    private boolean checkSuccessfulRegCriteria(RegResponseInfo returnRegResponse) {
        return true;
    }

    private void createGradeRosterEntryForRegisteredStudent(LprTransactionInfo submittedLprTransaction, ContextInfo context) throws DataValidationErrorException, AlreadyExistsException,
            InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        // TODO: since we're creating at a minimum 3 lprs per course reg we
        // shouldn't loop through all of them. need a way to get directly to the
        // grade roster
        for (LprTransactionItemInfo lprItem : submittedLprTransaction.getLprTransactionItems()) {
            if (lprItem.getTypeKey().equals(LprServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY)) {
                LprRosterEntryInfo newLprRosterEntry = new LprRosterEntryInfo();
                newLprRosterEntry.setLprId(lprItem.getLprTransactionItemResult().getResultingLprId());
                newLprRosterEntry.setTypeKey(LprServiceConstants.LPRROSTER_COURSE_FINAL_GRADE_TYPE_KEY);
                newLprRosterEntry.setStateKey(LprServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_READY_STATE_KEY);

                List<LprRosterInfo> lprRosters = lprRosterService.getLprRostersByTypeAndLui(LprServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_TYPE_KEY,
                        lprItem.getNewLuiId(), context);
                if (lprRosters.size() == 1) {
                    newLprRosterEntry.setLprRosterId(lprRosters.get(0).getId());
                    lprRosterService.createLprRosterEntry(
                            newLprRosterEntry.getLprRosterId(),
                            newLprRosterEntry.getLprId(),
                            newLprRosterEntry.getTypeKey(),
                            newLprRosterEntry, context);
                }

            }

        }

    }

    @Override
    public CourseRegistrationInfo getCourseRegistration(String courseRegistrationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return new CourseRegistrationInfo();

    }

    @Override
	public List<CourseRegistrationInfo> getCourseRegistrationsByIds(
			List<String> courseRegistrationIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

    	 List<CourseRegistrationInfo> courseRegistrationInfos = new ArrayList<CourseRegistrationInfo>();
       ResultValuesGroup rvGroup = null;
       List<LprInfo> lprs = lprService.getLprsByIds(courseRegistrationIds, context);
       for (LprInfo lpr : lprs) {
           for (String rvGroupKey : lpr.getResultValuesGroupKeys()) {
               rvGroup = lrcService.getResultValuesGroup(rvGroupKey, context);
               if (rvGroup != null) {
                   ResultScaleInfo resScale = lrcService.getResultScale(rvGroup.getResultScaleKey(), context);
                   if (resScale != null) {
                       if (StringUtils.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED, resScale.getTypeKey())) {
                           courseRegistrationInfos.add(courseRegistrationAssembler.assemble(lpr, rvGroup, context));
                           break;
                       }
                   }
               }

           }
       }

       return courseRegistrationInfos;
	}

	// TODO - post core slice need to ensure that the list has one
    private List<LprInfo> filterLprByState(List<LprInfo> lprInfoList, String stateKey) {
        List<LprInfo> filteredLprInfoList = new ArrayList<LprInfo>();
        for (LprInfo lprInfo : filteredLprInfoList) {
            if (lprInfo.getStateKey().equals(stateKey)) {
                filteredLprInfoList.add(lprInfo);
            }
        }
        return filteredLprInfoList;
    }



    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndTerm(String studentId, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
                                                                                                                                             MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<CourseRegistrationInfo> courseRegistrationList = new ArrayList<CourseRegistrationInfo>();

        List<LprInfo> courseLprList = lprService.getLprsByPersonForAtpAndLuiType(studentId, termKey, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, context);

        List<LprInfo> regGroupLprList = lprService.getLprsByPersonForAtpAndLuiType(studentId, termKey, LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, context);

        getCourseRegistration(studentId, courseRegistrationList, courseLprList, regGroupLprList, context);

        return courseRegistrationList;
    }

    private void getCourseRegistration(String studentId, List<CourseRegistrationInfo> courseRegistrationList, List<LprInfo> courseLprList, List<LprInfo> regGroupLprList,
                                       ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (courseLprList != null && !courseLprList.isEmpty()) {
            for (LprInfo courseOfferinglprInfo : courseLprList) {

                if (courseOfferinglprInfo.getTypeKey().equals(LprServiceConstants.REGISTRANT_TYPE_KEY)) {
                    CourseOfferingInfo courseOfferingInfo = courseOfferingService.getCourseOffering(courseOfferinglprInfo.getLuiId(), context);

                    List<RegistrationGroupInfo> regGroupList = courseOfferingService.getRegistrationGroupsForCourseOffering(courseOfferinglprInfo.getLuiId(), context);

                    for (RegistrationGroupInfo regGroup : regGroupList) {

                        RegistrationGroupInfo reg = new RegistrationGroupInfo();

                        Map<LprInfo, ActivityOfferingInfo> activtiesLprInfoMap = new HashMap<LprInfo, ActivityOfferingInfo>();

                        for (LprInfo regGroupLprInfo : regGroupLprList) {

                            if (regGroup.getId().equals(regGroupLprInfo.getLuiId()) && regGroupLprInfo.getTypeKey().equals(LprServiceConstants.REGISTRANT_TYPE_KEY)) {

                                reg = courseOfferingService.getRegistrationGroup(regGroup.getId(), context);

                                for (String activityOfferingId : regGroup.getActivityOfferingIds()) {

                                    List<LprInfo> lprsForActivity = lprService.getLprsByPersonAndLui(studentId, activityOfferingId, context);

                                    for (LprInfo activityLpr : lprsForActivity) {

                                        if (activityLpr.getTypeKey().equals(LprServiceConstants.REGISTRANT_TYPE_KEY) && activityLpr.getStateKey().equals(regGroupLprInfo.getStateKey())) {

                                            ActivityOfferingInfo activityOffering = courseOfferingService.getActivityOffering(activityOfferingId, context);

                                            activtiesLprInfoMap.put(activityLpr, activityOffering);
                                        }
                                    }

                                }

                                courseRegistrationList.add(courseRegistrationAssembler.assemble(courseOfferinglprInfo, courseOfferingInfo, activtiesLprInfoMap, regGroupLprInfo, reg, context));
                            }

                        }

                    }
                }

            }
        }
    }


    @Override
    public List<RegRequestItemInfo> getRegRequestItemsByCourseRegistration(
            String courseRegistrationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<RegRequestItemInfo> regrequests = new ArrayList<RegRequestItemInfo>();
        List<LprTransactionItemInfo> lprTransactionItems = lprService.getLprTransactionItemsByResultingLpr(
                courseRegistrationId, context);
        for (LprTransactionItemInfo lprTransactionItem : lprTransactionItems) {
            regrequests.add(regRequestAssembler.assembleItem(
                    lprTransactionItem, context));
        }
        return regrequests;
    }




    @Override
    public RegRequestInfo getRegRequest(String regRequestId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        LprTransactionInfo lprTransaction = lprService.getLprTransaction(regRequestId, context);
        RegRequestInfo regRequest = null;
        try {
            regRequest = regRequestAssembler.assemble(lprTransaction, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException in assembling: " + e.getMessage());
        }
        return regRequest;
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudent(String studentId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                                                                                                                      OperationFailedException, PermissionDeniedException {
        List<CourseRegistrationInfo> courseRegistrationList = new ArrayList<CourseRegistrationInfo>();

        List<LprInfo> courseLprList = lprService.getLprsByPersonAndLuiType(studentId, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, context);

        List<LprInfo> regGroupLprList = lprService.getLprsByPersonAndLuiType(studentId, LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, context);

        getCourseRegistration(studentId, courseRegistrationList, courseLprList, regGroupLprList, context);

        return courseRegistrationList;
    }

	@Override
	public List<ValidationResultInfo> verifyRegRequestForSubmission(
			String regRequestId, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateRegRequest(
			String validationTypeKey, String regRequestTypeKey,
			RegRequestInfo regRequestInfo, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegRequestInfo> getRegRequestsByIds(List<String> regRequestIds,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegRequestInfo> getUnsubmittedRegRequestsByRequestorAndTerm(
			String requestorId, String termId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCourseRegistrationIdsByType(
			String courseRegistrationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndCourseOffering(
			String studentId, String courseOfferingId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
                               OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<CourseRegistrationInfo> getCourseRegistrationsByCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CourseRegistrationInfo> searchForCourseRegistrations(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegRequestItemInfo> getRegRequestItemsByCourseOfferingAndStudent(
			String courseOfferingId, String studentId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForCourseRegistrationIds(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityRegistrationInfo> searchForActivityRegistrations(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForActivityRegistrationIds(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}
    **********/
}
