package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSDisclosureSectionImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite; 
import com.google.gwt.user.client.ui.Widget;

/**
 * A wrapper for gwt DisclosurePanel.  This class provides most of the same functionality, but sets KS css styles
 * for its default look and a variety of events (for improved browser compatibility and customizability).
 * 
 * This class allows a panel to appear when its header is clicked, and hides it
 * when its header is clicked again.
 * 
 * @author Kuali Student Team
 *
 */
public class KSDisclosureSection extends KSDisclosureSectionAbstract{
    
    KSDisclosureSectionAbstract section = GWT.create(KSDisclosureSectionImpl.class);
	

    /**
     * Constructs a disclosure section with headerText for its header text.  If headerText is null,
     * headerWidget is used as the header instead.  isOpen sets the KSDisclosurePanel's content to showing if
     * true.  If all paramaters are null, creates an empty disclosure section.
     * 
     * @param headerText the text to be shown in the header.
     * @param headerWidget used as the header if headerText is set to null.  This is NOT used otherwise.
     * @param isOpen true if panel content should be showing, false otherwise
     */
    public KSDisclosureSection(String headerText, Widget headerWidget, boolean isOpen) {
        section.init(headerText, headerWidget, isOpen);
        this.initWidget(section);
    }

    /**
     * Clear the content panel's widgets.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDisclosureSectionAbstract#clear()
     */
    public void clear(){
        section.clear();
    }

    /**
     * Add a widget to the content panel.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDisclosureSectionAbstract#add(com.google.gwt.user.client.ui.Widget)
     */
    public void add(Widget w){
        section.add(w);
    }

    /**
     * Remove a widget from the content panel.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDisclosureSectionAbstract#remove(com.google.gwt.user.client.ui.Widget)
     */
    public boolean remove(Widget w){
        return section.remove(w);
    }

    /**
     * Returns true if the content panel is showing ("open"), false otherwise.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDisclosureSectionAbstract#isOpen()
     */
    public boolean isOpen(){
        return section.isOpen();
    }

    /**
     * Returns true if the disclosure section is visible, false otherwise.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDisclosureSectionAbstract#isVisible()
     */
    public boolean isVisible(){
        return section.isVisible();
    }

    /**
     * Returns true if animation is enabled on this disclosure section.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDisclosureSectionAbstract#isAnimationEnabled()
     */
    public boolean isAnimationEnabled(){
        return section.isAnimationEnabled();
    }

    /**
     * Shows the panel content if set to true, otherwise hides the panel content.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDisclosureSectionAbstract#setOpen(boolean)
     */
    public void setOpen(boolean isOpen){
        section.setOpen( isOpen);
    }

    /**
     * Shows the widget if set to true, otherwise hides the widget.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDisclosureSectionAbstract#setVisible(boolean)
     */
    public void setVisible(boolean visible){
        section.setVisible( visible);
    }

	

}
