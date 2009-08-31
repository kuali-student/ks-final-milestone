package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.menus.KSBasicMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class PagedSectionLayout extends Controller implements ConfigurableLayout {
      
    private final DockPanel panel = new DockPanel();
    
	private final VerticalPanel menuPanel = new VerticalPanel();
	private final SimplePanel contentPanel = new SimplePanel();
	private final HorizontalPanel sectionButtonPanel = new HorizontalPanel();

	//FIXME: Better way to manage hierarchy, ordering, and handle to views
	private final Map<String, KSMenuItemData> menuHierarchyMap = new HashMap<String, KSMenuItemData>();
	private final HashMap<String, View> sectionViewMap = new HashMap<String, View>();	
	private final ArrayList<KSMenuItemData> sectionMenuItems = new ArrayList<KSMenuItemData>();
	
	private final List<KSMenuItemData> topLevelMenuItems = new ArrayList<KSMenuItemData>();
	private final List<KSMenuItemData> toolMenuItems = new ArrayList<KSMenuItemData>();
	private KSBasicMenu toolMenu;
	private KSBasicMenu sectionMenu;
	
    private KSLightBox startSectionWindow = new KSLightBox();
    private SectionView startSectionView;
    
	private boolean loaded = false;
	
	private int currSectionIdx = 0;
	
	Enum<?> defaultView = null;
	
	private Class<? extends ModelDTO> modelDTOType;
	
	private String sectionMenuTitle = "Proposal";
	private String toolMenuTitle = "Tools";
		
	private KSButton nextButton = new KSButton("Next", new ClickHandler(){
        public void onClick(ClickEvent event) {
            currSectionIdx++;
            sectionMenuItems.get(currSectionIdx).setSelected(true);
        }	    
    }
	);
				
	public PagedSectionLayout() {
		super.initWidget(panel);
		panel.add(menuPanel, DockPanel.WEST);
		panel.add(contentPanel, DockPanel.CENTER);
		panel.add(sectionButtonPanel, DockPanel.SOUTH); 
	}
	
	private void init(){
        sectionMenu = new KSBasicMenu();
        sectionMenu.setTitle(sectionMenuTitle);
        sectionMenu.setDescription("complete sections to submit");
        sectionMenu.setItems(topLevelMenuItems);
        
        toolMenu = new KSBasicMenu();
        toolMenu.setTitle(toolMenuTitle);
        toolMenu.setItems(toolMenuItems);
        menuPanel.clear();
        menuPanel.add(toolMenu);
        menuPanel.add(sectionMenu);

        sectionButtonPanel.add(nextButton);         
	}
	
	@Override
	public void addSection(final String[] hierarchy, final SectionView section) {
		    
	    String path = "";	    
			
		KSMenuItemData current = null;
		for (int i=0; i<hierarchy.length; i++) {
			path = path + "/" + hierarchy[i];
			KSMenuItemData item = menuHierarchyMap.get(path);
			if (item == null) {
				item = new KSMenuItemData(hierarchy[i]);
				if (current == null) {
					topLevelMenuItems.add(item);
					current = item;
				} else {
					current.addSubItem(item);
					current = item;
				}
				menuHierarchyMap.put(path, item);
			} else {
				current = item;
			}
		}
		
		final KSMenuItemData sectionItem = new KSMenuItemData(section.getName());
		current.addSubItem(sectionItem);
		sectionMenuItems.add(sectionItem);
		
		sectionItem.setClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			    int newMenuItemIdx = sectionMenuItems.indexOf(sectionItem);
			    if (currSectionIdx != newMenuItemIdx){
                    currSectionIdx = newMenuItemIdx;
                    toolMenu.clearSelected();
                    sectionButtonPanel.setVisible(true);
    			    showView(section.getViewEnum());
			    }
			}
		});
		
		sectionViewMap.put(section.getViewEnum().name(), section);
		section.setController(this);
		
		if (defaultView == null){
		    defaultView = section.getViewEnum();
		}
	}
	
    @Override
    public void addTool(final ToolView tool) {
        KSMenuItemData item = new KSMenuItemData(tool.getName());
        toolMenuItems.add(item);
        item.setClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                currSectionIdx = -1;
                sectionMenu.clearSelected();
                sectionButtonPanel.setVisible(false);
                showView(tool.getViewEnum());
            }
        }); 
        
        sectionViewMap.put(tool.getViewEnum().name(), tool);
        tool.setController(this);
    }
    
    public void setSectionMenuTitle(String title){
        this.sectionMenuTitle = title;
    }
    
    public void setToolMenuTitle(String title){
        this.toolMenuTitle = title;
    }    
	
	public void addStartSection(final SectionView section){
	    startSectionView = section;
	    
	    VerticalPanel panel = new VerticalPanel();        	    
	    panel.add(section);
	    panel.add(new KSButton("Save",new ClickHandler(){
            public void onClick(ClickEvent event) {
                section.updateModel();
                SaveActionEvent saveActionEvent = new SaveActionEvent();
                
                saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
                    public void onActionComplete(ActionEvent action) {
                        startSectionWindow.hide();
                    }                    
                });
                
                fireApplicationEvent(saveActionEvent);
            }	        
	    }));

        section.setController(this);
	    startSectionWindow.setWidget(panel);
	}
		
	public void addButton(KSButton button){
	    sectionButtonPanel.add(button);	    
	}
	
	/** 
	 * @see org.kuali.student.common.ui.client.mvc.Controller#renderView(org.kuali.student.common.ui.client.mvc.View)
	 */
	@Override
	public void renderView(View view) {
	    if (currSectionIdx == sectionMenuItems.size() - 1){
	        nextButton.setVisible(false);
	    } else {
	        nextButton.setVisible(true);
	    }
	    contentPanel.setWidget((Widget)view); 
	}	
	
    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#getView(java.lang.Enum)
     */
    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        return sectionViewMap.get(viewType.name());
    }
	
    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#hideView(org.kuali.student.common.ui.client.mvc.View)
     */
    @Override
    protected void hideView(View view) {
        contentPanel.clear();        
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#showDefaultView()
     */
    @Override
    public void showDefaultView() {
        if (!loaded){
            init();
            loaded = true;
        }
                   
        showView(defaultView);
    }
    
    public void showStartSection(){
        startSectionView.beforeShow();        
        startSectionWindow.show();
    }
    public Class<? extends ModelDTO> getModelDTOType() {
        return modelDTOType;
    }

    public void setModelDTOType(Class<? extends ModelDTO> modelDTOType) {
        this.modelDTOType = modelDTOType;
    }

}
