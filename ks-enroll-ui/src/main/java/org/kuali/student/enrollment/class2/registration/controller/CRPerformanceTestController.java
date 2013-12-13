package org.kuali.student.enrollment.class2.registration.controller;

import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/action")
public class CRPerformanceTestController {

    @RequestMapping(method = RequestMethod.GET)
    public String printHello(@RequestParam Map<String,String> allRequestParams, ModelMap model) {
        String inputOne = allRequestParams.get("inputOne")==null?null:allRequestParams.get("inputOne");
        int rowCount = 0;

        if(inputOne != null) {

            try{
                rowCount = Integer.parseInt(inputOne);   // convert string -> int
            }  catch (Exception ex){
                GlobalVariables.getMessageMap().putError("inputOne", RiceKeyConstants.ERROR_CUSTOM, "Must be a valid integer");
            }
        }

        List<org.kuali.student.enrollment.class2.registration.controller.KitchenSinkPerformanceCollection> collectionList = new ArrayList<org.kuali.student.enrollment.class2.registration.controller.KitchenSinkPerformanceCollection>();

        // loop and generate collection
        for(int i = 0; i<rowCount; i++){
            collectionList.add(new org.kuali.student.enrollment.class2.registration.controller.KitchenSinkPerformanceCollection((i+1))); // we want to display rows starting at 1, not 0.
        }
        model.addAttribute("perfCollection", collectionList); // add collection to form.
        return "cr-performance-test";
    }

}
