/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * <p/>
 * http://www.osedu.org/licenses/ECL-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 * <p/>
 *
 * Date: 14-Jul-2010
 * Time: 10:52:16 AM
 */
package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;

/**
 * This abstract class will create a layout with a header containing supplied text, a help action button
 * and optionally a delete action button.
 *
 * Method setLayoutTitle should be overridden in the subclass to set the correct html level of SectionTitle for that instance
 *
 */
public abstract class HeadedLayout extends FieldLayout {
    protected FlowPanel body = new FlowPanel();
    protected VerticalFieldLayout verticalLayout = new VerticalFieldLayout();
    protected Header header;
    protected boolean updateable;
    protected SectionTitle title;

    public HeadedLayout( ){
    	super();
 		this.hasValidation = true;
 		this.add(body);
  	
    }

    protected abstract void buildHeader(String titleText, boolean updateable) ;
    
    public abstract void setLayoutTitle(SectionTitle layoutTitle) ;


	@Override
    public void addFieldToLayout(FieldElement field) {
        verticalLayout.addField(field);

    }

    @Override
    public void addLayoutToLayout(FieldLayout layout) {
        verticalLayout.addLayoutToLayout(layout);

    }

    @Override
    public void addWidgetToLayout(Widget widget) {
        verticalLayout.addWidgetToLayout(widget);
    }

    @Override
    public void removeFieldLayoutComponentFromLayout(
            FieldLayoutComponent component) {
        verticalLayout.removeFieldLayoutComponentFromLayout(component);

    }

    @Override
    public void removeWidgetFromLayout(Widget widget) {
        verticalLayout.removeWidgetFromLayout(widget);
    }

     @Override
    public void addButtonLayoutToLayout(ButtonLayout buttonLayout) {
        if(buttonLayout != null){
            body.add(buttonLayout);
        }

    }

    public void addDeleteHandler(ClickHandler handler) {
        getHeader().addDeleteHandler(handler);

    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }



}
