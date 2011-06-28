/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.enrollment.exemption.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.enrollment.exemption.dto.ExemptionInfo;
import org.kuali.student.enrollment.exemption.dto.ExemptionRequestInfo;

import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.service.TypeService;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.ExemptionServiceConstants;

/**
 * Version: DRAFT - NOT READY FOR RELEASE. 
 *
 * The Exemption service stores information to permit a person to be
 * exempted from the enforcement of a restriction, deadline, or
 * statement.
 *
 * The service begins with creating an ExemptionRequest for a
 * person. On approval of an ExemptionRequest, one or more Exemptions
 * are created. There are several types of Exemptions. Exemption types
 * govern what data is stored in the Exemption structure. Current
 * types include:
 *
 *     Restriction Exemption: overrides a Restriction in the Hold Service
 *     Statement Exemption: overrides a Statement in the Statement Service to true
 *     Date Exemption: overrides a Milestone in ATP with a Date
 *     Milestone Exemption: overrides a Milestone in ATP with another Milestone
 *
 * The Exemption stores the fact that an exemption has been
 * granted. The interpretation and enforcment of the exemption is
 * performed by the caller of this service. The Exemption service
 * provides the information to override a specific check that occurs
 * somewhere in the system based on one of the above Exemption
 * types. 
 *
 * A code example that demonstrates a way to check for Exemptions
 * while performing a task with restrictions, deadlines, and statement
 * evaluations:
 *
 * <pre>
 * pretendToRegisterStudentInCourse(String personId, RegstrationGroup regGrp) {
 *     Collection<Exemption> usedExemptions = new HashSet<Exemption>();
 *
 *     String courseId = regGrp.getCourseOffering().getCourse().getId();
 *
 *     // check for registration restrictions
 *     String restrictionKey = "course registration";
 *     if (HoldService.isPersonRestricted("course registration", personId, context)) {
 *         try {
 *             Exemption e = ExemptionService.retrieveRestrictionExemption(personId, restrictionKey, courseObjectType, courseId, context);
 *             usedExemptions.add(e);
 *         } catch (NotFoundException nfe) {
 *             throw new YouCantDoThisException("If I were a nice person, I'd fetch the restriction and tell you what it is.");
 *         }
 *     }
 *
 *     // check for registration deadlines
 *     String regDeadlineCheckKey = "kualu.courseregistration.check.deadline";
 *     String milestoneKey = "this term's drop/add date milestone key";
 *     Milestone deadline = AtpService.getMilestone(milestoneKey, context);
 *     if (now > deadline.getStartDate().getTime()) {
 *         boolean hasDeadline = true;
 *         try {
 *             // there are two kinds of milestone exsmptions. first check
 *             // for an overriding milestone
 *             Exemption e = ExemptionService.retrieveMilestoneExemption(regDeadlineCheckKey, personId, milestoneKey, courseObjectType, courseId, context);
 *             Milestone m = AtpService.getMilestone(e.getMilestoneOverride().getEffectiveMilestoneKey()), context);
 *             if (now < m.getStartDate().getTime()) {
 *                 hasDeadline = false;
 *                 usedExemptions.add(e);
 *             }
 *         } catch (NotFoundException nfe) {
 *             try {
 *                 // check for a date override to the milestone
 *                 Exemption e = ExemptionService.retrieveDateExemption(personId, milestoneKey, courseObjectType, courseId, context);
 *                 if (now < e.getDateOverride().getEndDate().getTime()) {
 *                     hasDeadline = false;
 *                     usedExemptions.add(e);
 *                 }
 *             } catch (NotFoundException nfe2) {
 *                 // hasDeadline still true
 *             }
 *         }
 *
 *         if (hasDeadline) {
 *              throw new YouMissedItException("try again next year");
 *         }
 *     }
 *
 *     // check for course prereqs
 *     String coursePrereqCheckKey = "kualu.courseregistration.check.course.prereq";
 *     for (RefStatementRelationInfo relation : StatementService.getRefStatementRelationsByRef(COURSE_TYPE, courseId)) {
 *         if (relation.getType().equals("kuali.student.statement.relation.clu.prerequisites")) {
 *             try {
 *                 Exemption e = ExemptionService.retrieveStatementExemption(coursePrereqCheckKey, personId, relation.getStatementId(), courseId, courseObjectType, courseId, context))
 *                 usedExemptions.add(e);
 *             } catch (NotFoundException nfe2) {
 *                 throw new YouDontMeetARequirementException("read the requirements");
 *             }
 *         }
 *     }
 *
 *     // proceed with registration. If registration is successful, 
 *     // add the usage to all the exemptions used.
 *     persistRegistration();
 *     for (Exemption e : usedExemptions) {
 *         ExemptionService.addUseToExemption(e.getId());
 *     }
 * }
 * </pre>   
 *
 * Finally... there are two additional exemption types. 
 *
 *    Hold Exemption: records that a Hold was overidden in the Hold
 *                    service. Overriding a Hold effects the
 *                    Restriction in the Hold service. Hold Exemptions
 *                    should not be checked to determine the fate of a
 *                    Restriction.
 *
 *    Learning Result Exception: records the fact that an LRR was
 *                               created. Creating an LRR changes the
 *                               academic record and may have numerous
 *                               impacts throughout the
 *                               system. Nothing should be making a
 *                               determination based on the existence
 *                               of a Learning Result Exception.
 *                               
 * Exemptions are ebbraviated Exmpts in very long method names.
 *
 * @author tom
 * @since Tue Jun 21 14:22:34 EDT 2011
 */

@WebService(name = "ExemptionService", targetNamespace = ExemptionServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface ExemptionService extends DataDictionaryService, StateService, TypeService {

    /*
     * This section defines methods used to retrieve an exemption to
     * perform overrides on various checks. A separate set of methods
     * are defined to examine all exemptions that exist.
     */

    /** 
     * Retrieves an active effective exemption for a person to
     * override a Restriction. An effective exemption is one with an
     * active state, the current date falls within the effective
     * date range, and the use count is less than the use limit.
     *
     * Exemptions may have a qualifier which serves to scope the
     * Exemption. An Exemption that is unqualified is global such that
     * any Exemption that is related to the person and restriction may
     * be returned. Otherwise, the qualifier and qualifier type in the
     * Exemption must match the given qualifier and qualifier type.
     *
     * In the case multiple Exemptions meet the criteria, the
     * implementation chooses the one that expires the soonest.
     *
     * The Check to which the Exemption applies is implied by the
     * Restriction.
     *
     * @param personId a unique Id of the Person
     * @param restrictionKey a unique key for the restriction to exempt
     * @param qualifierTypeKey the key for a qualifier type
     * @param qualifierId the Id for a qualifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return an Exemption if one exists
     * @throws DoesNotExistException no valid exemption exists
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
     ExemptionInfo retrieveRestrictionExemption(@WebParam(name="personId") String personId, @WebParam(name = "restrictionKey") String restrictionKey, @WebParam(name = "qualifierTypeKey") String qualifierTypeKey, @WebParam(name = "qualifierId") String qualifierId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves an active effective exemption for a person to
     * override a Milestone. An effective exemption is one with an
     * active state, the current date falls within the effective
     * date range, and the use count is less than the use limit.
     *
     * The MilestoneOverride returns a new milstone.
     *     
     * Exemptions may have a qualifier which serves to scope the
     * Exemption. An Exemption that is unqualified is global such that
     * any Exemption that is related to the person and restriction may
     * be returned. Otherwise, the qualifier and qualifier type in the
     * Exemption must match the given qualifier and qualifier type.
     *
     * In the case multiple Exemptions meet the criteria, the
     * implementation chooses the one that expires the soonest.
     *
     * @param checkKey a key indicating the check to which the
     *        exemption applies
     * @param personId a unique Id of the Person
     * @param milestoneKey a unique key for milestone to exempt
     * @param qualifierTypeKey the key for a qualifier type
     * @param qualifierId the Id for a qualifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return an Exemption if one exists
     * @throws DoesNotExistException no valid exemption exists
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
     ExemptionInfo retrieveMilestoneExemption(@WebParam(name = "checkKey") String checkKey, @WebParam(name="personId") String personId, @WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "qualifierTypeKey") String qualifierTypeKey, @WebParam(name = "qualifierId") String qualifierId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves an active effective exemption for a person to
     * override a Milestone. An effective exemption is one with an
     * active state, the current date falls within the effective
     * date range, and the use count is less than the use limit.
     *
     * The DateOverride returns a new date.
     *
     * Exemptions may have a qualifier which serves to scope the
     * Exemption. An Exemption that is unqualified is global such that
     * any Exemption that is related to the person and restriction may
     * be returned. Otherwise, the qualifier and qualifier type in the
     * Exemption must match the given qualifier and qualifier type.
     *
     * In the case multiple Exemptions meet the criteria, the
     * implementation chooses the one that expires the soonest.
     *
     * @param checkKey a key indicating the check to which the
     *        exemption applies
     * @param personId a unique Id of the Person
     * @param milestoneKey a unique key for milestone to exempt
     * @param qualifierTypeKey the key for a qualifier type
     * @param qualifierId the Id for a qualifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return an Exemption if one exists
     * @throws DoesNotExistException no valid exemption exists
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
     ExemptionInfo retrieveDateExemption(@WebParam(name = "checkKey") String checkKey, @WebParam(name="personId") String personId, @WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "qualifierTypeKey") String qualifierTypeKey, @WebParam(name = "qualifierId") String qualifierId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves an active effective exemption for a person to
     * override a Milestone. An effective exemption is one with an
     * active state, the current date falls within the effective
     * date range, and the use count is less than the use limit.
     *
     * The Statement Override instructs the caller to evaluate the
     * given statement as true.
     *
     * Exemptions may have a qualifier which serves to scope the
     * Exemption. An Exemption that is unqualified is global such that
     * any Exemption that is related to the person and restriction may
     * be returned. Otherwise, the qualifier and qualifier type in the
     * Exemption must match the given qualifier and qualifier type.
     *
     * In the case multiple Exemptions meet the criteria, the
     * implementation chooses the one that expires the soonest.
     *
     * @param checkKey a key indicating the check to which the
     *        exemption applies
     * @param personId a unique Id of the Person
     * @param statementId a unique Id for a Statement
     * @param statementAnchorId the Statement anchor
     * @param qualifierTypeKey the key for a qualifier type
     * @param qualifierId the Id for a qualifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return an Exemption if one exists
     * @throws DoesNotExistException no valid exemption exists
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
     ExemptionInfo retrieveStatementExemption(@WebParam(name = "checkKey") String checkKey, @WebParam(name="personId") String personId, @WebParam(name = "statementId") String statementId, @WebParam(name = "statementAnchorId") String statementAnchorId, @WebParam(name = "qualifierTypeKey") String qualifierTypeKey, @WebParam(name = "qualifierId") String qualifierId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Indicate that the given Exemption has been used successfully in
     * a transaction. This method increments the Exemption use count.
     *
     * @param exemptionId the Id for the Exemption
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the status
     * @throws DoesNotExistException no valid exemption exists
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
     StatusInfo addUseToExemption(@WebParam(name = "exeptionId") String exemptionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /*
     * This set of methods examines all Exemptions as it
     * related to a person.
     */

    /** 
     * Gets a list of all exemptions by qualfiier. 
     *
     * @param qualifierTypeKey the qualifier type
     * @param qualifierId the qualifier
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getExemptionsByQualifier(@WebParam(name = "qualifierTypeKey") String qualifierTypeKey, @WebParam(name = "qualifierId") String qualifierId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets a list of all exemptions for a Person.
     *
     * @param personId a unique Id of the Person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getExemptionsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets a list of all active effective exemptions for a Person. An
     * effective exemption is one with an active state, the current
     * date falls within the effective date range, and the use count
     * is less than the use limit.
     *
     * @param personId a unique Id of the Person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getActiveExemptionsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets a list of all exemptions by Type for a Person.
     *
     * @param typeKey an exemption Type
     * @param personId a unique Id of the Person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getExemptionsByTypeForPerson(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets a list of all effective exemptions by Type for a
     * Person. An effective exemption is one with an active state, the
     * current date falls within the effective date range, and the use
     * count is less than the use limit.
     *
     * @param typeKey an exemption Type
     * @param personId a unique Id of the Person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getActiveExemptionsByTypeForPerson(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /*
     * This section defines the standard crud pattern for
     * managing Exemptions and requests.
     */

    /** 
     * Retrieves the details of a single Exemption by a exemption Id.
     *
     * @param exemptionId Unique Id of the Exemption to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Exemption requested
     * @throws DoesNotExistException exemptionId not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ExemptionInfo getExemption(@WebParam(name = "exemptionId") String exemptionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Exemptions corresponding to a list of
     * exemption Ids.
     *
     * @param exemptionIdList list of unique Ids of the
     *        Exemption to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Exemptions
     * @throws DoesNotExistException an exemptionId in list not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionInfo> getExemptionsByIdList(@WebParam(name = "exemptionIdList") List<String> exemptionIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Exemption Ids by Type.
     *
     * @param exemptionTypeKey a unique key for an exemption Type
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Exemptions
     * @throws DoesNotExistException an exemptionId in list not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getExemptionIdsByType(@WebParam(name = "exemptionTypeKey") String exemptionTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates an Exemption. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained subobjects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the exemption and a record is found for that identifier,
     * the validation checks if the exemption can be shifted to the new
     * values. If a record cannot be found for the identifier, it is
     * assumed that the record does not exist and as such, the checks
     * performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current
     * object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create
     * statement instead of the server assigning an identifier.
     *
     * @param validationTypeKey Identifier of the extent of validation
     * @param exemptionInfo the exemption information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, exemptionInfo
     * @throws MissingParameterException missing validationTypeKey, exemptionInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateExemption(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "exemptionInfo") ExemptionInfo exemptionInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Exemption. Exemptions can only be created off of
     * an exemption request.
     *
     * @param exemptionRequestId an Id for an exemption request
     * @param exemptionInfo Details of the Exemption to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Exemption just created
     * @throws AlreadyExistsException the Exemption being created already exists
     * @throws DataValidationErrorException One or more values invalid for 
     *         this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ExemptionInfo createExemption(@WebParam(name = "exemptionRequestId") String exemptionRequestId, @WebParam(name = "exemptionInfo") ExemptionInfo exemptionInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Exemption.
     *
     * @param exemptionId Id of Exemption to be updated
     * @param exemptionInfo Details of updates to the Exemption
     *        being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of Exemption just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the Exemption does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public ExemptionInfo updateExemption(@WebParam(name = "exemptionId") String exemptionId, @WebParam(name = "exemptionInfo") ExemptionInfo exemptionInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Exemption record.
     *
     * @param exemptionId the Id of the Exemption to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Exemption does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteExemption(@WebParam(name = "exemptionId") String exemptionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets a list of all exemption requests for a Person.
     *
     * @param personId a unique Id of the Person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionRequestInfo> getRequestsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets a list of all exemption requests by Type for a Person.
     *
     * @param typeKey an exemption Type
     * @param personId a unique Id of the Person
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionRequestInfo> getRequestsByTypeForPerson(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the details of a single ExemptionRequest by a
     * exemption Id.
     *
     * @param exemptionRequestId Unique Id of the ExemptionRequest to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the ExemptionRequest requested
     * @throws DoesNotExistException exemptionRequestId not found
     * @throws InvalidParameterException invalid exemptionRequestId
     * @throws MissingParameterException missing exemptionRequestId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ExemptionRequestInfo getExemptionRequest(@WebParam(name = "exemptionRequestId") String exemptionRequestId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list ExemptionRequests corresponding to a list of
     * exemption request Ids.
     *
     * @param exemptionRequestIdList list of unique Ids of the
     *        ExemptionRequests to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of ExemptionRequests
     * @throws DoesNotExistException an exemptionRequestId in list not found
     * @throws InvalidParameterException invalid exemptionRequestId in list
     * @throws MissingParameterException missing exemptionRequestIdList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ExemptionRequestInfo> getExemptionRequestsByIdList(@WebParam(name = "exemptionRequestIdList") List<String> exemptionRequestIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Exemption Request Ids by Type.
     *
     * @param exemptionRequestTypeKey a unique key for an exemption
     *        requestType
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Exemptions
     * @throws DoesNotExistException an exemptionId in list not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getExemptionRequestIdsByType(@WebParam(name = "exemptionRequestTypeKey") String exemptionRequestTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Exemption Request Ids by Check Key.
     *
     * @param checkKey a unique key for a Check
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Exemptions
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getExemptionRequestIdsByCheck(@WebParam(name = "checkKey") String checkKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates an ExemptionRequest. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained subobjects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the exemption request and a record is
     * found for that identifier, the validation checks if the
     * exemption request can be shifted to the new values. If a record
     * cannot be found for the identifier, it is assumed that the
     * record does not exist and as such, the checks performed will be
     * much shallower, typically mimicking those performed by setting
     * the validationType to the current object. This is a slightly
     * different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the
     * server assigning an identifier.
     *
     * @param validationTypeKey Identifier of the extent of validation
     * @param exemptionRequestInfo the exemption request information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, exemptionRequestInfo
     * @throws MissingParameterException missing validationTypeKey, exemptionRequestInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateExemptionRequest(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "exemptionRequestInfo") ExemptionRequestInfo exemptionRequestInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new ExemptionRequst.
     *
     * @param exemptionRequestInfo Details of the Exemption Request to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Exemption Requst just created
     * @throws AlreadyExistsException the Exemption Request being
     *         created already exists
     * @throws DataValidationErrorException One or more values invalid for 
     *         this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ExemptionRequestInfo createExemptionRequest(@WebParam(name = "exemptionRequestInfo") ExemptionRequestInfo exemptionRequestInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Exemption Request.
     *
     * @param exemptionRequestId Id of Exemption Request to be updated
     * @param exemptionRequestInfo Details of updates to the Exemption
     *        Request being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of Exemption Request just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the Exemption Request does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public ExemptionRequestInfo updateExemptionRequest(@WebParam(name = "exemptionRequestId") String exemptionRequestId, @WebParam(name = "exemptionRequestInfo") ExemptionRequestInfo exemptionRequestInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Exemption Request record.
     *
     * @param exemptionRequest Id the Id of the Exemption Request to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Exemption Request does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteExemptionRequest(@WebParam(name = "exemptionRequestId") String exemptionRequestId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}
