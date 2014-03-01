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

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.datadictionary.parse.BeanTagAttribute;
import org.kuali.rice.krad.datadictionary.validator.ValidationTrace;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.element.Header;
import org.kuali.rice.krad.uif.element.Label;
import org.kuali.rice.krad.uif.element.Message;
import org.kuali.rice.krad.uif.field.ImageField;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.View;

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

    private String iconToolTipText;

    /**
     * If iconToolTipText is configured, it configures that text to be
     * displayed as tooltip over the icon.
     *
     * @param view
     * @param model
     * @param parent
     */
    @Override
    public void performApplyModel(View view, Object model, Component parent) {

        if (StringUtils.isNotBlank(iconToolTipText)){
            Component component = (Component)parent.getContext().get("parent");
            String label = "";
            if (parent instanceof Label){

                label = ((Label)parent).getLabelText();
                boolean isRequiredField = isParentRequiredField(component);

                if (isRequiredField){
                    label = label + " * [0]";
                } else {
                    label = label + " [0]";
                }

                ((Label)parent).setLabelText(label);

            } else if (parent instanceof Header){
                label =  ((Header)parent).getHeaderText();
                label = label + " [0]";
            }

            setMessageText(label);
            ImageField iconImageField = (ImageField)getInlineComponents().get(0);
            iconImageField.getToolTip().setTooltipContent(iconToolTipText);

        }

        super.performApplyModel(view, model, parent);
    }

    @BeanTagAttribute(name="iconToolTipText")
    public String getIconToolTipText() {
        return iconToolTipText;
    }

    public void setIconToolTipText(String iconToolTipText) {
        this.iconToolTipText = iconToolTipText;
    }

    /**
     * Returns true if the parent component is a required field.
     * If true, it appends an * in between the label and the icon.
     *
     * @param component
     * @return
     */
    protected boolean isParentRequiredField(Component component){
        if (component instanceof  InputField && ((InputField) component).getSimpleConstraint() != null && BooleanUtils.isTrue(((InputField) component).getSimpleConstraint().isRequired())){
            return true;
        } else {
            return BooleanUtils.isTrue(component.getRequired());
        }
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

    /**
     * @see org.kuali.rice.krad.uif.component.ComponentBase#copy()
     */
    @Override
    protected <T> void copyProperties(T component) {
        super.copyProperties(component);

        KSIconLabelMessage inputFieldCopy = (KSIconLabelMessage) component;
        inputFieldCopy.setIconToolTipText(getIconToolTipText());
    }

}
