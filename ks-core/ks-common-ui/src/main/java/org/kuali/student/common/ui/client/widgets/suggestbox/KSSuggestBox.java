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

package org.kuali.student.common.ui.client.widgets.suggestbox;

import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.TranslatableValueWidget;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.HasSelectionChangeHandlers;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;

// TODO implement some form of focus handling for SuggestBox
public class KSSuggestBox extends SuggestBox implements HasSelectionChangeHandlers, TranslatableValueWidget {
    
    private IdableSuggestion currentSuggestion = null;
    private IdableSuggestOracle oracle;
    private String currentId = "";
    
    public KSSuggestBox(IdableSuggestOracle oracle) {
    	this(oracle, true);
    }
    
    public KSSuggestBox(IdableSuggestOracle oracle, boolean enabled){
    	super(oracle, new KSTextBox());
    	super.getTextBox().setEnabled(enabled);
        this.oracle = oracle;
        oracle.addSearchCompletedCallback(new Callback<IdableSuggestion>() {
            @Override
            public void exec(IdableSuggestion result) {
                currentSuggestion = result;
                currentId = KSSuggestBox.this.getSelectedId();
                SelectionChangeEvent.fire(KSSuggestBox.this);
            }
        });
        this.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>(){

            @Override
            public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event) {
                currentSuggestion = (IdableSuggestion)(event.getSelectedItem());
                currentId = KSSuggestBox.this.getSelectedId();
                SelectionChangeEvent.fire(KSSuggestBox.this);
            }
        });
        
        this.getTextBox().addBlurHandler(new BlurHandler(){

            @Override
            public void onBlur(BlurEvent event) {
                String currentText = KSSuggestBox.this.getText();
                if(currentText != null && !currentText.equals("")){
                	if((currentSuggestion != null && !KSSuggestBox.this.getText().equals(currentSuggestion.getReplacementString()))
                			|| currentSuggestion == null){
                		currentSuggestion = KSSuggestBox.this.oracle.getSuggestionByText(currentText);
                	}
                    
                    if(currentSuggestion == null){
                    	currentSuggestion = new IdableSuggestion();
                    	currentSuggestion.setId("");
                    	currentSuggestion.setDisplayString("");
                    	currentSuggestion.setReplacementString("");
                    }
                }
                else if(currentText == null || currentText.equals("")){
                	currentSuggestion = new IdableSuggestion();
                	currentSuggestion.setId("");
                	currentSuggestion.setDisplayString("");
                	currentSuggestion.setReplacementString("");                	
                }
                
                if(!KSSuggestBox.this.getSelectedId().equals(currentId)){
                	currentId = KSSuggestBox.this.getSelectedId();
                	SelectionChangeEvent.fire(KSSuggestBox.this);
                }                
            }
        });
    }
    
    public void reset(){
        this.setText("");
        currentSuggestion = null;
    }

    public IdableSuggestion getSelectedSuggestion() {
        return currentSuggestion;
    }   
    
    public String getSelectedId() {
        String id = "";
        if(currentSuggestion != null){
            id = currentSuggestion.getId();
        }
        return id;
    }

    public IdableSuggestOracle getOracle() {
        return oracle;
    }

    @Override
    public String getValue() {
        return this.getSelectedId();
    }

    @Override
    public void setValue(String id) {
    	if(id == null || id.equals("")){
        	currentSuggestion = new IdableSuggestion();
        	currentSuggestion.setId("");
        	currentSuggestion.setDisplayString("");
        	currentSuggestion.setReplacementString("");
        	KSSuggestBox.this.setText("");
        	currentId = KSSuggestBox.this.getSelectedId();
    	}
    	else
    	{
	        oracle.getSuggestionByIdSearch(id, new Callback<IdableSuggestion>(){
	
	            @Override
	            public void exec(IdableSuggestion result) {
	                currentSuggestion = result;
	                KSSuggestBox.this.setText((currentSuggestion == null) ? "" : currentSuggestion.getReplacementString());
	                currentId = KSSuggestBox.this.getSelectedId();
	            }
	        });
    	}
    }   
    
    public void setValue(String id, final Callback<IdableSuggestion> callback) {
    	if(id == null || id.equals("")){
        	currentSuggestion = new IdableSuggestion();
        	currentSuggestion.setId("");
        	currentSuggestion.setDisplayString("");
        	currentSuggestion.setReplacementString("");
        	KSSuggestBox.this.setText("");
        	currentId = KSSuggestBox.this.getSelectedId();
        	callback.exec(currentSuggestion);
    	}
    	else
    	{
	        oracle.getSuggestionByIdSearch(id, new Callback<IdableSuggestion>(){
	
	            @Override
	            public void exec(IdableSuggestion result) {
	                currentSuggestion = result;
	                KSSuggestBox.this.setText((currentSuggestion == null) ? "" : currentSuggestion.getReplacementString());
	                currentId = KSSuggestBox.this.getSelectedId();
	                callback.exec(currentSuggestion);
	            }
	        });
    	}
    }
    
    public void setValue(String id, String translation) {
    	currentSuggestion = new IdableSuggestion();
    	currentSuggestion.setId(id);
    	currentSuggestion.setDisplayString(translation);
    	currentSuggestion.setReplacementString(translation);
    	KSSuggestBox.this.setText(translation);
    	currentId = KSSuggestBox.this.getSelectedId();
    }
    
    @Override
    public void setValue(String id, boolean fireEvents) {
    	if(fireEvents == true){
    		
	    	if(id == null || id.equals("")){
	        	currentSuggestion = new IdableSuggestion();
	        	currentSuggestion.setId("");
	        	currentSuggestion.setDisplayString("");
	        	currentSuggestion.setReplacementString("");
	        	KSSuggestBox.this.setText("");
	        	currentId = KSSuggestBox.this.getSelectedId();
	    	}
	    	else
	    	{
		        oracle.getSuggestionByIdSearch(id, new Callback<IdableSuggestion>(){
		
		            @Override
		            public void exec(IdableSuggestion result) {
		                currentSuggestion = result;
		                KSSuggestBox.this.setText((currentSuggestion == null) ? "" : currentSuggestion.getReplacementString());         
		            	if(!KSSuggestBox.this.getSelectedId().equals(currentId)){
		            		SelectionChangeEvent.fire(KSSuggestBox.this);
		            		currentId = KSSuggestBox.this.getSelectedId();
		            	}
		            }
		        });
	    	}
	    	
    	}
    	else
    	{
    		this.setValue(id);
    	}

    }
    
    public void setValue(IdableSuggestion theSuggestion) {
    	currentSuggestion = theSuggestion;
    	SelectionChangeEvent.fire(KSSuggestBox.this);
    	KSSuggestBox.this.setText((currentSuggestion == null) ? "" : currentSuggestion.getReplacementString());
    	currentId = KSSuggestBox.this.getSelectedId();
    }
    
	@Override
	public HandlerRegistration addSelectionChangeHandler(
			SelectionChangeHandler handler) {
		return addHandler(handler, SelectionChangeEvent.getType());
	}

    public IdableSuggestion getCurrentSuggestion() {
        return currentSuggestion;
    }

    @Override
    public void setValue(Map<String, String> translations) {
        // TODO ryan - THIS METHOD NEEDS JAVADOCS
        
    }

}
