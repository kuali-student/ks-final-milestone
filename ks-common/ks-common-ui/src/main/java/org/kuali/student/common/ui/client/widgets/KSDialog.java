package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;


public class KSDialog extends DialogBox  {

    private final HandlerManager handlers = new HandlerManager(this);
    private int maxWidth = 800;
    private int maxHeight = 0;
    private FlowPanel mainPanel = new FlowPanel();
    private FlowPanel titlePanel = new FlowPanel();
    private ScrollPanel scrollPanel = new ScrollPanel();
    
    private Anchor closeLink = new Anchor();
    private KSDialogResizeHandler resizeHandler = new KSDialogResizeHandler();
    private HandlerRegistration resizeHandlerRegistrater;
    
    private FlexTable verticalPanel = new FlexTable();
    private HorizontalPanel buttonPanel = new HorizontalPanel();
    public KSDialog() {
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

        closeLink.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
               hide();
            }
        });
       
    }
    public void uninstallResizeHandler(){
        if(resizeHandlerRegistrater != null){
            resizeHandlerRegistrater.removeHandler();
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
        buttonPanel.add(button);
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
        scrollPanel.setSize((width+10)+"px", (height+10)+"px");
    }
    @Override
    public void hide(){
        super.hide();
        uninstallResizeHandler();
    }
    @Override
    public void show(){
        resizeDialog();
        super.show();
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
    private void resizeDialog(){
        int width = maxWidth;
        int height = maxHeight;
        if (Window.getClientWidth() < 850){
            width = Window.getClientWidth() - 160;
        }
        
        height = Window.getClientHeight() - 160;
        
        
        if(width > maxWidth){
            width = maxWidth;
        }
        if(height > maxHeight && maxHeight != 0){
            height = maxHeight;
        }

        if(width > 0 && height > 0){
            setSize(width, height);
        }
        DeferredCommand.addCommand(new Command(){

            @Override
            public void execute() {
                int left = (Window.getClientWidth() - getOffsetWidth()) >> 1;
                int top = (Window.getClientHeight() - getOffsetHeight()) >> 1;
                setPopupPosition(Math.max(Window.getScrollLeft() + left, 0), Math.max(
                    Window.getScrollTop() + top, 0));        
            }
        });        
    }


    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlers.fireEvent(event);
    }
    
    class KSDialogResizeHandler implements ResizeHandler{
        @Override
        public void onResize(ResizeEvent event) {
            DeferredCommand.addCommand(new Command(){

                @Override
                public void execute() {
                    resizeDialog();
                   // show();
                }
            });
        }
    }
}
