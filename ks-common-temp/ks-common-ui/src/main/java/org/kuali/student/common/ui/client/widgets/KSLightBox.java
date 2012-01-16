
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

import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * */
public class KSLightBox extends DialogBox /*implements HasCloseHandlers<KSLightBox> */{
//  private final HandlerManager handlers = new HandlerManager(this);

    private static final List<String> FOCUSABLE_TAGS = Arrays.asList("INPUT", "SELECT", "BUTTON", "TEXTAREA");

    private int maxWidth = 800;
    private int maxHeight = 0;
    private int minWidth = 400;
    private int minHeight = 200;
    private int permWidth = -1;
    private int permHeight = -1;
    private FlowPanel mainPanel = new FlowPanel();
    private FlowPanel titlePanel = new FlowPanel();
    private ScrollPanel scrollPanel = new ScrollPanel();

    private Anchor closeLink = new Anchor();
    private KSDialogResizeHandler resizeHandler = new KSDialogResizeHandler();
    private HandlerRegistration resizeHandlerRegistrater;

    private FlexTable verticalPanel = new FlexTable();
    private HorizontalPanel buttonPanel = new HorizontalPanel();
    public KSLightBox() {
    	((Widget) this.getCaption()).setVisible(false);
        init();
    }
    private void init(){
        super.setStyleName("ks-lightbox");

        mainPanel.setStyleName("ks-lightbox-mainPanel");
        titlePanel.setStyleName("ks-lightbox-titlePanel");
        closeLink.setStyleName("ks-lightbox-title-closeLink");
        scrollPanel.setStyleName("ks-lightbox-title-scrollPanel");

        setGlassEnabled(true);
        super.setWidget(mainPanel);
        mainPanel.add(titlePanel);
        mainPanel.add(scrollPanel);
        titlePanel.add(closeLink);

        verticalPanel.setStyleName("ks-lightbox-layoutTable");
        verticalPanel.setWidget(1, 0, buttonPanel);
        verticalPanel.getRowFormatter().setStyleName(1, "ks-lightbox-buttonRow");
        scrollPanel.add(verticalPanel);

        installResizeHandler();
        //super.
        closeLink.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
               hide();
            }
        });


    }
    public KSLightBox(boolean addCloseLink) {
    	((Widget) this.getCaption()).setVisible(false);
        init();
        closeLink.setVisible(addCloseLink);
    }
    public KSLightBox(String title) {
        init();
        super.setText(title);
    }
    public void uninstallResizeHandler(){
        if(resizeHandlerRegistrater != null){
            resizeHandlerRegistrater.removeHandler();
            resizeHandlerRegistrater = null;

        }
    }
    public void installResizeHandler(){
        if(resizeHandlerRegistrater == null){
            resizeHandlerRegistrater =  Window.addResizeHandler(resizeHandler);
        }
    }
    public void setCloseLinkVisible(boolean visible){
        closeLink.setVisible(visible);
    }
    public void addButton(Widget button){
    	button.addStyleName("ks-button-spacing");
        buttonPanel.add(button);
    }

    @SuppressWarnings("unchecked")
	public void addButtonGroup(ButtonGroup group){
    	buttonPanel.add(group);
    }

    public void setWidget(Widget content){
        verticalPanel.setWidget(0, 0, content);
        verticalPanel.getRowFormatter().setStyleName(0, "ks-lightbox-contentRow");
    }
    public void setMaxWidth(int width){
        this.maxWidth = width;
    }
    public void setMaxHeight(int height){
        this.maxHeight = height;
    }
    public void setSize(int width, int height){
        super.setSize(width+"px", height+"px");
        this.permHeight = height;
        this.permWidth = width;
        scrollPanel.setSize((width+10)+"px", (height+10)+"px");
    }

    public void showButtons(boolean show){
    	buttonPanel.setVisible(show);
    }

    @Override
    public void hide(){
        super.hide();
        uninstallResizeHandler();
    }
    @Override
    public void show(){
        resizeDialog();
        installResizeHandler();
        super.show();
        super.center();
        grabFocus();
    }

    private void grabFocus() {
        Widget mainContent = verticalPanel.getWidget(0, 0);
        NodeList<Element> nodeList = mainContent.getElement().getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element e = nodeList.getItem(i);
            if (FOCUSABLE_TAGS.contains(e.getTagName().toUpperCase())) {
                e.focus();
                return;
            }
        }


/*
        Widget mainContent = verticalPanel.getWidget(0, 0);
        if(mainContent instanceof HasWidgets){
            HasWidgets hasWidget = (HasWidgets) mainContent;
            Iterator<Widget> iter =  hasWidget.iterator();
            
            for(;iter.hasNext();){
                Widget w = iter.next();
              //  if(w instanceof Composite ){
                //    Composite c = (Composite)w;
                  //  c.
               // }
                if(w instanceof FocusWidget){
                    ((FocusWidget)w).setFocus(true);
                    return;
                }else if(w instanceof HasWidgets){
                    grabFocus((HasWidgets) w);
                }
            }
        }
*/
    }
/*    private void grabFocus(HasWidgets hasWidgets){
        Iterator<Widget> iter =  hasWidgets.iterator();
        for(;iter.hasNext();){
            Widget w = iter.next();
            if(w instanceof FocusWidget){
                ((FocusWidget)w).setFocus(true);
                return;
            }else if(w instanceof HasWidgets){
                grabFocus((HasWidgets) w);
            }
        }
    }
*/
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
    public Widget getWidget() {
    	return verticalPanel.getWidget(0, 0);
    }

    public void removeCloseLink(){
        closeLink.setVisible(false);
    }
//    public HandlerRegistration addCloseHandler(CloseHandler<KSLightBox> handler){
  //      return handlers.addHandler(CloseEvent.getType(), handler);
   // }

    private void resizeDialog(){

    	int width = maxWidth;
        int height = maxHeight;

        //Width calculation
        if(permWidth != -1){
    		width = permWidth;
    	}
        else{
        	if (Window.getClientWidth() < 850){
                width = Window.getClientWidth() - 160;
            }
        	if(width > maxWidth){
                width = maxWidth;
            }
        	if(width < minWidth){
        		width = minWidth;
        	}
        }

        //Height calculation
        if(permHeight != -1){
    		height = permHeight;
    	}
        else{
        	height = Window.getClientHeight() - 160;

        	if(height > maxHeight && maxHeight != 0){
                height = maxHeight;
            }
        	if(height < minHeight){
        		height = minHeight;
        	}
        }

        if(width > 0 && height > 0){
            super.setSize(width + "px", height + "px");
            scrollPanel.setSize((width+10)+"px", (height+10)+"px");
        }

/*        DeferredCommand.addCommand(new Command(){

            @Override
            public void execute() {
            	//center();
                int left = (Window.getClientWidth() - getOffsetWidth()) >> 1;
                int top = (Window.getClientHeight() - getOffsetHeight()) >> 1;
                setPopupPosition(Math.max(Window.getScrollLeft() + left, 0), Math.max(
                    Window.getScrollTop() + top, 0));
            }
        });  */
    }

    class KSDialogResizeHandler implements ResizeHandler{
        @Override
        public void onResize(ResizeEvent event) {
            DeferredCommand.addCommand(new Command(){

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
