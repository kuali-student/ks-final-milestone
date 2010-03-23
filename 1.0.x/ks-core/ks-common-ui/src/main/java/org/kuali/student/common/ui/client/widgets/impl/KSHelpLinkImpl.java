/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.KSHelpLinkAbstract;
import org.kuali.student.common.ui.client.widgets.KSInfoDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSToolTip;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

@Deprecated
public class KSHelpLinkImpl extends KSHelpLinkAbstract { 

    private final HorizontalPanel main = new HorizontalPanel();
    private final SimplePanel iconPanel = new SimplePanel();
    private final VerticalPanel labelPanel = new VerticalPanel();
    private Widget content;
    private String helpId = "FIX_ME"; //What is this and how do I initialize it?
    private HelpInfo helpInfo = null;
    private HelpLinkState state = HelpLinkState.DEFAULT;

    private final MyEventHandler handler = new MyEventHandler();

    //private final KSImages icons = GWT.create(KSImages.class);
    private final KSLabel validationText = new KSLabel();
    private KSInfoDialogPanel popup ;
    private KSToolTip tip ;

    public KSHelpLinkImpl() {
        super.initWidget(main);
        main.add(iconPanel);
        main.add(labelPanel);

        labelPanel.add(validationText);

        setStateDefault();
    }

    public HelpInfo getHelpInfo() {
        return helpInfo;
    }

    public void setHelpInfo(HelpInfo helpInfo) {
        this.helpInfo = helpInfo;
        loadPopups();
    }

    public HelpLinkState getState() {
        return state;
    }

    public void setStateDefault() {
        validationText.setVisible(false);
        if (helpInfo == null) {
            loadDefaultHelpInfo();
        }

        if (iconPanel.getWidget() != null) {
            iconPanel.remove(iconPanel.getWidget());
        }
        Image image = generateDefaultImage();
        iconPanel.setWidget(image);

        setDefaultStyle();
    }

    private void loadPopups() {

        tip = new KSToolTip(helpInfo.getTitle(), new KSLabel(helpInfo.getShortVersion()));

        popup = new KSInfoDialogPanel();
        popup.setHeader(helpInfo.getTitle());
        if (helpInfo.getUrl() != null) {
            content = new Frame(helpInfo.getUrl());
        }
        else {
            content = new KSLabel(helpInfo.getShortVersion());
        }
        content.setHeight((Window.getClientHeight() * .50) + "px");
        content.setWidth((Window.getClientWidth() * .50) + "px");
        content.addStyleName(KSStyles.KS_HELP_POPUP_FRAME);
    }

    private void loadDefaultHelpInfo(){
        HelpInfo testData = new HelpInfo();
        testData.setId(this.helpId);
        testData.setTitle("Help Title");
        testData.setShortVersion("No help available");
        this.helpInfo = testData;

        loadPopups();
    }
    private void setDefaultStyle(){
        removeCurrentStateStyle();
        main.addStyleName(KSStyles.KS_HELP_TEXT_PANEL);
    }  

    /**
     * Remove styles for current help state
     */
    private void removeCurrentStateStyle() {
        switch(state) {

            case OK:
                main.removeStyleName(KSStyles.KS_HELP_TEXT_PANEL_OK);
                break;
            case ERROR:
                main.removeStyleName(KSStyles.KS_HELP_TEXT_PANEL_ERROR);
                break;
            case DEFAULT:
            default:
                main.removeStyleName(KSStyles.KS_HELP_TEXT_PANEL);
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
        iconPanel.setWidget(generateOKImage());

        //  remove any other state dependent style names and add style name for OK state
        setOKStyle();
    }

    private void setOKStyle(){
        removeCurrentStateStyle();
        main.addStyleName(KSStyles.KS_HELP_TEXT_PANEL_OK);
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
        iconPanel.setWidget(generateErrorImage());

        // remove any other state dependent style names and add style name for error state
        setErrorStyle();
    }

    private void setErrorStyle(){
        removeCurrentStateStyle();
        main.addStyleName(KSStyles.KS_HELP_TEXT_PANEL_ERROR);
    }

    //BELOW commented out because this widget is no longer in use, fix if it comes back into use
    private Image generateDefaultImage() {
        //Image image = icons.defaultIcon().createImage();
        //addImageListeners(image);          
        return null;
    }

    private Image generateOKImage() {
        //Image image = icons.okIcon().createImage();
//        addImageListeners(image); // Do we need any listeners if field is now OK?                   
        return null;
    }

    private Image generateErrorImage() {
        //Image image = icons.errorIcon().createImage();
        //addImageListeners(image);                   
        return null;
    }

    private void addImageListeners(Image image) {
        image.addStyleName(KSStyles.KS_HELP_IMAGE);

        image.addMouseOverHandler(handler);
        image.addMouseOutHandler(handler);
        image.addClickHandler(handler);

    }

    private class MyEventHandler implements MouseOverHandler, MouseOutHandler, ClickHandler  {

        @Override
        public void onMouseOver(MouseOverEvent event) {
            tip.show(event);                 
        }

        @Override
        public void onMouseOut(MouseOutEvent event) {
            tip.hide();
        }

        @Override
        public void onClick(ClickEvent event) {
            tip.hide();  

            popup.setLocation(30, 30);
            popup.setWidget(content);
            popup.show();
        }
    }
}
