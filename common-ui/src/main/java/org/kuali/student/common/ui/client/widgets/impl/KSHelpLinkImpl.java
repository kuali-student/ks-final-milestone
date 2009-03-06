package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.widgets.KSHelpDialog;
import org.kuali.student.common.ui.client.widgets.KSHelpLinkAbstract;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KSHelpLinkImpl extends KSHelpLinkAbstract { 


    
    private final Label helpText = new Label();
    private final Label validationText = new Label();
    private final KSImages icons = GWT.create(KSImages.class);
    private final HorizontalPanel panel = new HorizontalPanel();
    private final SimplePanel iconPanel = new SimplePanel();
    private final VerticalPanel labelPanel = new VerticalPanel();
    private KSHelpDialog popup = null;
	private String helpId = "FIX_ME"; //What is this and how do I initialize it?
    private HelpInfo helpInfo = null;
    private HelpLinkState state = HelpLinkState.DEFAULT;
    
    private final ClickHandler clickHandler = new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            // on click, show non-modal popup containing iframe to helpinfo url
        	fetchHelpInfo();
        	popup = new KSHelpDialog(helpInfo);
        	//popup.init(helpInfo);
        	popup.show();
        }        
    };
    
    public KSHelpLinkImpl() {
		super.initWidget(panel);
		panel.add(iconPanel);
		panel.add(labelPanel);
		labelPanel.add(helpText);
		labelPanel.add(validationText);
		
		helpText.addClickHandler(clickHandler);
		validationText.addClickHandler(clickHandler);
		setStateDefault();
	}

    public HelpInfo getHelpInfo() {
        return helpInfo;
    }

    public void setHelpInfo(HelpInfo helpInfo) {
        this.helpInfo = helpInfo;
    }

	private void fetchHelpInfo(){
		//TODO implement this with a RPC call
		HelpInfo testData = new HelpInfo();
		testData.setId(this.helpId);
		testData.setTitle("Help Title");
		testData.setShortVersion("HELP TEXT HELP TEXT HELP TEXT HELP TEXT");
		testData.setUrl("http://www.kuali.org");
		this.helpInfo = testData;
	}
    public HelpLinkState getState() {
        return state;
    }
    
    public void setStateDefault() {
        validationText.setVisible(false);
        if (iconPanel.getWidget() != null) {
            iconPanel.remove(iconPanel.getWidget());
        }
        iconPanel.setWidget(icons.defaultIcon().createImage());
        // remove any other state dependent style names and add style name for default state
        setDefaultStyle();
    }
    
	private void setDefaultStyle(){
		removeCurrentStateStyle();
		helpText.addStyleName(KSStyles.KS_HELP_TEXT);
		panel.addStyleName(KSStyles.KS_HELP_TEXT_PANEL);
	}  
	
	/**
	 * Remove styles for current help state
	 */
	private void removeCurrentStateStyle() {
		switch(state) {

		case OK:
			helpText.removeStyleName(KSStyles.KS_HELP_TEXT_OK);
			panel.removeStyleName(KSStyles.KS_HELP_TEXT_PANEL_OK);
			break;
		case ERROR:
			helpText.removeStyleName(KSStyles.KS_HELP_TEXT_ERROR);
			panel.removeStyleName(KSStyles.KS_HELP_TEXT_PANEL_ERROR);
			break;
		case DEFAULT:
		default:
			helpText.removeStyleName(KSStyles.KS_HELP_TEXT);
			panel.removeStyleName(KSStyles.KS_HELP_TEXT_PANEL);
			break;
		}
	}
	
    public void setStateOK() {
        setStateOK(null);
    }
    public void setStateOK(String text) {
        if (text != null) {
            validationText.setText(text);
        }
        validationText.setVisible(text != null);
        if (iconPanel.getWidget() != null) {
            iconPanel.remove(iconPanel.getWidget());
        }
        iconPanel.setWidget(icons.okIcon().createImage());
        //  remove any other state dependent style names and add style name for OK state
        setOKStyle();
    }

	private void setOKStyle(){
		removeCurrentStateStyle();
		helpText.addStyleName(KSStyles.KS_HELP_TEXT_OK);
		panel.addStyleName(KSStyles.KS_HELP_TEXT_PANEL_OK);
	} 
	
    public void setStateError() {
        setStateError(null);
    }
    public void setStateError(String text) {
        if (text != null) {
            validationText.setText(text);
        }
        validationText.setVisible(text != null);
        if (iconPanel.getWidget() != null) {
            iconPanel.remove(iconPanel.getWidget());
        }
        iconPanel.setWidget(icons.errorIcon().createImage());
        // remove any other state dependent style names and add style name for error state
        setErrorStyle();
    }

	private void setErrorStyle(){
		removeCurrentStateStyle();
		helpText.addStyleName(KSStyles.KS_HELP_TEXT_ERROR);
		panel.addStyleName(KSStyles.KS_HELP_TEXT_PANEL_ERROR);
	}    
}
