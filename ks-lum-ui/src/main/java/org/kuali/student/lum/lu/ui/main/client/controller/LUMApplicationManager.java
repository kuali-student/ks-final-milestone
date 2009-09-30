package org.kuali.student.lum.lu.ui.main.client.controller;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DelegatingViewComposite;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.events.LogoutEvent;
import org.kuali.student.common.ui.client.mvc.events.LogoutHandler;
import org.kuali.student.lum.lu.ui.course.client.configuration.history.KSHistory;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalController;
import org.kuali.student.lum.lu.ui.course.client.configuration.viewclu.ViewCluController;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;
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

    KSHistory history;
    
    private final View homeMenuView = new DelegatingViewComposite(this, new HomeMenuController());

    private Controller cluProposalController = null;
    private DelegatingViewComposite createCourseView;
    private DelegatingViewComposite viewCourseView;
        
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
                    String selectedId = selectedIds.get(0);
                    
                    if (event.getViewType().equals(LUMViews.EDIT_COURSE_PROPOSAL)){
                        initCluProposalViewFromProposalId(selectedId);                        
                    }

                    if (viewCourseView == null && event.getViewType().equals(LUMViews.VIEW_COURSE)){
                        viewCourseView = new DelegatingViewComposite(LUMApplicationManager.this, new ViewCluController(selectedId));
                    }
     
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
                initBlankCluProposalView();
                return createCourseView;
            case EDIT_COURSE_PROPOSAL:
                //View setup should already be handled
                return createCourseView;
            case VIEW_COURSE:
                if (viewCourseView == null){
                    viewCourseView = new DelegatingViewComposite(this, new ViewCluController(null));
                }
                //((LUCreateUpdateView)courseView).addLayoutToHistory(history, LUMViews.CREATE_COURSE); 
                return viewCourseView;
            default:
                return null;
        }
    }

    private View initBlankCluProposalView(){
        if (cluProposalController == null){
            cluProposalController = new CluProposalController(); 
            createCourseView = new DelegatingViewComposite(LUMApplicationManager.this, cluProposalController);            
        }
        ((CluProposalController)cluProposalController).clear();
        
        return createCourseView;
    }
    
    private View initCluProposalViewFromProposalId(String proposalId){
        initBlankCluProposalView();
        ((CluProposalController)cluProposalController).setProposalId(proposalId);
        return createCourseView;
    }
    
    private View initCluProposalViewFromDocId(String docId){
        initBlankCluProposalView();
        ((CluProposalController)cluProposalController).setDocId(docId);
        return createCourseView;        
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
                        initCluProposalViewFromDocId(docId);
                        showView(LUMViews.EDIT_COURSE_PROPOSAL);
                    }
                
                });
            }else{
                initCluProposalViewFromDocId(docId);
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
}
