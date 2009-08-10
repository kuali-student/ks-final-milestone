package org.kuali.student.common.ui.client.widgets.buttonlayout;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ButtonColumn extends ButtonLayoutTwoGroups{
    private VerticalPanel topPanel = new VerticalPanel();
    private VerticalPanel bottomPanel = new VerticalPanel();
    private SimplePanel contentPanel = new SimplePanel();
    private DockPanel mainPanel = new DockPanel();
    private Widget content = null;
    
    public ButtonColumn(){
        setupDefaultStyles();
        mainPanel.add(contentPanel, DockPanel.WEST);
        mainPanel.add(topPanel, DockPanel.NORTH);
        mainPanel.setVerticalAlignment(HasAlignment.ALIGN_BOTTOM);
        mainPanel.add(bottomPanel, DockPanel.SOUTH);
        this.initWidget(mainPanel);
    }
    
    public ButtonColumn(boolean contentRight){
        setupDefaultStyles();
        if(contentRight){
            mainPanel.add(contentPanel, DockPanel.EAST);
        }
        else{
            mainPanel.add(contentPanel, DockPanel.WEST);
        }
        mainPanel.add(topPanel, DockPanel.NORTH);
        mainPanel.setVerticalAlignment(HasAlignment.ALIGN_BOTTOM);
        mainPanel.add(bottomPanel, DockPanel.SOUTH);
        this.initWidget(mainPanel);
    }
    
    private void setupDefaultStyles(){
        mainPanel.addStyleName(KSStyles.KS_BUTTON_COLUMN_PANEL);
        topPanel.addStyleName(KSStyles.KS_BUTTON_COLUMN_TOP_PANEL);
        bottomPanel.addStyleName(KSStyles.KS_BUTTON_COLUMN_BOTTOM_PANEL);
        contentPanel.addStyleName(KSStyles.KS_BUTTON_COLUMN_CONTENT_PANEL);
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        if(content != null){
            mainPanel.setHeight(content.getOffsetHeight() + "px");
        }
    }

    @Override
    public void setContent(Widget w) {
        contentPanel.setWidget(w);
        content = w;
    }

    @Override
    public void addButtonToPrimaryGroup(KSButton button) {
        button.addStyleName(KSStyles.KS_BUTTON_COLUMN_BUTTON);
        topPanel.add(button);
        buttons.add(button);
    }

    @Override
    public void addButtonToSecondaryGroup(KSButton button) {
        button.addStyleName(KSStyles.KS_BUTTON_COLUMN_BUTTON);
        bottomPanel.add(button);
        buttons.add(button);
    }

    @Override
    public void addButton(KSButton button) {
        this.addButtonToPrimaryGroup(button);
        
    }
}
