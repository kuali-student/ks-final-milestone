package org.kuali.student.ap.i18n.controller;

import org.kuali.rice.krad.web.controller.extension.KsapControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.ap.i18n.LocaleUtil;
import org.kuali.student.ap.i18n.form.POCResourceBundleFormImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 1/10/14
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/rb/**")
public class POCResourceBundleController extends KsapControllerBase {

    private static final Logger LOG = LoggerFactory.getLogger(POCResourceBundleController.class);

    private static final String RB_FORM = "POCResourceBundle-FormView";

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new POCResourceBundleFormImpl();
    }

    /**
     * Entry point for the screen that handles setting up the form for the first display of the screen.
     *
     * Does not appear to be hit at any time.
     */
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=startRB")
    public ModelAndView startRB(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("In start()");
        super.start(form, result, request, response);

        form.setViewId(RB_FORM);
        form.setView(super.getViewService().getViewById(RB_FORM));
        ((POCResourceBundleFormImpl)form).setLocale(getLocaleFromContext());

        return getUIFModelAndView(form);
    }

    private String getLocaleFromContext() {
        KsapContext ksapCtx = KsapFrameworkServiceLocator.getContext();
        ContextInfo contextInfo = ksapCtx.getContextInfo();
        Locale locale = LocaleUtil.localeInfo2Locale(contextInfo.getLocale());
        return locale.toString();
    }
}
