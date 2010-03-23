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
import org.kuali.student.lum.lu.ui.tools.client.configuration.itemlist.CluItemValue;
import org.kuali.student.lum.lu.ui.tools.client.configuration.itemlist.ItemList;

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
                "Add courses individually, or use advanced searchto batch select and add.");
        KSButton addToList = new KSButton("Add to list");
        HorizontalPanel courseSearchPanel = new HorizontalPanel();
        cluItemList = new ItemList<CluItemValue>();
        
        courseSearchPanel.add(searchClu);
        courseSearchPanel.add(addToList);
        
        this.searchClu = searchClu;
        
        
        searchClu.getSearchWindow().addSelectionCompleteCallback(new Callback<List<SelectedResults>>() {
            @Override
            public void exec(List<SelectedResults> result) {
                List<CluItemValue> selectedResults = null;
                List<CluItemValue> cluItems = cluItemList.getValue();
                if (result != null) {
                    for (SelectedResults selection : result) {
                        CluItemValue cluItem = new CluItemValue();
                        cluItem.setId(selection.getReturnKey());
                        cluItem.setName(selection.getDisplayKey());
                        selectedResults = (selectedResults == null)?
                                new ArrayList<CluItemValue>(7) : selectedResults;
                        selectedResults.add(cluItem);   
                    }
                }
                if (cluItems == null || cluItems.isEmpty()) {
                    cluItemList.setValue(selectedResults);
//                  // this causes cluItemList to be redrawn with new data
//                    f.getFieldWidget()
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
        });
        
        addToList.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // TODO populate the item entered in the suggest box
            }
        });
        
        layout.add(titleBar);
        layout.add(instructions);
        layout.add(courseSearchPanel);
        layout.add(cluItemList);
        layout.add(buttonPanel);
        dialog.setWidget(layout);
    }

    public void addSelectionCompleteCallback(Callback<List<CluItemValue>> callback){
        callbacks.add(callback);
    }
    
    public void show() {
        dialog.show();
    }
    
    public void hide() {
        dialog.hide();
    }
}
