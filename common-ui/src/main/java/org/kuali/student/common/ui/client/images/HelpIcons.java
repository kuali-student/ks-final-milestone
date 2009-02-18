package org.kuali.student.common.ui.client.images;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface HelpIcons extends ImageBundle {

    // TODO set the resource value to an image of a question mark
    @Resource("the/path/to/help-default.gif")
    AbstractImagePrototype defaultIcon();
    
    // TODO set the resource value to an image of a green checkmark
    @Resource("the/path/to/help-ok.gif")
    AbstractImagePrototype okIcon();
    
    // TODO set the resource value to an image of a yellow triangle with exclamation point
    @Resource("the/path/to/help-error.gif")
    AbstractImagePrototype errorIcon();
    
}
