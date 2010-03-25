package org.kuali.student.lum.lu.ui.tools.client.configuration;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ButtonEnum;
import org.kuali.student.common.ui.client.widgets.buttonlayout.ButtonRow;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.core.assembly.data.Data.StringValue;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.CluItemValue;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.ItemList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class AddCluLightBox {
    private KSLightBox dialog = new KSLightBox();
    private KSLabel titleBar = null;
    private List<Callback<List<CluItemValue>>> callbacks = new ArrayList<Callback<List<CluItemValue>>>();
    private VerticalFlowPanel layout = new VerticalFlowPanel();
    private KSPicker searchClu;
    private ItemList<CluItemValue> cluItemList;
    private SelectedResults basicSelectionResult;
    private KSButton addToList;
    
    public static enum AddCluButtonsEnum implements ButtonEnum {
        ADD_COURSE, CANCEL;

        public String getText() {
            switch(this) {
                case ADD_COURSE:
                    return "Add Course";
                case CANCEL:
                    return "Cancel";
            }
            return null;
        }
    }
    
    public static enum Style {
        PRIMARY("KS-Advanced-Search-Window");

        private String style;

        private Style(String style) {
            this.style = style;
        }

        public String getStyle() {
            return style;
        }
    };
    
    public class AddCluButtonsGroup extends ButtonGroup<AddCluButtonsEnum> {
        public AddCluButtonsGroup(Callback<AddCluButtonsEnum> callback) {
            layout = new ButtonRow();
            this.addCallback(callback);
            
            addButton(AddCluButtonsEnum.CANCEL);
            addButtonToSecondaryGroup(AddCluButtonsEnum.ADD_COURSE);
            
            this.initWidget(layout);
        }
        
        private void addButton(final AddCluButtonsEnum type) {
            KSButton button = new KSButton(type.getText(), new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    sendCallbacks(type);
                }
            });
            layout.addButton(button);
            buttonMap.put(type, button);
        }
        
        private void addButtonToSecondaryGroup(final AddCluButtonsEnum type) {
            KSButton button = new KSButton(type.getText(), new ClickHandler(){
                
                @Override
                public void onClick(ClickEvent event) {
                    sendCallbacks(type);
                }
            });
            ((ButtonRow)layout).addButtonToSecondaryGroup(button);
            buttonMap.put(type, button);
        }
    }

    private AddCluButtonsGroup buttonPanel = new AddCluButtonsGroup(new Callback<AddCluButtonsEnum>(){
        @Override
        public void exec(AddCluButtonsEnum result) {
            switch(result){
                case ADD_COURSE:
                    for(Callback<List<CluItemValue>> callback: callbacks){
                        callback.exec(cluItemList.getValue());
                    }
                    dialog.hide();
                    break;
                case CANCEL:
                    dialog.hide();
                    break;
            }
        }
    });
    
    public AddCluLightBox(KSPicker searchClu) {
        layout.addStyleName(Style.PRIMARY.getStyle());
        titleBar = new KSLabel("Add Courses");
        titleBar.addStyleName("KS-Advanced-Search-Header-Label");
        KSLabel instructions = new KSLabel(
                "Add courses individually, or use advanced search to batch select and add.");
        addToList = new KSButton("Add to list");
        HorizontalPanel courseSearchPanel = new HorizontalPanel();
        cluItemList = new ItemList<CluItemValue>();
        addToList.setEnabled(false);
        
        courseSearchPanel.add(searchClu);
        courseSearchPanel.add(addToList);
        
        this.searchClu = searchClu;
        
        searchClu.addBasicSelectionTextChangeCallback(new Callback<String>() {
            @Override
            public void exec(String result) {
                addToList.setEnabled(false);
            }
        });
        
        searchClu.addBasicSelectionCompletedCallback(new Callback<SelectedResults>() {
            @Override
            public void exec(SelectedResults result) {
                basicSelectionResult = result;
                addToList.setEnabled(true);
            }
        });
        
        searchClu.getSearchWindow().addSelectionCompleteCallback(new Callback<List<SelectedResults>>() {
            @Override
            public void exec(List<SelectedResults> results) {
                addSelectedResultsToList(results);
            }
        });
        
        addToList.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (basicSelectionResult != null) {
                    List<SelectedResults> results = new ArrayList<SelectedResults>(1);
                    results.add(basicSelectionResult);
                    addSelectedResultsToList(results);
                }
            }
        });
        
        layout.add(titleBar);
        layout.add(instructions);
        layout.add(courseSearchPanel);
        layout.add(cluItemList);
        layout.add(buttonPanel);
        dialog.setWidget(layout);
    }
    
    private void addSelectedResultsToList(List<SelectedResults> results) {
        List<CluItemValue> selectedResults = null;
        List<CluItemValue> cluItems = cluItemList.getValue();
        if (results != null) {
            for (SelectedResults selection : results) {
                CluItemValue cluItem = new CluItemValue();
                cluItem.setId(selection.getReturnKey());
                cluItem.setName(selection.getDisplayKey());
                selectedResults = (selectedResults == null)?
                        new ArrayList<CluItemValue>(7) : selectedResults;
                selectedResults.add(cluItem);   
            }
        }
        if (cluItems == null || cluItems.isEmpty()) {
//          // this causes cluItemList to be redrawn with new data
            cluItemList.setValue(selectedResults);
        } else {
            for (CluItemValue selectedCluItem: selectedResults) {
                if (!cluItems.contains(selectedCluItem)) {
                    cluItems.add(selectedCluItem);
                }
            }
            // this causes cluItemList to be redrawn with new data
            cluItemList.setValue(cluItems);
        }
    }

    public void addSelectionCompleteCallback(Callback<List<CluItemValue>> callback){
        callbacks.add(callback);
    }
    
    public void clearSelections() {
        searchClu.setValue(new StringValue(""), false);
        addToList.setEnabled(false);
        basicSelectionResult = null;
        if (cluItemList != null) {
            cluItemList.setValue(null);
        }
    }
    
    public void show() {
        dialog.show();
    }
    
    public void hide() {
        dialog.hide();
    }
}
