package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.images.KSImages;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

public class KSRequiredMarker extends Composite{
    Image asterisk = KSImages.INSTANCE.asterisk().createImage();
    private boolean required = true;
    
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
        asterisk.setVisible(required);
    }

    public KSRequiredMarker(){
        initWidget(asterisk);
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        asterisk.setVisible(required);
    }
    
    
    
}
