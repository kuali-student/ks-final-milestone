package org.kuali.student.common.ui.client.widgets.suggestbox;

import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;

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
    }

    public IdableSuggestion getSelectedSuggestion() {
        return currentSuggestion;
    }
    
    public String getSelectedId() {
        return currentSuggestion.getId();
    }

    public IdableSuggestOracle getOracle() {
        return oracle;
    }
    
}
