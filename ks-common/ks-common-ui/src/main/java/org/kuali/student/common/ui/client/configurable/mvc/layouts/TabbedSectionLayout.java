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

package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.containers.KSTitleContainerImpl;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.impl.KSBlockMenuImpl;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel.TabPosition;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class TabbedSectionLayout extends LayoutController implements ConfigurableLayout{

	//FIXME: Better way to manage hierarchy, ordering, and handle to views
	private final Map<String, KSMenuItemData> menuHierarchyMap = new HashMap<String, KSMenuItemData>();

	private Map<String, TabLayout> tabLayoutMap = new HashMap<String, TabLayout>();

	private Map<String, String> sectionNameTabMap = new HashMap<String, String>();

	private boolean loaded = false;
	private final Map<String, Enum<?>> viewEnums = new HashMap<String, Enum<?>>();

	Enum<?> defaultView = null;

	private KSTabPanel tabPanel = new KSTabPanel();
	private KSTitleContainerImpl container = new KSTitleContainerImpl();

	private boolean updateableSection = true;

	private class TabLayout extends Composite{
		private FlowPanel layout = new FlowPanel();
		private SimplePanel content = new SimplePanel();
		private FlowPanel contentLayout = new FlowPanel();
		private KSBlockMenuImpl menu = new KSBlockMenuImpl();
		//KSBasicMenu menu = new KSBasicMenu();
		private int currSectionIdx = 0;
		protected final ArrayList<View> orderedSectionViews = new ArrayList<View>();
		private final HorizontalPanel sectionButtonPanel = new HorizontalPanel();
		private final ArrayList<KSMenuItemData> sectionMenuItems = new ArrayList<KSMenuItemData>();
		private final List<KSMenuItemData> topLevelMenuItems = new ArrayList<KSMenuItemData>();
		private boolean menuAdded = false;
		private Enum<?> tabDefaultView = null;

		public Enum<?> getTabDefaultView() {
			return tabDefaultView;
		}

		public void setTabDefaultView(Enum<?> tabDefaultView) {
			this.tabDefaultView = tabDefaultView;
		}

		public HorizontalPanel getButtonPanel(){
		    return this.sectionButtonPanel;
		}

		public KSButton getNextButton() {
		    return nextButton;
		}

		private KSButton nextButton = new KSButton("Save & Continue", new ClickHandler(){
	        public void onClick(final ClickEvent event) {

                final SaveActionEvent saveActionEvent = new SaveActionEvent();
                saveActionEvent.setAcknowledgeRequired(false);
                saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
                    public void onActionComplete(ActionEvent action) {
                        int nextSectionIndex = currSectionIdx + 1;
                        // FIXME this is not safe for all sorts of reasons, do not call handlers directly like this.
                        if (nextSectionIndex < sectionMenuItems.size()) {
                            sectionMenuItems.get(nextSectionIndex).getClickHandler().onClick(event);
                        }
                    }
                });

                fireApplicationEvent(saveActionEvent);
	        }
	    }
		);

		public TabLayout(){
		    if (updateableSection) {
	            sectionButtonPanel.add(nextButton);
		    }
			menu.setTopLevelItems(topLevelMenuItems);
			contentLayout.add(content);
			contentLayout.add(sectionButtonPanel);

			layout.add(contentLayout);
			this.initWidget(layout);
		}

		public void init(){
			contentLayout.setStyleName("ks-page-content");
			//menu.addStyleName("KS-TabbedSectionLayout-Menu"); // FIXME keep or delete?
			menu.setStyleName("ks-page-sub-navigation-container");
			menu.setTopLevelItems(topLevelMenuItems);
		}

		public void setContent(Widget content){
			this.content.setWidget(content);
		}

		public void addMenuItem(String[] hierarchy, final SectionView section) {
			String path = "";
			StringBuilder pathBuffer= new StringBuilder();
			pathBuffer.append(path);
			KSMenuItemData current = null;
			for (int i=1; i<hierarchy.length; i++) {
						    // For configurable section layout the hierarchy element obtained from XML file might contain
			   // null element. In such a case we dont require a menu to be displayed on the screen.
			    if(hierarchy[i]==null){
			        return;
			    }
				pathBuffer.append("/");
				pathBuffer.append(hierarchy[i]);
				KSMenuItemData item = menuHierarchyMap.get(pathBuffer.toString());
				if (item == null) {
					item = new KSMenuItemData(hierarchy[i]);
					if (current == null) {
						topLevelMenuItems.add(item);
						current = item;
					} else {
						current.addSubItem(item);
						current = item;
					}
					menuHierarchyMap.put(pathBuffer.toString(), item);
				} else {
					current = item;
				}
			}

			final KSMenuItemData sectionItem = new KSMenuItemData(section.getName());
			current.addSubItem(sectionItem);
			sectionMenuItems.add(sectionItem);
			orderedSectionViews.add(section);

			sectionItem.setClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {

				    int newMenuItemIdx = sectionMenuItems.indexOf(sectionItem);
				    if (currSectionIdx != newMenuItemIdx){
	                    currSectionIdx = newMenuItemIdx;
	                    sectionButtonPanel.setVisible(true);
	    			    showView(section.getViewEnum(), NO_OP_CALLBACK);
				    }
				}
			});

			if(!menuAdded){
				layout.insert(menu, 0);
				menuAdded = true;
			}

			if (tabDefaultView == null){
			    tabDefaultView = section.getViewEnum();
			}
		}

		public void renderView(View view) {
			content.setWidget((Widget)view);
			if(menuAdded){
			    if (currSectionIdx == sectionMenuItems.size() - 1){
			        nextButton.setVisible(false);
			    } else {
			        nextButton.setVisible(true);
			    }
		        currSectionIdx = orderedSectionViews.indexOf(view);
		        if(currSectionIdx == -1){
		            return;
		        }
				sectionMenuItems.get(currSectionIdx).setSelected(true);
			}
			else{
				nextButton.setVisible(false);
			}
		}

		public void removeContent() {
			content.clear();
		}

		public void addButton(KSButton button) {
			sectionButtonPanel.add(button);
		}

		public void clear() {
	        for (View view:orderedSectionViews){
	            view.clear();
	        }

		}

		public void updateModel() {
            for(View sectionView : orderedSectionViews){
            	sectionView.updateModel();
            }

		}

		public void beforeShow(final Callback<Boolean> onReadyCallback) {
			showView(tabDefaultView, onReadyCallback);
		}


	}

	private void init(){
    	for(TabLayout layout: tabLayoutMap.values()){
			layout.init();
		}
	}

	public TabbedSectionLayout(String controllerId){
	    super(controllerId);
	    container.setContent(tabPanel);
		container.setTitle("New Course Proposal");
		super.initWidget(container);
	}

	public TabbedSectionLayout(String controllerId, KSTitleContainerImpl container){
	    super(controllerId);
	    this.container.setContent(tabPanel);
        this.container.setTitle(container.getTitle());
        this.container.setStatus(container.getStatus());
        this.container.setLinkText(container.getLinkText());
        super.initWidget(this.container);
	}


    public KSTitleContainerImpl getContainer(){
        return this.container;
    }

    public void setContainer(KSTitleContainerImpl container){
        this.container=container;
    }

	@Override
	public <V extends Enum<?>> void getView(V viewType, Callback<View> callback) {
		callback.exec(viewMap.get(viewType));
	}

	@Override
    public Enum<?> getViewEnumValue(String enumValue) {
        return viewEnums.get(enumValue);
    }

	public boolean isUpdateableSection() {
        return updateableSection;
    }

    public void setUpdateableSection(boolean isUpdateable) {
        this.updateableSection = isUpdateable;
    }

    @Override
	protected void hideView(View view) {
		//Does nothing: no need to hide, it view is always replaced in this layout
/*		String tabName = sectionNameTabMap.get(view.getName());
		TabLayout layout = tabLayoutMap.get(tabName);
		layout.removeContent();*/
	}

	@Override
	protected void renderView(View view) {
		String tabName = sectionNameTabMap.get(view.getName());
		if(!(tabPanel.getSelectedTabKey().equals(tabName))){
			tabPanel.selectTab(tabName);
		}
		TabLayout layout = tabLayoutMap.get(tabName);

		layout.renderView(view);
		view.getName();
	}

	@Override
	public void showDefaultView(final Callback<Boolean> onReadyCallback) {
        if (!loaded){
            init();
            loaded = true;
        }

        super.showDefaultView(onReadyCallback);
	}

	@Override
	public void addSection(String[] hierarchy, final SectionView section) {
		viewEnums.put(section.getViewEnum().toString(), section.getViewEnum());
		String tabKey = hierarchy[0];

		sectionNameTabMap.put(section.getName(), tabKey);
		viewMap.put(section.getViewEnum(), section);
		section.setController(this);
		section.setLayoutController(this);

		final TabLayout layout;
		if(!(tabPanel.hasTabKey(tabKey))){
			layout = new TabLayout();
			tabLayoutMap.put(tabKey, layout);
			tabPanel.addTab(tabKey, tabKey, layout);
			if(section != null){
				layout.setTabDefaultView(section.getViewEnum());
			}

			//Handler for when tab is clicked
			tabPanel.addTabCustomCallback(tabKey, new Callback<String>(){

				@Override
				public void exec(String result) {
					layout.beforeShow(NO_OP_CALLBACK);
				}

			});
		}
		else{
			layout = tabLayoutMap.get(tabKey);
		}

		if(hierarchy.length > 1){
			layout.addMenuItem(hierarchy, section);
		}
		else{
			layout.renderView(section);
		}

		if (defaultView == null){
		    defaultView = section.getViewEnum();
		}
	}

	@Override
	public void addTool(final ToolView tool) {
		viewEnums.put(tool.getViewEnum().toString(), tool.getViewEnum());
		String tabKey = tool.getName();

		TabLayout layout;
		if(!(tabPanel.hasTabKey(tabKey))){
			layout = new TabLayout();
			tabLayoutMap.put(tabKey, layout);
			tabPanel.addTab(tabKey, tabKey, tool.getImage(), layout, TabPosition.RIGHT);

			tabPanel.addTabCustomCallback(tabKey, new Callback<String>(){

				@Override
				public void exec(String result) {
					//layout.beforeShow();
					showView(tool.getViewEnum(), NO_OP_CALLBACK);
				}

			});
		}
		else{
			layout = tabLayoutMap.get(tabKey);
		}

		//layout.renderView(tool);
		sectionNameTabMap.put(tool.getName(), tabKey);
		viewMap.put(tool.getViewEnum(), tool);
        tool.setController(this);

	}

	public void addToolbar(Widget toolbar){
		this.container.setToolbar(toolbar);
	}

    public void showStartSection(final Callback<Boolean> onReadyCallback){
        this.showStartPopup(onReadyCallback);
    }

    public SectionView getStartSection(){
    	if(startPopupView instanceof SectionView){
    		return (SectionView)startPopupView;
    	}
    	else{
    		return null;
    	}
        
    }

    @Override
	public void addStartSection(final SectionView section){
	    this.addStartViewPopup(section);

	    HorizontalPanel buttonPanel = new HorizontalPanel();

	    VerticalPanel panel = new VerticalPanel();
	    panel.add(section);
	    buttonPanel.add(new KSButton("Save",new ClickHandler(){
            public void onClick(ClickEvent event) {
                section.updateModel();
                SaveActionEvent saveActionEvent = new SaveActionEvent();

                saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
                    public void onActionComplete(ActionEvent action) {
                        startViewWindow.hide();
                    }
                });

                fireApplicationEvent(saveActionEvent);
            }
	    }));
	    buttonPanel.add(new KSButton("Cancel", new ClickHandler(){
            public void onClick(ClickEvent event) {
                startViewWindow.hide();
            }
	    }));

	    panel.add(buttonPanel);
        section.setController(this);
	    startViewWindow.setWidget(panel);
	}

	public void addButton(String tabKey, KSButton button){
		TabLayout layout = tabLayoutMap.get(tabKey);

		if(layout != null){
			layout.addButton(button);
		}

	}

    public HorizontalPanel getButtonPanel(String tabKey){
        TabLayout layout = tabLayoutMap.get(tabKey);

        if(layout != null){
            return layout.getButtonPanel();
        }
        return null;
    }

    public KSButton getNextButton(String tabKey) {
        TabLayout layout = tabLayoutMap.get(tabKey);

        if (layout != null) {
            return layout.getNextButton();
        }
        return null;
    }

    public void clear(){
    	super.clear();
    	for(TabLayout layout: tabLayoutMap.values()){
			layout.clear();
		}


    }

    public void updateModel(){
    	for(TabLayout layout: tabLayoutMap.values()){
			layout.updateModel();
		}
    }

	@Override
	public Class<? extends Enum<?>> getViewsEnum() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
 	 * Check to see if current/all section(s) is valid (ie. does not contain any errors)
 	 *
	 * @param validationResults List of validation results for the layouts model.
	 * @param checkCurrentSectionOnly true if errors should be checked on current section only, false if all sections should be checked
	 * @return true if the specified sections (all or current) has any validation errors
	 */
	public boolean isValid(List<ValidationResultInfo> validationResults, boolean checkCurrentSectionOnly){
		boolean isValid = true;

		if (checkCurrentSectionOnly){
			//Check for validation errors on the currently displayed section only
	    	if(this.isStartViewShowing()){
	    		isValid = isValid(validationResults, getStartSection());
	    	} else {
	    		View v = getCurrentView();
	        	if(v instanceof Section){
	        		isValid = isValid(validationResults, (Section)v);
	        	}
	    	}
		} else {
			//Check for validation errors on all sections
			container.clearMessages();
			String errorSections = "";
			StringBuilder errorSectionsbuffer = new StringBuilder();
			errorSectionsbuffer.append(errorSections);
			for (Entry<Enum<?>, View> entry:viewMap.entrySet()) {
				View v = entry.getValue();
				if (v instanceof Section){
					if (!isValid(validationResults, (Section)v)){
						isValid = false;
						errorSectionsbuffer.append(((SectionView)v).getName() + ", ");
//						errorSections += ((SectionView)v).getName() + ", ";
					}
				}
			}
			errorSections = errorSectionsbuffer.toString();
			if (!errorSections.isEmpty()){
				errorSections = errorSections.substring(0, errorSections.length()-2);
				container.addMessage("Following section(s) has errors & must be corrected: " + errorSections);
			}
		}

		return isValid;
	}

	private boolean isValid(List<ValidationResultInfo> validationResults, Section section){
		section.setFieldHasHadFocusFlags(true);
		ErrorLevel status = section.processValidationResults(validationResults);

		return (status != ErrorLevel.ERROR);
	}
}
