package org.kuali.student.common.ui.client.widgets.suggestbox;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSConfirmButtonPanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTabPanel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.testData.Color;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KSAdvancedSearchWindow {
    private KSModalDialogPanel dialog = new KSModalDialogPanel();
    private KSTabPanel tabPanel = new KSTabPanel();
    private KSConfirmButtonPanel buttonPanel = new KSConfirmButtonPanel();
    private VerticalPanel searchLayout = new VerticalPanel();
    private VerticalPanel resultLayout = new VerticalPanel();
    private VerticalPanel dialogLayout = new VerticalPanel();
    private KSSelectableTableList searchResults = new KSSelectableTableList();
    private KSLabel resultLabel = new KSLabel("No Search Results");
    
    //Mock data
    private List<Color> colors = new ArrayList<Color>();
    
    //private Map<String, Widget> param
    
    public static class SearchParameter{
        private boolean enumeration = false;
        private String label = "";
        private String key = ""; 
        private List<String> enumeratedValues = new ArrayList<String>();
        
        public SearchParameter(String displayedLabel, String key){
            this.label = displayedLabel;
            this.key = key;
        }
        
        public SearchParameter(String displayedLabel, String key, List<String> enumeratedValues){
            this.label = displayedLabel;
            this.setEnumeratedValues(enumeratedValues);
            this.key = key;
            enumeration = true;
        }

        public String getLabel() {
            return label;
        }

        public void setEnumeratedValues(List<String> enumeratedValues) {
            enumeration = true;
            this.enumeratedValues = enumeratedValues;
        }

        public List<String> getEnumeratedValues() {
            return enumeratedValues;
        }

        public boolean isEnumeration() {
            return enumeration;
        }

        public String getKey() {
            return key;
        }
        
    }
    
    public KSAdvancedSearchWindow(List<SearchParameter> params){
        generateSearchLayout(params);
        dialogLayout.add(tabPanel);
        dialogLayout.add(buttonPanel);
        buttonPanel.addCancelHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                dialog.hide();
            }
        });
        
        searchResults.setListItems(new ListItems(){
            
            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Color");
                attributes.add("Warmth");
                attributes.add("Type");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                for(Color c: colors){
                    if(c.getId().equals(id)){
                        if(attrkey.equals("Color")){
                            value = c.getColor();
                        }
                        else if(attrkey.equals("Warmth")){
                            value = c.getWarmth();
                        }
                        else if(attrkey.equals("Type")){
                            value = c.getType();
                        }
                        break;
                    }
                }
                return value;
            }

            @Override
            public String getItemText(String id) {
                String value = null;
                for(Color c: colors){
                    if(c.getId().equals(id)){
                        value = c.getColor();
                        break;
                    }
                }
                return value;
            }


            @Override
            public int getItemCount() {
                return colors.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(Color c: colors){
                    ids.add(c.getId());
                }
                return ids;
            }

        });
        
        generateResultLayout();
        tabPanel.addTab(searchLayout, "Search");
        tabPanel.addTab(resultLayout, "Results");
        tabPanel.selectTab(0);
        
        resultLayout.addStyleName(KSStyles.KS_ADVANCED_SEARCH_RESULTS_PANEL);
        searchLayout.addStyleName(KSStyles.KS_ADVANCED_SEARCH_PANEL);
        searchLayout.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        tabPanel.addStyleName(KSStyles.KS_ADVANCED_SEARCH_TAB_PANEL);
        dialogLayout.addStyleName(KSStyles.KS_ADVANCED_SEARCH_WINDOW);
        dialog.setHeader("Advanced Search");
        dialog.setWidget(dialogLayout);
        
    }
    
    private void generateSearchLayout(final List<SearchParameter> params) {
        FlexTable table = new FlexTable();
        int row = 0;
        int column = 0;
        for(SearchParameter p: params){
            KSLabel paramLabel = new KSLabel(p.getLabel());
            paramLabel.addStyleName(KSStyles.KS_ADVANCED_SEARCH_PARAM_LABELS);
            table.getColumnFormatter().addStyleName(0, KSStyles.KS_ADVANCED_SEARCH_PARAM_LABEL_COLUMN);
            table.getCellFormatter().addStyleName(row, column, KSStyles.KS_ADVANCED_SEARCH_PARAM_LABEL_CELLS);
            table.setWidget(row, column, paramLabel);
            column++;
            if(p.isEnumeration()){
               ListBox lb = new ListBox();
               lb.addItem("");
               for(String v : p.getEnumeratedValues()){
                   lb.addItem(v);
               }
               table.setWidget(row, column, lb);
               
            }
            else{
                KSTextBox tb = new KSTextBox();
                table.setWidget(row, column, tb);
            }
            column = 0;
            row++;
        }
        column++;
        KSButton searchButton = new KSButton("Search");
        searchButton.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                // TODO does the search!!!!
                // TODO displays the result!!!
                //Below is a dummy result for demonstration purposes only
                colors.clear();
                colors.add(new Color("1", "Blue", "Cool", "Primary"));
                colors.add(new Color("2", "Red", "Warm", "Primary"));
                colors.add(new Color("4", "Yellow", "Warm", "Primary"));
                
                resultLabel.setText("Search results for: Type=Primary");
                

                tabPanel.selectTab(1);
                searchResults.redraw();
            }
        });
        table.setWidget(row, column, searchButton);
        searchLayout.add(table);
    }
    
    private void generateResultLayout(){
        FlexTable table = new FlexTable();
        resultLabel.addStyleName(KSStyles.KS_ADVANCED_SEARCH_RESULTS_LABEL);
        table.setWidget(0, 0, resultLabel);
        table.setWidget(1, 0, searchResults);
        resultLayout.add(table);
    }

    public void addConfirmHandler(ClickHandler handler){
        buttonPanel.addConfirmHandler(handler);
    }
    
    public void show(){
        dialog.show();
    }
    
    public void hide(){
        dialog.hide();
    }
    
    public List<String> getSelectedIds(){
        return searchResults.getSelectedItems();
    }
}
