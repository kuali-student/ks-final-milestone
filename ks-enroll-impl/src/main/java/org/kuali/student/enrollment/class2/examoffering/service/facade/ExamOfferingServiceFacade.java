package org.kuali.student.enrollment.class2.examoffering.service.facade;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import java.util.List;
import java.util.Map;

/**
 * Used to support service calls related to exam offerings. *
 *
 * @author Kuali Student Team
 */
public interface ExamOfferingServiceFacade {

    public static final String RECREATE_OPTION_KEY = "kuali.option.key.exam.offering.recreate";

    /**
     * This method generates new Exam Offerings for the Course Offering for the given Course Offering Id based on
     * the exam drivers.
     *
     * If the Final Exam Status is not STANDARD, then all Exam Offerings will be cancelled
     *
     * If the Final Exam Driver changes, the existing offerings will be cancelled and new offerings created based on
     * new driver, or cancelled exam offerings for the current driver will be reinstated if they do exist.
     *
     * @param courseOfferingId
     * @param termId
     * @param examPeriodId
     * @param optionKeys
     * @param context
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    StatusInfo generateFinalExamOffering(String courseOfferingId, String termId, String examPeriodId, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * This method generates new Exam Offerings for the Course Offering for the given Course Offering Id based on
     * the exam drivers.
     *
     * If the Final Exam Status is not STANDARD, then all Exam Offerings will be cancelled
     *
     * If the Final Exam Driver changes, the existing offerings will be cancelled and new offerings created based on
     * new driver, or cancelled exam offerings for the current driver will be reinstated if they do exist.
     *
     * @param courseOfferingInfo
     * @param termId
     * @param examPeriodId
     * @param optionKeys
     * @param context
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    StatusInfo generateFinalExamOffering(CourseOfferingInfo courseOfferingInfo, String termId, String examPeriodId, List<String> optionKeys,
                                         ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException;
    /**
     * This method generates new Exam Offerings for the Course Offering for the given Course Offering Id based on
     * the exam drivers.
     *
     * If the Final Exam Status is not STANDARD, then all Exam Offerings will be cancelled
     *
     * If the Final Exam Driver changes, the existing offerings will be cancelled and new offerings created based on
     * new driver, or cancelled exam offerings for the current driver will be reinstated if they do exist.
     *
     * @param courseOfferingInfo
     * @param termId
     * @param examPeriodId
     * @param optionKeys
     * @param context
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    StatusInfo generateFinalExamOfferingOptimized(CourseOfferingInfo courseOfferingInfo, String termId, String examPeriodId, List<String> optionKeys,
                                          ContextInfo context, Map<String, List<ActivityOfferingInfo>> foIdToListOfAOs)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException;


    /**
     * This method is used to create exam offerings for new activity offerings that are added to the course offering after
     * the rollover process was completed.
     *
     * If the Final Exam Driver changes, the existing offerings will be cancelled and new offerings created based on
     * new driver, or cancelled exam offerings for the current driver will be reinstated if they do exist.
     *
     * @param courseOfferingInfo
     * @param activityOfferingInfo
     * @param termId
     * @param examPeriodID
     * @param finalExamLevelTypeKey
     * @param optionKeys
     * @param context
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    StatusInfo generateFinalExamOfferingForAO(CourseOfferingInfo courseOfferingInfo, ActivityOfferingInfo activityOfferingInfo,
                                        String termId, String examPeriodID, String finalExamLevelTypeKey, List<String> optionKeys,
                                        ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * This method is used to create exam offerings for new activity offerings that are added to the course offering after
     * the rollover process was completed.
     *
     * If the Final Exam Driver changes, the existing offerings will be cancelled and new offerings created based on
     * new driver, or cancelled exam offerings for the current driver will be reinstated if they do exist.
     *
     * @param activityOfferingInfo
     * @param termId
     * @param examPeriodID
     * @param optionKeys
     * @param context
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    StatusInfo generateFinalExamOfferingForAO(ActivityOfferingInfo activityOfferingInfo, String termId, String examPeriodID,
                                              List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;
    /**
     * Generates a single Exam Offering per Format Offering.
     *
     * If the Final Exam Driver changes, the existing offerings will be cancelled and new offerings created based on
     * new driver, or cancelled exam offerings for the current driver will be reinstated if they do exist.
     *
     * @param courseOfferingId
     * @param termId
     * @param examPeriodId
     * @param context
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    void generateFinalExamOfferingsPerFO(String courseOfferingId, String termId, String examPeriodId, List<String> optionKeys,
                                         ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException;
    /**
     * An "optimized" version of generateFinalExamOfferingsPerFO where a map of foId to a list of AOids
     * is passed in (for this CO) to avoid additional calls to the DB.
     *
     * @param courseOfferingId
     * @param termId
     * @param examPeriodId
     * @param context
     * @param foIdToListOfAOs For the courseOfferingId, this is a map from FO Ids (of the CO) to the AO
     *                        Infos belonging to the FO.  Saves a service call by passing this info in.
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    void generateFinalExamOfferingsPerFOOptimized(String courseOfferingId, String termId, String examPeriodId, List<String> optionKeys,
                                         ContextInfo context, Map<String, List<ActivityOfferingInfo>> foIdToListOfAOs)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException;

    /**
     * Generates an Exam Offering for each Activity Offering.
     *
     * If the Final Exam Driver changes, the existing offerings will be cancelled and new offerings created based on
     * new driver, or cancelled exam offerings for the current driver will be reinstated if they do exist.
     *
     * @param courseOfferingId
     * @param termId
     * @param examPeriodId
     * @param context
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    void generateFinalExamOfferingsPerAO(String courseOfferingId, String termId, String examPeriodId,
                                         List<String> optionKeys, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException;

    /**
     * An "optimized" version of generateFinalExamOfferingsPerAO where a map of foId to a list of AOids
     * is passed in (for this CO) to avoid additional calls to the DB.
     *
     * @param courseOfferingId
     * @param termId
     * @param examPeriodId
     * @param context
     * @param foIdToListOfAOs For the courseOfferingId, this is a map from FO Ids (of the CO) to the AO
     *                        Infos belonging to the FO.  Saves a service call by passing this info in.
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    void generateFinalExamOfferingsPerAOOptimized(String courseOfferingId, String termId, String examPeriodId,
                                                  List<String> optionKeys,
                                                  ContextInfo context,
                                                  Map<String, List<ActivityOfferingInfo>> foIdToListOfAOs)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException;
    /**
     * This method removes all Exam Offering from the Course Offering for the given Coure Offering Id. This
     * include all the Exam Offering for the Format Offerings and Activity Offerings linked to the Course
     * Offering.
     *
     * @param courseOfferingId
     * @param context
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    void removeFinalExamOfferingsFromCO(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException;

    /**
     * This method change all the states of the exam offerings associated with the given courseOfferingId to
     * the given stateKey.
     *
     * @param courseOfferingId
     * @param stateKey
     * @param context
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    void changeFinalExamOfferingsState(String courseOfferingId, String stateKey, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException;

    /**
     * This method retrieves the exam period id for the term that the given course offering is attached to.
     *
     * @param termID
     * @param context
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     *
     */
    public String getExamPeriodId(String termID, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;


    /**
     * @param courseOfferingId
     * @param termId
     * @param examPeriodId
     * @param optionKeys
     * @param context
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @throws ReadOnlyException
     * @throws DataValidationErrorException
     */
    void generateFinalExamOfferingsPerCO(String courseOfferingId, String termId, String examPeriodId, List<String> optionKeys,
                                         ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException;

    /**
     * An "optimized" version of generateFinalExamOfferingsPerCO where a map of foId to a list of AOids
     * is passed in (for this CO) to avoid additional calls to the DB.
     *
     * @param courseOfferingId
     * @param termId
     * @param examPeriodId
     * @param optionKeys
     * @param context
     * @param foIdToListOfAOs For the courseOfferingId, this is a map from FO Ids (of the CO) to the AO
     *                        Infos belonging to the FO.  Saves a service call by passing this info in.
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @throws ReadOnlyException
     * @throws DataValidationErrorException
     */
    void generateFinalExamOfferingsPerCOOptimized(String courseOfferingId, String termId, String examPeriodId, List<String> optionKeys,
                                         ContextInfo context,
                                         Map<String, List<ActivityOfferingInfo>> foIdToListOfAOs)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException;

    /**
     * This method retrieves the boolea value to indicate if the execution process should or not set the location on the RDL.
     *
     */
    public boolean isSetLocation();
}
