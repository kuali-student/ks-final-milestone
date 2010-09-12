package org.kuali.student.common.ui.client.widgets.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.client.LookupMetadata;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.KSButton.ButtonImageAlign;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;

public class CrossContextSearchBar {
	
	private KSDropDown dropdown = new KSDropDown();
	private Map<String, Metadata> metaKeyMetadataMap;
	private Map<String, String> metaKeyLookupKeyMap;
	private LookupMetadata selectedLookup;
	private VerticalFlowPanel layout = new VerticalFlowPanel();
	private HorizontalBlockFlowPanel searchBarContainer = new HorizontalBlockFlowPanel();
	private KSTextBox searchField = new KSTextBox();
	private KSButton searchButton = new KSButton("Go");
	private KSLabel searchNameLabel = new KSLabel();

	private Metadata selectedMeta;
	/**
	 * @param metaKeyMetadataMap metakey to metadata map, the key is used to generate the appropriate message and
	 * used to link the appropriate lookup to the the search bar using the second map
	 * @param metaKeyLookupKeyMap metaKey to lookup map - links the appropriate lookup IN THE metadata specified by
	 * the key to the searchbar... this is the lookup that will be used for that metadata initially
	 */
	public CrossContextSearchBar(String searchName, final Map<String, Metadata> metaKeyMetadataMap, Map<String, String> metaKeyLookupKeyMap){
/*		searchNameLabel.setText(searchName);
		searchButton.setImage(Theme.INSTANCE.getCommonImages().getSearchIcon().getImage(), ButtonImageAlign.RIGHT);
		this.metaKeyMetadataMap = metaKeyMetadataMap;
		this.metaKeyLookupKeyMap = metaKeyLookupKeyMap;
		Iterator<String> it = metaKeyMetadataMap.keySet().iterator();
		final List<String> keys = new ArrayList<String>();
		while(it.hasNext()){
			String key = it.next();
			keys.add(key);
		}
		dropdown.setBlankFirstItem(false);
		dropdown.setListItems(new ListItems(){

			@Override
			public List<String> getAttrKeys() {
				//nothing
				return new ArrayList<String>();
			}

			@Override
			public String getItemAttribute(String id, String attrkey) {
				//nothing
				return "";
			}

			@Override
			public int getItemCount() {
				return CrossContextSearchBar.this.metaKeyMetadataMap.size();
			}

			@Override
			public List<String> getItemIds() {
				return keys;
			}

			@Override
			public String getItemText(String id) {
				//TODO get message here
				return id;
			}
		});
		
		changeSearchSelection(dropdown);
		
		dropdown.addSelectionChangeHandler(new SelectionChangeHandler(){

			@Override
			public void onSelectionChange(KSSelectItemWidgetAbstract w) {
				CrossContextSearchBar.this.changeSearchSelection(w);
			}
		});

	}
	
	private void changeSearchSelection(KSSelectItemWidgetAbstract w){
		String key = w.getSelectedItem();
		selectedMeta = CrossContextSearchBar.this.metaKeyMetadataMap.get(key);
		String lookupKey = CrossContextSearchBar.this.metaKeyLookupKeyMap.get(key);
		for(LookupMetadata lookup: selectedMeta.getLookupMetadata()){
			if(lookupKey.equals(lookup.getLookupKey())){
				selectedLookup = lookup;
				break;
			}
		}
	}
	
	public LookupMetadata getSelectedLookup() {
		return selectedLookup;
	}

	public Metadata getSelectedMeta() {
		return selectedMeta;
	}*/
	}
}
