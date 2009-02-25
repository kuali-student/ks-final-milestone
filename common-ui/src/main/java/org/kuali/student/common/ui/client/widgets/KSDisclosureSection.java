package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSDisclosureSectionImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite; 
import com.google.gwt.user.client.ui.Widget;

public class KSDisclosureSection extends KSDisclosureSectionAbstract{
    
    KSDisclosureSectionAbstract section = GWT.create(KSDisclosureSectionImpl.class);
	
	/**
     * This constructs a ...
     * 
     */
    public KSDisclosureSection(String headerText, Widget headerWidget, boolean isOpen) {
        section.init(headerText, headerWidget, isOpen);
        this.initWidget(section);
    }

    public void clear(){
        section.clear();
    }

    public void add(Widget w){
        section.add(w);
    }

    public boolean remove(Widget w){
        return section.remove(w);
    }

    public boolean isOpen(){
        return section.isOpen();
    }

    public boolean isVisible(){
        return section.isVisible();
    }

    public boolean isAnimationEnabled(){
        return section.isAnimationEnabled();
    }

    public void setOpen(boolean isOpen){
        section.setOpen( isOpen);
    }

    public void setVisible(boolean visible){
        section.setVisible( visible);
    }

	

}
