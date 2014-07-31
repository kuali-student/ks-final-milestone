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
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.component.PropertyReplacer;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.element.Header;
import org.kuali.rice.krad.uif.element.Label;
import org.kuali.rice.krad.uif.element.Message;
import org.kuali.rice.krad.uif.field.ImageField;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.util.LifecycleElement;
import org.kuali.rice.krad.uif.view.ExpressionEvaluator;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *    This component can be used in a Label#richLabelMessage or Header#richHeaderMessage to display an info icon with a
 *    tooltip. Supplying iconToolTipText will cause the info icon to be rendered. Otherwise, it won't be rendered.
 * </p>
 * <p>
 *    In the case of Header it will also pull content from rightGroup. This is useful for displaying a required indicator.
 * </p>
 */
public class CMIconLabelMessage extends Message {

    protected String iconToolTipText;

    @Override
    public void performApplyModel(Object model, LifecycleElement parent) {

        StringBuilder labelText = new StringBuilder();

        //  Labels are handled differently than Headers.
        if (parent instanceof Label) {
            Label parentLabel = (Label) parent;
            labelText.append(parentLabel.getLabelText());
            if (StringUtils.isNotBlank(iconToolTipText)) {
                labelText.append(CurriculumManagementConstants.CM_MESSAGE_ICON_IMAGE_ID);
            }
        } else if (parent instanceof Header){
            labelText.append(((Header) parent).getHeaderText());

            /**
             * Process the property replacers first as right group is configured with that.
             */
            ExpressionEvaluator expressionEvaluator = ViewLifecycle.getExpressionEvaluator();
            if (((Header) parent).getPropertyReplacers() != null) {
                View view = ViewLifecycle.getView();
                for (PropertyReplacer replacer : ((Header) parent).getPropertyReplacers()) {
                    expressionEvaluator.evaluateExpressionsOnConfigurable(view, replacer, parent.getContext());
                }
            }

            /*
             * Pull the content of rightGroup into the header as an inline component. This allows us to put the required
             * indicator (for example) after the header text and before the info icon.
             */
            Group rightGroup = ((Header) parent).getRightGroup();

            if (rightGroup != null){
                Group groupCopy = ComponentUtils.copy(rightGroup);
                rightGroup.setRender(false);
                if (StringUtils.isNotBlank(iconToolTipText)) {
                    labelText.append(CurriculumManagementConstants.CM_MESSAGE_ICON_IMAGE_ID);
                }
                labelText.append(" [0] ");
                List<Component> inlineComponents = new ArrayList<Component>();
                inlineComponents.add(groupCopy);
                setInlineComponents(inlineComponents);
            }
        }

        setMessageText(labelText.toString());

        super.performApplyModel(model, parent);
    }

    @Override
    public void performFinalize(Object model, LifecycleElement parent) {

        if (StringUtils.isNotBlank(iconToolTipText)){
            ImageField iconImageField = null;
            if(getMessageComponentStructure() != null) {
                for (Component component : getMessageComponentStructure()) {
                    if (component instanceof ImageField) {
                        iconImageField = (ImageField) component;
                        iconImageField.getToolTip().setTooltipContent(iconToolTipText);
                        break;
                    }
                }
            }
        }

        super.performFinalize(model, parent);
    }

    /**
     * The content that will be displayed in the info icon tooltip.
     */
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

        if (StringUtils.isNotBlank(iconToolTipText)) {
            if (getInlineComponents() == null || getInlineComponents().isEmpty()) {
                String currentValues[] = new String[1];
                if (getInlineComponents() == null) {
                    currentValues[0] = "fieldLabel.richLabelMessage.inlineComponents is NULL";
                } else if (getInlineComponents().isEmpty()){
                    currentValues[0] = "fieldLabel.richLabelMessage.inlineComponents is empty";
                }
                tracer.createError("To Use 'iconToolTipText' property, FieldLabel should have RichLabelMessage set",currentValues);
            } else if (!(getInlineComponents().get(0) instanceof ImageField)) {
                String currentValues[] ={"fieldLabel.richLabelMessage.inlineComponents[0] " + getInlineComponents().get(0).getClass()};
                tracer.createError("To Use KSIconInputField, it's fieldLabel.richLabelMessage.inlineComponents[0] should be an ImageField",currentValues);
            } else if (((getInlineComponents().get(0)).getToolTip() == null)) {
                String currentValues[] ={"fieldLabel.richLabelMessage.inlineComponents[0].tooTip is NULL "};
                tracer.createError("To Use KSIconInputField, it's fieldLabel.richLabelMessage.inlineComponents[0].toolTip should not be NULL",currentValues);
            }
        }
        super.completeValidation(tracer.getCopy());
    }
}
