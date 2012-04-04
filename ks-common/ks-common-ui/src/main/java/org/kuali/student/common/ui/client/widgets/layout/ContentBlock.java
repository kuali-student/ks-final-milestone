package org.kuali.student.common.ui.client.widgets.layout;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Content block for use in ContentBlockLayout
 * @author Kuali Student Team
 * @see ContentBlockLayout
 */
@Deprecated
public class ContentBlock extends VerticalFlowPanel{
	
	private SectionTitle sectionTitle = SectionTitle.generateH2Title("");
	private VerticalFlowPanel titlePanel = new VerticalFlowPanel();
	private HTML description = new HTML();
	private int size;
	
	public ContentBlock(String blockTitle, String blockDescriptionHtml){
		this(blockTitle, blockDescriptionHtml, 1);
	}

	public ContentBlock(String blockTitle, String blockDescriptionHtml, int blockSize){
		sectionTitle.setText(blockTitle);
		description.setHTML(blockDescriptionHtml);
		titlePanel.add(sectionTitle);
		titlePanel.add(description);
		size = blockSize;
		super.add(titlePanel);
		if(blockSize == 3){
			this.setStyleName("contentBlock-size3");
		}
		else if(blockSize == 2){
			this.setStyleName("contentBlock-size2");
		}
		else{
			this.setStyleName("contentBlock-size1");
		}
		titlePanel.setStyleName("contentBlock-titlePanel");
		sectionTitle.setStyleName("contentBlock-title");
		description.setStyleName("contentBlock-desc");
	}
	
	public int getBlockSize(){
		return size;
	}
	
	@Override
	public void add(Widget w){
		super.add(w);
	}
	
	public void addBlock(ContentBlock block){
		super.add(block);
	}
	
	public Panel getTitlePanel(){
		return titlePanel;
	}

}
