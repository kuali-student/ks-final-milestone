package org.kuali.student.lum.lu.ui.course.client.controllers;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.lum.lu.ui.course.client.views.CurriculumHomeView;
import org.kuali.student.lum.lu.ui.main.client.controllers.ApplicationController;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CatalogBrowserController;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CluSetsManagementController;
import org.kuali.student.lum.program.client.ProgramViewController;

public class CurriculumHomeController extends LayoutController {

    private boolean loaded = false;

    private CurriculumHomeView home;
    private SpanPanel panel = new SpanPanel();

    private CourseProposalController courseProposalController;
    private LayoutController viewCourseController;
    private LayoutController manageCluSetsController;
    private LayoutController browseCatalogController;
    private LayoutController programViewController;
    private LayoutController programEditController;

    public enum LUMViews {
        DEFAULT,
        COURSE_PROPOSAL,
        VIEW_COURSE,
        PROGRAM_VIEW,
        PROGRAM_EDIT,
        CLU_SETS,
        COURSE_CATALOG
    }

    public CurriculumHomeController(Controller controller, String name, Enum<?> viewType) {
        super(CurriculumHomeController.class.getName());
        super.setController(controller);
        super.setName(name);
        super.setViewEnum(viewType);
        this.setDefaultView(LUMViews.DEFAULT);
        this.initWidget(panel);
        setupDefaultView();
    }

    private void setupDefaultView() {
        home = new CurriculumHomeView(this, LUMViews.DEFAULT);
    }

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        //this is done so the views can have delayed loading
        switch ((LUMViews) viewType) {
            case DEFAULT:
                return home;
            case COURSE_PROPOSAL:
                return getCourseProposalController();
            case VIEW_COURSE:
                return getViewCourseController();
            case PROGRAM_VIEW:
                return getProgramViewController();
            case PROGRAM_EDIT:
                return getProgramEditController();
            case CLU_SETS:
                return getCluSetsController();
            case COURSE_CATALOG:
                return getBrowseCatalogController();
            default:
                return home;
        }
    }


    private CourseProposalController getCourseProposalController() {
        courseProposalController = new CourseProposalController();
        return courseProposalController;
    }

    private LayoutController getViewCourseController() {
        if (viewCourseController == null) {
            viewCourseController = new ViewCourseController();
        }
        return this.viewCourseController;
    }

    private LayoutController getProgramViewController() {
        if (programViewController == null) {
            programViewController = new ProgramViewController();
        }
        return programViewController;
    }

    private LayoutController getProgramEditController() {
        if (programEditController == null) {
            programEditController = new ProgramViewController();
        }
        return programEditController;
    }

    private LayoutController getCluSetsController() {
        manageCluSetsController = new CluSetsManagementController();
        return manageCluSetsController;
    }

    private LayoutController getBrowseCatalogController() {
        browseCatalogController = new CatalogBrowserController(this);
        return browseCatalogController;
    }

    @Override
    protected void hideView(View view) {
        ApplicationController.getApplicationViewContainer().clear();
    }

    @Override
    protected void renderView(View view) {
        ApplicationController.getApplicationViewContainer().add(view.asWidget());
    }

    public Class<? extends Enum<?>> getViewsEnum() {
        return LUMViews.class;
    }

    @Override
    public Enum<?> getViewEnumValue(String enumValue) {
        return LUMViews.valueOf(enumValue);
    }

    @Override
    public void showDefaultView(Callback<Boolean> onReadyCallback) {
        HistoryManager.setLogNavigationHistory(false);
        this.showView(LUMViews.DEFAULT);
        HistoryManager.setLogNavigationHistory(true);
    }

    @Override
    public void updateModel() {
        // No model needed here
    }

}
