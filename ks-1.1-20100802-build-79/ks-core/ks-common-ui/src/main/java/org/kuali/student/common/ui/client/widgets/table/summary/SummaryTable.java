package org.kuali.student.common.ui.client.widgets.table.summary;

import java.util.HashMap;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class SummaryTable  extends FlexTable{
    private SummaryTableModel model = new SummaryTableModel();
    private int rowIndex = 0;
    private HashMap<String, Integer> rowMap = new HashMap<String, Integer>();
    public SummaryTable(){
        setStyleName("summaryTable");

        getColumnFormatter().setStyleName(0, "rowTitleColunm");
        getColumnFormatter().setStyleName(1, "cell1Colunm");
        getColumnFormatter().setStyleName(2, "cell2Colunm");    
    }
    public SummaryTableModel getModel() {
        return model;
    }
    public void setModel(SummaryTableModel model) {
        this.model = model;
        doLayout();
    }
    private void doLayout(){
        for(SummaryTableSection section: model.getSectionList()){
           addSection(section);
        }
    }
    public void highLightRow(String rowKey){
        this.getRowFormatter().setStyleName(rowMap.get(rowKey).intValue(),"rowHighlight");
    }
    public void highLightCell(String rowKey, int cellIndex){
        this.getCellFormatter().setStyleName(rowMap.get(rowKey).intValue(),cellIndex,"cellHighlight");
    }
    public void clearHighLightRow(String rowKey){
        this.getRowFormatter().setStyleName(rowMap.get(rowKey).intValue(),"");
    }
    public void clearHighLightCell(String rowKey, int cellIndex){
        this.getCellFormatter().setStyleName(rowMap.get(rowKey).intValue(),cellIndex,"");
        
    }
    private void addSection(SummaryTableSection section){
        getFlexCellFormatter().setStyleName(rowIndex,0, "sectionTitleRow");
        getFlexCellFormatter().setColSpan(rowIndex, 0, 3); 
        HorizontalPanel sectionTitlePanel = new HorizontalPanel();
        Label sectionTitle = new Label(section.getTitle());
        sectionTitle.setStyleName("sectionTitle");
        sectionTitlePanel.add(sectionTitle);
    
        if(section.isEditable()){
            Anchor sectionEditLink = new Anchor("edit");
            sectionEditLink.setStyleName("sectionEditLink");
            if(section.getEditingHandler() != null){
                sectionEditLink.addClickHandler(section.getEditingHandler());
            }
            sectionTitlePanel.add(sectionEditLink);
        }
        setWidget(rowIndex,0, sectionTitlePanel);
        rowIndex++;
        
        for(SectionRow row: section.getSectionRowList()){
            addSectionRow(row);
        }
        
    }
    private void addSectionRow(SectionRow row){
        setText(rowIndex,0, row.getTitle());
        getFlexCellFormatter().setStyleName(rowIndex,0, "rowTitle");
        setWidget(rowIndex,1, row.getCell1());
        if(row.getContentCellCount() == 1){
            getFlexCellFormatter().setColSpan(rowIndex, 1,2);
        }else if(row.getContentCellCount() == 2){
            setWidget(rowIndex,1, row.getCell1());
            setWidget(rowIndex,2,row.getCell2());
        }
        rowMap.put(row.getKey(), rowIndex);
        
        rowIndex++;
    }

}
