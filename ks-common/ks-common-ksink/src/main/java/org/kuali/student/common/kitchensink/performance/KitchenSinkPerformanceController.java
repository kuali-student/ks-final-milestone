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
package org.kuali.student.common.kitchensink.performance;

import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the controller class for kitchen sink performance page.
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/kitchensinkperformance")
public class KitchenSinkPerformanceController extends UifControllerBase {

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

        String inputOne = form.getInputOne(); // get input from page
        int rowCount = 0;

        if(inputOne != null) {

            try{
               rowCount = Integer.parseInt(inputOne);   // convert string -> int
            }  catch (Exception ex){
               GlobalVariables.getMessageMap().putError("inputOne", RiceKeyConstants.ERROR_CUSTOM, "Must be a valid integer");
            }
        }

        List<KitchenSinkPerformanceCollection> collectionList = new ArrayList<KitchenSinkPerformanceCollection>();

        // loop and generate collection
        for(int i = 0; i<rowCount; i++){
            collectionList.add(new KitchenSinkPerformanceCollection((i+1))); // we want to display rows starting at 1, not 0.
        }
        form.setPerfCollection(collectionList); // add collection to form.


        return getUIFModelAndView(form);
    }



}
