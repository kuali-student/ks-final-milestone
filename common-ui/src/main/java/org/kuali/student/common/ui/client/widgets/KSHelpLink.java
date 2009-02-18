package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.images.HelpIcons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSHelpLink extends Composite {

    public enum HelpLinkState {
        DEFAULT,
        OK,
        ERROR
    }
    
    private final Label helpText = new Label();
    private final Label validationText = new Label();
    private final HelpIcons icons = GWT.create(HelpIcons.class);
    private final HorizontalPanel panel = new HorizontalPanel();
    private final SimplePanel iconPanel = new SimplePanel();
    private final VerticalPanel labelPanel = new VerticalPanel();
    
    private final ClickHandler clickHandler = new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            // TODO on click, show non-modal popup containing iframe to helpinfo url
        }
        
    };
    
    private HelpInfo helpInfo = null;
    private HelpLinkState state = HelpLinkState.DEFAULT;
    
    

    public KSHelpLink() {
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


    public HelpLinkState getState() {
        return state;
    }
    
    public void setStateDefault() {
        validationText.setVisible(false);
        if (iconPanel.getWidget() != null) {
            iconPanel.remove(iconPanel.getWidget());
        }
        iconPanel.setWidget(icons.defaultIcon().createImage());
        // TODO remove any other state dependent style names and add style name for default state
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
        // TODO remove any other state dependent style names and add style name for OK state
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
        // TODO remove any other state dependent style names and add style name for error state
    }

}
