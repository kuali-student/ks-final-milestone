package org.kuali.student.core.workflow.ui.client.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.LookupParamMetadata;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.Metadata.WriteAccess;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.rice.StudentWorkflowConstants.ActionRequestType;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.InfoMessage;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.event.ContentDirtyEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.util.UtilConstants;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.GroupFieldLayout;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.table.SimpleWidgetTable;
import org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService;
import org.kuali.student.core.workflow.ui.client.service.WorkflowRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 * This view can be used to add collaborators to a proposal. This view can only be used when both a ProposalWorkflowFilter
 * and a CollaboratorFilter is configured on the server. It expects the data model to have both the proposal and
 * collaborators elements.
 * 
 * @author Will
 */
public class CollaboratorSectionView extends SectionView {

	protected static final String COLLAB_PERSON_SUGGEST_NAME_LABEL_KEY = "collaboratorNameSuggest";
	
	
    protected WorkflowRpcServiceAsync workflowRpcServiceAsync = GWT.create(WorkflowRpcService.class);

    private QueryPath collabPath = QueryPath.parse("collaboratorInfo/collaborators");

    protected GroupSection section;
    protected FieldDescriptor person;
    protected FieldDescriptor permissions;
    protected FieldDescriptor actionRequests;
    protected FieldDescriptor authorNotation;
    protected KSButton addButton = new KSButton("Add Collaborator", ButtonStyle.SECONDARY);
    protected SimpleWidgetTable table;
    protected VerticalSection tableSection;
    protected InfoMessage saveWarning = new InfoMessage("The document must be saved before Collaborators can be added.", true);
    protected InfoMessage addWarning = new InfoMessage("Both Permission and Action Request must be selected before a collaborator could be added.", false);
    protected SimpleListItems permissionListItems = new SimpleListItems();
    protected SimpleListItems actionRequestListItems = new SimpleListItems();

    protected KSDropDown permissionList = new KSDropDown();
    protected KSDropDown actionRequestList = new KSDropDown();

    protected boolean canRemoveCollaborators = false;
    protected boolean loaded = false;

    protected String workflowId;
    protected String documentStatus = null;
    protected int numCollabs = 0;

    protected List<Data> newCollaborators = new ArrayList<Data>();
    
    public CollaboratorSectionView(){
    	
    }
    
    public CollaboratorSectionView(Enum<?> viewEnum, String name, String modelId) {
        this(viewEnum, name, modelId, true);
    }

    public CollaboratorSectionView(Enum<?> viewEnum, String name, String modelId, boolean showTitle) {
        super(viewEnum, name);
        this.modelId = modelId;
        if (name != null && !name.isEmpty() && showTitle) {
            SectionTitle sectionTitle = SectionTitle.generateH2Title(getName());
            layout = new GroupFieldLayout(sectionTitle);
        } else {
            layout = new GroupFieldLayout();
        }
        section = new GroupSection();
        section.addStyleName("KS-Add-Collaborator-Box");
        this.add(layout);
    }
    
   public void init(Enum<?> viewEnum, String name, String modelId){
    	init(viewEnum, name, modelId, true);
    }
    
    public void init(Enum<?> viewEnum, String name, String modelId, boolean showTitle){
    	init(viewEnum, name);
        this.modelId = modelId;
        if (name != null && !name.isEmpty() && showTitle) {
            SectionTitle sectionTitle = SectionTitle.generateH2Title(getName());
            layout = new GroupFieldLayout(sectionTitle);
        } else {
            layout = new GroupFieldLayout();
        }
        section = new GroupSection();
        section.addStyleName("KS-Add-Collaborator-Box");
        this.add(layout);
    } 

    public void init() {
        createAddCollabSection();

        List<String> columns = new ArrayList<String>();
        columns.add("Name");
        columns.add("Permissions");
        columns.add("Action Request");
        columns.add("Request Status");
        columns.add("Delete");

        table = new SimpleWidgetTable(columns);

        layout.add(saveWarning);
        layout.add(addWarning);
        layout.add(section);
        layout.add(addButton);
        addButton.addStyleName("ks-section-widget");
        addButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                String personId = null;
                String personName = null;
                if (person != null) {
                    Widget w = person.getFieldWidget();
                    if (w instanceof KSPicker) {
                        KSPicker personPicker = (KSPicker) w;
                        personId = (String) personPicker.getValue().get();
                        personName = personPicker.getDisplayValue();

                        if (personId != null && !personId.isEmpty() && !personId.equals(UtilConstants.IMPOSSIBLE_CHARACTERS)) {
                            String permissionCode = null;
                            if (permissions != null) {
                                permissionCode = permissionList.getSelectedItem();
                            }
                            String actionRequestCode = null;
                            if (actionRequests != null) {
                                actionRequestCode = actionRequestList.getSelectedItem();
                            }
                            KSCheckBox authorCheckbox = (KSCheckBox) authorNotation.getFieldWidget();
                            boolean isAuthor = authorCheckbox.getValue();

                            if (permissionCode != null && actionRequestCode != null) {
                                addCollaborator(personId, personName, permissionCode, actionRequestCode, isAuthor);

                                personPicker.clear();
                                authorCheckbox.setValue(false);
                                actionRequestList.clear();
                                permissionList.clear();
                                refreshActionRequestListItems();
                                addWarning.setVisible(false);
                            } else {
                                addWarning.setVisible(true);
                            }
                        }
                    }
                }
            }
        });

        actionRequestList.addSelectionChangeHandler(new SelectionChangeHandler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                String selectedAction = actionRequestList.getSelectedItem();
                refreshPermissionList(selectedAction);
            }

        });

        layout.add(createTableSection());

        loaded = true;
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        super.beforeShow(new Callback<Boolean>() {
            @Override
            public void exec(Boolean result) {
                if (!loaded) {
                    init();
                }

                workflowId = model.get(QueryPath.parse("proposal/workflowId"));
                if (workflowId != null && !workflowId.isEmpty()) {
                    saveWarning.setVisible(false);
                    section.setVisible(true);
                    tableSection.setVisible(true);
                    addButton.setVisible(true);
                    refreshDocumentStatus(onReadyCallback);
                } else {
                    saveWarning.setVisible(true);
                    section.setVisible(false);
                    addButton.setVisible(false);
                    tableSection.setVisible(false);
                    documentStatus = null;
                    onReadyCallback.exec(true);
                }
            }
        });
    }

    protected void refreshDocumentStatus(final Callback<Boolean> onReadyCallback) {
        workflowRpcServiceAsync.getDocumentStatus(workflowId, new KSAsyncCallback<String>() {
            @Override
            public void handleFailure(Throwable caught) {
                documentStatus = null;
                checkAuthorization(onReadyCallback);
            }

            @Override
            public void onSuccess(final String result) {
                documentStatus = result;
                refreshActionRequestListItems();
                checkAuthorization(onReadyCallback);
            }
        });
    }

    protected void checkAuthorization(final Callback<Boolean> onReadyCallback) {
        workflowRpcServiceAsync.isAuthorizedAddReviewer(workflowId, new KSAsyncCallback<Boolean>() {
            @Override
            public void handleFailure(Throwable caught) {
                GWT.log("Caught error trying to verify authorization for adding adhoc reviewers", caught);
                Window.alert("Error checking authorization for adding collaborators/reviewers");
                onReadyCallback.exec(true);
            }

            @Override
            public void onSuccess(Boolean result) {
                GWT.log("Authorization check for adding adhoc reviewers: " + result, null);
                section.setVisible(result);
                addButton.setVisible(result);

                workflowRpcServiceAsync.isAuthorizedRemoveReviewers(workflowId, new KSAsyncCallback<Boolean>() {
                    @Override
                    public void handleFailure(Throwable caught) {
                        GWT.log("Caught error trying to verify authorization for removing adhoc reviewers", caught);
                        Window.alert("Error checking authorization for removing collaborators/reviewers");
                        onReadyCallback.exec(true);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        GWT.log("Authorization check for removing adhoc reviewers: " + result, null);
                        canRemoveCollaborators = result;
                        refreshCollaboratorsTable();
                        onReadyCallback.exec(true);
                    }
                });
            }
        });

    }

    private void createAddCollabSection() {
        // Retrieve person meta data.
        String principalIdKey = "collaboratorInfo/collaborators/*/principalId";
        Metadata personIdMeta = model.getMetadata(QueryPath.parse(principalIdKey));

        // Add current logged on user to initial lookup data.
        LookupParamMetadata param = new LookupParamMetadata();
        param.setKey("person.queryParam.excludedUserId");
        param.setDefaultValueString(Application.getApplicationContext().getSecurityContext().getUserId());
        param.setWriteAccess(WriteAccess.NEVER);
        personIdMeta.getInitialLookup().getParams().add(param); // Added for the suggestbox.
        if (personIdMeta.getAdditionalLookups().size() > 0) {
            personIdMeta.getAdditionalLookups().get(0).getParams().add(param); // Added for the search window.
        }

        // Retrieve permission and action meta data.
        Metadata permissionMeta = model.getMetadata(QueryPath.parse("collaboratorInfo/collaborators/*/permission"));
        Metadata actionMeta = model.getMetadata(QueryPath.parse("collaboratorInfo/collaborators/*/action"));
        
        person = new FieldDescriptor(null, new MessageKeyInfo("course", null, getController().getViewContext().getState(), COLLAB_PERSON_SUGGEST_NAME_LABEL_KEY), personIdMeta);
        final KSPicker personPicker = (KSPicker) person.getFieldElement().getFieldWidget();
        personPicker.addFocusLostCallback(new Callback<Boolean>() {
            @Override
            public void exec(Boolean result) {
                Data.Value value = personPicker.getValue();
                if (value instanceof Data.StringValue) {
                    String stringValue = ((Data.StringValue) value).get();
                    FieldElement fieldElement = person.getFieldElement();
                    fieldElement.clearValidationErrors();
                    if (stringValue.equals(UtilConstants.IMPOSSIBLE_CHARACTERS)) {
                        fieldElement.addValidationErrorMessage("Invalid Value");
                    }
                }
            }
        });
        permissions = new FieldDescriptor(null, generateMessageInfo("Permission"), permissionMeta);
        permissions.setFieldWidget(permissionList);
        actionRequests = new FieldDescriptor(null, generateMessageInfo("Action Request"), actionMeta);
        actionRequests.setFieldWidget(actionRequestList);
        authorNotation = new FieldDescriptor(null, generateMessageInfo("Author Notation"), actionMeta);
        authorNotation.setFieldWidget(new KSCheckBox("Add Author Notation"));
        section.addField(person);
        section.addField(permissions);
        section.addField(actionRequests);
        section.addField(authorNotation);

        permissionList.setBlankFirstItem(true);
        actionRequestList.setBlankFirstItem(true);
    }

    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo(null, null, null, labelKey);
    }

    protected boolean isDocumentPreRoute() {
        return "I".equals(documentStatus) || "S".equals(documentStatus);
    }

    protected void refreshActionRequestListItems() {
        actionRequestListItems.clear();
        if (isDocumentPreRoute()) {
            actionRequestListItems.addItem(ActionRequestType.FYI.getActionRequestCode(), ActionRequestType.FYI.getActionRequestLabel());
        } else {
            actionRequestListItems.addItem(ActionRequestType.APPROVE.getActionRequestCode(), ActionRequestType.APPROVE.getActionRequestLabel());
            actionRequestListItems.addItem(ActionRequestType.ACKNOWLEDGE.getActionRequestCode(), ActionRequestType.ACKNOWLEDGE.getActionRequestLabel());
            actionRequestListItems.addItem(ActionRequestType.FYI.getActionRequestCode(), ActionRequestType.FYI.getActionRequestLabel());

        }
        actionRequestList.setListItems(actionRequestListItems);
        refreshPermissionList(ActionRequestType.FYI.getActionRequestCode());
    }

    /**
     * If this code is changed or overriden to allow non-APPROVE adhoc requests to have the EDIT permission then the Kuali
     * Student Post Processor classes that remove permission added via the People/Permissions screen must be altered to
     * remove EDIT permission when a route level changes or users who are sent an ACKKNOWLEDGE or FYI request will keep their
     * edit access across nodes
     */
    protected void refreshPermissionList(String selectedAction) {
        permissionListItems.clear();
        // SEE JAVADOC ABOVE IF CODE BELOW IS CHANGED OR OVERRIDEN
        if (selectedAction != null && selectedAction.equals(ActionRequestType.APPROVE.getActionRequestCode()) || isDocumentPreRoute()) {
            permissionListItems.addItem(PermissionType.EDIT.getCode(), "Edit, Comment, View");
        }

        permissionListItems.addItem(PermissionType.ADD_COMMENT.getCode(), "Comment, View");
        permissionListItems.addItem(PermissionType.OPEN.getCode(), "View");

        permissionList.setListItems(permissionListItems);
    }

    /**
     * Is this translation necessary, does it make sense to use these labels in the PermissonType itself?
     * 
     * @param permCode
     * @return
     */
    private String translatePermissionCode(String permCode) {
        PermissionType permType = PermissionType.getByCode(permCode);
        switch (permType) {
            case ADD_COMMENT:
                return "Comment, View";
            case EDIT:
                return "Edit, Comment, View";
            case OPEN:
                return "View";
        }
        return "";
    }

    private Widget createTableSection() {
        tableSection = new VerticalSection(SectionTitle.generateH3Title("Added People"));
        tableSection.addWidget(table);

        return tableSection;
    }

    private void addCollaborator(final String recipientPrincipalId, final String recipientPersonName, final String selectedPermissionCode, final String selectedActionRequest, boolean isAuthor) {
        // TODO: Is there a way to get first, last name from picker w/o parsing display value
        String firstName = "";
        String lastName = "";
        String[] nameTokens = recipientPersonName.split("[,\\(]");
        if (nameTokens.length >= 3) {
            lastName = nameTokens[0].trim();
            firstName = nameTokens[1].trim();
        } else {
            firstName = recipientPersonName;
        }

        // Add person to new collaborators list
        if (!prinicipalExists(recipientPrincipalId)) {
            Data personData = new Data();
            personData.set("principalId", recipientPrincipalId);
            personData.set("permission", selectedPermissionCode);
            personData.set("action", selectedActionRequest);
            personData.set("firstName", firstName);
            personData.set("lastName", lastName);
            personData.set("actionRequestStatus", "New");
            personData.set("author", isAuthor);

            newCollaborators.add(personData);
            refreshCollaboratorsTable();

            if (!isDirty()) {
                setIsDirty(true);
                getController().fireApplicationEvent(new ContentDirtyEvent());
            }
        }
    }

    public boolean prinicipalExists(String principalId) {
        Map<QueryPath, Object> collabs = model.query("collaboratorInfo/collaborators/*");

        if (collabs != null) {
            for (Object personData : collabs.values()) {
                if (principalId.equals((String) ((Data) personData).get("principalId"))) {
                    return true;
                }
                numCollabs++;
            }
        }

        for (Data personData : newCollaborators) {
            if (principalId.equals(personData.get("principalId"))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void updateModel() {
        // Just copying code from VerticalSectionView, why is the isValidationEnabledCheck here?
        if (model != null && isValidationEnabled()) {
            updateModel(model);
        }
    }

    private void refreshCollaboratorsTable() {
        Map<QueryPath, Object> collabs = model.query("collaboratorInfo/collaborators/*");

        table.clear();
        Collections.sort(newCollaborators, new Comparator<Data>() {
          
            @Override
            public int compare(Data personData1, Data personData2) {
            	 return personName(personData1).compareToIgnoreCase(personName(personData2));
            }
            
            String personName(Data personData){
        	   String personName = "";
               if (personData.query("lastName") != null) {
                   personName = personData.query("lastName") + ", " + personData.query("firstName");
               } else {
                   personName = personData.query("principalId");
               }
               return personName;
           }
        });
        numCollabs = 0;
        for (int i = 0; i < newCollaborators.size(); i++) {
            addPersonRow(newCollaborators.get(i), new Integer(i));
            numCollabs++;
        }

        for (Object personData : collabs.values()) {
            if (!("Remove").equals(((Data) personData).query("actionRequestStatus"))) {
                addPersonRow((Data) personData, null);
                numCollabs++;
            }
        }

        if (numCollabs > 0) {
            tableSection.getLayout().setLayoutTitle(SectionTitle.generateH3Title("Added Collaborators (" + numCollabs + ")"));
        } else {
            tableSection.getLayout().setLayoutTitle(SectionTitle.generateH3Title("Added Collaborators"));
        }

    }

    private void addPersonRow(final Data personData, final Integer deleteIndex) {
        final String personName;
        if (personData.query("lastName") != null) {
            personName = personData.query("lastName") + ", " + personData.query("firstName");
        } else {
            personName = personData.query("principalId");
        }

        Boolean isAuthor = personData.query("author");
        Boolean canRevokeRequest = personData.query("canRevokeRequest");

        // Add person to table widget
        List<Widget> rowWidgets = new ArrayList<Widget>();
        rowWidgets.add(new KSLabel(personName + (isAuthor != null && isAuthor ? " (Author)" : "")));
        rowWidgets.add(new KSLabel(translatePermissionCode((String) personData.query("permission"))));
        rowWidgets.add(new KSLabel(ActionRequestType.getByCode((String) personData.query("action")).getActionRequestLabel()));
        rowWidgets.add(new KSLabel((String) personData.get("actionRequestStatus")));

        if (canRemoveCollaborators && (canRevokeRequest == null || canRevokeRequest)) {
            // Add delete widget
            AbbrButton removeButton = new AbbrButton(AbbrButtonType.DELETE);
            rowWidgets.add(removeButton);
            // Register remove click handler
            if (deleteIndex != null) {
                // Handler for newly added collaborators
                removeButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        Boolean ok = Window.confirm("Are you sure you want remove " + personName + " as a collaborator?");
                        if (ok) {
                            newCollaborators.remove(deleteIndex.intValue());
                            refreshCollaboratorsTable();
                        }
                    }
                });
            } else {
                // Handler for existing collaborators
                removeButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        Boolean ok = Window.confirm("Are you sure you want remove " + personName + " as a collaborator?");
                        if (ok) {
                            personData.set("actionRequestStatus", "Remove");
                            refreshCollaboratorsTable();
                        }
                    }
                });
            }
        } else {
            // add a dummy label for table placeholder
            rowWidgets.add(new KSLabel());
        }

        table.addRow(rowWidgets);
    }

    @Override
    public void updateModel(DataModel model) {
        super.updateModel(model);
        Data collaborators = model.get(collabPath);
        if (collaborators == null) {
            collaborators = new Data();
            model.set(collabPath, collaborators);
        }
        for (Data personData : newCollaborators) {
            collaborators.add(personData);
        }
    }

    @Override
    public void updateWidgetData(DataModel model) {
        super.updateWidgetData(model);
        if (loaded) {
            newCollaborators.clear();
            refreshCollaboratorsTable();
        }
    }

}
