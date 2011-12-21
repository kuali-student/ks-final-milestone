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

package org.kuali.student.common.ui.client.configurable.mvc;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class provides static methods for generating H1-H6 elements with the passed in text
 *
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class SectionTitle extends ComplexPanel {

    private String reportText;

    private SectionTitle(Element e) {
        this.setElement(e);
    }

    public static SectionTitle generateEmptyTitle() {
        return generateTitle(DOM.createSpan(), null);
    }

    public static SectionTitle generateH1Title(String titletext) {

    	Element headerElement = DOM.createElement("H1");
    	headerElement.setInnerText(titletext);
        return generateTitle(headerElement, "KS-H1-Section-Title");
    }

    public static SectionTitle generateH2Title(String titletext) {

    	Element headerElement = DOM.createElement("H2");
    	headerElement.setInnerText(titletext);
        return generateTitle(headerElement, "KS-H2-Section-Title");
    }

    public static SectionTitle generateH3Title(String titletext) {

    	Element headerElement = DOM.createElement("H3");
    	headerElement.setInnerText(titletext);
        return generateTitle(headerElement, "KS-H3-Section-Title");
    }

    public static SectionTitle generateH4Title(String titletext) {

    	Element headerElement = DOM.createElement("H4");
    	headerElement.setInnerText(titletext);
        return generateTitle(headerElement, "KS-H4-Section-Title");
    }

    public static SectionTitle generateH5Title(String titletext) {

    	Element headerElement = DOM.createElement("H5");
    	headerElement.setInnerText(titletext);
        return generateTitle(headerElement, "KS-H5-Section-Title");
    }

    public static SectionTitle generateH6Title(String titletext) {

    	Element headerElement = DOM.createElement("H6");
    	headerElement.setInnerText(titletext);
        return generateTitle(headerElement, "KS-H6-Section-Title");
    }

    private static SectionTitle generateTitle(Element header, String styleName) {
    	SectionTitle thisTitle = new SectionTitle(header);
    	thisTitle.addStyleName("KS-Section-Title");
        if(styleName != null){
        	thisTitle.addStyleName(styleName);
        }
        return thisTitle;
    }

    public void setText(String text){
    	this.getElement().setInnerText(text);
    	this.reportText = text;
    }
    
    public void setHTML(String html){
    	this.getElement().setInnerHTML(html);
    	this.reportText = html;
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
	 * Returns a text value of the title for the export report.
	 * 
	 * @return
	 */
	public String getExportFieldValue() {
	    if ((this.reportText != null) && (this.reportText.length() > 0)){
	        return this.reportText;
	    }
	    return this.getElement().getInnerText();
	}
}


