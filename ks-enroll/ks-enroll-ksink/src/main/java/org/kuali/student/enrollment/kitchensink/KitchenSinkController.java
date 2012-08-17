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
/*
        KitchenSinkForm uiTestForm = (KitchenSinkForm) form;

        uiTestForm.setStringField1("Field One");
        uiTestForm.setStringField2("Field Two");

        //return super.start(uiTestForm, result, request, response);
        return getUIFModelAndView(uiTestForm);
*/
        return getUIFModelAndView(form);
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

        return getUIFModelAndView(form);
    }

}
