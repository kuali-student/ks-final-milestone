package org.kuali.student.lum.lu.ui.dependency.client.controllers;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.lum.lu.ui.dependency.client.views.DependencyAnalysisView;

public class DependencyAnalysisController extends BasicLayout {

    public enum DependencyViews {
        MAIN
    };

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
    public List<ExportElement> getExportElementsFromView() {
        DependencyAnalysisView view = (DependencyAnalysisView) this.getCurrentView();
        ExportElement element = new ExportElement();
        element.setViewName(view.getName());
        element.setSectionName("");
        return view.getDepResultPanel().getExportElementSubset(element);
    }

    /**
     * @see org.kuali.student.common.ui.client.reporting.ReportExport#getExportTemplateName()
     */
    @Override
    public String getExportTemplateName() {
        return "analysis.template";
    }

    /**
     * This overridden method return the view name when the controller name is null.
     * 
     * @see org.kuali.student.common.ui.client.configurable.mvc.LayoutController#getName()
     */
    @Override
    public String getName() {
        String name = super.getName();
        if ((name == null) && (this.getCurrentView() != null)) {
            name = this.getCurrentView().getName();
        }
        return name;
    }

}
