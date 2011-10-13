package org.kuali.student.enrollment.class2.registration.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.field.ActionField;
import org.kuali.rice.krad.uif.layout.StackedLayoutManager;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.student.enrollment.class2.registration.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.registration.form.RegistrationForm;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;

import java.util.Collection;
import java.util.List;

public class RegistrationViewHelperServiceImpl extends ViewHelperServiceImpl {

    public void checkRegElements(CollectionGroup collGroup, RegistrationForm form){
        //RegistrationForm form = (RegistrationForm)model;
        //CollectionGroup collGroup = (CollectionGroup)component;
        List<CourseRegistrationInfo> regs = form.getCourseRegistrations();
        Collection<RegistrationGroupWrapper> regGroups =
                (Collection<RegistrationGroupWrapper>)ObjectPropertyUtils.getPropertyValue(form, collGroup.getBindingInfo().getBindingPath());
        boolean renderRegButtons = true;
        boolean renderDropButton = false;
        boolean renderRemoveFromCartButton = false;
        for(RegistrationGroupWrapper regGroup: regGroups){
            for(CourseRegistrationInfo reg: regs){
                if(reg.getCourseOffering().getId().equals(regGroup.getCourseOffering().getId())){
                    renderRegButtons = false;
                    renderDropButton = true;
                    break;
                }
            }

            if(!renderRegButtons){
                break;
            }
            if(form.getRegRequest() != null){
                for (RegRequestItemInfo regRequestItemInfo : form.getRegRequest().getRegRequestItems()) {
                    // find the regGroupId of the current item
                    String regGroupId = regRequestItemInfo.getNewRegGroupId();
                    if(StringUtils.isNotBlank(regGroupId)){
                        // find the regGroupWrapper that matches the id from the supplemental list
                        RegistrationGroupWrapper regGroupWrapper = form.getRegistrationGroupWrappersById().get(regGroupId);
                        if(regGroupWrapper.getCourseOffering().getId().equals(regGroup.getCourseOffering().getId())){
                            renderRegButtons = false;
                            renderRemoveFromCartButton = true;
                            break;
                        }
                    }
                }
            }

            if(!renderRegButtons){
                break;
            }
        }

        if(renderDropButton){
           collGroup.getSummaryMessageField().setMessageText("You are currently registered for a version of this course.");
           collGroup.getSummaryMessageField().addStyleClass("ks-regWarning");
        }
        else if(renderRemoveFromCartButton){
           collGroup.getSummaryMessageField().setMessageText("Your cart contains a version of this course.");
           collGroup.getSummaryMessageField().addStyleClass("ks-regWarning");
        }
        else{
           collGroup.getSummaryMessageField().setMessageText("Add a course to your cart or register with a single click:");
           collGroup.getSummaryMessageField().getStyleClasses().remove("ks-regWarning");
        }

        for(Group group: ((StackedLayoutManager)collGroup.getLayoutManager()).getStackedGroups()){
/*            if (renderDropButton) {
                group.getFooter().getSummaryMessageField().setMessageText("You are already registered for a version of this course!");
                group.getFooter().getSummaryMessageField().addStyleClass("ks-regWarning");
            } else if (renderRemoveFromCartButton) {
                group.getFooter().getSummaryMessageField().setMessageText("Your cart already contains a version of this course!");
                group.getFooter().getSummaryMessageField().addStyleClass("ks-regWarning");
            } else {
                group.getFooter().getSummaryMessageField().setMessageText("Add a course to your cart or register with a single click:");
                group.getFooter().getSummaryMessageField().getStyleClasses().remove("ks-regWarning");
            }*/
            for (Component c : group.getFooter().getItems()) {
                if(c instanceof ActionField){
                    if((((ActionField)c).getMethodToCall().equals("addToCart") || ((ActionField)c).getMethodToCall().equals("registerClass"))){
                        if(renderRegButtons){
                           c.setRender(true);
                        }
                        else{
                           c.setRender(false);
                        }
                    }
                }
            }
        }
    }

    public void checkSubmitItemRender(Component component, RegistrationForm form){
        boolean render = false;
        if(form.getRegRequest() != null && form.getRegRequest().getRegRequestItems() != null
                && !form.getRegRequest().getRegRequestItems().isEmpty()){
            render = true;
        }
        component.setRender(render);
    }

    public void checkRenderCartEmpty(Component component, RegistrationForm form){
        boolean render = true;
        if(form.getRegRequest() != null && form.getRegRequest().getRegRequestItems() != null
                && !form.getRegRequest().getRegRequestItems().isEmpty()){
            render = false;
        }
        component.setRender(render);
    }
}