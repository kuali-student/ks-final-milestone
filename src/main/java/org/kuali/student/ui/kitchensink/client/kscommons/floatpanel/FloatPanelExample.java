package org.kuali.student.ui.kitchensink.client.kscommons.floatpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSFloatPanel;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FloatPanelExample extends Composite {


    final HorizontalPanel main = new HorizontalPanel();

    final KSFloatPanel floatPanel = KSWidgetFactory.getFloatPanelInstance();
    final VerticalPanel contentPanel = new VerticalPanel();

    final KSButton showRightButton ;
    final KSButton showLeftButton ;
    final KSButton hideButton ;


    public FloatPanelExample() {

        main.addStyleName(STYLE_EXAMPLE);

        showRightButton = KSWidgetFactory.getButtonInstance("Click to see Popup Right",
                new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                floatPanel.setLocation(KSFloatPanel.FloatLocation.FLOAT_RIGHT);
                floatPanel.show();

            }});

        showLeftButton = KSWidgetFactory.getButtonInstance("Click to see Popup Left",
                new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                floatPanel.setLocation(KSFloatPanel.FloatLocation.FLOAT_LEFT);
                floatPanel.show();

            }});


        hideButton = KSWidgetFactory.getButtonInstance("Click to close Popup", 
                new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                floatPanel.hide();

            }});
        contentPanel.add(KSWidgetFactory.getImageInstance("images/flower2.jpg") );
        contentPanel.add(hideButton);

        floatPanel.setWidget(contentPanel);
        floatPanel.setHeightRatio(25);

        main.add(showLeftButton);
        main.add(showRightButton);

        super.initWidget(main);


    }


}
