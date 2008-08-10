package org.kuali.student.ui.personidentity.client.controller;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.model.lu.LuModelState;
import org.kuali.student.ui.personidentity.client.service.LearningUnitAppService;
import org.kuali.student.ui.personidentity.client.service.LearningUnitAppServiceAsync;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;
import org.kuali.student.ui.personidentity.client.view.AdminStudentTab;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class LearningUnitController {
	
    static Controller   luController = null;
	static Model<GwtLuiInfo> model = null;
	static AdminStudentTab adminStudentTab;
	
	
	public static final String SERVICE_URI = "LearningUnitAppService";
	
	private static LearningUnitAppServiceAsync service = null;
	
	protected static Widget baseWidget = null;
	
	public static void setAdminStudentTab(AdminStudentTab tab) {
	    adminStudentTab = tab;
	}
	
	public static void setController(Controller con) {
	    luController = con;
        model = (Model<GwtLuiInfo>)con.getModel(GwtLuiInfo.class);
    }
	
	public static synchronized LearningUnitAppServiceAsync getService(){
		if(service == null){
			service = LearningUnitAppService.Util.getInstance();			
		}
		return service;
	}
	
	
	public static void findClusForLuType(String luTypeKey, AsyncCallback callback){
		getService().findClusForLuType(luTypeKey, callback);
	}
	
	public static void findLuTypes(AsyncCallback callback){
		getService().findLuTypes(callback);		
	}
	
	public static void findLuisForClu(String cluId, String atpId, AsyncCallback callback){
		getService().findLuisForClu(cluId, atpId, callback);
	}
	public static void fetchClu(String cluId, AsyncCallback callback){
		getService().fetchClu(cluId, callback);
	}
	
	public static void searchForClus(GwtCluCriteria criteria, AsyncCallback callback){
		getService().searchForClus(criteria, callback);
	}
	public static void searchForLuis(GwtLuiCriteria criteria, AsyncCallback async){
		
		getService().searchForLuiInfo(criteria, async);
	}
	
	public static  void searchForLuis(GwtLuiCriteria criteria){
		
		getService().searchForLuiInfo(criteria, new AsyncCallback<List<GwtLuiInfo>>(){
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			public void onSuccess(List<GwtLuiInfo> result) {
			    updateSearchResults(result);				
			}});
	}
	
	public static void updateSearchResults(List<GwtLuiInfo> luiList){
	    model.clear();
        for(GwtLuiInfo info : luiList){
            model.add(info);
        }
        adminStudentTab.selectTab(1);
        adminStudentTab.getCourseTab().displaySearchResultsTab();
	}
	
	public static void updateSearchResults(String criteria, List<GwtLuiInfo> luiList){
	    model.clear();
        for(GwtLuiInfo info : luiList){
            model.add(info);
        }
        adminStudentTab.selectTab(1);
        adminStudentTab.getCourseTab().displaySearchResultsTab();
        
        if(model.items().isEmpty())
        {
        	adminStudentTab.getCourseTab().getCourseSearchPanel().getSrPanel().setSearchLabel(
        			ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("noSearchResults") 
        			+ " \"" + criteria + "\"");
        	adminStudentTab.getCourseTab().getCourseSearchPanel().getSrPanel().setTableVisibility(false);
        }
        else if(criteria.equals(""))
        {
        	adminStudentTab.getCourseTab().getCourseSearchPanel().getSrPanel().setSearchLabel(
        			ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("allCourses")
        			+ " - " + model.items().size()+ " "
        			+ ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("courseItems"));
        	adminStudentTab.getCourseTab().getCourseSearchPanel().getSrPanel().setTableVisibility(true);
        }
        else
        {
        	adminStudentTab.getCourseTab().getCourseSearchPanel().getSrPanel().setSearchLabel(
        			ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("searchResults") 
        			+ " \"" + criteria + "\""
        			+ " - " + model.items().size()+ " "
        			+ ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("courseItems"));
        	adminStudentTab.getCourseTab().getCourseSearchPanel().getSrPanel().setTableVisibility(true);
        }
	}
	
	public static void displayCourseDetails(){								
				adminStudentTab.selectTab(1);
				adminStudentTab.getCourseTab().displayCourseDetailsTab();						
	}
	
	public static void setCurrentLui(String luiId){
		getService().fetchLui(luiId, new AsyncCallback(){

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			public void onSuccess(Object result) {
				LuModelState.getInstance().setCurrLui((GwtLuiInfo)result);
			}});
	}
	
	public static void setView(Widget w){
		baseWidget = w;
	}
	
	public static Widget getView(){
		return baseWidget;
	}
	
}
