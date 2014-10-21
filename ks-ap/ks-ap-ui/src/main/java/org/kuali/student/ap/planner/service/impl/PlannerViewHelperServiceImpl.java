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

package org.kuali.student.ap.planner.service.impl;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.TypedObjectReferenceInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.academicplan.infc.TypedObjectReference;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.coursesearch.util.CourseDetailsUtil;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.planner.PlannerForm;
import org.kuali.student.ap.planner.dataobject.CourseSummaryPopoverDetailsWrapper;
import org.kuali.student.ap.planner.form.AddCourseToPlanForm;
import org.kuali.student.ap.planner.form.CourseNoteForm;
import org.kuali.student.ap.planner.form.CourseSummaryForm;
import org.kuali.student.ap.planner.form.DeletePlanItemForm;
import org.kuali.student.ap.planner.form.MovePlanItemForm;
import org.kuali.student.ap.planner.form.PlanItemEditForm;
import org.kuali.student.ap.planner.form.PlannerFormImpl;
import org.kuali.student.ap.planner.form.QuickAddCourseToPlanForm;
import org.kuali.student.ap.planner.form.TermNoteForm;
import org.kuali.student.ap.planner.service.PlannerViewHelperService;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.lum.course.infc.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlannerViewHelperServiceImpl extends PlanEventViewHelperServiceImpl implements PlannerViewHelperService {

    private static final Logger LOG = LoggerFactory.getLogger(PlannerViewHelperServiceImpl.class);

    /**
     * @see org.kuali.student.ap.planner.service.PlannerViewHelperService#loadAddToPlanDialogForm(org.kuali.rice.krad.web.form.UifFormBase, org.kuali.student.ap.planner.form.AddCourseToPlanForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public UifFormBase loadAddToPlanDialogForm(UifFormBase submittedForm, AddCourseToPlanForm dialogForm, HttpServletRequest request, HttpServletResponse response){
        String courseId = request.getParameter("courseId");

        Course course = KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourse(courseId);

        dialogForm.setCourseId(courseId);
        dialogForm.setCourseCode(course.getCode());
        dialogForm.setCourseTitle(course.getCourseTitle());
        String uniqueId = ((PlannerFormImpl)submittedForm).getUniqueId();
        if(uniqueId==null){
            dialogForm.setUniqueId(UUID.randomUUID().toString());
        }else{
            dialogForm.setUniqueId(uniqueId);
        }

        // Set Credits to display for course
        String creditString = KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().formatCredits(course);
        dialogForm.setCreditsDisplay(creditString);

        // Set if course is variable credits
        boolean isVariableCredits = false;
        CreditsFormatter.Range range = KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().getRange(course);
        if(range.getMultiple()!=null && !range.getMultiple().isEmpty()) isVariableCredits = true;
        isVariableCredits = !range.getMax().equals(range.getMin());
        dialogForm.setVariableCredit(isVariableCredits);

        dialogForm.setCourseSummaryDetails(getCourseSummaryPopoverDetails(course));

        //Find terms that already contain this planned course
        List<PlanItem> planItems = KsapFrameworkServiceLocator.getPlanHelper().loadStudentsPlanItemsForCourse(course);
        List<String> plannedTermIds = KsapFrameworkServiceLocator.getPlanHelper().getTermIdsForPlanItems(planItems);
        dialogForm.setPlannedTermIds(plannedTermIds);

        return dialogForm;
    }

    /**
     * @see org.kuali.student.ap.planner.service.PlannerViewHelperService#loadQuickAddToPlanDialogForm(org.kuali.rice.krad.web.form.UifFormBase, org.kuali.student.ap.planner.form.QuickAddCourseToPlanForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public UifFormBase loadQuickAddToPlanDialogForm(UifFormBase submittedForm, QuickAddCourseToPlanForm dialogForm, HttpServletRequest request, HttpServletResponse response){
        String termId = request.getParameter("termId");
        boolean backup = Boolean.parseBoolean(request.getParameter("backup"));

        dialogForm.setTermId(termId);
        dialogForm.setBackup(backup);
        dialogForm.setTermName(KsapFrameworkServiceLocator.getTermHelper().getYearTerm(termId).getLongName());

        return dialogForm;
    }

    /**
     * @see org.kuali.student.ap.planner.service.PlannerViewHelperService#loadTermNoteDialogForm(org.kuali.rice.krad.web.form.UifFormBase, org.kuali.student.ap.planner.form.TermNoteForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public UifFormBase loadTermNoteDialogForm(UifFormBase submittedForm, TermNoteForm dialogForm, HttpServletRequest request, HttpServletResponse response){
        String termId = request.getParameter("termId");
        String uniqueId = request.getParameter("uniqueId");
        String planId = dialogForm.getPlanId();

        dialogForm.setTermId(termId);
        dialogForm.setTermName(KsapFrameworkServiceLocator.getTermHelper().getYearTerm(termId).getLongName());
        dialogForm.setUniqueId(uniqueId);

        String termNote = "";
        CommentService commentService = KsapFrameworkServiceLocator.getCommentService();
        List<CommentInfo> commentInfos;
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

        for (CommentInfo comment : commentInfos) {
            String commentAtpId = comment.getAttributeValue(PlanConstants.TERM_NOTE_COMMENT_ATTRIBUTE_ATPID);
            if (termId.equals(commentAtpId)) {
                RichTextInfo text = comment.getCommentText();
                if (text != null)
                    termNote = text.getPlain();
            }
        }

        dialogForm.setTermNote(termNote);

        return dialogForm;
    }

    /**
     * @see org.kuali.student.ap.planner.service.PlannerViewHelperService#loadPlanItemEditForm(org.kuali.rice.krad.web.form.UifFormBase, org.kuali.student.ap.planner.form.PlanItemEditForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public UifFormBase loadPlanItemEditForm(UifFormBase submittedForm, PlanItemEditForm dialogForm, HttpServletRequest request, HttpServletResponse response){

        String planItemId = request.getParameter("planItemId");
        String termId = request.getParameter("termId");
        String uniqueId = request.getParameter("uniqueId");


        PlanItem planItem = KsapFrameworkServiceLocator.getPlanHelper().getPlanItem(planItemId);
        Course course = KsapFrameworkServiceLocator.getCourseHelper()
                .getCurrentVersionOfCourseByVersionIndependentId(planItem.getRefObjectId());

        dialogForm.setUniqueId(uniqueId);
        dialogForm.setPlanItemId(planItem.getId());
        dialogForm.setPlanId(planItem.getLearningPlanId());
        dialogForm.setCourseId(planItem.getRefObjectId());
        String courseNote ="";
        if(planItem.getDescr()!=null){
            courseNote = planItem.getDescr().getFormatted();
        }
        dialogForm.setCourseNote(courseNote);
        dialogForm.setCourseCd(course.getCode());
        dialogForm.setTermId(termId);

        return dialogForm;
    }

    /**
     * @see org.kuali.student.ap.planner.service.PlannerViewHelperService#finishAddCourse(org.kuali.student.ap.academicplan.infc.LearningPlan, org.kuali.student.ap.planner.PlannerForm, org.kuali.student.r2.lum.course.infc.Course, String, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void finishAddCourse(LearningPlan plan, PlannerForm form, Course course, String termId,
                                 HttpServletResponse response) throws IOException, ServletException {
        AcademicPlanServiceConstants.ItemCategory category = form.isBackup() ? AcademicPlanServiceConstants.ItemCategory.BACKUP
                : AcademicPlanServiceConstants.ItemCategory.PLANNED;
        Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(termId);
        JsonObjectBuilder eventList = Json.createObjectBuilder();

        PlanItem planItemInfo=null;
        List<String> planTermIds = new ArrayList<String>(1);
        planTermIds.add(termId);
        TypedObjectReference planItemRef = new TypedObjectReferenceInfo(PlanConstants.COURSE_TYPE, course.getVersion().getVersionIndId());
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();

        // Check for existing bookmark items for
        PlanItem wishlistPlanItem = null;
        List<PlanItem> existingPlanItems =  KsapFrameworkServiceLocator.getPlanHelper().getPlanItems(plan.getId());
        if (existingPlanItems != null && planItemRef != null){
            for (PlanItem existingPlanItem : existingPlanItems) {
                if (!KsapFrameworkServiceLocator.getPlanHelper().isSame(existingPlanItem, planItemRef) || wishlistPlanItem != null){
                    continue;
                }

                // If item has no term then record it
                if (AcademicPlanServiceConstants.ItemCategory.WISHLIST.equals(existingPlanItem.getCategory())){
                    wishlistPlanItem = existingPlanItem;
                }
            }
        }


        try {
            planItemInfo = KsapFrameworkServiceLocator.getPlanHelper().addPlanItem(plan.getId(), category,
                    form.getCourseNote(),form.getCreditsForPlanItem(course),planTermIds,planItemRef,attributes);
        } catch (AlreadyExistsException e) {
            LOG.warn(String.format("%s is already planned for %s", course.getCode(), term.getName()), ".", e);
            sendJsonEvents(false,
                    KsapFrameworkServiceLocator.getTextHelper().getFormattedMessage(
                            PlanConstants.COURSE_ALREADY_PLANNED,course.getCode(),term.getName()), response, eventList);
            return;
        }

        // Create json strings for displaying action's response and updating the planner screen.
        eventList = makeAddEvent(planItemInfo, eventList);
        eventList = updateTotalCreditsEvent(true, termId, eventList);
        eventList = makeUpdateBookmarkTotalEvent(planItemInfo, eventList);
        if(wishlistPlanItem != null){
            eventList = makeRemoveEvent(form.getUniqueId(), wishlistPlanItem, eventList);
        }

        List<PlanItem> planItems = KsapFrameworkServiceLocator.getPlanHelper().loadStudentsPlanItemsForCourse(course);
        eventList = makeUpdatePlanItemStatusMessage(planItems, eventList);
        sendJsonEvents(true, course.getCode() + " was successfully added to your plan.",
                response, eventList);
    }
    /**
     * @see org.kuali.student.ap.planner.service.PlannerViewHelperService#loadCourseNotePlanForm(org.kuali.rice.krad.web.form.UifFormBase, org.kuali.student.ap.planner.form.CourseNoteForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public UifFormBase loadCourseNotePlanForm(UifFormBase submittedForm, CourseNoteForm dialogForm,
            HttpServletRequest request, HttpServletResponse response){
        String planItemId = request.getParameter("planItemId");

        PlanItem planItem = KsapFrameworkServiceLocator.getPlanHelper().getPlanItem(planItemId);
        dialogForm.setPlanItemId(planItem.getId());
        dialogForm.setPlanId(planItem.getLearningPlanId());
        dialogForm.setCourseId(planItem.getRefObjectId());
        String courseNote ="";
        if(planItem.getDescr()!=null){
            courseNote = planItem.getDescr().getFormatted();
        }
        dialogForm.setCourseNote(courseNote);
        try {
            dialogForm.setTermId(KSCollectionUtils.getRequiredZeroElement(planItem.getPlanTermIds()));
        } catch (OperationFailedException e) {
            String errMsg = String.format("unexpected error retrieving single termId for plannedItem (id=%s): %s",
                    planItemId, e.getMessage());
            LOG.warn(errMsg, e);
            throw new IllegalStateException(errMsg, e);
        }

        Course course = KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourseByVersionIndependentId(
                planItem.getRefObjectId());
        dialogForm.setCourseCode(course.getCode());
        dialogForm.setCourseTitle(course.getCourseTitle());
        dialogForm.setCourseCredits(KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().formatCreditsShortVersion(course));

        String uniqueId= request.getParameter("uniqueId");
        if(uniqueId==null){
            dialogForm.setUniqueId(UUID.randomUUID().toString());
        }else{
            dialogForm.setUniqueId(uniqueId);
        }
        dialogForm.setUniqueId(uniqueId);

        return dialogForm;
    }

    /**
     * @see org.kuali.student.ap.planner.service.PlannerViewHelperService#loadCourseSummaryForm(org.kuali.rice.krad.web.form.UifFormBase, org.kuali.student.ap.planner.form.CourseSummaryForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public UifFormBase loadCourseSummaryForm(UifFormBase submittedForm, CourseSummaryForm dialogForm,
                                              HttpServletRequest request, HttpServletResponse response){

        String courseId = request.getParameter("courseId");

        Course course = KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourse(courseId);

        dialogForm.setCourseId(courseId);
        dialogForm.setCourseCode(course.getCode());
        dialogForm.setCourseTitle(course.getCourseTitle());
        dialogForm.setCourseSummaryDetails(getCourseSummaryPopoverDetails(course));

        // Set Credits to display for course
        String creditString = KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().formatCredits(course);
        dialogForm.setCreditsDisplay(creditString);

        List<PlanItem> planItems = KsapFrameworkServiceLocator.getPlanHelper().loadStudentsPlanItemsForCourse(course);
        dialogForm.setPlannedMessage(KsapFrameworkServiceLocator.getPlanHelper().createPlanningStatusMessages(planItems));
        dialogForm.setBookmarkMessage(KsapFrameworkServiceLocator.getPlanHelper().createBookmarkStatusMessages(planItems));

        return dialogForm;
    }

    /**
     * @see org.kuali.student.ap.planner.service.PlannerViewHelperService#loadDeletePlanItemForm(org.kuali.rice.krad.web.form.UifFormBase, org.kuali.student.ap.planner.form.DeletePlanItemForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public UifFormBase loadDeletePlanItemForm(UifFormBase submittedForm, DeletePlanItemForm dialogForm,
                                             HttpServletRequest request, HttpServletResponse response){

        String courseId = request.getParameter("courseId");
        String planItemId = request.getParameter("planItemId");
        String uniqueId = request.getParameter("uniqueId");
        String termId = request.getParameter("termId");

        Course course = KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourse(courseId);

        dialogForm.setCourseId(courseId);
        dialogForm.setCourseCode(course.getCode());
        dialogForm.setCourseTitle(course.getCourseTitle());

        // Set Credits to display for course
        String creditString = KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().formatCredits(course);
        dialogForm.setCreditsDisplay(creditString);

        dialogForm.setUniqueId(uniqueId);
        dialogForm.setPlanItemId(planItemId);
        dialogForm.setTermId(termId);
        dialogForm.setTermName(KsapFrameworkServiceLocator.getTermHelper().getYearTerm(termId).getLongName());


        return dialogForm;
    }

    /**
     * @see org.kuali.student.ap.planner.service.PlannerViewHelperService#loadMovePlanItemForm(org.kuali.rice.krad.web.form.UifFormBase, org.kuali.student.ap.planner.form.MovePlanItemForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public UifFormBase loadMovePlanItemForm(UifFormBase submittedForm, MovePlanItemForm dialogForm,
                                              HttpServletRequest request, HttpServletResponse response){

        String courseId = request.getParameter("courseId");
        String planItemId = request.getParameter("planItemId");
        String uniqueId = request.getParameter("uniqueId");
        String termId = request.getParameter("termId");

        Course course = KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourse(courseId);

        dialogForm.setCourseId(courseId);
        dialogForm.setCourseCode(course.getCode());
        dialogForm.setCourseTitle(course.getCourseTitle());

        CourseSummaryPopoverDetailsWrapper courseDetails = new CourseSummaryPopoverDetailsWrapper();
        courseDetails.setScheduledTerms(KsapFrameworkServiceLocator.getCourseHelper().getScheduledTermsForCourse(course));
        dialogForm.setCourseSummaryDetails(courseDetails);

        // Set Credits to display for course
        String creditString = KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().formatCredits(course);
        dialogForm.setCreditsDisplay(creditString);

        //Find terms that already contain this planned course
        List<PlanItem> planItems = KsapFrameworkServiceLocator.getPlanHelper().loadStudentsPlanItemsForCourse(course);
        List<String> plannedTermIds = KsapFrameworkServiceLocator.getPlanHelper().getTermIdsForPlanItems(planItems);
        dialogForm.setPlannedTermIds(plannedTermIds);

        dialogForm.setUniqueId(uniqueId);
        dialogForm.setPlanItemId(planItemId);
        dialogForm.setTermId(termId);


        return dialogForm;
    }

    private CourseSummaryPopoverDetailsWrapper getCourseSummaryPopoverDetails(Course course){
        CourseSummaryPopoverDetailsWrapper courseDetails = new CourseSummaryPopoverDetailsWrapper();

        // courseDetails.setCourseRequisites(CourseDetailsUtil.getCourseRequisites(course));
        courseDetails.setCourseRequisitesMap(CourseDetailsUtil.getCourseRequisitesMap(course));

        // Load Term information
        courseDetails.setScheduledTerms(KsapFrameworkServiceLocator.getCourseHelper()
                .getScheduledTermsForCourse(course));
        courseDetails.setProjectedTerms(KsapFrameworkServiceLocator.getCourseHelper().getProjectedTermsForCourse(course));

        // Load Last Offered Term information if course is not scheduled
        if(courseDetails.getScheduledTerms().isEmpty()){
            Term lastOfferedTerm = KsapFrameworkServiceLocator.getCourseHelper().getLastOfferedTermForCourse(course);
            if (lastOfferedTerm != null){
                courseDetails.setLastOffered(lastOfferedTerm.getName());
            }
            else {
                // If no last offered is found set as null
                courseDetails.setLastOffered(null);
            }
        }else{
            courseDetails.setLastOffered(null);
        }

        return courseDetails;
    }
}
