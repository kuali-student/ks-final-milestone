package org.kuali.student.common.ui.client.widgets.table.summary;

import java.util.HashMap;

import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrPanel;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
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
    
    public void doLayout(){
        rowIndex = 0;
        this.removeAllRows();
        if(model.getContentColumnHeader1() != null && model.getContentColumnHeader2() != null){
	        super.setText(rowIndex, 1, model.getContentColumnHeader1());
	        super.setText(rowIndex, 2, model.getContentColumnHeader2());
	        getFlexCellFormatter().setStyleName(rowIndex,1, "columnTitle");
	        getFlexCellFormatter().setStyleName(rowIndex,2, "columnTitle");
	         
	        rowIndex++;
        }
        for(SummaryTableBlock section: model.getSectionList()){
           addSection(section);
        }
        
    }
    
    public void markDiffs(String style){
    	//skip first row, will always be title related
    	for(int i = 1; i < rowIndex; i++){
    		if(getFlexCellFormatter().getColSpan(i, 0) == 1 && getFlexCellFormatter().getColSpan(i, 1) == 1
    				&& getFlexCellFormatter().getColSpan(i, 2) == 1){
	    		Element cell1 = this.getCellFormatter().getElement(i, 1);
	    		Element cell2 = this.getCellFormatter().getElement(i, 2);
	    		if(!cell1.getInnerText().equals(cell2.getInnerText())){
	    			this.getRowFormatter().addStyleName(i, style);
	    		}
	    		else{
	    			this.getRowFormatter().removeStyleName(i, style);
	    		}
    		}
    	}
    }
    
    public boolean containsKey(String key){
    	return rowMap.containsKey(key);
    }
    
    public void highlightRow(String rowKey, String style){
    	if(rowMap.containsKey(rowKey)){
    		this.getRowFormatter().setStyleName(rowMap.get(rowKey).intValue(), style);
    	}
    }

    public void clearHighlightRow(String rowKey, String removeThisStyle){
    	if(rowMap.containsKey(rowKey)){
    		this.getRowFormatter().removeStyleName(rowMap.get(rowKey).intValue(), removeThisStyle);
    	}
    }
    
    public void clearHighlightedRows(String removeThisStyle){
    	for(int i = 0; i < rowIndex; i++){
    		this.getRowFormatter().removeStyleName(i, removeThisStyle);
    	}
    }
    
    public void highlightCell(String rowKey, int cellIndex, String style){
    	if(rowMap.containsKey(rowKey)){
    		this.getCellFormatter().setStyleName(rowMap.get(rowKey).intValue(),cellIndex, style);
    	}
    }
    
    public void clearHighlightCell(String rowKey, int cellIndex){
    	if(rowMap.containsKey(rowKey)){
    		this.getCellFormatter().setStyleName(rowMap.get(rowKey).intValue(),cellIndex,"");
    	}
        
    }
    private void addSection(SummaryTableBlock section){
    	int topRowIndex = -1;
    	if(section.getTitle() != null && !section.getTitle().isEmpty()){
	        getFlexCellFormatter().setStyleName(rowIndex,0, "sectionTitleRow");
	        getFlexCellFormatter().setColSpan(rowIndex, 0, 3); 
	        getFlexCellFormatter().setVerticalAlignment(rowIndex, 0, HasVerticalAlignment.ALIGN_BOTTOM);
	        HorizontalPanel sectionTitlePanel = new HorizontalPanel();
	        Label sectionTitle = new Label(section.getTitle());
	        sectionTitlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
	        sectionTitle.setStyleName("sectionTitle");
	        sectionTitlePanel.add(sectionTitle);
	    
	        if(model.isEditable()){
	            Anchor sectionEditLink = new Anchor("Edit");
	            sectionEditLink.setStyleName("sectionEditLink");
	            if(section.getEditingHandler() != null){
	                sectionEditLink.addClickHandler(section.getEditingHandler());
	            }
	            sectionTitlePanel.add(sectionEditLink);
	            
	        }
	        setWidget(rowIndex,0, sectionTitlePanel);
	        rowIndex++;
    	}
    	else{
    		topRowIndex = rowIndex;
    	}
    	
        for(SummaryTableRow row: section.getSectionRowList()){
            addSectionRow(row);
        }
        
        if(topRowIndex != -1){
        	this.getRowFormatter().addStyleName(topRowIndex, "firstRowInUnnamedBlock");
        }
        
    }
    private void addSectionRow(SummaryTableRow row){
    	if(row.isShown()){
	    	if(row.isRequired()){
	    		AbbrPanel required = new AbbrPanel("Required", "ks-form-module-elements-required", " * ");
	    		setHTML(rowIndex,0, row.getTitle() + required.toString());
	    	}
	    	else{
	    		setText(rowIndex,0, row.getTitle());
	    	}
	        getFlexCellFormatter().setStyleName(rowIndex,0, "rowTitle");
	        if(row.getTitleCellStyleName() != null){
	        	getFlexCellFormatter().addStyleName(rowIndex,0, row.getTitleCellStyleName());
	        }
	        if(row.getContentCellCount() == 1){
	        	setWidget(rowIndex,1, row.getCell1());
	        	getFlexCellFormatter().setStyleName(rowIndex,1, "cell1");
	            getFlexCellFormatter().setColSpan(rowIndex, 1,2);
	            if(row.getContentCellStyleName() != null){
	            	getFlexCellFormatter().addStyleName(rowIndex,1, row.getContentCellStyleName());
	            }
	        }else if(row.getContentCellCount() == 2){
	            setWidget(rowIndex,1, row.getCell1());
	            setWidget(rowIndex,2,row.getCell2());
	            getFlexCellFormatter().setColSpan(rowIndex, 1,1);
	            getFlexCellFormatter().setColSpan(rowIndex, 2,1);
	            getFlexCellFormatter().setStyleName(rowIndex,1, "cell1");
	            getFlexCellFormatter().setStyleName(rowIndex,2, "cell2");
	            if(row.getContentCellStyleName() != null){
	            	getFlexCellFormatter().addStyleName(rowIndex,1, row.getContentCellStyleName());
	            	getFlexCellFormatter().addStyleName(rowIndex,2, row.getContentCellStyleName());
	            }
	        }
	        rowMap.put(row.getKey(), rowIndex);
	        rowIndex++;
    	}
    }

}
