package org.kuali.student.common.ui.client.application;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.breadcrumb.KSBreadcrumb;
import org.kuali.student.common.ui.client.widgets.breadcrumb.KSBreadcrumbItem;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Header extends Composite {
    private final VerticalPanel main = new VerticalPanel();

    private final HorizontalPanel linksContentPanel = new HorizontalPanel();
    private final DockPanel linksPanel = new DockPanel();
    private final SimplePanel logoPanel = new SimplePanel();

    //Using KSLabel for now - couldn't change color for Anchor
    private final KSLabel logout = new KSLabel("Logout");
    private final KSLabel preferences = new KSLabel("Preferences");

    private KSBreadcrumb breadcrumb;


    private final KSImage logo = new KSImage("images/Kuali_logo_bar.jpg");
    private final KSImage separator1 = new KSImage("images/red_gradient_1.jpg");
    private final KSImage separator2 = new KSImage("images/red_gradient_2.jpg");
//  private final KSImage logo = new KSImage("images/logo-ks.gif");

    public Header() {
        super.initWidget(main);

        buildInitialBreadcrumb();

        buildLinksPanel();
        buildLogoPanel();

        separator1.addStyleName("KS-Header-Separator");
        separator2.addStyleName("KS-Header-Separator");
        main.add(logoPanel);
        main.add(separator1);
        main.add(linksPanel);    
        main.add(separator2);
        main.add(breadcrumb);
        main.addStyleName("KS-Header");
    }

    private void buildLogoPanel() {

        logo.addStyleName("KS-Header-Logo");
        logoPanel.add(logo);
        logoPanel.addStyleName("KS-Header-Logo-Panel");
    }

    private void buildLinksPanel() {
        buildLogoutLink();
        buildPreferencesLink();

        linksContentPanel.add(preferences);
        linksContentPanel.add(logout);
        linksContentPanel.addStyleName("KS-Header-Link-Panel");

        linksPanel.add(linksContentPanel ,DockPanel.EAST);
        linksPanel.addStyleName("KS-Header-Link");
        linksPanel.setHorizontalAlignment(DockPanel.ALIGN_RIGHT);
        linksPanel.setVerticalAlignment(DockPanel.ALIGN_BOTTOM);
    }

    private void buildPreferencesLink() {
        preferences.addStyleName("KS-Header-Link");
        preferences.setTitle("Create, modify or delete user preferences");
        preferences.addMouseOverHandler(new MouseOverHandler() {

            @Override
            public void onMouseOver(MouseOverEvent event) {
                preferences.addStyleName("KS-Header-Link-Focus");               
            }});

        preferences.addMouseOutHandler(new MouseOutHandler() {

            @Override
            public void onMouseOut(MouseOutEvent event) {
                preferences.removeStyleName("KS-Header-Link-Focus");               

            }});
        preferences.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Go to preferences dialogs");               
            }});
    }

    private void buildLogoutLink() {
        logout.addStyleName("KS-Header-Link");
        logout.setTitle("Logout of Kuali Student");
        logout.addMouseOverHandler(new MouseOverHandler() {

            @Override
            public void onMouseOver(MouseOverEvent event) {
                logout.addStyleName("KS-Header-Link-Focus");               
            }});

        logout.addMouseOutHandler(new MouseOutHandler() {

            @Override
            public void onMouseOut(MouseOutEvent event) {
                logout.removeStyleName("KS-Header-Link-Focus");               

            }});

        logout.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Logging out now?");               
            }});
    }

    private void buildInitialBreadcrumb() {
        breadcrumb = new KSBreadcrumb();
        KSBreadcrumbItem item = new KSBreadcrumbItem("Home", "Home page");
        List<KSBreadcrumbItem> items = new ArrayList<KSBreadcrumbItem>();
        items.add(item);
        breadcrumb.setUnLinkedBreadcrumbList(items);
        breadcrumb.addStyleName("KS-Header-Breadcrumb");
    }
}
