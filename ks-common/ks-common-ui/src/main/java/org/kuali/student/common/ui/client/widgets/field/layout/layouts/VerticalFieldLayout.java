/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.ValidationMessagePanel;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A layout which lays out fields and other layouts vertically.  Fields and layouts added will appear
 * in the order they are added.  Validation will appear next to appropriate fields if hasValidation is true.
 * The title will appear above all elements and button layout below all elements.
 * 
 * @author Kuali Student Team
 *
 */
public class VerticalFieldLayout extends FieldLayout {
    private Map<String, FlowPanel> fieldContainers = new HashMap<String, FlowPanel>();

    protected FlowPanel verticalLayout = new FlowPanel();
    private SpanPanel buttonArea = new SpanPanel();

    public VerticalFieldLayout() {
        super();
        hasValidation = true;
        init();
    }

    public VerticalFieldLayout(boolean hasValidation) {
        super();
        this.hasValidation = hasValidation;
        init();
    }

    public VerticalFieldLayout(SectionTitle title) {
        super();
        this.setLayoutTitle(title);
        this.hasValidation = true;
        init();
    }

    public VerticalFieldLayout(Widget titleWidget) {
        super();
        this.setTitleWidget(titleWidget);
        this.hasValidation = true;
        init();
    }

    public VerticalFieldLayout(SectionTitle title, boolean hasValidation) {
        super();
        this.setLayoutTitle(title);
        this.hasValidation = hasValidation;
        init();
    }

    private void init() {
        instructions.setVisible(false);
        this.add(verticalLayout);
        verticalLayout.add(instructions);
        verticalLayout.add(message);
        this.add(buttonArea);
        verticalLayout.setStyleName("ks-form-module");
    }


    @Override
    public void addFieldToLayout(FieldElement field) {
        FlowPanel fieldContainer = new FlowPanel();
        FlowPanel fieldLayout = new FlowPanel();
        fieldContainer.add(field);
        fieldLayout.add(fieldContainer);
        if (hasValidation) {
            ValidationMessagePanel validationPanel = new ValidationMessagePanel();
            fieldLayout.add(validationPanel);
            field.setValidationPanel(validationPanel);
            validationPanel.setStyleName("ks-form-module-validation-inline");
        }
        field.setParentPanel(fieldLayout);
        verticalLayout.add(fieldLayout);
        fieldContainers.put(field.getKey(), fieldLayout);
        fieldLayout.setStyleName("ks-form-module-group");
        fieldLayout.addStyleName("clearfix");
        //field.addStyleName("ks-form-module-single-line-margin");
        fieldContainer.setStyleName("ks-form-module-fields");
    }

    @Override
    public void addLayoutToLayout(FieldLayout layout) {
        verticalLayout.add(layout);
        layout.setParentLayout(this);
    }

    @Override
    public void addWidgetToLayout(Widget widget) {
        widget.addStyleName("ks-section-widget");
        verticalLayout.add(widget);
    }

    @Override
    public void removeWidgetFromLayout(Widget widget) {
        verticalLayout.remove(widget);
    }

    @Override
    public void removeFieldLayoutComponentFromLayout(FieldLayoutComponent component) {
        if (component instanceof FieldElement) {
            FlowPanel panel = fieldContainers.get(component.getKey());
            verticalLayout.remove(panel);
            fieldContainers.remove(component.getKey());
        } else if (component instanceof FieldLayout) {
            verticalLayout.remove((FieldLayout) component);
        }
    }

    @Override
    public void setLayoutTitle(SectionTitle layoutTitle) {
        if (this.layoutTitle != null) {
            verticalLayout.remove(this.layoutTitle);
        }
        if (layoutTitle != null) {
            this.layoutTitle = layoutTitle;
            verticalLayout.insert(layoutTitle, 0);
            layoutTitle.addStyleName("ks-layout-header");
        }
    }

    public void setTitleWidget(Widget layoutTitle) {
        if (this.layoutTitle != null) {
            verticalLayout.remove(this.layoutTitle);
        }
        verticalLayout.insert(layoutTitle, 0);
    }

    @Override
    public void addButtonLayoutToLayout(ButtonLayout buttonLayout) {
        buttonArea.add(buttonLayout);

    }

    public Map<String, FlowPanel> getFieldContainers() {
        return fieldContainers;
    }

	public FlowPanel getVerticalLayout() {
		return verticalLayout;
	}

}