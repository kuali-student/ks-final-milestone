package org.kuali.student.enrollment.class2.examoffering.service.facade;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/09/17
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ExamOfferingServiceFacade {

    public static final String RECREATE_OPTION_KEY = "kuali.option.key.exam.offering.recreate";

    /**
     * This method generates new Exam Offerings for the Course Offering for the given Course Offering Id based on
     * the exam drivers.
     *
     * If the Final Exam Status is not STANDARD, then all Exam Offerings linked to the Course Offering will be deleted.
     *
     * @param courseOfferingId
     * @param optionKeys
     * @param context
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    void generateFinalExamOffering(String courseOfferingId, List<String> optionKeys, ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * This method generates new Exam Offerings for the Course Offering for the given Course Offering Id based on
     * the exam drivers.
     *
     * If the Final Exam Status is not STANDARD, then all Exam Offerings linked to the Course Offering will be deleted.
     *
     * @param courseOfferingInfo
     * @param optionKeys
     * @param context
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    void generateFinalExamOffering(CourseOfferingInfo courseOfferingInfo, List<String> optionKeys, ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Generates a single Exam Offering per Format Offering.
     *
     * @param courseOfferingId
     * @param examPeriodId
     * @param context
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    void generateFinalExamOfferingsPerFO(String courseOfferingId, String examPeriodId, List<String> optionKeys,
                                         ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException;

    /**
     * Generates an Exam Offering for each Activity Offering.
     *
     * @param courseOfferingId
     * @param examPeriodId
     * @param context
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    void generateFinalExamOfferingsPerAO(String courseOfferingId, String examPeriodId, List<String> optionKeys, ContextInfo context)
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

}
