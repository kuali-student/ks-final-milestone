package org.kuali.student.lum.lu.ui.dependency.client.controllers;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.client.util.ExportUtils;
import org.kuali.student.lum.lu.ui.dependency.client.views.DependencyAnalysisView;

public class DependencyAnalysisController extends BasicLayout{

	public enum DependencyViews{MAIN};
    	    
    public DependencyAnalysisController(String controllerId) {
		super(controllerId);		
		addView(new DependencyAnalysisView(this));
		setDefaultView(DependencyViews.MAIN);				
	}
    
    public void showExport(boolean show) {
    	DependencyAnalysisView dependancyView = (DependencyAnalysisView) viewMap.get(DependencyViews.MAIN);
			if (dependancyView.getHeader() != null) {
				dependancyView.getHeader().showExport(isExportButtonActive());
				
			}
    }
    
    @Override
    public boolean isExportButtonActive() {
            return true;            
    }

	@Override
	public DataModel getExportDataModel() {
		// TODO Auto-generated method stub
		return super.getExportDataModel();
	}

	@Override
	public ArrayList<ExportElement> getExportElementsFromView() {
		DependencyAnalysisView view = (DependencyAnalysisView) this.getCurrentView();
		ArrayList<ExportElement> exportElements = new ArrayList<ExportElement>(); 
		exportElements = view.getDepResultPanel().getExportElementsWidget(view.getName(), view.getName());
		return exportElements;
	}  

    	
}
