package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSLandingPage extends Composite{
	private VerticalPanel layout = new VerticalPanel();
	private SimplePanel wrapper = new SimplePanel();
	private HorizontalPanel titlePanel = new HorizontalPanel();
	private HorizontalPanel titleWidgetsPanel = new HorizontalPanel();
	private HorizontalPanel innerLayout = new HorizontalPanel();
	private VerticalPanel contentLayout = new VerticalPanel();
	private VerticalPanel descLayout = new VerticalPanel();
	private SimplePanel content = new SimplePanel();
	private KSLabel titleLabel = new KSLabel();
	private HTML largeDescription = new HTML();
	private Widget subDescription;
	private KSImage mainImage;
	
	public KSLandingPage(String title, String largeDescription){
		this.titleLabel.setText(title);
		this.largeDescription.setHTML(largeDescription);
		titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		titlePanel.add(titleLabel);
		titlePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		titlePanel.add(titleWidgetsPanel);
		descLayout.add(this.largeDescription);
		contentLayout.add(descLayout);
		contentLayout.add(content);
		innerLayout.add(contentLayout);
		layout.add(titlePanel);
		layout.add(innerLayout);
		wrapper.setWidget(layout);
		this.largeDescription.addStyleName("KS-LandingPage-Description");
		this.titleLabel.addStyleName("KS-LandingPage-Title");
		this.layout.addStyleName("KS-LandingPage-Panel");
		this.innerLayout.addStyleName("KS-LandingPage-ContentPanel");
		this.titlePanel.addStyleName("KS-LandingPage-TitlePanel");
		this.wrapper.addStyleName("KS-LandingPage");
		
		this.initWidget(wrapper);
		
	}
	
	public KSLandingPage(String title, KSImage mainImage, String largeDescription){
		this.titleLabel.setText(title);
		this.largeDescription.setHTML(largeDescription);
		this.mainImage = mainImage;
		innerLayout.add(mainImage);
		
		titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		titlePanel.add(titleLabel);
		titlePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		titlePanel.add(titleWidgetsPanel);
		descLayout.add(this.largeDescription);
		contentLayout.add(descLayout);
		contentLayout.add(content);
		innerLayout.add(contentLayout);
		
		layout.add(titlePanel);
		layout.add(innerLayout);
		wrapper.setWidget(layout);
		this.largeDescription.addStyleName("KS-LandingPage-Description");
		this.titleLabel.addStyleName("KS-LandingPage-Title");
		this.layout.addStyleName("KS-LandingPage-Panel");
		this.innerLayout.addStyleName("KS-LandingPage-ContentPanel");
		this.titlePanel.addStyleName("KS-LandingPage-TitlePanel");
		this.wrapper.addStyleName("KS-LandingPage");
		this.titleWidgetsPanel.addStyleName("KS-LandingPage-TitleWidgetsPanel");
		
		this.initWidget(wrapper);
	}
	
	public void setContent(Widget content){
		this.content.setWidget(content);
	}
	
	public void setSubDescWidget(Widget sub){
		descLayout.add(sub);
	}
	
	public void addTitlePanelWidget(Widget widget){
		titleWidgetsPanel.setSpacing(5);
		titleWidgetsPanel.add(widget);
	}
}
