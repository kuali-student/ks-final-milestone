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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.WarnContainer;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public abstract class FieldLayout extends FlowPanel implements FieldLayoutComponent{
	protected Map<String, FieldElement> fieldMap = new HashMap<String, FieldElement>();
	protected Map<String, FieldLayout> layoutMap = new HashMap<String, FieldLayout>();
	protected LinkedHashMap<String, Widget> drawOrder = new LinkedHashMap<String, Widget>();
	protected SpanPanel instructions = new SpanPanel();
	protected WarnContainer message = new WarnContainer(false);
	protected FieldLayout parentLayout;
	protected boolean hasValidation = false;
	private HTML messagePanel;
	private static int generatedKeyNum = 0;
	private String key;
	private ButtonLayout buttonLayout;
	protected SectionTitle layoutTitle = null;
	private AbbrButton help = null;


	private static String getNextId() {
		return "fieldComponent" + (generatedKeyNum++);
	}

	public void underlineTitle(boolean underline){
		if(layoutTitle != null){
			if(underline){
				layoutTitle.addStyleName("header-underline");
			}
			else{
				layoutTitle.removeStyleName("header-underline");
			}
		}
	}

	public FieldLayout getParentLayout() {
		return parentLayout;
	}

	protected void setParentLayout(FieldLayout parentLayout) {
		this.parentLayout = parentLayout;
	}

	public String addField(FieldElement field){
		String key = null;
		if(field != null){
			if(field.getKey() == null){
				key = getNextId();
				field.setKey(key);
			}
			else{
				key = field.getKey();
			}
			fieldMap.put(key, field);
			drawOrder.put(key, field);
			addFieldToLayout(field);
		}
		return key;
	}

	public String addLayout(FieldLayout layout){
		String key = null;
		if(layout != null){
			if(layout.getKey() == null){
				key = getNextId();
				layout.setKey(key);
			}
			else{
				key = layout.getKey();
			}
			layoutMap.put(key, layout);
			drawOrder.put(key, layout);
			addLayoutToLayout(layout);
		}
		return key;
	}

	public String addWidget(Widget widget){
		return this.addWidget(null, widget);
	}

	public String addWidget(String key, Widget widget){
		if(widget != null){
			if(key == null){
				key = getNextId();
			}
			drawOrder.put(key, widget);
			addWidgetToLayout(widget);
		}
		else{
			key = null;
		}
		return key;
	}

	public boolean removeLayoutElement(String key){
		Widget w = drawOrder.get(key);
		if(w != null){
			//removeElementFromLayout(w);
			fieldMap.remove(key);
			layoutMap.remove(key);
			drawOrder.remove(key);
			if(w instanceof FieldLayoutComponent){
				removeFieldLayoutComponentFromLayout((FieldLayoutComponent)w);
			}
			else{
				removeWidgetFromLayout(w);
			}
			return true;
		}
		else{
			return false;
		}
	}

	public boolean removeLayoutElement(Widget widget){
		if(drawOrder.containsValue(widget)){
			Iterator<String> it = drawOrder.keySet().iterator();
			String matchedKey = null;
			while(it.hasNext()){
				String key = it.next();
				Widget w = drawOrder.get(key);
				if(w.equals(widget)){
					matchedKey = key;
					break;
				}
			}
			fieldMap.remove(matchedKey);
			layoutMap.remove(matchedKey);
			drawOrder.remove(matchedKey);
			if(widget instanceof FieldLayoutComponent){
				removeFieldLayoutComponentFromLayout((FieldLayoutComponent)widget);
			}
			else{
				removeWidgetFromLayout(widget);
			}
			return true;
		}
		else{
			return false;
		}
	}

	public void setInstructions(String instructions) {
		if(instructions != null && !instructions.equals("")){
			this.instructions.addStyleName("ks-section-instuctions");
			this.instructions.setHTML(instructions);
			this.instructions.setVisible(true);
		}
		else{
			this.instructions.setVisible(false);
		}
	}

	public void setMessage(String html, boolean show) {
		if(messagePanel == null){
			messagePanel = new HTML(html);
			message.addWarnWidget(messagePanel);
		}
		else{
			messagePanel.setHTML(html);
		}
		message.setVisible(show);
		message.showWarningLayout(show);
	}

	public void showMessage(boolean show) {
		message.setVisible(show);
		message.showWarningLayout(show);
	}
	
	public WarnContainer getMessageWarnContainer(){
		return message;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public void setKey(String layoutKey) {
		key = layoutKey;
	}

	public FieldElement getFieldElement(String key){
		return fieldMap.get(key);
	}

	public FieldLayout getFieldLayout(String key){
		return layoutMap.get(key);
	}

	public Widget getWidget(String key){
		return drawOrder.get(key);
	}

	public abstract void addFieldToLayout(FieldElement field);
	public abstract void addLayoutToLayout(FieldLayout layout);
	public abstract void addWidgetToLayout(Widget widget);
	public abstract void removeWidgetFromLayout(Widget widget);
	public abstract void removeFieldLayoutComponentFromLayout(FieldLayoutComponent component);

	public void processValidationResults(String fieldElementKey, ValidationResultInfo validationResult){
		FieldElement field = fieldMap.get(fieldElementKey);
		if(field != null && hasValidation){
			field.processValidationResult(validationResult);
		}
	}

	public void addValidationErrorMessage(String fieldElementKey, String message){
		FieldElement field = fieldMap.get(fieldElementKey);
		if(field != null && hasValidation){
			field.addValidationErrorMessage(message);
		}
	}

	public void clearValidation(){
		//fieldMap.
		for(FieldElement e: fieldMap.values()){
			e.clearValidationPanel();
		}
		for(FieldLayout layout: layoutMap.values()){
			layout.clearValidation();
		}
	}
	//protected abstract void redraw();

	public abstract void setLayoutTitle(SectionTitle layoutTitle);

	public SectionTitle getLayoutTitle() {
		return layoutTitle;
	}

	public void addButtonLayout(ButtonLayout buttonLayout){
		this.buttonLayout = buttonLayout;
		addButtonLayoutToLayout(buttonLayout);
	}

	public ButtonLayout getButtonLayout(){
		return buttonLayout;
	}

	public abstract void addButtonLayoutToLayout(ButtonLayout buttonLayout);
	
	public void setHelp(String html){
		if(layoutTitle != null){
			if(help == null){
				help = new AbbrButton(AbbrButtonType.HELP);
				layoutTitle.add(help);
			}
			
	    	if(html != null && !html.trim().equals("")){
	    		help.setVisible(true);
	    		help.setHoverHTML(html);
	    	}
	    	else{
	    		help.setVisible(false);
	    	}
	    	
	    	
		}
	}

}
