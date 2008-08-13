/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.widgets.BusyIndicator;
import org.kuali.student.commons.ui.widgets.BusyWidgetShade;
import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.controller.PersonIdentityController;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCriteria;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Garey
 *
 */
public class SearchWidget extends Composite {

	public static abstract class SearchWidgetSearch extends MVCEvent {}
	public static abstract class CourseSearchEvent extends SearchWidgetSearch {}
	public static abstract class PersonSearchEvent extends SearchWidgetSearch {}
    public static final CourseSearchEvent COURSE_SEARCH = GWT.create(CourseSearchEvent.class);
    public static final PersonSearchEvent PERSON_SEARCH = GWT.create(PersonSearchEvent.class);
    final SearchWidget me = this;
    
	HorizontalPanel	root		= null;
	SearchListBox	listBox 	= null;
	TextBox			searchBox 	= null;
	
	Button			btnSearch 	= null;
	SuggestBox		suggestBox = null;
	
	ClickListener	clkSearch = new ClickListener(){	
		public void onClick(Widget sender) {
		    BusyWidgetShade.shade(btnSearch);
			String lValue = listBox.getValue(listBox.getSelectedIndex());
			if("course".equals(lValue)){
				GwtLuiCriteria cCriteria = new GwtLuiCriteria();
				cCriteria.setDescription("%" +suggestBox.getText()+ "%");
				
				LearningUnitController.searchForLuis(cCriteria, new AsyncCallback(){               
                    public void onFailure(Throwable caught) {
                        BusyWidgetShade.unshade(btnSearch); 
                        Window.alert(caught.getMessage());                            
                    }

                    public void onSuccess(Object result) {
                        LearningUnitController.updateSearchResults(suggestBox.getText(), (List<GwtLuiInfo>)result);
                        BusyWidgetShade.unshade(btnSearch);
                        Controller c = MVC.findParentController(me);
                        c.getEventDispatcher().fireEvent(COURSE_SEARCH);
                    }});
			}
			if("people".equals(lValue) ){
			    BusyWidgetShade.shade(btnSearch);				
				PersonIdentityController.serachForPeople(suggestBox.getText() + "%", new AsyncCallback(){

    
                    public void onFailure(Throwable caught) {
                        BusyWidgetShade.unshade(btnSearch); 
                        Window.alert(caught.getMessage());                       
                    }
                  
                    public void onSuccess(Object result) {
                        PersonIdentityController.updateSearchResults(suggestBox.getText(), (List<GwtPersonInfo>)result);
                        BusyWidgetShade.unshade(btnSearch);
                        Controller c = MVC.findParentController(me);
                        c.getEventDispatcher().fireEvent(PERSON_SEARCH);
                    }});	
			}			
		}		
	};
	
	boolean loaded = false;
	
	/**
	 * 
	 */
	public SearchWidget() {
		root 		= new HorizontalPanel();
		listBox 	= new SearchListBox();
		searchBox	= new TextBox();
		btnSearch 	= new Button();	

		initWidget(root);
	}
	protected void onLoad() {
		if (!loaded) {
			loaded = true;
			
			
			SuggestOracle oracle = new SuggestOracle() {
			      public void requestSuggestions(final Request request, final Callback callback) {
			        String query = request.getQuery();
			        String lValue = listBox.getValue(listBox.getSelectedIndex());
			        
			        
			        		        		        
			        if("course".equals(lValue)){
						GwtLuiCriteria cCriteria = new GwtLuiCriteria();
						cCriteria.setDescription("%" +query+ "%");
		        		ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.BEGIN_TASK);
						LearningUnitController.searchForLuis(cCriteria, new AsyncCallback() {
				        	
				        	public void onFailure(Throwable caught) {
				        		ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
				        		Window.alert(caught.getMessage());
								
							}
							public void onSuccess(Object result) {
								List<GwtLuiInfo> cInfo = (List<GwtLuiInfo>)result;
								Iterator<GwtLuiInfo> it = cInfo.iterator();
								Collection suggestions = new ArrayList();
								
								while(it.hasNext()){
									final GwtLuiInfo crsInfo = it.next();
									
									suggestions.add(new SuggestOracle.Suggestion(){					
										public String getDisplayString() {
											return crsInfo.getCluDisplay().getCluShortName();
										}
	
										
										public String getReplacementString() {
											return crsInfo.getCluDisplay().getCluShortName();
										}
							        	
							        });
								}
								callback.onSuggestionsReady(request, new Response(suggestions));
				        		ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
							}		        		  		           		           
				          });
					}
					if("people".equals(lValue) && false){
						if(query != null && query.length() > 0){
				        	query += "%";
				        }
					ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.BEGIN_TASK);
			        PersonIdentityController.serachForPeople(query, new AsyncCallback() {
			        			        			        			        	
			        	public void onFailure(Throwable caught) {
			        		ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
			        		Window.alert(caught.getMessage());
							
						}
						public void onSuccess(Object result) {
							List<GwtPersonInfo> pInfo = (List<GwtPersonInfo>)result;
							Iterator<GwtPersonInfo> it = pInfo.iterator();
							Collection suggestions = new ArrayList();
							
							while(it.hasNext()){
								final GwtPersonInfo perInfo = it.next();
								
								suggestions.add(new SuggestOracle.Suggestion(){					
									public String getDisplayString() {
										return perInfo.getPreferredName().getGivenName() 				                  	
					                  	+ " " 
					                  	+ perInfo.getPreferredName().getSurname();
									}
	
									
									public String getReplacementString() {
										return perInfo.getPreferredName().getGivenName() 				               
					                  	+ " " 
					                  	+ perInfo.getPreferredName().getSurname();
									}
						        	
						        });
							}
							callback.onSuggestionsReady(request, new Response(suggestions));
				        	ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
						}		        		  		           		           
			          });		     
					}
			      }
			        
			};
			
			suggestBox = new SuggestBox(oracle);
			
			suggestBox.setStyleName("KS-TextBox");
			listBox.setStyleName("KS-ListBox");
			btnSearch.setStyleName("KS-Button");

			Messages messages = ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages();
			listBox.addItem(messages.get("people"), "people");
			listBox.addItem(messages.get("courses"), "course");
			
			
			btnSearch.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("search"));
			btnSearch.addClickListener(clkSearch);
			
			
			root.add(listBox);
			//root.add(searchBox);
			root.add(suggestBox);
			root.add(btnSearch);
		}
	}
	
	
	protected void setupOracle(){
		
	}
	protected void arrangeWidget(){
		
		root.addStyleName("KS-SearchWidget-BasePanel");
		
		//listBox.addItem("People", "people");
//		listBox.addItem(I18N.i18nConstant.people(), "people");
		listBox.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("people"), "people");
		//listBox.addItem("Courses", "course");
		listBox.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("course"), "course");
		
		btnSearch.setText("Search");
		btnSearch.addClickListener(clkSearch);
		
		
		root.add(listBox);
		root.add(searchBox);
		root.add(btnSearch);
		
		
	}

}
