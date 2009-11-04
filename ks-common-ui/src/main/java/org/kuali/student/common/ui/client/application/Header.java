/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.application;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.service.ServerPropertiesRpcService;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcServiceAsync;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.breadcrumb.KSBreadcrumb;
import org.kuali.student.common.ui.client.widgets.breadcrumb.KSBreadcrumbItem;

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
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
@Deprecated
public class Header extends Composite {
    private final VerticalPanel main = new VerticalPanel();

    private final SimplePanel logoContentPanel = new SimplePanel();
    private final DockPanel logoPanel = new DockPanel();

    private final HorizontalPanel linksContentPanel = new HorizontalPanel();
    private final HorizontalPanel actionlistPanel = new HorizontalPanel();
    private final HorizontalPanel docSearchPanel = new HorizontalPanel();
    private final DockPanel linksPanel = new DockPanel();

    private List<KSBreadcrumbItem> breadcrumbItems;
    private List<HeaderLinkItem> linkItems;

    private KSBreadcrumb breadcrumb;

    private final KSImage logo = new KSImage("images/kru_banner2.jpg");//Theme.INSTANCE.getCommonImages().getHeaderLogo();
    private final KSImage separator1 = new KSImage("");//Theme.INSTANCE.getCommonImages().getHeaderLineBreak();
    private final KSImage separator2 = new KSImage("");//Theme.INSTANCE.getCommonImages().getHeaderLineBreak();
//    private  String actionListUrl = "http://localhost:8081/ks-rice-dev/kew/ActionList.do";
    private  String actionListUrl;
    private  String docSearchUrl;
    ServerPropertiesRpcServiceAsync serverProperties = GWT.create(ServerPropertiesRpcService.class);
    
    private final KSLabel userId = new KSLabel();

    /**
     * 
     * This constructs a default Header with no Breadcrumb menu and just Logout and Preferences 
     * function links.
     *
     */
    public Header() {
        super();
        breadcrumbItems = new ArrayList<KSBreadcrumbItem>();
        linkItems = new ArrayList<HeaderLinkItem>();
        initHeader();
    }

    /**
     * 
     * This constructs a custom Header with breadcrumb menu and additional function links. N.B Always get Logout 
     * and Preferences functions
     * 
     * if only one of breadcrumb or links is required, set other parameter to null
     * 
     * @param breadcrumbItems
     * @param linkItems
     */
    public Header(List<KSBreadcrumbItem> breadcrumbItems,
            List<HeaderLinkItem> linkItems) {
        super();
        this.breadcrumbItems = breadcrumbItems;
        this.linkItems = linkItems;
        initHeader();
    }

    private void initHeader() {
        super.initWidget(main);

        buildLogoPanel();
        main.add(logoPanel);

        main.add(separator1);
        buildLinksPanel();
        main.add(linksPanel);    
        //main.add(separator2);

        if (breadcrumbItems != null &&
                !breadcrumbItems.isEmpty()) {
            buildBreadcrumb();
            main.add(breadcrumb);
        }

        // getting the rice action list url from server properties
        serverProperties.get("ks.rice.actionList.serviceAddress", new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) { //ignoring, we'll use the default
            }

            @Override
            public void onSuccess(String result) {
                GWT.log("ServerProperties fetched for ks.rice.personLookup.serviceAddress: "+result, null);
                if(result != null)
                    actionListUrl = result;
            }
            
        });
        
        // getting the rice doc search url from server properties
        serverProperties.get("ks.rice.docSearch.serviceAddress", new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) { //ignoring, we'll use the default
            }

            @Override
            public void onSuccess(String result) {
                GWT.log("ServerProperties fetched for ks.rice.docSearch.serviceAddress: "+result, null);
                if(result != null)
                    docSearchUrl = result;
            }
            
        });
        main.addStyleName("KS-Header");
    }

    private void buildLogoPanel() {

        logo.addStyleName("KS-Header-Logo");
        logoContentPanel.add(logo);
        logoContentPanel.addStyleName("KS-Header-Logo-Panel");
        
        //logoPanel is a spacer panel to allow for expansion of browser
        logoPanel.add(logoContentPanel ,DockPanel.WEST);
        logoPanel.addStyleName("KS-Header-Logo-Spacer");
        logoPanel.setHorizontalAlignment(DockPanel.ALIGN_LEFT);
        logoPanel.setVerticalAlignment(DockPanel.ALIGN_BOTTOM);
    }

    private void buildLinksPanel() {

        separator1.addStyleName("KS-Header-Separator");
        separator2.addStyleName("KS-Header-Separator");

        if (linkItems == null) {
            linkItems = new ArrayList<HeaderLinkItem>();
        }

        linksContentPanel.add(buildUserIdPanel());
        actionlistPanel.add(buildActionListPanel()); // Open Action List Panel from rice
        docSearchPanel.add(buildDocSearchPanel()); // Open Doc Search Panel from rice
        // Always have logout and preferences options
        linkItems.add(new HeaderLinkItem("Preferences", "Create, modify or delete user preferences", "Preferences not yet implemented"));
        linkItems.add(new HeaderLinkItem("Home", "Return to home page", GWT.getModuleBaseURL() + "../"));
        linkItems.add(new HeaderLinkItem("Logout", "End current Kuali Student session", GWT.getModuleBaseURL()+"../j_spring_security_logout"/*"Logout not yet implemented"*/));

        for (HeaderLinkItem i : linkItems) {
            linksContentPanel.add(buildLink(i.getText(), i.getTitle(), i.getActionUrl()));           
        }

        
        linksContentPanel.addStyleName("KS-Header-Link-Panel");
        actionlistPanel.addStyleName("KS-Header-ActionListLink-Panel");
        docSearchPanel.addStyleName("KS-Header-ActionListLink-Panel");
        //linksPanel is a spacer panel for right alignment of links
        
        linksPanel.add(actionlistPanel,DockPanel.WEST);
        linksPanel.add(docSearchPanel,DockPanel.WEST);
        linksPanel.add(linksContentPanel ,DockPanel.EAST);        
        linksPanel.addStyleName("KS-Header-Link-Spacer");
        linksPanel.setHorizontalAlignment(DockPanel.ALIGN_RIGHT);
        linksPanel.setVerticalAlignment(DockPanel.ALIGN_BOTTOM);
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

    private void buildBreadcrumb() {
        //TODO need to allow Linked or Unlinked breadcrumb lists

        breadcrumb = new KSBreadcrumb();
        breadcrumb.setUnLinkedBreadcrumbList(breadcrumbItems);
        breadcrumb.addStyleName("KS-Header-Breadcrumb");

    }
    
    private Widget buildUserIdPanel(){
        HorizontalPanel userIdPanel = new HorizontalPanel();
        KSLabel userLabel = new KSLabel("User:");
        
        userLabel.addStyleName("KS-Header-Link");        
        userId.addStyleName("KS-Header-Link");
        
        userId.setText(Application.getApplicationContext().getUserId());
        
        userIdPanel.add(userLabel);        
        userIdPanel.add(userId);
        
        return userIdPanel;
    }
    
    //Method to build the light box for the action list
    private Widget buildActionListPanel(){
        final KSLightBox actionListDialog = new KSLightBox();
        final Frame actionList = new Frame(actionListUrl);

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
        final KSLightBox docSearchDialog = new KSLightBox();
        final Frame docSearch = new Frame(docSearchUrl);

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

}
