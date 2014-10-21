package org.kuali.student.lum.common.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.SwapSection;
import org.kuali.student.common.ui.client.event.SectionUpdateEvent;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class SwitchSection extends BaseSection {
    private HashMap<String, Section> swapSectionMap = new HashMap<String, Section>();
    private KSSelectItemWidgetAbstract selectableWidget;
    private List<Section> deleted = new ArrayList<Section>();
    private ConfirmationDialog dialog; 
    private boolean showConfirmation = true;
    private List<String> lastSelection = new ArrayList<String>();
    private List<String> deletionParentKeys;
    
    /**
     * Constructor for SwapSection, note that the SelectableWidget passed in is not added to the
     * UI but is instead used as reference to show different sections.  The widget must still be added
     * to a layout as a field or a widget to show in the UI.  A default confirmation dialog will be used.
     * @param selectableWidget
     */
    public SwitchSection(KSSelectItemWidgetAbstract selectableWidget){
        this.init(selectableWidget);
    }
    
    public SwitchSection(KSSelectItemWidgetAbstract selectableWidget, ConfirmationDialog dialog){
        this.dialog = dialog;
        this.init(selectableWidget);
    }
    
    private void init(KSSelectItemWidgetAbstract selectableWidget){
        this.selectableWidget = selectableWidget;
        
        if(dialog == null){
            dialog = 
                new ConfirmationDialog(Application.getApplicationContext().getMessage("fieldDeletionTitle"),  
                        Application.getApplicationContext().getMessage("fieldDeletionConfirmMessage"));
        }
        dialog.getConfirmButton().addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                handleUserSelection();
                dialog.hide();
            }
        });
        
        selectableWidget.addSelectionChangeHandler(new SelectionChangeHandler(){

            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                if(event.isUserInitiated() && showConfirmation){
                    if(SwitchSection.this.selectableWidget.getSelectedItems().size() < lastSelection.size()){
                        dialog.show();
                    }
                    else if(!SwitchSection.this.selectableWidget.getSelectedItems().containsAll(lastSelection)){
//                      List<String> selected  = SwapSection.this.selectableWidget.getSelectedItems();
//                      for(int i = 0; i < selected.size(); i++){
//                          String key = selected.get(i);
//                          Section section = swapSectionMap.get(key);
//                          if(section!=null){
//                              if(section.getLayout().isVisible()){
//                                  dialog.show();
//                              }
//                          }
//                      }
                        dialog.show();
                    }
                    else{
                        handleUserSelection();
                    }
                }
                else if(event.isUserInitiated()){
                    handleUserSelection();
                }
                else{
                    handleSelection();
                }
                lastSelection.clear();
                lastSelection.addAll(SwitchSection.this.selectableWidget.getSelectedItems());
            }
        });
        layout = new VerticalFieldLayout();
        this.add(layout);
    }
    
    private void handleUserSelection(){
        List<String> selected  = SwitchSection.this.selectableWidget.getSelectedItems();
        for(int i = 0; i < selected.size(); i++){
            String key = selected.get(i);
            showSwappableSection(key);
        }
        
        Iterator<String> it = swapSectionMap.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            if(!selected.contains(key)){
                removeSwappableSection(key);
            }
        }
    }
    
    /**
     * This is handled differently than handleUserSelection because it is assumed that the atpService
     * is setting the correct values into the widgets, therefore no need to delete sections
     * (also reduces chance of actually deleting data before it is even shown)
     */
    private void handleSelection(){
        List<String> selected  = SwitchSection.this.selectableWidget.getSelectedItems();
        for(int i = 0; i < selected.size(); i++){
            String key = selected.get(i);
            showSwappableSection(key);
        }
        
        Iterator<String> it = swapSectionMap.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            if(!selected.contains(key)){
                removeSwappableSection(key);
            }
        }
    }
    
    private void showSwappableSection(String key){
        Section section = swapSectionMap.get(key);
        if(section != null){
            if(deleted.contains(section)){
                deleted.remove(section);
            }
            if(!section.getLayout().isVisible()){
                section.enableValidation(true);
                section.getLayout().setVisible(true);
            }
        }
    }
    
    private void removeSwappableSection(String key){
        Section section = swapSectionMap.get(key);
        if(section != null){
            if(!deleted.contains(section)){
                deleted.add(section);
            }
            if(section.getLayout().isVisible()){
                section.enableValidation(false);
                section.getLayout().setVisible(false);
            }

        }
    }
    
    public void enableConfirmation(boolean enable){
        showConfirmation = enable;
    }
    
    public String addSection(Section section, String swapKey){
        swapSectionMap.put(swapKey, section);
        String key = layout.addLayout(section.getLayout());
        section.getLayout().setVisible(false);
        if(selectableWidget.getSelectedItems().contains(swapKey)){
            handleSelection();
        }
        sections.add(section);
        return key;
    }
    
    public String addSection(String key, Section section, String swapKey){
        swapSectionMap.put(swapKey, section);
        section.getLayout().setKey(key);
        String rkey = layout.addLayout(section.getLayout());
        section.getLayout().setVisible(false);
        if(selectableWidget.getSelectedItems().contains(swapKey)){
            handleSelection();
        }
        sections.add(section);
        return rkey;
    }
    
    
    @Override
    public String addSection(Section section) {
        throw new UnsupportedOperationException("Sections can be added to swappable section only through " +
                "the addSection(Section section, String swapKey) method");
        
    }

    @Override
    public String addSection(String key, Section section) {
        throw new UnsupportedOperationException("Sections can be added to swappable section only through " +
                "the addSection(Section section, String swapKey) method");
    }
}
