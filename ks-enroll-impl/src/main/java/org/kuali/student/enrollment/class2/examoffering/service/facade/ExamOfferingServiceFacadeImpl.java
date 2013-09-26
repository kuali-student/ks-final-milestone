package org.kuali.student.enrollment.class2.examoffering.service.facade;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FinalExam;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.exam.service.ExamService;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.infc.ExamOffering;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private enum Driver {PER_CO, PER_AO, NONE};

    @Override
    public void generateFinalExamOffering(String courseOfferingId, List<String> optionKeys,
                                          ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        //Retrieve the course offering to create the exam offerings for.
        CourseOfferingInfo courseOfferingInfo = this.getCourseOfferingService().getCourseOffering(courseOfferingId, context);
        this.generateFinalExamOffering(courseOfferingInfo, optionKeys, context);

    }

    @Override
    public void generateFinalExamOffering(CourseOfferingInfo courseOfferingInfo, List<String> optionKeys,
                                          ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        Driver driver = calculateEODriver(courseOfferingInfo);
        if (driver.equals(Driver.PER_AO)){
            String epId = getExamPeriodId(courseOfferingInfo, context);
            generateFinalExamOfferingsPerAO(courseOfferingInfo.getId(), epId, optionKeys, context);
        } else if (driver.equals(Driver.PER_CO)) {
            String epId = getExamPeriodId(courseOfferingInfo, context);
            generateFinalExamOfferingsPerFO(courseOfferingInfo.getId(), epId, optionKeys, context);
        } else if (driver.equals(Driver.NONE)){
            removeFinalExamOfferingsFromCO(courseOfferingInfo.getId(), context);
        }

    }

    @Override
    public void generateFinalExamOfferingForAO(ActivityOfferingInfo activityOfferingInfo, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        FormatOfferingInfo fo = this.getCourseOfferingService().getFormatOffering(activityOfferingInfo.getFormatOfferingId(), context);
        CourseOfferingInfo co = this.getCourseOfferingService().getCourseOffering(fo.getCourseOfferingId(), context);
        this.generateFinalExamOfferingForAO(co, activityOfferingInfo, optionKeys, context);
    }

    @Override
    public void generateFinalExamOfferingForAO(CourseOfferingInfo courseOfferingInfo, ActivityOfferingInfo activityOfferingInfo,
                                               List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        Driver driver = calculateEODriver(courseOfferingInfo);
        if (driver.equals(Driver.PER_AO)){
            String epId = getExamPeriodId(courseOfferingInfo, context);
            generateFinalExamOfferingPerAO(activityOfferingInfo.getFormatOfferingId(), activityOfferingInfo.getId(), epId, context);
        } else if (driver.equals(Driver.PER_CO)) {
            List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                    activityOfferingInfo.getFormatOfferingId(), context);

            //Create a new exam offering if this is the first AO linked to the CO.
            if(eoRelations.size()==0){
                String epId = getExamPeriodId(courseOfferingInfo, context);
                generateFinalExamOfferingPerFO(activityOfferingInfo.getFormatOfferingId(), epId, context);
            } else {
                for(ExamOfferingRelationInfo eoRelation : eoRelations){
                    eoRelation.getActivityOfferingIds().add(activityOfferingInfo.getId());
                    this.getExamOfferingService().updateExamOfferingRelation(eoRelation.getId(), eoRelation, context);
                    break; //There should only be one.
                }
            }
        }

    }

    private String getExamPeriodId(CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        //Get the Exam Period Id for the term.
        String epId = null;
        List<ExamPeriodInfo> epInfos = this.getAcalService().getExamPeriodsForTerm(courseOfferingInfo.getTermId(), context);
        for(ExamPeriodInfo epInfo : epInfos){
            epId = epInfo.getId();
            break;
        }

        // Return if the
        if(epId == null){
            throw new DoesNotExistException("Exam Period does not exist for term.");
        }
        return epId;
    }

    private Driver calculateEODriver(CourseOfferingInfo co){
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
    public void generateFinalExamOfferingsPerFO(String courseOfferingId, String examPeriodId, List<String> optionKeys,
                                                ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        //Retrieve all format offerings linked to the course offering.
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, context);
        if(optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)){
            removeFinalExamOfferingsFromCO(foInfos, context);
        }

        for (FormatOfferingInfo foInfo : foInfos) {

            //Check for existing relationships.
            if(!optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)){
                List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                    foInfo.getId(), context);
                if(eoRelations.size()>1){
                    removeFinalExamOfferings(eoRelations, context);
                } else if(eoRelations.size()==1){
                    return;
                }
            }
            generateFinalExamOfferingPerFO(foInfo.getId(), examPeriodId, context);
        }
    }

    private void generateFinalExamOfferingPerFO(String foId, String examPeriodId, ContextInfo context)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException,
            DoesNotExistException, DataValidationErrorException, ReadOnlyException {

        //Create a new Exam Offering
        ExamOfferingInfo eo = createExamOffering(examPeriodId, context);

        //Create new Exam Offering Relationship
        List<String> aoIds = new ArrayList<String>();
        List<ActivityOfferingInfo> aoInfos = this.getCourseOfferingService().getActivityOfferingsByFormatOffering(
                foId, context);
        for(ActivityOfferingInfo aoInfo : aoInfos){
            aoIds.add(aoInfo.getId());
        }
        createExamOfferingRelation(foId, eo.getId(), aoIds, context);
    }

    @Override
    public void generateFinalExamOfferingsPerAO(String courseOfferingId, String examPeriodId, List<String> optionKeys,
                                                ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        //Retrieve all format offerings linked to the course offering.
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, context);
        if(optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)){
            removeFinalExamOfferingsFromCO(foInfos, context);
        }

        for (FormatOfferingInfo foInfo : foInfos) {

            //Retrieve all activity offerings linked to the course offering.
            List<ActivityOfferingInfo> aoInfos = this.getCourseOfferingService().getActivityOfferingsByFormatOffering(
                    foInfo.getId(), context);

            //Check for existing relationships.
            if(!optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)){
                List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                        foInfo.getId(), context);
                if(aoInfos.size()!=eoRelations.size()){
                    removeFinalExamOfferings(eoRelations, context);
                } else {
                    return;
                }
            }

            //Create new Exam Offering per Activity Offering.
            for (ActivityOfferingInfo aoInfo : aoInfos) {
                generateFinalExamOfferingPerAO(foInfo.getId(), aoInfo.getId(), examPeriodId, context);
            }
        }

    }

    private void generateFinalExamOfferingPerAO(String foId, String aoId, String examPeriodId, ContextInfo context)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException,
            DoesNotExistException, DataValidationErrorException, ReadOnlyException {

        //Create a new Exam Offering
        ExamOfferingInfo eo = createExamOffering(examPeriodId, context);

        //Create new Exam Offering Relationship
        List<String> aoIds = new ArrayList<String>();
        aoIds.add(aoId);
        createExamOfferingRelation(foId, eo.getId(), aoIds, context);
    }

    @Override
    public void removeFinalExamOfferingsFromCO(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {

        //Retrieve all format offerings linked to the course offering.
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId,
                context);

        removeFinalExamOfferingsFromCO(foInfos, context);
    }

    private void removeFinalExamOfferingsFromCO(List<FormatOfferingInfo> foInfos, ContextInfo context) throws
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            DoesNotExistException {
        for (FormatOfferingInfo foInfo : foInfos) {
            //Retrieve all exam offerings linked to the format offering.
            List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                    foInfo.getId(), context);
            removeFinalExamOfferings(eoRelations, context);
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
        eo.setStateKey(ExamOfferingServiceConstants.EXAM_OFFERING_SCHEDULING_UNSCHEDULED_STATE_KEY);
        eo.setExamId(this.getCanonicalExam(context));
        eo.setExamPeriodId(examPeriodId);

        return this.getExamOfferingService().createExamOffering(eo.getExamPeriodId(), eo.getExamId(), eo.getTypeKey(), eo,
                context);
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
        eoRelation.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_FO_TO_EO_TYPE_KEY);

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
        AttributeInfo attributeInfo = null;
        for (AttributeInfo attr : courseOfferingInfo.getAttributes()) {
            if (key.equals(attr.getKey())) {
                return attr.getValue();
            }
        }
        return null;
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
