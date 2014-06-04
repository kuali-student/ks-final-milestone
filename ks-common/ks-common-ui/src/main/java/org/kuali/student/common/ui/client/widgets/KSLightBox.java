/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.ui.client.widgets;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A KSLightBox is a dialog box that lays out its contents as follows using a
 * {@link DockLayoutPanel}:<br/>
 * <br />
 * ------------------<br/>
 * HEADER (either static caption / header widget that is not part of the scrollable area)<br/>
 * ------------------<br/>
 * CONTENT (part of the scrollable area that will fill available space)<br/>
 * ------------------<br/>
 * BUTTONS (static button area that is not part of the scrollable area)<br/>
 * ------------------<br/>
 * <br/>
 * 
 * The size of the dock panel will determine the size of the lightbox. Thus only the dock
 * panel size will need to be set.<br/>
 * The size of the dock panel is usually determined dynamically when the lightbox is
 * displayed, but you can also statically set it with one of the 'setSize' methods.<br/>
 * If you however set the size statically, the lightbox won't resize.<br/>
 * If you're making use of the <b>static sizes, the non-caption header and displaying the
 * buttons</b>, don't set the height of the dialog smaller than about 155px. Otherwise
 * there's too little space left for the content and the content (even if its just one line 
 * of text) will be displayed in a scroll panel.
 * 
 */
public class KSLightBox extends DialogBox {

    private static final List<String> FOCUSABLE_TAGS = Arrays.asList("INPUT", "SELECT", "BUTTON", "TEXTAREA");

    /**
     * An enum that specifies predefined width and height values (in pixels) for the
     * lightbox.
     */
    public enum Size {
        SMALL(400, 155), MEDIUM(550, 340), LARGE(600, 400);

        private int width;

        private int height;

        Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    private enum LightBoxStyle {
        LIGHT_BOX {
            public String toString() {
                return "ks-lightbox";
            }
        },
        MAIN_PANEL {
            public String toString() {
                return "ks-lightbox-mainPanel";
            }
        },
        TITLE_PANEL {
            public String toString() {
                return "ks-lightbox-titlePanel";
            }
        },
        SCROLL_PANEL {
            public String toString() {
                return "ks-lightbox-scrollPanel";
            }
        },
        BUTTON_PANEL {
            public String toString() {
                return "ks-lightbox-buttonPanel";
            }
        },
        CLOSE_LINK {
            public String toString() {
                return "ks-lightbox-closeLink";
            }
        },
        CLOSE_LINK_WITH_CAPTION {
            public String toString() {
                return "ks-lightbox-closeLink-with-Caption";
            }
        }
    }

    //Resizing variables
    private int maxWidth = 800;
    private int maxHeight = 0;
    private int minWidth = 400;
    private int minHeight = 200;
    private int permWidth = -1;
    private int permHeight = -1;

    //DockLayoutPanel sizes
    private static final int BUTTON_HEIGHT = 40;//'px' units (specified in mainPanel's constructor)
    private static final int NON_CAPTION_HEIGHT = 40;//'px' units (specified in mainPanel's constructor)

    private DockLayoutPanel mainPanel = new DockLayoutPanel(Unit.PX);
    private FlowPanel closeLinkPanel = new FlowPanel();
    private SimplePanel titlePanel = new SimplePanel();
    private ScrollPanel scrollPanel = new ScrollPanel();
    private SimplePanel contentPanel = new SimplePanel();
    private FlowPanel buttonPanel = new FlowPanel();
    private Anchor closeLink = new Anchor();

    private KSDialogResizeHandler resizeHandler = new KSDialogResizeHandler();
    private HandlerRegistration resizeHandlerRegistrater;

    public KSLightBox() {
        getCaption().asWidget().setVisible(false);
        init();
    }

    public KSLightBox(boolean addCloseLink) {
        getCaption().asWidget().setVisible(false);
        init();
        closeLink.setVisible(addCloseLink);
    }

    public KSLightBox(String title) {
        init();
        setText(title);
    }

    public KSLightBox(String title, Size size) {
        init();
        setText(title);
        setSize(size);
    }

    private void init() {
        mainPanel.setStyleName(LightBoxStyle.MAIN_PANEL.toString());
        titlePanel.setStyleName(LightBoxStyle.TITLE_PANEL.toString());
        closeLink.setStyleName(LightBoxStyle.CLOSE_LINK.toString());
        scrollPanel.setStyleName(LightBoxStyle.SCROLL_PANEL.toString());
        buttonPanel.setStyleName(LightBoxStyle.BUTTON_PANEL.toString());

        setGlassEnabled(true);
        super.setWidget(mainPanel);
        mainPanel.addNorth(closeLinkPanel, 1);
        mainPanel.addNorth(titlePanel, 0);
        mainPanel.addSouth(buttonPanel, BUTTON_HEIGHT);
        mainPanel.add(scrollPanel);
        closeLinkPanel.add(closeLink);
        //parent element sets overflow to hidden to allow background and other css styling on the titlePanel
        Element titlePanelContainer = mainPanel.getWidgetContainerElement(titlePanel);
        titlePanelContainer.getStyle().setOverflow(Overflow.VISIBLE);
        //parent element sets overflow to hidden, must reset overflow to visible to show the 'closeLink'
        Element closeLinkPanelContainer = mainPanel.getWidgetContainerElement(closeLinkPanel);
        closeLinkPanelContainer.getStyle().setOverflow(Overflow.VISIBLE);
        scrollPanel.add(contentPanel);

        installResizeHandler();
        //super.
        closeLink.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        super.setStyleName(LightBoxStyle.LIGHT_BOX.toString());

    }

    public void setCloseLinkVisible(boolean visible) {
        closeLink.setVisible(visible);
    }

    public void removeCloseLink() {
        closeLink.setVisible(false);
    }

    public HandlerRegistration addCloseLinkClickHandler(ClickHandler clickHandler) {
        return closeLink.addClickHandler(clickHandler);
    }

    /**
     * Sets the header that will be displayed at the top of the lightbox.<br/>
     * Please note: This header will not be displayed in the caption, but in the actual
     * lightbox content area.
     * @param widget The header widget.
     */
    public void setNonCaptionHeader(Widget widget) {
        titlePanel.setWidget(widget);
        mainPanel.setWidgetSize(titlePanel, NON_CAPTION_HEIGHT);
        mainPanel.forceLayout();
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        getCaption().asWidget().setVisible(true);
        //Style needed to reposition the 'closeLink'
        closeLink.addStyleName(LightBoxStyle.CLOSE_LINK_WITH_CAPTION.toString());
    }
    
    /**
     * Removes all the buttons at the bottom of the lightbox.
     */
    public void clearButtons() {
        buttonPanel.clear();
    }

    /**
     * Adds a button to the bottom of the lightbox.
     */
    public void addButton(Widget button) {
        button.addStyleName("ks-button-spacing");
        buttonPanel.add(button);
    }

    /**
     * Adds a {@link org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup} to the button panel at the bottom of the lightbox. 
     * @param group
     */
    @SuppressWarnings("rawtypes")
    public void addButtonGroup(org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup group) {
        buttonPanel.add(group);
    }
    
    /**
     * Adds a {@link org.kuali.student.common.ui.client.widgets.buttongroups.ButtonGroup} to the button panel at the bottom of the lightbox.
     * @param group
     */
    @SuppressWarnings("rawtypes")
    public void addButtonGroup(org.kuali.student.common.ui.client.widgets.buttongroups.ButtonGroup group) {
        buttonPanel.add(group);
        
    }

    public void showButtons(boolean show) {
        buttonPanel.setVisible(show);
        if (show) {
            mainPanel.setWidgetSize(buttonPanel, BUTTON_HEIGHT);
        } else {
            mainPanel.setWidgetSize(buttonPanel, 0);
        }
        mainPanel.forceLayout();
    }

    /**
     * Set the maximum width in pixels that this dialog will grow to.<br />
     * Please note: If the lightbox's size was set explicitly, this call will have no
     * effect.
     * 
     * @param width The dialog's maximum width in pixels.
     */
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    /**
     * Set the maximum height in pixels that this dialog will grow to.<br />
     * Please note: If the lightbox's size was set explicitly, this call will have no
     * effect.
     * 
     * @param height The dialog's maximum height in pixels.
     */
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    /**
     * Set the width and height of the lightbox in pixels.<br/>
     * Please note: These values will not be affected by resizing. Thus the lightbox will
     * remain the specified size, irrespective of resizing.
     * 
     * @param width The specified width in pixels.
     * @param height The specified height in pixels.
     */
    public void setSize(int width, int height) {
        mainPanel.setSize(width + "px", width + "px");
        this.permHeight = height;
        this.permWidth = width;
    }

    /**
     * Set the width and height of the lightbox in pixels using the {@link Size} enum.
     * 
     * @param size A predefined dialog size.
     */
    public void setSize(Size size) {
        setSize(size.getWidth(), size.getHeight());
    }

    @Override
    public void setWidget(Widget content) {
        contentPanel.setWidget(content);
    }

    @Override
    public Widget getWidget() {
        return contentPanel.getWidget();
    }

    @Override
    public void hide() {
        super.hide();
        uninstallResizeHandler();
    }

    @Override
    public void show() {
        resizeDialog();
        installResizeHandler();
        super.show();
        super.center();
        grabFocus();
    }

    private void resizeDialog() {

        int width = maxWidth;
        int height = maxHeight;

        //Width calculation
        if (permWidth != -1) {
            width = permWidth;
        } else {
            if (Window.getClientWidth() < 850) {
                width = Window.getClientWidth() - 160;
            }
            if (width > maxWidth) {
                width = maxWidth;
            }
            if (width < minWidth) {
                width = minWidth;
            }
        }

        //Height calculation
        if (permHeight != -1) {
            height = permHeight;
        } else {
            height = Window.getClientHeight() - 160;

            if (height > maxHeight && maxHeight != 0) {
                height = maxHeight;
            }
            if (height < minHeight) {
                height = minHeight;
            }
        }

        if (width > 0 && height > 0) {
            mainPanel.setSize(width + "px", height + "px");
        }

    }

    private void grabFocus() {
        Widget mainContent = contentPanel.getWidget();
        NodeList<Element> nodeList = mainContent.getElement().getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element e = nodeList.getItem(i);
            if (FOCUSABLE_TAGS.contains(e.getTagName().toUpperCase())) {
                e.focus();
                return;
            }
        }

    }

    @Override
    protected void onPreviewNativeEvent(NativePreviewEvent preview) {
        super.onPreviewNativeEvent(preview);
        NativeEvent evt = preview.getNativeEvent();
        if (evt.getType().equals("keydown")) {
            switch (evt.getKeyCode()) {
                case KeyCodes.KEY_ESCAPE:
                    hide();
                    break;
            }
        }
    }

    public void uninstallResizeHandler() {
        if (resizeHandlerRegistrater != null) {
            resizeHandlerRegistrater.removeHandler();
            resizeHandlerRegistrater = null;

        }
    }

    public void installResizeHandler() {
        if (resizeHandlerRegistrater == null) {
            resizeHandlerRegistrater = Window.addResizeHandler(resizeHandler);
        }
    }

    class KSDialogResizeHandler implements ResizeHandler {
        @SuppressWarnings("deprecation")
        @Override
        public void onResize(ResizeEvent event) {
            DeferredCommand.addCommand(new Command() {

                @Override
                public void execute() {
                    resizeDialog();
                    int left = (Window.getClientWidth() - getOffsetWidth()) >> 1;
                    int top = (Window.getClientHeight() - getOffsetHeight()) >> 1;
                    setPopupPosition(Math.max(Window.getScrollLeft() + left, 0), Math.max(
                            Window.getScrollTop() + top, 0));
                }
            });
        }
    }

    

}
