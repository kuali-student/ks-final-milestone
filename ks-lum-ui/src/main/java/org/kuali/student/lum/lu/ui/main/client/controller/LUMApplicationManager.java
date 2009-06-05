package org.kuali.student.lum.lu.ui.main.client.controller;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUCreateUpdateView;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUDictionaryManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.LULayoutFactory;
import org.kuali.student.lum.lu.ui.course.client.configuration.history.KSHistory;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;
import org.kuali.student.lum.lu.ui.home.client.view.HomeMenuController;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateEvent;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class LUMApplicationManager extends Controller{

    private final SimplePanel viewPanel = new SimplePanel();

    private final View homeMenu = new HomeMenuController(this);
    //private CourseProposalManager createCourse = new CourseProposalManager(this);
    KSHistory history;

    private View courseView ;

    private LuRpcServiceAsync luRpcServiceAsync = GWT.create(LuRpcService.class);

    public LUMApplicationManager(){
        super();
        loadDictionary();
        history = new KSHistory(this);
        super.initWidget(viewPanel);
    }

    protected void onLoad() {
        addApplicationEventHandler(ChangeViewStateEvent.TYPE, new ChangeViewStateHandler() {
            public void onViewStateChange(ChangeViewStateEvent event) {
                showView(event.getViewType());  
            }
        });
    }

    public enum LUMViews {
        HOME_MENU, CREATE_COURSE
    }

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        switch ((LUMViews) viewType) {
            case HOME_MENU:
                return homeMenu;
            case CREATE_COURSE:
                /*
                createCourse.setCourseProposalType(CourseProposalType.NEW_COURSE);                
                return createCourse;
                 */
                courseView = new LUCreateUpdateView(LUConstants.LU_TYPE_CREDIT_COURSE, LUConstants.LU_STATE_PROPOSED);

                ((LUCreateUpdateView)courseView).addLayoutToHistory(history, LUMViews.CREATE_COURSE);
                return courseView;
            default:
                return null;
        }
    }

    @Override
    protected void hideView(View view) {
        viewPanel.clear();

    }

    @Override
    protected void renderView(View view) {
        // TODO Bsmith - THIS METHOD NEEDS JAVADOCS
        viewPanel.setWidget((Composite)view);
    }

    @Override
    public void showDefaultView() {
        this.showView(LUMViews.HOME_MENU);
    }

    public Class<? extends Enum<?>> getViewsEnum() {
        return LUMViews.class;
    }        

    private void loadDictionary() {
        
        //  If msg load OK, load proposalInfo structure
        luRpcServiceAsync.getObjectStructure(LUDictionaryManager.STRUCTURE_PROPOSAL_INFO, new AsyncCallback<ObjectStructure>(){
            public void onFailure(Throwable caught) {
                throw new RuntimeException("Unable to load proposalInfo object structure", caught);                
            }

            @Override
            public void onSuccess(ObjectStructure result) {
                
                LUDictionaryManager.getInstance().loadStructure(result);

                //  If proposal Info structure load OK, load cluInfo structure                      
                luRpcServiceAsync.getObjectStructure(LUDictionaryManager.STRUCTURE_CLU_INFO, new AsyncCallback<ObjectStructure>(){
                    public void onFailure(Throwable caught) {
                        throw new RuntimeException("Unable to load cluInfo object structure", caught);                
                    }

                    @Override
                    public void onSuccess(ObjectStructure result) {
                        LUDictionaryManager.getInstance().loadStructure(result);

                    }
                }
                );


            }
        }
        );

    }

}
