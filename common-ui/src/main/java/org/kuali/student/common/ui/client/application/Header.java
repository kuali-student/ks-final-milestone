package org.kuali.student.common.ui.client.application;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.breadcrumb.KSBreadcrumb;
import org.kuali.student.common.ui.client.widgets.breadcrumb.KSBreadcrumbItem;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Header extends Composite {
    private final VerticalPanel main = new VerticalPanel();

    private final HorizontalPanel headerPanel = new HorizontalPanel();
    private final HorizontalPanel linksPanel = new HorizontalPanel();
    private final DockPanel linksDockPanel = new DockPanel();
    private final SimplePanel logoPanel = new SimplePanel();

    private final Hyperlink logout = new Hyperlink();
    private final Hyperlink preferences = new Hyperlink();

    private KSBreadcrumb breadcrumb;

    //	private final KSImage logo = new KSImage("images/KULSTP.jpg");
    private final KSImage logo = new KSImage("images/logo-ks.gif");

    public Header() {
        super.initWidget(main);

        buildInitialBreadcrumb();

        //TODO need to set these links to something, probably to an RPC call
        logout.setText("Logout");
        preferences.setText("Preferences");

        linksPanel.add(preferences);
        linksPanel.add(logout);
        linksPanel.addStyleName("KS-Right-Align");

        linksDockPanel.add(linksPanel ,DockPanel.EAST);
        linksDockPanel.addStyleName("KS-Header-Link");
        linksDockPanel.setHorizontalAlignment(DockPanel.ALIGN_RIGHT);
        linksDockPanel.setVerticalAlignment(DockPanel.ALIGN_BOTTOM);

        logoPanel.add(logo);

        headerPanel.add(logoPanel);
        headerPanel.addStyleName("KS-Header");

        main.add(headerPanel);
        main.add(linksDockPanel);    
        main.add(breadcrumb);
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
