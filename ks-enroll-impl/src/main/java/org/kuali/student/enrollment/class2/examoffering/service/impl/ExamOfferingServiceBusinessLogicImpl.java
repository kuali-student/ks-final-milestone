package org.kuali.student.enrollment.class2.examoffering.service.impl;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.class2.examoffering.service.ExamOfferingServiceBusinessLogic;
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
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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
public class ExamOfferingServiceBusinessLogicImpl implements ExamOfferingServiceBusinessLogic {

    private static final Logger LOGGER = Logger.getLogger(ExamOfferingServiceBusinessLogicImpl.class);

    private AcademicCalendarService acalService;
    private ExamService examService;
    private CourseOfferingService courseOfferingService;
    private ExamOfferingService examOfferingService;

    @Override
    public void generateFinalExamOffering(String courseOfferingId, String termId, List<String> optionKeys,
                                          ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        //Get the Exam Period Id for the term.
        String epId = null;
        List<ExamPeriodInfo> epInfos = this.getAcalService().getExamPeriodsForTerm(termId, context);
        for(ExamPeriodInfo epInfo : epInfos){
            epId = epInfo.getId();
            break;
        }

        //Retrieve the course offering to create the exam offerings for.
        CourseOfferingInfo courseOfferingInfo = this.getCourseOfferingService().getCourseOffering(courseOfferingId, context);
        if (FinalExam.STANDARD.toString().equals(courseOfferingInfo.getFinalExamType())) {

            //Check driver.
            String examDriver = getAttrValueForKey(courseOfferingInfo, CourseOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR);
            if (LuServiceConstants.LU_EXAM_DRIVER_AO_KEY.equals(examDriver)) {
                generateFinalExamOfferingsPerAO(courseOfferingId, epId, context);
            } else if (LuServiceConstants.LU_EXAM_DRIVER_CO_KEY.equals(examDriver)) {
                generateFinalExamOfferingsPerCO(courseOfferingId, epId, context);
            }
        } else {

            //If it is not a Standard exam, the course offering should not have any exams.
            removeFinalExamOfferingsPerCO(courseOfferingId, context);
        }
    }

    @Override
    public void generateFinalExamOfferingsPerCO(String courseOfferingId, String examPeriodId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        //Retrieve all format offerings linked to the course offering.
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, context);
        removeFinalExamOfferingsPerCo(foInfos, context);

        for (FormatOfferingInfo foInfo : foInfos) {

            //Create a new Exam Offering
            ExamOfferingInfo eo = createExamOffering(examPeriodId, context);

            //Create new Exam Offering Relationship
            List<String> aoIds = new ArrayList<String>();
            List<ActivityOfferingInfo> aoInfos = this.getCourseOfferingService().getActivityOfferingsByFormatOffering(foInfo.getId(), context);
            for(ActivityOfferingInfo aoInfo : aoInfos){
                aoIds.add(aoInfo.getId());
            }
            createExamOfferingRelation(foInfo.getId(), eo.getId(), aoIds, context);
        }
    }

    @Override
    public void removeFinalExamOfferingsPerCO(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {

        //Retrieve all format offerings linked to the course offering.
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId,
                context);
        removeFinalExamOfferingsPerCo(foInfos, context);
    }

    private void removeFinalExamOfferingsPerCo(List<FormatOfferingInfo> formatOfferings, ContextInfo context) throws
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            DoesNotExistException {

        Set<String> eosToDelete = new HashSet<String>();
        for (FormatOfferingInfo foInfo : formatOfferings) {

            //Retrieve all exam offerings linked to the format offering.
            List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                    foInfo.getId(), context);
            for (ExamOfferingRelationInfo eoRelation : eoRelations) {

                //Delete both relation and offering.
                this.getExamOfferingService().deleteExamOfferingRelation(eoRelation.getId(), context);
                eosToDelete.add(eoRelation.getExamOfferingId());
            }
        }

        //Delete the orphaned exam offerings.
        for (String eoId : eosToDelete) {
            this.getExamOfferingService().deleteExamOffering(eoId, context);
        }
    }

    @Override
    public void generateFinalExamOfferingsPerAO(String courseOfferingId, String examPeriodId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        //Retrieve all format offerings linked to the course offering.
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, context);
        removeFinalExamOfferingsPerCo(foInfos, context);

        for (FormatOfferingInfo foInfo : foInfos) {

            //Retrieve all activity offerings linked to the course offering.
            List<ActivityOfferingInfo> aoInfos = this.getCourseOfferingService().getActivityOfferingsByFormatOffering(
                    foInfo.getId(), context);
            for (ActivityOfferingInfo aoInfo : aoInfos) {

                //Create a new Exam Offering
                ExamOfferingInfo eo = createExamOffering(examPeriodId, context);

                //Create new Exam Offering Relationship
                List<String> aoIds = new ArrayList<String>();
                aoIds.add(aoInfo.getId());
                createExamOfferingRelation(foInfo.getId(), eo.getId(), aoIds, context);
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
