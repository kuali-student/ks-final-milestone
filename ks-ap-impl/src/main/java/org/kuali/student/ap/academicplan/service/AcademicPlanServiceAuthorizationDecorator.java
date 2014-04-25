package org.kuali.student.ap.academicplan.service;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
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
import org.kuali.student.r2.common.infc.HoldsPermissionService;
import org.kuali.student.r2.common.util.constants.KimIdentityServiceConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@inheritDoc}
 * This service decorator simply adds authorization logic to each service method.
 * <p/>
 * IMPORTANT NOTE: this is a first cut, somewhat crude implementation of authorization for KSAP, but it does set up a
 * good framework in which a more rich authz design for KSAP can be applied.  A more rich design could, IMO, include a
 * rich set of hierarachical roles, such as:  PlanMaintainer, PlanReviewer, KSAPAdmin.  PlanMaintainer could be granted
 * PLAN CREATE/UPDATE/DELETE permissions,and could be granted to the Student role, but would need to be qualified by
 * planOwnerPrincipalId=studentPrincipalId, PlanMaintainer could be granted to a new Advisor [derived] role,
 * but would be perhaps qualified by planType='TEMPLATE' (note: something more complex might be need to allow an
 * adivisor to add recommended courses to a shared plan).PlanReviewer would give READ permissions to
 * plans/items, and could be granted to the Advisor role qualified by plan.shared='T' (& perhaps something different,
 * or more specific for plan snapshots) or planType='TEMPLATE', and to the Student role qualified [again] by
 * planOwnerPrincipalId=studentPrincipalId.
 */
public class AcademicPlanServiceAuthorizationDecorator
extends AcademicPlanServiceDecorator
        implements HoldsPermissionService {

    private PermissionService permissionService;
		
	@Override
	public PermissionService getPermissionService() {
		return permissionService;
	}

	@Override
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
    
    Map<String, String> qualifiers= new HashMap<String,String>();

    @Override
    public LearningPlanInfo getLearningPlan(String learningPlanId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/getLearningPlan", qualifiers)) {
            throw new PermissionDeniedException("Permission Denied.");
        }
        return checkPlanAccess(getNextDecorator().getLearningPlan(learningPlanId, context), context.getPrincipalId());
    }

    @Override
    public List<LearningPlanInfo> getLearningPlansByIds(List<String> learningPlanIds, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/getLearningPlansByIds", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return checkPlanListAccess(getNextDecorator().getLearningPlansByIds(learningPlanIds, context),
                context.getPrincipalId());
    }

    @Override
    public PlanItemInfo getPlanItem(String planItemId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/getPlanItem", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return checkItemAccess(getNextDecorator().getPlanItem(planItemId, context), context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsByIds(List<String> planItemIds, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/getPlanItemsByIds", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return checkItemListAccess(getNextDecorator().getPlanItemsByIds(planItemIds, context), context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByType(String learningPlanId, String planItemTypeKey,
            ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/getPlanItemsInPlanByType", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return checkItemListAccess(getNextDecorator().getPlanItemsInPlanByType(learningPlanId, planItemTypeKey,
                context), context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByCategory(String learningPlanId,
            AcademicPlanServiceConstants.ItemCategory category, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/getPlanItemsInPlanByCategory", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return checkItemListAccess(getNextDecorator().getPlanItemsInPlanByCategory(learningPlanId, category,
                context), context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlan(String learningPlanId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/getPlanItemsInPlan", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return checkItemListAccess(getNextDecorator().getPlanItemsInPlan(learningPlanId, context), context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByTermIdByCategory(String learningPlanId, String termId,
            AcademicPlanServiceConstants.ItemCategory category, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/getPlanItemsInPlanByTermIdByCategory", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return checkItemListAccess(getNextDecorator().getPlanItemsInPlanByTermIdByCategory(learningPlanId, termId,
                category, context), context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByRefObjectIdByRefObjectType(String learningPlanId, String refObjectId,
            String refObjectType, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/getPlanItemsInPlanByRefObjectIdByRefObjectType", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return checkItemListAccess(getNextDecorator().
                getPlanItemsInPlanByRefObjectIdByRefObjectType(learningPlanId, refObjectId, refObjectType, context),
                context);
    }

    @Override
    public List<LearningPlanInfo> getLearningPlansForStudentByType(String studentId, String planTypeKey,
            ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/getLearningPlansForStudentByType", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return checkPlanListAccess(getNextDecorator().getLearningPlansForStudentByType(studentId, planTypeKey,
                context), context.getPrincipalId());
    }

    @Override
    public LearningPlanInfo createLearningPlan(LearningPlanInfo learningPlan, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
                   MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/createLearningPlan", qualifiers)) {
            throw new PermissionDeniedException("Permission Denied.");
        }
        return checkPlanAccess(getNextDecorator().createLearningPlan(learningPlan, context), context.getPrincipalId());
    }

    @Override
    public PlanItemInfo createPlanItem(PlanItemInfo planItem, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
                   MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/createPlanItem", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return checkItemAccess(getNextDecorator().createPlanItem(planItem, context), context);
    }

    @Override
    public LearningPlanInfo updateLearningPlan(String learningPlanId, LearningPlanInfo learningPlan,
            ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException, DoesNotExistException,
                   VersionMismatchException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/updateLearningPlan", qualifiers)) {
            throw new PermissionDeniedException("Permission Denied.");
        }
        return getNextDecorator().updateLearningPlan(learningPlanId,
                checkPlanAccess(learningPlan, context.getPrincipalId()), context);
    }

    @Override
    public PlanItemInfo updatePlanItem(String planItemId, PlanItemInfo planItem, ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException, DoesNotExistException,
                   VersionMismatchException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/updatePlanItem", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return getNextDecorator().updatePlanItem(planItemId, checkItemAccess(planItem, context), context);
    }

    @Override
    public StatusInfo deleteLearningPlan(String learningPlanId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/deleteLearningPlan", qualifiers)) {
            throw new PermissionDeniedException("Permission Denied.");
        }
        return getNextDecorator().deleteLearningPlan(
                checkPlanAccess(getLearningPlan(learningPlanId, context), context.getPrincipalId()).getId(), context);
    }

    @Override
    public StatusInfo deletePlanItem(String planItemId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/deletePlanItem", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return getNextDecorator().deletePlanItem(checkItemAccess(getPlanItem(planItemId,context),context).getId(),context);
    }

    @Override
    public List<ValidationResultInfo> validateLearningPlan(String validationType, LearningPlanInfo learningPlanInfo,
            ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/validateLearningPlan", qualifiers)) {
            throw new PermissionDeniedException("Permission Denied.");
        }
        return getNextDecorator().validateLearningPlan(validationType, checkPlanAccess(learningPlanInfo,
                context.getPrincipalId()), context);
    }

    @Override
    public List<ValidationResultInfo> validatePlanItem(String validationType, PlanItemInfo planItemInfo,
            ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, AlreadyExistsException, PermissionDeniedException {
        if (!permissionService.isAuthorized(context.getPrincipalId(), AcademicPlanServiceConstants.KS_AP_NAMESPACE,
                AcademicPlanServiceConstants.SERVICE_NAME + "/validatePlanItem", qualifiers)) {
            throw new OperationFailedException("Permission Denied.");
        }
        return getNextDecorator().validatePlanItem(validationType, checkItemAccess(planItemInfo, context), context);
    }
    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param paramName
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName) throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }
    private boolean isStudent(String principal) {
        return hasAffiliation(KimIdentityServiceConstants.STUDENT_AFFILIATION_TYPE_KEY, principal);
    }

    private boolean isAdvisor(String principal) {
        return hasAffiliation(AcademicPlanServiceConstants.ADVISOR_AFFILIATION_TYPE_KEY, principal);
    }
    private boolean hasAffiliation(String affiliationTypeKey, String principalId) {
        Person signedOnPerson = KimApiServiceLocator.getPersonService().getPerson(principalId);
        // check to see if principalID is a staff member or faculty memeber
        if (signedOnPerson.hasAffiliationOfType(affiliationTypeKey)) {
            return true;
        }
        return false;
    }

    private LearningPlanInfo checkPlanAccess(LearningPlanInfo plan,String principalId)
            throws PermissionDeniedException {
        if (isStudent(principalId))  {
            if  (!principalId.equals(plan.getStudentId())) {
                throw new PermissionDeniedException ("Students may retrieve only their own academic plans.");
            }
        } else if (isAdvisor(principalId)) {
            if (!plan.getShared()) {
                throw new PermissionDeniedException ("Plan (id="+plan.getId()+", name="+plan.getName()+
                        " for studentId="+plan.getStudentId()+") is not marked a shared. Permission Denied. ");
            }
        } else {
            throw new PermissionDeniedException("Permission Denied.");
        }
        return plan;
    }

    private List<LearningPlanInfo> checkPlanListAccess(List<LearningPlanInfo> learningPlans, String principalId)
            throws PermissionDeniedException {
        for (LearningPlanInfo plan : learningPlans) {
            checkPlanAccess(plan,principalId);
        }
        return learningPlans;
    }

    private PlanItemInfo checkItemAccess(PlanItemInfo planItem, ContextInfo context)
            throws PermissionDeniedException, OperationFailedException {
        try {
            checkPlanAccess(getLearningPlan(planItem.getLearningPlanId(), context), context.getPrincipalId());
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Unexpected exception checking plan access: "+e.getMessage(),e);
        } catch (InvalidParameterException e) {
            throw new OperationFailedException("Unexpected exception checking plan access: "+e.getMessage(),e);
        } catch (MissingParameterException e) {
            throw new OperationFailedException("Unexpected exception checking plan access: "+e.getMessage(),e);
        } catch (OperationFailedException e) {
            throw new OperationFailedException("Unexpected exception checking plan access: "+e.getMessage(),e);
        }
        return planItem;  //To change body of created methods use File | Settings | File Templates.
    }

    private List<PlanItemInfo> checkItemListAccess(List<PlanItemInfo> planItemsByIds, ContextInfo context)
            throws PermissionDeniedException, OperationFailedException {
        String lastPlanChecked=null;
        for (PlanItemInfo item : planItemsByIds) {

            //only check access to plan once! (i.e. likely list of items is for one plan only)
            if (!item.getLearningPlanId().equals(lastPlanChecked)) {
                checkItemAccess(item,context);
                lastPlanChecked=item.getLearningPlanId();
            }
        }
        return planItemsByIds;
    }


}
