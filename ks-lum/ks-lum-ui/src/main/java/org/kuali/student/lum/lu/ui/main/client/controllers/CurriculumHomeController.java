package org.kuali.student.lum.lu.ui.main.client.controllers;

import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.configuration.LUMViews;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.ui.browseprogram.client.controllers.BrowseProgramController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminRetireController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminWithoutVersionController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseRetireByProposalController;
import org.kuali.student.lum.lu.ui.course.client.controllers.ViewCourseParentController;
import org.kuali.student.lum.lu.ui.course.client.views.CategoryManagementView;
import org.kuali.student.lum.lu.ui.course.client.views.CurriculumHomeView;
import org.kuali.student.lum.lu.ui.dependency.client.controllers.DependencyAnalysisController;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CatalogBrowserController;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CluSetsManagementController;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.core.CoreManager;
import org.kuali.student.lum.program.client.credential.CredentialManager;
import org.kuali.student.lum.program.client.major.MajorManager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;

/**
 *   Curriculum home controller which controls the main LayoutController views of the lum application.  The
 * default view of this controller is the Curriculum Home Landing page.  The following views are views within
 * this controller's scope:<br>
 * 		COURSE_PROPOSAL<br>
        COURSE_RETIRE_BY_PROPOSAL<br>
        VIEW_COURSE<br>
        PROGRAM_VIEW<br>
        PROGRAM_EDIT<br>
        PROGRAM_CREATE<br>
        PROGRAM_VERSIONS<br>
        CLU_SETS<br>
        VARIATION_VIEW<br>
        VARIATION_EDIT<br>
        COURSE_CATALOG<br>
        LO_CATEGORIES<br>
        BACC_PROGRAM_VIEW<br>
        BACC_PROGRAM_EDIT<br>
        BACC_PROGRAM_VERSIONS<br>
        CORE_PROGRAM_VIEW<br>
        CORE_PROGRAM_EDIT<br>
        CORE_PROGRAM_VERSIONS<br>
 * These views can be accessed through links and searches provided by the CurriculumHomeView (the default view).
 * 
 * @author Kuali Student Team
 * @see CurriculumHomeView
 */
public class CurriculumHomeController extends LayoutController {

    private CurriculumHomeView home;
    private final SpanPanel panel = new SpanPanel();

    private CourseProposalController courseProposalController;
    private CourseRetireByProposalController courseRetireByProposalController;
    private CourseAdminController courseAdminController;
    private CourseAdminWithoutVersionController courseAdminWithoutVersionController;
    private CourseAdminRetireController courseAdminRetireController;
    private LayoutController viewCourseController;
    private LayoutController manageCluSetsController;
    private LayoutController browseCatalogController;
    private LayoutController dependencyAnalysisController;
    private LayoutController browseProgramController;
    private MajorManager majorManager = new MajorManager();
    private CredentialManager credentialManager = new CredentialManager();
    private CoreManager coreManager = new CoreManager();

    private abstract class RunAsyncGetView implements RunAsyncCallback {
        public void onFailure(Throwable reason) {
            Window.alert("Download failed.  Please try again.");
        }
    }
    
    public CurriculumHomeController() {
        super();
        this.setDefaultView(LUMViews.DEFAULT);
        this.initWidget(panel);
        setupDefaultView();
    }

    public CurriculumHomeController(Controller controller, String name, Enum<?> viewType) {
        super();
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
    public <V extends Enum<?>> void getView(V viewType, final Callback<View> callback, Map<String, String> tokenMap) {
        //this is done so the views can have delayed loading

        switch ((LUMViews) viewType) {
            case DEFAULT:
                callback.exec(home);
                break;
            case COURSE_PROPOSAL:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(getCourseProposalController());
//                    }
//                });
                break;
            case COURSE_RETIRE_BY_PROPOSAL:
//              GWT.runAsync(new RunAsyncGetView() {
//                  @Override
//                  public void onSuccess() {
                      callback.exec(getCourseRetirebyProposalController());
//                  }
//              });
              break;  
            case COURSE_ADMIN:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(getCourseAdminController());
//                    }
//                });
                break;
            case COURSE_ADMIN_NO_VERSION:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(getCourseAdminWithoutVersionController());
//                    }
//                });
                break;
            case COURSE_ADMIN_RETIRE:
//              GWT.runAsync(new RunAsyncGetView() {
//                  @Override
//                  public void onSuccess() {
                      callback.exec(getCourseAdminRetireController());
//                  }
//              });
              break;

            case VIEW_COURSE:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(getViewCourseController());
//                    }
//                });
                break;
            case PROGRAM_PROPOSAL:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                         if (ProgramRegistry.isCreateNew()) {
                           ProgramRegistry.setCreateNew(false);
                            majorManager = new MajorManager();
                         }
                         callback.exec(majorManager.getMajorProposalController());
//                    }
//                });
                break;
             case PROGRAM_VIEW:
//                GWT.runAsync(new RunAsyncGetView() {
//                   @Override
//                    public void onSuccess() {
                        if (ProgramRegistry.isCreateNew()) {
                            ProgramRegistry.setCreateNew(false);
                            majorManager = new MajorManager();
                        }
                        callback.exec(majorManager.getProgramViewController());
//                    }
//                });
                break;
            case PROGRAM_EDIT:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                    	Application.getApplicationContext().setParentPath("");//Reset the parent path when navigating back
                        if (ProgramRegistry.isCreateNew()) {
                            ProgramRegistry.setCreateNew(false);
                            majorManager = new MajorManager();
                        }
                        callback.exec(majorManager.getProgramEditController());
//                    }
//                });
                break;
            case PROGRAM_SPEC_EDIT:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        if (ProgramRegistry.isCreateNew()) {
                            ProgramRegistry.setCreateNew(false);
                            majorManager = new MajorManager();
                        }
                        callback.exec(majorManager.getProgramSpecEditController());
//                    }
//                });
                break;                
            case PROGRAM_CREATE:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        majorManager = new MajorManager();
                        callback.exec(majorManager.getProgramEditController());
//                    }
//                });
                break;
            case PROGRAM_VERSIONS:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(majorManager.getProgramVersionsController());
//                    }
//                });
                break;
            case CLU_SETS:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(getCluSetsController());
//                    }
//                });
                break;
            case COURSE_CATALOG:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(getBrowseCatalogController());
//                    }
//                });
                break;
            case VARIATION_VIEW:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
            	//Check if key data has been initialized (the program was loaded before the variation)
            	if(majorManager.getProgramModel()!=null && majorManager.getProgramModel().getDefinition()!=null){	
                        callback.exec(majorManager.getVariationViewController());
            	}else{
            		//If it has not yet been initialized, we need to pull some context information and 
            		//navigate to the program view which will initialize program information and then load the variation view.
            		//This is similar to the getFindMajorsWidget() in the CurriculumHomeConfigurer
            		ViewContext viewContext = new ViewContext();
                    viewContext.setId(tokenMap.get("docId"));
                    viewContext.setAttribute(ProgramConstants.VARIATION_ID, tokenMap.get(ProgramConstants.VARIATION_ID));
                    viewContext.setAttribute(ProgramConstants.TYPE, ProgramConstants.VARIATION_TYPE_KEY);
                    viewContext.setIdType(IdType.OBJECT_ID);
                    ProgramRegistry.setCreateNew(true);
                    Application.navigate(AppLocations.Locations.VIEW_PROGRAM.getLocation(), viewContext);
            	}
//                    }
//                });
                break;
            case VARIATION_EDIT:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
            	if(majorManager.getProgramModel()!=null && majorManager.getProgramModel().getDefinition()!=null){	
                    callback.exec(majorManager.getVariationEditController());
            	}else{
            		//If the variation edit is refreshed, bring the user to the program screen first
                	Application.getApplicationContext().setParentPath("");//Reset the parent path when navigating back
                    if (ProgramRegistry.isCreateNew()) {
                        ProgramRegistry.setCreateNew(false);
                        majorManager = new MajorManager();
                    }
                    callback.exec(majorManager.getProgramEditController());
            	}
//                    }
//                });
                break;
            case CORE_PROGRAM_VIEW:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        if (ProgramRegistry.isCreateNew()) {
                            ProgramRegistry.setCreateNew(false);
                            coreManager = new CoreManager();
                        }
                        callback.exec(coreManager.getViewController());
//                    }
//                });
                break;
            case CORE_PROGRAM_EDIT:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(coreManager.getEditController());
//                    }
//                });
                break;
            case CORE_PROGRAM_VERSIONS:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(coreManager.getProgramVersionsController());
//                    }
//                });
                break;
            case BACC_PROGRAM_VIEW:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        if (ProgramRegistry.isCreateNew()) {
                            ProgramRegistry.setCreateNew(false);
                            credentialManager = new CredentialManager();
                        }
                        callback.exec(credentialManager.getBaccViewController());
//                    }
//                });
                break;
            case BACC_PROGRAM_EDIT:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(credentialManager.getBaccEditController());
//                    }
//                });
                break;
            case BACC_PROGRAM_VERSIONS:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(credentialManager.getProgramVersionsController());
//                    }
//                });
                break;
            case LO_CATEGORIES:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(getCategoryManagementController());
//                    }
//                });
                break;
            case DEPENDENCY_ANALYSIS:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(getDependencyAnalysisController());
//                    }
//                });
                break;
            case BROWSE_PROGRAM:
//                GWT.runAsync(new RunAsyncGetView() {
//                    @Override
//                    public void onSuccess() {
                        callback.exec(getBrowseProgramController());
//                    }
//                });
                break;
            default:
                callback.exec(home);
        }
    }


   protected View getCategoryManagementController() {
        return new CategoryManagementView(this, "Learning Objective Categories", LUMViews.LO_CATEGORIES);
    }

    private CourseProposalController getCourseProposalController() {
        courseProposalController = GWT.create(CourseProposalController.class);
        return courseProposalController;
    }
    
    private CourseProposalController getCourseRetirebyProposalController(){
        courseRetireByProposalController = GWT.create(CourseRetireByProposalController.class);
        return courseRetireByProposalController;
    }

    private CourseAdminController getCourseAdminController() {
        courseAdminController = GWT.create(CourseAdminController.class);
        return courseAdminController;
    }
    
    private CourseAdminWithoutVersionController getCourseAdminRetireController(){
        courseAdminRetireController = GWT.create(CourseAdminRetireController.class);
        return courseAdminRetireController;
    }

    private CourseAdminWithoutVersionController getCourseAdminWithoutVersionController(){
        courseAdminWithoutVersionController = GWT.create(CourseAdminWithoutVersionController.class);
        return courseAdminWithoutVersionController;
    }

    private LayoutController getViewCourseController() {
        if (viewCourseController == null) {
            viewCourseController = GWT.create(ViewCourseParentController.class);
        }
        return this.viewCourseController;
    }

    private LayoutController getCluSetsController() {
        manageCluSetsController = GWT.create(CluSetsManagementController.class);
        return manageCluSetsController;
    }

    private LayoutController getBrowseCatalogController() {
        browseCatalogController = new CatalogBrowserController(this);
        return browseCatalogController;
    }

    private LayoutController getDependencyAnalysisController() {
    	dependencyAnalysisController = new DependencyAnalysisController("DependencyAnalaysis");
        return dependencyAnalysisController;
    }
    
    private LayoutController getBrowseProgramController() {
    	browseProgramController = new BrowseProgramController("BrowseProgram");
        return browseProgramController;
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
}
