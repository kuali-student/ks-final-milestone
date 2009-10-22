package org.kuali.student.searchwidgettest.ui.client;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.organization.ui.client.view.OrgLocateTree;
import org.kuali.student.core.organization.ui.client.view.OrgSearchTypeWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SearchUITest implements EntryPoint {
	private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
	private VerticalPanel testPanel = new VerticalPanel();

	@Override
	public void onModuleLoad() {
		final KSFormLayoutPanel orgRelForm = new KSFormLayoutPanel();
		final KSDropDown orgRelTypeDropDown = new KSDropDown();
		VerticalPanel searchField = new VerticalPanel();
		final SearchSuggestOracle oracle = new SearchSuggestOracle((BaseRpcServiceAsync)orgRpcServiceAsync,  "org.search.orgByShortName", "org.queryParam.orgShortName",
                "org.queryParam.orgId", "org.resultColumn.orgId", "org.resultColumn.orgShortName");		//
		KSSuggestBox sb = new KSSuggestBox(oracle);
        oracle.setTextWidget(sb.getTextBox()); 
		
        HorizontalPanel advancedSearches = new HorizontalPanel();
        final KSLabel searchLink = new KSLabel("Search");
        searchLink.addStyleName("action");
        final KSLabel browseLink = new KSLabel("Browse");
        browseLink.addStyleName("action");
        advancedSearches.add(searchLink);
        final KSLabel orText = new KSLabel("or");
        orText.addStyleName("non-action");
        advancedSearches.add(orText);
        advancedSearches.add(browseLink);
        
        final OrgSearchTypeWidget searchWindow = 
            new OrgSearchTypeWidget(
                  orgRpcServiceAsync, 
                    "org.search.orgQuickLongViewByFirstLetter", 
                    "org.resultColumn.orgId",
                  orgRpcServiceAsync,
                    "org.search.orgQuickViewByHierarchyShortName", 
                    "org.resultColumn.orgId",
                  "Find Organization");
        
        searchLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                searchWindow.show();
            }
        });
        
        final KSLightBox testLightBox = new KSLightBox();
        final VerticalPanel testLightBoxPanel = new VerticalPanel();
        KSButton close = new KSButton("Close", new
                ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        testLightBox.hide();
                    }
        });
        browseLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                testLightBox.show();
            }
        });
        
        testLightBoxPanel.add(new OrgLocateTree());
        testLightBoxPanel.add(close);
        testLightBox.setWidget(testLightBoxPanel);
        
        searchField.add(sb);
        searchField.add(advancedSearches);
        
        addFormField(searchField, "Organization", "relOrgName", orgRelForm);
        KSTextBox relOrgId = new KSTextBox();
        relOrgId.setEnabled(true);
        addFormField(relOrgId, "Organization Id", "relOrgId", orgRelForm);
        addFormField(orgRelTypeDropDown, "Relationship", "relType", orgRelForm);
        addFormField(new KSDatePicker(), "Effective date", "relEffDate", orgRelForm);
        addFormField(new KSDatePicker(), "Expiration date", "relExpDate", orgRelForm);
        addFormField(new KSTextArea(), "Note", "relNote", orgRelForm);
        
        testPanel.add(orgRelForm);

        //        KSAdvancedSearchWindow searchWindow = new KSAdvancedSearchWindow(orgRpcServiceAsync, "org.search.orgQuickViewByHierarchyShortName",
//				"org.resultColumn.orgId", "Find Organization");
//		searchWindow.show();
		
		RootPanel.get().add(testPanel);

		//RootPanel.get().add(xxx);
	}

    protected static void addFormField(Widget w, String label, String name, KSFormLayoutPanel formPanel) {
        KSFormField ff = new KSFormField();
        ff.setLabelText(label);
        ff.setWidget(w);
        if (w instanceof HasName)
            ((HasName) w).setName(name);
        ff.setHelpInfo(new HelpInfo());
        ff.setName(name);

        formPanel.addFormField(ff);
    }
	
	
}
