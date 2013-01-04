package org.kuali.student.common.ui.client.widgets;

import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * A simple list panel that uses the UL/LI tag HTML structure 
 * 
 * @author alubbers
 *
 */
public class ULPanel extends ComplexPanel {

    private UListElement list;

    public ULPanel() {
        list = Document.get().createULElement();
        setElement(list);
    }

    @Override
    public void add(Widget child) {
        addChildElement(child, null);
    }

    public void add(Widget child, String liClassName) {
        addChildElement(child, liClassName);
    }
    
    private void addChildElement(Widget child, String className) {
        Element li = Document.get().createLIElement().cast();
        if(className != null) {
            li.setClassName(className);
        }
        list.appendChild(li);
        super.add(child, li);
    }
    
    public void setULClassName(String className) {
        list.setClassName(className);
    }

    /**
     * 
     * This method adds a collection of widgets as LI entries under this UL
     * 
     * @param reqCompWidgets
     * @param className CSS class name for each LI
     */
    public void addAll(List<Widget> widgets, String className) {
        for(Widget w : widgets) {
            add(w, className);
        }
    }
}