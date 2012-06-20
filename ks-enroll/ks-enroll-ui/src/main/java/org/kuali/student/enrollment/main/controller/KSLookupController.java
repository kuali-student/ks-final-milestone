package org.kuali.student.enrollment.main.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.datadictionary.DataObjectEntry;
import org.kuali.rice.krad.lookup.CollectionIncomplete;
import org.kuali.rice.krad.lookup.Lookupable;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
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
import java.util.Collection;
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
     * search - sets the values of the data entered on the form on the jsp into a map and then searches for the
     * results.
     */
    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") LookupForm lookupForm, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {
        suppressActionsIfNeeded(lookupForm);

        Lookupable lookupable = lookupForm.getLookupable();
        if (lookupable == null) {
            LOG.error("Lookupable is null.");
            throw new RuntimeException("Lookupable is null.");
        }

        // validate search parameters
        lookupable.validateSearchParameters(lookupForm, lookupForm.getCriteriaFields());

        Collection<?> displayList =
                lookupable.performSearch(lookupForm, lookupForm.getCriteriaFields(), true);

        if (displayList instanceof CollectionIncomplete<?>) {
            request.setAttribute("reqSearchResultsActualSize",
                    ((CollectionIncomplete<?>) displayList).getActualSizeIfTruncated());
        } else {
            request.setAttribute("reqSearchResultsActualSize", new Integer(displayList.size()));
        }

        lookupForm.setSearchResults(displayList);

        if(getView(lookupForm) instanceof KSLookupView){
            KSLookupView ksLookupView = (KSLookupView)getView(lookupForm);
            String defaultAction = ksLookupView.getDefaultSingleLookupResultAction();
            if (StringUtils.isNotBlank(defaultAction) && displayList != null && displayList.size() == 1){
                Object object = displayList.iterator().next();

                DataObjectEntry ddEntry = KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getDataObjectEntry(lookupForm.getDataObjectClassName());
                String titleAttribute = ddEntry.getTitleAttribute();

                Properties props = new Properties();
                props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, UifConstants.MethodToCallNames.START);
                props.put(titleAttribute,ObjectPropertyUtils.getPropertyValue(object, titleAttribute));
                props.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));
                props.put(UifConstants.UrlParams.SHOW_HOME,BooleanUtils.toStringTrueFalse(false));
                props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE,lookupForm.getDataObjectClassName());
                props.put("returnLocation", lookupForm.getReturnLocation());

                return performRedirect(lookupForm,defaultAction,props );
            }
        }

        return getUIFModelAndView(lookupForm);
    }

    private View getView(LookupForm form){
         if (form.getView() != null){
             return form.getView();
         }else{
             return form.getPostedView();
         }
    }
}
