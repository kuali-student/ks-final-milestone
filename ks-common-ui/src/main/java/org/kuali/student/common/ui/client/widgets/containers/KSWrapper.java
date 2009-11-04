package org.kuali.student.common.ui.client.widgets.containers;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSWrapper extends Composite{
	private VerticalFlowPanel layout = new VerticalFlowPanel();
	private VerticalPanel leftHeader = new VerticalPanel();
	private VerticalPanel rightHeader = new VerticalPanel();
	private HorizontalPanel header = new HorizontalPanel();
	private HorizontalPanel headerContent = new HorizontalPanel();
	private HorizontalPanel footer = new HorizontalPanel();
	private HorizontalPanel headerTopLinks = new HorizontalPanel();
	private HorizontalPanel headerBottomLinks = new HorizontalPanel();
	//TODO replace with custom drop down.  Is it hard coded OR from somewhere
	private KSDropDown navDropdown = new KSDropDown();
	//TODO replace with custom drop down.  Probably custom widget itself eventually.
	private KSDropDown userDropdown = new KSDropDown();
	
	//TODO replace with raw link widget list
	private List<KSLabel> headerLinkList = new ArrayList<KSLabel>();
	private List<KSLabel> footerLinkList = new ArrayList<KSLabel>();
	
	//TODO replace with raw link widget(?)
	private KSLabel helpLabel = new KSLabel("Help");
	private KSImage helpImage = Theme.INSTANCE.getCommonImages().getHelpIcon();
	//TODO
	private Widget headerCustomWidget = Theme.INSTANCE.getCommonWidgets().getHeaderWidget();
	private SimplePanel content = new SimplePanel();
	
	public KSWrapper(){
		headerBottomLinks.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		headerBottomLinks.add(userDropdown);
		headerBottomLinks.add(helpLabel);
		headerBottomLinks.add(helpImage);
		leftHeader.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		if(headerCustomWidget != null){
			leftHeader.add(headerCustomWidget);
			headerCustomWidget.addStyleName("KS-Wrapper-Header-Custom-Widget-Panel");
		}
		leftHeader.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		leftHeader.add(navDropdown);
		rightHeader.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		rightHeader.add(headerTopLinks);
		rightHeader.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		rightHeader.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		rightHeader.add(headerBottomLinks);
		headerContent.add(leftHeader);
		headerContent.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerContent.add(rightHeader);
		header.add(headerContent);
		//layout.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		layout.add(header);
		layout.add(content);
		layout.add(footer);
		this.initWidget(layout);
		header.addStyleName("KS-Wrapper-Header");
		headerContent.addStyleName("KS-Wrapper-Header-Content");
		footer.addStyleName("KS-Wrapper-Footer");
		content.addStyleName("KS-Wrapper-Content");
		layout.addStyleName("KS-Wrapper");
		helpLabel.addStyleName("KS-Wrapper-Help-Label");
		rightHeader.addStyleName("KS-Wrapper-Header-Right-Panel");
		leftHeader.addStyleName("KS-Wrapper-Header-Left-Panel");
		navDropdown.addStyleName("KS-Wrapper-Navigation-Dropdown");
		userDropdown.addStyleName("KS-Wrapper-User-Dropdown");
	}
	
	public void setContent(Widget wrappedContent){
		content.setWidget(wrappedContent);
	}
	
	public void setHeaderCustomLinks(List<KSLabel> links){
		headerLinkList = links;
		for(KSLabel link: links){
			FocusPanel panel = new FocusPanel();
			panel.setWidget(link);
			headerTopLinks.add(panel);
			panel.addStyleName("KS-Wrapper-Header-Custom-Link-Panel");
			link.addStyleName("KS-Wrapper-Header-Custom-Link");
		}
	}
	
	public void setFooterLinks(List<KSLabel> links){
		footerLinkList = links;
		for(KSLabel link: links){
			footer.add(link);
			link.addStyleName("KS-Wrapper-Footer-Link");
		}
	}
	
}
