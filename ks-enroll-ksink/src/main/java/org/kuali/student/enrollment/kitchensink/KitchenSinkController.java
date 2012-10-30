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
package org.kuali.student.enrollment.kitchensink;

import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/kitchensink")
public class KitchenSinkController extends UifControllerBase {

    /**
     * @see org.kuali.rice.krad.web.controller.UifControllerBase#createInitialForm(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected KitchenSinkForm createInitialForm(HttpServletRequest request) {
        return new KitchenSinkForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        KitchenSinkForm uiTestForm = (KitchenSinkForm) form;

        // pre-select option key "3" in checkbox controls
        uiTestForm.setCheckboxSelections(Arrays.asList("3"));
        // pre-select option key "2" in radio button controls
        uiTestForm.setRadioButtonSelection("2");
        // pre-select option key "2" in dropdown controls
        uiTestForm.setDropdownSelection("2");

        return getUIFModelAndView(uiTestForm);
    }

    @RequestMapping(params = "methodToCall=collection")
    public ModelAndView collection(@ModelAttribute("KualiForm") KitchenSinkForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) {

        List<KitchenSinkFormCollection1> collectionList = new ArrayList<KitchenSinkFormCollection1>();
        collectionList.add(new KitchenSinkFormCollection1("Item #1", "This is the first item", "2001-01-01"));
        collectionList.add(new KitchenSinkFormCollection1("John Adams", "POTUS #2", "1735-10-30"));
        collectionList.add(new KitchenSinkFormCollection1("Big Bang Theory", "A funny television show", "2007-09-24"));
        collectionList.add(new KitchenSinkFormCollection1("Chainbreaker IPA", "A tasty beverage", "2011-06-09"));
        form.setCollection(collectionList);

        // Collections.xml has been removed
        // for Collections.xml; same collection property causes validation problems
        //List<KitchenSinkFormCollection1> collectionList2 = KitchenSinkFormCollection1.clone(collectionList);
        //form.setCollection2(KitchenSinkFormCollection1.clone(collectionList));

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=collectionOne")
    public ModelAndView collectionOne(@ModelAttribute("KualiForm") KitchenSinkForm form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) {

        List<KitchenSinkFormCollection1> collectionList = new ArrayList<KitchenSinkFormCollection1>();
        collectionList.add(new KitchenSinkFormCollection1("A", "First letter of the alphabet", "1970-01-01"));
        form.setCollection(collectionList);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=collectionTerm")
    public ModelAndView collectionTerm(@ModelAttribute("KualiForm") KitchenSinkForm form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) {

        List<KitchenSinkFormCollection1> collectionList = new ArrayList<KitchenSinkFormCollection1>();
        collectionList.add(new KitchenSinkFormCollection1("Fall 1997", "kuali.atp.type.Fall", "1997-09-01"));
        collectionList.add(new KitchenSinkFormCollection1("Winter 2000", "kuali.atp.type.Winter", "2000-01-01"));
        form.setCollection(collectionList);

        return getUIFModelAndView(form);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=addLineCollectionAsForm")
    public ModelAndView addLineCollectionAsForm(@ModelAttribute("KualiForm") KitchenSinkForm form, BindingResult result,
                                HttpServletRequest request, HttpServletResponse response) {
        List<KitchenSinkFormCollection1> collectionList = form.getCollection();
        Map<String, Object> newCollectionLines = form.getNewCollectionLines();
        if (null != newCollectionLines && !newCollectionLines.isEmpty()) {
            for (Map.Entry<String, Object> entry : newCollectionLines.entrySet()) {
                //
                // Code goes here to save new collection line to database...
                //
                // for this example, just assign a unique ID
                KitchenSinkFormCollection1 collection = (KitchenSinkFormCollection1)entry.getValue();
                collection.setId(KitchenSinkFormCollection1.assignId());
            }
            GlobalVariables.getMessageMap().addGrowlMessage("NOTE", "kitchensink.addLine");
        }

        return super.addLine(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=getActivities")
    public ModelAndView getActivities(
            @RequestParam(value = "actionParameters", required = false) Map<String, Integer> actionParameters,
            @ModelAttribute("KualiForm") KitchenSinkForm form,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        List<KitchenSinkMockActivityData> activities = new ArrayList<KitchenSinkMockActivityData>();

        Integer index = actionParameters.get("index");

        if (displayScheduleList != null && !(displayScheduleList.size() < index.intValue())) {
            activities = displayScheduleList.get(index.intValue()).getActivities();
        }

        form.setActivityList(activities);
        return getUIFModelAndView(form);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveForm")
    public ModelAndView saveForm(@ModelAttribute("KualiForm") KitchenSinkForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        //
        // Code goes here to edit form and exit if any errors exist
        //
        // Code goes here to save form to database
        //
        GlobalVariables.getMessageMap().addGrowlMessage("NOTE", "kitchensink.saveForm");
        return getUIFModelAndView(form);
    }

    List<KitchenSinkMockDisplayScheduleData> displayScheduleList = KitchenSinkMockDisplayScheduleData.mockTestData();

}
