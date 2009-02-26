package org.kuali.student.ui.kitchensink.client.kscommons.collapsablefloatpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCollapsableFloatPanel;
import org.kuali.student.common.ui.client.widgets.KSFloatPanel;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CollapsableFloatPanelExample extends Composite {


    final HorizontalPanel main = new HorizontalPanel();

    final KSCollapsableFloatPanel floatPanel = KSWidgetFactory.getCollapsablePanelInstance(true);
    final VerticalPanel contentPanel = new VerticalPanel();

    final KSButton showButton ;
    final KSButton hideButton ;


    public CollapsableFloatPanelExample() {

        main.addStyleName(STYLE_EXAMPLE);

        showButton = KSWidgetFactory.getButtonInstance("Click to see Popup",
                new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
               floatPanel.show();

            }});


        hideButton = KSWidgetFactory.getButtonInstance("Click to close Popup", 
                new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                floatPanel.hide();

            }});
        contentPanel.add(KSWidgetFactory.getImageInstance("images/flower3.jpg") );
        contentPanel.add(hideButton);

        floatPanel.setWidget(contentPanel);
        floatPanel.setHeightRatio(25);
//        floatPanel.setLocation(KSFloatPanel.FloatLocation.FLOAT_LEFT);

        main.add(showButton);

        super.initWidget(main);


    }


}
