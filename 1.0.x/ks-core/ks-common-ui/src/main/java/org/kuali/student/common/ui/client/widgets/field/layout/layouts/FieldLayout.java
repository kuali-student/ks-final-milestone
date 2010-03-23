package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.InfoMessage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class FieldLayout extends Composite implements FieldLayoutComponent{
	protected Map<String, FieldElement> fieldMap = new HashMap<String, FieldElement>();
	protected Map<String, FieldLayout> layoutMap = new HashMap<String, FieldLayout>();
	protected LinkedHashMap<String, Widget> drawOrder = new LinkedHashMap<String, Widget>();
	protected KSLabel instructions = new KSLabel();
	protected InfoMessage message = new InfoMessage();
	protected FieldLayout parentLayout;
	protected boolean hasValidation = false;
	private static int generatedKeyNum = 0;
	private String key;
	protected SectionTitle layoutTitle = null;
	

	private static String getNextId() { 
		return "fieldComponent" + (generatedKeyNum); 
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
			if(key == null){
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
			this.instructions.setText(instructions);
			this.instructions.setVisible(true);
		}
		else{
			this.instructions.setVisible(false);
		}
	}

	public void setMessage(String text, boolean show) {
		this.message.setMessage(text, show);
	}

	public void showMessage(boolean show) {
		this.message.setVisible(show);
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
	
	protected abstract void addFieldToLayout(FieldElement field);
	protected abstract void addLayoutToLayout(FieldLayout layout);
	protected abstract void addWidgetToLayout(Widget widget);
	protected abstract void removeWidgetFromLayout(Widget widget);
	protected abstract void removeFieldLayoutComponentFromLayout(FieldLayoutComponent component);
	
	public void processValidationResults(String fieldElementKey, List<ValidationResultInfo> validationResults){
		FieldElement field = fieldMap.get(fieldElementKey);
		if(field != null && hasValidation){
			field.processValidationResults(validationResults);
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
	
/*	public void addButton(KSButton button){
		
	}
	
	public void removeButton(KSButton button){
		
	}*/

}
