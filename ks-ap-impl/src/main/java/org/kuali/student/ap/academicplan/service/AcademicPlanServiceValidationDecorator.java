package org.kuali.student.ap.academicplan.service;

import org.kuali.student.ap.academicplan.dao.PlanItemDao;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemSetInfo;
import org.kuali.student.ap.academicplan.model.PlanItemEntity;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.HoldsDataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import java.util.ArrayList;
import java.util.List;

public class AcademicPlanServiceValidationDecorator extends
		AcademicPlanServiceDecorator implements HoldsValidator,
		HoldsDataDictionaryService {


    private PlanItemDao planItemDao;

    public PlanItemDao getPlanItemDao() {
        return planItemDao;
    }

    public void setPlanItemDao(PlanItemDao planItemDao) {
        this.planItemDao = planItemDao;
    }

	private DataDictionaryValidator validator;

	private DataDictionaryService dataDictionaryService;

	@Override
	public DataDictionaryService getDataDictionaryService() {
		return dataDictionaryService;
	}

	@Override
	public void setDataDictionaryService(
			DataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

	@Override
	public DataDictionaryValidator getValidator() {
		return validator;
	}

	@Override
	public void setValidator(DataDictionaryValidator validator) {
		this.validator = validator;
	}

	@Override
	public LearningPlanInfo getLearningPlan(String learningPlanId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return getNextDecorator().getLearningPlan(learningPlanId, context);
	}

	@Override
	public List<PlanItemInfo> getPlanItemsInPlan(String learningPlanId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return getNextDecorator().getPlanItemsInPlan(learningPlanId, context);
	}

	@Override
	public List<PlanItemInfo> getPlanItemsInPlanByRefObjectIdByRefObjectType(
			String learningPlanId, String refObjectId, String refObjectType,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return getNextDecorator()
				.getPlanItemsInPlanByRefObjectIdByRefObjectType(learningPlanId,
						refObjectId, refObjectType, context);
	}

	@Override
	public List<LearningPlanInfo> getLearningPlansForStudentByType(
			String studentId, String planTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return getNextDecorator().getLearningPlansForStudentByType(studentId,
				planTypeKey, context);
	}

	@Override
	public LearningPlanInfo createLearningPlan(LearningPlanInfo learningPlan,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		fullValidation(learningPlan, context);
		return getNextDecorator().createLearningPlan(learningPlan, context);
	}

	@Override
	public PlanItemInfo createPlanItem(PlanItemInfo planItem,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		fullValidation(planItem, context);

        try {
            KsapFrameworkServiceLocator.getTypeService().getType(planItem.getTypeKey(), context);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException(String.format("Unknown plan item type id [%s].", planItem.getTypeKey()));
        }
        return getNextDecorator().createPlanItem(planItem, context);
	}

	private static List<ValidationResultInfo> validateInfo(
			DataDictionaryValidator validator, String validationType,
			Object info, ContextInfo context) throws OperationFailedException,
			MissingParameterException, InvalidParameterException {
                                         		if (null == validator) {
			throw new InvalidParameterException(
					"DataDictionaryValidator parameter cannot be null");
		}

		List<ValidationResultInfo> errors;
		try {
			errors = validator.validate(DataDictionaryValidator.ValidationType
					.fromString(validationType), info, context);
		} catch (PermissionDeniedException ex) {
			throw new OperationFailedException(
					"Validation failed due to permission exception", ex);
		}
		return errors;
	}

	@Override
	public List<ValidationResultInfo> validateLearningPlan(
			String validationType, LearningPlanInfo learningPlanInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		List<ValidationResultInfo> errors = null;
		try {
			errors = validateInfo(validator, validationType,
					learningPlanInfo, context);

			List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator()
					.validateLearningPlan(validationType, learningPlanInfo,
							context);

			if (null != nextDecoratorErrors) {
				errors.addAll(nextDecoratorErrors);
			}
		} catch (DoesNotExistException ex) {
			throw new OperationFailedException(
					"Error validating learning plan.", ex);
		}
		return errors;
	}

	@Override
	public List<ValidationResultInfo> validatePlanItem(String validationType,
			PlanItemInfo planItemInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			AlreadyExistsException {

        List<ValidationResultInfo> validationResultInfos = validateInfo(validator, validationType,
            planItemInfo, context);

		/*
		 * Validate that the course exists.
		 */
        try {
            if (KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(planItemInfo.getRefObjectId()) == null) {
                validationResultInfos.add(makeValidationResultInfo(
                    String.format("Could not find course with ID [%s].", planItemInfo.getRefObjectId()),
                    "refObjectId", ValidationResult.ErrorLevel.ERROR));
            }
        } catch (RuntimeException e) {
            validationResultInfos.add(makeValidationResultInfo(e.getLocalizedMessage(), "refObjectId",
                    ValidationResult.ErrorLevel.ERROR));
        }

        //  Make sure a plan period exists if category is planned course.
        //note: the DD validation will catch this null & report an error for it...
        //      BIT we just need to avoid this block when it is null to avoid a NP exception
        if (planItemInfo.getCategory()!=null) {
            if (planItemInfo.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.PLANNED)
                    || planItemInfo.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.BACKUP)) {
                if (planItemInfo.getPlanPeriods() == null || planItemInfo.getPlanPeriods().size() == 0) {
                    validationResultInfos.add(makeValidationResultInfo(
                        String.format("Plan Item category was [%s], but no plan periods were defined.",
                                planItemInfo.getCategory()), "category", ValidationResult.ErrorLevel.ERROR));
                } else {
                    //  Make sure the plan periods are valid. Note: There should never be more than one item in the collection.
                    for (String atpId : planItemInfo.getPlanPeriods()) {
                        boolean valid = false;
                        try {
                            valid = isValidTerm(atpId);
                            if (!valid) {
                                validationResultInfos.add(makeValidationResultInfo(
                                    String.format("ATP ID [%s] was not valid.", atpId), "atpId",
                                    ValidationResult.ErrorLevel.ERROR));
                            }
                        } catch (Exception e) {
                            validationResultInfos.add(makeValidationResultInfo("ATP ID lookup failed.", "typeKey",
                                    ValidationResult.ErrorLevel.ERROR));
                        }
                    }
                }
            }
        }

		/*
		 * Check for duplicate list items: Make sure a saved courses item with
		 * this course id doesn't already exist in the plan. Make sure a planned
		 * course item with the same ATP id doesn't exist in the plan.
		 *
		 * Note: This validation is last to insure that all of the other
		 * validations are performed on "update" operations. The duplicate check
		 * throw an AlreadyExistsException on updates.
		 */
        checkPlanItemDuplicate(planItemInfo);

        try {
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator()
                    .validatePlanItem(validationType, planItemInfo, context);
            if (null != nextDecoratorErrors) {
                validationResultInfos.addAll(nextDecoratorErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating plan item.",
                    ex);
        }
		return validationResultInfos;
	}

	@Override
	public List<ValidationResultInfo> validatePlanItemSet(
			String validationType, PlanItemSetInfo planItemSetInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<ValidationResultInfo> errors = null;
		try {
			errors = validateInfo(validator, validationType,
					planItemSetInfo, context);

			List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator()
					.validatePlanItemSet(validationType, planItemSetInfo,
							context);

			if (null != nextDecoratorErrors) {
				errors.addAll(nextDecoratorErrors);
			}
		} catch (DoesNotExistException ex) {
			throw new OperationFailedException(
					"Error validating plan item set.", ex);
		}
		return errors;
	}

	/**
	 * Data dictionary validation for LearningPlanInfo.
	 */
	private List<ValidationResultInfo> fullValidation(LearningPlanInfo learningPlanInfo,
			ContextInfo context) throws DataValidationErrorException,
			OperationFailedException, InvalidParameterException,
			MissingParameterException {

		if (learningPlanInfo == null) {
			throw new MissingParameterException("learningPlanInfo was null.");
		}

        List<ValidationResultInfo> errors = null;
		try {
			errors = this.validateLearningPlan(
					DataDictionaryValidator.ValidationType.FULL_VALIDATION
							.toString(), learningPlanInfo, context);
			if (checkForErrors(errors)) {
				throw new DataValidationErrorException(
						"Error(s) validating learning plan.", errors);
			}
		} catch (DoesNotExistException ex) {
			throw new OperationFailedException(
					"Error validating learning plan.", ex);
		}
        return errors;
	}

	/**
	 * Data dictionary validation for PlanItemInfo.
	 */
	private void fullValidation(PlanItemInfo planItemInfo, ContextInfo context)
			throws DataValidationErrorException, OperationFailedException,
			InvalidParameterException, MissingParameterException,
			AlreadyExistsException {

		if (planItemInfo == null) {
			throw new MissingParameterException("planItemInfo was null.");
		}

        List<ValidationResultInfo> errors = null;
		try {
			errors = this.validatePlanItem(
					DataDictionaryValidator.ValidationType.FULL_VALIDATION
							.toString(), planItemInfo, context);
			if (checkForErrors(errors)) {
				throw new DataValidationErrorException(
						"Error(s) validating plan item.", errors);
			}
		} catch (DoesNotExistException ex) {
			throw new OperationFailedException("Error validating plan item.",
					ex);
		}
	}

	@Override
	public LearningPlanInfo updateLearningPlan(String learningPlanId,
			LearningPlanInfo learningPlan, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DoesNotExistException {
		fullValidation(learningPlan, context);
		return getNextDecorator().updateLearningPlan(learningPlanId,
				learningPlan, context);
	}

	@Override
	public PlanItemInfo updatePlanItem(String planItemId,
			PlanItemInfo planItem, ContextInfo context)
			throws DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			AlreadyExistsException {

		// Since this is an update we can ignore AlreadyExistsExceptions. That
		// is the last validation which is performed.
		try {
			fullValidation(planItem, context);
		} catch (AlreadyExistsException aee) {
			//
		}
		return getNextDecorator().updatePlanItem(planItemId, planItem, context);
	}

	@Override
	public StatusInfo deleteLearningPlan(String learningPlanId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return getNextDecorator().deleteLearningPlan(learningPlanId, context);
	}

	@Override
	public StatusInfo deletePlanItem(String planItemId, ContextInfo context)
			throws OperationFailedException, InvalidParameterException,
			MissingParameterException, DoesNotExistException,
			PermissionDeniedException {
		return getNextDecorator().deletePlanItem(planItemId, context);
	}

    /**
     * @throws AlreadyExistsException
     *             If the plan item is a duplicate.
     */
    private void checkPlanItemDuplicate(PlanItemInfo planItem) throws AlreadyExistsException {

        String planItemId = planItem.getLearningPlanId();
        String courseId = planItem.getRefObjectId();
        AcademicPlanServiceConstants.ItemCategory category = planItem.getCategory();

        /**
         * See if a duplicate item exits in the plan. If the type is wishlist
         * then only the course id has to match to make it a duplicate. If the
         * type is planned course then the ATP must match as well.
         */
        List<PlanItemEntity> planItems = this.planItemDao.getLearningPlanItems(planItemId, category);
        for (PlanItemEntity p : planItems) {
            if (p.getRefObjectId().equals(courseId)) {
                if (category.equals(AcademicPlanServiceConstants.ItemCategory.PLANNED)
                        || category.equals(AcademicPlanServiceConstants.ItemCategory.BACKUP)
                        || category.equals(AcademicPlanServiceConstants.ItemCategory.CART)) {
                    for (String atpId : planItem.getPlanPeriods()) {
                        if (p.getPlanPeriods().contains(atpId)) {
                            throw new AlreadyExistsException(String.format(
                                    "A plan item for plan [%s], course id [%s], and term [%s] already exists.", p
                                    .getLearningPlan().getId(), courseId, atpId));
                        }
                    }
                } else {
                    throw new AlreadyExistsException(String.format(
                            "A plan item for plan [%s] and course id [%s] already exists.",
                            p.getLearningPlan().getId(), courseId));
                }
            }
        }
    }

    private ValidationResultInfo makeValidationResultInfo(String errorMessage, String element,
                                                          ValidationResult.ErrorLevel errorLevel) {
        ValidationResultInfo vri = new ValidationResultInfo();
        vri.setError(errorMessage);
        vri.setElement(element);
        vri.setLevel(errorLevel);
        return vri;
    }

    private boolean isValidTerm(String atpId) {
        try {
            return KsapFrameworkServiceLocator.getTermHelper().getTerm(atpId) != null;
        } catch (Exception e) {
            throw new RuntimeException("Query to ATP service failed.", e);
        }
    }

    /**
     * Check for validation errors in given list list
     * @param errors
     * @return
     */
    private static boolean checkForErrors(List<ValidationResultInfo> errors) {
        if (errors != null && !errors.isEmpty()) {
            for (ValidationResultInfo error : errors) {
                if (error.isError()) {
                    return true;
                }
            }
        }
        return false;
    }

}
