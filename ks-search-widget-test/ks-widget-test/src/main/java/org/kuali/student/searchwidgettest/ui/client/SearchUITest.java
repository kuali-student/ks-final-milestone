package org.kuali.student.searchwidgettest.ui.client;

import java.util.List;

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
import org.kuali.student.core.atp.ui.client.service.AtpRpcService;
import org.kuali.student.core.atp.ui.client.service.AtpRpcServiceAsync;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.organization.ui.client.view.OrgLocateTree;
import org.kuali.student.core.organization.ui.client.view.OrgSearchTypeWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SearchUITest implements EntryPoint {
	private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
	private AtpRpcServiceAsync atpRpcServiceAsync = GWT.create(AtpRpcService.class);
	private VerticalPanel testPanel = new VerticalPanel();

	@Override
	public void onModuleLoad() {
		KSFormLayoutPanel orgRelForm = null;
		KSFormLayoutPanel atp = null;
		
		orgRelForm = setupOrgTestPanel();
		atp = setupATPTestPanel();
        SimplePanel verticalSpacer;

        // org test
        KSLabel orgSearchWidghtTitle = new KSLabel("Organization Search Widget");
        orgSearchWidghtTitle.getElement().getStyle().setProperty("fontWeight", "bold");
        orgSearchWidghtTitle.getElement().getStyle().setProperty("fontSize", "14pt");
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        testPanel.add(verticalSpacer);
        testPanel.add(orgSearchWidghtTitle);
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("5px");
        testPanel.add(verticalSpacer);
        testPanel.add(orgRelForm);

        // atp test
        KSLabel atpSearchWidghtTitle = new KSLabel("ATP Search Widget");
        orgSearchWidghtTitle.getElement().getStyle().setProperty("fontWeight", "bold");
        orgSearchWidghtTitle.getElement().getStyle().setProperty("fontSize", "14pt");
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        testPanel.add(verticalSpacer);
//        testPanel.add(atpSearchWidghtTitle);
//        verticalSpacer = new SimplePanel();
//        verticalSpacer.setHeight("5px");
//        testPanel.add(verticalSpacer);
        testPanel.add(atp);

		RootPanel.get().add(testPanel);
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
	
    private KSFormLayoutPanel setupOrgTestPanel() {
		final KSFormLayoutPanel orgRelForm = new KSFormLayoutPanel();
		TextableVerticalPanel searchField = new TextableVerticalPanel();
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
        
        searchWindow.addSelectionHandler(new SelectionHandler<List<String>>() {
			@Override
			public void onSelection(SelectionEvent<List<String>> event) {
				String orgId = event.getSelectedItem().get(0);
                orgRpcServiceAsync.getOrganization(orgId, new AsyncCallback<OrgInfo>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(OrgInfo orgInfo) {
                        orgRelForm.setFieldValue("relOrgName", orgInfo.getLongName());
                        orgRelForm.setFieldValue("relOrgId", orgInfo.getId());
                        searchWindow.hide();
                    }            
                });   
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
        
        searchField.addTextWidget(sb);
        searchField.add(advancedSearches);
        
        addFormField(searchField, "Organization", "relOrgName", orgRelForm);
        KSTextBox relOrgId = new KSTextBox();
        relOrgId.setEnabled(true);
        addFormField(relOrgId, "Organization Id", "relOrgId", orgRelForm);

        return orgRelForm;
    }
    
    private KSFormLayoutPanel setupATPTestPanel() {
    	final KSFormLayoutPanel atp = new KSFormLayoutPanel();
		TextableVerticalPanel atpField = new TextableVerticalPanel();
		final SearchSuggestOracle oracle = new SearchSuggestOracle((BaseRpcServiceAsync)atpRpcServiceAsync,  "atp.search.atpByShortName", "atp.queryParam.atpShortName",
                "atp.queryParam.atpId", "atp.resultColumn.atpId", "atp.resultColumn.atpShortName");		//
		KSSuggestBox sb = new KSSuggestBox(oracle);
        oracle.setTextWidget(sb.getTextBox()); 
        HorizontalPanel advancedSearches = new HorizontalPanel();
        final KSLabel searchLink = new KSLabel("search");
        searchLink.addStyleName("action");
        final KSLabel browseLink = new KSLabel("choose by date");
        browseLink.addStyleName("action");
        advancedSearches.add(searchLink);
        final KSLabel orText = new KSLabel(" | ");
        orText.addStyleName("non-action");
        advancedSearches.add(orText);
        advancedSearches.add(browseLink);

//        atp.search.basicAtpByShortName
//        atp.search.atpByShortName
        final OrgSearchTypeWidget searchWindow = 
        	new OrgSearchTypeWidget(
        			atpRpcServiceAsync, 
        			"atp.search.basicAtpByShortName", 
        			"atp.resultColumn.atpId",
        			atpRpcServiceAsync,
        			"atp.search.basicAtpByShortName", 
        			"atp.resultColumn.atpId",
        	"Find Session");
        
        searchLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                searchWindow.show();
            }
        });
        
//        searchWindow.addSelectionHandler(new SelectionHandler<List<String>>() {
//			@Override
//			public void onSelection(SelectionEvent<List<String>> event) {
//				String orgId = event.getSelectedItem().get(0);
//                atpRpcServiceAsync.get(orgId, new AsyncCallback<OrgInfo>(){
//                    public void onFailure(Throwable caught) {
//                        Window.alert(caught.getMessage());
//                    }
//
//                    public void onSuccess(OrgInfo orgInfo) {
//                        atp.setFieldValue("relOrgName", orgInfo.getLongName());
//                        atp.setFieldValue("relOrgId", orgInfo.getId());
//                        searchWindow.hide();
//                    }            
//                });   
//			}
//        });

        atpField.addTextWidget(sb);
        atpField.add(advancedSearches);
        addFormField(atpField, "Start Session", "relOrgName", atp);

        return atp;
    }
	
}

class TextableVerticalPanel extends VerticalPanel implements HasText {
	
	private HasText textWidget;
	
	public void addTextWidget(Widget textWidget) {
		this.textWidget = (HasText)textWidget;
		add(textWidget);
	}

	@Override
	public String getText() {
		return textWidget.getText();
	}

	@Override
	public void setText(String text) {
		textWidget.setText(text);
	}
	
}
