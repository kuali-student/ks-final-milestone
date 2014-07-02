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
 */
package org.kuali.student.common.uif.controller;

import com.sun.corba.se.impl.logging.InterceptorsSystemException;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.core.api.util.RiceConstants;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.datadictionary.DataObjectEntry;
import org.kuali.rice.krad.lookup.LookupController;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupUtils;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.ModuleService;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.UifPropertyPaths;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.view.KSLookupView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;










/**
 * This is the base class for the KS Lookup controller which extends from KRAD controller class. This class is intended to
 * provide common functionalities at the KS level.
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/lookup")
public class KSLookupController extends LookupController {

    @RequestMapping(params = "methodToCall=start")
    @Override
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form,
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
                // clear current form from session
                GlobalVariables.getUifFormManager().removeSessionForm(form);

                return performRedirect(form, lookupUrl, redirectUrlProps);
            }
        }

        return super.start(lookupForm, request, response);
    }

    /**
     * Overrides the KRAD search functionality to perform redirect on single search result.
     */
    @RequestMapping(params = "methodToCall=search")
    @Override
    public ModelAndView search(@ModelAttribute("KualiForm") LookupForm lookupForm) {

        ModelAndView modelAndView = super.search(lookupForm);

        if(lookupForm.getView() instanceof KSLookupView){
            KSLookupView ksLookupView = (KSLookupView)lookupForm.getView();
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
                props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE,lookupForm.getDataObjectClassName());

                return performRedirect(lookupForm,defaultAction,props );
            }
        }

        return modelAndView;
    }



    @RequestMapping(method = RequestMethod.POST,params = "methodToCall=returnSelected")
    @Override
    public String returnSelected(@ModelAttribute(UifConstants.KUALI_FORM_ATTR) LookupForm lookupForm,
                                         HttpServletRequest request, final RedirectAttributes redirectAttributes) {

        LookupUtils.refreshLookupResultSelections((LookupForm) lookupForm);

        // build string of select line fields
        String multiValueReturnFieldsParam = "";
        List<String> multiValueReturnFields = lookupForm.getMultiValueReturnFields();
        Collections.sort(multiValueReturnFields);
        if (multiValueReturnFields != null && !multiValueReturnFields.isEmpty()) {
            for (String field : multiValueReturnFields) {
                multiValueReturnFieldsParam += field + ",";
            }
            multiValueReturnFieldsParam = StringUtils.join
            multiValueReturnFieldsParam = StringUtils.removeEnd(multiValueReturnFieldsParam, ",");

        }

        // build string of select line identifiers
        String selectedLineValues = "";
        Set<String> selectedLines = lookupForm.getSelectedCollectionLines().get(UifPropertyPaths.LOOKUP_RESULTS);
        if (selectedLines != null) {
            for (String selectedLine : selectedLines) {
                selectedLineValues += selectedLine.replaceAll(",", "&#44;").replaceAll(":","&#58;") + ",";
            }
            selectedLineValues = StringUtils.removeEnd(selectedLineValues, ",");

        }

        Properties parameters = new Properties();
        parameters.put(UifParameters.SELECTED_LINE_VALUES, selectedLineValues);
        parameters.putAll(lookupForm.getInitialRequestParameters());

        String redirectUrl = UrlFactory.parameterizeUrl(lookupForm.getReturnLocation(), parameters);

        boolean lookupCameFromDifferentServer = KRADUtils.areDifferentDomains(lookupForm.getReturnLocation(),
                lookupForm.getRequestUrl());

        if (StringUtils.isNotBlank(multiValueReturnFieldsParam)) {
            redirectAttributes.addAttribute(UifParameters.MULIT_VALUE_RETURN_FILEDS, multiValueReturnFieldsParam);
        }

        if (redirectUrl.length() > RiceConstants.MAXIMUM_URL_LENGTH && !lookupCameFromDifferentServer) {
            redirectAttributes.addFlashAttribute(UifParameters.SELECTED_LINE_VALUES, selectedLineValues);
        }
        if (redirectUrl.length() > RiceConstants.MAXIMUM_URL_LENGTH && lookupCameFromDifferentServer) {
            Map<String, String[]> parms = lookupForm.getInitialRequestParameters();
            parms.remove(UifParameters.RETURN_FORM_KEY);

            //add an error message to display to the user
            redirectAttributes.mergeAttributes(parms);
            redirectAttributes.addAttribute(UifParameters.MESSAGE_TO_DISPLAY,
                    RiceKeyConstants.INFO_LOOKUP_RESULTS_MV_RETURN_EXCEEDS_LIMIT);

            String formKeyParam = request.getParameter(UifParameters.FORM_KEY);
            redirectAttributes.addAttribute(UifParameters.FORM_KEY, formKeyParam);

            return UifConstants.REDIRECT_PREFIX + lookupForm.getRequestUrl();
        }

        if (redirectUrl.length() < RiceConstants.MAXIMUM_URL_LENGTH) {
            redirectAttributes.addAttribute(UifParameters.SELECTED_LINE_VALUES, selectedLineValues);
        }

        redirectAttributes.addAttribute(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.RETURN_METHOD_TO_CALL);

        if (StringUtils.isNotBlank(lookupForm.getReturnFormKey())) {
            redirectAttributes.addAttribute(UifParameters.FORM_KEY, lookupForm.getReturnFormKey());
        }

        redirectAttributes.addAttribute(KRADConstants.REFRESH_CALLER, lookupForm.getView().getId());
        redirectAttributes.addAttribute(KRADConstants.REFRESH_CALLER_TYPE,
                UifConstants.RefreshCallerTypes.MULTI_VALUE_LOOKUP);
        redirectAttributes.addAttribute(KRADConstants.REFRESH_DATA_OBJECT_CLASS, lookupForm.getDataObjectClassName());

        if (StringUtils.isNotBlank(lookupForm.getQuickfinderId())) {
            redirectAttributes.addAttribute(UifParameters.QUICKFINDER_ID, lookupForm.getQuickfinderId());
        }

        if (StringUtils.isNotBlank(lookupForm.getLookupCollectionName())) {
            redirectAttributes.addAttribute(UifParameters.LOOKUP_COLLECTION_NAME, lookupForm.getLookupCollectionName());
        }

        if (StringUtils.isNotBlank(lookupForm.getLookupCollectionId())) {
            redirectAttributes.addAttribute(UifParameters.LOOKUP_COLLECTION_ID, lookupForm.getLookupCollectionId());
        }

        if (StringUtils.isNotBlank(lookupForm.getReferencesToRefresh())) {
            redirectAttributes.addAttribute(KRADConstants.REFERENCES_TO_REFRESH, lookupForm.getReferencesToRefresh());
        }

        // clear current form from session
        GlobalVariables.getUifFormManager().removeSessionForm(lookupForm);

        return UifConstants.REDIRECT_PREFIX + lookupForm.getReturnLocation();
    }



}
