/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by venkat on 2/28/14
 */
package org.kuali.student.cm.uif.element;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.datadictionary.parse.BeanTagAttribute;
import org.kuali.rice.krad.datadictionary.validator.ValidationTrace;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.element.Header;
import org.kuali.rice.krad.uif.element.Label;
import org.kuali.rice.krad.uif.element.Message;
import org.kuali.rice.krad.uif.field.ImageField;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.util.LifecycleElement;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 *
 * <p>
 * The <code>KSIconLabelMessage</code> is used to display an 'info' icon and
 * hovering over the icon will display a tooltip.
 * </p>
 *
 * @author Kuali Student Team
 */

public class KSIconLabelMessage extends Message {

    protected String iconToolTipText;

    /**
     * If iconToolTipText is configured, it configures that text to be displayed as tooltip over the icon.
     * Here, we're adding 2 inline components. The first one is a placement for required message component
     * to be displayed. The second one is to display the icon
     *
     * @param model
     * @param parent
     */
    @Override
    public void performApplyModel(Object model, LifecycleElement parent) {

        if (StringUtils.isNotBlank(iconToolTipText)){

            ImageField iconImageField = null;
            try {
                iconImageField = (ImageField) KSCollectionUtils.getRequiredZeroElement(getInlineComponents());
            } catch (OperationFailedException e) {
                throw new RuntimeException(e);
            }

            String label = "";

            if (parent instanceof Label){
                label = ((Label) parent).getLabelText();
                /**
                 * Rice 2.4 Upgrade: alteration made here TODO: KSCM-2000 Required Message Changes
                 *
                 * Manually add in the required indicator (is this necessary now?)
                 */
//                Label parentObject = (Label)parent;
//                label = parentObject.getLabelText() + " " + parentObject.getRequiredIndicator() + " [0]";
//                parentObject.setLabelText(label);

            } else if (parent instanceof Header){
                label =  ((Header)parent).getHeaderText();
                /** If there are any components configured to display as right group, move that
                 * component to the middle sothat icon always display as last component.
                 */
                Group group = ((Header) parent).getRightGroup();

                if (group != null){

                    Group groupCopy = ComponentUtils.copy(group);
                    group.setRender(false);

                    label = label + " [0] [1]";

                    getInlineComponents().add(0, groupCopy);
                } else {
                    label = label + " [0]";
                }

            }

            setMessageText(label);
            iconImageField.getToolTip().setTooltipContent(iconToolTipText);

        }

        super.performApplyModel(model, parent);
    }

    @Override
    public void performFinalize(Object model, LifecycleElement parent) {

        super.performFinalize(model, parent);

        if (StringUtils.isNotBlank(iconToolTipText)){
            if (parent instanceof Label){
                /**
                 * Hide the Label's required message component. it's not needed to be displayed
                 * as the Label's inline component will be displaying that.
                 */
                // rice 2.4 upgrade: label does not have requiredMessage any more TODO: KSCM-2000 Required Message Changes
/*
                Message copy = ComponentUtils.copy(((Label) parent).getRequiredMessage());
                ((Label) parent).setRequiredMessage(copy);
                ((Label) parent).getRequiredMessage().setRender(false);
*/
            }
        }

    }

    @BeanTagAttribute(name="iconToolTipText")
    public String getIconToolTipText() {
        return iconToolTipText;
    }

    public void setIconToolTipText(String iconToolTipText) {
        this.iconToolTipText = iconToolTipText;
    }

    @Override
    public void completeValidation(ValidationTrace tracer) {
        tracer.addBean(this);

        if (StringUtils.isNotBlank(iconToolTipText)){

            if (getInlineComponents() == null || getInlineComponents().isEmpty()){
                String currentValues[] = new String[1];
                if (getInlineComponents() == null){
                    currentValues[0] = "fieldLabel.richLabelMessage.inlineComponents is NULL";
                } else if (getInlineComponents().isEmpty()){
                    currentValues[0] = "fieldLabel.richLabelMessage.inlineComponents is empty";
                }
                tracer.createError("To Use 'iconToolTipText' property, FieldLabel should be having RichLabelMessage set",currentValues);
            } else if (!(getInlineComponents().get(0) instanceof ImageField)){
                String currentValues[] ={"fieldLabel.richLabelMessage.inlineComponents[0] " + getInlineComponents().get(0).getClass()};
                tracer.createError("To Use KSIconInputField, it's fieldLabel.richLabelMessage.inlineComponents[0] should be an ImageField",currentValues);
            } else if (((getInlineComponents().get(0)).getToolTip() == null)){
                String currentValues[] ={"fieldLabel.richLabelMessage.inlineComponents[0].tooTip is NULL "};
                tracer.createError("To Use KSIconInputField, it's fieldLabel.richLabelMessage.inlineComponents[0].toolTip should not be NULL",currentValues);
            }
        }

        super.completeValidation(tracer.getCopy());
    }
}
