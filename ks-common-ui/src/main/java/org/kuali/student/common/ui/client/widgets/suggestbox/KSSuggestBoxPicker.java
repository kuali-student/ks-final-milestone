package org.kuali.student.common.ui.client.widgets.suggestbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.list.KSPickList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.UpdatableListItems;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;
import org.kuali.student.core.dto.Idable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestionEvent;
import com.google.gwt.user.client.ui.SuggestionHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class KSSuggestBoxPicker extends Composite{
    //private VerticalPanel layout = new VerticalPanel();
    //private HorizontalPanel suggestRow = new HorizontalPanel();
    //private HorizontalPanel listRow = new HorizontalPanel();
    
    private FlexTable layout = new FlexTable();
    private KSButton remove = new KSButton("Remove");
    //private KSButton add = new KSButton("Add");
    private KSSelectItemWidgetAbstract listWidget;
    private KSSuggestBoxWAdvSearch suggest;
    
    private Map<String, IdableSuggestion> addedSuggestions = new HashMap<String, IdableSuggestion>();
    
    private ListItems selectedItems = new ListItems(){

        @Override
        public List<String> getAttrKeys() {
            return suggest.getSuggestBox().getOracle().getAttrKeys();
        }

        @Override
        public String getItemAttribute(String id, String attrkey) {
            IdableSuggestion i = addedSuggestions.get(id);
            return i.getAttrMap().get(attrkey);
        }

        @Override
        public int getItemCount() {
            return addedSuggestions.size();
        }

        @Override
        public List<String> getItemIds() {
            List<String> ids = new ArrayList<String>();
            ids.addAll(addedSuggestions.keySet());
            return ids;
        }

        @Override
        public String getItemText(String id) {
            IdableSuggestion i = addedSuggestions.get(id);
            return i.getReplacementString();
        }
    };
    
    public KSSuggestBoxPicker(KSSuggestBoxWAdvSearch suggestWidget, KSSelectItemWidgetAbstract widget){
        suggest = suggestWidget;
        listWidget = widget;
        layout.setWidget(0, 0, suggest);
        //layout.setWidget(0, 1, add);
        listWidget.setListItems(selectedItems);
        layout.setWidget(1, 0, listWidget);
        layout.setWidget(1, 1, remove);
        layout.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);

        this.setupHandlers();
        layout.getRowFormatter().addStyleName(1, KSStyles.KS_SUGGEST_PICKER_LIST_ROW);
        layout.addStyleName(KSStyles.KS_SUGGEST_PICKER_LAYOUT_TABLE);
        this.initWidget(layout);
    }
    
    private void setupHandlers(){
        
        suggest.getSuggestBox().addSelectionHandler(new SelectionHandler<Suggestion>(){

            @Override
            public void onSelection(SelectionEvent<Suggestion> event) {
                if(suggest.getSuggestBox().getSelectedSuggestion() != null){
                    addedSuggestions.put(suggest.getSuggestBox().getSelectedId(), suggest.getSuggestBox().getSelectedSuggestion());
                    listWidget.redraw();
                    suggest.getSuggestBox().setText("");
                } 
            }
        });
        
        
/*        add.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                if(suggest.getSuggestBox().getSelectedSuggestion() != null){
                    addedSuggestions.put(suggest.getSuggestBox().getSelectedId(), suggest.getSuggestBox().getSelectedSuggestion());
                    listWidget.redraw();
                    suggest.getSuggestBox().setText("");
                }  
            }
        });*/
        
        
        suggest.getSearchWindow().addConfirmHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
               List<String> ids = suggest.getSearchWindow().getSelectedIds();
               for(String id: ids){
                   addedSuggestions.put(id, suggest.getSuggestBox().getOracle().getSuggestion(id));
               }
               listWidget.redraw();
               suggest.getSearchWindow().hide();
               suggest.getSuggestBox().getTextBox().setText("");
            }
        });
        
        remove.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                List<String> selectedIds = listWidget.getSelectedItems();
                if(!selectedIds.isEmpty()){
                    for(String id: selectedIds){
                        addedSuggestions.remove(id);
                    }
                }
                listWidget.redraw();
            }
        });
        
    }
    
}
