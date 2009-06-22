package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class KSThinTitleBar extends Composite{
    private final HorizontalPanel titlePanel = new HorizontalPanel();
    private Label titleLabel = new Label();
    
    public KSThinTitleBar(String text){
        titlePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        titleLabel = new Label(text, false);
        titlePanel.add(titleLabel);
        titlePanel.addStyleName(KSStyles.KS_POPUP_HEADER);
        this.initWidget(titlePanel);
    }
    
    public void setTitle(String text){
        titleLabel.setText(text);
    }
    
    public String getTitle(){
        return titleLabel.getText();
    }
}
