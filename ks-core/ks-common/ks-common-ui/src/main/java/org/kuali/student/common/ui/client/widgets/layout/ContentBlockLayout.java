package org.kuali.student.common.ui.client.widgets.layout;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * A layout which contains content blocks, used for a layout which contains logical blocks of content.
 * Each row contains a space of 3 blocks, and depending on size of blocks added will wrap to the next line.
 * 
 * @author Kuali Student Team
 *
 */
@Deprecated
public class ContentBlockLayout extends VerticalFlowPanel{
	
	private SectionTitle sectionTitle = SectionTitle.generateH1Title("");
	private FlowPanel titlePanel = new FlowPanel();
	//private FlowPanel blockPanel = new FlowPanel();
	private FlowPanel currentRow;
	private int rowSize = 0;
	private int titleWidgetCount = 0;
	
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
		
		if(titleWidgetCount != 0){
			Label separator = new Label(" | ");
			separator.addStyleName("titleWidget-separator");
			separator.addStyleName("blockLayout-title-widget");
			titlePanel.add(separator);
			titlePanel.add(widget);
		}
		else{
			titlePanel.add(widget);
		}
		widget.addStyleName("blockLayout-title-widget");
		titleWidgetCount++;
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
