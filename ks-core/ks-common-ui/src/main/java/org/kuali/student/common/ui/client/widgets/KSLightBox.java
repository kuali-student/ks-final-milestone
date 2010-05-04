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

import org.kuali.student.common.ui.client.util.Elements;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.GlassPanel;

/**
 * 
 * */
public class KSLightBox implements HasCloseHandlers<KSLightBox> {
	public interface Resizer {
		public void resize(ScrollPanel scroll);
	}
	public static final Resizer DEFAULT_RESIZER = new Resizer() {
		private final int maxPercentX = 80;
		private final int maxPercentY = 80;
		private final int minPixelsX = 320;
		private final int minPixelsY = 240;
		
		@Override
		public void resize(ScrollPanel scroll) {
			Widget w = scroll.getWidget();
			int width = Math.min(w.getOffsetWidth(), (Window.getClientWidth() * maxPercentX) / 100); 
			width = Math.max(width, minPixelsX);
			
			int height = Math.min(w.getOffsetHeight(), (Window.getClientHeight() * maxPercentY) / 100); 
			height = Math.max(height, minPixelsY);
			
			scroll.setPixelSize(width + 20, height + 20);
		}
	};
	
	
	public enum Styles {
		LIGHTBOX("ks-lightbox"),
		TITLE("ks-lightbox-title"),
		CONTENT("ks-lightbox-content"),
		CLOSE("ks-lightbox-close"),
		CLOSE_PANEL("ks-lightbox-close-panel");
		private final String style;
		private Styles(final String style) {
			this.style = style;
		}
		public String getStyle() {
			return this.style;
		}
	}
    private final PopupPanel pop = new PopupPanel(false, true);
    private final ScrollPanel scroll = new ScrollPanel();
    private final GlassPanel glass = new GlassPanel(false);
    // KSLightBox actually needs to bind to the RootPanel, unlike other widgets that should use ApplicationPanel
    private final AbsolutePanel parentPanel = RootPanel.get();
    private final Resizer resizer;
    private final VerticalFlowPanel panel = new VerticalFlowPanel();
    private final FlowPanel headerPanel = new FlowPanel();
    private final Anchor closeLink = new Anchor();
    private boolean addCloseLink = true;
    private boolean showing = false;
    

    private final Timer resizeTimer = new Timer() {
		@Override
		public void run() {
			try {
				adjust();
			} catch (Exception e){
				this.cancel();
				throw new RuntimeException("Error Resizing Lightbox", e);
			}
		}
	};
	
	private final HandlerManager handlers = new HandlerManager(this);
	
	public KSLightBox() {
		// TODO review with UX what the expected look/behavior is when no title is specified, right now the close image can get in the way of the scroll-up button
		// this is a hack for now
		this.resizer = DEFAULT_RESIZER;
		final SimplePanel titlePlaceHolder = new SimplePanel();
		titlePlaceHolder.setSize("2em", "2em");
		construct(titlePlaceHolder);
	}
	
	public KSLightBox(boolean addCloseLink) {
	        // TODO review with UX what the expected look/behavior is when no title is specified, right now the close image can get in the way of the scroll-up button
	        // this is a hack for now
	        this.resizer = DEFAULT_RESIZER;
	        final SimplePanel titlePlaceHolder = new SimplePanel();
	        titlePlaceHolder.setSize("2em", "2em");
	        this.addCloseLink=addCloseLink;
	        construct(titlePlaceHolder);
	}
	public KSLightBox(String title) {
		this(title, DEFAULT_RESIZER);
	}
    public KSLightBox(String title, Resizer resizer) {
    	this(new HTML("<h2>" + title + "</h2>"), resizer);
    }
	public KSLightBox(Widget title) {
		this(title, DEFAULT_RESIZER);
	}
    public KSLightBox(Widget title, Resizer resizer) {
    	this.resizer = resizer;
    	construct(title);
    }
    protected void construct(Widget title) {
    	pop.setStyleName(Styles.LIGHTBOX.getStyle());
    	pop.addStyleName("KS-Drop-Shadow");
    	scroll.addStyleName(Styles.CONTENT.getStyle());
    	panel.addStyleName("ks-lightbox-flowpanel");
    	title.addStyleName(Styles.TITLE.getStyle());
        
    	
    	pop.setWidget(panel);
    	panel.add(headerPanel);
    	panel.add(scroll);
    	
    	headerPanel.add(title);
        if(addCloseLink){
            headerPanel.add(closeLink);
        }
    	headerPanel.addStyleName(Styles.CLOSE_PANEL.getStyle());
    	
    	// TODO fetch this from i18n messages
    	closeLink.setTitle("Close");
    	closeLink.setStyleName(Styles.CLOSE.getStyle());
    	closeLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
    	

    }
    
    protected void adjust() {
        resizer.resize(scroll);
        pop.center();
    }
    


    public void show() {
        if (!showing) {
            glass.getElement().setAttribute("zIndex", "" + KSZIndexStack.pop());
            parentPanel.add(glass, 0, 0);
            pop.getElement().setAttribute("zIndex", "" + KSZIndexStack.pop());
            resizeTimer.scheduleRepeating(500);
            Elements.setOpacity(pop, 0);
            adjust();
            Elements.setOpacity(pop, 100);
        }
        adjust();
        showing = true;
    }

    public void hide() {
    	resizeTimer.cancel();
    	if (showing) {
	        pop.hide();
	        KSZIndexStack.push();
	        parentPanel.remove(glass);
	        KSZIndexStack.push();
	        showing = false;
	        CloseEvent.fire(this, this);
    	}
    }

    public Widget getWidget() {
        return scroll.getWidget();
    }

    public void setWidget(Widget w) {
    	scroll.setWidget(w);
    }
    public HandlerRegistration addCloseHandler(CloseHandler<KSLightBox> handler){
    	return handlers.addHandler(CloseEvent.getType(), handler);
    }

	public boolean isShowing() {
		return showing;
	}
	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlers.fireEvent(event);
	}
	
    /*
     * Call this method to remove the Close link from the light box
     */
    public void removeCloseLink(){
        headerPanel.remove(closeLink);
    }

	
    
}
