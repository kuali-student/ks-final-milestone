/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by bobhurt on 8/7/12
 */
package org.kuali.student.enrollment.class2.registration.performance.krad;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.List;

/**
 * This class is the controller class for kitchen sink performance page.
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/kitchensinkperformance")
public class KitchenSinkPerformanceController extends UifControllerBase {

    public static final String KUALI_ATP_2012_FALL = "kuali.atp.2012Fall";
    ScheduleOfClassesService scheduleOfClassesService;

    /**
     * @see org.kuali.rice.krad.web.controller.UifControllerBase#createInitialForm(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected KitchenSinkPerformanceForm createInitialForm(HttpServletRequest request) {
        return new KitchenSinkPerformanceForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        KitchenSinkPerformanceForm perfForm = (KitchenSinkPerformanceForm) form;

        return getUIFModelAndView(perfForm);
    }

    /**
     *  This method takes the user input (int) and generates a collection with that many rows. That collection is saved
     *  to the form object so it can be displayed on the page.
     * @param form    KitchenSinkPerformanceForm
     * @param result
     * @param request
     * @param response
     * @return    ModelAndView
     */
    @RequestMapping(params = "methodToCall=buildcollection")
    public ModelAndView buildCollection(@ModelAttribute("KualiForm") KitchenSinkPerformanceForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) {

        String courseCode = form.getInputOne(); // get input from page
        String termId = KUALI_ATP_2012_FALL;

        List<CourseSearchResult> collectionList;
        try {
            collectionList = getScheduleOfClassesService().loadCourseOfferingsByTermAndCourseCode(termId, courseCode);
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }

        form.setPerfCollection(collectionList); // add collection to form.


        return getUIFModelAndView(form);
    }

    private ScheduleOfClassesService getScheduleOfClassesService() {
        if (scheduleOfClassesService == null) {
            scheduleOfClassesService = (ScheduleOfClassesService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "scheduleOfClasses", ScheduleOfClassesService.class.getSimpleName()));
        }
        return scheduleOfClassesService;
    }

}
