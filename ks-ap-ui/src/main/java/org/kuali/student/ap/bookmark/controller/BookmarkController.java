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
package org.kuali.student.ap.bookmark.controller;

import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.controller.extension.KsapControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.bookmark.form.BookmarkForm;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.planner.util.PlanEventUtils;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.lum.course.infc.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller handling the interactions of the bookmarks.
 */
@Controller
@RequestMapping(value = "/bookmark/**")
public class BookmarkController extends KsapControllerBase {
    private static final Logger LOG = LoggerFactory.getLogger(BookmarkController.class);


    /**
     * @see org.kuali.rice.krad.web.controller.UifControllerBase
     */
    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new BookmarkForm();
    }

    /**
     * Controller method handling the updating of the bookmark count
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=refreshBookmarkCount")
    public ModelAndView refreshBookmarkCount(@ModelAttribute("KualiForm") BookmarkForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        LearningPlan learningPlan = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan();

        JsonObjectBuilder eventList = Json.createObjectBuilder();

        eventList = PlanEventUtils.makeUpdateBookmarkTotalEvent(learningPlan.getId(), eventList);

        PlanEventUtils.sendJsonEvents(true,"refresh bookmark count", response, eventList);
        return null;
    }

    /**
     * Controller method that handles the adding of courses as bookmarks and related events for dynamically updating
     * the page
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=addBookmark")
    public ModelAndView addBookmark(@ModelAttribute("KualiForm") BookmarkForm form, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

        JsonObjectBuilder eventList = Json.createObjectBuilder();
        LearningPlan learningPlan = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan();

        String courseId = request.getParameter("courseId");

        Course course = KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(courseId);
        if(course==null){
            LOG.warn(String.format("Course %s cannot be found", courseId));
            PlanEventUtils.sendJsonEvents(false,"Course cannot be found ", response, eventList);
            return null;
        }

        // Add the course to the plan
        PlanItemInfo newBookmark = new PlanItemInfo();
        newBookmark.setRefObjectId(course.getVersion().getVersionIndId());
        newBookmark.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
        newBookmark.setStateKey(PlanConstants.LEARNING_PLAN_ITEM_ACTIVE_STATE_KEY);
        newBookmark.setRefObjectType(PlanConstants.COURSE_TYPE);
        newBookmark.setCategory(AcademicPlanServiceConstants.ItemCategory.WISHLIST);
        newBookmark.setLearningPlanId(learningPlan.getId());

        try {
            // If creating new add it to the database
            newBookmark = KsapFrameworkServiceLocator.getAcademicPlanService().createPlanItem(newBookmark,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (AlreadyExistsException e) {
            LOG.warn(String.format("Course %s is already bookmarked", course.getCode()), e);
            PlanEventUtils.sendJsonEvents(false, "Course " + course.getCode() + " is already bookmarked",
                    response, eventList);
            return null;
        } catch (DataValidationErrorException e) {
            throw new IllegalArgumentException("AP service failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("AP service failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("AP service failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("AP service failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("AP service failure", e);
        }
        eventList = PlanEventUtils.makeAddBookmarkEvent(newBookmark, eventList);
        eventList = PlanEventUtils.makeUpdateBookmarkTotalEvent(newBookmark, eventList);
        List<PlanItem> planItems = KsapFrameworkServiceLocator.getPlanHelper().loadStudentsPlanItemsForCourse(course);
        eventList = PlanEventUtils.makeUpdatePlanItemStatusMessage(planItems, eventList);
        PlanEventUtils.sendJsonEvents(true, "Course " + course.getCode() + " added to bookmarks",
                response, eventList);
        return null;
    }

    /**
     *  Controller method that handles the deletion of bookmark plan items and related events needed to update the page.
     */
    @MethodAccessible
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=deleteBookmark")
    public ModelAndView deleteBookmark(@ModelAttribute("KualiForm") BookmarkForm form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        JsonObjectBuilder eventList = Json.createObjectBuilder();
        LearningPlan learningPlan = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan();

        String courseId = request.getParameter("courseId");
        String uniqueId = request.getParameter("uniqueId");

        // Delete plan item from the database
        PlanItemInfo itemToDelete = null;
        List<PlanItemInfo> planItems = null;
        try {
            // Retrieve valid plan item
            planItems = KsapFrameworkServiceLocator.getAcademicPlanService()
                    .getPlanItemsInPlanByRefObjectIdByRefObjectType(learningPlan.getId(),courseId,
                            PlanConstants.COURSE_TYPE,KsapFrameworkServiceLocator.getContext().getContextInfo());
            if (planItems == null){
                LOG.warn(String.format("Plan Item for %s cannot be found", courseId));
                PlanEventUtils.sendJsonEvents(false,"Plan Item cannot be found ", response, eventList);
                return null;
            }

            for(PlanItemInfo planItem : planItems){
                if(planItem.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.WISHLIST)){
                    itemToDelete = planItem;
                    break;
                }
            }

            if (itemToDelete == null){
                LOG.warn(String.format("Plan Item for %s cannot be found in wish list", courseId));
                PlanEventUtils.sendJsonEvents(false,"Plan Item cannot be found ", response, eventList);
                return null;
            }

            KsapFrameworkServiceLocator.getAcademicPlanService().deletePlanItem(itemToDelete.getId(),
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

        // Create json strings for displaying action's response and updating the planner screen.
        eventList = PlanEventUtils.makeRemoveEvent(uniqueId, itemToDelete, eventList);
        eventList = PlanEventUtils.makeUpdateBookmarkTotalEvent(learningPlan.getId(), eventList);
        Course course = KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(courseId);
        List<PlanItem> items = KsapFrameworkServiceLocator.getPlanHelper().loadStudentsPlanItemsForCourse(course);
        eventList = PlanEventUtils.makeUpdatePlanItemStatusMessage(items, eventList);
        PlanEventUtils.sendJsonEvents(true, "Course " + itemToDelete + " removed from Bookmarks",
                response, eventList);
        return null;
    }
}
