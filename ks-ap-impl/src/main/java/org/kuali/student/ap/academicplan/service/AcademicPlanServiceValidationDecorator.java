package org.kuali.student.ap.academicplan.service;

import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
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
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.lum.course.infc.Course;

import javax.jws.WebParam;
import java.util.List;

public class AcademicPlanServiceValidationDecorator extends
		AcademicPlanServiceDecorator implements HoldsValidator {


	private DataDictionaryValidator validator;

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
			ContextInfo context)
            throws DoesNotExistException,
                   InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
		return getNextDecorator().getLearningPlan(learningPlanId, context);
	}

    @Override
    public List<LearningPlanInfo> getLearningPlansByIds(@WebParam(name = "learningPlanIds") List<String> learningPlanIds, @WebParam(name = "contextInfo") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLearningPlansByIds(learningPlanIds, context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsByIds(@WebParam(name = "planItemIds") List<String> planItemIds, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
        return getNextDecorator().getPlanItemsByIds(planItemIds, context);
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlan(String learningPlanId,
			ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
		return getNextDecorator().getPlanItemsInPlan(learningPlanId, context);
	}

	@Override
    public List<PlanItemInfo> getPlanItemsInPlanByRefObjectIdByRefObjectType(
			String learningPlanId, String refObjectId, String refObjectType,
			ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
		return getNextDecorator()
				.getPlanItemsInPlanByRefObjectIdByRefObjectType(learningPlanId,
						refObjectId, refObjectType, context);
	}

    @Override
    public List<LearningPlanInfo> getLearningPlansForStudentByType(
			String studentId, String planTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException {
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
            ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		fullValidation(planItem, context);

        try {
            KsapFrameworkServiceLocator.getTypeService().getType(planItem.getTypeKey(), context);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException(String.format("Unknown plan item type id [%s].", planItem.getTypeKey()),e);
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

		List<ValidationResultInfo> errors;
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
			throw new OperationFailedException("Error validating learning plan.", ex);
		} catch (PermissionDeniedException e) {
            throw new OperationFailedException(
                    "Permission denied error while validating learning plan.", e);
        }
        return errors;
	}

	@Override
    public List<ValidationResultInfo> validatePlanItem(String validationType,
			PlanItemInfo planItemInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {

        List<ValidationResultInfo> validationResultInfos = validateInfo(validator, validationType,
            planItemInfo, context);

        //Validate learningPlanId is not null
        if (planItemInfo.getLearningPlanId()==null) {
            validationResultInfos.add(makeValidationResultInfo(
                    String.format("PlanId is null on planItem with ID [%s].", planItemInfo.getId()),
                    "id", ValidationResult.ErrorLevel.ERROR));
        }
        if (planItemInfo.getRefObjectType().equals(PlanConstants.COURSE_TYPE)) {
            Course currentVersionOfCourseByVersionIndependentId=null;
            // Validate that the course exists if course object
            try {
                currentVersionOfCourseByVersionIndependentId = KsapFrameworkServiceLocator.getCourseHelper()
                        .getCurrentVersionOfCourseByVersionIndependentId(
                                planItemInfo.getRefObjectId());
                if (currentVersionOfCourseByVersionIndependentId == null) {
                    validationResultInfos.add(makeValidationResultInfo(
                        String.format("Could not find course with ID [%s].", planItemInfo.getRefObjectId()),
                        "refObjectId", ValidationResult.ErrorLevel.ERROR));
                }
            } catch (RuntimeException e) {
                validationResultInfos.add(makeValidationResultInfo(
                        String.format("Could not find course with ID [%s].", planItemInfo.getRefObjectId()),
                        "refObjectId", ValidationResult.ErrorLevel.ERROR));
            }

            //Validate that the user is not already registered for this course
            if (planItemInfo.getLearningPlanId()!=null) {
                String studentId = this.getLearningPlan(planItemInfo.getLearningPlanId(),context).getStudentId();
                List<CourseRegistrationInfo> regList;
                for (String termId : planItemInfo.getPlanTermIds() ) {
                    try {
                        regList =KsapFrameworkServiceLocator.getCourseRegistrationService()
                            .getCourseRegistrationsByStudentAndTerm(studentId,termId, context);
                    } catch (Exception e) {
                        throw new OperationFailedException(
                                String.format("Unexpected error retrieving registration groups for course offering [%s] and " +
                                        "student [%s]: %s  ",
                                        (currentVersionOfCourseByVersionIndependentId != null ? currentVersionOfCourseByVersionIndependentId.getId() : null),studentId,e.getMessage()),e);
                    }
                    for (CourseRegistrationInfo registration : regList) {
                        CourseOfferingInfo offering = KsapFrameworkServiceLocator.getCourseOfferingService().getCourseOffering
                                (registration.getCourseOfferingId(), context);
                        if (offering==null) {
                            throw new OperationFailedException(
                                    String.format("Unexpected null returned while retrieving course offering [%s]  " +
                                            "student [%s] registration [%s]  ",
                                           registration.getCourseOfferingId(),studentId,registration.getId()));
                        }
                        Course course = KsapFrameworkServiceLocator.getCourseService().getCourse(offering.getCourseId(),context);
                        if (planItemInfo.getRefObjectId().equals(course.getVersion().getVersionIndId())) {
                            validationResultInfos.add(makeValidationResultInfo(
                                    String.format("Already registered for course [%s], " +
                                            "registration group [%s].",course.getCode(),
                                            currentVersionOfCourseByVersionIndependentId.getId(),
                                            registration.getRegistrationGroupId()),
                                    "refObjectId", ValidationResult.ErrorLevel.ERROR));
                        }
                    }
                }
            }
        } else if (planItemInfo.getRefObjectType().equals(PlanConstants.REG_GROUP_TYPE)) {
            // if reg group object type then validate that the reg group exists
            try {
                if (KsapFrameworkServiceLocator.getCourseOfferingService().getRegistrationGroup(
                                planItemInfo.getRefObjectId(),context)== null) {
                    validationResultInfos.add(makeValidationResultInfo(
                            String.format("Could not find registration group with ID [%s].",
                                    planItemInfo.getRefObjectId()),
                            "refObjectId", ValidationResult.ErrorLevel.ERROR));
                }
            } catch (RuntimeException e) {
                validationResultInfos.add(makeValidationResultInfo(
                        String.format("Could not find registration group with ID [%s].", planItemInfo.getRefObjectId()),
                        "refObjectId", ValidationResult.ErrorLevel.ERROR));
            }
        }

        //  Make sure a plan term exists if category is planned course.
        //note: the DD validation will catch this null & report an error for it...
        //      BIT we just need to avoid this block when it is null to avoid a NP exception
        if (planItemInfo.getCategory()!=null) {
            if (planItemInfo.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.PLANNED)
                    || planItemInfo.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.BACKUP)) {
                if (planItemInfo.getPlanTermIds() == null || planItemInfo.getPlanTermIds().size() == 0) {
                    validationResultInfos.add(makeValidationResultInfo(
                        String.format("Plan Item category was [%s], but no plan terms were defined.",
                                planItemInfo.getCategory()), "category", ValidationResult.ErrorLevel.ERROR));
                } else {
                    //  Make sure the plan terms are valid. Note: There should never be more than one item in the
                    // collection.
                    for (String atpId : planItemInfo.getPlanTermIds()) {
                        boolean valid;
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

    /**
	 * Data dictionary & Type validation for LearningPlanInfo.
	 */
	private List<ValidationResultInfo> fullValidation(LearningPlanInfo learningPlanInfo,
			ContextInfo context) throws DataValidationErrorException,
			OperationFailedException, InvalidParameterException,
			MissingParameterException {

		if (learningPlanInfo == null) {
			throw new MissingParameterException("learningPlanInfo was null.");
		}

        String refUri = AcademicPlanServiceConstants.REF_OBJECT_URI_ACADEMIC_PLAN_INFO;
        String errMsg = String.format("Invalid type [%s] for ref-uri [%s].",
                learningPlanInfo.getTypeKey(), refUri);
        try {
             List<ValidationResultInfo> resultInfos = ValidationUtils.validateTypeKey(learningPlanInfo.getTypeKey(),
                    refUri,KsapFrameworkServiceLocator.getTypeService(), context);
            if (checkForErrors(resultInfos)) {
                throw new InvalidParameterException(errMsg);
            }
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException(errMsg,e);
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException(String.format("Error validating learning plan type[%s].",
                    learningPlanInfo.getTypeKey()), e);
        }

		try {
            List<ValidationResultInfo> errors = this.validateLearningPlan(DataDictionaryValidator.ValidationType.FULL_VALIDATION
							.toString(), learningPlanInfo, context);
			if (checkForErrors(errors)) {
				throw new DataValidationErrorException(
						"Error(s) validating learning plan.", errors);
			}
            return errors;
		} catch (DoesNotExistException ex) {
			throw new OperationFailedException(
					"Error validating learning plan.", ex);
		}
	}

	/**
	 * Data dictionary validation for PlanItemInfo.
	 */
	private void fullValidation(PlanItemInfo planItemInfo, ContextInfo context)
            throws DataValidationErrorException, OperationFailedException,
                   InvalidParameterException, MissingParameterException,
                   AlreadyExistsException, PermissionDeniedException {

		if (planItemInfo == null) {
			throw new MissingParameterException("planItemInfo was null.");
		}

        //Validate Plan Item Type
        String refUri = AcademicPlanServiceConstants.REF_OBJECT_URI_ACADEMIC_PLAN_ITEM_INFO;
        String errMsg = String.format("Invalid type [%s] for ref-uri [%s].",planItemInfo.getTypeKey(), refUri);
        try {
            List<ValidationResultInfo> results = ValidationUtils.validateTypeKey(planItemInfo.getTypeKey(),
                    refUri,KsapFrameworkServiceLocator.getTypeService(), context);
            if (checkForErrors(results)) {
                throw new InvalidParameterException(errMsg);
            }
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException(errMsg,e);
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException("Error validating plan item type.", e);
        }

        //Validate Ref Object Type
        errMsg = String.format("Invalid item reference object type [%s] for item type [%s].",
                planItemInfo.getRefObjectType(), planItemInfo.getTypeKey());
        try {
            List<ValidationResultInfo> results = ValidationUtils.validateGroupMbrTypeKey(planItemInfo.getTypeKey(),
                    planItemInfo.getRefObjectType(),KsapFrameworkServiceLocator.getTypeService(), context);
            if (checkForErrors(results)) {
                throw new InvalidParameterException(errMsg);
            }
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException(errMsg,e);
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException("Error validating item reference object type.", e);
        }

        try {
            List<ValidationResultInfo> errors = this.validatePlanItem(
					DataDictionaryValidator.ValidationType.FULL_VALIDATION
							.toString(), planItemInfo, context);
			if (checkForErrors(errors)) {
				throw new DataValidationErrorException("Error(s) validating plan item.", errors);
			}
		} catch (DoesNotExistException ex) {
			throw new OperationFailedException("Error validating plan item.",
					ex);
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
        if (planItemInfo.getLearningPlanId()!=null) {
            checkPlanItemDuplicate(planItemInfo,context);
        }


    }

	@Override
    public LearningPlanInfo updateLearningPlan(String learningPlanId,
			LearningPlanInfo learningPlan, ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, VersionMismatchException {
        if (!learningPlanId.equals(learningPlan.getId())) {
            throw new InvalidParameterException(learningPlanId + " does not match the id on the object " + learningPlan.getId());
        }
        fullValidation(learningPlan, context);
		return getNextDecorator().updateLearningPlan(learningPlanId,
				learningPlan, context);
	}

	@Override
    public PlanItemInfo updatePlanItem(String planItemId,
			PlanItemInfo planItem, ContextInfo context)
    throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {
        if (!planItemId.equals(planItem.getId())) {
            throw new InvalidParameterException(planItemId + " does not match the id on the object " + planItem.getId());
        }
        // Since this is an update we can ignore AlreadyExistsExceptions. That
		// is the last validation which is performed.
		try {
			fullValidation(planItem, context);
		} catch (AlreadyExistsException aee) {
			//
        } catch (DataValidationErrorException e) {
            boolean rethrowIt=true;
            if (e.getValidationResults().size()==1) {
                ValidationResultInfo validationResult = KSCollectionUtils.getRequiredZeroElement(
                        e.getValidationResults());
                if (validationResult.getMessage()!=null
                        && validationResult.getMessage().startsWith("Already registered for course")) {
                    rethrowIt=false;  //ignore already registered validation msg when updating a plan item
                }
            }
            if (rethrowIt) throw e;
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
    private void checkPlanItemDuplicate(PlanItemInfo planItem, ContextInfo context) throws AlreadyExistsException {

        String planItemId = planItem.getLearningPlanId();
        String courseId = planItem.getRefObjectId();
        AcademicPlanServiceConstants.ItemCategory category = planItem.getCategory();

        /**
         * See if a duplicate item exits in the plan. If the type is wishlist
         * then only the course id has to match to make it a duplicate. If the
         * type is planned course then the ATP must match as well.
         */
        List<PlanItemInfo> planItems = null;
        try {
            planItems = this.getPlanItemsInPlanByRefObjectIdByRefObjectType(planItem.getLearningPlanId(),courseId,
                    planItem.getRefObjectType(), context);
        } catch (Exception e) {
            throw new RuntimeException("unexpected exception: "+e.getMessage(),e);
        }
        for (PlanItemInfo p : planItems) {
            if ((AcademicPlanServiceConstants.ItemCategory.PLANNED.equals(category)
                    || AcademicPlanServiceConstants.ItemCategory.BACKUP.equals(category))
                  && (AcademicPlanServiceConstants.ItemCategory.PLANNED.equals(p.getCategory())
                    || AcademicPlanServiceConstants.ItemCategory.BACKUP.equals(p.getCategory()))) {
                for (String atpId : planItem.getPlanTermIds()) {
                    if (p.getPlanTermIds().contains(atpId)) {
                        throw new AlreadyExistsException(String.format(
                                "A planned/backup item for plan [%s], course id [%s], and term [%s] already exists.",
                                p.getLearningPlanId(), courseId, atpId));
                    }
                }
            } else if (AcademicPlanServiceConstants.ItemCategory.WISHLIST.equals(category)
                    && AcademicPlanServiceConstants.ItemCategory.WISHLIST.equals(p.getCategory())) {
                throw new AlreadyExistsException(String.format(
                        "A bookmark item for plan [%s] and course id [%s] already exists.",
                        p.getLearningPlanId(), courseId));
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
