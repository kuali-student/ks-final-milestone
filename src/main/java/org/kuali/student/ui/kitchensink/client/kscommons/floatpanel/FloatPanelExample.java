package org.kuali.student.ui.kitchensink.client.kscommons.floatpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_FLOAT_PANEL;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSFloatPanel;
import org.kuali.student.common.ui.client.widgets.KSImage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FloatPanelExample extends Composite {


    final HorizontalPanel main = new HorizontalPanel();

    final KSFloatPanel floatPanel = GWT.create(KSFloatPanel.class);
    final VerticalPanel contentPanel = new VerticalPanel();

    final KSButton showRightButton ;
    final KSButton showLeftButton ;
    final KSButton hideButton ;


    public FloatPanelExample() {

        main.addStyleName(STYLE_EXAMPLE);

        showRightButton = new KSButton("Click to see Popup Right",
                new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                floatPanel.setLocation(KSFloatPanel.FloatLocation.FLOAT_RIGHT);
                floatPanel.show();

            }});

        showLeftButton = new KSButton("Click to see Popup Left",
                new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                floatPanel.setLocation(KSFloatPanel.FloatLocation.FLOAT_LEFT);
                floatPanel.show();

            }});


        hideButton = new KSButton("Click to close Popup", 
                new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                floatPanel.hide();

            }});
        contentPanel.add(new KSImage("images/flower2.jpg") );
        contentPanel.add(hideButton);
        contentPanel.addStyleName(STYLE_FLOAT_PANEL);

        floatPanel.setWidget(contentPanel);
        floatPanel.setHeightRatio(25);

        main.add(showLeftButton);
        main.add(showRightButton);

        super.initWidget(main);


    }


}
