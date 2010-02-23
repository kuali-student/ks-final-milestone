package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.user.client.ui.Composite;

public class KSTitleDescPanel extends Composite{
	private VerticalFlowPanel layout = new VerticalFlowPanel();
	private KSLabel title = new KSLabel();
	private KSLabel desc = new KSLabel();
	
	public KSTitleDescPanel(String title, String desc){
		this.title.setText(title);
		this.desc.setText(desc);
		this.title.setStyleName("KS-Common-Title");
		this.desc.setStyleName("KS-Common-Desc");
		layout.add(this.title);
		layout.add(this.desc);
		this.initWidget(layout);
	}
	
	public KSTitleDescPanel(String title){
		this.title.setText(title);
		this.title.setStyleName("KS-Common-Title");
		this.desc.setStyleName("KS-Common-Desc");
		layout.add(this.title);
		layout.add(this.desc);
		this.desc.setVisible(false);
		this.initWidget(layout);
	}
	
	public KSTitleDescPanel(){
		layout.add(this.title);
		layout.add(this.desc);
		this.title.setStyleName("KS-Common-Title");
		this.desc.setStyleName("KS-Common-Desc");
		this.desc.setVisible(false);
		this.title.setVisible(false);
		this.initWidget(layout);
	}
	
	public void setTitleText(String title){
		this.title.setText(title);
		this.title.setVisible(true);
	}
	
	public void setDesc(String desc){
		this.desc.setText(desc);
		this.desc.setVisible(true);
	}

	public KSLabel getTitleWidget() {
		return title;
	}
	
	public KSLabel getDescWidget(){
		return desc;
	}
	
	
}
