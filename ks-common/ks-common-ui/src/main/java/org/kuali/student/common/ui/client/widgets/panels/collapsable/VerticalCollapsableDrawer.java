package org.kuali.student.common.ui.client.widgets.panels.collapsable;

import org.kuali.student.common.ui.client.widgets.KSButton;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class VerticalCollapsableDrawer extends Composite{
		private static VerticalCollapsableDrawerBinder uiBinder = GWT
		.create(VerticalCollapsableDrawerBinder.class);
		
		interface VerticalCollapsableDrawerBinder extends
			UiBinder<Widget, VerticalCollapsableDrawer> {
		}

		@UiField
		public SimplePanel content;
		
		@UiField
		public KSButton drawerHandle;
		
		@UiField
		public HTMLPanel container;
		
		protected boolean isOpen = true;
		private ContentAnimation animation = new ContentAnimation();
		
		public VerticalCollapsableDrawer(){
		    initialise();
		}
		
		public void initialise(){
			initWidget(uiBinder.createAndBindUi(this));
			drawerHandle.setText("\u00AB");
			drawerHandle.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					if(isOpen){
						close();
						drawerHandle.setText("\u00BB");
					}
					else{
						open();
						drawerHandle.setText("\u00AB");
					}
					
				}
			});
		}
		
		@Override
		protected void onLoad() {
			super.onLoad();
			container.getElementById("collapsePanel").setAttribute("style", "height: " 
					+ content.getOffsetHeight() + "px");
			//this.getElement().g;
		}
		
		public void open(){
			isOpen = true;
			animation.setOpen(this, true);
		}
		
		public void close(){
			isOpen = false;
			animation.setOpen(this, true);
		}
		
		public void setContent(Widget w){
			content.setWidget(w);
		}
		
		private static class ContentAnimation extends Animation {
			    /**
			     * Whether the item is being opened or closed.
			     */
			    private boolean opening;

			    /**
			     * The {@link DisclosurePanel} being affected.
			     */
			    private VerticalCollapsableDrawer curPanel;

			    /**
			     * Open or close the content.
			     *
			     * @param panel the panel to open or close
			     * @param animate true to animate, false to open instantly
			     */
			    public void setOpen(VerticalCollapsableDrawer panel, boolean animate) {
			      // Immediately complete previous open
			      cancel();

			      // Open the new item
			      if (animate) {
			        curPanel = panel;
			        opening = panel.isOpen;
			        run(1000);
			      } else {
			        panel.content.setVisible(panel.isOpen);
			        if (panel.isOpen) {
			          // Special treatment on the visible case to ensure LazyPanel works
			          panel.content.setVisible(true);
			        }
			      }
			    }

			    @Override
			    protected void onComplete() {
			      if (!opening) {
			        curPanel.content.setVisible(false);
			      }
			      DOM.setStyleAttribute(curPanel.content.getElement(), "width",
			          "auto");
			      DOM.setStyleAttribute(curPanel.content.getElement(), "overflow", "visible");
			      curPanel = null;
			    }

			    @Override
			    protected void onStart() {
			      super.onStart();
			      DOM.setStyleAttribute(curPanel.content.getElement(), "overflow", "hidden");
			      if (opening) {
			        curPanel.content.setVisible(true);
			        // Special treatment on the visible case to ensure LazyPanel works
			        curPanel.content.setVisible(true);
			     }
			    }

			    @Override
			    protected void onUpdate(double progress) {
			      int scrollWidth = DOM.getElementPropertyInt(
			          curPanel.content.getElement(), "scrollWidth");
			      int width = (int) (progress * scrollWidth);
			      if (!opening) {
			        width = scrollWidth - width;
			      }
			      width = Math.max(width, 1);

			      DOM.setStyleAttribute(curPanel.content.getElement(), "width",
			          width + "px");
			      DOM.setStyleAttribute(curPanel.content.getElement(), "height",
			          "auto");
			    }
		 }
		
		
}
