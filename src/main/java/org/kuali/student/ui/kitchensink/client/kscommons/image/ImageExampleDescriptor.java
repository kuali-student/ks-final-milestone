package org.kuali.student.ui.kitchensink.client.kscommons.image;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ImageExampleDescriptor extends KitchenSinkExample {
    public ImageExampleDescriptor() {
        super();
        super.addResource("java", "ImageExample.java", "kscommons/image/ImageExample.java", "Example usage of KSImage.");
        super.addResource("css", "ImageExample.css", "examplecss/ImageExample.css", "Example styling of KSImage.");
    }
    public String getDescription() {       
        return "Image is a widget that contains an image. Optionally can specify the size of the image"; 
    }

    public Widget getExampleWidget() {
        return new ImageExample();
    }
 
    public String getTitle() {
        return "Image";
    }

}
