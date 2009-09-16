package org.kuali.student.lum.lu.ui.main.client.controller;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DelegatingViewComposite;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.events.LogoutEvent;
import org.kuali.student.common.ui.client.mvc.events.LogoutHandler;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUCreateUpdateView;
import org.kuali.student.lum.lu.ui.course.client.configuration.history.KSHistory;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalController;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;
import org.kuali.student.lum.lu.ui.home.client.view.HomeMenuController;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateEvent;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class LUMApplicationManager extends Controller{
   
    private final SimplePanel viewPanel = new SimplePanel();

    private final View homeMenuView = new DelegatingViewComposite(this, new HomeMenuController());
    KSHistory history;

    private View createCourseView;
//    private View viewCourseView;
    private LUCreateUpdateView modifyView;
    
    private Model<CluProposal> cluModel = new Model<CluProposal>();

    private LuRpcServiceAsync luRpcServiceAsync = GWT.create(LuRpcService.class);
    private CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);
    
    public LUMApplicationManager(){
        super();
        history = new KSHistory(this);
        super.initWidget(viewPanel);
    }

    protected void onLoad() {
        addApplicationEventHandler(ChangeViewStateEvent.TYPE, new ChangeViewStateHandler() {
            public void onViewStateChange(ChangeViewStateEvent event) {
                //FIXME: This is very hacky
                if (event.getEventSource() != null && event.getEventSource() instanceof SelectionEvent){                    
                    List<String> selectedIds = (List<String>)((SelectionEvent)event.getEventSource()).getSelectedItem();
                    if (modifyView == null){
                        modifyView = new LUCreateUpdateView(LUMApplicationManager.this, LUConstants.LU_TYPE_CREDIT_COURSE, LUConstants.LU_STATE_PROPOSED, false);
                    }
                    modifyView.setId(selectedIds.get(0));
//                    if (viewCourseView == null){
//                        viewCourseView = new DelegatingViewComposite(LUMApplicationManager.this, new ViewCluController(selectedIds.get(0), LUConstants.LU_TYPE_CREDIT_COURSE, LUConstants.LU_STATE_PROPOSED));
//                    }
     
                }
                showView(event.getViewType());  
            }
        });
        addApplicationEventHandler(LogoutEvent.TYPE, new LogoutHandler() {
            public void onLogout(LogoutEvent event) {
                Window.Location.assign("/j_spring_security_logout");
            }
        });
    }

    public enum LUMViews {
        HOME_MENU, CREATE_COURSE, EDIT_COURSE_PROPOSAL, VIEW_COURSE
    }

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        switch ((LUMViews) viewType) {
            case HOME_MENU:
                return homeMenuView;
            case CREATE_COURSE:
                if (createCourseView == null){
//                  createCourseView = new LUCreateUpdateView(LUMApplicationManager.this, LUConstants.LU_TYPE_CREDIT_COURSE, LUConstants.LU_STATE_PROPOSED);
                	createCourseView = new DelegatingViewComposite(this, new CluProposalController());
                }
                //((LUCreateUpdateView)courseView).addLayoutToHistory(history, LUMViews.CREATE_COURSE); 
                return createCourseView;
            case EDIT_COURSE_PROPOSAL:
                if (createCourseView == null){
//                  createCourseView = new LUCreateUpdateView(LUMApplicationManager.this, LUConstants.LU_TYPE_CREDIT_COURSE, LUConstants.LU_STATE_PROPOSED);
                	createCourseView = new DelegatingViewComposite(this, new CluProposalController());
                }
              //((LUCreateUpdateView)courseView).addLayoutToHistory(history, LUMViews.CREATE_COURSE); 
              return createCourseView;
//            case VIEW_COURSE:
//                if (viewCourseView == null){
//                    viewCourseView = new DelegatingViewComposite(this, new ViewCluController(null, LUConstants.LU_TYPE_CREDIT_COURSE, LUConstants.LU_STATE_PROPOSED));
//                }
//                //((LUCreateUpdateView)courseView).addLayoutToHistory(history, LUMViews.CREATE_COURSE); 
//                return viewCourseView;
            default:
                return null;
        }
    }

    //Accessor for get view
    public <V extends Enum<?>> View getControllerView(V viewType){
        return this.getView(viewType);
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
        final String docId=Window.Location.getParameter("docId");
        String backdoorId=Window.Location.getParameter("backdoorId");
        final LUMApplicationManager thisManager = this;
        if(docId!=null){
            if(backdoorId!=null){
                cluProposalRpcServiceAsync.loginBackdoor(backdoorId, new AsyncCallback<Boolean>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(Boolean result) {
                        if(!result){
                            Window.alert("Error with backdoor login");
                        }
                        if (createCourseView == null){
                            createCourseView = new DelegatingViewComposite(thisManager, new CluProposalController(docId));
                        }
                        showView(LUMViews.EDIT_COURSE_PROPOSAL);
                    }
                
                });
            }else{
                if (createCourseView == null){
                    createCourseView = new DelegatingViewComposite(thisManager, new CluProposalController(docId));
                }
                this.showView(LUMViews.EDIT_COURSE_PROPOSAL);
            }
        }
        else{
            this.showView(LUMViews.HOME_MENU);
        }
    }

    public Class<? extends Enum<?>> getViewsEnum() {
        return LUMViews.class;
    }        

    public void setCluId(String id){
       // this.courseView.setId(id);
    }
}
