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

package org.kuali.student.common.ui.client.widgets.field.layout.element;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.reporting.ReportExportWidget;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.client.util.ExportUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class SpanPanel extends ComplexPanel implements ReportExportWidget {
	
	private String html;

	public SpanPanel(){
		setElement(DOM.createSpan());
	}
	
	public SpanPanel(String text){
		Element span = DOM.createSpan();
		span.setInnerText(text);
		setElement(span);
	}
	
	  /**
	   * Adds a new child widget to the panel.
	   * 
	   * @param w the widget to be added
	   */
	  @Override
	  public void add(Widget w) {
	    add(w, getElement());
	  }

	  /**
	   * Inserts a widget before the specified index.
	   * 
	   * @param w the widget to be inserted
	   * @param beforeIndex the index before which it will be inserted
	   * @throws IndexOutOfBoundsException if <code>beforeIndex</code> is out of
	   *           range
	   */
	  public void insert(Widget w, int beforeIndex) {
	    insert(w, getElement(), beforeIndex, true);
	  }
	  
	  public void setText(String text){
		  this.getElement().setInnerText(text);
	  }
	  
	  public void setHTML(String html){
		  this.getElement().setInnerHTML(html);
		  this.html = html;
	  }

	public String getHtml() {
		return html;
	}

	@Override
	public ArrayList<ExportElement> getExportElementsWidget(String viewName,
			String sectionName) {
		ArrayList<ExportElement> returnItems = new ArrayList<ExportElement>();
		//
		ExportElement element = new ExportElement(viewName,sectionName);
		element.setFieldValue(this.getHtml());

		//
		if (this.getHtml() == null) {
			returnItems = ExportUtils.getDetailsForWidget(this, returnItems, viewName, sectionName);
		}
		//
		returnItems = ExportUtils.addElementToElementArray(returnItems, element);
		return returnItems;
	}

}
