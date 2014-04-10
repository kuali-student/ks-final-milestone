/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.ap.academicplan.service;

import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;


import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;


/**
 * Provides a means for students to plan an academic career.
 * <p/>
 * It designed to allow the student to:
 * <p/>
 * <ul>
 * <li>Search for courses [...or, requirements, certifications, etc.] by subject course code, keyword, and to selective
 *      narrow a search result by a plethora of categories (e.g. subj code, course # level, gen-end rqmts,....)</li>
 * <li>Select courses and add to their plan, or to a "bookmark" list</li>
 * <li>Adopt a learning template plan</li>
 * <li>View of courses already taken and credits/grade recieved for each, directly within their plan</li>
 * <li>Share a plan (...or part thereof) with an Advisor for review/comment/recommendation</li>
 * <li>Run degree audit on a plan</li>
 * <li>Select courses for doing a what if audit</li>
 * <li>Creating multiple plans (pathways) to meet program goals</li>
 * <li>Build out a detailed class schedule that may be used to feed registration (i.e. ...to make  a "registration
 *      request")</li>
 * <li>Use an interface to the program / learning objectives (degree/major/minor/certificate/...) in which
 *      they are enrolled, or for which they may wish to consider in a "what if" planning scenario (e.g. when running
 *      degree audit)</li>
 * </ul>
 * @author Kuali Student Team
 * @version 1.0 (Dev)
 */
@WebService(name = "AcademicPlanService", serviceName = "AcademicPlanService", portName = "AcademicPlanService",
        targetNamespace = AcademicPlanServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL,
        parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface AcademicPlanService {

    /**
     * Retrieve learning plan by it's Id
     *
     * @param learningPlanId plan id
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return requested learning plan
     * @throws DoesNotExistException     a learning plan having the passed in Id was not found
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public LearningPlanInfo getLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId,
            @WebParam(name = "context") ContextInfo context)
    throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieve a set of learning plans that match the passed in list of plan ids
     *
     * @param learningPlanIds list of ids of plans to retrieve
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return set of learning plans that match the passed in list of plan ids
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException the user specified in the contextInfo is not authorized to perform the
     *                                   retrieval requested
     */
    public List<LearningPlanInfo> getLearningPlansByIds(
            @WebParam(name = "learningPlanIds") List<String> learningPlanIds,
            @WebParam(name = "context") ContextInfo context)
    throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve plan item that matches the passed in plan item id
     *
     * @param planItemId id of plan item to be retrieved
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return plan item matching the passed in plan item id
     * @throws DoesNotExistException     a plan item having the passed in Id was not found
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public PlanItemInfo getPlanItem(@WebParam(name = "planItemId") String planItemId,
            @WebParam(name = "context") ContextInfo context)
    throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieve a list of plan items matching the passed in ids
     *
     * @param planItemIds list of plan item ids
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return a list of plan items matching the passed in ids
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public List<PlanItemInfo> getPlanItemsByIds(@WebParam(name = "planItemIds") List<String> planItemIds,
            @WebParam(name = "context") ContextInfo context)
    throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieve a list of plan items in a specific plan, by plan item type
     *
     * @param learningPlanId id of learning plan for which items are to be retrieved
     * @param planItemTypeKey key of plan item type
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return a list of plan items for the indicated plan id and item type
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public List<PlanItemInfo> getPlanItemsInPlanByType(@WebParam(name = "learningPlanId") String learningPlanId,
            @WebParam(name = "planItemTypeKey") String planItemTypeKey,
            @WebParam(name = "context") ContextInfo context)
    throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieve a list of plan items in a specific plan, by item category
     *
     * @param learningPlanId id of learning plan for which items are to be retrieved
     * @param category the category (e.g. planned, bookmarked, backup) of items to be retrieved
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return a list of plan items for the indicated plan id and category
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public List<PlanItemInfo> getPlanItemsInPlanByCategory(@WebParam(name = "learningPlanId") String learningPlanId,
            @WebParam(name = "category") AcademicPlanServiceConstants.ItemCategory category,
            @WebParam(name = "context") ContextInfo context)
    throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * retrieve all items for the indicated plan
     *
     * @param learningPlanId id of plan for which items are to be retrieved
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return a list of plan items for the indcated plan id
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public List<PlanItemInfo> getPlanItemsInPlan(@WebParam(name = "learningPlanId") String learningPlanId,
            @WebParam(name = "context") ContextInfo context)
    throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieve a list of plan items that are planned for the indicated academic term and in the indicated category
     * category
     *
     * @param learningPlanId id of learning plan for which items are to be retrieved
     * @param termId id of the academic term for which items are to be retrieved
     * @param category the category (e.g. planned, bookmarked, backup) of items to be retrieved
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return a list of plan items for the indicated plan id, term id, and item category
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public List<PlanItemInfo> getPlanItemsInPlanByTermIdByCategory(
            @WebParam(name = "learningPlanId") String learningPlanId,
            @WebParam(name = "termId") String termId,
            @WebParam(name = "category") AcademicPlanServiceConstants.ItemCategory category,
            @WebParam(name = "context") ContextInfo context)
    throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Gets plan items for the indicated reference object id and reference object type
     *
     * @param learningPlanId The id of the plan.
     * @param refObjectId    The id of the referenced object.
     * @param refObjectType  The type of the referenced object.
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return a list of plan items for the indicated plan id, reference object id, and reference object type
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public List<PlanItemInfo> getPlanItemsInPlanByRefObjectIdByRefObjectType(
            @WebParam(name = "learningPlanId")String learningPlanId,
            @WebParam(name = "refObjectId") String refObjectId,
            @WebParam(name = "refObjectType") String refObjectType,
            @WebParam(name = "context") ContextInfo context)
    throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Get learning plans for the indicated student and of the indicated plan type
     *
     * @param studentId id of student for which learning plans are to be retrieved
     * @param planTypeKey key for type of plans to be retrieved
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return a list of learning plans for the indicated student and plan type
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public List<LearningPlanInfo> getLearningPlansForStudentByType(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "planTypeKey") String
                    planTypeKey,
            @WebParam(name = "context") ContextInfo context)
    throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * create learning plan
     *
     * @param learningPlan learning plan to be created
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return the newly created learning plan (...includes the newly assigned learningPlanId)
     * @throws AlreadyExistsException a learning plan with the indicated plan id already exists
     * @throws DataValidationErrorException a data item of the learning plan is invalid
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException the user (context.getUserId()) is not authorized to perform this operation
     */
    public LearningPlanInfo createLearningPlan(@WebParam(name = "learningPlan") LearningPlanInfo learningPlan,
            @WebParam(name = "context") ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * create plan item
     *
     *
     * @param planItem item to be created
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return the newly created learning plan (...includes the newly assigned learningPlanId)
     * @throws AlreadyExistsException a plan item with the indicated id already exists
     * @throws DataValidationErrorException a data item of the plan item is invalid
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException the user (context.getUserId()) is not authorized to perform this operation
     */
    public PlanItemInfo createPlanItem(@WebParam(name = "planItem") PlanItemInfo planItem,
            @WebParam(name = "context") ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * update learning plan
     *
     * @param learningPlanId id of learning plan to be updated
     * @param learningPlan updated learning plan to be persisted
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return the updated learning plan
     * @throws DataValidationErrorException a data item of the passed in learning plan is invalid
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException the user (context.getUserId()) is not authorized to perform this operation
     * @throws DoesNotExistException a learning plan does not exist having the indicated plan id
     * @throws VersionMismatchException the version on the passed in plan was out of sync with the last saved version
     */
    public LearningPlanInfo updateLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId,
            @WebParam(name = "learningPlan") LearningPlanInfo learningPlan,
            @WebParam(name = "context") ContextInfo context)
    throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
           OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException;

    /**
     * update learning plan item
     *
     * @param planItemId id of the plan item to be udpated
     * @param planItem item to be persisted
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return the updated plan item
     * @throws DataValidationErrorException a data item of the passed in plan item is invalid
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException the user (context.getUserId()) is not authorized to perform this operation
     * @throws DoesNotExistException a learning plan does not exist having the indicated plan id
     * @throws VersionMismatchException the version on the passed in plan was out of sync with the last saved version
     */
    public PlanItemInfo updatePlanItem(@WebParam(name = "planItemId") String planItemId,
            @WebParam(name = "planItem") PlanItemInfo planItem,
            @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException;

    /**
     * delete the indicated learning plan
     * @param learningPlanId id of plan to be deleted
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return StatusInfo status of delete:  where StatusInfo.isSuccess() is true only if the delete was successful
     *                                       and StatusInfo.getMessage() contains a textual description of the status
     * @throws DoesNotExistException a plan item does not exist having the indicated id
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException the user (context.getUserId()) is not authorized to perform this operation
     */
    public StatusInfo deleteLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId,
            @WebParam(name = "context") ContextInfo context)
    throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * delete the indicated learning plan
     * @param planItemId id of plan item to be deleted
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return StatusInfo status of delete:  where StatusInfo.isSuccess() is true only if the delete was successful
     *                                       and StatusInfo.getMessage() contains a textual description of the status
     * @throws DoesNotExistException a plan item does not exist having the indicated id
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException the user (context.getUserId()) is not authorized to perform this operation
     */
    public StatusInfo deletePlanItem(@WebParam(name = "planItemId") String planItemId,
            @WebParam(name = "context") ContextInfo context)
    throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * validate the passed in learning plan
     * @param validationType must be one of: "FULL_VALIDATION", "SKIP_REQUREDNESS_VALIDATIONS", see: DataDictionaryValidator
     * @param learningPlanInfo the learning plan object to be validated
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return a list of validation results (multiple validation issues may exist)
     * @throws DoesNotExistException a plan does not exist having the indicated id
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public List<ValidationResultInfo> validateLearningPlan(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "learningPlanInfo") LearningPlanInfo learningPlanInfo,
            @WebParam(name = "context") ContextInfo context)
    throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * validate the passed in plan item
     * @param validationType must be one of: "FULL_VALIDATION", "SKIP_REQUREDNESS_VALIDATIONS", see: DataDictionaryValidator
     * @param planItemInfo the learning plan item to be validated
     * @param context service call context, including: date-time of call, id of user executing the call
     * @return a list of validation results (multiple validation issues may exist)
     * @throws DoesNotExistException a plan item does not exist having the incated id
     * @throws InvalidParameterException a passed in parameter value is invalid
     * @throws MissingParameterException a passed in parameter value is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws AlreadyExistsException
     */
    public List<ValidationResultInfo> validatePlanItem(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "planItemInfo") PlanItemInfo planItemInfo,
            @WebParam(name = "context") ContextInfo context)
    throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            AlreadyExistsException;
}
