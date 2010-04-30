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
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.NavigationHandler;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class KSWrapper extends Composite{
   
    private final String LUM_APP_URL		= "lum.application.url";
    private final String APP_URL			= "application.url";
    private final String DOC_SEARCH_URL		= "ks.rice.docSearch.serviceAddress";
    private final String RICE_URL           = "ks.rice.url";
    private final String RICE_LINK_LABEL	= "ks.rice.label";
    private final String APP_VERSION		= "ks.application.version";

    private ServerPropertiesRpcServiceAsync serverPropertiesRpcService = GWT.create(ServerPropertiesRpcService.class);
        
    private VerticalPanel layout = new VerticalPanel();
	private VerticalPanel leftHeader = new VerticalPanel();
	private VerticalPanel rightHeader = new VerticalPanel();
	private HorizontalPanel header = new HorizontalPanel();
	private HorizontalPanel headerContent = new HorizontalPanel();
	private HorizontalPanel footer = new HorizontalPanel();
	private HorizontalPanel headerTopLinks = new HorizontalPanel();
	private HorizontalPanel headerBottomLinks = new HorizontalPanel();
	private StylishDropDown navDropDown = new StylishDropDown("Home");
	private StylishDropDown userDropDown = new StylishDropDown(Application.getApplicationContext().getUserId());
	
	//TODO replace with raw link widget list
	private List<KSLabel> headerLinkList = new ArrayList<KSLabel>();
	private List<KSLabel> footerLinkList = new ArrayList<KSLabel>();
	
	//TODO replace with raw link widget(?)
	private KSLabel helpLabel = new KSLabel("Help");
	private KSImage helpImage = Theme.INSTANCE.getCommonImages().getHelpIcon();
	//TODO
	private Widget headerCustomWidget = Theme.INSTANCE.getCommonWidgets().getHeaderWidget();
	private SimplePanel content = new SimplePanel();
	
	private KSLightBox docSearchDialog = new KSLightBox();
	private Frame docSearch;

    private String docSearchUrl = "";
    private String appUrl = "..";
    private String lumAppUrl = "..";
    private String riceURL ="..";
    private String riceLinkLabel="Rice";
    private String appVersion = "";
    
    private boolean loaded = false;
            
    public class WrapperNavigationHandler extends NavigationHandler{

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
			List<String> serverPropertyList = Arrays.asList(APP_URL, DOC_SEARCH_URL, LUM_APP_URL,RICE_URL,RICE_LINK_LABEL, APP_VERSION);
			
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
	                }
	                init();
	            }
	            
	        });

			loaded = false;
		}
	}


	private void init(){
		headerBottomLinks.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		createUserDropDown();
		headerBottomLinks.add(userDropDown);//Todo, put in current user
		//headerBottomLinks.add(buildUserIdPanel());
		
		createHelpInfo();
		headerBottomLinks.add(helpLabel);
		headerBottomLinks.add(helpImage);
		leftHeader.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		if(headerCustomWidget != null){
			leftHeader.add(headerCustomWidget);
			headerCustomWidget.addStyleName("KS-Wrapper-Header-Custom-Widget-Panel");
		}
		leftHeader.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		createNavDropDown();
		leftHeader.add(navDropDown);//TODO Put back in with operations
		rightHeader.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		List<KSLabel> topLinks = new ArrayList<KSLabel>();
		topLinks.add(buildLink(riceLinkLabel,riceLinkLabel,riceURL+"/portal.do"));
		setHeaderCustomLinks(topLinks);
		rightHeader.add(headerTopLinks);
		rightHeader.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		rightHeader.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		rightHeader.add(headerBottomLinks);
		headerContent.add(leftHeader);
		
		headerContent.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerContent.add(rightHeader);
		header.add(headerContent);
		//layout.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		layout.add(header);
		layout.add(content);
		layout.add(footer);

		navDropDown.addStyleName("KS-Navigation-DropDown");
		userDropDown.addStyleName("KS-Username-DropDown");
		header.addStyleName("KS-Wrapper-Header");
		headerContent.addStyleName("KS-Wrapper-Header-Content");
		footer.addStyleName("KS-Wrapper-Footer");
		content.addStyleName("KS-Wrapper-Content");
		layout.addStyleName("KS-Wrapper");
		helpLabel.addStyleName("KS-Wrapper-Help-Label");
		rightHeader.addStyleName("KS-Wrapper-Header-Right-Panel");
		leftHeader.addStyleName("KS-Wrapper-Header-Left-Panel");
		footer.add(Theme.INSTANCE.getCommonImages().getFooterImage());		
	}
	
	private void createHelpInfo(){
	    helpImage.addClickHandler(new ClickHandler(){
	        
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
    	//TODO preferences real link here
    	items.add(new KSMenuItemData("Settings",
    			new ClickHandler(){
					public void onClick(ClickEvent event) {
						Window.alert("Settings not yet implemented");
					}
    			})
    	);
    	items.add(new KSMenuItemData("Logout",
    			new WrapperNavigationHandler(
    					"j_spring_security_logout"))
    	);
    	userDropDown.setArrowImage(Theme.INSTANCE.getCommonImages().getDropDownIconWhite());
    	userDropDown.setItems(items);
	}

	private void createNavDropDown() {
		navDropDown.setImageLocation(MenuImageLocation.LEFT);
		
		List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
	
		items.add(new KSMenuItemData("Home",Theme.INSTANCE.getCommonImages().getSpacerIcon(),
    			new WrapperNavigationHandler(
    					lumAppUrl+"/index.html"))
    	);
    	items.add(new KSMenuItemData("My Action List",Theme.INSTANCE.getCommonImages().getApplicationIcon(),
    			new WrapperNavigationHandler(
    					lumAppUrl+"/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp"))
    	);
		items.add(new KSMenuItemData("Curriculum Management",Theme.INSTANCE.getCommonImages().getBookIcon(),
    			new WrapperNavigationHandler(
    					lumAppUrl+"/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=curriculum"))
    	);
    	items.add(new KSMenuItemData("Organization Management", Theme.INSTANCE.getCommonImages().getPeopleIcon(),
    			new WrapperNavigationHandler(
    					lumAppUrl+"/org.kuali.student.core.organization.ui.OrgEntry/OrgEntry.jsp"))
    	);
    	items.add(new KSMenuItemData("Workflow Doc Search", Theme.INSTANCE.getCommonImages().getNodeIcon(),
    			new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						buildDocSearchPanel();
						docSearchDialog.show();						
					}})
    	);
    	items.add(new KSMenuItemData("Rice", Theme.INSTANCE.getCommonImages().getSpacerIcon(),
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
		headerLinkList = links;
		for(KSLabel link: links){
			FocusPanel panel = new FocusPanel();
			panel.setWidget(link);
			headerTopLinks.add(panel);
			panel.addStyleName("KS-Wrapper-Header-Custom-Link-Panel");
			link.addStyleName("KS-Wrapper-Header-Custom-Link");
		}
	}
	
	public void setFooterLinks(List<KSLabel> links){
		footerLinkList = links;
		for(KSLabel link: links){
			footer.add(link);
			link.addStyleName("KS-Wrapper-Footer-Link");
		}
	}
	
	
    private KSLabel buildLink(final String text, final String title, final String actionUrl) {
        //TODO need to add the action for the link        

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
                //TODO need to set actionUrl
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
	        
	        KSButton closeActionButton = new KSButton("Close");
	        closeActionButton.addClickHandler(new ClickHandler(){
	            public void onClick(ClickEvent event) {
	                docSearchDialog.hide();
	            }
	        });
	        
	        docSearchPanel.add(closeActionButton);	       
	        docSearchDialog.setWidget(docSearchPanel);
    	}
    }
    
	
}
