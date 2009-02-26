package org.kuali.student.ui.kitchensink.client.kscommons.image;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_IMAGE;

import org.kuali.student.common.ui.client.widgets.KSImage;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ImageExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    
    final KSImage image = new KSImage("images/flower1.jpg");


    public ImageExample() {

        main.addStyleName(STYLE_EXAMPLE);
        image.addStyleName(STYLE_IMAGE);
        image.setTitle("This is an image");
        main.add(image);


        super.initWidget(main);
    }


}
