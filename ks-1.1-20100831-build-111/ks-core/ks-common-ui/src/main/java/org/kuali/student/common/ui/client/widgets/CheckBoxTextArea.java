package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextArea;
/**
 * CheckBoxTextArea is for linking a master text area with a slave text area.
 * A check box is used to control when to use the text from master or from 
 * slave text area.
 * 
 * 
 * */
public class CheckBoxTextArea extends Composite implements HasText{
    HasText master;
    KSTextArea slave = new KSTextArea();
    KSCheckBox checkBox = new KSCheckBox();
    public CheckBoxTextArea(HasText master, String checkBoxText){
        this.master = master;
        FlowPanel flowPanel = new FlowPanel();
        flowPanel.add(checkBox);
        flowPanel.add(slave);
        super.initWidget(flowPanel);
        checkBox.addValueChangeHandler(new ValueChangeHandler(){
            @Override
            public void onValueChange(ValueChangeEvent event) {
               if(checkBox.getValue() == true){
                   slave.setVisible(false);
               }else{
                   slave.setVisible(true);
               }
            }
        });
        checkBox.setValue(true);
        checkBox.setText(checkBoxText);
    }
    @Override
    public String getText() {
        if(checkBox.getValue() == true){
            return master.getText();
        }else{
            return slave.getText();
        }
    }

    @Override
    public void setText(String text) {
        if(master.getText().equals(text)){
            checkBox.setValue(true);
        }else{
            checkBox.setValue(false);
            slave.setText(text);
        }
    }
}
