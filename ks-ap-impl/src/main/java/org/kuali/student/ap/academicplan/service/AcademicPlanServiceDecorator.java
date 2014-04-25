package org.kuali.student.ap.academicplan.service;

import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;


import java.util.List;

public class AcademicPlanServiceDecorator implements AcademicPlanService {
    private AcademicPlanService nextDecorator;

    public AcademicPlanService getNextDecorator()
        throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Next decorator was null. Your application may be misconfigured.");
        }
        return nextDecorator;
    }

    public void setNextDecorator(AcademicPlanService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public LearningPlanInfo getLearningPlan(String learningPlanId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLearningPlan(learningPlanId, context);
    }

    @Override
    public List<LearningPlanInfo> getLearningPlansByIds(List<String> learningPlanIds, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLearningPlansByIds(learningPlanIds, context);
    }

    @Override
    public PlanItemInfo getPlanItem(String planItemId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPlanItem(planItemId, context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsByIds(List<String> planItemIds,ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        return getNextDecorator().getPlanItemsByIds(planItemIds, context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByType(String learningPlanId,String planItemTypeKey,ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        return getNextDecorator().getPlanItemsInPlanByType(learningPlanId, planItemTypeKey, context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByCategory(String learningPlanId,
                           AcademicPlanServiceConstants.ItemCategory category, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        return getNextDecorator().getPlanItemsInPlanByCategory(learningPlanId, category, context);
    }
    @Override
    public List<PlanItemInfo> getPlanItemsInPlan(String learningPlanId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        return getNextDecorator().getPlanItemsInPlan(learningPlanId, context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByTermIdByCategory(String learningPlanId, String termId,
                                                                   AcademicPlanServiceConstants.ItemCategory category, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        return getNextDecorator().getPlanItemsInPlanByTermIdByCategory(learningPlanId, termId, category, context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByRefObjectIdByRefObjectType(String learningPlanId,String refObjectId,
                                                                             String refObjectType,ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        return getNextDecorator().getPlanItemsInPlanByRefObjectIdByRefObjectType(learningPlanId, refObjectId, refObjectType, context);
    }

    @Override
    public List<LearningPlanInfo> getLearningPlansForStudentByType(String studentId,String planTypeKey,ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        return getNextDecorator().getLearningPlansForStudentByType(studentId, planTypeKey, context);
    }

    @Override
    public LearningPlanInfo createLearningPlan(LearningPlanInfo learningPlan,ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
                MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLearningPlan(learningPlan, context);
    }

    @Override
    public PlanItemInfo createPlanItem(PlanItemInfo planItem,ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createPlanItem(planItem, context);
    }

    @Override
    public LearningPlanInfo updateLearningPlan(String learningPlanId,LearningPlanInfo learningPlan,ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {
        return getNextDecorator().updateLearningPlan(learningPlanId, learningPlan, context);
    }

    @Override
    public PlanItemInfo updatePlanItem(String planItemId,PlanItemInfo planItem,ContextInfo context)
    throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {
        return getNextDecorator().updatePlanItem(planItemId, planItem, context);
    }

    @Override
    public StatusInfo deleteLearningPlan(String learningPlanId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                    OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLearningPlan(learningPlanId, context);
    }

    @Override
    public StatusInfo deletePlanItem(String planItemId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deletePlanItem(planItemId, context);
    }

    @Override
    public List<ValidationResultInfo> validateLearningPlan(String validationType, LearningPlanInfo learningPlanInfo,
                                                           ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateLearningPlan(validationType, learningPlanInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validatePlanItem(String validationType, PlanItemInfo planItemInfo,
                                                       ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, AlreadyExistsException, PermissionDeniedException {
        return getNextDecorator().validatePlanItem(validationType, planItemInfo, context);
    }

}