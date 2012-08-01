package org.kuali.student.enrollment.main.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.datadictionary.DataObjectEntry;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.LookupController;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.uif.view.KSLookupView;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 6/19/12
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping(value = "/lookup")
public class KSLookupController extends LookupController {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(KSLookupController.class);

    /**
     * Overrides the KRAD search functionality to perform redirect on single search result.
     */
    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") LookupForm lookupForm, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView = super.search(lookupForm,result,request,response);

        if(lookupForm.getPostedView() instanceof KSLookupView){
            KSLookupView ksLookupView = (KSLookupView)lookupForm.getPostedView();
            String defaultAction = ksLookupView.getDefaultSingleLookupResultAction();
            if (StringUtils.isNotBlank(defaultAction) && lookupForm.getLookupResults() != null && lookupForm.getLookupResults().size() == 1){
                Object object = lookupForm.getLookupResults().iterator().next();

                Properties props = new Properties();

                DataObjectEntry ddEntry = KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getDataObjectEntry(lookupForm.getDataObjectClassName());

                List<String> pkKeys = ddEntry.getPrimaryKeys();
                for (String pkKey : pkKeys) {
                    props.put(pkKey,ObjectPropertyUtils.getPropertyValue(object, pkKey));
                }

                if(StringUtils.equals(defaultAction,KRADConstants.PARAM_MAINTENANCE_VIEW_MODE_MAINTENANCE)){
                    props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
                }  else{
                    props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, UifConstants.MethodToCallNames.START);
                }
                props.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));
                props.put(UifConstants.UrlParams.SHOW_HOME,BooleanUtils.toStringTrueFalse(false));
                props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE,lookupForm.getDataObjectClassName());

                return performRedirect(lookupForm,defaultAction,props );
            }
        }

        return modelAndView;
    }

}
