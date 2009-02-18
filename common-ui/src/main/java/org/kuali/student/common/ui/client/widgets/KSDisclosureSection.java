package org.kuali.student.common.ui.client.widgets;

import java.util.Iterator;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class KSDisclosureSection extends Composite{
	
	private DisclosurePanel panel;


	private FlowPanel content = new FlowPanel();
	
	public KSDisclosureSection(){
		panel = new DisclosurePanel();
		this.initWidget(panel);
		panel.setContent(content);
		setupDefaultStyle();
	}
	
	public KSDisclosureSection(String headerText){
		this(headerText, false);
	}
	
	public KSDisclosureSection(Widget headerWidget){
		this(headerWidget, false);
	}
	
	public KSDisclosureSection(String headerText, boolean isOpen){
		panel = new DisclosurePanel(headerText, isOpen);
		this.initWidget(panel);
		panel.setContent(content);
		setupDefaultStyle();
	}
	
	public KSDisclosureSection(Widget headerWidget, boolean isOpen){
		panel = new DisclosurePanel(headerWidget, isOpen);
		this.initWidget(panel);
		panel.setContent(content);
		setupDefaultStyle();
	}
	
	private void setupDefaultStyle() {
		panel.getHeader().addStyleName(KSStyles.KS_DISCLOSURE_HEADER_STYLE);
		panel.addStyleName(KSStyles.KS_DISCLOSURE_SECTION_PANEL);
		content.addStyleName(KSStyles.KS_DISCLOSURE_CONTENTPANEL_STYLE);
		
		panel.addCloseHandler(new CloseHandler<DisclosurePanel>(){

			public void onClose(CloseEvent<DisclosurePanel> event) {
				panel.getHeader().removeStyleName(KSStyles.KS_DISCLOSURE_OPEN_HEADER_STYLE);
				panel.removeStyleName(KSStyles.KS_DISCLOSURE_OPEN_SECTION_PANEL);
				content.removeStyleName(KSStyles.KS_DISCLOSURE_OPEN_CONTENTPANEL_STYLE);
			}
			
		});
		
		panel.addOpenHandler(new OpenHandler<DisclosurePanel>(){

			public void onOpen(OpenEvent<DisclosurePanel> event) {
				panel.getHeader().addStyleName(KSStyles.KS_DISCLOSURE_OPEN_HEADER_STYLE);
				panel.addStyleName(KSStyles.KS_DISCLOSURE_OPEN_SECTION_PANEL);
				content.addStyleName(KSStyles.KS_DISCLOSURE_OPEN_CONTENTPANEL_STYLE);
			}
			
		});
		
		
	}
	
	public void add(Widget w){
		content.add(w);
	}

	public Widget getContent() {
		return content;
	}

	public Iterator<Widget> iterator() {
		return content.iterator();
	}

	public boolean remove(Widget w) {
		return content.remove(w);
	}
	
	public void clear() {
		content.clear();
	}
	
	
	public HandlerRegistration addCloseHandler(
			CloseHandler<DisclosurePanel> handler) {
		return panel.addCloseHandler(handler);
	}

	public HandlerRegistration addOpenHandler(
			OpenHandler<DisclosurePanel> handler) {
		return panel.addOpenHandler(handler);
	}

	public void addStyleDependentName(String styleSuffix) {
		panel.addStyleDependentName(styleSuffix);
	}

	public void addStyleName(String style) {
		panel.addStyleName(style);
	}

	public Widget getHeader() {
		return panel.getHeader();
	}

	public HasText getHeaderTextAccessor() {
		return panel.getHeaderTextAccessor();
	}

	public String getTitle() {
		return panel.getTitle();
	}

	public boolean isAnimationEnabled() {
		return panel.isAnimationEnabled();
	}

	public boolean isOpen() {
		return panel.isOpen();
	}

	public boolean isVisible() {
		return panel.isVisible();
	}

	public void removeStyleDependentName(String styleSuffix) {
		panel.removeStyleDependentName(styleSuffix);
	}

	public void removeStyleName(String style) {
		panel.removeStyleName(style);
	}

	public void setAnimationEnabled(boolean enable) {
		panel.setAnimationEnabled(enable);
	}

	public void setHeader(Widget headerWidget) {
		panel.setHeader(headerWidget);
	}

	public void setOpen(boolean isOpen) {
		panel.setOpen(isOpen);
	}

	public void setStyleName(String style) {
		panel.setStyleName(style);
	}

	public void setStylePrimaryName(String style) {
		panel.setStylePrimaryName(style);
	}

	public void setTitle(String title) {
		panel.setTitle(title);
	}

	public void setVisible(boolean visible) {
		panel.setVisible(visible);
	}
	

}
