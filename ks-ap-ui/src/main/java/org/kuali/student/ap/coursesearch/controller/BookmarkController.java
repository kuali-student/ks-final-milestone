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
package org.kuali.student.ap.coursesearch.controller;

import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.controller.extension.KsapControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.form.BookmarkForm;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsDialogForm;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsForm;
import org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.planner.util.PlanEventUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller handling the interactions of the course section portion of the Course Details Page.
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

    @MethodAccessible
    @RequestMapping(params = "methodToCall=refreshBookmarkCount")
    public ModelAndView refreshBookmarkCount(@ModelAttribute("KualiForm") BookmarkForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {


        // Gather information about the registration group
        // Create the new plan item
        LearningPlan learningPlan = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan();
        PlanItemInfo newPlanItem = new PlanItemInfo();

        List<PlanItemInfo> bookmarkItems=null;

        // Save the new plan item to the database
        try{
            bookmarkItems = KsapFrameworkServiceLocator.getAcademicPlanService()
                    .getPlanItemsInPlanByCategory
                    (learningPlan.getId(),AcademicPlanServiceConstants.ItemCategory.WISHLIST,KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("Academic Plan Service lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Academic Plan Service lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Academic Plan Service lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("Academic Plan Service lookup error", e);
        }


        JsonObjectBuilder eventList = Json.createObjectBuilder();
        JsonObjectBuilder refreshEventData = Json.createObjectBuilder();
        int bookmarkCount = bookmarkItems.size();
        refreshEventData.add("bookmarkCount", bookmarkCount);
        eventList.add("REFRESH_BOOKMARK_COUNT", refreshEventData);
        PlanEventUtils.sendJsonEvents(true,"refresh bookmark count (val="+bookmarkCount+")", response, eventList);
        return null;
    }


}
