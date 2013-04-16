package org.kuali.student.lum.program.client.credential.view;

import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.lu.LUUIPermissions;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.common.client.widgets.DropdownList;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.ProgramStatus;
import org.kuali.student.lum.program.client.credential.CredentialController;
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.events.ProgramViewEvent;
import org.kuali.student.lum.program.client.major.ActionType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;

/**
 * @author Igor
 */
public class CredentialViewController extends CredentialController implements RequiresAuthorization{
    /**
     * Initialize the action drop-down with a list of values.  Note that these values
     * will be changed further down in the code depending on if we are working with the latest 
     * version of the program.
     */
    private final DropdownList actionBox = new DropdownList(ActionType.getValuesForCredentialProgram(false));

    /**
     * Constructor.
     *
     * @param programModel
     */
    public CredentialViewController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(programModel, viewContext, eventBus);
        configurer = GWT.create(CredentialViewConfigurer.class);
        bind();
    }

    private void bind() {
        actionBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                ActionType actionType = ActionType.of(actionBox.getSelectedValue());
                ViewContext viewContext = getViewContext();
                if (actionType == ActionType.MODIFY) {
                    ProgramRegistry.setSection(ProgramSections.getEditSection(getCurrentViewEnum()));
                    HistoryManager.navigate(AppLocations.Locations.EDIT_BACC_PROGRAM.getLocation(), getViewContext());
                } else if (actionType == ActionType.MODIFY_VERSION) {
                    String versionIndId = getStringProperty(ProgramConstants.VERSION_IND_ID);
                    viewContext.setId(versionIndId);
                    viewContext.setIdType(IdType.COPY_OF_OBJECT_ID);
                    HistoryManager.navigate(AppLocations.Locations.EDIT_BACC_PROGRAM.getLocation(), viewContext);
                }
            }
        });
        eventBus.addHandler(ProgramViewEvent.TYPE, new ProgramViewEvent.Handler() {
            @Override
            public void onEvent(ProgramViewEvent event) {
                actionBox.setSelectedIndex(0);
            }
        });
        eventBus.addHandler(ModelLoadedEvent.TYPE, new ModelLoadedEvent.Handler() {
            @Override
            public void onEvent(ModelLoadedEvent event) {
                resetActionList();
            }
        });
    }

    @Override
    protected void configureView() {
        super.configureView();
        addContentWidget(actionBox);
        initialized = true;
    }

    protected void resetActionList() {
        //Only allow modify with version option for an active course that id also the latest version
        ProgramStatus status = ProgramStatus.of(programModel);
        String versionIndId = getStringProperty(ProgramConstants.VERSION_IND_ID);
        Long sequenceNumber = programModel.get(ProgramConstants.VERSION_SEQUENCE_NUMBER);

        actionBox.clear();
        if (status == ProgramStatus.ACTIVE) {
            programRemoteService.isLatestVersion(versionIndId, sequenceNumber, new KSAsyncCallback<Boolean>() {
                public void onSuccess(Boolean isLatest) {
                    actionBox.setList(ActionType.getValuesForCredentialProgram(isLatest));
                }
            });
        } else {
            actionBox.setList(ActionType.getValuesForCredentialProgram(false));
        }
    }
    
    @Override
	public boolean isAuthorizationRequired() {
		return true;
	}

	@Override
	public void setAuthorizationRequired(boolean required) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void checkAuthorization(final AuthorizationCallback authCallback) {
		Application.getApplicationContext().getSecurityContext().checkScreenPermission(LUUIPermissions.USE_VIEW_CREDENTIAL_PROGRAMS_SCREEN, new Callback<Boolean>() {
			@Override
			public void exec(Boolean result) {

				final boolean isAuthorized = result;
	        
				if(isAuthorized){
					authCallback.isAuthorized();
				}
				else
					authCallback.isNotAuthorized("User is not authorized: " + LUUIPermissions.USE_VIEW_CREDENTIAL_PROGRAMS_SCREEN);
			}	
		});
	}
}
