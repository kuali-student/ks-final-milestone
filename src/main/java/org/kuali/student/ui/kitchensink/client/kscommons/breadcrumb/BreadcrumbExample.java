package org.kuali.student.ui.kitchensink.client.kscommons.breadcrumb;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.breadcrumb.KSBreadcrumb;
import org.kuali.student.common.ui.client.widgets.breadcrumb.KSBreadcrumbItem;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BreadcrumbExample extends Composite {

    private final VerticalPanel main = new VerticalPanel();


    public BreadcrumbExample() {
        
        Frame frame = new Frame("http://en.wikipedia.org/wiki/Poissy");
        frame.setSize("800px", "500px");

        KSBreadcrumb breadcrumb = new KSBreadcrumb();

      List <KSBreadcrumbItem> items = buildLinkedList();
      breadcrumb.setLinkedBreadcrumbList(items);
        
//        List <KSBreadcrumbItem> items = buildUnlinkedList();
//        breadcrumb.setUnLinkedBreadcrumbList(items);


        main.add(breadcrumb);
        main.add(frame);

        super.initWidget(main);
    }
    
    private List<KSBreadcrumbItem> buildLinkedList () {

      KSBreadcrumbItem item0 = new KSBreadcrumbItem("Home", "#", "Return to Home", "_self");
      KSBreadcrumbItem item1 = new KSBreadcrumbItem("Europe", "#", "Return to Europe", "_self");
      KSBreadcrumbItem item2 = new KSBreadcrumbItem("France", "#", "Return to France", "_self");
      KSBreadcrumbItem item3 = new KSBreadcrumbItem("Paris", "#", "Return to Paris", "_self");                      
      KSBreadcrumbItem item4 = new KSBreadcrumbItem("Poissy", "Current Page");
      List<KSBreadcrumbItem> items = new ArrayList<KSBreadcrumbItem>();
      items.add(item0);
      items.add(item1);
      items.add(item2);
      items.add(item3);
      items.add(item4);

      return items;
  }
}
