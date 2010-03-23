package org.kuali.student.lum.lu.ui.tools.client.configuration.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton.ButtonStyle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class CluItemValue implements ItemValue<CluItemValue> {
    
    private String id;
    private String name;
    private Callback<CluItemValue> deleteCallback;

    @Override
    public List<Widget> generateDisplayWidgets() {
        List<Widget> displayWidgets = new ArrayList<Widget>(3);
        KSLinkButton deleteButton = new KSLinkButton("X", ButtonStyle.DELETE);
        KSLabel itemLabel = new KSLabel(getName());
        // TODO set style color here
//      itemLabel.getElement().getStyle().setProperty("background", "#E0E0E0");
        displayWidgets.add(itemLabel);
        displayWidgets.add(deleteButton);
        
        if (deleteCallback != null) {
            deleteButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    deleteCallback.exec(CluItemValue.this);
                }
            });
        }
        return displayWidgets;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Callback<CluItemValue> getDeleteCallback() {
        return deleteCallback;
    }

    @Override
    public void setDeleteCallback(Callback<CluItemValue> deleteCallback) {
        this.deleteCallback = deleteCallback;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        CluItemValue item2 = null;
        
        if (obj == null) return false;
        item2 = (CluItemValue) obj;
        if (id == item2.getId() && 
                name != null && item2.getName() != null &&
                name.equals(item2.getName())) {
            result = true;
        }
        return result;
    }

    @Override
    public int hashCode() {
        int hashCode = -1;
        hashCode = id.hashCode() + name.hashCode();
        return hashCode;
    }
    
}
