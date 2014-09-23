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

package org.kuali.student.ap.framework.context.support;

import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants.ItemCategory;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.dto.TypedObjectReferenceInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.Placeholder;
import org.kuali.student.ap.academicplan.infc.PlaceholderInstance;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.academicplan.infc.TypedObjectReference;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.PlanHelper;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.ap.planner.PlannerItem;
import org.kuali.student.ap.planner.PlannerTerm;
import org.kuali.student.ap.planner.PlannerTermNote;
import org.kuali.student.ap.planner.RegCodeListPropertyEditor;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.course.infc.Course;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Default implementation of the PlanHelper
 */
public class DefaultPlanHelper implements PlanHelper {

    private static final long serialVersionUID = 3987763030779110660L;
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultPlanHelper.class);

	/**
	 * Retrieves the first plan item of type PlanConstants.Learning_Plan_Type_Plan for the student as the default plan.
	 * 
	 * @see org.kuali.student.ap.framework.context.PlanHelper#getDefaultLearningPlan()
	 * 
	 * @return A single learning plan.
	 */
	@Override
	public LearningPlanInfo getDefaultLearningPlan() {
		LearningPlanInfo defaultPlan = null;
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper().getStudentId();

		List<LearningPlanInfo> learningPlans = null;
		try {
			learningPlans = KsapFrameworkServiceLocator.getAcademicPlanService().getLearningPlansForStudentByType(
					studentId, PlanConstants.LEARNING_PLAN_TYPE_PLAN,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (Exception e) {
			throw new RuntimeException(String.format("Could not fetch plan for user [%s].", studentId), e);
		}
        if(learningPlans==null || learningPlans.isEmpty()){
            LearningPlanInfo newPlan = new LearningPlanInfo();
            newPlan.setStudentId(studentId);
            newPlan.setStateKey(AcademicPlanServiceConstants.LEARNING_PLAN_ACTIVE_STATE_KEY);
            newPlan.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN);
            newPlan.setShared(true);
            RichTextInfo descr = new RichTextInfo("Default Plan For "+KsapFrameworkServiceLocator.getUserSessionHelper().getStudentName(),"Default Plan For "+KsapFrameworkServiceLocator.getUserSessionHelper().getStudentName());
            newPlan.setDescr(descr);
            try{
                defaultPlan = KsapFrameworkServiceLocator.getAcademicPlanService().createLearningPlan(newPlan,KsapFrameworkServiceLocator.getContext().getContextInfo());
            } catch (InvalidParameterException e) {
                throw new RuntimeException(String.format("Could not fetch plan for user [%s].", studentId), e);
            } catch (PermissionDeniedException e) {
                throw new RuntimeException(String.format("Could not fetch plan for user [%s].", studentId), e);
            } catch (OperationFailedException e) {
                //Check for unique-key (studentId) violation... (key added to prevent > 1 plan per student)
                if (e.getMessage().matches("org\\.hibernate\\.exception\\.ConstraintViolationException.*")) {
                    //now we can just try to retrieve plan...it should already exist
                    try {
                        learningPlans = KsapFrameworkServiceLocator.getAcademicPlanService().getLearningPlansForStudentByType(
                                studentId, PlanConstants.LEARNING_PLAN_TYPE_PLAN,
                                KsapFrameworkServiceLocator.getContext().getContextInfo());
                        if (learningPlans!=null && !learningPlans.isEmpty()) {
                            defaultPlan =  KSCollectionUtils.getRequiredZeroElement(learningPlans);
                        } else {
                            throw new RuntimeException(String.format("Could not fetch plan for user [%s]: not found",
                                    studentId));
                        }
                    } catch (Exception e2) {
                        throw new RuntimeException(String.format("Could not fetch plan for user [%s].", studentId),
                                e2);
                    }
                } else {
                    throw new RuntimeException(String.format("Could not fetch plan for user [%s].", studentId), e);
                }
            } catch (AlreadyExistsException e) {
                throw new RuntimeException(String.format("Could not fetch plan for user [%s].", studentId), e);
            } catch (MissingParameterException e) {
                throw new RuntimeException(String.format("Could not fetch plan for user [%s].", studentId), e);
            } catch (DataValidationErrorException e) {
                throw new RuntimeException(String.format("Could not fetch plan for user [%s].", studentId), e);
            }
        }else{
            try {
                defaultPlan =  KSCollectionUtils.getRequiredZeroElement(learningPlans);
            }catch (OperationFailedException e){
                throw new RuntimeException(String.format("Could not fetch plan for user [%s].", studentId), e);
            }
        }

		return defaultPlan;
	}

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#setDefaultLearningPlan(String)
     */
    @Override
    public void setDefaultLearningPlan(String learningPlanId) {
        throw new UnsupportedOperationException("Unsupported in Default KSAP implementation");
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#getLearningPlan(String)
     */
    @Override
    public LearningPlanInfo getLearningPlan(String learningPlanId) {
        try {
            LearningPlanInfo learningPlan = KsapFrameworkServiceLocator.getAcademicPlanService().getLearningPlan(
                            learningPlanId,KsapFrameworkServiceLocator.getContext().getContextInfo());
            String lpStudent = learningPlan.getStudentId();
            if (lpStudent == null || !lpStudent.equals(KsapFrameworkServiceLocator.getUserSessionHelper()
                    .getStudentId())){
                return null;
            } else {
                return learningPlan;
            }
        } catch (DoesNotExistException e) {
            LOG.warn(String.format("Learning plan %s does not exist",learningPlanId), e);
            return null;
        } catch (InvalidParameterException e) {
            LOG.warn(String.format("Invalid learning plan ID %s",learningPlanId), e);
            return null;
        } catch (MissingParameterException e) {
            throw new IllegalStateException("LP lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("LP lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("LP lookup permission failure", e);
        }
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#getPlanItems(String)
     */
    @Override
    public List<PlanItem> getPlanItems(String planId) {
        try {
            return new ArrayList<PlanItem>(KsapFrameworkServiceLocator.getAcademicPlanService().getPlanItemsInPlan(
                            planId, KsapFrameworkServiceLocator.getContext().getContextInfo()));
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("LP lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("LP lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("LP lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("LP lookup permission failure", e);
        }
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#getPlanItem(String)
     */
    @Override
    public PlanItem getPlanItem(String planItemId) {
        try {
            PlanItemInfo planItem = KsapFrameworkServiceLocator.getAcademicPlanService().getPlanItem(planItemId,
                            KsapFrameworkServiceLocator.getContext().getContextInfo());
            if (planItem != null) {
                String studentId = KsapFrameworkServiceLocator.getUserSessionHelper().getStudentId();
                LearningPlanInfo lp = KsapFrameworkServiceLocator.getAcademicPlanService().getLearningPlan(
                        planItem.getLearningPlanId(),KsapFrameworkServiceLocator.getContext().getContextInfo());
                if (studentId == null || !studentId.equals(lp.getStudentId())){
                    return null;
                }
            }
            return planItem;
        } catch (DoesNotExistException e) {
            LOG.warn(String.format("Plan item %s does not exist", planItemId),e);
            return null;
        } catch (InvalidParameterException e) {
            LOG.warn(String.format("Invalid plan item ID %s", planItemId), e);
            return null;
        } catch (MissingParameterException e) {
            throw new IllegalStateException("LP lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("LP lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("LP lookup permission failure", e);
        }
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#addPlanItem(String, org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants.ItemCategory, String, java.math.BigDecimal, java.util.List, org.kuali.student.ap.academicplan.infc.TypedObjectReference, java.util.List)
     */
    @Override
    public PlanItem addPlanItem(String learningPlanId, ItemCategory category, String descr, BigDecimal credits,
                                List<String> termIds,TypedObjectReference ref, List<AttributeInfo> attributes)
            throws AlreadyExistsException, DataValidationErrorException {
        PlanHelper planHelper = KsapFrameworkServiceLocator.getPlanHelper();
        PlanItem wishlistPlanItem = null;

        // Get list of existing plan items
        List<PlanItem> existingPlanItems = planHelper.getPlanItems(learningPlanId);
        if (existingPlanItems != null && ref != null){
            for (PlanItem existingPlanItem : existingPlanItems) {
                if (!planHelper.isSame(existingPlanItem, ref) || wishlistPlanItem != null){
                    continue;
                }

                // If item has no term then record it
                if (ItemCategory.WISHLIST.equals(existingPlanItem.getCategory())){
                    wishlistPlanItem = existingPlanItem;
                }
            }
        }

        // Fill in basic information
        PlanItemInfo planItemInfo;
        planItemInfo = new PlanItemInfo();
        planItemInfo.setCategory(category);
        planItemInfo.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
        planItemInfo.setStateKey(PlanConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);
        planItemInfo.setLearningPlanId(learningPlanId);

        // Fill in course information
        planItemInfo.setRefObjectId(ref.getRefObjectId());
        planItemInfo.setRefObjectType(ref.getRefObjectType());
        planItemInfo.setPlanTermIds(new ArrayList<String>(termIds));

        if (StringUtils.hasText(descr)) {
            RichTextInfo rt = new RichTextInfo();
            rt.setPlain(descr);
            rt.setFormatted(descr);
            planItemInfo.setDescr(rt);
        } else{
            planItemInfo.setDescr(null);
        }
        planItemInfo.setCredit(credits);

        planItemInfo.getAttributes().addAll(attributes);

        try {
            // If already in wishlist delete existing object
            if(wishlistPlanItem != null){
                KsapFrameworkServiceLocator.getAcademicPlanService().deletePlanItem(wishlistPlanItem.getId(),
                        KsapFrameworkServiceLocator.getContext().getContextInfo());
            }

            // If creating new add it to the database
            planItemInfo = KsapFrameworkServiceLocator.getAcademicPlanService().createPlanItem(planItemInfo,
                            KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (AlreadyExistsException e) {
            LOG.warn("Reference " + ref.getRefObjectType() + " "+ ref.getRefObjectId() + " is already planned", e);
            throw e;
        } catch (DataValidationErrorException e) {
            throw e;
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("LP service failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("LP service failure", e);
        }

        return planItemInfo;
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#updatePlanItem(org.kuali.student.ap.academicplan.infc.PlanItem)
     */
    @Override
    public PlanItemInfo updatePlanItem(PlanItem item) {
        // Save updated plan item
        try {
            return KsapFrameworkServiceLocator.getAcademicPlanService().updatePlanItem(item.getId(),
                    new PlanItemInfo(item), KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DataValidationErrorException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("LP service failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("LP service failure", e);
        } catch (VersionMismatchException e) {
            // TODO: ksap-1012 handle VersionMismatchException appropriately
            throw new IllegalStateException("LP service failure", e);
        }
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#removePlanItem(String)
     */
    @Override
    public void removePlanItem(String planItemId) {
        // Delete plan item from the database
        try {
            KsapFrameworkServiceLocator.getAcademicPlanService().deletePlanItem(planItemId,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("LP service failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("LP service failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("LP service failure", e);
        }
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#editTermNote(String, String, String)
     */
    @Override
    public void editTermNote(String learningPlanId, String termId,String termNote) {
        // Retrieve the list of term notes for this plan.
        CommentService commentService = KsapFrameworkServiceLocator.getCommentService();
        List<CommentInfo> commentInfos;
        try {
            commentInfos = commentService.getCommentsByRefObject(learningPlanId,
                    PlanConstants.TERM_NOTE_COMMENT_TYPE, KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Comment lookup failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Comment lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Comment lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("Comment lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("Comment lookup failure", e);
        }

        // Create replacement rich text with new term note
        RichTextInfo newNote = new RichTextInfo();
        newNote.setFormatted(termNote);
        newNote.setPlain(termNote);

        // Search for existing term note for that term.
        boolean found = false;
        for (CommentInfo comment : commentInfos) {
            String commentAtpId = comment.getAttributeValue(PlanConstants.TERM_NOTE_COMMENT_ATTRIBUTE_ATPID);
            if (termId.equals(commentAtpId)) {
                found = true;
                comment.setCommentText(newNote);
                try {
                    if (StringUtils.isEmpty(termNote)) {
                        commentService.deleteComment(comment.getId(),
                                KsapFrameworkServiceLocator.getContext()
                                        .getContextInfo());
                    } else {
                        // If existing note is found replace the rich text and update it in the database.
                        commentService.updateComment(comment.getId(), comment,KsapFrameworkServiceLocator.getContext()
                                        .getContextInfo());
                    }
                } catch (DataValidationErrorException e) {
                    throw new IllegalArgumentException("Comment lookup failure", e);
                } catch (DoesNotExistException e) {
                    throw new IllegalArgumentException("Comment lookup failure", e);
                } catch (InvalidParameterException e) {
                    throw new IllegalArgumentException("Comment lookup failure", e);
                } catch (MissingParameterException e) {
                    throw new IllegalArgumentException("Comment lookup failure", e);
                } catch (OperationFailedException e) {
                    throw new IllegalStateException("Comment lookup failure", e);
                } catch (PermissionDeniedException e) {
                    throw new IllegalStateException("Comment lookup failure", e);
                } catch (ReadOnlyException e) {
                    throw new IllegalStateException("Comment lookup failure", e);
                } catch (VersionMismatchException e) {
                    throw new IllegalStateException("Comment lookup failure", e);
                }
                break;
            }
        }

        // If no existing note is found create new term note and save it to the
        // database
        if (!found && !StringUtils.isEmpty(termNote)) {
            CommentInfo newComment = new CommentInfo();
            newComment.setCommentText(newNote);
            ContextInfo ctx = KsapFrameworkServiceLocator.getContext().getContextInfo();
            newComment.setEffectiveDate(ctx.getCurrentDate());
            newComment.setRefObjectId(learningPlanId);
            newComment.setRefObjectUri(PlanConstants.TERM_NOTE_COMMENT_TYPE);
            newComment.setTypeKey(PlanConstants.TERM_NOTE_COMMENT_TYPE);
            newComment.setStateKey("ACTIVE");
            AttributeInfo atpIdAttr = new AttributeInfo();
            atpIdAttr.setKey(PlanConstants.TERM_NOTE_COMMENT_ATTRIBUTE_ATPID);
            atpIdAttr.setValue(termId);
            newComment.getAttributes().add(atpIdAttr);
            try {
                commentService.createComment(newComment.getRefObjectId(),newComment.getRefObjectUri(),
                        PlanConstants.TERM_NOTE_COMMENT_TYPE, newComment,KsapFrameworkServiceLocator.getContext()
                                .getContextInfo());
            } catch (DataValidationErrorException e) {
                throw new IllegalArgumentException("Comment lookup failure", e);
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("Comment lookup failure", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("Comment lookup failure", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("Comment lookup failure", e);
            } catch (OperationFailedException e) {
                throw new IllegalStateException("Comment lookup failure", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalStateException("Comment lookup failure", e);
            } catch (ReadOnlyException e) {
                throw new IllegalStateException("Comment lookup failure", e);
            }
        }

    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#getCompletedRecords()
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedRecords() {
        try {
            return KsapFrameworkServiceLocator.getAcademicRecordService().getCompletedCourseRecords(
                    KsapFrameworkServiceLocator.getUserSessionHelper().getStudentId(),
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("AR lookup failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("AR lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("AR lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("AR lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("AR lookup failure", e);
        }
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#getPlannerFirstTermId()
     */
    @Override
    public String getPlannerFirstTermId() {
        return KsapFrameworkServiceLocator.getTermHelper().getFirstTermIdOfCurrentAcademicYear();
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#getPlannerCalendarTerms(org.kuali.student.r2.core.acal.infc.Term)
     */
    @Override
    public List<Term> getPlannerCalendarTerms(Term startTerm) {
        Calendar c = Calendar.getInstance();
        Date startDate = startTerm.getStartDate();

        // Check that start term is before the current date, in not use current date as start term
        if(c.getTime().before(startTerm.getStartDate())){
            startDate=c.getTime();
        }

        int futureYears = Integer.parseInt(ConfigContext.getCurrentContextConfig().getProperty( "ks.ap.planner.future.years"));
        c.add(Calendar.YEAR, futureYears);
        List<Term> calendarTerms = KsapFrameworkServiceLocator.getTermHelper().getTermsByDateRange(startDate,c.getTime());
        calendarTerms = KsapFrameworkServiceLocator.getTermHelper().sortTermsByStartDate(calendarTerms,true);
        if(calendarTerms.isEmpty()){
            throw new RuntimeException("No Valid Terms Found for Calendar "+startDate.toString() +" to " + c.getTime().toString());
        }
        Term start = calendarTerms.get(0);
        Term end = calendarTerms.get(calendarTerms.size()-1);
        List<Term> startYear = KsapFrameworkServiceLocator.getTermHelper().getTermsInAcademicYear(new DefaultYearTerm(start.getId(),start.getTypeKey(),start.getStartDate().getYear()));
        List<Term> endYear= KsapFrameworkServiceLocator.getTermHelper().getTermsInAcademicYear(new DefaultYearTerm(end.getId(),end.getTypeKey(),end.getStartDate().getYear()));

        // Sorted in reverse order so terms are added in order.
        startYear = KsapFrameworkServiceLocator.getTermHelper().sortTermsByStartDate(startYear,false);

        endYear = KsapFrameworkServiceLocator.getTermHelper().sortTermsByStartDate(endYear,false);
        Collections.sort(endYear, new Comparator<Term>() {
            @Override
            public int compare(Term o1, Term o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });
        for(Term t : startYear){
            if(t.getStartDate().compareTo(start.getStartDate())<0){
                calendarTerms.add(0,t);
            }
        }
        for(Term t : endYear){
            if(t.getStartDate().compareTo(end.getStartDate())>0){
                calendarTerms.add(t);
            }
        }
        return calendarTerms;
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#loadStudentsPlanItemsForCourse(String)
     */
    @Override
    public List<PlanItem> loadStudentsPlanItemsForCourse(String courseVersionId) {
        String studentId = KsapFrameworkServiceLocator.getUserSessionHelper().getStudentId();
        if (studentId == null)
            return new ArrayList<PlanItem>();

        try {
            // Retrieve plan items for the student's default plan
            LearningPlanInfo learningPlan = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan();
            List<PlanItemInfo> planItems = KsapFrameworkServiceLocator.getAcademicPlanService().getPlanItemsInPlan(
                    learningPlan.getId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
            List<PlanItem> planItemsForCourse = new ArrayList<PlanItem>();

            // Filter plan items by the course
            for(PlanItem item : planItems){
                if(item.getRefObjectId().equals(courseVersionId)){
                    planItemsForCourse.add(new PlanItemInfo(item));
                }
            }

            return planItemsForCourse;
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("LP lookup failure ", e);
        } catch (MissingParameterException e) {
            throw new IllegalStateException("LP lookup failure ", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("LP lookup failure ", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("LP lookup permission failure ", e);
        }
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#loadStudentsPlanItemsForCourse(org.kuali.student.r2.lum.course.infc.Course)
     */
    @Override
    public List<PlanItem> loadStudentsPlanItemsForCourse(Course course) {
        return loadStudentsPlanItemsForCourse(course.getVersion().getVersionIndId());
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#isSame(org.kuali.student.ap.academicplan.infc.TypedObjectReference, org.kuali.student.ap.academicplan.infc.TypedObjectReference)
     */
    @Override
    public boolean isSame(TypedObjectReference ref1, TypedObjectReference ref2) {
        if (ref1 == null){
            return ref2 == null;
        }
        if (ref2 == null){
            return false;
        }

        String type1 = ref1.getRefObjectType();
        String type2 = ref2.getRefObjectType();
        if (type1 == null){
            return type2 == null;
        }
        if (!type1.equals(type2)){
            return false;
        }

        String id1 = ref1.getRefObjectId();
        String id2 = ref2.getRefObjectId();
        if (id1 == null){
            return id2 == null;
        }

        return id1.equals(id2);
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#isEncompassed(org.kuali.student.ap.academicplan.infc.TypedObjectReference, org.kuali.student.ap.academicplan.infc.TypedObjectReference)
     */
    @Override
    public boolean isEncompassed(TypedObjectReference inner,TypedObjectReference outer) {
        throw new UnsupportedOperationException("Unsupported in Default KSAP implementation");
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#getCourse(org.kuali.student.ap.academicplan.infc.TypedObjectReference)
     */
    @Override
    public Course getCourse(TypedObjectReference ref) {
        if (PlanConstants.COURSE_TYPE.equals(ref.getRefObjectType())){
            return KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourseByVersionIndependentId(
                    ref.getRefObjectId());
        }
        return null;
    }

    /**
     * Need to look into when implementing placeholders M3 work
     *
     * @see org.kuali.student.ap.framework.context.PlanHelper#getPlaceHolder(org.kuali.student.ap.academicplan.infc.TypedObjectReference)
     */
    @Override
    public Placeholder getPlaceHolder(TypedObjectReference ref) {
        throw new UnsupportedOperationException("Unsupported in Default KSAP implementation");
        /*
        if (ref.getRefObjectType().equals(PlanConstants.REF_TYPE_PLACEHOLDER)) {

            String placeholderId = ref.getRefObjectId();
            Placeholder placeholder;
            try {
                placeholder = KsapFrameworkServiceLocator.getDegreeMapService()
                        .getPlaceholder(
                                placeholderId,
                                KsapFrameworkServiceLocator.getContext()
                                        .getContextInfo());
                if (PlanConstants.PLACEHOLDER_ERROR.equals(placeholder
                        .getTypeKey()))
                    throw new IllegalArgumentException(
                            "Placeholder type is error.");
                return placeholder;
            } catch (DoesNotExistException e) {
                return null;
            } catch (InvalidParameterException e) {
                throw new RuntimeException(String.format(
                        "Could not fetch placeholder with id [%s].",
                        placeholderId), e);
            } catch (MissingParameterException e) {
                throw new RuntimeException(String.format(
                        "Could not fetch placeholder with id [%s].",
                        placeholderId), e);
            } catch (OperationFailedException e) {
                throw new RuntimeException(String.format(
                        "Could not fetch placeholder with id [%s].",
                        placeholderId), e);
            }

        }

        return null;
        */
    }

    /**
     * Need to look into when implementing placeholders M3 work
     *
     * @see org.kuali.student.ap.framework.context.PlanHelper#getPlaceHolderInstance(org.kuali.student.ap.academicplan.infc.TypedObjectReference)
     */
    @Override
    public PlaceholderInstance getPlaceHolderInstance(TypedObjectReference ref) {
        throw new UnsupportedOperationException("Unsupported in Default KSAP implementation");
        /*
        if (ref.getRefObjectType().equals(
                PlanConstants.REF_TYPE_PLACEHOLDER_INSTANCE)) {

            String pid = ref.getRefObjectId();
            PlaceholderInstance pi;

            try {
                pi = KsapFrameworkServiceLocator.getDegreeMapService()
                        .getPlaceholderInstance(
                                pid,
                                KsapFrameworkServiceLocator.getContext()
                                        .getContextInfo());
                return pi;
            } catch (DoesNotExistException e) {
                return null;
            } catch (InvalidParameterException e) {
                throw new RuntimeException(String.format(
                        "Could not fetch placeholder instance with id [%s].",
                        pid), e);
            } catch (MissingParameterException e) {
                throw new RuntimeException(String.format(
                        "Could not fetch placeholder instance with id [%s].",
                        pid), e);
            } catch (OperationFailedException e) {
                throw new RuntimeException(String.format(
                        "Could not fetch placeholder instance with id [%s].",
                        pid), e);
            }

        }

        return null;
        */
    }

    /**
     * Need to look into when implementing placeholders M3 work
     *
     * @see org.kuali.student.ap.framework.context.PlanHelper#getCourseIdsForPlaceHolder(org.kuali.student.ap.academicplan.infc.Placeholder)
     */
    @Override
    public Set<String> getCourseIdsForPlaceHolder(Placeholder ph) {
        throw new UnsupportedOperationException("Unsupported in Default KSAP implementation");
        /*
        PlaceholderResolver phr;

        if (PlanConstants.PLACEHOLDER_COURSE.equals(ph.getTypeKey())) {
            phr = new CoursePlaceholderResolver();
        } else if (PlanConstants.PLACEHOLDER_SEARCH.equals(ph.getTypeKey())) {
            phr = new SearchPlaceholderResolver();
        } else {
            throw new IllegalArgumentException("Placeholder type "
                    + ph.getTypeKey() + " not recognized.");
        }

        try {
            return phr.resolve(ph);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException(
                    "Missing requirement parameter for placeholder type "
                            + ph.getTypeKey());
        }
        */
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#createPlanningStatusMessages(java.util.List)
     */
    @Override
    public String createPlanningStatusMessages(List<PlanItem> planItems){
        List<String> plannedStatus = new ArrayList<String>();

        // Create message segments for each planned instance
        for(PlanItem item : planItems){
            StringBuilder message = new StringBuilder("<b>");
            switch (item.getCategory()){
                case PLANNED:
                    for(String termId : item.getPlanTermIds()){
                        message.append(KsapFrameworkServiceLocator.getTermHelper().getYearTerm(termId)
                                .getLongName()+ " ");
                    }
                    message.append("plan</b> on " + DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(item.getMeta().getUpdateTime()));
                    plannedStatus.add(message.toString());
                    break;
                case BACKUP:
                    for(String termId : item.getPlanTermIds()){
                        message.append(KsapFrameworkServiceLocator.getTermHelper().getYearTerm(termId)
                                .getLongName()+ " ");
                    }
                    message.append("backup</b> on " + DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(item.getMeta().getUpdateTime()));
                    plannedStatus.add(message.toString());
                    break;
                default:
                    break;
            }
        }

        // If not planned return empty
        if(plannedStatus.isEmpty()) return "";

        // Compile segments into a single planned summary message
        StringBuilder plannedMessages = new StringBuilder();
        plannedMessages.append("Added to ");
        for(int i=0;i<plannedStatus.size();i++){
            String message = plannedStatus.get(i);
            if(i==0){
                plannedMessages.append(message);
            }else{
                if(i == plannedStatus.size()-1){
                    plannedMessages.append(" and "+message);
                }else{
                    plannedMessages.append(", "+message);
                }
            }
        }

        return plannedMessages.toString();
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#createBookmarkStatusMessages(java.util.List)
     */
    @Override
    public String createBookmarkStatusMessages(List<PlanItem> planItems){
        for(PlanItem item : planItems){
            // Return message based on first bookmarked entry found (there should be only one).
            if(item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.WISHLIST)){
                return "<b>Bookmarked on</b> " + DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(item.getMeta().getUpdateTime());
            }
        }

        return "";
    }

    @Override
    public List<String> getTermIdsForPlanItems(List<PlanItem> planItems) {
        List<String> plannedTerms = new ArrayList<String>();
        for(PlanItem item : planItems){
            if(item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.PLANNED) ||
                    item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.BACKUP)){
                plannedTerms.addAll(item.getPlanTermIds());
            }
        }
        return plannedTerms;
    }

    /**
     * Assumes that the course id passed in is the clu id and translates it to the version independent id which is used
     * as the plan items refObjId
     * If plan item exist returns it
     * If plan item does not exist creates default version in term.
     *
     * @see PlanHelper#findCourseItem(String, String, String)
     */
    @Override
    public PlanItem findCourseItem(String courseId, String termId, String planId){

        SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSE_IND_VERSION_BY_CLU_ID_KEY);
        request.addParam(CourseSearchConstants.SearchParameters.CLU_ID,courseId);
        try {

            SearchResultInfo results = KsapFrameworkServiceLocator.getSearchService().search(request, KsapFrameworkServiceLocator.getContext().getContextInfo());
            if(results.getRows().isEmpty()){
                throw new IllegalArgumentException("No version id found for course with id " +courseId);
            }
            SearchResultRow row = KSCollectionUtils.getRequiredZeroElement(results.getRows());
            String versionId = KsapHelperUtil.getCellValue(row,CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID);
            List<PlanItem> courseItems = KsapFrameworkServiceLocator.getPlanHelper().loadStudentsPlanItemsForCourse(versionId);
            for(PlanItem item : courseItems){
                if(item.getPlanTermIds().contains(termId)){
                    return item;
                }
            }
            // Create the new plan item
            TypedObjectReference planItemRef = new TypedObjectReferenceInfo(PlanConstants.COURSE_TYPE,versionId);
            BigDecimal creditValue = null;
            List<String> terms = new ArrayList<String>();
            terms.add(termId);
            List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();

            // Save the new plan item to the database
            try{
                return KsapFrameworkServiceLocator.getPlanHelper().addPlanItem(planId,
                        AcademicPlanServiceConstants.ItemCategory.PLANNED, "", creditValue, terms, planItemRef,attributes);
            }catch (AlreadyExistsException e){
                throw new RuntimeException("Unable to create course item for reg group item", e);
            } catch (DataValidationErrorException e) {
                throw new IllegalArgumentException("LP service failure", e);
            }
        } catch (MissingParameterException e) {
            throw new RuntimeException("Unable to load course item", e);
        } catch (InvalidParameterException e) {
            throw new RuntimeException("Unable to load course item", e);
        } catch (OperationFailedException e) {
            throw new RuntimeException("Unable to load course item", e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException("Unable to load course item", e);
        }

    }

    @Override
    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#createPlannerItem(org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo)
     */
    public PlannerItem createPlannerItem(CourseRegistrationInfo registrationRecord) {
        PlannerItem newPlannerItem = new PlannerItem();
        newPlannerItem.setType(PlannerItem.COURSE_RECORD_ITEM);

        newPlannerItem.setUniqueId(UUID.randomUUID().toString());
        newPlannerItem.setTermId(registrationRecord.getTermId());
        newPlannerItem.setCategory(ItemCategory.REGISTERED);

        CourseOffering offering = null;
        try {
            offering = KsapFrameworkServiceLocator.getCourseOfferingService().getCourseOffering(registrationRecord.getCourseOfferingId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException|InvalidParameterException|MissingParameterException|OperationFailedException|PermissionDeniedException
                e) {
            throw new IllegalArgumentException("Course Offering Service failure", e);
        }

        newPlannerItem.setCourseCode(offering.getCourseOfferingCode());
        newPlannerItem.setCourseId(offering.getCourseId());
        newPlannerItem.setCourseTitle(offering.getCourseOfferingTitle());

        newPlannerItem.setMeta(registrationRecord.getMeta());

        try {
            if(registrationRecord.getCredits() == null){
                newPlannerItem.setMaxCredits(BigDecimal.ZERO);
                newPlannerItem.setMinCredits(BigDecimal.ZERO);
            }else{
                newPlannerItem.setMaxCredits(registrationRecord.getCredits().bigDecimalValue());
                newPlannerItem.setMinCredits(registrationRecord.getCredits().bigDecimalValue());
            }
        } catch (NumberFormatException e) {
            LOG.warn(String.format("Invalid credits in registration record %s", registrationRecord.getCredits()), e);
        }

        return newPlannerItem;
    }

    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#createPlannerItem(org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo)
     */
    public PlannerItem createPlannerItem(StudentCourseRecordInfo courseRecord){
        PlannerItem newPlannerItem = new PlannerItem();
        newPlannerItem.setType(PlannerItem.COURSE_RECORD_ITEM);

        newPlannerItem.setUniqueId(UUID.randomUUID().toString());
        newPlannerItem.setTermId(courseRecord.getTermId());
        newPlannerItem.setCourseCode(courseRecord.getCourseCode());
        newPlannerItem.setCategory(ItemCategory.COMPLETE);

        CourseOffering offering = null;
        try {
            offering = KsapFrameworkServiceLocator.getCourseOfferingService().getCourseOffering(courseRecord.getCourseOfferingId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Course Offering Service failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Course Offering Service failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Course Offering Service failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("Course Offering Service failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("Course Offering Service failure", e);
        }

        newPlannerItem.setCourseId(offering.getCourseId());
        newPlannerItem.setCourseTitle(courseRecord.getCourseTitle());

        newPlannerItem.setMeta(courseRecord.getMeta());

        String creditsString = courseRecord.getCreditsEarned();
        try {
            if(creditsString == null){
                newPlannerItem.setMaxCredits(BigDecimal.ZERO);
                newPlannerItem.setMinCredits(BigDecimal.ZERO);
            }else{
                newPlannerItem.setMaxCredits(new BigDecimal(creditsString));
                newPlannerItem.setMinCredits(new BigDecimal(creditsString));
            }
        } catch (NumberFormatException e) {
            LOG.warn(String.format("Invalid credits in course record %s", courseRecord.getCreditsEarned()), e);
        }

        String grade = courseRecord.getCalculatedGradeValue();
        if (!"X".equalsIgnoreCase(grade)|| KsapFrameworkServiceLocator.getTermHelper().isCompleted(
                newPlannerItem.getTermId())) {
            newPlannerItem.setGrade(grade);
        }

        return newPlannerItem;
    }

    @Override
    /**
     * @see org.kuali.student.ap.framework.context.PlanHelper#createPlannerItem(org.kuali.student.ap.academicplan.infc.PlanItem)
     */
    public PlannerItem createPlannerItem(PlanItem planItem){
        List<String> statusMessages = new ArrayList<String>();

        PlannerItem newPlannerItem = new PlannerItem();
        newPlannerItem.setType(PlannerItem.PLAN_ITEM);
        newPlannerItem.setUniqueId(UUID.randomUUID().toString());
        try{
            newPlannerItem.setTermId(KSCollectionUtils.getRequiredZeroElement(planItem.getPlanTermIds()));
        }catch (OperationFailedException e){
            LOG.warn(String.format("No Term id found for %s", planItem.getId()), e);
        }
        newPlannerItem.setLearningPlanId(planItem.getLearningPlanId());
        newPlannerItem.setPlanItemId(planItem.getId());
        newPlannerItem.setCategory(planItem.getCategory());

        // Retrieve Current Version of related course
        Course course = KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourseByVersionIndependentId(planItem.getRefObjectId());

        newPlannerItem.setCourseId(course.getId());
        newPlannerItem.setCourseCode(course.getCode());
        newPlannerItem.setCourseTitle(course.getCourseTitle());

        newPlannerItem.setMeta(planItem.getMeta());

        BigDecimal credits = planItem.getCredits();
        if (credits == null) {
            CreditsFormatter.Range range = CreditsFormatter.getRange(course);
            if (range != null) {
                newPlannerItem.setMaxCredits(range.getMax());
                newPlannerItem.setMinCredits(range.getMin());
            }
            if(range.getMultiple() != null)  {
                newPlannerItem.setMultipleCredits(range.getMultiple());
            }
        } else {
            newPlannerItem.setMaxCredits(credits);
            newPlannerItem.setMinCredits(credits);
        }

        RichText descr = planItem.getDescr();
        if (descr != null){
            newPlannerItem.setCourseNote(descr.getPlain());
        }

        // If course is in progress and registration is closed remove any remaining reg group items
        if(KsapFrameworkServiceLocator.getTermHelper().isInProgress(newPlannerItem.getTermId())){
            if(!KsapFrameworkServiceLocator.getTermHelper().isRegistrationOpen(newPlannerItem.getTermId())){
                Iterator iter = planItem.getAttributes().iterator();
                while(iter.hasNext()){
                    AttributeInfo attributeInfo = (AttributeInfo)iter.next();
                    if(attributeInfo.getKey().equals(AcademicPlanServiceConstants.PLAN_ITEM_RELATION_TYPE_COURSE2RG)){
                        KsapFrameworkServiceLocator.getPlanHelper().removePlanItem(attributeInfo.getValue());
                        iter.remove();
                    }
                }
            }
        }

        //Find any associated plan items of the registration group variety
        List<String> registrationGroupCodes = validateAndGetRegistrationGroupCodes(planItem, statusMessages);
        if (registrationGroupCodes.size() > 0) {
            newPlannerItem.setRegistrationGroupCodes(registrationGroupCodes);
        }

        statusMessages.addAll(validateCourseItem(course,newPlannerItem));
        newPlannerItem.setStatusMessages(statusMessages);

        return newPlannerItem;
    }

    /**
     *
     * @see org.kuali.student.ap.framework.context.PlanHelper#getPlannerCalendarTerms(org.kuali.student.r2.core.acal.infc.Term)
     */
    public List<PlannerTerm> getPlannerTerms(LearningPlan learningPlan) {
        LOG.info("Retrieve Completed Records: {}",System.currentTimeMillis());
        List<StudentCourseRecordInfo> completedRecords = retrieveCourseRecords(learningPlan.getStudentId());
        LOG.info("Retrieve PlanItems: {}",System.currentTimeMillis());

        List<CourseRegistrationInfo> registeredRecords=retrieveRegistrationRecords(learningPlan.getStudentId());

        List<PlanItem> planItems = retrieveCoursePlanItems(learningPlan.getId());
        LOG.info("Create Planner Items: {}",System.currentTimeMillis());
        List<PlannerItem> plannerItems = new ArrayList<PlannerItem>();
        for(StudentCourseRecordInfo record : completedRecords){
            PlannerItem newItem = KsapFrameworkServiceLocator.getPlanHelper().createPlannerItem(record);
            plannerItems.add(newItem);
        }
        for(CourseRegistrationInfo registrationRecord : registeredRecords){
            PlannerItem newItem = KsapFrameworkServiceLocator.getPlanHelper().createPlannerItem(registrationRecord);
            plannerItems.add(newItem);
        }
        for(PlanItem planItem : planItems){
            PlannerItem newItem = KsapFrameworkServiceLocator.getPlanHelper().createPlannerItem(planItem);
            plannerItems.add(newItem);
        }
        LOG.info("Create Planner Item Map: {}",System.currentTimeMillis());
        Map<String,List<PlannerItem>> plannerItemMap = createTermPlannerItemMap(plannerItems);
        LOG.info("Retrieve Term Notes: {}",System.currentTimeMillis());
        List<PlannerTermNote> notes = retrieveTermNotes(learningPlan.getId());
        LOG.info("Create Term note map: {}",System.currentTimeMillis());
        Map<String,List<PlannerTermNote>> termNotesMap = createTermTermNoteMap(notes);

        LOG.info("Find first term: {}",System.currentTimeMillis());
        String startTermId = findFirstTermForStudent(plannerItemMap,termNotesMap);

        LOG.info("Find term list: {}",System.currentTimeMillis());
        List<Term> calendarTerms = KsapFrameworkServiceLocator.getPlanHelper().getPlannerCalendarTerms(KsapFrameworkServiceLocator.getTermHelper().getTerm(startTermId));

        LOG.info("Create Planner Terms: {}",System.currentTimeMillis());
        List<PlannerTerm> terms = createPlannerTerms(calendarTerms,plannerItemMap,termNotesMap);
        LOG.info("Return terms: {}",System.currentTimeMillis());
        return terms;
    }

    /**
     * Retrieve student's registrationRecords (...ignore completed terms)
     * @param studentId - Id of student
     */
    private List<CourseRegistrationInfo> retrieveRegistrationRecords(String studentId) {
        List<CourseRegistrationInfo> registeredRecords=new ArrayList<>();
        try {
            List<CourseRegistrationInfo> tempRegisteredRecords = KsapFrameworkServiceLocator
                    .getCourseRegistrationService().getCourseRegistrationsByStudent(
                            studentId,KsapFrameworkServiceLocator.getContext().getContextInfo());
            for (CourseRegistrationInfo record : tempRegisteredRecords) {
                if (!KsapFrameworkServiceLocator.getTermHelper().isCompleted(record.getTermId())) {
                    registeredRecords.add(record);
                }
            }
        } catch (InvalidParameterException|MissingParameterException|OperationFailedException|PermissionDeniedException  e) {
            throw new IllegalArgumentException("Registered Records lookup failure", e);
        }
        return registeredRecords;
    }

    /**
     * Retrieves records of completed courses
     * Need to look into how Registerd records are retrieved
     *
     * @param studentId - Id of student records are being retrieved for
     * @return List of Course Records student is enrolled for
     */
    private List<StudentCourseRecordInfo> retrieveCourseRecords(String studentId){
        List<StudentCourseRecordInfo> completedRecords;
        try {
            completedRecords = KsapFrameworkServiceLocator.getAcademicRecordService().getCompletedCourseRecords(
                    studentId,KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            completedRecords=new ArrayList<>();
//            throw new IllegalArgumentException("AR lookup failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("AR lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("AR lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("AR lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("AR lookup failure", e);
        }

        return completedRecords;
    }

    /**
     * Retrieve all plan items for the plan
     *
     * @param planId - Id of the plan being loaded
     * @return List of items for the plan
     */
    private List<PlanItem> retrieveCoursePlanItems(String planId){
        List<PlanItem> itemsToReturn = new ArrayList<PlanItem>();
        List<PlanItem> planItems = KsapFrameworkServiceLocator.getPlanHelper().getPlanItems(planId);

        for(PlanItem item : planItems){
            if(!item.getRefObjectType().equals(PlanConstants.COURSE_TYPE)){
                continue;
            }
            if(item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.WISHLIST)){
                continue;
            }
            itemsToReturn.add(item);
        }

        return itemsToReturn;
    }

    /**
     * Sort a list of items into a ordered map based on the term
     *
     * @param items - List of items to sort
     * @return A ordered Map of term to term items
     */
    protected Map<String, List<PlannerItem>> createTermPlannerItemMap(List<PlannerItem> items){
        // Setup map and its comparator
        Map<String, List<PlannerItem>> itemMap = new TreeMap<String, List<PlannerItem>>() {
            @Override
            public Comparator<? super String> comparator() {
                return new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        Term t1 = KsapFrameworkServiceLocator.getTermHelper().getTerm(o1);
                        Term t2 = KsapFrameworkServiceLocator.getTermHelper().getTerm(o2);
                        return t1.getStartDate().compareTo(t2.getStartDate());
                    }
                };
            }
        };

        // Sort items into map
        for(PlannerItem item : items){
            List<PlannerItem> existingItems;
            if(itemMap.containsKey(item.getTermId())){
                existingItems = itemMap.get(item.getTermId());
            }
            else{
                existingItems = new ArrayList<PlannerItem>();
            }
            existingItems.add(item);
            itemMap.put(item.getTermId(),existingItems);
        }

        return itemMap;
    }

    /**
     * Retrieve a list of term notes for a the plan
     *
     * @param planId - Id of the plan being loaded
     * @return List of term notes for the plan
     */
    protected List<PlannerTermNote> retrieveTermNotes(String planId){
        List<PlannerTermNote> termNotes = new ArrayList<PlannerTermNote>();
        CommentService commentService = KsapFrameworkServiceLocator.getCommentService();
        List<CommentInfo> commentInfos;

        // Retrieve comments containing the notes
        try {
            commentInfos = commentService.getCommentsByRefObject(planId,PlanConstants.TERM_NOTE_COMMENT_TYPE,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Comment lookup failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Comment lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Comment lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("Comment lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("Comment lookup failure", e);
        }

        // Convert comments into term notes
        for (CommentInfo comment : commentInfos) {
            PlannerTermNote newTermNote = new PlannerTermNote();
            String termId = comment.getAttributeValue(PlanConstants.TERM_NOTE_COMMENT_ATTRIBUTE_ATPID);
            newTermNote.setUniqueId(termId.replace('.', '-'));
            newTermNote.setTermId(termId);
            newTermNote.setDate(comment.getEffectiveDate());
            newTermNote.setTermNote(comment.getCommentText().getFormatted());
            termNotes.add(newTermNote);
        }

        return termNotes;
    }

    /**
     * Sort a list of notes into a ordered map based on the term
     *
     * @param items - List of notes to sort
     * @return A ordered Map of term to term notes
     */
    protected Map<String, List<PlannerTermNote>> createTermTermNoteMap(List<PlannerTermNote> items){
        // Setup map and its comparator
        Map<String, List<PlannerTermNote>> itemMap = new TreeMap<String, List<PlannerTermNote>>() {
            @Override
            public Comparator<? super String> comparator() {
                return new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        Term t1 = KsapFrameworkServiceLocator.getTermHelper().getTerm(o1);
                        Term t2 = KsapFrameworkServiceLocator.getTermHelper().getTerm(o2);
                        return t1.getStartDate().compareTo(t2.getStartDate());
                    }
                };
            }
        };

        // Sort items into map
        for(PlannerTermNote item : items){
            List<PlannerTermNote> existingItems;
            if(itemMap.containsKey(item.getTermId())){
                existingItems = itemMap.get(item.getTermId());
            }
            else{
                existingItems = new ArrayList<PlannerTermNote>();
            }
            existingItems.add(item);
            itemMap.put(item.getTermId(),existingItems);
        }

        return itemMap;
    }

    /**
     * Calculates the id of the first term the student has activity in
     * This algorithm needs to be redone to take into account items/notes only in future terms.
     *
     * @param items - Map of planner items in each term
     * @param notes - Map of term notes in each term
     * @return Id of the first term with activity
     */
    protected String findFirstTermForStudent(Map<String,List<PlannerItem>> items, Map<String,List<PlannerTermNote>> notes){
        Term current = KsapFrameworkServiceLocator.getTermHelper().getCurrentTerm();
        Term toCompare = null;
        if(items.isEmpty() && notes.isEmpty()){
            // If no items or notes are set use the current date
            return current.getId();
        }

        if(items.isEmpty()){
            // If no items are set use first notes term
            toCompare=  KsapFrameworkServiceLocator.getTermHelper().getTerm(notes.keySet().iterator().next());
        }
        if(notes.isEmpty()){
            // If no notes are set use first item term
            toCompare=  KsapFrameworkServiceLocator.getTermHelper().getTerm(items.keySet().iterator().next());
        }
        if(toCompare == null){
            String earliestItemTerm = items.keySet().iterator().next();
            String earliestNoteTerm = notes.keySet().iterator().next();
            Term t1 = KsapFrameworkServiceLocator.getTermHelper().getTerm(earliestItemTerm);
            Term t2 = KsapFrameworkServiceLocator.getTermHelper().getTerm(earliestNoteTerm);
            int compare = t2.getStartDate().compareTo(t1.getStartDate());
            if(compare<0) toCompare = t2;
            toCompare = t1;
        }
        int compare = toCompare.getStartDate().compareTo(current.getStartDate());
        if(compare<0) return toCompare.getId();
        return current.getId();
    }

    /**
     * Create the terms to be displayed in the planner
     *
     * @param terms - List of terms being displayed
     * @param itemsMap - Map of planner items in each term
     * @param notesMap - Map of term notes in each term
     * @return A completed list of planner terms
     */
    protected List<PlannerTerm> createPlannerTerms(List<Term> terms, Map<String,List<PlannerItem>> itemsMap, Map<String,List<PlannerTermNote>> notesMap){
        List<PlannerTerm> plannerTerms = new ArrayList<PlannerTerm>();

        for(Term term : terms){
            PlannerTerm plannerTerm = new PlannerTerm(term.getId());
            plannerTerm = fillPlannerTerm(plannerTerm,itemsMap.get(term.getId()),notesMap.get(term.getId()));
            plannerTerms.add(plannerTerm);
        }

        return plannerTerms;
    }

    /**
     * Fill in information for a planner term based on the items and notes for the term.
     *
     * @param term - Term to fill in
     * @param items - List of items in the term
     * @param notes - List of notes for the term
     * @return A completed planner term
     */
    protected PlannerTerm fillPlannerTerm(PlannerTerm term, List<PlannerItem> items, List<PlannerTermNote> notes){
        // Set term notes
        term.setTermNoteList(notes);

        List<PlannerItem> completed = new ArrayList<PlannerItem>();
        List<PlannerItem> registered = new ArrayList<PlannerItem>();
        List<PlannerItem> planned = new ArrayList<PlannerItem>();
        List<PlannerItem> backup = new ArrayList<PlannerItem>();

        // Separated list of items to individual categories.
        if(items != null){
            //Sort items by date the item was added
            sortPlannerItemsByDate(items);
            for(PlannerItem item : items){
                if(item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.PLANNED)){
                    planned.add(item);
                }else if(item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.BACKUP)){
                    backup.add(item);
                }else if(item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.COMPLETE)){
                    completed.add(item);
                }else if(item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.REGISTERED)){
                    registered.add(item);
                }
            }
        }

        // Add Add to Course Item
        if(term.isPlanning()){
            PlannerItem addPlanned = new PlannerItem();
            addPlanned.setType(PlannerItem.ADD_ITEM);
            addPlanned.setTermId(term.getTermId());
            addPlanned.setCategory(ItemCategory.PLANNED);
            PlannerItem addBackup = new PlannerItem();
            addBackup.setType(PlannerItem.ADD_ITEM);
            addBackup.setTermId(term.getTermId());
            addBackup.setCategory(ItemCategory.BACKUP);
            planned.add(addPlanned);
            backup.add(addBackup);
        }

        // Set Categories
        term.setAcademicRecord(completed);
        term.setRegistrationList(registered);
        term.setPlannedList(planned);
        term.setBackupList(backup);

        // Calculate the credit line
        term = calculateCreditLineForPlannerTerm(term);

        // Pad categories to min level
        term = padCategory(term);

        return term;
    }

    /**
     * Sorts a list of planner items by the creation date
     * @param plannerItems - List of planner items to sort
     */
    protected void sortPlannerItemsByDate(List<PlannerItem> plannerItems) {
        Collections.sort(plannerItems, new Comparator<PlannerItem>() {
            @Override
            public int compare(PlannerItem o1, PlannerItem o2) {
                if (o1.getMeta() == null && o2.getMeta() != null)
                    return 1;
                if (o1.getMeta() != null && o2.getMeta() == null)
                    return -1;
                if (o1.getMeta() == null && o2.getMeta() == null)
                    return 0;
                return o1.getMeta().getCreateTime().compareTo(o2.getMeta().getCreateTime());
            }
        });

    }


    /**
     * Calculates and adds any needed padding to the list of categories
     *
     * @param term - Term to add padding to
     * @return Term with categories padded as needed
     */
    protected PlannerTerm padCategory(PlannerTerm term){
        int numberToAdd = Integer.parseInt(ConfigContext.getCurrentContextConfig()
                .getProperty("ks.ap.planner.item.min"));
        if(term.isCompleted()){
            // If term is completed only completed items are above the credit line
            numberToAdd = numberToAdd - term.getAcademicRecord().size();
            term.setAcademicRecord(padCategory(term.getAcademicRecord(),numberToAdd));
        } else if(term.isFutureTerm()){
            if(term.getRegistrationList().isEmpty()){
                // If no registered items exist only planned is above credit line
                numberToAdd = numberToAdd - term.getPlannedList().size();
                term.setPlannedList(padCategory(term.getPlannedList(),numberToAdd));
            } else{
                // If there are registered items then both  are displayed with 1 extra space for planned header
                numberToAdd = numberToAdd - term.getPlannedList().size() - term.getRegistrationList().size()-1;
                term.setPlannedList(padCategory(term.getPlannedList(),numberToAdd));
            }
        } else if(term.isInProgress()){
            // If term is in progress Registered and/or planned items are above credit line
            if(term.isRegistrationOpen()){
                if(term.getRegistrationList().isEmpty()){
                    // If no registered items exist only planned is above credit line
                    numberToAdd = numberToAdd - term.getPlannedList().size();
                    term.setPlannedList(padCategory(term.getPlannedList(),numberToAdd));
                } else{
                    // If there are registered items then both  are displayed with 1 extra space for planned header
                    numberToAdd = numberToAdd - term.getPlannedList().size() - term.getRegistrationList().size()-1;
                    term.setPlannedList(padCategory(term.getPlannedList(),numberToAdd));
                }
            } else{
                // If registration is closed then only registration items
                numberToAdd = numberToAdd - term.getRegistrationList().size();
                term.setRegistrationList(padCategory(term.getRegistrationList(),numberToAdd));
            }
        }
        return term;
    }

    /**
     * Adds extra blank line items to a list of existing items
     *
     * @param category - List of items to add to
     * @param numberToAdd - The number of blank items to add
     * @return Modified list of items
     */
    protected List<PlannerItem> padCategory(List<PlannerItem> category, int numberToAdd){
        for(int i = 0; i<numberToAdd; i++){
            PlannerItem blank = new PlannerItem();
            category.add(blank);
        }
        return category;
    }


    /**
     * Determine the items needed to get the terms credit sum and set the value.
     *
     * @param term - Term credits are being calculated on
     * @return Term with the credit line set
     */
    protected PlannerTerm calculateCreditLineForPlannerTerm(PlannerTerm term){
        List<PlannerItem> itemsToTotal = new ArrayList<PlannerItem>();

        if(term.isCompleted()){
            itemsToTotal.addAll(term.getAcademicRecord());
        }else if(term.isFutureTerm()){
            itemsToTotal.addAll(term.getPlannedList());
            itemsToTotal.addAll(term.getRegistrationList());
        }else if(term.isInProgress()){
            itemsToTotal.addAll(term.getRegistrationList());
            if(term.isRegistrationOpen()){
                itemsToTotal.addAll(term.getPlannedList());
            }
        }

        return calculateCreditLineForPlannerTerm(term,itemsToTotal);

    }

    /**
     * Calculate the total sum of the min and max credits for a list of planner items
     *
     * @param term - Planner term being set
     * @param items - List of items to sum
     * @return Planner term with the credit updated
     */
    protected PlannerTerm calculateCreditLineForPlannerTerm(PlannerTerm term, List<PlannerItem> items){
        BigDecimal minCredits = BigDecimal.ZERO;
        BigDecimal maxCredits = BigDecimal.ZERO;
        for(PlannerItem item : items){
            if(item.getMinCredits()!=null){
                minCredits = minCredits.add(item.getMinCredits());
            }
            if(item.getMaxCredits()!=null){
                maxCredits = maxCredits.add(item.getMaxCredits());
            }
        }

        term.setCreditLineMinCredits(minCredits);
        term.setCreditLineMaxCredits(maxCredits);

        return term;

    }

    /**
     * Retrieves a list of registration codes repersented by plan items linked to the course item.
     *
     * @param planItem - Course Item in the plan
     * @return A list of registration codes for groups planned
     */
    protected List<String> validateAndGetRegistrationGroupCodes(PlanItem planItem, List<String> statusMessages){

        //Find any associated plan items of the registration group variety
        List<String> registrationGroupCodes = new ArrayList<String>();
        PlanItemInfo planItemInfo = new PlanItemInfo(planItem);
        List<String> planItemIdsForRegGroups = planItemInfo.getAttributeValueList(AcademicPlanServiceConstants.
                PLAN_ITEM_RELATION_TYPE_COURSE2RG);

        List<String> regGroupIds = new ArrayList<String>();

        //Get the registration group IDs
        for (String planItemIdForRegGroup : planItemIdsForRegGroups) {
            PlanItem planItemForRegGroup = KsapFrameworkServiceLocator.getPlanHelper().getPlanItem(planItemIdForRegGroup);
            regGroupIds.add(planItemForRegGroup.getRefObjectId());
        }

        List <String> canceledRGCodes = new ArrayList<String>();
        List <String> suspendedRGCodes = new ArrayList<String>();

        //Look up the reg groups
        List<RegistrationGroupInfo> regGroups = null;
        for(String regGroupId : regGroupIds){
            SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants.KSAP_SEARCH_LUI_NAME_BY_LUI_ID_KEY);
            request.addParam(CourseSearchConstants.SearchParameters.LUI_ID,regGroupId);
            try {
                SearchResultInfo results = KsapFrameworkServiceLocator.getSearchService().search(
                        request,KsapFrameworkServiceLocator.getContext().getContextInfo());
                if(results==null || results.getRows().size() == 0){
                    statusMessages.add("The planned section is not available any more.");
                    return registrationGroupCodes;
                }
                String regGroupName = KsapHelperUtil.getCellValue(KSCollectionUtils.getOptionalZeroElement(
                        results.getRows()),CourseSearchConstants.SearchResultColumns.LUI_NAME);
                registrationGroupCodes.add(regGroupName);
                String regGroupState = KsapHelperUtil.getCellValue(KSCollectionUtils.getOptionalZeroElement(
                        results.getRows()),CourseSearchConstants.SearchResultColumns.LUI_STATE);
                if(!regGroupState.equals(LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY)){
                    if(regGroupState.equals(LuiServiceConstants.REGISTRATION_GROUP_CANCELED_STATE_KEY)){
                        canceledRGCodes.add(regGroupName);
                    } else if(regGroupState.equals(LuiServiceConstants.REGISTRATION_GROUP_SUSPENDED_STATE_KEY)){
                        suspendedRGCodes.add(regGroupName);
                    }
                }
            } catch ( InvalidParameterException | MissingParameterException | OperationFailedException | PermissionDeniedException e) {
                throw new IllegalStateException("RG lookup failure", e);
            }
        }
        if (!canceledRGCodes.isEmpty()) {
            generateWarningMessage(statusMessages, canceledRGCodes, true, false);
        }
        if (!suspendedRGCodes.isEmpty()) {
            generateWarningMessage(statusMessages, suspendedRGCodes, false, true);
        }
        return registrationGroupCodes;
    }

    private void generateWarningMessage(List<String> statusMessages, List<String> rgCodes, boolean isCanceled, boolean isSuspended) {
        try {
            if (rgCodes.size() == 1){
                if (isCanceled){
                    statusMessages.add("Section "+KSCollectionUtils.getOptionalZeroElement(rgCodes).toString()+" has been canceled.");
                }
                else if(isSuspended){
                    statusMessages.add("Section "+KSCollectionUtils.getOptionalZeroElement(rgCodes).toString()+" has been suspended.");
                }
            }
            else if(rgCodes.size()>1){
                RegCodeListPropertyEditor editor = new RegCodeListPropertyEditor();
                editor.setValue(rgCodes);
                if (isCanceled){
                    statusMessages.add("Sections "+ editor.getAsText()+" have been canceled.");
                }
                else if (isSuspended){
                    statusMessages.add("Section "+editor.getAsText()+" has been suspended.");
                }

            }
        } catch (OperationFailedException e) {
            throw new IllegalStateException("Fail to display Regitration Group Code", e);
        }
    }

    /**
     * Validates the course for the planner item and returns a list of any status messages found
     * Validations:
     * Course is suspended
     * Course is retired and past expiration date
     * Term is in progress and course has no offered course offerings
     *
     * @param course - Course repersented in the planner item
     * @param plannerItem - Planner item being created
     * @return A list of messages based on the validation
     */
    protected List<String> validateCourseItem(Course course, PlannerItem plannerItem){
        List<String> statusMessages = new ArrayList<String>();

         //Check for suspended/canceled CO's ...if SOC (for term) is published
        if (KsapFrameworkServiceLocator.getTermHelper().isTermSocPublished(plannerItem.getTermId())
                && (KsapFrameworkServiceLocator.getTermHelper().isCurrentTerm(plannerItem.getTermId())
                    || KsapFrameworkServiceLocator.getTermHelper().isFutureTerm(plannerItem.getTermId()))) {
            List<CourseOfferingInfo> coList=new ArrayList<>();
            try {
                coList = KsapFrameworkServiceLocator.getCourseOfferingService()
                        .getCourseOfferingsByCourseAndTerm(course.getId(),plannerItem.getTermId(),
                                KsapFrameworkServiceLocator.getContext().getContextInfo());
            } catch (DoesNotExistException e) {
                coList = new ArrayList<>();
            } catch (InvalidParameterException|MissingParameterException|OperationFailedException|PermissionDeniedException e) {
                throw new RuntimeException(String.format("Error retrieving Offerings for Course(%s) & term(%s): %s",
                        course.getCode(),plannerItem.getTermId(),e.getMessage()),e);
            }
            if (coList==null) {
                coList= new ArrayList<>();
            }
            int totalCoCnt=0;
            int suspendedCoCnt = 0;
            int cancelledCoCnt = 0;
            int offeredCoCnt=0;
            for (CourseOfferingInfo co : coList) {
                if (LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY.equals( co.getStateKey())) {
                    ++offeredCoCnt;
                } else if (LuiServiceConstants.LUI_CO_STATE_SUSPENDED_KEY.equals( co.getStateKey())) {
                    ++suspendedCoCnt;
                } else if (LuiServiceConstants.LUI_CO_STATE_CANCELED_KEY.equals( co.getStateKey())) {
                    ++cancelledCoCnt;
                }
                ++totalCoCnt;
            }
            if (suspendedCoCnt==0&&cancelledCoCnt==0&&offeredCoCnt==0) {
                statusMessages.add(KsapFrameworkServiceLocator.getTextHelper().getFormattedMessage(PlanConstants.
                        PLANNER_VALIDATION_MESSAGE_NOT_SCHEDULED,course.getCode()));
            } else if (offeredCoCnt==0 && (suspendedCoCnt>0 && cancelledCoCnt==0)) {
                statusMessages.add(KsapFrameworkServiceLocator.getTextHelper().getFormattedMessage(PlanConstants.
                        PLANNER_VALIDATION_MESSAGE_SUSPENDED,course.getCode()));
            } else if (offeredCoCnt==0 && (suspendedCoCnt==0 && cancelledCoCnt>0)) {
                statusMessages.add(KsapFrameworkServiceLocator.getTextHelper().getFormattedMessage(PlanConstants.
                        PLANNER_VALIDATION_MESSAGE_CANCELED,course.getCode()));
            } else if (offeredCoCnt==0 && (suspendedCoCnt>0 && cancelledCoCnt>0)) {
                statusMessages.add(KsapFrameworkServiceLocator.getTextHelper().getFormattedMessage(PlanConstants.
                        PLANNER_VALIDATION_MESSAGE_SUSPENDED_OR_CANCELED,course.getCode()));
            }
        }
        return statusMessages;
    }
}
