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

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.util.DebugIdUtils;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSMenu.MenuImageLocation;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.MenuChangeEvent;
import org.kuali.student.common.ui.client.widgets.menus.MenuEventHandler;
import org.kuali.student.common.ui.client.widgets.menus.MenuSelectEvent;
import org.kuali.student.common.ui.client.widgets.menus.impl.KSListMenuImpl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class StylishDropDown extends Composite{
	
	protected ClickablePanel namePanel = new ClickablePanel();
	protected SpanPanel parentPanel = new SpanPanel();
	protected boolean showSelectedItem = false;
	protected boolean showTitleIcon = false;
	protected PopupPanel menuPanel = new PopupPanel();
	protected KSListMenuImpl menu = new KSListMenuImpl();
	protected HorizontalPanel layout = new HorizontalPanel();
	protected KSLabel titleLabel = new KSLabel();
	protected Image titleImage = Theme.INSTANCE.getCommonImages().getSpacer();
	protected HorizontalPanel titleLayout = new HorizontalPanel();
	protected Image defaultArrow = Theme.INSTANCE.getCommonImages().getDropDownIconBlack();
	private boolean mouseOver = false;
	protected MenuImageLocation imgLoc = MenuImageLocation.RIGHT;
	private boolean makeButton = false;
	protected boolean enabled = true;
	
	//optional button
	private KSButton button;
	
	protected ClickHandler panelHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			if(enabled){
				if(!menuPanel.isShowing()){
					StylishDropDown.this.showMenu();
				}
				else{
					StylishDropDown.this.hideMenu();
				}
			}
			
		}
		
	};

	protected KeyDownHandler downHandler = new KeyDownHandler(){

		@Override
		public void onKeyDown(KeyDownEvent event) {
			if(enabled){
				if (event.getNativeKeyCode() == KeyboardListener.KEY_DOWN || event.getNativeKeyCode() == KeyboardListener.KEY_ENTER) 
					StylishDropDown.this.showMenu();
				else if (event.getNativeKeyCode() == KeyboardListener.KEY_UP)
					StylishDropDown.this.hideMenu();
				else if (event.getNativeKeyCode() == KeyboardListener.KEY_TAB)
				{
					StylishDropDown.this.showMenu();
					titleLayout.removeStyleName("KS-Basic-Menu-Item-Panel-Main-Hover");
				}					
			}	
		} 
	}; 

	protected FocusHandler focusHandler = new FocusHandler(){

		@Override
		public void onFocus(FocusEvent event) {
			if(enabled) 
				titleLayout.addStyleName("KS-Basic-Menu-Item-Panel-Main-Hover");
		}
	}; 

	protected MouseOverHandler mouseOverHandler = new MouseOverHandler() {

		@Override
		public void onMouseOver(MouseOverEvent event) {
			titleLayout.addStyleName("KS-Basic-Menu-Item-Panel-Main-Hover");
		}
		
	};

	protected MouseOutHandler mouseOutHandler = new MouseOutHandler() {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			titleLayout.removeStyleName("KS-Basic-Menu-Item-Panel-Main-Hover");
		}
		
	};

	private MenuEventHandler menuHandler = new MenuEventHandler(){

		@Override
		public void onChange(MenuChangeEvent e) {
			//Not needed?
			
		}

		@Override
		public void onSelect(MenuSelectEvent e) {
			KSMenuItemData i = (KSMenuItemData) e.getSource();
			StylishDropDown.this.hideMenu();
			if(showSelectedItem){
				titleLayout.clear();
				titleLabel.setText(i.getLabel());
				titleLayout.add(titleLabel);
				if(i.getShownIcon() != null){
					titleLayout.add(i.getShownIcon());
				}
			}
			
		}
	};
	
	public StylishDropDown() {}
	
	public StylishDropDown(String title){
		titleLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		titleLabel.setText(title);
		titleLayout.add(titleLabel);
		titleLayout.add(titleImage);
		init();
	}
	
	public StylishDropDown(String title, Image image, MenuImageLocation imgLoc){
		titleLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		titleLabel.setText(title);
		titleImage = image;
		titleLayout.add(titleLabel);
		if(imgLoc == MenuImageLocation.RIGHT){
			titleLayout.add(titleImage);
		}
		else{
			titleLayout.insert(titleImage, 0);
		}
		menu.setImageLocation(imgLoc);
        init();
    }

    public StylishDropDown(Widget widget) {
        titleLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        titleLayout.add(widget);
        init();
    }

    public void initialise(String title) {
        titleLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        titleLabel.setText(title);
        titleLayout.add(titleLabel);
        titleLayout.add(titleImage);
        init();
    }

    public void initialise(String title, Image image, MenuImageLocation imgLoc) {
        titleLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        titleLabel.setText(title);
        titleImage = image;
        titleLayout.add(titleLabel);
        if (imgLoc == MenuImageLocation.RIGHT) {
            titleLayout.add(titleImage);
        } else {
            titleLayout.insert(titleImage, 0);
        }
        menu.setImageLocation(imgLoc);
        init();
    }

    public void initialise(Widget widget) {
        titleLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        titleLayout.add(widget);
        init();
    }

    /**
     * This method will make the stylish drop down just a button when a list of 1 item is
     * passed in
     * @param makeButton
     */
    public void makeAButtonWhenOneItem(boolean makeButton) {
        this.makeButton = makeButton;
    }
	
	protected void init(){
		layout.clear();
		layout.setWidth("100%");
		layout.add(titleLayout);
		layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		layout.add(defaultArrow);

		namePanel.setWidget(layout);
		menu.addGlobalMenuItemSelectCallback(new Callback<KSMenuItemData>(){

			@Override
			public void exec(KSMenuItemData item) {
				if(item.getClickHandler() != null){
					StylishDropDown.this.hideMenu();
					if(showSelectedItem){
						titleLabel.setText(item.getLabel());
						if(item.getShownIcon() != null && showTitleIcon){
							titleLayout.remove(titleImage);
							Image image = item.getShownIcon();
							titleImage = new Image(image.getUrl(), image.getOriginLeft(), 
									image.getOriginTop(), image.getWidth(), image.getHeight());
							if(imgLoc == MenuImageLocation.RIGHT){
								titleLayout.add(titleImage);
							}
							else{
								titleLayout.insert(titleImage, 0);
							}
							
						}
					}
				}
			}
		});
		menuPanel.setWidget(menu);
		namePanel.addClickHandler(panelHandler);
		namePanel.addKeyDownHandler(downHandler);
		namePanel.addFocusHandler(focusHandler);
		namePanel.addMouseOverHandler(mouseOverHandler);
		namePanel.addMouseOutHandler(mouseOutHandler);
		namePanel.setTabIndex(1);
		menuPanel.setAutoHideEnabled(true);
		menuPanel.addAutoHidePartner(namePanel.getElement());
		namePanel.getElement().setAttribute("id", HTMLPanel.createUniqueId());
		parentPanel.add(namePanel);
		this.initWidget(parentPanel);
		titleLabel.addStyleName("KS-CustomDropDown-TitleLabel");
		layout.addStyleName("KS-CustomDropDown-TitlePanel");
		defaultArrow.addStyleName("KS-CustomDropDown-Arrow");
	}
	
	public void showMenu(){
		menuPanel.setPopupPosition(layout.getAbsoluteLeft(), layout.getAbsoluteTop() + layout.getOffsetHeight());
		menuPanel.show();
	}
	
	public void hideMenu(){
		menuPanel.hide();
	}
	
	public void setArrowImage(Image arrow){
		layout.remove(defaultArrow);
		arrow.addStyleName("KS-CustomDropDown-Arrow");
		layout.add(arrow);
	}
	
	public void setItems(List<KSMenuItemData> items){
		if(makeButton && items.size() == 1){
			KSMenuItemData item = items.get(0);
			button = new KSButton();
			button.addStyleName("ks-button-spacing");
			button.addClickHandler(item.getClickHandler());
			button.setText(item.getLabel());
			parentPanel.clear();
			parentPanel.add(button);
		}
		else{
			if(!namePanel.isAttached()){
				parentPanel.clear();
				parentPanel.add(namePanel);
			}
			for(KSMenuItemData item: items){
				item.addMenuEventHandler(MenuSelectEvent.TYPE, menuHandler);
				item.addMenuEventHandler(MenuChangeEvent.TYPE, menuHandler);
			}
			menu.setItems(items);
		}
		
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
		if(!enabled){
			layout.addStyleName("KS-CustomDropDown-TitlePanel-Disabled");
		}
		else{
			layout.removeStyleName("KS-CustomDropDown-TitlePanel-Disabled");
		}
		if(button != null){
			button.setEnabled(enabled);
		}
	}
	
	public void setImageLocation(MenuImageLocation loc){
		imgLoc = loc;
		menu.setImageLocation(loc);
	}
	
	@Override
	public void addStyleName(String style){
		namePanel.addStyleName(style);
		menu.addStyleName(style);
	}
	
	public boolean isShowingSelectedItem() {
		return showSelectedItem;
	}

	public void setShowSelectedItem(boolean showSelectedItem) {
		this.showSelectedItem = showSelectedItem;
	}
	
	public void setShowTitleIcon(boolean showTitleIcon){
		this.showTitleIcon = showTitleIcon;
	}
	
	public boolean isShowingTitleIcon(){
		return showTitleIcon;
	}
	
	@Override
    protected void onEnsureDebugId(String baseID) {
        super.onEnsureDebugId(baseID);
        titleLabel.ensureDebugId(DebugIdUtils.createWebDriverSafeDebugId(baseID + "-" + titleLabel.getText() + "-label"));
        layout.ensureDebugId(DebugIdUtils.createWebDriverSafeDebugId(baseID + "-" + titleLabel.getText() + "-panel"));
    }
	
	
}
