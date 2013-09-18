package org.kuali.student.enrollment.class2.examoffering.service.impl;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.class2.examoffering.service.ExamOfferingServiceBusinessLogic;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FinalExam;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
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
import org.kuali.student.r2.common.util.constants.LuServiceConstants;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/09/17
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExamOfferingServiceBusinessLogicImpl implements ExamOfferingServiceBusinessLogic {

    private static final Logger LOGGER = Logger.getLogger(ExamOfferingServiceBusinessLogicImpl.class);

    private CourseOfferingService courseOfferingService;
    private ExamOfferingService examOfferingService;

    @Override
    public void generateFinalExamOffering(String courseOfferingId, String targetTermId, List<String> optionKeys,
                                          ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        CourseOfferingInfo courseOfferingInfo = this.getCourseOfferingService().getCourseOffering(courseOfferingId, context);
        if(FinalExam.STANDARD.equals(courseOfferingInfo.getFinalExamType())){
            //Check driver.
            String examDriver = getAttrValueForKey(courseOfferingInfo, CourseOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR);
            if(LuServiceConstants.LU_EXAM_DRIVER_AO_KEY.equals(examDriver)){
                generateFinalExamOfferingsPerAO(courseOfferingId, context);
            } else if (LuServiceConstants.LU_EXAM_DRIVER_CO_KEY.equals(examDriver)){
                generateFinalExamOfferingsPerCO(courseOfferingId, context);
            }
        } else {
            removeFinalExamOfferingsPerCO(courseOfferingId, context);
            removeFinalExamOfferingsPerAO(courseOfferingId, context);
        }
    }

    @Override
    public void generateFinalExamOfferingsPerCO(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, context);
    }

    @Override
    public void removeFinalExamOfferingsPerCO(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, context);
    }

    @Override
    public void generateFinalExamOfferingsPerAO(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        List<ActivityOfferingInfo> aoInfos = this.getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, context);

    }

    @Override
    public void removeFinalExamOfferingsPerAO(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        List<ActivityOfferingInfo> aoInfos = this.getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, context);

    }

    private String getAttrValueForKey(CourseOfferingInfo courseOfferingInfo, String key) {
        AttributeInfo attributeInfo = null;
        for(AttributeInfo attr : courseOfferingInfo.getAttributes()){
            if (key.equals(attr.getKey())){
                return attr.getValue();
            }
        }
        return null;
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
