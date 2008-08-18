/**
 * 
 */
package org.kuali.student.registration.client.controller;

import java.util.List;

import org.kuali.student.commons.ui.widgets.BusyWidgetShade;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationDisplay;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationInfo;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationTypeInfo;
import org.kuali.student.registration.client.model.GwtRelationStateInfo;
import org.kuali.student.registration.client.model.RegistrationModelState;
import org.kuali.student.registration.client.service.RegistrationService;
import org.kuali.student.registration.client.service.RegistrationServiceAsync;
import org.kuali.student.ui.personidentity.client.ModelState;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Garey
 */
public class RegistrationController {

    protected static RegistrationController instance = null;
    protected static RegistrationServiceAsync service = null;

    public static final String SERVICE_URI = "RegistrationService";

    /**
     * 
     */
    protected RegistrationController() {
        super();
    }

    public static RegistrationController getInstance() {
        return instance == null ? instance = new RegistrationController() : instance;
    }

    public static synchronized RegistrationServiceAsync getService() {
        if (service == null) {
            service = (RegistrationServiceAsync) GWT.create(RegistrationService.class);
            ServiceDefTarget piEndpoint = (ServiceDefTarget) service;
            piEndpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
        }
        return service;
    }

    public void createLuiPersonRelation(String personId, String luiId, GwtRelationStateInfo relationStateInfo, GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo, GwtLuiPersonRelationInfo luiPersonRelationCreateInfo) {

        getService().createLuiPersonRelation(personId, luiId, relationStateInfo, luiPersonRelationTypeInfo, luiPersonRelationCreateInfo, new AsyncCallback() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(Object result) {
                if (ModelState.getInstance().getCurrPerson() != null)
                    RegistrationController.getInstance().setCurrentUserLuiRelations(ModelState.getInstance().getCurrPerson().getPersonId());
            }

        });

    }

    public void createLuiPersonRelation(String personId, List<String> luiIds, GwtRelationStateInfo relationStateInfo, GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo, GwtLuiPersonRelationInfo luiPersonRelationCreateInfo) {
        getService().createLuiPersonRelation(personId, luiIds, relationStateInfo, luiPersonRelationTypeInfo, luiPersonRelationCreateInfo, new AsyncCallback() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(Object result) {
                if (ModelState.getInstance().getCurrPerson() != null)
                    RegistrationController.getInstance().setCurrentUserLuiRelations(ModelState.getInstance().getCurrPerson().getPersonId());
            }

        });

    }

    public void updateLuiPersonRelation(String luiPersonRelationId, GwtLuiPersonRelationInfo luiPersonRelationUpdateInfo) {

        getService().updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationUpdateInfo, new AsyncCallback() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(Object result) {
                if (ModelState.getInstance().getCurrPerson() != null)
                    RegistrationController.getInstance().setCurrentUserLuiRelations(ModelState.getInstance().getCurrPerson().getPersonId());
            }
        });
    }

    public void updateLuiPersonRelation(List<String> luiPersonRelationIds, GwtLuiPersonRelationInfo luiPersonRelationUpdateInfo) {
        this.updateLuiPersonRelation(luiPersonRelationIds, luiPersonRelationUpdateInfo, null);
    }
    
    public void updateLuiPersonRelation(List<String> luiPersonRelationIds, GwtLuiPersonRelationInfo luiPersonRelationUpdateInfo, final Widget shadeWidget) {

        for (final String luiPersonRelationId : luiPersonRelationIds) {
            GwtLuiPersonRelationInfo hk = new GwtLuiPersonRelationInfo();
            hk.setLuiPersonRelationType(luiPersonRelationUpdateInfo.getLuiPersonRelationType());
            hk.setRelationState(luiPersonRelationUpdateInfo.getRelationState());
            hk.setLuiPersonRelationId(luiPersonRelationId);

            final String lastId = luiPersonRelationIds.get(luiPersonRelationIds.size() - 1);

            getService().updateLuiPersonRelation(luiPersonRelationId, hk, new AsyncCallback() {
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());                                        
                }

                public void onSuccess(Object result) {
                    if (luiPersonRelationId.equals(lastId)) {
                        if (ModelState.getInstance().getCurrPerson() != null){
                            RegistrationController.getInstance().setCurrentUserLuiRelations(ModelState.getInstance().getCurrPerson().getPersonId(), shadeWidget);               
                        }
                    }
                }
            });
        }
    }

    public void setCurrentUserBasket(String personId) {
        this.setCurrentUserLuiRelations(personId);
        /*
         * getService().findLuiInfosRelatedToPerson(personId, RegistrationService.REL_STUDENT,
         * RegistrationService.STATE_BASKET, new AsyncCallback(){ public void onFailure(Throwable caught) {
         * Window.alert(caught.getMessage()); } public void onSuccess(Object result) {
         * RegistrationModelState.getInstance().setCurrUserBasket((List<GwtLuiInfo>)result); }});
         */
    }

    public void setCurrentUserLuiRelations(String personId) {
        this.setCurrentUserLuiRelations(personId, null);
    }
    
    public void setCurrentUserLuiRelations(String personId, final Widget shadeWidget) {
        if(shadeWidget != null)
            BusyWidgetShade.shade(shadeWidget);
        
        if (personId == null) {
            RegistrationModelState.getInstance().setCurrUserRelations(null);
            RegistrationModelState.getInstance().setCurrUserCourses(null);
        } else {
            getService().findLuiPersonRelationsForPerson(personId, new AsyncCallback() {
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                    if(shadeWidget != null)
                        BusyWidgetShade.unshade(shadeWidget);
                }
    
                public void onSuccess(Object result) {
                    RegistrationModelState.getInstance().setCurrUserRelations((List<GwtLuiPersonRelationDisplay>) result);
                    if(shadeWidget != null)
                        BusyWidgetShade.unshade(shadeWidget);
                }
            });

        	getService().findLuiInfosRelatedToPerson(personId, RegistrationService.REL_STUDENT, RegistrationService.STATE_COMPLETE, new AsyncCallback() {
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                    if(shadeWidget != null)
                        BusyWidgetShade.unshade(shadeWidget);
                }
    
                public void onSuccess(Object result) {
                    RegistrationModelState.getInstance().setCurrUserCourses((List<GwtLuiInfo>) result);
                    if(shadeWidget != null)
                        BusyWidgetShade.unshade(shadeWidget);
                }
            });
        	
        	getService().findLuiInfosRelatedToPerson(personId, RegistrationService.REL_STUDENT, RegistrationService.STATE_BASKET, new AsyncCallback() {
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                    if(shadeWidget != null)
                        BusyWidgetShade.unshade(shadeWidget);
                }
    
                public void onSuccess(Object result) {
                    RegistrationModelState.getInstance().setCurrUserBasket((List<GwtLuiInfo>) result);
                    if(shadeWidget != null)
                        BusyWidgetShade.unshade(shadeWidget);
                }
            });
            
        }
    }

    public void deleteLuiPersonRelation(final List<String> luiPersonRelationIds) {
        if (luiPersonRelationIds != null) {
            final String lastId = luiPersonRelationIds.get(luiPersonRelationIds.size() - 1);
            for (final String id : luiPersonRelationIds) {
                getService().deleteLuiPersonRelation(id, new AsyncCallback() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(Object result) {
                        if (id.equals(lastId)) {
                            if (ModelState.getInstance().getCurrPerson() != null) {
                                setCurrentUserLuiRelations(ModelState.getInstance().getCurrPerson().getPersonId());
                            }
                        }
                    }
                });

            }

        }
    }

    public void setCurrentUserCourses(String personId) {
        this.setCurrentUserLuiRelations(personId);

        /*
         * getService().findLuiInfosRelatedToPerson(personId, RegistrationService.REL_STUDENT,
         * RegistrationService.STATE_COMPLETE, new AsyncCallback(){ public void onFailure(Throwable caught) {
         * Window.alert(caught.getMessage()); } public void onSuccess(Object result) {
         * RegistrationModelState.getInstance().setCurrUserCourses((List<GwtLuiInfo>)result); }});
         */
    }

}
