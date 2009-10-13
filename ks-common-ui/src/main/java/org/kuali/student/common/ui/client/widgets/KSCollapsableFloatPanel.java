package org.kuali.student.common.ui.client.widgets;

/**
 * A panel that floats above content on either the left or right side of the screen.  This panel can open and close
 * (a drawer like effect) when a button is pressed. 
 * 
 * @author Kuali Student Team
 *
 */
public interface  KSCollapsableFloatPanel extends KSFloatPanel {
	/**
	 * Check if the collapsable panel is "expanded".  True if it is expanded, false otherwise.
	 * 
	 * @return True if the panel is expanded, false otherwise.
	 */
	public boolean isExpanded();

	/**
	 * Set the panel's expanded flag.  True will expand the panel, false will collapse the panel.
	 * 
	 * @param expanded boolean representing if the panel is expanded.
	 */
	public void setExpanded(boolean expanded);
}
