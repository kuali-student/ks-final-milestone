package org.kuali.student.common.ui.client.widgets.containers;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSWrapper extends Composite{
    ServerPropertiesRpcServiceAsync serverProperties = GWT.create(ServerPropertiesRpcService.class);
    
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
	
	private KSLightBox actionListDialog = new KSLightBox();
	private Frame actionList = new Frame();
	private KSLightBox docSearchDialog = new KSLightBox();
	private Frame docSearch = new Frame();
    private  String actionListUrl;
    private  String docSearchUrl;
    
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
		
		getDocSearchAndActionListUrls();
		
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
		rightHeader.add(headerTopLinks);
		rightHeader.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		rightHeader.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		rightHeader.add(headerBottomLinks);
		headerContent.add(leftHeader);

		//Put these in since dropdowns were blank, can remove later
		headerContent.add(buildActionListPanel());
		headerContent.add(buildDocSearchPanel());
		//headerContent.add(buildLink("Preferences", "Create, modify or delete user preferences", "Preferences not yet implemented"));
		//headerContent.add(buildLink("Home", "Return to home page", GWT.getModuleBaseURL() + "../"));
		//headerContent.add(buildLink("Logout", "End current Kuali Student session", GWT.getModuleBaseURL()+"../j_spring_security_logout"));
		
		headerContent.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerContent.add(rightHeader);
		header.add(headerContent);
		//layout.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		layout.add(header);
		layout.add(content);
		layout.add(footer);
		this.initWidget(layout);
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
		footer.add(Theme.INSTANCE.getCommonImages().getSpacer());
	}
	
	private void createHelpInfo(){
	    helpImage.addClickHandler(new ClickHandler(){
	        
	           public void onClick(ClickEvent event) {
	               final PopupPanel helpPopup = new PopupPanel(true);
	               helpPopup.setWidget(new HTML("<br><h3>&nbsp;&nbsp; Kauli Student Version : 1.0.0-m4 &nbsp;&nbsp;<h3>"));
	               
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
    	items.add(new KSMenuItemData("Preferences",
    			new WrapperNavigationHandler(
    					"/Preferences%20not%20yet%20implemented"))
    	);
    	items.add(new KSMenuItemData("Logout",
    			new WrapperNavigationHandler(
    					"j_spring_security_logout"))
    	);
    	userDropDown.setArrowImage(Theme.INSTANCE.getCommonImages().getDropDownIconWhite());
    	userDropDown.setItems(items);
	}

	private void createNavDropDown() {
		navDropDown.setShowSelectedItem(true);
    	final List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
    	serverProperties.get("lum.application.url", new AsyncCallback<String>(){
			public void onFailure(Throwable caught) {
		    	items.add(new KSMenuItemData("Home",
		    			new WrapperNavigationHandler(
		    					"../index.html"))
		    	);
		    	items.add(new KSMenuItemData("Curriculum Management",
		    			new WrapperNavigationHandler(
		    					"../org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp"))
		    	);
		    	items.add(new KSMenuItemData("Organizations",
		    			new WrapperNavigationHandler(
		    					"../org.kuali.student.core.organization.ui.OrgEntry/OrgEntry.jsp"))
		    	);
		    	serverProperties.get("application.url", new AsyncCallback<String>(){
					public void onFailure(Throwable caught) {
				    	items.add(new KSMenuItemData("Rice",
				    			new WrapperNavigationHandler(
				    					"../portal.do?selectedTab=main"))
				    	);
				    	navDropDown.setItems(items);

					}
					public void onSuccess(String result) {
				    	items.add(new KSMenuItemData("Rice",
				    			new WrapperNavigationHandler(
				    					result+"/portal.do?selectedTab=main"))
				    	);
				    	navDropDown.setItems(items);
					}
		    	});
			}
			public void onSuccess(String result) {
		    	items.add(new KSMenuItemData("Home",
		    			new WrapperNavigationHandler(
		    					result+"/index.html"))
		    	);
		    	items.add(new KSMenuItemData("Curriculum Management",
		    			new WrapperNavigationHandler(
		    					result+"/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp"))
		    	);
		    	items.add(new KSMenuItemData("Organizations",
		    			new WrapperNavigationHandler(
		    					result+"/org.kuali.student.core.organization.ui.OrgEntry/OrgEntry.jsp"))
		    	);
		    	serverProperties.get("application.url", new AsyncCallback<String>(){
					public void onFailure(Throwable caught) {
				    	items.add(new KSMenuItemData("Rice",
				    			new WrapperNavigationHandler(
				    					"/portal.do?selectedTab=main"))
				    	);
				    	navDropDown.setItems(items);

					}
					public void onSuccess(String result) {
				    	items.add(new KSMenuItemData("Rice",
				    			new WrapperNavigationHandler(
				    					result+"/portal.do?selectedTab=main"))
				    	);
				    	navDropDown.setItems(items);
					}
		    	});
			}
    	});


/*    	items.add(new KSMenuItemData("Action List",
    			new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						actionList.setUrl(actionListUrl);
						actionListDialog.show();
						
					}})
    	);
        items.add(new KSMenuItemData("Doc Search",
    			new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						docSearch.setUrl(docSearchUrl);
						docSearchDialog.show();
						
					}})
    	);*/

        
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
    
    private Widget buildUserIdPanel(){
/*        String userId = 
        KSLabel userLabel = new KSLabel("User: "+userId);
        
        userLabel.addStyleName("KS-Header-Link");        
        */
        return null;
    }
    
    private Widget buildActionListPanel(){
        
        

        actionList.setSize("700px", "500px");
        
        VerticalPanel actionListPanel = new VerticalPanel();
        
        actionListPanel.add(actionList);
        
        KSButton closeActionButton = new KSButton("Close");
        closeActionButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                actionListDialog.hide();
            }
        });
        
        actionListPanel.add(closeActionButton);
        
        actionListDialog.setWidget(actionListPanel);
        
        //Create the button that opens the search dialog
        Hyperlink actionListLink = new Hyperlink("Action List","Actionlist");
        actionListLink.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                actionList.setUrl(actionListUrl);
                actionListDialog.show();
            }
        });
        actionListLink.setStyleName("KS-Header-Hyperlink");
        
        return actionListLink;
        
    }
    
    //Method to build the light box for the doc search
    private Widget buildDocSearchPanel(){
        
        

        docSearch.setSize("700px", "500px");
        
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
        
        //Create the button that opens the search dialog
        Hyperlink docSearchLink = new Hyperlink("Doc Search ","DocSearch");
        docSearchLink.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                docSearch.setUrl(docSearchUrl);
                docSearchDialog.show();
            }
        });
        docSearchLink.setStyleName("KS-Header-Hyperlink");
        
        return docSearchLink;
        
    }
	
	private void getDocSearchAndActionListUrls() {
        // getting the rice action list url from server properties
        serverProperties.get("ks.rice.actionList.serviceAddress", new AsyncCallback<String>() {
            public void onFailure(Throwable caught) { //ignoring, we'll use the default
            }
            public void onSuccess(String result) {
                GWT.log("ServerProperties fetched for ks.rice.personLookup.serviceAddress: "+result, null);
                if(result != null)
                    actionListUrl = result;
            }
        });
        
        // getting the rice doc search url from server properties
        serverProperties.get("ks.rice.docSearch.serviceAddress", new AsyncCallback<String>() {
            public void onFailure(Throwable caught) { //ignoring, we'll use the default
            }
            public void onSuccess(String result) {
                GWT.log("ServerProperties fetched for ks.rice.docSearch.serviceAddress: "+result, null);
                if(result != null)
                    docSearchUrl = result;
            }
            
        });
	}
}
