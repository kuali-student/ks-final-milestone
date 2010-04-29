package org.kuali.student.common.ui.client.widgets.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class SimpleWidgetTable extends Composite{
	private FlexTable simpleTable = new FlexTable();
	private int rowCount = 0;
	public List<String> columnList = new ArrayList<String>();
	//private G
	
	public class TableRow{

		public List<Widget> widgetList = new ArrayList<Widget>();
		public List<Widget> getWidgetList() {
			return widgetList;
		}
		public void setWidgetList(List<Widget> widgetList) {
			this.widgetList = widgetList;
		}
		public TableRow(List<Widget> widgetList){
			this.widgetList = widgetList;
		}
	}

	public SimpleWidgetTable(List<String> columnNames){
		Element thead = DOM.createElement("thead");
		Element tr = DOM.createTR();
		int columnPercentage = 100/columnNames.size();
		
		
		Element table = simpleTable.getElement();
		//table.setAttribute("style", "border-collapse: collapse; ");
		 DOM.appendChild(thead,tr); 
		 for (String columnName: columnNames) {
			 Element th = DOM.createTH(); 
		 	 DOM.appendChild(tr,th);
			 DOM.setInnerText(th,columnName);
			 th.setAttribute("width",  columnPercentage + "%");
		 }

		DOM.insertChild(table,thead,0);
		
		//TODO fix style names here
		simpleTable.setWidth("100%");
		simpleTable.setStyleName("ks-table-plain");
		this.initWidget(simpleTable);
		
	}
	
	public void addRow(List<Widget> widgets){
		int columnNum = 0;
		TableRow row = new TableRow(widgets);
		for(Widget w: row.getWidgetList()){
			simpleTable.setWidget(rowCount, columnNum, w);
			columnNum++;
		}
		rowCount++;
	}
	
/*	public void setRows(List<TableRow> rows){
		clear();
		for(TableRow row: rows){
			addRow(row);
		}
	}*/
	
	public void clear(){
		rowCount = 0;
		for(int i=0; i < simpleTable.getRowCount(); i++){
			simpleTable.removeRow(i);
		}
	}
}
