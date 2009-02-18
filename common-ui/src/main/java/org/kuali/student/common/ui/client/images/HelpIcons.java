package org.kuali.student.common.ui.client.images;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.ImageBundle.Resource;

public interface HelpIcons extends ImageBundle {

    // set the resource value to an image of a question mark
    @Resource("help-16x16.png")
    AbstractImagePrototype defaultIcon();
    
    // set the resource value to an image of a green checkmark
    @Resource("agt_action_success.png")
    AbstractImagePrototype okIcon();
    
    // set the resource value to an image of a yellow triangle with exclamation point
    @Resource("messagebox_warning.png")
    AbstractImagePrototype errorIcon();
    
}
