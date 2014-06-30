package org.kuali.student.enrollment.class2.examoffering.service.facade;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.enrollment.class2.examoffering.krms.evaluator.ExamOfferingSlottingEvaluator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.exam.service.ExamService;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the Application Service Layer to provide the functionally specified functionality
 * using several service calls.
 *
 * @author Kuali Student Team
 */
public class ExamOfferingServiceFacadeImpl implements ExamOfferingServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamOfferingServiceFacadeImpl.class);

    /** Properties **/
    /**
     * System property to indicate if the institution uses the final exam matrix for slotting or not. This setting
     * will override all other settings.
     *
     * Note: Not to be confused with the useFinalExamMatrix property on the Course Offering that indicates if
     * the CO is configured to use matrix or not.
     */
    private boolean useFinalExamMatrix;

    /**
     * System property to indicate if the slotting process should use the Activity Offering's location for the
     * Exam Offering if the EO location is not specified.
     */
    private boolean setLocation;

    /**
     * System property to indicate if the institution uses the bulk exam offering process or a dynamically
     * triggered process.
     *
     * When true, eo will be created on each generate call.
     * When false the system will first check if the bulk process was already executed for the relevant SOC and
     * only then create or update the EOs.
     */
    private boolean generateEODynamically;

    /** KRMS Evaluator **/
    private ExamOfferingSlottingEvaluator scheduleEvaluator;

    /** Services **/
    private AtpService atpService;
    private ExamService examService;
    private CourseOfferingService courseOfferingService;
    private ExamOfferingService examOfferingService;
    private CourseOfferingSetService socService;
    private SchedulingService schedulingService;
    private TypeService typeService;

    @Override
    public ExamOfferingResult generateFinalExamOffering(CourseOfferingInfo courseOfferingInfo, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        ExamOfferingContext examOfferingContext = new ExamOfferingContext(courseOfferingInfo);
        if(!validateExamOfferingContext(examOfferingContext, optionKeys, context)){
            return examOfferingContext.getValidationResult();
        }
        return generateFinalExamOffering(examOfferingContext, optionKeys, context);

    }

    @Override
    public ExamOfferingResult generateFinalExamOffering(ExamOfferingContext examOfferingContext, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        if(!validateExamOfferingContext(examOfferingContext, optionKeys, context)){
            return examOfferingContext.getValidationResult();
        }

        CourseOfferingInfo courseOfferingInfo = examOfferingContext.getCourseOffering();
        if (ExamOfferingContext.Driver.PER_AO.equals(examOfferingContext.getDriver())) {
            return generateFinalExamOfferingsPerAO(examOfferingContext, optionKeys, context);
        } else if (ExamOfferingContext.Driver.PER_FO.equals(examOfferingContext.getDriver())) {
            return generateFinalExamOfferingsPerFO(examOfferingContext, optionKeys, context);
        } else if (ExamOfferingContext.Driver.PER_CO.equals(examOfferingContext.getDriver())) {
            return generateFinalExamOfferingsPerCO(examOfferingContext, optionKeys, context);
        } else if (ExamOfferingContext.Driver.NONE.equals(examOfferingContext.getDriver())) {
            if ((!optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)) && (examOfferingContext.isSocPublished())) {
                cancelFinalExamOfferings(courseOfferingInfo.getId(), context);
            } else {
                removeFinalExamOfferingsFromCO(courseOfferingInfo.getId(), context);
            }
        }

        return new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_DRIVER_UNKNOWN);
    }

    protected boolean validateExamOfferingContext(ExamOfferingContext examOfferingContext, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        //Check Term
        if(examOfferingContext.getTermType()==null){
            try {
                examOfferingContext.setTermType(this.getAtpService().getAtp(examOfferingContext.getTermId(), context).getTypeKey());
            } catch (DoesNotExistException e) {
                examOfferingContext.setValidationResult(new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_TERM_NOT_FOUND));
                return false;
            }
        }

        //Check exam period id.
        if(examOfferingContext.getExamPeriodId()==null){
            try {
                examOfferingContext.setExamPeriodId(this.getExamPeriodId(examOfferingContext.getTermId(), context));
            } catch (DoesNotExistException dne){
                examOfferingContext.setValidationResult(new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_EXAM_PERIOD_NOT_FOUND));
                return false;
            }
        }

        //Check SOC.
        if(examOfferingContext.getSoc()==null){
            examOfferingContext.setSoc(CourseOfferingSetUtil.getMainSocForTermId(examOfferingContext.getTermId(), context));
            if(examOfferingContext.getSoc()==null){
                examOfferingContext.setValidationResult(new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_SOC_NOT_FOUND));
                return false;
            }
        }

        //Check if Bulk process has completed when system is not configured to use generate on dynamic triggers only.
        if(!this.isGenerateEODynamically() && !optionKeys.contains(ExamOfferingServiceConstants.EXAM_OFFERING_BULK_PROCESS) ) {
            String attrValue = examOfferingContext.getSoc().getAttributeValue(CourseOfferingSetServiceConstants.EO_SLOTTING_STATE_COMPLETED);
            if(attrValue==null){
                examOfferingContext.setValidationResult(new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_BULK_PROCESS_NOT_COMPLETED));
                return false;
            }
        }

        return true;
    }

    @Override
    public String getExamPeriodId(String termID, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        //Get the Exam Period Id for the term.
        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByTypeAndAtp(termID,
                AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TERM2EXAMPERIOD_TYPE_KEY, context);

        AtpAtpRelationInfo atpRelation = KSCollectionUtils.getOptionalZeroElement(results);
        if(atpRelation != null){
            return atpRelation.getRelatedAtpId();
        }

        return null;
    }

    /**
     * The Matrix Override attribute is set to TRUE when the user manually entered slotting information on the Manage Exam
     * Offering screen. When this attribute exist and is set to TRUE, the system should not re-slot this exam offering with
     * the matrix.
     */
    protected boolean userOverride(ExamOfferingInfo examOfferingInfo) {
        return Boolean.parseBoolean(examOfferingInfo.getAttributeValue(ExamOfferingServiceConstants.EXAM_OFFERING_MATRIX_OVERRIDE_ATTR));
    }

    /**
     * This method contains all business logic to decide if we should do the slotting with the Final Exam Matrix or not.
     * @return
     */
    protected boolean checkForMatrixSlotting(ExamOfferingContext eoContext, ExamOfferingInfo examOffering){

        // First check if the system is configured to use the Final Exam Matrix.
        if(!this.isUseFinalExamMatrix()){
            return false;
        }

        // Check if the Course Offering is set to use the Final Exam Matrix.
        if(!eoContext.useFinalExamMatrix()){
            return false;
        }

        // Check if the user did an override on the exam offering.
        if(userOverride(examOffering)){
            return false;
        }

        return true;
    }

    @Override
    public ExamOfferingResult generateFinalExamOfferingsPerCO(ExamOfferingContext examOfferingContext, List<String> optionKeys, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        if(!validateExamOfferingContext(examOfferingContext, optionKeys, context)){
            return examOfferingContext.getValidationResult();
        }

        CourseOfferingInfo coInfo = examOfferingContext.getCourseOffering();
        ExamOfferingResult result = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_GENERATED_PER_CO);
        Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations = loadExamOfferingRelationships(coInfo.getId(), context);
        if (optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)) {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }

        ExamOfferingInfo eo = null;
        Map<String, ExamOfferingInfo> eos = loadExamOfferings(foToEoRelations, context);
        //Do not create exam offerings for canceled course offerings.
        if (!LuiServiceConstants.LUI_CO_STATE_CANCELED_KEY.equals(coInfo.getStateKey())) {
        for (Map.Entry<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {

            //Get all existing eo as per co driver, and remove them from the map.
            List<ExamOfferingRelationInfo> eors = getExistingExamOfferingsPerDriver(eos, foEntry.getValue(), ExamOfferingContext.Driver.PER_CO.name());

                //Create new exam offerings per CO
                for (ExamOfferingRelationInfo eoRelation : eors) {
                    eo = eos.get(eoRelation.getExamOfferingId());
                    if (eo.getStateKey().equals(ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY)) {
                        this.getExamOfferingService().changeExamOfferingState(eoRelation.getExamOfferingId(),
                                ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY, context);
                    }
                }

                ExamOfferingResult foResult;
                if (eo == null) {

                    //Retrieve corresponding eo state for co.
                    String eoState = this.getExamOfferingStateForCourseOffering(coInfo);
                    //Create a new Exam Offering
                    eo = createExamOffering(examOfferingContext.getExamPeriodId(), eoState,
                            ExamOfferingContext.Driver.PER_CO.name(), new ArrayList<AttributeInfo>(), context);
                    foResult = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_CREATED);
                } else {
                    foResult = removeExamOfferingRDLIfNeeded(eo, examOfferingContext, context);
                }

                //(re)perform slotting if use fe matrix toggle is selected and use did not override timeslot.
                if (checkForMatrixSlotting(examOfferingContext, eo)) {
                    foResult.getChildren().add(this.getScheduleEvaluator().executeRuleForCOSlotting(coInfo, eo.getId(),
                            examOfferingContext.getTermType(), new ArrayList<String>(), userOverride(eo), context));
                }

                //Create new Exam Offering Relationship
                List<ActivityOfferingInfo> aoInfos = getAOsForFoId(foEntry.getKey().getId(), examOfferingContext.getFoIdToListOfAOs(), context);
                createExamOfferingRelationPerFO(foEntry.getKey().getId(), eo.getId(), aoInfos, context);

                // Update the result.
                Map<String, String> contextParms = new HashMap<String, String>();
                contextParms.put(CourseOfferingServiceConstants.CONTEXT_ELEMENT_COURSE_OFFERING_CODE, coInfo.getCourseOfferingCode());
                foResult.setContext(contextParms);
                result.getChildren().add(foResult);
            }
        }


        removeFinalExamOfferingIfNeeded(examOfferingContext, context, foToEoRelations, eos);
        return result;
    }

    protected List<ActivityOfferingInfo> getAOsForFoId(String foId, Map<String, List<ActivityOfferingInfo>> foIdToListOfAO, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (foIdToListOfAO != null && !foIdToListOfAO.isEmpty()) {
            return foIdToListOfAO.get(foId);
        } else {
            return this.getCourseOfferingService().getActivityOfferingsByFormatOffering(
                    foId, context);
        }
    }

    /** Removes all SheduleRequestInfoSets and ScheduleRequestInfos for given Exam Offering
     *
     * @param examOfferingInfo
     */
    protected ExamOfferingResult removeExamOfferingRDLIfNeeded(ExamOfferingInfo examOfferingInfo, ExamOfferingContext examOfferingContext, ContextInfo context) {

        // Do not remove if the user did an override on the exam offering.
        if(this.userOverride(examOfferingInfo)){
            return new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_UNCHANGED);
        }

        //Only remove RDL for Exam Offering if use fe matrix toggle was deselected.
        if(examOfferingContext.useFinalExamMatrix()){
            return new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_UNCHANGED);
        }

        //Remove the RDLs for this exam offering.
        List<ScheduleRequestSetInfo> scheduleRequestSetInfoList = null;
        try {
            scheduleRequestSetInfoList = getSchedulingService().getScheduleRequestSetsByRefObject(ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING, examOfferingInfo.getId(), context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (scheduleRequestSetInfoList != null && !scheduleRequestSetInfoList.isEmpty()) {
            try {
                for (ScheduleRequestSetInfo scheduleRequestSetInfo : scheduleRequestSetInfoList) {
                    List<ScheduleRequestInfo> scheduleRequestInfoList = getSchedulingService().getScheduleRequestsByScheduleRequestSet(scheduleRequestSetInfo.getId(), context);
                    for (ScheduleRequestInfo scheduleRequestInfo : scheduleRequestInfoList) {
                        getSchedulingService().deleteScheduleRequest(scheduleRequestInfo.getId(), context);
                    }
                    getSchedulingService().deleteScheduleRequestSet(scheduleRequestSetInfo.getId(), context);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_UPDATED);
    }

    @Override
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByCourseOffering(String courseOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, DoesNotExistException, PermissionDeniedException {

        List<String> foIds = new ArrayList<String>();
        List<FormatOfferingInfo> fos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId,
                ContextUtils.createDefaultContextInfo());
        for(FormatOfferingInfo fo : fos){
            foIds.add(fo.getId());
        }

        //Retrieve ExamOfferingRelationInfos
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.in("lui.id", foIds.toArray()));

        QueryByCriteria criteria = qbcBuilder.build();
        return this.getExamOfferingService().searchForExamOfferingRelations(criteria, contextInfo);
    }

    @Override
    public ExamOfferingResult generateFinalExamOfferingsPerFO(ExamOfferingContext examOfferingContext, List<String> optionKeys, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        if(!validateExamOfferingContext(examOfferingContext, optionKeys, context)){
            return examOfferingContext.getValidationResult();
        }

        ExamOfferingResult result = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_GENERATED_PER_FO);

        //Retrieve all format offerings linked to the course offering.
        Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations = loadExamOfferingRelationships(examOfferingContext.getCourseOffering().getId(), context);
        if (optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)) {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }

        Map<String, ExamOfferingInfo> eos = loadExamOfferings(foToEoRelations, context);
        for (Map.Entry<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {

            //Get all existing eo as per fo driver, and remove them from the map.
            List<ExamOfferingRelationInfo> eors = getExistingExamOfferingsPerDriver(eos, foEntry.getValue(), ExamOfferingContext.Driver.PER_FO.name());

            //Create new exam offerings per FO
            boolean hasEo = false;
            for (ExamOfferingRelationInfo eoRelation : eors) {
                ExamOfferingInfo eo = eos.get(eoRelation.getExamOfferingId());
                if (eo.getStateKey().equals(ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY)) {
                    this.getExamOfferingService().changeExamOfferingState(eoRelation.getExamOfferingId(),
                            ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY, context);
                    hasEo = true;
                    break;
                }
            }

            if (!hasEo) {
                //Create a new Exam Offering
                ExamOfferingInfo eo = createExamOffering(examOfferingContext.getExamPeriodId(), ExamOfferingContext.Driver.PER_FO.name(),
                        ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY, new ArrayList<AttributeInfo>(), context);
                result.getChildren().add(new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_CREATED));

                //Create new Exam Offering Relationship
                List<ActivityOfferingInfo> aoInfos = getAOsForFoId(foEntry.getKey().getId(), examOfferingContext.getFoIdToListOfAOs(), context);
                createExamOfferingRelationPerFO(foEntry.getKey().getId(), eo.getId(), aoInfos, context);
            } else {
                result.getChildren().add(new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_UPDATED));
            }
        }

        removeFinalExamOfferingIfNeeded(examOfferingContext, context, foToEoRelations, eos);
        return result;
    }

    @Override
    public ExamOfferingResult generateFinalExamOfferingsPerAO(ExamOfferingContext examOfferingContext, List<String> optionKeys, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        if(!validateExamOfferingContext(examOfferingContext, optionKeys, context)){
            return examOfferingContext.getValidationResult();
        }

        ExamOfferingResult result = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_GENERATED_PER_AO);

        //Retrieve all format offerings linked to the course offering.
        Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations = loadExamOfferingRelationships(examOfferingContext.getCourseOffering().getId(), context);
        if (optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)) {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }

        Map<String, ExamOfferingInfo> eos = loadExamOfferings(foToEoRelations, context);
        for (Map.Entry<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {

            //Get all existing eo as per ao driver, and remove them from the map.
            List<ExamOfferingRelationInfo> eors = getExistingExamOfferingsPerDriver(eos, foEntry.getValue(), ExamOfferingContext.Driver.PER_AO.name());

            TypeInfo finalExamLevelType = null;
            if (foEntry.getKey().getFinalExamLevelTypeKey() != null) {
                finalExamLevelType = this.getTypeService().getType(foEntry.getKey().getFinalExamLevelTypeKey(), context);
            }

            //Create new exam offerings per AO
            List<ActivityOfferingInfo> aoInfos = this.getAOsForFoId(foEntry.getKey().getId(), examOfferingContext.getFoIdToListOfAOs(), context);
            if (aoInfos != null && !aoInfos.isEmpty()) {
                for (ActivityOfferingInfo aoInfo : aoInfos) {
                    //Do not create exam offerings for canceled activity offerings.
                    if (LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY.equals(aoInfo.getStateKey())) continue;

                    //Check and Update EO type according to allowed FO
                    if (!checkDriverActivity(aoInfo, eors, finalExamLevelType, examOfferingContext.isSocPublished(), context)) {
                        continue; //next aoInfo if driver activity does not match.
                    }

                    ExamOfferingInfo eo = null;
                    for (ExamOfferingRelationInfo eoRelation : eors) {
                        if (eoRelation.getActivityOfferingIds().contains(aoInfo.getId())) {
                            eo = eos.get(eoRelation.getExamOfferingId());
                            if (eo.getStateKey().equals(ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY)) {
                                this.getExamOfferingService().changeExamOfferingState(eoRelation.getExamOfferingId(),
                                        ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY, context);
                            }
                            break;
                        }
                    }

                    ExamOfferingResult aoResult = null;
                    if (eo == null) {
                        //Retrieve corresponding eo state for ao.
                        String eoState = this.getExamOfferingStateForActivityOffering(aoInfo);
                        eo = createFinalExamOfferingPerAO(foEntry.getKey().getId(), aoInfo, foEntry.getKey().getFinalExamLevelTypeKey(),
                                examOfferingContext.getExamPeriodId(), eoState, context);

                        aoResult = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_CREATED);
                    } else {
                        aoResult = removeExamOfferingRDLIfNeeded(eo, examOfferingContext, context);
                    }

                    //(re)perform slotting if use fe matrix toggle is selected and use did not override timeslot.
                    if (checkForMatrixSlotting(examOfferingContext, eo)) {
                        aoResult.getChildren().add(this.getScheduleEvaluator().executeRuleForAOSlotting(aoInfo, eo.getId(),
                                examOfferingContext.getTermType(), getAOEvaluatorOptions(), userOverride(eo), context));
                    }

                    //Update the result.
                    Map<String, String> contextParms = new HashMap<String, String>();
                    contextParms.put(CourseOfferingServiceConstants.CONTEXT_ELEMENT_COURSE_OFFERING_CODE, aoInfo.getCourseOfferingCode());
                    contextParms.put(CourseOfferingServiceConstants.CONTEXT_ELEMENT_ACTIVITY_OFFERING_CODE, aoInfo.getActivityCode());
                    aoResult.setContext(contextParms);
                    result.getChildren().add(aoResult);
                }
            }
        }

        removeFinalExamOfferingIfNeeded(examOfferingContext, context, foToEoRelations, eos);
        return result;
    }

    protected void removeFinalExamOfferingIfNeeded(ExamOfferingContext examOfferingContext, ContextInfo context, Map<FormatOfferingInfo,
            List<ExamOfferingRelationInfo>> foToEoRelations, Map<String, ExamOfferingInfo> eos)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (examOfferingContext.isSocPublished()) {
            cancelFinalExamOfferings(foToEoRelations, eos, context);
        } else {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }
    }

    /**
     * Check if the ao type matches the fo driver activity. If not remove or cancel the exam offerings based on
     * the soc state, else return true.
     */
    protected boolean checkDriverActivity(ActivityOfferingInfo aoInfo, List<ExamOfferingRelationInfo> eors, TypeInfo finalExamLevelType,
                                        boolean socPublished, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (finalExamLevelType == null) {
            return true;
        }

        TypeInfo activityType = this.getTypeService().getType(aoInfo.getTypeKey(), context);
        //cancel if eo exits but not in sync with allowed FO
        if (!activityType.getName().equals(finalExamLevelType.getName())) {
            ExamOfferingRelationInfo eor = null;
            for (ExamOfferingRelationInfo eoRelation : eors) {
                if (eoRelation.getActivityOfferingIds().contains(aoInfo.getId())) {
                    eor = eoRelation;
                    break;
                }
            }

            //Remove or cancel unwanted exam offering based on soc state.
            if (eor != null) {
                if (socPublished) {
                    this.getExamOfferingService().changeExamOfferingState(eor.getExamOfferingId(),
                            ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY, context);
                } else {
                    this.getExamOfferingService().deleteExamOfferingRelation(eor.getId(), context);
                    this.getExamOfferingService().deleteExamOffering(eor.getExamOfferingId(), context);
                }
            }
            return false;
        }
        return true;
    }

    protected String getExamOfferingStateForActivityOffering(ActivityOfferingInfo aoInfo) {
        if (LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY.equals(aoInfo.getStateKey())){
            return ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY;
        }
        if (LuiServiceConstants.LUI_AO_STATE_SUSPENDED_KEY.equals(aoInfo.getStateKey())){
            return ExamOfferingServiceConstants.EXAM_OFFERING_SUSPENDED_STATE_KEY;
        }
        return ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY;
    }

    protected String getExamOfferingStateForCourseOffering(CourseOfferingInfo coInfo) {
        if (LuiServiceConstants.LUI_CO_STATE_SUSPENDED_KEY.equals(coInfo.getStateKey())){
            return ExamOfferingServiceConstants.EXAM_OFFERING_SUSPENDED_STATE_KEY;
        }
        return ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY;
    }

    protected List<ExamOfferingRelationInfo> getExistingExamOfferingsPerDriver(Map<String, ExamOfferingInfo> eos,
                                                                             List<ExamOfferingRelationInfo> eors, String driver)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<ExamOfferingRelationInfo> eorsForDriver = new ArrayList<ExamOfferingRelationInfo>();
        for (ExamOfferingRelationInfo eoRelation : eors) {
            ExamOfferingInfo eo = eos.get(eoRelation.getExamOfferingId());

            if (isPerDriver(driver, eo)) {
                eorsForDriver.add(eoRelation);
            }
        }
        for (ExamOfferingRelationInfo eoRelation : eorsForDriver) {
            eors.remove(eoRelation);
        }
        return eorsForDriver;
    }

    protected boolean isPerDriver(String driver, ExamOfferingInfo eo) {
        for (AttributeInfo attribute : eo.getAttributes()) {
            if (attribute.getKey().equals(ExamOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR)) {
                if (attribute.getValue().equals(driver)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> loadExamOfferingRelationships(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //Retrieve all format offerings linked to the course offering.
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, context);
        Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations = new HashMap<FormatOfferingInfo, List<ExamOfferingRelationInfo>>();
        for (FormatOfferingInfo foInfo : foInfos) {
            List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                    foInfo.getId(), context);
            foToEoRelations.put(foInfo, eoRelations);
        }
        return foToEoRelations;
    }

    protected Map<String, ExamOfferingInfo> loadExamOfferings(Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        Map<String, ExamOfferingInfo> eos = new HashMap<String, ExamOfferingInfo>();
        for (Map.Entry<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {
            for (ExamOfferingRelationInfo eoRelation : foEntry.getValue()) {
                ExamOfferingInfo eo = eos.get(eoRelation.getExamOfferingId());
                if (eo == null) {
                    eo = this.getExamOfferingService().getExamOffering(eoRelation.getExamOfferingId(), context);
                    eos.put(eo.getId(), eo);
                }
            }
        }
        return eos;
    }

    protected void createExamOfferingRelationPerFO(String foId, String eoId, List<ActivityOfferingInfo> aoInfos, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DataValidationErrorException, ReadOnlyException {
        //Create new Exam Offering Relationship
        List<String> aoIds = new ArrayList<String>();
        for (ActivityOfferingInfo aoInfo : aoInfos) {
            if(!LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY.equals(aoInfo.getStateKey())){
                aoIds.add(aoInfo.getId());
            }
        }
        createExamOfferingRelation(foId, eoId, aoIds, context);
    }

    protected ExamOfferingInfo createFinalExamOfferingPerAO(String foId, ActivityOfferingInfo activityOffering, String activityDriver,
                                                            String examPeriodId, String stateKey, ContextInfo context)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException,
            DoesNotExistException, DataValidationErrorException, ReadOnlyException {

        //Create a new Exam Offering
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        AttributeInfo attribute = new AttributeInfo();
        attribute.setKey(ExamOfferingServiceConstants.FINAL_EXAM_ACTIVITY_DRIVER_ATTR);
        attribute.setValue(activityDriver);
        attributes.add(attribute);

        ExamOfferingInfo eo = this.createExamOffering(examPeriodId, stateKey, ExamOfferingContext.Driver.PER_AO.name(), attributes, context);

        //Create new Exam Offering Relationship
        List<String> aoIds = new ArrayList<String>();
        aoIds.add(activityOffering.getId());
        createExamOfferingRelation(foId, eo.getId(), aoIds, context);

        return eo;
    }

    @Override
    public void removeFinalExamOfferingsFromCO(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        removeFinalExamOfferingsFromCO(this.loadExamOfferingRelationships(courseOfferingId, context), context);
    }

    protected void removeFinalExamOfferingsFromCO(Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations, ContextInfo context) throws
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            DoesNotExistException {

        Set<String> eoIds = new HashSet<String>();
        for (Map.Entry<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {
            //Remove the relationships.
            for (ExamOfferingRelationInfo eoRelation : foEntry.getValue()) {
                this.getExamOfferingService().deleteExamOfferingRelation(eoRelation.getId(), context);
                eoIds.add(eoRelation.getExamOfferingId());
            }
            foToEoRelations.put(foEntry.getKey(), new ArrayList());
        }

        //Delete orphaned exam offerings.
        for (String eoId : eoIds) {
            this.getExamOfferingService().deleteExamOffering(eoId, context);
        }
    }

    protected void cancelFinalExamOfferings(Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations, Map<String, ExamOfferingInfo> eos, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        for (Map.Entry<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {
            for (ExamOfferingRelationInfo eoRelation : foEntry.getValue()) {
                ExamOfferingInfo eo = eos.get(eoRelation.getExamOfferingId());
                if (!eo.getStateKey().equals(ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY)) {
                    this.getExamOfferingService().changeExamOfferingState(eoRelation.getExamOfferingId(),
                            ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY, context);
                }
            }
        }
    }

    protected void cancelFinalExamOfferings(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        changeFinalExamOfferingsState(courseOfferingId, ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY, context);
    }

    @Override
    public void changeFinalExamOfferingsState(String courseOfferingId, String stateKey, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {

        //Retrieve all format offerings linked to the course offering.
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId,
                context);

        for (FormatOfferingInfo foInfo : foInfos) {
            //Retrieve all exam offerings linked to the format offering.
            List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                    foInfo.getId(), context);
            for (ExamOfferingRelationInfo eoRelation : eoRelations) {
                this.getExamOfferingService().changeExamOfferingState(eoRelation.getExamOfferingId(), stateKey, context);
            }
        }
    }

    /**
     * Create a new Exam Offering.
     *
     * @param examPeriodId
     * @param stateKey
     * @param driver
     * @param context
     * @return
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws ReadOnlyException
     */
    protected ExamOfferingInfo createExamOffering(String examPeriodId, String stateKey, String driver, List<AttributeInfo> attributes,
                                                ContextInfo context) throws MissingParameterException,
            InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException,
            DataValidationErrorException, ReadOnlyException {

        if (driver != null) {
            AttributeInfo attribute = new AttributeInfo();
            attribute.setKey(ExamOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR);
            attribute.setValue(driver);
            attributes.add(attribute);
        }

        ExamOfferingInfo eo = new ExamOfferingInfo();
        eo.setTypeKey(ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY);
        eo.setStateKey(stateKey);
        eo.setExamId(this.getCanonicalExam(context));
        eo.setExamPeriodId(examPeriodId);
        eo.getAttributes().addAll(attributes);

        return this.getExamOfferingService().createExamOffering(eo.getExamPeriodId(),
                eo.getExamId(), eo.getTypeKey(), eo, context);
    }

    /**
     * Create a new Exam Offering Relationship.
     *
     * @param formatOfferingId
     * @param examOfferingId
     * @param aoIds
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    protected ExamOfferingRelationInfo createExamOfferingRelation(String formatOfferingId, String examOfferingId,
                                                                List<String> aoIds, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        ExamOfferingRelationInfo eoRelation = new ExamOfferingRelationInfo();
        eoRelation.setFormatOfferingId(formatOfferingId);
        eoRelation.setExamOfferingId(examOfferingId);
        eoRelation.setActivityOfferingIds(aoIds);
        eoRelation.setPopulationIds(new ArrayList<String>());
        eoRelation.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY);

        return this.getExamOfferingService().createExamOfferingRelation(formatOfferingId,
                examOfferingId, eoRelation.getTypeKey(), eoRelation, context);
    }

    protected String getCanonicalExam(ContextInfo context) throws MissingParameterException, InvalidParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> examIds = this.getExamService().getExamIdsByType(ExamServiceConstants.EXAM_FINAL_TYPE_KEY, context);
        return KSCollectionUtils.getOptionalZeroElement(examIds);
    }

    @Override
    public ExamOfferingResult reslotExamOffering(ExamOfferingInfo examOfferingInfo, ExamOfferingContext examOfferingContext, List<String> optionKeys, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        if(!validateExamOfferingContext(examOfferingContext, optionKeys, context)){
            return examOfferingContext.getValidationResult();
        }

        if (ExamOfferingContext.Driver.PER_AO.equals(examOfferingContext.getDriver())) {
            if(examOfferingContext.getFoIdToListOfAOs()==null){
                throw new MissingParameterException("Activity Offerings is null.");
            }

            ExamOfferingResult result = new ExamOfferingResult();
            for (Map.Entry<String, List<ActivityOfferingInfo>> foEntry : examOfferingContext.getFoIdToListOfAOs().entrySet()) {
                for(ActivityOfferingInfo activityOfferingInfo : foEntry.getValue()){
                    result.getChildren().add(this.getScheduleEvaluator().executeRuleForAOSlotting(activityOfferingInfo,
                            examOfferingInfo.getId(), examOfferingContext.getTermType(), getAOEvaluatorOptions(), userOverride(examOfferingInfo), context));
                }
            }
            return result;
        } else if (ExamOfferingContext.Driver.PER_CO.equals(examOfferingContext.getDriver())) {
            return this.getScheduleEvaluator().executeRuleForCOSlotting(examOfferingContext.getCourseOffering(), examOfferingInfo.getId(),
                    examOfferingContext.getTermType(), new ArrayList<String>(), userOverride(examOfferingInfo), context);
        }

        return null;
    }

    protected List<String> getAOEvaluatorOptions() {
        List<String> evaluatorOptions = new ArrayList<String>();
        if(this.isSetLocation()){
            evaluatorOptions.add(ExamOfferingSlottingEvaluator.USE_AO_LOCATION_OPTION_KEY);
        }
        return evaluatorOptions;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public ExamService getExamService() {
        return examService;
    }

    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public ExamOfferingService getExamOfferingService() {
        return examOfferingService;
    }

    public void setExamOfferingService(ExamOfferingService examOfferingService) {
        this.examOfferingService = examOfferingService;
    }

    public CourseOfferingSetService getSocService() {
        return socService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public ExamOfferingSlottingEvaluator getScheduleEvaluator() {
        return scheduleEvaluator;
    }

    public void setScheduleEvaluator(ExamOfferingSlottingEvaluator scheduleEvaluator) {
        this.scheduleEvaluator = scheduleEvaluator;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @Override
    public boolean isUseFinalExamMatrix() {
        return useFinalExamMatrix;
    }

    public void setUseFinalExamMatrix( boolean useFinalExamMatrix) {
        this.useFinalExamMatrix = useFinalExamMatrix;
    }

    @Override
    public boolean isSetLocation() {
        return setLocation;
    }

    public void setSetLocation(boolean setLocation) {
        this.setLocation = setLocation;
    }

    @Override
    public boolean isGenerateEODynamically() {
        return generateEODynamically;
    }

    public void setGenerateEODynamically(boolean generateEODynamically) {
        this.generateEODynamically = generateEODynamically;
    }

}
