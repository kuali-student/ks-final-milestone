package org.kuali.student.lum.lu.ui.course.client.controllers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.lum.lu.ui.course.client.views.CategoryManagementView;
import org.kuali.student.lum.lu.ui.course.client.views.CurriculumHomeView;
import org.kuali.student.lum.lu.ui.main.client.controllers.ApplicationController;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CatalogBrowserController;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CluSetsManagementController;
import org.kuali.student.lum.program.client.ProgramManager;

public class CurriculumHomeController extends LayoutController {

    private CurriculumHomeView home;
    private final SpanPanel panel = new SpanPanel();

    private CourseProposalController courseProposalController;
    private LayoutController viewCourseController;
    private LayoutController manageCluSetsController;
    private LayoutController browseCatalogController;
    private final ProgramManager programManager = new ProgramManager();

    private abstract class RunAsyncGetView implements RunAsyncCallback {
        public void onFailure(Throwable reason) {
            Window.alert("Download failed.  Please try again.");
        }
    }

    public enum LUMViews {
        DEFAULT,
        COURSE_PROPOSAL,
        VIEW_COURSE,
        PROGRAM_VIEW,
        PROGRAM_EDIT,
        PROGRAM_CREATE,
        CLU_SETS,
        VARIATION_VIEW,
        VARIATION_EDIT,
        COURSE_CATALOG,
        LO_CATEGORIES,
        BACC_PROGRAM_VIEW,
        BACC_PROGRAM_EDIT,
        CORE_PROGRAM_VIEW,
        CORE_PROGRAM_EDIT
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
    public <V extends Enum<?>> void getView(V viewType, final Callback<View> callback) {
        //this is done so the views can have delayed loading

        switch ((LUMViews) viewType) {
            case DEFAULT:
                callback.exec(home);
                break;
            case COURSE_PROPOSAL:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(getCourseProposalController());
                    }
                });
                break;
            case VIEW_COURSE:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(getViewCourseController());
                    }
                });
                break;
            case PROGRAM_VIEW:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(programManager.getProgramViewController());
                    }
                });
                break;
            case PROGRAM_EDIT:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(programManager.getProgramEditController());
                    }
                });
                break;
            case PROGRAM_CREATE:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(programManager.getProgramEditController());
                    }
                });
                break;
            case CLU_SETS:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(getCluSetsController());
                    }
                });
                break;
            case COURSE_CATALOG:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(getBrowseCatalogController());
                    }
                });
                break;
            case VARIATION_VIEW:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(programManager.getVariationViewController());
                    }
                });
                break;
            case VARIATION_EDIT:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(programManager.getVariationEditController());
                    }
                });
                break;
            case CORE_PROGRAM_VIEW:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(programManager.getCoreViewController());
                    }
                });
                break;
            case CORE_PROGRAM_EDIT:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(programManager.getCoreEditController());
                    }
                });
                break;
            case BACC_PROGRAM_VIEW:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(programManager.getBaccViewController());
                    }
                });
                break;
            case BACC_PROGRAM_EDIT:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(programManager.getBaccEditController());
                    }
                });
                break;
            case LO_CATEGORIES:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(getCategoryManagementController());
                    }
                });
                break;
            default:
                callback.exec(home);
        }
    }


    private View getCategoryManagementController() {
        return new CategoryManagementView(this, "Learning Objective Categories", LUMViews.LO_CATEGORIES);
    }

    private CourseProposalController getCourseProposalController() {
        courseProposalController = new CourseProposalController();
        return courseProposalController;
    }

    private LayoutController getViewCourseController() {
        if (viewCourseController == null) {
            viewCourseController = new ViewCourseParentController();
        }
        return this.viewCourseController;
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

    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        return LUMViews.class;
    }

    @Override
    public Enum<?> getViewEnumValue(String enumValue) {
        return LUMViews.valueOf(enumValue);
    }

    @Override
    public void updateModel() {
        // No model needed here
    }

    public <V extends Enum<?>> void showView(V viewType, Callback<Boolean> onReadyCallback) {
        if (viewType == LUMViews.DEFAULT) {
            WindowTitleUtils.setContextTitle(name);
        }
        super.showView(viewType, onReadyCallback);
    }

    ;

}
