package org.kuali.student.commons.ui.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * "Rigid" replacement for DockPanel, works better with resizing and CSS layouts
 * @author wilj
 *
 */
public class DockLayout extends Composite {
	final VerticalPanel main = new VerticalPanel();
	final SimplePanel north = new SimplePanel();
	final HorizontalPanel middleRow = new HorizontalPanel();
	final SimplePanel west = new SimplePanel();
	final SimplePanel center = new SimplePanel();
	final SimplePanel east = new SimplePanel();
	final SimplePanel south = new SimplePanel();
	
	public DockLayout() {
		main.add(north);
		main.add(middleRow);
		middleRow.add(west);
		middleRow.add(center);
		middleRow.add(east);
		main.add(south);
		super.initWidget(main);
	}

	public SimplePanel getNorth() {
		return north;
	}

	public SimplePanel getWest() {
		return west;
	}

	public SimplePanel getCenter() {
		return center;
	}

	public SimplePanel getEast() {
		return east;
	}

	public SimplePanel getSouth() {
		return south;
	}
	
}
