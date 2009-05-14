package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.LayoutSection;
import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.event.SaveHandler;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.events.ViewChangeEvent;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;
import org.kuali.student.common.ui.client.widgets.menus.KSBasicMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.lu.ui.course.client.widgets.SectionButtons;
import org.kuali.student.lum.lu.ui.course.client.widgets.StartSectionButtons;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DefaultCreateUpdateLayout<T extends Idable> extends ConfigurableLayout<T> {
    private final KSModalDialogPanel startSectionDialog = new KSModalDialogPanel();
    StartSectionButtons startSectionButtons = new StartSectionButtons();   
    
    private final HorizontalPanel panel = new HorizontalPanel();
	private final VerticalPanel menuPanel = new VerticalPanel();
	private final SimplePanel contentPanel = new SimplePanel();

	private final Map<String, KSMenuItemData> sectionMap = new HashMap<String, KSMenuItemData>();
	
	private List<LayoutSection<T>> sections = new ArrayList<LayoutSection<T>>();	
	
	private final List<KSMenuItemData> topLevelMenuItems = new ArrayList<KSMenuItemData>();
	private final List<KSMenuItemData> viewMenuItems = new ArrayList<KSMenuItemData>();
	
	private boolean showStart = true;
	
	
	public DefaultCreateUpdateLayout() {
		super.initWidget(panel);
		panel.add(menuPanel);
		panel.add(contentPanel);
		startSectionButtons.addCancelHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                startSectionDialog.hide();
            }		    
		});
		startSectionButtons.addSaveHandler(new SaveHandler(){
            public void onSave(SaveEvent saveEvent) {                
                //TODO: Update model & show first section in view
                startSectionDialog.hide();
            }            
        });
	}
	

	@Override
	public ConfigurableLayout<T> addSection(final String[] hierarchy,
			final LayoutSection<T> section) {
		
	    addSectionButtons(section);
	    
	    String path = "";
			
		KSMenuItemData current = null;
		for (int i=0; i<hierarchy.length; i++) {
			path = path + "/" + hierarchy[i];
			KSMenuItemData item = sectionMap.get(path);
			if (item == null) {
				item = new KSMenuItemData(hierarchy[i]);
				if (current == null) {
					topLevelMenuItems.add(item);
					current = item;
				} else {
					current.addSubItem(item);
					current = item;
				}
				sectionMap.put(path, item);
			} else {
				current = item;
			}
		}
		final String fullPath = path;
		
		current.setClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
	                fireEvent(new ViewChangeEvent(new View() {

	                    @Override
	                    public boolean beforeHide() {
	                        return false;
	                    }

	                    @Override
	                    public void beforeShow() {
	                    }

	                    @Override
	                    public Controller getController() {
	                        return null;
	                    }

	                    @Override
	                    public String getName() {
	                        return null;
	                    }}, new View() {

	                        @Override
	                        public boolean beforeHide() {
	                            return false;
	                        }

	                        @Override
	                        public void beforeShow() {
	                        }

	                        @Override
	                        public Controller getController() {
	                            return null;
	                        }

	                        @Override
	                        public String getName() {
	                            return fullPath;
	                        }}));
				showSection(section);
			}
		});
		
		//This assumes that sections are added in order
		sections.add(section);
		return this;
	}
	String[] initHierarchy;
	public void selectSection(String...hierarchy) {
        String path = "";
        
        if(hierarchy.length == 1 && hierarchy[0].startsWith("/")) {
            path = hierarchy[0];
            hierarchy = path.split("/");
        } else
            for (int i=0; i<hierarchy.length; i++)
                path = path + "/" + hierarchy[i];
        
        if(menuPanel.getWidgetCount() == 0) {
            initHierarchy = hierarchy;
        } else {
            KSBasicMenu proposalMenu = (KSBasicMenu)menuPanel.getWidget(1);
            proposalMenu.selectMenuItem(hierarchy);
        }
	}
	
	public ConfigurableLayout<T> addStartSection(LayoutSection<T> section){
	    //TODO: Make addSectionButtons part of LayoutSection interface
	    SimpleConfigurableSection simpleSection = (SimpleConfigurableSection)section;
	    simpleSection.addSectionButtons(startSectionButtons);
	    startSectionDialog.setWidget(simpleSection);
	    return this;
	}
	
	public ConfigurableLayout<T> addViewSection(String viewName,
            final LayoutSection<T> section) {
	    KSMenuItemData item = new KSMenuItemData(viewName);
	    viewMenuItems.add(item);
	    item.setClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                showSection(section);
            }
        });
	    
	    return this;
	}
	
	protected void showSection(LayoutSection<T> section) {
		contentPanel.clear();
		contentPanel.setWidget(section);
	}

	public void setShowStartSectionEnabled(boolean showStart){
	    this.showStart = showStart;
	}
	
	public boolean isShowStartSectionEnabled() {
	    return showStart;
	}
	
	@Override
	public void render() {
		final KSBasicMenu sectionMenu = new KSBasicMenu();
		sectionMenu.setTitle("Proposal Sections");
		sectionMenu.setDescription("complete sections to submit");
		sectionMenu.setItems(topLevelMenuItems);
		final KSBasicMenu viewMenu = new KSBasicMenu();
        viewMenu.setTitle("Views...");
        viewMenu.setItems(viewMenuItems);
		menuPanel.clear();
		menuPanel.add(viewMenu);
		menuPanel.add(sectionMenu);
		
		//Add next section button handlers, this code assumes section was added in order
		//and that they appear in same sequence in the menu.
		int sectionIndex = 0;
		for (KSMenuItemData menuItem:topLevelMenuItems){
		    for (KSMenuItemData subItem:menuItem.getSubItems()){
		        if (sectionIndex > 0){
    		        SimpleConfigurableSection prevSection = (SimpleConfigurableSection)sections.get(sectionIndex-1);
    		        SectionButtons sectionButtons = (SectionButtons)prevSection.getSectionButtons();
    		        sectionButtons.addNextSectionClickHandler(getNextSectionClickHandler(subItem));
		        }
		        sectionIndex++;
		    }
		}
		
		contentPanel.clear();
		
		if(initHierarchy != null)
		    sectionMenu.selectMenuItem(initHierarchy);
        
		if (showStart){
		    startSectionDialog.show();
		}
	}
	
	protected ClickHandler getNextSectionClickHandler(final KSMenuItemData menuItem){
	    ClickHandler handler = new ClickHandler(){
            public void onClick(ClickEvent event) {
                menuItem.setSelected(true);
            }	        
	    };
	    return handler;
	}

	protected void addSectionButtons(final LayoutSection<T> section){
        SectionButtons buttons = new SectionButtons();
        final SimpleConfigurableSection simpleSection = (SimpleConfigurableSection)section;
	    buttons.addSaveClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                simpleSection.setEditMode(EditMode.VIEW_ONLY);
            }                
        });
        
        buttons.addEditClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                simpleSection.setEditMode(EditMode.EDITABLE);
            }        
        });
        simpleSection.addSectionButtons(buttons);
	}
	
	public void addSaveSectionHandler(SaveHandler handler){
	    
	}
	
	public void addSaveStartSectionHandler(SaveHandler handler){
	    startSectionButtons.addSaveHandler(handler);
	}
	
	public void addCancelSectionHandler(ClickHandler handler){
	    startSectionButtons.addCancelHandler(handler);
	}

}
