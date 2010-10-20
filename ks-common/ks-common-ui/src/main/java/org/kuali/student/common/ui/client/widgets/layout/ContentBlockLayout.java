package org.kuali.student.common.ui.client.widgets.layout;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContentBlockLayout extends VerticalFlowPanel{
	
	private SectionTitle sectionTitle = SectionTitle.generateH1Title("");
	private FlowPanel titlePanel = new FlowPanel();
	//private FlowPanel blockPanel = new FlowPanel();
	private FlowPanel currentRow;
	private int rowSize = 0;
	
	public ContentBlockLayout(String title){
		this.setContentTitle(title);
		titlePanel.add(sectionTitle);
		this.add(titlePanel);
		//this.add(blockPanel);
		sectionTitle.addStyleName("blockLayout-title");
		titlePanel.addStyleName("blockLayout-titlePanel");
		//blockPanel.addStyleName("blockLayout-content");
		this.addStyleName("blockLayout");
	}
	
	public void setContentTitle(String title){
		sectionTitle.setText(title);
	}
	
	public void addContentTitleWidget(Widget widget){
		titlePanel.add(widget);
		widget.addStyleName("blockLayout-title-widget");
	}
	
	public void addContentBlock(ContentBlock block){
		if(rowSize == 0){
			FlowPanel row = new FlowPanel();
			this.add(row);
			row.addStyleName("blockLayout-row");
			currentRow = row;
		}
		currentRow.add(block);
		rowSize = rowSize + block.getBlockSize();
		if(rowSize == 3){
			rowSize = 0;
		}
		block.addStyleName("blockLayout-blockPadding");
	}
}
