package org.kuali.student.enrollment.main.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.krad.datadictionary.DataObjectEntry;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.ModuleService;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.controller.LookupController;
import org.kuali.rice.krad.web.controller.UifControllerHelper;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.rice.krad.web.form.UifFormBase;
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

   @RequestMapping(params = "methodToCall=start")
    @Override
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        LookupForm lookupForm = (LookupForm) form;

        // if request is not a redirect, determine if we need to redirect for an externalizable object lookup
        if (!lookupForm.isRedirectedLookup()) {
            Class lookupObjectClass = null;
            try {
                lookupObjectClass = Class.forName(lookupForm.getDataObjectClassName());
            } catch (ClassNotFoundException e) {
                throw new RiceRuntimeException("Unable to get class for name: " + lookupForm.getDataObjectClassName());
            }

            ModuleService responsibleModuleService =
                    KRADServiceLocatorWeb.getKualiModuleService().getResponsibleModuleService(lookupObjectClass);
            if (responsibleModuleService != null && responsibleModuleService.isExternalizable(lookupObjectClass)) {
                String lookupUrl = responsibleModuleService.getExternalizableDataObjectLookupUrl(lookupObjectClass,
                        KRADUtils.convertRequestMapToProperties(request.getParameterMap()));

                Properties redirectUrlProps = new Properties();
                redirectUrlProps.put(UifParameters.REDIRECTED_LOOKUP, "true");
                UifControllerHelper.prepareHistory(request, form);
                // clear current form from session
                GlobalVariables.getUifFormManager().removeSessionForm(form);

                return performRedirect(form, lookupUrl, redirectUrlProps);
            }
        }

        return super.start(lookupForm, result, request, response);
    }

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
