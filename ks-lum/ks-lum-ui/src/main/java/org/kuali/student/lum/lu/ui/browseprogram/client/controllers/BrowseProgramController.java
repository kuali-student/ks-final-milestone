package org.kuali.student.lum.lu.ui.browseprogram.client.controllers;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.lum.lu.ui.browseprogram.client.views.BrowseProgramView;

public class BrowseProgramController extends BasicLayout {

	public enum BrowseProgramViews {
		MAIN
	}

	public BrowseProgramController(String controllerId) {
		super(controllerId);
		addView(new BrowseProgramView(this, "Browse Program", BrowseProgramViews.MAIN));
		setDefaultView(BrowseProgramViews.MAIN);	
	}

}
