package org.kuali.student.ui.kitchensink.client.kscommons.tooltip;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_TOOLTIP;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSToolTip;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ToolTipExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
   
    final KSLabel label = new KSLabel(" Move mouse across image to see more information.");

    final Image image = new Image("images/flowers.jpg");
    final KSToolTip tip = new KSToolTip("Osteospermum", new KSLabel("There are about 50 species, native to Africa, 35 species in southern Africa, and southwestern Arabia. They are half-hardy perennials or subshrubs."));

    public ToolTipExample() {
        
        main.addStyleName(STYLE_EXAMPLE);
        tip.addStyleName(STYLE_TOOLTIP);

       
        image.addMouseOverHandler(new MouseOverHandler() {

            @Override
            public void onMouseOver(MouseOverEvent event) {
                tip.show(event);
                
            } });

        image.addMouseOutHandler(new MouseOutHandler() {

            @Override
            public void onMouseOut(MouseOutEvent event) {
                tip.hide();
                
            } });
       
        main.add(label);
        main.add(image);

        super.initWidget(main);
    }


}
