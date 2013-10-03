package org.kuali.student.enrollment.class2.examoffering.service.facade;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.rice.krms.api.KrmsApiServiceLocator;
import org.kuali.rice.krms.api.engine.Engine;
import org.kuali.rice.krms.api.engine.ExecutionFlag;
import org.kuali.rice.krms.api.engine.ExecutionOptions;
import org.kuali.rice.krms.api.engine.SelectionCriteria;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.hsqldb.lib.StringUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FinalExam;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/09/17
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExamOfferingServiceFacadeImpl implements ExamOfferingServiceFacade {

    private static final Logger LOGGER = Logger.getLogger(ExamOfferingServiceFacadeImpl.class);

    private AcademicCalendarService acalService;
    private ExamService examService;
    private CourseOfferingService courseOfferingService;
    private ExamOfferingService examOfferingService;

    private enum Driver {PER_CO, PER_FO, PER_AO, NONE}

    @Override
    public void generateFinalExamOffering(String courseOfferingId, String examPeriodId, List<String> optionKeys,
                                          ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        //Retrieve the course offering to create the exam offerings for.
        CourseOfferingInfo courseOfferingInfo = this.getCourseOfferingService().getCourseOffering(courseOfferingId, context);
        this.generateFinalExamOffering(courseOfferingInfo, examPeriodId, optionKeys, context);

    }


    @Override
    public void generateFinalExamOffering(CourseOfferingInfo courseOfferingInfo, String examPeriodId, List<String> optionKeys,
                                          ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (StringUtil.isEmpty(examPeriodId)) {
            throw new MissingParameterException("Exam Period id is not provided.");
        }

        Driver driver = calculateEODriver(courseOfferingInfo);
        if (driver.equals(Driver.PER_AO)) {
            generateFinalExamOfferingsPerAO(courseOfferingInfo.getId(), examPeriodId, optionKeys, context);
        } else if (driver.equals(Driver.PER_FO)) {
            generateFinalExamOfferingsPerFO(courseOfferingInfo.getId(), examPeriodId, optionKeys, context);
        } else if (driver.equals(Driver.PER_CO)) {
            generateFinalExamOfferingsPerCO(courseOfferingInfo.getId(), examPeriodId, optionKeys, context);
        } else if (driver.equals(Driver.NONE)) {
            if (optionKeys.contains(ExamOfferingServiceFacade.CANCEL_EXISTING_OPTION_KEY)) {
                changeFinalExamOfferingsState(courseOfferingInfo.getId(), ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY, context);
            } else {
                removeFinalExamOfferingsFromCO(courseOfferingInfo.getId(), context);
            }
        }
    }

    @Override
    public void generateFinalExamOfferingForAO(ActivityOfferingInfo activityOfferingInfo, String examPeriodID, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        FormatOfferingInfo fo = this.getCourseOfferingService().getFormatOffering(activityOfferingInfo.getFormatOfferingId(), context);
        CourseOfferingInfo co = this.getCourseOfferingService().getCourseOffering(fo.getCourseOfferingId(), context);
        this.generateFinalExamOfferingForAO(co, activityOfferingInfo, examPeriodID, optionKeys, context);
    }

    @Override
    public void generateFinalExamOfferingForAO(CourseOfferingInfo courseOfferingInfo, ActivityOfferingInfo activityOfferingInfo,
                                               String examPeriodID, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        Driver driver = calculateEODriver(courseOfferingInfo);
        if (driver.equals(Driver.PER_AO)) {
            createFinalExamOfferingPerAO(activityOfferingInfo.getFormatOfferingId(), activityOfferingInfo.getId(), examPeriodID, context);
        } else if (driver.equals(Driver.PER_CO)) {
            List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                    activityOfferingInfo.getFormatOfferingId(), context);

            //Create a new exam offering if this is the first AO linked to the CO.
            if (eoRelations.size() == 0) {
                //Create new Exam Offering and Relationship
                createFinalExamOfferingPerAO(activityOfferingInfo.getFormatOfferingId(), activityOfferingInfo.getId(), examPeriodID, context);

            } else {
                for (ExamOfferingRelationInfo eoRelation : eoRelations) {
                    eoRelation.getActivityOfferingIds().add(activityOfferingInfo.getId());
                    this.getExamOfferingService().updateExamOfferingRelation(eoRelation.getId(), eoRelation, context);
                    break; //There should only be one.
                }
            }
        }

    }

    @Override
    public String getExamPeriodId(String termID, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        //Get the Exam Period Id for the term.
        String epId = null;
        List<ExamPeriodInfo> epInfos = this.getAcalService().getExamPeriodsForTerm(termID, context);
        for (ExamPeriodInfo epInfo : epInfos) {
            epId = epInfo.getId();
            break;
        }

        // Check that an exam period was created for the target term
        if (epId == null) {
            throw new DoesNotExistException("Generate final exam offerings skipped because an exam period does not exist for the target term.");
        }
        return epId;
    }

    private Driver calculateEODriver(CourseOfferingInfo co) {
        if (FinalExam.STANDARD.toString().equals(co.getFinalExamType())) {

            //Check driver.
            String examDriver = getAttrValueForKey(co, CourseOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR);
            if (LuServiceConstants.LU_EXAM_DRIVER_AO_KEY.equals(examDriver)) {
                return Driver.PER_AO;
            } else if (LuServiceConstants.LU_EXAM_DRIVER_CO_KEY.equals(examDriver)) {
                return Driver.PER_CO;
            }
            return Driver.NONE;
        } else {

            //If it is not a Standard exam, the course offering should not have any exams.
            return Driver.NONE;
        }
    }

    @Override
    public void generateFinalExamOfferingsPerCO(String courseOfferingId, String examPeriodId, List<String> optionKeys,
                                                ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        Map<String, List<ExamOfferingRelationInfo>> foToEoRelations = getExamOfferingRelationships(courseOfferingId, context);
        if (optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)) {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }

        Set<String> eoIds = getUniqueExamOfferingIds(foToEoRelations);
        if (eoIds.size() > 1) {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        } else if (eoIds.size() == 1) {
            String eoId = eoIds.iterator().next();
            for (Map.Entry<String, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {
                if (foEntry.getValue().size() == 0) {
                    createExamOfferingRelationPerFO(foEntry.getKey(), eoId, context);
                }
            }
            return;
        }

        //Create a new Exam Offering
        ExamOfferingInfo eo = createExamOffering(examPeriodId, context);
        //executeRuleForCOScheduling(courseOfferingId, context);

        for (Map.Entry<String, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {
            createExamOfferingRelationPerFO(foEntry.getKey(), eo.getId(), context);
        }

    }


    @Override
    public void generateFinalExamOfferingsPerFO(String courseOfferingId, String examPeriodId, List<String> optionKeys,
                                                ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        //Retrieve all format offerings linked to the course offering.
        Map<String, List<ExamOfferingRelationInfo>> foToEoRelations = getExamOfferingRelationships(courseOfferingId, context);
        if (optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)) {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }

        for (Map.Entry<String, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {

            //Check for existing relationships.
            List<ExamOfferingRelationInfo> eoRelations = foEntry.getValue();
            if (eoRelations.size() > 1) {
                removeFinalExamOfferings(eoRelations, context);
            } else if (eoRelations.size() == 1) {
                return;
            }

            //Create a new Exam Offering
            ExamOfferingInfo eo = createExamOffering(examPeriodId, context);

            //Create new Exam Offering Relationship
            createExamOfferingRelationPerFO(foEntry.getKey(), eo.getId(), context);
        }
    }

    @Override
    public void generateFinalExamOfferingsPerAO(String courseOfferingId, String examPeriodId, List<String> optionKeys,
                                                ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        //Retrieve all format offerings linked to the course offering.
        Map<String, List<ExamOfferingRelationInfo>> foToEoRelations = getExamOfferingRelationships(courseOfferingId, context);
        if (optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)) {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }

        int nrOfEORelations = 0;
        for (String foId : foToEoRelations.keySet()) {
            nrOfEORelations += foToEoRelations.get(foId).size();
        }

        if (nrOfEORelations != getUniqueExamOfferingIds(foToEoRelations).size()) {
            //If relations does not match exam offerings, delete all and start over.
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        } else {
            foloop: for (Map.Entry<String, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {
                for (ExamOfferingRelationInfo eoRelation : foEntry.getValue()) {
                    if (eoRelation.getActivityOfferingIds().size() > 1) {
                        //If any relationship is linked to more than one AO, delete all and start over.
                        removeFinalExamOfferingsFromCO(foToEoRelations, context);
                        break foloop;
                    }
                }
            }
        }

        //Create new exam offerings per AO.
        for (Map.Entry<String, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {
            List<ActivityOfferingInfo> aoInfos = this.getCourseOfferingService().getActivityOfferingsByFormatOffering(
                    foEntry.getKey(), context);
            aoloop:
            for (ActivityOfferingInfo aoInfo : aoInfos) {
                for (ExamOfferingRelationInfo eoRelation : foEntry.getValue()) {
                    if (eoRelation.getActivityOfferingIds().contains(aoInfo.getId())) {
                        continue aoloop;
                    }
                }
                createFinalExamOfferingPerAO(foEntry.getKey(), aoInfo.getId(), examPeriodId, context);
            }
        }

    }

    private Map<String, List<ExamOfferingRelationInfo>> getExamOfferingRelationships(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //Retrieve all format offerings linked to the course offering.
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, context);
        Map<String, List<ExamOfferingRelationInfo>> foToEoRelations = new HashMap<String, List<ExamOfferingRelationInfo>>();
        for (FormatOfferingInfo foInfo : foInfos) {
            List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                    foInfo.getId(), context);
            foToEoRelations.put(foInfo.getId(), eoRelations);
        }
        return foToEoRelations;
    }

    private Set<String> getUniqueExamOfferingIds(Map<String, List<ExamOfferingRelationInfo>> foToEoRelations) {
        Set<String> eoIds = new HashSet<String>();
        for (Map.Entry<String, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {
            for (ExamOfferingRelationInfo eoRelation : foEntry.getValue()) {
                eoIds.add(eoRelation.getExamOfferingId());
            }
        }
        return eoIds;
    }

    private void createExamOfferingRelationPerFO(String foId, String eoId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DataValidationErrorException, ReadOnlyException {

        //Create new Exam Offering Relationship
        List<String> aoIds = new ArrayList<String>();
        List<ActivityOfferingInfo> aoInfos = this.getCourseOfferingService().getActivityOfferingsByFormatOffering(
                foId, context);
        for (ActivityOfferingInfo aoInfo : aoInfos) {
            aoIds.add(aoInfo.getId());
        }
        createExamOfferingRelation(foId, eoId, aoIds, context);
    }

    private void createFinalExamOfferingPerAO(String foId, String aoId, String examPeriodId, ContextInfo context)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException,
            DoesNotExistException, DataValidationErrorException, ReadOnlyException {

        //Create a new Exam Offering
        ExamOfferingInfo eo = createExamOffering(examPeriodId, context);
        //executeRuleForAOScheduling(aoId, context);

        //Create new Exam Offering Relationship
        List<String> aoIds = new ArrayList<String>();
        aoIds.add(aoId);
        createExamOfferingRelation(foId, eo.getId(), aoIds, context);
    }

    @Override
    public void removeFinalExamOfferingsFromCO(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {

        Map<String, List<ExamOfferingRelationInfo>> foToEoRelations = this.getExamOfferingRelationships(courseOfferingId, context);
        removeFinalExamOfferingsFromCO(foToEoRelations, context);

    }

    private void removeFinalExamOfferingsFromCO(Map<String, List<ExamOfferingRelationInfo>> foToEoRelations, ContextInfo context) throws
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            DoesNotExistException {

        Set<String> eoIds = new HashSet<String>();
        for (Map.Entry<String, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {
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

    /**
     * This method currently assumes that Exam Offerings are only linked to a single Exam Offering Relationship as all
     * other scenarios are currently out of scope.
     *
     * @param eoRelations
     * @param context
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    private void removeFinalExamOfferings(List<ExamOfferingRelationInfo> eoRelations, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        for (ExamOfferingRelationInfo eoRelation : eoRelations) {
            this.getExamOfferingService().deleteExamOfferingRelation(eoRelation.getId(), context);
            this.getExamOfferingService().deleteExamOffering(eoRelation.getExamOfferingId(), context);
        }
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
    private ExamOfferingInfo createExamOffering(String examPeriodId, ContextInfo context) throws MissingParameterException,
            InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException,
            DataValidationErrorException, ReadOnlyException {

        ExamOfferingInfo eo = new ExamOfferingInfo();
        eo.setTypeKey(ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY);
        eo.setStateKey(ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY);
        eo.setExamId(this.getCanonicalExam(context));
        eo.setExamPeriodId(examPeriodId);

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
    private ExamOfferingRelationInfo createExamOfferingRelation(String formatOfferingId, String examOfferingId,
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

    private String getCanonicalExam(ContextInfo context) throws MissingParameterException, InvalidParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> examIds = this.getExamService().getExamIdsByType(ExamServiceConstants.EXAM_FINAL_TYPE_KEY, context);
        for (String examId : examIds) {
            return examId; //Return the first one as there should only be one canonical final exam.
        }
        return null;
    }

    private String getAttrValueForKey(CourseOfferingInfo courseOfferingInfo, String key) {
        for (AttributeInfo attr : courseOfferingInfo.getAttributes()) {
            if (key.equals(attr.getKey())) {
                return attr.getValue();
            }
        }
        return null;
    }

    private void executeRuleForAOScheduling(String aoId, ContextInfo context) {
        Engine engine = KrmsApiServiceLocator.getEngine();

        Map<String, String> contextQualifiers = new HashMap<String, String>();
        contextQualifiers.put("namespaceCode", PermissionServiceConstants.KS_SYS_NAMESPACE);
        contextQualifiers.put("name", "Final Exam Matrix Rules");
        Map<String, String> agendaQualifiers = new HashMap<String, String>();
        agendaQualifiers.put("id", "KS-KRMS-AGENDA-11923");
        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(new DateTime(), contextQualifiers, agendaQualifiers);

        Map<String, Object> executionFacts = new HashMap<String, Object>();
        executionFacts.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO, context);
        executionFacts.put(KSKRMSServiceConstants.TERM_PREREQUISITE_AO_ID, aoId);

        engine.execute(selectionCriteria, executionFacts, getDefaultExecutionOptions());
    }

    private void executeRuleForCOScheduling(String coId, ContextInfo context) {
        Engine engine = KrmsApiServiceLocator.getEngine();

        Map<String, String> contextQualifiers = new HashMap<String, String>();
        contextQualifiers.put("namespaceCode", PermissionServiceConstants.KS_SYS_NAMESPACE);
        contextQualifiers.put("name", "Final Exam Matrix Rules");
        Map<String, String> agendaQualifiers = new HashMap<String, String>();
        agendaQualifiers.put("id", "KS-KRMS-AGENDA-11924");
        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(new DateTime(), contextQualifiers, agendaQualifiers);

        Map<String, Object> executionFacts = new HashMap<String, Object>();
        executionFacts.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO, context);
        executionFacts.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CO_ID, coId);

        engine.execute(selectionCriteria, executionFacts, getDefaultExecutionOptions());
    }

    private ExecutionOptions getDefaultExecutionOptions() {
        ExecutionOptions executionOptions = new ExecutionOptions();
        executionOptions.setFlag(ExecutionFlag.LOG_EXECUTION, true);
        executionOptions.setFlag(ExecutionFlag.EVALUATE_ALL_PROPOSITIONS, true);
        return executionOptions;
    }

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
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
}
