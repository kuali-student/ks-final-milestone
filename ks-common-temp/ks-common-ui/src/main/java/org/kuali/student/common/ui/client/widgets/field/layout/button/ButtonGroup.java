package org.kuali.student.common.ui.client.widgets.field.layout.button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ButtonEnum;

public abstract class ButtonGroup<T extends ButtonEnum> extends ButtonLayout{
    private List<Callback<T>> callbacks = new ArrayList<Callback<T>>();
    protected Map<T, KSButton> buttonMap = new HashMap<T, KSButton>();

    public void addCallback(Callback<T> callback) {
        callbacks.add(callback);
    }

    public List<Callback<T>> getCallbacks() {
        return callbacks;
    }
    
    protected void sendCallbacks(T type){
        for(Callback<T> c: getCallbacks()){
            c.exec(type);
        }
    }
    
    public void setButtonText(T key, String text){
        buttonMap.get(key).setText(text);
    }
    
    public KSButton getButton(T key){
        return buttonMap.get(key);
    }
}
