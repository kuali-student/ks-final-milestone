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

    private final HorizontalPanel linksPanel = new HorizontalPanel();
    private final DockPanel linksSpacerPanel = new DockPanel();
    private final SimplePanel logoPanel = new SimplePanel();

    private KSBreadcrumb breadcrumb;

    private final KSImage logo = new KSImage("images/Kuali_logo_bar.jpg");
    private final KSImage separator1 = new KSImage("images/red_gradient_1.jpg");
    private final KSImage separator2 = new KSImage("images/red_gradient_2.jpg");

    public Header() {
        super.initWidget(main);

        buildInitialBreadcrumb();

        buildLinksPanel();
        buildLogoPanel();
        
        main.add(logoPanel);
        main.add(separator1);
        main.add(linksSpacerPanel);    
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
        //TODO This should be dynamic, i.e. accept a list of required links.

        separator1.addStyleName("KS-Header-Separator");
        separator2.addStyleName("KS-Header-Separator");

        linksPanel.add(buildLink("Preferences", "Create, modify or delete user preferences"));
        linksPanel.add(buildLink("Logout", "End current Kuali Student session"));
        linksPanel.addStyleName("KS-Header-Link-Panel");

        linksSpacerPanel.add(linksPanel ,DockPanel.EAST);
        linksSpacerPanel.addStyleName("KS-Header-Link-Spacer");
        linksSpacerPanel.setHorizontalAlignment(DockPanel.ALIGN_RIGHT);
        linksSpacerPanel.setVerticalAlignment(DockPanel.ALIGN_BOTTOM);
    }

    private KSLabel buildLink(final String text, final String title) {
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
                Window.alert("Go to " + text + " function");               
            }});
        
        return link;
        
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
