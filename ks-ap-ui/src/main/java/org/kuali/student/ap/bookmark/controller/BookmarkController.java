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
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.bookmark.form.BookmarkForm;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.planner.util.PlanEventUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        LearningPlan learningPlan = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan();

        JsonObjectBuilder eventList = Json.createObjectBuilder();

        eventList = PlanEventUtils.makeUpdateBookmarkTotalEvent(learningPlan.getId(), eventList);

        PlanEventUtils.sendJsonEvents(true,"refresh bookmark count", response, eventList);
        return null;
    }
}
