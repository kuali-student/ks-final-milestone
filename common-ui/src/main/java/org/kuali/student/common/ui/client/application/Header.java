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
//    private final HorizontalPanel linksPanel = new HorizontalPanel();
    private final DockPanel linksDockPanel = new DockPanel();
    private final SimplePanel logoPanel = new SimplePanel();
    
    private final Hyperlink logout = new Hyperlink();
    private final Hyperlink preferences = new Hyperlink();

    private final KSBreadcrumb breadcrumb = new KSBreadcrumb();

    //	private final KSImage logo = new KSImage("images/KULSTP.jpg");
    private final KSImage logo = new KSImage("images/logo-ks.gif");

    public Header() {
        super.initWidget(main);

        KSBreadcrumbItem item = new KSBreadcrumbItem("Home", "Home page");
        List<KSBreadcrumbItem> items = new ArrayList<KSBreadcrumbItem>();
        items.add(item);
        breadcrumb.setUnLinkedBreadcrumbList(items);
        breadcrumb.addStyleName("KS-Header-Breadcrumb");

        logout.setText("Logout");
        preferences.setText("Preferences");
        
        HorizontalPanel test = new HorizontalPanel();
        test.add(preferences);
        test.add(logout);
        linksDockPanel.add(test ,DockPanel.EAST);
       
//        linksDockPanel.add(logout,DockPanel.EAST);

        linksDockPanel.addStyleName("KS-Header-Link");
        test.addStyleName("KS-Right-Align");

        linksDockPanel.setHorizontalAlignment(DockPanel.ALIGN_RIGHT);
        linksDockPanel.setVerticalAlignment(DockPanel.ALIGN_BOTTOM);

        logoPanel.add(logo);
        
        headerPanel.add(logoPanel);

        
        headerPanel.addStyleName("KS-Header");

        main.add(headerPanel);
            main.add(linksDockPanel);    
            main.add(breadcrumb);
    }
}
