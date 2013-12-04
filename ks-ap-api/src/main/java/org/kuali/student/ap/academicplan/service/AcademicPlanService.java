package org.kuali.student.ap.academicplan.service;

import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemSetInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;


import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Academic Plan service provides a means for students to specify plans for taking courses and other requirements at various time periods to meet
 * certain program goals.
 * <p/>
 * Things available through learning plan :
 * <p/>
 * <ol>
 * <li>Selecting courses, course sets, requirements, certifications etc for a specific time period</li>
 * <li>Selecting courses and adding them to a wish list</li>
 * <li>Selecting courses for doing a what if audit</li>
 * <li>Creating multiple plans (pathways) to meet program goals</li>
 * <li>Template plans</li>
 * <li>Planning for </li>
 * </ol>
 *
 * @version 1.0 (Dev)
 * @Author Kamal
 * Date: 1/6/12
 */
@WebService(name = "AcademicPlanService", serviceName = "AcademicPlanService", portName = "AcademicPlanService", targetNamespace = AcademicPlanServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface AcademicPlanService {

    public LearningPlanInfo getLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    public PlanItemInfo getPlanItem(@WebParam(name = "planItemId") String planItemId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    public List<PlanItemInfo> getPlanItemsInPlanByType(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "planItemTypeKey") String planItemTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    public List<PlanItemInfo> getPlanItemsInPlan(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    public List<PlanItemInfo> getPlanItemsInPlanByAtp(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "planItemTypeKey") String planItemTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Gets plan items for a
     * @param learningPlanId The id of the plan.
     * @param refObjectId The id of the referenced object.
     * @param refObjectType The type of the referenced object.
     * @param context
     * @return A list of plan items related to a particular course (refObjectId).
     */
    public List<PlanItemInfo> getPlanItemsInPlanByRefObjectIdByRefObjectType(@WebParam(name = "learningPlanId") String learningPlanId,
                                                                             @WebParam(name = "refObjectId") String refObjectId,
                                                                             @WebParam(name = "refObjectType") String refObjectType,
                                                                             @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    public PlanItemSetInfo getPlanItemSet(@WebParam(name = "planItemSetId") String planItemSetId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    public List<PlanItemInfo> getPlanItemsInSet(@WebParam(name = "planItemSetId") String planItemSetId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    public List<LearningPlanInfo> getLearningPlansForStudentByType(@WebParam(name = "studentId") String studentId, @WebParam(name = "planTypeKey") String planTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    public LearningPlanInfo createLearningPlan(@WebParam(name = "learningPlan") LearningPlanInfo learningPlan, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    public PlanItemInfo createPlanItem(@WebParam(name = "planItem") PlanItemInfo planItem, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    public PlanItemSetInfo createPlanItemSet(@WebParam(name = "planItemSet") PlanItemSetInfo planItemSet, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    public LearningPlanInfo updateLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "learningPlan") LearningPlanInfo learningPlan, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;


    public PlanItemInfo updatePlanItem(@WebParam(name = "planItemId") String planItemId, @WebParam(name = "planItem") PlanItemInfo planItem,
                                       @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, AlreadyExistsException;


    public PlanItemSetInfo updatePlanItemSet(@WebParam(name = "planItemSetId") String planItemSetId, @WebParam(name = "planItemSet") PlanItemSetInfo planItemSet, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    public StatusInfo deleteLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    public StatusInfo deletePlanItem(@WebParam(name = "planItemId") String planItemId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    public StatusInfo deletePlanItemSet(@WebParam(name = "planItemSetId") String planItemSetId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    public List<ValidationResultInfo> validateLearningPlan(@WebParam(name = "validationType") String validationType,
                                                           @WebParam(name = "learningPlanInfo") LearningPlanInfo learningPlanInfo,
                                                           @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    public List<ValidationResultInfo> validatePlanItem(@WebParam(name = "validationType") String validationType,
                                                       @WebParam(name = "planItemInfo") PlanItemInfo planItemInfo,
                                                       @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException;


    public List<ValidationResultInfo> validatePlanItemSet(@WebParam(name = "validationType") String validationType,
                                                          @WebParam(name = "planItemSetInfo") PlanItemSetInfo planItemSetInfo,
                                                          @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

}
