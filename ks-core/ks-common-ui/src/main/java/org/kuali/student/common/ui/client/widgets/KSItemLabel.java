package org.kuali.student.common.ui.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.util.Elements;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Data.DataValue;
import org.kuali.student.core.assembly.data.Data.Value;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class KSItemLabel extends Composite implements HasCloseHandlers<KSItemLabel>, HasDataValue {

    private final String id = HTMLPanel.createUniqueId();
    private final String contentId = HTMLPanel.createUniqueId();
    private final String removeLinkId = HTMLPanel.createUniqueId();
    private final String backgroundId = HTMLPanel.createUniqueId();
    private final String PANEL_CONTENT_OPEN = "<span id='" + contentId + "'></span>";
    private final String PANEL_CONTENT_REMOVE_LINK = "<a href='javascript:return false;' title='Remove' class='ks-selected-list-value-remove' id='" + removeLinkId + "'></a>"; 
    private final String PANEL_CONTENT_BACKGROUND = "<div id='" + backgroundId + "' class='ks-selected-list-value-container'></div>"; 
    private HTMLPanel panel;

    private DataHelper dataHelper;
    private Data data;
    private List<Callback<Value>> valueChangeCallbacks =
        new ArrayList<Callback<Value>>();
    private String deletedKey;
    
    public KSItemLabel(boolean canEdit, DataHelper dataParser) {
        init(canEdit, dataParser);
    }
    
    private void init(boolean canEdit, DataHelper dataParser) {
        panel = new HTMLPanel(PANEL_CONTENT_OPEN + (canEdit ? PANEL_CONTENT_REMOVE_LINK : "") + PANEL_CONTENT_BACKGROUND);
        panel.setStyleName("ks-selected-list-value");
        panel.getElement().setId(id);
        this.dataHelper = dataParser;
        if(canEdit) {
            initHandlers();
        }
        String labelText = "";
        panel.getElementById(contentId).setInnerText(labelText);
        panel.setVisible(false);
        super.initWidget(panel);
    }
    
    @Override
    public HandlerRegistration addCloseHandler(CloseHandler<KSItemLabel> handler) {
        return addHandler(handler, CloseEvent.getType());
    }

    @Override
    public void addValueChangeCallback(Callback<Value> callback) {
        valueChangeCallbacks.add(callback);
    }

    private void callHandlers() {
        if (valueChangeCallbacks != null) {
//            MyGwtEvent myEvent = new MyGwtEvent(getValue());
            for (Callback<Data.Value> handler : valueChangeCallbacks) {
                handler.exec(getValue());
            }
        }
    }

    @Override
    public Value getValue() {
        DataValue result = new DataValue(data);
        return result;
    }

    @Override
    public void setValue(Value value) {
        deletedKey = null;
        if (value == null) {
            this.data = null;
        } else {
            this.data = value.get();
        }
        callHandlers();
        redraw();
    }
    
    public String getKey() {
        return dataHelper.getKey(data);
    }
    
    public String getDisplayText() {
        if (data == null) {
            return null;
        }
        return dataHelper.parse(data);
    }
    
    private void redraw() {
        String labelText = null;
        labelText = dataHelper.parse(data);
        panel.getElementById(contentId).setInnerText(labelText);
        if (labelText == null || labelText.trim().isEmpty()) {
            panel.setVisible(false);
        } else {
            panel.setVisible(true);
        }
    }

    private void initHandlers() {
        DOM.sinkEvents(panel.getElementById(removeLinkId), Event.ONCLICK);
        addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Element e = Element.as(event.getNativeEvent().getEventTarget());
                if (e.equals(panel.getElementById(removeLinkId))) {
                    doRemove();
                }
            }
        }, ClickEvent.getType());
    }

    private void doRemove() {
//        TODO
//        selectedValues.remove(this);
//        removedValues.add(this);
//        valuesPanel.remove(this);
        deletedKey = dataHelper.getKey(data);
        data = null;
        redraw();
        callHandlers();
        CloseEvent.fire(this, this);
    }
    
    public String getDeletedKey() {
        return deletedKey;
    }

    public void setHighlighted(boolean highlighted) {
        if (highlighted) {
            Elements.fadeIn(panel.getElementById(backgroundId), 250, 100, new Elements.EmptyFadeCallback());
        } else {
            Elements.fadeOut(panel.getElementById(backgroundId), 1000, 0, new Elements.EmptyFadeCallback());   
        }
    }
    
    public void removeHighlight(){
    	Elements.setOpacity(panel.getElementById(backgroundId), 0);
    }

    @Override
    public boolean equals(Object obj) {
        KSItemLabel item2 = null;
        boolean result = false;
        try {
            item2 = (KSItemLabel) obj;
        } catch (Exception e) {
            return false;
        }
        if (item2 != null && nullSafeEquals(getKey(), item2.getKey()) &&
                nullSafeEquals(getDisplayText(), item2.getDisplayText())) {
            result = true;
        }
        return result;
    }
    
    
    
    private boolean nullSafeEquals(String str1, String str2) {
        boolean result = false;
        String tempStr1 = (str1 == null)? "" : str1;
        String tempStr2 = (str2 == null)? "" : str2;
        result = tempStr1.equals(tempStr2);
        return result;
    }

    @Override
    public int hashCode() {
        String key = getKey();
        String displayText = getDisplayText();
        key = (key == null)? "" : key;
        displayText = (displayText == null)? "" : displayText;
        return key.hashCode() + displayText.hashCode();
    }
    
}
