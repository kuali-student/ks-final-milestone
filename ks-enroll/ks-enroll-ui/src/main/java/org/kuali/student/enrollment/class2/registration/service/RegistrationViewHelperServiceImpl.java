package org.kuali.student.enrollment.class2.registration.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.field.ActionField;
import org.kuali.rice.krad.uif.field.MessageField;
import org.kuali.rice.krad.uif.layout.StackedLayoutManager;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.student.enrollment.class2.registration.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.registration.form.RegistrationForm;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;

import java.util.List;

public class RegistrationViewHelperServiceImpl extends ViewHelperServiceImpl {

    public void checkRegElements(CollectionGroup collGroup, RegistrationForm form){
        List<CourseRegistrationInfo> regs = form.getCourseRegistrations();

        boolean courseRegistered = false;
        boolean courseInCart = false;

        for(Group group: ((StackedLayoutManager)collGroup.getLayoutManager()).getStackedGroups()){
            RegistrationGroupWrapper registrationGroupWrapper = (RegistrationGroupWrapper)group.getContext().get(UifConstants.ContextVariableNames.LINE);
            boolean renderRegButtons = true;
            boolean renderDropButton = false;
            boolean renderRemoveFromCartButton = false;
            String id = registrationGroupWrapper.getRegistrationGroup().getId();

            if(StringUtils.isNotBlank(id)){

                for (CourseRegistrationInfo reg : regs) {
                    if (reg.getRegGroupRegistration().getRegistrationGroup().getId().equals(id)) {
                        renderDropButton = true;
                        courseRegistered = true;
                        renderRegButtons = false;
                        break;
                    }
                }

                if (form.getRegRequest() != null && form.getRegRequest().getRegRequestItems() != null) {
                    for (RegRequestItemInfo regRequestItemInfo : form.getRegRequest().getRegRequestItems()) {
                        String regGroupId = regRequestItemInfo.getNewRegGroupId();
                        if (StringUtils.isNotBlank(regGroupId)) {
                            // find the regGroupWrapper that matches the id from the supplemental list
                            //RegistrationGroupWrapper regGroupWrapper = form.getRegistrationGroupWrappersById().get(regGroupId);
                            if (regGroupId.equals(id)) {
                                renderRemoveFromCartButton = true;
                                courseInCart = true;
                                renderRegButtons = false;
                                break;
                            }
                        }
                    }
                }
            }
            for (Component c : group.getFooter().getItems()) {

                if(c instanceof ActionField){
                    if(((ActionField)c).getMethodToCall().equals("addToCart") || (((ActionField)c).getMethodToCall().equals("registerClass"))){
                        c.setRender(renderRegButtons);
                    }
                    else if(((ActionField)c).getMethodToCall().equals("removeFromCart")){
                        c.setRender(renderRemoveFromCartButton);
                    }
                    else if(((ActionField)c).getMethodToCall().equals("dropClass")){
                        c.setRender(renderDropButton);
                    }
                }
            }
            if(!renderRegButtons && renderDropButton){
                MessageField message = ComponentFactory.getMessageField();
                message.setMessageText("You are currently registered for this course.");
                ((List<Component>)group.getFooter().getItems()).add(0, message);
            }
            else if(!renderRegButtons && renderRemoveFromCartButton){
                MessageField message = ComponentFactory.getMessageField();
                message.setMessageText("This course is currently in your registration cart");
                ((List<Component>)group.getFooter().getItems()).add(0, message);
            }
        }


        if(courseRegistered){
           collGroup.getSummaryMessageField().setMessageText("You are currently registered for a version of this course.");
           collGroup.getSummaryMessageField().addStyleClass("ks-regWarning");
        }
        else if(courseInCart){
           collGroup.getSummaryMessageField().setMessageText("Your cart contains a version of this course.");
           collGroup.getSummaryMessageField().addStyleClass("ks-regWarning");
        }
        else{
           collGroup.getSummaryMessageField().setMessageText("Add a course to your cart or register with a single click:");
           collGroup.getSummaryMessageField().getStyleClasses().remove("ks-regWarning");
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