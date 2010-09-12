/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;

// TODO implement some form of focus handling for SuggestBox
public class KSSuggestBox extends SuggestBox{
    
    private IdableSuggestion currentSuggestion = null;
    private IdableSuggestOracle oracle;
    
    public KSSuggestBox(IdableSuggestOracle oracle){

        super(oracle, new KSTextBox());
        this.oracle = oracle;
        this.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>(){

            @Override
            public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event) {
                currentSuggestion = (IdableSuggestion)(event.getSelectedItem());
                ValueChangeEvent.fire(KSSuggestBox.this, KSSuggestBox.this.getSelectedId());
            }
        });
        
        this.getTextBox().addBlurHandler(new BlurHandler(){

            @Override
            public void onBlur(BlurEvent event) {
                String currentText = KSSuggestBox.this.getText();
                if(currentText != null && !currentText.equals("") && currentSuggestion == null){
                    currentSuggestion = KSSuggestBox.this.oracle.getSuggestionByText(currentText);
                    if(currentSuggestion == null){
                    	currentSuggestion = new IdableSuggestion();
                    	currentSuggestion.setId("");
                    	currentSuggestion.setDisplayString("");
                    	currentSuggestion.setReplacementString("");
                    }
                    ValueChangeEvent.fire(KSSuggestBox.this, KSSuggestBox.this.getSelectedId());
                }
                else if(currentText == null || currentText.equals("")){
                	currentSuggestion = new IdableSuggestion();
                	currentSuggestion.setId("");
                	currentSuggestion.setDisplayString("");
                	currentSuggestion.setReplacementString("");
                	ValueChangeEvent.fire(KSSuggestBox.this, KSSuggestBox.this.getSelectedId());
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
        String id = null;
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
    	}
    	else
    	{
	        oracle.getSuggestionByIdSearch(id, new Callback<IdableSuggestion>(){
	
	            @Override
	            public void exec(IdableSuggestion result) {
	                currentSuggestion = result;
	                KSSuggestBox.this.setText((currentSuggestion == null) ? "" : currentSuggestion.getReplacementString());
	                
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
    	}
    	else
    	{
	        oracle.getSuggestionByIdSearch(id, new Callback<IdableSuggestion>(){
	
	            @Override
	            public void exec(IdableSuggestion result) {
	                currentSuggestion = result;
	                KSSuggestBox.this.setText((currentSuggestion == null) ? "" : currentSuggestion.getReplacementString());
	                callback.exec(result);
	            }
	        });
    	}
    }
    
    @Override
    public void setValue(String id, boolean fireEvents) {
    	if(fireEvents == true){
	    	if(id == null || id.equals("")){
	        	currentSuggestion = new IdableSuggestion();
	        	currentSuggestion.setId("");
	        	currentSuggestion.setDisplayString("");
	        	currentSuggestion.setReplacementString("");
	        	ValueChangeEvent.fire(KSSuggestBox.this, KSSuggestBox.this.getSelectedId());
	    	}
	    	else
	    	{
		        oracle.getSuggestionByIdSearch(id, new Callback<IdableSuggestion>(){
		
		            @Override
		            public void exec(IdableSuggestion result) {
		                currentSuggestion = result;
		                ValueChangeEvent.fire(KSSuggestBox.this, KSSuggestBox.this.getSelectedId());
		                KSSuggestBox.this.setText((currentSuggestion == null) ? "" : currentSuggestion.getReplacementString());
		                
		            }
		        });
	    	}
    	}
    	else{
    		this.setValue(id);
    	}
    }
    
    
}
