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

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.breadcrumb.BreadcrumbSupport;
import org.kuali.student.common.ui.client.mvc.events.ViewChangeEvent;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.mvc.history.HistorySupport;
import org.kuali.student.common.ui.client.mvc.history.NavigationEvent;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.core.rice.authorization.PermissionType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * Abstract Controller composite. Provides basic controller operations, and defines abstract methods that a composite must
 * implement in order to be a controller.
 * 
 * @author Kuali Student Team
 */
public abstract class Controller extends Composite implements HistorySupport, BreadcrumbSupport{
	public static final Callback<Boolean> NO_OP_CALLBACK = new Callback<Boolean>() {
		@Override
		public void exec(Boolean result) {
			// do nothing
		}
	};
	
	private final String controllerId;
    private Controller parentController = null;
    private View currentView = null;
    private Enum<?> currentViewEnum = null;
    private String defaultModelId = null;
    private ViewContext context = new ViewContext();
    private final Map<String, ModelProvider<? extends Model>> models = new HashMap<String, ModelProvider<? extends Model>>();

    private HandlerManager applicationEventHandlers = new HandlerManager(this);

    protected Controller(final String controllerId) {
        this.controllerId = controllerId;
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
        final View view = getView(viewType);
        if (view == null) {
        	onReadyCallback.exec(false);
            throw new ControllerException("View not registered: " + viewType.toString());
        }
        
        beforeViewChange(new Callback<Boolean>(){

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
				        	
				        	if (view instanceof DelegatingViewComposite) {
				        		tempContext = ((DelegatingViewComposite)view).getChildController().getViewContext();
				        	}
				        	
				        	PermissionType permType = (tempContext != null) ? tempContext.getPermissionType() : null;
				        	if (permType != null) {
				        		GWT.log("Checking permission type '" + permType.getPermissionTemplateName() + "' for view '" + view.toString() + "'", null);
				            	//A callback is required if async rpc call is required for authz check
					        	((RequiresAuthorization)view).checkAuthorization(permType, new AuthorizationCallback(){
									public void isAuthorized() {
										showView(view, viewType, onReadyCallback);
									}
				
									public void isNotAuthorized(String msg) {
										Window.alert(msg);
										onReadyCallback.exec(false);					
									}        		
					        	});
				        	}
				        	else {
				        		GWT.log("Cannot find PermissionType for view '" + view.toString() + "' which requires authorization", null);
				            	showView(view, viewType, onReadyCallback);
				        	}
				        } else {
				    		GWT.log("Not Requiring Auth.", null);
				        	showView(view, viewType, onReadyCallback);
				        }
				}
				else{
					onReadyCallback.exec(false);
				}
				
			}
		});
    }
    
    private <V extends Enum<?>> void showView(final View view, final V viewType, final Callback<Boolean> onReadyCallback){
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
			            fireApplicationEvent(new ViewChangeEvent(currentView, view));
			            currentView = view;
			            GWT.log("renderView " + viewType.toString(), null);
			            renderView(view);
			        	onReadyCallback.exec(true);
			        	fireNavigationEvent();
					}
				}
			});
        } else {
        	onReadyCallback.exec(false);
            GWT.log("Current view canceled hide action", null);
        }    	
    }

    protected void fireNavigationEvent() {
        DeferredCommand.addCommand(new Command() {
            @Override
            public void execute() {
                fireApplicationEvent(new NavigationEvent(Controller.this));
            }
        });
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
            callback.onRequestFail(new RuntimeException("The requested model was not found: " + modelId));
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
    protected void setDefaultModelId(String defaultModelId) {
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
    protected abstract <V extends Enum<?>> View getView(V viewType);
    
    /**
     * If a controller which extends this class must perform some action or check before a view
     * is changed, then override this method.  Do not call super() in the override, as it will
     * allow the view to continue to change.
     * @param okToChangeCallback
     */
    public void beforeViewChange(Callback<Boolean> okToChangeCallback){
    	okToChangeCallback.exec(true);
    }

    /**
     * Shows the default view. Must be implemented by subclass, in order to define the default view.
     */
    public abstract void showDefaultView(Callback<Boolean> onReadyCallback);

    public abstract Class<? extends Enum<?>> getViewsEnum();
    
    public abstract Enum<?> getViewEnumValue(String enumValue);
    
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

    @Override
    public void onHistoryEvent(String historyStack) {
    	final String nextHistoryStack = HistoryManager.nextHistoryStack(historyStack);
        String[] tokens = HistoryManager.splitHistoryStack(nextHistoryStack);
        if (tokens.length >= 1 && tokens[0] != null && !tokens[0].isEmpty()) {
            Map<String, String> tokenMap = HistoryManager.getTokenMap(tokens[0]);
            //TODO add some automatic view context setting here, get and set
            String viewEnumString = tokenMap.get("view");
            if (viewEnumString != null) {
                Enum<?> viewEnum = getViewEnumValue(viewEnumString);
                
                if (viewEnum != null) {
                	View theView = getView(viewEnum);
                	boolean sameContext = true;
                	if(theView instanceof Controller){
                		
                		ViewContext newContext = new ViewContext();
                		if(tokenMap.get(ViewContext.ID_ATR) != null){
                			newContext.setId(tokenMap.get(ViewContext.ID_ATR));
                		}
                		if(tokenMap.get(ViewContext.ID_TYPE_ATR) != null){
                			newContext.setIdType(tokenMap.get(ViewContext.ID_TYPE_ATR));
                		}
                		
                		ViewContext viewContext = ((Controller) theView).getViewContext();
                		if(viewContext.compareTo(newContext) != 0){
                			((Controller) theView).setViewContext(newContext);
                			sameContext = false;
                		}
                	}
                    if (currentViewEnum == null || !viewEnum.equals(currentViewEnum) 
                    		|| !sameContext) {
                        showView(viewEnum, new Callback<Boolean>() {
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

    public void setViewContext(ViewContext viewContext){
    	this.context = viewContext;
    }

    public ViewContext getViewContext() {
    	return this.context;
    }

    public void clearViewContext(){
        this.context = new ViewContext();
    }

    public String getControllerId() {
        return this.controllerId;
    }
    
    public void resetCurrentView(){
    	currentView = null;
    }
    

}
