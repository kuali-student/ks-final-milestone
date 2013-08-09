package org.kuali.student.lum.lu.ui.dependency.client.controllers;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.lum.common.client.lu.LUUIPermissions;
import org.kuali.student.lum.lu.ui.dependency.client.views.DependencyAnalysisView;

import com.google.gwt.core.client.GWT;

public class DependencyAnalysisController extends BasicLayout implements RequiresAuthorization {

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
        ExportElement element = GWT.create(ExportElement.class);
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
    
    @Override
    public boolean isAuthorizationRequired() {
        return true;
    }

    @Override
    public void setAuthorizationRequired(boolean required) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void checkAuthorization(final AuthorizationCallback authCallback) {
        Application.getApplicationContext().getSecurityContext().checkScreenPermission(LUUIPermissions.USE_DEPENDENCY_ANALYSIS_SCREEN, new Callback<Boolean>() {
            @Override
            public void exec(Boolean result) {

                final boolean isAuthorized = result;
            
                if(isAuthorized){
                    authCallback.isAuthorized();
                }
                else
                    authCallback.isNotAuthorized("User is not authorized: " + LUUIPermissions.USE_DEPENDENCY_ANALYSIS_SCREEN);
            }   
        });
    }

}
