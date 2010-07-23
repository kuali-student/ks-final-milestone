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

package org.kuali.student.common.ui.client.widgets.containers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcService;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcServiceAsync;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.NavigationHandler;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
import org.kuali.student.common.ui.client.widgets.headers.KSHeader;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.KSMenu.MenuImageLocation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class KSWrapper extends Composite{

    private static final String LUM_APP_URL		= "lum.application.url";
    private static final String APP_URL			= "application.url";
    private static final String DOC_SEARCH_URL		= "ks.rice.docSearch.serviceAddress";
    private static final String RICE_URL           = "ks.rice.url";
    private static final String RICE_LINK_LABEL	= "ks.rice.label";
    private static final String APP_VERSION		= "ks.application.version";
    
    //This property is used to append code server param to urls so links displayed in get dev mode works as intended
    private static final String CODE_SERVER		= "ks.gwt.codeServer"; 

    private ServerPropertiesRpcServiceAsync serverPropertiesRpcService = GWT.create(ServerPropertiesRpcService.class);

    private FlowPanel layout = new FlowPanel();

	private KSHeader ksHeader = new KSHeader();

	private StylishDropDown navDropDown = new StylishDropDown(getMessage("wrapperPanelTitleHome"));
	private Anchor versionAnchor = new Anchor(" ( Version ) ");
	//private Widget headerCustomWidget = Theme.INSTANCE.getCommonWidgets().getHeaderWidget();

	private SimplePanel content = new SimplePanel();
	private KSLightBox docSearchDialog = new KSLightBox();

	private Frame docSearch;
    private String docSearchUrl = "";
    private String appUrl = "..";
    private String lumAppUrl = "..";
    private String riceURL ="..";
    private String riceLinkLabel="Rice";
    private String appVersion = "";
    private String codeServer = "";

    private boolean loaded = false;

    private static class WrapperNavigationHandler extends NavigationHandler{
		public WrapperNavigationHandler(String url) {
			super(url);
		}

		@Override
		public void beforeNavigate(Callback<Boolean> callback) {
			//FIXME notify current controller of the page change so it can perform an action
			//FIXME before navigation event
			callback.exec(true);
		}
    }
	public KSWrapper(){
		this.initWidget(layout);
	}
	protected void onLoad() {
		super.onLoad();
		if (!loaded){
			List<String> serverPropertyList = Arrays.asList(APP_URL, DOC_SEARCH_URL, LUM_APP_URL,RICE_URL,RICE_LINK_LABEL, APP_VERSION, CODE_SERVER);

	        serverPropertiesRpcService.get(serverPropertyList, new AsyncCallback<Map<String,String>>() {
	            public void onFailure(Throwable caught) {
	            	//ignoring, we'll use the default
	            	init();
	            }

	            public void onSuccess(Map<String,String> result) {
	                GWT.log("ServerProperties fetched: "+result.toString(), null);
	                if(result != null){
	                    appUrl 			= result.get(APP_URL);
	                    docSearchUrl	= result.get(DOC_SEARCH_URL);
	                    lumAppUrl 		= result.get(LUM_APP_URL);
	                    riceURL         = result.get(RICE_URL);
	                    riceLinkLabel 	= result.get(RICE_LINK_LABEL);
	                    appVersion		= result.get(APP_VERSION);
	                    
	                    if (result.get(CODE_SERVER) != null){
	                    	codeServer		= result.get(CODE_SERVER);
	                    }
	                }
	                init();
	            }

	        });

			loaded = false;
		}
	}
	private void init(){
		//headerBottomLinks.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		createUserDropDown();
		//headerBottomLinks.add(userDropDown);
		ksHeader.setHiLabelText("Hi,");
		ksHeader.setUserName(Application.getApplicationContext().getUserId());
		Anchor logoutLink = new Anchor(getMessage("wrapperPanelLogout"));
		logoutLink.addClickHandler(new WrapperNavigationHandler("j_spring_security_logout"));
		ksHeader.addLogout(logoutLink);
		ksHeader.addLogout(versionAnchor);

		//headerBottomLinks.add(logoutLink);
		createHelpInfo();
		createNavDropDown();
		ksHeader.addNavigation(navDropDown);
		
		
		List<KSLabel> topLinks = new ArrayList<KSLabel>();
		topLinks.add(buildLink(riceLinkLabel,riceLinkLabel,riceURL+"/portal.do"));
		setHeaderCustomLinks(topLinks);

		layout.add(ksHeader);
		layout.add(content);

		navDropDown.addStyleName("KS-Navigation-DropDown");
		content.addStyleName("KS-Wrapper-Content");
		layout.addStyleName("KS-Wrapper");
	}

	private void createHelpInfo(){
	    versionAnchor.addClickHandler(new ClickHandler(){

	           public void onClick(ClickEvent event) {
	               final PopupPanel helpPopup = new PopupPanel(true);
	               helpPopup.setWidget(new HTML("<br><h3>&nbsp;&nbsp; " + appVersion + "&nbsp;&nbsp;<h3>"));

	               helpPopup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
	                   public void setPosition(int offsetWidth, int offsetHeight) {
	                     int left = (Window.getClientWidth() - offsetWidth);
	                     int top = 0;
	                     helpPopup.setPopupPosition(left, top);
	                   }
	               });
	           }
	    });
	}

	private void createUserDropDown() {
		List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
    	items.add(new KSMenuItemData(getMessage("wrapperPanelLogout"),new WrapperNavigationHandler("j_spring_security_logout")));
	}

	private void createNavDropDown() {
		navDropDown.setImageLocation(MenuImageLocation.LEFT);

		List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();

		items.add(new KSMenuItemData(getMessage("wrapperPanelTitleHome"),Theme.INSTANCE.getCommonImages().getSpacerIcon(),
    			new WrapperNavigationHandler(
    					"#"))
    	);
    	items.add(new KSMenuItemData(getMessage("wrapperPanelTitleMyActionList"),Theme.INSTANCE.getCommonImages().getApplicationIcon(),
    			new WrapperNavigationHandler(
    					lumAppUrl+"/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp"+(codeServer.isEmpty()?"":"?"+codeServer)))
    	);
		items.add(new KSMenuItemData(getMessage("wrapperPanelTitleCurriculumManagement"),Theme.INSTANCE.getCommonImages().getBookIcon(),
    			new WrapperNavigationHandler(
    					lumAppUrl+"/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=curriculum"+(codeServer.isEmpty()?"":"&"+codeServer)))
    	);
    	items.add(new KSMenuItemData(getMessage("wrapperPanelTitleOrg"), Theme.INSTANCE.getCommonImages().getPeopleIcon(),
    			new WrapperNavigationHandler(
    					lumAppUrl+"/org.kuali.student.core.organization.ui.OrgEntry/OrgEntry.jsp"))
    	);
    	items.add(new KSMenuItemData(getMessage("wrapperPanelTitleWorkflowDocSearch"), Theme.INSTANCE.getCommonImages().getNodeIcon(),
    			new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						buildDocSearchPanel();
						docSearchDialog.show();
					}})
    	);
    	items.add(new KSMenuItemData(getMessage("wrapperPanelTitleRice"), Theme.INSTANCE.getCommonImages().getSpacerIcon(),
    			new WrapperNavigationHandler(
    					appUrl+"/portal.do?selectedTab=main"))
    	);

		navDropDown.setShowSelectedItem(true);
    	navDropDown.setItems(items);
    	navDropDown.setArrowImage(Theme.INSTANCE.getCommonImages().getDropDownIconWhite());

	}

	public void setContent(Widget wrappedContent){
		content.setWidget(wrappedContent);
	}

	public void setHeaderCustomLinks(List<KSLabel> links){
		for(KSLabel link: links){
			FocusPanel panel = new FocusPanel();
			panel.setWidget(link);
			//headerTopLinks.add(panel);
			panel.addStyleName("KS-Wrapper-Header-Custom-Link-Panel");
			link.addStyleName("KS-Wrapper-Header-Custom-Link");
		}
	}

	public void setFooterLinks(List<KSLabel> links){
		for(KSLabel link: links){
			//footer.add(link);
			link.addStyleName("KS-Wrapper-Footer-Link");
		}
	}


    private KSLabel buildLink(final String text, final String title, final String actionUrl) {

        //Using KSLabel for now - couldn't change color for Anchor
        final KSLabel link = new KSLabel(text);
        link.addStyleName("KS-Header-Link");
        link.setTitle(title);
        link.addMouseOverHandler(new MouseOverHandler() {

            @Override
            public void onMouseOver(MouseOverEvent event) {
                link.addStyleName("KS-Header-Link-Focus");
            }});

        link.addMouseOutHandler(new MouseOutHandler() {

            @Override
            public void onMouseOut(MouseOutEvent event) {
                link.removeStyleName("KS-Header-Link-Focus");

            }});
        link.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.Location.assign(actionUrl);
            }});

        return link;

    }

    //Method to build the light box for the doc search
    private void buildDocSearchPanel(){
    	if (docSearch == null){
	        docSearch = new Frame();
	    	docSearch.setSize("700px", "500px");
	        docSearch.setUrl(docSearchUrl);

	        VerticalPanel docSearchPanel = new VerticalPanel();
	        docSearchPanel.add(docSearch);

	        KSButton closeActionButton = new KSButton(getMessage("wrapperPanelClose"));
	        closeActionButton.addClickHandler(new ClickHandler(){
	            public void onClick(ClickEvent event) {
	                docSearchDialog.hide();
	            }
	        });

	        docSearchPanel.add(closeActionButton);
	        docSearchDialog.setWidget(docSearchPanel);
    	}
    }

    private static String getMessage(final String messageId) {
        return Application.getApplicationContext().getMessage(messageId);
    }
}
