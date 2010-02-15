package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBoxWAdvSearch;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KSSearchLayout extends Composite{
	private KSLabel searchTitle = new KSLabel("Search");
	private KSDropDown searchCategory = new KSDropDown();
	private VerticalPanel layout = new VerticalPanel();
	private HorizontalPanel searchLayout = new HorizontalPanel();
	private KSLabel advSearchLink = new KSLabel("Advanced Search");
	
	public KSSearchLayout(KSSearchBox searchWidget, KSAdvancedSearchWindow searchWindow){
		layout.add(searchTitle);
		searchLayout.add(searchCategory);
		searchLayout.add(searchWidget);
		layout.add(searchLayout);
		layout.add(advSearchLink);
	}
	
	public void setSearch(String search){
		
	}
}
