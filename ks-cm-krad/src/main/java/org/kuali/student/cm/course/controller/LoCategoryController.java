package org.kuali.student.cm.course.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.course.form.LoCategoryForm;
import org.kuali.student.logging.FormattedLogger;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.LearningObjectiveServiceConstants;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller handles the Learning Objective Category maintenance from the CM Home
 * screen.
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
@Controller
@RequestMapping(value = "/loCategory")
public class LoCategoryController extends UifControllerBase {

    private transient LearningObjectiveService loService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        //Need to register a null date converter for the BeanUtils copy process, otherwise an exception is thrown if a null date is copied.
        ConvertUtils.register(new DateConverter(null), Date.class);
        LoCategoryForm form = new LoCategoryForm();
        form.setLoCategories(getLoCategories());
        return form;
    }

    private List<LoCategoryInfo> getLoCategories() {
        List<LoCategoryInfo> loCategories = new ArrayList<LoCategoryInfo>();
        try {
            List<LoCategoryInfo> retrievedCategories = getLoService().getLoCategoriesByLoRepository(
                    "kuali.loRepository.key.singleUse", ContextUtils.getContextInfo());
            for (LoCategoryInfo loCat : retrievedCategories) {
                if ("Active".equalsIgnoreCase(loCat.getStateKey())) {
                    loCategories.add(loCat);
                }
            }
        } catch (Exception e) {
            FormattedLogger.error("The Learning Objective Categories could not be retrieved from the database: %s",
                    e.getMessage());
        }
        return loCategories;
    }

    @Override
    public ModelAndView refresh(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        LoCategoryForm loCatForm = (LoCategoryForm) form;
        loCatForm.setLoCategories(getLoCategories());
        return super.refresh(loCatForm, result, request, response);
    }

    @Override
    public ModelAndView addLine(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView model = super.addLine(uifForm, result, request, response);
        try {
            model = refresh(uifForm, result, request, response);
        } catch (Exception e) {
            FormattedLogger.error("An error occurred while refreshing the Learning Objective Categories: %s",
                    e.getMessage());
        }
        return model;
    }

    @Override
    public ModelAndView saveLine(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView model = super.saveLine(uifForm, result, request, response);
        try {
            model = refresh(uifForm, result, request, response);
        } catch (Exception e) {
            FormattedLogger.error("An error occurred while refreshing the Learning Objective Categories: %s",
                    e.getMessage());
        }
        return model;
    }

    protected LearningObjectiveService getLoService() {
        if (loService == null) {
            loService = GlobalResourceLoader.getService(new QName(LearningObjectiveServiceConstants.NAMESPACE,
                    LearningObjectiveService.class.getSimpleName()));
        }
        return loService;
    }

}
