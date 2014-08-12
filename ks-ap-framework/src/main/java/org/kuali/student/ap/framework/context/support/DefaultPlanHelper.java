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
import org.kuali.student.ap.academicplan.infc.Placeholder;
import org.kuali.student.ap.academicplan.infc.PlaceholderInstance;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.academicplan.infc.TypedObjectReference;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.PlanHelper;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
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
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.lum.course.infc.Course;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 * Default implementation of the PlanHelper
 */
public class DefaultPlanHelper implements PlanHelper {

    private static final long serialVersionUID = 3987763030779110660L;
    private static final Logger LOG = Logger.getLogger(DefaultPlanHelper.class);

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
                throw new RuntimeException(String.format("Could not fetch plan for user [%s].", studentId), e);
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
     * @see org.kuali.student.ap.framework.context.PlanHelper#addPlanItem(String, org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants.ItemCategory, String, java.math.BigDecimal, java.util.List, org.kuali.student.ap.academicplan.infc.TypedObjectReference)
     */
    @Override
    public PlanItem addPlanItem(String learningPlanId, ItemCategory category,
                                String descr, BigDecimal units, List<String> termIds,TypedObjectReference ref) {
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

        // If item is in wishlist use existing entry instead of creating new.
        boolean create = wishlistPlanItem == null;
        PlanItemInfo planItemInfo;
        if (create) {
            planItemInfo = new PlanItemInfo();
            planItemInfo.setCategory(category);
            planItemInfo.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
            planItemInfo.setStateKey(PlanConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);
            planItemInfo.setLearningPlanId(learningPlanId);
        } else {
            assert learningPlanId.equals(wishlistPlanItem.getLearningPlanId()) : learningPlanId + " " +
                    wishlistPlanItem.getLearningPlanId();
            planItemInfo = new PlanItemInfo(wishlistPlanItem);
            planItemInfo.setCategory(category);
        }

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
        planItemInfo.setCredit(units);

        try {
            if (create) {
                // If creating new add it to the database
                planItemInfo = KsapFrameworkServiceLocator
                        .getAcademicPlanService().createPlanItem(
                                planItemInfo,
                                KsapFrameworkServiceLocator.getContext()
                                        .getContextInfo());
            } else {
                // If using wish list item update it
                planItemInfo = KsapFrameworkServiceLocator.getAcademicPlanService().updatePlanItem(planItemInfo.getId(),
                                planItemInfo, KsapFrameworkServiceLocator.getContext().getContextInfo());
            }
        } catch (AlreadyExistsException e) {
            LOG.warn("Reference " + ref.getRefObjectType() + " "+ ref.getRefObjectId() + " is already planned", e);
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
            newComment.setEffectiveDate(KsapHelperUtil.getCurrentDate());
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
}
