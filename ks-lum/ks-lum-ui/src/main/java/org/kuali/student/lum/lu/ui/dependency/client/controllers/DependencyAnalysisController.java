package org.kuali.student.lum.lu.ui.dependency.client.controllers;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.lum.lu.ui.dependency.client.views.DependencyAnalysisView;

public class DependencyAnalysisController extends BasicLayout{

	public enum DependencyViews{MAIN};
    	    
    public DependencyAnalysisController(String controllerId) {
		super(controllerId);		
		addView(new DependencyAnalysisView(this));
		setDefaultView(DependencyViews.MAIN);				
	}
    	
}
