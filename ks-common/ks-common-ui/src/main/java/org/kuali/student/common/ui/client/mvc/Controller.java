/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.breadcrumb.BreadcrumbSupport;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.mvc.history.HistorySupport;
import org.kuali.student.common.ui.client.mvc.history.NavigationEvent;
import org.kuali.student.common.ui.client.reporting.ReportExport;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.client.service.GwtExportRpcService;
import org.kuali.student.common.ui.client.service.GwtExportRpcServiceAsync;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.client.util.ExportUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * Abstract Controller composite. Provides basic controller operations, and defines abstract methods that a composite must
 * implement in order to be a controller.
 * 
 * @author Kuali Student Team
 */
public abstract class Controller extends Composite implements HistorySupport, BreadcrumbSupport, ReportExport{
	public static final Callback<Boolean> NO_OP_CALLBACK = new Callback<Boolean>() {
		@Override
		public void exec(Boolean result) {
			// do nothing
		}
	};
	
	// TODO Nina how do you do loggin in GWT?  
//	final static Logger logger = Logger.getLogger(Controller.class);
    protected Controller parentController = null;
    private View currentView = null;
    private Enum<?> currentViewEnum = null;
    private String defaultModelId = null;
    protected ViewContext context = new ViewContext();
    private final Map<String, ModelProvider<? extends Model>> models = new HashMap<String, ModelProvider<? extends Model>>();
    private boolean fireNavEvents = true;
    private HandlerManager applicationEventHandlers = new HandlerManager(this);
    private GwtExportRpcServiceAsync reportExportRpcService = GWT.create(GwtExportRpcService.class);
    
    protected Controller() {
    }
    
    /**
     * Simple Version of showView, no callback
     * @param <V>
     * 			view enum type
     * @param viewType
     * 			enum value representing the view to show
     */
    public <V extends Enum<?>> void showView(final V viewType){
    	this.showView(viewType, NO_OP_CALLBACK);
    }
    

    
    /**
     * Directs the controller to display the specified view. The parameter must be an enum value, based on an enum defined in
     * the controller implementation. For example, a "Search" controller might have an enumeration of: <code>
     *  public enum SearchViews {
     *      SIMPLE_SEARCH,
     *      ADVANCED_SEARCH,
     *      SEARCH_RESULTS
     *  }
     * </code> The implementing class must define a getView(V viewType) method that will cast the generic enum to the view
     * specific enum.
     * 
     * @param <V>
     *            view enum type
     * @param viewType
     *            enum value representing the view to show
     * @param onReadyCallback the callback to invoke when the method has completed execution
     * @return false if the current view cancels the operation
     */
    public <V extends Enum<?>> void showView(final V viewType, final Callback<Boolean> onReadyCallback) {
        GWT.log("showView " + viewType.toString(), null);
        getView(viewType, new Callback<View>(){

			@Override
			public void exec(View result) {
				View view = result;
				if (view == null) {
		        	onReadyCallback.exec(false);
		            //throw new ControllerException("View not registered: " + viewType.toString());
		        }
		        beginShowView(view, viewType, onReadyCallback);
				
			}});
    }
    
    private <V extends Enum<?>> void beginShowView(final View view, final V viewType, final Callback<Boolean> onReadyCallback){
    	beforeViewChange(viewType, new Callback<Boolean>(){

			@Override
			public void exec(Boolean result) {
				if(result){
					 boolean requiresAuthz = (view instanceof RequiresAuthorization) && ((RequiresAuthorization)view).isAuthorizationRequired(); 
						
				        if (requiresAuthz){
				        	ViewContext tempContext = new ViewContext();
				        	if(view instanceof LayoutController){
				        		tempContext = ((LayoutController) view).getViewContext();
				        	}                 
				        	else{
				        		tempContext = view.getController().getViewContext();
				        	}
				        	
				        	PermissionType permType = (tempContext != null) ? tempContext.getPermissionType() : null;
				        	if (permType != null) {
				        		GWT.log("Checking permission type '" + permType.getPermissionTemplateName() + "' for view '" + view.toString() + "'", null);
				            	//A callback is required if async rpc call is required for authz check
					        	((RequiresAuthorization)view).checkAuthorization(permType, new AuthorizationCallback(){
									public void isAuthorized() {
										finalizeShowView(view, viewType, onReadyCallback);
									}
				
									public void isNotAuthorized(String msg) {
										Window.alert(msg);
										onReadyCallback.exec(false);					
									}        		
					        	});
				        	}
				        	else {
				        		GWT.log("Cannot find PermissionType for view '" + view.toString() + "' which requires authorization", null);
				            	finalizeShowView(view, viewType, onReadyCallback);
				        	}
				        } else {
				    		GWT.log("Not Requiring Auth.", null);
				        	finalizeShowView(view, viewType, onReadyCallback);
				        }
				}
				else{
					onReadyCallback.exec(false);
				}
				
			}
		});
    }
    
    private <V extends Enum<?>> void finalizeShowView(final View view, final V viewType, final Callback<Boolean> onReadyCallback){
        if ((currentView == null) || currentView.beforeHide()) {
			view.beforeShow(new Callback<Boolean>() {
				@Override
				public void exec(Boolean result) {
					if (!result) {
						GWT.log("showView: beforeShow yielded false " + viewType, null);
			        	onReadyCallback.exec(false);
					} else {
			        	if (currentView != null) {
			                hideView(currentView);
			            }
			            
			            currentViewEnum = viewType;
			            currentView = view;
			            GWT.log("renderView " + viewType.toString(), null);
			            if(fireNavEvents){
			            	fireNavigationEvent();
			            }
			            renderView(view);
			        	onReadyCallback.exec(true);
			        	
					}
				}
			});
        } else {
        	onReadyCallback.exec(false);
            GWT.log("Current view canceled hide action", null);
        }    	
    }

    protected void fireNavigationEvent() {
        //DeferredCommand.addCommand(new Command() {
           // @Override
            //public void execute() {
                fireApplicationEvent(new NavigationEvent(Controller.this));
            //}
        //});
    }
    
    /**
     * Returns the currently displayed view
     * 
     * @return the currently displayed view
     */
    public View getCurrentView() {
        return currentView;
    }
    
    public Enum<?> getCurrentViewEnum() {
        return currentViewEnum;
    }

    public void setCurrentViewEnum(Enum<?> currentViewEnum) {
        this.currentViewEnum = currentViewEnum;
    }

    /**
     * Sets the controller's parent controller. In most cases, this can be omitted as the controller will be automatically
     * detected via the DOM in cases where it is not specified. The only time that the controller needs to be manually set is
     * in cases where the logical controller hierarchy differs from the physical DOM hierarchy. For example, if a nested
     * controller is rendered in a PopupPanel, then the parent controller must be set manually using this method
     * 
     * @param controller
     *            the parent controller
     */
    public void setParentController(Controller controller) {
        parentController = controller;
    }

    /**
     * Returns the parent controller. If the current parent controller is not set, then the controller will attempt to
     * automatically locate the parent controller via the DOM.
     * 
     * @return
     */
    public Controller getParentController() {
        if (parentController == null) {
            parentController = Controller.findController(this);
        }
        return parentController;
    }

    /**
     * Attempts to find the parent controller of a given widget via the DOM
     * 
     * @param w
     *            the widget for which to find the parent controller
     * @return the controller, or null if not found
     */
    public static Controller findController(Widget w) {
        Controller result = null;
        while (true) {
            w = w.getParent();
            if (w == null) {
                break;
            } else if (w instanceof Controller) {
                result = (Controller) w;
                break;
            } else if (w instanceof View) {
                // this is in the event that a parent/child relationship is broken by a view being rendered in a lightbox,
                // etc
                result = ((View) w).getController();
                break;
            }
        }
        return result;
    }

    /**
     * Called by child views and controllers to request a model reference. By default it delegates calls to the parent
     * controller if one is found. Override this method to declare a model local to the controller. Always make sure to
     * delegate the call to the superclass if the requested type is not one which is defined locally. For example: <code>
     * 
     * @Override
     * @SuppressWarnings("unchecked") public void requestModel(Class<? extends Idable> modelType, ModelRequestCallback
     *                                callback) { if (modelType.equals(Address.class)) { callback.onModelReady(addresses); }
     *                                else { super.requestModel(modelType, callback); } } </code>
     * @param modelType
     * @param callback
     */
    @SuppressWarnings("unchecked")
    public void requestModel(final Class modelType, final ModelRequestCallback callback) {
        requestModel((modelType == null) ? null : modelType.getName(), callback);
    }
    
    @SuppressWarnings("unchecked")
    public void requestModel(final String modelId, final ModelRequestCallback callback) {
        String id = (modelId == null) ? defaultModelId : modelId;

        ModelProvider<? extends Model> p = models.get(id);
        if (p != null) {
            p.requestModel(callback);
        } else if (getParentController() != null) {
            parentController.requestModel(modelId, callback);
        } else {
            if (callback != null) {
                callback.onRequestFail(new RuntimeException("The requested model was not found: " + modelId));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void requestModel(final ModelRequestCallback callback) {
        requestModel((String)null, callback);
    }

    public <T extends Model> void registerModel(String modelId, ModelProvider<T> provider) {
        models.put(modelId, provider);
    }
    
    public String getDefaultModelId() {
        return defaultModelId;
    }
    public void setDefaultModelId(String defaultModelId) {
        this.defaultModelId = defaultModelId;
    }
    
    /**
     * Registers an application eventhandler. The controller will try to propagate "unchecked" handlers to the parent
     * controller if a parent controller exists. This method can be overridden to handle unchecked locally if they are fired
     * locally.
     * 
     * @param type
     * @param handler
     * @return
     */
    @SuppressWarnings("unchecked")
    public HandlerRegistration addApplicationEventHandler(Type type, ApplicationEventHandler handler) {
        if ((handler instanceof UncheckedApplicationEventHandler) && (getParentController() != null)) {
            return parentController.addApplicationEventHandler(type, handler);
        }
        return applicationEventHandlers.addHandler(type, handler);
    }

    /**
     * Fires an application event.
     * 
     * @param event
     */
    @SuppressWarnings("unchecked")
    public void fireApplicationEvent(ApplicationEvent event) {
        // TODO this logic needs to be reworked a bit... if an unchecked event has been bound locally, do we want to still
        // fire it externally as well?
        if ((event instanceof UncheckedApplicationEvent) && (getParentController() != null)) {
            parentController.fireApplicationEvent(event);
        }
        // dispatch to local "checked" handlers, and to any unchecked handlers that have been bound to local
        applicationEventHandlers.fireEvent(event);

    }

    /**
     * Must be implemented by the subclass to render the view.
     * 
     * @param view
     */
    protected abstract void renderView(View view);

    /**
     * Must be implemented by the subclass to hide the view.
     * 
     * @param view
     */
    protected abstract void hideView(View view);

    /**
     * Returns the view associated with the specified enum value. See showView(V viewType) above for a full description
     * 
     * @param <V>
     * @param viewType
     * @return
     */
    protected abstract <V extends Enum<?>> void getView(V viewType, Callback<View> callback);
    
    /**
     * If a controller which extends this class must perform some action or check before a view
     * is changed, then override this method.  Do not call super() in the override, as it will
     * allow the view to continue to change.
     * @param okToChangeCallback
     */
    public void beforeViewChange(Enum<?> viewChangingTo, Callback<Boolean> okToChangeCallback) {
    	okToChangeCallback.exec(true);
    }

    /**
     * Shows the default view. Must be implemented by subclass, in order to define the default view.
     */
    public abstract void showDefaultView(Callback<Boolean> onReadyCallback);
    
    public abstract Enum<?> getViewEnumValue(String enumValue);
    
    /**
     * This particular implementation appends to the history stack the name of the current view shown by
     * this controller and view context (in string format) to that historyStack and passes the stack to
     * be processed to the currentView.
     * @see org.kuali.student.common.ui.client.mvc.history.HistorySupport#collectHistory(java.lang.String)
     */
    @Override
    public String collectHistory(String historyStack) {
    	String token = getHistoryToken();
    	historyStack = historyStack + "/" + token;
    	
    	if(currentView != null){
    		String tempHistoryStack = historyStack;
    		historyStack = currentView.collectHistory(historyStack);
    		
    		//Sanity check, if collectHistory returns null or empty string, restore
    		if(historyStack == null){
    			historyStack = tempHistoryStack;
    		}
    		else if(historyStack != null && historyStack.isEmpty()){
    			historyStack = tempHistoryStack;
    		}
    	}
    	return historyStack;
    }
    
    protected String getHistoryToken() {
    	String historyToken = "";
        if (currentViewEnum != null) {
            historyToken = currentViewEnum.toString();
            if(currentView != null && currentView instanceof Controller 
            		&& ((Controller)currentView).getViewContext() != null){
            	ViewContext context = ((Controller) currentView).getViewContext();
            	historyToken = HistoryManager.appendContext(historyToken, context);
            }
             
        }
        return historyToken;
    }

    /**
     * The onHistoryEvent implementation in controller reads the history stack it receives and determines
     * if the next token/view to be processed is a controller, if it is, it hands off the rest of the history stack
     * to that controller after showing it.  Otherwise, it shows the view
     * and allows that view to perform any onHistoryEvent actions it may need to take.
     * <br><br>For example the historyStack /HOME/CURRICULUM_HOME/COURSE_PROPOSAL would start at the root controller,
     * and hand it off to the home controller, then the curriculum home controller, then the course proposal controller
     * and stop there.  Along the way each of those controller would show themselves visually in the UI, 
     * if they contain any layout (some do not).
     * 
     * @see org.kuali.student.common.ui.client.mvc.history.HistorySupport#onHistoryEvent(java.lang.String)
     */
    @Override
    public void onHistoryEvent(String historyStack) {
    	final String nextHistoryStack = HistoryManager.nextHistoryStack(historyStack);
        String[] tokens = HistoryManager.splitHistoryStack(nextHistoryStack);
        if (tokens.length >= 1 && tokens[0] != null && !tokens[0].isEmpty()) {
            final Map<String, String> tokenMap = HistoryManager.getTokenMap(tokens[0]);
            //TODO add some automatic view context setting here, get and set
            String viewEnumString = tokenMap.get("view");
            if (viewEnumString != null) {
                final Enum<?> viewEnum = getViewEnumValue(viewEnumString);
                
                if (viewEnum != null) {
                	getView(viewEnum, new Callback<View>(){

						@Override
						public void exec(View result) {
							View theView = result;
			            	boolean sameContext = true;
		                	if(theView instanceof Controller){
		                		
		                		ViewContext newContext = new ViewContext();
		                		Iterator<String> tokenIt = tokenMap.keySet().iterator();
		                		while(tokenIt.hasNext()){
		                			String key = tokenIt.next();
		                			if(key.equals(ViewContext.ID_ATR)){
		                				newContext.setId(tokenMap.get(ViewContext.ID_ATR));
		                			}
		                			else if(key.equals(ViewContext.ID_TYPE_ATR)){
		                				newContext.setIdType(tokenMap.get(ViewContext.ID_TYPE_ATR));
		                			}
		                			//do not add view attribute from the token map to the context
		                			else if(!key.equals("view")){
		                				newContext.setAttribute(key, tokenMap.get(key));
		                			}
		                		}
		                		
		                		ViewContext viewContext = ((Controller) theView).getViewContext();
		                		if(viewContext.compareTo(newContext) != 0){
		                			((Controller) theView).setViewContext(newContext);
		                			sameContext = false;
		                		}
		                	}
		                    if (currentViewEnum == null || !viewEnum.equals(currentViewEnum) 
		                    		|| !sameContext) {
		                        beginShowView(theView, viewEnum, new Callback<Boolean>() {
		                            @Override
		                            public void exec(Boolean result) {
		                                if (result) {
		                                    currentView.onHistoryEvent(nextHistoryStack);
		                                }
		                            }
		                        });
		                    } else if (currentView != null) {
		                    	currentView.onHistoryEvent(nextHistoryStack);
		                    }
						}
					});
    
                }
            }
        }
        else{
    		this.showDefaultView(new Callback<Boolean>(){

				@Override
				public void exec(Boolean result) {
					if(result){
						currentView.onHistoryEvent(nextHistoryStack);
					}
					
				}
			});
    	}
        
    }

    /**
     * Sets the view context.  This is important for determining the permission for seeing views under
     * this controllers scope, what the id and id type of the model the controller handles are defined here.
     * Additional attributes that the controller and it's views need to know about are also defined in the
     * viewContext.
     * @param viewContext
     */
    public void setViewContext(ViewContext viewContext){
    	this.context = viewContext;
    }

    public ViewContext getViewContext() {
    	return this.context;
    }
    
    public void resetCurrentView(){
    	currentView = null;
    }
  
    /**
     * 
     * This method implement the "Generic Export" of a windows content to Jasper based on the format the user selected.
     * This method can be overwritten on a subclass to do specific export to the specific view
     * 
     * @see org.kuali.student.common.ui.client.reporting.ReportExport#doReportExport(java.util.ArrayList)
     */
    @Override
    public void doReportExport(ArrayList<ExportElement> exportElements, String format) {
     // TODO NINA Event is not working, as i can't seem to fire it on the specific controller...
        System.out.println("Generic report generator : report name = " + this.exportTemplateName);
        //
        
        View currView = this.getCurrentView();
        
     // Service call...
        
        DataModel dataModel = getExportDataModel();
        
        if (dataModel != null) {
            Data modelDataObject = dataModel.getRoot();
            
            reportExportRpcService.reportExport(exportElements, modelDataObject, getExportTemplateName(), format, new KSAsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                    // TODO Nina Confirm this is best way to handle onFailure...
                        System.out.println("reportExportRpcService was unsuccessfull");
                        caught.printStackTrace();
//
                        Window.alert(caught.getMessage());
                        GWT.log("Failed to retrieve clu", caught);
                    }

                    @Override
                    public void onSuccess(String result) {
                        // On success get documentID back from GWT Servlet
                        System.out.println("On success....export ID = " + result);
                        Window.open("/exportDownloadHTTPServlet?exportId="+result, "", "");                          
                    }
                });

            
        }
    }
       
    // TODO Nina ??? Do we want to keep this seen in the light of the exportElements parameter
    @Override
    public DataModel getExportDataModel() {
        return null;
    }

    /**
     * 
     * @see org.kuali.student.common.ui.client.reporting.ReportExport#getExportTemplateName()
     */
    @Override
    public String getExportTemplateName() {
        return exportTemplateName;
    }
    
    @Override
    public ArrayList<ExportElement> getExportElementsFromView() {
        String viewName = null;
        View currentView = this.getCurrentView();
        if (currentView != null) {
            
            ArrayList<ExportElement> exportElements = null;

            if (currentView != null && currentView instanceof SectionView) {
                viewName =  currentView.getName();
                exportElements = ExportUtils.getExportElementsFromView((SectionView)currentView, exportElements, viewName, "Sectionname");
                return exportElements;
            } else {
//                logger.warn("ExportUtils.getExportElementsFromView not implemented for :" + this.getCurrentView());
            }
        } else {
//            logger.warn("ExportUtils.getExportElementsFromView controller currentView is null :" + this.getClass().getName());
        }
        return null;
    }
}