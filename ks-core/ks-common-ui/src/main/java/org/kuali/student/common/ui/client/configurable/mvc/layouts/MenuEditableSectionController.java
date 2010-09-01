package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;

public class MenuEditableSectionController extends MenuSectionController{
	
	private boolean editMode = false;
	private boolean isEditable = true;
	
	//better way to do this other than 2 maps?
	private Map<Enum<?>, Enum<?>> readOnlyToEditMap = new HashMap<Enum<?>, Enum<?>>();
	private Map<Enum<?>, Enum<?>> editToReadOnlyMap = new HashMap<Enum<?>, Enum<?>>();
	
	private List<View> readOnlyViews = new ArrayList<View>();
	private List<KSButton> editLinks = new ArrayList<KSButton>();
	
	private Callback<Boolean> editLinkCallback = new Callback<Boolean>(){

		@Override
		public void exec(Boolean result) {
			if(result == true){
				KSMenuItemData item = viewMenuItemMap.get(getCurrentView().getViewEnum());
				if(item == null){
					Enum<?> readOnlyEnum = editToReadOnlyMap.get(getCurrentView().getViewEnum());
					if(readOnlyEnum != null){
						item = viewMenuItemMap.get(readOnlyEnum);
					}
				}
				if(item != null && !item.isSelected()){
					item.setSelected(true, false);
				}
			}
		}
	};

	public MenuEditableSectionController(String controllerId) {
		super(controllerId);
	}
	
	public void addMenuItem(String parentMenu, final View readOnlyView, final View editView){
		super.addMenuItem(parentMenu, readOnlyView);
		this.addView(editView);
		readOnlyToEditMap.put(readOnlyView.getViewEnum(), editView.getViewEnum());
		editToReadOnlyMap.put(editView.getViewEnum(), readOnlyView.getViewEnum());
		attachEditLink(readOnlyView);
	}
	
	public void attachEditLink(final View v){
		readOnlyViews.add(v);
		createEditLink(v);
	}
	
	public KSButton generateEditLink(final View v){
		KSButton editLink = new KSButton(Application.getApplicationContext().getMessage("edit"), ButtonStyle.DEFAULT_ANCHOR, new ClickHandler(){
			
			@Override
			public void onClick(ClickEvent event) {
				editMode = true;
				showView(v.getViewEnum(), editLinkCallback);
			}
		});
		editLinks.add(editLink);
		
		editLink.addStyleName("ks-header-edit-link");
		
		return editLink;
	}
	
	private void createEditLink(final View v){
		if(isEditable){
			KSButton editLink = generateEditLink(v);
			
			if(v instanceof SectionView){
				//TODO Change to put in section header
				SectionTitle title = ((SectionView) v).getLayout().getLayoutTitle();
				if(title != null){
					title.add(editLink);
				}
				else{
					((SectionView) v).getLayout().insert(editLink, 0);
				}
			}
			else if(v.asWidget() instanceof FlowPanel){
				((FlowPanel)v.asWidget()).insert(editLink, 0);
			}
		}
	}
	
	private void detachEditLinks(){
		for(KSButton b: editLinks){
			b.removeFromParent();
		}
	}
	
	@Override
	public <V extends Enum<?>> void showView(V viewType, Callback<Boolean> onReadyCallback){
		//show the edit view only
		if(editMode && isEditable){
			Enum<?> editViewEnum = readOnlyToEditMap.get(viewType);
			if(editViewEnum != null){
				super.showView(editViewEnum, onReadyCallback);
			}
			else{
				super.showView(viewType, onReadyCallback);
			}
		}
		//show the read only view only
		else{
			Enum<?> readOnlyEnum = editToReadOnlyMap.get(viewType);
			if(readOnlyEnum != null){
				super.showView(readOnlyEnum, onReadyCallback);
			}
			else{
				super.showView(viewType, onReadyCallback);
			}
		}
	}
	
	public void setEditable(boolean editable){
		if(editable && this.isEditable != true){
			this.isEditable = editable;
			for(View v: readOnlyViews){
				createEditLink(v);
			}
		}
		else if(!editable && this.isEditable == true){
			this.isEditable = editable;
			detachEditLinks();
		}
	}
	
	public void setEditMode(final boolean editMode){
		if(this.editMode != editMode){
			this.editMode = editMode;
			this.showView(this.getCurrentViewEnum(), new Callback<Boolean>(){

				@Override
				public void exec(Boolean result) {
					if(false){
						MenuEditableSectionController.this.editMode = !editMode;
					}
				}
			});
		}
	}
	
	public void addCommonEditButton(String parentMenu, KSButton button){
		if(parentMenu != null){
			List<View> views = menuViewMap.get(parentMenu);
			if(views != null){
				for(int i=0; i < views.size(); i++){
					Enum<?> editEnum = readOnlyToEditMap.get(views.get(i).getViewEnum());
					if(editEnum != null){
						addButtonForView(editEnum, button);
					}
				}
			}
		}
	}
	
}
