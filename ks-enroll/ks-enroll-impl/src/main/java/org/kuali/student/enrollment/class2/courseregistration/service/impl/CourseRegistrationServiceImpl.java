package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class2.courseregistration.service.assembler.CourseRegistrationAssembler;
import org.kuali.student.enrollment.class2.courseregistration.service.assembler.RegRequestAssembler;
import org.kuali.student.enrollment.class2.courseregistration.service.assembler.RegResponseAssembler;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.*;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitlistEntryInfo;
import org.kuali.student.enrollment.grading.dto.LoadInfo;
import org.kuali.student.enrollment.lpr.dto.*;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.lrc.infc.ResultValuesGroup;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class CourseRegistrationServiceImpl implements CourseRegistrationService {

    private LuiPersonRelationService lprService;
    private CourseOfferingService courseOfferingService;
    private RegRequestAssembler regRequestAssembler;
    private RegResponseAssembler regResponseAssembler;
    private CourseRegistrationAssembler courseRegistrationAssembler;

    private LRCService lrcService;

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public LuiPersonRelationService getLprService() {
        return lprService;
    }

    public void setLprService(LuiPersonRelationService lprService) {
        this.lprService = lprService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
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

    
    
    private  List <LprTransactionItemInfo>  createModifiedLprTransactionItemsForNew(RegRequestItemInfo regRequestItem, ContextInfo context )throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {

        List<LprTransactionItemInfo> newTransactionItems = new ArrayList<LprTransactionItemInfo>();

        String regGroupId = regRequestItem.getNewRegGroupId();
        RegistrationGroupInfo regGroup = courseOfferingService.getRegistrationGroup(regGroupId, context);
        LprTransactionItemInfo lprTransactionItem = regRequestAssembler.disassembleItem(regRequestItem, null, context);
        if (getAvailableSeatsForStudentInRegGroup(regRequestItem.getStudentId(), regGroupId, context) > 0) {
            List<LprTransactionItemInfo> lprActivityTransactionItems = new ArrayList<LprTransactionItemInfo>();
            for (String activityOfferingId : regGroup.getActivityOfferingIds()) {
                LprTransactionItemInfo activtyItemInfo = regRequestAssembler.disassembleItem(regRequestItem, null, context);
                regRequestItem.setId(null);
                activtyItemInfo.setNewLuiId(activityOfferingId);
                newTransactionItems.add(activtyItemInfo);
            }

            String courseOfferingId = regGroup.getCourseOfferingId();
            LprTransactionItemInfo courseOfferingItemInfo = regRequestAssembler.disassembleItem(regRequestItem, null, context);
            courseOfferingItemInfo.setNewLuiId(courseOfferingId);
            regRequestItem.setId(null);
            lprActivityTransactionItems.add(courseOfferingItemInfo);
            newTransactionItems.add(courseOfferingItemInfo);

        } else {
            // TODO: copy the transaction item and change the type
            // of the new one to be ADD TO WAITLIST
            // and then on that item mark the original with a state
            // of failed
          
            lprTransactionItem.setTypeKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TO_WAITLIST_TYPE_KEY);
            lprTransactionItem.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        }
        newTransactionItems.add(lprTransactionItem);
        return newTransactionItems;

    
    }
    
    
    private  List <LprTransactionItemInfo>  createModifiedLprTransactionItemsForDrop(RegRequestItemInfo regRequestItem, ContextInfo context )throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        List<LprTransactionItemInfo> newTransactionItems = new ArrayList<LprTransactionItemInfo>();

        String regGroupId = regRequestItem.getExistingRegGroupId();
        RegistrationGroupInfo regGroup = courseOfferingService.getRegistrationGroup(regGroupId, context);
        List<LprTransactionItemInfo> lprActivityTransactionItems = new ArrayList<LprTransactionItemInfo>();
        for (String activityOfferingId : regGroup.getActivityOfferingIds()) {
            LprTransactionItemInfo activtyItemInfo = regRequestAssembler.disassembleItem(regRequestItem, null, context);
            activtyItemInfo.setExistingLuiId(activityOfferingId);
            activtyItemInfo.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_DROP_TYPE_KEY);
            activtyItemInfo.setGroupId(regRequestItem.getId());
            newTransactionItems.add(activtyItemInfo);

        }

        String courseOfferingId = regGroup.getCourseOfferingId();
        LprTransactionItemInfo courseOfferingItemInfo = regRequestAssembler.disassembleItem(regRequestItem, null, context);
        courseOfferingItemInfo.setExistingLuiId(courseOfferingId);
        courseOfferingItemInfo.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_DROP_TYPE_KEY);
        courseOfferingItemInfo.setGroupId(regRequestItem.getId());
        lprActivityTransactionItems.add(courseOfferingItemInfo);
        newTransactionItems.add(courseOfferingItemInfo);
        regRequestAssembler.disassembleItem(regRequestItem, null, context);
        return newTransactionItems;

    }
    
    private LprTransactionInfo createModifiedTransactionItems(LprTransactionInfo storedLprTransaction, RegRequestInfo storedRegRequest, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        List<LprTransactionItemInfo> newTransactionItems = new ArrayList<LprTransactionItemInfo>();
        List<RegRequestItemInfo> regRequestItems = storedRegRequest.getRegRequestItems();
        for (RegRequestItemInfo regRequestItem : regRequestItems) {
            if (regRequestItem.getTypeKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY)
                    || regRequestItem.getTypeKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_DROP_TYPE_KEY)
                    || regRequestItem.getTypeKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_UPDATE_TYPE_KEY)) {
                if (regRequestItem.getTypeKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY)) {
                    
                    newTransactionItems.addAll(createModifiedLprTransactionItemsForNew(regRequestItem, context));
                
                } else if (regRequestItem.getTypeKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_DROP_TYPE_KEY)) {
                    
                    newTransactionItems.addAll(createModifiedLprTransactionItemsForDrop(regRequestItem, context ));
                }
                storedLprTransaction.setLprTransactionItems(newTransactionItems);

                storedLprTransaction = lprService.updateLprTransaction(storedLprTransaction.getId(), storedLprTransaction, context);

            }

        }

        return storedLprTransaction;
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getProcessByObjectType(String refObjectUri, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Boolean checkStudentEligibility(String studentId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return true;
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibilityForTerm(String studentId, String termKey, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<DateRangeInfo> getAppointmentWindows(String studentId, String termKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> checkStudentEligibiltyForCourseOffering(String studentId, String courseOfferingId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
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
    public Integer getAvailableSeatsInSeatpool(String seatpoolId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RegRequestInfo updateRegRequest(String regRequestId, RegRequestInfo regRequestInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        LprTransactionInfo lprTransactionInfo = regRequestAssembler.disassemble(regRequestInfo, context);
        return regRequestAssembler.assemble(lprService.updateLprTransaction(regRequestId, lprTransactionInfo, context), context);
    }

    @Override
    public StatusInfo deleteRegRequest(String regRequestId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return lprService.deleteLprTransaction(regRequestId, context);
    }

    @Override
    public List<ValidationResultInfo> validateRegRequest(RegRequestInfo regRequestInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> verifyRegRequest(RegRequestInfo regRequestInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public RegResponseInfo verifySavedReqRequest(String regRequestId, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RegRequestInfo createRegRequest(RegRequestInfo regRequestInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        LprTransactionInfo lprTransaction = regRequestAssembler.disassemble(regRequestInfo, context);
        LprTransactionInfo newLprTransaction = lprService.createLprTransaction(lprTransaction, context);
        RegRequestInfo createdRegRequestInfo = regRequestAssembler.assemble(newLprTransaction, context);
        return createdRegRequestInfo;
    }

    @Override
    public RegRequestInfo createRegRequestFromExisting(String existingRegRequestId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        LprTransactionInfo newLprTransaction = lprService.createLprTransactionFromExisting(existingRegRequestId, context);
        RegRequestInfo createdRegRequestInfo = regRequestAssembler.assemble(newLprTransaction, context);
        return createdRegRequestInfo;
    }

    @Override
    public RegResponseInfo submitRegRequest(String regRequestId, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, AlreadyExistsException {
        LprTransactionInfo storedLprTransaction = lprService.getLprTransaction(regRequestId, context);

        RegRequestInfo storedRegRequest = regRequestAssembler.assemble(storedLprTransaction, context);
        try {
            List<ValidationResultInfo> listValidationResult = validateRegRequest(storedRegRequest, context);

            if (listValidationResult != null && listValidationResult.size() > 0) {
                String message = "";
                String newline = System.getProperty("line.separator");
                for (ValidationResultInfo validationResultInfo : listValidationResult) {
                    if (validationResultInfo.getIsError()) {
                        message += newline + validationResultInfo.getMessage();
                        throw new OperationFailedException(message);
                    }
                }

            }
            List<ValidationResultInfo> verificationResultList = verifyRegRequest(storedRegRequest, context);
            for (ValidationResultInfo verificationResult : verificationResultList) {
                if (!verificationResult.getIsOk()) {
                    throw new DataValidationErrorException("Error while verifying registration request: " + verificationResult.getMessage());
                }
            }
        } catch (DataValidationErrorException dataValidException) {
            throw new OperationFailedException(dataValidException.getMessage(), dataValidException);
        }

        LprTransactionInfo multipleItemsTransaction = createModifiedTransactionItems(storedLprTransaction, storedRegRequest, context);

        LprTransactionInfo submittedLprTransaction = lprService.processLprTransaction(multipleItemsTransaction.getId(), context);

        RegResponseInfo returnRegResponse = regResponseAssembler.assemble(submittedLprTransaction, context);

/*        if (checkSuccessfulRegCriteria(returnRegResponse)) {

            createGradeRosterEntryForRegisteredStudent(submittedLprTransaction, context);

        }*/

        return returnRegResponse;

    }

    private boolean checkSuccessfulRegCriteria(RegResponseInfo returnRegResponse) {
        return true;
    }

    private void createGradeRosterEntryForRegisteredStudent(LprTransactionInfo submittedLprTransaction, ContextInfo context) throws DataValidationErrorException, AlreadyExistsException,
            InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {

        for (LprTransactionItemInfo lprItem : submittedLprTransaction.getLprTransactionItems()) {
            if (lprItem.getTypeKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY)) {
                LprRosterEntryInfo newLprRosterEntry = new LprRosterEntryInfo();
                newLprRosterEntry.setLprId(lprItem.getLprTransactionItemResult().getResultingLprId());

                newLprRosterEntry.setStateKey(LuiPersonRelationServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_NEW_STATE_KEY);

                List<LprRosterInfo> lprRosters = lprService.getLprRostersByLuiAndRosterType(lprItem.getNewLuiId(), LuiPersonRelationServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_TYPE_KEY,
                        context);
                if (lprRosters.size() == 1) {
                    newLprRosterEntry.setLprRosterId(lprRosters.get(0).getId());
                } else {
                    throw new OperationFailedException("The number of final grade rosters should be one for course offering: " + lprItem.getNewLuiId());
                }
                lprService.createLprRosterEntry(newLprRosterEntry, context);

            }

        }

    }

    @Override
    public StatusInfo cancelRegRequest(String regRequestId, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<RegRequestInfo> getRegRequestsByIdList(List<String> regRequestIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<RegRequestInfo> getRegRequestsForStudentByTerm(String studentId, String termKey, List<String> requestStates, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LprTransactionInfo> retrievedLprTransactions = lprService.getLprTransactionsForPersonByAtp(termKey, studentId, requestStates, context);
        List<RegRequestInfo> regRequestInfos = new ArrayList<RegRequestInfo>();
        for (LprTransactionInfo retrievedLprTransaction : retrievedLprTransactions) {
            regRequestInfos.add(regRequestAssembler.assemble(retrievedLprTransaction, context));
        }

        return regRequestInfos;
    }

    @Override
    public CourseWaitlistEntryInfo getCourseWaitlistEntry(String courseWaitlistEntryId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo updateCourseWaitlistEntry(String courseWaitlistEntryId, CourseWaitlistEntryInfo courseWaitlistEntryInfo, ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo reorderCourseWaitlistEntries(List<String> courseWaitlistEntryIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo insertCourseWaitlistEntryAtPosition(String courseWaitlistEntryId, Integer position, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo removeCourseWaitlistEntry(String courseWaitlistEntryId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo validateCourseWaitlistEntry(String validateTypeKey, CourseWaitlistEntryInfo courseWaitlistEntryInfo, ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RegResponseInfo registerStudentFromWaitlist(String courseWaitlistEntryId, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForRegGroup(String regGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentInCourseOffering(String courseOfferingId, String studentId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CourseWaitlistEntryInfo getCourseWaitlistEntryForStudentInRegGroup(String regGroupId, String studentId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentByTerm(String studentId, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CourseRegistrationInfo getCourseRegistration(String courseRegistrationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return new CourseRegistrationInfo();

    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByIdList(List<String> courseRegistrationIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<CourseRegistrationInfo> courseRegistrationInfos = new ArrayList();
        ResultValuesGroup rvGroup = null;
        List<LuiPersonRelationInfo> lprs = lprService.getLprsByIdList(courseRegistrationIds, context);
        for (LuiPersonRelationInfo lpr : lprs){
            for(String rvGroupKey : lpr.getResultValuesGroupKeys()){
                rvGroup = lrcService.getResultValuesGroup(rvGroupKey,context);
                if(StringUtils.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_GRADE,rvGroup.getTypeKey())){
                    break;
                }
            }
            courseRegistrationInfos.add(courseRegistrationAssembler.assemble(lpr,rvGroup, context));
        }

        return courseRegistrationInfos;

    }

    // TODO - post core slice need to ensure that the list has one
    private List<LuiPersonRelationInfo> filterLprByState(List<LuiPersonRelationInfo> lprInfoList, String stateKey) {
        List<LuiPersonRelationInfo> filteredLprInfoList = new ArrayList<LuiPersonRelationInfo>();
        for (LuiPersonRelationInfo lprInfo : filteredLprInfoList) {
            if (lprInfo.getStateKey().equals(stateKey))
                filteredLprInfoList.add(lprInfo);
        }
        return filteredLprInfoList;
    }

    @Override
    public CourseRegistrationInfo getActiveCourseRegistrationForStudentByCourseOffering(String studentId, String courseOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DisabledIdentifierException {
        
                
        return new CourseRegistrationInfo();
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudentByTerm(String studentId, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DisabledIdentifierException {

        List<CourseRegistrationInfo> courseRegistrationList = new ArrayList<CourseRegistrationInfo>();

        List<LuiPersonRelationInfo> courseLprList = lprService.getLprsByPersonForAtpAndLuiType(studentId, termKey, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, context);

        List<LuiPersonRelationInfo> regGroupLprList = lprService.getLprsByPersonForAtpAndLuiType(studentId, termKey, LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, context);

        for (LuiPersonRelationInfo courseOfferinglprInfo : courseLprList) {

            CourseOfferingInfo courseOfferingInfo = courseOfferingService.getCourseOffering(courseOfferinglprInfo.getLuiId(), context);

            List<RegistrationGroupInfo> regGroupList = courseOfferingService.getRegGroupsForCourseOffering(courseOfferinglprInfo.getLuiId(), context);

            for (RegistrationGroupInfo regGroup : regGroupList) {

                RegistrationGroupInfo reg = new RegistrationGroupInfo();

                Map<LuiPersonRelationInfo, ActivityOfferingInfo> activtiesLprInfoMap = new HashMap<LuiPersonRelationInfo, ActivityOfferingInfo>();

                for (LuiPersonRelationInfo regGroupLprInfo : regGroupLprList) {

                    if (regGroup.getId().equals(regGroupLprInfo.getLuiId())) {

                        reg = courseOfferingService.getRegistrationGroup(regGroup.getId(), context);

                        for (String activityOfferingId : regGroup.getActivityOfferingIds()) {

                            List<LuiPersonRelationInfo> lprsForActivity = lprService.getLprsByLuiAndPerson(studentId, activityOfferingId, context);

                            for (LuiPersonRelationInfo activityLpr : lprsForActivity) {

                                if (activityLpr.getStateKey().equals(regGroupLprInfo.getStateKey())) {

                                    ActivityOfferingInfo activityOffering = courseOfferingService.getActivityOffering(activityOfferingId, context);

                                    activtiesLprInfoMap.put(activityLpr, activityOffering);
                                }
                            }

                        }
                    }

                    courseRegistrationList.add(courseRegistrationAssembler.assemble(courseOfferinglprInfo, courseOfferingInfo, activtiesLprInfoMap, regGroupLprInfo, reg, context));

                }

            }

        }

        return courseRegistrationList;
    }

    @Override
    public List<CourseRegistrationInfo> getActiveCourseRegistrationsByCourseOfferingId(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CourseRegistrationInfo> courseRegInfoList = courseRegistrationAssembler.assembleList(lprService.getLprsByLui(courseOfferingId, context), context);
        return courseRegInfoList;
    }

    @Override
    public List<RegRequestInfo> getRegRequestsForCourseRegistration(String courseRegistrationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RegRequestInfo> regrequests = new ArrayList<RegRequestInfo>();
        List<LprTransactionInfo> lprTransactions = lprService.getLprTransactionsForLpr(courseRegistrationId, context);
        for (LprTransactionInfo lprTransaction : lprTransactions) {

            regrequests.add(regRequestAssembler.assemble(lprTransaction, context));

        }

        return regrequests;
    }

    @Override
    public List<RegRequestInfo> getRegRequestsForCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return regRequestAssembler.assembleList(lprService.getLprTransactionsForLui(courseOfferingId, context), context);

    }

    @Override
    public List<RegRequestInfo> getRegRequestsForCourseOfferingByStudent(String courseOfferingId, String studentId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return regRequestAssembler.assembleList(lprService.getLprTransactionsForPersonByLui(studentId, courseOfferingId, context), context);
    }

    @Override
    public List<CourseRegistrationInfo> searchForCourseRegistrations(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForCourseOfferingRegistrationIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ActivityRegistrationInfo> searchForActivityRegistrations(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForActivityRegistrationIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<RegGroupRegistrationInfo> searchForRegGroupRegistrations(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForRegGroupRegistrationIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseWaitlistEntryInfo> searchForCourseWaitlistEntries(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForCourseWaitlistEntryIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RegRequestInfo getRegRequest(String regRequestId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        LprTransactionInfo lprTransaction = lprService.getLprTransaction(regRequestId, context);
        RegRequestInfo regRequest = regRequestAssembler.assemble(lprTransaction, context);
        return regRequest;
    }

    @Override
    public StatusInfo deleteCourseWaitlistEntry(String courseWaitlistEntryId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RegResponseInfo dropStudentsFromRegGroups(List<String> regGroupIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RegResponseInfo moveStudentsBetweenRegGroups(String sourceRegGroupId, String destinationRegGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudentByCourseOffering(String studentId, String courseOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DisabledIdentifierException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseRegistrationInfo> getDroppedCourseRegistrationsByCourseOfferingId(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudent(String studentId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DisabledIdentifierException {
        List<LuiPersonRelationInfo> courseLprList = new ArrayList<LuiPersonRelationInfo>();
        List<LuiPersonRelationInfo> lprs = lprService.getLprsByPerson(studentId, context);

        if (lprs != null && !lprs.isEmpty()) {
            for (LuiPersonRelationInfo lpr : lprs) {
                if (lpr.getTypeKey() != null && lpr.getTypeKey().equals(LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY)) {
                    courseLprList.add(lpr);
                }
            }
        }

        List<CourseRegistrationInfo> courseRegistrationList = new ArrayList<CourseRegistrationInfo>();

        for (LuiPersonRelationInfo courseLpr : courseLprList) {

            courseRegistrationList.add(getActiveCourseRegistrationForStudentByCourseOffering(studentId, courseLpr.getLuiId(), context));

        }

        return courseRegistrationList;
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
