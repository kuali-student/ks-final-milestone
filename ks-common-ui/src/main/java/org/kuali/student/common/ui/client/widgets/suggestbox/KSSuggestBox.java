package org.kuali.student.common.ui.client.widgets.suggestbox;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;

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
            }
        });
        
        this.getTextBox().addBlurHandler(new BlurHandler(){

            @Override
            public void onBlur(BlurEvent event) {
                String currentText = KSSuggestBox.this.getText();
                if(currentText != null && currentText != "" && currentSuggestion == null){
                    currentSuggestion = KSSuggestBox.this.oracle.getSuggestionByText(currentText);
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
        oracle.getSuggestionByIdSearch(id, new Callback<IdableSuggestion>(){

            @Override
            public void exec(IdableSuggestion result) {
                currentSuggestion = result;
                KSSuggestBox.this.setText((currentSuggestion == null) ? "" : currentSuggestion.getReplacementString());
                
            }
        });
    }
    
    
    
}
