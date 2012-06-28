package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.statement.util.PropositionBuilder;
import org.kuali.student.core.statement.util.RulesEvaluationUtil;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.grading.dto.LoadInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.roster.dto.LprRosterInfo;
import org.kuali.student.enrollment.roster.service.LprRosterService;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import org.kuali.student.r2.core.process.service.ProcessService;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultValuesGroup;
import org.kuali.student.r2.lum.lrc.service.LRCService;


public class CourseRegistrationServiceImpl 
    extends AbstractCourseRegistrationService
    implements CourseRegistrationService {

    private LprService lprService;
    private CourseOfferingService courseOfferingService;
//    private LprRosterService lprRosterService;
    /*    private RegRequestAssembler regRequestAssembler;
    private RegResponseAssembler regResponseAssembler;
    private CourseRegistrationAssembler courseRegistrationAssembler;*/
//    private StatementService statementService;
//    private CourseService courseService;
//    private PropositionBuilder propositionBuilder;
//    private RulesEvaluationUtil rulesEvaluationUtil;
//    private ProcessService processService;
//    private LRCService lrcService;

    
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
            statements = courseService.getCourseStatements(courseOffering.getCourseId(), null, null);
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
                } catch (org.kuali.student.common.exceptions.InvalidParameterException e) {
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
