package org.kuali.student.ui.personidentity.client.controller;

import java.util.List;
import java.util.Vector;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.ui.personidentity.client.ModelState;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCreateInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCriteria;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;
import org.kuali.student.ui.personidentity.client.service.PersonIdentityService;
import org.kuali.student.ui.personidentity.client.service.PersonIdentityServiceAsync;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;
import org.kuali.student.ui.personidentity.client.view.AdminStudentTab;
import org.kuali.student.ui.personidentity.client.view.PersonSearchResultPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class PersonIdentityController {

    static Controller  pController = null;
    static Model<GwtPersonInfo> model = null;
    static AdminStudentTab adminStudentTab;
    
    private static PersonIdentityServiceAsync piService = null;
                    
    protected static Widget   baseWidget = null;
    
    public static void setAdminStudentTab(AdminStudentTab tab) {
        adminStudentTab = tab;
    }
    
    public static void setController(Controller con) {
        pController = con;
        model = (Model<GwtPersonInfo>)con.getModel(GwtPersonInfo.class);
    }
                
    protected static AsyncCallback updateModelPersonCallback = new  AsyncCallback()
    {
          public void onFailure(Throwable caught)
          {      
              Window.alert(caught.getMessage());
          }
          public void onSuccess(Object result)
          {
             ModelState.getInstance().setCurrPerson((GwtPersonInfo)result);
             model.update((GwtPersonInfo)result);
             adminStudentTab.selectTab(0);
             adminStudentTab.getPersonTab().getPersonDetailsWidget().setEditMode(false);
             adminStudentTab.getPersonTab().displayDetailsResultsTab();
                      
          }
    };
    
    protected static AsyncCallback  updateModelSearchResultCallback = new  AsyncCallback()
    {
          public void onFailure(Throwable caught)
          {      
              Window.alert(caught.getMessage());
          }
          public void onSuccess(Object result)
          {
             ModelState.getInstance().setSearchResult((List<GwtPersonInfo>)result);                   
          }
    };
    
    
    public static synchronized PersonIdentityServiceAsync getService(){
        if(piService == null){
            piService =         PersonIdentityService.Util.getInstance();
        }
        return piService;
    }
    
    public  static void createPerson(GwtPersonCreateInfo pInfo, List<String> personTypeKeys){
        
        
        getService().createPerson(pInfo, personTypeKeys, new  AsyncCallback()
                {
              public void onFailure(Throwable caught)
              {      
                  Window.alert(caught.getMessage());
              }
              public void onSuccess(Object result)
              {
                 
                  fetchFullPersonInfo((String)result, updateModelPersonCallback);   
                  
                  
                  adminStudentTab.selectTab(0);
                  adminStudentTab.getPersonTab().getPersonCreateWidget().clear();
                  adminStudentTab.getPersonTab().getPersonCreateWidget().setEditable(true);
                  
              }
            });
    }
    
    public static void createPerson(GwtPersonCreateInfo pInfo, List<String> personTypeKeys, AsyncCallback callback ){
        PersonIdentityServiceAsync piService = PersonIdentityController.getService();
        
        getService().createPerson(pInfo, personTypeKeys, callback);
    }
    
    public static void fetchFullPersonInfo(String personId, AsyncCallback callback){
        PersonIdentityServiceAsync piService = PersonIdentityController.getService();
        
        getService().fetchFullPersonInfo(personId, callback);
    }
    
    public static void serachForPeople(GwtPersonCriteria pCriteria, AsyncCallback callback){
        getService().searchForPeople(pCriteria, callback);
    }
    
    public static void serachForPeople(GwtPersonCriteria pCriteria){
        
        getService().searchForPeople(pCriteria, new  AsyncCallback(){

            
            public void onFailure(Throwable caught) {
                 Window.alert(caught.getMessage());
                
            }
            public void onSuccess(Object result) {
                updateSearchResults((List<GwtPersonInfo>)result);
               
            }});
    }
    
    public static void serachForPeople(String pCriteria){
        
        getService().searchForPeople(pCriteria, new  AsyncCallback(){

            
            public void onFailure(Throwable caught) {
                 Window.alert(caught.getMessage());
                
            }
            public void onSuccess(Object result) {
                updateSearchResults((List<GwtPersonInfo>)result);                
           
            }});
    }
    
    //New method to display what you were searching for in a label
    public static void updateSearchResults(String criteria, List<GwtPersonInfo> result){
        ModelState.getInstance().setSearchResult(result);
        model.clear();
        for (GwtPersonInfo g : result) {
            model.add(g);
        }
        adminStudentTab.selectTab(0);
        adminStudentTab.getPersonTab().displaySearchResultsTab();

        if(model.items().isEmpty())
        {
        	adminStudentTab.getPersonTab().getPersonSearchWidget().getSearchResultPanel().setSearchLabel(
        			ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("noSearchResults") 
        			+" \"" + criteria +"\"");
        	adminStudentTab.getPersonTab().getPersonSearchWidget().getSearchResultPanel().setTableVisibility(false);
        }
        else if(criteria.equals(""))
        {
        	//message
        	adminStudentTab.getPersonTab().getPersonSearchWidget().getSearchResultPanel().setSearchLabel(
        			ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("allPeople")
        			+ " - " + model.items().size()+ " "
        			+ ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("peopleItems"));
        	//table visible
        	adminStudentTab.getPersonTab().getPersonSearchWidget().getSearchResultPanel().setTableVisibility(true);
        }
        else
        {
        	//message
        	adminStudentTab.getPersonTab().getPersonSearchWidget().getSearchResultPanel().setSearchLabel(
        			ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("searchResults") 
        			+" \"" + criteria +"\""
        			+ " - " + model.items().size()+ " "
        			+ ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("peopleItems"));
        	//table visible
        	adminStudentTab.getPersonTab().getPersonSearchWidget().getSearchResultPanel().setTableVisibility(true);
        }
        
    }
    
    public static void updateSearchResults(List<GwtPersonInfo> result){
        ModelState.getInstance().setSearchResult(result);
        model.clear();
        for (GwtPersonInfo g : result) {
            model.add(g);
        }
        adminStudentTab.selectTab(0);
        adminStudentTab.getPersonTab().displaySearchResultsTab();
        
    }
    
    public static void serachForPeople(final String pCriteria, final AsyncCallback async){
        getService().searchForPeople(pCriteria, async);
    }
    
    
    public static void updatePerson(final GwtPersonInfo pInfo){
        getService().updatePerson(pInfo, new AsyncCallback(){

            
            public void onFailure(Throwable caught) {
                 Window.alert(caught.getMessage());
                
            }

            
            public void onSuccess(Object result) {
                model.update(pInfo);
                ModelState.getInstance().setCurrPerson(pInfo);
                List<GwtPersonInfo> l = new Vector<GwtPersonInfo>();
                
                l.addAll(ModelState.getInstance().getSearchResult());
                
                
                if(l != null && l.isEmpty() == false){
                    for(int i =0; i< l.size(); i++){
                        
                        GwtPersonInfo pi = l.get(i);
                        if(pi.getPersonId().equalsIgnoreCase(pInfo.getPersonId())){
                            l.set(i, pInfo);
                            ModelState.getInstance().setSearchResult(l);
                        }
                    }
                }
                
                
            }});
        
    }
    
    
    public static void fetchAllPeople(){
        PersonIdentityServiceAsync piService = PersonIdentityController.getService();
        
        //TODO:
        
    }
    
    public static void viewPersonDetailsScreen(){
        adminStudentTab.selectTab(0);
        adminStudentTab.getPersonTab().displayDetailsResultsTab();
    }
    
    public static void setView(Widget w){
        baseWidget = w;
    }
    
    public static  Widget getView(){
        return baseWidget;
    }
    
    public static void deletePerson(final String p_id){
        getService().deletePerson(p_id, new AsyncCallback()
        {           
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());                
            }           
            public void onSuccess(Object result) {
                model.remove(model.get(p_id));
                pController.getEventDispatcher().fireEvent(PersonSearchResultPanel.PERSON_SELECTED, null);
                adminStudentTab.selectTab(0);
                adminStudentTab.getPersonTab().displaySearchResultsTab();
            }
            
        });
    }
    
    public static void validate(GwtPersonCreateInfo pInfo, AsyncCallback callback){
        getService().validate(pInfo, callback);
    }
    
}
