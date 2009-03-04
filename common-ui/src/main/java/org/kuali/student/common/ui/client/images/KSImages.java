package org.kuali.student.common.ui.client.images;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.ImageBundle.Resource;

public interface KSImages  extends ImageBundle{

	//Text Bar Images*******************************************************
    AbstractImagePrototype bold();

    AbstractImagePrototype createLink();

    AbstractImagePrototype hr();

    AbstractImagePrototype indent();

    AbstractImagePrototype insertImage();

    AbstractImagePrototype italic();

    AbstractImagePrototype justifyCenter();

    AbstractImagePrototype justifyLeft();

    AbstractImagePrototype justifyRight();

    AbstractImagePrototype ol();

    AbstractImagePrototype outdent();

    AbstractImagePrototype removeFormat();

    AbstractImagePrototype removeLink();

    AbstractImagePrototype strikeThrough();

    AbstractImagePrototype subscript();

    AbstractImagePrototype superscript();

    AbstractImagePrototype ul();

    AbstractImagePrototype underline();
    
    AbstractImagePrototype popout();
    
    //Help Link Images***************************************************
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
