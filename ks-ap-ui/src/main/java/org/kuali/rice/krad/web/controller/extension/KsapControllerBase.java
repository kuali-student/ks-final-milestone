package org.kuali.rice.krad.web.controller.extension;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;


/**
 * Fix under KSAP-265
 * Used to replace krad FullView.ftl with ksapFullView.ftl
 */
public abstract class KsapControllerBase extends UifControllerBase {
    /**
     * Configures the <code>ModelAndView</code> instance containing the form
     * data and pointing to the UIF generic spring view
     *
     * @param form form instance containing the model data
     * @param pageId id of the page within the view that should be rendered, can
     * be left blank in which the current or default page is rendered
     * @return ModelAndView object with the contained form
     */
    @Override
    protected ModelAndView getUIFModelAndView(UifFormBase form, String pageId) {
        return getUIFModelAndViewHelper(form, pageId);
    }

    /**
     * Retrieves a new view instance for the given view id and then configures the <code>ModelAndView</code>
     * instance containing the form data and pointing to the UIF generic spring view
     *
     * @param form form instance containing the model data
     * @param viewId id for the view that should be built
     * @return ModelAndView object with the contained form
     */
    @Override
    protected ModelAndView getUIFModelAndViewWithInit(UifFormBase form, String viewId) {
        View view = getViewService().getViewById(viewId);

        Assert.notNull(view, "View not found with id: " + viewId);

        form.setView(view);
        form.setViewId(viewId);

        return getUIFModelAndViewHelper(form, form.getPageId());
    }

    /**
     * Configures the <code>ModelAndView</code> instance containing the form
     * data and pointing to the UIF generic spring view
     *
     * @param form - Form instance containing the model data
     * @param pageId - Id of the page within the view that should be rendered, can
     * be left blank in which the current or default page is rendered
     * @return ModelAndView object with the contained form
     */
    private static ModelAndView getUIFModelAndViewHelper(UifFormBase form, String pageId) {
        if (StringUtils.isNotBlank(pageId)) {
            form.setPageId(pageId);
        }

        // create the spring return object pointing to View.jsp
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(UifConstants.DEFAULT_MODEL_NAME, form);
        modelAndView.setViewName("/krad/WEB-INF/ftl/ksapUifRender");

        return modelAndView;
    }
}
