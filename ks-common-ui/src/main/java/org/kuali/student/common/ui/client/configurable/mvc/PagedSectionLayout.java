package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.menus.KSBasicMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class PagedSectionLayout extends Controller implements ConfigurableLayout {
      
    private final DockPanel panel = new DockPanel();
    
	private final VerticalPanel menuPanel = new VerticalPanel();
	private final SimplePanel contentPanel = new SimplePanel();
	private final HorizontalPanel buttonPanel = new HorizontalPanel();

	//FIXME: Better way to manage hierarchy, ordering, and handle to views
	private final Map<String, KSMenuItemData> menuHierarchyMap = new HashMap<String, KSMenuItemData>();
	private final HashMap<String, SectionView> sectionViewMap = new HashMap<String, SectionView>();	
	private final ArrayList<KSMenuItemData> sectionMenuItems = new ArrayList<KSMenuItemData>();
	
	private final List<KSMenuItemData> topLevelMenuItems = new ArrayList<KSMenuItemData>();
	private final List<KSMenuItemData> viewMenuItems = new ArrayList<KSMenuItemData>();
	
    private KSLightBox startSectionWindow = new KSLightBox();
    
	private boolean loaded = false;
	private int currMenuItemIdx = 0;
	
	private Class<? extends ModelDTO> modelDTOType;
	
	private KSButton nextButton = new KSButton("Next", new ClickHandler(){
        public void onClick(ClickEvent event) {
            currMenuItemIdx++;
            sectionMenuItems.get(currMenuItemIdx).setSelected(true);
        }	    
    }
	);
				
	public PagedSectionLayout() {
		super.initWidget(panel);
		panel.add(menuPanel, DockPanel.WEST);
		panel.add(contentPanel, DockPanel.CENTER);
		panel.add(buttonPanel, DockPanel.SOUTH); 
	}
	
	private void init(){
        final KSBasicMenu sectionMenu = new KSBasicMenu();
        sectionMenu.setTitle("Proposal Sections");
        sectionMenu.setDescription("complete sections to submit");
        sectionMenu.setItems(topLevelMenuItems);
        final KSBasicMenu viewMenu = new KSBasicMenu();
        viewMenu.setTitle("Tools");
        viewMenu.setItems(viewMenuItems);
        menuPanel.clear();
        menuPanel.add(viewMenu);
        menuPanel.add(sectionMenu);

        buttonPanel.add(nextButton);         
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
			    if (currMenuItemIdx != newMenuItemIdx){
                    currMenuItemIdx = newMenuItemIdx;
    			    showView(section.getViewEnum());
			    }
			}
		});
		
		sectionViewMap.put(section.getViewEnum().name(), section);
		section.setController(this);
	}
	
	public void addStartSection(final SectionView section){
	    VerticalPanel panel = new VerticalPanel();
        
	    panel.add(section);
	    panel.add(new KSButton("Create",new ClickHandler(){
            public void onClick(ClickEvent event) {
                //Fire create event, and close on successful event, or expose
                //a way to close the window.
                startSectionWindow.hide();
            }	        
	    }));

        section.setController(this);
	    section.beforeShow();
	    startSectionWindow.setWidget(panel);
	}
	
	public void addButton(KSButton button){
	    buttonPanel.add(button);	    
	}
	
	/** 
	 * @see org.kuali.student.common.ui.client.mvc.Controller#renderView(org.kuali.student.common.ui.client.mvc.View)
	 */
	@Override
	public void renderView(View view) {
	    if (currMenuItemIdx == sectionMenuItems.size() - 1){
	        nextButton.setVisible(false);
	    } else {
	        nextButton.setVisible(true);
	    }
	    contentPanel.setWidget((Composite)view); 
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
        requestModel(modelDTOType, defaultViewCallback);
    }
    
    private ModelRequestCallback<ModelDTO> defaultViewCallback = new ModelRequestCallback<ModelDTO>(){

        public void onModelReady(Model<ModelDTO> model) {
            if (!loaded){
                init();
                loaded = true;
            }
                            
            sectionMenuItems.get(0).setSelected(true);
            
            ModelDTO modelDTO = model.get();
            if (modelDTO.get("id") == null){                
                startSectionWindow.show();
            } 
        }

        public void onRequestFail(Throwable cause) {
            // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
            
        }
        
    };

    public Class<? extends ModelDTO> getModelDTOType() {
        return modelDTOType;
    }

    public void setModelDTOType(Class<? extends ModelDTO> modelDTOType) {
        this.modelDTOType = modelDTOType;
    }

}
