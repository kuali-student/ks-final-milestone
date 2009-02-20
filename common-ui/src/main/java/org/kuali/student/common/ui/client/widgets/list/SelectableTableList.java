package org.kuali.student.common.ui.client.widgets.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.visualization.client.AjaxLoader;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;
import com.google.gwt.visualization.client.visualizations.Table.Options.Policy;

public class SelectableTableList extends SelectItemWidget {
    Table table;
    SimplePanel root = new SimplePanel();
    Map<Integer, String> idMapping = new HashMap();
    Map<String, Integer> rowMapping = new HashMap();
    boolean loaded = false;
    boolean multiSelect = false;
    int selRow = -1;
    
    public SelectableTableList(){
        initWidget(root);
    }
    
    public void deSelectItem(String id) {
        JsArray<Selection> selections = table.getSelections();

        JsArray<Selection> newSelections = (JsArray<Selection>)JsArray.createArray();
        int j = 0;
        for (int i=0; i < selections.length(); i++){
            if (selections.get(i).getRow() != rowMapping.get(id).intValue()){
                newSelections.set(j, selections.get(i));
                j++;
            }
        }       

        table.setSelections(newSelections);
    }

    public List<String> getSelectedItems() {
        JsArray<Selection> selections = table.getSelections();

        List<String> selected = new ArrayList<String>();
        for (int i=0; i < selections.length(); i++){
            selected.add(idMapping.get(new Integer(selections.get(i).getRow())));
        }
        
        return selected;
    }

    public void selectItem(String id) {
        Selection sel = Selection.createRowSelection(rowMapping.get(id).intValue());
        
        JsArray<Selection> selections;
        if (multiSelect){
            selections = table.getSelections();
        } else {
            selections = (JsArray<Selection>)JsArray.createArray();
            selRow = sel.getRow();
        }
        
        selections.set(selections.length(),sel);
        table.setSelections(selections);
    }

    public void enableMultiSelect(boolean b){
        this.multiSelect = b;
    }
    
    public void onLoad() {               
        Runnable onLoadCallback = new Runnable() {
          public void run() {     
             DataTable data = DataTable.create(); 
             
            ListItems listItems = getListItems();
            List<String> attrKeys = listItems.getAttrKeys();

            for (String attr:attrKeys){
                data.addColumn(ColumnType.STRING, attr);
            }

            int row = 0;
            int col = 0;

            
            for (String id:listItems.getItemIds()){
                data.addRow();
                idMapping.put(row,id);
                rowMapping.put(id,row);
                for (String attr:attrKeys){
                    String value = listItems.getItemAttribute(id, attr);
                    data.setCell(row, col, value, value, null);
                    col++;
                }
                row ++;
                col=0;
            }
            
            if (table == null){
                table = new Table();                
                table.addSelectHandler(new SelectHandler(){
                    public void onSelect(SelectEvent event) {
                        if (!multiSelect){
                            //Make it to only select one row
                            JsArray<Selection> selections = table.getSelections();
                            if (selections.length() > 1){
                                Selection sel = (selections.get(0).getRow() == selRow ? selections.get(1):selections.get(0));
                                selections = (JsArray<Selection>)JsArray.createArray();
                                selections.set(0,sel);
                                selRow = sel.getRow();
                                table.setSelections(selections);
                            } else if (selections.length() == 1){
                                selRow = selections.get(0).getRow();
                            } else {
                                selRow = -1;
                            }
                        }
                        fireChangeEvent();
                    }                   
                });
            }

            Options options = Options.create();
            table.draw(data,options);
            root.setWidget(table);
          }
        };

        AjaxLoader.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
    }
    
}
